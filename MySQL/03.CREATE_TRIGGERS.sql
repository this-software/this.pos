DROP TRIGGER IF EXISTS `i_post_products`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE CONTABILIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UN INVENTARIO DE TIPO "SISTEMA"
CREATE TRIGGER i_post_products
AFTER INSERT ON `inventory` FOR EACH ROW
BEGIN
	-- SI EL REGISTRO ES DE TIPO "SISTEMA"
	IF NEW.`type` = 3 THEN
		INSERT INTO `inventory_product` (`inventory_id`, `product_id`, `unit_id`, `units`)
		SELECT NEW.`id`, `p`.`id`, `pu`.`unit_id`, `p`.`units_in_stock`
		FROM `product` `p`
        JOIN `product_unit` `pu` ON `p`.`id` = `pu`.`product_id`
        WHERE (`p`.`deleted` = 0 OR `p`.`deleted` IS NULL) AND `pu`.`is_default` = 1;
	END IF;
END; //
DELIMITER ;


DROP TRIGGER IF EXISTS `i_update_units_in_stock`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA "CANCELACIÓN" DE INVENTARIO
CREATE TRIGGER i_update_units_in_stock
AFTER UPDATE ON `inventory` FOR EACH ROW
BEGIN
	DECLARE f INT DEFAULT FALSE;
    DECLARE product_id INT;
    DECLARE units INT;
    DECLARE quantity_by_unit INT;
    DECLARE inventory_products CURSOR FOR
		-- SE OBTIENEN SOLO LOS REGISTROS QUE ERAN DE TIPO "ENTRADA" O "SALIDA"
		SELECT `ip`.`product_id`, `ip`.`units`, `pu`.`quantity_by_unit`
        FROM `inventory_product` `ip`
        JOIN `product_unit` `pu` ON `ip`.`product_id` = `pu`.`product_id` AND `ip`.`unit_id` = `pu`.`unit_id`
        WHERE `ip`.`inventory_id` = NEW.`id` AND (OLD.`type` = 0 OR OLD.`type` = 1);
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET f = TRUE;
    
    OPEN inventory_products;
    INVENTORY_PRODUCTS_LOOP: LOOP
		-- SIGUIENTE RESULTADO
		FETCH inventory_products INTO product_id, units, quantity_by_unit;
        -- ¿YA NO EXISTEN RESULTADOS?
        IF f = TRUE THEN
			LEAVE INVENTORY_PRODUCTS_LOOP;
		END IF;
        -- SI EL REGISTRO ERA DE TIPO "ENTRADA" Y SE GENERA UNA "CANCELACIÓN" SE RESTAN LAS UNIDADES PREVIAMENTE INGRESADAS DE LAS EXISTENTES.
		IF OLD.`type` = 0 AND NEW.`type` = 2 THEN
			UPDATE `product` `p`
            SET `p`.`units_in_stock` = (`p`.`units_in_stock` - (units * quantity_by_unit))
            WHERE `p`.`id` = product_id;
		-- SI EL REGISTRO ERA DE TIPO "SALIDA" Y SE GENERA UNA "CANCELACIÓN" SE SUMAN LAS UNIDADES A LAS EXISTENTES.
		ELSEIF OLD.`type` = 1 AND NEW.`type` = 2 THEN
			UPDATE `product` `p`
            SET `p`.`units_in_stock` = (`p`.`units_in_stock` + (units * quantity_by_unit))
            WHERE `p`.`id` = product_id;
            -- SI EXISTE UNA SOLICITUD PENDIENTE SE ELIMINA
		END IF;
	END LOOP INVENTORY_PRODUCTS_LOOP;
    CLOSE inventory_products;
    
    -- SI EL REGISTRO ERA DE TIPO ("SISTEMA" Y FUE EL ÚLTIMO GENERADO Y SE GENERA UNA "CANCELACIÓN") SE ESTABLECEN A "0" LAS UNIDADES REQUERIDAS
    IF OLD.`type` = 3 AND (
		SELECT COUNT(`i`.`id`) `id` FROM `inventory` `i` 
        WHERE `i`.`type` = 3 AND `i`.`created_date` > OLD.`created_date`
	) = 0 THEN
		UPDATE `product` `p`
        SET `p`.`required` = 0, `p`.`required_units` = 0
        WHERE (`p`.`deleted` = 0 OR `p`.`deleted` IS NULL);
    END IF;
END; //
DELIMITER ;


DROP TRIGGER IF EXISTS `ip_update_units_in_stock`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA "ENTRADA" Y/O "SALIDA" DE INVENTARIO
CREATE TRIGGER ip_update_units_in_stock
AFTER INSERT ON `inventory_product` FOR EACH ROW
BEGIN
	DECLARE inventory_type INT;
    DECLARE quantity_by_unit INT;
    
    SELECT `i`.`type` INTO inventory_type FROM `inventory` `i` WHERE `i`.`id` = NEW.`inventory_id`;
    -- SI EL REGISTRO ES DE TIPO "ENTRADA" O "SALIDA"
    IF inventory_type = 0 OR inventory_type = 1 THEN
        -- CANTIDAD POR UNIDAD
        SELECT `pu`.`quantity_by_unit` INTO quantity_by_unit
        FROM `product_unit` `pu`
        WHERE `pu`.`product_id` = NEW.`product_id` AND `pu`.`unit_id` = NEW.`unit_id`;
		-- SI EL REGISTRO ES DE TIPO "ENTRADA" SE SUMAN LAS UNIDADES NUEVAS A LAS EXISTENTES.
		IF inventory_type = 0 THEN
			UPDATE `product` `p`
            SET `p`.`units_in_stock` = (`p`.`units_in_stock` + (NEW.`units` * quantity_by_unit))
            WHERE `p`.`id` = NEW.`product_id`;
		-- SI EL REGISTRO ES DE TIPO "SALIDA" SE RESTAN LAS UNIDADES DE LAS EXISTENTES.
		ELSEIF inventory_type = 1 THEN
			UPDATE `product` `p`
            SET `p`.`units_in_stock` = (`p`.`units_in_stock` - (NEW.`units` * quantity_by_unit))
            WHERE `p`.`id` = NEW.`product_id`;
            -- SE GENERA AUTOMATICAMENTE UNA SOLICITUD DEL PRODUCTO SI LAS UNIDADES SON MENORES A CERO
		END IF;
    END IF;
END; //
DELIMITER ;


DROP TRIGGER IF EXISTS `s_update_units_in_stock`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA "CANCELACIÓN" DE UNA VENTA
CREATE TRIGGER s_update_units_in_stock
AFTER UPDATE ON `sale` FOR EACH ROW
BEGIN
	DECLARE f INT DEFAULT FALSE;
    DECLARE product_id INT;
    DECLARE units INT;
    DECLARE quantity_by_unit INT;
    DECLARE sale_details CURSOR FOR
		-- SE OBTIENEN LOS DETALLES DEL REGISTRO DE VENTA
		SELECT `sd`.`product_id`, `sd`.`quantity`, `pu`.`quantity_by_unit`
        FROM `sale_detail` `sd`
        JOIN `product_unit` `pu` ON `sd`.`product_id` = `pu`.`product_id` AND `pu`.`is_default` = 1
        WHERE `sd`.`sale_id` = NEW.`id` AND NEW.`deleted` = 1;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET f = TRUE;
    
    OPEN sale_details;
    SALE_DETAILS_LOOP: LOOP
		-- SIGUIENTE RESULTADO
		FETCH sale_details INTO product_id, units, quantity_by_unit;
        -- ¿YA NO EXISTEN RESULTADOS?
        IF f = TRUE THEN
			LEAVE SALE_DETAILS_LOOP;
		END IF;
		-- SE SUMAN LAS UNIDADES VENDIDAS A LAS EXISTENTES
		UPDATE `product` `p`
		SET `p`.`units_in_stock` = (`p`.`units_in_stock` + (units * quantity_by_unit))
		WHERE `p`.`id` = product_id;
        -- SI EXISTE UNA SOLICITUD PENDIENTE SE ELIMINA
    END LOOP SALE_DETAILS_LOOP;
    CLOSE sale_details;
    
END; //
DELIMITER ;


DROP TRIGGER IF EXISTS `sd_update_units_in_stock`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA VENTA DE UN PRODUCTO
CREATE TRIGGER sd_update_units_in_stock
AFTER INSERT ON `sale_detail` FOR EACH ROW
BEGIN
	DECLARE quantity_by_unit INT;
    -- CANTIDAD POR UNIDAD PREDETERMINADA
	SELECT `pu`.`quantity_by_unit` INTO quantity_by_unit
	FROM `product_unit` `pu`
	WHERE `pu`.`product_id` = NEW.`product_id` AND `pu`.`is_default` = 1;
	-- SE RESTAN LAS UNIDADES DE LAS EXISTENTES.
	UPDATE `product` `p`
    SET `p`.`units_in_stock` = (`p`.`units_in_stock` - (NEW.`quantity` * quantity_by_unit))
    WHERE `p`.`id` = NEW.`product_id`;
    -- SE GENERA AUTOMATICAMENTE UNA SOLICITUD DEL PRODUCTO SI LAS UNIDADES SON MENORES A CERO
END; //
DELIMITER ;


DROP TRIGGER IF EXISTS `spd_update_units_in_stock`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA VENTA DE UNA PROMOCIÓN
CREATE TRIGGER spd_update_units_in_stock
AFTER INSERT ON `sale_promotion_detail` FOR EACH ROW
BEGIN
	DECLARE f INT DEFAULT FALSE;
    DECLARE product_id INT;
    DECLARE units INT;
    DECLARE quantity_by_unit INT;
    DECLARE promotion_products CURSOR FOR
		-- SE OBTIENEN LOS DETALLES DE LA PROMOCIÓN
		SELECT `pp`.`product_id`, `pp`.`units`, `pu`.`quantity_by_unit`
        FROM `promotion_product` `pp`
        JOIN `product_unit` `pu` ON `pp`.`product_id` = `pu`.`product_id` AND `pp`.`unit_id` = `pu`.`unit_id`
        WHERE `pp`.`promotion_id` = NEW.`promotion_id`;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET f = TRUE;
    
    OPEN promotion_products;
    PROMOTION_PRODUCTS_LOOP: LOOP
		-- SIGUIENTE RESULTADO
		FETCH promotion_products INTO product_id, units, quantity_by_unit;
        -- ¿YA NO EXISTEN RESULTADOS?
        IF f = TRUE THEN
			LEAVE PROMOTION_PRODUCTS_LOOP;
		END IF;
        -- SE RESTAN LAS UNIDADES DENTRO DE LA PROMOCIÓN DE LAS EXISTENTES
		UPDATE `product` `p`
        SET `p`.`units_in_stock` = (`p`.`units_in_stock` - (units * quantity_by_unit * NEW.`quantity`))
        WHERE `p`.`id` = product_id;
        -- SE GENERA AUTOMATICAMENTE UNA SOLICITUD DEL PRODUCTO SI LAS UNIDADES SON MENORES A CERO
	END LOOP PROMOTION_PRODUCTS_LOOP;
    CLOSE promotion_products;
    
END; //
DELIMITER ;


DROP TRIGGER IF EXISTS `pd_update_units_in_stock`;
DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA COMPRA DE UN PRODUCTO
CREATE TRIGGER pd_update_units_in_stock
AFTER INSERT ON `purchase_detail` FOR EACH ROW
BEGIN
    DECLARE quantity_by_unit INT;
    -- CANTIDAD DE PIEZAS POR UNIDAD
    SELECT `pu`.`quantity_by_unit` INTO quantity_by_unit
    FROM `product_unit` `pu`
    WHERE `pu`.`product_id` = NEW.`product_id` AND `pu`.`unit_id` = NEW.`unit_id`;
	-- SE SUMAN LAS UNIDADES A LAS EXISTENTES.
	UPDATE `product` `p`
    SET `p`.`units_in_stock` = (`p`.`units_in_stock` + (NEW.`quantity` * quantity_by_unit))
    WHERE `p`.`id` = NEW.`product_id`;
END; //
DELIMITER ;

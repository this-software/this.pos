CREATE TABLE `user` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`email` VARCHAR(100) NULL,
	`password` VARCHAR(100) NOT NULL,
	`salt` VARCHAR(100) NOT NULL,
	`active` INT NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_user_id PRIMARY KEY (`id`),
	CONSTRAINT uq_user_code UNIQUE (`code`),
	CONSTRAINT uq_user_name UNIQUE (`name`),
	CONSTRAINT uq_user_email UNIQUE (`email`)
);
INSERT INTO `user` (email, `name`, `password`, salt, active, created_by, created_date)
VALUES ('milton.cavazos@gmail.com', 'mcavazos', 'faceebf57097e09aa377569adc38132a722e32e5', 'MMkaz5VWpTOYEwKOjikaFA==', 1, 1, NOW());


CREATE TABLE `profile` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
	`deleted` INT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT current_timestamp,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_profile_id PRIMARY KEY (`id`),
	CONSTRAINT uq_profile_code UNIQUE (`code`),
	CONSTRAINT uq_profile_name UNIQUE (`name`)
);


CREATE TABLE `user_profile` (
	`user_id` INT NOT NULL,
	`profile_id` INT NOT NULL,
	CONSTRAINT pk_up_uid_pid PRIMARY KEY (`user_id`, `profile_id`),
    CONSTRAINT fk_up_uid_user_id FOREIGN KEY (`user_id`) REFERENCES `user`(`id`),
    CONSTRAINT fk_up_pid_profile_id FOREIGN KEY (`profile_id`) REFERENCES `profile`(`id`)
);


CREATE TABLE `role` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
	`deleted` INT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_role_id PRIMARY KEY (`id`),
	CONSTRAINT uq_role_code UNIQUE (`code`),
	CONSTRAINT uq_role_name UNIQUE (`name`)
);


CREATE TABLE `user_role` (
	`user_id` INT NOT NULL,
	`role_id` INT NOT NULL,
	CONSTRAINT pk_user_role_uid_rid PRIMARY KEY (`user_id`, `role_id`)
);


CREATE TABLE `role_profile` (
    `role_id` INT NOT NULL,
    `profile_id` INT NOT NULL,
    CONSTRAINT pk_role_profile_rid_pid PRIMARY KEY (`role_id`, `profile_id`)
);


CREATE TABLE `module` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
	`deleted` INT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_module_id PRIMARY KEY (`id`),
	CONSTRAINT uq_module_code UNIQUE (`code`),
	CONSTRAINT uq_module_name UNIQUE (`name`)
);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-001', 'Usuarios', 'Módulo para la gestión de usuarios', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-002', 'Perfiles', 'Módulo para la gestión de perfiles', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-003', 'Inventario', 'Módulo para la gestión de inventario', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-004', 'Articulos', 'Módulo para la gestión de articulos', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-005', 'Conversiones', 'Módulo para la gestión de conversión de unidades', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-006', 'Costos', 'Módulo para la gestión de costos y utilidades', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-007', 'Proveedores', 'Módulo para la gestión de proveedores', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-008', 'Sincronización', 'Módulo para la gestión de sincronización', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-009', 'Ventas', 'Módulo para la gestión de ventas', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD-010', 'Notificaciones', 'Módulo para la gestión de notificaciones', 1);


CREATE TABLE `category` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
	`deleted` BIT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_category_id PRIMARY KEY (`id`),
	CONSTRAINT uq_category_code UNIQUE (`code`),
	CONSTRAINT uq_category_name UNIQUE (`name`)
);


CREATE TABLE `provider` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
    `country` VARCHAR(50) NULL,
    `state` VARCHAR(50) NULL,
    `city` VARCHAR(50) NULL,
    `address` VARCHAR(100) NULL,
    `postal_code` VARCHAR(8) NULL,
    `phone` VARCHAR(12) NULL,
	`deleted` BIT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_provider_id PRIMARY KEY (`id`),
	CONSTRAINT uq_provider_code UNIQUE (`code`),
	CONSTRAINT uq_provider_name UNIQUE (`name`)
);


CREATE TABLE `product` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
    `price` DOUBLE NOT NULL,
    `cost` DOUBLE NOT NULL,
    `discount` DOUBLE NULL DEFAULT 0,
    `quantity_by_unit` INT NOT NULL,
    `units_in_stock` INT NOT NULL,
    `min_stock_level` INT NULL DEFAULT 0,
    `image_path` VARCHAR(250) NULL,
    `required` BIT NULL DEFAULT 0,
	`deleted` BIT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    `discount_expiration_date` DATETIME NULL,
    `category_id` INT NOT NULL,
    `provider_id` INT NOT NULL,
	CONSTRAINT pk_product_id PRIMARY KEY (`id`),
	CONSTRAINT uq_product_code UNIQUE (`code`),
	CONSTRAINT uq_product_name UNIQUE (`name`),
    CONSTRAINT fk_product_cid_category_id FOREIGN KEY (`category_id`) REFERENCES `category`(`id`),
    CONSTRAINT fk_product_pid_provider_id FOREIGN KEY (`provider_id`) REFERENCES `provider`(`id`)
);


CREATE TABLE `inventory` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(50) NOT NULL,
    `type` TINYINT NOT NULL, -- TIPO DE MOVIMIENTO (0=ENTRADA, 1=SALIDA, 2=CANCELACIÓN)
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_inventory_id PRIMARY KEY (`id`)
	CONSTRAINT uq_inventory_code UNIQUE (`code`)
);


CREATE TABLE `inventory_product` (
    `inventory_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `units` INT NOT NULL,
	CONSTRAINT pk_ip_iid_pid PRIMARY KEY (`inventory_id`, `product_id`),
    CONSTRAINT fk_ip_iid_inventory_id FOREIGN KEY (`inventory_id`) REFERENCES `inventory`(`id`),
    CONSTRAINT fk_ip_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);


CREATE TABLE `promotion` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
    `price` DOUBLE NOT NULL,
    `cost` DOUBLE NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    `begin_date` DATETIME NOT NULL,
    `expiration_date` DATETIME NOT NULL,
	CONSTRAINT pk_promotion_id PRIMARY KEY (`id`),
	CONSTRAINT uq_promotion_code UNIQUE (`code`),
	CONSTRAINT uq_promotion_name UNIQUE (`name`)
);


CREATE TABLE `promotion_product` (
	`promotion_id` INT NOT NULL,
    `product_id` INT NOT NULL,
	CONSTRAINT pk_pp_pid_pid PRIMARY KEY (`promotion_id`, `product_id`),
    CONSTRAINT fk_pp_pid_promotion_id FOREIGN KEY (`promotion_id`) REFERENCES `promotion`(`id`),
    CONSTRAINT fk_pp_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);


DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA "ENTRADA" Y/O "SALIDA" DE INVENTARIO
CREATE TRIGGER ip_update_units_in_stock
AFTER INSERT ON `inventory_product` FOR EACH ROW
BEGIN
	DECLARE inventory_type INT;
	DECLARE units_in_stock INT;
    
    SELECT `i`.`type` INTO inventory_type FROM `inventory` `i` WHERE `i`.`id` = NEW.`inventory_id`;
	SELECT `p`.`units_in_stock` INTO units_in_stock FROM `product` `p` WHERE `p`.`id` = NEW.`product_id`;
    
    -- SI EL REGISTRO ES DE TIPO "ENTRADA" SE SUMAN LAS UNIDADES NUEVAS A LAS EXISTENTES.
    IF inventory_type = 0 THEN
        UPDATE `product` `p` SET `p`.`units_in_stock` = (units_in_stock + NEW.`units`) WHERE `p`.`id` = NEW.`product_id`;
	-- SI EL REGISTRO ES DE TIPO "SALIDA" SE RESTAN LAS UNIDADES DE LAS EXISTENTES.
	ELSEIF inventory_type != 0 THEN
        UPDATE `product` `p` SET `p`.`units_in_stock` = (units_in_stock - NEW.`units`) WHERE `p`.`id` = NEW.`product_id`;
    END IF;
END; //
DELIMITER ;


DELIMITER //
-- TRIGGER QUE SE ENCARGA DE ACTUALIZAR LAS UNIDADES EXISTENTES DE UN PRODUCTO
-- CUANDO SE GENERA UNA "CANCELACION" DE INVENTARIO
CREATE TRIGGER i_update_units_in_stock
AFTER UPDATE ON `inventory` FOR EACH ROW
BEGIN
	DECLARE f INT DEFAULT FALSE;
    DECLARE product_id INT;
    DECLARE units INT;
    DECLARE units_in_stock INT;
    DECLARE inventory_products CURSOR FOR
		SELECT `ip`.`product_id`, `ip`.`units`, `p`.`units_in_stock` FROM `inventory_product` `ip` 
		JOIN `product` `p` ON `ip`.`product_id` = `p`.`id` WHERE `ip`.`inventory_id` = NEW.`id`;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET f = TRUE;
    
    OPEN inventory_products;
    
    REPEAT
		FETCH inventory_products INTO product_id, units, units_in_stock;
        -- SI EL REGISTRO ES DE TIPO "CANCELACION" SE RESTAN LAS UNIDADES PREVIAMENTE INGRESADAS DE LAS EXISTENTES.
		IF NEW.`type` = 2 THEN
			UPDATE `product` `p` SET `p`.`units_in_stock` = (units_in_stock - units) WHERE `p`.`id` = product_id;
		END IF;
	UNTIL f END REPEAT;
    
    CLOSE inventory_products;
END; //
DELIMITER ;


CREATE TABLE `sales` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `subtotal` DOUBLE NOT NULL,
	`total_amount` DOUBLE NOT NULL,
    `tax` DOUBLE NOT NULL,
	`cost` DOUBLE NOT NULL,
	`observations` VARCHAR(250) NULL,
    `status` INT NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_sales_id PRIMARY KEY (`id`)
);


CREATE TABLE `sales_detail` (
	`sales_id` INT NOT NULL AUTO_INCREMENT,
    `product_id` INT NOT NULL,
	`price` DOUBLE NOT NULL,
    `discount` DOUBLE NOT NULL,
    `amount` DOUBLE NOT NULL,
	`cost` DOUBLE NOT NULL,
    `net_cost` DOUBLE NOT NULL,
	`quantity` INT NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT fk_sd_sid_sales_id FOREIGN KEY (`sales_id`) REFERENCES `sales`(`id`),
    CONSTRAINT fk_sd_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`)
);


CREATE TABLE `sales_promotion_detail` (
	`sales_id` INT NOT NULL AUTO_INCREMENT,
    `promotion_id` INT NOT NULL,
	`price` DOUBLE NOT NULL,
    `amount` DOUBLE NOT NULL,
	`cost` DOUBLE NOT NULL,
    `net_cost` DOUBLE NOT NULL,
	`quantity` INT NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT fk_spd_sid_sales_id FOREIGN KEY (`sales_id`) REFERENCES `sales`(`id`),
    CONSTRAINT fk_spd_pid_promotion_id FOREIGN KEY (`promotion_id`) REFERENCES `promotion`(`id`)
);

-- CORTES DE CAJA
CREATE TABLE `cash_out` (
	`id` INT NOT NULL AUTO_INCREMENT
);


CREATE TABLE `log` (
	`description` VARCHAR(512) NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_log_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
);

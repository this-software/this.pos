CREATE TABLE `user` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`email` VARCHAR(100) NULL,
	`password` VARCHAR(100) NOT NULL,
	`salt` VARCHAR(100) NOT NULL,
	`status` INT NOT NULL DEFAULT 0,
    `deleted` BIT NOT NULL DEFAULT 0,
    `receive_notifications` BIT NOT NULL DEFAULT 0,
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
	`deleted` BIT NULL DEFAULT 0,
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
	`deleted` BIT NULL DEFAULT 0,
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
	CONSTRAINT pk_ur_uid_rid PRIMARY KEY (`user_id`, `role_id`),
    CONSTRAINT fk_ur_uid_user_id FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT fk_ur_rid_role_id FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);


CREATE TABLE `role_profile` (
    `role_id` INT NOT NULL,
    `profile_id` INT NOT NULL,
    CONSTRAINT pk_role_profile_rid_pid PRIMARY KEY (`role_id`, `profile_id`),
    CONSTRAINT fk_rp_rid_role_id FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
    CONSTRAINT fk_rp_pid_profile_id FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`)
);


CREATE TABLE `module` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
	`deleted` BIT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_module_id PRIMARY KEY (`id`),
	CONSTRAINT uq_module_code UNIQUE (`code`),
	CONSTRAINT uq_module_name UNIQUE (`name`)
);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0001', 'Usuarios', 'Módulo para la gestión de usuarios', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0002', 'Perfiles', 'Módulo para la gestión de perfiles', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0003', 'Inventario', 'Módulo para la gestión de inventario', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0004', 'Compras', 'Módulo para la gestión de compras', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0005', 'Ventas', 'Módulo para la gestión de ventas', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0006', 'Categorías', 'Módulo para la gestión de categorías', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0007', 'Proveedores', 'Módulo para la gestión de proveedores', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0008', 'Articulos', 'Módulo para la gestión de articulos', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0009', 'Costos', 'Opción de gestión sobre costos y utilidades de un producto', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0010', 'Unidades', 'Módulo para la gestión de conversión de unidades', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0011', 'Notificaciones', 'Módulo para la gestión de notificaciones', 1);
INSERT INTO `module` (`code`, `name`, description, created_by) VALUES ('MOD0012', 'Sincronización', 'Módulo para la gestión de sincronización', 1);


CREATE TABLE `profile_module` (
  `profile_id` INT(11) NOT NULL,
  `module_id` INT(11) NOT NULL,
  `view` INT(11) DEFAULT NULL,
  `create` INT(11) DEFAULT NULL,
  `edit` INT(11) DEFAULT NULL,
  `delete` INT(11) DEFAULT NULL,
  CONSTRAINT pk_pm_pid_mid PRIMARY KEY (`profile_id`,`module_id`),
  CONSTRAINT fk_pm_pid_profile_id FOREIGN KEY (`profile_id`) REFERENCES `profile` (`id`),
  CONSTRAINT fk_pm_mid_module_id FOREIGN KEY (`module_id`) REFERENCES `module` (`id`)
);


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


CREATE TABLE `store` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL -- UNI001
);
-- http:// ?code=UNI001


CREATE TABLE `product` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
    `cost` DOUBLE(8,2) NOT NULL,
    `price` DOUBLE(8,2) NOT NULL,
    `special_price` DOUBLE(8,2) NOT NULL DEFAULT 0,
    `discount` DOUBLE(8,2) NOT NULL DEFAULT 0,
    `quantity_by_unit` INT NOT NULL, -- CANTIDAD POR UNIDAD PREDETERMINADA EN LA QUE SE VENDE EL PRODUCTO
    `units_in_stock` INT NOT NULL, -- CANTIDAD DE PIEZAS
    `min_stock_level` INT NOT NULL DEFAULT 0, -- MINIMO DE PIEZAS PERMITIDAS
    `image_path` VARCHAR(250) NULL,
    `required` BIT DEFAULT 0, -- ¿ES SOLICITADO? (DETERMINADO POR EL INVENTARIO GENERADO POR "SISTEMA")
    `required_units` INT DEFAULT 0, -- CANTIDAD SOLICITADA (DETERMINADO POR EL INVENTARIO GENERADO POR "SISTEMA")
	`deleted` BIT DEFAULT 0,
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


-- SOLICITUD DE PRODUCTO
CREATE TABLE `product_request` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `product_id` INT NOT NULL, -- PRODUCTO
    `unit_id` INT NOT NULL, -- UNIDAD
    `required_units` INT DEFAULT 0, -- CANTIDAD SOLICITADA
    `created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL
);


CREATE TABLE `inventory` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(50) NOT NULL,
    `type` TINYINT NOT NULL, -- TIPO DE MOVIMIENTO (0=ENTRADA, 1=SALIDA, 2=CANCELACIÓN, 3=SISTEMA)
    `deleted` BIT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_inventory_id PRIMARY KEY (`id`)
	CONSTRAINT uq_inventory_code UNIQUE (`code`)
);


CREATE TABLE `inventory_product` (
    `inventory_id` INT NOT NULL,
    `product_id` INT NOT NULL, -- PRODUCTO
    `unit_id` INT NOT NULL, -- UNIDAD
    `units` INT NOT NULL, -- UNIDADES EN ALMACÉN, UNIDADES DE ENTRADA, UNIDADES DE SALIDA (SEGÚN SEA EL CASO)
    `real_units` INT NULL DEFAULT 0, -- UNIDADES REALES EN ALMACÉN
	CONSTRAINT pk_ip_iid_pid PRIMARY KEY (`inventory_id`, `product_id`),
    CONSTRAINT fk_ip_iid_inventory_id FOREIGN KEY (`inventory_id`) REFERENCES `inventory`(`id`),
    CONSTRAINT fk_ip_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT fk_ip_uid_unit_id FOREIGN KEY (`unit_id`) REFERENCES `unit`(`id`)
);


CREATE TABLE `promotion` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
    `price` DOUBLE(8,2) NOT NULL,
    `cost` DOUBLE(8,2) NOT NULL,
    `deleted` BIT NULL DEFAULT 0,
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
    `unit_id` INT NOT NULL,
    `units` INT NOT NULL,
	CONSTRAINT pk_pp_pid_pid PRIMARY KEY (`promotion_id`, `product_id`),
    CONSTRAINT fk_pp_pid_promotion_id FOREIGN KEY (`promotion_id`) REFERENCES `promotion`(`id`),
    CONSTRAINT fk_pp_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT fk_pp_uid_unit_id FOREIGN KEY (`unit_id`) REFERENCES `unit`(`id`)
);


CREATE TABLE `sale` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `subtotal` DOUBLE(8,2) NOT NULL,
    `tax` DOUBLE(8,2) NOT NULL,
	`total_amount` DOUBLE(8,2) NOT NULL,
	`cost` DOUBLE(8,2) NOT NULL,
    `paid_amount` DOUBLE(8,2) NOT NULL,
    `change` DOUBLE(8,2) NOT NULL,
	`observations` VARCHAR(250) NULL,
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_sale_id PRIMARY KEY (`id`),
    CONSTRAINT fk_sale_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`),
    CONSTRAINT fk_sale_uby_user_id FOREIGN KEY (`updated_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `sale_detail` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`sale_id` INT NOT NULL,
    `product_id` INT NOT NULL,
    `unit_id` INT NOT NULL, -- TODO: ES NECESARIO SABER EN QUE UNIDAD SE VENDIO AUN QUE SIEMPRE SEA EN PIEZAS
	`price` DOUBLE(8,2) NOT NULL,
    `discount` DOUBLE(8,2) NOT NULL,
    `total_discount` DOUBLE(8,2) NOT NULL,
    `amount` DOUBLE(8,2) NOT NULL,
	`cost` DOUBLE(8,2) NOT NULL,
    `total_cost` DOUBLE(8,2) NOT NULL,
	`quantity` INT NOT NULL, -- TODO: CAMBIAR NOMBRE A "UNITS"
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT pk_sd_id PRIMARY KEY (`id`),
    CONSTRAINT fk_sd_sid_sale_id FOREIGN KEY (`sale_id`) REFERENCES `sale`(`id`),
    CONSTRAINT fk_sd_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT fk_sd_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `sale_promotion_detail` (
	`id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
	`sale_id` INT NOT NULL,
    `promotion_id` INT NOT NULL,
	`price` DOUBLE(8,2) NOT NULL,
    `amount` DOUBLE(8,2) NOT NULL,
	`cost` DOUBLE(8,2) NOT NULL,
    `total_cost` DOUBLE(8,2) NOT NULL,
	`quantity` INT NOT NULL,
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT pk_spd_id PRIMARY KEY (`id`),
    CONSTRAINT fk_spd_sid_sale_id FOREIGN KEY (`sale_id`) REFERENCES `sale`(`id`),
    CONSTRAINT fk_spd_pid_promotion_id FOREIGN KEY (`promotion_id`) REFERENCES `promotion`(`id`),
    CONSTRAINT fk_spd_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
);


-- CORTES DE CAJA
CREATE TABLE `cash_out` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `sales_total_amount` DOUBLE(8,2) NOT NULL, -- MONTO TOTAL DE VENTA
    `cash_withdrawal` DOUBLE(8,2) NOT NULL, -- MONTO RETIRADO DE CAJA
    `total_cash` DOUBLE(8,2) NOT NULL, -- MONTO TOTAL EN CAJA
    `total_gap` DOUBLE(8,2) NOT NULL, -- DIFERENCIA TOTAL
    `sale_id` INT NOT NULL, -- ULTIMA VENTA ANTES DEL CORTE
    `purchase_id` INT NOT NULL, -- ULTIMA COMPRA ANTES DEL CORTE
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT pk_cash_out_id PRIMARY KEY (`id`),
    CONSTRAINT fk_cash_out_sid_sale_id FOREIGN KEY (`sale_id`) REFERENCES `sale`(`id`),
    CONSTRAINT fk_cash_out_pid_purchase_id FOREIGN KEY (`purchase_id`) REFERENCES `purchase`(`id`),
    CONSTRAINT fk_cash_out_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `log` (
	`description` VARCHAR(512) NOT NULL,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_log_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `unit` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`description` VARCHAR(512) NOT NULL,
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_unit_id PRIMARY KEY (`id`),
	CONSTRAINT uq_unit_code UNIQUE (`code`),
    CONSTRAINT uq_unit_name UNIQUE (`name`),
    CONSTRAINT fk_unit_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`),
    CONSTRAINT fk_unit_uby_user_id FOREIGN KEY (`updated_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `product_unit` (
	`product_id` INT NOT NULL,
    `unit_id` INT NOT NULL,
    `quantity_by_unit` INT NOT NULL,
    `cost_by_unit` DOUBLE(8,2) NOT NULL,
    `price_by_unit` DOUBLE(8,2) NOT NULL,
    `special_price_by_unit` DOUBLE(8,2) NOT NULL,
    `is_default` BIT NULL DEFAULT 0,
    CONSTRAINT pk_pu_pid_uid PRIMARY KEY (`product_id`, `unit_id`),
    CONSTRAINT fk_pu_pid_user_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT fk_pu_uid_user_id FOREIGN KEY (`unit_id`) REFERENCES `unit`(`id`)
);


CREATE TABLE `purchase` (
	`id` INT NOT NULL AUTO_INCREMENT,
    `subtotal` DOUBLE(8,2) NOT NULL,
    `tax` DOUBLE(8,2) NOT NULL,
	`cost` DOUBLE(8,2) NOT NULL,
    `paid_amount` DOUBLE(8,2) NOT NULL,
	`observations` VARCHAR(250) NULL,
    `status` INT NOT NULL,
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
	CONSTRAINT pk_purchase_id PRIMARY KEY (`id`),
    CONSTRAINT fk_purchase_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`),
    CONSTRAINT fk_purchase_uby_user_id FOREIGN KEY (`updated_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `purchase_detail` (
	`purchase_id` INT NOT NULL,
    `product_id` INT NOT NULL, -- PRODUCTO COMPRADO
    `unit_id` INT NOT NULL, -- UNIDAD COMPRADA
	`cost` DOUBLE(8,2) NOT NULL, -- COSTO DE UNIDAD
	`quantity` INT NOT NULL, -- CANTIDAD DE UNIDADES
    `total_cost` DOUBLE(8,2) NOT NULL, -- COSTO TOTAL (COSTO DE UNIDAD x CANTIDAD)
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT pk_pd PRIMARY KEY (`purchase_id`,`product_id`,`unit_id`),
    CONSTRAINT fk_pd_pid_purchase_id FOREIGN KEY (`purchase_id`) REFERENCES `purchase`(`id`),
    CONSTRAINT fk_pd_pid_product_id FOREIGN KEY (`product_id`) REFERENCES `product`(`id`),
    CONSTRAINT fk_pd_uid_unit_id FOREIGN KEY (`unit_id`) REFERENCES `unit`(`id`),
    CONSTRAINT fk_pd_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`),
    CONSTRAINT fk_pd_uby_user_id FOREIGN KEY (`updated_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `settings` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`store_code` VARCHAR(50) NOT NULL, -- CÓDIGO UNICO DEL NEGOCIO
    `store_name` VARCHAR(250) NOT NULL, -- NOMBRE DEL NEGOCIO
    `store_description` VARCHAR(250) NULL, -- DESCRIPCIÓN DEL NEGOCIO
	`tax` DOUBLE(8,2) NOT NULL, -- PORCENTAJE DE IMPUESTO
    `special_sale_time` TIME NOT NULL, -- HORA (FUERA DE HORARIO)
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT pk_settings PRIMARY KEY (`id`),
    CONSTRAINT fk_settings_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`),
    CONSTRAINT fk_settings_uby_user_id FOREIGN KEY (`updated_by`) REFERENCES `user`(`id`)
);


CREATE TABLE `notification` (
	`id` INT NOT NULL AUTO_INCREMENT,
	`code` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100) NULL,
	`status` INT NOT NULL DEFAULT 0,
    `deleted` BIT NOT NULL DEFAULT 0,
	`created_by` INT NOT NULL,
	`created_date` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`updated_by` INT NULL,
	`updated_date` DATETIME NULL,
    CONSTRAINT pk_notification PRIMARY KEY (`id`),
	CONSTRAINT uq_notification_code UNIQUE (`code`),
    CONSTRAINT fk_notification_cby_user_id FOREIGN KEY (`created_by`) REFERENCES `user`(`id`),
    CONSTRAINT fk_notification_uby_user_id FOREIGN KEY (`updated_by`) REFERENCES `user`(`id`)
);
INSERT INTO `notification` (`code`, description, created_by) VALUES ('NOTINVOUT', 'Notificación al generar una salida de inventario', 1);
INSERT INTO `notification` (`code`, description, created_by) VALUES ('NOTINVCAN', 'Notificación al generar una entrada de inventario', 1);
INSERT INTO `notification` (`code`, description, created_by) VALUES ('NOTPURCAN', 'Notificación al generar una cancelación de compra', 1);
INSERT INTO `notification` (`code`, description, created_by) VALUES ('NOTSALCAN', 'Notificación al generar una cancelación de venta', 1);
INSERT INTO `notification` (`code`, description, created_by) VALUES ('NOTPROREQ', 'Notificación al generar una solicitud de producto', 1);
INSERT INTO `notification` (`code`, description, created_by) VALUES ('NOTMINLEV', 'Notificación generada después de alcanzar el nivel minimo de unidades', 1);


DROP PROCEDURE IF EXISTS `sp_create_inventory_entry`;
DELIMITER $
CREATE PROCEDURE `sp_create_inventory_entry`()
BEGIN
	DECLARE icode VARCHAR(8);
    DECLARE inumber INT;
    
    SELECT MAX(`code`) INTO icode FROM `inventory` WHERE `code` LIKE 'INV%';
    IF icode IS NULL THEN
		SET inumber = 1;
	ELSE
		SET inumber = CONVERT(SUBSTRING(icode, 4), UNSIGNED INTEGER);
	END IF;
    SET icode = CONCAT('INV', LPAD(inumber, 4, '0'));
    
	INSERT INTO `inventory` (`code`,`type`,`created_by`) VALUES (icode, 3, 1);
    
    INSERT INTO `inventory_product` (`inventory_id`,`product_id`,`units`)
    SELECT `p`.`id`, `p`.`units_in_stock`
    FROM `product` `p`;
END $
DELIMITER ;


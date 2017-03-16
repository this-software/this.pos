/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.PromotionProduct;
import dis.software.pos.entities.PromotionProductPk;
import dis.software.pos.interfaces.IPromotionProduct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los datos de relación entre Promociones y Productos en la base de datos.
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class PromotionProductController extends GenericHibernateController<PromotionProduct, PromotionProductPk>
    implements IPromotionProduct
{
    
    private static final Logger logger = LogManager.getLogger(PromotionProductController.class.getSimpleName());
    
}

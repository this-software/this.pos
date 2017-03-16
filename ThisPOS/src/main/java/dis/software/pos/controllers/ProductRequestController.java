/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductRequest;
import dis.software.pos.entities.ProductRequestPk;
import dis.software.pos.interfaces.IProductRequest;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a la relación Producto - Solicitudes
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProductRequestController extends GenericHibernateController<ProductRequest, ProductRequestPk>
    implements IProductRequest
{
    
    private static final Logger logger = LogManager.getLogger(ProductRequestController.class.getSimpleName());

    /**
     * Método para validar si el producto especificado ha sido solicitado previamente
     * @param product Producto solicitado
     * @return Boolean.TRUE si el producto ha sido solicitado previamente
     */
    @Override
    @Transactional(readOnly = true)
    public Boolean getIsRequired(Product product)
    {
        List<ProductRequest> list = super.getCurrentSession().createQuery(""
            + "select pr "
            + "from dis.software.pos.entities.ProductRequest pr "
            + "where pr.productRequestPk.product.id = :product_id")
            .setParameter("product_id", product.getId()).list();
        logger.info("getIsRequired of " + clazz + " completed.");
        return list.size() > 0;
    }
    
}

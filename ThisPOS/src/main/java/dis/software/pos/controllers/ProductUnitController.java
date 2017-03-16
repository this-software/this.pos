/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductUnit;
import dis.software.pos.entities.ProductUnitPk;
import dis.software.pos.entities.Unit;
import dis.software.pos.interfaces.IProductUnit;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los datos de relación entre Producto y Unidad en la base de datos.
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProductUnitController extends GenericHibernateController<ProductUnit, ProductUnitPk>
    implements IProductUnit
{
    
    private static final Logger logger = LogManager.getLogger(ProductUnitController.class.getSimpleName());

    /**
     * Método para obtener la relación unidad - producto filtrada por unidad y producto
     * @param product Producto a filtrar
     * @param unit Unidad a filtrar
     * @return Relación Producto - Unidad
     */
    @Override
    @Transactional(readOnly = true)
    public ProductUnit findBy(Product product, Unit unit)
    {
        ProductUnit entity = (ProductUnit) super.getCurrentSession().createQuery(""
            + "select pu "
            + "from dis.software.pos.entities.ProductUnit pu "
            + "where pu.productUnitPk.unit.id = :uid and pu.productUnitPk.product.id = :pid")
            .setParameter("uid", unit.getId())
            .setParameter("pid", product.getId()).uniqueResult();
        logger.info("findBy of " + clazz + " completed.");
        return entity;
    }
    
    /**
     * Método para obtener la relación Producto - Unidad filtrada por producto
     * @param product Producto a filtrar
     * @return Relación Producto - Unidad
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProductUnit> findByProduct(Product product)
    {
        List<ProductUnit> list = super.getCurrentSession().createQuery(""
            + "select pu "
            + "from dis.software.pos.entities.ProductUnit pu "
            + "where pu.productUnitPk.product.id = :id")
            .setParameter("id", product.getId()).list();
        logger.info("findByProduct of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener la cantidad de un producto filtrada por unidad y producto
     * @param product Producto a filtrar
     * @param unit Unidad a filtrar
     * @return Cantidad por unidad
     */
    @Override
    @Transactional(readOnly = true)
    public Integer getQuantityBy(Product product, Unit unit)
    {
        Integer quantityByUnit = (Integer) super.getCurrentSession().createQuery(""
            + "select pu.quantityByUnit "
            + "from dis.software.pos.entities.ProductUnit pu "
            + "where pu.productUnitPk.unit.id = :uid and pu.productUnitPk.product.id = :pid")
            .setParameter("uid", unit.getId())
            .setParameter("pid", product.getId()).uniqueResult();
        logger.info("getQuantityByUnit of " + clazz + " completed.");
        return quantityByUnit;
    }
    
}

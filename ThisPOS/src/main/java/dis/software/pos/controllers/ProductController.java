/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Category;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.IProduct;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los productos
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProductController extends GenericHibernateController<Product, Long> implements IProduct
{
    
    private static final Logger logger = LogManager.getLogger(ProductController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de perfil
     * @return Código de perfil
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(p.code) "
            + "from dis.software.pos.entities.Product p "
            + "where p.code like 'ITE%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "ITE" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener todos los productos activos
     * @return Lista de productos
     */
    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<Product> findAll()
    {
        super.clear();
        List<Product> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.deleted = 0 or p.deleted is null").list();
        logger.info("findAll of " + clazz + " completed.");
        return list;
    }

    /**
     * Método para obtener productos filtrados por categoría
     * @param category Categoría para filtrar
     * @return Lista de productos
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findByCategory(Category category)
    {
        List<Product> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.category.id = :category_id "
            + "and (p.deleted = 0 or p.deleted is null)")
            .setParameter("category_id", category.getId()).list();
        logger.info("findByCategory of " + clazz + " completed.");
        return list;
    }

    /**
     * Método para obtener productos filtrados por proveedor
     * @param provider Proveedor para filtrar
     * @return Lista de productos
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findByProvider(Provider provider)
    {
        List<Product> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.provider.id = :provider_id "
            + "and (p.deleted = 0 or p.deleted is null)")
            .setParameter("provider_id", provider.getId()).list();
        logger.info("findByProvider of " + clazz + " completed.");
        return list;
    }

    /**
     * Método para obtener productos filtrados por categoría y proveedor
     * @param category Categoría para filtrar
     * @param provider Proveedor para filtrar
     * @return Lista de productos
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> findBy(Category category, Provider provider)
    {
        List<Product> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.category.id = :category_id "
            + "and p.provider.id = :provider_id "
            + "and (p.deleted = 0 or p.deleted is null)")
            .setParameter("category_id", category.getId())
            .setParameter("provider_id", provider.getId()).list();
        logger.info("findBy of " + clazz + " completed.");
        return list;
    }

    /**
     * Método para obtener productos filtrados por código
     * @param code Código para filtrar
     * @return Producto filtrado
     */
    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public Product findByCode(String code)
    {
        super.clear();
        Product product = (Product) super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.code = :code "
            + "and (p.deleted = 0 or p.deleted is null)")
            .setParameter("code", code).uniqueResult();
        logger.info("findByCode of " + clazz + " completed.");
        return product;
    }
    
    /**
     * Método para obtener todos los productos que han alcanzado el nivel mínimo de unidades en almacén
     * @return Lista de productos
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getMinStockLevel()
    {
        List<Product> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.unitsInStock <= p.minStockLevel "
            + "and p.minStockLevelNotified != 1 "
            + "and (p.deleted = 0 or p.deleted is null)").list();
        logger.info("getMinStockLevel of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para marcar lo productos que ya fueron notificados por tener un nivel mínimo de unidades
     * en almacén
     * @param list Lista de productos
     */
    @Override
    public void setMinStockLevelNotified(List<Product> list)
    {
        super.getCurrentSession().createQuery(""
            + "update "
            + "dis.software.pos.entities.Product p "
            + "set p.minStockLevelNotified = 1 "
            + "where p.id in (" + list.stream()
                .map(l -> String.valueOf(l.getId())).collect(Collectors.joining(","))
            + ")").executeUpdate();
        logger.info("setMinStockLevelNotified of " + clazz + " completed.");
    }
    
    /**
     * Método para obtener todos los productos marcados como eliminados
     * @return Lista de productos eliminados
     */
    @Override
    @Transactional(readOnly = true)
    public List<Product> getDeleted()
    {
        List<Product> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Product p "
            + "where p.deleted = 1").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
}

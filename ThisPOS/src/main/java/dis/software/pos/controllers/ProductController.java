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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los productos
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProductController extends GenericHibernateController<Product, Long> implements IProduct
{
    
    private static Logger logger = LogManager.getLogger(ProductController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de perfil
     * @return Código de perfil
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery("select max(p.code) "
            + "from dis.software.pos.entities.Product p where p.code like 'ITE-%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(4));
        return "ITE-" + String.format("%03d", code == null ? number : number+1);
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
        List<Product> products = super.getCurrentSession().createQuery("select p "
            + "from dis.software.pos.entities.Product p where p.category.id = :category_id")
            .setParameter("category_id", category.getId()).list();
        logger.info("findByCategory of " + clazz + " completed.");
        
        return products;
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
        List<Product> products = super.getCurrentSession().createQuery("select p "
            + "from dis.software.pos.entities.Product p where p.provider.id = :provider_id")
            .setParameter("provider_id", provider.getId()).list();
        logger.info("findByProvider of " + clazz + " completed.");
        
        return products;
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
        List<Product> products = super.getCurrentSession().createQuery(""
            + "select p from dis.software.pos.entities.Product p "
            + "where p.category.id = :category_id and p.provider.id = :provider_id")
            .setParameter("category_id", category.getId())
            .setParameter("provider_id", provider.getId()).list();
        logger.info("findBy of " + clazz + " completed.");
        
        return products;
    }
    
}

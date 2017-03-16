/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Category;
import dis.software.pos.interfaces.ICategory;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a las categorías
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class CategoryController extends GenericHibernateController<Category, Long> implements ICategory
{
    
    private static final Logger logger = LogManager.getLogger(CategoryController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de una categoría
     * @return Código de categoría
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(c.code) "
            + "from dis.software.pos.entities.Category c "
            + "where c.code like 'CAT%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "CAT" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener todos los productos marcados como eliminados
     * @return Lista de productos eliminados
     */
    @Override
    @Transactional(readOnly = true)
    public List<Category> getDeleted()
    {
        List<Category> list = super.getCurrentSession().createQuery(""
            + "select c "
            + "from dis.software.pos.entities.Category c "
            + "where c.deleted = 1 or c.deleted is null").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
}

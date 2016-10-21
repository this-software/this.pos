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
    
    private static Logger logger = LogManager.getLogger(CategoryController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de una categoría
     * @return Código de categoría
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery("select max(c.code) "
            + "from dis.software.pos.entities.Category c where c.code like 'CAT-%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(4));
        return "CAT-" + String.format("%03d", code == null ? number : number+1);
    }
    
}

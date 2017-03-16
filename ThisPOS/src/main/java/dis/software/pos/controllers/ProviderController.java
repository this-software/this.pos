/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.IProvider;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los proveedores
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProviderController extends GenericHibernateController<Provider, Long> implements IProvider
{
    
    private static final Logger logger = LogManager.getLogger(ProviderController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de proveedor
     * @return Código de proveedor
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(p.code) "
            + "from dis.software.pos.entities.Provider p "
            + "where p.code like 'SUP%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "SUP" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener todos los proveedores marcados como eliminados
     * @return Lista de proveedores eliminados
     */
    @Override
    @Transactional(readOnly = true)
    public List<Provider> getDeleted()
    {
        List<Provider> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Provider p "
            + "where p.deleted = 1 or p.deleted is null").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
}

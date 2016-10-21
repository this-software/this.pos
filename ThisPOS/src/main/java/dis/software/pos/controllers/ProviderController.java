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
    
    private static Logger logger = LogManager.getLogger(ProviderController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de proveedor
     * @return Código de proveedor
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery("select max(p.code) "
            + "from dis.software.pos.entities.Provider p where p.code like 'SUP-%'");
        Integer number = Integer.valueOf(code.substring(4));
        return "SUP-" + String.format("%03d", number+1);
    }
}

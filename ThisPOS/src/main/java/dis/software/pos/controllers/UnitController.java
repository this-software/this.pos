/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Unit;
import dis.software.pos.interfaces.IUnit;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los datos de Unidad en la base de datos.
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class UnitController extends GenericHibernateController<Unit, Long> implements IUnit
{
    
    private static final Logger logger = LogManager.getLogger(UnitController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de Unidad
     * @return Código de unidad
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(u.code) "
            + "from dis.software.pos.entities.Unit u "
            + "where u.code like 'UNT%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "UNT" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener todas las unidades marcadas como eliminadas
     * @return Lista de unidades eliminadas
     */
    @Override
    @Transactional(readOnly = true)
    public List<Unit> getDeleted()
    {
        List<Unit> list = super.getCurrentSession().createQuery(""
            + "select u "
            + "from dis.software.pos.entities.Unit u "
            + "where u.deleted = 1").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
}

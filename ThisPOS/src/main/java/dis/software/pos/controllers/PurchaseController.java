/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Purchase;
import dis.software.pos.interfaces.IPurchase;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a las compras
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class PurchaseController extends GenericHibernateController<Purchase, Long> implements IPurchase
{
    
    private static final Logger logger = LogManager.getLogger(PurchaseController.class.getSimpleName());
    
    /**
     * Método para obtener registros de compra filtrados por un rango de fechas
     * @param initialDate Fecha inicial 
     * @param finalDate Fecha final
     * @return Lista de registros de compra
     */
    @Override
    @Transactional(readOnly = true)
    public List<Purchase> findByDateRange(Calendar initialDate, Calendar finalDate)
    {
        List<Purchase> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Purchase p "
            + "where p.createdDate >= :initialDate "
            + "and p.createdDate <= :finalDate "
            + "and p.deleted = 0 or p.deleted is null")
            .setParameter("initialDate", initialDate)
            .setParameter("finalDate", finalDate).list();
        logger.info("findByDateRange of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener todas las compras marcadas como eliminadas
     * @return Lista de compras eliminadas
     */
    @Override
    @Transactional(readOnly = true)
    public List<Purchase> getDeleted()
    {
        List<Purchase> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Purchase p "
            + "where p.deleted = 1").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
}

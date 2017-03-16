/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Sale;
import dis.software.pos.interfaces.ISale;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a las ventas
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class SaleController extends GenericHibernateController<Sale, Long> implements ISale
{
    
    private static final Logger logger = LogManager.getLogger(SaleController.class.getSimpleName());

    /**
     * Método para obtener todos las ventas marcadas como canceladas
     * @return Lista de ventas canceladas
     */
    @Override
    @Transactional(readOnly = true)
    public List<Sale> getDeleted()
    {
        List<Sale> list = super.getCurrentSession().createQuery(""
            + "select s "
            + "from dis.software.pos.entities.Sale s "
            + "where s.deleted = 1").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener registros de venta filtrados por un rango de fechas
     * @param initialDate Fecha inicial
     * @param finalDate Fecha final
     * @return Lista de registros de venta
     */
    @Override
    public List<Sale> findByDateRange(Calendar initialDate, Calendar finalDate)
    {
        List<Sale> list = super.getCurrentSession().createQuery(""
            + "select s "
            + "from dis.software.pos.entities.Sale s "
            + "where s.createdDate >= :initialDate "
            + "and s.createdDate <= :finalDate "
            + "and (s.deleted = 0 or s.deleted is null)")
            .setParameter("initialDate", initialDate)
            .setParameter("finalDate", finalDate).list();
        logger.info("findByDateRange of " + clazz + " completed.");
        
        return list;
    }
    
    @Override
    public Double getDailyCashOutAmount()
    {
        //Fecha inicial
        Calendar iCalendar = Calendar.getInstance();
        //Fecha final (final de día)
        Calendar fCalendar = Calendar.getInstance();
        fCalendar.setTime(iCalendar.getTime());
        fCalendar.add(Calendar.HOUR_OF_DAY, 23);
        fCalendar.add(Calendar.MINUTE, 59);
        
        Double amount = (Double) super.getCurrentSession().createQuery(""
            + "select sum(s.totalAmount) "
            + "from dis.software.pos.entities.Sale s "
            + "where s.createdDate >= :initialDate "
            + "and s.createdDate <= :finalDate "
            + "and (s.deleted = 0 or s.deleted is null)")
            .setParameter("initialDate", iCalendar)
            .setParameter("finalDate", fCalendar).uniqueResult();
        logger.info("getDailyCashOutAmount of " + clazz + " completed.");
        
        return amount;
    }
    
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.InventoryType;
import dis.software.pos.entities.Inventory;
import dis.software.pos.interfaces.IInventory;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso al inventario
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class InventoryController extends GenericHibernateController<Inventory, Long>
    implements IInventory
{
    
    private static final Logger logger = LogManager.getLogger(InventoryController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de inventario
     * @return Código de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(i.code) "
            + "from dis.software.pos.entities.Inventory i "
            + "where i.code like 'INV%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "INV" + String.format("%04d", code == null ? number : number+1);
    }
    
    /**
     * Método para obtener todos los productos marcados como eliminados
     * @return Lista de productos eliminados
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> getDeleted()
    {
        List<Inventory> list = super.getCurrentSession().createQuery(""
            + "select i "
            + "from dis.software.pos.entities.Inventory i "
            + "where i.deleted = 1").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener registros de inventario filtrados por tipo
     * @param inventoryType Tipo de inventario para filtrar
     * @return Lista de registros de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> findByInventoryType(InventoryType inventoryType)
    {
        List<Inventory> list = super.getCurrentSession().createQuery(""
            + "select i "
            + "from dis.software.pos.entities.Inventory i "
            + "where i.type = :type "
            + "and (i.deleted = 0 or i.deleted is null)")
            .setParameter("type", inventoryType.getValue()).list();
        logger.info("findByInventoryType of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener registros de inventario
     * @return Lista de registros de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> findAll()
    {
        List<Inventory> list = super.getCurrentSession().createQuery(""
            + "select i "
            + "from dis.software.pos.entities.Inventory i "
            + "where i.deleted = 0 or i.deleted is null").list();
        logger.info("findAll of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener registros de inventario filtrados por tipo y rango de fechas
     * @param inventoryType Tipo de inventario
     * @param initialDate Fecha inicial
     * @param finalDate Fecha final
     * @return Lista de registros de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> findByInventoryTypeAndDate(
        InventoryType inventoryType, Calendar initialDate, Calendar finalDate)
    {
        List<Inventory> list = super.getCurrentSession().createQuery(""
            + "select i "
            + "from dis.software.pos.entities.Inventory i "
            + "where i.type = :type "
            + "and i.createdDate >= :initialDate "
            + "and i.createdDate <= :finalDate "
            + "and (i.deleted = 0 or i.deleted is null)")
            .setParameter("type", inventoryType.getValue())
            .setParameter("initialDate", initialDate)
            .setParameter("finalDate", finalDate).list();
        logger.info("findByInventoryTypeAndDate of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener registros de entrada de inventario filtrados por un rango de fechas
     * @param initialDate Fecha inicial
     * @param finalDate Fecha final
     * @return Lista de registros de entrada de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> findIncomesByDate(Calendar initialDate, Calendar finalDate)
    {
        logger.info("findIncomesByDate of " + clazz + " completed.");
        return findByInventoryTypeAndDate(InventoryType.INCOME, initialDate, finalDate);
    }

    /**
     * Método para obtener registros de salida de inventario filtrados por un rango de fechas
     * @param initialDate Fecha inicial
     * @param finalDate Fecha final
     * @return Lista de registros de salida de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inventory> findOutcomesByDate(Calendar initialDate, Calendar finalDate)
    {
        logger.info("findOutcomesByDate of " + clazz + " completed.");
        return findByInventoryTypeAndDate(InventoryType.OUTCOME, initialDate, finalDate);
    }

    /**
     * Método para determinar si el inventario especificado fue el último que se generó
     * @param inventory Inventario a determinar
     * @return Verdadero si ha sido el último, Falso en caso contrario
     */
    @Override
    public Boolean isLast(Inventory inventory)
    {
        List<Inventory> list = super.getCurrentSession().createQuery(""
            + "select i "
            + "from dis.software.pos.entities.Inventory i "
            + "where i.type = :type "
            + "and i.createdDate > :createdDate "
            + "and (i.deleted = 0 or i.deleted is null)")
            .setParameter("type", InventoryType.SYSTEM.getValue())
            .setParameter("createdDate", inventory.getCreatedDate()).list();
        logger.info("isLast of " + clazz + " completed.");
        return list.size() <= 0;
    }
    
}

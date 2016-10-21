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
        String code = (String) super.executeQuery("select max(i.code) "
            + "from dis.software.pos.entities.Inventory i where i.code like 'INV-%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(4));
        return "INV-" + String.format("%03d", code == null ? number : number+1);
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
        List<Inventory> inventories = super.getCurrentSession().createQuery(""
            + "select i from dis.software.pos.entities.Inventory i "
            + "where i.type = :type").setParameter("type", inventoryType.getValue()).list();
        logger.info("findByInventoryType of " + clazz + " completed.");
        return inventories;
    }
    
}

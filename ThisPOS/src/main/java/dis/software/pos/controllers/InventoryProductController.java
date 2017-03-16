/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.EntityStatus;
import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.InventoryProductPk;
import dis.software.pos.interfaces.IInventoryProduct;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a la relación Inventario - Productos
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class InventoryProductController extends GenericHibernateController<InventoryProduct, InventoryProductPk>
    implements IInventoryProduct
{
    
    private static final Logger logger = LogManager.getLogger(InventoryProductController.class.getSimpleName());
    
    /**
     * Método para bloquear los registros de inventario por "SISTEMA" que ya fueron atendidos
     * @param list Lista de productos por registro de inventario
     */
    @Override
    public void lockAll(List<InventoryProduct> list)
    {
        list.forEach(ip ->
        {
            super.getCurrentSession().createQuery(""
                + "update "
                + "dis.software.pos.entities.InventoryProduct ip "
                + "set ip.status = :status "
                + "where ip.inventoryProductPk.inventory.id = :inventory_id "
                + "and ip.inventoryProductPk.product.id = :product_id "
                + "and ip.inventoryProductPk.unit.id = :unit_id")
                .setParameter("status", EntityStatus.LOCKED.getValue())
                .setParameter("inventory_id", ip.getInventory().getId())
                .setParameter("product_id", ip.getProduct().getId())
                .setParameter("unit_id", ip.getUnit().getId()).executeUpdate();
        });
        logger.info("setLocked of " + clazz + " completed.");
    }
    
    /**
     * Método para obtener productos filtrados por registro de inventario
     * @param inventory Inventario para filtrar
     * @return Lista de productos por registro de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventoryProduct> findByInventory(Inventory inventory)
    {
        List<InventoryProduct> list = super.getCurrentSession().createQuery(""
            + "select ip "
            + "from dis.software.pos.entities.InventoryProduct ip "
            + "where ip.inventoryProductPk.inventory.id = :inventory_id")
            .setParameter("inventory_id", inventory.getId()).list();
        logger.info("findByInventory of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener registros de productos en inventario filtrados por un rango de fechas
     * @param initialDate Fecha inicial
     * @param finalDate Fecha final
     * @return Lista de registros de productos en inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventoryProduct> findByDate(Calendar initialDate, Calendar finalDate)
    {
        List<InventoryProduct> list = super.getCurrentSession().createQuery(""
            + "select ip "
            + "from dis.software.pos.entities.InventoryProduct ip "
            + "where ip.inventoryProductPk.inventory.createdDate >= :initialDate "
            + "and ip.inventoryProductPk.inventory.createdDate <= :finalDate")
            .setParameter("initialDate", initialDate)
            .setParameter("finalDate", finalDate).list();
        logger.info("findByDate of " + clazz + " completed.");
        return list;
    }
    
}

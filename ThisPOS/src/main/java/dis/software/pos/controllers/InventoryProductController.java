/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.InventoryProductPk;
import dis.software.pos.interfaces.IInventoryProduct;
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
     * Método para obtener productos filtrados por registro de inventario
     * @param inventory Inventario para filtrar
     * @return Lista de productos por registro de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public List<InventoryProduct> findByInventory(Inventory inventory)
    {
        List<InventoryProduct> inventoryProducts = super.getCurrentSession().createQuery(""
            + "select ip from dis.software.pos.entities.InventoryProduct ip "
            + "where ip.inventoryProductPk.inventory.id = :inventory_id")
            .setParameter("inventory_id", inventory.getId()).list();
        logger.info("findByInventory of " + clazz + " completed.");
        return inventoryProducts;
    }
    
}

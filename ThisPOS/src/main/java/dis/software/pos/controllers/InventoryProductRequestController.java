/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.InventoryProductRequest;
import dis.software.pos.entities.InventoryProductRequestPk;
import dis.software.pos.interfaces.IInventoryProductRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class InventoryProductRequestController
    extends GenericHibernateController<InventoryProductRequest, InventoryProductRequestPk>
    implements IInventoryProductRequest
{
    
    private static final Logger logger = LogManager.getLogger(InventoryProductRequestController.class.getSimpleName());
    
    /**
     * Método para verificar si existe el registro de solicitud de producto por conteo de inventario
     * @param pk Llave primaria de la tabla
     * @return Solicitud de producto por conteo de inventario
     */
    @Override
    @Transactional(readOnly = true)
    public InventoryProductRequest findById(InventoryProductRequestPk pk)
    {
        return (InventoryProductRequest) super.getCurrentSession().createQuery(""
            + "select ipr "
            + "from dis.software.pos.entities.InventoryProductRequest ipr "
            + "where ipr.inventoryProductRequestPk.inventory.id = :inventory_id "
            + "and ipr.inventoryProductRequestPk.product.id = :product_id "
            + "and ipr.inventoryProductRequestPk.unit.id = :unit_id")
            .setParameter("inventory_id", pk.getInventory().getId())
            .setParameter("product_id", pk.getProduct().getId())
            .setParameter("unit_id", pk.getUnit().getId()).uniqueResult();
    }
    
}

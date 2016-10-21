/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.InventoryProductPk;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IInventoryProduct extends IGenericHibernate<InventoryProduct, InventoryProductPk>
{
    List<InventoryProduct> findByInventory(Inventory inventory);
}

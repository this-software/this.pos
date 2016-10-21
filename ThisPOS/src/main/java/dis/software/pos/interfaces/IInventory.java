/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.InventoryType;
import dis.software.pos.entities.Inventory;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IInventory extends IGenericHibernate<Inventory, Long>
{
    String getNextCode();
    List<Inventory> findByInventoryType(InventoryType inventoryType);
}

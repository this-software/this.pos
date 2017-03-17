/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.InventoryType;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class InventoryTypeComboBoxModel extends DefaultComboBoxModel<InventoryType>
{
    
    List<InventoryType> list = new ArrayList<>();
    
    public InventoryTypeComboBoxModel(List<InventoryType> units)
    {
        super(units.toArray(new InventoryType[units.size()]));
        this.list = units;
    }
    
    @Override
    public InventoryType getSelectedItem()
    {
        InventoryType inventoryType = (InventoryType) super.getSelectedItem();
        return inventoryType;
    }
    
    public List<InventoryType> getItems()
    {
        return list;
    }
    
}

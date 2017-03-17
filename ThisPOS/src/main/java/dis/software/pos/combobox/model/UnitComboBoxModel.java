/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.entities.Unit;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class UnitComboBoxModel extends DefaultComboBoxModel<Unit>
{
    
    List<Unit> list = new ArrayList<>();
    
    public UnitComboBoxModel(List<Unit> units)
    {
        super(units.toArray(new Unit[units.size()]));
        this.list = units;
    }
    
    @Override
    public Unit getSelectedItem()
    {
        Unit unit = (Unit) super.getSelectedItem();
        return unit;
    }
    
    public List<Unit> getItems()
    {
        return list;
    }
    
}

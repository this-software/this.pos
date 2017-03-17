/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.TableFilterBy;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class TableFilterByComboBoxModel extends DefaultComboBoxModel<TableFilterBy>
{
    
    public TableFilterByComboBoxModel(List<TableFilterBy> tableFiltersBy)
    {
        super(tableFiltersBy.toArray(new TableFilterBy[tableFiltersBy.size()]));
    }
    
    @Override
    public TableFilterBy getSelectedItem()
    {
        TableFilterBy tableFilterBy = (TableFilterBy) super.getSelectedItem();
        return tableFilterBy;
    }
    
}

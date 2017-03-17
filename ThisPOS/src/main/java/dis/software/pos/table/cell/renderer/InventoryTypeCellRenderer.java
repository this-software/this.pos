/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.cell.renderer;

import dis.software.pos.InventoryType;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Clase para construir y personalizar una columna de tipo InventoryType
 * @author Milton Cavazos
 */
public class InventoryTypeCellRenderer extends DefaultTableCellRenderer
{
    
    public InventoryTypeCellRenderer()
    {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (!(value instanceof InventoryType))
        {
            return this;
        }
        switch((InventoryType) value)
        {
            case INCOME : setText("ENTRADA"); break;
            case OUTCOME : setText("SALIDA"); break;
            case CANCEL : setText("CANCELADO"); break;
            case SYSTEM : setText("SISTEMA"); break;
        }
        return this;
    }

}

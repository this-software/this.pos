/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.cell.renderer;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Clase para construir y personalizar una columna de tipo Double
 * @author Milton Cavazos
 */
public class MoneyCellRenderer extends DefaultTableCellRenderer
{
    
    public MoneyCellRenderer()
    {
        super();
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof Double
            && (Double) value >= 0.00)
        {
            setText(String.format("$%,.2f", value));
            setHorizontalAlignment(SwingConstants.RIGHT);
        }
        return this;
    }
    
}

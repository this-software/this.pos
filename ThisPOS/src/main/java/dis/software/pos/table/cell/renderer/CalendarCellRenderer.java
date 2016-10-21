/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.cell.renderer;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Clase para construir y personalizar una columna de tipo Calendar
 * @author Milton Cavazos
 */
public class CalendarCellRenderer extends DefaultTableCellRenderer
{
    
    public CalendarCellRenderer()
    {
        super();
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column)
    {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setText(((new SimpleDateFormat("yyyy-MMM-dd HH:mm"))
            .format(((Calendar) value).getTime())).toUpperCase());
        return this;
    }
    
}

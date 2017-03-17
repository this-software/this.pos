/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.renderers;

import dis.software.pos.InventoryType;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Clase para construir y personalizar una lista desplegable de tipos de inventario
 * @author Milton Cavazos
 */
public class InventoryTypeComboBoxRenderer extends DefaultListCellRenderer
{
    
    public InventoryTypeComboBoxRenderer()
    {
        super();
    }
    
    @Override
    public Component getListCellRendererComponent(JList list,
        Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
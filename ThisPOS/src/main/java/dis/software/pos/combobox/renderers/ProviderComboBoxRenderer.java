/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.renderers;

import dis.software.pos.entities.Provider;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Clase para construir y personalizar una lista desplegable de proveedores
 * @author Milton Cavazos
 */
public class ProviderComboBoxRenderer extends DefaultListCellRenderer
{
    
    public ProviderComboBoxRenderer()
    {
        super();
    }
    
    @Override
    public Component getListCellRendererComponent(JList list,
        Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        super.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (value instanceof Provider)
        {
            Provider provider = (Provider) value;
            super.setText(provider.getName());
        }
        return this;
    }
    
}

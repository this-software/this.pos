/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.renderers;

import dis.software.pos.entities.User;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Clase modelo constructora de una lista desplegable de usuarios
 * @author Milton Cavazos
 */
public class UserComboBoxRenderer extends DefaultListCellRenderer
{

    public UserComboBoxRenderer()
    {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list,
        Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (value instanceof User)
        {
            User user = (User) value;
            setText(user.getName());
        }
        return this;
    }
    
}

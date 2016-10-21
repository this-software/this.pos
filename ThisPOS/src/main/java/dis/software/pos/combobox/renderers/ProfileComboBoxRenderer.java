package dis.software.pos.combobox.renderers;

import dis.software.pos.entities.Profile;
import java.awt.Component;
import java.awt.Font;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 * Clase modelo constructora de una lista desplegable de perfiles
 * @author Milton Cavazos
 */
public class ProfileComboBoxRenderer extends DefaultListCellRenderer
{

    public ProfileComboBoxRenderer()
    {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList list,
        Object value, int index, boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        super.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        if (value instanceof Profile)
        {
            Profile profile = (Profile) value;
            super.setText(profile.getName());
        }
        return this;
    }
    
}

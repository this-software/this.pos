/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.entities.User;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class UserComboBoxModel extends DefaultComboBoxModel<User>
{
    
    public UserComboBoxModel(List<User> profiles)
    {
        super(profiles.toArray(new User[profiles.size()]));
    }

    @Override
    public User getSelectedItem()
    {
        User user = (User) super.getSelectedItem();
        return user;
    }
    
}

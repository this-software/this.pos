/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.entities.Profile;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class ProfileComboBoxModel extends DefaultComboBoxModel<Profile>
{
    
    public ProfileComboBoxModel(List<Profile> profiles)
    {
        super(profiles.toArray(new Profile[profiles.size()]));
    }

    @Override
    public Profile getSelectedItem()
    {
        Profile profile = (Profile) super.getSelectedItem();
        return profile;
    }
    
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.entities.Provider;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class ProviderComboBoxModel extends DefaultComboBoxModel<Provider>
{
    
    public ProviderComboBoxModel(List<Provider> providers)
    {
        super(providers.toArray(new Provider[providers.size()]));
    }
    
    @Override
    public Provider getSelectedItem()
    {
        Provider provider = (Provider) super.getSelectedItem();
        return provider;
    }
    
}

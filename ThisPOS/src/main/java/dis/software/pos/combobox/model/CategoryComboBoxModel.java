/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.entities.Category;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class CategoryComboBoxModel extends DefaultComboBoxModel<Category>
{
    
    public CategoryComboBoxModel(List<Category> categories)
    {
        super(categories.toArray(new Category[categories.size()]));
    }
    
    @Override
    public Category getSelectedItem()
    {
        Category category = (Category) super.getSelectedItem();
        return category;
    }
    
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.combobox.model;

import dis.software.pos.entities.Product;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Milton Cavazos
 */
public class ProductComboBoxModel extends DefaultComboBoxModel<Product>
{
    
    public ProductComboBoxModel(List<Product> products)
    {
        super(products.toArray(new Product[products.size()]));
    }
    
    @Override
    public Product getSelectedItem()
    {
        Product product = (Product) super.getSelectedItem();
        return product;
    }
    
}

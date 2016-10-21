/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Clase que contiene los campos de identidad para los productos ingresados al inventario
 * @author Milton Cavazos
 */
@Embeddable
public class InventoryProductPk implements Serializable
{
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Inventory inventory;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        InventoryProductPk that = (InventoryProductPk) o;
        if (inventory != null ? !inventory.equals(that.inventory) : that.inventory != null) return false;
        return !(product != null ? !product.equals(that.product) : that.product != null);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.inventory);
        hash = 47 * hash + Objects.hashCode(this.product);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ProfileModulePk: " + this.inventory + ", " + this.product;
    }
    
}

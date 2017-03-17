/*
 * Copyright (C) 2017 Milton Cavazos
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
 * Clase que contiene los campos de identidad para los productos solicitados por cotejo de inventario
 * @author Milton Cavazos
 */
@Embeddable
public class InventoryProductRequestPk implements Serializable
{

    @ManyToOne(fetch = FetchType.LAZY)
    private Inventory inventory;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unit;

    public InventoryProductRequestPk() {}
    
    public InventoryProductRequestPk(Inventory inventory, Product product, Unit unit)
    {
        this.inventory = inventory;
        this.product = product;
        this.unit = unit;
    }

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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        InventoryProductRequestPk that = (InventoryProductRequestPk) o;
        if (inventory != null ? !inventory.equals(that.inventory) : that.inventory != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return !(unit != null ? !unit.equals(that.unit) : that.unit != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.inventory);
        hash = 59 * hash + Objects.hashCode(this.product);
        hash = 59 * hash + Objects.hashCode(this.unit);
        return hash;
    }

    @Override
    public String toString() {
        return "InventoryProductRequestPk: " + this.inventory + ", " + this.product + ", " + this.unit;
    }

}

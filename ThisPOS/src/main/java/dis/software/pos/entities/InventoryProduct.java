/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.entities;

import dis.software.pos.EntityStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author Milton Cavazos
 */
@Entity
@Table(name = "inventory_product")
@AssociationOverrides({
    @AssociationOverride(name = "inventoryProductPk.inventory", joinColumns = @JoinColumn(name = "inventory_id")),
    @AssociationOverride(name = "inventoryProductPk.product", joinColumns = @JoinColumn(name = "product_id")),
    @AssociationOverride(name = "inventoryProductPk.unit", joinColumns = @JoinColumn(name = "unit_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class InventoryProduct implements Serializable
{
    
    @EmbeddedId
    private InventoryProductPk inventoryProductPk = new InventoryProductPk();
    
    @Column(nullable = false)
    private Integer status;
    
    @Column(nullable = false)
    private Integer units;
    
    @Column(name = "real_units", nullable = true)
    private Integer realUnits;
    
    public InventoryProduct() {}
    
    public InventoryProduct(InventoryProductPk inventoryProductPk) {
        this.inventoryProductPk = inventoryProductPk;
    }

    public InventoryProductPk getInventoryProductPk() {
        return inventoryProductPk;
    }

    public void setInventoryProductPk(InventoryProductPk inventoryProductPk) {
        this.inventoryProductPk = inventoryProductPk;
    }

    public EntityStatus getStatus()
    {
        switch(status)
        {
            case 0: return EntityStatus.INACTIVE;
            case 1: return EntityStatus.ACTIVE;
            case 2: return EntityStatus.CANCELED;
            case 3: return EntityStatus.LOCKED;
        }
        return null;
    }

    public void setStatus(EntityStatus status) {
        this.status = status.getValue();
    }

    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    public Integer getRealUnits() {
        return realUnits;
    }

    public void setRealUnits(Integer realUnits) {
        this.realUnits = realUnits;
    }
    
    @Transient
    public Inventory getInventory()
    {
        return getInventoryProductPk().getInventory();
    }
    
    public void setInventory(Inventory inventory)
    {
        getInventoryProductPk().setInventory(inventory);
    }
    
    @Transient
    public Product getProduct()
    {
        return getInventoryProductPk().getProduct();
    }
    
    public void setProduct(Product product)
    {
        getInventoryProductPk().setProduct(product);
    }
    
    @Transient
    public Unit getUnit()
    {
        return getInventoryProductPk().getUnit();
    }
    
    public void setUnit(Unit unit)
    {
        getInventoryProductPk().setUnit(unit);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        InventoryProduct that = (InventoryProduct) o;
        return !(getInventoryProductPk() != null ? !getInventoryProductPk().equals(that.getInventoryProductPk())
            : that.getInventoryProductPk() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.inventoryProductPk);
        return hash;
    }
    
    @Override
    public String toString() {
        return "InventoryProduct: " + this.inventoryProductPk;
    }
    
}

/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Milton Cavazos
 */
@Entity
@Table(name = "inventory_product_request")
@AssociationOverrides({
    @AssociationOverride(name = "inventoryProductRequestPk.inventory", joinColumns = @JoinColumn(name = "inventory_id")),
    @AssociationOverride(name = "inventoryProductRequestPk.product", joinColumns = @JoinColumn(name = "product_id")),
    @AssociationOverride(name = "inventoryProductRequestPk.unit", joinColumns = @JoinColumn(name = "unit_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class InventoryProductRequest implements Serializable
{

    @EmbeddedId
    private InventoryProductRequestPk inventoryProductRequestPk = new InventoryProductRequestPk();
    
    @Column(name = "required_units", nullable = false)
    private Integer requiredUnits;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "created_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar createdDate = new GregorianCalendar();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    
    @Column(name = "updated_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar updatedDate;

    public InventoryProductRequest() {}

    public InventoryProductRequestPk getInventoryProductRequestPk() {
        return inventoryProductRequestPk;
    }

    public void setInventoryProductRequestPk(InventoryProductRequestPk inventoryProductRequestPk) {
        this.inventoryProductRequestPk = inventoryProductRequestPk;
    }

    public Integer getRequiredUnits() {
        return requiredUnits;
    }

    public void setRequiredUnits(Integer requiredUnits) {
        this.requiredUnits = requiredUnits;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    @Transient
    public Inventory getInventory()
    {
        return getInventoryProductRequestPk().getInventory();
    }
    
    public void setInventory(Inventory inventory)
    {
        getInventoryProductRequestPk().setInventory(inventory);
    }
    
    @Transient
    public Product getProduct()
    {
        return getInventoryProductRequestPk().getProduct();
    }
    
    public void setProduct(Product product)
    {
        getInventoryProductRequestPk().setProduct(product);
    }
    
    @Transient
    public Unit getUnit()
    {
        return getInventoryProductRequestPk().getUnit();
    }
    
    public void setUnit(Unit unit)
    {
        getInventoryProductRequestPk().setUnit(unit);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        InventoryProductRequest that = (InventoryProductRequest) o;
        return !(getInventoryProductRequestPk() != null
            ? !getInventoryProductRequestPk().equals(that.getInventoryProductRequestPk())
            : that.getInventoryProductRequestPk() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.inventoryProductRequestPk);
        return hash;
    }

    @Override
    public String toString() {
        return "InventoryProductRequest: " + this.inventoryProductRequestPk;
    }

}

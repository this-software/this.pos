/*
 * Copyright (C) 2016 Milton Cavazos
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
@Table(name = "purchase_detail")
@AssociationOverrides({
    @AssociationOverride(name = "purchaseDetailPk.purchase", joinColumns = @JoinColumn(name = "purchase_id")),
    @AssociationOverride(name = "purchaseDetailPk.product", joinColumns = @JoinColumn(name = "product_id")),
    @AssociationOverride(name = "purchaseDetailPk.unit", joinColumns = @JoinColumn(name = "unit_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class PurchaseDetail implements Serializable
{
    
    @EmbeddedId
    private PurchaseDetailPk purchaseDetailPk = new PurchaseDetailPk();
    
    @Column(nullable = false)
    private Double cost;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(name = "total_cost", nullable = false)
    private Double totalCost;
    
    @Column(nullable = false)
    private Integer deleted = 0;
    
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

    public PurchaseDetail() {}

    public PurchaseDetailPk getPurchaseDetailPk() {
        return purchaseDetailPk;
    }

    public void setPurchaseDetailPk(PurchaseDetailPk purchaseDetailPk) {
        this.purchaseDetailPk = purchaseDetailPk;
    }
    
    @Transient
    public Purchase getPurchase()
    {
        return getPurchaseDetailPk().getPurchase();
    }
    
    public void setPurchase(Purchase purchase)
    {
        getPurchaseDetailPk().setPurchase(purchase);
    }
    
    @Transient
    public Product getProduct()
    {
        return getPurchaseDetailPk().getProduct();
    }
    
    public void setProduct(Product product)
    {
        getPurchaseDetailPk().setProduct(product);
    }
    
    @Transient
    public Unit getUnit()
    {
        return getPurchaseDetailPk().getUnit();
    }
    
    public void setUnit(Unit unit)
    {
        getPurchaseDetailPk().setUnit(unit);
    }

    /**
     * Costo del producto en el momento que fue comprado
     * @return Costo del producto
     */
    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PurchaseDetail that = (PurchaseDetail) o;
        return !(getPurchaseDetailPk()!= null ? !getPurchaseDetailPk().equals(that.getPurchaseDetailPk())
            : that.getPurchaseDetailPk() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.purchaseDetailPk);
        return hash;
    }
    
    @Override
    public String toString() {
        return "PurchaseDetail: " + this.purchaseDetailPk;
    }

}

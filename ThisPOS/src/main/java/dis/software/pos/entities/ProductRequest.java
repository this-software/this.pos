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
@Table(name = "product_request")
@AssociationOverrides({
    @AssociationOverride(name = "productRequestPk.product", joinColumns = @JoinColumn(name = "product_id")),
    @AssociationOverride(name = "productRequestPk.unit", joinColumns = @JoinColumn(name = "unit_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class ProductRequest implements Serializable
{

    @EmbeddedId
    private ProductRequestPk productRequestPk = new ProductRequestPk();
    
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

    public ProductRequest() {}

    public ProductRequestPk getProductRequestPk() {
        return productRequestPk;
    }

    public void setProductRequestPk(ProductRequestPk productRequestPk) {
        this.productRequestPk = productRequestPk;
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
    public Product getProduct()
    {
        return getProductRequestPk().getProduct();
    }
    
    public void setProduct(Product product)
    {
        getProductRequestPk().setProduct(product);
    }
    
    @Transient
    public Unit getUnit()
    {
        return getProductRequestPk().getUnit();
    }
    
    public void setUnit(Unit unit)
    {
        getProductRequestPk().setUnit(unit);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ProductRequest that = (ProductRequest) o;
        return !(getProductRequestPk() != null ? !getProductRequestPk().equals(that.getProductRequestPk())
            : that.getProductRequestPk() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.productRequestPk);
        return hash;
    }

    @Override
    public String toString() {
        return "ProductRequest: " + this.productRequestPk;
    }

}

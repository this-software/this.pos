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
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Clase que contiene la relación para cada combinación de Producto y Unidad
 * @author Milton Cavazos
 */
@Entity
@Table(name = "product_unit")
@AssociationOverrides({
    @AssociationOverride(name = "productUnitPk.product", joinColumns = @JoinColumn(name = "product_id")),
    @AssociationOverride(name = "productUnitPk.unit", joinColumns = @JoinColumn(name = "unit_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class ProductUnit implements Serializable
{
    
    @EmbeddedId
    private ProductUnitPk productUnitPk = new ProductUnitPk();
    
    @Column(name = "quantity_by_unit")
    private Integer quantityByUnit = 0;
    
    @Column(name = "cost_by_unit")
    private Double costByUnit = 0.00;
    
    @Column(name = "price_by_unit")
    private Double priceByUnit = 0.00;
    
    @Column(name = "out_of_time_price_by_unit")
    private Double outOfTimePriceByUnit = 0.00;
    
    @Column(name = "is_default")
    private Boolean isDefault;
    
    public ProductUnit() {}

    public ProductUnitPk getProductUnitPk() {
        return productUnitPk;
    }

    public void setProductUnitPk(ProductUnitPk productUnitPk) {
        this.productUnitPk = productUnitPk;
    }

    public Integer getQuantityByUnit() {
        return quantityByUnit;
    }

    public void setQuantityByUnit(Integer quantityByUnit) {
        this.quantityByUnit = quantityByUnit;
    }

    public Double getCostByUnit() {
        return costByUnit;
    }

    public void setCostByUnit(Double costByUnit) {
        this.costByUnit = costByUnit;
    }

    public Double getPriceByUnit() {
        return priceByUnit;
    }

    public void setPriceByUnit(Double priceByUnit) {
        this.priceByUnit = priceByUnit;
    }

    /**
     * Precio especial que será cobrado solo si las condiciones especificas se cumplen
     * @return Precio especial por unidad
     */
    public Double getOutOfTimePriceByUnit() {
        return outOfTimePriceByUnit;
    }

    public void setOutOfTimePriceByUnit(Double outOfTimePriceByUnit) {
        this.outOfTimePriceByUnit = outOfTimePriceByUnit;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    @Transient
    public Product getProduct()
    {
        return getProductUnitPk().getProduct();
    }
    
    public void setProduct(Product product)
    {
        getProductUnitPk().setProduct(product);
    }
    
    @Transient
    public Unit getUnit()
    {
        return getProductUnitPk().getUnit();
    }
    
    public void setUnit(Unit unit)
    {
        getProductUnitPk().setUnit(unit);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ProductUnit that = (ProductUnit) o;
        return !(getProductUnitPk()!= null ? !getProductUnitPk().equals(that.getProductUnitPk())
            : that.getProductUnitPk() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.productUnitPk);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ProductUnit: " + this.productUnitPk;
    }
    
}

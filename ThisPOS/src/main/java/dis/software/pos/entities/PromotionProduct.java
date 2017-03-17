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
 * Clase que contiene la relación para cada combinación de Promoción y Producto
 * @author Milton Cavazos
 */
@Entity
@Table(name = "promotion_product")
@AssociationOverrides({
    @AssociationOverride(name = "promotionProductPk.promotion", joinColumns = @JoinColumn(name = "promotion_id")),
    @AssociationOverride(name = "promotionProductPk.product", joinColumns = @JoinColumn(name = "product_id")),
    @AssociationOverride(name = "promotionProductPk.unit", joinColumns = @JoinColumn(name = "unit_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class PromotionProduct implements Serializable
{

    @EmbeddedId
    private PromotionProductPk promotionProductPk = new PromotionProductPk();

    @Column
    private Integer units;
    
    public PromotionProduct() {}

    public PromotionProductPk getPromotionProductPk() {
        return promotionProductPk;
    }

    public void setPromotionProductPk(PromotionProductPk promotionProductPk) {
        this.promotionProductPk = promotionProductPk;
    }
    
    public Integer getUnits() {
        return units;
    }

    public void setUnits(Integer units) {
        this.units = units;
    }

    @Transient
    public Promotion getPromotion()
    {
        return getPromotionProductPk().getPromotion();
    }
    
    public void setPromotion(Promotion promotion)
    {
        getPromotionProductPk().setPromotion(promotion);
    }
    
    @Transient
    public Product getProduct()
    {
        return getPromotionProductPk().getProduct();
    }
    
    public void setProduct(Product product)
    {
        getPromotionProductPk().setProduct(product);
    }
    
    @Transient
    public Unit getUnit()
    {
        return getPromotionProductPk().getUnit();
    }
    
    public void setUnit(Unit unit)
    {
        getPromotionProductPk().setUnit(unit);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PromotionProduct that = (PromotionProduct) o;
        return !(getPromotionProductPk() != null ? !getPromotionProductPk().equals(that.getPromotionProductPk())
            : that.getPromotionProductPk() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.promotionProductPk);
        return hash;
    }

    @Override
    public String toString() {
        return "PromotionProduct: " + this.promotionProductPk;
    }

}

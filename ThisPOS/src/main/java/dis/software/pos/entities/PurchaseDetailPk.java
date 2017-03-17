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
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Clase que contiene los campos de identidad para los detalles de compra
 * @author Milton Cavazos
 */
@Embeddable
public class PurchaseDetailPk implements Serializable
{
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Purchase purchase;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Unit unit;

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
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
        
        PurchaseDetailPk that = (PurchaseDetailPk) o;
        if (purchase != null ? !purchase.equals(that.purchase) : that.purchase != null) return false;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return !(unit != null ? !unit.equals(that.unit) : that.unit != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.purchase);
        hash = 79 * hash + Objects.hashCode(this.product);
        hash = 79 * hash + Objects.hashCode(this.unit);
        return hash;
    }
    
    @Override
    public String toString() {
        return "PurchaseDetailPk: " + this.purchase + ", " + this.product + ", " + this.unit;
    }
    
}

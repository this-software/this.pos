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
 * Clase que contiene los campos de identidad para cada combinación de Venta y Promoción
 * @author Milton Cavazos
 */
@Embeddable
public class SalePromotionDetailPk implements Serializable
{

    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Promotion promotion;

    public SalePromotionDetailPk() {}

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        SalePromotionDetailPk that = (SalePromotionDetailPk) o;
        if (sale != null ? !sale.equals(that.sale) : that.sale != null) return false;
        return !(promotion != null ? !promotion.equals(that.promotion) : that.promotion != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.sale);
        hash = 79 * hash + Objects.hashCode(this.promotion);
        return hash;
    }

    @Override
    public String toString() {
        return "SalesPromotionDetailPk: " + this.sale + ", " + this.promotion;
    }

}

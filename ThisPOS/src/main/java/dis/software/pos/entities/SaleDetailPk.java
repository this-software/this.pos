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
 * Clase que contiene los campos de identidad para cada combinación de Venta y Producto
 * @author Milton Cavazos
 */
@Embeddable
public class SaleDetailPk implements Serializable
{

    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    public SaleDetailPk() {}

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
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
        
        SaleDetailPk that = (SaleDetailPk) o;
        if (sale != null ? !sale.equals(that.sale) : that.sale != null) return false;
        return !(product != null ? !product.equals(that.product) : that.product != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.sale);
        hash = 61 * hash + Objects.hashCode(this.product);
        return hash;
    }

    @Override
    public String toString() {
        return "SalesDetailPk: " + this.sale + ", " + this.product;
    }

}

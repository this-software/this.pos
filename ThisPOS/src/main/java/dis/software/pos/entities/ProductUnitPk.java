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
 * Clase que contiene los campos de identidad para cada combinación de Producto y Unidad
 * @author Milton Cavazos
 */
@Embeddable
public class ProductUnitPk implements Serializable
{
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;
    
    @ManyToOne
    private Unit unit;
    
    public ProductUnitPk() {}

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
        
        ProductUnitPk that = (ProductUnitPk) o;
        if (product != null ? !product.equals(that.product) : that.product != null) return false;
        return !(unit != null ? !unit.equals(that.unit) : that.unit != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.product);
        hash = 67 * hash + Objects.hashCode(this.unit);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ProductUnitPk: " + this.product + ", " + this.unit;
    }
    
}

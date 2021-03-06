/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.entities;

import dis.software.pos.InventoryType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que contiene los campos de inventario
 * @author Milton Cavazos
 */
@Entity
public class Inventory implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String code;
    
    @Column(unique = true, length = 50)
    private Integer type;
    
    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;
    
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
    
    @OneToMany(mappedBy = "inventoryProductPk.inventory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<InventoryProduct> inventoryProducts = new HashSet<>();
    
    public Inventory() {}
    
    public Inventory(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param id
     * @param type 
     */
    public Inventory(Long id, InventoryType type)
    {
        this.id = id;
        this.type = type.getValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public InventoryType getType()
    {
        switch(type)
        {
            case 0: return InventoryType.INCOME;
            case 1: return InventoryType.OUTCOME;
            case 2: return InventoryType.CANCEL;
            case 3: return InventoryType.SYSTEM;
        }
        return null;
    }

    public void setType(InventoryType type) {
        this.type = type.getValue();
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
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

    public Set<InventoryProduct> getInventoryProducts() {
        return inventoryProducts;
    }

    public void setInventoryProducts(Set<InventoryProduct> inventoryProducts) {
        this.inventoryProducts = inventoryProducts;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Inventory that = (Inventory) o;
        return !(this.id != null ? !this.id.equals(that.getId())
            : that.getId() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public String toString() {
        return "Inventory: " + this.id + ", " + this.code + ", " + this.type;
    }    
    
}

/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.entities;

import dis.software.pos.EntityStatus;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que contiene los campos de notificación
 * @author Milton Cavazos
 */
@Entity
public class Notification implements Serializable
{
    
    public static final String INVENTORY_OUTCOME = "NOTINVOUT";
    public static final String INVENTORY_CANCEL = "NOTINVCAN";
    public static final String PURCHASE_CANCEL = "NOTPURCAN";
    public static final String SALE_CANCEL = "NOTSALCAN";
    public static final String PRODUCT_REQUEST = "NOTPROREQ";
    public static final String PRODUCT_REQUEST_CANCEL = "NOTPROREQCAN";
    public static final String PRODUCT_MIN_LEVEL = "NOTMINLEV";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, length = 50)
    private String code;
    
    @Column
    private String description;
    
    @Column
    private Integer status;
    
    @Column
    private Boolean deleted;
    
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

    public Notification() {}
    
    /**
     * Método constructor de la clase
     * @param id Id de notificación
     */
    public Notification(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param code Código de notificación
     * @param description Descripción de notificación
     */
    public Notification(String code, String description)
    {
        this.code = code;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EntityStatus getStatus()
    {
        switch(status)
        {
            case 0: return EntityStatus.INACTIVE;
            case 1: return EntityStatus.ACTIVE;
            case 2: return EntityStatus.CANCELED;
            case 3: return EntityStatus.LOCKED;
        }
        return null;
    }

    public void setStatus(EntityStatus status) {
        this.status = status.getValue();
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Notification that = (Notification) o;
        return !(this.id != null ? !this.id.equals(that.getId())
            : that.getId() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "Notification: " + this.id + ", " + this.code + ", " + this.description;
    }

}

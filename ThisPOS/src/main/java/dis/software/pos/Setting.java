/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import com.google.gson.annotations.SerializedName;
import dis.software.pos.entities.User;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Milton Cavazos
 */
public class Setting implements Serializable
{
    
    public static final String FILE_NAME = "\\app_setting.json";

    @SerializedName(value = "store_code")
    private String storeCode;
    
    @SerializedName(value = "store_name")
    private String storeName;
    
    @SerializedName(value = "store_description")
    private String storeDescription;
    
    private Double tax;
    
    @SerializedName(value = "out_of_time")
    private Date outOfTime;
    
    private Boolean deleted = Boolean.FALSE;
    
    @SerializedName(value = "updated_by")
    private User updatedBy;
    
    @SerializedName(value = "updated_date")
    private Calendar updatedDate;

    public Setting() {}
    
    /**
     * Método constructor de la clase
     * @param storeName Nombre del negocio
     * @param storeDescription Descripción del negocio
     */
    public Setting(String storeName, String storeDescription)
    {
        this.storeName = storeName;
        this.storeDescription = storeDescription;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreDescription() {
        return storeDescription;
    }

    public void setStoreDescription(String storeDescription) {
        this.storeDescription = storeDescription;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Date getOutOfTime() {
        return outOfTime;
    }

    public void setOutOfTime(Date outOfTime) {
        this.outOfTime = outOfTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
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
        
        Setting that = (Setting) o;
        return !(this.storeCode != null ? !this.storeCode.equals(that.getStoreCode())
            : that.getStoreCode() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.storeCode);
        return hash;
    }

    @Override
    public String toString() {
        return "Setting: " + this.storeCode + ", " + this.storeName + ", " + this.storeDescription;
    }

}

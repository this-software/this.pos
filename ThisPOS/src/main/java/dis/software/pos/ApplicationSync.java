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
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 *
 * @author Milton Cavazos
 */
public class ApplicationSync implements Serializable
{
    
    public static final String FILE_NAME = "\\app_sync.json";
    
    public static final String INVENTORY = "SYNINV"; //Sincronizar inventario
    public static final String PRODUCT = "SYNPRO"; //Sincronizar productos
    public static final String PROMOTION = "SYNPRM"; //Sincronizar promociones
    public static final String SALE = "SYNSAL"; //Sincronizar ventas
    public static final String PURCHASE = "SYNPUR"; //Sincronizar compras
    
    private String code;
    
    private String link;
    
    @SerializedName(value = "updated_by")
    private User updatedBy;
    
    @SerializedName(value = "updated_date")
    private Calendar updatedDate = new GregorianCalendar();
    
    public ApplicationSync() {}
    
    /**
     * Método constructor de la clase
     * @param code Código de sincronización
     * @param link Enlace de sincronización
     */
    public ApplicationSync(String code, String link)
    {
        this.code = code;
        this.link = link;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
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
        
        ApplicationSync that = (ApplicationSync) o;
        return !(this.code != null ? !this.code.equals(that.getCode())
            : that.getCode() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.code);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ApplicationSync: " + this.code + ", " + this.link;
    }
    
}

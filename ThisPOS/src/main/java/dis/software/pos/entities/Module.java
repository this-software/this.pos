/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
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
 * Clase que contiene los campos de módulo
 * @author Milton Cavazos
 */
@Entity
public class Module implements Serializable
{
    
    public static final String USERS = "MOD0001";
    public static final String PROFILES = "MOD0002";
    public static final String INVENTORY = "MOD0003";
    public static final String PURCHASES = "MOD0004";
    public static final String SALES = "MOD0005";
    public static final String CATEGORIES = "MOD0006";
    public static final String PROVIDERS = "MOD0007";
    public static final String PRODUCTS = "MOD0008";
    public static final String COSTS = "MOD0009";
    public static final String UNITS = "MOD0010";
    public static final String NOTIFICATIONS = "MOD0011";
    public static final String SYNCH = "MOD0012";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, length = 50)
    private String code;
    
    @Column(unique = true, length = 50)
    private String name;
    
    @Column
    private String description;
    
    @Column
    private Integer deleted;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "created_date")
    @Temporal(value = TemporalType.DATE)
    private Calendar createdDate = new GregorianCalendar();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    
    @Column(name = "updated_date")
    @Temporal(value = TemporalType.DATE)
    private Calendar updatedDate;
    
    @OneToMany(mappedBy = "profileModulePk.module", fetch = FetchType.LAZY)
    private Set<ProfileModule> profileModules = new HashSet<>();

    public Module() {}
    
    public Module(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param code Código del módulo
     * @param name Nombre del módulo
     * @param description Descripción del módulo
     */
    public Module(String code, String name, String description)
    {
        this.code = code;
        this.name = name;
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer isDeleted() {
        return deleted;
    }

    public void isDeleted(Integer deleted) {
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
    
    public Set<ProfileModule> getProfileModules() {
        return profileModules;
    }

    public void setProfileModules(Set<ProfileModule> profileModules) {
        this.profileModules = profileModules;
    }
    
    @Override
    public String toString() {
        return "Module: " + this.id + ", " + this.code + ", " + this.name + ", " + this.description;
    }
    
}

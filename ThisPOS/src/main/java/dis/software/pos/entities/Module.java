/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que contiene los campos de módulo
 * @author Milton Cavazos
 */
@Entity
public class Module implements Serializable
{
    
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
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "created_date")
    @Temporal(value = TemporalType.DATE)
    private Date createdDate = new Date();
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    @Column(name = "updated_date")
    @Temporal(value = TemporalType.DATE)
    private Date updatedDate;
    
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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
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
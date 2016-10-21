/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que contiene los campos de perfil
 * @author Milton Cavazos
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable
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
    
    /*@OneToMany
    @JoinTable(name = "user_profile",
        joinColumns = @JoinColumn(name = "id", referencedColumnName = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "id", referencedColumnName = "user_id"))
    private Set<User> users = new HashSet<>();/**/
    
    @OneToMany(mappedBy = "profileModulePk.profile", fetch = FetchType.LAZY)
    private Set<ProfileModule> profileModules = new HashSet<>();

    public Profile() {}
    
    public Profile(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param code Código del perfil
     * @param name Nombre del perfil
     * @param description Descripción del perfil
     */
    public Profile(String code, String name, String description)
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
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
        return "Profile: " + this.id + ", " + this.code + ", " + this.name + ", " + this.description;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author Milton Cavazos
 */
@Entity
@Table(name = "user")
public class User implements Serializable
{
    
    public static final Integer ACTIVE = 1;
    public static final Integer INACTIVE = 0;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column
    private String code;
    
    @Column
    private String name;
    
    @Column
    private String email;
    
    @Column
    private String password;
    
    @Column
    private String salt;
    
    @Column
    private Integer active;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "created_date")
    @Temporal(value = TemporalType.DATE)
    private Date createdDate;
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    @Column(name = "updated_date")
    @Temporal(value = TemporalType.DATE)
    private Date updatedDate;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
    private Profile profile;

    public User() {}
    
    /**
     * Método constructor de la clase
     * @param email Correo del usuario
     * @param userName Nombre de usuario
     * @param password Contraseña del usuario
     */
    public User(String email, String userName, String password)
    {
        this.email = email;
        this.name = userName;
        this.password = password;
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
    
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    
    public Boolean isActive() {
        return (active == 1);
    }
    
    public void isActive(Integer active) {
        this.active = active;
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

    
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    
    @Override
    public String toString() {
        return "User: " + this.id + ", " + this.name + ", " + this.email;
    }
    
}

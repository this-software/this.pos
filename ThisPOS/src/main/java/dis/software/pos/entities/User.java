/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    private Integer status;
    
    @Column
    private Boolean deleted;
    
    @Column(name = "receive_notifications")
    private Boolean receiveNotifications;
    
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
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_profile",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
    private Profile profile;

    public User() {}
    
    public User(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param email Correo del usuario
     * @param name Nombre de usuario
     * @param password Contraseña del usuario
     */
    public User(String email, String name, String password)
    {
        this.email = email;
        this.name = name;
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

    public Boolean getReceiveNotifications() {
        return receiveNotifications;
    }

    public void setReceiveNotifications(Boolean receiveNotifications) {
        this.receiveNotifications = receiveNotifications;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User that = (User) o;
        return !(this.id != null ? !this.id.equals(that.getId())
            : that.getId() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public String toString() {
        return "User: " + this.id + ", " + this.name + ", " + this.email;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Clase que contiene la relación para cada combinación de Perfil, Modulo y Privilegios
 * @author Milton Cavazos
 */
@Entity
@Table(name = "profile_module")
@AssociationOverrides({
    @AssociationOverride(name = "profileModulePk.profile", joinColumns = @JoinColumn(name = "profile_id")),
    @AssociationOverride(name = "profileModulePk.module", joinColumns = @JoinColumn(name = "module_id"))
})
@SuppressWarnings({"IdDefinedInHierarchy", "ConsistentAccessType"})
public class ProfileModule implements Serializable
{
    
    @EmbeddedId
    private ProfileModulePk profileModulePk = new ProfileModulePk();
    
    @Embedded
    private Privileges privileges;
    
    public ProfileModule() {}
    
    public ProfileModulePk getProfileModulePk() {
        return profileModulePk;
    }

    public void setProfileModulePk(ProfileModulePk profileModulePk) {
        this.profileModulePk = profileModulePk;
    }
    
    @Transient
    public Profile getProfile()
    {
        return getProfileModulePk().getProfile();
    }
    
    public void setProfile(Profile profile)
    {
        getProfileModulePk().setProfile(profile);
    }
    
    @Transient
    public Module getModule()
    {
        return getProfileModulePk().getModule();
    }
    
    public void setModule(Module module)
    {
        getProfileModulePk().setModule(module);
    }
    
    public Privileges getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Privileges privileges) {
        this.privileges = privileges;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ProfileModule that = (ProfileModule) o;
        return !(getProfileModulePk() != null ? !getProfileModulePk().equals(that.getProfileModulePk())
            : that.getProfileModulePk() != null);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.profileModulePk);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ProfileModule: " + this.profileModulePk;
    }
    
}

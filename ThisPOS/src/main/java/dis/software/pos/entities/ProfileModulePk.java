/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 * Clase que contiene los campos de identidad para cada combinación de Perfil, Modulo y Privilegios
 * @author Milton Cavazos
 */
@Embeddable
public class ProfileModulePk implements Serializable
{
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Module module;
    
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ProfileModulePk that = (ProfileModulePk) o;
        if (profile != null ? !profile.equals(that.profile) : that.profile != null) return false;
        return !(module != null ? !module.equals(that.module) : that.module != null);
    }
    
    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.profile);
        hash = 59 * hash + Objects.hashCode(this.module);
        return hash;
    }
    
    @Override
    public String toString() {
        return "ProfileModulePk: " + this.profile + ", " + this.module;
    }
    
}

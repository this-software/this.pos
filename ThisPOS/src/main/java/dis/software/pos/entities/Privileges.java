/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.entities;

import dis.software.pos.Property;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Clase que contiene los campos de privilegio para cada módulo
 * @author Milton Cavazos
 */
@Embeddable
public class Privileges implements Serializable
{
    
    @Column(name = "[view]", nullable = true)
    private Integer view;
    
    @Column(name = "[create]", nullable = true)
    private Integer create;
    
    @Column(name = "[edit]", nullable = true)
    private Integer edit;
    
    @Column(name = "[delete]", nullable = true)
    private Integer delete;
    
    public Privileges() {}
    
    /**
     * Método constructor de la clase
     * @param view
     * @param create
     * @param edit
     * @param delete 
     */
    public Privileges(Property view, Property create, Property edit, Property delete)
    {
        this.view = (view == Property.ALLOW ? 1 : 0);
        this.create = (create == Property.ALLOW ? 1 : 0);
        this.edit = (edit == Property.ALLOW ? 1 : 0);
        this.delete = (delete == Property.ALLOW ? 1 : 0);
    }
    
    
    public Boolean getViewProperty() {
        return view!=0;
    }
    
    public void setViewProperty(Property property) {
        this.view = (property == Property.ALLOW ? 1 : 0);
    }
    
    
    public Boolean getCreateProperty() {
        return create!=0;
    }
    
    public void setCreateProperty(Property property) {
        this.create = (property == Property.ALLOW ? 1 : 0);
    }
    
    
    public Boolean getEditProperty() {
        return edit!=0;
    }
    
    public void setEditProperty(Property property) {
        this.edit = (property == Property.ALLOW ? 1 : 0);
    }
    
    
    public Boolean getDeleteProperty() {
        return delete!=0;
    }
    
    public void setDeleteProperty(Property property) {
        this.delete = (property == Property.ALLOW ? 1 : 0);
    }
    
    
    @Override
    public String toString() {
        return "Privileges: " + this.view + ", " + this.create
            + ", " + this.edit + ", " + this.delete;
    }
    
}

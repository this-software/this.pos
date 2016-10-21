package dis.software.pos.table.model;

import dis.software.pos.Property;
import dis.software.pos.entities.ProfileModule;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase modelo constructora de una tabla para privilegios de un módulo por perfil
 * @author Milton Cavazos
 */
public class ProfileModuleTableModel extends AbstractTableModel
{
    
    private final List<ProfileModule> list;
    
    private final String[] columnNames = new String[] {
        "Id","Módulo", "Descripción", "Ver", "Crear", "Modificar", "Eliminar"
    };
    private final Class[] columnClass = new Class[] {
        Long.class, String.class, String.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, true, true, true, true
    };
    
    public ProfileModuleTableModel(List<ProfileModule> list)
    {
        this.list = list;
    }
    
    @Override
    public String getColumnName(int columnIndex)
    {
        return columnNames[columnIndex];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

    @Override
    public int getRowCount()
    {
        return list.size();
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        ProfileModule row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getModule().getId();
            case 1: return row.getModule().getName();
            case 2: return row.getModule().getDescription();
            case 3: return row.getPrivileges().getViewProperty();
            case 4: return row.getPrivileges().getCreateProperty();
            case 5: return row.getPrivileges().getEditProperty();
            case 6: return row.getPrivileges().getDeleteProperty();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        ProfileModule row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.getModule().setId((Long) aValue); break;
            case 1: row.getModule().setName((String) aValue); break;
            case 2: row.getModule().setDescription((String) aValue); break;
            case 3: row.getPrivileges().setViewProperty(
                (Boolean) aValue ? Property.ALLOW : Property.DENY); break;
            case 4: row.getPrivileges().setCreateProperty(
                (Boolean) aValue ? Property.ALLOW : Property.DENY); break;
            case 5: row.getPrivileges().setEditProperty(
                (Boolean) aValue ? Property.ALLOW : Property.DENY); break;
            case 6: row.getPrivileges().setDeleteProperty(
                (Boolean) aValue ? Property.ALLOW : Property.DENY); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

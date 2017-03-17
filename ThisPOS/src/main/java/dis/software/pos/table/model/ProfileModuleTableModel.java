package dis.software.pos.table.model;

import dis.software.pos.Property;
import dis.software.pos.entities.ProfileModule;
import dis.software.pos.entities.ProfileModulePk;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para privilegios de un módulo por perfil
 * @author Milton Cavazos
 */
public class ProfileModuleTableModel extends GenericTableModel<ProfileModule, ProfileModulePk>
{
    
    public static final int COLUMN_MOD_ID = 0;
    public static final int COLUMN_MOD_NAME = 1;
    public static final int COLUMN_MOD_DESC = 2;
    public static final int COLUMN_PRI_VIEW = 3;
    public static final int COLUMN_PRI_CREATE = 4;
    public static final int COLUMN_PRI_EDIT = 5;
    public static final int COLUMN_PRI_DELETE = 6;
    
    private final String[] columnNames = new String[] {
        "Id módulo", "Módulo", "Descripción", "Ver", "Crear", "Modificar", "Eliminar"
    };
    private final Class[] columnClass = new Class[] {
        Long.class, String.class, String.class, Boolean.class, Boolean.class, Boolean.class, Boolean.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, true, true, true, true
    };
    
    public ProfileModuleTableModel()
    {
        super();
    }
    
    public ProfileModuleTableModel(List<ProfileModule> list)
    {
        super(list);
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
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return canEdit[columnIndex];
    }
    
    @Override
    public void setColumnEditable(int columnIndex, boolean canEdit)
    {
        this.canEdit[columnIndex] = canEdit;
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
            case 3: return row.getPrivileges().getViewProperty() == Property.ALLOW;
            case 4: return row.getPrivileges().getCreateProperty() == Property.ALLOW;
            case 5: return row.getPrivileges().getEditProperty() == Property.ALLOW;
            case 6: return row.getPrivileges().getDeleteProperty() == Property.ALLOW;
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

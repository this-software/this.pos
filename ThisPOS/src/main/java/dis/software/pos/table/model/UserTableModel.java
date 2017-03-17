package dis.software.pos.table.model;

import dis.software.pos.EntityStatus;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.User;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para usuarios
 * @author Milton Cavazos
 */
public class UserTableModel extends GenericTableModel<User, Long>
{
    
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CODE = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_EMAIL = 3;
    public static final int COLUMN_PROFILE_NAME = 4;
    public static final int COLUMN_STATUS = 5;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Usuario", "Correo", "Perfil", "Estado"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, String.class, String.class, String.class, EntityStatus.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };
    
    public UserTableModel()
    {
        super();
    }
    
    public UserTableModel(List<User> list)
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
        User row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getId();
            case 1: return row.getCode();
            case 2: return row.getName();
            case 3: return row.getEmail();
            case 4: return row.getProfile().getName();
            case 5: return row.getStatus();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        User row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.setId((Long) aValue); break;
            case 1: row.setCode((String) aValue); break;
            case 2: row.setName((String) aValue); break;
            case 3: row.setEmail((String) aValue); break;
            case 4: row.setProfile((Profile) aValue); break;
            case 5: row.setStatus((EntityStatus) aValue);
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

package dis.software.pos.table.model;

import dis.software.pos.EntityStatus;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.User;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase modelo constructora de una tabla para usuarios
 * @author Milton Cavazos
 */
public class UserTableModel extends AbstractTableModel
{
    
    private final List<User> list;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Usuario", "Correo", "Perfil", "Estado"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, String.class, String.class, String.class, EntityStatus.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };
    
    public UserTableModel(List<User> list)
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
        User row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getId();
            case 1: return row.getCode();
            case 2: return row.getName();
            case 3: return row.getEmail();
            case 4: return row.getProfile().getName();
            case 5: return row.isActive() ? EntityStatus.ACTIVE : EntityStatus.INACTIVE;
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
            case 5: row.isActive((Integer) aValue);
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.Provider;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para proveedores
 * @author Milton Cavazos
 */
public class ProviderTableModel extends GenericTableModel<Provider, Long>
{
    
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CODE = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_DESC = 3;
    public static final int COLUMN_ADDRESS = 4;
    public static final int COLUMN_PHONE = 5;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Nombre", "Descripción", "Dirección", "Teléfono"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, String.class, String.class, String.class, String.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };
    
    public ProviderTableModel()
    {
        super();
    }
    
    public ProviderTableModel(List<Provider> list)
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
        Provider row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getId();
            case 1: return row.getCode();
            case 2: return row.getName();
            case 3: return row.getDescription();
            case 4: return row.getAddress();
            case 5: return row.getPhone();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Provider row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.setId((Long) aValue); break;
            case 1: row.setCode((String) aValue); break;
            case 2: row.setName((String) aValue); break;
            case 3: row.setDescription((String) aValue); break;
            case 4: row.setAddress((String) aValue); break;
            case 5: row.setPhone((String) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

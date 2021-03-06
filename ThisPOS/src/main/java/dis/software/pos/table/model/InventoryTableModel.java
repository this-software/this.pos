/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.InventoryType;
import dis.software.pos.entities.Inventory;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public class InventoryTableModel extends GenericTableModel<Inventory, Long>
{
    
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CODE = 1;
    public static final int COLUMN_TYPE = 2;
    public static final int COLUMN_CREATED_DATE = 3;
    public static final int COLUMN_PROD_QTY = 4;
    public static final int COLUMN_CREATED_BY = 5;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Tipo de mov.", "Fecha de mov.", "Cant. de productos", "Generado por"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, InventoryType.class, Calendar.class, Integer.class, String.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };
    
    public InventoryTableModel()
    {
        super();
    }
    
    public InventoryTableModel(List<Inventory> list)
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
        Inventory row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_ID: return row.getId();
            case COLUMN_CODE: return row.getCode();
            case COLUMN_TYPE: return row.getType();
            case COLUMN_CREATED_DATE: return row.getCreatedDate();
            case COLUMN_PROD_QTY: return row.getInventoryProducts().size();
            case COLUMN_CREATED_BY: return row.getCreatedBy().getName();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Inventory row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_ID: row.setId((Long) aValue); break;
            case COLUMN_CODE: row.setCode((String) aValue); break;
            case COLUMN_TYPE: row.setType((InventoryType) aValue); break;
            case COLUMN_CREATED_DATE: row.setCreatedDate((Calendar) aValue); break;
            case COLUMN_CREATED_BY: row.getCreatedBy().setName((String) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

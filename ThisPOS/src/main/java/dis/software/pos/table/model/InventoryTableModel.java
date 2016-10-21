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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milton Cavazos
 */
public class InventoryTableModel extends AbstractTableModel
{
    
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CODE = 1;
    public static final int COLUMN_TYPE = 2;
    public static final int COLUMN_DATE = 3;
    public static final int COLUMN_PROD_QTY = 4;
    
    private final List<Inventory> list;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Tipo de mov.", "Fecha de mov.", "Cant. de productos"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, InventoryType.class, Calendar.class, Integer.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false
    };
    
    public InventoryTableModel()
    {
        this.list = new ArrayList<>();
    }
    
    public InventoryTableModel(List<Inventory> list)
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
        Inventory row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_ID: return row.getId();
            case COLUMN_CODE: return row.getCode();
            case COLUMN_TYPE: return row.getType() == 0
                ? InventoryType.INCOME
                : row.getType() == 1 ? InventoryType.OUTCOME : InventoryType.CANCEL;
            case COLUMN_DATE: return row.getCreatedDate();
            case COLUMN_PROD_QTY: return row.getInventoryProducts().size();
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
            case COLUMN_DATE: row.setCreatedDate((Calendar) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public void add(Inventory inventory)
    {
        int rowIndex = list.size();
        if (!list.contains(inventory))
        {
            list.add(inventory);
            this.fireTableRowsInserted(rowIndex, rowIndex);
        }
    }
    
    public void remove(int aRowIndex)
    {
        if (aRowIndex < 0 || aRowIndex >= list.size())
        {
            return;
        }
        list.remove(aRowIndex);
        this.fireTableRowsDeleted(aRowIndex, aRowIndex);
    }
    
}

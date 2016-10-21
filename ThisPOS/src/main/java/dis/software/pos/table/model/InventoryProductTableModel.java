/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.InventoryType;
import dis.software.pos.entities.InventoryProduct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase modelo constructora de una tabla para la relación Inventario - Productos
 * @author Milton Cavazos
 */
public class InventoryProductTableModel extends AbstractTableModel
{
    
    public static final int COLUMN_PROD_ID = 0;
    public static final int COLUMN_INV_TYPE = 1;
    public static final int COLUMN_INV_DATE = 2;
    public static final int COLUMN_PROD_CODE = 3;
    public static final int COLUMN_PROD_NAME = 4;
    public static final int COLUMN_PROD_DESC = 5;
    public static final int COLUMN_UNITS = 6;

    private final List<InventoryProduct> list;
    
    private final String[] columnNames = new String[] {
        "Id prod.", "Tipo de mov.", "Fecha de mov.", "Código prod.",
        "Nombre prod.", "Descripción prod.", "Cantidad"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, InventoryType.class, Calendar.class, String.class,
        String.class, String.class, Integer.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false
    };
    
    public InventoryProductTableModel()
    {
        this.list = new ArrayList<>();
    }
    
    public InventoryProductTableModel(List<InventoryProduct> list)
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
        InventoryProduct row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_PROD_ID: return row.getProduct().getId();
            case COLUMN_INV_TYPE: return row.getInventory().getType() == 0
                ? InventoryType.INCOME
                : row.getInventory().getType() == 1 ? InventoryType.OUTCOME : InventoryType.CANCEL;
            case COLUMN_INV_DATE: return row.getInventory().getCreatedDate();
            case COLUMN_PROD_CODE: return row.getProduct().getCode();
            case COLUMN_PROD_NAME: return row.getProduct().getName();
            case COLUMN_PROD_DESC: return row.getProduct().getDescription();
            case COLUMN_UNITS: return row.getUnits();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        InventoryProduct row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_PROD_ID: row.getProduct().setId((Long) aValue); break;
            case COLUMN_INV_TYPE: row.getInventory().setType((InventoryType) aValue); break;
            case COLUMN_INV_DATE: row.getInventory().setCreatedDate((Calendar) aValue); break;
            case COLUMN_PROD_CODE: row.getProduct().setCode((String) aValue); break;
            case COLUMN_PROD_NAME: row.getProduct().setName((String) aValue); break;
            case COLUMN_PROD_DESC: row.getProduct().setDescription((String) aValue); break;
            case COLUMN_UNITS: row.setUnits((Integer) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public void add(InventoryProduct inventoryProduct)
    {
        int rowIndex = list.size();
        if (!list.contains(inventoryProduct))
        {
            list.add(inventoryProduct);
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
    
    public void removeAll()
    {
        if (list.size() <= 0)
        {
            return;
        }
        int lastRow = list.size();
        for (int i = 0; i<=list.size(); i++)
        {
            list.remove(i);
        }
        this.fireTableRowsDeleted(0, lastRow);
    }
    
}

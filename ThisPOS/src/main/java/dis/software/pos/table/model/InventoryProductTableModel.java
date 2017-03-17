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
import dis.software.pos.entities.InventoryProductPk;
import java.util.Calendar;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para la relación Inventario - Productos
 * @author Milton Cavazos
 */
public class InventoryProductTableModel extends GenericTableModel<InventoryProduct, InventoryProductPk>
{
    
    public static final int COLUMN_PROD_ID = 0;
    public static final int COLUMN_UNIT_ID = 1;
    public static final int COLUMN_INV_TYPE = 2;
    public static final int COLUMN_INV_DATE = 3;
    public static final int COLUMN_PROD_CODE = 4;
    public static final int COLUMN_PROD_NAME = 5;
    public static final int COLUMN_PROD_DESC = 6;
    public static final int COLUMN_QUANTITY = 7;
    public static final int COLUMN_UNIT_CODE = 8;
    public static final int COLUMN_REAL_QTY = 9;
    
    private final String[] columnNames = new String[] {
        "Id prod.", "Id unidad", "Tipo de mov.", "Fecha de mov.", "Código prod.", "Nombre prod.",
        "Descripción prod.", "Cantidad", "Unidad", "Cantidad real"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, Integer.class, InventoryType.class, Calendar.class, String.class, String.class,
        String.class, Integer.class, String.class, Integer.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, true
    };
    
    public InventoryProductTableModel()
    {
        super();
    }
    
    public InventoryProductTableModel(List<InventoryProduct> list)
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
        InventoryProduct row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_PROD_ID: return row.getProduct().getId();
            case COLUMN_UNIT_ID: return row.getUnit().getId();
            case COLUMN_INV_TYPE: return row.getInventory().getType();
            case COLUMN_INV_DATE: return row.getInventory().getCreatedDate();
            case COLUMN_PROD_CODE: return row.getProduct().getCode();
            case COLUMN_PROD_NAME: return row.getProduct().getName();
            case COLUMN_PROD_DESC: return row.getProduct().getDescription();
            case COLUMN_QUANTITY: return row.getUnits();
            case COLUMN_UNIT_CODE: return row.getUnit().getName();
            case COLUMN_REAL_QTY: return row.getRealUnits();
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
            case COLUMN_UNIT_ID: row.getUnit().setId((Long) aValue); break;
            case COLUMN_INV_TYPE: row.getInventory().setType((InventoryType) aValue); break;
            case COLUMN_INV_DATE: row.getInventory().setCreatedDate((Calendar) aValue); break;
            case COLUMN_PROD_CODE: row.getProduct().setCode((String) aValue); break;
            case COLUMN_PROD_NAME: row.getProduct().setName((String) aValue); break;
            case COLUMN_PROD_DESC: row.getProduct().setDescription((String) aValue); break;
            case COLUMN_QUANTITY: row.setUnits((Integer) aValue); break;
            case COLUMN_UNIT_CODE: row.getUnit().setName((String) aValue); break;
            case COLUMN_REAL_QTY: row.setRealUnits((Integer) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

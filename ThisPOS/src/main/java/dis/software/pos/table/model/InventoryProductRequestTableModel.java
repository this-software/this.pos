/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.InventoryProductRequest;
import dis.software.pos.entities.InventoryProductRequestPk;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public class InventoryProductRequestTableModel
    extends GenericTableModel<InventoryProductRequest, InventoryProductRequestPk>
{
    
    public static final int COLUMN_ACTION = 0;
    public static final int COLUMN_INV_CODE = 1;
    public static final int COLUMN_PROD_CODE = 2;
    public static final int COLUMN_PROD_NAME = 3;
    public static final int COLUMN_REQ_UNITS = 4;
    public static final int COLUMN_UNIT_NAME = 5;
    public static final int COLUMN_CREATED_BY = 6;
    public static final int COLUMN_CREATED_DATE = 7;
    
    private final String[] columnNames = new String[] {
        "Acción", "Código inv.", "Código prod.", "Nombre prod.", "Cantidad", "Unidad",
        "Responsable", "Fecha"
    };
    private final Class[] columnClass = new Class[] {
        String.class, String.class, String.class, String.class, Integer.class, String.class,
        String.class, Calendar.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
    };
    
    public InventoryProductRequestTableModel()
    {
        super();
    }
    
    public InventoryProductRequestTableModel(List<InventoryProductRequest> list)
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
        InventoryProductRequest row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getRequiredUnits() < 0 ? "COMPRA" : "VENTA";
            case 1: return row.getInventory().getCode();
            case 2: return row.getProduct().getCode();
            case 3: return row.getProduct().getName();
            case 4: return Math.abs(row.getRequiredUnits());
            case 5: return row.getUnit().getName();
            case 6: return row.getCreatedBy().getName();
            case 7: return row.getCreatedDate();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        InventoryProductRequest row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: break;
            case 1: row.getInventory().setCode((String) aValue); break;
            case 2: row.getProduct().setCode((String) aValue); break;
            case 3: row.getProduct().setName((String) aValue); break;
            case 4: row.setRequiredUnits((Integer) aValue); break;
            case 5: row.getUnit().setName((String) aValue); break;
            case 6: row.getCreatedBy().setName((String) aValue); break;
            case 7: row.setCreatedDate((Calendar) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

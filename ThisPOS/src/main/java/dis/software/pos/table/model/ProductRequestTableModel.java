/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.ProductRequest;
import dis.software.pos.entities.ProductRequestPk;
import java.util.Calendar;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para solicitudes de producto
 * @author Milton Cavazos
 */
public class ProductRequestTableModel extends GenericTableModel<ProductRequest, ProductRequestPk>
{
    
    public static final int COLUMN_PROD_CODE = 0;
    public static final int COLUMN_PROD_NAME = 1;
    public static final int COLUMN_REQ_UNITS = 2;
    public static final int COLUMN_UNIT_NAME = 3;
    public static final int COLUMN_CREATED_BY = 4;
    public static final int COLUMN_CREATED_DATE = 5;
    
    private final String[] columnNames = new String[] {
        "Código prod.", "Nombre prod.", "Cantidad", "Unidad", "Solicitó", "Fecha"
    };
    private final Class[] columnClass = new Class[] {
        String.class, String.class, Integer.class, String.class, String.class, Calendar.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };

    public ProductRequestTableModel()
    {
        super();
    }
    
    public ProductRequestTableModel(List<ProductRequest> list)
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
        ProductRequest row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getProduct().getCode();
            case 1: return row.getProduct().getName();
            case 2: return row.getRequiredUnits();
            case 3: return row.getUnit().getName();
            case 4: return row.getCreatedBy().getName();
            case 5: return row.getCreatedDate();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        ProductRequest row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.getProduct().setCode((String) aValue); break;
            case 1: row.getProduct().setName((String) aValue); break;
            case 2: row.setRequiredUnits((Integer) aValue); break;
            case 3: row.getUnit().setName((String) aValue); break;
            case 4: row.getCreatedBy().setName((String) aValue); break;
            case 5: row.setCreatedDate((Calendar) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

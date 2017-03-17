/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.PurchaseDetail;
import dis.software.pos.entities.PurchaseDetailPk;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para los detalles de Compra
 * @author Milton Cavazos
 */
public class PurchaseDetailTableModel extends GenericTableModel<PurchaseDetail, PurchaseDetailPk>
{
    
    public static final int COLUMN_PROD_CODE = 0;
    public static final int COLUMN_PROD_DESC = 1;
    public static final int COLUMN_QUANTITY = 2;
    public static final int COLUMN_UNIT_NAME = 3;
    public static final int COLUMN_COST = 4;
    public static final int COLUMN_TOTAL_COST = 5;
    
    private final String[] columnNames = new String[] {
        "Código prod.", "Desc. producto", "Cantidad", "Unidad", "Costo unit.", "Costo total"
    };
    private final Class[] columnClass = new Class[] {
        String.class, String.class, Integer.class, String.class, Double.class, Double.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false
    };
    
    public PurchaseDetailTableModel()
    {
        super();
    }
    
    public PurchaseDetailTableModel(List<PurchaseDetail> list)
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
        PurchaseDetail row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_PROD_CODE: return row.getProduct().getCode();
            case COLUMN_PROD_DESC: return row.getProduct().getDescription();
            case COLUMN_QUANTITY: return row.getQuantity();
            case COLUMN_UNIT_NAME: return row.getUnit().getName();
            case COLUMN_COST: return row.getCost();
            case COLUMN_TOTAL_COST: return row.getQuantity() * row.getCost();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        PurchaseDetail row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_PROD_CODE: row.getProduct().setCode((String) aValue); break;
            case COLUMN_PROD_DESC: row.getProduct().setDescription((String) aValue); break;
            case COLUMN_QUANTITY: row.setQuantity((Integer) aValue); break;
            case COLUMN_UNIT_NAME: row.getUnit().setName((String) aValue); break;
            case COLUMN_COST: row.setCost((Double) aValue); break;
            case COLUMN_TOTAL_COST: row.setTotalCost((Double) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

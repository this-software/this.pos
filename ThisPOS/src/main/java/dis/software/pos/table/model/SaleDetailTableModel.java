/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.SaleDetailPk;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para los detalles de Venta
 * @author Milton Cavazos
 */
public class SaleDetailTableModel extends GenericTableModel<SaleDetail, SaleDetailPk>
{
    
    public static final int COLUMN_SALE_ID = 0;
    public static final int COLUMN_PROD_ID = 1;
    public static final int COLUMN_PROD_CODE = 2;
    public static final int COLUMN_PROD_DESC = 3;
    public static final int COLUMN_PRICE = 4;
    public static final int COLUMN_DISCOUNT = 5;
    public static final int COLUMN_TOTAL_DISCOUNT = 6;
    public static final int COLUMN_AMOUNT = 7;
    public static final int COLUMN_COST = 8;
    public static final int COLUMN_TOTAL_COST = 9;
    public static final int COLUMN_QUANTITY = 10;
    
    private final String[] columnNames = new String[] {
        "Id venta", "Id prod.", "Código prod.", "Desc. producto", "Precio unit.",
        "Descuento", "Descuento total", "Importe", "Costo unit.", "Costo total", "Cantidad"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, Integer.class, String.class, String.class, Double.class,
        Double.class, Double.class, Double.class, Double.class, Double.class, Integer.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, false, false
    };
    
    public SaleDetailTableModel()
    {
        super();
    }
    
    public SaleDetailTableModel(List<SaleDetail> list)
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
        SaleDetail row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_SALE_ID: return row.getSale().getId();
            case COLUMN_PROD_ID: return row.getProduct().getId();
            case COLUMN_PROD_CODE: return row.getProduct().getCode();
            case COLUMN_PROD_DESC: return row.getProduct().getDescription();
            case COLUMN_PRICE: return row.getPrice();
            case COLUMN_DISCOUNT: return row.getDiscount();
            case COLUMN_TOTAL_DISCOUNT: return row.getTotalDiscount();
            case COLUMN_AMOUNT: return row.getAmount();
            case COLUMN_COST: return row.getCost();
            case COLUMN_TOTAL_COST: return row.getTotalCost();
            case COLUMN_QUANTITY: return row.getQuantity();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        SaleDetail row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_SALE_ID: row.getSale().setId((Long) aValue); break;
            case COLUMN_PROD_ID: row.getProduct().setId((Long) aValue); break;
            case COLUMN_PROD_CODE: row.getProduct().setCode((String) aValue); break;
            case COLUMN_PROD_DESC: row.getProduct().setDescription((String) aValue); break;
            case COLUMN_PRICE: row.setPrice((Double) aValue); break;
            case COLUMN_DISCOUNT: row.setDiscount((Double) aValue); break;
            case COLUMN_TOTAL_DISCOUNT: row.setTotalDiscount((Double) aValue); break;
            case COLUMN_AMOUNT: row.setAmount((Double) aValue); break;
            case COLUMN_COST: row.setCost((Double) aValue); break;
            case COLUMN_TOTAL_COST: row.setTotalCost((Double) aValue); break;
            case COLUMN_QUANTITY: row.setQuantity((Integer) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.SalePromotionDetail;
import dis.software.pos.entities.SalePromotionDetailPk;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para los detalles de promociones vendidas
 * @author Milton Cavazos
 */
public class SalePromotionDetailTableModel extends GenericTableModel<SalePromotionDetail, SalePromotionDetailPk>
{
    
    public static final int COLUMN_SALE_ID = 0;
    public static final int COLUMN_PROM_ID = 1;
    public static final int COLUMN_PROM_CODE = 2;
    public static final int COLUMN_PROM_DESC = 3;
    public static final int COLUMN_PRICE = 4;
    public static final int COLUMN_AMOUNT = 5;
    public static final int COLUMN_COST = 6;
    public static final int COLUMN_TOTAL_COST = 7;
    public static final int COLUMN_QUANTITY = 8;
    
    private final String[] columnNames = new String[] {
        "Id venta", "Id prom.", "Código prom.", "Desc. promoción", "Precio unit.",
        "Importe", "Costo unit.", "Costo total", "Cantidad"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, Integer.class, String.class, String.class, Double.class,
        Double.class, Double.class, Double.class, Integer.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false
    };
    
    public SalePromotionDetailTableModel()
    {
        super();
    }
    
    public SalePromotionDetailTableModel(List<SalePromotionDetail> list)
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
        SalePromotionDetail row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_SALE_ID: return row.getSale().getId();
            case COLUMN_PROM_ID: return row.getPromotion().getId();
            case COLUMN_PROM_CODE: return row.getPromotion().getCode();
            case COLUMN_PROM_DESC: return row.getPromotion().getDescription();
            case COLUMN_PRICE: return row.getPrice();
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
        SalePromotionDetail row = list.get(rowIndex);
        switch(columnIndex)
        {
            case COLUMN_SALE_ID: row.getSale().setId((Long) aValue); break;
            case COLUMN_PROM_ID: row.getPromotion().setId((Long) aValue); break;
            case COLUMN_PROM_CODE: row.getPromotion().setCode((String) aValue); break;
            case COLUMN_PROM_DESC: row.getPromotion().setDescription((String) aValue); break;
            case COLUMN_PRICE: row.setPrice((Double) aValue); break;
            case COLUMN_AMOUNT: row.setAmount((Double) aValue); break;
            case COLUMN_COST: row.setCost((Double) aValue); break;
            case COLUMN_TOTAL_COST: row.setTotalCost((Double) aValue); break;
            case COLUMN_QUANTITY: row.setQuantity((Integer) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

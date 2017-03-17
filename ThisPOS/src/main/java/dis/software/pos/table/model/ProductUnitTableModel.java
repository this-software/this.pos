/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductUnit;
import dis.software.pos.entities.ProductUnitPk;
import dis.software.pos.entities.Unit;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public class ProductUnitTableModel extends GenericTableModel<ProductUnit, ProductUnitPk>
{
    
    public static final int COLUMN_PROD_ID = 0;
    public static final int COLUMN_UNIT_ID = 1;
    public static final int COLUMN_QTY_BY_UNIT = 2;
    public static final int COLUMN_UNIT_CODE = 3;
    public static final int COLUMN_COST_BY_UNIT = 4;
    public static final int COLUMN_PRICE_BY_UNIT = 5;
    public static final int COLUMN_SPECIAL_PRICE_BY_UNIT = 6;
    public static final int COLUMN_IS_DEFAULT = 7;
    
    private final String[] columnNames = new String[] {
        "Id prod.", "Id unidad", "Cantidad", "Código", "Costo", "Precio",
        "Precio especial", "Predeterminado"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, Integer.class, Integer.class, String.class, Double.class, Double.class,
        Double.class, Boolean.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, true, false, true, true, true, false
    };
    
    public ProductUnitTableModel()
    {
        super();
    }
    
    public ProductUnitTableModel(List<ProductUnit> list)
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
        ProductUnit row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getProduct().getId();
            case 1: return row.getUnit().getId();
            case 2: return row.getQuantityByUnit() != null ? row.getQuantityByUnit() : 0;
            case 3: return row.getUnit().getCode();
            case 4: return row.getCostByUnit();
            case 5: return row.getPriceByUnit();
            case 6: return row.getOutOfTimePriceByUnit();
            case 7: return row.getIsDefault();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        ProductUnit row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.setProduct((Product) aValue); break;
            case 1: row.setUnit((Unit) aValue); break;
            case 2: row.setQuantityByUnit((Integer) aValue); break;
            case 3: row.getUnit().setCode((String) aValue); break;
            case 4: row.setCostByUnit((Double) aValue); break;
            case 5: row.setPriceByUnit((Double) aValue); break;
            case 6: row.setOutOfTimePriceByUnit((Double) aValue); break;
            case 7: row.setIsDefault((Boolean) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

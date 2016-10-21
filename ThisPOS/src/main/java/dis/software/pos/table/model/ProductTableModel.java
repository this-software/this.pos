/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.Product;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase modelo constructora de una tabla para productos
 * @author Milton Cavazos
 */
public class ProductTableModel extends AbstractTableModel
{
    
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CODE = 1;
    public static final int COLUMN_NAME = 2;
    public static final int COLUMN_DESC = 3;
    public static final int COLUMN_PRICE = 4;
    public static final int COLUMN_COST = 5;
    public static final int COLUMN_CATEGORY = 6;
    public static final int COLUMN_PROVIDER = 7;
    public static final int COLUMN_UNITS_IN_STOCK = 8;

    private final List<Product> list;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Nombre", "Descripción", "Precio", "Costo", "Categoría", "Proveedor", "Cantidad disp."
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, String.class, String.class,
        Double.class, Double.class, String.class, String.class, Integer.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false
    };
    
    public ProductTableModel()
    {
        this.list = new ArrayList<>();
    }
    
    public ProductTableModel(List<Product> list)
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
        Product row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getId();
            case 1: return row.getCode();
            case 2: return row.getName();
            case 3: return row.getDescription();
            case 4: return row.getPrice();
            case 5: return row.getCost();
            case 6: return row.getCategory().getName();
            case 7: return row.getProvider().getName();
            case 8: return row.getUnitsInStock();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Product row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.setId((Long) aValue); break;
            case 1: row.setCode((String) aValue); break;
            case 2: row.setName((String) aValue); break;
            case 3: row.setDescription((String) aValue); break;
            case 4: row.setPrice((Double) aValue); break;
            case 5: row.setCost((Double) aValue); break;
            case 6: row.getCategory().setName((String) aValue); break;
            case 7: row.getProvider().setName((String) aValue); break;
            case 8: row.setUnitsInStock((Integer) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public void add(Product product)
    {
        int rowIndex = list.size();
        if (!list.contains(product))
        {
            list.add(product);
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

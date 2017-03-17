/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.Sale;
import dis.software.pos.entities.User;
import java.util.Calendar;
import java.util.List;

/**
 * Clase modelo constructora de una tabla para ventas
 * @author Milton Cavazos
 */
public class SaleTableModel extends GenericTableModel<Sale, Long>
{
    
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_CREATED_DATE = 1;
    public static final int COLUMN_SUBTOTAL = 2;
    public static final int COLUMN_TAX = 3;
    public static final int COLUMN_TOTAL_AMOUNT = 4;
    public static final int COLUMN_COST = 5;
    public static final int COLUMN_GAINS_LOSSES = 6;
    public static final int COLUMN_CREATED_BY = 7;
    
    private final String[] columnNames = new String[] {
        "Id", "Fecha venta", "Subtotal", "Impuesto", "Monto", "Costo venta", "Utilidad/Perdida",
        "Atendió"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, Calendar.class, Double.class, Double.class, Double.class, Double.class, Double.class,
        String.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false
    };
    
    public SaleTableModel()
    {
        super();
    }
    
    public SaleTableModel(List<Sale> list)
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
    public void setColumnEditable(int column, boolean canEdit)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount()
    {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Sale row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getId();
            case 1: return row.getCreatedDate();
            case 2: return row.getSubtotal();
            case 3: return row.getTax();
            case 4: return row.getTotalAmount();
            case 5: return row.getCost();
            case 6: return row.getTotalAmount() - row.getCost();
            case 7: return row.getCreatedBy().getName();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Sale row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.setId((Long) aValue); break;
            case 1: row.setCreatedDate((Calendar) aValue); break;
            case 2: row.setSubtotal((Double) aValue); break;
            case 3: row.setTax((Double) aValue); break;
            case 4: row.setTotalAmount((Double) aValue); break;
            case 5: row.setCost((Double) aValue); break;
            case 6: break;
            case 7: row.setCreatedBy((User) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}

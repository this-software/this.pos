/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.entities.Category;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Clase modelo constructora de una tabla para categorías
 * @author Milton Cavazos
 */
public class CategoryTableModel extends AbstractTableModel
{
    
    private final List<Category> list;
    
    private final String[] columnNames = new String[] {
        "Id", "Código", "Nombre", "Descripción"
    };
    private final Class[] columnClass = new Class[] {
        Integer.class, String.class, String.class, String.class
    };
    private final boolean[] canEdit = new boolean [] {
        false, false, false, false
    };
    
    public CategoryTableModel(List<Category> list)
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
        Category row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: return row.getId();
            case 1: return row.getCode();
            case 2: return row.getName();
            case 3: return row.getDescription();
        }
        return null;
    }
    
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        Category row = list.get(rowIndex);
        switch(columnIndex)
        {
            case 0: row.setId((Long) aValue); break;
            case 1: row.setCode((String) aValue); break;
            case 2: row.setName((String) aValue); break;
            case 3: row.setDescription((String) aValue); break;
        }
        this.fireTableCellUpdated(rowIndex, columnIndex);
    }
    
}
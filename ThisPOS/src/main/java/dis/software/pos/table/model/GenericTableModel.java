/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.table.model;

import dis.software.pos.interfaces.IGenericTableModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Milton Cavazos
 * @param <T>
 * @param <Id>
 */
public abstract class GenericTableModel<T, Id extends Serializable>
    extends AbstractTableModel implements IGenericTableModel<T, Id>
{
    
    public List<T> list;
    
    public GenericTableModel()
    {
        this.list = new ArrayList<>();
    }
    
    public GenericTableModel(List<T> list)
    {
        this.list = list;
    }
    
    @Override
    public abstract String getColumnName(int columnIndex);
    
    @Override
    public abstract Class<?> getColumnClass(int columnIndex);
    
    @Override
    public abstract boolean isCellEditable(int rowIndex, int columnIndex);
    
    @Override
    public abstract void setColumnEditable(int columnIndex, boolean canEdit);

    @Override
    public int getRowCount()
    {
        return list.size();
    }

    @Override
    public abstract int getColumnCount();

    @Override
    public abstract Object getValueAt(int rowIndex, int columnIndex);

    @Override
    public abstract void setValueAt(Object aValue, int rowIndex, int columnIndex);

    @Override
    public T get(int rowIndex)
    {
        if (rowIndex < 0 || rowIndex >= list.size())
        {
            return null;
        }
        return list.get(rowIndex);
    }
    
    @Override
    public List<T> get(int[] rowIndices)
    {
        List<T> result = new ArrayList<>();
        for (int i = 0; i < rowIndices.length; i++)
        {
            if (rowIndices[i] < 0 || rowIndices[i] >= list.size())
            {
                return null;
            }
            result.add(list.get(rowIndices[i]));
        }
        return result;
    }

    @Override
    public List<T> getAll()
    {
        return list;
    }

    @Override
    public void add(T entity)
    {
        int rowIndex = list.size();
        if (!list.contains(entity))
        {
            list.add(entity);
            this.fireTableRowsInserted(rowIndex, rowIndex);
        }
    }

    @Override
    public void remove(int aRowIndex)
    {
        if (aRowIndex < 0 || aRowIndex >= list.size())
        {
            return;
        }
        list.remove(aRowIndex);
        this.fireTableRowsDeleted(aRowIndex, aRowIndex);
    }
    
    @Override
    public void remove(int[] aRowIndices)
    {
        List<T> removeList = new ArrayList<>();
        for (int i = 0; i < aRowIndices.length; i++)
        {
            if (aRowIndices[i] < 0 || aRowIndices[i] >= list.size())
            {
                break;
            }
            removeList.add(list.get(aRowIndices[i]));
        }
        list.removeAll(removeList);
        this.fireTableRowsDeleted(aRowIndices[0], aRowIndices[aRowIndices.length - 1]);
    }

    @Override
    public void removeAll()
    {
        if (list.size() <= 0)
        {
            return;
        }
        int lastRow = list.size();
        list.removeAll(list);
        this.fireTableRowsDeleted(0, lastRow);
    }
    
}

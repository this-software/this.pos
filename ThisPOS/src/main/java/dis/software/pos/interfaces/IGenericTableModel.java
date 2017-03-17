/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 * @param <T>
 * @param <Id>
 */
public interface IGenericTableModel<T, Id extends Serializable>
{
    void setColumnEditable(int column, boolean canEdit);
    void setValueAt(Object aValue, int rowIndex, int columnIndex);
    T get(int rowIndex);
    List<T> get(int[] rowIndices);
    List<T> getAll();
    void add(T entity);
    void remove(int aRowIndex);
    void remove(int[] aRowIndices);
    void removeAll();
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

/**
 *
 * @author Milton Cavazos
 */
public class TableFilterBy
{
    
    private int tableColumnIndex;
    private String tableColumnName;
    
    public TableFilterBy() {}
    
    public TableFilterBy(int columnIndex, String columnName)
    {
        this.tableColumnIndex = columnIndex;
        this.tableColumnName = columnName;
    }

    public int getTableColumnIndex() {
        return tableColumnIndex;
    }

    public void setTableColumnIndex(int tableColumnIndex) {
        this.tableColumnIndex = tableColumnIndex;
    }

    public String getTableColumnName() {
        return tableColumnName;
    }

    public void setTableColumnName(String tableColumnName) {
        this.tableColumnName = tableColumnName;
    }
    
    @Override
    public String toString() {
        return "TableFilterBy: " + this.tableColumnIndex + ", " + this.tableColumnName;
    }
    
}

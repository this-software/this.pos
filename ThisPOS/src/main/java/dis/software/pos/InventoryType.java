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
public enum InventoryType
{
    INCOME(0), OUTCOME(1), CANCEL(2);
    private final int value;
    private InventoryType(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return this.value;
    }    
}

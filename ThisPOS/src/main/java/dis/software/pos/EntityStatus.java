/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos;

/**
 * Clase Enum para indicar los estados de un registro
 * @author Milton Cavazos
 */
public enum EntityStatus
{
    INACTIVE(0), ACTIVE(1), CANCELED(2), LOCKED(3);
    private final int value;
    private EntityStatus(int value)
    {
        this.value = value;
    }
    public int getValue()
    {
        return this.value;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.User;

/**
 *
 * @author Milton Cavazos
 */
public interface IUserService
{
    User getUser(String username);
}

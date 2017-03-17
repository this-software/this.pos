/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.User;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IUser extends IGenericHibernate<User, Long>
{
    String getNextCode();
    User getUser(String username);
    @Override List<User> findAll();
    List<User> getNotificationReceivers();
    List<User> getDeleted();
}

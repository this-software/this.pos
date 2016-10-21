package dis.software.pos.controllers;

import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import dis.software.pos.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Milton Cavazos
 */
@Service
public class UserService implements IUserService
{
    
    @Autowired
    IUser iUser;

    @Override
    public User getUser(String username)
    {
        return iUser.getUser(username);
    }
    
}

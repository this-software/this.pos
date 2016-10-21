package dis.software.pos.controllers;

import dis.software.pos.entities.User;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import dis.software.pos.interfaces.IUser;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los datos de usuario en la base de datos.
 * @author Milton Cavazos
 * @version 12/09/2016
 */
@Repository
@Transactional
public class UserController extends GenericHibernateController<User, Long> implements IUser
{

    private static Logger logger = LogManager.getLogger(UserController.class.getSimpleName());

    /**
     * Método para obtener la información de un usuario filtrado en base al nombre de usuario
     * @param username Nombre de usuario a buscar
     * @return Objeto usuario
     */
    @Override
    public User getUser(String username)
    {
        Session session = super.getCurrentSession();
        User user = (User) session.createQuery("from dis.software.pos.entities.User u "
            + "where u.name = :name and u.active = :active")
            .setParameter("name", username).setParameter("active", User.ACTIVE).uniqueResult();
        return user;
    }

    /**
     * Método para obtener un listado de todos los usuarios que existen en la base de datos.
     * @return Lista de objetos usuario
     */
    @Override
    public List<User> getUserList()
    {
        Session session = super.getCurrentSession();
        List<User> list = session.createQuery("from dis.software.pos.entities.User").list();
        list.stream().forEach((user) -> {
            logger.info("User: " + user.getId() + ", " + user.getEmail());
        });
        return list;
    }
    
}

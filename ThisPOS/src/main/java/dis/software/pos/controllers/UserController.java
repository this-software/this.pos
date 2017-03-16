package dis.software.pos.controllers;

import dis.software.pos.EntityStatus;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
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

    private static final Logger logger = LogManager.getLogger(UserController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de usuario
     * @return Código de usuario
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(u.code) "
            + "from dis.software.pos.entities.User u "
            + "where u.code like 'USR%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "USR" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener la información de un usuario filtrado en base al nombre de usuario
     * @param username Nombre de usuario a buscar
     * @return Usuario
     */
    @Override
    @Transactional(readOnly = true)
    public User getUser(String username)
    {
        super.clear();
        User user = (User) super.getCurrentSession().createQuery(""
            + "select u "
            + "from dis.software.pos.entities.User u "
            + "where u.name = :name "
            + "and u.status = :status")
            .setParameter("name", username)
            .setParameter("status", User.ACTIVE).uniqueResult();
        logger.info("getUser of " + clazz + " completed.");
        return user;
    }

    /**
     * Método para obtener un listado de todos los usuarios que existen en la base de datos.
     * @return Lista de usuarios
     */
    @Override
    @Transactional(isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<User> findAll()
    {
        super.clear();
        List<User> list = super.getCurrentSession().createQuery(""
            + "select u "
            + "from dis.software.pos.entities.User u "
            + "where u.deleted = 0 or u.deleted is null").list();
        logger.info("findAll of " + clazz + " completed.");
        return list;
    }

    /**
     * Método para obtener un listado de los usuarios a los que se les envía notificaciones
     * @return Lista de usuarios
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getNotificationReceivers()
    {
        super.clear();
        List<User> list = super.getCurrentSession().createQuery(""
            + "select u "
            + "from dis.software.pos.entities.User u "
            + "where u.receiveNotifications = 1 "
            + "and (u.deleted = 0 or u.deleted is null)").list();
        logger.info("getNotificationReceivers of " + clazz + " completed.");
        return list;
    }

    /**
     * Método para obtener todos los usuarios marcados como inactivos
     * @return Lista de usuarios inactivos
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getDeleted()
    {
        List<User> list = super.getCurrentSession().createQuery(""
            + "select u "
            + "from dis.software.pos.entities.User u "
            + "where u.status = :status")
            .setParameter("status", EntityStatus.INACTIVE.getValue()).list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
}

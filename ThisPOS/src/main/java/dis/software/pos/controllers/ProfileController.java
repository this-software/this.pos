package dis.software.pos.controllers;

import dis.software.pos.entities.Profile;
import dis.software.pos.interfaces.IProfile;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los perfiles
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProfileController extends GenericHibernateController<Profile, Long> implements IProfile
{
    
    private static final Logger logger = LogManager.getLogger(ProfileController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de perfil
     * @return Código de perfil
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(p.code) "
            + "from dis.software.pos.entities.Profile p "
            + "where p.code like 'PRF%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "PRF" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener todas los perfiles marcadas como eliminados
     * @return Lista de perfiles eliminados
     */
    @Override
    @Transactional(readOnly = true)
    public List<Profile> getDeleted()
    {
        List<Profile> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Profile p "
            + "where p.deleted = 1").list();
        logger.info("getDeleted of " + clazz + " completed.");
        return list;
    }
    
    /**
     * Método para obtener todos los perfiles activos
     * @return Lista de perfiles
     */
    @Override
    @Transactional(readOnly = true)
    public List<Profile> findAll()
    {
        super.clear();
        List<Profile> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Profile p "
            + "where p.deleted = 0 or p.deleted is null").list();
        logger.info("findAll of " + clazz + " completed.");
        return list;
    }
    
}

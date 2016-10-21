package dis.software.pos.controllers;

import dis.software.pos.entities.Profile;
import dis.software.pos.interfaces.IProfile;
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
    
    private static Logger logger = LogManager.getLogger(ProfileController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de perfil
     * @return Código de perfil
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery("select max(p.code) "
            + "from dis.software.pos.entities.Profile p where p.code like 'PRO-%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(4));
        return "PRO-" + String.format("%03d", code == null ? number : number+1);
    }
    
}

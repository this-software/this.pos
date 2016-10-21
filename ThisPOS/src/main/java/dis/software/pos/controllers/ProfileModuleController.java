package dis.software.pos.controllers;

import dis.software.pos.entities.ProfileModule;
import dis.software.pos.entities.ProfileModulePk;
import dis.software.pos.interfaces.IProfileModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los datos de relación entre Perfiles y Módulos en la base de datos.
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ProfileModuleController extends GenericHibernateController<ProfileModule, ProfileModulePk>
    implements IProfileModule
{
    
    private static Logger logger = LogManager.getLogger(ProfileController.class.getSimpleName());

}

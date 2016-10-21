package dis.software.pos.controllers;

import dis.software.pos.entities.Module;
import dis.software.pos.interfaces.IModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a los datos de módulos en la base de datos.
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class ModuleController extends GenericHibernateController<Module, Long> implements IModule
{
    private static Logger logger = LogManager.getLogger(ModuleController.class.getSimpleName());
}

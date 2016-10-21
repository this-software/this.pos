package dis.software.pos.controllers;

import dis.software.pos.entities.Privileges;
import dis.software.pos.interfaces.IPrivileges;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase controladora de acceso a los privilegios
 * @author Milton Cavazos
 */
public class PrivilegesController extends GenericHibernateController<Privileges, Long> implements IPrivileges
{
    
    private static Logger logger = LogManager.getLogger(PrivilegesController.class.getSimpleName());

}

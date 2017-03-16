package dis.software.pos.controllers;

import dis.software.pos.entities.Module;
import dis.software.pos.interfaces.IModule;
import java.util.List;
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
    
    private static final Logger logger = LogManager.getLogger(ModuleController.class.getSimpleName());
    
    /**
     * Método para obtener el listado de módulos ordenados por nombre
     * @return Lista de módulos
     */
    @Override
    @Transactional(readOnly = true)
    public List<Module> findAll()
    {
        List<Module> list = super.getCurrentSession().createQuery(""
            + "select m "
            + "from dis.software.pos.entities.Module m "
            + "where m.deleted = 0 or m.deleted is null "
            + "order by m.name asc").list();
        logger.info("findAll of " + clazz + " completed.");
        return list;
    }
    
}

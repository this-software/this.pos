package dis.software.pos.controllers;

import dis.software.pos.interfaces.IGenericHibernate;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase genérica responsable de realizar operaciones de lectura, escritura en la base de datos
 * @author Milton Cavazos
 * @param <T> Objeto de persistencia
 * @param <Id> Id del objeto de persistencia
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericHibernateController<T, Id extends Serializable> implements IGenericHibernate<T, Id>
{
    
    private static final Logger logger = LogManager.getLogger(GenericHibernateController.class.getSimpleName());
    
    @Autowired
    private SessionFactory sessionFactory;
    private Session session;
    
    public Class<T> clazz;
    
    public GenericHibernateController()
    {
        if (clazz == null) {
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();
            clazz = (Class) parameterizedType.getActualTypeArguments()[0];
        }
    }
    
    public void openSession()
    {
        session = sessionFactory.openSession();
    }
    
    public Session getCurrentSession()
    {
        if (session == null || !session.isOpen()) openSession();
        return session;
    }
    
    @Override
    public void clear()
    {
        if (session != null) session.clear();
    }
    
    @Override
    public void commit()
    {
        session.getTransaction().commit();
    }
    
    @Override
    public void flush()
    {
        session.flush();
    }

    /**
     * Método genérico para buscar una entidad en la base de datos
     * @param id Id de la entidad a buscar
     * @return Entidad
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public T findById(Id id)
    {
        T entity = null;
        try
        {
            session = getCurrentSession();
            entity = (T) session.load(clazz, id);
            logger.info("Load of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error loading object", e);
            return null;
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
            }
            //Grabar movimiento (LOG) en base de datos
        }
        return entity;
    }

    /**
     * Método genérico para buscar todas las entidades en la base de datos
     * @return Listado de entidades
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<T> findAll()
    {
        List<T> list = null;
        try
        {
            session = getCurrentSession();
            clear(); //Limpiar objetos de la sesión
            list = session.createQuery("from " + clazz.getName()).list();
            logger.info("Load of " + clazz + " objects successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error loading object list", e);
            return null;
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
            }
            //Grabar movimiento (LOG) en base de datos
        }
        return list;
    }
    
    @Override
    public T merge(T entity)
    {
        try
        {
            session = getCurrentSession();
            entity = (T) session.merge(entity);
            logger.info("Merge of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            logger.error("Error saving object", e);
        }
        finally
        {
            //Grabar movimiento (LOG) en base de datos
        }
        return entity;
    }

    /**
     * Método genérico para guardar una entidad en la base de datos
     * @param entity Entidad a guardar
     * @return Entidad guardada
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public T save(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.save(entity);
            logger.info("Save of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error saving object", e);
            return null;
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
        return entity;
    }

    /**
     * Método genérico para guardar o actualizar una entidad en la base de datos <br>
     * según lo determine la sesión
     * @param entity Entidad a guardar o actualizar
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveOrUpdate(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.saveOrUpdate(entity);
            logger.info("Save or Update of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error saving or updating object", e);
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
    }

    /**
     * Método genérico para actualizar una entidad en la base de datos
     * @param entity Entidad a actualizar
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.update(entity);
            logger.info("Update of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error updating object", e);
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
    }
    
    /**
     * Método genérico para actualizar una lista de entidades en la base de datos
     * @param list Entidades a actualizar
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateAll(List<T> list)
    {
        try
        {
            session = getCurrentSession();
            list.forEach(entity ->
            {
                session.update(entity);
            });
            logger.info("List update of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error updating object list", e);
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
    }

    /**
     * Método genérico para eliminar una entidad de la base de datos
     * @param entity Entidad a eliminar
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.delete(entity);
            logger.info("Delete of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error deleting object", e);
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
    }

    /**
     * Método genérico para eliminar una lista de entidades de la base de datos
     * @param list Entidades a eliminar
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteAll(List<T> list)
    {
        try
        {
            session = getCurrentSession();
            list.forEach(entity ->
            {
                session.delete(entity);
            });
            logger.info("List delete of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error deleting object list", e);
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
    }

    @Override
    public Object executeQuery(String query)
    {
        Object result = null;
        try
        {
            session = getCurrentSession();
            result = session.createQuery(query).uniqueResult();
            logger.info("Quering of " + clazz + " successful");
        }
        catch (HibernateException e)
        {
            if (session.getTransaction() != null)
            {
                session.getTransaction().rollback();
            }
            logger.error("Error quering object", e);
            return null;
        }
        finally
        {
            if (session.getTransaction() != null)
            {
                flush();
                clear();
            }
            //Grabar movimiento (LOG) en base de datos
        }
        return result;
    }
    
}

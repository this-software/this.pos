package dis.software.pos.controllers;

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
import dis.software.pos.interfaces.IGenericHibernate;

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
    public void commit()
    {
        session.getTransaction().commit();
    }
    
    @Override
    public void flush()
    {
        session.flush();
    }

    @Override
    public T findById(Id id)
    {
        T entity = null;
        try
        {
            session = getCurrentSession();
            entity = (T) session.load(clazz, id);
            logger.info("Load of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error loading object", e);
        }
        return entity;
    }

    @Override
    public List<T> findAll()
    {
        List<T> entities = null;
        try
        {
            session = getCurrentSession();
            entities = session.createQuery("from " + clazz.getName()).list();
            logger.info("Load of " + clazz + " objects successful");
        } catch (HibernateException e) {
            logger.error("Error loading objects", e);
        }
        return entities;
    }
    
    @Override
    public T merge(T entity)
    {
        try
        {
            session = getCurrentSession();
            entity = (T) session.merge(entity);
            logger.info("Merge of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error saving object", e);
        } finally {
            //Grabar movimiento (LOG) en base de datos
        }
        return entity;
    }

    @Override
    public T save(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.save(entity);
            logger.info("Save of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error saving object", e);
        } finally {
            //Grabar movimiento (LOG) en base de datos
        }
        return entity;
    }

    @Override
    public void saveOrUpdate(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.saveOrUpdate(entity);
            logger.info("Save or Update of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error saving or updating object", e);
        }
    }

    @Override
    public void update(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.update(entity);
            logger.info("Update of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error updating object", e);
        }
    }

    @Override
    public void delete(T entity)
    {
        try
        {
            session = getCurrentSession();
            session.delete(entity);
            logger.info("Delete of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error deleting object", e);
        }
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object executeQuery(String string)
    {
        Object result = null;
        try
        {
            session = getCurrentSession();
            result = session.createQuery(string).uniqueResult();
            logger.info("Quering of " + clazz + " successful");
        } catch (HibernateException e) {
            logger.error("Error quering object", e);
        }
        return result;
    }
    
}

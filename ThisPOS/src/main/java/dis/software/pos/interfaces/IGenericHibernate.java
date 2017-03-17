/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.interfaces;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 * @param <T>
 * @param <Id>
 */
public interface IGenericHibernate<T, Id extends Serializable>
{
    void clear();
    void commit();
    void flush();
    T findById(Id id);
    List<T> findAll();
    T merge(T entity);
    T save(T entity);
    void saveOrUpdate(T entity);
    void update(T entity);
    void updateAll(List<T> list);
    void delete(T entity);
    void deleteAll(List<T> list);
    Object executeQuery(String query);
}

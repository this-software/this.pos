/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Category;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.Provider;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IProduct extends IGenericHibernate<Product, Long>
{
    String getNextCode();
    List<Product> findByCategory(Category category);
    List<Product> findByProvider(Provider provider);
    List<Product> findBy(Category category, Provider provider);
}

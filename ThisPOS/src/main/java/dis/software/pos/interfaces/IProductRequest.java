/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductRequest;
import dis.software.pos.entities.ProductRequestPk;

/**
 *
 * @author Milton Cavazos
 */
public interface IProductRequest extends IGenericHibernate<ProductRequest, ProductRequestPk>
{
    Boolean getIsRequired(Product product);
}

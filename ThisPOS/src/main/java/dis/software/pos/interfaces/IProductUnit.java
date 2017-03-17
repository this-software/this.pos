/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductUnit;
import dis.software.pos.entities.ProductUnitPk;
import dis.software.pos.entities.Unit;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IProductUnit extends IGenericHibernate<ProductUnit, ProductUnitPk>
{
    ProductUnit findBy(Product product, Unit unit);
    List<ProductUnit> findByProduct(Product product);
    Integer getQuantityBy(Product product, Unit unit);
}

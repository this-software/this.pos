/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.Promotion;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IPromotion extends IGenericHibernate<Promotion, Long>
{
    String getNextCode();
    Promotion findByCode(String code);
    List<Promotion> findByProduct(Product product);
}

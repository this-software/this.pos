/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Sale;
import dis.software.pos.entities.SalePromotionDetail;
import dis.software.pos.entities.SalePromotionDetailPk;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface ISalePromotionDetail extends IGenericHibernate<SalePromotionDetail, SalePromotionDetailPk>
{
    List<SalePromotionDetail> findBySale(Sale sale);
}

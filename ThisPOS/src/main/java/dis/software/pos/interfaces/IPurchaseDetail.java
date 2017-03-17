/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.interfaces;

import dis.software.pos.entities.Purchase;
import dis.software.pos.entities.PurchaseDetail;
import dis.software.pos.entities.PurchaseDetailPk;
import java.util.List;

/**
 *
 * @author Milton Cavazos
 */
public interface IPurchaseDetail extends IGenericHibernate<PurchaseDetail, PurchaseDetailPk>
{
    List<PurchaseDetail> findByPurchase(Purchase purchase);
}

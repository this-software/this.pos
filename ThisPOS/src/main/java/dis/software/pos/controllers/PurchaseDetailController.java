/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Purchase;
import dis.software.pos.entities.PurchaseDetail;
import dis.software.pos.entities.PurchaseDetailPk;
import dis.software.pos.interfaces.IPurchaseDetail;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso al detalle de las compras
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class PurchaseDetailController extends GenericHibernateController<PurchaseDetail, PurchaseDetailPk>
    implements IPurchaseDetail
{

    private static final Logger logger = LogManager.getLogger(PurchaseDetailController.class.getSimpleName());
    
    /**
     * Método para obtener el detalle de compra filtrado por registro de compra
     * @param purchase Compra para filtrar
     * @return Lista de detalle de compra por registro de compra
     */
    @Override
    @Transactional(readOnly = true)
    public List<PurchaseDetail> findByPurchase(Purchase purchase)
    {
        List<PurchaseDetail> list = super.getCurrentSession().createQuery(""
            + "select pd "
            + "from dis.software.pos.entities.PurchaseDetail pd "
            + "where pd.purchaseDetailPk.purchase.id = :purchase_id")
            .setParameter("purchase_id", purchase.getId()).list();
        logger.info("findByPurchase of " + clazz + " completed.");
        return list;
    }
    
}

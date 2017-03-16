/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Sale;
import dis.software.pos.entities.SalePromotionDetail;
import dis.software.pos.entities.SalePromotionDetailPk;
import dis.software.pos.interfaces.ISalePromotionDetail;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso al detalle de venta de las promociones
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class SalePromotionDetailController
    extends GenericHibernateController<SalePromotionDetail, SalePromotionDetailPk> implements ISalePromotionDetail
{
    
    private static final Logger logger = LogManager.getLogger(SalePromotionDetailController.class.getSimpleName());

    /**
     * Método para obtener el detalle de venta de promociones filtrado por registro de venta
     * @param sale Venta para filtrar
     * @return Lista de detalle de venta de promociones por registro de venta
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalePromotionDetail> findBySale(Sale sale)
    {
        List<SalePromotionDetail> list = super.getCurrentSession().createQuery(""
            + "select spd "
            + "from dis.software.pos.entities.SalePromotionDetail spd "
            + "where spd.sale.id = :sale_id")
            .setParameter("sale_id", sale.getId()).list();
        logger.info("findBySale of " + clazz + " completed.");
        return list;
    }
    
}

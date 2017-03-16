/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Sale;
import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.SaleDetailPk;
import dis.software.pos.interfaces.ISaleDetail;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso al detalle de las ventas
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class SaleDetailController extends GenericHibernateController<SaleDetail, SaleDetailPk>
    implements ISaleDetail
{
    
    private static final Logger logger = LogManager.getLogger(SaleDetailController.class.getSimpleName());

    /**
     * Método para obtener el detalle de venta filtrado por registro de venta
     * @param sale Venta para filtrar
     * @return Lista de detalle de venta por registro de venta
     */
    @Override
    @Transactional(readOnly = true)
    public List<SaleDetail> findBySale(Sale sale)
    {
        List<SaleDetail> list = super.getCurrentSession().createQuery(""
            + "select sd "
            + "from dis.software.pos.entities.SaleDetail sd "
            + "where sd.sale.id = :sale_id")
            .setParameter("sale_id", sale.getId()).list();
        logger.info("findBySale of " + clazz + " completed.");
        return list;
    }
    
}

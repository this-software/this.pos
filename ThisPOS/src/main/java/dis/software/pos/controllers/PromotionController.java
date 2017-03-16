/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.controllers;

import dis.software.pos.entities.Product;
import dis.software.pos.entities.Promotion;
import dis.software.pos.interfaces.IPromotion;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Clase controladora de acceso a las promociones
 * @author Milton Cavazos
 */
@Repository
@Transactional
public class PromotionController extends GenericHibernateController<Promotion, Long> implements IPromotion
{
    
    private static final Logger logger = LogManager.getLogger(PromotionController.class.getSimpleName());

    /**
     * Método para obtener el siguiente código de promoción
     * @return Código de promoción
     */
    @Override
    @Transactional(readOnly = true)
    public String getNextCode()
    {
        String code = (String) super.executeQuery(""
            + "select max(p.code) "
            + "from dis.software.pos.entities.Promotion p "
            + "where p.code like 'PMO%'");
        Integer number = code == null ? 1 : Integer.valueOf(code.substring(3));
        return "PMO" + String.format("%04d", code == null ? number : number + 1);
    }
    
    /**
     * Método para obtener promociones filtradas por código
     * @param code Código para filtrar
     * @return Promoción filtrada
     */
    @Override
    @Transactional(readOnly = true)
    public Promotion findByCode(String code)
    {
        Promotion promotion = (Promotion) super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Promotion p "
            + "where p.code = :code")
            .setParameter("code", code).uniqueResult();
        logger.info("findByCode of " + clazz + " completed.");
        return promotion;
    }
    
    /**
     * Método para obtener un listado de promociones filtrados por producto
     * @param product Producto para filtrar
     * @return Lista de promociones
     */
    @Override
    public List<Promotion> findByProduct(Product product)
    {
        List<Promotion> list = super.getCurrentSession().createQuery(""
            + "select p "
            + "from dis.software.pos.entities.Promotion p "
            + "where p.promotionProducts.promotionProductPk.product.id = :product_id")
            .setParameter("product_id", product.getId()).list();
        logger.info("findByProduct of " + clazz + " completed.");
        return list;
    }
    
}

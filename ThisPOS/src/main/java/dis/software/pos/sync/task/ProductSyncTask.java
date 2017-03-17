/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.sync.task;

import dis.software.pos.Application;
import dis.software.pos.GsonRequest;
import dis.software.pos.entities.Product;
import dis.software.pos.interfaces.IProduct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase encargada de ejecutar la sincronización de productos
 * @author Milton Cavazos
 */
public class ProductSyncTask extends TimerTask
{
    
    private static final Logger logger = LogManager.getLogger(ProductSyncTask.class.getSimpleName());
    
    private final String link;
    
    /**
     * Método constructor de la clase
     * @param link Enlace donde se harán las peticiones
     */
    public ProductSyncTask(String link)
    {
        this.link = link;
    }

    @Override
    public void run()
    {
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        
        GsonRequest<Product[]> gsonRequest = new GsonRequest<>(Product[].class, link);
        List<Product> list = new ArrayList<>(Arrays.asList(gsonRequest.get()));
        list.forEach(x ->
        {
            logger.info(x.getName());
        });
        //iProduct.updateAll(list);
        
    }
    
}

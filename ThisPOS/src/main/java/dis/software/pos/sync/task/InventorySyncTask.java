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
import dis.software.pos.entities.Inventory;
import dis.software.pos.interfaces.IInventory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase encargada de ejecutar la sincronización de inventario
 * @author Milton Cavazos
 */
public class InventorySyncTask extends TimerTask
{
    
    private static final Logger logger = LogManager.getLogger(ProductSyncTask.class.getSimpleName());
    
    private final String link;
    
    /**
     * Método constructor de la clase
     * @param link Enlace donde se harán las peticiones
     */
    public InventorySyncTask(String link)
    {
        this.link = link;
    }
    
    @Override
    public void run()
    {
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        
        GsonRequest<Inventory[]> gsonRequest = new GsonRequest<>(Inventory[].class, link);
        List<Inventory> list = new ArrayList<>(Arrays.asList(gsonRequest.get()));
        list.forEach(x ->
        {
            logger.info(x.getCode());
        });
        //iInventory.updateAll(list);
        
    }
    
}

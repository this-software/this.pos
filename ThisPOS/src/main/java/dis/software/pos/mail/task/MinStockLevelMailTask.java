/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.mail.task;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.EntityStatus;
import dis.software.pos.entities.Notification;
import dis.software.pos.entities.Product;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.mail.processors.MinStockLevelMailProcessor;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class MinStockLevelMailTask extends TimerTask
{

    private static final Logger logger = LogManager.getLogger(MinStockLevelMailTask.class.getSimpleName());
    
    @Override
    public void run()
    {
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        
        List<Product> list = iProduct.getMinStockLevel();
        //Si no existen productos con unidades mínimas y ya han sido notificados, "REGRESAR"
        if (list.isEmpty()
            //O si la notificación se encuentra inactiva
            || ApplicationSession.getNotifications().stream()
                .anyMatch(x -> x.getCode().equals(Notification.PRODUCT_MIN_LEVEL)
                    && x.getStatus().equals(EntityStatus.INACTIVE.getValue())))
        {
            logger.info("Minimum stock level notification is INACTIVE");
            return;
        }
        
        ApplicationSession.getThreadPoolExecutor().execute(new MinStockLevelMailProcessor(list));
        
        Boolean isWaiting = Boolean.TRUE;
        while (isWaiting)
        {
            try
            {
                isWaiting = !ApplicationSession.getThreadPoolExecutor().awaitTermination(5, TimeUnit.SECONDS);
                //Si la tarea encargada de enviar el correo ya ha terminado
                if (!isWaiting)
                {
                    iProduct.setMinStockLevelNotified(list);
                    logger.info("Minimum stock level notified");
                    //Se asigna valor "null" para que pueda ser inicializado nuevamente
                    ApplicationSession.setThreadPoolExecutor(null);
                    break;
                }
            }
            catch (InterruptedException ex)
            {
                logger.error("Interrupted thread: " + Thread.currentThread().getName(), ex);
            }
            logger.info("Waiting to set minimum stock level notified");
        }
        
    }
    
}

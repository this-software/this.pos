/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos;

import dis.software.pos.entities.Notification;
import dis.software.pos.entities.User;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase de sesión que contiene valores del usuario que se encuentra firmado
 * en el sistema ThisPOS
 * @author Milton Cavazos
 */
public class ApplicationSession
{
    
    private static final Logger logger = LogManager.getLogger(ApplicationSession.class.getSimpleName());
    
    private static User user = null;
    private static List<Notification> notifications = null;
    
    private static final ThreadFactory threadFactory = Executors.defaultThreadFactory();
    
    private static ThreadPoolExecutor threadPoolExecutor = null;
    
    private static final int corePoolSize = 2;
    private static final int maximunPoolSize = 4;
    private static final long keepAliveTime = 5000;
    private static final int maxWaitingTasks = 4;
    
    public ApplicationSession() {}

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        ApplicationSession.user = user;
    }

    public static List<Notification> getNotifications() {
        return notifications;
    }

    public static void setNotifications(List<Notification> notifications) {
        ApplicationSession.notifications = notifications;
    }
    
    public static ThreadPoolExecutor getThreadPoolExecutor()
    {
        if (threadPoolExecutor == null)
        {
            threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximunPoolSize, keepAliveTime,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(maxWaitingTasks), threadFactory, 
                    (Runnable r, ThreadPoolExecutor executor) -> 
                    {
                        try
                        {
                            executor.getQueue().put(r);
                        }
                        catch (InterruptedException e)
                        {
                            logger.error("Rejected task", e);
                            throw new RuntimeException("Interrupted while submitting task", e);
                        }
                    });
        }
        return threadPoolExecutor;
    }

    public static void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
        ApplicationSession.threadPoolExecutor = threadPoolExecutor;
    }
    
}

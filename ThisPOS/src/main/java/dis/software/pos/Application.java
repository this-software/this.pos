package dis.software.pos;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Clase para inicializar el contexto de la configuración de Spring
 * @author Milton Cavazos
 */
public class Application
{
    
    private static Logger logger = LogManager.getLogger(Application.class.getSimpleName());
    
    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.cfg.xml");;
    
    public Application()
    {
        logger.info("New instance of Application class to initiate Spring context");
        if (context == null) context = new ClassPathXmlApplicationContext("spring.cfg.xml");
    }
    
    public static ClassPathXmlApplicationContext getContext() {
        return context;
    }

    public void setContext(ClassPathXmlApplicationContext context) {
        Application.context = context;
    }
    
}

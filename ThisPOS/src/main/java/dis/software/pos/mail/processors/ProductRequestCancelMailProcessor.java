/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.mail.processors;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.MailUtil;
import dis.software.pos.entities.ProductRequest;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class ProductRequestCancelMailProcessor implements Runnable
{
    
    private static final Logger logger = LogManager.getLogger(ProductRequestMailProcessor.class.getSimpleName());
    
    private ProductRequest productRequest;
    
    public ProductRequestCancelMailProcessor() {}
    
    /**
     * Constructor de la clase
     * @param productRequest Producto solicitado
     */
    public ProductRequestCancelMailProcessor(ProductRequest productRequest)
    {
        this.productRequest = productRequest;
    }

    /**
     * Método para notificar a los usuarios correspondientes acerca de la CANCELACIÓN de solicitud de producto
     */
    @Override
    public void run()
    {
        
        MailUtil mailUtil = new MailUtil();
        mailUtil.setSubject("Notificación: CANCELACIÓN de solicitud");
        
        StringBuilder content = new StringBuilder();
        content.append("El usuario (").append(ApplicationSession.getUser().getName()).append(") ")
            .append("ha generado una <b>CANCELACIÓN</b> de solicitud de producto.<br><br>")
            .append("<b>Código:</b> ").append(productRequest.getProduct().getCode()).append("<br>")
            .append("<b>Nombre:</b> ").append(productRequest.getProduct().getName()).append("<br>")
            .append("<b>Fecha de cancelación:</b> ").append((new SimpleDateFormat("dd 'de' MMMM 'del' yyyy, hh:mm a"))
                .format(new GregorianCalendar().getTime())).append("<br>")
            .append("<b>Unidades solicitadas:</b> ").append(productRequest.getRequiredUnits()).append(" ")
                .append(productRequest.getUnit().getName()).append("(S)<br>")
            .append("<b>Negocio:</b> ").append(Application.getSetting().getStoreName()).append("<br><br>");

        mailUtil.setContent(content.toString());

        IUser iUser = Application.getContext().getBean(IUser.class);
        try
        {
            mailUtil.setAddresses(InternetAddress.parse(iUser.getNotificationReceivers()
                .stream().map(User::getEmail).collect(Collectors.joining(","))));
        }
        catch (AddressException ex)
        {
            logger.error("Error with some address", ex);
        }
        mailUtil.send();
        
    }
    
}

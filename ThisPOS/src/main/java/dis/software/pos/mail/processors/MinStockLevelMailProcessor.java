/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.mail.processors;

import dis.software.pos.Application;
import dis.software.pos.MailUtil;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase encargada de procesar los correos de nivel mínimo en almacén
 * @author Milton Cavazos
 */
public class MinStockLevelMailProcessor implements Runnable
{
    
    private static final Logger logger = LogManager.getLogger(MinStockLevelMailProcessor.class.getSimpleName());
    
    private List<Product> list;
    
    public MinStockLevelMailProcessor() {}
    
    /**
     * Constructor de la clase
     * @param list Lista de productos con un nivel mínimo de unidades
     */
    public MinStockLevelMailProcessor(List<Product> list)
    {
        this.list = list;
    }

    @Override
    public void run()
    {
        
        MailUtil mailUtil = new MailUtil();
        mailUtil.setSubject("Notificación: NIVEL MINIMO de producto alcanzado");
        
        StringBuilder header = new StringBuilder();
        header.append("<table>")
                .append("<tr>")
                    .append("<th style='text-align:left;width:10%;'>Código</th>")
                    .append("<th style='text-align:left;width:20%;'>Nombre</th>")
                    .append("<th style='text-align:left;width:40%;'>Descripción</th>")
                    .append("<th style='text-align:left;width:15%;'>Nivel mínimo</th>")
                    .append("<th style='text-align:left;width:15%;'>Unidades en almacén</th>")
                .append("</tr>");

        StringBuilder row = new StringBuilder();
        list.stream().forEach(product ->
        {
            row.append("<tr>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(product.getCode())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(product.getName())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:40%;'>")
                    .append(product.getDescription())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(product.getMinStockLevel())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(product.getUnitsInStock())
                .append("</td>")
            .append("</tr>");
        });
        row.append("</table>");
        
        StringBuilder content = new StringBuilder();
        content.append("El sistema ha generado una notificación de tipo: ")
            .append("<b>NIVEL MINIMO</b> de producto alcanzado.<br><br>")
            .append("<b>Fecha de notificación:</b> ")
            .append((new SimpleDateFormat("dd 'de' MMMM 'del' yyyy, hh:mm a"))
                .format(new GregorianCalendar().getTime())).append("<br>")
            .append("<b>Negocio:</b> ").append(Application.getSetting().getStoreName()).append("<br><br>")
            .append("Listado de productos<br><br>")
            .append(header)
            .append(row);

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
        mailUtil.send(Boolean.TRUE);
        
    }
    
}

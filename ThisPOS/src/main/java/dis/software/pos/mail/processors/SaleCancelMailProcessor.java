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
import dis.software.pos.entities.Sale;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class SaleCancelMailProcessor implements Runnable
{
    
    private static final Logger logger = LogManager.getLogger(SaleCancelMailProcessor.class.getSimpleName());
    
    private Sale sale;
    
    public SaleCancelMailProcessor() {}
    
    /**
     * Constructor de la clase
     * @param sale Venta cancelada
     */
    public SaleCancelMailProcessor(Sale sale)
    {
        this.sale = sale;
    }

    /**
     * Método para notificar a los usuarios correspondientes acerca de la CANCELACIÓN del registro
     */
    @Override
    public void run()
    {
        
        MailUtil mailUtil = new MailUtil();
        mailUtil.setSubject("Notificación: CANCELACIÓN de venta");

        StringBuilder header = new StringBuilder();
        header.append("<table>")
                .append("<tr>")
                    .append("<th style='text-align:left;width:10%;'>Código de prod.</th>")
                    .append("<th style='text-align:left;width:15%;'>Nombre de prod.</th>")
                    .append("<th style='text-align:left;width:35%;'>Descripción de prod.</th>")
                    .append("<th style='text-align:right;width:10%;'>Cantidad</th>")
                    .append("<th style='text-align:right;width:10%;'>Monto</th>")
                    .append("<th style='text-align:right;width:10%;'>Descuento</th>")
                    .append("<th style='text-align:right;width:10%;'>Total</th>")
                .append("</tr>");

        StringBuilder row = new StringBuilder();
        sale.getSaleDetails().forEach(entity ->
        {
            row.append("<tr>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(entity.getProduct().getCode())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(entity.getProduct().getName())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:35%;'>")
                    .append(entity.getProduct().getDescription())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(entity.getQuantity())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(String.format("$%,.2f", entity.getPrice() * entity.getQuantity()))
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(String.format("$%,.2f", entity.getTotalDiscount()))
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(String.format("$%,.2f", entity.getAmount()))
                .append("</td>")
            .append("</tr>");
        });
        sale.getSalePromotionDetails().forEach(entity ->
        {
            row.append("<tr>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(entity.getPromotion().getCode())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(entity.getPromotion().getName())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:35%;'>")
                    .append(entity.getPromotion().getDescription())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(entity.getQuantity())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(String.format("$%,.2f", entity.getAmount()))
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(String.format("$%,.2f", 0.00))
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:10%;'>")
                    .append(String.format("$%,.2f", entity.getAmount()))
                .append("</td>")
            .append("</tr>");
        });
        row.append("</table>");
        
        StringBuilder content = new StringBuilder();
        content.append("El usuario (").append(ApplicationSession.getUser().getName()).append(") ")
            .append("ha generado una <b>CANCELACIÓN</b> de venta.<br><br>")
            //.append("<b>Código:</b> ").append(purchase.getCode()).append("<br>")
            .append("<b>Fecha de cancelación:</b> ").append((new SimpleDateFormat("dd 'de' MMMM 'del' yyyy, hh:mm a"))
                .format(sale.getUpdatedDate().getTime())).append("<br>")
            .append("<b>Monto total:</b> ").append(String.format("$%,.2f",sale.getTotalAmount())).append("<br>")
            .append("<b>Observaciones:</b> ").append(sale.getObservations()).append("<br>")
            .append("<b>Negocio:</b> ").append(Application.getSetting().getStoreName()).append("<br><br>")
            //.append("El registro <b>").append(purchase.getCode()).append("</b> de ");
            .append("El registro de venta ha sido cancelado.<br>")
            .append("Listado de productos y promociones<br><br>")
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
        mailUtil.send();
        
    }
    
}

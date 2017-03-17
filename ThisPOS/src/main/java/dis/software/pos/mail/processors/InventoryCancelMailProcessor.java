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
import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Clase encargada de procesar los correos de cancelación de inventario
 * @author Milton Cavazos
 */
public class InventoryCancelMailProcessor implements Runnable
{
    
    private static final Logger logger = LogManager.getLogger(InventoryOutcomeMailProcessor.class.getSimpleName());
    
    private Inventory inventory;
    
    public InventoryCancelMailProcessor() {}
    
    /**
     * Constructor de la clase
     * @param inventory Registro de inventario cancelado
     */
    public InventoryCancelMailProcessor(Inventory inventory)
    {
        this.inventory = inventory;
    }

    /**
     * Método para notificar a los usuarios correspondientes acerca de la CANCELACIÓN del registro
     */
    @Override
    public void run()
    {
        
        MailUtil mailUtil = new MailUtil();
        mailUtil.setSubject("Notificación: CANCELACIÓN de inventario");

        StringBuilder header = new StringBuilder();
        header.append("<table>")
                .append("<tr>")
                    .append("<th style='text-align:left;width:15%;'>Código de prod.</th>")
                    .append("<th style='text-align:left;width:15%;'>Nombre de prod.</th>")
                    .append("<th style='text-align:left;width:40%;'>Descripción de prod.</th>")
                    .append("<th style='text-align:right;width:15%;'>Cantidad</th>")
                    .append("<th style='text-align:left;width:15%;'>Unidad</th>")
                .append("</tr>");

        StringBuilder row = new StringBuilder();
        inventory.getInventoryProducts().forEach(entity ->
        {
            row.append("<tr>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(entity.getProduct().getCode())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(entity.getProduct().getName())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:40%;'>")
                    .append(entity.getProduct().getDescription())
                .append("</td>")
                .append("<td style='text-align:right;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(entity.getUnits())
                .append("</td>")
                .append("<td style='text-align:left;border-top:2px solid #b3b3b3;width:15%;'>")
                    .append(entity.getUnit().getName())
                .append("</td>")
            .append("</tr>");
        });
        row.append("</table>");
        
        StringBuilder content = new StringBuilder();
        content.append("El usuario (").append(ApplicationSession.getUser().getName()).append(") ")
            .append("ha generado una <b>CANCELACIÓN</b> de inventario.<br><br>")
            .append("<b>Código:</b> ").append(inventory.getCode()).append("<br>")
            .append("<b>Fecha de cancelación:</b> ").append((new SimpleDateFormat("dd 'de' MMMM 'del' yyyy, hh:mm a"))
                .format(inventory.getUpdatedDate().getTime())).append("<br>")
            .append("<b>Negocio:</b> ").append(Application.getSetting().getStoreName()).append("<br><br>")
            .append("El registro <b>").append(inventory.getCode()).append("</b> de ");
        
        switch(inventory.getType())
        {
            case INCOME : content.append("ENTRADA"); break;
            case OUTCOME : content.append("SALIDA"); break;
            case SYSTEM : content.append("SISTEMA"); break;
        }
        
        content.append(" ha sido cancelado.<br>")
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
        mailUtil.send();
        
    }
    
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.tickets;

import dis.software.pos.PrintJobWatcher;
import dis.software.pos.Application;
import dis.software.pos.entities.Sale;
import dis.software.pos.interfaces.ISale;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class CashOutTicket
{
    
    private static final Logger logger = LogManager.getLogger(SaleTicket.class.getSimpleName());
    
    Sale sale;
    List<Object> items;
    
    public String contentTicket = ""
        + "  {{nameLocal}}\r\n"
        + "  EXPEDIDO EN: {{expedition}}\r\n"
        + "  {{domicilio}} \r\n"
        + "  ===========================================\r\n"
        + "  OCOSINGO, CHIAPAS C.P. 29950\r\n"
        + "  RFC: {{RFC}}\r\n\n"
        + "  Caja # {{box}} - Ticket # {{ticket}}\r\n"
        + "  -------------------------------------------\r\n"
        + "           CORTE DE CAJA\r\n"
        + "  -------------------------------------------\r\n"
        + "  Realizo: {{cajero}}\r\n  {{dateTime}}\r\n"
        + "  ===========================================\r\n"
        + "  Caja Inicial:      {{Inicial}}\r\n"
        + "  Efectivo:          {{Efectivo}}\r\n"
        + "  Cheque:            {{Cheque}}\r\n"
        + "  Apartado:          {{Apartado}}\r\n"
        + "  Pago Diferencia:   {{Dif}}\r\n"
        + "  ------------------------------------------\r\n"
        + "  Devoluciones:      {{Devoluciones}}\r\n"
        + "  Gastos:            {{Gasto}}\r\n"
        + "  ------------------------------------------\r\n"
        + "  Caja Total:          {{CajaTot}}\r\n\n"
        + "  ==========================================\r\n"
        + "       ¡GRACIAS POR SU COMPRA!\r\n"
        + "     ESPERAMOS SU VISITA NUEVAMENTE\r\n\n\n\n\n";
    
    public CashOutTicket() {}
    
    public CashOutTicket(Sale sale, List<Object> items)
    {
        this.sale = sale;
        this.items = items;
    }
    
    public void print()
    {
        
        ISale iSale = Application.getContext().getBean(ISale.class);
        Double amount = iSale.getDailyCashOutAmount() == null ? 0 : iSale.getDailyCashOutAmount();
        
        this.contentTicket = this.contentTicket.replace("{{Efectivo}}", String.format("$ %1$,.2f", amount));
        
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        
        byte[] bytes = contentTicket.getBytes();

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        Doc doc = new SimpleDoc(bytes, flavor, null);
        DocPrintJob printJob = null;
        if (services.length > 0)
        {
            for (PrintService service : services)
            {
                if (service.getName().equals("Microsoft XPS Document Writer")) {
                    printJob = service.createPrintJob();
                }
            }
        }
        try
        {
            if (printJob != null)
            {
                PrintJobWatcher pjw = new PrintJobWatcher(printJob);
                printJob.print(doc, null);
                pjw.waitForDone();
            }
        }
        catch (PrintException ex)
        {
            logger.error(ex);
        }
        
    }
    
}

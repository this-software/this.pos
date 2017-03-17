/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.tickets;

import br.com.adilson.util.PrinterMatrix;
import dis.software.pos.PrintJobWatcher;
import dis.software.pos.entities.Sale;
import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.SalePromotionDetail;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class SaleTicket
{
    
    private static final Logger logger = LogManager.getLogger(SaleTicket.class.getSimpleName());
    
    private Sale sale;
    private List<Object> items;
    
    public String contentTicket = ""
        + " EXPEDIDO EN: {{issued}}\n "
        + " {{address}} \n "
        + " ============================================\n "
        + " OCOSINGO, CHIAPAS C.P. 29950\n "
        + " RFC: {{rfc}}\n "
        + " CLIENTE: {{client}}\n "
        + " Caja # {{box}} - Ticket # {{ticket}}\n "
        + " LE ATENDIO: {{cashier}}\n "
        + " {{date}}\n "
        + " --------------------------------------------\n "
        + " |Cant  |Descripcion                 |Costo |\n "
        + " --------------------------------------------\n "
        + " {{items}}\n "
        + " ============================================\n "
        + " TOTAL :    {{total}}\n\n "
        + " {{payment_type}}:  {{paid_amount}}\n "
        + " CAMBIO :   {{refund_amount}}\n "
        + " ============================================\n "
        + "           ¡GRACIAS POR SU COMPRA!\n "
        + "       ESPERAMOS SU VISITA NUEVAMENTE\n "
        + "          NO SE ACEPTAN DEVOLUCIONES\n\n\n\n\n "
        + "\\033m";
    
    String items1 = ""
        + "1      ARTICULO NUMERO 1            $100.00\n "
        + "2      ARTICULO NUMERO 2 @ $25.00   $50.00\n "
        + "1      ARTICULO NUMERO 3            $70.00\n "
        + "1      ARTICULO NUMERO 4            $45.00\n "
        + "1      ARTICULO NUMERO 5            $16.00";
    
    public SaleTicket() {}
    
    public SaleTicket(Sale sale, List<Object> items)
    {
        this.sale = sale;
        this.items = items;
    }
    
    private void print1()
    {
        
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        
        this.contentTicket = this.contentTicket.replace("{{items}}", items1);
        
        byte[] bytes = contentTicket.getBytes();

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        InputStream is = null;
        try
        {
            is = new ByteArrayInputStream("hello world!\f".getBytes("UTF8"));
        }
        catch (UnsupportedEncodingException ex)
        {
            logger.error(ex);
        }
        Doc doc = new SimpleDoc(bytes, flavor, null);
        DocPrintJob printJob = null;
        if (services.length > 0)
        {
            for (PrintService service : services)
            {
                if (service.getName().equals("Microsoft XPS Document Writer"))
                {
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
            if (is != null) is.close();
        }
        catch (PrintException | IOException ex)
        {
            logger.error(ex);
        }
        
    }
    
    public void print()
    {
        
        PrinterMatrix matrix = new PrinterMatrix();
        
        //Definir el tamanho del papel para la impresion  aca 25 lineas y 80 columnas
        matrix.setOutSize(60, 80);
        //Imprimir * de la 2da linea a 25 en la columna 1;
        // printer.printCharAtLin(2, 25, 1, "*");
        //Imprimir * 1ra linea de la columa de 1 a 80
        matrix.printTextWrap(1, 2, 1, 80, "EXPEDIDO EN: {{issued}}".replace("{{issued}}", "ABARROTES KRLOS"));
        matrix.printTextWrap(2, 3, 1, 80, "{{address}}".replace("{{address}}", "AV. UNIVERSIDAD #200 ESCOBEDO, N.L."));
        matrix.printCharAtCol(4, 1, 80, "=");
        //Imprimir encabezado de la empresa
        matrix.printTextWrap(4, 5, 32, 80, "TICKET DE VENTA");
        matrix.printTextWrap(6, 7, 1, 20, "NO. TICKET: {{ticket}}".replace("{{ticket}}", sale.getId().toString()));
        matrix.printTextWrap(6, 7, 25, 55, "FECHA DE EMISION: {{date}}".replace("{{date}}",
            new SimpleDateFormat("yyyy-MMM-dd").format(sale.getCreatedDate().getTime())));
        matrix.printTextWrap(6, 7, 60, 80, "HORA: {{hour}}".replace("{{hour}}",
            new SimpleDateFormat("HH:mm:ss").format(sale.getCreatedDate().getTime())));
        matrix.printTextWrap(7, 8, 1, 80, "RFC: {{rfc}}".replace("{{rfc}}","CAGM911019MD1"));
        matrix.printTextWrap(8, 9, 1, 80, "CLIENTE: {{client}}".replace("{{client}}","AL PUBLICO"));
        matrix.printTextWrap(9, 10, 1, 80, "CAJA: {{box}}".replace("{{box}}","045"));
        matrix.printTextWrap(10, 11, 1, 80, "LE ATENDIO: {{cashier}}".replace("{{cashier}}",
            sale.getCreatedBy().getName()));
        matrix.printCharAtCol(12, 1, 80, "-");
        matrix.printTextWrap(12, 13, 0, 49, "|DESCRIPCION");
        matrix.printTextWrap(12, 13, 50, 64, "|CANT");
        matrix.printTextWrap(12, 13, 65, 79, "|PRECIO");
        matrix.printCharAtCol(14, 1, 80, "-");
        
        int rowIndex = 13;
        for (Object object : items)
        {
            if (object instanceof SaleDetail
                && ((SaleDetail) object).getProduct() != null)
            {
                SaleDetail saleDetail = (SaleDetail) object;
                matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 1, 50, "{{desc}}".replace("{{desc}}",
                    saleDetail.getProduct().getDescription()));
                matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 51, 65, "{{qty}}".replace("{{qty}}",
                    saleDetail.getQuantity().toString()));
                matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 66, 80, "{{price}}".replace("{{price}}",
                    String.format("$%1$,.2f", saleDetail.getAmount())));
                rowIndex = rowIndex + 1;
            }
            if (object instanceof SalePromotionDetail
                && ((SalePromotionDetail) object).getPromotion() != null)
            {
                SalePromotionDetail salePromotionDetail = (SalePromotionDetail) object;
                matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 1, 50, "{{desc}}".replace("{{desc}}",
                    salePromotionDetail.getPromotion().getDescription()));
                matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 51, 65, "{{qty}}".replace("{{qty}}",
                    salePromotionDetail.getQuantity().toString()));
                matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 66, 80, "{{price}}".replace("{{price}}",
                    String.format("$%1$,.2f", salePromotionDetail.getAmount())));
                rowIndex = rowIndex + 1;
            }
        }
        matrix.printCharAtCol(rowIndex + 2, 1, 80, "=");
        matrix.printTextWrap(rowIndex + 2, rowIndex + 3, 59, 80, "TOTAL: {{total}}".replace("{{total}}",
            String.format("$%1$,.2f", sale.getTotalAmount())));
        String paymentType = "EFECTIVO";
        matrix.printTextWrap(rowIndex + 3, rowIndex + 4, 64 - paymentType.length(), 80,
            "{{payment_type}}: {{paid_amount}}".replace("{{payment_type}}", paymentType).replace("{{paid_amount}}",
            String.format("$%1$,.2f", sale.getPaidAmount())));
        matrix.printTextWrap(rowIndex + 4, rowIndex + 5, 58, 80, "CAMBIO: {{refund_amount}}".replace("{{refund_amount}}",
            String.format("$%1$,.2f", sale.getRefundAmount())));
        matrix.printCharAtCol(rowIndex + 6, 1, 80, "=");
        matrix.printTextWrap(rowIndex + 7, rowIndex + 8, 29, 80, "¡GRACIAS POR SU COMPRA!");
        matrix.printTextWrap(rowIndex + 8, rowIndex + 9, 26, 80, "ESPERAMOS SU VISITA NUEVAMENTE");
        matrix.printTextWrap(rowIndex + 9, rowIndex + 10, 28, 80, "NO SE ACEPTAN DEVOLUCIONES\f");
        
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        matrix.toFile(System.getProperty("user.dir").concat("\\tickets\\sale\\")
            .concat(sdf.format(now).toUpperCase()).concat("T").concat(sale.getId().toString())
                .concat(".txt"));
        
        byte[] bytes = null;
        try
        {
            bytes = Files.readAllBytes(
                Paths.get(new File(System.getProperty("user.dir").concat("\\tickets\\sale\\")
                    .concat(sdf.format(now).toUpperCase()).concat("T").concat(sale.getId().toString())
                        .concat(".txt")).getAbsolutePath()));
        }
        catch (IOException ex)
        {
            logger.error(ex);
        }
        
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(OrientationRequested.PORTRAIT);
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, pras);
        
        if (services.length > 0)
        {
            for (PrintService service : services)
            {
                if (service.getName().equals("Microsoft XPS Document Writer"))
                {
                    DocPrintJob printJob = service.createPrintJob();
                    DocAttributeSet das = new HashDocAttributeSet();
                    Doc document = new SimpleDoc(bytes, flavor, das);
                    try
                    {
                        PrintJobWatcher pjw = new PrintJobWatcher(printJob);
                        printJob.print(document, pras);
                        pjw.waitForDone();
                    }
                    catch (Exception ex)
                    {
                        logger.error("Ha ocurrido un error al intentar imprimir el ticket.", ex);
                    }
                }
            }
        }
        
    }
  
}

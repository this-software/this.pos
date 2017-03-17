/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.tickets;

import dis.software.pos.PrintJobWatcher;
import br.com.adilson.util.PrinterMatrix;
import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
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
public class InventoryProductDetailTicket
{
    
    private static final Logger logger = LogManager.getLogger(InventoryProductDetailTicket.class.getSimpleName());
    
    Inventory inventory;
    List<InventoryProduct> inventoryProducts;
    
    public InventoryProductDetailTicket() {}
    
    public InventoryProductDetailTicket(Inventory inventory, List<InventoryProduct> inventoryProducts)
    {
        this.inventory = inventory;
        this.inventoryProducts = inventoryProducts;
    }
    
    public void print()
    {
        
        PrinterMatrix matrix = new PrinterMatrix();
        
        //Definir el tamaño del papel para la impresion: 20 + "Total de productos" lineas y 65 columnas
        matrix.setOutSize(20 + inventoryProducts.size(), 65);
        matrix.printTextWrap(1, 2, 1, 65, "EXPEDIDO EN: {{issued}}".replace("{{issued}}", "ABARROTES KRLOS"));
        matrix.printTextWrap(2, 3, 1, 65, "{{address}}".replace("{{address}}", "AV. UNIVERSIDAD #200 ESCOBEDO, N.L."));
        matrix.printCharAtCol(4, 1, 65, "=");
        //Imprimir encabezado de la empresa
        matrix.printTextWrap(4, 5, 22, 65, "DETALLE DE INVENTARIO");
        matrix.printTextWrap(5, 6, 1, 20, "NO. BOLETA: {{ticket}}".replace("{{ticket}}", inventory.getId().toString()));
        matrix.printTextWrap(6, 7, 1, 30, "FECHA DE EMISION: {{date}}".replace("{{date}}",
            new SimpleDateFormat("yyyy-MMM-dd").format(inventory.getCreatedDate().getTime())));
        matrix.printTextWrap(6, 7, 36, 65, "HORA: {{hour}}".replace("{{hour}}",
            new SimpleDateFormat("HH:mm:ss").format(inventory.getCreatedDate().getTime())));
        matrix.printTextWrap(7, 8, 1, 65, "CAJA: {{box}}".replace("{{box}}","045"));
        matrix.printTextWrap(8, 9, 1, 65, "GENERADO POR: {{cashier}}".replace("{{cashier}}",
            inventory.getCreatedBy().getName()));
        matrix.printCharAtCol(10, 1, 65, "-");
        matrix.printTextWrap(10, 11, 0, 49, "|DESCRIPCION");
        matrix.printTextWrap(10, 11, 50, 65, "|CANT");
        matrix.printCharAtCol(12, 1, 65, "-");
        
        int rowIndex = 11;
        for (InventoryProduct ip : inventoryProducts)
        {
            matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 1, 50, "{{desc}}".replace("{{desc}}",
                ip.getProduct().getDescription()));
            matrix.printTextWrap(rowIndex + 1, rowIndex + 2, 51, 65, "{{qty}}".replace("{{qty}}",
                ip.getProduct().getUnitsInStock().toString()));
            rowIndex = rowIndex + 1;
        }
        matrix.printCharAtCol(rowIndex + 2, 1, 65, "=");
        matrix.printTextWrap(rowIndex + 2, rowIndex + 3, 31, 65, "TOTAL DE PRODUCTOS: {{total}}".replace("{{total}}",
            String.valueOf(inventoryProducts.size())));
        matrix.printCharAtCol(rowIndex + 4, 1, 65, "=");
        matrix.printTextWrap(rowIndex + 5, rowIndex + 6, 21, 65, "¡GRACIAS POR SU COMPRA!");
        matrix.printTextWrap(rowIndex + 6, rowIndex + 7, 18, 65, "ESPERAMOS SU VISITA NUEVAMENTE");
        matrix.printTextWrap(rowIndex + 7, rowIndex + 8, 20, 65, "NO SE ACEPTAN DEVOLUCIONES\f");
        
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        matrix.toFile(System.getProperty("user.dir").concat("\\tickets\\inventory\\")
            .concat(sdf.format(now).toUpperCase()).concat("I").concat(inventory.getId().toString())
                .concat(".txt"));
        
        byte[] bytes = null;
        try
        {
            bytes = Files.readAllBytes(
                Paths.get(new File(System.getProperty("user.dir").concat("\\tickets\\inventory\\")
                    .concat(sdf.format(now).toUpperCase()).concat("I").concat(inventory.getId().toString())
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
        }/**/
        
    }
    
}

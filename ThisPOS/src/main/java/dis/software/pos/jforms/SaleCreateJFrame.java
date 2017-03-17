/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.OptionPane;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.InventoryProductPk;
import dis.software.pos.entities.InventoryProductRequest;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.Promotion;
import dis.software.pos.entities.Sale;
import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.SalePromotionDetail;
import dis.software.pos.interfaces.IInventoryProduct;
import dis.software.pos.interfaces.IInventoryProductRequest;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.interfaces.IPromotion;
import dis.software.pos.interfaces.ISale;
import dis.software.pos.jforms.components.MoneyCollectionComponent;
import dis.software.pos.jforms.components.SearchProductComponent;
import dis.software.pos.list.model.SaleDetailListModel;
import dis.software.pos.tickets.SaleTicket;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

/**
 * Formulario para las ventas
 * @author Milton Cavazos
 */
public class SaleCreateJFrame extends javax.swing.JFrame
{
    
    Timer timer = null;
    Integer clipCount = 0;
    
    List<SaleDetail> saleDetails = new ArrayList<>();
    List<InventoryProductRequest> inventoryProductRequests = new ArrayList<>();
    
    /**
     * Creación de nuevo formulario SaleCreateJFrame
     */
    public SaleCreateJFrame()
    {
        init();
    }
    
    /**
     * Creación de nuevo formulario SaleCreateJFrame en base a una o más solicitudes
     * @param saleDetails Detalles de venta
     * @param inventoryProductRequests Solicitudes de producto al cotejar inventario
     */
    public SaleCreateJFrame(List<SaleDetail> saleDetails,
        List<InventoryProductRequest> inventoryProductRequests)
    {
        
        this.saleDetails = saleDetails;
        this.inventoryProductRequests = inventoryProductRequests;
        
        init();
        
    }
    
    /**
     * Creación de nuevo formulario SalesJFrame
     */
    private void init()
    {
        
        super.setUndecorated(true);
        //super.setAlwaysOnTop(true);
        super.setResizable(false);
        //super.setVisible(true);
        
        initComponents();
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int) toolkit.getScreenSize().getWidth();
        int y = (int) toolkit.getScreenSize().getHeight();
        
        super.setSize(x, y);
        
        jtxtProductCode.getInputMap(JComponent.WHEN_FOCUSED)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ADD");
        jtxtProductCode.getActionMap().put("ADD", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                add();
            }
        }); //Se ejecuta al presionar: ENTER
        
        JRootPane jRootPane = super.getRootPane();
        
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), "CLEAR");
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), "REMOVE");
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "SEARCH");
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "SELL");
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "CLOSE");
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "CLIPBOARD-BACKWARD");
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "CLIPBOARD-FORWARD");
        
        Action clearAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                clear();
            }
        };
        Action removeAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (jbtnRemove.isEnabled())
                {
                    remove();
                }
            }
        };
        Action searchAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                search();
            }
        };
        Action sellAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (jbtnSell.isEnabled())
                {
                    sell();
                }
            }
        };
        Action closeAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                close();
            }
        };
        Action clipBackward = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                backward();
            }
        };
        Action clipForward = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                forward();
            }
        };
        
        jRootPane.getActionMap().put("CLEAR", clearAction); //Se ejecuta al presionar: F6
        jRootPane.getActionMap().put("REMOVE", removeAction); //Se ejecuta al presionar: F8
        jRootPane.getActionMap().put("SEARCH", searchAction); //Se ejecuta al presionar: F10
        jRootPane.getActionMap().put("SELL", sellAction); //Se ejecuta al presionar: F12
        jRootPane.getActionMap().put("CLOSE", closeAction); //Se ejecuta al presionar: ESC
        jRootPane.getActionMap().put("CLIPBOARD-BACKWARD", clipBackward); //Se ejecuta al presionar: UP
        jRootPane.getActionMap().put("CLIPBOARD-FORWARD", clipForward); //Se ejecuta al presionar: DOWN
        
        jtxtProductCode.grabFocus();
        
        jbtnPrintLastTicket.setFocusable(false);
        jbtnClear.setFocusable(false);
        jbtnRemove.setFocusable(false);
        jbtnSearch.setFocusable(false);
        jbtnSell.setFocusable(false);
        jlstSaleProducts.setFocusable(false);
        jbtnRemove.setEnabled(false);
        jbtnSell.setEnabled(false);
        
        jtxtUser.setText("ATIENDE: " + ApplicationSession.getUser().getName());
        
        SwingUtilities.invokeLater(() ->
        {
            SaleDetailListModel sdListModel = new SaleDetailListModel();
            sdListModel.setWidth(jlstSaleProducts.getWidth() - 28);
            jlstSaleProducts.setModel(sdListModel);
            
            ((SaleDetailListModel) jlstSaleProducts.getModel()).getPropertyChangeSupport()
                .addPropertyChangeListener(SaleDetailListModel.ADD, (PropertyChangeEvent evt) ->
            {
                jlstSaleProducts.ensureIndexIsVisible(
                    ((SaleDetailListModel) jlstSaleProducts.getModel()).getSize() - 1);
            });
            ((SaleDetailListModel) jlstSaleProducts.getModel()).getPropertyChangeSupport()
                .addPropertyChangeListener(SaleDetailListModel.REMOVE, (PropertyChangeEvent evt) ->
            {
                jlstSaleProducts.ensureIndexIsVisible(
                    ((SaleDetailListModel) jlstSaleProducts.getModel()).getSize() - 1);
            });
            ((SaleDetailListModel) jlstSaleProducts.getModel()).getPropertyChangeSupport()
                .addPropertyChangeListener(SaleDetailListModel.CONTENTS_CHANGED, (PropertyChangeEvent evt) ->
            {
                jlstSaleProducts.ensureIndexIsVisible(
                    ((SaleDetailListModel) jlstSaleProducts.getModel()).getSize() - 1);
            });
            
            if (!saleDetails.isEmpty())
            {
                saleDetails.forEach(saleDetail ->
                {
                    ((SaleDetailListModel) jlstSaleProducts.getModel()).add(saleDetail);
                });
                
                jtxtTotalQuantity.setText(String.valueOf(sdListModel.getTotalQuantity()));
                jtxtSubtotal.setText(String.format("$%1$,.2f", sdListModel.getSubtotal()));
                jtxtTax.setText(String.format("$%1$,.2f", sdListModel.getTax()));
                jtxtDiscount.setText(String.format("$%1$,.2f", sdListModel.getTotalDiscount()));
                jtxtTotalAmount.setText(String.format("$%1$,.2f", sdListModel.getTotalAmount()));
                
                jbtnRemove.setEnabled(true);
                jbtnSell.setEnabled(true);
            }
        });
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para buscar un producto">
    private void search()
    {
        
        JDialog jdSearchProduct = new JDialog(this);
        SearchProductComponent searchProductComponent = new SearchProductComponent(jdSearchProduct);
        jdSearchProduct.add(searchProductComponent.getContentPane());
        jdSearchProduct.setSize(searchProductComponent.getSize());
        jdSearchProduct.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jdSearchProduct.setLocationRelativeTo(this);
        jdSearchProduct.setVisible(true);

        if (searchProductComponent.getProduct() != null)
        {
            jtxtProductCode.setText(searchProductComponent.getProduct().getCode());
        }
        
        searchProductComponent.dispose();
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para agregar un producto">
    private void add()
    {
        
        Product product;
        Promotion promotion = null;
        
        TimerTask timerTask = new TimerTask()
        {
            Integer seconds = 0;
            @Override
            public void run()
            {
                seconds++;
                jlblProductCode.setVisible(seconds % 2 == 0);
            }
        };
        
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        String productCode = !jtxtProductCode.getText().matches(".*[\\*\\-].*")
            ? jtxtProductCode.getText().trim()
            : jtxtProductCode.getText().trim().substring(0, jtxtProductCode.getText().trim().contains("*")
                ? jtxtProductCode.getText().trim().indexOf("*")
                : jtxtProductCode.getText().trim().indexOf("-"));
        product = iProduct.findByCode(productCode);
        
        //Si el producto no existe buscar como código de promoción
        if (product == null)
        {
            IPromotion iPromotion = Application.getContext().getBean(IPromotion.class);
            promotion = iPromotion.findByCode(productCode);
            //Si la promoción no existe
            if (promotion == null)
            {
                //Si el timer esta corriendo: REGRESAR
                if (timer != null) return;
                
                jlblProductCode.setText("CÓDIGO INCORRECTO");
                jlblProductCode.setForeground(Color.YELLOW);
                jtxtProductCode.setBackground(new Color(255, 102, 102));
                //INICIAR timer
                timer = new Timer();
                timer.schedule(timerTask, 0, 500);
                return;
            }
        }
        
        //Si el producto o promoción existe y el timer esta corriendo: DETENER
        if (timer != null)
        {
            timer.cancel();
            timer = null;
            jlblProductCode.setText("CÓDIGO DE PRODUCTO");
            jlblProductCode.setForeground(Color.WHITE);
            jlblProductCode.setVisible(true);
            jtxtProductCode.setBackground(Color.WHITE);
        }
        
        if (jtxtProductCode.getText().endsWith("-"))
        {
            remove();
            return;
        }
        
        Integer quantity = !jtxtProductCode.getText().contains("*") ? 1
            : new Integer(jtxtProductCode.getText().substring(jtxtProductCode.getText().indexOf("*") + 1));
        
        if (product != null)
        {
            SaleDetail saleDetail = new SaleDetail();
            
            saleDetail.setProduct(product);
            saleDetail.setPrice(product.getPrice());
            //Se obtiene la hora configurada para la venta "Fuera de horario"
            Calendar time = Calendar.getInstance();
            time.setTime(Application.getSetting().getOutOfTime());
            //Se corrige el año por que se interpreta como 1970 al obtener la hora de la base de datos
            Calendar outOfTime = Calendar.getInstance();
            outOfTime.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
            outOfTime.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
            outOfTime.set(Calendar.SECOND, time.get(Calendar.SECOND));
            //Si la hora del sistema es posterior a la configurada
            if (new GregorianCalendar().after(outOfTime))
            {
                saleDetail.setPrice(product.getOutOfTimePrice());
            }
            saleDetail.setDiscount(0.00);
            //Si el producto tiene un descuento vigente
            if (product.getDiscountExpirationDate() != null
                && product.getDiscountExpirationDate().compareTo(Calendar.getInstance()) > 0)
            {
                saleDetail.setDiscount(product.getDiscount());
            }
            saleDetail.setTotalDiscount(
                BigDecimal.valueOf(saleDetail.getDiscount() * quantity)
                    .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
            saleDetail.setAmount(
                BigDecimal.valueOf((saleDetail.getPrice() * quantity) - (saleDetail.getDiscount() * quantity))
                    .setScale(2, RoundingMode.HALF_EVEN).doubleValue()
            );
            saleDetail.setCost(product.getCost());
            saleDetail.setTotalCost(
                BigDecimal.valueOf(product.getCost() * quantity)
                    .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
            saleDetail.setQuantity(quantity);
            saleDetail.setCreatedBy(ApplicationSession.getUser());

            sdListModel.add(saleDetail);
        }
        else
        {
            //Si la promoción esta dentro del rango de fechas
            if (promotion.getBeginDate() != null
                && promotion.getExpirationDate() != null
                && promotion.getBeginDate().compareTo(Calendar.getInstance()) <= 0
                && promotion.getExpirationDate().compareTo(Calendar.getInstance()) > 0)
            {
                SalePromotionDetail salePromotionDetail = new SalePromotionDetail();
                
                salePromotionDetail.setPromotion(promotion);
                salePromotionDetail.setPrice(promotion.getPrice());
                salePromotionDetail.setAmount(
                    BigDecimal.valueOf(promotion.getPrice() * quantity)
                        .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                salePromotionDetail.setCost(promotion.getCost());
                salePromotionDetail.setTotalCost(
                    BigDecimal.valueOf(promotion.getCost() * quantity)
                        .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                salePromotionDetail.setQuantity(quantity);
                salePromotionDetail.setCreatedBy(ApplicationSession.getUser());

                sdListModel.add(salePromotionDetail);
            }
            else
            {
                //Si el timer esta corriendo: REGRESAR
                if (timer != null) return;
                
                jlblProductCode.setText("LA PROMOCIÓN HA EXPIRADO");
                jlblProductCode.setForeground(Color.YELLOW);
                jtxtProductCode.setBackground(new Color(255, 102, 102));
                //INICIAR timer
                timer = new Timer();
                timer.schedule(timerTask, 0, 750);
                return;
            }
        }

        jtxtTotalQuantity.setText(String.valueOf(sdListModel.getTotalQuantity()));
        jtxtSubtotal.setText(String.format("$%1$,.2f", sdListModel.getSubtotal()));
        jtxtTax.setText(String.format("$%1$,.2f", sdListModel.getTax()));
        jtxtDiscount.setText(String.format("$%1$,.2f", sdListModel.getTotalDiscount()));
        jtxtTotalAmount.setText(String.format("$%1$,.2f", sdListModel.getTotalAmount()));

        jtxtProductCode.setText("");
        jtxtProductCode.grabFocus();
        jbtnRemove.setEnabled(true);
        jbtnSell.setEnabled(true);
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para quitar un producto">
    private void remove()
    {
        
        //SaleCreateJFrame salesJFrame = this;
        String jlblMsg = new String();
                    
        if (jlstSaleProducts.getSelectedIndex() <= 0
            && clipCount == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione un producto para continuar.",
                " Eliminar producto (F8)", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        Object selectedObj = sdListModel.get(clipCount == 0 ?
            jlstSaleProducts.getSelectedIndex() : clipCount);
        
        if (selectedObj instanceof SaleDetail)
        {
            jlblMsg = "<html>¿Desea eliminar el producto: <b>"
                + ((SaleDetail) selectedObj).getProduct().getDescription() + "</b>?</html>";
        }
        else if (selectedObj instanceof SalePromotionDetail)
        {
            jlblMsg = "<html>¿Desea eliminar la promoción: <b>"
                + ((SalePromotionDetail) selectedObj).getPromotion().getDescription() + "</b>?</html>";
        }

        if (OptionPane.showConfirmDialog(this, jlblMsg, " Eliminar producto (F8)",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
        {
            sdListModel.remove(jlstSaleProducts.getSelectedIndex());
            
            jtxtSubtotal.setText(String.format("$%1$,.2f", sdListModel.getSubtotal()));
            jtxtTax.setText(String.format("$%1$,.2f", sdListModel.getTax()));
            jtxtDiscount.setText(String.format("$%1$,.2f", sdListModel.getTotalDiscount()));
            jtxtTotalAmount.setText(String.format("$%1$,.2f", sdListModel.getTotalAmount()));
            jtxtTotalQuantity.setText(String.valueOf(sdListModel.getSize()-1));
            
            jtxtProductCode.setText("");
            clipCount = 0;
            jlstSaleProducts.setSelectedIndex(-1);
            
            if (sdListModel.getSize() == 1)
            {
                jbtnRemove.setEnabled(false);
                jbtnSell.setEnabled(false);
            }
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para limpiar la pantalla">
    private void clear()
    {
        
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        sdListModel.removeAll();
        //Se reinician todas las cantidades
        jtxtSubtotal.setText(String.format("$%1$,.2f", sdListModel.getSubtotal()));
        jtxtTax.setText(String.format("$%1$,.2f", sdListModel.getTax()));
        jtxtDiscount.setText(String.format("$%1$,.2f", sdListModel.getTotalDiscount()));
        jtxtTotalAmount.setText(String.format("$%1$,.2f", sdListModel.getTotalAmount()));
        jtxtTotalQuantity.setText(String.valueOf(sdListModel.getSize()-1));
        
        //Si el timer esta corriendo: DETENER
        if (timer != null)
        {
            timer.cancel();
            timer = null;
            jlblProductCode.setText("CÓDIGO DE PRODUCTO");
            jlblProductCode.setForeground(Color.WHITE);
            jlblProductCode.setVisible(true);
        }
        
        jtxtProductCode.setText("");
        jtxtProductCode.setBackground(Color.WHITE);
        jtxtProductCode.grabFocus();
        jbtnRemove.setEnabled(false);
        jbtnSell.setEnabled(false);
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para concretar la venta">
    private void sell()
    {
        
        //SaleCreateJFrame salesJFrame = this;
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        if (sdListModel.getSize() <= 1)
        {
            OptionPane.showMessageDialog(this, "Agregue algunos productos para continuar.",
                " Cobrar venta (F12)", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        JDialog jdMoneyCollection = new JDialog(this);
        MoneyCollectionComponent component = new MoneyCollectionComponent(
            jdMoneyCollection, sdListModel.getTotalAmount());
        jdMoneyCollection.add(component.getContentPane());
        jdMoneyCollection.setSize(component.getSize());
        jdMoneyCollection.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        jdMoneyCollection.setLocationRelativeTo(this);
        jdMoneyCollection.setVisible(true);
        
        ISale iSale = Application.getContext().getBean(ISale.class);
        
        Sale sale = new Sale(
            sdListModel.getSubtotal(),
            sdListModel.getTax(),
            sdListModel.getTotalAmount());
        sale.setCost(sdListModel.getCost());
        sale.setPaidAmount(MoneyCollectionComponent.getPaidAmount());
        sale.setRefundAmount(MoneyCollectionComponent.getRefundAmount());
        sale.setCreatedBy(ApplicationSession.getUser());
        
        sdListModel.getAll().forEach((entity) ->
        {
            if (entity instanceof SaleDetail
                && ((SaleDetail) entity).getProduct() != null)
            {
                ((SaleDetail) entity).setSale(sale);
                sale.getSaleDetails().add((SaleDetail) entity);
            }
            if (entity instanceof SalePromotionDetail
                && ((SalePromotionDetail) entity).getPromotion() != null)
            {
                ((SalePromotionDetail) entity).setSale(sale);
                sale.getSalePromotionDetails().add((SalePromotionDetail) entity);
            }
        });
        iSale.save(sale);
        
        if (sale.getId() != null)
        {
            if (!inventoryProductRequests.isEmpty())
            {
                IInventoryProduct iInventoryProduct = Application.getContext().getBean(IInventoryProduct.class);
                IInventoryProductRequest iInventoryProductRequest =
                    Application.getContext().getBean(IInventoryProductRequest.class);
                
                List<InventoryProduct> inventoryProducts = new ArrayList<>();
                inventoryProductRequests.stream().map((ipr) ->
                {
                    InventoryProduct inventoryProduct = new InventoryProduct(
                        new InventoryProductPk(ipr.getInventory(), ipr.getProduct(), ipr.getUnit()));
                    return inventoryProduct;
                })
                .forEach((ip) ->
                {
                    inventoryProducts.add(ip);
                });
                //Se bloquean los registros de inventario por "SISTEMA" que ya fueron atendidos
                iInventoryProduct.lockAll(inventoryProducts);
                iInventoryProductRequest.deleteAll(inventoryProductRequests);
            }
        }
        
        //Se agrega a la lista de venta el monto pagado por el cliente y su cambio
        sdListModel.setPaidAmount(sale.getPaidAmount());
        sdListModel.setRefundAmount(sale.getRefundAmount());
        sdListModel.fireContentsChanged(); //Se dispara el evento para actualizar la lista de venta
        
        component.dispose();
        
        jtxtProductCode.setText("");
        jtxtProductCode.grabFocus();
        jbtnRemove.setEnabled(false);
        jbtnSell.setEnabled(false);
        
        SaleTicket saleTicket = new SaleTicket(sale, sdListModel.getAll());
        saleTicket.print();
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para cerrar el punto de venta">
    private void close()
    {
        
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        String jlblMsg;
        
        if (sdListModel.getSize() > 1)
        {
            jlblMsg = "<html>Aún queda una venta por concretar.<br>"
                + "¿Está seguro de que quiere cerrar el punto de venta?</html>";
            if (OptionPane.showConfirmDialog(this, jlblMsg, " Salir (ESC)",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
            {
                return;
            }
        }
        //Si el timer esta corriendo: DETENER
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
        this.dispose();
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para retroceder en la lista de productos">
    private void backward()
    {
        
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        if (jtxtProductCode.getText().isEmpty())
        {
            clipCount = sdListModel.getSize();
        }
        if (clipCount > 1)
        {
            Object obj = sdListModel.get(clipCount - 1);
            if (obj instanceof SaleDetail)
            {
                jtxtProductCode.setText(((SaleDetail) obj).getProduct().getCode());
            }
            else if (obj instanceof SalePromotionDetail)
            {
                jtxtProductCode.setText(((SalePromotionDetail) obj).getPromotion().getCode());
            }
            jlstSaleProducts.setSelectedIndex(clipCount - 1);
            clipCount = clipCount - 1;
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para avanzar en la lista de productos">
    private void forward()
    {
        
        SaleDetailListModel sdListModel = (SaleDetailListModel) jlstSaleProducts.getModel();
        
        if (jtxtProductCode.getText().isEmpty())
        {
            clipCount = 0;
        }
        if (clipCount < sdListModel.getSize() - 1)
        {
            Object obj = sdListModel.get(clipCount + 1);
            if (obj instanceof SaleDetail)
            {
                jtxtProductCode.setText(((SaleDetail) obj).getProduct().getCode());
            }
            else if (obj instanceof SalePromotionDetail)
            {
                jtxtProductCode.setText(((SalePromotionDetail) obj).getPromotion().getCode());
            }
            jlstSaleProducts.setSelectedIndex(clipCount + 1);
            clipCount = clipCount + 1;
        }
        
    }
    //</editor-fold>
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelProducts = new javax.swing.JPanel();
        jbtnPrintLastTicket = new javax.swing.JButton();
        jbtnClear = new javax.swing.JButton();
        jbtnRemove = new javax.swing.JButton();
        jbtnSearch = new javax.swing.JButton();
        jbtnSell = new javax.swing.JButton();
        jscrollPaneSaleProducts = new javax.swing.JScrollPane();
        jlstSaleProducts = new javax.swing.JList<>();
        jpanelProductsQuantity = new javax.swing.JPanel();
        jlblTotalQuantity = new javax.swing.JLabel();
        jtxtTotalQuantity = new javax.swing.JLabel();
        jpanelSubtotal = new javax.swing.JPanel();
        jlblSubtotal = new javax.swing.JLabel();
        jtxtSubtotal = new javax.swing.JLabel();
        jpanelTax = new javax.swing.JPanel();
        jlblTax = new javax.swing.JLabel();
        jtxtTax = new javax.swing.JLabel();
        jpanelDiscount = new javax.swing.JPanel();
        jlblDiscount = new javax.swing.JLabel();
        jtxtDiscount = new javax.swing.JLabel();
        jpanelTotalAmount = new javax.swing.JPanel();
        jlblTotalAmount = new javax.swing.JLabel();
        jtxtTotalAmount = new javax.swing.JLabel();
        jpanelProduct = new javax.swing.JPanel();
        jlblProductCode = new javax.swing.JLabel();
        jtxtProductCode = new javax.swing.JTextField();
        jpanelExtra = new javax.swing.JPanel();
        jtxtUser = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("jFrameSales"); // NOI18N

        jbtnPrintLastTicket.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnPrintLastTicket.setText("<html><center><b>F4</b><br>Imprimir<br>último<br>ticket</center></html>");
        jbtnPrintLastTicket.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnPrintLastTicket.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jbtnPrintLastTicket.setPreferredSize(new java.awt.Dimension(90, 85));
        jbtnPrintLastTicket.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnPrintLastTicketActionPerformed(evt);
            }
        });

        jbtnClear.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnClear.setText("<html><center><b>F6</b><br>Limpiar<br>pantalla</center></html>");
        jbtnClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnClear.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jbtnClear.setPreferredSize(new java.awt.Dimension(90, 85));
        jbtnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnClearActionPerformed(evt);
            }
        });

        jbtnRemove.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnRemove.setText("<html><center><b>F8</b><br>Eliminar<br>producto</center></html>");
        jbtnRemove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnRemove.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jbtnRemove.setPreferredSize(new java.awt.Dimension(90, 85));
        jbtnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRemoveActionPerformed(evt);
            }
        });

        jbtnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnSearch.setText("<html><center><b>F10</b><br>Buscar</center></html>");
        jbtnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSearch.setPreferredSize(new java.awt.Dimension(90, 85));
        jbtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSearchActionPerformed(evt);
            }
        });

        jbtnSell.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnSell.setText("<html><center><b>F12</b><br>Cobrar</center></html>");
        jbtnSell.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSell.setPreferredSize(new java.awt.Dimension(90, 85));
        jbtnSell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSellActionPerformed(evt);
            }
        });

        jscrollPaneSaleProducts.setPreferredSize(new java.awt.Dimension(259, 306));

        jlstSaleProducts.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jlstSaleProducts.setFont(new java.awt.Font("Consolas", 0, 22)); // NOI18N
        jlstSaleProducts.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jscrollPaneSaleProducts.setViewportView(jlstSaleProducts);

        jpanelProductsQuantity.setBackground(new java.awt.Color(0, 204, 51));
        jpanelProductsQuantity.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jlblTotalQuantity.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblTotalQuantity.setForeground(new java.awt.Color(0, 0, 0));
        jlblTotalQuantity.setText("TOTAL DE PRODUCTOS:");

        jtxtTotalQuantity.setFont(new java.awt.Font("Consolas", 0, 22)); // NOI18N
        jtxtTotalQuantity.setForeground(new java.awt.Color(0, 0, 0));
        jtxtTotalQuantity.setText("0");

        javax.swing.GroupLayout jpanelProductsQuantityLayout = new javax.swing.GroupLayout(jpanelProductsQuantity);
        jpanelProductsQuantity.setLayout(jpanelProductsQuantityLayout);
        jpanelProductsQuantityLayout.setHorizontalGroup(
            jpanelProductsQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProductsQuantityLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTotalQuantity)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtTotalQuantity)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpanelProductsQuantityLayout.setVerticalGroup(
            jpanelProductsQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProductsQuantityLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jpanelProductsQuantityLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTotalQuantity)
                    .addComponent(jlblTotalQuantity))
                .addGap(6, 6, 6))
        );

        jpanelSubtotal.setBackground(new java.awt.Color(0, 204, 51));
        jpanelSubtotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jpanelSubtotal.setPreferredSize(new java.awt.Dimension(200, 86));

        jlblSubtotal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblSubtotal.setForeground(new java.awt.Color(0, 0, 0));
        jlblSubtotal.setText("SUBTOTAL:");

        jtxtSubtotal.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jtxtSubtotal.setForeground(new java.awt.Color(0, 0, 0));
        jtxtSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jtxtSubtotal.setText("$0.00");

        javax.swing.GroupLayout jpanelSubtotalLayout = new javax.swing.GroupLayout(jpanelSubtotal);
        jpanelSubtotal.setLayout(jpanelSubtotalLayout);
        jpanelSubtotalLayout.setHorizontalGroup(
            jpanelSubtotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSubtotalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelSubtotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jtxtSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpanelSubtotalLayout.setVerticalGroup(
            jpanelSubtotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelSubtotalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblSubtotal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxtSubtotal)
                .addGap(4, 4, 4))
        );

        jpanelTax.setBackground(new java.awt.Color(0, 204, 51));
        jpanelTax.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jpanelTax.setPreferredSize(new java.awt.Dimension(185, 86));

        jlblTax.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblTax.setForeground(new java.awt.Color(0, 0, 0));
        jlblTax.setText("IMPUESTO:");

        jtxtTax.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jtxtTax.setForeground(new java.awt.Color(0, 0, 0));
        jtxtTax.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jtxtTax.setText("$0.00");

        javax.swing.GroupLayout jpanelTaxLayout = new javax.swing.GroupLayout(jpanelTax);
        jpanelTax.setLayout(jpanelTaxLayout);
        jpanelTaxLayout.setHorizontalGroup(
            jpanelTaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTaxLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelTaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtTax, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblTax, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpanelTaxLayout.setVerticalGroup(
            jpanelTaxLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTaxLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTax)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxtTax)
                .addGap(4, 4, 4))
        );

        jpanelDiscount.setBackground(new java.awt.Color(0, 204, 51));
        jpanelDiscount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jpanelDiscount.setPreferredSize(new java.awt.Dimension(185, 86));

        jlblDiscount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblDiscount.setForeground(new java.awt.Color(0, 0, 0));
        jlblDiscount.setText("SU AHORRO:");

        jtxtDiscount.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jtxtDiscount.setForeground(new java.awt.Color(255, 0, 0));
        jtxtDiscount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jtxtDiscount.setText("$0.00");

        javax.swing.GroupLayout jpanelDiscountLayout = new javax.swing.GroupLayout(jpanelDiscount);
        jpanelDiscount.setLayout(jpanelDiscountLayout);
        jpanelDiscountLayout.setHorizontalGroup(
            jpanelDiscountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelDiscountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelDiscountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlblDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jtxtDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jpanelDiscountLayout.setVerticalGroup(
            jpanelDiscountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelDiscountLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblDiscount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jtxtDiscount)
                .addGap(4, 4, 4))
        );

        jpanelTotalAmount.setBackground(new java.awt.Color(0, 204, 51));
        jpanelTotalAmount.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        jpanelTotalAmount.setPreferredSize(new java.awt.Dimension(200, 86));

        jlblTotalAmount.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jlblTotalAmount.setForeground(new java.awt.Color(0, 0, 0));
        jlblTotalAmount.setText("TOTAL M.N.");

        jtxtTotalAmount.setFont(new java.awt.Font("Consolas", 0, 24)); // NOI18N
        jtxtTotalAmount.setForeground(new java.awt.Color(255, 0, 0));
        jtxtTotalAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jtxtTotalAmount.setText("$0.00");

        javax.swing.GroupLayout jpanelTotalAmountLayout = new javax.swing.GroupLayout(jpanelTotalAmount);
        jpanelTotalAmount.setLayout(jpanelTotalAmountLayout);
        jpanelTotalAmountLayout.setHorizontalGroup(
            jpanelTotalAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTotalAmountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelTotalAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(jtxtTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpanelTotalAmountLayout.setVerticalGroup(
            jpanelTotalAmountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelTotalAmountLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jlblTotalAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxtTotalAmount)
                .addGap(4, 4, 4))
        );

        jpanelProduct.setBackground(new java.awt.Color(0, 0, 153));
        jpanelProduct.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jlblProductCode.setFont(new java.awt.Font("Consolas", 0, 20)); // NOI18N
        jlblProductCode.setForeground(new java.awt.Color(255, 255, 255));
        jlblProductCode.setText("CÓDIGO DE PRODUCTO");

        jtxtProductCode.setFont(new java.awt.Font("Consolas", 0, 26)); // NOI18N

        javax.swing.GroupLayout jpanelProductLayout = new javax.swing.GroupLayout(jpanelProduct);
        jpanelProduct.setLayout(jpanelProductLayout);
        jpanelProductLayout.setHorizontalGroup(
            jpanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProductLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelProductLayout.createSequentialGroup()
                        .addComponent(jtxtProductCode)
                        .addGap(20, 20, 20))
                    .addGroup(jpanelProductLayout.createSequentialGroup()
                        .addComponent(jlblProductCode)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jpanelProductLayout.setVerticalGroup(
            jpanelProductLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelProductLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jlblProductCode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jtxtProductCode, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jpanelExtra.setBackground(new java.awt.Color(0, 204, 51));
        jpanelExtra.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));

        jtxtUser.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jtxtUser.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jpanelExtraLayout = new javax.swing.GroupLayout(jpanelExtra);
        jpanelExtra.setLayout(jpanelExtraLayout);
        jpanelExtraLayout.setHorizontalGroup(
            jpanelExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelExtraLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jtxtUser)
                .addGap(8, 8, 8))
        );
        jpanelExtraLayout.setVerticalGroup(
            jpanelExtraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelExtraLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jtxtUser)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpanelProductsLayout = new javax.swing.GroupLayout(jpanelProducts);
        jpanelProducts.setLayout(jpanelProductsLayout);
        jpanelProductsLayout.setHorizontalGroup(
            jpanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProductsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanelProductsQuantity, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jscrollPaneSaleProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelProductsLayout.createSequentialGroup()
                        .addComponent(jpanelProduct, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnPrintLastTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbtnSell, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelProductsLayout.createSequentialGroup()
                        .addComponent(jpanelExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanelSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanelTax, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanelDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jpanelTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jpanelProductsLayout.setVerticalGroup(
            jpanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelProductsLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jpanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnPrintLastTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jpanelProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jpanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbtnSell, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnSearch, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnRemove, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnClear, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneSaleProducts, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelProductsQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelProductsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jpanelExtra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelDiscount, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jpanelTax, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jpanelSubtotal, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                    .addComponent(jpanelTotalAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelProducts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRemoveActionPerformed
        
        remove();
        
    }//GEN-LAST:event_jbtnRemoveActionPerformed

    private void jbtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSearchActionPerformed
        
        search();
        
    }//GEN-LAST:event_jbtnSearchActionPerformed

    private void jbtnSellActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSellActionPerformed
        
        sell();
        
    }//GEN-LAST:event_jbtnSellActionPerformed

    private void jbtnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnClearActionPerformed
        
        clear();
        
    }//GEN-LAST:event_jbtnClearActionPerformed

    private void jbtnPrintLastTicketActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnPrintLastTicketActionPerformed
        
        //Consultar ultima venta realizada y mandar imprimir
        
    }//GEN-LAST:event_jbtnPrintLastTicketActionPerformed

    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SaleCreateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SaleCreateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SaleCreateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SaleCreateJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new SaleCreateJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnClear;
    private javax.swing.JButton jbtnPrintLastTicket;
    private javax.swing.JButton jbtnRemove;
    private javax.swing.JButton jbtnSearch;
    private javax.swing.JButton jbtnSell;
    private javax.swing.JLabel jlblDiscount;
    private javax.swing.JLabel jlblProductCode;
    private javax.swing.JLabel jlblSubtotal;
    private javax.swing.JLabel jlblTax;
    private javax.swing.JLabel jlblTotalAmount;
    private javax.swing.JLabel jlblTotalQuantity;
    private javax.swing.JList<Object> jlstSaleProducts;
    private javax.swing.JPanel jpanelDiscount;
    private javax.swing.JPanel jpanelExtra;
    private javax.swing.JPanel jpanelProduct;
    private javax.swing.JPanel jpanelProducts;
    private javax.swing.JPanel jpanelProductsQuantity;
    private javax.swing.JPanel jpanelSubtotal;
    private javax.swing.JPanel jpanelTax;
    private javax.swing.JPanel jpanelTotalAmount;
    private javax.swing.JScrollPane jscrollPaneSaleProducts;
    private javax.swing.JLabel jtxtDiscount;
    private javax.swing.JTextField jtxtProductCode;
    private javax.swing.JLabel jtxtSubtotal;
    private javax.swing.JLabel jtxtTax;
    private javax.swing.JLabel jtxtTotalAmount;
    private javax.swing.JLabel jtxtTotalQuantity;
    private javax.swing.JLabel jtxtUser;
    // End of variables declaration//GEN-END:variables
}

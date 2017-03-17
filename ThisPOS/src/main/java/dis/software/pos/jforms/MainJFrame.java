/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.EntityStatus;
import dis.software.pos.OptionPane;
import dis.software.pos.Property;
import dis.software.pos.entities.InventoryProductRequest;
import dis.software.pos.entities.Module;
import dis.software.pos.entities.Notification;
import dis.software.pos.entities.ProductRequest;
import dis.software.pos.entities.ProductUnit;
import dis.software.pos.entities.ProfileModule;
import dis.software.pos.entities.PurchaseDetail;
import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IInventoryProductRequest;
import dis.software.pos.interfaces.INotification;
import dis.software.pos.interfaces.IProductRequest;
import dis.software.pos.interfaces.IProductUnit;
import dis.software.pos.jforms.components.NotificationComponent;
import dis.software.pos.jforms.components.SyncComponent;
import dis.software.pos.mail.processors.ProductRequestCancelMailProcessor;
import dis.software.pos.mail.task.MinStockLevelMailTask;
import dis.software.pos.table.cell.renderer.CalendarCellRenderer;
import dis.software.pos.table.model.InventoryProductRequestTableModel;
import dis.software.pos.table.model.ProductRequestTableModel;
import dis.software.pos.tickets.CashOutTicket;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario principal
 * @author Milton Cavazos
 */
public class MainJFrame extends javax.swing.JFrame
{

    private static final Logger logger = LogManager.getLogger(MainJFrame.class.getSimpleName());
    
    /**
     * Creación de nuevo formulario MainJFrame
     */
    public MainJFrame()
    {
        
        initComponents();
        
        jlblCurrentDate.setText((new SimpleDateFormat("dd-MMM-yyyy"))
            .format(new GregorianCalendar().getTime()));
        
        jbtnExecute.setVisible(Boolean.FALSE);
        
        MainJFrame mainJFrame = this;
        mainJFrame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });
        
        jmItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        
        User user = ApplicationSession.getUser();
        if (user.getProfile() == null)
        {
            jMenuUser.setVisible(false);
            jMenuProfile.setVisible(false);
            jMenuInventory.setVisible(false);
            jMenuPurchases.setVisible(false);
            jMenuSales.setVisible(false);
            jMenuCatalogs.setVisible(false);
            jbtnInventoryViewShortcut.setEnabled(false);
            jbtnSale.setEnabled(false);
            jbtnProductViewShortcut.setEnabled(false);
            jbtnCash.setEnabled(false);
            jbtnReport.setEnabled(false);
            jbtnSyncShortcut.setEnabled(false);
            return;
        }
        
        List<ProfileModule> profileModules = new ArrayList<>(user.getProfile().getProfileModules());
        profileModules.forEach(profileModule ->
        {
            //Permisos de módulo: Usuarios
            if (Module.USERS.equals(profileModule.getModule().getCode()))
            {
                jmItemUserView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemUserCreate.setVisible(profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemUserEdit.setVisible(profileModule.getPrivileges().getEditProperty() == Property.ALLOW);
                jmItemUserBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Perfiles
            if (Module.PROFILES.equals(profileModule.getModule().getCode()))
            {
                jmItemProfileView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemProfileCreate.setVisible(profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemProfileEdit.setVisible(profileModule.getPrivileges().getEditProperty() == Property.ALLOW);
                jmItemProfileBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Inventario
            if (Module.INVENTORY.equals(profileModule.getModule().getCode()))
            {
                jmItemInventoryView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jbtnInventoryViewShortcut.setVisible(
                    profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemInventoryIncome.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemInventoryOutcome.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemInventoryCancel.setVisible(
                    profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Compras
            if (Module.PURCHASES.equals(profileModule.getModule().getCode()))
            {
                jmItemPurchaseView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemPurchaseCreate.setVisible(profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemPurchaseBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Ventas
            if (Module.SALES.equals(profileModule.getModule().getCode()))
            {
                jmItemSalesView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemSalesCreate.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jbtnSale.setVisible(profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemSalesBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Categorías
            if (Module.CATEGORIES.equals(profileModule.getModule().getCode()))
            {
                jmItemCategoryView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemCategoryCreate.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemCategoryEdit.setVisible(profileModule.getPrivileges().getEditProperty() == Property.ALLOW);
                jmItemCategoryBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Proveedores
            if (Module.PROVIDERS.equals(profileModule.getModule().getCode()))
            {
                jmItemProviderView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemProviderCreate.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemProviderEdit.setVisible(profileModule.getPrivileges().getEditProperty() == Property.ALLOW);
                jmItemProviderBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Productos
            if (Module.PRODUCTS.equals(profileModule.getModule().getCode()))
            {
                jmItemProductView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jbtnProductViewShortcut.setVisible(
                    profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemProductCreate.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemProductEdit.setVisible(profileModule.getPrivileges().getEditProperty() == Property.ALLOW);
                jmItemProductBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Unidades
            if (Module.UNITS.equals(profileModule.getModule().getCode()))
            {
                jmItemUnitsView.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jmItemUnitsCreate.setVisible(
                    profileModule.getPrivileges().getCreateProperty() == Property.ALLOW);
                jmItemUnitsEdit.setVisible(profileModule.getPrivileges().getEditProperty() == Property.ALLOW);
                jmItemUnitsBin.setVisible(profileModule.getPrivileges().getDeleteProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Notificaciones
            if (Module.NOTIFICATIONS.equals(profileModule.getModule().getCode()))
            {
                jmItemNotifications.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
            }
            
            //Permisos de módulo: Sincronización
            if (Module.SYNCH.equals(profileModule.getModule().getCode()))
            {
                jmItemSynchronize.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
                jbtnSyncShortcut.setVisible(profileModule.getPrivileges().getViewProperty() == Property.ALLOW);
            }
            
        });
        
        Timer timer0 = new Timer("JTABLE-PR");
        timer0.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                
                IProductRequest iProductRequest = Application.getContext().getBean(IProductRequest.class);
                jtableProductRequest.setModel(new ProductRequestTableModel(iProductRequest.findAll()));
                
                jtabbedPaneRequests.setTitleAt(0, "Lista de solicitudes ("
                    + ((ProductRequestTableModel) jtableProductRequest.getModel()).getRowCount() + ")");
                
            }
        }, TimeUnit.SECONDS.toMillis(0), TimeUnit.MINUTES.toMillis(3));
        
        jtableProductRequest.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableProductRequest.setShowHorizontalLines(Boolean.TRUE);
        jtableProductRequest.setGridColor(new Color(179, 179, 179));

        jtableProductRequest.setDefaultRenderer(Calendar.class, new CalendarCellRenderer());

        jtableProductRequest.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        Timer timer1 = new Timer("JTABLE-IPR");
        timer1.scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                
                IInventoryProductRequest iInventoryProductReq =
                    Application.getContext().getBean(IInventoryProductRequest.class);
                jtableInventoryProductRequest.setModel(
                    new InventoryProductRequestTableModel(iInventoryProductReq.findAll()));
                
                jtabbedPaneRequests.setTitleAt(1, "Lista de acciones ("
                    + ((InventoryProductRequestTableModel) jtableInventoryProductRequest.getModel()).getRowCount() + ")");
                
            }
        }, TimeUnit.SECONDS.toMillis(0), TimeUnit.MINUTES.toMillis(3));
        
        jtableInventoryProductRequest.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableInventoryProductRequest.setShowHorizontalLines(Boolean.TRUE);
        jtableInventoryProductRequest.setGridColor(new Color(179, 179, 179));

        jtableInventoryProductRequest.setDefaultRenderer(Calendar.class, new CalendarCellRenderer());

        jtableInventoryProductRequest.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        jtableInventoryProductRequest.getSelectionModel()
            .addListSelectionListener((ListSelectionEvent e) ->
        {
            if (!e.getValueIsAdjusting() && jtableInventoryProductRequest.getSelectedRow() != -1)
            {
                if (((InventoryProductRequestTableModel) jtableInventoryProductRequest.getModel())
                    .get(jtableInventoryProductRequest.getSelectedRow()).getRequiredUnits() < 0)
                {
                    jbtnExecute.setIcon(
                        new ImageIcon(getClass().getResource("/images/cart-in-w.png")));
                    jbtnExecute.setBackground(new Color(51, 51, 255));
                    jbtnExecute.setText("Comprar");
                    jbtnExecute.setVisible(Boolean.TRUE);
                    return;
                }
                jbtnExecute.setIcon(
                    new ImageIcon(getClass().getResource("/images/cart-out-w.png")));
                jbtnExecute.setBackground(new Color(17, 157, 17));
                jbtnExecute.setText("Vender");
                jbtnExecute.setVisible(Boolean.TRUE);
            }
        });
        
        //Se agenda una tarea para que se ejecute cada hora
        Timer timer2 = new Timer("MAIL-TIMER");
        TimerTask timerTask = new MinStockLevelMailTask();
        timer2.scheduleAtFixedRate(timerTask, TimeUnit.MINUTES.toMillis(5), TimeUnit.HOURS.toMillis(1));
        
    }
    
    @Override
    public void setDefaultCloseOperation(int operation)
    {
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para salir del sistema">
    private void exit()
    {
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere salir del sistema?",
            " Mensaje del sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.YES_OPTION)
        {
            logger.info("¿Window closed? ¡YES!");
            System.exit(0);
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para verificar una ventana">
    private Boolean isWindowOpened(Class<? extends JInternalFrame> window)
    {
        Boolean isOpened = Boolean.FALSE;
        for (Component component : desktopPane.getComponents())
        {
            if (window.isInstance(component))
            {
                isOpened = Boolean.TRUE;
                break;
            }
        }
        return isOpened;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para obtener una ventana">
    private Component getWindow(Class<? extends JInternalFrame> window)
    {
        Component openWindow = null;
        for (Component component : desktopPane.getComponents())
        {
            if (window.isInstance(component))
            {
                openWindow = component;
                break;
            }
        }
        return openWindow;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para construir una ventana">
    private void build(Class<? extends JInternalFrame> clazz)
    {
        if (isWindowOpened(clazz))
        {
            JInternalFrame window = clazz.cast(getWindow(clazz));
            try
            {
                if (window.isIcon())
                {
                    window.setMaximum(true);
                }
                window.toFront();
                window.requestFocus();
            }
            catch (PropertyVetoException ex)
            {
                logger.error(ex);
            }
            return;
        }
        
        try
        {
            JInternalFrame window = clazz.newInstance();
            desktopPane.add(window);
            window.pack();
            window.setMaximum(true);
            window.setVisible(true);
        }
        catch (PropertyVetoException | InstantiationException | IllegalAccessException ex)
        {
            logger.error("Error", ex);
        }
    }
    //</editor-fold>
    
    private JOptionPane getOptionPane(JComponent parent)
    {
        JOptionPane pane;
        if (!(parent instanceof JOptionPane))
        {
            pane = getOptionPane((JComponent) parent.getParent());
        }
        else
        {
            pane = (JOptionPane) parent;
        }
        return pane;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktopPane = new javax.swing.JDesktopPane();
        jpanelToolbar = new javax.swing.JPanel();
        jbtnInventoryViewShortcut = new javax.swing.JButton();
        jbtnSale = new javax.swing.JButton();
        jbtnProductViewShortcut = new javax.swing.JButton();
        jbtnCash = new javax.swing.JButton();
        jbtnReport = new javax.swing.JButton();
        jbtnSyncShortcut = new javax.swing.JButton();
        jlblCurrentDate = new javax.swing.JLabel();
        jtabbedPaneRequests = new javax.swing.JTabbedPane();
        jpanelProductRequests = new javax.swing.JPanel();
        jlblHeaderProductRequest = new javax.swing.JLabel();
        jbtnDeleteProductRequest = new javax.swing.JButton();
        jscrollPaneTableRequest = new javax.swing.JScrollPane();
        jtableProductRequest = new javax.swing.JTable();
        jpaneInventoryProductRequests = new javax.swing.JPanel();
        jlblHeaderInventoryProductRequest = new javax.swing.JLabel();
        jscrollPaneTableActions = new javax.swing.JScrollPane();
        jtableInventoryProductRequest = new javax.swing.JTable();
        jbtnExecute = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        jMenuSystem = new javax.swing.JMenu();
        jmItemConfigure = new javax.swing.JMenuItem();
        jsepConfigure = new javax.swing.JPopupMenu.Separator();
        jmItemLogout = new javax.swing.JMenuItem();
        jsepLogout = new javax.swing.JPopupMenu.Separator();
        jmItemExit = new javax.swing.JMenuItem();
        jMenuUser = new javax.swing.JMenu();
        jmItemUserView = new javax.swing.JMenuItem();
        jmItemUserCreate = new javax.swing.JMenuItem();
        jmItemUserEdit = new javax.swing.JMenuItem();
        jmItemUserBin = new javax.swing.JMenuItem();
        jMenuProfile = new javax.swing.JMenu();
        jmItemProfileView = new javax.swing.JMenuItem();
        jmItemProfileCreate = new javax.swing.JMenuItem();
        jmItemProfileEdit = new javax.swing.JMenuItem();
        jmItemProfileBin = new javax.swing.JMenuItem();
        jMenuInventory = new javax.swing.JMenu();
        jmItemInventoryView = new javax.swing.JMenuItem();
        jmItemInventoryCancel = new javax.swing.JMenuItem();
        jsepInventoryBin = new javax.swing.JPopupMenu.Separator();
        jmItemInventoryIncome = new javax.swing.JMenuItem();
        jmItemInventoryOutcome = new javax.swing.JMenuItem();
        jMenuPurchases = new javax.swing.JMenu();
        jmItemPurchaseView = new javax.swing.JMenuItem();
        jmItemPurchaseCreate = new javax.swing.JMenuItem();
        jmItemPurchaseBin = new javax.swing.JMenuItem();
        jMenuSales = new javax.swing.JMenu();
        jmItemSalesView = new javax.swing.JMenuItem();
        jmItemSalesCreate = new javax.swing.JMenuItem();
        jmItemSalesBin = new javax.swing.JMenuItem();
        jsepBin = new javax.swing.JPopupMenu.Separator();
        jmItemSalesCashOut = new javax.swing.JMenuItem();
        jMenuCatalogs = new javax.swing.JMenu();
        jMenuCategory = new javax.swing.JMenu();
        jmItemCategoryView = new javax.swing.JMenuItem();
        jmItemCategoryCreate = new javax.swing.JMenuItem();
        jmItemCategoryEdit = new javax.swing.JMenuItem();
        jmItemCategoryBin = new javax.swing.JMenuItem();
        jMenuProvider = new javax.swing.JMenu();
        jmItemProviderView = new javax.swing.JMenuItem();
        jmItemProviderCreate = new javax.swing.JMenuItem();
        jmItemProviderEdit = new javax.swing.JMenuItem();
        jmItemProviderBin = new javax.swing.JMenuItem();
        jMenuProduct = new javax.swing.JMenu();
        jmItemProductView = new javax.swing.JMenuItem();
        jmItemProductCreate = new javax.swing.JMenuItem();
        jmItemProductEdit = new javax.swing.JMenuItem();
        jmItemProductBin = new javax.swing.JMenuItem();
        jMenuUnits = new javax.swing.JMenu();
        jmItemUnitsView = new javax.swing.JMenuItem();
        jmItemUnitsCreate = new javax.swing.JMenuItem();
        jmItemUnitsEdit = new javax.swing.JMenuItem();
        jmItemUnitsBin = new javax.swing.JMenuItem();
        jmItemNotifications = new javax.swing.JMenuItem();
        jMenuHelp = new javax.swing.JMenu();
        jmItemSynchronize = new javax.swing.JMenuItem();
        jmItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("mainJFrame"); // NOI18N

        jpanelToolbar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 2, true));

        jbtnInventoryViewShortcut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnInventoryViewShortcut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/stock.png"))); // NOI18N
        jbtnInventoryViewShortcut.setText("Inventario");
        jbtnInventoryViewShortcut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnInventoryViewShortcut.setFocusable(false);
        jbtnInventoryViewShortcut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnInventoryViewShortcut.setMaximumSize(new java.awt.Dimension(100, 85));
        jbtnInventoryViewShortcut.setOpaque(false);
        jbtnInventoryViewShortcut.setPreferredSize(new java.awt.Dimension(100, 85));
        jbtnInventoryViewShortcut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnInventoryViewShortcut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnInventoryViewShortcutActionPerformed(evt);
            }
        });

        jbtnSale.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnSale.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/shopping-basket.png"))); // NOI18N
        jbtnSale.setText("Venta");
        jbtnSale.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSale.setFocusable(false);
        jbtnSale.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnSale.setMaximumSize(new java.awt.Dimension(100, 85));
        jbtnSale.setOpaque(false);
        jbtnSale.setPreferredSize(new java.awt.Dimension(100, 85));
        jbtnSale.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSaleActionPerformed(evt);
            }
        });

        jbtnProductViewShortcut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnProductViewShortcut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cart.png"))); // NOI18N
        jbtnProductViewShortcut.setText("Articulos");
        jbtnProductViewShortcut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnProductViewShortcut.setFocusable(false);
        jbtnProductViewShortcut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnProductViewShortcut.setMaximumSize(new java.awt.Dimension(100, 85));
        jbtnProductViewShortcut.setOpaque(false);
        jbtnProductViewShortcut.setPreferredSize(new java.awt.Dimension(100, 85));
        jbtnProductViewShortcut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnProductViewShortcut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnProductViewShortcutActionPerformed(evt);
            }
        });

        jbtnCash.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnCash.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cashbox.png"))); // NOI18N
        jbtnCash.setText("Caja");
        jbtnCash.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnCash.setFocusable(false);
        jbtnCash.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnCash.setMaximumSize(new java.awt.Dimension(100, 85));
        jbtnCash.setOpaque(false);
        jbtnCash.setPreferredSize(new java.awt.Dimension(100, 85));
        jbtnCash.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnCash.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCashActionPerformed(evt);
            }
        });

        jbtnReport.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pie-chart.png"))); // NOI18N
        jbtnReport.setText("Informes");
        jbtnReport.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnReport.setFocusable(false);
        jbtnReport.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnReport.setMaximumSize(new java.awt.Dimension(100, 85));
        jbtnReport.setOpaque(false);
        jbtnReport.setPreferredSize(new java.awt.Dimension(100, 85));
        jbtnReport.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnReportActionPerformed(evt);
            }
        });

        jbtnSyncShortcut.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnSyncShortcut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cloud-computing.png"))); // NOI18N
        jbtnSyncShortcut.setText("Sincronizar");
        jbtnSyncShortcut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSyncShortcut.setFocusable(false);
        jbtnSyncShortcut.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jbtnSyncShortcut.setMaximumSize(new java.awt.Dimension(100, 85));
        jbtnSyncShortcut.setPreferredSize(new java.awt.Dimension(100, 85));
        jbtnSyncShortcut.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jbtnSyncShortcut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSyncShortcutActionPerformed(evt);
            }
        });

        jlblCurrentDate.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jlblCurrentDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblCurrentDate.setText("26-feb-2017");

        javax.swing.GroupLayout jpanelToolbarLayout = new javax.swing.GroupLayout(jpanelToolbar);
        jpanelToolbar.setLayout(jpanelToolbarLayout);
        jpanelToolbarLayout.setHorizontalGroup(
            jpanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelToolbarLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jpanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnInventoryViewShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnSale, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnProductViewShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnCash, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnReport, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jbtnSyncShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jlblCurrentDate, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );
        jpanelToolbarLayout.setVerticalGroup(
            jpanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelToolbarLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jbtnInventoryViewShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jbtnSale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jbtnProductViewShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jbtnCash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jbtnReport, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jbtnSyncShortcut, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jlblCurrentDate)
                .addContainerGap())
        );

        jtabbedPaneRequests.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblHeaderProductRequest.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeaderProductRequest.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeaderProductRequest.setText("Listado de productos");

        jbtnDeleteProductRequest.setBackground(new java.awt.Color(157, 16, 16));
        jbtnDeleteProductRequest.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDeleteProductRequest.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDeleteProductRequest.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin-w.png"))); // NOI18N
        jbtnDeleteProductRequest.setText("Eliminar");
        jbtnDeleteProductRequest.setToolTipText("Modificar producto");
        jbtnDeleteProductRequest.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnDeleteProductRequest.setIconTextGap(8);
        jbtnDeleteProductRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteProductRequestActionPerformed(evt);
            }
        });

        jtableProductRequest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableProductRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableProductRequest.setRowHeight(25);
        jscrollPaneTableRequest.setViewportView(jtableProductRequest);

        javax.swing.GroupLayout jpanelProductRequestsLayout = new javax.swing.GroupLayout(jpanelProductRequests);
        jpanelProductRequests.setLayout(jpanelProductRequestsLayout);
        jpanelProductRequestsLayout.setHorizontalGroup(
            jpanelProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelProductRequestsLayout.createSequentialGroup()
                .addGroup(jpanelProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelProductRequestsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jscrollPaneTableRequest, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE))
                    .addGroup(jpanelProductRequestsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jlblHeaderProductRequest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnDeleteProductRequest)))
                .addGap(6, 6, 6))
        );
        jpanelProductRequestsLayout.setVerticalGroup(
            jpanelProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelProductRequestsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jpanelProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblHeaderProductRequest)
                    .addComponent(jbtnDeleteProductRequest, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscrollPaneTableRequest, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jtabbedPaneRequests.addTab("Lista de solicitudes (0)", jpanelProductRequests);

        jlblHeaderInventoryProductRequest.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeaderInventoryProductRequest.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeaderInventoryProductRequest.setText("Listado de productos");

        jtableInventoryProductRequest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableInventoryProductRequest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableInventoryProductRequest.setRowHeight(25);
        jscrollPaneTableActions.setViewportView(jtableInventoryProductRequest);

        jbtnExecute.setBackground(new java.awt.Color(51, 51, 255));
        jbtnExecute.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnExecute.setForeground(new java.awt.Color(255, 255, 255));
        jbtnExecute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cart-in-w.png"))); // NOI18N
        jbtnExecute.setText("Comprar");
        jbtnExecute.setToolTipText("Modificar producto");
        jbtnExecute.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnExecute.setIconTextGap(8);
        jbtnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpaneInventoryProductRequestsLayout = new javax.swing.GroupLayout(jpaneInventoryProductRequests);
        jpaneInventoryProductRequests.setLayout(jpaneInventoryProductRequestsLayout);
        jpaneInventoryProductRequestsLayout.setHorizontalGroup(
            jpaneInventoryProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpaneInventoryProductRequestsLayout.createSequentialGroup()
                .addGroup(jpaneInventoryProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpaneInventoryProductRequestsLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jscrollPaneTableActions, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE))
                    .addGroup(jpaneInventoryProductRequestsLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jlblHeaderInventoryProductRequest)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnExecute)))
                .addGap(6, 6, 6))
        );
        jpaneInventoryProductRequestsLayout.setVerticalGroup(
            jpaneInventoryProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpaneInventoryProductRequestsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jpaneInventoryProductRequestsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnExecute, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblHeaderInventoryProductRequest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscrollPaneTableActions, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        jtabbedPaneRequests.addTab("Lista de acciones (0)", jpaneInventoryProductRequests);

        desktopPane.setLayer(jpanelToolbar, javax.swing.JLayeredPane.DEFAULT_LAYER);
        desktopPane.setLayer(jtabbedPaneRequests, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout desktopPaneLayout = new javax.swing.GroupLayout(desktopPane);
        desktopPane.setLayout(desktopPaneLayout);
        desktopPaneLayout.setHorizontalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpanelToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jtabbedPaneRequests)
                .addContainerGap())
        );
        desktopPaneLayout.setVerticalGroup(
            desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(desktopPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(desktopPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(desktopPaneLayout.createSequentialGroup()
                        .addComponent(jtabbedPaneRequests, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jpanelToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuSystem.setMnemonic('a');
        jMenuSystem.setText("Archivo");
        jMenuSystem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemConfigure.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemConfigure.setText("Configurar");
        jmItemConfigure.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemConfigureActionPerformed(evt);
            }
        });
        jMenuSystem.add(jmItemConfigure);
        jMenuSystem.add(jsepConfigure);

        jmItemLogout.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemLogout.setText("Cerrar sesión");
        jmItemLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemLogoutActionPerformed(evt);
            }
        });
        jMenuSystem.add(jmItemLogout);
        jMenuSystem.add(jsepLogout);

        jmItemExit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemExit.setText("Salir");
        jmItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemExitActionPerformed(evt);
            }
        });
        jMenuSystem.add(jmItemExit);

        menuBar.add(jMenuSystem);

        jMenuUser.setMnemonic('u');
        jMenuUser.setText("Usuario");
        jMenuUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemUserView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUserView.setText("Ver");
        jmItemUserView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUserViewActionPerformed(evt);
            }
        });
        jMenuUser.add(jmItemUserView);

        jmItemUserCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUserCreate.setText("Nuevo");
        jmItemUserCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUserCreateActionPerformed(evt);
            }
        });
        jMenuUser.add(jmItemUserCreate);

        jmItemUserEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUserEdit.setText("Modificar");
        jmItemUserEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUserEditActionPerformed(evt);
            }
        });
        jMenuUser.add(jmItemUserEdit);

        jmItemUserBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUserBin.setText("Papelera");
        jmItemUserBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUserBinActionPerformed(evt);
            }
        });
        jMenuUser.add(jmItemUserBin);

        menuBar.add(jMenuUser);

        jMenuProfile.setMnemonic('p');
        jMenuProfile.setText("Perfil");
        jMenuProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemProfileView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProfileView.setText("Ver");
        jmItemProfileView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProfileViewActionPerformed(evt);
            }
        });
        jMenuProfile.add(jmItemProfileView);

        jmItemProfileCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProfileCreate.setText("Nuevo");
        jmItemProfileCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProfileCreateActionPerformed(evt);
            }
        });
        jMenuProfile.add(jmItemProfileCreate);
        jmItemProfileCreate.getAccessibleContext().setAccessibleName("jMenuItemProfileCreate");

        jmItemProfileEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProfileEdit.setMnemonic('y');
        jmItemProfileEdit.setText("Modificar");
        jmItemProfileEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProfileEditActionPerformed(evt);
            }
        });
        jMenuProfile.add(jmItemProfileEdit);

        jmItemProfileBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProfileBin.setText("Papelera");
        jmItemProfileBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProfileBinActionPerformed(evt);
            }
        });
        jMenuProfile.add(jmItemProfileBin);

        menuBar.add(jMenuProfile);

        jMenuInventory.setMnemonic('i');
        jMenuInventory.setText("Inventario");
        jMenuInventory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemInventoryView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemInventoryView.setText("Ver");
        jmItemInventoryView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemInventoryViewActionPerformed(evt);
            }
        });
        jMenuInventory.add(jmItemInventoryView);

        jmItemInventoryCancel.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemInventoryCancel.setText("Papelera");
        jmItemInventoryCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemInventoryCancelActionPerformed(evt);
            }
        });
        jMenuInventory.add(jmItemInventoryCancel);
        jMenuInventory.add(jsepInventoryBin);

        jmItemInventoryIncome.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemInventoryIncome.setText("Entrada");
        jmItemInventoryIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemInventoryIncomeActionPerformed(evt);
            }
        });
        jMenuInventory.add(jmItemInventoryIncome);

        jmItemInventoryOutcome.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemInventoryOutcome.setText("Salida");
        jmItemInventoryOutcome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemInventoryOutcomeActionPerformed(evt);
            }
        });
        jMenuInventory.add(jmItemInventoryOutcome);

        menuBar.add(jMenuInventory);

        jMenuPurchases.setMnemonic('c');
        jMenuPurchases.setText("Compras");
        jMenuPurchases.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemPurchaseView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemPurchaseView.setText("Ver");
        jmItemPurchaseView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemPurchaseViewActionPerformed(evt);
            }
        });
        jMenuPurchases.add(jmItemPurchaseView);

        jmItemPurchaseCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemPurchaseCreate.setText("Nuevo");
        jmItemPurchaseCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemPurchaseCreateActionPerformed(evt);
            }
        });
        jMenuPurchases.add(jmItemPurchaseCreate);

        jmItemPurchaseBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemPurchaseBin.setText("Papelera");
        jmItemPurchaseBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemPurchaseBinActionPerformed(evt);
            }
        });
        jMenuPurchases.add(jmItemPurchaseBin);

        menuBar.add(jMenuPurchases);

        jMenuSales.setMnemonic('v');
        jMenuSales.setText("Ventas");
        jMenuSales.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemSalesView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemSalesView.setText("Ver");
        jmItemSalesView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemSalesViewActionPerformed(evt);
            }
        });
        jMenuSales.add(jmItemSalesView);

        jmItemSalesCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemSalesCreate.setText("Nuevo");
        jmItemSalesCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemSalesCreateActionPerformed(evt);
            }
        });
        jMenuSales.add(jmItemSalesCreate);

        jmItemSalesBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemSalesBin.setText("Papelera");
        jmItemSalesBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemSalesBinActionPerformed(evt);
            }
        });
        jMenuSales.add(jmItemSalesBin);
        jMenuSales.add(jsepBin);

        jmItemSalesCashOut.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemSalesCashOut.setText("Corte diario");
        jmItemSalesCashOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemSalesCashOutActionPerformed(evt);
            }
        });
        jMenuSales.add(jmItemSalesCashOut);

        menuBar.add(jMenuSales);

        jMenuCatalogs.setMnemonic('h');
        jMenuCatalogs.setText("Catálogo");
        jMenuCatalogs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jMenuCategory.setText("Categoría");
        jMenuCategory.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jmItemCategoryView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemCategoryView.setText("Ver");
        jmItemCategoryView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemCategoryViewActionPerformed(evt);
            }
        });
        jMenuCategory.add(jmItemCategoryView);

        jmItemCategoryCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemCategoryCreate.setText("Nuevo");
        jmItemCategoryCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemCategoryCreateActionPerformed(evt);
            }
        });
        jMenuCategory.add(jmItemCategoryCreate);

        jmItemCategoryEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemCategoryEdit.setText("Modificar");
        jmItemCategoryEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemCategoryEditActionPerformed(evt);
            }
        });
        jMenuCategory.add(jmItemCategoryEdit);

        jmItemCategoryBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemCategoryBin.setText("Papelera");
        jmItemCategoryBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemCategoryBinActionPerformed(evt);
            }
        });
        jMenuCategory.add(jmItemCategoryBin);

        jMenuCatalogs.add(jMenuCategory);

        jMenuProvider.setText("Proveedor");
        jMenuProvider.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jmItemProviderView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProviderView.setText("Ver");
        jmItemProviderView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProviderViewActionPerformed(evt);
            }
        });
        jMenuProvider.add(jmItemProviderView);

        jmItemProviderCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProviderCreate.setText("Nuevo");
        jmItemProviderCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProviderCreateActionPerformed(evt);
            }
        });
        jMenuProvider.add(jmItemProviderCreate);

        jmItemProviderEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProviderEdit.setText("Modificar");
        jmItemProviderEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProviderEditActionPerformed(evt);
            }
        });
        jMenuProvider.add(jmItemProviderEdit);

        jmItemProviderBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProviderBin.setText("Papelera");
        jmItemProviderBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProviderBinActionPerformed(evt);
            }
        });
        jMenuProvider.add(jmItemProviderBin);

        jMenuCatalogs.add(jMenuProvider);

        jMenuProduct.setText("Artículo");
        jMenuProduct.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jmItemProductView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProductView.setText("Ver");
        jmItemProductView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProductViewActionPerformed(evt);
            }
        });
        jMenuProduct.add(jmItemProductView);

        jmItemProductCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProductCreate.setText("Nuevo");
        jmItemProductCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProductCreateActionPerformed(evt);
            }
        });
        jMenuProduct.add(jmItemProductCreate);

        jmItemProductEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProductEdit.setText("Modificar");
        jmItemProductEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProductEditActionPerformed(evt);
            }
        });
        jMenuProduct.add(jmItemProductEdit);

        jmItemProductBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemProductBin.setText("Papelera");
        jmItemProductBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemProductBinActionPerformed(evt);
            }
        });
        jMenuProduct.add(jmItemProductBin);

        jMenuCatalogs.add(jMenuProduct);

        jMenuUnits.setText("Unidades");
        jMenuUnits.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jmItemUnitsView.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUnitsView.setText("Ver");
        jmItemUnitsView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUnitsViewActionPerformed(evt);
            }
        });
        jMenuUnits.add(jmItemUnitsView);

        jmItemUnitsCreate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUnitsCreate.setText("Nuevo");
        jmItemUnitsCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUnitsCreateActionPerformed(evt);
            }
        });
        jMenuUnits.add(jmItemUnitsCreate);

        jmItemUnitsEdit.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUnitsEdit.setText("Modificar");
        jmItemUnitsEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUnitsEditActionPerformed(evt);
            }
        });
        jMenuUnits.add(jmItemUnitsEdit);

        jmItemUnitsBin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemUnitsBin.setText("Papelera");
        jmItemUnitsBin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemUnitsBinActionPerformed(evt);
            }
        });
        jMenuUnits.add(jmItemUnitsBin);

        jMenuCatalogs.add(jMenuUnits);

        jmItemNotifications.setText("Notificaciones");
        jmItemNotifications.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemNotifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemNotificationsActionPerformed(evt);
            }
        });
        jMenuCatalogs.add(jmItemNotifications);

        menuBar.add(jMenuCatalogs);

        jMenuHelp.setMnemonic('h');
        jMenuHelp.setText("Ayuda");
        jMenuHelp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jmItemSynchronize.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemSynchronize.setText("Sincronizar");
        jmItemSynchronize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmItemSynchronizeActionPerformed(evt);
            }
        });
        jMenuHelp.add(jmItemSynchronize);

        jmItemAbout.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jmItemAbout.setMnemonic('a');
        jmItemAbout.setText("Acerca de");
        jMenuHelp.add(jmItemAbout);

        menuBar.add(jMenuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktopPane)
        );

        getAccessibleContext().setAccessibleName("mainJFrame");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jmItemProfileCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProfileCreateActionPerformed
        
        build(ProfileCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemProfileCreateActionPerformed

    private void jmItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemExitActionPerformed
        
        exit();
        
    }//GEN-LAST:event_jmItemExitActionPerformed

    private void jmItemProfileViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProfileViewActionPerformed
        
        build(ProfileViewJFrame.class);
        
    }//GEN-LAST:event_jmItemProfileViewActionPerformed

    private void jmItemProfileEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProfileEditActionPerformed
        
        build(ProfileEditJFrame.class);
        
    }//GEN-LAST:event_jmItemProfileEditActionPerformed

    private void jmItemProfileBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProfileBinActionPerformed
        
        build(ProfileBinJFrame.class);
        
    }//GEN-LAST:event_jmItemProfileBinActionPerformed

    private void jmItemProviderCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProviderCreateActionPerformed
        
        build(ProviderCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemProviderCreateActionPerformed

    private void jmItemProviderViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProviderViewActionPerformed
        
        build(ProviderViewJFrame.class);
        
    }//GEN-LAST:event_jmItemProviderViewActionPerformed

    private void jmItemProviderEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProviderEditActionPerformed
        
        build(ProviderEditJFrame.class);
        
    }//GEN-LAST:event_jmItemProviderEditActionPerformed

    private void jmItemCategoryViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemCategoryViewActionPerformed
        
        build(CategoryViewJFrame.class);
        
    }//GEN-LAST:event_jmItemCategoryViewActionPerformed

    private void jmItemCategoryCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemCategoryCreateActionPerformed
        
        build(CategoryCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemCategoryCreateActionPerformed

    private void jmItemCategoryEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemCategoryEditActionPerformed
        
        build(CategoryEditJFrame.class);
        
    }//GEN-LAST:event_jmItemCategoryEditActionPerformed

    private void jmItemProductViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProductViewActionPerformed
        
        build(ProductViewJFrame.class);
        
    }//GEN-LAST:event_jmItemProductViewActionPerformed

    private void jmItemProductCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProductCreateActionPerformed
        
        build(ProductCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemProductCreateActionPerformed

    private void jmItemProductEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProductEditActionPerformed
        
        build(ProductEditJFrame.class);
        
    }//GEN-LAST:event_jmItemProductEditActionPerformed
    
    private void jmItemInventoryViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemInventoryViewActionPerformed
        
        build(InventoryViewJFrame.class);
        
    }//GEN-LAST:event_jmItemInventoryViewActionPerformed

    private void jmItemInventoryIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemInventoryIncomeActionPerformed

        build(InventoryIncomeJFrame.class);
        
    }//GEN-LAST:event_jmItemInventoryIncomeActionPerformed

    private void jmItemInventoryOutcomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemInventoryOutcomeActionPerformed
        
        build(InventoryOutcomeJFrame.class);
        
    }//GEN-LAST:event_jmItemInventoryOutcomeActionPerformed

    private void jmItemInventoryCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemInventoryCancelActionPerformed
        
        build(InventoryBinJFrame.class);
        
    }//GEN-LAST:event_jmItemInventoryCancelActionPerformed

    private void jmItemSalesViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemSalesViewActionPerformed
        
        build(SaleViewJFrame.class);
        
    }//GEN-LAST:event_jmItemSalesViewActionPerformed

    private void jmItemSalesBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemSalesBinActionPerformed
        
        build(SaleBinJFrame.class);       
        
    }//GEN-LAST:event_jmItemSalesBinActionPerformed

    private void jmItemSalesCashOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemSalesCashOutActionPerformed
        
        CashOutTicket cashOutTicket = new CashOutTicket();
        cashOutTicket.print();
        
        JOptionPane.showMessageDialog(this, "Se ha generado correctamente el corte diario de caja.");
        
    }//GEN-LAST:event_jmItemSalesCashOutActionPerformed

    private void jbtnInventoryViewShortcutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnInventoryViewShortcutActionPerformed
        
        build(InventoryViewJFrame.class);
        
    }//GEN-LAST:event_jbtnInventoryViewShortcutActionPerformed

    private void jbtnSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSaleActionPerformed
        
        SaleCreateJFrame saleCreateJFrame = new SaleCreateJFrame();
        saleCreateJFrame.setVisible(true);
        
    }//GEN-LAST:event_jbtnSaleActionPerformed

    private void jbtnProductViewShortcutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnProductViewShortcutActionPerformed
        
        build(ProductViewJFrame.class);
        
    }//GEN-LAST:event_jbtnProductViewShortcutActionPerformed

    private void jbtnCashActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCashActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnCashActionPerformed

    private void jbtnReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnReportActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnReportActionPerformed

    private void jbtnSyncShortcutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSyncShortcutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jbtnSyncShortcutActionPerformed

    private void jmItemUserViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUserViewActionPerformed
        
        build(UserViewJFrame.class);
        
    }//GEN-LAST:event_jmItemUserViewActionPerformed

    private void jmItemUserCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUserCreateActionPerformed
        
        build(UserCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemUserCreateActionPerformed

    private void jmItemUserEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUserEditActionPerformed
        
        build(UserEditJFrame.class);
        
    }//GEN-LAST:event_jmItemUserEditActionPerformed

    private void jmItemUserBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUserBinActionPerformed
        
        build(UserBinJFrame.class);
        
    }//GEN-LAST:event_jmItemUserBinActionPerformed

    private void jmItemLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemLogoutActionPerformed
        
        logger.info("Logout.");
        //Se inicializan los datos de configuración, usuario y notificaciones
        Application.setSetting(null);
        ApplicationSession.setUser(null);
        ApplicationSession.setNotifications(null);
        //Se despliega la pantalla de inicio del sistema
        LoginJFrame loginJFrame = new LoginJFrame();
        loginJFrame.setVisible(true);
        //Se oculta formulario principal del sistema
        dispose();
        
    }//GEN-LAST:event_jmItemLogoutActionPerformed

    private void jmItemUnitsViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUnitsViewActionPerformed
        
        build(UnitViewJFrame.class);
        
    }//GEN-LAST:event_jmItemUnitsViewActionPerformed

    private void jmItemUnitsCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUnitsCreateActionPerformed
        
        build(UnitCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemUnitsCreateActionPerformed

    private void jmItemUnitsEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUnitsEditActionPerformed
        
        build(UnitEditJFrame.class);
        
    }//GEN-LAST:event_jmItemUnitsEditActionPerformed

    private void jmItemProductBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProductBinActionPerformed
        
        build(ProductBinJFrame.class);
        
    }//GEN-LAST:event_jmItemProductBinActionPerformed

    private void jmItemPurchaseViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemPurchaseViewActionPerformed
        
        build(PurchaseViewJFrame.class);
        
    }//GEN-LAST:event_jmItemPurchaseViewActionPerformed

    private void jmItemPurchaseCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemPurchaseCreateActionPerformed
        
        build(PurchaseCreateJFrame.class);
        
    }//GEN-LAST:event_jmItemPurchaseCreateActionPerformed

    private void jmItemPurchaseBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemPurchaseBinActionPerformed
        
        build(PurchaseBinJFrame.class);
        
    }//GEN-LAST:event_jmItemPurchaseBinActionPerformed

    private void jmItemSalesCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemSalesCreateActionPerformed
        
        SaleCreateJFrame saleCreateJFrame = new SaleCreateJFrame();
        saleCreateJFrame.setVisible(true);
        
    }//GEN-LAST:event_jmItemSalesCreateActionPerformed

    private void jmItemProviderBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemProviderBinActionPerformed
        
        build(ProviderBinJFrame.class);
        
    }//GEN-LAST:event_jmItemProviderBinActionPerformed

    private void jmItemCategoryBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemCategoryBinActionPerformed
        
        build(CategoryBinJFrame.class);
        
    }//GEN-LAST:event_jmItemCategoryBinActionPerformed

    private void jmItemUnitsBinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemUnitsBinActionPerformed
        
        build(UnitBinJFrame.class);
        
    }//GEN-LAST:event_jmItemUnitsBinActionPerformed

    private void jmItemConfigureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemConfigureActionPerformed
        
        build(SettingsJFrame.class);
        
    }//GEN-LAST:event_jmItemConfigureActionPerformed

    private void jmItemNotificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemNotificationsActionPerformed
        
        NotificationComponent component = new NotificationComponent();
        
        JButton jbtnSave = new JButton("Guardar");
        JButton jbtnClose = new JButton("Cerrar");
        
        jbtnSave.addActionListener((ActionEvent e) ->
        {
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnSave);
        });
        jbtnClose.addActionListener((ActionEvent e) ->
        {
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnClose);
        });
        
        if (JOptionPane.showOptionDialog(this, component.getContentPane(),
            " Panel de notificaciones", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, new Object[]{jbtnSave, jbtnClose}, jbtnSave) == 0)
        {
            INotification iNotification = Application.getContext().getBean(INotification.class);
            
            iNotification.updateAll(component.getAll());
            
            ApplicationSession.setNotifications(component.getAll());
        }
        
    }//GEN-LAST:event_jmItemNotificationsActionPerformed

    private void jbtnDeleteProductRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteProductRequestActionPerformed

        ProductRequestTableModel productRequestTableModel =
            (ProductRequestTableModel) jtableProductRequest.getModel();
        
        if (jtableProductRequest.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere eliminar el registro?",
            " Eliminar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IProductRequest iProductRequest = Application.getContext().getBean(IProductRequest.class);
        
        ProductRequest productRequest = iProductRequest.findById(
            productRequestTableModel.get(jtableProductRequest.getSelectedRow()).getProductRequestPk());

        iProductRequest.delete(productRequest);

        if (productRequest.getProductRequestPk() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha eliminado exitosamente.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            
            productRequestTableModel.remove(
                jtableProductRequest.getSelectedRow());
            
            jtabbedPaneRequests.setTitleAt(0, "Lista de solicitudes ("
                + productRequestTableModel.getRowCount() + ")");
            
            if (ApplicationSession.getNotifications().stream()
                .anyMatch(x -> x.getCode().equals(Notification.PRODUCT_REQUEST_CANCEL)
                    && x.getStatus().equals(EntityStatus.ACTIVE)))
            {
                ApplicationSession.getThreadPoolExecutor().execute(
                    new ProductRequestCancelMailProcessor(productRequest));
            }
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el registro.",
                " Eliminar registro", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jbtnDeleteProductRequestActionPerformed

    private void jbtnExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnExecuteActionPerformed

        InventoryProductRequestTableModel inventoryProductRequestTableModel =
            (InventoryProductRequestTableModel) jtableInventoryProductRequest.getModel();
        
        String jlblMsg = new String();
        
        if (jtableInventoryProductRequest.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione uno o más registros.",
                " Ejecutar acción sobre registro(s)", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (inventoryProductRequestTableModel.get(jtableInventoryProductRequest.getSelectedRows())
            .stream().anyMatch(x -> x.getRequiredUnits() > 0)
            && inventoryProductRequestTableModel.get(jtableInventoryProductRequest.getSelectedRows())
                .stream().anyMatch(x -> x.getRequiredUnits() < 0))
        {
            OptionPane.showMessageDialog(this, "Seleccione uno o más registros del mismo tipo de acción.",
                " Ejecutar acción sobre registro(s)", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Boolean isPurchase = inventoryProductRequestTableModel.get(
            jtableInventoryProductRequest.getSelectedRows()).stream().allMatch(x -> x.getRequiredUnits() < 0);
        
        jlblMsg =
            "<html>¿Está seguro de que quiere ejecutar la <b>" + (isPurchase ? "COMPRA" : "VENTA")
            + (jtableInventoryProductRequest.getSelectedRowCount() == 1
                ? "</b> del registro seleccionado?"
                : "</b> de los registros seleccionados?</html>");
        
        if (OptionPane.showConfirmDialog(this, jlblMsg, " Ejecutar acción sobre registro(s)",
            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        if (isPurchase)
        {
            
            Boolean loopBreaked = Boolean.FALSE;
            List<PurchaseDetail> purchaseDetails = new ArrayList<>();

            for (InventoryProductRequest x :
                inventoryProductRequestTableModel.get(jtableInventoryProductRequest.getSelectedRows()))
            {
                PurchaseDetail purchaseDetail = new PurchaseDetail();

                purchaseDetail.setProduct(x.getProduct());
                purchaseDetail.setUnit(x.getUnit());

                //Obteniendo costo del producto de acuerdo a la unidad seleccionada
                IProductUnit iProductUnit = Application.getContext().getBean(IProductUnit.class);
                
                ProductUnit productUnit = iProductUnit.findBy(
                    purchaseDetail.getProduct(), purchaseDetail.getUnit());
                //Si el producto no tiene unidades configuradas
                if (productUnit == null)
                {
                    jlblMsg = 
                        "<html>Aún no se han configurado las unidades del producto: "
                        + "<b>" + x.getProduct().getName().toUpperCase() + "</b><br>"
                        + "Lleve a cabo la configuración antes de continuar.</html>";
                    OptionPane.showMessageDialog(this, jlblMsg,
                        " Ejecutar acción sobre registro(s)", JOptionPane.INFORMATION_MESSAGE);
                    loopBreaked = Boolean.TRUE;
                    break;
                }
                
                purchaseDetail.setCost(productUnit.getCostByUnit());
                purchaseDetail.setQuantity(Math.abs(x.getRequiredUnits()));
                purchaseDetail.setTotalCost(productUnit.getCostByUnit() * purchaseDetail.getQuantity());
                purchaseDetail.setCreatedBy(ApplicationSession.getUser());

                purchaseDetails.add(purchaseDetail);
            }

            if (!loopBreaked)
            {
                //Se envían los parámetros requeridos para inicializar una compra a partir de una solicitud
                PurchaseCreateJFrame purchaseCreateJFrame = new PurchaseCreateJFrame(purchaseDetails,
                    inventoryProductRequestTableModel.get(jtableInventoryProductRequest.getSelectedRows()));
                desktopPane.add(purchaseCreateJFrame);
                purchaseCreateJFrame.pack();
                try
                {
                    purchaseCreateJFrame.setMaximum(true);
                }
                catch (PropertyVetoException ex)
                {
                    logger.error("Error setting maximum", ex);
                }
                purchaseCreateJFrame.setVisible(true);
                //Se eliminan las filas previamente seleccionadas
                inventoryProductRequestTableModel.remove(jtableInventoryProductRequest.getSelectedRows());
                
                jtabbedPaneRequests.setTitleAt(1, "Lista de acciones ("
                    + inventoryProductRequestTableModel.getRowCount() + ")");
            }
            
        }
        else
        {
            
            List<SaleDetail> saleDetails = new ArrayList<>();

            inventoryProductRequestTableModel.get(
                jtableInventoryProductRequest.getSelectedRows()).stream().map((ipr) ->
            {
                SaleDetail saleDetail = new SaleDetail();
                
                saleDetail.setProduct(ipr.getProduct());
                saleDetail.setPrice(ipr.getProduct().getPrice());
                saleDetail.setDiscount(0.00);
                //Si el producto tiene un descuento vigente
                if (ipr.getProduct().getDiscountExpirationDate() != null
                    && ipr.getProduct().getDiscountExpirationDate().compareTo(Calendar.getInstance()) > 0)
                {
                    saleDetail.setDiscount(ipr.getProduct().getDiscount());
                }
                saleDetail.setTotalDiscount(
                    BigDecimal.valueOf(saleDetail.getDiscount() * ipr.getRequiredUnits())
                        .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                saleDetail.setAmount(
                    BigDecimal.valueOf((saleDetail.getPrice() * ipr.getRequiredUnits()) -
                        (saleDetail.getDiscount() * ipr.getRequiredUnits()))
                            .setScale(2, RoundingMode.HALF_EVEN).doubleValue()
                );
                saleDetail.setCost(ipr.getProduct().getCost());
                saleDetail.setTotalCost(
                    BigDecimal.valueOf(ipr.getProduct().getCost() * ipr.getRequiredUnits())
                        .setScale(2, RoundingMode.HALF_EVEN).doubleValue());
                saleDetail.setQuantity(ipr.getRequiredUnits());
                saleDetail.setCreatedBy(ApplicationSession.getUser());
                
                return saleDetail;
            })
            .forEach((saleDetail) ->
            {
                saleDetails.add(saleDetail);
            });
            
            //Se envían los parámetros requeridos para inicializar una venta a partir de una solicitud
            SaleCreateJFrame saleCreateJFrame = new SaleCreateJFrame(saleDetails,
                inventoryProductRequestTableModel.get(jtableInventoryProductRequest.getSelectedRows()));
            saleCreateJFrame.setVisible(true);
            //Se eliminan las filas previamente seleccionadas
            inventoryProductRequestTableModel.remove(jtableInventoryProductRequest.getSelectedRows());
            
            jtabbedPaneRequests.setTitleAt(1, "Lista de acciones ("
                + inventoryProductRequestTableModel.getRowCount() + ")");
            
        }

    }//GEN-LAST:event_jbtnExecuteActionPerformed

    private void jmItemSynchronizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmItemSynchronizeActionPerformed
        
        SyncComponent component = new SyncComponent();
        
        JButton jbtnSave = new JButton("Aceptar");
        JButton jbtnClose = new JButton("Cerrar");
        
        jbtnSave.addActionListener((ActionEvent e) ->
        {
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnSave);
        });
        jbtnClose.addActionListener((ActionEvent e) ->
        {
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnClose);
        });
        
        if (JOptionPane.showOptionDialog(this, component.getContentPane(),
            " Panel de sincronización", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, new Object[]{jbtnSave, jbtnClose}, jbtnSave) == 0)
        {
            
        }
        
    }//GEN-LAST:event_jmItemSynchronizeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenu jMenuCatalogs;
    private javax.swing.JMenu jMenuCategory;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenu jMenuInventory;
    private javax.swing.JMenu jMenuProduct;
    private javax.swing.JMenu jMenuProfile;
    private javax.swing.JMenu jMenuProvider;
    private javax.swing.JMenu jMenuPurchases;
    private javax.swing.JMenu jMenuSales;
    private javax.swing.JMenu jMenuSystem;
    private javax.swing.JMenu jMenuUnits;
    private javax.swing.JMenu jMenuUser;
    private javax.swing.JButton jbtnCash;
    private javax.swing.JButton jbtnDeleteProductRequest;
    private javax.swing.JButton jbtnExecute;
    private javax.swing.JButton jbtnInventoryViewShortcut;
    private javax.swing.JButton jbtnProductViewShortcut;
    private javax.swing.JButton jbtnReport;
    private javax.swing.JButton jbtnSale;
    private javax.swing.JButton jbtnSyncShortcut;
    private javax.swing.JLabel jlblCurrentDate;
    private javax.swing.JLabel jlblHeaderInventoryProductRequest;
    private javax.swing.JLabel jlblHeaderProductRequest;
    private javax.swing.JMenuItem jmItemAbout;
    private javax.swing.JMenuItem jmItemCategoryBin;
    private javax.swing.JMenuItem jmItemCategoryCreate;
    private javax.swing.JMenuItem jmItemCategoryEdit;
    private javax.swing.JMenuItem jmItemCategoryView;
    private javax.swing.JMenuItem jmItemConfigure;
    private javax.swing.JMenuItem jmItemExit;
    private javax.swing.JMenuItem jmItemInventoryCancel;
    private javax.swing.JMenuItem jmItemInventoryIncome;
    private javax.swing.JMenuItem jmItemInventoryOutcome;
    private javax.swing.JMenuItem jmItemInventoryView;
    private javax.swing.JMenuItem jmItemLogout;
    private javax.swing.JMenuItem jmItemNotifications;
    private javax.swing.JMenuItem jmItemProductBin;
    private javax.swing.JMenuItem jmItemProductCreate;
    private javax.swing.JMenuItem jmItemProductEdit;
    private javax.swing.JMenuItem jmItemProductView;
    private javax.swing.JMenuItem jmItemProfileBin;
    private javax.swing.JMenuItem jmItemProfileCreate;
    private javax.swing.JMenuItem jmItemProfileEdit;
    private javax.swing.JMenuItem jmItemProfileView;
    private javax.swing.JMenuItem jmItemProviderBin;
    private javax.swing.JMenuItem jmItemProviderCreate;
    private javax.swing.JMenuItem jmItemProviderEdit;
    private javax.swing.JMenuItem jmItemProviderView;
    private javax.swing.JMenuItem jmItemPurchaseBin;
    private javax.swing.JMenuItem jmItemPurchaseCreate;
    private javax.swing.JMenuItem jmItemPurchaseView;
    private javax.swing.JMenuItem jmItemSalesBin;
    private javax.swing.JMenuItem jmItemSalesCashOut;
    private javax.swing.JMenuItem jmItemSalesCreate;
    private javax.swing.JMenuItem jmItemSalesView;
    private javax.swing.JMenuItem jmItemSynchronize;
    private javax.swing.JMenuItem jmItemUnitsBin;
    private javax.swing.JMenuItem jmItemUnitsCreate;
    private javax.swing.JMenuItem jmItemUnitsEdit;
    private javax.swing.JMenuItem jmItemUnitsView;
    private javax.swing.JMenuItem jmItemUserBin;
    private javax.swing.JMenuItem jmItemUserCreate;
    private javax.swing.JMenuItem jmItemUserEdit;
    private javax.swing.JMenuItem jmItemUserView;
    private javax.swing.JPanel jpaneInventoryProductRequests;
    private javax.swing.JPanel jpanelProductRequests;
    private javax.swing.JPanel jpanelToolbar;
    private javax.swing.JScrollPane jscrollPaneTableActions;
    private javax.swing.JScrollPane jscrollPaneTableRequest;
    private javax.swing.JPopupMenu.Separator jsepBin;
    private javax.swing.JPopupMenu.Separator jsepConfigure;
    private javax.swing.JPopupMenu.Separator jsepInventoryBin;
    private javax.swing.JPopupMenu.Separator jsepLogout;
    private javax.swing.JTabbedPane jtabbedPaneRequests;
    private javax.swing.JTable jtableInventoryProductRequest;
    private javax.swing.JTable jtableProductRequest;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

}

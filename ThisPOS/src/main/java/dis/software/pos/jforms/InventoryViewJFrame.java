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
import dis.software.pos.EntityStatus;
import dis.software.pos.InventoryType;
import dis.software.pos.OptionPane;
import dis.software.pos.combobox.model.InventoryTypeComboBoxModel;
import dis.software.pos.combobox.renderers.InventoryTypeComboBoxRenderer;
import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.InventoryProductRequest;
import dis.software.pos.entities.InventoryProductRequestPk;
import dis.software.pos.entities.Notification;
import dis.software.pos.interfaces.IInventory;
import dis.software.pos.interfaces.IInventoryProduct;
import dis.software.pos.interfaces.IInventoryProductRequest;
import dis.software.pos.mail.processors.InventoryCancelMailProcessor;
import dis.software.pos.table.cell.renderer.CalendarCellRenderer;
import dis.software.pos.table.cell.renderer.InventoryTypeCellRenderer;
import dis.software.pos.table.model.InventoryProductTableModel;
import dis.software.pos.table.model.InventoryTableModel;
import dis.software.pos.tickets.InventoryProductDetailTicket;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Formulario para la visualización de inventarios generados
 * @author Milton Cavazos
 */
public class InventoryViewJFrame extends javax.swing.JInternalFrame
{

    /**
     * Creación de nuevo formulario InventoryViewJFrame
     */
    public InventoryViewJFrame()
    {
        
        initComponents();
        
        jtableInventory.setModel(new InventoryTableModel());
        jtableInventory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableInventory.setShowHorizontalLines(Boolean.TRUE);
        jtableInventory.setGridColor(new Color(179, 179, 179));
        
        jtableInventory.setDefaultRenderer(InventoryType.class, new InventoryTypeCellRenderer());
        jtableInventory.setDefaultRenderer(Calendar.class, new CalendarCellRenderer());
        
        //Se eliminan columnas innecesarias para esta vista
        jtableInventory.getColumnModel().removeColumn(jtableInventory.getColumnModel().getColumn(
            jtableInventory.convertColumnIndexToView(InventoryTableModel.COLUMN_ID)));
        
        jtableInventory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JPopupMenu jpopupMenu = new JPopupMenu();
        JMenuItem jmItemEdit = new JMenuItem("Cotejar registro");
        JMenuItem jmItemCancel = new JMenuItem("Cancelar registro");
        JMenuItem jmItemDelete = new JMenuItem("Eliminar registro");
        jmItemEdit.addActionListener((ActionEvent e) ->
        {
            if (e.getSource() == jmItemEdit)
            {
                edit();
            }
        });
        jmItemCancel.addActionListener((ActionEvent e) ->
        {
            if (e.getSource() == jmItemCancel)
            {
                cancel();
            }
        });
        jmItemDelete.addActionListener((ActionEvent e) ->
        {
            if (e.getSource() == jmItemDelete)
            {
                delete();
            }
        });
        jpopupMenu.add(jmItemEdit);
        jpopupMenu.add(jmItemCancel);
        jpopupMenu.add(jmItemDelete);
        jtableInventory.setComponentPopupMenu(jpopupMenu);
        
        List<InventoryType> list = new ArrayList<>();
        list.add(InventoryType.INCOME);
        list.add(InventoryType.OUTCOME);
        list.add(InventoryType.SYSTEM);
        list.add(InventoryType.CANCEL);
        jcboFilterBy.setModel(new InventoryTypeComboBoxModel(list));
        jcboFilterBy.setRenderer(new InventoryTypeComboBoxRenderer());
        
        jxInitialDatePicker.getComponentToggleCalendarButton().setText("");
        jxInitialDatePicker.getComponentToggleCalendarButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/calendar-b.png")));
        jxInitialDatePicker.getSettings().setAllowKeyboardEditing(false);
        
        jxFinalDatePicker.getComponentToggleCalendarButton().setText("");
        jxFinalDatePicker.getComponentToggleCalendarButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/calendar-b.png")));
        jxFinalDatePicker.getSettings().setAllowKeyboardEditing(false);
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para contabilizar existencias">
    private void create()
    {
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere generar \n"
            + "la contabilización de las unidades existentes de productos en almacén?",
            " Nuevo inventario", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        
        Inventory inventory = new Inventory();
        inventory.setCode(iInventory.getNextCode());
        inventory.setType(InventoryType.SYSTEM);
        inventory.setCreatedBy(ApplicationSession.getUser());

        inventory = iInventory.save(inventory);

        if (inventory.getId() != null)
        {
            IInventoryProduct iInventoryProduct = Application.getContext().getBean(IInventoryProduct.class);
            //Se realiza la impresión de la lista de productos en almacén
            InventoryProductDetailTicket inventoryProductDetailTicket =
                new InventoryProductDetailTicket(inventory, iInventoryProduct.findByInventory(inventory));
            inventoryProductDetailTicket.print();
            
            OptionPane.showMessageDialog(this, "El registro de inventario se ha generado exitosamente.",
                " Nuevo inventario", OptionPane.SUCCESS_MESSAGE);
            //Se realiza búsqueda automática de los inventarios generados por el sistema
            jcboFilterBy.setSelectedItem(InventoryType.SYSTEM);
            jxInitialDatePicker.setDate(LocalDate.now(ZoneId.systemDefault()));
            jxFinalDatePicker.setDate(LocalDate.now(ZoneId.systemDefault()));
            
            search();
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar generar el registro.",
                " Nuevo registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para cotejar registro">
    private void edit()
    {
        
        InventoryTableModel inventoryTableModel = (InventoryTableModel) jtableInventory.getModel();
               
        if (jtableInventory.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Cotejar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (InventoryType.INCOME == inventoryTableModel.get(jtableInventory.getSelectedRow()).getType()
            || InventoryType.OUTCOME == inventoryTableModel.get(jtableInventory.getSelectedRow()).getType()
            || InventoryType.CANCEL == inventoryTableModel.get(jtableInventory.getSelectedRow()).getType())
        {
            OptionPane.showMessageDialog(this, "No es posible cotejar este tipo de registro.",
                " Cotejar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        //Si existe un inventario generado después del que se trata de cotejar se mostrará un mensaje
        if (!iInventory.isLast(inventoryTableModel.get(jtableInventory.getSelectedRow())))
        {
            String jlblMsg = "<html>No es posible cotejar el siguiente registro: "
                + "<b>" + inventoryTableModel.get(jtableInventory.getSelectedRow()).getCode() + "</b><br>"
                + "Sólo se pueden realizar ajustes de inventario en el registro mas reciente.</html>";
            OptionPane.showMessageDialog(this, jlblMsg, " Cotejar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        IInventoryProduct iInventoryProduct = Application.getContext().getBean(IInventoryProduct.class);
        
        JTable jtableInventoryProducts = new JTable();
        
        jtableInventoryProducts.setModel(new InventoryProductTableModel(
            iInventoryProduct.findByInventory(inventoryTableModel.get(jtableInventory.getSelectedRow()))));
        jtableInventoryProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jtableInventoryProducts.setRowHeight(25);
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableInventoryProducts.setShowHorizontalLines(Boolean.TRUE);
        jtableInventoryProducts.setGridColor(new Color(179, 179, 179));
        
        jtableInventoryProducts.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
            {
                setHorizontalAlignment(SwingConstants.LEFT);
                setBackground(table.getBackground());
                setToolTipText(null);
                if (value instanceof Integer)
                {
                    setHorizontalAlignment(SwingConstants.RIGHT);
                }
                if (((InventoryProductTableModel) table.getModel()).get(row).getStatus()
                    .equals(EntityStatus.LOCKED))
                {
                    setBackground(new Color(255, 255, 153));
                    setToolTipText("La solicitud del registro ya fue atendida.");
                }
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                return this;
            }
        });

        //Se eliminan columnas innecesarias para esta vista
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_PROD_ID)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_UNIT_ID)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_TYPE)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_DATE)));
        
        jtableInventoryProducts.addPropertyChangeListener(new PropertyChangeListener()
        {
            private Integer oValue;
            private Integer nValue;
            
            @Override
            public void propertyChange(PropertyChangeEvent evt)
            {
                if ("tableCellEditor".equals(evt.getPropertyName()))
                {
                    InventoryProductTableModel inventoryProductTableModel = 
                        (InventoryProductTableModel) jtableInventoryProducts.getModel();
                    
                    if (jtableInventoryProducts.isEditing())
                    {
                        //invokeLater es necesario por que el evento "tableCellEditor" se dispara antes
                        //de obtener el valor de la celda
                        SwingUtilities.invokeLater(() ->
                        {
                            InventoryProduct inventoryProduct = inventoryProductTableModel.get(
                                jtableInventoryProducts.getEditingRow());
                            oValue = inventoryProduct.getRealUnits();
                            nValue = null;
                            if (inventoryProduct.getStatus().equals(EntityStatus.LOCKED))
                            {
                                jtableInventoryProducts.getCellEditor().stopCellEditing();
                            }
                        });
                    }
                    else
                    {
                        InventoryProduct inventoryProduct = inventoryProductTableModel.get(
                            jtableInventoryProducts.getEditingRow());
                        nValue = inventoryProduct.getRealUnits();
                        //Si el valor de la celda cambió, realizar las actualizaciones correspondientes
                        if (!nValue.equals(oValue))
                        {
                            IInventoryProductRequest iInventoryProductReq =
                                Application.getContext().getBean(IInventoryProductRequest.class);
                            
                            InventoryProductRequest inventoryProductReq = null;
                            
                            //Llave de búsqueda para solicitud de sistema
                            InventoryProductRequestPk inventoryProductReqPk =
                                new InventoryProductRequestPk(inventoryProduct.getInventory(),
                                    inventoryProduct.getProduct(), inventoryProduct.getUnit());
                            
                            if (inventoryProduct.getUnits().compareTo(inventoryProduct.getRealUnits()) != 0)
                            {
                                if ((inventoryProductReq = iInventoryProductReq
                                    .findById(inventoryProductReqPk)) == null)
                                {
                                    inventoryProductReq = new InventoryProductRequest();
                                
                                    inventoryProductReq.setInventoryProductRequestPk(inventoryProductReqPk);
                                    //Si la cantidad contabilizada es mayor (número negativo)
                                    //será necesario realizar una entrada o compra,
                                    //si es menor (número positivo) será necesario realizar una salida o venta.
                                    inventoryProductReq.setRequiredUnits(
                                        inventoryProduct.getUnits() - inventoryProduct.getRealUnits());
                                    inventoryProductReq.setCreatedBy(ApplicationSession.getUser());

                                    iInventoryProductReq.save(inventoryProductReq);
                                    return;
                                }
                                //Si la cantidad contabilizada es mayor (número negativo)
                                //será necesario realizar una entrada o compra,
                                //si es menor (número positivo) será necesario realizar una salida o venta.
                                inventoryProductReq.setRequiredUnits(
                                    inventoryProduct.getUnits() - inventoryProduct.getRealUnits());
                                inventoryProductReq.setUpdatedBy(ApplicationSession.getUser());
                                inventoryProductReq.setUpdatedDate(new GregorianCalendar());
                                
                                iInventoryProductReq.update(inventoryProductReq);
                            }
                            
                            if (inventoryProduct.getUnits().compareTo(inventoryProduct.getRealUnits()) == 0)
                            {
                                if ((inventoryProductReq = iInventoryProductReq
                                    .findById(inventoryProductReqPk)) != null)
                                {
                                    iInventoryProductReq.delete(inventoryProductReq);
                                }
                            }
                            iInventoryProduct.update(inventoryProduct);
                        }
                    }
                }
            }
        });
        jtableInventoryProducts.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        JScrollPane jscrollPane = new JScrollPane(jtableInventoryProducts);
        jscrollPane.setPreferredSize(new Dimension(650, 250));

        JButton jbtnAccept = new JButton("Aceptar");
        
        jbtnAccept.addActionListener((ActionEvent e) ->
        {
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnAccept);
        });
        
        if (JOptionPane.showOptionDialog(this, jscrollPane, " Cotejar registro", JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, new Object[]{jbtnAccept, "Cerrar"}, jbtnAccept) == 0)
        {
            if (jtableInventoryProducts.isEditing())
            {
                jtableInventoryProducts.getCellEditor().stopCellEditing();
            }
        }
        
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para cancelar registro">
    private void cancel()
    {
        
        InventoryTableModel inventoryTableModel = (InventoryTableModel) jtableInventory.getModel();
        
        if (jtableInventory.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Cancelar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (InventoryType.CANCEL == inventoryTableModel.get(jtableInventory.getSelectedRow()).getType())
        {
            OptionPane.showMessageDialog(this, "No es posible cancelar este tipo de registro.",
                " Cancelar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere cancelar el registro?", 
            " Cancelar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        
        Inventory inventory = iInventory.findById(
            inventoryTableModel.get(jtableInventory.getSelectedRow()).getId());
        inventory.setType(InventoryType.CANCEL);
        inventory.setUpdatedBy(ApplicationSession.getUser());
        inventory.setUpdatedDate(new GregorianCalendar());

        iInventory.update(inventory);

        if (inventory.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha cancelado exitosamente.",
                " Cancelar registro", OptionPane.SUCCESS_MESSAGE);
            
            if (ApplicationSession.getNotifications().stream()
                .anyMatch(x -> x.getCode().equals(Notification.INVENTORY_CANCEL)
                    && x.getStatus().equals(EntityStatus.ACTIVE)))
            {
                ApplicationSession.getThreadPoolExecutor().execute(new InventoryCancelMailProcessor(inventory));
            }
            
            inventoryTableModel.remove(jtableInventory.getSelectedRow());
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar cancelar el registro.",
                " Cancelar registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para eliminar registro">
    private void delete()
    {
        
        InventoryTableModel inventoryTableModel = (InventoryTableModel) jtableInventory.getModel();
        
        if (jtableInventory.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (InventoryType.CANCEL != inventoryTableModel.get(jtableInventory.getSelectedRow()).getType())
        {
            OptionPane.showMessageDialog(this, "Cancele el registro antes de intentar llevar a cabo esta acción.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere eliminar el registro?", 
            " Eliminar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        
        Inventory inventory = iInventory.findById(
            inventoryTableModel.get(jtableInventory.getSelectedRow()).getId());
        inventory.setDeleted(Boolean.TRUE);
        inventory.setUpdatedBy(ApplicationSession.getUser());
        inventory.setUpdatedDate(new GregorianCalendar());

        iInventory.update(inventory);

        if (inventory.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha eliminado exitosamente.",
                " Eliminar registro", OptionPane.SUCCESS_MESSAGE);
            
            inventoryTableModel.remove(jtableInventory.getSelectedRow());
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el registro.",
                " Eliminar registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para buscar registro">
    private void search()
    {
        
        if (jxInitialDatePicker.getDate() == null
            || jxFinalDatePicker.getDate() == null)
        {
            OptionPane.showMessageDialog(this, "Seleccione una fecha de inicio y fin para continuar.",
                " Buscar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Calendar initialDate = Calendar.getInstance();
        initialDate.setTime(Date.from(jxInitialDatePicker.getDate().atStartOfDay().atZone(
            ZoneId.systemDefault()).toInstant()));

        Calendar finalDate = Calendar.getInstance();
        finalDate.setTime(Date.from(jxFinalDatePicker.getDate().atStartOfDay().atZone(
            ZoneId.systemDefault()).toInstant()));
        finalDate.add(Calendar.HOUR_OF_DAY, 23);
        finalDate.add(Calendar.MINUTE, 59);

        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        jtableInventory.setModel(new InventoryTableModel(iInventory.findByInventoryTypeAndDate(
            ((InventoryTypeComboBoxModel) jcboFilterBy.getModel()).getSelectedItem(), initialDate, finalDate)));
        //Se eliminan columnas innecesarias para esta vista
        jtableInventory.getColumnModel().removeColumn(jtableInventory.getColumnModel().getColumn(
            jtableInventory.convertColumnIndexToView(InventoryTableModel.COLUMN_ID)));

        jtxtTotalRows.setText(
            Integer.toString(((InventoryTableModel) jtableInventory.getModel()).getRowCount()));
        
    }
    //</editor-fold>
    
    protected JOptionPane getOptionPane(JComponent parent)
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

        jpanelHeader = new javax.swing.JPanel();
        jlblHeader = new javax.swing.JLabel();
        jbtnCreate = new javax.swing.JButton();
        jbtnEdit = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jbtnDelete = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableInventory = new javax.swing.JTable();
        jlblInitialDatePicker = new javax.swing.JLabel();
        jxInitialDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        jlblFinalDatePicker = new javax.swing.JLabel();
        jxFinalDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        jbtnSearch = new javax.swing.JButton();
        jlblTotalRows = new javax.swing.JLabel();
        jtxtTotalRows = new javax.swing.JLabel();
        jcboFilterBy = new javax.swing.JComboBox<>();
        jlblInitialDatePicker1 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ver movimientos en inventario");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jlblHeader.setText("Listado de inventario");
        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));

        jbtnCreate.setBackground(new java.awt.Color(51, 51, 255));
        jbtnCreate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCreate.setForeground(new java.awt.Color(255, 255, 255));
        jbtnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/asterisk-w.png"))); // NOI18N
        jbtnCreate.setText("Nuevo");
        jbtnCreate.setToolTipText("Efectuar nuevo inventario");
        jbtnCreate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnCreate.setIconTextGap(8);
        jbtnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCreateActionPerformed(evt);
            }
        });

        jbtnEdit.setBackground(new java.awt.Color(17, 157, 17));
        jbtnEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnEdit.setForeground(new java.awt.Color(255, 255, 255));
        jbtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit-w.png"))); // NOI18N
        jbtnEdit.setText("Cotejar");
        jbtnEdit.setToolTipText("Cotejar unidades en inventario");
        jbtnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnEdit.setIconTextGap(8);
        jbtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditActionPerformed(evt);
            }
        });

        jbtnCancel.setBackground(new java.awt.Color(234, 168, 0));
        jbtnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCancel.setForeground(new java.awt.Color(255, 255, 255));
        jbtnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ban-circle-w.png"))); // NOI18N
        jbtnCancel.setText("Cancelar");
        jbtnCancel.setToolTipText("Cancelar registro");
        jbtnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        jbtnDelete.setBackground(new java.awt.Color(157, 16, 16));
        jbtnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDelete.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin-w.png"))); // NOI18N
        jbtnDelete.setText("Eliminar");
        jbtnDelete.setToolTipText("Eliminar registro");
        jbtnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnDelete.setIconTextGap(8);
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanelHeaderLayout = new javax.swing.GroupLayout(jpanelHeader);
        jpanelHeader.setLayout(jpanelHeaderLayout);
        jpanelHeaderLayout.setHorizontalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelHeaderLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jlblHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jbtnCreate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnCancel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDelete)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jtableInventory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableInventory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableInventory.setRowHeight(25);
        jtableInventory.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableInventory);

        jlblInitialDatePicker.setText("Fecha inicial:");
        jlblInitialDatePicker.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblFinalDatePicker.setText("Fecha final:");
        jlblFinalDatePicker.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jbtnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/search-b.png"))); // NOI18N
        jbtnSearch.setText("Buscar");
        jbtnSearch.setBackground(new java.awt.Color(204, 204, 204));
        jbtnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnSearch.setForeground(new java.awt.Color(0, 0, 0));
        jbtnSearch.setIconTextGap(8);
        jbtnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnSearchActionPerformed(evt);
            }
        });

        jlblTotalRows.setText("Total de registros:");
        jlblTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jtxtTotalRows.setText("0");
        jtxtTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jlblInitialDatePicker1.setText("Tipo de movimiento");
        jlblInitialDatePicker1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                    .addComponent(jsepHeader)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlblInitialDatePicker1)
                                    .addComponent(jcboFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jxInitialDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblInitialDatePicker))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jlblFinalDatePicker)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jxFinalDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbtnSearch))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlblTotalRows)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtTotalRows)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblInitialDatePicker1)
                    .addComponent(jlblInitialDatePicker)
                    .addComponent(jlblFinalDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jxInitialDatePicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jxFinalDatePicker, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jcboFilterBy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                    .addComponent(jbtnSearch))
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblTotalRows)
                    .addComponent(jtxtTotalRows))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSearchActionPerformed

        search();
        
    }//GEN-LAST:event_jbtnSearchActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed

        delete();

    }//GEN-LAST:event_jbtnDeleteActionPerformed

    private void jbtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditActionPerformed

        edit();

    }//GEN-LAST:event_jbtnEditActionPerformed

    private void jbtnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCreateActionPerformed
        
        create();
        
    }//GEN-LAST:event_jbtnCreateActionPerformed

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        
        cancel();
        
    }//GEN-LAST:event_jbtnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnCreate;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnEdit;
    private javax.swing.JButton jbtnSearch;
    private javax.swing.JComboBox<InventoryType> jcboFilterBy;
    private javax.swing.JLabel jlblFinalDatePicker;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblInitialDatePicker;
    private javax.swing.JLabel jlblInitialDatePicker1;
    private javax.swing.JLabel jlblTotalRows;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableInventory;
    private javax.swing.JLabel jtxtTotalRows;
    private com.github.lgooddatepicker.components.DatePicker jxFinalDatePicker;
    private com.github.lgooddatepicker.components.DatePicker jxInitialDatePicker;
    // End of variables declaration//GEN-END:variables
}

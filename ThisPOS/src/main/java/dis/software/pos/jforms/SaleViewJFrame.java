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
import dis.software.pos.OptionPane;
import dis.software.pos.entities.Notification;
import dis.software.pos.entities.Sale;
import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.SalePromotionDetail;
import dis.software.pos.interfaces.ISale;
import dis.software.pos.interfaces.ISaleDetail;
import dis.software.pos.interfaces.ISalePromotionDetail;
import dis.software.pos.mail.processors.SaleCancelMailProcessor;
import dis.software.pos.table.cell.renderer.CalendarCellRenderer;
import dis.software.pos.table.cell.renderer.MoneyCellRenderer;
import dis.software.pos.table.model.SaleDetailTableModel;
import dis.software.pos.table.model.SalePromotionDetailTableModel;
import dis.software.pos.table.model.SaleTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la visualización de las ventas realizadas
 * @author Milton Cavazos
 */
public class SaleViewJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(SaleViewJFrame.class.getSimpleName());

    /**
     * Creación de nuevo formulario SalesDetailJFrame
     */
    public SaleViewJFrame()
    {
        
        initComponents();
        
        jtableSales.setModel(new SaleTableModel());
        jtableSales.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableSales.setShowHorizontalLines(Boolean.TRUE);
        jtableSales.setGridColor(new Color(179, 179, 179));
        
        jtableSales.setAutoCreateRowSorter(true);
        
        jtableSales.setDefaultRenderer(Calendar.class, new CalendarCellRenderer());
        jtableSales.setDefaultRenderer(Double.class,
            new MoneyCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
            {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (column != SaleTableModel.COLUMN_GAINS_LOSSES - 1)
                {
                    setIcon(null);
                    return this;
                }
                if ((Double) value > 0.00)
                {
                    setIcon(new ImageIcon(getClass().getResource("/images/up.png")));
                }
                else
                {
                    setIcon(new ImageIcon(getClass().getResource("/images/down.png")));
                }
                return this;
            }
        });
        
        //Se eliminan columnas innecesarias para esta vista
        jtableSales.getColumnModel().removeColumn(jtableSales.getColumnModel().getColumn(
            jtableSales.convertColumnIndexToView(SaleTableModel.COLUMN_ID)));
        
        jtableSales.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JPopupMenu jpopupMenu = new JPopupMenu();
        JMenuItem jmItemViewDetail = new JMenuItem("Ver detalle");
        JMenuItem jmItemDelete = new JMenuItem("Eliminar registro");
        jmItemViewDetail.addActionListener((ActionEvent e) ->
        {
            if (e.getSource() == jmItemViewDetail)
            {
                details();
            }
        });
        jmItemDelete.addActionListener((ActionEvent e) ->
        {
            if (e.getSource() == jmItemDelete)
            {
                delete();
            }
        });
        jpopupMenu.add(jmItemViewDetail);
        jpopupMenu.add(jmItemDelete);
        jtableSales.setComponentPopupMenu(jpopupMenu);
        
        jxInitialDatePicker.getComponentToggleCalendarButton().setText("");
        jxInitialDatePicker.getComponentToggleCalendarButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/calendar-b.png")));
        jxInitialDatePicker.getSettings().setAllowKeyboardEditing(false);
        
        jxFinalDatePicker.getComponentToggleCalendarButton().setText("");
        jxFinalDatePicker.getComponentToggleCalendarButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/calendar-b.png")));
        jxFinalDatePicker.getSettings().setAllowKeyboardEditing(false);
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para ver detalle de un registro">
    private void details()
    {
        
        SaleTableModel saleTableModel = (SaleTableModel) jtableSales.getModel();
        
        if (jtableSales.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Ver detalle de registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Sale sale = new Sale(saleTableModel.get(jtableSales.getSelectedRow()).getId());
        
        //Tabla de productos
        JTable jtableSaleDetail = new JTable();
        
        ISaleDetail iSaleDetail = Application.getContext().getBean(ISaleDetail.class);
        
        List<SaleDetail> saleDetails = iSaleDetail.findBySale(sale);
        jtableSaleDetail.setModel(new SaleDetailTableModel(saleDetails));
        jtableSaleDetail.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jtableSaleDetail.setRowHeight(25);
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableSaleDetail.setShowHorizontalLines(Boolean.TRUE);
        jtableSaleDetail.setGridColor(new Color(179, 179, 179));
        
        jtableSaleDetail.setDefaultRenderer(Double.class, new MoneyCellRenderer());

        //Se eliminan columnas innecesarias para esta vista
        jtableSaleDetail.getColumnModel().removeColumn(jtableSaleDetail.getColumnModel().getColumn(
            jtableSaleDetail.convertColumnIndexToView(SaleDetailTableModel.COLUMN_SALE_ID)));
        jtableSaleDetail.getColumnModel().removeColumn(jtableSaleDetail.getColumnModel().getColumn(
            jtableSaleDetail.convertColumnIndexToView(SaleDetailTableModel.COLUMN_PROD_ID)));
        
        //Tabla de promociones
        JTable jtableSalePromotionDetail = new JTable();
        
        ISalePromotionDetail iSalePromotionDetail = Application.getContext().getBean(ISalePromotionDetail.class);
        
        List<SalePromotionDetail> salePromotionDetails = iSalePromotionDetail.findBySale(sale);
        jtableSalePromotionDetail.setModel(new SalePromotionDetailTableModel(salePromotionDetails));
        jtableSalePromotionDetail.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jtableSalePromotionDetail.setRowHeight(25);
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableSaleDetail.setShowHorizontalLines(Boolean.TRUE);
        jtableSaleDetail.setGridColor(new Color(179, 179, 179));
        
        jtableSalePromotionDetail.setDefaultRenderer(Double.class, new MoneyCellRenderer());

        //Se eliminan columnas innecesarias para esta vista
        jtableSalePromotionDetail.getColumnModel().removeColumn(jtableSalePromotionDetail.getColumnModel().getColumn(
            jtableSalePromotionDetail.convertColumnIndexToView(SalePromotionDetailTableModel.COLUMN_SALE_ID)));
        jtableSalePromotionDetail.getColumnModel().removeColumn(jtableSalePromotionDetail.getColumnModel().getColumn(
            jtableSalePromotionDetail.convertColumnIndexToView(SalePromotionDetailTableModel.COLUMN_PROM_ID)));

        JScrollPane jscrollPaneSaleDetail = new JScrollPane(jtableSaleDetail);
        jscrollPaneSaleDetail.setPreferredSize(new Dimension(650, 200));
        
        JScrollPane jscrollPaneSalePromotionDetail = new JScrollPane(jtableSalePromotionDetail);
        jscrollPaneSalePromotionDetail.setPreferredSize(new Dimension(650, 200));
        
        JTabbedPane jtabbedSaleDetails = new JTabbedPane();
        jtabbedSaleDetails.add("Productos (" + saleDetails.size() + ")", jscrollPaneSaleDetail);
        jtabbedSaleDetails.add("Promociones (" + salePromotionDetails.size() + ")", jscrollPaneSalePromotionDetail);
        
        JOptionPane.showMessageDialog(this, jtabbedSaleDetails, " Ver detalle de registro", JOptionPane.PLAIN_MESSAGE);
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para eliminar un registro">
    private void delete()
    {
        
        SaleTableModel saleTableModel = (SaleTableModel) jtableSales.getModel();
        
        if (jtableSales.getSelectedRow() == -1)
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
        
        ISale iSale = Application.getContext().getBean(ISale.class);
        
        Sale sale = iSale.findById(saleTableModel.get(jtableSales.getSelectedRow()).getId());
        sale.setDeleted(Boolean.TRUE);
        sale.setUpdatedBy(ApplicationSession.getUser());
        sale.setUpdatedDate(new GregorianCalendar());

        iSale.update(sale);

        if (sale.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha eliminado exitosamente.",
                " Eliminar registro", OptionPane.SUCCESS_MESSAGE);
            
            if (ApplicationSession.getNotifications().stream()
                .anyMatch(x -> x.getCode().equals(Notification.SALE_CANCEL)
                    && x.getStatus().equals(EntityStatus.ACTIVE)))
            {
                ApplicationSession.getThreadPoolExecutor().execute(new SaleCancelMailProcessor(sale));
            }
            
            saleTableModel.remove(jtableSales.getSelectedRow());
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el registro.",
                " Eliminar registro", JOptionPane.ERROR_MESSAGE);
        }
        
        jtxtTotalRows.setText(Integer.toString(saleTableModel.getRowCount()));
        
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

        jpanelHeader = new javax.swing.JPanel();
        jlblHeader = new javax.swing.JLabel();
        jbtnDetail = new javax.swing.JButton();
        jbtnDelete = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jlblInitialDatePicker = new javax.swing.JLabel();
        jxInitialDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        jlblFinalDatePicker = new javax.swing.JLabel();
        jxFinalDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        jbtnSearch = new javax.swing.JButton();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableSales = new javax.swing.JTable();
        jlblTotalRows = new javax.swing.JLabel();
        jtxtTotalRows = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ver ventas");

        jlblHeader.setText("Listado de ventas");
        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));

        jbtnDetail.setBackground(new java.awt.Color(17, 157, 17));
        jbtnDetail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDetail.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/eye-open-w.png"))); // NOI18N
        jbtnDetail.setText("Ver detalle");
        jbtnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnDetail.setIconTextGap(8);
        jbtnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDetailActionPerformed(evt);
            }
        });

        jbtnDelete.setBackground(new java.awt.Color(157, 16, 16));
        jbtnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDelete.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin-w.png"))); // NOI18N
        jbtnDelete.setText("Eliminar");
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
                .addComponent(jbtnDetail)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDelete)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

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

        jtableSales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableSales.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableSales.setRowHeight(25);
        jtableSales.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableSales);

        jlblTotalRows.setText("Total de registros:");
        jlblTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jtxtTotalRows.setText("0");
        jtxtTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

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
                                .addComponent(jlblInitialDatePicker)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jxInitialDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jlblFinalDatePicker)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jxFinalDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jbtnSearch))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlblTotalRows)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtTotalRows)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jbtnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jxInitialDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jxFinalDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblInitialDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblFinalDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblTotalRows)
                    .addComponent(jtxtTotalRows))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDetailActionPerformed

        details();

    }//GEN-LAST:event_jbtnDetailActionPerformed

    private void jbtnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnSearchActionPerformed

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

        ISale iSale = Application.getContext().getBean(ISale.class);
        
        jtableSales.setModel(new SaleTableModel(iSale.findByDateRange(initialDate, finalDate)));
        //Se eliminan columnas innecesarias para esta vista
        jtableSales.getColumnModel().removeColumn(jtableSales.getColumnModel().getColumn(
            jtableSales.convertColumnIndexToView(SaleTableModel.COLUMN_ID)));

        jtxtTotalRows.setText(Integer.toString(((SaleTableModel) jtableSales.getModel()).getRowCount()));
        
    }//GEN-LAST:event_jbtnSearchActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed

        delete();
        
    }//GEN-LAST:event_jbtnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnDetail;
    private javax.swing.JButton jbtnSearch;
    private javax.swing.JLabel jlblFinalDatePicker;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblInitialDatePicker;
    private javax.swing.JLabel jlblTotalRows;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableSales;
    private javax.swing.JLabel jtxtTotalRows;
    private com.github.lgooddatepicker.components.DatePicker jxFinalDatePicker;
    private com.github.lgooddatepicker.components.DatePicker jxInitialDatePicker;
    // End of variables declaration//GEN-END:variables
}

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
import dis.software.pos.InventoryType;
import dis.software.pos.entities.Inventory;
import dis.software.pos.interfaces.IInventory;
import dis.software.pos.interfaces.IInventoryProduct;
import dis.software.pos.table.cell.renderer.CalendarCellRenderer;
import dis.software.pos.table.cell.renderer.InventoryTypeCellRenderer;
import dis.software.pos.table.model.InventoryProductTableModel;
import dis.software.pos.table.model.InventoryTableModel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Formulario para la cancelación de movimientos en inventario
 * @author Milton Cavazos
 */
public class InventoryCancelJFrame extends javax.swing.JInternalFrame
{

    /**
     * Creación de nuevo formulario InventoryCancelJFrame
     */
    public InventoryCancelJFrame()
    {
        initComponents();
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        jtableInventory.setModel(
            new InventoryTableModel(iInventory.findByInventoryType(InventoryType.INCOME)));
        jtableInventory.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        //Se eliminan columnas innecesarias para esta vista
        jtableInventory.getColumnModel().removeColumn(jtableInventory.getColumnModel().getColumn(
            jtableInventory.convertColumnIndexToView(InventoryProductTableModel.COLUMN_PROD_ID)));
        
        jtableInventory.setDefaultRenderer(InventoryType.class, new InventoryTypeCellRenderer());
        jtableInventory.setDefaultRenderer(Calendar.class, new CalendarCellRenderer());
        
        jtableInventory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JPopupMenu jpopupMenu = new JPopupMenu();
        JMenuItem jmItemViewDetail = new JMenuItem("Ver detalle");
        JMenuItem jmItemCancel = new JMenuItem("Cancelar registro");
        jmItemViewDetail.addActionListener((ActionEvent e) -> {
            if (e.getSource() == jmItemViewDetail)
            {
                showInventoryProductsDialog();
            }
        });
        jmItemCancel.addActionListener((ActionEvent e) -> {
            if (e.getSource() == jmItemCancel)
            {
                jbtnCancel.doClick();
            }
        });
        jpopupMenu.add(jmItemViewDetail);
        jpopupMenu.add(jmItemCancel);
        jtableInventory.setComponentPopupMenu(jpopupMenu);
        
    }
    
    private void showInventoryProductsDialog()
    {
               
        if (jtableInventory.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un registro.");
            return;
        }
        
        IInventoryProduct iInventoryProduct = Application.getContext().getBean(IInventoryProduct.class);
        JTable jtableInvProducts = new JTable();
        
        Inventory inventory = new Inventory((Long) jtableInventory.getModel().getValueAt(
            jtableInventory.getSelectedRow(), InventoryTableModel.COLUMN_ID));
        jtableInvProducts.setModel(
            new InventoryProductTableModel(iInventoryProduct.findByInventory(inventory)));
        jtableInvProducts.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jtableInvProducts.setRowHeight(25);

        //Se eliminan columnas innecesarias para esta vista
        jtableInvProducts.getColumnModel().removeColumn(jtableInvProducts.getColumnModel().getColumn(
            jtableInvProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_PROD_ID)));
        jtableInvProducts.getColumnModel().removeColumn(jtableInvProducts.getColumnModel().getColumn(
            jtableInvProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_TYPE)));
        jtableInvProducts.getColumnModel().removeColumn(jtableInvProducts.getColumnModel().getColumn(
            jtableInvProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_DATE)));

        JScrollPane jScrollPane = new JScrollPane(jtableInvProducts);
        jScrollPane.setPreferredSize(new Dimension(650, 200));

        if (JOptionPane.showOptionDialog(this, jScrollPane, "Detalle de registro", JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar", "Cancelar registro"}, null) == 1)
        {
            jbtnCancel.doClick();
        }
        
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
        jbtnDetail = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableInventory = new javax.swing.JTable();

        setClosable(true);
        setTitle("Cancelaciones de inventario");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Cancelación de inventario");

        jbtnDetail.setBackground(new java.awt.Color(17, 157, 17));
        jbtnDetail.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDetail.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDetail.setText("Ver detalle");
        jbtnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDetailActionPerformed(evt);
            }
        });

        jbtnCancel.setBackground(new java.awt.Color(204, 204, 204));
        jbtnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCancel.setForeground(new java.awt.Color(0, 0, 0));
        jbtnCancel.setText("Cancelar registro");
        jbtnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanelHeaderLayout = new javax.swing.GroupLayout(jpanelHeader);
        jpanelHeader.setLayout(jpanelHeaderLayout);
        jpanelHeaderLayout.setHorizontalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelHeaderLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jlblHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jbtnDetail)
                .addGap(18, 18, 18)
                .addComponent(jbtnCancel)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbtnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblHeader))
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jsepHeader)
                    .addComponent(jscrollPaneTable, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDetailActionPerformed
        
        showInventoryProductsDialog();
        
    }//GEN-LAST:event_jbtnDetailActionPerformed

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        
        if (JOptionPane.showConfirmDialog(this, "¿Estás seguro de que quieres cancelar el registro?",
            "Advertencia", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        
        Inventory inventory = iInventory.findById((Long) jtableInventory.getModel().getValueAt(
            jtableInventory.getSelectedRow(), InventoryTableModel.COLUMN_ID));
        inventory.setType(InventoryType.CANCEL);
        inventory.setUpdatedBy(ApplicationSession.getUser().getId());
        inventory.setUpdatedDate(new GregorianCalendar());

        iInventory.update(inventory);
        iInventory.flush();

        if (inventory.getId() != null)
        {
            JOptionPane.showMessageDialog(this, "El registro se ha cancelado exitosamente.");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar cancelar el registro.");
        }
        
        ((InventoryTableModel) jtableInventory.getModel()).remove(
            jtableInventory.getSelectedRow());
        
    }//GEN-LAST:event_jbtnCancelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnDetail;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableInventory;
    // End of variables declaration//GEN-END:variables
}

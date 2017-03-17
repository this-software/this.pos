/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.InventoryType;
import dis.software.pos.OptionPane;
import dis.software.pos.TableFilterBy;
import dis.software.pos.combobox.model.TableFilterByComboBoxModel;
import dis.software.pos.combobox.renderers.TableFilterByComboBoxRenderer;
import dis.software.pos.entities.Inventory;
import dis.software.pos.interfaces.IInventory;
import dis.software.pos.table.cell.renderer.CalendarCellRenderer;
import dis.software.pos.table.cell.renderer.InventoryTypeCellRenderer;
import dis.software.pos.table.model.InventoryTableModel;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 * Formulario para la eliminación de movimientos en inventario cancelados
 * @author Milton Cavazos
 */
public class InventoryBinJFrame extends javax.swing.JInternalFrame
{
    
    private final TableRowSorter<InventoryTableModel> sorter;

    /**
     * Creación de nuevo formulario InventoryCancelJFrame
     */
    public InventoryBinJFrame()
    {
        
        initComponents();
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        InventoryTableModel inventoryTableModel = new InventoryTableModel(iInventory.getDeleted());
        
        jtableInventory.setModel(inventoryTableModel);
        jtableInventory.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableInventory.setShowHorizontalLines(Boolean.TRUE);
        jtableInventory.setGridColor(new Color(179, 179, 179));
        
        sorter = new TableRowSorter<>(inventoryTableModel);
        jtableInventory.setRowSorter(sorter);
        
        jtableInventory.setDefaultRenderer(InventoryType.class, new InventoryTypeCellRenderer());
        jtableInventory.setDefaultRenderer(Calendar.class, new CalendarCellRenderer());
        
        //Se eliminan columnas innecesarias para esta vista
        jtableInventory.getColumnModel().removeColumn(jtableInventory.getColumnModel().getColumn(
            jtableInventory.convertColumnIndexToView(InventoryTableModel.COLUMN_ID)));
        
        jtableInventory.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        jtxtFilter.getDocument().addDocumentListener(
            new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });
        
        List<TableFilterBy> list = new ArrayList<>();
        list.add(new TableFilterBy(InventoryTableModel.COLUMN_CODE, "Código"));
        list.add(new TableFilterBy(InventoryTableModel.COLUMN_TYPE, "Tipo"));
        list.add(new TableFilterBy(InventoryTableModel.COLUMN_CREATED_BY, "Creado por"));
        jcboFilterBy.setModel(new TableFilterByComboBoxModel(list));
        jcboFilterBy.setRenderer(new TableFilterByComboBoxRenderer());
        
        jtxtTotalRows.setText(Integer.toString(((InventoryTableModel) jtableInventory.getModel()).getRowCount()));
        
    }
    
    private void filter()
    {
        
        RowFilter<InventoryTableModel, Object> descRowFilter = null;
        try
        {
            descRowFilter = RowFilter.regexFilter("(?i)" + jtxtFilter.getText() + "(?-i)",
                ((TableFilterByComboBoxModel) jcboFilterBy.getModel()).getSelectedItem().getTableColumnIndex());
        }
        catch (PatternSyntaxException e)
        {
            return;
        }
        sorter.setRowFilter(descRowFilter);
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para eliminar un registro">
    private void delete()
    {
        
        InventoryTableModel inventoryTableModel = (InventoryTableModel) jtableInventory.getModel();
        
        if (jtableInventory.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el registro "
            + "de forma permanente?", " Eliminar registro", JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        Inventory inventory = iInventory.findById(inventoryTableModel.get(
            jtableInventory.convertRowIndexToModel(jtableInventory.getSelectedRow())).getId());

        iInventory.delete(inventory);
        
        inventoryTableModel.remove(
            jtableInventory.convertRowIndexToModel(jtableInventory.getSelectedRow()));
        
        jtxtTotalRows.setText(Integer.toString(inventoryTableModel.getRowCount()));
        
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
        jbtnDelete = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableInventory = new javax.swing.JTable();
        jlblTotalRows = new javax.swing.JLabel();
        jtxtTotalRows = new javax.swing.JLabel();
        jlblFilterBy = new javax.swing.JLabel();
        jcboFilterBy = new javax.swing.JComboBox<>();
        jlblTextToFilter = new javax.swing.JLabel();
        jtxtFilter = new javax.swing.JTextField();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Papelera de inventarios");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Listado de inventarios cancelados");

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
                .addComponent(jbtnDelete)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jlblTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlblTotalRows.setText("Total de registros:");

        jtxtTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jtxtTotalRows.setText("0");

        jlblFilterBy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblFilterBy.setText("Buscar por:");

        jlblTextToFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblTextToFilter.setText("Texto a buscar:");

        jtxtFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsepHeader, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jscrollPaneTable)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jlblTotalRows)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtTotalRows)
                        .addGap(0, 624, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jlblFilterBy)
                .addGap(8, 8, 8)
                .addComponent(jcboFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblTextToFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblTextToFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcboFilterBy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed

        delete();

    }//GEN-LAST:event_jbtnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JComboBox<TableFilterBy> jcboFilterBy;
    private javax.swing.JLabel jlblFilterBy;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblTextToFilter;
    private javax.swing.JLabel jlblTotalRows;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableInventory;
    private javax.swing.JTextField jtxtFilter;
    private javax.swing.JLabel jtxtTotalRows;
    // End of variables declaration//GEN-END:variables
}

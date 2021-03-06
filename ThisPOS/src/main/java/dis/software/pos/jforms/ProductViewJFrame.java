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
import dis.software.pos.TableFilterBy;
import dis.software.pos.combobox.model.TableFilterByComboBoxModel;
import dis.software.pos.combobox.renderers.TableFilterByComboBoxRenderer;
import dis.software.pos.entities.Notification;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductRequest;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.interfaces.IProductRequest;
import dis.software.pos.jforms.components.RequestProductComponent;
import dis.software.pos.mail.processors.ProductRequestMailProcessor;
import dis.software.pos.table.cell.renderer.MoneyCellRenderer;
import dis.software.pos.table.model.ProductTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

/**
 * Formulario para la visualización de productos
 * @author Milton Cavazos
 */
public class ProductViewJFrame extends javax.swing.JInternalFrame
{
    
    private TableRowSorter<ProductTableModel> sorter;
    
    /**
     * Creación de nuevo formulario ProductViewJFrame
     */
    public ProductViewJFrame()
    {
        
        initComponents();
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        ProductTableModel productTableModel = new ProductTableModel(iProduct.findAll());
        
        jtableProducts.setModel(productTableModel);
        jtableProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableProducts.setShowHorizontalLines(Boolean.TRUE);
        jtableProducts.setGridColor(new Color(179, 179, 179));
        
        sorter = new TableRowSorter<>(productTableModel);
        jtableProducts.setRowSorter(sorter);
        
        jtableProducts.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
            {
                setBackground(getStockLevelColor(table, table.convertRowIndexToModel(row)));
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                return this;
            }
        });
        jtableProducts.setDefaultRenderer(Double.class, new MoneyCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column)
            {
                setBackground(getStockLevelColor(table, table.convertRowIndexToModel(row)));
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                return this;
            }
        });
        
        //Se eliminan columnas innecesarias para esta vista
        jtableProducts.getColumnModel().removeColumn(jtableProducts.getColumnModel().getColumn(
            jtableProducts.convertColumnIndexToView(ProductTableModel.COLUMN_ID)));
        
        jtableProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
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
        list.add(new TableFilterBy(ProductTableModel.COLUMN_CODE, "Código"));
        list.add(new TableFilterBy(ProductTableModel.COLUMN_NAME, "Nombre"));
        list.add(new TableFilterBy(ProductTableModel.COLUMN_DESC, "Descripción"));
        list.add(new TableFilterBy(ProductTableModel.COLUMN_CATEGORY, "Categoría"));
        list.add(new TableFilterBy(ProductTableModel.COLUMN_PROVIDER, "Proveedor"));
        jcboFilterBy.setModel(new TableFilterByComboBoxModel(list));
        jcboFilterBy.setRenderer(new TableFilterByComboBoxRenderer());
        
        jtxtTotalRows.setText(Integer.toString(((ProductTableModel) jtableProducts.getModel()).getRowCount()));
        
    }
    
    private Color getStockLevelColor(JTable table, int rowIndex)
    {
        
        Integer unitsInStock = ((ProductTableModel) table.getModel()).get(rowIndex).getUnitsInStock();
        Integer minStockLevel = ((ProductTableModel) table.getModel()).get(rowIndex).getMinStockLevel();
        if (unitsInStock > minStockLevel)
        {
            return new Color(64, 191, 64); //Verde
        }
        if (unitsInStock > 0 && unitsInStock <= minStockLevel)
        {
            return new Color(255, 204, 0); //Amarillo
        }
        if (unitsInStock <= 0)
        {
            return new Color(255, 112, 77); //Rojo
        }
        return null;
        
    }
    
    private void filter()
    {
        
        RowFilter<ProductTableModel, Object> descRowFilter = null;
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
    
    //<editor-fold defaultstate="collapsed" desc="Método para solicitar un producto">
    private void request()
    {
        
        ProductTableModel productTableModel = (ProductTableModel) jtableProducts.getModel();
        
        if (jtableProducts.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Solicitud de producto", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Product product = productTableModel.get(
            jtableProducts.convertRowIndexToModel(jtableProducts.getSelectedRow()));
        
        RequestProductComponent component = new RequestProductComponent(product);
        
        JButton jbtnRequire = new JButton("Solicitar");
        JButton jbtnCancel = new JButton("Cancelar");
        
        jbtnRequire.addActionListener((ActionEvent e) ->
        {
            //Si no se ha seleccionado ninguna unidad
            if (RequestProductComponent.getUnit() == null)
            {
                OptionPane.showMessageDialog(this, "Seleccione una unidad para realizar la solicitud.",
                    " Solicitud de producto", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            //Si no ha sido ingresada alguna cantidad
            if (RequestProductComponent.getQuantity() == -1)
            {
                String jlblMsg = "<html>Solo se permiten números en el campo <b>Cantidad</b>.</html>";
                OptionPane.showMessageDialog(this, jlblMsg,
                    " Solicitud de producto", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnRequire);
        });
        jbtnCancel.addActionListener((ActionEvent e) ->
        {
            JOptionPane jop = getOptionPane((JComponent) e.getSource());
            jop.setValue(jbtnCancel);
        });
        
        IProductRequest iProductRequest = Application.getContext().getBean(IProductRequest.class);
        //Si el producto no tiene unidades configuradas
        if (!RequestProductComponent.getIsConfigured()
            //O si el producto tiene una solicitud pendiente
            || iProductRequest.getIsRequired(product))
        {
            //Se bloquean los controles y se muestra el mensaje correspondiente
            jbtnRequire.setEnabled(Boolean.FALSE);
            component.lock();
        }
        
        if (JOptionPane.showOptionDialog(this, component.getContentPane(),
            " Solicitud de producto", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE,
            null, new Object[]{jbtnRequire, jbtnCancel}, jbtnRequire) == 0)
        {
            ProductRequest productRequest = new ProductRequest();
            
            productRequest.setProduct(product);
            productRequest.setUnit(RequestProductComponent.getUnit());
            productRequest.setRequiredUnits(RequestProductComponent.getQuantity());
            productRequest.setCreatedBy(ApplicationSession.getUser());
            
            iProductRequest.save(productRequest);
            
            if (ApplicationSession.getNotifications().stream()
                .anyMatch(x -> x.getCode().equals(Notification.PRODUCT_REQUEST)
                    && x.getStatus().equals(EntityStatus.ACTIVE)))
            {
                ApplicationSession.getThreadPoolExecutor().execute(
                    new ProductRequestMailProcessor(productRequest));
            }
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para modificar un registro">
    private void edit()
    {
        
        ProductTableModel productTableModel = (ProductTableModel) jtableProducts.getModel();
        
        if (jtableProducts.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Product product = productTableModel.get(
            jtableProducts.convertRowIndexToModel(jtableProducts.getSelectedRow()));
        
        ProductEditJFrame productEditJFrame = new ProductEditJFrame(product);
        getDesktopPane().add(productEditJFrame);
        productEditJFrame.pack();
        try
        {
            productEditJFrame.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            //logger.error("Error setting maximum", ex);
        }
        productEditJFrame.setVisible(true);
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para eliminar un registro">
    private void delete()
    {
        
        ProductTableModel productTableModel = (ProductTableModel) jtableProducts.getModel();
        
        if (jtableProducts.getSelectedRow() == -1)
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
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        
        Product product = iProduct.findById(productTableModel.get(
            jtableProducts.convertRowIndexToModel(jtableProducts.getSelectedRow())).getId());
        product.setDeleted(Boolean.TRUE);
        product.setUpdatedBy(ApplicationSession.getUser());
        product.setUpdatedDate(new GregorianCalendar());

        iProduct.update(product);

        if (product.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha eliminado exitosamente.",
                " Eliminar registro", OptionPane.SUCCESS_MESSAGE);
            
            productTableModel.remove(
                jtableProducts.convertRowIndexToModel(jtableProducts.getSelectedRow()));
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el registro.",
                " Eliminar registro", JOptionPane.ERROR_MESSAGE);
        }
        
        jtxtTotalRows.setText(Integer.toString(productTableModel.getRowCount()));
        
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
        jbtnDelete = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jlblFilterBy = new javax.swing.JLabel();
        jcboFilterBy = new javax.swing.JComboBox<>();
        jlblTextToFilter = new javax.swing.JLabel();
        jtxtFilter = new javax.swing.JTextField();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableProducts = new javax.swing.JTable();
        jlblTotalRows = new javax.swing.JLabel();
        jtxtTotalRows = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ver productos");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Listado de productos");

        jbtnCreate.setBackground(new java.awt.Color(51, 51, 255));
        jbtnCreate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCreate.setForeground(new java.awt.Color(255, 255, 255));
        jbtnCreate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cart-in-w.png"))); // NOI18N
        jbtnCreate.setText("Solicitar");
        jbtnCreate.setToolTipText("Solicitar producto");
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
        jbtnEdit.setText("Modificar");
        jbtnEdit.setToolTipText("Modificar producto");
        jbtnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnEdit.setIconTextGap(8);
        jbtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditActionPerformed(evt);
            }
        });

        jbtnDelete.setBackground(new java.awt.Color(157, 16, 16));
        jbtnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDelete.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin-w.png"))); // NOI18N
        jbtnDelete.setText("Eliminar");
        jbtnDelete.setToolTipText("Eliminar producto");
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
                    .addComponent(jbtnCreate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jlblFilterBy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblFilterBy.setLabelFor(jcboFilterBy);
        jlblFilterBy.setText("Buscar por:");

        jlblTextToFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblTextToFilter.setText("Texto a buscar:");

        jtxtFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtableProducts.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableProducts.setRowHeight(25);
        jscrollPaneTable.setViewportView(jtableProducts);

        jlblTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlblTotalRows.setText("Total de registros:");

        jtxtTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jtxtTotalRows.setText("0");

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
                                .addComponent(jlblFilterBy)
                                .addGap(8, 8, 8)
                                .addComponent(jcboFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblTextToFilter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jbtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditActionPerformed
        
        edit();
        
    }//GEN-LAST:event_jbtnEditActionPerformed

    private void jbtnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCreateActionPerformed

        request();

    }//GEN-LAST:event_jbtnCreateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnCreate;
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnEdit;
    private javax.swing.JComboBox<TableFilterBy> jcboFilterBy;
    private javax.swing.JLabel jlblFilterBy;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblTextToFilter;
    private javax.swing.JLabel jlblTotalRows;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableProducts;
    private javax.swing.JTextField jtxtFilter;
    private javax.swing.JLabel jtxtTotalRows;
    // End of variables declaration//GEN-END:variables
}

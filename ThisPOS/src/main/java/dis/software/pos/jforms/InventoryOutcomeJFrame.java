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
import dis.software.pos.combobox.model.CategoryComboBoxModel;
import dis.software.pos.combobox.model.ProductComboBoxModel;
import dis.software.pos.combobox.model.ProviderComboBoxModel;
import dis.software.pos.combobox.renderers.CategoryComboBoxRenderer;
import dis.software.pos.combobox.renderers.ProductComboBoxRenderer;
import dis.software.pos.combobox.renderers.ProviderComboBoxRenderer;
import dis.software.pos.entities.Category;
import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.InventoryProductPk;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.ICategory;
import dis.software.pos.interfaces.IInventory;
import dis.software.pos.interfaces.IInventoryProduct;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.interfaces.IProvider;
import dis.software.pos.table.model.InventoryProductTableModel;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Formulario para sacar productos del inventario
 * @author Milton Cavazos
 */
public class InventoryOutcomeJFrame extends javax.swing.JInternalFrame
{

    /**
     * Creación de nuevo formulario InventoryOutcomeJFrame
     */
    public InventoryOutcomeJFrame()
    {
        initComponents();
        
        ICategory iCategory = Application.getContext().getBean(ICategory.class);
        List<Category> categories = iCategory.findAll();
        categories.add(0, new Category("", "Elige una categoría", ""));
        jcboCategory.setModel(new CategoryComboBoxModel(categories));
        jcboCategory.setRenderer(new CategoryComboBoxRenderer());
        jcboCategory.addItemListener((ItemEvent e) ->
        {
            if (!(e.getItem() instanceof Category))
            {
                return;
            }
            Category category = (Category) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED
                && category.getId() != null)
            {
                IProduct iProduct = Application.getContext().getBean(IProduct.class);
                List<Product> products = jcboProvider.getSelectedIndex() == 0
                    ? iProduct.findByCategory(category)
                    : iProduct.findBy(category, (Provider) jcboProvider.getSelectedItem());
                products.add(0, new Product("", "Elige un producto", ""));
                jcboProduct.setModel(new ProductComboBoxModel(products));
                jcboProduct.setRenderer(new ProductComboBoxRenderer());
            }
        });
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        List<Provider> providers = iProvider.findAll();
        providers.add(0, new Provider("", "Elige un proveedor", ""));
        jcboProvider.setModel(new ProviderComboBoxModel(providers));
        jcboProvider.setRenderer(new ProviderComboBoxRenderer());
        jcboProvider.addItemListener((ItemEvent e) ->
        {
            if (!(e.getItem() instanceof Provider))
            {
                return;
            }
            Provider provider = (Provider) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED
                && provider.getId() != null)
            {
                IProduct iProduct = Application.getContext().getBean(IProduct.class);
                List<Product> products = jcboCategory.getSelectedIndex() == 0
                    ? iProduct.findByProvider(provider)
                    : iProduct.findBy((Category) jcboCategory.getSelectedItem(), provider);
                products.add(0, new Product("", "Elige un producto", ""));
                jcboProduct.setModel(new ProductComboBoxModel(products));
                jcboProduct.setRenderer(new ProductComboBoxRenderer());
            }
        });
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        List<Product> products = iProduct.findAll();
        products.add(0, new Product("", "Elige un producto", ""));
        jcboProduct.setModel(new ProductComboBoxModel(products));
        jcboProduct.setRenderer(new ProductComboBoxRenderer());
        
        jtableInvProducts.setModel(new InventoryProductTableModel());
        jtableInvProducts.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        //Se eliminan columnas innecesarias para esta vista
        jtableInvProducts.getColumnModel().removeColumn(jtableInvProducts.getColumnModel().getColumn(
            jtableInvProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_PROD_ID)));
        jtableInvProducts.getColumnModel().removeColumn(jtableInvProducts.getColumnModel().getColumn(
            jtableInvProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_TYPE)));
        jtableInvProducts.getColumnModel().removeColumn(jtableInvProducts.getColumnModel().getColumn(
            jtableInvProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_DATE)));
        
        jtableInvProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    }
    
    private Boolean isJframeValid()
    {
        if (jtxtUnits.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Por favor ingresa los datos marcados con un asterisco "
                + "para guardar la entrada de inventario.");
            return Boolean.FALSE;
        }
        if (!jtxtUnits.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            JOptionPane.showMessageDialog(this, "Solo se permiten números en el campo Unidades por entrar.");
            return Boolean.FALSE;
        }
        if (jcboProduct.getSelectedIndex() == 0)
        {
            JOptionPane.showMessageDialog(this, "Por favor selecciona un producto "
                + "para guardar la entrada de inventario.");
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
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
        jbtnSave = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jpanelBody = new javax.swing.JPanel();
        jlblCategory = new javax.swing.JLabel();
        jcboCategory = new javax.swing.JComboBox<>();
        jlblProvider = new javax.swing.JLabel();
        jcboProvider = new javax.swing.JComboBox<>();
        jlblProduct = new javax.swing.JLabel();
        jcboProduct = new javax.swing.JComboBox<>();
        jlblUnits = new javax.swing.JLabel();
        jtxtUnits = new javax.swing.JTextField();
        jbtnAddProduct = new javax.swing.JButton();
        jbtnRemoveProduct = new javax.swing.JButton();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableInvProducts = new javax.swing.JTable();

        setClosable(true);

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Salida de inventario");

        jbtnSave.setBackground(new java.awt.Color(17, 157, 17));
        jbtnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnSave.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSave.setText("Guardar");
        jbtnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnSaveMouseClicked(evt);
            }
        });

        jbtnCancel.setBackground(new java.awt.Color(204, 204, 204));
        jbtnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCancel.setForeground(new java.awt.Color(0, 0, 0));
        jbtnCancel.setText("Cancelar");
        jbtnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jpanelHeaderLayout = new javax.swing.GroupLayout(jpanelHeader);
        jpanelHeader.setLayout(jpanelHeaderLayout);
        jpanelHeaderLayout.setHorizontalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelHeaderLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jlblHeader)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 314, Short.MAX_VALUE)
                .addComponent(jbtnSave)
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
                    .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblHeader))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jpanelBody.setBorder(javax.swing.BorderFactory.createTitledBorder("Información de inventario"));

        jlblCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCategory.setText("Categoria");

        jcboCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProvider.setText("Proveedor");

        jcboProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProduct.setText("Producto");

        jcboProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblUnits.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblUnits.setText("* Unidades por salir:");

        jtxtUnits.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jbtnAddProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/glyphicons-191-plus-sign.png"))); // NOI18N
        jbtnAddProduct.setText("Agregar");
        jbtnAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnAddProduct.setIconTextGap(10);
        jbtnAddProduct.setMargin(new java.awt.Insets(2, 2, 2, 10));
        jbtnAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnAddProductMouseClicked(evt);
            }
        });

        jbtnRemoveProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jbtnRemoveProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/glyphicons-193-remove-sign.png"))); // NOI18N
        jbtnRemoveProduct.setText("Eliminar");
        jbtnRemoveProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnRemoveProduct.setIconTextGap(10);
        jbtnRemoveProduct.setMargin(new java.awt.Insets(2, 2, 2, 10));
        jbtnRemoveProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnRemoveProductMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpanelBodyLayout = new javax.swing.GroupLayout(jpanelBody);
        jpanelBody.setLayout(jpanelBodyLayout);
        jpanelBodyLayout.setHorizontalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblProduct)
                    .addComponent(jlblUnits)
                    .addComponent(jlblProvider)
                    .addComponent(jlblCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelBodyLayout.createSequentialGroup()
                        .addComponent(jtxtUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jpanelBodyLayout.createSequentialGroup()
                        .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpanelBodyLayout.setVerticalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBodyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCategory))
                .addGap(18, 18, 18)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProvider))
                .addGap(18, 18, 18)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProduct))
                .addGap(18, 18, 18)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblUnits)
                    .addComponent(jbtnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtableInvProducts.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableInvProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableInvProducts.setRowHeight(25);
        jtableInvProducts.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableInvProducts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jpanelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jsepHeader)
                    .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jpanelBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtableInvProducts.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(this, "Por favor agrega algunos productos para continuar.");
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);

        Inventory inventory = new Inventory();
        inventory.setCode(iInventory.getNextCode());
        inventory.setType(InventoryType.OUTCOME);
        inventory.setCreatedBy(ApplicationSession.getUser().getId());
        
        inventory = iInventory.save(inventory);

        for (int i = 0; i<jtableInvProducts.getModel().getRowCount(); i++)
        {
            //Se asigna la llave primaria
            InventoryProductPk inventoryProductPk = new InventoryProductPk();
            inventoryProductPk.setInventory(inventory);
            inventoryProductPk.setProduct(new Product((Long)
                jtableInvProducts.getModel().getValueAt(i, InventoryProductTableModel.COLUMN_PROD_ID)));

            InventoryProduct inventoryProduct = new InventoryProduct();
            inventoryProduct.setInventoryProductPk(inventoryProductPk);
            inventoryProduct.setUnits((Integer)
            jtableInvProducts.getModel().getValueAt(i, InventoryProductTableModel.COLUMN_UNITS));

            IInventoryProduct iInventoryProduct = Application.getContext().getBean(IInventoryProduct.class);
            iInventoryProduct.save(inventoryProduct);
            iInventoryProduct.flush();
        }

        if (inventory.getId() != null)
        {
            JOptionPane.showMessageDialog(this, "El registro de salida se ha guardado exitosamente.");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar guardar el registro de salida.");
        }

        jcboProduct.setSelectedIndex(0);
        jtxtUnits.setText("");
        ((InventoryProductTableModel) jtableInvProducts.getModel()).removeAll();
        
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jbtnAddProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnAddProductMouseClicked

        if (!isJframeValid()) return;

        InventoryProductTableModel inventoryProductTableModel =
        (InventoryProductTableModel) jtableInvProducts.getModel();

        InventoryProductPk inventoryProductPk = new InventoryProductPk();
        inventoryProductPk.setProduct((Product) jcboProduct.getSelectedItem());

        InventoryProduct inventoryProduct = new InventoryProduct();
        inventoryProduct.setInventoryProductPk(inventoryProductPk);
        inventoryProduct.setUnits(new Integer(jtxtUnits.getText()));

        inventoryProductTableModel.add(inventoryProduct);

    }//GEN-LAST:event_jbtnAddProductMouseClicked

    private void jbtnRemoveProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnRemoveProductMouseClicked

        if (jtableInvProducts.getSelectedRow() == -1)
        {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona la fila que deseas eliminar.");
        }

        InventoryProductTableModel inventoryProductTableModel =
        (InventoryProductTableModel) jtableInvProducts.getModel();
        inventoryProductTableModel.remove(jtableInvProducts.getSelectedRow());

    }//GEN-LAST:event_jbtnRemoveProductMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnAddProduct;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnRemoveProduct;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<Category> jcboCategory;
    private javax.swing.JComboBox<Product> jcboProduct;
    private javax.swing.JComboBox<Provider> jcboProvider;
    private javax.swing.JLabel jlblCategory;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblProduct;
    private javax.swing.JLabel jlblProvider;
    private javax.swing.JLabel jlblUnits;
    private javax.swing.JPanel jpanelBody;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableInvProducts;
    private javax.swing.JTextField jtxtUnits;
    // End of variables declaration//GEN-END:variables
}

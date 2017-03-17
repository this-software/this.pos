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
import dis.software.pos.OptionPane;
import dis.software.pos.combobox.model.CategoryComboBoxModel;
import dis.software.pos.combobox.model.ProductComboBoxModel;
import dis.software.pos.combobox.model.ProviderComboBoxModel;
import dis.software.pos.combobox.model.UnitComboBoxModel;
import dis.software.pos.combobox.renderers.CategoryComboBoxRenderer;
import dis.software.pos.combobox.renderers.ProductComboBoxRenderer;
import dis.software.pos.combobox.renderers.ProviderComboBoxRenderer;
import dis.software.pos.combobox.renderers.UnitComboBoxRenderer;
import dis.software.pos.entities.Category;
import dis.software.pos.entities.Inventory;
import dis.software.pos.entities.InventoryProduct;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductUnit;
import dis.software.pos.entities.Provider;
import dis.software.pos.entities.Unit;
import dis.software.pos.interfaces.ICategory;
import dis.software.pos.interfaces.IInventory;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.interfaces.IProductUnit;
import dis.software.pos.interfaces.IProvider;
import dis.software.pos.table.model.InventoryProductTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para agregar productos al inventario
 * @author Milton Cavazos
 */
public class InventoryIncomeJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(InventoryIncomeJFrame.class.getSimpleName());

    /**
     * Creación de nuevo formulario InventoryIncomeJFrame
     */
    public InventoryIncomeJFrame()
    {
        
        initComponents();
        
        InventoryIncomeJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (((InventoryProductTableModel) jtableInventoryProducts.getModel()).getRowCount() > 0)
                {
                    String jlblMsg = "<html>Los cambios efectuados aún no han sido guardados.<br>"
                        + "¿Está seguro de que quiere continuar?</html>";
                    if (OptionPane.showConfirmDialog(frame, jlblMsg, " Cerrar ventana",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                    {
                        logger.info("Window closed");
                        frame.dispose();
                        return;
                    }
                    return;
                }
                logger.info("Window closed");
                frame.dispose();
            }
        });
        
        ICategory iCategory = Application.getContext().getBean(ICategory.class);
        List<Category> categories = iCategory.findAll();
        categories.add(0, new Category("", "Elige una categoría", ""));
        jcboCategory.setModel(new CategoryComboBoxModel(categories));
        jcboCategory.setRenderer(new CategoryComboBoxRenderer());
        jcboCategory.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                IProduct iProduct = Application.getContext().getBean(IProduct.class);
                //Objeto afectado por el evento
                Category category = (Category) e.getItem();
                List<Product> products;
                if (category.getId() == null && jcboProvider.getSelectedIndex() != 0)
                {
                    products = iProduct.findByProvider((Provider) jcboProvider.getSelectedItem());
                }
                else if (category.getId() != null)
                {
                    products = jcboProvider.getSelectedIndex() == 0
                        ? iProduct.findByCategory(category)
                        : iProduct.findBy(category, (Provider) jcboProvider.getSelectedItem());
                }
                else
                {
                    products = iProduct.findAll();
                }
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
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                IProduct iProduct = Application.getContext().getBean(IProduct.class);
                //Objeto afectado por el evento
                Provider provider = (Provider) e.getItem();
                List<Product> products;
                if (provider.getId() == null && jcboCategory.getSelectedIndex() != 0)
                {
                    products = iProduct.findByCategory((Category) jcboCategory.getSelectedItem());
                }
                else if (provider.getId() != null)
                {
                    products = jcboCategory.getSelectedIndex() == 0
                        ? iProduct.findByProvider(provider)
                        : iProduct.findBy((Category) jcboCategory.getSelectedItem(), provider);
                }
                else
                {
                    products = iProduct.findAll();
                }
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
        jcboProduct.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                //Objeto afectado por el evento
                Product product = (Product) e.getItem();
                if (product.getId() == null)
                {
                    jcboUnit.setModel(new UnitComboBoxModel(Arrays.asList(new Unit("", "Elige una unidad", ""))));
                    jcboUnit.setRenderer(new UnitComboBoxRenderer());
                    return;
                }
                
                IProductUnit iProductUnit = Application.getContext().getBean(IProductUnit.class);
                
                List<Unit> units = iProductUnit.findByProduct(product).stream()
                    .map(ProductUnit::getUnit).collect(Collectors.toList());
                units.add(0, new Unit("", "Elige una unidad", ""));
                jcboUnit.setModel(new UnitComboBoxModel(units));
                jcboUnit.setRenderer(new UnitComboBoxRenderer());
            }
        });
        
        jcboUnit.setModel(new UnitComboBoxModel(Arrays.asList(new Unit("", "Elige una unidad", ""))));
        jcboUnit.setRenderer(new UnitComboBoxRenderer());
        jcboUnit.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                IProductUnit iProductUnit = Application.getContext().getBean(IProductUnit.class);
                //Objeto afectado por el evento
                Unit unit = (Unit) e.getItem();
                if (unit.getId() == null)
                {
                    jlblQuantityInPieces.setText("(0 piezas)");
                    return;
                }
                if (jcboProduct.getSelectedIndex() != 0)
                {
                    jlblQuantityInPieces.setText("(" + iProductUnit.getQuantityBy(
                        ((Product) jcboProduct.getSelectedItem()), unit).toString() + " piezas)");
                }
            }
        });
        
        jtableInventoryProducts.setModel(new InventoryProductTableModel());
        jtableInventoryProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableInventoryProducts.setShowHorizontalLines(Boolean.TRUE);
        jtableInventoryProducts.setGridColor(new Color(179, 179, 179));
        
        //Se eliminan columnas innecesarias para esta vista
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_PROD_ID)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_UNIT_ID)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_TYPE)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_INV_DATE)));
        jtableInventoryProducts.getColumnModel().removeColumn(jtableInventoryProducts.getColumnModel().getColumn(
            jtableInventoryProducts.convertColumnIndexToView(InventoryProductTableModel.COLUMN_REAL_QTY)));
                
        jtableInventoryProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
    }
    
    private Boolean isJframeValid()
    {
        
        if (jcboProduct.getSelectedIndex() == 0
            || jcboUnit.getSelectedIndex() == 0
            || jtxtQuantity.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this,
                "Ingrese los datos marcados con un asterisco para agregar el registro.", " Agregar registro",
                JOptionPane.INFORMATION_MESSAGE);
            return Boolean.FALSE;
        }
        if (!jtxtQuantity.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            String jlblMsg = "<html>Solo se permiten números en el campo <b>Cantidad</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg, " Agregar registro", JOptionPane.WARNING_MESSAGE);
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
        jsepHeader = new javax.swing.JSeparator();
        jpanelBody = new javax.swing.JPanel();
        jlblCategory = new javax.swing.JLabel();
        jcboCategory = new javax.swing.JComboBox<>();
        jlblProvider = new javax.swing.JLabel();
        jcboProvider = new javax.swing.JComboBox<>();
        jlblProduct = new javax.swing.JLabel();
        jcboProduct = new javax.swing.JComboBox<>();
        jlblUnit = new javax.swing.JLabel();
        jcboUnit = new javax.swing.JComboBox<>();
        jlblIncomeUnits = new javax.swing.JLabel();
        jtxtQuantity = new javax.swing.JTextField();
        jbtnAddProduct = new javax.swing.JButton();
        jbtnRemoveProduct = new javax.swing.JButton();
        jlblQuantityInPieces = new javax.swing.JLabel();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableInventoryProducts = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Nueva entrada a inventario");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Agregar nueva entrada a inventario");

        jbtnSave.setBackground(new java.awt.Color(17, 157, 17));
        jbtnSave.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnSave.setForeground(new java.awt.Color(255, 255, 255));
        jbtnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/floppy-disk-w.png"))); // NOI18N
        jbtnSave.setText("Guardar");
        jbtnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnSave.setIconTextGap(8);
        jbtnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnSaveMouseClicked(evt);
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
                .addComponent(jbtnSave)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jpanelBody.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Información de inventario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jlblCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCategory.setText("Categoria");

        jcboCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProvider.setText("Proveedor");

        jcboProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProduct.setText("* Producto");

        jcboProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblUnit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblUnit.setText("* Unidad");

        jcboUnit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblIncomeUnits.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblIncomeUnits.setText("* Cantidad:");

        jtxtQuantity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jbtnAddProduct.setBackground(new java.awt.Color(204, 204, 204));
        jbtnAddProduct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnAddProduct.setForeground(new java.awt.Color(0, 0, 0));
        jbtnAddProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/plus-sign-b.png"))); // NOI18N
        jbtnAddProduct.setText("Agregar");
        jbtnAddProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnAddProduct.setIconTextGap(8);
        jbtnAddProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnAddProductMouseClicked(evt);
            }
        });

        jbtnRemoveProduct.setBackground(new java.awt.Color(204, 204, 204));
        jbtnRemoveProduct.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnRemoveProduct.setForeground(new java.awt.Color(0, 0, 0));
        jbtnRemoveProduct.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remove-sign-b.png"))); // NOI18N
        jbtnRemoveProduct.setText("Eliminar");
        jbtnRemoveProduct.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnRemoveProduct.setIconTextGap(8);
        jbtnRemoveProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbtnRemoveProductMouseClicked(evt);
            }
        });

        jlblQuantityInPieces.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jlblQuantityInPieces.setText("(0 piezas)");

        javax.swing.GroupLayout jpanelBodyLayout = new javax.swing.GroupLayout(jpanelBody);
        jpanelBody.setLayout(jpanelBodyLayout);
        jpanelBodyLayout.setHorizontalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBodyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblUnit)
                    .addComponent(jlblIncomeUnits)
                    .addComponent(jlblProduct)
                    .addComponent(jlblProvider)
                    .addComponent(jlblCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpanelBodyLayout.createSequentialGroup()
                        .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                        .addComponent(jbtnAddProduct)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbtnRemoveProduct))
                    .addGroup(jpanelBodyLayout.createSequentialGroup()
                        .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpanelBodyLayout.createSequentialGroup()
                                .addComponent(jcboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblQuantityInPieces))
                            .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpanelBodyLayout.setVerticalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBodyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProvider))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblUnit)
                    .addComponent(jlblQuantityInPieces))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblIncomeUnits)
                    .addComponent(jbtnAddProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jbtnRemoveProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jtableInventoryProducts.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableInventoryProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableInventoryProducts.setRowHeight(25);
        jtableInventoryProducts.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableInventoryProducts);

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
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("jtableInvProducts");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        InventoryProductTableModel inventoryProductTableModel =
            (InventoryProductTableModel) jtableInventoryProducts.getModel();
        
        if (jtableInventoryProducts.getRowCount() == 0)
        {
            OptionPane.showMessageDialog(this, "Agregue algunos productos para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere efectuar la entrada de estos productos?",
            " Guardar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IInventory iInventory = Application.getContext().getBean(IInventory.class);
        
        Inventory inventory = new Inventory();
        inventory.setCode(iInventory.getNextCode());
        inventory.setType(InventoryType.INCOME);
        inventory.setCreatedBy(ApplicationSession.getUser());
        
        inventoryProductTableModel.getAll().forEach(entity ->
        {
            entity.setInventory(inventory);
            inventory.getInventoryProducts().add(entity);
        });
        iInventory.save(inventory);

        if (inventory.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha guardado exitosamente.",
                " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            
            jcboCategory.setSelectedIndex(0);
            jcboProvider.setSelectedIndex(0);
            jcboProduct.setSelectedIndex(0);
            jcboUnit.setSelectedIndex(0);
            jtxtQuantity.setText("");

            inventoryProductTableModel.removeAll();
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar guardar el registro.",
                " Guardar registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jbtnAddProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnAddProductMouseClicked
        
        if (!isJframeValid()) return;
        
        InventoryProductTableModel inventoryProductTableModel =
            (InventoryProductTableModel) jtableInventoryProducts.getModel();
        
        InventoryProduct inventoryProduct = new InventoryProduct();
        inventoryProduct.setProduct((Product) jcboProduct.getSelectedItem());
        inventoryProduct.setUnit((Unit) jcboUnit.getSelectedItem());
        inventoryProduct.setUnits(new Integer(jtxtQuantity.getText()));
        
        inventoryProductTableModel.add(inventoryProduct);
        
    }//GEN-LAST:event_jbtnAddProductMouseClicked

    private void jbtnRemoveProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnRemoveProductMouseClicked
        
        if (jtableInventoryProducts.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
        }
        
        InventoryProductTableModel inventoryProductTableModel =
            (InventoryProductTableModel) jtableInventoryProducts.getModel();
        inventoryProductTableModel.remove(jtableInventoryProducts.getSelectedRow());
        
    }//GEN-LAST:event_jbtnRemoveProductMouseClicked

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnAddProduct;
    private javax.swing.JButton jbtnRemoveProduct;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<Category> jcboCategory;
    private javax.swing.JComboBox<Product> jcboProduct;
    private javax.swing.JComboBox<Provider> jcboProvider;
    private javax.swing.JComboBox<Unit> jcboUnit;
    private javax.swing.JLabel jlblCategory;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblIncomeUnits;
    private javax.swing.JLabel jlblProduct;
    private javax.swing.JLabel jlblProvider;
    private javax.swing.JLabel jlblQuantityInPieces;
    private javax.swing.JLabel jlblUnit;
    private javax.swing.JPanel jpanelBody;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableInventoryProducts;
    private javax.swing.JTextField jtxtQuantity;
    // End of variables declaration//GEN-END:variables
}

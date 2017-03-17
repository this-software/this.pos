/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms;

import com.github.lgooddatepicker.components.TimePickerSettings;
import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.OptionPane;
import dis.software.pos.Property;
import dis.software.pos.combobox.model.CategoryComboBoxModel;
import dis.software.pos.combobox.model.ProductComboBoxModel;
import dis.software.pos.combobox.model.ProviderComboBoxModel;
import dis.software.pos.combobox.model.UnitComboBoxModel;
import dis.software.pos.combobox.renderers.CategoryComboBoxRenderer;
import dis.software.pos.combobox.renderers.ProductComboBoxRenderer;
import dis.software.pos.combobox.renderers.ProviderComboBoxRenderer;
import dis.software.pos.combobox.renderers.UnitComboBoxRenderer;
import dis.software.pos.entities.Category;
import dis.software.pos.entities.Module;
import dis.software.pos.entities.Product;
import dis.software.pos.entities.ProductUnit;
import dis.software.pos.entities.Provider;
import dis.software.pos.entities.Unit;
import dis.software.pos.interfaces.ICategory;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.interfaces.IProductUnit;
import dis.software.pos.interfaces.IProvider;
import dis.software.pos.interfaces.IUnit;
import dis.software.pos.table.cell.renderer.MoneyCellRenderer;
import dis.software.pos.table.model.ProductUnitTableModel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la modificación de productos
 * @author Milton Cavazos
 */
public class ProductEditJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(ProductEditJFrame.class.getSimpleName());
    
    private Product product1 = null;
    private List<ProductUnit> productUnits = new ArrayList<>();
    
    /**
     * Creación de nuevo formulario ProductEditJFrame
     * @param product
     */
    public ProductEditJFrame(Product product)
    {
        
        this.product1 = product;
        init();
        
    }

    /**
     * Creación de nuevo formulario ProductEditJFrame
     */
    public ProductEditJFrame()
    {
        
        init();
        
    }
    
    private void init()
    {
        
        initComponents();
        
        ProductEditJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (product1 == null)
                {
                    logger.info("Window closed");
                    frame.dispose();
                    return;
                }
                //Si la categoria de producto
                if (!((Category) jcboCategory.getSelectedItem()).getId()
                        .equals(product1.getCategory().getId())
                    //El proveedor de producto
                    || !((Provider) jcboProvider.getSelectedItem()).getId()
                        .equals(product1.getProvider().getId())
                    //El nombre de producto
                    || !jtxtName.getText().equals(product1.getName())
                    //La descripción de producto
                    || !jtxtDescription.getText().equals(product1.getDescription())
                    //La unidad predeterminada del producto
                    || productUnits.stream()
                        .filter((pu) -> pu.getIsDefault())
                        .anyMatch(pu ->
                        {
                            return !((Unit) jcboUnit.getSelectedItem()).equals(pu.getUnit());
                        })
                    //Las unidades en inventario de producto
                    || !jtxtUnitsInStock.getText().equals(product1.getUnitsInStock().toString())
                    //El inventario minimo producto
                    || !jtxtMinStockLevel.getText().equals(product1.getMinStockLevel().toString())
                    //El descuento de producto
                    || !jtxtDiscount.getText().equals(product1.getDiscount().toString())
                    //O la fecha de expiración de descuento de producto han cambiado, entonces:
                    || ((Date.from(jxDiscountExpirationDateTime.getDatePicker().getDate().atTime(
                        jxDiscountExpirationDateTime.getTimePicker().getTime()).atZone(ZoneId.systemDefault())
                            .toInstant())).compareTo(product1.getDiscountExpirationDate().getTime()) != 0)
                    )
                {
                    if (OptionPane.showConfirmDialog(frame,
                        "<html>Los cambios efectuados aún no han sido guardados.<br>"
                        + "¿Está seguro de que quiere continuar?</html>", " Cerrar ventana",
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
                    //jtxtCode.setText("");
                    jcboCategory.setSelectedIndex(0);
                    jcboProvider.setSelectedIndex(0);
                    jtxtName.setText("");
                    jtxtDescription.setText("");
                    jcboUnit.setSelectedIndex(0);
                    jlblInfo.setText("");
                    jtxtUnitsInStock.setText("");
                    jtxtMinStockLevel.setText("");
                    jtxtDiscount.setText("");
                    jxDiscountExpirationDateTime.getDatePicker().setDate(null);
                    jxDiscountExpirationDateTime.getTimePicker().setTime(null);
                    productUnits = new ArrayList<>();
                    return;
                }
                
                IProductUnit iProductUnit = Application.getContext().getBean(IProductUnit.class);
                productUnits = iProductUnit.findByProduct(product);
                
                //jtxtCode.setText(product.getCode());
                jcboCategory.setSelectedItem(product.getCategory());
                jcboProvider.setSelectedItem(product.getProvider());
                jtxtName.setText(product.getName());
                jtxtDescription.setText(product.getDescription());
                ProductUnit productUnit = productUnits.stream()
                    .filter(x -> x.getIsDefault() != null && x.getIsDefault()).findAny().orElse(null);
                if (productUnit != null)
                {
                    jcboUnit.setSelectedItem(productUnit.getUnit());
                    jlblInfo.setText("(La unidad seleccionada aún no ha sido configurada)");
                    if (productUnit.getQuantityByUnit() != null
                        && productUnit.getQuantityByUnit() > 0)
                    {
                        jlblInfo.setText(String.format("(Piezas: %s, Costo: $%,.2f, Precio: $%,.2f)",
                            productUnit.getQuantityByUnit(), productUnit.getCostByUnit(), productUnit.getPriceByUnit()));
                    }
                }
                jtxtUnitsInStock.setText(product.getUnitsInStock().toString());
                jtxtMinStockLevel.setText(product.getMinStockLevel().toString());
                jtxtDiscount.setText(product.getDiscount().toString());
                jxDiscountExpirationDateTime.getDatePicker().setDate(product.getDiscountExpirationDate() != null
                    ? Instant.ofEpochMilli(product.getDiscountExpirationDate().getTimeInMillis())
                        .atZone(ZoneId.systemDefault()).toLocalDate() : null);
                jxDiscountExpirationDateTime.getTimePicker().setTime(product.getDiscountExpirationDate() != null
                    ? Instant.ofEpochMilli(product.getDiscountExpirationDate().getTimeInMillis())
                        .atZone(ZoneId.systemDefault()).toLocalTime() : null);
            }
        });
        
        ICategory iCategory = Application.getContext().getBean(ICategory.class);
        List<Category> categories = iCategory.findAll();
        categories.add(0, new Category("", "Elige una categoría", ""));
        jcboCategory.setModel(new CategoryComboBoxModel(categories));
        jcboCategory.setRenderer(new CategoryComboBoxRenderer());
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        List<Provider> providers = iProvider.findAll();
        providers.add(0, new Provider("", "Elige un proveedor", ""));
        jcboProvider.setModel(new ProviderComboBoxModel(providers));
        jcboProvider.setRenderer(new ProviderComboBoxRenderer());
        
        IUnit iUnit = Application.getContext().getBean(IUnit.class);
        List<Unit> units = iUnit.findAll();
        jcboUnit.setModel(new UnitComboBoxModel(units));
        jcboUnit.setRenderer(new UnitComboBoxRenderer());
        jcboUnit.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                //Objeto afectado por el evento
                Unit unit = (Unit) e.getItem();
                if (unit.getId() == null)
                {
                    return;
                }
                ProductUnit productUnit = productUnits.stream()
                    .filter(x -> unit.getId().equals(x.getUnit().getId())).findAny().orElse(null);
                if (productUnit == null
                    || productUnit.getQuantityByUnit() <= 0)
                {
                    jlblInfo.setText("(La unidad seleccionada aún no ha sido configurada)");
                    return;
                }
                jlblInfo.setText(String.format("(Piezas: %s, Costo: $%,.2f, Precio: $%,.2f)",
                    productUnit.getQuantityByUnit(), productUnit.getCostByUnit(), productUnit.getPriceByUnit()));
            }
        });
        
        jlblInfo.setText("(La unidad seleccionada aún no ha sido configurada)");
        
        jxDiscountExpirationDateTime.getDatePicker().getComponentToggleCalendarButton().setText("");
        jxDiscountExpirationDateTime.getDatePicker().getComponentToggleCalendarButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/calendar-b.png")));
        jxDiscountExpirationDateTime.getDatePicker().getSettings().setAllowKeyboardEditing(false);
        
        jxDiscountExpirationDateTime.getTimePicker().getComponentToggleTimeMenuButton().setText("");
        jxDiscountExpirationDateTime.getTimePicker().getComponentToggleTimeMenuButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/clock-b.png")));
        jxDiscountExpirationDateTime.getTimePicker().getSettings().use24HourClockFormat();
        jxDiscountExpirationDateTime.getTimePicker().getSettings().generatePotentialMenuTimes(
            TimePickerSettings.TimeIncrement.OneHour, LocalTime.NOON, LocalTime.MAX);
        
        SwingUtilities.invokeLater(() ->
        {
            if (product1 != null)
            {
                jcboProduct.setSelectedItem(product1);
            }
        });
        
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
        jcboProduct = new javax.swing.JComboBox<>();
        jlblProduct = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblName = new javax.swing.JLabel();
        jtxtDescription = new javax.swing.JTextField();
        jlblDescription = new javax.swing.JLabel();
        jtxtUnitsInStock = new javax.swing.JTextField();
        jlblUnitsInStock = new javax.swing.JLabel();
        jcboCategory = new javax.swing.JComboBox<>();
        jlblCategory = new javax.swing.JLabel();
        jcboProvider = new javax.swing.JComboBox<>();
        jlblProvider = new javax.swing.JLabel();
        jlblMinStockLevel = new javax.swing.JLabel();
        jtxtMinStockLevel = new javax.swing.JTextField();
        jtxtDiscount = new javax.swing.JTextField();
        jlblDiscount = new javax.swing.JLabel();
        jlblUnit = new javax.swing.JLabel();
        jcboUnit = new javax.swing.JComboBox<>();
        jbtnConfigureUnits = new javax.swing.JButton();
        jlblInfo = new javax.swing.JLabel();
        jlblDiscountExpirationDateTime = new javax.swing.JLabel();
        jxDiscountExpirationDateTime = new com.github.lgooddatepicker.components.DateTimePicker();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modificar productos");

        jlblHeader.setText("Modificar producto");
        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));

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

        jcboProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProduct.setText("Producto");
        jlblProduct.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setText("* Nombre de producto:");
        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDescription.setText("Descripción:");
        jlblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtUnitsInStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblUnitsInStock.setText("* Unidades en inventario:");
        jlblUnitsInStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jcboCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblCategory.setText("Categoría");
        jlblCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jcboProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProvider.setText("Proveedor");
        jlblProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblMinStockLevel.setText("Inventario mínimo:");
        jlblMinStockLevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtMinStockLevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtDiscount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDiscount.setText("<html>Descuento (<b>$</b>):</html>");
        jlblDiscount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblUnit.setText("* Unidad predeterminada");
        jlblUnit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jcboUnit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jbtnConfigureUnits.setBackground(new java.awt.Color(204, 204, 204));
        jbtnConfigureUnits.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnConfigureUnits.setForeground(new java.awt.Color(0, 0, 0));
        jbtnConfigureUnits.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cogwheel-b.png"))); // NOI18N
        jbtnConfigureUnits.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnConfigureUnits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnConfigureUnitsActionPerformed(evt);
            }
        });

        jlblInfo.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N

        jlblDiscountExpirationDateTime.setText("<html>Fecha y hora de<br> expiración:</html>");
        jlblDiscountExpirationDateTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jsepHeader))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(25, 25, 25)
                                            .addComponent(jlblUnitsInStock))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(jlblMinStockLevel)))
                                    .addComponent(jlblDiscount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtUnitsInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jtxtMinStockLevel, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jtxtDiscount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                                    .addComponent(jxDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblUnit)
                                    .addComponent(jlblProduct))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblInfo)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jcboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbtnConfigureUnits))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(117, 117, 117)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jlblCategory)
                                            .addComponent(jlblProvider)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jlblName, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jlblDescription, javax.swing.GroupLayout.Alignment.TRAILING))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtxtDescription)
                                    .addComponent(jtxtName)
                                    .addComponent(jcboProvider, 0, 200, Short.MAX_VALUE)
                                    .addComponent(jcboCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 207, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(83, 83, 83)
                .addComponent(jlblDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProduct))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCategory))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProvider))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblName)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDescription)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblUnit)
                        .addComponent(jcboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jbtnConfigureUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtUnitsInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblUnitsInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtMinStockLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblMinStockLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jxDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        //"Unidad" predeterminada seleccionada
        ProductUnit productUnit = productUnits.stream()
            .filter(x -> ((Unit) jcboUnit.getSelectedItem()).getId().equals(x.getUnit().getId()))
            .findAny().orElse(null);
        
        String jlblMsg = new String();
        
        if (jtxtName.getText().isEmpty()
            || jtxtUnitsInStock.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco "
                + "para continuar.", " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jcboCategory.getSelectedIndex() == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione una categoría para continuar.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jcboProvider.getSelectedIndex() == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione un proveedor para continuar.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jcboUnit.getSelectedIndex() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione una unidad predeterminada para continuar.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!productUnit.getPriceByUnit().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Precio</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!productUnit.getCostByUnit().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Costo</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!productUnit.getQuantityByUnit().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Cantidad</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!jtxtUnitsInStock.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Unidades en inventario</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!jtxtMinStockLevel.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Inventario mínimo</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!jtxtDiscount.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Descuento</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if ((jtxtDiscount.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")
            && Double.valueOf(jtxtDiscount.getText()) > 0.00)
            && (jxDiscountExpirationDateTime.getDatePicker().getDate() == null
                || jxDiscountExpirationDateTime.getTimePicker().getTime() == null))
        {
            OptionPane.showMessageDialog(this, "La fecha y hora de expiración seleccionadas son incorrectas.",
                " Modificar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        
        Product product = (Product) jcboProduct.getSelectedItem();
        //product.setCode(jtxtCode.getText());
        product.setName(jtxtName.getText());
        product.setDescription(jtxtDescription.getText());
        product.setCost(productUnit.getCostByUnit()); //Costo predeterminado
        product.setPrice(productUnit.getPriceByUnit()); //Precio predeterminado
        product.setOutOfTimePrice(productUnit.getOutOfTimePriceByUnit()); //Precio "Fuera de horario" predeterminado
        product.setDiscount(new Double(jtxtDiscount.getText()));
        product.setQuantityByUnit(productUnit.getQuantityByUnit()); //Cantidad por unidad predeterminada
        product.setUnitsInStock(new Integer(jtxtUnitsInStock.getText()));
        product.setMinStockLevel(new Integer(jtxtMinStockLevel.getText()));
        if (jxDiscountExpirationDateTime.getDatePicker().getDate() != null
            && jxDiscountExpirationDateTime.getTimePicker().getTime() != null)
        {
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.setTime(Date.from(jxDiscountExpirationDateTime.getDatePicker().getDate().atTime(
                jxDiscountExpirationDateTime.getTimePicker().getTime()).atZone(ZoneId.systemDefault()).toInstant()));
            product.setDiscountExpirationDate(expirationDate);
        }
        product.setUpdatedBy(ApplicationSession.getUser());
        product.setUpdatedDate(new GregorianCalendar());
        product.setCategory((Category) jcboCategory.getSelectedItem());
        product.setProvider((Provider) jcboProvider.getSelectedItem());

        iProduct.update(product);

        if (product.getId() != null)
        {
            IProductUnit iProductUnit = Application.getContext().getBean(IProductUnit.class);
            productUnits.stream()
                .filter((pu) -> (pu.getQuantityByUnit() > 0))
                .map((pu) ->
                {
                    pu.setProduct(product);
                    pu.setIsDefault(pu.getUnit().equals((Unit) jcboUnit.getSelectedItem()));
                    return pu;
                })
                .forEach((pu) ->
                {
                    iProductUnit.saveOrUpdate(pu);
                });
            OptionPane.showMessageDialog(this, "El registro se ha actualizado exitosamente.",
                " Modificar registro", OptionPane.SUCCESS_MESSAGE);

            List<Product> list = iProduct.findAll();
            list.add(0, new Product("", "Elige un producto", ""));
            jcboProduct.setModel(new ProductComboBoxModel(list));

            //jtxtCode.setText("");
            jcboCategory.setSelectedIndex(0);
            jcboProvider.setSelectedIndex(0);
            jtxtName.setText("");
            jtxtDescription.setText("");
            jcboUnit.setSelectedIndex(0);
            jlblInfo.setText("");
            jtxtUnitsInStock.setText("");
            jtxtMinStockLevel.setText("");
            jtxtDiscount.setText("");
            jxDiscountExpirationDateTime.getDatePicker().setDate(null);
            jxDiscountExpirationDateTime.getTimePicker().setTime(null);
            productUnits = new ArrayList<>();
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar actualizar el producto.",
                " Modificar registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jbtnConfigureUnitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnConfigureUnitsActionPerformed

        if (ApplicationSession.getUser().getProfile().getProfileModules().stream()
            .anyMatch(x -> x.getModule().getCode().equals(Module.COSTS)
                && x.getPrivileges().getViewProperty().equals(Property.DENY)))
        {
            OptionPane.showMessageDialog(this, "No tiene privilegios para realizar esta acción.\n"
                + "Contacte al administrador del sistema.", " Mensaje del sistema", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ((UnitComboBoxModel) jcboUnit.getModel()).getItems().forEach(x ->
        {
            Unit unit = productUnits.stream()
                .filter(y -> x.getId().equals(y.getUnit().getId()))
                .map(ProductUnit::getUnit).findAny().orElse(null);
            //Si alguna unidad no existe en la configuración se agregará
            if (unit == null)
            {
                ProductUnit productUnit = new ProductUnit();
                productUnit.setUnit(x);
                productUnits.add(productUnit);
            }
        });
            
        JTable jtableProductUnits = new JTable();
        
        jtableProductUnits.setModel(new ProductUnitTableModel(productUnits));
        jtableProductUnits.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jtableProductUnits.setRowHeight(25);
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableProductUnits.setShowHorizontalLines(Boolean.TRUE);
        jtableProductUnits.setGridColor(new Color(179, 179, 179));
        
        jtableProductUnits.setDefaultRenderer(Double.class, new MoneyCellRenderer());

        //Se eliminan columnas innecesarias para esta vista
        jtableProductUnits.getColumnModel().removeColumn(jtableProductUnits.getColumnModel().getColumn(
            jtableProductUnits.convertColumnIndexToView(ProductUnitTableModel.COLUMN_PROD_ID)));
        jtableProductUnits.getColumnModel().removeColumn(jtableProductUnits.getColumnModel().getColumn(
            jtableProductUnits.convertColumnIndexToView(ProductUnitTableModel.COLUMN_UNIT_ID)));
        jtableProductUnits.getColumnModel().removeColumn(jtableProductUnits.getColumnModel().getColumn(
            jtableProductUnits.convertColumnIndexToView(ProductUnitTableModel.COLUMN_IS_DEFAULT)));

        jtableProductUnits.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        ProductUnitTableModel productUnitTableModel = (ProductUnitTableModel) jtableProductUnits.getModel();
        
        String msgTitle = " Configuración de unidades";
        if (ApplicationSession.getUser().getProfile().getProfileModules().stream()
            .anyMatch(x -> x.getModule().getCode().equals(Module.COSTS)
                && x.getPrivileges().getCreateProperty().equals(Property.DENY)))
        {
            jtableProductUnits.setBackground(new Color(255, 255, 153));
            productUnitTableModel.setColumnEditable(ProductUnitTableModel.COLUMN_QTY_BY_UNIT, Boolean.FALSE);
            productUnitTableModel.setColumnEditable(ProductUnitTableModel.COLUMN_COST_BY_UNIT, Boolean.FALSE);
            productUnitTableModel.setColumnEditable(ProductUnitTableModel.COLUMN_PRICE_BY_UNIT, Boolean.FALSE);
            productUnitTableModel.setColumnEditable(ProductUnitTableModel.COLUMN_SPECIAL_PRICE_BY_UNIT, Boolean.FALSE);
            msgTitle += " - [Modo de solo lectura]";
        }

        JScrollPane jscrollPane = new JScrollPane(jtableProductUnits);
        jscrollPane.setPreferredSize(new Dimension(540, 180));

        if (JOptionPane.showConfirmDialog(this, jscrollPane, msgTitle,
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null) == JOptionPane.OK_OPTION)
        {
            productUnits = productUnitTableModel.getAll();
            ProductUnit productUnit = productUnits.stream()
                .filter(x -> ((Unit) jcboUnit.getSelectedItem()).getId().equals(x.getUnit().getId()))
                .findAny().orElse(null);
            if (productUnit.getQuantityByUnit() == null
                || productUnit.getQuantityByUnit() <= 0)
            {
                jlblInfo.setText("(La unidad seleccionada aún no ha sido configurada)");
                return;
            }
            jlblInfo.setText(String.format("(Piezas: %s, Costo: $%,.2f, Precio: $%,.2f)",
                productUnit.getQuantityByUnit(), productUnit.getCostByUnit(), productUnit.getPriceByUnit()));
        }

    }//GEN-LAST:event_jbtnConfigureUnitsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnConfigureUnits;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<Category> jcboCategory;
    private javax.swing.JComboBox<Product> jcboProduct;
    private javax.swing.JComboBox<Provider> jcboProvider;
    private javax.swing.JComboBox<Unit> jcboUnit;
    private javax.swing.JLabel jlblCategory;
    private javax.swing.JLabel jlblDescription;
    private javax.swing.JLabel jlblDiscount;
    private javax.swing.JLabel jlblDiscountExpirationDateTime;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblInfo;
    private javax.swing.JLabel jlblMinStockLevel;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblProduct;
    private javax.swing.JLabel jlblProvider;
    private javax.swing.JLabel jlblUnit;
    private javax.swing.JLabel jlblUnitsInStock;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTextField jtxtDescription;
    private javax.swing.JTextField jtxtDiscount;
    private javax.swing.JTextField jtxtMinStockLevel;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtUnitsInStock;
    private com.github.lgooddatepicker.components.DateTimePicker jxDiscountExpirationDateTime;
    // End of variables declaration//GEN-END:variables
}

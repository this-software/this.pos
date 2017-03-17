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
import dis.software.pos.combobox.model.ProviderComboBoxModel;
import dis.software.pos.combobox.model.UnitComboBoxModel;
import dis.software.pos.combobox.renderers.CategoryComboBoxRenderer;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la creación de productos
 * @author Milton Cavazos
 */
public class ProductCreateJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(ProductCreateJFrame.class.getSimpleName());
    
    private List<ProductUnit> productUnits = new ArrayList<>();

    /**
     * Creación de nuevo formulario ProductCreateJFrame
     */
    public ProductCreateJFrame()
    {
        
        initComponents();
        
        ProductCreateJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                //Si el código de producto
                if (!jtxtCode.getText().isEmpty()
                    //El nombre de producto
                    && !jtxtName.getText().isEmpty()
                    //La unidad seleccionada esta configurada
                    && productUnits.stream().anyMatch(x ->
                    {
                        return ((Unit) jcboUnit.getSelectedItem()).equals(x.getUnit())
                            && x.getQuantityByUnit() != null
                            && x.getQuantityByUnit() > 0;
                    })
                    //Y las unidades en inventario tienen datos
                    && !jtxtUnitsInStock.getText().isEmpty())
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
                    .filter(x -> unit.equals(x.getUnit())).findAny().orElse(null);
                if (productUnit != null)
                {
                    if (productUnit.getQuantityByUnit() == null
                        || productUnit.getQuantityByUnit() <= 0)
                    {
                        jlblInfo.setText("(La unidad seleccionada aún no ha sido configurada)");
                        return;
                    }
                    jlblInfo.setText(String.format("(Piezas: %s, Costo: $%,.2f, Precio: $%,.2f)",
                        productUnit.getQuantityByUnit(), productUnit.getCostByUnit(), productUnit.getPriceByUnit()));
                }
            }
        });
        
        jlblInfo.setText("(La unidad seleccionada aún no ha sido configurada)");
        jtxtUnitsInStock.setText("0");
        jtxtMinStockLevel.setText("0");
        jtxtDiscount.setText("0.00");
        
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
        jxDiscountExpirationDateTime.getTimePicker().getSettings().setAllowKeyboardEditing(false);
        
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
        jlblCode = new javax.swing.JLabel();
        jtxtCode = new javax.swing.JTextField();
        jchkAutoCode = new javax.swing.JCheckBox();
        jlblCategory = new javax.swing.JLabel();
        jcboCategory = new javax.swing.JComboBox<>();
        jlblProvider = new javax.swing.JLabel();
        jcboProvider = new javax.swing.JComboBox<>();
        jlblName = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblDescription = new javax.swing.JLabel();
        jtxtDescription = new javax.swing.JTextField();
        jlblUnit = new javax.swing.JLabel();
        jcboUnit = new javax.swing.JComboBox<>();
        jbtnConfigureUnits = new javax.swing.JButton();
        jlblInfo = new javax.swing.JLabel();
        jlblUnitsInStock = new javax.swing.JLabel();
        jtxtUnitsInStock = new javax.swing.JTextField();
        jlblMinStockLevel = new javax.swing.JLabel();
        jtxtMinStockLevel = new javax.swing.JTextField();
        jlblDiscount = new javax.swing.JLabel();
        jtxtDiscount = new javax.swing.JTextField();
        jlblDiscountExpirationDateTime = new javax.swing.JLabel();
        jxDiscountExpirationDateTime = new com.github.lgooddatepicker.components.DateTimePicker();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Nuevo producto");

        jlblHeader.setText("Agregar nuevo producto");
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

        jlblCode.setText("* Código:");
        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jchkAutoCode.setText("Automático");
        jchkAutoCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkAutoCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAutoCodeActionPerformed(evt);
            }
        });

        jlblCategory.setText("Categoría");
        jlblCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jcboCategory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProvider.setText("Proveedor");
        jlblProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jcboProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setText("* Nombre de producto:");
        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDescription.setText("Descripción:");
        jlblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblUnit.setText("* Unidad predeterminada");
        jlblUnit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jcboUnit.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jbtnConfigureUnits.setBackground(new java.awt.Color(204, 204, 204));
        jbtnConfigureUnits.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnConfigureUnits.setForeground(new java.awt.Color(0, 0, 0));
        jbtnConfigureUnits.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cogwheel-b.png"))); // NOI18N
        jbtnConfigureUnits.setToolTipText("Configurar unidades");
        jbtnConfigureUnits.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnConfigureUnits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnConfigureUnitsActionPerformed(evt);
            }
        });

        jlblInfo.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N

        jlblUnitsInStock.setText("* Unidades en inventario:");
        jlblUnitsInStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtUnitsInStock.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblMinStockLevel.setText("Inventario mínimo:");
        jlblMinStockLevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtMinStockLevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDiscount.setText("<html>Descuento (<b>$</b>):</html>");
        jlblDiscount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtDiscount.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblDescription)
                                    .addComponent(jlblUnit)
                                    .addComponent(jlblName))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jcboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jbtnConfigureUnits))
                                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblInfo)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(116, 116, 116)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblCategory)
                                    .addComponent(jlblCode)
                                    .addComponent(jlblProvider))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jchkAutoCode))
                                    .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblUnitsInStock)
                                    .addComponent(jlblMinStockLevel)
                                    .addComponent(jlblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlblDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtxtMinStockLevel, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                        .addComponent(jtxtUnitsInStock))
                                    .addComponent(jtxtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jxDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 207, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jsepHeader)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblCode)
                        .addComponent(jchkAutoCode)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCategory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProvider))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbtnConfigureUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcboUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblUnit)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtUnitsInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblUnitsInStock))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtMinStockLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblMinStockLevel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jxDiscountExpirationDateTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        //"Unidad" predeterminada seleccionada
        ProductUnit productUnit = productUnits.stream()
            .filter(x -> ((Unit) jcboUnit.getSelectedItem()).getId().equals(x.getUnit().getId()))
            .findAny().orElse(null);
        
        String jlblMsg = new String();
        
        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty()
            || jtxtUnitsInStock.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco "
                + "para continuar.", " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jcboCategory.getSelectedIndex() == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione una categoría para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jcboProvider.getSelectedIndex() == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione un proveedor para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jcboUnit.getSelectedIndex() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione una unidad predeterminada para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!productUnit.getPriceByUnit().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Precio</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!productUnit.getCostByUnit().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Costo</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!productUnit.getQuantityByUnit().toString().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Cantidad</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!jtxtUnitsInStock.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Unidades en inventario</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!jtxtMinStockLevel.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Inventario mínimo</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!jtxtDiscount.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            jlblMsg = "<html>Solo se permiten números en el campo <b>Descuento</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if ((jtxtDiscount.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$")
            && Double.valueOf(jtxtDiscount.getText()) > 0.00)
            && (jxDiscountExpirationDateTime.getDatePicker().getDate() == null
                || jxDiscountExpirationDateTime.getTimePicker().getTime() == null))
        {
            OptionPane.showMessageDialog(this, "La fecha y hora de expiración seleccionadas son incorrectas.",
                " Guardar registro", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        
        Product product = new Product(jtxtCode.getText(), jtxtName.getText(), jtxtDescription.getText());
        product.setCost(productUnit.getCostByUnit()); //Costo predeterminado
        product.setPrice(productUnit.getPriceByUnit()); //Precio predeterminado
        product.setOutOfTimePrice(productUnit.getOutOfTimePriceByUnit()); //Precio "Fuera de horario" predeterminado
        product.setDiscount(new Double(jtxtDiscount.getText()));
        product.setQuantityByUnit(productUnit.getQuantityByUnit()); //Cantidad por unidad predeterminada
        product.setUnitsInStock(new Integer(jtxtUnitsInStock.getText()));
        product.setMinStockLevel(new Integer(jtxtMinStockLevel.getText()));
        product.setCreatedBy(ApplicationSession.getUser());
        if (jxDiscountExpirationDateTime.getDatePicker().getDate() != null
            && jxDiscountExpirationDateTime.getTimePicker().getTime() != null)
        {
            Calendar expirationDate = Calendar.getInstance();
            expirationDate.setTime(Date.from(jxDiscountExpirationDateTime.getDatePicker().getDate().atTime(
                jxDiscountExpirationDateTime.getTimePicker().getTime()).atZone(ZoneId.systemDefault()).toInstant()));
            product.setDiscountExpirationDate(expirationDate);
        }
        product.setCategory((Category) jcboCategory.getSelectedItem());
        product.setProvider((Provider) jcboProvider.getSelectedItem());
        
        iProduct.save(product);

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
                    iProductUnit.save(pu);
                });
            OptionPane.showMessageDialog(this, "El registro se ha guardado exitosamente.",
                " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            
            jtxtCode.setText("");
            jtxtCode.setEditable(true);
            jtxtCode.setBackground(Color.WHITE);
            jchkAutoCode.setSelected(false);
            jtxtName.setText("");
            jtxtDescription.setText("");
            jtxtUnitsInStock.setText("");
            jtxtMinStockLevel.setText("");
            jtxtDiscount.setText("");
            jxDiscountExpirationDateTime.getDatePicker().setDate(null);
            jxDiscountExpirationDateTime.getTimePicker().setTime(null);
            jcboCategory.setSelectedIndex(0);
            jcboProvider.setSelectedIndex(0);
            jcboUnit.setSelectedIndex(0);
            jlblInfo.setText("");
            productUnits = new ArrayList<>();
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar guardar el registro.",
                " Guardar registro", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jchkAutoCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAutoCodeActionPerformed
        
        if (jchkAutoCode.isSelected())
        {
            IProduct iProduct = Application.getContext().getBean(IProduct.class);
            jtxtCode.setText(iProduct.getNextCode());
            jtxtCode.setEditable(false);
            jtxtCode.setBackground(new Color(196, 196, 196));
            return;
        }
        jtxtCode.setText("");
        jtxtCode.setEditable(true);
        jtxtCode.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_jchkAutoCodeActionPerformed

    private void jbtnConfigureUnitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnConfigureUnitsActionPerformed

        if (ApplicationSession.getUser().getProfile().getProfileModules().stream()
            .anyMatch(x -> x.getModule().getCode().equals(Module.COSTS)
                && x.getPrivileges().getViewProperty().equals(Property.DENY)))
        {
            OptionPane.showMessageDialog(this, "No tiene privilegios para realizar esta acción.\n"
                + "Contacte al administrador del sistema.", " Mensaje del sistema", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (productUnits == null || productUnits.isEmpty())
        {
            IUnit iUnit = Application.getContext().getBean(IUnit.class);
            iUnit.findAll().forEach(unit ->
            {
                ProductUnit productUnit = new ProductUnit();
                productUnit.setUnit(unit);
                productUnits.add(productUnit);
            });
        }
        
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
    private javax.swing.JComboBox<Provider> jcboProvider;
    private javax.swing.JComboBox<Unit> jcboUnit;
    private javax.swing.JCheckBox jchkAutoCode;
    private javax.swing.JLabel jlblCategory;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblDescription;
    private javax.swing.JLabel jlblDiscount;
    private javax.swing.JLabel jlblDiscountExpirationDateTime;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblInfo;
    private javax.swing.JLabel jlblMinStockLevel;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblProvider;
    private javax.swing.JLabel jlblUnit;
    private javax.swing.JLabel jlblUnitsInStock;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTextField jtxtCode;
    private javax.swing.JTextField jtxtDescription;
    private javax.swing.JTextField jtxtDiscount;
    private javax.swing.JTextField jtxtMinStockLevel;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtUnitsInStock;
    private com.github.lgooddatepicker.components.DateTimePicker jxDiscountExpirationDateTime;
    // End of variables declaration//GEN-END:variables
}

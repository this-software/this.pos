/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.ApplicationSync;
import dis.software.pos.OptionPane;
import dis.software.pos.Setting;
import dis.software.pos.entities.User;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la configuración del sistema
 * @author Milton Cavazos
 */
public class SettingsJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(SettingsJFrame.class.getSimpleName());

    /**
     * Creación de nuevo formulario SettingsJFrame
     */
    public SettingsJFrame()
    {
        
        initComponents();
        
        jxOutOfTime.getComponentToggleTimeMenuButton()
            .setIcon(new ImageIcon(getClass().getResource("/images/clock-b.png")));
        jxOutOfTime.getSettings().setAllowKeyboardEditing(false);
        
        Gson gson = new Gson();
        //Se cargan los datos de configuración del sistema
        try (Reader reader = new FileReader(
            System.getProperty("user.dir").concat(Setting.FILE_NAME)))
        {
            Setting setting = gson.fromJson(reader, Setting.class);
            
            jtxtStoreCode.setText(setting.getStoreCode());
            jtxtStoreName.setText(setting.getStoreName());
            jtxtStoreDescription.setText(setting.getStoreDescription());
            jtxtTax.setText(String.format("%,.2f", setting.getTax() * 100));
            jxOutOfTime.setTime(setting.getOutOfTime() != null
                ? Instant.ofEpochMilli(setting.getOutOfTime().getTime())
                    .atZone(ZoneId.systemDefault()).toLocalTime() : null);
            
            logger.info("Setting JSON file read.");
        }
        catch (IOException e)
        {
            logger.error("Error reading setting JSON file", e);
        }
        //Se cargan los datos de sincronización del sistema
        try (Reader reader = new FileReader(
            System.getProperty("user.dir").concat(ApplicationSync.FILE_NAME)))
        {
            List<ApplicationSync> list = gson.fromJson(reader,
                new TypeToken<List<ApplicationSync>>(){}.getType());
            list.forEach(entity ->
            {
                switch(entity.getCode())
                {
                    case ApplicationSync.INVENTORY:
                        jtxtInventoryLink.setText(entity.getLink());
                        break;
                    case ApplicationSync.PRODUCT:
                        jtxtProductLink.setText(entity.getLink());
                        break;
                    case ApplicationSync.PROMOTION:
                        jtxtPromotionLink.setText(entity.getLink());
                        break;
                    case ApplicationSync.PURCHASE:
                        jtxtPurchaseLink.setText(entity.getLink());
                        break;
                    case ApplicationSync.SALE:
                        jtxtSaleLink.setText(entity.getLink());
                        break;
                }
            });
            logger.info("Application sync JSON file read.");
        }
        catch (IOException e)
        {
            logger.error("Error reading application sync JSON file", e);
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
        jbtnSave = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jpanelBody = new javax.swing.JPanel();
        jlblInventoryLink = new javax.swing.JLabel();
        jtxtInventoryLink = new javax.swing.JTextField();
        jlblProductLink = new javax.swing.JLabel();
        jtxtProductLink = new javax.swing.JTextField();
        jlblPromotionLink = new javax.swing.JLabel();
        jtxtPromotionLink = new javax.swing.JTextField();
        jlblPurchaseLink = new javax.swing.JLabel();
        jtxtPurchaseLink = new javax.swing.JTextField();
        jlblSaleLink = new javax.swing.JLabel();
        jtxtSaleLink = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jlblStoreName = new javax.swing.JLabel();
        jlblStoreCode = new javax.swing.JLabel();
        jtxtStoreCode = new javax.swing.JTextField();
        jtxtStoreName = new javax.swing.JTextField();
        jlblStoreDescription = new javax.swing.JLabel();
        jtxtStoreDescription = new javax.swing.JTextField();
        jlblTax = new javax.swing.JLabel();
        jtxtTax = new javax.swing.JTextField();
        jlblOutOfTime = new javax.swing.JLabel();
        jxOutOfTime = new com.github.lgooddatepicker.components.TimePicker();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Configurar sistema");
        setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cogwheel-b.png"))); // NOI18N

        jlblHeader.setText("Configuración del sistema");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 326, Short.MAX_VALUE)
                .addComponent(jbtnSave)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jpanelBody.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Enlaces de sincronización", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jlblInventoryLink.setText("* Enlace para sincronizar inventario:");
        jlblInventoryLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtInventoryLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProductLink.setText("* Enlace para sincronizar productos:");
        jlblProductLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtProductLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblPromotionLink.setText("* Enlace para sincronizar promociones:");
        jlblPromotionLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtPromotionLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblPurchaseLink.setText("* Enlace para sincronizar compras:");
        jlblPurchaseLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtPurchaseLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblSaleLink.setText("* Enlace para sincronizar ventas:");
        jlblSaleLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtSaleLink.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jpanelBodyLayout = new javax.swing.GroupLayout(jpanelBody);
        jpanelBody.setLayout(jpanelBodyLayout);
        jpanelBodyLayout.setHorizontalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelBodyLayout.createSequentialGroup()
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelBodyLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(jlblInventoryLink)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtInventoryLink))
                    .addGroup(jpanelBodyLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jpanelBodyLayout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblSaleLink)
                                    .addComponent(jlblPurchaseLink))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtPurchaseLink)
                                    .addComponent(jtxtSaleLink)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpanelBodyLayout.createSequentialGroup()
                                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblPromotionLink)
                                    .addComponent(jlblProductLink))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtProductLink)
                                    .addComponent(jtxtPromotionLink))))))
                .addGap(25, 25, 25))
        );
        jpanelBodyLayout.setVerticalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelBodyLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtInventoryLink, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblInventoryLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtProductLink, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProductLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPromotionLink, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPromotionLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPurchaseLink, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPurchaseLink))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtSaleLink, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblSaleLink))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Configuración general", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        jlblStoreName.setText("* Nombre del negocio:");
        jlblStoreName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblStoreCode.setText("* Código del negocio:");
        jlblStoreCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtStoreCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtStoreName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblStoreDescription.setText("Descripción del negocio:");
        jlblStoreDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtStoreDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblTax.setText("<html>* I.V.A (<b>%</b>):</html>");
        jlblTax.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtxtTax.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblOutOfTime.setText("* Horario de precio especial:");
        jlblOutOfTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlblStoreCode)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtStoreCode, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlblStoreDescription)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtStoreDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlblStoreName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtStoreName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jlblTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblOutOfTime)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jxOutOfTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtStoreCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblStoreCode)
                        .addComponent(jlblOutOfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jxOutOfTime, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtStoreName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblStoreName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtStoreDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblStoreDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jsepHeader)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jpanelBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtxtStoreCode.getText().isEmpty()
            || jtxtStoreName.getText().isEmpty()
            || jtxtTax.getText().isEmpty()
            || jtxtInventoryLink.getText().isEmpty()
            || jtxtProductLink.getText().isEmpty()
            || jtxtPromotionLink.getText().isEmpty()
            || jtxtPurchaseLink.getText().isEmpty()
            || jtxtSaleLink.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (!jtxtTax.getText().matches("^[0-9]+(\\.[0-9]{1,2})?$"))
        {
            String jlblMsg = "<html>Solo se permiten números en el campo <b>I.V.A</b>.</html>";
            OptionPane.showMessageDialog(this, jlblMsg,
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (jxOutOfTime.getTime() == null)
        {
            OptionPane.showMessageDialog(this, "La hora seleccionada es incorrecta.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Setting setting = new Setting();
        
        setting.setStoreCode(jtxtStoreCode.getText());
        setting.setStoreName(jtxtStoreName.getText());
        setting.setStoreDescription(jtxtStoreDescription.getText());
        setting.setTax(new Double(jtxtTax.getText()) / 100);
        
        if (jxOutOfTime.getTime() != null)
        {
            //Hora a partir de la cual se aplicarán los precios especiales "Fuera de horario"
            Calendar outOfTime = Calendar.getInstance();
            outOfTime.set(Calendar.HOUR_OF_DAY, jxOutOfTime.getTime().getHour());
            outOfTime.set(Calendar.MINUTE, jxOutOfTime.getTime().getMinute());
            outOfTime.set(Calendar.SECOND, jxOutOfTime.getTime().getSecond());
            setting.setOutOfTime(outOfTime.getTime());
        }
        
        setting.setUpdatedBy(new User(
            ApplicationSession.getUser().getEmail(),
            ApplicationSession.getUser().getName(),
            ApplicationSession.getUser().getPassword()));
        setting.setUpdatedDate(new GregorianCalendar());
        
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setPrettyPrinting();

        Gson gson = builder.create();
        try (FileWriter writer = new FileWriter(
            System.getProperty("user.dir").concat(Setting.FILE_NAME)))
        {
            gson.toJson(setting, writer);
            //Se actualiza la configuración en sesión
            Application.setSetting(setting);
            logger.info("Setting JSON file created.");
        }
        catch (IOException e)
        {
            OptionPane.showMessageDialog(this, "La configuración general no se ha guardado exitosamente.",
                " Guardar registro", JOptionPane.ERROR_MESSAGE);
            logger.error("Error creating setting JSON file", e);
            return;
        }
        
        List<ApplicationSync> list = new ArrayList<>();
        
        ApplicationSync applicationSync = null;

        if (!jtxtInventoryLink.getText().isEmpty())
        {
            applicationSync = new ApplicationSync();

            applicationSync.setCode(ApplicationSync.INVENTORY);
            applicationSync.setLink(jtxtInventoryLink.getText());
            applicationSync.setUpdatedBy(new User(
                ApplicationSession.getUser().getEmail(),
                ApplicationSession.getUser().getName(),
                ApplicationSession.getUser().getPassword()));

            list.add(applicationSync);
        }

        if (!jtxtProductLink.getText().isEmpty())
        {
            applicationSync = new ApplicationSync();

            applicationSync.setCode(ApplicationSync.PRODUCT);
            applicationSync.setLink(jtxtProductLink.getText());
            applicationSync.setUpdatedBy(new User(
                ApplicationSession.getUser().getEmail(),
                ApplicationSession.getUser().getName(),
                ApplicationSession.getUser().getPassword()));

            list.add(applicationSync);
        }

        if (!jtxtPromotionLink.getText().isEmpty())
        {
            applicationSync = new ApplicationSync();

            applicationSync.setCode(ApplicationSync.PROMOTION);
            applicationSync.setLink(jtxtPromotionLink.getText());
            applicationSync.setUpdatedBy(new User(
                ApplicationSession.getUser().getEmail(),
                ApplicationSession.getUser().getName(),
                ApplicationSession.getUser().getPassword()));

            list.add(applicationSync);
        }

        if (!jtxtPurchaseLink.getText().isEmpty())
        {
            applicationSync = new ApplicationSync();

            applicationSync.setCode(ApplicationSync.PURCHASE);
            applicationSync.setLink(jtxtPurchaseLink.getText());
            applicationSync.setUpdatedBy(new User(
                ApplicationSession.getUser().getEmail(),
                ApplicationSession.getUser().getName(),
                ApplicationSession.getUser().getPassword()));

            list.add(applicationSync);
        }

        if (!jtxtSaleLink.getText().isEmpty())
        {
            applicationSync = new ApplicationSync();

            applicationSync.setCode(ApplicationSync.SALE);
            applicationSync.setLink(jtxtSaleLink.getText());
            applicationSync.setUpdatedBy(new User(
                ApplicationSession.getUser().getEmail(),
                ApplicationSession.getUser().getName(),
                ApplicationSession.getUser().getPassword()));

            list.add(applicationSync);
        }

        try (FileWriter writer = new FileWriter(
            System.getProperty("user.dir").concat(ApplicationSync.FILE_NAME)))
        {
            gson.toJson(list.toArray(new ApplicationSync[list.size()]), writer);
            OptionPane.showMessageDialog(this, "La configuración general y los enlaces de sincronización "
                + "se han guardado exitosamente.", " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            logger.info("Sync settings JSON file created.");
        }
        catch (IOException e)
        {
            OptionPane.showMessageDialog(this, "Los enlaces de sincronización no se han guardado exitosamente.",
                " Guardar registro", JOptionPane.ERROR_MESSAGE);
            logger.error("Error creating sync settings JSON file", e);
        }
        
    }//GEN-LAST:event_jbtnSaveMouseClicked
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblInventoryLink;
    private javax.swing.JLabel jlblOutOfTime;
    private javax.swing.JLabel jlblProductLink;
    private javax.swing.JLabel jlblPromotionLink;
    private javax.swing.JLabel jlblPurchaseLink;
    private javax.swing.JLabel jlblSaleLink;
    private javax.swing.JLabel jlblStoreCode;
    private javax.swing.JLabel jlblStoreDescription;
    private javax.swing.JLabel jlblStoreName;
    private javax.swing.JLabel jlblTax;
    private javax.swing.JPanel jpanelBody;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTextField jtxtInventoryLink;
    private javax.swing.JTextField jtxtProductLink;
    private javax.swing.JTextField jtxtPromotionLink;
    private javax.swing.JTextField jtxtPurchaseLink;
    private javax.swing.JTextField jtxtSaleLink;
    private javax.swing.JTextField jtxtStoreCode;
    private javax.swing.JTextField jtxtStoreDescription;
    private javax.swing.JTextField jtxtStoreName;
    private javax.swing.JTextField jtxtTax;
    private com.github.lgooddatepicker.components.TimePicker jxOutOfTime;
    // End of variables declaration//GEN-END:variables
}

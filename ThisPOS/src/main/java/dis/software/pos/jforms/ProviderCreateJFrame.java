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
import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.IProvider;
import java.awt.Color;
import javax.swing.JOptionPane;

/**
 * Formulario para la creación de proveedores
 * @author Milton Cavazos
 */
public class ProviderCreateJFrame extends javax.swing.JInternalFrame {

    /**
     * Creación de nuevo formulario ProviderCreateJForm
     */
    public ProviderCreateJFrame() {
        initComponents();
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
        jtxtCode = new javax.swing.JTextField();
        jlblCode = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblName = new javax.swing.JLabel();
        jtxtDescription = new javax.swing.JTextField();
        jlblDescription = new javax.swing.JLabel();
        jchkAutoCode = new javax.swing.JCheckBox();
        jcboCountry = new javax.swing.JComboBox<>();
        jlblCountry = new javax.swing.JLabel();
        jcboState = new javax.swing.JComboBox<>();
        jlblState = new javax.swing.JLabel();
        jcboCity = new javax.swing.JComboBox<>();
        jlblCity = new javax.swing.JLabel();
        jtxtAddress = new javax.swing.JTextField();
        jlblAddress = new javax.swing.JLabel();
        jtxtPostalCode = new javax.swing.JTextField();
        jlblPostalCode = new javax.swing.JLabel();
        jtxtPhone = new javax.swing.JTextField();
        jlblPhone = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Nuevo proveedor");
        setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Agregar nuevo proveedor");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 241, Short.MAX_VALUE)
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

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtCode.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCode.setText("* Código");

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblName.setText("* Nombre de proveedor:");

        jtxtDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblDescription.setText("Descripción:");

        jchkAutoCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkAutoCode.setText("Automático");
        jchkAutoCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAutoCodeActionPerformed(evt);
            }
        });

        jcboCountry.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcboCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige un pais", "Mexico" }));
        jcboCountry.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jlblCountry.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCountry.setText("Pais:");

        jcboState.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcboState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige un estado", "Nuevo León" }));
        jcboState.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jlblState.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblState.setText("Estado:");

        jcboCity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcboCity.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige una ciudad", "Guadalupe" }));
        jcboCity.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jlblCity.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCity.setText("Ciudad:");

        jtxtAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblAddress.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblAddress.setText("Dirección:");

        jtxtPostalCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblPostalCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblPostalCode.setText("Código postal:");

        jtxtPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblPhone.setText("Teléfono:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblName)
                    .addComponent(jlblCode)
                    .addComponent(jlblDescription)
                    .addComponent(jlblAddress)
                    .addComponent(jlblPostalCode)
                    .addComponent(jlblPhone)
                    .addComponent(jlblCountry)
                    .addComponent(jlblState)
                    .addComponent(jlblCity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcboCountry, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtDescription)
                    .addComponent(jtxtPostalCode)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jcboState, 0, 250, Short.MAX_VALUE)
                    .addComponent(jcboCity, 0, 250, Short.MAX_VALUE)
                    .addComponent(jtxtAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jtxtName)
                    .addComponent(jtxtPhone, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchkAutoCode)
                .addContainerGap(225, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsepHeader))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCode)
                    .addComponent(jchkAutoCode))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblName)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDescription))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCountry))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboState, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblState))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCity, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCity))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblAddress))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPostalCode))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPhone))
                .addGap(0, 103, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtxtCode.getText().isEmpty() || jtxtName.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un código y un nombre para guardar el proveedor.");
            return;
        }
        
        Provider provider = new Provider(
            jtxtCode.getText(), jtxtName.getText(), jtxtDescription.getText());
        provider.setCountry(jcboCountry.getSelectedItem().toString());
        provider.setState(jcboState.getSelectedItem().toString());
        provider.setCity(jcboCity.getSelectedItem().toString());
        provider.setAddress(jtxtAddress.getText());
        provider.setPostalCode(jtxtPostalCode.getText());
        provider.setPhone(jtxtPhone.getText());
        provider.setCreatedBy(ApplicationSession.getUser().getId());

        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        provider = iProvider.save(provider);

        if (provider.getId() != null)
        {
            JOptionPane.showMessageDialog(this, "El proveedor se ha guardado exitosamente.");
        }

        jtxtCode.setText("");
        jtxtCode.setEditable(true);
        jtxtCode.setBackground(Color.WHITE);
        jchkAutoCode.setSelected(false);
        jtxtName.setText("");
        jtxtDescription.setText("");
        jcboCountry.setSelectedIndex(0);
        jcboState.setSelectedIndex(0);
        jcboCity.setSelectedIndex(0);
        jtxtAddress.setText("");
        jtxtPostalCode.setText("");
        jtxtPhone.setText("");

    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jchkAutoCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAutoCodeActionPerformed
        if (jchkAutoCode.isSelected())
        {
            IProvider iProvider = Application.getContext().getBean(IProvider.class);
            jtxtCode.setText(iProvider.getNextCode());
            jtxtCode.setEditable(false);
            jtxtCode.setBackground(new Color(196, 196, 196));
            return;
        }
        jtxtCode.setText("");
        jtxtCode.setEditable(true);
        jtxtCode.setBackground(Color.WHITE);
    }//GEN-LAST:event_jchkAutoCodeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<String> jcboCity;
    private javax.swing.JComboBox<String> jcboCountry;
    private javax.swing.JComboBox<String> jcboState;
    private javax.swing.JCheckBox jchkAutoCode;
    private javax.swing.JLabel jlblAddress;
    private javax.swing.JLabel jlblCity;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblCountry;
    private javax.swing.JLabel jlblDescription;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblPhone;
    private javax.swing.JLabel jlblPostalCode;
    private javax.swing.JLabel jlblState;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTextField jtxtAddress;
    private javax.swing.JTextField jtxtCode;
    private javax.swing.JTextField jtxtDescription;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JTextField jtxtPhone;
    private javax.swing.JTextField jtxtPostalCode;
    // End of variables declaration//GEN-END:variables
}
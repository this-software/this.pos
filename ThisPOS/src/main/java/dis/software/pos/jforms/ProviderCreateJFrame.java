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
import dis.software.pos.OptionPane;
import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.IProvider;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la creación de proveedores
 * @author Milton Cavazos
 */
public class ProviderCreateJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(ProviderCreateJFrame.class.getSimpleName());

    /**
     * Creación de nuevo formulario ProviderCreateJForm
     */
    public ProviderCreateJFrame()
    {
        
        initComponents();
        
        ProviderCreateJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (!jtxtCode.getText().isEmpty()
                    && !jtxtName.getText().isEmpty())
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
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Nuevo proveedor");
        setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Agregar nuevo proveedor");

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
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(25, 25, 25)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jtxtPostalCode, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtAddress, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcboCity, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                    .addComponent(jcboState, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jcboCountry, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtName, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtPhone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jchkAutoCode)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCode)
                    .addComponent(jchkAutoCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblName)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblDescription))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCountry))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboState, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblState))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboCity, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCity))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPostalCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPhone))
                .addGap(0, 109, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco "
                + "para continuar.", " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        
        Provider provider = new Provider(
            jtxtCode.getText(), jtxtName.getText(), jtxtDescription.getText());
        provider.setCountry(jcboCountry.getSelectedItem().toString());
        provider.setState(jcboState.getSelectedItem().toString());
        provider.setCity(jcboCity.getSelectedItem().toString());
        provider.setAddress(jtxtAddress.getText());
        provider.setPostalCode(jtxtPostalCode.getText());
        provider.setPhone(jtxtPhone.getText());
        provider.setCreatedBy(ApplicationSession.getUser());

        provider = iProvider.save(provider);

        if (provider.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha guardado exitosamente.",
                " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            
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

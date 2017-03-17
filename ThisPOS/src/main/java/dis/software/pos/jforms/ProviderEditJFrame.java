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
import dis.software.pos.combobox.model.ProviderComboBoxModel;
import dis.software.pos.combobox.renderers.ProviderComboBoxRenderer;
import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.IProvider;
import java.awt.event.ItemEvent;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la modificación de proveedores
 * @author Milton Cavazos
 */
public class ProviderEditJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(ProviderEditJFrame.class.getSimpleName());
    
    private Provider provider1 = null;
    
    /**
     * Creación de nuevo formulario ProductEditJFrame
     * @param provider
     */
    public ProviderEditJFrame(Provider provider)
    {
        
        this.provider1 = provider;
        init();
        
    }

    /**
     * Creación de nuevo formulario ProductEditJFrame
     */
    public ProviderEditJFrame()
    {
        
        init();
        
    }

    private void init()
    {
        
        initComponents();
        
        ProviderEditJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (provider1 == null)
                {
                    logger.info("Window closed");
                    frame.dispose();
                    return;
                }
                //Si el código de proveedor
                if (!jtxtCode.getText().equals(provider1.getCode())
                    //El nombre de proveedor
                    || !jtxtName.getText().equals(provider1.getName())
                    //La descripción de proveedor
                    || !jtxtDescription.getText().equals(provider1.getDescription())
                    //La dirección de proveedor
                    || !jtxtAddress.getText().equals(provider1.getAddress())
                    //El código postal de proveedor
                    || !jtxtPostalCode.getText().equals(provider1.getPostalCode())
                    //O el telefono de proveedor han cambiado, entonces:
                    || !jtxtPhone.getText().equals(provider1.getPhone()))
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
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        List<Provider> list = iProvider.findAll();
        list.add(0, new Provider("", "Elige un proveedor", ""));
        jcboProvider.setModel(new ProviderComboBoxModel(list));
        jcboProvider.setRenderer(new ProviderComboBoxRenderer());
        
        jcboProvider.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                //Objeto afectado por el evento
                Provider provider = (Provider) e.getItem();
                if (provider.getId() == null)
                {
                    jtxtCode.setText("");
                    jtxtName.setText("");
                    jtxtDescription.setText("");
                    jcboCountry.setSelectedIndex(0);
                    jcboState.setSelectedIndex(0);
                    jcboCity.setSelectedIndex(0);
                    jtxtAddress.setText("");
                    jtxtPostalCode.setText("");
                    jtxtPhone.setText("");
                    return;
                }
                jtxtCode.setText(provider.getCode());
                jtxtName.setText(provider.getName());
                jtxtDescription.setText(provider.getDescription());
                jcboCountry.setSelectedItem(provider.getCountry());
                jcboState.setSelectedItem(provider.getState());
                jcboCity.setSelectedItem(provider.getCity());
                jtxtAddress.setText(provider.getAddress());
                jtxtPostalCode.setText(provider.getPostalCode());
                jtxtPhone.setText(provider.getPhone());
            }
        });
        
        SwingUtilities.invokeLater(() ->
        {
            if (provider1 != null)
            {
                jcboProvider.setSelectedItem(provider1);
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
        jcboProvider = new javax.swing.JComboBox<>();
        jlblProfile = new javax.swing.JLabel();
        jtxtCode = new javax.swing.JTextField();
        jlblCode = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblName = new javax.swing.JLabel();
        jtxtDescription = new javax.swing.JTextField();
        jlblDescription = new javax.swing.JLabel();
        jlblState = new javax.swing.JLabel();
        jcboCity = new javax.swing.JComboBox<>();
        jlblCity = new javax.swing.JLabel();
        jtxtAddress = new javax.swing.JTextField();
        jlblAddress = new javax.swing.JLabel();
        jtxtPostalCode = new javax.swing.JTextField();
        jlblPostalCode = new javax.swing.JLabel();
        jtxtPhone = new javax.swing.JTextField();
        jcboCountry = new javax.swing.JComboBox<>();
        jlblPhone = new javax.swing.JLabel();
        jlblCountry = new javax.swing.JLabel();
        jcboState = new javax.swing.JComboBox<>();

        setClosable(true);
        setTitle("Modificar proveedores");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Modificar proveedor");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 390, Short.MAX_VALUE)
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

        jcboProvider.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProfile.setText("Proveedor");

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCode.setText("* Código:");

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblName.setText("* Nombre de proveedor:");

        jtxtDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblDescription.setText("Descripción:");

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

        jcboCountry.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcboCountry.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige un pais", "Mexico" }));
        jcboCountry.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jlblPhone.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblPhone.setText("Teléfono:");

        jlblCountry.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCountry.setText("Pais:");

        jcboState.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jcboState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Elige un estado", "Nuevo León" }));
        jcboState.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

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
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblDescription)
                                    .addComponent(jlblName)
                                    .addComponent(jlblCode)
                                    .addComponent(jlblProfile))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jtxtName, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jtxtCode, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcboProvider, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                                    .addComponent(jtxtDescription)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(86, 86, 86)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                                    .addComponent(jtxtPhone))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCode))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblName)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDescription)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addGap(0, 67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco "
                + "para continuar.", " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        
        Provider provider = (Provider) jcboProvider.getSelectedItem();
        provider.setCode(jtxtCode.getText());
        provider.setName(jtxtName.getText());
        provider.setDescription(jtxtDescription.getText());
        provider.setCountry(jcboCountry.getSelectedItem().toString());
        provider.setState(jcboState.getSelectedItem().toString());
        provider.setCity(jcboCity.getSelectedItem().toString());
        provider.setAddress(jtxtAddress.getText());
        provider.setPostalCode(jtxtPostalCode.getText());
        provider.setPhone(jtxtPhone.getText());
        provider.setUpdatedBy(ApplicationSession.getUser());
        provider.setUpdatedDate(new GregorianCalendar());

        iProvider.update(provider);

        if (provider.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha actualizado exitosamente.",
                " Modificar registro", OptionPane.SUCCESS_MESSAGE);
            
            List<Provider> list = iProvider.findAll();
            list.add(0, new Provider("", "Elige un proveedor", ""));
            jcboProvider.setModel(new ProviderComboBoxModel(list));
            
            jtxtCode.setText("");
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
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar actualizar el registro.",
                " Modificar registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jbtnSaveMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<String> jcboCity;
    private javax.swing.JComboBox<String> jcboCountry;
    private javax.swing.JComboBox<Provider> jcboProvider;
    private javax.swing.JComboBox<String> jcboState;
    private javax.swing.JLabel jlblAddress;
    private javax.swing.JLabel jlblCity;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblCountry;
    private javax.swing.JLabel jlblDescription;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblPhone;
    private javax.swing.JLabel jlblPostalCode;
    private javax.swing.JLabel jlblProfile;
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

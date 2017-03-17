/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.OptionPane;
import dis.software.pos.Property;
import dis.software.pos.combobox.model.ProfileComboBoxModel;
import dis.software.pos.combobox.renderers.ProfileComboBoxRenderer;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.ProfileModule;
import dis.software.pos.interfaces.IProfile;
import dis.software.pos.table.model.ProfileModuleTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la modificación de perfiles
 * @author Milton Cavazos
 */
public class ProfileEditJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(ProfileEditJFrame.class.getSimpleName());
    
    private Profile profile1 = null;
    
    /**
     * Creación de nuevo formulario ProfileEditJFrame
     * @param profile
     */
    public ProfileEditJFrame(Profile profile)
    {
        
        this.profile1 = profile;
        init();
        
    }

    /**
     * Creación de nuevo formulario ProfileEditJFrame
     */
    public ProfileEditJFrame()
    {
        
        init();
        
    }
    
    private void init()
    {
        
        initComponents();
        
        ProfileEditJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (profile1 == null)
                {
                    logger.info("Window closed");
                    frame.dispose();
                    return;
                }
                //Si el código de perfil
                if (!jtxtCode.getText().equals(profile1.getCode())
                    //El nombre de perfil
                    || !jtxtName.getText().equals(profile1.getName())
                    //O la descripción de perfil han cambiado, entonces:
                    || !jtxtDescription.getText().equals(profile1.getDescription()))
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
        
        jtablePrivileges.setModel(new ProfileModuleTableModel());
        jtablePrivileges.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtablePrivileges.setShowHorizontalLines(Boolean.TRUE);
        jtablePrivileges.setGridColor(new Color(179, 179, 179));
        
        //Se eliminan columnas innecesarias para esta vista
        jtablePrivileges.getColumnModel().removeColumn(jtablePrivileges.getColumnModel().getColumn(
            jtablePrivileges.convertColumnIndexToView(ProfileModuleTableModel.COLUMN_MOD_ID)));
        
        jtablePrivileges.addPropertyChangeListener((PropertyChangeEvent evt) ->
        {
            if ("tableCellEditor".equals(evt.getPropertyName()))
            {
                if (!jtablePrivileges.isEditing())
                {
                    ProfileModuleTableModel profileModuleTableModel =
                            (ProfileModuleTableModel) jtablePrivileges.getModel();
                    jchkViewAll.setSelected(profileModuleTableModel.getAll().stream()
                        .allMatch(pm -> pm.getPrivileges().getViewProperty() == Property.ALLOW));
                    jchkCreateEditAll.setSelected(profileModuleTableModel.getAll().stream()
                        .allMatch(pm -> pm.getPrivileges().getCreateProperty() == Property.ALLOW
                            && pm.getPrivileges().getEditProperty() == Property.ALLOW));
                }
            }
        });
        
        ((JComponent) jtablePrivileges.getDefaultRenderer(Boolean.class)).setOpaque(true);

        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        List<Profile> list = iProfile.findAll();
        list.add(0, new Profile("", "Elige un perfil", ""));
        jcboProfile.setModel(new ProfileComboBoxModel(list));
        jcboProfile.setRenderer(new ProfileComboBoxRenderer());

        jcboProfile.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                //Objeto afectado por el evento
                Profile profile = (Profile) e.getItem();
                if (profile.getId() == null)
                {
                    jtxtCode.setText("");
                    jtxtName.setText("");
                    jtxtDescription.setText("");
                    ProfileModuleTableModel profileModuleTableModel =
                        (ProfileModuleTableModel) jtablePrivileges.getModel();
                    profileModuleTableModel.getAll().forEach(profileModule ->
                    {
                        profileModule.getPrivileges().setViewProperty(Property.DENY);
                        profileModule.getPrivileges().setCreateProperty(Property.DENY);
                        profileModule.getPrivileges().setEditProperty(Property.DENY);
                        profileModule.getPrivileges().setDeleteProperty(Property.DENY);
                    });
                    profileModuleTableModel.fireTableDataChanged();
                    return;
                }
                
                jtxtCode.setText(profile.getCode());
                jtxtName.setText(profile.getName());
                jtxtDescription.setText(profile.getDescription());
                
                List<ProfileModule> profileModules = new ArrayList<>(profile.getProfileModules());
                jtablePrivileges.setModel(new ProfileModuleTableModel(
                    //Se ordena la lista por nombre del modulo
                    profileModules.stream().sorted((obj1, obj2) -> obj1.getModule().getName().compareTo(
                        obj2.getModule().getName())).collect(Collectors.toList())));
                
                //Si todos los modulos del perfil tienen privilegio "Ver" asignado
                if (profileModules.stream()
                    .allMatch(pm -> pm.getPrivileges().getViewProperty() == Property.ALLOW))
                {
                    jchkViewAll.setSelected(Boolean.TRUE);
                }
                //Si todos los modulos del perfil tienen privilegio "Crear" y "Editar" asignado
                if (profileModules.stream()
                    .allMatch(pm -> pm.getPrivileges().getCreateProperty() == Property.ALLOW
                        && pm.getPrivileges().getEditProperty() == Property.ALLOW));
                {
                    jchkCreateEditAll.setSelected(Boolean.TRUE);
                }
                
                //Se eliminan columnas innecesarias para esta vista
                jtablePrivileges.getColumnModel().removeColumn(jtablePrivileges.getColumnModel().getColumn(
                    jtablePrivileges.convertColumnIndexToView(ProfileModuleTableModel.COLUMN_MOD_ID)));

                jtablePrivileges.getColumnModel().getColumn(0).setMinWidth(150);
                jtablePrivileges.getColumnModel().getColumn(0).setMaxWidth(150);
                jtablePrivileges.getColumnModel().getColumn(2).setMinWidth(80);
                jtablePrivileges.getColumnModel().getColumn(2).setMaxWidth(80);
                jtablePrivileges.getColumnModel().getColumn(3).setMinWidth(80);
                jtablePrivileges.getColumnModel().getColumn(3).setMaxWidth(80);
                jtablePrivileges.getColumnModel().getColumn(4).setMinWidth(80);
                jtablePrivileges.getColumnModel().getColumn(4).setMaxWidth(80);
                jtablePrivileges.getColumnModel().getColumn(5).setMinWidth(80);
                jtablePrivileges.getColumnModel().getColumn(5).setMaxWidth(80);
                
                jtablePrivileges.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            }
        });
        
        SwingUtilities.invokeLater(() ->
        {
            if (profile1 != null)
            {
                jcboProfile.setSelectedItem(profile1);
            }
        });
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para modificar un registro">
    private void edit()
    {
        
        ProfileModuleTableModel profileModuleTableModel =
            (ProfileModuleTableModel) jtablePrivileges.getModel();
        
        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco "
                + "para continuar.", " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        
        Profile profile = (Profile) jcboProfile.getSelectedItem();
        profile.setCode(jtxtCode.getText());
        profile.setName(jtxtName.getText());
        profile.setDescription(jtxtDescription.getText());
        profile.setUpdatedBy(ApplicationSession.getUser());
        profile.setUpdatedDate(new GregorianCalendar());
        
        iProfile.update(profile);

        if (profile.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha actualizado exitosamente.",
                " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            
            List<Profile> list = iProfile.findAll();
            list.add(0, new Profile("", "Elige un perfil", ""));
            jcboProfile.setModel(new ProfileComboBoxModel(list));
            
            jtxtCode.setText("");
            jtxtName.setText("");
            jtxtDescription.setText("");
            jchkViewAll.setSelected(false);
            jchkCreateEditAll.setSelected(false);
            profileModuleTableModel.getAll().forEach(profileModule ->
            {
                profileModule.getPrivileges().setViewProperty(Property.DENY);
                profileModule.getPrivileges().setCreateProperty(Property.DENY);
                profileModule.getPrivileges().setEditProperty(Property.DENY);
                profileModule.getPrivileges().setDeleteProperty(Property.DENY);
            });
            profileModuleTableModel.fireTableDataChanged();
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar actualizar el registro.",
                " Guardar registro", JOptionPane.ERROR_MESSAGE);
        }
        
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
        jbtnSave = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jcboProfile = new javax.swing.JComboBox<>();
        jlblProfile = new javax.swing.JLabel();
        jtxtCode = new javax.swing.JTextField();
        jlblCode = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblName = new javax.swing.JLabel();
        jtxtDescription = new javax.swing.JTextField();
        jlblDescription = new javax.swing.JLabel();
        jpaneOptions = new javax.swing.JPanel();
        jchkViewAll = new javax.swing.JCheckBox();
        jchkCreateEditAll = new javax.swing.JCheckBox();
        jlblCreateEditAll = new javax.swing.JLabel();
        jlblViewAll = new javax.swing.JLabel();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtablePrivileges = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modificar perfiles");
        setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Modificar perfil");

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

        jcboProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProfile.setText("Perfil");

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCode.setText("* Código:");

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblName.setText("* Nombre de perfil:");

        jtxtDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblDescription.setText("Descripción:");

        jpaneOptions.setBackground(new java.awt.Color(255, 255, 255));
        jpaneOptions.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 51), 2, true));

        jchkViewAll.setBackground(new java.awt.Color(255, 255, 255));
        jchkViewAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkViewAll.setForeground(new java.awt.Color(0, 0, 0));
        jchkViewAll.setText("Ver todo");
        jchkViewAll.setOpaque(false);
        jchkViewAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkViewAllActionPerformed(evt);
            }
        });

        jchkCreateEditAll.setBackground(new java.awt.Color(255, 255, 255));
        jchkCreateEditAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkCreateEditAll.setForeground(new java.awt.Color(0, 0, 0));
        jchkCreateEditAll.setText("Crear/modificar todo");
        jchkCreateEditAll.setOpaque(false);
        jchkCreateEditAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkCreateEditAllActionPerformed(evt);
            }
        });

        jlblCreateEditAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCreateEditAll.setForeground(new java.awt.Color(0, 0, 0));
        jlblCreateEditAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info-sign-b.png"))); // NOI18N
        jlblCreateEditAll.setText("Puede crear y modificar toda la información de los módulos.");

        jlblViewAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblViewAll.setForeground(new java.awt.Color(0, 0, 0));
        jlblViewAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info-sign-b.png"))); // NOI18N
        jlblViewAll.setText("Puede ver toda la información de los módulos.");
        jlblViewAll.setToolTipText("");

        javax.swing.GroupLayout jpaneOptionsLayout = new javax.swing.GroupLayout(jpaneOptions);
        jpaneOptions.setLayout(jpaneOptionsLayout);
        jpaneOptionsLayout.setHorizontalGroup(
            jpaneOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpaneOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpaneOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchkCreateEditAll)
                    .addComponent(jchkViewAll))
                .addGap(69, 69, 69)
                .addGroup(jpaneOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblViewAll)
                    .addComponent(jlblCreateEditAll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpaneOptionsLayout.setVerticalGroup(
            jpaneOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpaneOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpaneOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkViewAll)
                    .addComponent(jlblViewAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jpaneOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkCreateEditAll)
                    .addComponent(jlblCreateEditAll))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jtablePrivileges.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtablePrivileges.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtablePrivileges.setName("jtablePrivileges"); // NOI18N
        jtablePrivileges.setRowHeight(25);
        jtablePrivileges.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtablePrivileges);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jcboProfile, javax.swing.GroupLayout.Alignment.LEADING, 0, 200, Short.MAX_VALUE)
                    .addComponent(jtxtDescription))
                .addContainerGap(437, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jscrollPaneTable, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jpaneOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jsepHeader)
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addGap(18, 18, 18)
                .addComponent(jpaneOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );

        jcboProfile.getAccessibleContext().setAccessibleName("jcboProfile");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        edit();
        
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jchkViewAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkViewAllActionPerformed
        
        ProfileModuleTableModel profileModuleTableModel =
            (ProfileModuleTableModel) jtablePrivileges.getModel();
        
        profileModuleTableModel.getAll().forEach(profileModule ->
        {
            profileModule.getPrivileges().setViewProperty(
                jchkViewAll.isSelected() ? Property.ALLOW : Property.DENY);
        });
        profileModuleTableModel.fireTableDataChanged();
        
    }//GEN-LAST:event_jchkViewAllActionPerformed

    private void jchkCreateEditAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkCreateEditAllActionPerformed
        
        ProfileModuleTableModel profileModuleTableModel =
            (ProfileModuleTableModel) jtablePrivileges.getModel();
        
        profileModuleTableModel.getAll().forEach(profileModule ->
        {
            profileModule.getPrivileges().setCreateProperty(
                jchkCreateEditAll.isSelected() ? Property.ALLOW : Property.DENY);
            profileModule.getPrivileges().setEditProperty(
                jchkCreateEditAll.isSelected() ? Property.ALLOW : Property.DENY);
        });
        profileModuleTableModel.fireTableDataChanged();
        
    }//GEN-LAST:event_jchkCreateEditAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<Profile> jcboProfile;
    private javax.swing.JCheckBox jchkCreateEditAll;
    private javax.swing.JCheckBox jchkViewAll;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblCreateEditAll;
    private javax.swing.JLabel jlblDescription;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblProfile;
    private javax.swing.JLabel jlblViewAll;
    private javax.swing.JPanel jpaneOptions;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtablePrivileges;
    private javax.swing.JTextField jtxtCode;
    private javax.swing.JTextField jtxtDescription;
    private javax.swing.JTextField jtxtName;
    // End of variables declaration//GEN-END:variables
}

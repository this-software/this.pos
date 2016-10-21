/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.Property;
import dis.software.pos.combobox.model.ProfileComboBoxModel;
import dis.software.pos.combobox.renderers.ProfileComboBoxRenderer;
import dis.software.pos.entities.Module;
import dis.software.pos.entities.Privileges;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.ProfileModule;
import dis.software.pos.entities.ProfileModulePk;
import dis.software.pos.interfaces.IProfile;
import dis.software.pos.interfaces.IProfileModule;
import dis.software.pos.table.model.ProfileModuleTableModel;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 * Formulario para la modificación de perfiles
 * @author Milton Cavazos
 */
public class ProfileEditJFrame extends javax.swing.JInternalFrame
{

    /**
     * Creación de nuevo formulario ProfileEditJFrame
     */
    public ProfileEditJFrame()
    {
        initComponents();

        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        List<Profile> list = iProfile.findAll();
        list.add(0, new Profile("", "Elige un perfil", ""));
        jcboProfile.setModel(new ProfileComboBoxModel(list));
        jcboProfile.setRenderer(new ProfileComboBoxRenderer());

        jcboProfile.addItemListener((ItemEvent e) ->
        {
            //Objeto afectado por el evento
            if (!(e.getItem() instanceof Profile))
            {
                jtxtCode.setText("");
                jtxtName.setText("");
                jtxtDescription.setText("");
                for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++)
                {
                    jtablePrivileges.setValueAt(Boolean.FALSE, i, 2);
                    jtablePrivileges.setValueAt(Boolean.FALSE, i, 3);
                    jtablePrivileges.setValueAt(Boolean.FALSE, i, 4);
                    jtablePrivileges.setValueAt(Boolean.FALSE, i, 5);
                }
                return;
            }
            Profile profile = (Profile) e.getItem();
            if (e.getStateChange() == ItemEvent.SELECTED
                && profile.getId() != null)
            {
                jtxtCode.setText(profile.getCode());
                jtxtName.setText(profile.getName());
                jtxtDescription.setText(profile.getDescription());
                
                Set<ProfileModule> profileModules = profile.getProfileModules();
                jtablePrivileges.setModel(new ProfileModuleTableModel(new ArrayList<>(profileModules)));
                
                ((JComponent) jtablePrivileges.getDefaultRenderer(Boolean.class)).setOpaque(true);
        
                jtablePrivileges.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 14));
                jtablePrivileges.getColumnModel().removeColumn(jtablePrivileges.getColumnModel().getColumn(0));

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
        setTitle("Modificar perfiles");
        setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Modificar perfil");

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        jlblCreateEditAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/glyphicons-196-info-sign.png"))); // NOI18N
        jlblCreateEditAll.setText("Puede crear y modificar toda la información de los módulos.");

        jlblViewAll.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblViewAll.setForeground(new java.awt.Color(0, 0, 0));
        jlblViewAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/glyphicons-196-info-sign.png"))); // NOI18N
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
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblDescription)
                    .addComponent(jlblName)
                    .addComponent(jlblCode)
                    .addComponent(jlblProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jcboProfile, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtxtDescription)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jtxtName))
                .addContainerGap(340, Short.MAX_VALUE))
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
                    .addComponent(jcboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProfile))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCode))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblName)
                    .addComponent(jtxtName, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblDescription)
                    .addComponent(jtxtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jpaneOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );

        jcboProfile.getAccessibleContext().setAccessibleName("jcboProfile");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtxtCode.getText().isEmpty() || jtxtName.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un código y un nombre para guardar el perfil.");
            return;
        }
        Profile profile = (Profile) jcboProfile.getSelectedItem();
        //profile.setCode(jtxtCode.getText());
        profile.setName(jtxtName.getText());
        profile.setDescription(jtxtDescription.getText());
        profile.setUpdatedBy(ApplicationSession.getUser().getId());
        profile.setUpdatedDate(new Date());
        
        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        iProfile.update(profile);
        iProfile.flush();

        for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++)
        {
            //Se asigna la llave primaria
            ProfileModulePk profileModulePk = new ProfileModulePk();
            profileModulePk.setProfile(profile);
            profileModulePk.setModule(new Module((Long) jtablePrivileges.getModel().getValueAt(i, 0)));
            
            //Se asignan los privilegios por cada módulo
            Privileges privilege = new Privileges();
            privilege.setViewProperty((Boolean) jtablePrivileges.getModel().getValueAt(i, 3)
                ? Property.ALLOW : Property.DENY);
            privilege.setCreateProperty((Boolean) jtablePrivileges.getModel().getValueAt(i, 4)
                ? Property.ALLOW : Property.DENY);
            privilege.setEditProperty((Boolean) jtablePrivileges.getModel().getValueAt(i, 5)
                ? Property.ALLOW : Property.DENY);
            privilege.setDeleteProperty((Boolean) jtablePrivileges.getModel().getValueAt(i, 6)
                ? Property.ALLOW : Property.DENY);
            
            ProfileModule profileModule = new ProfileModule();
            profileModule.setProfileModulePk(profileModulePk);
            profileModule.setPrivileges(privilege);

            IProfileModule iProfileModule = Application.getContext().getBean(IProfileModule.class);
            iProfileModule.update(profileModule);
        }

        if (profile.getId() != null)
        {
            JOptionPane.showMessageDialog(this, "El perfil se ha actualizado exitosamente.");
            
            List<Profile> list = iProfile.findAll();
            list.add(0, new Profile("", "Elige un perfil", ""));
            jcboProfile.setModel(new ProfileComboBoxModel(list));
            
            jtxtCode.setText("");
            jtxtName.setText("");
            jtxtDescription.setText("");
            for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++)
            {
                jtablePrivileges.setValueAt(Boolean.FALSE, i, 2);
                jtablePrivileges.setValueAt(Boolean.FALSE, i, 3);
                jtablePrivileges.setValueAt(Boolean.FALSE, i, 4);
                jtablePrivileges.setValueAt(Boolean.FALSE, i, 5);
            }
        }
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jchkViewAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkViewAllActionPerformed
        if (jchkViewAll.isSelected()) {
            for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++) {
                jtablePrivileges.setValueAt(Boolean.TRUE, i, 2);
            }
            return;
        }
        for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++) {
            jtablePrivileges.setValueAt(Boolean.FALSE, i, 2);
        }
    }//GEN-LAST:event_jchkViewAllActionPerformed

    private void jchkCreateEditAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkCreateEditAllActionPerformed
        if (jchkCreateEditAll.isSelected()) {
            for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++) {
                jtablePrivileges.setValueAt(Boolean.TRUE, i, 3);
                jtablePrivileges.setValueAt(Boolean.TRUE, i, 4);
            }
            return;
        }
        for (int i = 0; i < jtablePrivileges.getModel().getRowCount(); i++) {
            jtablePrivileges.setValueAt(Boolean.FALSE, i, 3);
            jtablePrivileges.setValueAt(Boolean.FALSE, i, 4);
        }
    }//GEN-LAST:event_jchkCreateEditAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnCancel;
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

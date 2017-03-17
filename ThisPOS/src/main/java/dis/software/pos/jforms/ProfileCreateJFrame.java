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
import dis.software.pos.entities.Privileges;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.ProfileModule;
import dis.software.pos.interfaces.IModule;
import dis.software.pos.interfaces.IProfile;
import dis.software.pos.table.model.ProfileModuleTableModel;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la creación de perfiles
 * @author Milton Cavazos
 */
public class ProfileCreateJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(ProfileCreateJFrame.class.getSimpleName());
    
    /**
     * Creación de nuevo formulario ProfileCreateJFrame
     */
    public ProfileCreateJFrame()
    {
        
        initComponents();
        
        ProfileCreateJFrame frame = this;
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
        
        List<ProfileModule> list = new ArrayList<>();
        
        IModule iModule = Application.getContext().getBean(IModule.class);
        iModule.findAll().forEach(module ->
        {
            ProfileModule profileModule = new ProfileModule();
            profileModule.setModule(module);
            Privileges privileges = new Privileges(
                Property.DENY, Property.DENY, Property.DENY, Property.DENY);
            profileModule.setPrivileges(privileges);
            list.add(profileModule);
        });
        jtablePrivileges.setModel(new ProfileModuleTableModel(list));
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
        setTitle("Nuevo perfil");
        setMinimumSize(new java.awt.Dimension(800, 600));
        setPreferredSize(new java.awt.Dimension(800, 600));

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Agregar nuevo perfil");

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

        jlblHeader.getAccessibleContext().setAccessibleName("jlblHeader");
        jbtnSave.getAccessibleContext().setAccessibleName("jbtnSave");

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCode.setText("* Código");

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblName.setText("* Nombre de perfil");

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
        jlblViewAll.setLabelFor(jchkViewAll);
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
                .addContainerGap(146, Short.MAX_VALUE))
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

        jchkCreateEditAll.getAccessibleContext().setAccessibleName("jchkCreateEditAll");
        jlblCreateEditAll.getAccessibleContext().setAccessibleName("jlblCreateEditAll");
        jlblViewAll.getAccessibleContext().setAccessibleName("jlblViewAll");

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
        jtablePrivileges.getAccessibleContext().setAccessibleName("jtablePrivileges");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jsepHeader)
                            .addComponent(jpaneOptions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jscrollPaneTable)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jlblName)
                            .addComponent(jlblCode)
                            .addComponent(jlblDescription))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jtxtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                .addComponent(jtxtName))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jchkAutoCode)))
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
                    .addComponent(jtxtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblCode)
                    .addComponent(jchkAutoCode))
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
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("jFrameProfileCreate");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked
        
        ProfileModuleTableModel profileModuleTableModel =
            (ProfileModuleTableModel) jtablePrivileges.getModel();
        
        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty())
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        
        Profile profile = new Profile(
            jtxtCode.getText(), jtxtName.getText(), jtxtDescription.getText());
        profile.setCreatedBy(ApplicationSession.getUser());
        
        profileModuleTableModel.getAll().stream().forEach(profileModule ->
        {
            profileModule.setProfile(profile);
            profile.getProfileModules().add(profileModule);
        });
        
        iProfile.save(profile);
        
        if (profile.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha guardado exitosamente.",
                " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            
            jtxtCode.setText("");
            jtxtCode.setEditable(true);
            jtxtCode.setBackground(Color.WHITE);
            jchkAutoCode.setSelected(false);
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
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar guardar el registro.",
                " Guardar registro", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jchkAutoCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkAutoCodeActionPerformed
        
        if (jchkAutoCode.isSelected())
        {
            IProfile iProfile = Application.getContext().getBean(IProfile.class);
            jtxtCode.setText(iProfile.getNextCode());
            jtxtCode.setEditable(false);
            jtxtCode.setBackground(new Color(196, 196, 196));
            return;
        }
        jtxtCode.setText("");
        jtxtCode.setEditable(true);
        jtxtCode.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_jchkAutoCodeActionPerformed

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
    private javax.swing.JCheckBox jchkAutoCode;
    private javax.swing.JCheckBox jchkCreateEditAll;
    private javax.swing.JCheckBox jchkViewAll;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblCreateEditAll;
    private javax.swing.JLabel jlblDescription;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblName;
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

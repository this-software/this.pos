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
import dis.software.pos.Crypt;
import dis.software.pos.OptionPane;
import dis.software.pos.combobox.model.ProfileComboBoxModel;
import dis.software.pos.combobox.model.UserComboBoxModel;
import dis.software.pos.combobox.renderers.ProfileComboBoxRenderer;
import dis.software.pos.combobox.renderers.UserComboBoxRenderer;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IProfile;
import dis.software.pos.interfaces.IUser;
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
 * Formulario para la modificación de usuarios
 * @author Milton Cavazos
 */
public class UserEditJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(UserEditJFrame.class.getSimpleName());
    
    private User user1 = null;
    
    /**
     * Creación de nuevo formulario UserEditJFrame
     * @param user
     */
    public UserEditJFrame(User user)
    {
        
        this.user1 = user;
        init();
        
    }

    /**
     * Creación de nuevo formulario UserEditJFrame
     */
    public UserEditJFrame()
    {
        
        init();
        
    }

    private void init()
    {
        
        initComponents();
        
        UserEditJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (user1 == null)
                {
                    logger.info("Window closed");
                    frame.dispose();
                    return;
                }
                //Si el código de usuario
                if (!jtxtCode.getText().equals(user1.getCode())
                    //El nombre de usuario
                    || !jtxtName.getText().equals(user1.getName())
                    //El correo de usuario
                    || !jtxtEmail.getText().equals(user1.getEmail())
                    //La contraseña de usuario
                    || !new String(jtxtPassword.getPassword()).equals(
                        Crypt.decrypt(user1.getPassword(), user1.getSalt()))
                    //El perfil de usuario
                    || !((Profile) jcboProfile.getSelectedItem()).getId().equals(user1.getProfile().getId())
                    //O las notificaciones han cambiado, entonces:
                    || jchkReceiveNotifications.isSelected() != user1.getReceiveNotifications())
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
        
        IUser iUser = Application.getContext().getBean(IUser.class);
        List<User> users = iUser.findAll();
        users.add(0, new User("", "Elige un usuario", ""));
        jcboUser.setModel(new UserComboBoxModel(users));
        jcboUser.setRenderer(new UserComboBoxRenderer());
        jcboUser.addItemListener((ItemEvent e) ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                //Objeto afectado por el evento
                User user = (User) e.getItem();
                if (user.getId() == null)
                {
                    jtxtCode.setText("");
                    jtxtName.setText("");
                    jtxtEmail.setText("");
                    jtxtPassword.setText("");
                    jcboProfile.setSelectedIndex(0);
                    jchkReceiveNotifications.setSelected(Boolean.FALSE);
                    return;
                }
                jtxtCode.setText(user.getCode());
                jtxtName.setText(user.getName());
                jtxtEmail.setText(user.getEmail());
                jtxtPassword.setText(Crypt.decrypt(user.getPassword(), user.getSalt()));
                jcboProfile.setSelectedItem(user.getProfile());
                jchkReceiveNotifications.setSelected(user.getReceiveNotifications());
                jchkReceiveNotifications.setText(
                    jchkReceiveNotifications.isSelected() ? "Activo" : "Inactivo");
                this.user1 = user;
            }
        });
        
        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        List<Profile> profiles = iProfile.findAll();
        profiles.add(0, new Profile("", "Elige un perfil", ""));
        jcboProfile.setModel(new ProfileComboBoxModel(profiles));
        jcboProfile.setRenderer(new ProfileComboBoxRenderer());
        
        SwingUtilities.invokeLater(() ->
        {
            if (user1 != null)
            {
                jcboUser.setSelectedItem(user1);
            }
        });
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para modificar un registro">
    private void edit()
    {
        
        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty()
            || jtxtPassword.getPassword().length == 0)
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco para continuar.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (jcboProfile.getSelectedIndex() == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione un perfil para continuar.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        IUser iUser = Application.getContext().getBean(IUser.class);
        
        User user = (User) jcboUser.getSelectedItem();
        user.setCode(jtxtCode.getText());
        user.setName(jtxtName.getText());
        user.setEmail(jtxtEmail.getText());
        if (!new String(jtxtPassword.getPassword()).equals(
            Crypt.decrypt(user.getPassword(), user.getSalt())))
        {
            String salt = Crypt.getSalt();
            user.setPassword(Crypt.encrypt(new String(jtxtPassword.getPassword()), salt));
            user.setSalt(salt);
        }
        user.setProfile((Profile) jcboProfile.getSelectedItem());
        user.setReceiveNotifications(jchkReceiveNotifications.isSelected());
        user.setUpdatedBy(ApplicationSession.getUser());
        user.setUpdatedDate(new GregorianCalendar());

        iUser.update(user);

        if (user.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha actualizado exitosamente.",
                " Modificar registro", OptionPane.SUCCESS_MESSAGE);
            
            jcboUser.setSelectedIndex(0);
            jtxtCode.setText("");
            jtxtName.setText("");
            jtxtEmail.setText("");
            jtxtPassword.setText("");
            jcboProfile.setSelectedIndex(0);
            jchkReceiveNotifications.setSelected(false);
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar actualizar el registro.",
                " Modificar registro", JOptionPane.ERROR_MESSAGE);
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
        jlblUser = new javax.swing.JLabel();
        jcboUser = new javax.swing.JComboBox<>();
        jlblCode = new javax.swing.JLabel();
        jtxtCode = new javax.swing.JTextField();
        jlblName = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblEmail = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jlblPassword = new javax.swing.JLabel();
        jtxtPassword = new javax.swing.JPasswordField();
        jbtnRestorePassword = new javax.swing.JButton();
        jlblProfile = new javax.swing.JLabel();
        jcboProfile = new javax.swing.JComboBox<>();
        jlblReceiveNotifications = new javax.swing.JLabel();
        jchkReceiveNotifications = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modificar usuarios");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Modificar usuario");

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

        jlblUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblUser.setText("Usuario");

        jcboUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCode.setText("* Código:");

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblName.setText("* Nombre de usuario:");

        jtxtName.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblEmail.setText("Correo:");

        jtxtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblPassword.setText("* Contraseña:");

        jtxtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtxtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtxtPasswordKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtxtPasswordKeyReleased(evt);
            }
        });

        jbtnRestorePassword.setBackground(new java.awt.Color(204, 204, 204));
        jbtnRestorePassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnRestorePassword.setForeground(new java.awt.Color(0, 0, 0));
        jbtnRestorePassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/info-sign-b.png"))); // NOI18N
        jbtnRestorePassword.setText("Restaurar");
        jbtnRestorePassword.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnRestorePassword.setIconTextGap(8);

        jlblProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProfile.setText("Perfil");

        jcboProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblReceiveNotifications.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblReceiveNotifications.setText("Recibir notificaciones:");

        jchkReceiveNotifications.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkReceiveNotifications.setText("Inactivo");
        jchkReceiveNotifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkReceiveNotificationsActionPerformed(evt);
            }
        });

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
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblName)
                                    .addComponent(jlblCode)
                                    .addComponent(jlblEmail)
                                    .addComponent(jlblPassword)
                                    .addComponent(jlblProfile)
                                    .addComponent(jlblReceiveNotifications))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jcboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jtxtName)
                                        .addComponent(jtxtCode)
                                        .addComponent(jtxtEmail)
                                        .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jchkReceiveNotifications)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlblUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jcboUser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jbtnRestorePassword)
                        .addGap(0, 242, Short.MAX_VALUE)))
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
                    .addComponent(jcboUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblUser))
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
                    .addComponent(jlblEmail)
                    .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblPassword)
                    .addComponent(jbtnRestorePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkReceiveNotifications)
                    .addComponent(jlblReceiveNotifications))
                .addGap(167, 167, 167))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        edit();
        
    }//GEN-LAST:event_jbtnSaveMouseClicked

    private void jtxtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPasswordKeyPressed

        

    }//GEN-LAST:event_jtxtPasswordKeyPressed

    private void jtxtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPasswordKeyReleased

        

    }//GEN-LAST:event_jtxtPasswordKeyReleased

    private void jchkReceiveNotificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkReceiveNotificationsActionPerformed

        jchkReceiveNotifications.setText(
            jchkReceiveNotifications.isSelected() ? "Activo" : "Inactivo");

    }//GEN-LAST:event_jchkReceiveNotificationsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnRestorePassword;
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<Profile> jcboProfile;
    private javax.swing.JComboBox<User> jcboUser;
    private javax.swing.JCheckBox jchkReceiveNotifications;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblEmail;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblPassword;
    private javax.swing.JLabel jlblProfile;
    private javax.swing.JLabel jlblReceiveNotifications;
    private javax.swing.JLabel jlblUser;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTextField jtxtCode;
    private javax.swing.JTextField jtxtEmail;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JPasswordField jtxtPassword;
    // End of variables declaration//GEN-END:variables
}

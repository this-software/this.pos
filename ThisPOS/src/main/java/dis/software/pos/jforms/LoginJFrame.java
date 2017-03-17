/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.jforms;

import com.google.gson.Gson;
import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.ApplicationSound;
import dis.software.pos.Crypt;
import dis.software.pos.OptionPane;
import dis.software.pos.Setting;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Milton Cavazos
 */
public class LoginJFrame extends javax.swing.JFrame
{

    private static final Logger logger = LogManager.getLogger(LoginJFrame.class.getSimpleName());
    
    /**
     * Creación de nuevo formulario LoginJFrame
     */
    public LoginJFrame()
    {
        
        initComponents();
        
        LoginJFrame frame = this;
        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                exit();
            }
        });
        
        JRootPane jRootPane = super.getRootPane();
        
        jRootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        jRootPane.getActionMap().put("ENTER", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               login();
            }
        });
        
        logger.info("Login window loaded.");
        
    }
    
    @Override
    public void setDefaultCloseOperation(int operation)
    {
        super.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para iniciar sesión">
    private void login()
    {
        
        String username = jtxtUser.getText();
        char[] password = jtxtPassword.getPassword();
        if (!username.isEmpty() && password.length != 0)
        {
            IUser iUser = Application.getContext().getBean(IUser.class);
            User user = iUser.getUser(username);
            if (user == null)
            {
                OptionPane.showMessageDialog(this, "Ingrese un usuario válido para continuar.",
                    " Ingresar al sistema", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!user.getPassword().equals(
                Crypt.encrypt(new String(password), user.getSalt())))
            {
                OptionPane.showMessageDialog(this, "Contraseña incorrecta.",
                    " Ingresar al sistema", JOptionPane.ERROR_MESSAGE);
                return;
            }
            logger.info("Property (user.dir) : ".concat(System.getProperty("user.dir")));
            //Se cargan los datos de configuración del sistema
            Gson gson = new Gson();
            try (Reader reader = new FileReader(
                System.getProperty("user.dir").concat(Setting.FILE_NAME)))
            {
                Setting setting = gson.fromJson(reader, Setting.class);
                //Se cargan los datos de configuración de la aplicación
                Application.setSetting(setting);
                logger.info("Setting JSON file read.");
            }
            catch (IOException e)
            {
                logger.error("Error reading setting JSON file", e);
            }
            ApplicationSound.start();
            //Se cargan los datos de usuario en sesión
            ApplicationSession.setUser(user);
            //Se inicia la pantalla principal del sistema
            MainJFrame mainJFrame = new MainJFrame();
            mainJFrame.setVisible(true);
            //Se oculta formulario de inicio de sesión
            dispose();
        }
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para salir del sistema">
    private void exit()
    {
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere salir del sistema?",
            " Mensaje del sistema", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.YES_OPTION)
        {
            logger.info("¿Window closed? ¡YES!");
            System.exit(0);
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

        jpanelLogin = new javax.swing.JPanel();
        jtxtUser = new javax.swing.JTextField();
        jlblUser = new javax.swing.JLabel();
        jtxtPassword = new javax.swing.JPasswordField();
        jlblPassword = new javax.swing.JLabel();
        jbtnLogin = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jsepLogin = new javax.swing.JSeparator();
        jpanelLoginHeader = new javax.swing.JPanel();
        jpanelRobotLogo = new javax.swing.JPanel();
        jlblRobotLogo = new javax.swing.JLabel();
        jpanelThispos = new javax.swing.JPanel();
        jlblThispos = new javax.swing.JLabel();
        jlblPos = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("this.Software (1.0.0)");
        setBackground(new java.awt.Color(51, 51, 51));
        setName("jframeLogin"); // NOI18N
        setResizable(false);
        setSize(new java.awt.Dimension(0, 0));

        jpanelLogin.setBackground(new java.awt.Color(51, 51, 51));
        jpanelLogin.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jtxtUser.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jlblUser.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblUser.setForeground(new java.awt.Color(204, 204, 204));
        jlblUser.setText("Nombre de usuario");

        jtxtPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jlblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblPassword.setForeground(new java.awt.Color(204, 204, 204));
        jlblPassword.setText("Contraseña");

        jbtnLogin.setBackground(new java.awt.Color(51, 51, 255));
        jbtnLogin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnLogin.setForeground(new java.awt.Color(255, 255, 255));
        jbtnLogin.setText("Ingresar");
        jbtnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnLoginActionPerformed(evt);
            }
        });

        jbtnCancel.setBackground(new java.awt.Color(204, 204, 204));
        jbtnCancel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnCancel.setForeground(new java.awt.Color(0, 0, 0));
        jbtnCancel.setText("Cancelar");
        jbtnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpanelLoginLayout = new javax.swing.GroupLayout(jpanelLogin);
        jpanelLogin.setLayout(jpanelLoginLayout);
        jpanelLoginLayout.setHorizontalGroup(
            jpanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLoginLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jpanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jpanelLoginLayout.createSequentialGroup()
                        .addComponent(jbtnLogin)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnCancel))
                    .addGroup(jpanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jlblUser)
                        .addComponent(jlblPassword)
                        .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jtxtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jpanelLoginLayout.setVerticalGroup(
            jpanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLoginLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jlblUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(jpanelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnCancel)
                    .addComponent(jbtnLogin))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jsepLogin.setBackground(new java.awt.Color(153, 153, 153));
        jsepLogin.setForeground(new java.awt.Color(153, 153, 153));
        jsepLogin.setOpaque(true);

        jpanelLoginHeader.setBackground(new java.awt.Color(49, 231, 94));
        jpanelLoginHeader.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        jpanelRobotLogo.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jpanelRobotLogo.setOpaque(false);
        jpanelRobotLogo.setPreferredSize(new java.awt.Dimension(74, 56));

        jlblRobotLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thissoft-robot-50.png"))); // NOI18N

        javax.swing.GroupLayout jpanelRobotLogoLayout = new javax.swing.GroupLayout(jpanelRobotLogo);
        jpanelRobotLogo.setLayout(jpanelRobotLogoLayout);
        jpanelRobotLogoLayout.setHorizontalGroup(
            jpanelRobotLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
            .addGroup(jpanelRobotLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelRobotLogoLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jlblRobotLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jpanelRobotLogoLayout.setVerticalGroup(
            jpanelRobotLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 56, Short.MAX_VALUE)
            .addGroup(jpanelRobotLogoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jlblRobotLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE))
        );

        jpanelThispos.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jpanelThispos.setOpaque(false);
        jpanelThispos.setPreferredSize(new java.awt.Dimension(133, 30));

        jlblThispos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thispos-logo-30.png"))); // NOI18N

        javax.swing.GroupLayout jpanelThisposLayout = new javax.swing.GroupLayout(jpanelThispos);
        jpanelThispos.setLayout(jpanelThisposLayout);
        jpanelThisposLayout.setHorizontalGroup(
            jpanelThisposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlblThispos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpanelThisposLayout.setVerticalGroup(
            jpanelThisposLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlblThispos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jlblPos.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jlblPos.setForeground(new java.awt.Color(255, 255, 255));
        jlblPos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlblPos.setText("PUNTO DE VENTA");

        javax.swing.GroupLayout jpanelLoginHeaderLayout = new javax.swing.GroupLayout(jpanelLoginHeader);
        jpanelLoginHeader.setLayout(jpanelLoginHeaderLayout);
        jpanelLoginHeaderLayout.setHorizontalGroup(
            jpanelLoginHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLoginHeaderLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jpanelRobotLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jpanelLoginHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblPos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jpanelThispos, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE))
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jpanelLoginHeaderLayout.setVerticalGroup(
            jpanelLoginHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelLoginHeaderLayout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jpanelLoginHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelLoginHeaderLayout.createSequentialGroup()
                        .addComponent(jpanelThispos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)
                        .addComponent(jlblPos))
                    .addComponent(jpanelRobotLogo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpanelLoginHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jsepLogin)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jpanelLoginHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jsepLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jpanelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jsepLogin.getAccessibleContext().setAccessibleName("jsepLogin");

        getAccessibleContext().setAccessibleName("jframeLogin");
        getAccessibleContext().setAccessibleDescription("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnLoginActionPerformed
        
        login();
        
    }//GEN-LAST:event_jbtnLoginActionPerformed

    private void jbtnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnCancelActionPerformed
        
        exit();
        
    }//GEN-LAST:event_jbtnCancelActionPerformed

    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info
                : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException
            | InstantiationException
            | IllegalAccessException
            | javax.swing.UnsupportedLookAndFeelException ex)
        {
            logger.error(ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new LoginJFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnLogin;
    private javax.swing.JLabel jlblPassword;
    private javax.swing.JLabel jlblPos;
    private javax.swing.JLabel jlblRobotLogo;
    private javax.swing.JLabel jlblThispos;
    private javax.swing.JLabel jlblUser;
    private javax.swing.JPanel jpanelLogin;
    private javax.swing.JPanel jpanelLoginHeader;
    private javax.swing.JPanel jpanelRobotLogo;
    private javax.swing.JPanel jpanelThispos;
    private javax.swing.JSeparator jsepLogin;
    private javax.swing.JPasswordField jtxtPassword;
    private javax.swing.JTextField jtxtUser;
    // End of variables declaration//GEN-END:variables
}

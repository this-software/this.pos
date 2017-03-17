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
import dis.software.pos.EntityStatus;
import dis.software.pos.OptionPane;
import dis.software.pos.combobox.model.ProfileComboBoxModel;
import dis.software.pos.combobox.renderers.ProfileComboBoxRenderer;
import dis.software.pos.entities.Profile;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IProfile;
import dis.software.pos.interfaces.IUser;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.plaf.basic.BasicProgressBarUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la creación de usuarios
 * @author Milton Cavazos
 */
public class UserCreateJFrame extends javax.swing.JInternalFrame
{
    
    private static final Logger logger = LogManager.getLogger(UserCreateJFrame.class.getSimpleName());

    /**
     * Creación de nuevo formulario UserCreateJFrame
     */
    public UserCreateJFrame()
    {
        
        initComponents();
        
        UserCreateJFrame frame = this;
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.addInternalFrameListener(new InternalFrameAdapter()
        {
            @Override
            public void internalFrameClosing(InternalFrameEvent e)
            {
                if (!jtxtCode.getText().isEmpty()
                    && !jtxtName.getText().isEmpty()
                    && jtxtPassword.getPassword().length != 0
                    && jcboProfile.getSelectedIndex() != 0)
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
        
        jpBarPasswordStrength.setUI(new GradientPalletProgressBarUI());
        jpBarPasswordStrength.setString("Nivel de seguridad");
        
        IProfile iProfile = Application.getContext().getBean(IProfile.class);
        List<Profile> list = iProfile.findAll();
        list.add(0, new Profile("", "Elige un perfil", ""));
        jcboProfile.setModel(new ProfileComboBoxModel(list));
        jcboProfile.setRenderer(new ProfileComboBoxRenderer());
        
    }

    //<editor-fold defaultstate="collapsed" desc="Método que mide la fuerza de la contraseña">
    private void strength()
    {
        
        try
        {
            jpBarPasswordStrength.setStringPainted(true);
            jpBarPasswordStrength.setValue(jtxtPassword.getText().toLowerCase().length());
            if (jtxtPassword.getText().toLowerCase().length() > 15)
            {
                //this.Cont += 1;
                jtxtPassword.setText("");
                jpBarPasswordStrength.setValue(jtxtPassword.getText().indexOf(""));
            }
            if ((jtxtPassword.getText().toLowerCase().length() >= 10)
                && (jtxtPassword.getText().toLowerCase().length() <= 15))
            {
                jpBarPasswordStrength.setString("Alto");
            }
            if ((jtxtPassword.getText().toLowerCase().length() >= 5)
                && (jtxtPassword.getText().toLowerCase().length() <= 10))
            {
                jpBarPasswordStrength.setString("Medio");
            }
            if ((jtxtPassword.getText().toLowerCase().length() >= 1)
                && (jtxtPassword.getText().toLowerCase().length() <= 5))
            {
                jpBarPasswordStrength.setString("Bajo");
            }
            if (jtxtPassword.getText().equalsIgnoreCase(""))
            {
                jpBarPasswordStrength.setString("Nivel de seguridad");
            }
        }
        catch (Exception e)
        {
            logger.error(e);
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
        jlblCode = new javax.swing.JLabel();
        jtxtCode = new javax.swing.JTextField();
        jchkAutoCode = new javax.swing.JCheckBox();
        jlblName = new javax.swing.JLabel();
        jtxtName = new javax.swing.JTextField();
        jlblEmail = new javax.swing.JLabel();
        jtxtEmail = new javax.swing.JTextField();
        jlblPassword = new javax.swing.JLabel();
        jtxtPassword = new javax.swing.JPasswordField();
        jpanelPasswordStrength = new javax.swing.JPanel();
        jpBarPasswordStrength = new javax.swing.JProgressBar();
        jcboProfile = new javax.swing.JComboBox<>();
        jlblProfile = new javax.swing.JLabel();
        jchkReceiveNotifications = new javax.swing.JCheckBox();
        jlblReceiveNotifications = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Nuevo usuario");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Agregar nuevo usuario");

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

        jlblCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblCode.setText("* Código:");

        jtxtCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jchkAutoCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkAutoCode.setText("Automático");
        jchkAutoCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkAutoCodeActionPerformed(evt);
            }
        });

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

        jpanelPasswordStrength.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        jpBarPasswordStrength.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jpBarPasswordStrength.setMaximum(15);
        jpBarPasswordStrength.setOpaque(false);
        jpBarPasswordStrength.setString("Nivel de seguridad");

        javax.swing.GroupLayout jpanelPasswordStrengthLayout = new javax.swing.GroupLayout(jpanelPasswordStrength);
        jpanelPasswordStrength.setLayout(jpanelPasswordStrengthLayout);
        jpanelPasswordStrengthLayout.setHorizontalGroup(
            jpanelPasswordStrengthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBarPasswordStrength, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jpanelPasswordStrengthLayout.setVerticalGroup(
            jpanelPasswordStrengthLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpBarPasswordStrength, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jcboProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblProfile.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblProfile.setText("Perfil");

        jchkReceiveNotifications.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkReceiveNotifications.setText("Inactivo");
        jchkReceiveNotifications.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkReceiveNotificationsActionPerformed(evt);
            }
        });

        jlblReceiveNotifications.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblReceiveNotifications.setText("Recibir notificaciones:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
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
                                    .addComponent(jlblPassword))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jtxtName)
                                    .addComponent(jtxtCode, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                                    .addComponent(jtxtEmail)
                                    .addComponent(jtxtPassword)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jlblProfile)
                                    .addComponent(jlblReceiveNotifications))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jchkReceiveNotifications)
                                    .addComponent(jcboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jchkAutoCode)
                            .addComponent(jpanelPasswordStrength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 196, Short.MAX_VALUE)))
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
                    .addComponent(jlblEmail)
                    .addComponent(jtxtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblPassword))
                    .addComponent(jpanelPasswordStrength, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcboProfile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblProfile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jchkReceiveNotifications)
                    .addComponent(jlblReceiveNotifications))
                .addGap(0, 250, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jbtnSaveMouseClicked

        if (jtxtCode.getText().isEmpty()
            || jtxtName.getText().isEmpty()
            || jtxtPassword.getPassword().length == 0)
        {
            OptionPane.showMessageDialog(this, "Ingrese los datos marcados con un asterisco para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (jcboProfile.getSelectedIndex() == 0)
        {
            OptionPane.showMessageDialog(this, "Seleccione un perfil para continuar.",
                " Guardar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        IUser iUser = Application.getContext().getBean(IUser.class);
        
        String salt = Crypt.getSalt();
        User user = new User(jtxtEmail.getText(), jtxtName.getText(),
            Crypt.encrypt(new String(jtxtPassword.getPassword()), salt));
        user.setCode(jtxtCode.getText());
        user.setSalt(salt);
        user.setStatus(EntityStatus.ACTIVE);
        user.setReceiveNotifications(jchkReceiveNotifications.isSelected());
        user.setCreatedBy(ApplicationSession.getUser());
        user.setProfile((Profile) jcboProfile.getSelectedItem());
        
        user = iUser.save(user);

        if (user.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha guardado exitosamente.",
                " Guardar registro", OptionPane.SUCCESS_MESSAGE);
            
            jtxtCode.setText("");
            jtxtCode.setEditable(true);
            jtxtCode.setBackground(Color.WHITE);
            jchkAutoCode.setSelected(false);
            jtxtName.setText("");
            jtxtEmail.setText("");
            jtxtPassword.setText("");
            jcboProfile.setSelectedIndex(0);
            jchkReceiveNotifications.setSelected(false);
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
            IUser iUser = Application.getContext().getBean(IUser.class);
            jtxtCode.setText(iUser.getNextCode());
            jtxtCode.setEditable(false);
            jtxtCode.setBackground(new Color(196, 196, 196));
            return;
        }
        jtxtCode.setText("");
        jtxtCode.setEditable(true);
        jtxtCode.setBackground(Color.WHITE);
        
    }//GEN-LAST:event_jchkAutoCodeActionPerformed

    private void jtxtPasswordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPasswordKeyPressed
        
        strength();
        
    }//GEN-LAST:event_jtxtPasswordKeyPressed

    private void jtxtPasswordKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtxtPasswordKeyReleased
        
        strength();
        
    }//GEN-LAST:event_jtxtPasswordKeyReleased

    private void jchkReceiveNotificationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkReceiveNotificationsActionPerformed
        
        jchkReceiveNotifications.setText(
            jchkReceiveNotifications.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkReceiveNotificationsActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnSave;
    private javax.swing.JComboBox<Profile> jcboProfile;
    private javax.swing.JCheckBox jchkAutoCode;
    private javax.swing.JCheckBox jchkReceiveNotifications;
    private javax.swing.JLabel jlblCode;
    private javax.swing.JLabel jlblEmail;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblName;
    private javax.swing.JLabel jlblPassword;
    private javax.swing.JLabel jlblProfile;
    private javax.swing.JLabel jlblReceiveNotifications;
    private javax.swing.JProgressBar jpBarPasswordStrength;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JPanel jpanelPasswordStrength;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTextField jtxtCode;
    private javax.swing.JTextField jtxtEmail;
    private javax.swing.JTextField jtxtName;
    private javax.swing.JPasswordField jtxtPassword;
    // End of variables declaration//GEN-END:variables
}

class GradientPalletProgressBarUI extends BasicProgressBarUI
{

    private final int[] pallet;

    public GradientPalletProgressBarUI()
    {
        super();
        this.pallet = makeGradientPallet();
    }

    private static int[] makeGradientPallet()
    {
        
        BufferedImage image = new BufferedImage(15, 1, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = image.createGraphics();
        Point2D start = new Point2D.Float(0f, 0f);
        Point2D end = new Point2D.Float(14f, 0f);
        float[] dist = {0.0f, 0.5f, 1.0f};
        Color[] colors = {Color.RED, Color.YELLOW, Color.GREEN};
        g2.setPaint(new LinearGradientPaint(start, end, dist, colors));
        g2.fillRect(0, 0, 15, 1);
        g2.dispose();

        int width = image.getWidth(null);
        int[] pallet = new int[width];
        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, 1, pallet, 0, width);
        try
        {
            pg.grabPixels();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return pallet;
        
    }

    private static Color getColorFromPallet(int[] pallet, float x)
    {
        
        if (x < 0.0 || x > 1.0) {
            throw new IllegalArgumentException("Parameter outside of expected range");
        }
        int i = (int) (pallet.length * x);
        int max = pallet.length - 1;
        int index = i < 0 ? 0 : i > max ? max : i;
        int pix = pallet[index] & 0x00ffffff | (0x64 << 24);
        return new Color(pix, true);
        
    }

    @Override
    public void paintDeterminate(Graphics g, JComponent c)
    {
        
        if (!(g instanceof Graphics2D)) {
            return;
        }
        Insets b = progressBar.getInsets(); // area for border
        int barRectWidth = progressBar.getWidth() - (b.right + b.left);
        int barRectHeight = progressBar.getHeight() - (b.top + b.bottom);
        if (barRectWidth <= 0 || barRectHeight <= 0) {
            return;
        }
        int cellLength = getCellLength();
        int cellSpacing = getCellSpacing();
        // amount of progress to draw
        int amountFull = getAmountFull(b, barRectWidth, barRectHeight);

        if (progressBar.getOrientation() == JProgressBar.HORIZONTAL)
        {
            // draw the cells
            float x = amountFull / (float) barRectWidth;
            g.setColor(getColorFromPallet(pallet, x));
            g.fillRect(b.left, b.top, amountFull, barRectHeight);
            
        } else { // VERTICAL
            //...
        }
        // Deal with possible text painting
        if (progressBar.isStringPainted())
        {
            g.setColor(Color.BLACK);
            paintString(g, b.left, b.top, barRectWidth, barRectHeight, amountFull, b);
        }
        
    }
    
}

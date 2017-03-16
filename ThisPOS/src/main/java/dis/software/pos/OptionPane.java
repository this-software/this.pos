/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos;

import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Milton Cavazos
 */
public class OptionPane
{
    
    /** Used for success messages. */
    public static final int SUCCESS_MESSAGE = 4;
    
    public static int showConfirmDialog(Component parentComponent,
        Object message, String title, int optionType, int messageType) throws HeadlessException
    {
        JLabel jlblMsg = new JLabel();
        jlblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jlblMsg.setText((String) message);
        ApplicationSound.info();
        return JOptionPane.showConfirmDialog(parentComponent, jlblMsg, title, optionType,
            messageType, null);
    }
    
    public static void showMessageDialog(Component parentComponent,
        Object message, String title, int messageType) throws HeadlessException
    {
        JLabel jlblMsg = new JLabel();
        jlblMsg.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jlblMsg.setText((String) message);
        switch (messageType)
        {
            case JOptionPane.INFORMATION_MESSAGE :
                ApplicationSound.info();
                break;
            case JOptionPane.ERROR_MESSAGE :
                ApplicationSound.error();
                break;
            case OptionPane.SUCCESS_MESSAGE :
                ApplicationSound.success();
                messageType = JOptionPane.INFORMATION_MESSAGE;
                break;
        }
        JOptionPane.showMessageDialog(parentComponent, jlblMsg, title, messageType);
    }
    
}

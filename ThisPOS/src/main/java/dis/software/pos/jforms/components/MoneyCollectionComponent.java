/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms.components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 * Formulario para el monto de cobro e informar el cambio
 * @author Milton Cavazos
 */
public class MoneyCollectionComponent extends javax.swing.JFrame
{

    private JDialog jdMoneyCollection;
    
    private Double chargedAmount; //Monto total que se cobrará al cliente
    private static Double paidAmount = 0.00; //Monto en efectivo entregado por el cliente
    private static Double refundAmount; //Cambio que se entrega al cliente
    
    /**
     * Creación de nuevo formulario ChargeJFrame
     */
    public MoneyCollectionComponent()
    {
        initComponents();
    }
    
    public MoneyCollectionComponent(JDialog jdMoneyCollection, Double chargedAmount)
    {
        
        initComponents();
        
        this.jdMoneyCollection = jdMoneyCollection;
        this.chargedAmount = chargedAmount;
        
        KeyAdapter keyAdapter = new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    if (!(paidAmount >= chargedAmount))
                    {
                        return;
                    }
                    jdMoneyCollection.dispose();
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_0
                    || e.getKeyCode() == KeyEvent.VK_1
                    || e.getKeyCode() == KeyEvent.VK_2
                    || e.getKeyCode() == KeyEvent.VK_3
                    || e.getKeyCode() == KeyEvent.VK_4
                    || e.getKeyCode() == KeyEvent.VK_5
                    || e.getKeyCode() == KeyEvent.VK_6
                    || e.getKeyCode() == KeyEvent.VK_7
                    || e.getKeyCode() == KeyEvent.VK_8
                    || e.getKeyCode() == KeyEvent.VK_9
                    || e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    paidAmount = ((JTextField) e.getSource()).getText().isEmpty() ? 0.00
                        : new Double(((JTextField) e.getSource()).getText());
                    if (paidAmount >= chargedAmount)
                    {
                        jtxtRefundAmount.setText(String.format("$%,.2f", paidAmount - chargedAmount));
                        refundAmount = paidAmount - BigDecimal.valueOf(chargedAmount)
                            .setScale(2, RoundingMode.HALF_EVEN).doubleValue();
                        return;
                    }
                    jtxtRefundAmount.setText(String.format("$%,.2f", 0.00));
                }
            }
        };
        jtxtPaidAmount.addKeyListener(keyAdapter);
        jtxtRefundAmount.setEditable(false);
        jtxtRefundAmount.setFocusable(false);
        
    }

    /*public static Double getChargedAmount() {
        return chargedAmount;
    }

    public void setChargedAmount(Double chargedAmount) {
        MoneyCollectionComponent.chargedAmount = chargedAmount;
    }/**/

    public static Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        MoneyCollectionComponent.paidAmount = paidAmount;
    }

    public static Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        MoneyCollectionComponent.refundAmount = refundAmount;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanelBody = new javax.swing.JPanel();
        jlblPaidAmount = new javax.swing.JLabel();
        jtxtPaidAmount = new javax.swing.JTextField();
        jlblRefundAmount = new javax.swing.JLabel();
        jtxtRefundAmount = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpanelBody.setBackground(new java.awt.Color(51, 51, 51));

        jlblPaidAmount.setFont(new java.awt.Font("Consolas", 1, 22)); // NOI18N
        jlblPaidAmount.setForeground(new java.awt.Color(255, 255, 255));
        jlblPaidAmount.setText("EFECTIVO");

        jtxtPaidAmount.setFont(new java.awt.Font("Consolas", 0, 26)); // NOI18N
        jtxtPaidAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jlblRefundAmount.setFont(new java.awt.Font("Consolas", 1, 22)); // NOI18N
        jlblRefundAmount.setForeground(new java.awt.Color(255, 255, 255));
        jlblRefundAmount.setText("CAMBIO");

        jtxtRefundAmount.setFont(new java.awt.Font("Consolas", 0, 26)); // NOI18N
        jtxtRefundAmount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jtxtRefundAmount.setText("$0.00");

        javax.swing.GroupLayout jpanelBodyLayout = new javax.swing.GroupLayout(jpanelBody);
        jpanelBody.setLayout(jpanelBodyLayout);
        jpanelBodyLayout.setHorizontalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBodyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblRefundAmount)
                    .addComponent(jlblPaidAmount)
                    .addComponent(jtxtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtxtRefundAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jpanelBodyLayout.setVerticalGroup(
            jpanelBodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpanelBodyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jlblPaidAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtPaidAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblRefundAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtRefundAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jpanelBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jpanelBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlblPaidAmount;
    private javax.swing.JLabel jlblRefundAmount;
    private javax.swing.JPanel jpanelBody;
    private javax.swing.JTextField jtxtPaidAmount;
    private javax.swing.JTextField jtxtRefundAmount;
    // End of variables declaration//GEN-END:variables
}

/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms.components;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.EntityStatus;
import dis.software.pos.TimeUtils;
import dis.software.pos.entities.Notification;
import dis.software.pos.interfaces.INotification;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Formulario para la configuración de notificaciones
 * @author Milton Cavazos
 */
public class NotificationComponent extends javax.swing.JFrame
{
    
    private List<Notification> list;

    /**
     * Creación de nuevo formulario NotificationsJFrame
     */
    public NotificationComponent()
    {
        
        initComponents();
        
        INotification iNotification = Application.getContext().getBean(INotification.class);
        list = iNotification.findAll();
        
        list.stream().forEach(notification ->
        {
            switch (notification.getCode())
            {
                case Notification.INVENTORY_OUTCOME:
                    jchkNotifyInventoryOutcome.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifyInventoryOutcome.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
                case Notification.INVENTORY_CANCEL:
                    jchkNotifyInventoryCancel.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifyInventoryCancel.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
                case Notification.PURCHASE_CANCEL:
                    jchkNotifyPurchaseCancel.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifyPurchaseCancel.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
                case Notification.SALE_CANCEL:
                    jchkNotifySaleCancel.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifySaleCancel.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
                case Notification.PRODUCT_REQUEST:
                    jchkNotifyProductRequest.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifyProductRequest.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
                case Notification.PRODUCT_REQUEST_CANCEL:
                    jchkNotifyProductRequestCancel.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifyProductRequestCancel.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
                case Notification.PRODUCT_MIN_LEVEL:
                    jchkNotifyProductMinimunLevel.setSelected(
                        notification.getStatus().equals(EntityStatus.ACTIVE));
                    if (notification.getStatus().equals(EntityStatus.ACTIVE)
                        && notification.getUpdatedDate() != null)
                    {
                        jchkNotifyProductMinimunLevel.setText("Activo desde "
                            + TimeUtils.getRelativeTime(notification.getUpdatedDate().getTimeInMillis()));
                    }
                    break;
            }
        });
        
    }
    
    /**
     * Método para obtener la lista del panel de notificaciones
     * @return Lista de notificaciones
     */
    public List<Notification> getAll()
    {
        return list;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblNotifyInventoryOutcome = new javax.swing.JLabel();
        jchkNotifyInventoryOutcome = new javax.swing.JCheckBox();
        jlblNotifyInventoryCancel = new javax.swing.JLabel();
        jchkNotifyInventoryCancel = new javax.swing.JCheckBox();
        jlblNotifyPurchaseCancel = new javax.swing.JLabel();
        jchkNotifyPurchaseCancel = new javax.swing.JCheckBox();
        jlblNotifySaleCancel = new javax.swing.JLabel();
        jchkNotifySaleCancel = new javax.swing.JCheckBox();
        jlblNotifyProductRequest = new javax.swing.JLabel();
        jchkNotifyProductRequest = new javax.swing.JCheckBox();
        jlblNotifyProductMinimunLevel = new javax.swing.JLabel();
        jchkNotifyProductMinimunLevel = new javax.swing.JCheckBox();
        jlblNotifyProductRequestCancel = new javax.swing.JLabel();
        jchkNotifyProductRequestCancel = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlblNotifyInventoryOutcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifyInventoryOutcome.setText("Notificar salida de inventario:");

        jchkNotifyInventoryOutcome.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifyInventoryOutcome.setText("Inactivo");
        jchkNotifyInventoryOutcome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifyInventoryOutcomeActionPerformed(evt);
            }
        });

        jlblNotifyInventoryCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifyInventoryCancel.setText("Notificar cancelación de inventario:");

        jchkNotifyInventoryCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifyInventoryCancel.setText("Inactivo");
        jchkNotifyInventoryCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifyInventoryCancelActionPerformed(evt);
            }
        });

        jlblNotifyPurchaseCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifyPurchaseCancel.setText("Notificar cancelación de compra:");

        jchkNotifyPurchaseCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifyPurchaseCancel.setText("Inactivo");
        jchkNotifyPurchaseCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifyPurchaseCancelActionPerformed(evt);
            }
        });

        jlblNotifySaleCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifySaleCancel.setText("Notificar cancelación de venta:");

        jchkNotifySaleCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifySaleCancel.setText("Inactivo");
        jchkNotifySaleCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifySaleCancelActionPerformed(evt);
            }
        });

        jlblNotifyProductRequest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifyProductRequest.setText("Notificar solicitud de productos:");

        jchkNotifyProductRequest.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifyProductRequest.setText("Inactivo");
        jchkNotifyProductRequest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifyProductRequestActionPerformed(evt);
            }
        });

        jlblNotifyProductMinimunLevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifyProductMinimunLevel.setText("Notificar existencia minima:");

        jchkNotifyProductMinimunLevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifyProductMinimunLevel.setText("Inactivo");
        jchkNotifyProductMinimunLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifyProductMinimunLevelActionPerformed(evt);
            }
        });

        jlblNotifyProductRequestCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblNotifyProductRequestCancel.setText("Notificar cancelación de solicitud:");

        jchkNotifyProductRequestCancel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jchkNotifyProductRequestCancel.setText("Inactivo");
        jchkNotifyProductRequestCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jchkNotifyProductRequestCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jlblNotifyProductRequestCancel)
                    .addComponent(jlblNotifySaleCancel)
                    .addComponent(jlblNotifyPurchaseCancel)
                    .addComponent(jlblNotifyProductMinimunLevel)
                    .addComponent(jlblNotifyProductRequest)
                    .addComponent(jlblNotifyInventoryCancel)
                    .addComponent(jlblNotifyInventoryOutcome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchkNotifyInventoryOutcome)
                    .addComponent(jchkNotifyInventoryCancel)
                    .addComponent(jchkNotifyPurchaseCancel)
                    .addComponent(jchkNotifySaleCancel)
                    .addComponent(jchkNotifyProductRequest)
                    .addComponent(jchkNotifyProductMinimunLevel)
                    .addComponent(jchkNotifyProductRequestCancel))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jchkNotifyInventoryOutcome)
                    .addComponent(jlblNotifyInventoryOutcome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNotifyInventoryCancel)
                    .addComponent(jchkNotifyInventoryCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNotifyPurchaseCancel)
                    .addComponent(jchkNotifyPurchaseCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNotifySaleCancel)
                    .addComponent(jchkNotifySaleCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNotifyProductRequest)
                    .addComponent(jchkNotifyProductRequest))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNotifyProductRequestCancel)
                    .addComponent(jchkNotifyProductRequestCancel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jlblNotifyProductMinimunLevel)
                    .addComponent(jchkNotifyProductMinimunLevel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jchkNotifyInventoryOutcomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifyInventoryOutcomeActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.INVENTORY_OUTCOME))
                {
                    x.setStatus(jchkNotifyInventoryOutcome.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifyInventoryOutcome.setText(
            jchkNotifyInventoryOutcome.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifyInventoryOutcomeActionPerformed

    private void jchkNotifyInventoryCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifyInventoryCancelActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.INVENTORY_CANCEL))
                {
                    x.setStatus(jchkNotifyInventoryCancel.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifyInventoryCancel.setText(
            jchkNotifyInventoryCancel.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifyInventoryCancelActionPerformed

    private void jchkNotifyPurchaseCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifyPurchaseCancelActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.PURCHASE_CANCEL))
                {
                    x.setStatus(jchkNotifyPurchaseCancel.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifyPurchaseCancel.setText(
            jchkNotifyPurchaseCancel.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifyPurchaseCancelActionPerformed

    private void jchkNotifySaleCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifySaleCancelActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.SALE_CANCEL))
                {
                    x.setStatus(jchkNotifySaleCancel.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifySaleCancel.setText(
            jchkNotifySaleCancel.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifySaleCancelActionPerformed

    private void jchkNotifyProductRequestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifyProductRequestActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.PRODUCT_REQUEST))
                {
                    x.setStatus(jchkNotifyProductRequest.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifyProductRequest.setText(
            jchkNotifyProductRequest.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifyProductRequestActionPerformed

    private void jchkNotifyProductMinimunLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifyProductMinimunLevelActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.PRODUCT_MIN_LEVEL))
                {
                    x.setStatus(jchkNotifyProductMinimunLevel.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifyProductMinimunLevel.setText(
            jchkNotifyProductMinimunLevel.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifyProductMinimunLevelActionPerformed

    private void jchkNotifyProductRequestCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jchkNotifyProductRequestCancelActionPerformed
        
        list.stream()
            .forEach(x ->
            {
                if (x.getCode().equals(Notification.PRODUCT_REQUEST_CANCEL))
                {
                    x.setStatus(jchkNotifyProductRequestCancel.isSelected()
                        ? EntityStatus.ACTIVE : EntityStatus.INACTIVE);
                    x.setUpdatedBy(ApplicationSession.getUser());
                    x.setUpdatedDate(new GregorianCalendar());
                }
            });
        jchkNotifyProductRequestCancel.setText(
            jchkNotifyProductRequestCancel.isSelected() ? "Activo" : "Inactivo");
        
    }//GEN-LAST:event_jchkNotifyProductRequestCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jchkNotifyInventoryCancel;
    private javax.swing.JCheckBox jchkNotifyInventoryOutcome;
    private javax.swing.JCheckBox jchkNotifyProductMinimunLevel;
    private javax.swing.JCheckBox jchkNotifyProductRequest;
    private javax.swing.JCheckBox jchkNotifyProductRequestCancel;
    private javax.swing.JCheckBox jchkNotifyPurchaseCancel;
    private javax.swing.JCheckBox jchkNotifySaleCancel;
    private javax.swing.JLabel jlblNotifyInventoryCancel;
    private javax.swing.JLabel jlblNotifyInventoryOutcome;
    private javax.swing.JLabel jlblNotifyProductMinimunLevel;
    private javax.swing.JLabel jlblNotifyProductRequest;
    private javax.swing.JLabel jlblNotifyProductRequestCancel;
    private javax.swing.JLabel jlblNotifyPurchaseCancel;
    private javax.swing.JLabel jlblNotifySaleCancel;
    // End of variables declaration//GEN-END:variables
}

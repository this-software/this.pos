/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.EntityStatus;
import dis.software.pos.OptionPane;
import dis.software.pos.TableFilterBy;
import dis.software.pos.combobox.model.TableFilterByComboBoxModel;
import dis.software.pos.combobox.renderers.TableFilterByComboBoxRenderer;
import dis.software.pos.entities.User;
import dis.software.pos.interfaces.IUser;
import dis.software.pos.table.cell.renderer.EntityStatusCellRenderer;
import dis.software.pos.table.model.UserTableModel;
import java.awt.Color;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formulario para la visualización de usuarios
 * @author Milton Cavazos
 */
public class UserViewJFrame extends javax.swing.JInternalFrame
{
    private static final Logger logger = LogManager.getLogger(UserViewJFrame.class.getSimpleName());
    
    private TableRowSorter<UserTableModel> sorter;

    /**
     * Creación de nuevo formulario UserViewJFrame
     */
    public UserViewJFrame()
    {
        
        initComponents();
        
        IUser iUser = Application.getContext().getBean(IUser.class);
        UserTableModel userTableModel = new UserTableModel(iUser.findAll());
        
        jtableUsers.setModel(userTableModel);
        jtableUsers.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableUsers.setShowHorizontalLines(Boolean.TRUE);
        jtableUsers.setGridColor(new Color(179, 179, 179));
        
        sorter = new TableRowSorter<>(userTableModel);
        jtableUsers.setRowSorter(sorter);
        
        jtableUsers.setDefaultRenderer(EntityStatus.class, new EntityStatusCellRenderer());
        //Se eliminan columnas innecesarias para esta vista
        jtableUsers.getColumnModel().removeColumn(jtableUsers.getColumnModel().getColumn(
            jtableUsers.convertColumnIndexToView(UserTableModel.COLUMN_ID)));
        
        jtableUsers.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        jtxtFilter.getDocument().addDocumentListener(
            new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
        });
        
        List<TableFilterBy> list = new ArrayList<>();
        list.add(new TableFilterBy(UserTableModel.COLUMN_CODE, "Código"));
        list.add(new TableFilterBy(UserTableModel.COLUMN_NAME, "Usuario"));
        list.add(new TableFilterBy(UserTableModel.COLUMN_EMAIL, "Correo"));
        list.add(new TableFilterBy(UserTableModel.COLUMN_PROFILE_NAME, "Perfil"));
        jcboFilterBy.setModel(new TableFilterByComboBoxModel(list));
        jcboFilterBy.setRenderer(new TableFilterByComboBoxRenderer());
        
        jtxtTotalRows.setText(Integer.toString(((UserTableModel) jtableUsers.getModel()).getRowCount()));
        
    }
    
    private void filter()
    {
        
        RowFilter<UserTableModel, Object> descRowFilter = null;
        try
        {
            descRowFilter = RowFilter.regexFilter("(?i)" + jtxtFilter.getText() + "(?-i)",
                ((TableFilterByComboBoxModel) jcboFilterBy.getModel()).getSelectedItem().getTableColumnIndex());
        }
        catch (PatternSyntaxException e)
        {
            return;
        }
        sorter.setRowFilter(descRowFilter);
        
    }
    
    //<editor-fold defaultstate="collapsed" desc="Método para modificar un registro">
    private void edit()
    {
        
        UserTableModel userTableModel = (UserTableModel) jtableUsers.getModel();
        
        if (jtableUsers.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Modificar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        User user = userTableModel.get(
            jtableUsers.convertRowIndexToModel(jtableUsers.getSelectedRow()));

        UserEditJFrame userEditJFrame = new UserEditJFrame(user);
        getDesktopPane().add(userEditJFrame);
        userEditJFrame.pack();
        try
        {
            userEditJFrame.setMaximum(true);
        }
        catch (PropertyVetoException ex)
        {
            //logger.error("Error setting maximum", ex);
        }
        userEditJFrame.setVisible(true);
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para inactivar un registro">
    private void delete()
    {
        
        UserTableModel userTableModel = (UserTableModel) jtableUsers.getModel();
        
        if (jtableUsers.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere eliminar el registro?",
            " Eliminar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IUser iUser = Application.getContext().getBean(IUser.class);
        
        User user = iUser.findById(userTableModel.get(
            jtableUsers.convertRowIndexToModel(jtableUsers.getSelectedRow())).getId());
        user.setStatus(EntityStatus.INACTIVE);
        user.setUpdatedBy(ApplicationSession.getUser());
        user.setUpdatedDate(new GregorianCalendar());

        iUser.update(user);

        if (user.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha eliminado exitosamente.",
                " Eliminar registro", OptionPane.SUCCESS_MESSAGE);
            
            userTableModel.remove(jtableUsers.convertRowIndexToModel(jtableUsers.getSelectedRow()));
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar eliminar el registro.",
                " Eliminar registro", JOptionPane.ERROR_MESSAGE);
        }
        
        jtxtTotalRows.setText(Integer.toString(userTableModel.getRowCount()));
        
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
        jbtnEdit = new javax.swing.JButton();
        jbtnDelete = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableUsers = new javax.swing.JTable();
        jlblFilterBy = new javax.swing.JLabel();
        jcboFilterBy = new javax.swing.JComboBox<>();
        jlblTextToFilter = new javax.swing.JLabel();
        jtxtFilter = new javax.swing.JTextField();
        jlblTotalRows = new javax.swing.JLabel();
        jtxtTotalRows = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Ver usuarios");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Listado de usuarios");

        jbtnEdit.setBackground(new java.awt.Color(17, 157, 17));
        jbtnEdit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnEdit.setForeground(new java.awt.Color(255, 255, 255));
        jbtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit-w.png"))); // NOI18N
        jbtnEdit.setText("Modificar");
        jbtnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnEdit.setIconTextGap(8);
        jbtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnEditActionPerformed(evt);
            }
        });

        jbtnDelete.setBackground(new java.awt.Color(157, 16, 16));
        jbtnDelete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnDelete.setForeground(new java.awt.Color(255, 255, 255));
        jbtnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/bin-w.png"))); // NOI18N
        jbtnDelete.setText("Eliminar");
        jbtnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnDelete.setIconTextGap(8);
        jbtnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnDeleteActionPerformed(evt);
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
                .addComponent(jbtnEdit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDelete)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jtableUsers.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableUsers.setRowHeight(25);
        jtableUsers.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableUsers);

        jlblFilterBy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblFilterBy.setText("Buscar por:");

        jlblTextToFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblTextToFilter.setText("Texto a buscar:");

        jtxtFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jlblTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jlblTotalRows.setText("Total de registros:");

        jtxtTotalRows.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jtxtTotalRows.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jsepHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                    .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jlblTotalRows)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtxtTotalRows)
                        .addGap(0, 624, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jlblFilterBy)
                .addGap(8, 8, 8)
                .addComponent(jcboFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlblTextToFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jtxtFilter, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jcboFilterBy, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jlblTextToFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jlblFilterBy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jlblTotalRows)
                    .addComponent(jtxtTotalRows))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnEditActionPerformed

        edit();

    }//GEN-LAST:event_jbtnEditActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed

        delete();

    }//GEN-LAST:event_jbtnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnEdit;
    private javax.swing.JComboBox<TableFilterBy> jcboFilterBy;
    private javax.swing.JLabel jlblFilterBy;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblTextToFilter;
    private javax.swing.JLabel jlblTotalRows;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableUsers;
    private javax.swing.JTextField jtxtFilter;
    private javax.swing.JLabel jtxtTotalRows;
    // End of variables declaration//GEN-END:variables
}

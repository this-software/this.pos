/*
 * Copyright (C) 2017 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms;

import dis.software.pos.Application;
import dis.software.pos.ApplicationSession;
import dis.software.pos.OptionPane;
import dis.software.pos.TableFilterBy;
import dis.software.pos.combobox.model.TableFilterByComboBoxModel;
import dis.software.pos.combobox.renderers.TableFilterByComboBoxRenderer;
import dis.software.pos.entities.Provider;
import dis.software.pos.interfaces.IProvider;
import dis.software.pos.table.model.ProviderTableModel;
import java.awt.Color;
import java.awt.Font;
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

/**
 * Formulario para la visualización de proveedores eliminados
 * @author Milton Cavazos
 */
public class ProviderBinJFrame extends javax.swing.JInternalFrame
{

    private final TableRowSorter<ProviderTableModel> sorter;
    
    /**
     * Creación de nuevo formulario ProviderBinJFrame
     */
    public ProviderBinJFrame()
    {
        
        initComponents();
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        ProviderTableModel providerTableModel = new ProviderTableModel(iProvider.getDeleted());
        
        jtableProviders.setModel(providerTableModel);
        jtableProviders.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        //Se habilitan las lineas horizontales para distinguir los registros
        jtableProviders.setShowHorizontalLines(Boolean.TRUE);
        jtableProviders.setGridColor(new Color(179, 179, 179));
        
        sorter = new TableRowSorter<>(providerTableModel);
        jtableProviders.setRowSorter(sorter);
        
        //Se eliminan columnas innecesarias para esta vista
        jtableProviders.getColumnModel().removeColumn(jtableProviders.getColumnModel().getColumn(
            jtableProviders.convertColumnIndexToView(ProviderTableModel.COLUMN_ID)));
        
        jtableProviders.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
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
        list.add(new TableFilterBy(ProviderTableModel.COLUMN_CODE, "Código"));
        list.add(new TableFilterBy(ProviderTableModel.COLUMN_NAME, "Nombre"));
        list.add(new TableFilterBy(ProviderTableModel.COLUMN_DESC, "Descripción"));
        list.add(new TableFilterBy(ProviderTableModel.COLUMN_ADDRESS, "Dirección"));
        list.add(new TableFilterBy(ProviderTableModel.COLUMN_PHONE, "Teléfono"));
        jcboFilterBy.setModel(new TableFilterByComboBoxModel(list));
        jcboFilterBy.setRenderer(new TableFilterByComboBoxRenderer());
        
        jtxtTotalRows.setText(Integer.toString(((ProviderTableModel) jtableProviders.getModel()).getRowCount()));
        
    }
    
    private void filter()
    {
        
        RowFilter<ProviderTableModel, Object> descRowFilter = null;
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
    
    //<editor-fold defaultstate="collapsed" desc="Método para restaurar un registro">
    private void restore()
    {
        
        ProviderTableModel providerTableModel = (ProviderTableModel) jtableProviders.getModel();
        
        if (jtableProviders.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Restaurar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que quiere restaurar el registro?",
            " Restaurar registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE)
            == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        
        Provider provider = iProvider.findById(providerTableModel.get(
            jtableProviders.convertRowIndexToModel(jtableProviders.getSelectedRow())).getId());
        provider.setDeleted(Boolean.FALSE);
        provider.setUpdatedBy(ApplicationSession.getUser());
        provider.setUpdatedDate(new GregorianCalendar());

        iProvider.update(provider);

        if (provider.getId() != null)
        {
            OptionPane.showMessageDialog(this, "El registro se ha restaurado exitosamente.",
                " Restaurar registro", OptionPane.SUCCESS_MESSAGE);
            
            providerTableModel.remove(
                jtableProviders.convertRowIndexToModel(jtableProviders.getSelectedRow()));
        }
        else
        {
            OptionPane.showMessageDialog(this, "Ha ocurrido un error al intentar restaurar el registro.",
                " Restaurar registro", JOptionPane.ERROR_MESSAGE);
        }
        
        jtxtTotalRows.setText(Integer.toString(providerTableModel.getRowCount()));
        
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Método para eliminar un registro">
    private void delete()
    {
        
        ProviderTableModel providerTableModel = (ProviderTableModel) jtableProviders.getModel();
        
        if (jtableProviders.getSelectedRow() == -1)
        {
            OptionPane.showMessageDialog(this, "Seleccione un registro por favor.",
                " Eliminar registro", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (OptionPane.showConfirmDialog(this, "¿Está seguro de que desea eliminar el registro "
            + "de forma permanente?", " Eliminar registro", JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION)
        {
            return;
        }
        
        IProvider iProvider = Application.getContext().getBean(IProvider.class);
        
        Provider provider = iProvider.findById(providerTableModel.get(
            jtableProviders.convertRowIndexToModel(jtableProviders.getSelectedRow())).getId());

        iProvider.delete(provider);
        
        providerTableModel.remove(
            jtableProviders.convertRowIndexToModel(jtableProviders.getSelectedRow()));
        
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
        jbtnRestore = new javax.swing.JButton();
        jbtnDelete = new javax.swing.JButton();
        jsepHeader = new javax.swing.JSeparator();
        jlblFilterBy = new javax.swing.JLabel();
        jcboFilterBy = new javax.swing.JComboBox<>();
        jlblTextToFilter = new javax.swing.JLabel();
        jtxtFilter = new javax.swing.JTextField();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableProviders = new javax.swing.JTable();
        jlblTotalRows = new javax.swing.JLabel();
        jtxtTotalRows = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Papelera de proveedores");

        jlblHeader.setFont(new java.awt.Font("Segoe UI", 1, 26)); // NOI18N
        jlblHeader.setForeground(new java.awt.Color(102, 102, 102));
        jlblHeader.setText("Listado de proveedores eliminados");

        jbtnRestore.setBackground(new java.awt.Color(17, 157, 17));
        jbtnRestore.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jbtnRestore.setForeground(new java.awt.Color(255, 255, 255));
        jbtnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new-window-alt-w.png"))); // NOI18N
        jbtnRestore.setText("Restaurar");
        jbtnRestore.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jbtnRestore.setIconTextGap(8);
        jbtnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnRestoreActionPerformed(evt);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addComponent(jbtnRestore)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtnDelete)
                .addContainerGap())
        );
        jpanelHeaderLayout.setVerticalGroup(
            jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpanelHeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jlblHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jbtnRestore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jsepHeader.setBackground(new java.awt.Color(0, 0, 0));
        jsepHeader.setForeground(new java.awt.Color(0, 0, 0));
        jsepHeader.setOpaque(true);

        jlblFilterBy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblFilterBy.setText("Buscar por:");

        jlblTextToFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblTextToFilter.setText("Texto a buscar:");

        jtxtFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtableProviders.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jtableProviders.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableProviders.setRowHeight(25);
        jtableProviders.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableProviders);

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
                    .addComponent(jscrollPaneTable)
                    .addComponent(jsepHeader)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlblFilterBy)
                                .addGap(8, 8, 8)
                                .addComponent(jcboFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jlblTextToFilter)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jlblTotalRows)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtxtTotalRows)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jpanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsepHeader, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jlblTextToFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jlblFilterBy, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcboFilterBy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void jbtnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnRestoreActionPerformed

        restore();
        
    }//GEN-LAST:event_jbtnRestoreActionPerformed

    private void jbtnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnDeleteActionPerformed

        delete();
        
    }//GEN-LAST:event_jbtnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jbtnDelete;
    private javax.swing.JButton jbtnRestore;
    private javax.swing.JComboBox<TableFilterBy> jcboFilterBy;
    private javax.swing.JLabel jlblFilterBy;
    private javax.swing.JLabel jlblHeader;
    private javax.swing.JLabel jlblTextToFilter;
    private javax.swing.JLabel jlblTotalRows;
    private javax.swing.JPanel jpanelHeader;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JSeparator jsepHeader;
    private javax.swing.JTable jtableProviders;
    private javax.swing.JTextField jtxtFilter;
    private javax.swing.JLabel jtxtTotalRows;
    // End of variables declaration//GEN-END:variables
}

/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */
package dis.software.pos.jforms.components;

import dis.software.pos.Application;
import dis.software.pos.entities.Product;
import dis.software.pos.interfaces.IProduct;
import dis.software.pos.table.model.ProductTableModel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.regex.PatternSyntaxException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 * Formulario para la búsqueda de productos
 * @author Milton Cavazos
 */
public class SearchProductComponent extends javax.swing.JFrame
{
    
    private JDialog jdProducts;
    private Product product;
    private TableRowSorter<ProductTableModel> sorter;

    /**
     * Creación de nuevo formulario ProductListJFrame
     */
    public SearchProductComponent()
    {
        
        initComponents();
        
    }
    
    public SearchProductComponent(JDialog jDialog)
    {
        
        initComponents();
        jdProducts = jDialog;
        
        IProduct iProduct = Application.getContext().getBean(IProduct.class);
        ProductTableModel productTableModel = new ProductTableModel(iProduct.findAll());
        
        sorter = new TableRowSorter<>(productTableModel);
        jtableProducts.setRowSorter(sorter);
        
        jtableProducts.setModel(productTableModel);
        jtableProducts.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        jtableProducts.setShowHorizontalLines(Boolean.TRUE);
        jtableProducts.setGridColor(new Color(179, 179, 179));
        
        //Se eliminan columnas innecesarias para esta vista
        jtableProducts.getColumnModel().removeColumn(jtableProducts.getColumnModel().getColumn(
            jtableProducts.convertColumnIndexToView(ProductTableModel.COLUMN_ID)));
        jtableProducts.getColumnModel().removeColumn(jtableProducts.getColumnModel().getColumn(
            jtableProducts.convertColumnIndexToView(ProductTableModel.COLUMN_PRICE)));
        jtableProducts.getColumnModel().removeColumn(jtableProducts.getColumnModel().getColumn(
            jtableProducts.convertColumnIndexToView(ProductTableModel.COLUMN_COST)));
        jtableProducts.getColumnModel().removeColumn(jtableProducts.getColumnModel().getColumn(
            jtableProducts.convertColumnIndexToView(ProductTableModel.COLUMN_CATEGORY)));
        jtableProducts.getColumnModel().removeColumn(jtableProducts.getColumnModel().getColumn(
            jtableProducts.convertColumnIndexToView(ProductTableModel.COLUMN_PROVIDER)));
        
        jtableProducts.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        jtableProducts.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "ENTER");
        jtableProducts.getActionMap().put("ENTER", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (jtableProducts.getSelectedRow() == -1)
                {
                    return;
                }
                setProduct(((ProductTableModel) jtableProducts.getModel()).get(
                    jtableProducts.convertRowIndexToModel(jtableProducts.getSelectedRow())));
                jdProducts.dispose();
            }
        });
        
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
        jtxtFilter.getInputMap(JComponent.WHEN_FOCUSED)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "TAB");
        jtxtFilter.getActionMap().put("TAB", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (jtxtFilter.hasFocus()) jtableProducts.grabFocus();
            }
        });
        
    }
    
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    
    private void filter()
    {
        
        RowFilter<ProductTableModel, Object> descRowFilter = null;
        try
        {
            descRowFilter = RowFilter.regexFilter(
                "(?i)" + jtxtFilter.getText() + "(?-i)", ProductTableModel.COLUMN_NAME);
        }
        catch (PatternSyntaxException e)
        {
            return;
        }
        sorter.setRowFilter(descRowFilter);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jlblSearch = new javax.swing.JLabel();
        jtxtFilter = new javax.swing.JTextField();
        jscrollPaneTable = new javax.swing.JScrollPane();
        jtableProducts = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlblSearch.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jlblSearch.setText("Texto a buscar:");

        jtxtFilter.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jtableProducts.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        jtableProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtableProducts.setRowHeight(25);
        jtableProducts.getTableHeader().setReorderingAllowed(false);
        jscrollPaneTable.setViewportView(jtableProducts);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jlblSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jlblSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jscrollPaneTable, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jlblSearch;
    private javax.swing.JScrollPane jscrollPaneTable;
    private javax.swing.JTable jtableProducts;
    private javax.swing.JTextField jtxtFilter;
    // End of variables declaration//GEN-END:variables

}

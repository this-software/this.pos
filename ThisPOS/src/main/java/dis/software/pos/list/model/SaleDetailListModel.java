/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.list.model;

import dis.software.pos.entities.PromotionProduct;
import dis.software.pos.entities.SaleDetail;
import dis.software.pos.entities.SalePromotionDetail;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Milton Cavazos
 */
public class SaleDetailListModel extends AbstractListModel<Object>
{
    
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String CONTENTS_CHANGED = "contents_changed";
    
    private List<Object> list = new ArrayList<>();
    private PropertyChangeSupport propertyChangeSupport;
    
    private Double totalAmount = 0.00; //Monto total a pagar
    private Double cost = 0.00; //Monto del costo de venta
    private Double totalDiscount = 0.00; //Monto total de los descuentos aplicados a la venta
    private Double paidAmount = 0.00; //Monto en efectivo entregado por el cliente
    private Double refundAmount = 0.00; //Cambio que se entrega al cliente
    private Integer totalQuantity = 0;
    private int width = 750;
    
    public SaleDetailListModel()
    {
        list.add(0, new Object());
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public SaleDetailListModel(List<Object> list)
    {
        this.list = list;
        this.list.add(0, new Object());
        propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public PropertyChangeSupport getPropertyChangeSupport()
    {
        return propertyChangeSupport;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
    
    @Override
    public int getSize()
    {
        return list.size();
    }

    @Override
    public Object getElementAt(int index)
    {
        Object object = list.get(index);
        
        if (index == 0)
        {
            StringBuilder header = new StringBuilder();
            header.append("<html>")
                .append("<table style='width:").append(width).append("';>")
                    .append("<tr>")
                        .append("<th style='text-align:left;width:15%'>Código</th>")
                        .append("<th style='text-align:left;width:40%'>Descripción</th>")
                        .append("<th style='text-align:right;width:15%'>Cantidad</th>")
                        .append("<th style='text-align:right;width:15%'>Precio</th>")
                        .append("<th style='text-align:right;width:15%'>Importe</th>")
                    .append("</tr>")
                .append("</table>")
                .append("<html>");
            return header.toString();
        }
        
        StringBuilder row = new StringBuilder();
        row.append("<html>")
            .append("<table style='border-top:2px solid #b3b3b3;width:").append(width).append(";'");
        
        if (object instanceof SaleDetail)
        {
            SaleDetail saleDetail = (SaleDetail) object;
            
            row.append("<tr>")
                    .append("<td style='text-align:left;width:15%'>")
                        .append(saleDetail.getProduct().getCode())
                    .append("</td>")
                    .append("<td style='text-align:left;width:40%'>")
                        .append(saleDetail.getProduct().getDescription().isEmpty()
                            ? saleDetail.getProduct().getName() : saleDetail.getProduct().getDescription())
                    .append("</td>")
                    .append("<td style='text-align:right;width:15%'>")
                        .append(saleDetail.getQuantity())
                    .append("</td>")
                    .append("<td style='text-align:right;width:15%'>")
                        .append(String.format("$%1$,.2f", saleDetail.getPrice()))
                    .append("</td>")
                    .append("<td style='text-align:right;width:15%'>")
                        .append(String.format("$%1$,.2f", //Se suma descuento restado previamente del monto
                            saleDetail.getAmount() + saleDetail.getTotalDiscount()))
                    .append("</td>")
                .append("</tr>");

            if (saleDetail.getDiscount() > 0.00)
            {
                row.append("<tr style='background-color:#ffb3b3;color:#e60000;'>")
                        .append("<td style='text-align:left;width:15%'></td>")
                        .append("<td style='text-align:left;width:40%'>")
                            .append("DESCUENTO: ").append(saleDetail.getProduct().getDescription().isEmpty()
                                ? saleDetail.getProduct().getName() : saleDetail.getProduct().getDescription())
                        .append("</td>")
                        .append("<td style='text-align:right;width:15%'>")
                            .append(saleDetail.getQuantity())
                        .append("</td>")
                        .append("<td style='text-align:right;width:15%'>")
                            .append(String.format("$-%1$,.2f", saleDetail.getDiscount()))
                        .append("</td>")
                        .append("<td style='text-align:right;width:15%'>")
                        .append(String.format("$-%1$,.2f",
                            saleDetail.getQuantity() * saleDetail.getDiscount()))
                        .append("</td>")
                    .append("</tr>");
            }
        }
        
        if (object instanceof SalePromotionDetail)
        {
            SalePromotionDetail salePromoDetail = (SalePromotionDetail) object;
            
            row.append("<tr style='background-color:#ccff99;'>")
                    .append("<td style='text-align:left;width:15%'>")
                        .append(salePromoDetail.getPromotion().getCode())
                    .append("</td>")
                    .append("<td style='text-align:left;width:40%'>")
                        .append(salePromoDetail.getPromotion().getDescription().isEmpty()
                            ? salePromoDetail.getPromotion().getName() : salePromoDetail.getPromotion().getDescription())
                    .append("</td>")
                    .append("<td style='text-align:right;width:15%'>")
                        .append(salePromoDetail.getQuantity())
                    .append("</td>")
                    .append("<td style='text-align:right;width:15%'>")
                        .append(String.format("$%1$,.2f", salePromoDetail.getPromotion().getPrice()))
                    .append("</td>")
                    .append("<td style='text-align:right;width:15%'>")
                        .append(String.format("$%1$,.2f", salePromoDetail.getAmount()))
                    .append("</td>")
                .append("</tr>");
            
            List<PromotionProduct> promoProducts =
                new ArrayList<>(salePromoDetail.getPromotion().getPromotionProducts());
            promoProducts.forEach(promoProduct ->
            {
                row.append("<tr style='background-color:#ccff99;color:green;'>")
                        .append("<td style='width:15%'></td>")
                        .append("<td style='text-align:left;width:40%'>")
                            .append(promoProduct.getProduct().getDescription().isEmpty()
                                ? promoProduct.getProduct().getName() : promoProduct.getProduct().getDescription())
                        .append("</td>")
                        .append("<td style='text-align:right;width:15%'>")
                            .append("-")
                        .append("</td>")
                        .append("<td style='text-align:right;width:15%'>")
                            .append("-")
                        .append("</td>")
                        .append("<td style='text-align:right;width:15%'>")
                            .append("-")
                        .append("</td>")
                    .append("</tr>");
            });
        }
        
        if (index >= getSize() - 1
            && paidAmount > 0 && refundAmount >= 0)
        {
            row.append("<tr style='border-top:4px dotted black;'>")
                    .append("<td colspan='3'></td>")
                    .append("<td style='text-align:right;'>EFECTIVO: </td>")
                    .append("<td style='text-align:right;'>")
                        .append(String.format("$%1$,.2f", paidAmount))
                    .append("</td>")
                .append("</tr>")
                .append("<tr>")
                    .append("<td colspan='3'></td>")
                    .append("<td style='text-align:right;'>CAMBIO: </td>")
                    .append("<td style='text-align:right;'>")
                        .append(String.format("$%1$,.2f", refundAmount))
                    .append("</td>")
                .append("</tr>");
        }
        
        row.append("</table>")
        .append("</html>");
        
        return row.toString();
    }
    
    public Double getCost() {
        return cost;
    }
    
    public Double getTotalAmount() {
        return totalAmount;
    }
    
    public Double getSubtotal() {
        return (totalAmount * 0.84);
    }
    
    public Double getTax() {
        return (totalAmount * 0.16);
    }
    
    public Double getTotalDiscount() {
        return totalDiscount;
    }
    
    public Integer getTotalQuantity() {
        return totalQuantity;
    }
    
    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }
    
    public Object get(int index)
    {
        return list.get(index);
    }
    
    public List<Object> getAll()
    {
        return list;
    }
    
    public void add(Object object)
    {
        
        SaleDetail saleDetail;
        SalePromotionDetail salePromotionDetail;
        
        if (object instanceof SaleDetail)
        {
            saleDetail = (SaleDetail) object;
            //Actualización del descuento total acumulado, monto de la venta, costo total acumulado
            //y cantidad total de artículos
            totalDiscount = totalDiscount + saleDetail.getTotalDiscount();
            totalAmount = totalAmount + saleDetail.getAmount();
            cost = cost + saleDetail.getTotalCost();
            totalQuantity = totalQuantity + saleDetail.getQuantity();
        }
        
        if (object instanceof SalePromotionDetail)
        {
            salePromotionDetail = (SalePromotionDetail) object;
            //Actualización del monto de la venta, costo total acumulado
            //y cantidad total de artículos
            totalAmount = totalAmount + salePromotionDetail.getAmount();
            cost = cost + salePromotionDetail.getTotalCost();
            totalQuantity = totalQuantity + salePromotionDetail.getQuantity();
        }
        
        list.add(object); //Se agrega objeto al detalle de venta
        
        this.fireIntervalAdded(this, getSize(), getSize() + 1);
        getPropertyChangeSupport().firePropertyChange(ADD, null, object);
        
    }
    
    public void remove(int index)
    {
        
        SaleDetail saleDetail;
        SalePromotionDetail salePromotionDetail;
        
        if (list.get(index) instanceof SaleDetail)
        {
            saleDetail = (SaleDetail) list.get(index);
            //Actualización del descuento total acumulado, monto de la venta, costo total acumulado
            //y cantidad total de artículos
            totalDiscount = totalDiscount - saleDetail.getTotalDiscount();
            totalAmount = totalAmount - saleDetail.getAmount();
            cost = cost - saleDetail.getTotalCost();
            totalQuantity = totalQuantity - saleDetail.getQuantity();
        }
        
        if (list.get(index) instanceof SalePromotionDetail)
        {
            salePromotionDetail = (SalePromotionDetail) list.get(index);
            //Actualización del monto de la venta, costo total acumulado
            //y cantidad total de artículos
            totalAmount = totalAmount - salePromotionDetail.getAmount();
            cost = cost - salePromotionDetail.getTotalCost();
            totalQuantity = totalQuantity - salePromotionDetail.getQuantity();
        }
        
        Object removed = list.get(index); //Detalle de venta eliminado
        list.remove(index); //Se elimina el detalle de venta seleccionado
        
        if (getSize() == 1)
        {
            totalDiscount = 0.00;
            totalAmount = 0.00;
            cost = 0.00;
            paidAmount = 0.00;
            refundAmount = 0.00;
            totalQuantity = 0;
        }
        
        this.fireIntervalRemoved(this, getSize(), getSize() + 1);
        getPropertyChangeSupport().firePropertyChange(REMOVE, removed, null);
        
    }
    
    public void removeAll()
    {
        
        totalDiscount = 0.00;
        totalAmount = 0.00;
        cost = 0.00;
        paidAmount = 0.00;
        refundAmount = 0.00;
        totalQuantity = 0;
        
        List<Object> removedList = new ArrayList<>(list);
        
        list.removeAll(list);
        list.add(0, new Object());
        
        this.fireIntervalRemoved(this, getSize(), getSize() + 1);
        getPropertyChangeSupport().firePropertyChange(REMOVE, removedList, null);
        
    }
    
    public void fireContentsChanged()
    {
        
        super.fireContentsChanged(this, getSize(), getSize() + 1);
        getPropertyChangeSupport().firePropertyChange(CONTENTS_CHANGED, null, null);
        
    }
    
}

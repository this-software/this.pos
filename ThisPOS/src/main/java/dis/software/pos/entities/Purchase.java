/*
 * Copyright (C) 2016 Milton Cavazos
 *
 * Este programa es de código libre; usted podrá instalar y/o
 * utilizar una copia del programa de computación en una computadora compatible,
 * limitándose al número permitido de computadoras.
 */

package dis.software.pos.entities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que contiene los campos de una compra
 * @author Milton Cavazos
 */
@Entity
public class Purchase implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(nullable = false)
    private Double subtotal;
    
    @Column(nullable = false)
    private Double tax;
    
    @Column(nullable = false)
    private Double cost;
    
    @Column(name = "paid_amount", nullable = false)
    private Double paidAmount;
    
    @Column(length = 250)
    private String observations;
    
    @Column(nullable = false)
    private Integer status;
    
    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    @Column(name = "created_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar createdDate = new GregorianCalendar();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private User updatedBy;
    
    @Column(name = "updated_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar updatedDate;
    
    @OneToMany(mappedBy = "purchaseDetailPk.purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PurchaseDetail> purchaseDetails = new HashSet<>();

    public Purchase() {}
    
    public Purchase(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param subtotal Subtotal de la compra
     * @param tax Impuesto de la compra
     * @param cost Costo de la compra según costos registrados por producto
     * @param paidAmount Monto real pagado por la compra
     */
    public Purchase(Double subtotal, Double tax, Double cost, Double paidAmount)
    {
        this.subtotal = subtotal;
        this.tax = tax;
        this.cost = cost;
        this.paidAmount = paidAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Set<PurchaseDetail> getPurchaseDetails() {
        return purchaseDetails;
    }

    public void setPurchaseDetails(Set<PurchaseDetail> purchaseDetails) {
        this.purchaseDetails = purchaseDetails;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Purchase that = (Purchase) o;
        return !(this.id != null ? !this.id.equals(that.getId())
            : that.getId() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public String toString() {
        return "Purchase: " + this.id + ", " + this.subtotal + ", " + this.tax + ", " + this.cost;
    }

}

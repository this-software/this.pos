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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Clase que contiene los campos de un producto
 * @author Milton Cavazos
 */
@Entity
public class Product implements Serializable
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(unique = true, length = 50)
    private String code;
    
    @Column(unique = true, length = 50)
    private String name;
    
    @Column
    private String description;
    
    @Column
    private Double cost;
    
    @Column
    private Double price;
    
    @Column(name = "out_of_time_price")
    private Double outOfTimePrice;
    
    @Column
    private Double discount;
    
    @Column(name = "quantity_by_unit")
    private Integer quantityByUnit;
    
    @Column(name = "units_in_stock")
    private Integer unitsInStock;
    
    @Column(name = "min_stock_level")
    private Integer minStockLevel;
    
    @Column(name = "min_stock_level_notified")
    private Boolean minStockLevelNotified;
    
    @Column(name = "image_path")
    private String imagePath;
    
    @Column
    private Boolean deleted;
    
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
    
    @Column(name = "discount_expiration_date")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Calendar discountExpirationDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;
    
    @OneToMany(mappedBy = "productUnitPk.product", fetch = FetchType.LAZY)
    private Set<ProductUnit> productUnits = new HashSet<>();
    
    public Product() {}
    
    public Product(Long id)
    {
        this.id = id;
    }
    
    /**
     * Método constructor de la clase
     * @param code Código del módulo
     * @param name Nombre del módulo
     * @param description Descripción del módulo
     */
    public Product(String code, String name, String description)
    {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOutOfTimePrice() {
        return outOfTimePrice;
    }

    public void setOutOfTimePrice(Double outOfTimePrice) {
        this.outOfTimePrice = outOfTimePrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantityByUnit() {
        return quantityByUnit;
    }

    public void setQuantityByUnit(Integer quantityByUnit) {
        this.quantityByUnit = quantityByUnit;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(Integer unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Integer getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(Integer minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public Boolean getMinStockLevelNotified() {
        return minStockLevelNotified;
    }

    public void setMinStockLevelNotified(Boolean minStockLevelNotified) {
        this.minStockLevelNotified = minStockLevelNotified;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public Calendar getDiscountExpirationDate() {
        return discountExpirationDate;
    }

    public void setDiscountExpirationDate(Calendar discountExpirationDate) {
        this.discountExpirationDate = discountExpirationDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Set<ProductUnit> getProductUnits() {
        return productUnits;
    }

    public void setProductUnits(Set<ProductUnit> productUnits) {
        this.productUnits = productUnits;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Product that = (Product) o;
        return !(this.id != null ? !this.id.equals(that.getId())
            : that.getId() != null);
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    @Override
    public String toString() {
        return "Product: " + this.id + ", " + this.code + ", " + this.name + ", " + this.description;
    }
    
}

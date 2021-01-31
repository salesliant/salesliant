/**
	* Copyright (c) minuteproject, minuteproject@gmail.com
	* All rights reserved.
	* 
	* Licensed under the Apache License, Version 2.0 (the "License")
	* you may not use this file except in compliance with the License.
	* You may obtain a copy of the License at
	* 
	* http://www.apache.org/licenses/LICENSE-2.0
	* 
	* Unless required by applicable law or agreed to in writing, software
	* distributed under the License is distributed on an "AS IS" BASIS,
	* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	* See the License for the specific language governing permissions and
	* limitations under the License.
	* 
	* More information on minuteproject:
	* twitter @minuteproject
	* wiki http://minuteproject.wikispaces.com 
	* blog http://minuteproject.blogspot.net
	* 
*/
/**
	* template reference : 
	* - Minuteproject version : 0.9.11
	* - name      : DomainEntityJPA2Annotation
	* - file name : DomainEntityJPA2Annotation.vm
	* - time      : 2021/01/30 AD at 23:59:31 EST
*/
package com.salesliant.entity;

//MP-MANAGED-ADDED-AREA-BEGINNING @import@
//MP-MANAGED-ADDED-AREA-ENDING @import@
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * <p>Title: Category</p>
 *
 * <p>Description: Domain Object describing a Category entity</p>
 *
 */
@Entity (name="Category")
@Table (name="category")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_COMMISION_MODE = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_COMMISSION_FIXED_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COMMISION_PERCENT_PROFIT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COMMISION_PERCENT_SALE = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=128 , nullable=true , unique=false)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sort_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sort_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sort_order@
    @Column(name="sort_order"   , nullable=true , unique=false)
    private Integer sortOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1@
    @Column(name="price1"   , nullable=true , unique=false)
    private java.math.BigDecimal price1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2@
    @Column(name="price2"   , nullable=true , unique=false)
    private java.math.BigDecimal price2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3@
    @Column(name="price3"   , nullable=true , unique=false)
    private java.math.BigDecimal price3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4@
    @Column(name="price4"   , nullable=true , unique=false)
    private java.math.BigDecimal price4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price5@
    @Column(name="price5"   , nullable=true , unique=false)
    private java.math.BigDecimal price5; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price6-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price6-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price6@
    @Column(name="price6"   , nullable=true , unique=false)
    private java.math.BigDecimal price6; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commision_mode-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commision_mode-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commision_mode@
    @Column(name="commision_mode"   , nullable=true , unique=false)
    private Integer commisionMode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commission_fixed_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commission_fixed_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commission_fixed_amount@
    @Column(name="commission_fixed_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal commissionFixedAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commision_maximum_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commision_maximum_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commision_maximum_amount@
    @Column(name="commision_maximum_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal commisionMaximumAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commision_percent_profit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commision_percent_profit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commision_percent_profit@
    @Column(name="commision_percent_profit"   , nullable=true , unique=false)
    private java.math.BigDecimal commisionPercentProfit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commision_percent_sale-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commision_percent_sale-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commision_percent_sale@
    @Column(name="commision_percent_sale"   , nullable=true , unique=false)
    private java.math.BigDecimal commisionPercentSale; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @is_asset_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @is_asset_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-is_asset_tag@
    @Column(name="is_asset_tag"   , nullable=true , unique=false)
    private Boolean isAssetTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @is_shipping_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @is_shipping_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-is_shipping_tag@
    @Column(name="is_shipping_tag"   , nullable=true , unique=false)
    private Boolean isShippingTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @count_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @count_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-count_tag@
    @Column(name="count_tag"   , nullable=true , unique=false)
    private Boolean countTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="parent_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Category parent;  

    @Column(name="parent_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer parentId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="tax_class_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TaxClass taxClass;  

    @Column(name="tax_class_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer taxClassId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @categories-field-category@
    @OneToMany (targetEntity=com.salesliant.entity.Category.class, fetch=FetchType.LAZY, mappedBy="parent", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Category> categories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @items-field-category@
    @OneToMany (targetEntity=com.salesliant.entity.Item.class, fetch=FetchType.LAZY, mappedBy="category", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Item> items = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @subCategories-field-category@
    @OneToMany (targetEntity=com.salesliant.entity.SubCategory.class, fetch=FetchType.LAZY, mappedBy="category", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SubCategory> subCategories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Category() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-name@
    public String getName() {
        return name;
    }
	
    public void setName (String name) {
        this.name =  name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sort_order@
    public Integer getSortOrder() {
        return sortOrder;
    }
	
    public void setSortOrder (Integer sortOrder) {
        this.sortOrder =  sortOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1@
    public java.math.BigDecimal getPrice1() {
        return price1;
    }
	
    public void setPrice1 (java.math.BigDecimal price1) {
        this.price1 =  price1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2@
    public java.math.BigDecimal getPrice2() {
        return price2;
    }
	
    public void setPrice2 (java.math.BigDecimal price2) {
        this.price2 =  price2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3@
    public java.math.BigDecimal getPrice3() {
        return price3;
    }
	
    public void setPrice3 (java.math.BigDecimal price3) {
        this.price3 =  price3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4@
    public java.math.BigDecimal getPrice4() {
        return price4;
    }
	
    public void setPrice4 (java.math.BigDecimal price4) {
        this.price4 =  price4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price5@
    public java.math.BigDecimal getPrice5() {
        return price5;
    }
	
    public void setPrice5 (java.math.BigDecimal price5) {
        this.price5 =  price5;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price6@
    public java.math.BigDecimal getPrice6() {
        return price6;
    }
	
    public void setPrice6 (java.math.BigDecimal price6) {
        this.price6 =  price6;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commision_mode@
    public Integer getCommisionMode() {
        return commisionMode;
    }
	
    public void setCommisionMode (Integer commisionMode) {
        this.commisionMode =  commisionMode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commission_fixed_amount@
    public java.math.BigDecimal getCommissionFixedAmount() {
        return commissionFixedAmount;
    }
	
    public void setCommissionFixedAmount (java.math.BigDecimal commissionFixedAmount) {
        this.commissionFixedAmount =  commissionFixedAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commision_maximum_amount@
    public java.math.BigDecimal getCommisionMaximumAmount() {
        return commisionMaximumAmount;
    }
	
    public void setCommisionMaximumAmount (java.math.BigDecimal commisionMaximumAmount) {
        this.commisionMaximumAmount =  commisionMaximumAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commision_percent_profit@
    public java.math.BigDecimal getCommisionPercentProfit() {
        return commisionPercentProfit;
    }
	
    public void setCommisionPercentProfit (java.math.BigDecimal commisionPercentProfit) {
        this.commisionPercentProfit =  commisionPercentProfit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commision_percent_sale@
    public java.math.BigDecimal getCommisionPercentSale() {
        return commisionPercentSale;
    }
	
    public void setCommisionPercentSale (java.math.BigDecimal commisionPercentSale) {
        this.commisionPercentSale =  commisionPercentSale;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-is_asset_tag@
    public Boolean getIsAssetTag() {
        return isAssetTag;
    }
	
    public void setIsAssetTag (Boolean isAssetTag) {
        this.isAssetTag =  isAssetTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-is_shipping_tag@
    public Boolean getIsShippingTag() {
        return isShippingTag;
    }
	
    public void setIsShippingTag (Boolean isShippingTag) {
        this.isShippingTag =  isShippingTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-count_tag@
    public Boolean getCountTag() {
        return countTag;
    }
	
    public void setCountTag (Boolean countTag) {
        this.countTag =  countTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public Category getParent () {
    	return parent;
    }
	
    public void setParent (Category parent) {
    	this.parent = parent;
    }

    public Integer getParentId() {
        return parentId;
    }
	
    public void setParentId (Integer parent) {
        this.parentId =  parent;
    }
	
    public TaxClass getTaxClass () {
    	return taxClass;
    }
	
    public void setTaxClass (TaxClass taxClass) {
    	this.taxClass = taxClass;
    }

    public Integer getTaxClassId() {
        return taxClassId;
    }
	
    public void setTaxClassId (Integer taxClass) {
        this.taxClassId =  taxClass;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @categories-getter-category@
    public List<Category> getCategories() {
        if (categories == null){
            categories = new ArrayList<>();
        }
        return categories;
    }

    public void setCategories (List<Category> categories) {
        this.categories = categories;
    }	
    
    public void addCategories (Category element) {
    	    getCategories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @items-getter-category@
    public List<Item> getItems() {
        if (items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems (List<Item> items) {
        this.items = items;
    }	
    
    public void addItems (Item element) {
    	    getItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @subCategories-getter-category@
    public List<SubCategory> getSubCategories() {
        if (subCategories == null){
            subCategories = new ArrayList<>();
        }
        return subCategories;
    }

    public void setSubCategories (List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }	
    
    public void addSubCategories (SubCategory element) {
    	    getSubCategories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-category@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (commisionMode==null) commisionMode=__DEFAULT_COMMISION_MODE;
        if (commissionFixedAmount==null) commissionFixedAmount=__DEFAULT_COMMISSION_FIXED_AMOUNT;
        if (commisionPercentProfit==null) commisionPercentProfit=__DEFAULT_COMMISION_PERCENT_PROFIT;
        if (commisionPercentSale==null) commisionPercentSale=__DEFAULT_COMMISION_PERCENT_SALE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-category@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (commisionMode==null) commisionMode=__DEFAULT_COMMISION_MODE;
        if (commissionFixedAmount==null) commissionFixedAmount=__DEFAULT_COMMISSION_FIXED_AMOUNT;
        if (commisionPercentProfit==null) commisionPercentProfit=__DEFAULT_COMMISION_PERCENT_PROFIT;
        if (commisionPercentSale==null) commisionPercentSale=__DEFAULT_COMMISION_PERCENT_SALE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

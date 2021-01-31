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
 * <p>Title: QuantityDiscount</p>
 *
 * <p>Description: Domain Object describing a QuantityDiscount entity</p>
 *
 */
@Entity (name="QuantityDiscount")
@Table (name="quantity_discount")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class QuantityDiscount implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_odd_items-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_odd_items-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_odd_items@
    @Column(name="discount_odd_items"   , nullable=true , unique=false)
    private Boolean discountOddItems; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity1@
    @Column(name="quantity1"   , nullable=true , unique=false)
    private Integer quantity1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1_1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1_1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1_1@
    @Column(name="price1_1"   , nullable=true , unique=false)
    private java.math.BigDecimal price11; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2_1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2_1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2_1@
    @Column(name="price2_1"   , nullable=true , unique=false)
    private java.math.BigDecimal price21; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3_1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3_1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3_1@
    @Column(name="price3_1"   , nullable=true , unique=false)
    private java.math.BigDecimal price31; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4_1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4_1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4_1@
    @Column(name="price4_1"   , nullable=true , unique=false)
    private java.math.BigDecimal price41; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity2@
    @Column(name="quantity2"   , nullable=true , unique=false)
    private Integer quantity2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1_2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1_2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1_2@
    @Column(name="price1_2"   , nullable=true , unique=false)
    private java.math.BigDecimal price12; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2_2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2_2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2_2@
    @Column(name="price2_2"   , nullable=true , unique=false)
    private java.math.BigDecimal price22; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3_2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3_2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3_2@
    @Column(name="price3_2"   , nullable=true , unique=false)
    private java.math.BigDecimal price32; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4_2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4_2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4_2@
    @Column(name="price4_2"   , nullable=true , unique=false)
    private java.math.BigDecimal price42; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity3@
    @Column(name="quantity3"   , nullable=true , unique=false)
    private Integer quantity3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1_3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1_3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1_3@
    @Column(name="price1_3"   , nullable=true , unique=false)
    private java.math.BigDecimal price13; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2_3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2_3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2_3@
    @Column(name="price2_3"   , nullable=true , unique=false)
    private java.math.BigDecimal price23; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3_3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3_3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3_3@
    @Column(name="price3_3"   , nullable=true , unique=false)
    private java.math.BigDecimal price33; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4_3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4_3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4_3@
    @Column(name="price4_3"   , nullable=true , unique=false)
    private java.math.BigDecimal price43; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity4@
    @Column(name="quantity4"   , nullable=true , unique=false)
    private Integer quantity4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1_4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1_4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1_4@
    @Column(name="price1_4"   , nullable=true , unique=false)
    private java.math.BigDecimal price14; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2_4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2_4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2_4@
    @Column(name="price2_4"   , nullable=true , unique=false)
    private java.math.BigDecimal price24; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3_4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3_4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3_4@
    @Column(name="price3_4"   , nullable=true , unique=false)
    private java.math.BigDecimal price34; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4_4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4_4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4_4@
    @Column(name="price4_4"   , nullable=true , unique=false)
    private java.math.BigDecimal price44; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @items-field-quantity_discount@
    @OneToOne(targetEntity = com.salesliant.entity.Item.class, fetch = FetchType.LAZY, mappedBy = "quantityDiscount", cascade = CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private Item item;

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public QuantityDiscount() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_odd_items@
    public Boolean getDiscountOddItems() {
        return discountOddItems;
    }
	
    public void setDiscountOddItems (Boolean discountOddItems) {
        this.discountOddItems =  discountOddItems;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity1@
    public Integer getQuantity1() {
        return quantity1;
    }
	
    public void setQuantity1 (Integer quantity1) {
        this.quantity1 =  quantity1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1_1@
    public java.math.BigDecimal getPrice11() {
        return price11;
    }
	
    public void setPrice11 (java.math.BigDecimal price11) {
        this.price11 =  price11;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2_1@
    public java.math.BigDecimal getPrice21() {
        return price21;
    }
	
    public void setPrice21 (java.math.BigDecimal price21) {
        this.price21 =  price21;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3_1@
    public java.math.BigDecimal getPrice31() {
        return price31;
    }
	
    public void setPrice31 (java.math.BigDecimal price31) {
        this.price31 =  price31;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4_1@
    public java.math.BigDecimal getPrice41() {
        return price41;
    }
	
    public void setPrice41 (java.math.BigDecimal price41) {
        this.price41 =  price41;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity2@
    public Integer getQuantity2() {
        return quantity2;
    }
	
    public void setQuantity2 (Integer quantity2) {
        this.quantity2 =  quantity2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1_2@
    public java.math.BigDecimal getPrice12() {
        return price12;
    }
	
    public void setPrice12 (java.math.BigDecimal price12) {
        this.price12 =  price12;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2_2@
    public java.math.BigDecimal getPrice22() {
        return price22;
    }
	
    public void setPrice22 (java.math.BigDecimal price22) {
        this.price22 =  price22;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3_2@
    public java.math.BigDecimal getPrice32() {
        return price32;
    }
	
    public void setPrice32 (java.math.BigDecimal price32) {
        this.price32 =  price32;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4_2@
    public java.math.BigDecimal getPrice42() {
        return price42;
    }
	
    public void setPrice42 (java.math.BigDecimal price42) {
        this.price42 =  price42;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity3@
    public Integer getQuantity3() {
        return quantity3;
    }
	
    public void setQuantity3 (Integer quantity3) {
        this.quantity3 =  quantity3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1_3@
    public java.math.BigDecimal getPrice13() {
        return price13;
    }
	
    public void setPrice13 (java.math.BigDecimal price13) {
        this.price13 =  price13;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2_3@
    public java.math.BigDecimal getPrice23() {
        return price23;
    }
	
    public void setPrice23 (java.math.BigDecimal price23) {
        this.price23 =  price23;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3_3@
    public java.math.BigDecimal getPrice33() {
        return price33;
    }
	
    public void setPrice33 (java.math.BigDecimal price33) {
        this.price33 =  price33;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4_3@
    public java.math.BigDecimal getPrice43() {
        return price43;
    }
	
    public void setPrice43 (java.math.BigDecimal price43) {
        this.price43 =  price43;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity4@
    public Integer getQuantity4() {
        return quantity4;
    }
	
    public void setQuantity4 (Integer quantity4) {
        this.quantity4 =  quantity4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1_4@
    public java.math.BigDecimal getPrice14() {
        return price14;
    }
	
    public void setPrice14 (java.math.BigDecimal price14) {
        this.price14 =  price14;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2_4@
    public java.math.BigDecimal getPrice24() {
        return price24;
    }
	
    public void setPrice24 (java.math.BigDecimal price24) {
        this.price24 =  price24;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3_4@
    public java.math.BigDecimal getPrice34() {
        return price34;
    }
	
    public void setPrice34 (java.math.BigDecimal price34) {
        this.price34 =  price34;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4_4@
    public java.math.BigDecimal getPrice44() {
        return price44;
    }
	
    public void setPrice44 (java.math.BigDecimal price44) {
        this.price44 =  price44;
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


    public Store getStore () {
    	return store;
    }
	
    public void setStore (Store store) {
    	this.store = store;
    }

    public Integer getStoreId() {
        return storeId;
    }
	
    public void setStoreId (Integer store) {
        this.storeId =  store;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @items-getter-quantity_discount@
    public Item getItem() {

        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-quantity_discount@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-quantity_discount@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

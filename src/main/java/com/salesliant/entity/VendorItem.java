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
 * <p>Title: VendorItem</p>
 *
 * <p>Description: Domain Object describing a VendorItem entity</p>
 *
 */
@Entity (name="VendorItem")
@Table (name="vendor_item")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class VendorItem implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_MINIMUM_ORDER = Integer.valueOf(1);
	public static final Integer __DEFAULT_PACK_QUANTITY = Integer.valueOf(1);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_item_look_up_code@
    @Column(name="vendor_item_look_up_code"  , length=32 , nullable=false , unique=false)
    private String vendorItemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @minimum_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @minimum_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-minimum_order@
    @Column(name="minimum_order"   , nullable=true , unique=false)
    private Integer minimumOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @pack_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @pack_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-pack_quantity@
    @Column(name="pack_quantity"   , nullable=true , unique=false)
    private Integer packQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_updated-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_updated-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_updated@
    @Column(name="last_updated"   , nullable=true , unique=false)
    private Timestamp lastUpdated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="vendor_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Vendor vendor;  

    @Column(name="vendor_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer vendorId;

    /**
    * Default constructor
    */
    public VendorItem() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_item_look_up_code@
    public String getVendorItemLookUpCode() {
        return vendorItemLookUpCode;
    }
	
    public void setVendorItemLookUpCode (String vendorItemLookUpCode) {
        this.vendorItemLookUpCode =  vendorItemLookUpCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cost@
    public java.math.BigDecimal getCost() {
        return cost;
    }
	
    public void setCost (java.math.BigDecimal cost) {
        this.cost =  cost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-minimum_order@
    public Integer getMinimumOrder() {
        return minimumOrder;
    }
	
    public void setMinimumOrder (Integer minimumOrder) {
        this.minimumOrder =  minimumOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-pack_quantity@
    public Integer getPackQuantity() {
        return packQuantity;
    }
	
    public void setPackQuantity (Integer packQuantity) {
        this.packQuantity =  packQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_updated@
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
	
    public void setLastUpdated (Timestamp lastUpdated) {
        this.lastUpdated =  lastUpdated;
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


    public Item getItem () {
    	return item;
    }
	
    public void setItem (Item item) {
    	this.item = item;
    }

    public Integer getItemId() {
        return itemId;
    }
	
    public void setItemId (Integer item) {
        this.itemId =  item;
    }
	
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
	
    public Vendor getVendor () {
    	return vendor;
    }
	
    public void setVendor (Vendor vendor) {
    	this.vendor = vendor;
    }

    public Integer getVendorId() {
        return vendorId;
    }
	
    public void setVendorId (Integer vendor) {
        this.vendorId =  vendor;
    }
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-vendor_item@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (minimumOrder==null) minimumOrder=__DEFAULT_MINIMUM_ORDER;
        if (packQuantity==null) packQuantity=__DEFAULT_PACK_QUANTITY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-vendor_item@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (minimumOrder==null) minimumOrder=__DEFAULT_MINIMUM_ORDER;
        if (packQuantity==null) packQuantity=__DEFAULT_PACK_QUANTITY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

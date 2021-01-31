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
 * <p>Title: VendorShippingService</p>
 *
 * <p>Description: Domain Object describing a VendorShippingService entity</p>
 *
 */
@Entity (name="VendorShippingService")
@Table (name="vendor_shipping_service")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class VendorShippingService implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_DELIVERY_DAY = Integer.valueOf(1);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=32 , nullable=false , unique=true)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @delivery_day-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @delivery_day-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-delivery_day@
    @Column(name="delivery_day"   , nullable=true , unique=false)
    private Integer deliveryDay; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="shipping_carrier_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private ShippingCarrier shippingCarrier;  

    @Column(name="shipping_carrier_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer shippingCarrierId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Vendor vendor;  

    @Column(name="vendor_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer vendorId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-field-vendor_shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrder.class, fetch=FetchType.LAZY, mappedBy="vendorShippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrder> purchaseOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-field-vendor_shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistory.class, fetch=FetchType.LAZY, mappedBy="vendorShippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistory> purchaseOrderHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-field-vendor_shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.Vendor.class, fetch=FetchType.LAZY, mappedBy="defaultVendorShippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Vendor> vendors = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public VendorShippingService() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-code@
    public String getCode() {
        return code;
    }
	
    public void setCode (String code) {
        this.code =  code;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-delivery_day@
    public Integer getDeliveryDay() {
        return deliveryDay;
    }
	
    public void setDeliveryDay (Integer deliveryDay) {
        this.deliveryDay =  deliveryDay;
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


    public ShippingCarrier getShippingCarrier () {
    	return shippingCarrier;
    }
	
    public void setShippingCarrier (ShippingCarrier shippingCarrier) {
    	this.shippingCarrier = shippingCarrier;
    }

    public Integer getShippingCarrierId() {
        return shippingCarrierId;
    }
	
    public void setShippingCarrierId (Integer shippingCarrier) {
        this.shippingCarrierId =  shippingCarrier;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-getter-vendor_shipping_service@
    public List<PurchaseOrder> getPurchaseOrders() {
        if (purchaseOrders == null){
            purchaseOrders = new ArrayList<>();
        }
        return purchaseOrders;
    }

    public void setPurchaseOrders (List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }	
    
    public void addPurchaseOrders (PurchaseOrder element) {
    	    getPurchaseOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-getter-vendor_shipping_service@
    public List<PurchaseOrderHistory> getPurchaseOrderHistories() {
        if (purchaseOrderHistories == null){
            purchaseOrderHistories = new ArrayList<>();
        }
        return purchaseOrderHistories;
    }

    public void setPurchaseOrderHistories (List<PurchaseOrderHistory> purchaseOrderHistories) {
        this.purchaseOrderHistories = purchaseOrderHistories;
    }	
    
    public void addPurchaseOrderHistories (PurchaseOrderHistory element) {
    	    getPurchaseOrderHistories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-getter-vendor_shipping_service@
    public List<Vendor> getVendors() {
        if (vendors == null){
            vendors = new ArrayList<>();
        }
        return vendors;
    }

    public void setVendors (List<Vendor> vendors) {
        this.vendors = vendors;
    }	
    
    public void addVendors (Vendor element) {
    	    getVendors().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-vendor_shipping_service@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (deliveryDay==null) deliveryDay=__DEFAULT_DELIVERY_DAY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-vendor_shipping_service@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (deliveryDay==null) deliveryDay=__DEFAULT_DELIVERY_DAY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

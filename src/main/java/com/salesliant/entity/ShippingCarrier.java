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
 * <p>Title: ShippingCarrier</p>
 *
 * <p>Description: Domain Object describing a ShippingCarrier entity</p>
 *
 */
@Entity (name="ShippingCarrier")
@Table (name="shipping_carrier")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ShippingCarrier implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=64 , nullable=true , unique=false)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=32 , nullable=false , unique=true)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @url-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @url-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-url@
    @Column(name="url"  , length=255 , nullable=true , unique=false)
    private String url; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tracking_url-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tracking_url-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tracking_url@
    @Column(name="tracking_url"  , length=255 , nullable=true , unique=false)
    private String trackingUrl; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @shipping_url-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @shipping_url-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-shipping_url@
    @Column(name="shipping_url"  , length=255 , nullable=true , unique=false)
    private String shippingUrl; 
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceEntries-field-shipping_carrier@
    @OneToMany (targetEntity=com.salesliant.entity.InvoiceEntry.class, fetch=FetchType.LAZY, mappedBy="shippingCarrier", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <InvoiceEntry> invoiceEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippingServices-field-shipping_carrier@
    @OneToMany (targetEntity=com.salesliant.entity.ShippingService.class, fetch=FetchType.LAZY, mappedBy="shippingCarrier", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ShippingService> shippingServices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrders-field-shipping_carrier@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrder.class, fetch=FetchType.LAZY, mappedBy="shippingCarrier", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrder> transferOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistories-field-shipping_carrier@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderHistory.class, fetch=FetchType.LAZY, mappedBy="shippingCarrier", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrderHistory> transferOrderHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShippingServices-field-shipping_carrier@
    @OneToMany (targetEntity=com.salesliant.entity.VendorShippingService.class, fetch=FetchType.LAZY, mappedBy="shippingCarrier", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorShippingService> vendorShippingServices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public ShippingCarrier() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-code@
    public String getCode() {
        return code;
    }
	
    public void setCode (String code) {
        this.code =  code;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-url@
    public String getUrl() {
        return url;
    }
	
    public void setUrl (String url) {
        this.url =  url;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tracking_url@
    public String getTrackingUrl() {
        return trackingUrl;
    }
	
    public void setTrackingUrl (String trackingUrl) {
        this.trackingUrl =  trackingUrl;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-shipping_url@
    public String getShippingUrl() {
        return shippingUrl;
    }
	
    public void setShippingUrl (String shippingUrl) {
        this.shippingUrl =  shippingUrl;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceEntries-getter-shipping_carrier@
    public List<InvoiceEntry> getInvoiceEntries() {
        if (invoiceEntries == null){
            invoiceEntries = new ArrayList<>();
        }
        return invoiceEntries;
    }

    public void setInvoiceEntries (List<InvoiceEntry> invoiceEntries) {
        this.invoiceEntries = invoiceEntries;
    }	
    
    public void addInvoiceEntries (InvoiceEntry element) {
    	    getInvoiceEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippingServices-getter-shipping_carrier@
    public List<ShippingService> getShippingServices() {
        if (shippingServices == null){
            shippingServices = new ArrayList<>();
        }
        return shippingServices;
    }

    public void setShippingServices (List<ShippingService> shippingServices) {
        this.shippingServices = shippingServices;
    }	
    
    public void addShippingServices (ShippingService element) {
    	    getShippingServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrders-getter-shipping_carrier@
    public List<TransferOrder> getTransferOrders() {
        if (transferOrders == null){
            transferOrders = new ArrayList<>();
        }
        return transferOrders;
    }

    public void setTransferOrders (List<TransferOrder> transferOrders) {
        this.transferOrders = transferOrders;
    }	
    
    public void addTransferOrders (TransferOrder element) {
    	    getTransferOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistories-getter-shipping_carrier@
    public List<TransferOrderHistory> getTransferOrderHistories() {
        if (transferOrderHistories == null){
            transferOrderHistories = new ArrayList<>();
        }
        return transferOrderHistories;
    }

    public void setTransferOrderHistories (List<TransferOrderHistory> transferOrderHistories) {
        this.transferOrderHistories = transferOrderHistories;
    }	
    
    public void addTransferOrderHistories (TransferOrderHistory element) {
    	    getTransferOrderHistories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShippingServices-getter-shipping_carrier@
    public List<VendorShippingService> getVendorShippingServices() {
        if (vendorShippingServices == null){
            vendorShippingServices = new ArrayList<>();
        }
        return vendorShippingServices;
    }

    public void setVendorShippingServices (List<VendorShippingService> vendorShippingServices) {
        this.vendorShippingServices = vendorShippingServices;
    }	
    
    public void addVendorShippingServices (VendorShippingService element) {
    	    getVendorShippingServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-shipping_carrier@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-shipping_carrier@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

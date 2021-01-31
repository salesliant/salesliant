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
	* - time      : 2021/01/30 AD at 23:59:32 EST
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
 * <p>Title: VendorTerm</p>
 *
 * <p>Description: Domain Object describing a VendorTerm entity</p>
 *
 */
@Entity (name="VendorTerm")
@Table (name="vendor_term")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class VendorTerm implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_DUE_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_DISCOUNT_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=128 , nullable=true , unique=false)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @due_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @due_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-due_days@
    @Column(name="due_days"   , nullable=true , unique=false)
    private Integer dueDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_days@
    @Column(name="discount_days"   , nullable=true , unique=false)
    private Integer discountDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @post_to_ap-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @post_to_ap-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-post_to_ap@
    @Column(name="post_to_ap"   , nullable=true , unique=false)
    private Boolean postToAp; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @paid_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @paid_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-paid_tag@
    @Column(name="paid_tag"   , nullable=true , unique=false)
    private Boolean paidTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_rate@
    @Column(name="discount_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal discountRate; 
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-field-vendor_term@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrder.class, fetch=FetchType.LAZY, mappedBy="vendorTerm", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrder> purchaseOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-field-vendor_term@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistory.class, fetch=FetchType.LAZY, mappedBy="vendorTerm", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistory> purchaseOrderHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-field-vendor_term@
    @OneToMany (targetEntity=com.salesliant.entity.Vendor.class, fetch=FetchType.LAZY, mappedBy="vendorTerm", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Vendor> vendors = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public VendorTerm() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-due_days@
    public Integer getDueDays() {
        return dueDays;
    }
	
    public void setDueDays (Integer dueDays) {
        this.dueDays =  dueDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_days@
    public Integer getDiscountDays() {
        return discountDays;
    }
	
    public void setDiscountDays (Integer discountDays) {
        this.discountDays =  discountDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-post_to_ap@
    public Boolean getPostToAp() {
        return postToAp;
    }
	
    public void setPostToAp (Boolean postToAp) {
        this.postToAp =  postToAp;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-paid_tag@
    public Boolean getPaidTag() {
        return paidTag;
    }
	
    public void setPaidTag (Boolean paidTag) {
        this.paidTag =  paidTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_rate@
    public java.math.BigDecimal getDiscountRate() {
        return discountRate;
    }
	
    public void setDiscountRate (java.math.BigDecimal discountRate) {
        this.discountRate =  discountRate;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-getter-vendor_term@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-getter-vendor_term@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-getter-vendor_term@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-vendor_term@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (dueDays==null) dueDays=__DEFAULT_DUE_DAYS;
        if (discountDays==null) discountDays=__DEFAULT_DISCOUNT_DAYS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-vendor_term@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (dueDays==null) dueDays=__DEFAULT_DUE_DAYS;
        if (discountDays==null) discountDays=__DEFAULT_DISCOUNT_DAYS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

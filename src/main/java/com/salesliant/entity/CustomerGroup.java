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
 * <p>Title: CustomerGroup</p>
 *
 * <p>Description: Domain Object describing a CustomerGroup entity</p>
 *
 */
@Entity (name="CustomerGroup")
@Table (name="customer_group")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class CustomerGroup implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=32 , nullable=true , unique=false)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount@
    @Column(name="discount"   , nullable=false , unique=false)
    private java.math.BigDecimal discount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_exempt-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_exempt-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_exempt@
    @Column(name="tax_exempt"   , nullable=true , unique=false)
    private Boolean taxExempt; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_added-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_added-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_added@
    @Column(name="date_added"   , nullable=true , unique=false)
    private Date dateAdded; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="payment_type_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private PaymentType paymentType;  

    @Column(name="payment_type_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer paymentTypeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="shipping_service_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ShippingService shippingService;  

    @Column(name="shipping_service_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer shippingServiceId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-field-customer_group@
    @OneToMany (targetEntity=com.salesliant.entity.Customer.class, fetch=FetchType.LAZY, mappedBy="customerGroup", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Customer> customers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public CustomerGroup() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount@
    public java.math.BigDecimal getDiscount() {
        return discount;
    }
	
    public void setDiscount (java.math.BigDecimal discount) {
        this.discount =  discount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_exempt@
    public Boolean getTaxExempt() {
        return taxExempt;
    }
	
    public void setTaxExempt (Boolean taxExempt) {
        this.taxExempt =  taxExempt;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_added@
    public Date getDateAdded() {
        return dateAdded;
    }
	
    public void setDateAdded (Date dateAdded) {
        this.dateAdded =  dateAdded;
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


    public PaymentType getPaymentType () {
    	return paymentType;
    }
	
    public void setPaymentType (PaymentType paymentType) {
    	this.paymentType = paymentType;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }
	
    public void setPaymentTypeId (Integer paymentType) {
        this.paymentTypeId =  paymentType;
    }
	
    public ShippingService getShippingService () {
    	return shippingService;
    }
	
    public void setShippingService (ShippingService shippingService) {
    	this.shippingService = shippingService;
    }

    public Integer getShippingServiceId() {
        return shippingServiceId;
    }
	
    public void setShippingServiceId (Integer shippingService) {
        this.shippingServiceId =  shippingService;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-getter-customer_group@
    public List<Customer> getCustomers() {
        if (customers == null){
            customers = new ArrayList<>();
        }
        return customers;
    }

    public void setCustomers (List<Customer> customers) {
        this.customers = customers;
    }	
    
    public void addCustomers (Customer element) {
    	    getCustomers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-customer_group@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (discount==null) discount=__DEFAULT_DISCOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-customer_group@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (discount==null) discount=__DEFAULT_DISCOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

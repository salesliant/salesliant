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
 * <p>Title: CustomerTerm</p>
 *
 * <p>Description: Domain Object describing a CustomerTerm entity</p>
 *
 */
@Entity (name="CustomerTerm")
@Table (name="customer_term")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class CustomerTerm implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_DUE_DAYS = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_INTEREST_RATE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_MINIMUM_FINANCE_CHARGE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_MINIMUM_PAYMENT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_RATE = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_DISCOUNT_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=128 , nullable=false , unique=true)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @store_account_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @store_account_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-store_account_tag@
    @Column(name="store_account_tag"   , nullable=true , unique=false)
    private Boolean storeAccountTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @due_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @due_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-due_days@
    @Column(name="due_days"   , nullable=true , unique=false)
    private Integer dueDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @interest_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @interest_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-interest_rate@
    @Column(name="interest_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal interestRate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @minimum_finance_charge-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @minimum_finance_charge-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-minimum_finance_charge@
    @Column(name="minimum_finance_charge"   , nullable=true , unique=false)
    private java.math.BigDecimal minimumFinanceCharge; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @minimum_payment-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @minimum_payment-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-minimum_payment@
    @Column(name="minimum_payment"   , nullable=true , unique=false)
    private java.math.BigDecimal minimumPayment; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_rate@
    @Column(name="discount_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal discountRate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_days@
    @Column(name="discount_days"   , nullable=true , unique=false)
    private Integer discountDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-field-customer_term@
    @OneToMany (targetEntity=com.salesliant.entity.Customer.class, fetch=FetchType.LAZY, mappedBy="customerTerm", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Customer> customers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-field-customer_term@
    @OneToMany (targetEntity=com.salesliant.entity.Store.class, fetch=FetchType.LAZY, mappedBy="defaultCustomerTerm", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Store> stores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public CustomerTerm() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-store_account_tag@
    public Boolean getStoreAccountTag() {
        return storeAccountTag;
    }
	
    public void setStoreAccountTag (Boolean storeAccountTag) {
        this.storeAccountTag =  storeAccountTag;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-interest_rate@
    public java.math.BigDecimal getInterestRate() {
        return interestRate;
    }
	
    public void setInterestRate (java.math.BigDecimal interestRate) {
        this.interestRate =  interestRate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-minimum_finance_charge@
    public java.math.BigDecimal getMinimumFinanceCharge() {
        return minimumFinanceCharge;
    }
	
    public void setMinimumFinanceCharge (java.math.BigDecimal minimumFinanceCharge) {
        this.minimumFinanceCharge =  minimumFinanceCharge;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-minimum_payment@
    public java.math.BigDecimal getMinimumPayment() {
        return minimumPayment;
    }
	
    public void setMinimumPayment (java.math.BigDecimal minimumPayment) {
        this.minimumPayment =  minimumPayment;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_days@
    public Integer getDiscountDays() {
        return discountDays;
    }
	
    public void setDiscountDays (Integer discountDays) {
        this.discountDays =  discountDays;
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



//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-getter-customer_term@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-getter-customer_term@
    public List<Store> getStores() {
        if (stores == null){
            stores = new ArrayList<>();
        }
        return stores;
    }

    public void setStores (List<Store> stores) {
        this.stores = stores;
    }	
    
    public void addStores (Store element) {
    	    getStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-customer_term@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (dueDays==null) dueDays=__DEFAULT_DUE_DAYS;
        if (interestRate==null) interestRate=__DEFAULT_INTEREST_RATE;
        if (minimumFinanceCharge==null) minimumFinanceCharge=__DEFAULT_MINIMUM_FINANCE_CHARGE;
        if (minimumPayment==null) minimumPayment=__DEFAULT_MINIMUM_PAYMENT;
        if (discountRate==null) discountRate=__DEFAULT_DISCOUNT_RATE;
        if (discountDays==null) discountDays=__DEFAULT_DISCOUNT_DAYS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-customer_term@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (dueDays==null) dueDays=__DEFAULT_DUE_DAYS;
        if (interestRate==null) interestRate=__DEFAULT_INTEREST_RATE;
        if (minimumFinanceCharge==null) minimumFinanceCharge=__DEFAULT_MINIMUM_FINANCE_CHARGE;
        if (minimumPayment==null) minimumPayment=__DEFAULT_MINIMUM_PAYMENT;
        if (discountRate==null) discountRate=__DEFAULT_DISCOUNT_RATE;
        if (discountDays==null) discountDays=__DEFAULT_DISCOUNT_DAYS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

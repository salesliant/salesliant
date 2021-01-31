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
 * <p>Title: Cheque</p>
 *
 * <p>Description: Domain Object describing a Cheque entity</p>
 *
 */
@Entity (name="Cheque")
@Table (name="cheque")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Cheque implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_CHECK_AMOUNT = java.math.BigDecimal.valueOf(0.00);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @reference_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @reference_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-reference_number@
    @Column(name="reference_number"   , nullable=true , unique=false)
    private Integer referenceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_date@
    @Column(name="check_date"   , nullable=true , unique=false)
    private Date checkDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_amount@
    @Column(name="check_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal checkAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_name@
    @Column(name="customer_name"  , length=64 , nullable=true , unique=false)
    private String customerName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=64 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_number@
    @Column(name="check_number"  , length=32 , nullable=true , unique=true)
    private String checkNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @license-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @license-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-license@
    @Column(name="license"  , length=32 , nullable=true , unique=false)
    private String license; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_type@
    @Column(name="check_type"  , length=1 , nullable=true , unique=false)
    private String checkType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public Cheque() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-reference_number@
    public Integer getReferenceNumber() {
        return referenceNumber;
    }
	
    public void setReferenceNumber (Integer referenceNumber) {
        this.referenceNumber =  referenceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_date@
    public Date getCheckDate() {
        return checkDate;
    }
	
    public void setCheckDate (Date checkDate) {
        this.checkDate =  checkDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_amount@
    public java.math.BigDecimal getCheckAmount() {
        return checkAmount;
    }
	
    public void setCheckAmount (java.math.BigDecimal checkAmount) {
        this.checkAmount =  checkAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_name@
    public String getCustomerName() {
        return customerName;
    }
	
    public void setCustomerName (String customerName) {
        this.customerName =  customerName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_number@
    public String getCheckNumber() {
        return checkNumber;
    }
	
    public void setCheckNumber (String checkNumber) {
        this.checkNumber =  checkNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-license@
    public String getLicense() {
        return license;
    }
	
    public void setLicense (String license) {
        this.license =  license;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_type@
    public String getCheckType() {
        return checkType;
    }
	
    public void setCheckType (String checkType) {
        this.checkType =  checkType;
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


    public Customer getCustomer () {
    	return customer;
    }
	
    public void setCustomer (Customer customer) {
    	this.customer = customer;
    }

    public Integer getCustomerId() {
        return customerId;
    }
	
    public void setCustomerId (Integer customer) {
        this.customerId =  customer;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-cheque@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (checkAmount==null) checkAmount=__DEFAULT_CHECK_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-cheque@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (checkAmount==null) checkAmount=__DEFAULT_CHECK_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

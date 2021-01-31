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
	* - time      : 2019/12/15 AD at 18:23:04 EST
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
 * <p>Title: AccountReceivableBatch</p>
 *
 * <p>Description: Domain Object describing a AccountReceivableBatch entity</p>
 *
 */
@Entity (name="AccountReceivableBatch")
@Table (name="account_receivable_batch")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class AccountReceivableBatch implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @date_paid_on-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_paid_on-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_paid_on@
    @Column(name="date_paid_on"   , nullable=true , unique=false)
    private Timestamp datePaidOn; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total_amount@
    @Column(name="total_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal totalAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name@
    @Column(name="employee_name"  , length=64 , nullable=true , unique=false)
    private String employeeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_name@
    @Column(name="customer_name"  , length=64 , nullable=true , unique=false)
    private String customerName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_number@
    @Column(name="check_number"  , length=64 , nullable=true , unique=false)
    private String checkNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @location_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @location_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-location_id@
    @Column(name="location_id"   , nullable=true , unique=false)
    private Integer location; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer customerId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivablePayments-field-account_receivable_batch@
    @OneToMany (targetEntity=com.salesliant.entity.AccountReceivablePayment.class, fetch=FetchType.LAZY, mappedBy="accountReceivableBatch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountReceivablePayment> accountReceivablePayments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public AccountReceivableBatch() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_paid_on@
    public Timestamp getDatePaidOn() {
        return datePaidOn;
    }
	
    public void setDatePaidOn (Timestamp datePaidOn) {
        this.datePaidOn =  datePaidOn;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total_amount@
    public java.math.BigDecimal getTotalAmount() {
        return totalAmount;
    }
	
    public void setTotalAmount (java.math.BigDecimal totalAmount) {
        this.totalAmount =  totalAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_name@
    public String getEmployeeName() {
        return employeeName;
    }
	
    public void setEmployeeName (String employeeName) {
        this.employeeName =  employeeName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_number@
    public String getCheckNumber() {
        return checkNumber;
    }
	
    public void setCheckNumber (String checkNumber) {
        this.checkNumber =  checkNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-location_id@
    public Integer getLocation() {
        return location;
    }
	
    public void setLocation (Integer location) {
        this.location =  location;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivablePayments-getter-account_receivable_batch@
    public List<AccountReceivablePayment> getAccountReceivablePayments() {
        if (accountReceivablePayments == null){
            accountReceivablePayments = new ArrayList<>();
        }
        return accountReceivablePayments;
    }

    public void setAccountReceivablePayments (List<AccountReceivablePayment> accountReceivablePayments) {
        this.accountReceivablePayments = accountReceivablePayments;
    }	
    
    public void addAccountReceivablePayments (AccountReceivablePayment element) {
    	    getAccountReceivablePayments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-account_receivable_batch@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-account_receivable_batch@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

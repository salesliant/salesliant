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
 * <p>Title: VoidedTransaction</p>
 *
 * <p>Description: Domain Object describing a VoidedTransaction entity</p>
 *
 */
@Entity (name="VoidedTransaction")
@Table (name="voided_transaction")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class VoidedTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @transaction_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transaction_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transaction_type@
    @Column(name="transaction_type"   , nullable=true , unique=false)
    private Integer transactionType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_entered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_entered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_entered@
    @Column(name="date_entered"   , nullable=true , unique=false)
    private Date dateEntered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_account_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_account_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_account_number@
    @Column(name="customer_account_number"  , length=64 , nullable=true , unique=false)
    private String customerAccountNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_name@
    @Column(name="customer_name"  , length=64 , nullable=true , unique=false)
    private String customerName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transaction_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transaction_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transaction_number@
    @Column(name="transaction_number"  , length=64 , nullable=true , unique=false)
    private String transactionNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name@
    @Column(name="employee_name"  , length=64 , nullable=true , unique=false)
    private String employeeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transaction_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transaction_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transaction_amount@
    @Column(name="transaction_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal transactionAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Employee employee;  

    @Column(name="employee_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer employeeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-field-voided_transaction@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="voidedTransaction", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> deposits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public VoidedTransaction() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transaction_type@
    public Integer getTransactionType() {
        return transactionType;
    }
	
    public void setTransactionType (Integer transactionType) {
        this.transactionType =  transactionType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_entered@
    public Date getDateEntered() {
        return dateEntered;
    }
	
    public void setDateEntered (Date dateEntered) {
        this.dateEntered =  dateEntered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_account_number@
    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }
	
    public void setCustomerAccountNumber (String customerAccountNumber) {
        this.customerAccountNumber =  customerAccountNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transaction_number@
    public String getTransactionNumber() {
        return transactionNumber;
    }
	
    public void setTransactionNumber (String transactionNumber) {
        this.transactionNumber =  transactionNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transaction_amount@
    public java.math.BigDecimal getTransactionAmount() {
        return transactionAmount;
    }
	
    public void setTransactionAmount (java.math.BigDecimal transactionAmount) {
        this.transactionAmount =  transactionAmount;
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
	
    public Employee getEmployee () {
    	return employee;
    }
	
    public void setEmployee (Employee employee) {
    	this.employee = employee;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
	
    public void setEmployeeId (Integer employee) {
        this.employeeId =  employee;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-getter-voided_transaction@
    public List<Deposit> getDeposits() {
        if (deposits == null){
            deposits = new ArrayList<>();
        }
        return deposits;
    }

    public void setDeposits (List<Deposit> deposits) {
        this.deposits = deposits;
    }	
    
    public void addDeposits (Deposit element) {
    	    getDeposits().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING



//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

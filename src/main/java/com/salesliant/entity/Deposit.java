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
 * <p>Title: Deposit</p>
 *
 * <p>Description: Domain Object describing a Deposit entity</p>
 *
 */
@Entity (name="Deposit")
@Table (name="deposit")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Deposit implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_STATUS = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @deposit_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @deposit_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-deposit_type@
    @Column(name="deposit_type"   , nullable=true , unique=false)
    private Integer depositType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-order_number@
    @Column(name="order_number"  , length=64 , nullable=true , unique=false)
    private String orderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_number@
    @Column(name="invoice_number"  , length=64 , nullable=true , unique=false)
    private String invoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-amount@
    @Column(name="amount"   , nullable=true , unique=false)
    private java.math.BigDecimal amount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Timestamp dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="close_batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Batch closeBatch;  

    @Column(name="close_batch_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer closeBatchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="open_batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Batch openBatch;  

    @Column(name="open_batch_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer openBatchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="sales_order_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private SalesOrder salesOrder;  

    @Column(name="sales_order_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer salesOrderId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="voided_transaction_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VoidedTransaction voidedTransaction;  

    @Column(name="voided_transaction_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer voidedTransactionId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @payments-field-deposit@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="deposit", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Deposit() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-deposit_type@
    public Integer getDepositType() {
        return depositType;
    }
	
    public void setDepositType (Integer depositType) {
        this.depositType =  depositType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-order_number@
    public String getOrderNumber() {
        return orderNumber;
    }
	
    public void setOrderNumber (String orderNumber) {
        this.orderNumber =  orderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_number@
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
	
    public void setInvoiceNumber (String invoiceNumber) {
        this.invoiceNumber =  invoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-status@
    public Integer getStatus() {
        return status;
    }
	
    public void setStatus (Integer status) {
        this.status =  status;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-amount@
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	
    public void setAmount (java.math.BigDecimal amount) {
        this.amount =  amount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_created@
    public Timestamp getDateCreated() {
        return dateCreated;
    }
	
    public void setDateCreated (Timestamp dateCreated) {
        this.dateCreated =  dateCreated;
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


    public Batch getCloseBatch () {
    	return closeBatch;
    }
	
    public void setCloseBatch (Batch closeBatch) {
    	this.closeBatch = closeBatch;
    }

    public Integer getCloseBatchId() {
        return closeBatchId;
    }
	
    public void setCloseBatchId (Integer closeBatch) {
        this.closeBatchId =  closeBatch;
    }
	
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
	
    public Invoice getInvoice () {
    	return invoice;
    }
	
    public void setInvoice (Invoice invoice) {
    	this.invoice = invoice;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }
	
    public void setInvoiceId (Integer invoice) {
        this.invoiceId =  invoice;
    }
	
    public Batch getOpenBatch () {
    	return openBatch;
    }
	
    public void setOpenBatch (Batch openBatch) {
    	this.openBatch = openBatch;
    }

    public Integer getOpenBatchId() {
        return openBatchId;
    }
	
    public void setOpenBatchId (Integer openBatch) {
        this.openBatchId =  openBatch;
    }
	
    public SalesOrder getSalesOrder () {
    	return salesOrder;
    }
	
    public void setSalesOrder (SalesOrder salesOrder) {
    	this.salesOrder = salesOrder;
    }

    public Integer getSalesOrderId() {
        return salesOrderId;
    }
	
    public void setSalesOrderId (Integer salesOrder) {
        this.salesOrderId =  salesOrder;
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
	
    public VoidedTransaction getVoidedTransaction () {
    	return voidedTransaction;
    }
	
    public void setVoidedTransaction (VoidedTransaction voidedTransaction) {
    	this.voidedTransaction = voidedTransaction;
    }

    public Integer getVoidedTransactionId() {
        return voidedTransactionId;
    }
	
    public void setVoidedTransactionId (Integer voidedTransaction) {
        this.voidedTransactionId =  voidedTransaction;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-deposit@
    public List<Payment> getPayments() {
        if (payments == null){
            payments = new ArrayList<>();
        }
        return payments;
    }

    public void setPayments (List<Payment> payments) {
        this.payments = payments;
    }	
    
    public void addPayments (Payment element) {
    	    getPayments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-deposit@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (status==null) status=__DEFAULT_STATUS;
        if (amount==null) amount=__DEFAULT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-deposit@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (status==null) status=__DEFAULT_STATUS;
        if (amount==null) amount=__DEFAULT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

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
	* - time      : 2021/01/30 AD at 23:59:30 EST
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
 * <p>Title: AccountPayableHistory</p>
 *
 * <p>Description: Domain Object describing a AccountPayableHistory entity</p>
 *
 */
@Entity (name="AccountPayableHistory")
@Table (name="account_payable_history")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class AccountPayableHistory implements Serializable {
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

//MP-MANAGED-ADDED-AREA-BEGINNING @date_posted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_posted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_posted@
    @Column(name="date_posted"   , nullable=true , unique=false)
    private Timestamp datePosted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_due-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_due-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_due@
    @Column(name="date_due"   , nullable=true , unique=false)
    private Date dateDue; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_invoiced-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_invoiced-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_invoiced@
    @Column(name="date_invoiced"   , nullable=true , unique=false)
    private Date dateInvoiced; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total_amount@
    @Column(name="total_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal totalAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_amount@
    @Column(name="discount_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal discountAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @paid_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @paid_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-paid_amount@
    @Column(name="paid_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal paidAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_credit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_credit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_credit_amount@
    @Column(name="gl_credit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal glCreditAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_debit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_debit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_debit_amount@
    @Column(name="gl_debit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal glDebitAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_account-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_account-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_account@
    @Column(name="gl_account"  , length=32 , nullable=true , unique=false)
    private String glAccount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_payable_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_payable_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_payable_type@
    @Column(name="account_payable_type"   , nullable=true , unique=false)
    private Integer accountPayableType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_invoice_number@
    @Column(name="vendor_invoice_number"  , length=32 , nullable=true , unique=false)
    private String vendorInvoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_name@
    @Column(name="vendor_name"  , length=32 , nullable=true , unique=false)
    private String vendorName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @purchase_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @purchase_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-purchase_order_number@
    @Column(name="purchase_order_number"  , length=32 , nullable=true , unique=false)
    private String purchaseOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="account_payable_batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private AccountPayableBatch accountPayableBatch;  

    @Column(name="account_payable_batch_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer accountPayableBatchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="purchase_order_history_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private PurchaseOrderHistory purchaseOrderHistory;  

    @Column(name="purchase_order_history_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer purchaseOrderHistoryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public AccountPayableHistory() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_posted@
    public Timestamp getDatePosted() {
        return datePosted;
    }
	
    public void setDatePosted (Timestamp datePosted) {
        this.datePosted =  datePosted;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_due@
    public Date getDateDue() {
        return dateDue;
    }
	
    public void setDateDue (Date dateDue) {
        this.dateDue =  dateDue;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_invoiced@
    public Date getDateInvoiced() {
        return dateInvoiced;
    }
	
    public void setDateInvoiced (Date dateInvoiced) {
        this.dateInvoiced =  dateInvoiced;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_amount@
    public java.math.BigDecimal getDiscountAmount() {
        return discountAmount;
    }
	
    public void setDiscountAmount (java.math.BigDecimal discountAmount) {
        this.discountAmount =  discountAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-paid_amount@
    public java.math.BigDecimal getPaidAmount() {
        return paidAmount;
    }
	
    public void setPaidAmount (java.math.BigDecimal paidAmount) {
        this.paidAmount =  paidAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gl_credit_amount@
    public java.math.BigDecimal getGlCreditAmount() {
        return glCreditAmount;
    }
	
    public void setGlCreditAmount (java.math.BigDecimal glCreditAmount) {
        this.glCreditAmount =  glCreditAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gl_debit_amount@
    public java.math.BigDecimal getGlDebitAmount() {
        return glDebitAmount;
    }
	
    public void setGlDebitAmount (java.math.BigDecimal glDebitAmount) {
        this.glDebitAmount =  glDebitAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gl_account@
    public String getGlAccount() {
        return glAccount;
    }
	
    public void setGlAccount (String glAccount) {
        this.glAccount =  glAccount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_payable_type@
    public Integer getAccountPayableType() {
        return accountPayableType;
    }
	
    public void setAccountPayableType (Integer accountPayableType) {
        this.accountPayableType =  accountPayableType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_invoice_number@
    public String getVendorInvoiceNumber() {
        return vendorInvoiceNumber;
    }
	
    public void setVendorInvoiceNumber (String vendorInvoiceNumber) {
        this.vendorInvoiceNumber =  vendorInvoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_name@
    public String getVendorName() {
        return vendorName;
    }
	
    public void setVendorName (String vendorName) {
        this.vendorName =  vendorName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-purchase_order_number@
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }
	
    public void setPurchaseOrderNumber (String purchaseOrderNumber) {
        this.purchaseOrderNumber =  purchaseOrderNumber;
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


    public AccountPayableBatch getAccountPayableBatch () {
    	return accountPayableBatch;
    }
	
    public void setAccountPayableBatch (AccountPayableBatch accountPayableBatch) {
    	this.accountPayableBatch = accountPayableBatch;
    }

    public Integer getAccountPayableBatchId() {
        return accountPayableBatchId;
    }
	
    public void setAccountPayableBatchId (Integer accountPayableBatch) {
        this.accountPayableBatchId =  accountPayableBatch;
    }
	
    public PurchaseOrderHistory getPurchaseOrderHistory () {
    	return purchaseOrderHistory;
    }
	
    public void setPurchaseOrderHistory (PurchaseOrderHistory purchaseOrderHistory) {
    	this.purchaseOrderHistory = purchaseOrderHistory;
    }

    public Integer getPurchaseOrderHistoryId() {
        return purchaseOrderHistoryId;
    }
	
    public void setPurchaseOrderHistoryId (Integer purchaseOrderHistory) {
        this.purchaseOrderHistoryId =  purchaseOrderHistory;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-account_payable_history@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-account_payable_history@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

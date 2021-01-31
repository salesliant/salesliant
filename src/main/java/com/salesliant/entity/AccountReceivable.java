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
 * <p>Title: AccountReceivable</p>
 *
 * <p>Description: Domain Object describing a AccountReceivable entity</p>
 *
 */
@Entity (name="AccountReceivable")
@Table (name="account_receivable")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class AccountReceivable implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_INVOICE_NUMBER = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_TOTAL_AMOUNT = java.math.BigDecimal.valueOf(00000000000.0000);
	public static final java.math.BigDecimal __DEFAULT_BALANCE_AMOUNT = java.math.BigDecimal.valueOf(00000000000.0000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_AMOUNT = java.math.BigDecimal.valueOf(00000000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PAID_AMOUNT = java.math.BigDecimal.valueOf(00000000000.0000);
	public static final java.math.BigDecimal __DEFAULT_GL_CREDIT_AMOUNT = java.math.BigDecimal.valueOf(00000000000.0000);
	public static final java.math.BigDecimal __DEFAULT_GL_DEBIT_AMOUNT = java.math.BigDecimal.valueOf(00000000000.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_number@
    @Column(name="invoice_number"   , nullable=false , unique=false)
    private Integer invoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transaction_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transaction_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transaction_id@
    @Column(name="transaction_id"  , length=14 , nullable=true , unique=false)
    private String transaction; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name@
    @Column(name="employee_name"  , length=64 , nullable=true , unique=false)
    private String employeeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_receivable_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_receivable_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_receivable_number@
    @Column(name="account_receivable_number"   , nullable=false , unique=false)
    private Integer accountReceivableNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_receivable_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_receivable_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_receivable_type@
    @Column(name="account_receivable_type"  , length=3 , nullable=false , unique=false)
    private String accountReceivableType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @posted_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @posted_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-posted_tag@
    @Column(name="posted_tag"   , nullable=false , unique=false)
    private Boolean postedTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_processed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_processed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_processed@
    @Column(name="date_processed"   , nullable=true , unique=false)
    private Date dateProcessed; 
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
    @Column(name="total_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal totalAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @balance_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @balance_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-balance_amount@
    @Column(name="balance_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal balanceAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_amount@
    @Column(name="discount_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal discountAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @paid_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @paid_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-paid_amount@
    @Column(name="paid_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal paidAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_credit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_credit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_credit_amount@
    @Column(name="gl_credit_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal glCreditAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_debit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_debit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_debit_amount@
    @Column(name="gl_debit_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal glDebitAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_account-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_account-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_account@
    @Column(name="gl_account"  , length=32 , nullable=true , unique=false)
    private String glAccount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @terms-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @terms-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-terms@
    @Column(name="terms"  , length=32 , nullable=true , unique=false)
    private String terms; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @collectable-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @collectable-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-collectable@
    @Column(name="collectable"   , nullable=true , unique=false)
    private Boolean collectable; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="account_receivable_payment_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private AccountReceivable accountReceivablePayment;  

    @Column(name="account_receivable_payment_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer accountReceivablePaymentId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Batch batch;  

    @Column(name="batch_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer batchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-field-account_receivable@
    @OneToMany (targetEntity=com.salesliant.entity.AccountReceivable.class, fetch=FetchType.LAZY, mappedBy="accountReceivablePayment", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountReceivable> accountReceivables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoices-field-account_receivable@
    @OneToMany (targetEntity=com.salesliant.entity.Invoice.class, fetch=FetchType.LAZY, mappedBy="ar", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Invoice> invoices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @payments-field-account_receivable@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="accountReceivable", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public AccountReceivable() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_number@
    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }
	
    public void setInvoiceNumber (Integer invoiceNumber) {
        this.invoiceNumber =  invoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transaction_id@
    public String getTransaction() {
        return transaction;
    }
	
    public void setTransaction (String transaction) {
        this.transaction =  transaction;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_receivable_number@
    public Integer getAccountReceivableNumber() {
        return accountReceivableNumber;
    }
	
    public void setAccountReceivableNumber (Integer accountReceivableNumber) {
        this.accountReceivableNumber =  accountReceivableNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_receivable_type@
    public String getAccountReceivableType() {
        return accountReceivableType;
    }
	
    public void setAccountReceivableType (String accountReceivableType) {
        this.accountReceivableType =  accountReceivableType;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-posted_tag@
    public Boolean getPostedTag() {
        return postedTag;
    }
	
    public void setPostedTag (Boolean postedTag) {
        this.postedTag =  postedTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_processed@
    public Date getDateProcessed() {
        return dateProcessed;
    }
	
    public void setDateProcessed (Date dateProcessed) {
        this.dateProcessed =  dateProcessed;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-balance_amount@
    public java.math.BigDecimal getBalanceAmount() {
        return balanceAmount;
    }
	
    public void setBalanceAmount (java.math.BigDecimal balanceAmount) {
        this.balanceAmount =  balanceAmount;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-terms@
    public String getTerms() {
        return terms;
    }
	
    public void setTerms (String terms) {
        this.terms =  terms;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-note@
    public String getNote() {
        return note;
    }
	
    public void setNote (String note) {
        this.note =  note;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-collectable@
    public Boolean getCollectable() {
        return collectable;
    }
	
    public void setCollectable (Boolean collectable) {
        this.collectable =  collectable;
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


    public AccountReceivable getAccountReceivablePayment () {
    	return accountReceivablePayment;
    }
	
    public void setAccountReceivablePayment (AccountReceivable accountReceivablePayment) {
    	this.accountReceivablePayment = accountReceivablePayment;
    }

    public Integer getAccountReceivablePaymentId() {
        return accountReceivablePaymentId;
    }
	
    public void setAccountReceivablePaymentId (Integer accountReceivablePayment) {
        this.accountReceivablePaymentId =  accountReceivablePayment;
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
	
    public Batch getBatch () {
    	return batch;
    }
	
    public void setBatch (Batch batch) {
    	this.batch = batch;
    }

    public Integer getBatchId() {
        return batchId;
    }
	
    public void setBatchId (Integer batch) {
        this.batchId =  batch;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-getter-account_receivable@
    public List<AccountReceivable> getAccountReceivables() {
        if (accountReceivables == null){
            accountReceivables = new ArrayList<>();
        }
        return accountReceivables;
    }

    public void setAccountReceivables (List<AccountReceivable> accountReceivables) {
        this.accountReceivables = accountReceivables;
    }	
    
    public void addAccountReceivables (AccountReceivable element) {
    	    getAccountReceivables().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoices-getter-account_receivable@
    public List<Invoice> getInvoices() {
        if (invoices == null){
            invoices = new ArrayList<>();
        }
        return invoices;
    }

    public void setInvoices (List<Invoice> invoices) {
        this.invoices = invoices;
    }	
    
    public void addInvoices (Invoice element) {
    	    getInvoices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-account_receivable@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-account_receivable@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (invoiceNumber==null) invoiceNumber=__DEFAULT_INVOICE_NUMBER;
        if (totalAmount==null) totalAmount=__DEFAULT_TOTAL_AMOUNT;
        if (balanceAmount==null) balanceAmount=__DEFAULT_BALANCE_AMOUNT;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (paidAmount==null) paidAmount=__DEFAULT_PAID_AMOUNT;
        if (glCreditAmount==null) glCreditAmount=__DEFAULT_GL_CREDIT_AMOUNT;
        if (glDebitAmount==null) glDebitAmount=__DEFAULT_GL_DEBIT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-account_receivable@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (invoiceNumber==null) invoiceNumber=__DEFAULT_INVOICE_NUMBER;
        if (totalAmount==null) totalAmount=__DEFAULT_TOTAL_AMOUNT;
        if (balanceAmount==null) balanceAmount=__DEFAULT_BALANCE_AMOUNT;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (paidAmount==null) paidAmount=__DEFAULT_PAID_AMOUNT;
        if (glCreditAmount==null) glCreditAmount=__DEFAULT_GL_CREDIT_AMOUNT;
        if (glDebitAmount==null) glDebitAmount=__DEFAULT_GL_DEBIT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

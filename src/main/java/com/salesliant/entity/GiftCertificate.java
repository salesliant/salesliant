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
 * <p>Title: GiftCertificate</p>
 *
 * <p>Description: Domain Object describing a GiftCertificate entity</p>
 *
 */
@Entity (name="GiftCertificate")
@Table (name="gift_certificate")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class GiftCertificate implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final String __DEFAULT_CODE = new String("0");
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=50 , nullable=false , unique=false)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_name@
    @Column(name="customer_name"  , length=60 , nullable=false , unique=false)
    private String customerName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @original_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @original_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-original_amount@
    @Column(name="original_amount"   , nullable=false , unique=false)
    private java.math.BigDecimal originalAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @balance-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @balance-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-balance@
    @Column(name="balance"   , nullable=false , unique=false)
    private java.math.BigDecimal balance; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_purchased-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_purchased-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_purchased@
    @Column(name="date_purchased"   , nullable=false , unique=false)
    private Timestamp datePurchased; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=false , unique=false)
    private Boolean status; 
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

    @Column(name="customer_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-field-gift_certificate@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificateTransaction.class, fetch=FetchType.LAZY, mappedBy="giftCertificate", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificateTransaction> giftCertificateTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public GiftCertificate() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_name@
    public String getCustomerName() {
        return customerName;
    }
	
    public void setCustomerName (String customerName) {
        this.customerName =  customerName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-original_amount@
    public java.math.BigDecimal getOriginalAmount() {
        return originalAmount;
    }
	
    public void setOriginalAmount (java.math.BigDecimal originalAmount) {
        this.originalAmount =  originalAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-balance@
    public java.math.BigDecimal getBalance() {
        return balance;
    }
	
    public void setBalance (java.math.BigDecimal balance) {
        this.balance =  balance;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_purchased@
    public Timestamp getDatePurchased() {
        return datePurchased;
    }
	
    public void setDatePurchased (Timestamp datePurchased) {
        this.datePurchased =  datePurchased;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-status@
    public Boolean getStatus() {
        return status;
    }
	
    public void setStatus (Boolean status) {
        this.status =  status;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-getter-gift_certificate@
    public List<GiftCertificateTransaction> getGiftCertificateTransactions() {
        if (giftCertificateTransactions == null){
            giftCertificateTransactions = new ArrayList<>();
        }
        return giftCertificateTransactions;
    }

    public void setGiftCertificateTransactions (List<GiftCertificateTransaction> giftCertificateTransactions) {
        this.giftCertificateTransactions = giftCertificateTransactions;
    }	
    
    public void addGiftCertificateTransactions (GiftCertificateTransaction element) {
    	    getGiftCertificateTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-gift_certificate@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (code==null) code=__DEFAULT_CODE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-gift_certificate@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (code==null) code=__DEFAULT_CODE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

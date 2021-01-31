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
 * <p>Title: Payment</p>
 *
 * <p>Description: Domain Object describing a Payment entity</p>
 *
 */
@Entity (name="Payment")
@Table (name="payment")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_TENDERED_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CHANGE_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @tendered_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tendered_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tendered_amount@
    @Column(name="tendered_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal tenderedAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @change_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @change_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-change_amount@
    @Column(name="change_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal changeAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_name@
    @Column(name="account_name"  , length=64 , nullable=true , unique=false)
    private String accountName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_number@
    @Column(name="credit_card_number"  , length=32 , nullable=true , unique=false)
    private String creditCardNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_expiration-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_expiration-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_expiration@
    @Column(name="credit_card_expiration"  , length=16 , nullable=true , unique=false)
    private String creditCardExpiration; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_approval_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_approval_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_approval_code@
    @Column(name="credit_card_approval_code"  , length=32 , nullable=true , unique=false)
    private String creditCardApprovalCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @license-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @license-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-license@
    @Column(name="license"  , length=32 , nullable=true , unique=false)
    private String license; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-state@
    @Column(name="state"  , length=32 , nullable=true , unique=false)
    private String state; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @birth_Date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @birth_Date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-birth_Date@
    @Column(name="birth_Date"   , nullable=true , unique=false)
    private Date birthDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_number@
    @Column(name="check_number"  , length=32 , nullable=true , unique=false)
    private String checkNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bank_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bank_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bank_number@
    @Column(name="bank_number"  , length=64 , nullable=true , unique=false)
    private String bankNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transit_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transit_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transit_number@
    @Column(name="transit_number"  , length=32 , nullable=true , unique=false)
    private String transitNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @coupon_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @coupon_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-coupon_code@
    @Column(name="coupon_code"  , length=32 , nullable=true , unique=false)
    private String couponCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gift_certificate_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gift_certificate_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gift_certificate_number@
    @Column(name="gift_certificate_number"  , length=32 , nullable=true , unique=false)
    private String giftCertificateNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="account_receivable_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private AccountReceivable accountReceivable;  

    @Column(name="account_receivable_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer accountReceivableId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="batch_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Batch batch;  

    @Column(name="batch_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer batchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="deposit_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Deposit deposit;  

    @Column(name="deposit_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer depositId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="drop_payout_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private DropPayout dropPayout;  

    @Column(name="drop_payout_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer dropPayoutId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="merchant_authorization_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private MerchantAuthorization merchantAuthorization;  

    @Column(name="merchant_authorization_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer merchantAuthorizationId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="payment_type_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private PaymentType paymentType;  

    @Column(name="payment_type_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer paymentTypeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @signatures-field-payment@
    @OneToMany (targetEntity=com.salesliant.entity.Signature.class, fetch=FetchType.LAZY, mappedBy="paymentEntry", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Signature> signatures = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voucherEntries-field-payment@
    @OneToMany (targetEntity=com.salesliant.entity.VoucherEntry.class, fetch=FetchType.LAZY, mappedBy="paymentEntry", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VoucherEntry> voucherEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Payment() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tendered_amount@
    public java.math.BigDecimal getTenderedAmount() {
        return tenderedAmount;
    }
	
    public void setTenderedAmount (java.math.BigDecimal tenderedAmount) {
        this.tenderedAmount =  tenderedAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-change_amount@
    public java.math.BigDecimal getChangeAmount() {
        return changeAmount;
    }
	
    public void setChangeAmount (java.math.BigDecimal changeAmount) {
        this.changeAmount =  changeAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_name@
    public String getAccountName() {
        return accountName;
    }
	
    public void setAccountName (String accountName) {
        this.accountName =  accountName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_card_number@
    public String getCreditCardNumber() {
        return creditCardNumber;
    }
	
    public void setCreditCardNumber (String creditCardNumber) {
        this.creditCardNumber =  creditCardNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_card_expiration@
    public String getCreditCardExpiration() {
        return creditCardExpiration;
    }
	
    public void setCreditCardExpiration (String creditCardExpiration) {
        this.creditCardExpiration =  creditCardExpiration;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_card_approval_code@
    public String getCreditCardApprovalCode() {
        return creditCardApprovalCode;
    }
	
    public void setCreditCardApprovalCode (String creditCardApprovalCode) {
        this.creditCardApprovalCode =  creditCardApprovalCode;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-state@
    public String getState() {
        return state;
    }
	
    public void setState (String state) {
        this.state =  state;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-birth_Date@
    public Date getBirthDate() {
        return birthDate;
    }
	
    public void setBirthDate (Date birthDate) {
        this.birthDate =  birthDate;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bank_number@
    public String getBankNumber() {
        return bankNumber;
    }
	
    public void setBankNumber (String bankNumber) {
        this.bankNumber =  bankNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transit_number@
    public String getTransitNumber() {
        return transitNumber;
    }
	
    public void setTransitNumber (String transitNumber) {
        this.transitNumber =  transitNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-coupon_code@
    public String getCouponCode() {
        return couponCode;
    }
	
    public void setCouponCode (String couponCode) {
        this.couponCode =  couponCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gift_certificate_number@
    public String getGiftCertificateNumber() {
        return giftCertificateNumber;
    }
	
    public void setGiftCertificateNumber (String giftCertificateNumber) {
        this.giftCertificateNumber =  giftCertificateNumber;
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


    public AccountReceivable getAccountReceivable () {
    	return accountReceivable;
    }
	
    public void setAccountReceivable (AccountReceivable accountReceivable) {
    	this.accountReceivable = accountReceivable;
    }

    public Integer getAccountReceivableId() {
        return accountReceivableId;
    }
	
    public void setAccountReceivableId (Integer accountReceivable) {
        this.accountReceivableId =  accountReceivable;
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
	
    public Deposit getDeposit () {
    	return deposit;
    }
	
    public void setDeposit (Deposit deposit) {
    	this.deposit = deposit;
    }

    public Integer getDepositId() {
        return depositId;
    }
	
    public void setDepositId (Integer deposit) {
        this.depositId =  deposit;
    }
	
    public DropPayout getDropPayout () {
    	return dropPayout;
    }
	
    public void setDropPayout (DropPayout dropPayout) {
    	this.dropPayout = dropPayout;
    }

    public Integer getDropPayoutId() {
        return dropPayoutId;
    }
	
    public void setDropPayoutId (Integer dropPayout) {
        this.dropPayoutId =  dropPayout;
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
	
    public MerchantAuthorization getMerchantAuthorization () {
    	return merchantAuthorization;
    }
	
    public void setMerchantAuthorization (MerchantAuthorization merchantAuthorization) {
    	this.merchantAuthorization = merchantAuthorization;
    }

    public Integer getMerchantAuthorizationId() {
        return merchantAuthorizationId;
    }
	
    public void setMerchantAuthorizationId (Integer merchantAuthorization) {
        this.merchantAuthorizationId =  merchantAuthorization;
    }
	
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @signatures-getter-payment@
    public List<Signature> getSignatures() {
        if (signatures == null){
            signatures = new ArrayList<>();
        }
        return signatures;
    }

    public void setSignatures (List<Signature> signatures) {
        this.signatures = signatures;
    }	
    
    public void addSignatures (Signature element) {
    	    getSignatures().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voucherEntries-getter-payment@
    public List<VoucherEntry> getVoucherEntries() {
        if (voucherEntries == null){
            voucherEntries = new ArrayList<>();
        }
        return voucherEntries;
    }

    public void setVoucherEntries (List<VoucherEntry> voucherEntries) {
        this.voucherEntries = voucherEntries;
    }	
    
    public void addVoucherEntries (VoucherEntry element) {
    	    getVoucherEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-payment@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (tenderedAmount==null) tenderedAmount=__DEFAULT_TENDERED_AMOUNT;
        if (changeAmount==null) changeAmount=__DEFAULT_CHANGE_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-payment@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (tenderedAmount==null) tenderedAmount=__DEFAULT_TENDERED_AMOUNT;
        if (changeAmount==null) changeAmount=__DEFAULT_CHANGE_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

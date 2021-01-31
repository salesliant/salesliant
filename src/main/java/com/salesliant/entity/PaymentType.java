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
 * <p>Title: PaymentType</p>
 *
 * <p>Description: Domain Object describing a PaymentType entity</p>
 *
 */
@Entity (name="PaymentType")
@Table (name="payment_type")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class PaymentType implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERIFICATION_TYPE = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=128 , nullable=false , unique=false)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-amount@
    @Column(name="amount"   , nullable=true , unique=false)
    private java.math.BigDecimal amount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_order@
    @Column(name="display_order"   , nullable=true , unique=false)
    private Integer displayOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @verification_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @verification_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-verification_type@
    @Column(name="verification_type"   , nullable=true , unique=false)
    private Integer verificationType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @currency_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @currency_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-currency_id@
    @Column(name="currency_id"   , nullable=true , unique=true)
    private Integer currency; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_account-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_account-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_account@
    @Column(name="gl_account"  , length=32 , nullable=true , unique=false)
    private String glAccount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @maximum_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @maximum_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-maximum_amount@
    @Column(name="maximum_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal maximumAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @prevent_over_tendering-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @prevent_over_tendering-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-prevent_over_tendering@
    @Column(name="prevent_over_tendering"   , nullable=false , unique=false)
    private Boolean preventOverTendering; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @do_not_pop_cash_drawer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @do_not_pop_cash_drawer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-do_not_pop_cash_drawer@
    @Column(name="do_not_pop_cash_drawer"   , nullable=false , unique=false)
    private Boolean doNotPopCashDrawer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @verify_via_edc-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @verify_via_edc-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-verify_via_edc@
    @Column(name="verify_via_edc"   , nullable=true , unique=false)
    private Boolean verifyViaEdc; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer_validation-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer_validation-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer_validation@
    @Column(name="printer_validation"   , nullable=true , unique=false)
    private Boolean printerValidation; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @validation_line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @validation_line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-validation_line1@
    @Column(name="validation_line1"  , length=32 , nullable=true , unique=false)
    private String validationLine1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @validation_line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @validation_line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-validation_line2@
    @Column(name="validation_line2"  , length=32 , nullable=true , unique=false)
    private String validationLine2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @validation_line3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @validation_line3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-validation_line3@
    @Column(name="validation_line3"  , length=32 , nullable=true , unique=false)
    private String validationLine3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @validation_mask-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @validation_mask-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-validation_mask@
    @Column(name="validation_mask"  , length=32 , nullable=true , unique=false)
    private String validationMask; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scan_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scan_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scan_code@
    @Column(name="scan_code"   , nullable=true , unique=false)
    private Integer scanCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @round_to_value-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @round_to_value-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-round_to_value@
    @Column(name="round_to_value"   , nullable=true , unique=false)
    private java.math.BigDecimal roundToValue; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @signature_required-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @signature_required-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-signature_required@
    @Column(name="signature_required"   , nullable=true , unique=false)
    private Boolean signatureRequired; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @is_net_term-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @is_net_term-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-is_net_term@
    @Column(name="is_net_term"   , nullable=true , unique=false)
    private Boolean isNetTerm; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerGroups-field-payment_type@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerGroup.class, fetch=FetchType.LAZY, mappedBy="paymentType", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerGroup> customerGroups = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceRecurrings-field-payment_type@
    @OneToMany (targetEntity=com.salesliant.entity.InvoiceRecurring.class, fetch=FetchType.LAZY, mappedBy="paymentType", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <InvoiceRecurring> invoiceRecurrings = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-field-payment_type@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="paymentType", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public PaymentType() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-amount@
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	
    public void setAmount (java.math.BigDecimal amount) {
        this.amount =  amount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_order@
    public Integer getDisplayOrder() {
        return displayOrder;
    }
	
    public void setDisplayOrder (Integer displayOrder) {
        this.displayOrder =  displayOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-verification_type@
    public Integer getVerificationType() {
        return verificationType;
    }
	
    public void setVerificationType (Integer verificationType) {
        this.verificationType =  verificationType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-currency_id@
    public Integer getCurrency() {
        return currency;
    }
	
    public void setCurrency (Integer currency) {
        this.currency =  currency;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-maximum_amount@
    public java.math.BigDecimal getMaximumAmount() {
        return maximumAmount;
    }
	
    public void setMaximumAmount (java.math.BigDecimal maximumAmount) {
        this.maximumAmount =  maximumAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-prevent_over_tendering@
    public Boolean getPreventOverTendering() {
        return preventOverTendering;
    }
	
    public void setPreventOverTendering (Boolean preventOverTendering) {
        this.preventOverTendering =  preventOverTendering;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-do_not_pop_cash_drawer@
    public Boolean getDoNotPopCashDrawer() {
        return doNotPopCashDrawer;
    }
	
    public void setDoNotPopCashDrawer (Boolean doNotPopCashDrawer) {
        this.doNotPopCashDrawer =  doNotPopCashDrawer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-verify_via_edc@
    public Boolean getVerifyViaEdc() {
        return verifyViaEdc;
    }
	
    public void setVerifyViaEdc (Boolean verifyViaEdc) {
        this.verifyViaEdc =  verifyViaEdc;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer_validation@
    public Boolean getPrinterValidation() {
        return printerValidation;
    }
	
    public void setPrinterValidation (Boolean printerValidation) {
        this.printerValidation =  printerValidation;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-validation_line1@
    public String getValidationLine1() {
        return validationLine1;
    }
	
    public void setValidationLine1 (String validationLine1) {
        this.validationLine1 =  validationLine1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-validation_line2@
    public String getValidationLine2() {
        return validationLine2;
    }
	
    public void setValidationLine2 (String validationLine2) {
        this.validationLine2 =  validationLine2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-validation_line3@
    public String getValidationLine3() {
        return validationLine3;
    }
	
    public void setValidationLine3 (String validationLine3) {
        this.validationLine3 =  validationLine3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-validation_mask@
    public String getValidationMask() {
        return validationMask;
    }
	
    public void setValidationMask (String validationMask) {
        this.validationMask =  validationMask;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scan_code@
    public Integer getScanCode() {
        return scanCode;
    }
	
    public void setScanCode (Integer scanCode) {
        this.scanCode =  scanCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-round_to_value@
    public java.math.BigDecimal getRoundToValue() {
        return roundToValue;
    }
	
    public void setRoundToValue (java.math.BigDecimal roundToValue) {
        this.roundToValue =  roundToValue;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-signature_required@
    public Boolean getSignatureRequired() {
        return signatureRequired;
    }
	
    public void setSignatureRequired (Boolean signatureRequired) {
        this.signatureRequired =  signatureRequired;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-is_net_term@
    public Boolean getIsNetTerm() {
        return isNetTerm;
    }
	
    public void setIsNetTerm (Boolean isNetTerm) {
        this.isNetTerm =  isNetTerm;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerGroups-getter-payment_type@
    public List<CustomerGroup> getCustomerGroups() {
        if (customerGroups == null){
            customerGroups = new ArrayList<>();
        }
        return customerGroups;
    }

    public void setCustomerGroups (List<CustomerGroup> customerGroups) {
        this.customerGroups = customerGroups;
    }	
    
    public void addCustomerGroups (CustomerGroup element) {
    	    getCustomerGroups().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceRecurrings-getter-payment_type@
    public List<InvoiceRecurring> getInvoiceRecurrings() {
        if (invoiceRecurrings == null){
            invoiceRecurrings = new ArrayList<>();
        }
        return invoiceRecurrings;
    }

    public void setInvoiceRecurrings (List<InvoiceRecurring> invoiceRecurrings) {
        this.invoiceRecurrings = invoiceRecurrings;
    }	
    
    public void addInvoiceRecurrings (InvoiceRecurring element) {
    	    getInvoiceRecurrings().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-payment_type@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-payment_type@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (amount==null) amount=__DEFAULT_AMOUNT;
        if (verificationType==null) verificationType=__DEFAULT_VERIFICATION_TYPE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-payment_type@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (amount==null) amount=__DEFAULT_AMOUNT;
        if (verificationType==null) verificationType=__DEFAULT_VERIFICATION_TYPE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

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
 * <p>Title: InvoiceRecurring</p>
 *
 * <p>Description: Domain Object describing a InvoiceRecurring entity</p>
 *
 */
@Entity (name="InvoiceRecurring")
@Table (name="invoice_recurring")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class InvoiceRecurring implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_QUANTITY_OF_RECURRING = Integer.valueOf(0);
	public static final Integer __DEFAULT_MONTHLY_DATE = Integer.valueOf(0);
	public static final Integer __DEFAULT_QUNATITY_OF_MONTHS = Integer.valueOf(0);
	public static final Integer __DEFAULT_QUANTITY_OF_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_QUANTITY_OF_CUSTOM_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @duration_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @duration_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-duration_type@
    @Column(name="duration_type"   , nullable=true , unique=false)
    private Integer durationType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @first_due_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @first_due_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-first_due_date@
    @Column(name="first_due_date"   , nullable=true , unique=false)
    private Date firstDueDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_of_recurring-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_of_recurring-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_of_recurring@
    @Column(name="quantity_of_recurring"   , nullable=true , unique=false)
    private Integer quantityOfRecurring; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @monthly_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @monthly_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-monthly_type@
    @Column(name="monthly_type"   , nullable=true , unique=false)
    private Integer monthlyType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @monthly_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @monthly_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-monthly_date@
    @Column(name="monthly_date"   , nullable=true , unique=false)
    private Integer monthlyDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @qunatity_of_months-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @qunatity_of_months-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-qunatity_of_months@
    @Column(name="qunatity_of_months"   , nullable=true , unique=false)
    private Integer qunatityOfMonths; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_of_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_of_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_of_days@
    @Column(name="quantity_of_days"   , nullable=true , unique=false)
    private Integer quantityOfDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_of_custom_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_of_custom_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_of_custom_days@
    @Column(name="quantity_of_custom_days"   , nullable=true , unique=false)
    private Integer quantityOfCustomDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_number@
    @Column(name="credit_card_number"  , length=32 , nullable=true , unique=false)
    private String creditCardNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_holder-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_holder-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_holder@
    @Column(name="account_holder"  , length=64 , nullable=true , unique=false)
    private String accountHolder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_expiration-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_expiration-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_expiration@
    @Column(name="credit_card_expiration"  , length=16 , nullable=true , unique=false)
    private String creditCardExpiration; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="payment_type_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private PaymentType paymentType;  

    @Column(name="payment_type_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer paymentTypeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="original_invoice_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private SalesOrder originalInvoice;  

    @Column(name="original_invoice_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer originalInvoiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public InvoiceRecurring() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-duration_type@
    public Integer getDurationType() {
        return durationType;
    }
	
    public void setDurationType (Integer durationType) {
        this.durationType =  durationType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-first_due_date@
    public Date getFirstDueDate() {
        return firstDueDate;
    }
	
    public void setFirstDueDate (Date firstDueDate) {
        this.firstDueDate =  firstDueDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_of_recurring@
    public Integer getQuantityOfRecurring() {
        return quantityOfRecurring;
    }
	
    public void setQuantityOfRecurring (Integer quantityOfRecurring) {
        this.quantityOfRecurring =  quantityOfRecurring;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-monthly_type@
    public Integer getMonthlyType() {
        return monthlyType;
    }
	
    public void setMonthlyType (Integer monthlyType) {
        this.monthlyType =  monthlyType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-monthly_date@
    public Integer getMonthlyDate() {
        return monthlyDate;
    }
	
    public void setMonthlyDate (Integer monthlyDate) {
        this.monthlyDate =  monthlyDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-qunatity_of_months@
    public Integer getQunatityOfMonths() {
        return qunatityOfMonths;
    }
	
    public void setQunatityOfMonths (Integer qunatityOfMonths) {
        this.qunatityOfMonths =  qunatityOfMonths;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_of_days@
    public Integer getQuantityOfDays() {
        return quantityOfDays;
    }
	
    public void setQuantityOfDays (Integer quantityOfDays) {
        this.quantityOfDays =  quantityOfDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_of_custom_days@
    public Integer getQuantityOfCustomDays() {
        return quantityOfCustomDays;
    }
	
    public void setQuantityOfCustomDays (Integer quantityOfCustomDays) {
        this.quantityOfCustomDays =  quantityOfCustomDays;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_holder@
    public String getAccountHolder() {
        return accountHolder;
    }
	
    public void setAccountHolder (String accountHolder) {
        this.accountHolder =  accountHolder;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


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
	
    public SalesOrder getOriginalInvoice () {
    	return originalInvoice;
    }
	
    public void setOriginalInvoice (SalesOrder originalInvoice) {
    	this.originalInvoice = originalInvoice;
    }

    public Integer getOriginalInvoiceId() {
        return originalInvoiceId;
    }
	
    public void setOriginalInvoiceId (Integer originalInvoice) {
        this.originalInvoiceId =  originalInvoice;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-invoice_recurring@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantityOfRecurring==null) quantityOfRecurring=__DEFAULT_QUANTITY_OF_RECURRING;
        if (monthlyDate==null) monthlyDate=__DEFAULT_MONTHLY_DATE;
        if (qunatityOfMonths==null) qunatityOfMonths=__DEFAULT_QUNATITY_OF_MONTHS;
        if (quantityOfDays==null) quantityOfDays=__DEFAULT_QUANTITY_OF_DAYS;
        if (quantityOfCustomDays==null) quantityOfCustomDays=__DEFAULT_QUANTITY_OF_CUSTOM_DAYS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-invoice_recurring@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantityOfRecurring==null) quantityOfRecurring=__DEFAULT_QUANTITY_OF_RECURRING;
        if (monthlyDate==null) monthlyDate=__DEFAULT_MONTHLY_DATE;
        if (qunatityOfMonths==null) qunatityOfMonths=__DEFAULT_QUNATITY_OF_MONTHS;
        if (quantityOfDays==null) quantityOfDays=__DEFAULT_QUANTITY_OF_DAYS;
        if (quantityOfCustomDays==null) quantityOfCustomDays=__DEFAULT_QUANTITY_OF_CUSTOM_DAYS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

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
 * <p>Title: Invoice</p>
 *
 * <p>Description: Domain Object describing a Invoice entity</p>
 *
 */
@Entity (name="Invoice")
@Table (name="invoice")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_SUB_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COST = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DEPOSIT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CREDIT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_GIFT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_SHIPPING_CHARGE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TAX_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COMMISSION_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_number@
    @Column(name="invoice_number"   , nullable=true , unique=true)
    private Integer invoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @station_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @station_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-station_number@
    @Column(name="station_number"   , nullable=true , unique=false)
    private Integer stationNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_po_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_po_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_po_number@
    @Column(name="customer_po_number"  , length=32 , nullable=true , unique=false)
    private String customerPoNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_account_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_account_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_account_number@
    @Column(name="customer_account_number"  , length=64 , nullable=true , unique=false)
    private String customerAccountNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-order_number@
    @Column(name="order_number"   , nullable=true , unique=false)
    private Integer orderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_term_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_term_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_term_code@
    @Column(name="customer_term_code"  , length=128 , nullable=true , unique=false)
    private String customerTermCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_zone_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_zone_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_zone_name@
    @Column(name="tax_zone_name"  , length=128 , nullable=true , unique=false)
    private String taxZoneName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @order_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @order_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-order_type@
    @Column(name="order_type"   , nullable=true , unique=false)
    private Integer orderType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_type@
    @Column(name="invoice_type"  , length=1 , nullable=true , unique=false)
    private String invoiceType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sales_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sales_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sales_name@
    @Column(name="sales_name"  , length=128 , nullable=true , unique=false)
    private String salesName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cashier_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cashier_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cashier_name@
    @Column(name="cashier_name"  , length=128 , nullable=true , unique=false)
    private String cashierName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_name@
    @Column(name="customer_name"  , length=128 , nullable=true , unique=false)
    private String customerName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @phone_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @phone_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-phone_number@
    @Column(name="phone_number"  , length=64 , nullable=true , unique=false)
    private String phoneNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address@
    @Column(name="ship_to_address"  , length=65535 , nullable=true , unique=false)
    private String shipToAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fob-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fob-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fob@
    @Column(name="fob"  , length=32 , nullable=true , unique=false)
    private String fob; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_company-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_company-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_company@
    @Column(name="bill_to_company"  , length=128 , nullable=true , unique=false)
    private String billToCompany; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_department-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_department-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_department@
    @Column(name="bill_to_department"  , length=128 , nullable=true , unique=false)
    private String billToDepartment; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_address1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_address1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_address1@
    @Column(name="bill_to_address1"  , length=128 , nullable=true , unique=false)
    private String billToAddress1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_address2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_address2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_address2@
    @Column(name="bill_to_address2"  , length=128 , nullable=true , unique=false)
    private String billToAddress2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_city@
    @Column(name="bill_to_city"  , length=128 , nullable=true , unique=false)
    private String billToCity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_state@
    @Column(name="bill_to_state"  , length=128 , nullable=true , unique=false)
    private String billToState; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_post_code@
    @Column(name="bill_to_post_code"  , length=64 , nullable=true , unique=false)
    private String billToPostCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bill_to_country-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bill_to_country-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bill_to_country@
    @Column(name="bill_to_country"  , length=128 , nullable=true , unique=false)
    private String billToCountry; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_invoiced-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_invoiced-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_invoiced@
    @Column(name="date_invoiced"   , nullable=true , unique=false)
    private Timestamp dateInvoiced; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_ordered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_ordered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_ordered@
    @Column(name="date_ordered"   , nullable=true , unique=false)
    private Date dateOrdered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @posted_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @posted_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-posted_flag@
    @Column(name="posted_flag"   , nullable=true , unique=false)
    private Boolean postedFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_exempt_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_exempt_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_exempt_flag@
    @Column(name="tax_exempt_flag"   , nullable=true , unique=false)
    private Boolean taxExemptFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @voided_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @voided_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-voided_flag@
    @Column(name="voided_flag"   , nullable=true , unique=false)
    private Boolean voidedFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @web_order_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @web_order_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-web_order_flag@
    @Column(name="web_order_flag"   , nullable=true , unique=false)
    private Boolean webOrderFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commission_paid_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commission_paid_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commission_paid_flag@
    @Column(name="commission_paid_flag"   , nullable=true , unique=false)
    private Boolean commissionPaidFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sub_total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sub_total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sub_total@
    @Column(name="sub_total"   , nullable=true , unique=false)
    private java.math.BigDecimal subTotal; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total@
    @Column(name="total"   , nullable=true , unique=false)
    private java.math.BigDecimal total; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @deposit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @deposit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-deposit_amount@
    @Column(name="deposit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal depositAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_amount@
    @Column(name="credit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal creditAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gift_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gift_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gift_amount@
    @Column(name="gift_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal giftAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @shipping_charge-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @shipping_charge-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-shipping_charge@
    @Column(name="shipping_charge"   , nullable=true , unique=false)
    private java.math.BigDecimal shippingCharge; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_amount@
    @Column(name="tax_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal taxAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commission_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commission_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commission_amount@
    @Column(name="commission_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal commissionAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_via-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_via-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_via@
    @Column(name="ship_via"  , length=128 , nullable=true , unique=false)
    private String shipVia; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="ar_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private AccountReceivable ar;  

    @Column(name="ar_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer arId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Batch batch;  

    @Column(name="batch_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer batchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="service_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Service service;  

    @Column(name="service_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer serviceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.AccountReceivable.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountReceivable> accountReceivables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissions-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.Commission.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Commission> commissions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> deposits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificates-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificate.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificate> giftCertificates = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificateTransaction.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificateTransaction> giftCertificateTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @invoiceEntries-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.InvoiceEntry.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <InvoiceEntry> invoiceEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLog.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLog> itemLogs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @payments-field-invoice@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Invoice() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-station_number@
    public Integer getStationNumber() {
        return stationNumber;
    }
	
    public void setStationNumber (Integer stationNumber) {
        this.stationNumber =  stationNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_po_number@
    public String getCustomerPoNumber() {
        return customerPoNumber;
    }
	
    public void setCustomerPoNumber (String customerPoNumber) {
        this.customerPoNumber =  customerPoNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-order_number@
    public Integer getOrderNumber() {
        return orderNumber;
    }
	
    public void setOrderNumber (Integer orderNumber) {
        this.orderNumber =  orderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_term_code@
    public String getCustomerTermCode() {
        return customerTermCode;
    }
	
    public void setCustomerTermCode (String customerTermCode) {
        this.customerTermCode =  customerTermCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_zone_name@
    public String getTaxZoneName() {
        return taxZoneName;
    }
	
    public void setTaxZoneName (String taxZoneName) {
        this.taxZoneName =  taxZoneName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-order_type@
    public Integer getOrderType() {
        return orderType;
    }
	
    public void setOrderType (Integer orderType) {
        this.orderType =  orderType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_type@
    public String getInvoiceType() {
        return invoiceType;
    }
	
    public void setInvoiceType (String invoiceType) {
        this.invoiceType =  invoiceType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sales_name@
    public String getSalesName() {
        return salesName;
    }
	
    public void setSalesName (String salesName) {
        this.salesName =  salesName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cashier_name@
    public String getCashierName() {
        return cashierName;
    }
	
    public void setCashierName (String cashierName) {
        this.cashierName =  cashierName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-phone_number@
    public String getPhoneNumber() {
        return phoneNumber;
    }
	
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber =  phoneNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_address@
    public String getShipToAddress() {
        return shipToAddress;
    }
	
    public void setShipToAddress (String shipToAddress) {
        this.shipToAddress =  shipToAddress;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fob@
    public String getFob() {
        return fob;
    }
	
    public void setFob (String fob) {
        this.fob =  fob;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_company@
    public String getBillToCompany() {
        return billToCompany;
    }
	
    public void setBillToCompany (String billToCompany) {
        this.billToCompany =  billToCompany;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_department@
    public String getBillToDepartment() {
        return billToDepartment;
    }
	
    public void setBillToDepartment (String billToDepartment) {
        this.billToDepartment =  billToDepartment;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_address1@
    public String getBillToAddress1() {
        return billToAddress1;
    }
	
    public void setBillToAddress1 (String billToAddress1) {
        this.billToAddress1 =  billToAddress1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_address2@
    public String getBillToAddress2() {
        return billToAddress2;
    }
	
    public void setBillToAddress2 (String billToAddress2) {
        this.billToAddress2 =  billToAddress2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_city@
    public String getBillToCity() {
        return billToCity;
    }
	
    public void setBillToCity (String billToCity) {
        this.billToCity =  billToCity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_state@
    public String getBillToState() {
        return billToState;
    }
	
    public void setBillToState (String billToState) {
        this.billToState =  billToState;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_post_code@
    public String getBillToPostCode() {
        return billToPostCode;
    }
	
    public void setBillToPostCode (String billToPostCode) {
        this.billToPostCode =  billToPostCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bill_to_country@
    public String getBillToCountry() {
        return billToCountry;
    }
	
    public void setBillToCountry (String billToCountry) {
        this.billToCountry =  billToCountry;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_invoiced@
    public Timestamp getDateInvoiced() {
        return dateInvoiced;
    }
	
    public void setDateInvoiced (Timestamp dateInvoiced) {
        this.dateInvoiced =  dateInvoiced;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_ordered@
    public Date getDateOrdered() {
        return dateOrdered;
    }
	
    public void setDateOrdered (Date dateOrdered) {
        this.dateOrdered =  dateOrdered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-posted_flag@
    public Boolean getPostedFlag() {
        return postedFlag;
    }
	
    public void setPostedFlag (Boolean postedFlag) {
        this.postedFlag =  postedFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_exempt_flag@
    public Boolean getTaxExemptFlag() {
        return taxExemptFlag;
    }
	
    public void setTaxExemptFlag (Boolean taxExemptFlag) {
        this.taxExemptFlag =  taxExemptFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-voided_flag@
    public Boolean getVoidedFlag() {
        return voidedFlag;
    }
	
    public void setVoidedFlag (Boolean voidedFlag) {
        this.voidedFlag =  voidedFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-web_order_flag@
    public Boolean getWebOrderFlag() {
        return webOrderFlag;
    }
	
    public void setWebOrderFlag (Boolean webOrderFlag) {
        this.webOrderFlag =  webOrderFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commission_paid_flag@
    public Boolean getCommissionPaidFlag() {
        return commissionPaidFlag;
    }
	
    public void setCommissionPaidFlag (Boolean commissionPaidFlag) {
        this.commissionPaidFlag =  commissionPaidFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sub_total@
    public java.math.BigDecimal getSubTotal() {
        return subTotal;
    }
	
    public void setSubTotal (java.math.BigDecimal subTotal) {
        this.subTotal =  subTotal;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total@
    public java.math.BigDecimal getTotal() {
        return total;
    }
	
    public void setTotal (java.math.BigDecimal total) {
        this.total =  total;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cost@
    public java.math.BigDecimal getCost() {
        return cost;
    }
	
    public void setCost (java.math.BigDecimal cost) {
        this.cost =  cost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-deposit_amount@
    public java.math.BigDecimal getDepositAmount() {
        return depositAmount;
    }
	
    public void setDepositAmount (java.math.BigDecimal depositAmount) {
        this.depositAmount =  depositAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_amount@
    public java.math.BigDecimal getCreditAmount() {
        return creditAmount;
    }
	
    public void setCreditAmount (java.math.BigDecimal creditAmount) {
        this.creditAmount =  creditAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gift_amount@
    public java.math.BigDecimal getGiftAmount() {
        return giftAmount;
    }
	
    public void setGiftAmount (java.math.BigDecimal giftAmount) {
        this.giftAmount =  giftAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-shipping_charge@
    public java.math.BigDecimal getShippingCharge() {
        return shippingCharge;
    }
	
    public void setShippingCharge (java.math.BigDecimal shippingCharge) {
        this.shippingCharge =  shippingCharge;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_amount@
    public java.math.BigDecimal getTaxAmount() {
        return taxAmount;
    }
	
    public void setTaxAmount (java.math.BigDecimal taxAmount) {
        this.taxAmount =  taxAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commission_amount@
    public java.math.BigDecimal getCommissionAmount() {
        return commissionAmount;
    }
	
    public void setCommissionAmount (java.math.BigDecimal commissionAmount) {
        this.commissionAmount =  commissionAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_via@
    public String getShipVia() {
        return shipVia;
    }
	
    public void setShipVia (String shipVia) {
        this.shipVia =  shipVia;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public AccountReceivable getAr () {
    	return ar;
    }
	
    public void setAr (AccountReceivable ar) {
    	this.ar = ar;
    }

    public Integer getArId() {
        return arId;
    }
	
    public void setArId (Integer ar) {
        this.arId =  ar;
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
	
    public Service getService () {
    	return service;
    }
	
    public void setService (Service service) {
    	this.service = service;
    }

    public Integer getServiceId() {
        return serviceId;
    }
	
    public void setServiceId (Integer service) {
        this.serviceId =  service;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-getter-invoice@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissions-getter-invoice@
    public List<Commission> getCommissions() {
        if (commissions == null){
            commissions = new ArrayList<>();
        }
        return commissions;
    }

    public void setCommissions (List<Commission> commissions) {
        this.commissions = commissions;
    }	
    
    public void addCommissions (Commission element) {
    	    getCommissions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-getter-invoice@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificates-getter-invoice@
    public List<GiftCertificate> getGiftCertificates() {
        if (giftCertificates == null){
            giftCertificates = new ArrayList<>();
        }
        return giftCertificates;
    }

    public void setGiftCertificates (List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }	
    
    public void addGiftCertificates (GiftCertificate element) {
    	    getGiftCertificates().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-getter-invoice@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceEntries-getter-invoice@
    public List<InvoiceEntry> getInvoiceEntries() {
        if (invoiceEntries == null){
            invoiceEntries = new ArrayList<>();
        }
        return invoiceEntries;
    }

    public void setInvoiceEntries (List<InvoiceEntry> invoiceEntries) {
        this.invoiceEntries = invoiceEntries;
    }	
    
    public void addInvoiceEntries (InvoiceEntry element) {
    	    getInvoiceEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-getter-invoice@
    public List<ItemLog> getItemLogs() {
        if (itemLogs == null){
            itemLogs = new ArrayList<>();
        }
        return itemLogs;
    }

    public void setItemLogs (List<ItemLog> itemLogs) {
        this.itemLogs = itemLogs;
    }	
    
    public void addItemLogs (ItemLog element) {
    	    getItemLogs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-invoice@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-invoice@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (subTotal==null) subTotal=__DEFAULT_SUB_TOTAL;
        if (total==null) total=__DEFAULT_TOTAL;
        if (cost==null) cost=__DEFAULT_COST;
        if (depositAmount==null) depositAmount=__DEFAULT_DEPOSIT_AMOUNT;
        if (creditAmount==null) creditAmount=__DEFAULT_CREDIT_AMOUNT;
        if (giftAmount==null) giftAmount=__DEFAULT_GIFT_AMOUNT;
        if (shippingCharge==null) shippingCharge=__DEFAULT_SHIPPING_CHARGE;
        if (taxAmount==null) taxAmount=__DEFAULT_TAX_AMOUNT;
        if (commissionAmount==null) commissionAmount=__DEFAULT_COMMISSION_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-invoice@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (subTotal==null) subTotal=__DEFAULT_SUB_TOTAL;
        if (total==null) total=__DEFAULT_TOTAL;
        if (cost==null) cost=__DEFAULT_COST;
        if (depositAmount==null) depositAmount=__DEFAULT_DEPOSIT_AMOUNT;
        if (creditAmount==null) creditAmount=__DEFAULT_CREDIT_AMOUNT;
        if (giftAmount==null) giftAmount=__DEFAULT_GIFT_AMOUNT;
        if (shippingCharge==null) shippingCharge=__DEFAULT_SHIPPING_CHARGE;
        if (taxAmount==null) taxAmount=__DEFAULT_TAX_AMOUNT;
        if (commissionAmount==null) commissionAmount=__DEFAULT_COMMISSION_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

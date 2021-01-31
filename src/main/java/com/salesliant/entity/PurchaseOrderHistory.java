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
 * <p>Title: PurchaseOrderHistory</p>
 *
 * <p>Description: Domain Object describing a PurchaseOrderHistory entity</p>
 *
 */
@Entity (name="PurchaseOrderHistory")
@Table (name="purchase_order_history")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class PurchaseOrderHistory implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_FREIGHT_INVOICED_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_FREIGHT_PRE_PAID_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TAX_ON_ORDER_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TAX_ON_FREIGHT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @purchase_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @purchase_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-purchase_order_number@
    @Column(name="purchase_order_number"  , length=64 , nullable=true , unique=false)
    private String purchaseOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_shipping_service_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_shipping_service_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_shipping_service_code@
    @Column(name="vendor_shipping_service_code"  , length=64 , nullable=true , unique=false)
    private String vendorShippingServiceCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_account_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_account_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_account_number@
    @Column(name="customer_account_number"  , length=64 , nullable=true , unique=false)
    private String customerAccountNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_term_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_term_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_term_code@
    @Column(name="vendor_term_code"  , length=64 , nullable=true , unique=false)
    private String vendorTermCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_purchased_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_purchased_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_purchased_name@
    @Column(name="employee_purchased_name"  , length=64 , nullable=true , unique=false)
    private String employeePurchasedName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_received_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_received_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_received_name@
    @Column(name="employee_received_name"  , length=64 , nullable=true , unique=false)
    private String employeeReceivedName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total@
    @Column(name="total"   , nullable=true , unique=false)
    private java.math.BigDecimal total; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @freight_invoiced_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @freight_invoiced_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-freight_invoiced_amount@
    @Column(name="freight_invoiced_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal freightInvoicedAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @freight_pre_paid_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @freight_pre_paid_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-freight_pre_paid_amount@
    @Column(name="freight_pre_paid_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal freightPrePaidAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_purchased-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_purchased-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_purchased@
    @Column(name="date_purchased"   , nullable=true , unique=false)
    private Timestamp datePurchased; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Timestamp dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_received@
    @Column(name="date_received"   , nullable=true , unique=false)
    private Date dateReceived; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_invoiced-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_invoiced-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_invoiced@
    @Column(name="date_invoiced"   , nullable=true , unique=false)
    private Date dateInvoiced; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_invoice_number@
    @Column(name="vendor_invoice_number"  , length=32 , nullable=true , unique=false)
    private String vendorInvoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @confirmation_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @confirmation_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-confirmation_number@
    @Column(name="confirmation_number"  , length=32 , nullable=true , unique=false)
    private String confirmationNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_on_order_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_on_order_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_on_order_amount@
    @Column(name="tax_on_order_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal taxOnOrderAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_on_freight_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_on_freight_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_on_freight_amount@
    @Column(name="tax_on_freight_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal taxOnFreightAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_contact_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_contact_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_contact_name@
    @Column(name="vendor_contact_name"  , length=64 , nullable=true , unique=false)
    private String vendorContactName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @posted_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @posted_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-posted_tag@
    @Column(name="posted_tag"   , nullable=true , unique=false)
    private Boolean postedTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_name@
    @Column(name="vendor_name"  , length=64 , nullable=true , unique=false)
    private String vendorName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_code@
    @Column(name="vendor_code"  , length=32 , nullable=true , unique=false)
    private String vendorCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_address1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_address1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_address1@
    @Column(name="vendor_address1"  , length=128 , nullable=true , unique=false)
    private String vendorAddress1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_address2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_address2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_address2@
    @Column(name="vendor_address2"  , length=128 , nullable=true , unique=false)
    private String vendorAddress2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_city@
    @Column(name="vendor_city"  , length=64 , nullable=true , unique=false)
    private String vendorCity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_state@
    @Column(name="vendor_state"  , length=64 , nullable=true , unique=false)
    private String vendorState; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_post_code@
    @Column(name="vendor_post_code"  , length=16 , nullable=true , unique=false)
    private String vendorPostCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_country-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_country-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_country@
    @Column(name="vendor_country"  , length=64 , nullable=true , unique=false)
    private String vendorCountry; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_phone_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_phone_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_phone_number@
    @Column(name="vendor_phone_number"  , length=64 , nullable=true , unique=false)
    private String vendorPhoneNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_fax_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_fax_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_fax_number@
    @Column(name="vendor_fax_number"  , length=64 , nullable=true , unique=false)
    private String vendorFaxNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address@
    @Column(name="ship_to_address"  , length=65535 , nullable=true , unique=false)
    private String shipToAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_name@
    @Column(name="ship_to_name"  , length=64 , nullable=true , unique=false)
    private String shipToName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_contact-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_contact-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_contact@
    @Column(name="ship_to_contact"  , length=64 , nullable=true , unique=false)
    private String shipToContact; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address1@
    @Column(name="ship_to_address1"  , length=128 , nullable=true , unique=false)
    private String shipToAddress1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address2@
    @Column(name="ship_to_address2"  , length=128 , nullable=true , unique=false)
    private String shipToAddress2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_city@
    @Column(name="ship_to_city"  , length=64 , nullable=true , unique=false)
    private String shipToCity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_state@
    @Column(name="ship_to_state"  , length=64 , nullable=true , unique=false)
    private String shipToState; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_post_code@
    @Column(name="ship_to_post_code"  , length=64 , nullable=true , unique=false)
    private String shipToPostCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_country-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_country-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_country@
    @Column(name="ship_to_country"  , length=64 , nullable=true , unique=false)
    private String shipToCountry; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_phone_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_phone_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_phone_number@
    @Column(name="ship_to_phone_number"  , length=64 , nullable=true , unique=false)
    private String shipToPhoneNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_fax_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_fax_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_fax_number@
    @Column(name="ship_to_fax_number"  , length=64 , nullable=true , unique=false)
    private String shipToFaxNumber; 
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
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Vendor vendor;  

    @Column(name="vendor_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer vendorId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_shipping_service_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private VendorShippingService vendorShippingService;  

    @Column(name="vendor_shipping_service_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer vendorShippingServiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_term_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private VendorTerm vendorTerm;  

    @Column(name="vendor_term_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer vendorTermId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayables-field-purchase_order_history@
    @OneToMany (targetEntity=com.salesliant.entity.AccountPayable.class, fetch=FetchType.LAZY, mappedBy="purchaseOrderHistory", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountPayable> accountPayables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayableHistories-field-purchase_order_history@
    @OneToMany (targetEntity=com.salesliant.entity.AccountPayableHistory.class, fetch=FetchType.LAZY, mappedBy="purchaseOrderHistory", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountPayableHistory> accountPayableHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-field-purchase_order_history@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLog.class, fetch=FetchType.LAZY, mappedBy="purchaseOrderHistory", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLog> itemLogs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @purchaseOrderHistoryEntries-field-purchase_order_history@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistoryEntry.class, fetch=FetchType.LAZY, mappedBy="purchaseOrderHistory", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public PurchaseOrderHistory() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-purchase_order_number@
    public String getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }
	
    public void setPurchaseOrderNumber (String purchaseOrderNumber) {
        this.purchaseOrderNumber =  purchaseOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_shipping_service_code@
    public String getVendorShippingServiceCode() {
        return vendorShippingServiceCode;
    }
	
    public void setVendorShippingServiceCode (String vendorShippingServiceCode) {
        this.vendorShippingServiceCode =  vendorShippingServiceCode;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_term_code@
    public String getVendorTermCode() {
        return vendorTermCode;
    }
	
    public void setVendorTermCode (String vendorTermCode) {
        this.vendorTermCode =  vendorTermCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_purchased_name@
    public String getEmployeePurchasedName() {
        return employeePurchasedName;
    }
	
    public void setEmployeePurchasedName (String employeePurchasedName) {
        this.employeePurchasedName =  employeePurchasedName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_received_name@
    public String getEmployeeReceivedName() {
        return employeeReceivedName;
    }
	
    public void setEmployeeReceivedName (String employeeReceivedName) {
        this.employeeReceivedName =  employeeReceivedName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-freight_invoiced_amount@
    public java.math.BigDecimal getFreightInvoicedAmount() {
        return freightInvoicedAmount;
    }
	
    public void setFreightInvoicedAmount (java.math.BigDecimal freightInvoicedAmount) {
        this.freightInvoicedAmount =  freightInvoicedAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-freight_pre_paid_amount@
    public java.math.BigDecimal getFreightPrePaidAmount() {
        return freightPrePaidAmount;
    }
	
    public void setFreightPrePaidAmount (java.math.BigDecimal freightPrePaidAmount) {
        this.freightPrePaidAmount =  freightPrePaidAmount;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_created@
    public Timestamp getDateCreated() {
        return dateCreated;
    }
	
    public void setDateCreated (Timestamp dateCreated) {
        this.dateCreated =  dateCreated;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_received@
    public Date getDateReceived() {
        return dateReceived;
    }
	
    public void setDateReceived (Date dateReceived) {
        this.dateReceived =  dateReceived;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_invoice_number@
    public String getVendorInvoiceNumber() {
        return vendorInvoiceNumber;
    }
	
    public void setVendorInvoiceNumber (String vendorInvoiceNumber) {
        this.vendorInvoiceNumber =  vendorInvoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-confirmation_number@
    public String getConfirmationNumber() {
        return confirmationNumber;
    }
	
    public void setConfirmationNumber (String confirmationNumber) {
        this.confirmationNumber =  confirmationNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_on_order_amount@
    public java.math.BigDecimal getTaxOnOrderAmount() {
        return taxOnOrderAmount;
    }
	
    public void setTaxOnOrderAmount (java.math.BigDecimal taxOnOrderAmount) {
        this.taxOnOrderAmount =  taxOnOrderAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_on_freight_amount@
    public java.math.BigDecimal getTaxOnFreightAmount() {
        return taxOnFreightAmount;
    }
	
    public void setTaxOnFreightAmount (java.math.BigDecimal taxOnFreightAmount) {
        this.taxOnFreightAmount =  taxOnFreightAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_contact_name@
    public String getVendorContactName() {
        return vendorContactName;
    }
	
    public void setVendorContactName (String vendorContactName) {
        this.vendorContactName =  vendorContactName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_name@
    public String getVendorName() {
        return vendorName;
    }
	
    public void setVendorName (String vendorName) {
        this.vendorName =  vendorName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_code@
    public String getVendorCode() {
        return vendorCode;
    }
	
    public void setVendorCode (String vendorCode) {
        this.vendorCode =  vendorCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_address1@
    public String getVendorAddress1() {
        return vendorAddress1;
    }
	
    public void setVendorAddress1 (String vendorAddress1) {
        this.vendorAddress1 =  vendorAddress1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_address2@
    public String getVendorAddress2() {
        return vendorAddress2;
    }
	
    public void setVendorAddress2 (String vendorAddress2) {
        this.vendorAddress2 =  vendorAddress2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_city@
    public String getVendorCity() {
        return vendorCity;
    }
	
    public void setVendorCity (String vendorCity) {
        this.vendorCity =  vendorCity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_state@
    public String getVendorState() {
        return vendorState;
    }
	
    public void setVendorState (String vendorState) {
        this.vendorState =  vendorState;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_post_code@
    public String getVendorPostCode() {
        return vendorPostCode;
    }
	
    public void setVendorPostCode (String vendorPostCode) {
        this.vendorPostCode =  vendorPostCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_country@
    public String getVendorCountry() {
        return vendorCountry;
    }
	
    public void setVendorCountry (String vendorCountry) {
        this.vendorCountry =  vendorCountry;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_phone_number@
    public String getVendorPhoneNumber() {
        return vendorPhoneNumber;
    }
	
    public void setVendorPhoneNumber (String vendorPhoneNumber) {
        this.vendorPhoneNumber =  vendorPhoneNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_fax_number@
    public String getVendorFaxNumber() {
        return vendorFaxNumber;
    }
	
    public void setVendorFaxNumber (String vendorFaxNumber) {
        this.vendorFaxNumber =  vendorFaxNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_name@
    public String getShipToName() {
        return shipToName;
    }
	
    public void setShipToName (String shipToName) {
        this.shipToName =  shipToName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_contact@
    public String getShipToContact() {
        return shipToContact;
    }
	
    public void setShipToContact (String shipToContact) {
        this.shipToContact =  shipToContact;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_address1@
    public String getShipToAddress1() {
        return shipToAddress1;
    }
	
    public void setShipToAddress1 (String shipToAddress1) {
        this.shipToAddress1 =  shipToAddress1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_address2@
    public String getShipToAddress2() {
        return shipToAddress2;
    }
	
    public void setShipToAddress2 (String shipToAddress2) {
        this.shipToAddress2 =  shipToAddress2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_city@
    public String getShipToCity() {
        return shipToCity;
    }
	
    public void setShipToCity (String shipToCity) {
        this.shipToCity =  shipToCity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_state@
    public String getShipToState() {
        return shipToState;
    }
	
    public void setShipToState (String shipToState) {
        this.shipToState =  shipToState;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_post_code@
    public String getShipToPostCode() {
        return shipToPostCode;
    }
	
    public void setShipToPostCode (String shipToPostCode) {
        this.shipToPostCode =  shipToPostCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_country@
    public String getShipToCountry() {
        return shipToCountry;
    }
	
    public void setShipToCountry (String shipToCountry) {
        this.shipToCountry =  shipToCountry;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_phone_number@
    public String getShipToPhoneNumber() {
        return shipToPhoneNumber;
    }
	
    public void setShipToPhoneNumber (String shipToPhoneNumber) {
        this.shipToPhoneNumber =  shipToPhoneNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_fax_number@
    public String getShipToFaxNumber() {
        return shipToFaxNumber;
    }
	
    public void setShipToFaxNumber (String shipToFaxNumber) {
        this.shipToFaxNumber =  shipToFaxNumber;
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
	
    public Vendor getVendor () {
    	return vendor;
    }
	
    public void setVendor (Vendor vendor) {
    	this.vendor = vendor;
    }

    public Integer getVendorId() {
        return vendorId;
    }
	
    public void setVendorId (Integer vendor) {
        this.vendorId =  vendor;
    }
	
    public VendorShippingService getVendorShippingService () {
    	return vendorShippingService;
    }
	
    public void setVendorShippingService (VendorShippingService vendorShippingService) {
    	this.vendorShippingService = vendorShippingService;
    }

    public Integer getVendorShippingServiceId() {
        return vendorShippingServiceId;
    }
	
    public void setVendorShippingServiceId (Integer vendorShippingService) {
        this.vendorShippingServiceId =  vendorShippingService;
    }
	
    public VendorTerm getVendorTerm () {
    	return vendorTerm;
    }
	
    public void setVendorTerm (VendorTerm vendorTerm) {
    	this.vendorTerm = vendorTerm;
    }

    public Integer getVendorTermId() {
        return vendorTermId;
    }
	
    public void setVendorTermId (Integer vendorTerm) {
        this.vendorTermId =  vendorTerm;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayables-getter-purchase_order_history@
    public List<AccountPayable> getAccountPayables() {
        if (accountPayables == null){
            accountPayables = new ArrayList<>();
        }
        return accountPayables;
    }

    public void setAccountPayables (List<AccountPayable> accountPayables) {
        this.accountPayables = accountPayables;
    }	
    
    public void addAccountPayables (AccountPayable element) {
    	    getAccountPayables().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayableHistories-getter-purchase_order_history@
    public List<AccountPayableHistory> getAccountPayableHistories() {
        if (accountPayableHistories == null){
            accountPayableHistories = new ArrayList<>();
        }
        return accountPayableHistories;
    }

    public void setAccountPayableHistories (List<AccountPayableHistory> accountPayableHistories) {
        this.accountPayableHistories = accountPayableHistories;
    }	
    
    public void addAccountPayableHistories (AccountPayableHistory element) {
    	    getAccountPayableHistories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-getter-purchase_order_history@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistoryEntries-getter-purchase_order_history@
    public List<PurchaseOrderHistoryEntry> getPurchaseOrderHistoryEntries() {
        if (purchaseOrderHistoryEntries == null){
            purchaseOrderHistoryEntries = new ArrayList<>();
        }
        return purchaseOrderHistoryEntries;
    }

    public void setPurchaseOrderHistoryEntries (List<PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries) {
        this.purchaseOrderHistoryEntries = purchaseOrderHistoryEntries;
    }	
    
    public void addPurchaseOrderHistoryEntries (PurchaseOrderHistoryEntry element) {
    	    getPurchaseOrderHistoryEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-purchase_order_history@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (total==null) total=__DEFAULT_TOTAL;
        if (freightInvoicedAmount==null) freightInvoicedAmount=__DEFAULT_FREIGHT_INVOICED_AMOUNT;
        if (freightPrePaidAmount==null) freightPrePaidAmount=__DEFAULT_FREIGHT_PRE_PAID_AMOUNT;
        if (taxOnOrderAmount==null) taxOnOrderAmount=__DEFAULT_TAX_ON_ORDER_AMOUNT;
        if (taxOnFreightAmount==null) taxOnFreightAmount=__DEFAULT_TAX_ON_FREIGHT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-purchase_order_history@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (total==null) total=__DEFAULT_TOTAL;
        if (freightInvoicedAmount==null) freightInvoicedAmount=__DEFAULT_FREIGHT_INVOICED_AMOUNT;
        if (freightPrePaidAmount==null) freightPrePaidAmount=__DEFAULT_FREIGHT_PRE_PAID_AMOUNT;
        if (taxOnOrderAmount==null) taxOnOrderAmount=__DEFAULT_TAX_ON_ORDER_AMOUNT;
        if (taxOnFreightAmount==null) taxOnFreightAmount=__DEFAULT_TAX_ON_FREIGHT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

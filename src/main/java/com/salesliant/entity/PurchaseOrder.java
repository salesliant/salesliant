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
 * <p>Title: PurchaseOrder</p>
 *
 * <p>Description: Domain Object describing a PurchaseOrder entity</p>
 *
 */
@Entity (name="PurchaseOrder")
@Table (name="purchase_order")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class PurchaseOrder implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_STATUS = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TOTAL_RECEIVED = java.math.BigDecimal.valueOf(0.0000);
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
    @Column(name="purchase_order_number"   , nullable=true , unique=false)
    private Integer purchaseOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sub_store_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sub_store_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sub_store_id@
    @Column(name="sub_store_id"   , nullable=true , unique=false)
    private Integer subStore; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sub_purchase_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sub_purchase_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sub_purchase_order_number@
    @Column(name="sub_purchase_order_number"   , nullable=true , unique=false)
    private Integer subPurchaseOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @purchase_order_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @purchase_order_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-purchase_order_type@
    @Column(name="purchase_order_type"   , nullable=true , unique=false)
    private Integer purchaseOrderType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @total_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total_received@
    @Column(name="total_received"   , nullable=true , unique=false)
    private java.math.BigDecimal totalReceived; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @date_expected-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_expected-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_expected@
    @Column(name="date_expected"   , nullable=true , unique=false)
    private Date dateExpected; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_days@
    @Column(name="discount_days"   , nullable=true , unique=false)
    private Integer discountDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_percent-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_percent-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_percent@
    @Column(name="discount_percent"   , nullable=true , unique=false)
    private java.math.BigDecimal discountPercent; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @due_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @due_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-due_tag@
    @Column(name="due_tag"   , nullable=true , unique=false)
    private Boolean dueTag; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address@
    @Column(name="ship_to_address"  , length=65535 , nullable=true , unique=false)
    private String shipToAddress; 
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
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="customer_ship_to_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private CustomerShipTo customerShipTo;  

    @Column(name="customer_ship_to_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer customerShipToId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Vendor vendor;  

    @Column(name="vendor_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer vendorId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_contact_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VendorContact vendorContact;  

    @Column(name="vendor_contact_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer vendorContactId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_shipping_service_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VendorShippingService vendorShippingService;  

    @Column(name="vendor_shipping_service_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer vendorShippingServiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_term_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VendorTerm vendorTerm;  

    @Column(name="vendor_term_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer vendorTermId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @purchaseOrderEntries-field-purchase_order@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderEntry.class, fetch=FetchType.LAZY, mappedBy="purchaseOrder", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderEntry> purchaseOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public PurchaseOrder() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-purchase_order_number@
    public Integer getPurchaseOrderNumber() {
        return purchaseOrderNumber;
    }
	
    public void setPurchaseOrderNumber (Integer purchaseOrderNumber) {
        this.purchaseOrderNumber =  purchaseOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sub_store_id@
    public Integer getSubStore() {
        return subStore;
    }
	
    public void setSubStore (Integer subStore) {
        this.subStore =  subStore;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sub_purchase_order_number@
    public Integer getSubPurchaseOrderNumber() {
        return subPurchaseOrderNumber;
    }
	
    public void setSubPurchaseOrderNumber (Integer subPurchaseOrderNumber) {
        this.subPurchaseOrderNumber =  subPurchaseOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-purchase_order_type@
    public Integer getPurchaseOrderType() {
        return purchaseOrderType;
    }
	
    public void setPurchaseOrderType (Integer purchaseOrderType) {
        this.purchaseOrderType =  purchaseOrderType;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total_received@
    public java.math.BigDecimal getTotalReceived() {
        return totalReceived;
    }
	
    public void setTotalReceived (java.math.BigDecimal totalReceived) {
        this.totalReceived =  totalReceived;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_expected@
    public Date getDateExpected() {
        return dateExpected;
    }
	
    public void setDateExpected (Date dateExpected) {
        this.dateExpected =  dateExpected;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_days@
    public Integer getDiscountDays() {
        return discountDays;
    }
	
    public void setDiscountDays (Integer discountDays) {
        this.discountDays =  discountDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_percent@
    public java.math.BigDecimal getDiscountPercent() {
        return discountPercent;
    }
	
    public void setDiscountPercent (java.math.BigDecimal discountPercent) {
        this.discountPercent =  discountPercent;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-due_tag@
    public Boolean getDueTag() {
        return dueTag;
    }
	
    public void setDueTag (Boolean dueTag) {
        this.dueTag =  dueTag;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_address@
    public String getShipToAddress() {
        return shipToAddress;
    }
	
    public void setShipToAddress (String shipToAddress) {
        this.shipToAddress =  shipToAddress;
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
	
    public CustomerShipTo getCustomerShipTo () {
    	return customerShipTo;
    }
	
    public void setCustomerShipTo (CustomerShipTo customerShipTo) {
    	this.customerShipTo = customerShipTo;
    }

    public Integer getCustomerShipToId() {
        return customerShipToId;
    }
	
    public void setCustomerShipToId (Integer customerShipTo) {
        this.customerShipToId =  customerShipTo;
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
	
    public VendorContact getVendorContact () {
    	return vendorContact;
    }
	
    public void setVendorContact (VendorContact vendorContact) {
    	this.vendorContact = vendorContact;
    }

    public Integer getVendorContactId() {
        return vendorContactId;
    }
	
    public void setVendorContactId (Integer vendorContact) {
        this.vendorContactId =  vendorContact;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderEntries-getter-purchase_order@
    public List<PurchaseOrderEntry> getPurchaseOrderEntries() {
        if (purchaseOrderEntries == null){
            purchaseOrderEntries = new ArrayList<>();
        }
        return purchaseOrderEntries;
    }

    public void setPurchaseOrderEntries (List<PurchaseOrderEntry> purchaseOrderEntries) {
        this.purchaseOrderEntries = purchaseOrderEntries;
    }	
    
    public void addPurchaseOrderEntries (PurchaseOrderEntry element) {
    	    getPurchaseOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-purchase_order@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (status==null) status=__DEFAULT_STATUS;
        if (total==null) total=__DEFAULT_TOTAL;
        if (totalReceived==null) totalReceived=__DEFAULT_TOTAL_RECEIVED;
        if (freightInvoicedAmount==null) freightInvoicedAmount=__DEFAULT_FREIGHT_INVOICED_AMOUNT;
        if (freightPrePaidAmount==null) freightPrePaidAmount=__DEFAULT_FREIGHT_PRE_PAID_AMOUNT;
        if (taxOnOrderAmount==null) taxOnOrderAmount=__DEFAULT_TAX_ON_ORDER_AMOUNT;
        if (taxOnFreightAmount==null) taxOnFreightAmount=__DEFAULT_TAX_ON_FREIGHT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-purchase_order@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (status==null) status=__DEFAULT_STATUS;
        if (total==null) total=__DEFAULT_TOTAL;
        if (totalReceived==null) totalReceived=__DEFAULT_TOTAL_RECEIVED;
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

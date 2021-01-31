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
 * <p>Title: SalesOrder</p>
 *
 * <p>Description: Domain Object describing a SalesOrder entity</p>
 *
 */
@Entity (name="SalesOrder")
@Table (name="sales_order")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class SalesOrder implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_TYPE = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COMMISSION_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_SHIPPING_CHARGE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TAX_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CREDIT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @sales_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sales_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sales_order_number@
    @Column(name="sales_order_number"   , nullable=true , unique=true)
    private Integer salesOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_po_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_po_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_po_number@
    @Column(name="customer_po_number"  , length=32 , nullable=true , unique=false)
    private String customerPoNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-type@
    @Column(name="type"   , nullable=true , unique=false)
    private Integer type; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total@
    @Column(name="total"   , nullable=true , unique=false)
    private java.math.BigDecimal total; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fob-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fob-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fob@
    @Column(name="fob"  , length=65535 , nullable=true , unique=false)
    private String fob; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_ordered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_ordered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_ordered@
    @Column(name="date_ordered"   , nullable=true , unique=false)
    private Date dateOrdered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_due-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_due-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_due@
    @Column(name="date_due"   , nullable=true , unique=false)
    private Date dateDue; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commission_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commission_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commission_amount@
    @Column(name="commission_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal commissionAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_exempt_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_exempt_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_exempt_flag@
    @Column(name="tax_exempt_flag"   , nullable=true , unique=false)
    private Boolean taxExemptFlag; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_amount@
    @Column(name="credit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal creditAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_amount@
    @Column(name="discount_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal discountAmount; 
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

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="buyer_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private CustomerBuyer buyer;  

    @Column(name="buyer_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer buyerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="ship_to_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private CustomerShipTo shipTo;  

    @Column(name="ship_to_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer shipToId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="sales_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Employee sales;  

    @Column(name="sales_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer salesId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="service_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Service service;  

    @Column(name="service_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer serviceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="station_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Station station;  

    @Column(name="station_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer stationId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="tax_zone_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TaxZone taxZone;  

    @Column(name="tax_zone_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer taxZoneId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-field-sales_order@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="salesOrder", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> deposits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-field-sales_order@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificateTransaction.class, fetch=FetchType.LAZY, mappedBy="salesOrder", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificateTransaction> giftCertificateTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceRecurrings-field-sales_order@
    @OneToMany (targetEntity=com.salesliant.entity.InvoiceRecurring.class, fetch=FetchType.LAZY, mappedBy="originalInvoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <InvoiceRecurring> invoiceRecurrings = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @salesOrderEntries-field-sales_order@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrderEntry.class, fetch=FetchType.LAZY, mappedBy="salesOrder", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <SalesOrderEntry> salesOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippings-field-sales_order@
    @OneToMany (targetEntity=com.salesliant.entity.Shipping.class, fetch=FetchType.LAZY, mappedBy="invoice", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Shipping> shippings = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public SalesOrder() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sales_order_number@
    public Integer getSalesOrderNumber() {
        return salesOrderNumber;
    }
	
    public void setSalesOrderNumber (Integer salesOrderNumber) {
        this.salesOrderNumber =  salesOrderNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-type@
    public Integer getType() {
        return type;
    }
	
    public void setType (Integer type) {
        this.type =  type;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fob@
    public String getFob() {
        return fob;
    }
	
    public void setFob (String fob) {
        this.fob =  fob;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_due@
    public Date getDateDue() {
        return dateDue;
    }
	
    public void setDateDue (Date dateDue) {
        this.dateDue =  dateDue;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_exempt_flag@
    public Boolean getTaxExemptFlag() {
        return taxExemptFlag;
    }
	
    public void setTaxExemptFlag (Boolean taxExemptFlag) {
        this.taxExemptFlag =  taxExemptFlag;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_amount@
    public java.math.BigDecimal getCreditAmount() {
        return creditAmount;
    }
	
    public void setCreditAmount (java.math.BigDecimal creditAmount) {
        this.creditAmount =  creditAmount;
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
	
    public CustomerBuyer getBuyer () {
    	return buyer;
    }
	
    public void setBuyer (CustomerBuyer buyer) {
    	this.buyer = buyer;
    }

    public Integer getBuyerId() {
        return buyerId;
    }
	
    public void setBuyerId (Integer buyer) {
        this.buyerId =  buyer;
    }
	
    public CustomerShipTo getShipTo () {
    	return shipTo;
    }
	
    public void setShipTo (CustomerShipTo shipTo) {
    	this.shipTo = shipTo;
    }

    public Integer getShipToId() {
        return shipToId;
    }
	
    public void setShipToId (Integer shipTo) {
        this.shipToId =  shipTo;
    }
	
    public Employee getSales () {
    	return sales;
    }
	
    public void setSales (Employee sales) {
    	this.sales = sales;
    }

    public Integer getSalesId() {
        return salesId;
    }
	
    public void setSalesId (Integer sales) {
        this.salesId =  sales;
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
	
    public Station getStation () {
    	return station;
    }
	
    public void setStation (Station station) {
    	this.station = station;
    }

    public Integer getStationId() {
        return stationId;
    }
	
    public void setStationId (Integer station) {
        this.stationId =  station;
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
	
    public TaxZone getTaxZone () {
    	return taxZone;
    }
	
    public void setTaxZone (TaxZone taxZone) {
    	this.taxZone = taxZone;
    }

    public Integer getTaxZoneId() {
        return taxZoneId;
    }
	
    public void setTaxZoneId (Integer taxZone) {
        this.taxZoneId =  taxZone;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-getter-sales_order@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-getter-sales_order@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceRecurrings-getter-sales_order@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrderEntries-getter-sales_order@
    public List<SalesOrderEntry> getSalesOrderEntries() {
        if (salesOrderEntries == null){
            salesOrderEntries = new ArrayList<>();
        }
        return salesOrderEntries;
    }

    public void setSalesOrderEntries (List<SalesOrderEntry> salesOrderEntries) {
        this.salesOrderEntries = salesOrderEntries;
    }	
    
    public void addSalesOrderEntries (SalesOrderEntry element) {
    	    getSalesOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippings-getter-sales_order@
    public List<Shipping> getShippings() {
        if (shippings == null){
            shippings = new ArrayList<>();
        }
        return shippings;
    }

    public void setShippings (List<Shipping> shippings) {
        this.shippings = shippings;
    }	
    
    public void addShippings (Shipping element) {
    	    getShippings().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-sales_order@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (type==null) type=__DEFAULT_TYPE;
        if (total==null) total=__DEFAULT_TOTAL;
        if (commissionAmount==null) commissionAmount=__DEFAULT_COMMISSION_AMOUNT;
        if (shippingCharge==null) shippingCharge=__DEFAULT_SHIPPING_CHARGE;
        if (taxAmount==null) taxAmount=__DEFAULT_TAX_AMOUNT;
        if (creditAmount==null) creditAmount=__DEFAULT_CREDIT_AMOUNT;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-sales_order@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (type==null) type=__DEFAULT_TYPE;
        if (total==null) total=__DEFAULT_TOTAL;
        if (commissionAmount==null) commissionAmount=__DEFAULT_COMMISSION_AMOUNT;
        if (shippingCharge==null) shippingCharge=__DEFAULT_SHIPPING_CHARGE;
        if (taxAmount==null) taxAmount=__DEFAULT_TAX_AMOUNT;
        if (creditAmount==null) creditAmount=__DEFAULT_CREDIT_AMOUNT;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

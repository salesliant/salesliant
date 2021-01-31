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
 * <p>Title: ReturnTransaction</p>
 *
 * <p>Description: Domain Object describing a ReturnTransaction entity</p>
 *
 */
@Entity (name="ReturnTransaction")
@Table (name="return_transaction")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ReturnTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COST = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity@
    @Column(name="quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal quantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=false , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price@
    @Column(name="price"   , nullable=false , unique=false)
    private java.math.BigDecimal price; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_look_up_code@
    @Column(name="item_look_up_code"  , length=64 , nullable=true , unique=false)
    private String itemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_description@
    @Column(name="item_description"  , length=128 , nullable=true , unique=false)
    private String itemDescription; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @reason_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @reason_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-reason_code@
    @Column(name="reason_code"  , length=128 , nullable=true , unique=false)
    private String reasonCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @rma_number_to_customer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @rma_number_to_customer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-rma_number_to_customer@
    @Column(name="rma_number_to_customer"   , nullable=true , unique=false)
    private Integer rmaNumberToCustomer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name_returned-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name_returned-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name_returned@
    @Column(name="employee_name_returned"  , length=32 , nullable=true , unique=false)
    private String employeeNameReturned; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name_processed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name_processed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name_processed@
    @Column(name="employee_name_processed"  , length=32 , nullable=true , unique=false)
    private String employeeNameProcessed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_number@
    @Column(name="invoice_number"  , length=32 , nullable=true , unique=false)
    private String invoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_invoice_number@
    @Column(name="vendor_invoice_number"  , length=32 , nullable=true , unique=false)
    private String vendorInvoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @own_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @own_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-own_tag@
    @Column(name="own_tag"   , nullable=true , unique=false)
    private Boolean ownTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @return_from_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_from_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_from_type@
    @Column(name="return_from_type"   , nullable=true , unique=false)
    private Integer returnFromType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @return_to_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_to_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_to_type@
    @Column(name="return_to_type"   , nullable=true , unique=false)
    private Integer returnToType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_returned-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_returned-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_returned@
    @Column(name="date_returned"   , nullable=true , unique=false)
    private Timestamp dateReturned; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_processed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_processed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_processed@
    @Column(name="date_processed"   , nullable=true , unique=false)
    private Timestamp dateProcessed; 
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

    @Column(name="customer_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_processed_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Employee employeeProcessed;  

    @Column(name="employee_processed_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer employeeProcessedId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_returned_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Employee employeeReturned;  

    @Column(name="employee_returned_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer employeeReturnedId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_entry_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private InvoiceEntry invoiceEntry;  

    @Column(name="invoice_entry_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer invoiceEntryId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-field-return_transaction@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnEntry.class, fetch=FetchType.LAZY, mappedBy="renturnTransaction", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnEntry> returnEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public ReturnTransaction() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity@
    public java.math.BigDecimal getQuantity() {
        return quantity;
    }
	
    public void setQuantity (java.math.BigDecimal quantity) {
        this.quantity =  quantity;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price@
    public java.math.BigDecimal getPrice() {
        return price;
    }
	
    public void setPrice (java.math.BigDecimal price) {
        this.price =  price;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_look_up_code@
    public String getItemLookUpCode() {
        return itemLookUpCode;
    }
	
    public void setItemLookUpCode (String itemLookUpCode) {
        this.itemLookUpCode =  itemLookUpCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_description@
    public String getItemDescription() {
        return itemDescription;
    }
	
    public void setItemDescription (String itemDescription) {
        this.itemDescription =  itemDescription;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-reason_code@
    public String getReasonCode() {
        return reasonCode;
    }
	
    public void setReasonCode (String reasonCode) {
        this.reasonCode =  reasonCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-rma_number_to_customer@
    public Integer getRmaNumberToCustomer() {
        return rmaNumberToCustomer;
    }
	
    public void setRmaNumberToCustomer (Integer rmaNumberToCustomer) {
        this.rmaNumberToCustomer =  rmaNumberToCustomer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_name_returned@
    public String getEmployeeNameReturned() {
        return employeeNameReturned;
    }
	
    public void setEmployeeNameReturned (String employeeNameReturned) {
        this.employeeNameReturned =  employeeNameReturned;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_name_processed@
    public String getEmployeeNameProcessed() {
        return employeeNameProcessed;
    }
	
    public void setEmployeeNameProcessed (String employeeNameProcessed) {
        this.employeeNameProcessed =  employeeNameProcessed;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_number@
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
	
    public void setInvoiceNumber (String invoiceNumber) {
        this.invoiceNumber =  invoiceNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-status@
    public Integer getStatus() {
        return status;
    }
	
    public void setStatus (Integer status) {
        this.status =  status;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-own_tag@
    public Boolean getOwnTag() {
        return ownTag;
    }
	
    public void setOwnTag (Boolean ownTag) {
        this.ownTag =  ownTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_from_type@
    public Integer getReturnFromType() {
        return returnFromType;
    }
	
    public void setReturnFromType (Integer returnFromType) {
        this.returnFromType =  returnFromType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_to_type@
    public Integer getReturnToType() {
        return returnToType;
    }
	
    public void setReturnToType (Integer returnToType) {
        this.returnToType =  returnToType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_returned@
    public Timestamp getDateReturned() {
        return dateReturned;
    }
	
    public void setDateReturned (Timestamp dateReturned) {
        this.dateReturned =  dateReturned;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_processed@
    public Timestamp getDateProcessed() {
        return dateProcessed;
    }
	
    public void setDateProcessed (Timestamp dateProcessed) {
        this.dateProcessed =  dateProcessed;
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
	
    public Employee getEmployeeProcessed () {
    	return employeeProcessed;
    }
	
    public void setEmployeeProcessed (Employee employeeProcessed) {
    	this.employeeProcessed = employeeProcessed;
    }

    public Integer getEmployeeProcessedId() {
        return employeeProcessedId;
    }
	
    public void setEmployeeProcessedId (Integer employeeProcessed) {
        this.employeeProcessedId =  employeeProcessed;
    }
	
    public Employee getEmployeeReturned () {
    	return employeeReturned;
    }
	
    public void setEmployeeReturned (Employee employeeReturned) {
    	this.employeeReturned = employeeReturned;
    }

    public Integer getEmployeeReturnedId() {
        return employeeReturnedId;
    }
	
    public void setEmployeeReturnedId (Integer employeeReturned) {
        this.employeeReturnedId =  employeeReturned;
    }
	
    public InvoiceEntry getInvoiceEntry () {
    	return invoiceEntry;
    }
	
    public void setInvoiceEntry (InvoiceEntry invoiceEntry) {
    	this.invoiceEntry = invoiceEntry;
    }

    public Integer getInvoiceEntryId() {
        return invoiceEntryId;
    }
	
    public void setInvoiceEntryId (Integer invoiceEntry) {
        this.invoiceEntryId =  invoiceEntry;
    }
	
    public Item getItem () {
    	return item;
    }
	
    public void setItem (Item item) {
    	this.item = item;
    }

    public Integer getItemId() {
        return itemId;
    }
	
    public void setItemId (Integer item) {
        this.itemId =  item;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-getter-return_transaction@
    public List<ReturnEntry> getReturnEntries() {
        if (returnEntries == null){
            returnEntries = new ArrayList<>();
        }
        return returnEntries;
    }

    public void setReturnEntries (List<ReturnEntry> returnEntries) {
        this.returnEntries = returnEntries;
    }	
    
    public void addReturnEntries (ReturnEntry element) {
    	    getReturnEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-return_transaction@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (cost==null) cost=__DEFAULT_COST;
        if (price==null) price=__DEFAULT_PRICE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-return_transaction@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (cost==null) cost=__DEFAULT_COST;
        if (price==null) price=__DEFAULT_PRICE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

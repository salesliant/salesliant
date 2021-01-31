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
 * <p>Title: ItemLog</p>
 *
 * <p>Description: Domain Object describing a ItemLog entity</p>
 *
 */
@Entity (name="ItemLog")
@Table (name="item_log")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ItemLog implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_AFTER_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_BEFORE_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COST = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_ITEM_PRICE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_ITEM_COST = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @transfer_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transfer_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transfer_type@
    @Column(name="transfer_type"   , nullable=true , unique=false)
    private Integer transferType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transaction_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transaction_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transaction_number@
    @Column(name="transaction_number"  , length=32 , nullable=true , unique=false)
    private String transactionNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Timestamp dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @after_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @after_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-after_quantity@
    @Column(name="after_quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal afterQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @before_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @before_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-before_quantity@
    @Column(name="before_quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal beforeQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price@
    @Column(name="price"   , nullable=true , unique=false)
    private java.math.BigDecimal price; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_price-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_price-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_price@
    @Column(name="item_price"   , nullable=true , unique=false)
    private java.math.BigDecimal itemPrice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_cost@
    @Column(name="item_cost"   , nullable=true , unique=false)
    private java.math.BigDecimal itemCost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Employee employee;  

    @Column(name="employee_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer employeeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="purchase_order_history_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private PurchaseOrderHistory purchaseOrderHistory;  

    @Column(name="purchase_order_history_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer purchaseOrderHistoryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="transfer_order_history_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TransferOrderHistory transferOrderHistory;  

    @Column(name="transfer_order_history_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer transferOrderHistoryId;

    /**
    * Default constructor
    */
    public ItemLog() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transfer_type@
    public Integer getTransferType() {
        return transferType;
    }
	
    public void setTransferType (Integer transferType) {
        this.transferType =  transferType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transaction_number@
    public String getTransactionNumber() {
        return transactionNumber;
    }
	
    public void setTransactionNumber (String transactionNumber) {
        this.transactionNumber =  transactionNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-after_quantity@
    public java.math.BigDecimal getAfterQuantity() {
        return afterQuantity;
    }
	
    public void setAfterQuantity (java.math.BigDecimal afterQuantity) {
        this.afterQuantity =  afterQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-before_quantity@
    public java.math.BigDecimal getBeforeQuantity() {
        return beforeQuantity;
    }
	
    public void setBeforeQuantity (java.math.BigDecimal beforeQuantity) {
        this.beforeQuantity =  beforeQuantity;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cost@
    public java.math.BigDecimal getCost() {
        return cost;
    }
	
    public void setCost (java.math.BigDecimal cost) {
        this.cost =  cost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_price@
    public java.math.BigDecimal getItemPrice() {
        return itemPrice;
    }
	
    public void setItemPrice (java.math.BigDecimal itemPrice) {
        this.itemPrice =  itemPrice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_cost@
    public java.math.BigDecimal getItemCost() {
        return itemCost;
    }
	
    public void setItemCost (java.math.BigDecimal itemCost) {
        this.itemCost =  itemCost;
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


    public Employee getEmployee () {
    	return employee;
    }
	
    public void setEmployee (Employee employee) {
    	this.employee = employee;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
	
    public void setEmployeeId (Integer employee) {
        this.employeeId =  employee;
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
	
    public PurchaseOrderHistory getPurchaseOrderHistory () {
    	return purchaseOrderHistory;
    }
	
    public void setPurchaseOrderHistory (PurchaseOrderHistory purchaseOrderHistory) {
    	this.purchaseOrderHistory = purchaseOrderHistory;
    }

    public Integer getPurchaseOrderHistoryId() {
        return purchaseOrderHistoryId;
    }
	
    public void setPurchaseOrderHistoryId (Integer purchaseOrderHistory) {
        this.purchaseOrderHistoryId =  purchaseOrderHistory;
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
	
    public TransferOrderHistory getTransferOrderHistory () {
    	return transferOrderHistory;
    }
	
    public void setTransferOrderHistory (TransferOrderHistory transferOrderHistory) {
    	this.transferOrderHistory = transferOrderHistory;
    }

    public Integer getTransferOrderHistoryId() {
        return transferOrderHistoryId;
    }
	
    public void setTransferOrderHistoryId (Integer transferOrderHistory) {
        this.transferOrderHistoryId =  transferOrderHistory;
    }
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-item_log@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (afterQuantity==null) afterQuantity=__DEFAULT_AFTER_QUANTITY;
        if (beforeQuantity==null) beforeQuantity=__DEFAULT_BEFORE_QUANTITY;
        if (price==null) price=__DEFAULT_PRICE;
        if (cost==null) cost=__DEFAULT_COST;
        if (itemPrice==null) itemPrice=__DEFAULT_ITEM_PRICE;
        if (itemCost==null) itemCost=__DEFAULT_ITEM_COST;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-item_log@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (afterQuantity==null) afterQuantity=__DEFAULT_AFTER_QUANTITY;
        if (beforeQuantity==null) beforeQuantity=__DEFAULT_BEFORE_QUANTITY;
        if (price==null) price=__DEFAULT_PRICE;
        if (cost==null) cost=__DEFAULT_COST;
        if (itemPrice==null) itemPrice=__DEFAULT_ITEM_PRICE;
        if (itemCost==null) itemCost=__DEFAULT_ITEM_COST;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

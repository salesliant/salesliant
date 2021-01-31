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
 * <p>Title: ReturnOrderEntry</p>
 *
 * <p>Description: Domain Object describing a ReturnOrderEntry entity</p>
 *
 */
@Entity (name="ReturnOrderEntry")
@Table (name="return_order_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ReturnOrderEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_invoice_number@
    @Column(name="vendor_invoice_number"  , length=32 , nullable=true , unique=false)
    private String vendorInvoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity@
    @Column(name="quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal quantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_purchased-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_purchased-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_purchased@
    @Column(name="date_purchased"   , nullable=true , unique=false)
    private Date datePurchased; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_rate@
    @Column(name="tax_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal taxRate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_order@
    @Column(name="display_order"   , nullable=true , unique=false)
    private Integer displayOrder; 
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
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="reason_code_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private ReasonCode reasonCode;  

    @Column(name="reason_code_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer reasonCodeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="return_order_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ReturnOrder returnOrder;  

    @Column(name="return_order_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer returnOrderId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="return_action_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ReasonCode returnAction;  

    @Column(name="return_action_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer returnActionId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public ReturnOrderEntry() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_invoice_number@
    public String getVendorInvoiceNumber() {
        return vendorInvoiceNumber;
    }
	
    public void setVendorInvoiceNumber (String vendorInvoiceNumber) {
        this.vendorInvoiceNumber =  vendorInvoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_purchased@
    public Date getDatePurchased() {
        return datePurchased;
    }
	
    public void setDatePurchased (Date datePurchased) {
        this.datePurchased =  datePurchased;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_rate@
    public java.math.BigDecimal getTaxRate() {
        return taxRate;
    }
	
    public void setTaxRate (java.math.BigDecimal taxRate) {
        this.taxRate =  taxRate;
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
	
    public ReasonCode getReasonCode () {
    	return reasonCode;
    }
	
    public void setReasonCode (ReasonCode reasonCode) {
    	this.reasonCode = reasonCode;
    }

    public Integer getReasonCodeId() {
        return reasonCodeId;
    }
	
    public void setReasonCodeId (Integer reasonCode) {
        this.reasonCodeId =  reasonCode;
    }
	
    public ReturnOrder getReturnOrder () {
    	return returnOrder;
    }
	
    public void setReturnOrder (ReturnOrder returnOrder) {
    	this.returnOrder = returnOrder;
    }

    public Integer getReturnOrderId() {
        return returnOrderId;
    }
	
    public void setReturnOrderId (Integer returnOrder) {
        this.returnOrderId =  returnOrder;
    }
	
    public ReasonCode getReturnAction () {
    	return returnAction;
    }
	
    public void setReturnAction (ReasonCode returnAction) {
    	this.returnAction = returnAction;
    }

    public Integer getReturnActionId() {
        return returnActionId;
    }
	
    public void setReturnActionId (Integer returnAction) {
        this.returnActionId =  returnAction;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-return_order_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-return_order_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

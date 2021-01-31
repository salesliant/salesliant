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
 * <p>Title: SalesOrderEntry</p>
 *
 * <p>Description: Domain Object describing a SalesOrderEntry entity</p>
 *
 */
@Entity (name="SalesOrderEntry")
@Table (name="sales_order_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class SalesOrderEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_RATE = java.math.BigDecimal.valueOf(0.0000);
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

//MP-MANAGED-ADDED-AREA-BEGINNING @taxable-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @taxable-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-taxable@
    @Column(name="taxable"   , nullable=true , unique=false)
    private Boolean taxable; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @display_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_order@
    @Column(name="display_order"   , nullable=true , unique=false)
    private Integer displayOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line_note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line_note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line_note@
    @Column(name="line_note"  , length=65535 , nullable=true , unique=false)
    private String lineNote; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @component_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @component_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-component_flag@
    @Column(name="component_flag"   , nullable=true , unique=false)
    private Boolean componentFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @shipped_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @shipped_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-shipped_flag@
    @Column(name="shipped_flag"   , nullable=true , unique=false)
    private Boolean shippedFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @drop_shipped_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @drop_shipped_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-drop_shipped_flag@
    @Column(name="drop_shipped_flag"   , nullable=true , unique=false)
    private Boolean dropShippedFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_rate@
    @Column(name="discount_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal discountRate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @component_quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @component_quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-component_quantity@
    @Column(name="component_quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal componentQuantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @return_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_code@
    @Column(name="return_code"  , length=64 , nullable=true , unique=false)
    private String returnCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="sales_order_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private SalesOrder salesOrder;  

    @Column(name="sales_order_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer salesOrderId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="shipping_service_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ShippingService shippingService;  

    @Column(name="shipping_service_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer shippingServiceId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @serialNumbers-field-sales_order_entry@
    @OneToMany (targetEntity=com.salesliant.entity.SerialNumber.class, fetch=FetchType.LAZY, mappedBy="salesOrderEntry", cascade=CascadeType.DETACH)//, cascade=CascadeType.ALL)
    private List <SerialNumber> serialNumbers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voucherEntries-field-sales_order_entry@
    @OneToMany (targetEntity=com.salesliant.entity.VoucherEntry.class, fetch=FetchType.LAZY, mappedBy="invoiceEntry", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VoucherEntry> voucherEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public SalesOrderEntry() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-taxable@
    public Boolean getTaxable() {
        return taxable;
    }
	
    public void setTaxable (Boolean taxable) {
        this.taxable =  taxable;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_order@
    public Integer getDisplayOrder() {
        return displayOrder;
    }
	
    public void setDisplayOrder (Integer displayOrder) {
        this.displayOrder =  displayOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line_note@
    public String getLineNote() {
        return lineNote;
    }
	
    public void setLineNote (String lineNote) {
        this.lineNote =  lineNote;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-component_flag@
    public Boolean getComponentFlag() {
        return componentFlag;
    }
	
    public void setComponentFlag (Boolean componentFlag) {
        this.componentFlag =  componentFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-shipped_flag@
    public Boolean getShippedFlag() {
        return shippedFlag;
    }
	
    public void setShippedFlag (Boolean shippedFlag) {
        this.shippedFlag =  shippedFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-drop_shipped_flag@
    public Boolean getDropShippedFlag() {
        return dropShippedFlag;
    }
	
    public void setDropShippedFlag (Boolean dropShippedFlag) {
        this.dropShippedFlag =  dropShippedFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_rate@
    public java.math.BigDecimal getDiscountRate() {
        return discountRate;
    }
	
    public void setDiscountRate (java.math.BigDecimal discountRate) {
        this.discountRate =  discountRate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-component_quantity@
    public java.math.BigDecimal getComponentQuantity() {
        return componentQuantity;
    }
	
    public void setComponentQuantity (java.math.BigDecimal componentQuantity) {
        this.componentQuantity =  componentQuantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_code@
    public String getReturnCode() {
        return returnCode;
    }
	
    public void setReturnCode (String returnCode) {
        this.returnCode =  returnCode;
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
	
    public SalesOrder getSalesOrder () {
    	return salesOrder;
    }
	
    public void setSalesOrder (SalesOrder salesOrder) {
    	this.salesOrder = salesOrder;
    }

    public Integer getSalesOrderId() {
        return salesOrderId;
    }
	
    public void setSalesOrderId (Integer salesOrder) {
        this.salesOrderId =  salesOrder;
    }
	
    public ShippingService getShippingService () {
    	return shippingService;
    }
	
    public void setShippingService (ShippingService shippingService) {
    	this.shippingService = shippingService;
    }

    public Integer getShippingServiceId() {
        return shippingServiceId;
    }
	
    public void setShippingServiceId (Integer shippingService) {
        this.shippingServiceId =  shippingService;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-getter-sales_order_entry@
    public List<SerialNumber> getSerialNumbers() {
        if (serialNumbers == null){
            serialNumbers = new ArrayList<>();
        }
        return serialNumbers;
    }

    public void setSerialNumbers (List<SerialNumber> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }	
    
    public void addSerialNumbers (SerialNumber element) {
    	    getSerialNumbers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voucherEntries-getter-sales_order_entry@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-sales_order_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (discountRate==null) discountRate=__DEFAULT_DISCOUNT_RATE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-sales_order_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (discountRate==null) discountRate=__DEFAULT_DISCOUNT_RATE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

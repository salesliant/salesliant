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
 * <p>Title: InvoiceEntry</p>
 *
 * <p>Description: Domain Object describing a InvoiceEntry entity</p>
 *
 */
@Entity (name="InvoiceEntry")
@Table (name="invoice_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class InvoiceEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COUPON_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COUPON_SAVING_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COMPONENT_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_QUANTITY_RETURN = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_QUANTITY_BACKORDER = java.math.BigDecimal.valueOf(0.0000);
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

//MP-MANAGED-ADDED-AREA-BEGINNING @item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_look_up_code@
    @Column(name="item_look_up_code"  , length=64 , nullable=true , unique=false)
    private String itemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @category_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @category_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-category_name@
    @Column(name="category_name"  , length=128 , nullable=true , unique=false)
    private String categoryName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_class_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_class_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_class_name@
    @Column(name="tax_class_name"  , length=128 , nullable=true , unique=false)
    private String taxClassName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sales_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sales_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sales_name@
    @Column(name="sales_name"  , length=64 , nullable=true , unique=false)
    private String salesName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"  , length=1 , nullable=true , unique=false)
    private String status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_description@
    @Column(name="item_description"  , length=128 , nullable=true , unique=false)
    private String itemDescription; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_amount@
    @Column(name="tax_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal taxAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @component_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @component_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-component_flag@
    @Column(name="component_flag"   , nullable=true , unique=false)
    private Boolean componentFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @weight-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @weight-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-weight@
    @Column(name="weight"   , nullable=true , unique=false)
    private java.math.BigDecimal weight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @shipped_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @shipped_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-shipped_flag@
    @Column(name="shipped_flag"   , nullable=true , unique=false)
    private Boolean shippedFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commission_paid_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commission_paid_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commission_paid_flag@
    @Column(name="commission_paid_flag"   , nullable=true , unique=false)
    private Boolean commissionPaidFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @drop_shipped_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @drop_shipped_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-drop_shipped_flag@
    @Column(name="drop_shipped_flag"   , nullable=true , unique=false)
    private Boolean dropShippedFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_amount@
    @Column(name="discount_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal discountAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @coupon_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @coupon_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-coupon_amount@
    @Column(name="coupon_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal couponAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @coupon_saving_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @coupon_saving_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-coupon_saving_amount@
    @Column(name="coupon_saving_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal couponSavingAmount; 
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
    @Column(name="return_code"  , length=128 , nullable=true , unique=false)
    private String returnCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_return-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_return-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_return@
    @Column(name="quantity_return"   , nullable=true , unique=false)
    private java.math.BigDecimal quantityReturn; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_backorder-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_backorder-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_backorder@
    @Column(name="quantity_backorder"   , nullable=true , unique=false)
    private java.math.BigDecimal quantityBackorder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer invoiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="shipping_carrier_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ShippingCarrier shippingCarrier;  

    @Column(name="shipping_carrier_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer shippingCarrierId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-field-invoice_entry@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnTransaction.class, fetch=FetchType.LAZY, mappedBy="invoiceEntry", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnTransaction> returnTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @serialNumbers-field-invoice_entry@
    @OneToMany (targetEntity=com.salesliant.entity.SerialNumber.class, fetch=FetchType.LAZY, mappedBy="invoiceEntry", cascade=CascadeType.DETACH)//, cascade=CascadeType.ALL)
    private List <SerialNumber> serialNumbers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public InvoiceEntry() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_look_up_code@
    public String getItemLookUpCode() {
        return itemLookUpCode;
    }
	
    public void setItemLookUpCode (String itemLookUpCode) {
        this.itemLookUpCode =  itemLookUpCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-category_name@
    public String getCategoryName() {
        return categoryName;
    }
	
    public void setCategoryName (String categoryName) {
        this.categoryName =  categoryName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_class_name@
    public String getTaxClassName() {
        return taxClassName;
    }
	
    public void setTaxClassName (String taxClassName) {
        this.taxClassName =  taxClassName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-status@
    public String getStatus() {
        return status;
    }
	
    public void setStatus (String status) {
        this.status =  status;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_amount@
    public java.math.BigDecimal getTaxAmount() {
        return taxAmount;
    }
	
    public void setTaxAmount (java.math.BigDecimal taxAmount) {
        this.taxAmount =  taxAmount;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-note@
    public String getNote() {
        return note;
    }
	
    public void setNote (String note) {
        this.note =  note;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-weight@
    public java.math.BigDecimal getWeight() {
        return weight;
    }
	
    public void setWeight (java.math.BigDecimal weight) {
        this.weight =  weight;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commission_paid_flag@
    public Boolean getCommissionPaidFlag() {
        return commissionPaidFlag;
    }
	
    public void setCommissionPaidFlag (Boolean commissionPaidFlag) {
        this.commissionPaidFlag =  commissionPaidFlag;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_amount@
    public java.math.BigDecimal getDiscountAmount() {
        return discountAmount;
    }
	
    public void setDiscountAmount (java.math.BigDecimal discountAmount) {
        this.discountAmount =  discountAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-coupon_amount@
    public java.math.BigDecimal getCouponAmount() {
        return couponAmount;
    }
	
    public void setCouponAmount (java.math.BigDecimal couponAmount) {
        this.couponAmount =  couponAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-coupon_saving_amount@
    public java.math.BigDecimal getCouponSavingAmount() {
        return couponSavingAmount;
    }
	
    public void setCouponSavingAmount (java.math.BigDecimal couponSavingAmount) {
        this.couponSavingAmount =  couponSavingAmount;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_return@
    public java.math.BigDecimal getQuantityReturn() {
        return quantityReturn;
    }
	
    public void setQuantityReturn (java.math.BigDecimal quantityReturn) {
        this.quantityReturn =  quantityReturn;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_backorder@
    public java.math.BigDecimal getQuantityBackorder() {
        return quantityBackorder;
    }
	
    public void setQuantityBackorder (java.math.BigDecimal quantityBackorder) {
        this.quantityBackorder =  quantityBackorder;
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
	
    public ShippingCarrier getShippingCarrier () {
    	return shippingCarrier;
    }
	
    public void setShippingCarrier (ShippingCarrier shippingCarrier) {
    	this.shippingCarrier = shippingCarrier;
    }

    public Integer getShippingCarrierId() {
        return shippingCarrierId;
    }
	
    public void setShippingCarrierId (Integer shippingCarrier) {
        this.shippingCarrierId =  shippingCarrier;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-getter-invoice_entry@
    public List<ReturnTransaction> getReturnTransactions() {
        if (returnTransactions == null){
            returnTransactions = new ArrayList<>();
        }
        return returnTransactions;
    }

    public void setReturnTransactions (List<ReturnTransaction> returnTransactions) {
        this.returnTransactions = returnTransactions;
    }	
    
    public void addReturnTransactions (ReturnTransaction element) {
    	    getReturnTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-getter-invoice_entry@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-invoice_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (couponAmount==null) couponAmount=__DEFAULT_COUPON_AMOUNT;
        if (couponSavingAmount==null) couponSavingAmount=__DEFAULT_COUPON_SAVING_AMOUNT;
        if (componentQuantity==null) componentQuantity=__DEFAULT_COMPONENT_QUANTITY;
        if (quantityReturn==null) quantityReturn=__DEFAULT_QUANTITY_RETURN;
        if (quantityBackorder==null) quantityBackorder=__DEFAULT_QUANTITY_BACKORDER;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-invoice_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (couponAmount==null) couponAmount=__DEFAULT_COUPON_AMOUNT;
        if (couponSavingAmount==null) couponSavingAmount=__DEFAULT_COUPON_SAVING_AMOUNT;
        if (componentQuantity==null) componentQuantity=__DEFAULT_COMPONENT_QUANTITY;
        if (quantityReturn==null) quantityReturn=__DEFAULT_QUANTITY_RETURN;
        if (quantityBackorder==null) quantityBackorder=__DEFAULT_QUANTITY_BACKORDER;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

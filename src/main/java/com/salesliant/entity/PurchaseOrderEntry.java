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
 * <p>Title: PurchaseOrderEntry</p>
 *
 * <p>Description: Domain Object describing a PurchaseOrderEntry entity</p>
 *
 */
@Entity (name="PurchaseOrderEntry")
@Table (name="purchase_order_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class PurchaseOrderEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_QUANTITY_ORDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_QUANTITY_RECEIVED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_QUANTITY_PER_UOM = java.math.BigDecimal.valueOf(1.0000);
	public static final java.math.BigDecimal __DEFAULT_TAX_RATE = java.math.BigDecimal.valueOf(0.000000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_look_up_code@
    @Column(name="item_look_up_code"  , length=64 , nullable=true , unique=false)
    private String itemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_item_look_up_code@
    @Column(name="vendor_item_look_up_code"  , length=64 , nullable=true , unique=false)
    private String vendorItemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_description@
    @Column(name="item_description"  , length=128 , nullable=true , unique=false)
    private String itemDescription; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_ordered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_ordered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_ordered@
    @Column(name="quantity_ordered"   , nullable=true , unique=false)
    private java.math.BigDecimal quantityOrdered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_received@
    @Column(name="quantity_received"   , nullable=true , unique=false)
    private java.math.BigDecimal quantityReceived; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_per_uom-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_per_uom-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_per_uom@
    @Column(name="quantity_per_uom"   , nullable=true , unique=false)
    private java.math.BigDecimal quantityPerUom; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=true , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_rate@
    @Column(name="tax_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal taxRate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_amount@
    @Column(name="discount_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal discountAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @weight-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @weight-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-weight@
    @Column(name="weight"   , nullable=true , unique=false)
    private java.math.BigDecimal weight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @processed_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @processed_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-processed_tag@
    @Column(name="processed_tag"   , nullable=true , unique=false)
    private Boolean processedTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_received@
    @Column(name="date_received"   , nullable=true , unique=false)
    private Date dateReceived; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line_note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line_note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line_note@
    @Column(name="line_note"  , length=65535 , nullable=true , unique=false)
    private String lineNote; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @unit_of_measure-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @unit_of_measure-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-unit_of_measure@
    @Column(name="unit_of_measure"  , length=32 , nullable=true , unique=false)
    private String unitOfMeasure; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_order@
    @Column(name="display_order"   , nullable=true , unique=false)
    private Integer displayOrder; 
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

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="sub_store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store subStore;  

    @Column(name="sub_store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer subStoreId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="purchase_order_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private PurchaseOrder purchaseOrder;  

    @Column(name="purchase_order_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer purchaseOrderId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @serialNumbers-field-purchase_order_entry@
    @OneToMany (targetEntity=com.salesliant.entity.SerialNumber.class, fetch=FetchType.LAZY, mappedBy="purchaseOrderEntry", cascade=CascadeType.DETACH)//, cascade=CascadeType.ALL)
    private List <SerialNumber> serialNumbers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public PurchaseOrderEntry() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_look_up_code@
    public String getItemLookUpCode() {
        return itemLookUpCode;
    }
	
    public void setItemLookUpCode (String itemLookUpCode) {
        this.itemLookUpCode =  itemLookUpCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_item_look_up_code@
    public String getVendorItemLookUpCode() {
        return vendorItemLookUpCode;
    }
	
    public void setVendorItemLookUpCode (String vendorItemLookUpCode) {
        this.vendorItemLookUpCode =  vendorItemLookUpCode;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_ordered@
    public java.math.BigDecimal getQuantityOrdered() {
        return quantityOrdered;
    }
	
    public void setQuantityOrdered (java.math.BigDecimal quantityOrdered) {
        this.quantityOrdered =  quantityOrdered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_received@
    public java.math.BigDecimal getQuantityReceived() {
        return quantityReceived;
    }
	
    public void setQuantityReceived (java.math.BigDecimal quantityReceived) {
        this.quantityReceived =  quantityReceived;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_per_uom@
    public java.math.BigDecimal getQuantityPerUom() {
        return quantityPerUom;
    }
	
    public void setQuantityPerUom (java.math.BigDecimal quantityPerUom) {
        this.quantityPerUom =  quantityPerUom;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_rate@
    public java.math.BigDecimal getTaxRate() {
        return taxRate;
    }
	
    public void setTaxRate (java.math.BigDecimal taxRate) {
        this.taxRate =  taxRate;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-weight@
    public java.math.BigDecimal getWeight() {
        return weight;
    }
	
    public void setWeight (java.math.BigDecimal weight) {
        this.weight =  weight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-processed_tag@
    public Boolean getProcessedTag() {
        return processedTag;
    }
	
    public void setProcessedTag (Boolean processedTag) {
        this.processedTag =  processedTag;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line_note@
    public String getLineNote() {
        return lineNote;
    }
	
    public void setLineNote (String lineNote) {
        this.lineNote =  lineNote;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-unit_of_measure@
    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }
	
    public void setUnitOfMeasure (String unitOfMeasure) {
        this.unitOfMeasure =  unitOfMeasure;
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
	
    public Store getSubStore () {
    	return subStore;
    }
	
    public void setSubStore (Store subStore) {
    	this.subStore = subStore;
    }

    public Integer getSubStoreId() {
        return subStoreId;
    }
	
    public void setSubStoreId (Integer subStore) {
        this.subStoreId =  subStore;
    }
	
    public PurchaseOrder getPurchaseOrder () {
    	return purchaseOrder;
    }
	
    public void setPurchaseOrder (PurchaseOrder purchaseOrder) {
    	this.purchaseOrder = purchaseOrder;
    }

    public Integer getPurchaseOrderId() {
        return purchaseOrderId;
    }
	
    public void setPurchaseOrderId (Integer purchaseOrder) {
        this.purchaseOrderId =  purchaseOrder;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-getter-purchase_order_entry@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-purchase_order_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantityOrdered==null) quantityOrdered=__DEFAULT_QUANTITY_ORDERED;
        if (quantityReceived==null) quantityReceived=__DEFAULT_QUANTITY_RECEIVED;
        if (quantityPerUom==null) quantityPerUom=__DEFAULT_QUANTITY_PER_UOM;
        if (taxRate==null) taxRate=__DEFAULT_TAX_RATE;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-purchase_order_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantityOrdered==null) quantityOrdered=__DEFAULT_QUANTITY_ORDERED;
        if (quantityReceived==null) quantityReceived=__DEFAULT_QUANTITY_RECEIVED;
        if (quantityPerUom==null) quantityPerUom=__DEFAULT_QUANTITY_PER_UOM;
        if (taxRate==null) taxRate=__DEFAULT_TAX_RATE;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

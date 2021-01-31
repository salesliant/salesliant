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
 * <p>Title: TransferOrderHistoryEntry</p>
 *
 * <p>Description: Domain Object describing a TransferOrderHistoryEntry entity</p>
 *
 */
@Entity (name="TransferOrderHistoryEntry")
@Table (name="transfer_order_history_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class TransferOrderHistoryEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_QUANTITY_RECEIVED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COST = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @purchase_order_history_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @purchase_order_history_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-purchase_order_history_id@
    @Column(name="purchase_order_history_id"   , nullable=true , unique=false)
    private Integer purchaseOrderHistory; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_order@
    @Column(name="display_order"   , nullable=true , unique=false)
    private Integer displayOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_look_up_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_look_up_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_look_up_code@
    @Column(name="item_look_up_code"  , length=64 , nullable=true , unique=false)
    private String itemLookUpCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity@
    @Column(name="quantity"   , nullable=false , unique=false)
    private java.math.BigDecimal quantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_received@
    @Column(name="quantity_received"   , nullable=false , unique=false)
    private java.math.BigDecimal quantityReceived; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_description@
    @Column(name="item_description"  , length=128 , nullable=true , unique=false)
    private String itemDescription; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost@
    @Column(name="cost"   , nullable=false , unique=false)
    private java.math.BigDecimal cost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line_note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line_note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line_note@
    @Column(name="line_note"  , length=65535 , nullable=true , unique=false)
    private String lineNote; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="transfer_order_history_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TransferOrderHistory transferOrderHistory;  

    @Column(name="transfer_order_history_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer transferOrderHistoryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer itemId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-field-transfer_order_history_entry@
    @OneToMany (targetEntity=com.salesliant.entity.SerialNumber.class, fetch=FetchType.LAZY, mappedBy="transferOrderHistoryEntry", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SerialNumber> serialNumbers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public TransferOrderHistoryEntry() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-purchase_order_history_id@
    public Integer getPurchaseOrderHistory() {
        return purchaseOrderHistory;
    }
	
    public void setPurchaseOrderHistory (Integer purchaseOrderHistory) {
        this.purchaseOrderHistory =  purchaseOrderHistory;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_look_up_code@
    public String getItemLookUpCode() {
        return itemLookUpCode;
    }
	
    public void setItemLookUpCode (String itemLookUpCode) {
        this.itemLookUpCode =  itemLookUpCode;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_received@
    public java.math.BigDecimal getQuantityReceived() {
        return quantityReceived;
    }
	
    public void setQuantityReceived (java.math.BigDecimal quantityReceived) {
        this.quantityReceived =  quantityReceived;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cost@
    public java.math.BigDecimal getCost() {
        return cost;
    }
	
    public void setCost (java.math.BigDecimal cost) {
        this.cost =  cost;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-getter-transfer_order_history_entry@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-transfer_order_history_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (quantityReceived==null) quantityReceived=__DEFAULT_QUANTITY_RECEIVED;
        if (cost==null) cost=__DEFAULT_COST;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-transfer_order_history_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (quantityReceived==null) quantityReceived=__DEFAULT_QUANTITY_RECEIVED;
        if (cost==null) cost=__DEFAULT_COST;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

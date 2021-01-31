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
 * <p>Title: ItemQuantity</p>
 *
 * <p>Description: Domain Object describing a ItemQuantity entity</p>
 *
 */
@Entity (name="ItemQuantity")
@Table (name="item_quantity")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ItemQuantity implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_REORDER_POINT = Integer.valueOf(0);
	public static final Integer __DEFAULT_RESTOCK_LEVEL = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_QUANTITY = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @last_sold-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_sold-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_sold@
    @Column(name="last_sold"   , nullable=true , unique=false)
    private Date lastSold; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_received@
    @Column(name="last_received"   , nullable=true , unique=false)
    private Date lastReceived; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @reorder_point-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @reorder_point-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-reorder_point@
    @Column(name="reorder_point"   , nullable=true , unique=false)
    private Integer reorderPoint; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @restock_level-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @restock_level-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-restock_level@
    @Column(name="restock_level"   , nullable=true , unique=false)
    private Integer restockLevel; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity@
    @Column(name="quantity"   , nullable=true , unique=false)
    private java.math.BigDecimal quantity; 
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

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public ItemQuantity() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_sold@
    public Date getLastSold() {
        return lastSold;
    }
	
    public void setLastSold (Date lastSold) {
        this.lastSold =  lastSold;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_received@
    public Date getLastReceived() {
        return lastReceived;
    }
	
    public void setLastReceived (Date lastReceived) {
        this.lastReceived =  lastReceived;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-reorder_point@
    public Integer getReorderPoint() {
        return reorderPoint;
    }
	
    public void setReorderPoint (Integer reorderPoint) {
        this.reorderPoint =  reorderPoint;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-restock_level@
    public Integer getRestockLevel() {
        return restockLevel;
    }
	
    public void setRestockLevel (Integer restockLevel) {
        this.restockLevel =  restockLevel;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-item_quantity@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (reorderPoint==null) reorderPoint=__DEFAULT_REORDER_POINT;
        if (restockLevel==null) restockLevel=__DEFAULT_RESTOCK_LEVEL;
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-item_quantity@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (reorderPoint==null) reorderPoint=__DEFAULT_REORDER_POINT;
        if (restockLevel==null) restockLevel=__DEFAULT_RESTOCK_LEVEL;
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

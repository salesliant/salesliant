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
 * <p>Title: KitEntry</p>
 *
 * <p>Description: Domain Object describing a KitEntry entity</p>
 *
 */
@Entity (name="KitEntry")
@Table (name="kit_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class KitEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_QUANTITY = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity@
    @Column(name="quantity"   , nullable=false , unique=false)
    private Integer quantity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price_different-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price_different-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price_different@
    @Column(name="price_different"   , nullable=true , unique=false)
    private java.math.BigDecimal priceDifferent; 
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

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="component_item_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Item componentItem;  

    @Column(name="component_item_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer componentItemId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="kit_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private Kit kit;  

    @Column(name="kit_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer kitId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public KitEntry() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity@
    public Integer getQuantity() {
        return quantity;
    }
	
    public void setQuantity (Integer quantity) {
        this.quantity =  quantity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price_different@
    public java.math.BigDecimal getPriceDifferent() {
        return priceDifferent;
    }
	
    public void setPriceDifferent (java.math.BigDecimal priceDifferent) {
        this.priceDifferent =  priceDifferent;
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


    public Item getComponentItem () {
    	return componentItem;
    }
	
    public void setComponentItem (Item componentItem) {
    	this.componentItem = componentItem;
    }

    public Integer getComponentItemId() {
        return componentItemId;
    }
	
    public void setComponentItemId (Integer componentItem) {
        this.componentItemId =  componentItem;
    }
	
    public Kit getKit () {
    	return kit;
    }
	
    public void setKit (Kit kit) {
    	this.kit = kit;
    }

    public Integer getKitId() {
        return kitId;
    }
	
    public void setKitId (Integer kit) {
        this.kitId =  kit;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-kit_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-kit_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantity==null) quantity=__DEFAULT_QUANTITY;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

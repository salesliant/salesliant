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
 * <p>Title: ReorderList</p>
 *
 * <p>Description: Domain Object describing a ReorderList entity</p>
 *
 */
@Entity (name="ReorderList")
@Table (name="reorder_list")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ReorderList implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_QUANTITY_NEEDED = Integer.valueOf(0);
	public static final Integer __DEFAULT_QUANTITY_TO_ORDER = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_needed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_needed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_needed@
    @Column(name="quantity_needed"   , nullable=true , unique=false)
    private Integer quantityNeeded; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quantity_to_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quantity_to_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quantity_to_order@
    @Column(name="quantity_to_order"   , nullable=true , unique=false)
    private Integer quantityToOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @taged-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @taged-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-taged@
    @Column(name="taged"   , nullable=true , unique=false)
    private Boolean taged; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Vendor vendor;  

    @Column(name="vendor_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer vendorId;

    /**
    * Default constructor
    */
    public ReorderList() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_needed@
    public Integer getQuantityNeeded() {
        return quantityNeeded;
    }
	
    public void setQuantityNeeded (Integer quantityNeeded) {
        this.quantityNeeded =  quantityNeeded;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quantity_to_order@
    public Integer getQuantityToOrder() {
        return quantityToOrder;
    }
	
    public void setQuantityToOrder (Integer quantityToOrder) {
        this.quantityToOrder =  quantityToOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-taged@
    public Boolean getTaged() {
        return taged;
    }
	
    public void setTaged (Boolean taged) {
        this.taged =  taged;
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
	
    public Vendor getVendor () {
    	return vendor;
    }
	
    public void setVendor (Vendor vendor) {
    	this.vendor = vendor;
    }

    public Integer getVendorId() {
        return vendorId;
    }
	
    public void setVendorId (Integer vendor) {
        this.vendorId =  vendor;
    }
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-reorder_list@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (quantityNeeded==null) quantityNeeded=__DEFAULT_QUANTITY_NEEDED;
        if (quantityToOrder==null) quantityToOrder=__DEFAULT_QUANTITY_TO_ORDER;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-reorder_list@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (quantityNeeded==null) quantityNeeded=__DEFAULT_QUANTITY_NEEDED;
        if (quantityToOrder==null) quantityToOrder=__DEFAULT_QUANTITY_TO_ORDER;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

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
 * <p>Title: Promotion</p>
 *
 * <p>Description: Domain Object describing a Promotion entity</p>
 *
 */
@Entity (name="Promotion")
@Table (name="promotion")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Promotion implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_PRICE1 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE2 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE3 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE4 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE5 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final java.math.BigDecimal __DEFAULT_PRICE6 = java.math.BigDecimal.valueOf(1000000.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @start_time-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @start_time-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-start_time@
    @Column(name="start_time"   , nullable=false , unique=false)
    private Date startTime; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @end_time-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @end_time-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-end_time@
    @Column(name="end_time"   , nullable=false , unique=false)
    private Date endTime; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-enabled@
    @Column(name="enabled"   , nullable=true , unique=false)
    private Boolean enabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price1@
    @Column(name="price1"   , nullable=true , unique=false)
    private java.math.BigDecimal price1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price2@
    @Column(name="price2"   , nullable=true , unique=false)
    private java.math.BigDecimal price2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price3@
    @Column(name="price3"   , nullable=true , unique=false)
    private java.math.BigDecimal price3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price4@
    @Column(name="price4"   , nullable=true , unique=false)
    private java.math.BigDecimal price4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price5@
    @Column(name="price5"   , nullable=true , unique=false)
    private java.math.BigDecimal price5; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @price6-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @price6-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-price6@
    @Column(name="price6"   , nullable=true , unique=false)
    private java.math.BigDecimal price6; 
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

    @Column(name="item_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public Promotion() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-start_time@
    public Date getStartTime() {
        return startTime;
    }
	
    public void setStartTime (Date startTime) {
        this.startTime =  startTime;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-end_time@
    public Date getEndTime() {
        return endTime;
    }
	
    public void setEndTime (Date endTime) {
        this.endTime =  endTime;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-enabled@
    public Boolean getEnabled() {
        return enabled;
    }
	
    public void setEnabled (Boolean enabled) {
        this.enabled =  enabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price1@
    public java.math.BigDecimal getPrice1() {
        return price1;
    }
	
    public void setPrice1 (java.math.BigDecimal price1) {
        this.price1 =  price1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price2@
    public java.math.BigDecimal getPrice2() {
        return price2;
    }
	
    public void setPrice2 (java.math.BigDecimal price2) {
        this.price2 =  price2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price3@
    public java.math.BigDecimal getPrice3() {
        return price3;
    }
	
    public void setPrice3 (java.math.BigDecimal price3) {
        this.price3 =  price3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price4@
    public java.math.BigDecimal getPrice4() {
        return price4;
    }
	
    public void setPrice4 (java.math.BigDecimal price4) {
        this.price4 =  price4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price5@
    public java.math.BigDecimal getPrice5() {
        return price5;
    }
	
    public void setPrice5 (java.math.BigDecimal price5) {
        this.price5 =  price5;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-price6@
    public java.math.BigDecimal getPrice6() {
        return price6;
    }
	
    public void setPrice6 (java.math.BigDecimal price6) {
        this.price6 =  price6;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-promotion@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (price1==null) price1=__DEFAULT_PRICE1;
        if (price2==null) price2=__DEFAULT_PRICE2;
        if (price3==null) price3=__DEFAULT_PRICE3;
        if (price4==null) price4=__DEFAULT_PRICE4;
        if (price5==null) price5=__DEFAULT_PRICE5;
        if (price6==null) price6=__DEFAULT_PRICE6;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-promotion@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (price1==null) price1=__DEFAULT_PRICE1;
        if (price2==null) price2=__DEFAULT_PRICE2;
        if (price3==null) price3=__DEFAULT_PRICE3;
        if (price4==null) price4=__DEFAULT_PRICE4;
        if (price5==null) price5=__DEFAULT_PRICE5;
        if (price6==null) price6=__DEFAULT_PRICE6;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

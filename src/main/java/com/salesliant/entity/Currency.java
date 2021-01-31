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
 * <p>Title: Currency</p>
 *
 * <p>Description: Domain Object describing a Currency entity</p>
 *
 */
@Entity (name="Currency")
@Table (name="currency")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Currency implements Serializable {
    private static final long serialVersionUID = 1L;

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @title-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @title-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-title@
    @Column(name="title"  , length=128 , nullable=false , unique=false)
    private String title; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=3 , nullable=false , unique=true)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @symbol_left-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @symbol_left-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-symbol_left@
    @Column(name="symbol_left"  , length=12 , nullable=true , unique=false)
    private String symbolLeft; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @symbol_right-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @symbol_right-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-symbol_right@
    @Column(name="symbol_right"  , length=12 , nullable=true , unique=false)
    private String symbolRight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @decimal_places-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @decimal_places-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-decimal_places@
    @Column(name="decimal_places"   , nullable=true , unique=false)
    private Short decimalPlaces; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value@
    @Column(name="value"   , nullable=true , unique=false)
    private Long value; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_updated-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_updated-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_updated@
    @Column(name="last_updated"   , nullable=true , unique=false)
    private Timestamp lastUpdated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-field-currency@
    @OneToMany (targetEntity=com.salesliant.entity.Store.class, fetch=FetchType.LAZY, mappedBy="defaultCurrency", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Store> stores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Currency() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-title@
    public String getTitle() {
        return title;
    }
	
    public void setTitle (String title) {
        this.title =  title;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-code@
    public String getCode() {
        return code;
    }
	
    public void setCode (String code) {
        this.code =  code;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-symbol_left@
    public String getSymbolLeft() {
        return symbolLeft;
    }
	
    public void setSymbolLeft (String symbolLeft) {
        this.symbolLeft =  symbolLeft;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-symbol_right@
    public String getSymbolRight() {
        return symbolRight;
    }
	
    public void setSymbolRight (String symbolRight) {
        this.symbolRight =  symbolRight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-decimal_places@
    public Short getDecimalPlaces() {
        return decimalPlaces;
    }
	
    public void setDecimalPlaces (Short decimalPlaces) {
        this.decimalPlaces =  decimalPlaces;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value@
    public Long getValue() {
        return value;
    }
	
    public void setValue (Long value) {
        this.value =  value;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_updated@
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }
	
    public void setLastUpdated (Timestamp lastUpdated) {
        this.lastUpdated =  lastUpdated;
    }
	
//MP-MANAGED-UPDATABLE-ENDING



//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-getter-currency@
    public List<Store> getStores() {
        if (stores == null){
            stores = new ArrayList<>();
        }
        return stores;
    }

    public void setStores (List<Store> stores) {
        this.stores = stores;
    }	
    
    public void addStores (Store element) {
    	    getStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING



//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

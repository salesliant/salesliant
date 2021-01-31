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
 * <p>Title: TaxRate</p>
 *
 * <p>Description: Domain Object describing a TaxRate entity</p>
 *
 */
@Entity (name="TaxRate")
@Table (name="tax_rate")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class TaxRate implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=128 , nullable=false , unique=false)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-rate@
    @Column(name="rate"   , nullable=false , unique=false)
    private java.math.BigDecimal rate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fixed_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fixed_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fixed_amount@
    @Column(name="fixed_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal fixedAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @include_previous_tax-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @include_previous_tax-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-include_previous_tax@
    @Column(name="include_previous_tax"   , nullable=true , unique=false)
    private Boolean includePreviousTax; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @use_partial_dollar-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @use_partial_dollar-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-use_partial_dollar@
    @Column(name="use_partial_dollar"   , nullable=true , unique=false)
    private Boolean usePartialDollar; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @apply_over_minimum-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @apply_over_minimum-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-apply_over_minimum@
    @Column(name="apply_over_minimum"   , nullable=true , unique=false)
    private Boolean applyOverMinimum; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_minimum-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_minimum-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_minimum@
    @Column(name="item_minimum"   , nullable=true , unique=false)
    private java.math.BigDecimal itemMinimum; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @item_maximum-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @item_maximum-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-item_maximum@
    @Column(name="item_maximum"   , nullable=true , unique=false)
    private java.math.BigDecimal itemMaximum; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket01-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket01-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket01@
    @Column(name="bracket01"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket01; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket02-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket02-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket02@
    @Column(name="bracket02"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket02; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket03-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket03-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket03@
    @Column(name="bracket03"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket03; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket04-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket04-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket04@
    @Column(name="bracket04"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket04; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket05-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket05-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket05@
    @Column(name="bracket05"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket05; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket06-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket06-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket06@
    @Column(name="bracket06"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket06; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket07-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket07-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket07@
    @Column(name="bracket07"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket07; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket08-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket08-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket08@
    @Column(name="bracket08"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket08; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket09-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket09-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket09@
    @Column(name="bracket09"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket09; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket10-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket10-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket10@
    @Column(name="bracket10"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket10; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @bracket00-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @bracket00-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-bracket00@
    @Column(name="bracket00"   , nullable=true , unique=false)
    private java.math.BigDecimal bracket00; 
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @taxs-field-tax_rate@
    @OneToMany (targetEntity=com.salesliant.entity.Tax.class, fetch=FetchType.LAZY, mappedBy="taxRate", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Tax> taxs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public TaxRate() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-name@
    public String getName() {
        return name;
    }
	
    public void setName (String name) {
        this.name =  name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-rate@
    public java.math.BigDecimal getRate() {
        return rate;
    }
	
    public void setRate (java.math.BigDecimal rate) {
        this.rate =  rate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fixed_amount@
    public java.math.BigDecimal getFixedAmount() {
        return fixedAmount;
    }
	
    public void setFixedAmount (java.math.BigDecimal fixedAmount) {
        this.fixedAmount =  fixedAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-include_previous_tax@
    public Boolean getIncludePreviousTax() {
        return includePreviousTax;
    }
	
    public void setIncludePreviousTax (Boolean includePreviousTax) {
        this.includePreviousTax =  includePreviousTax;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-use_partial_dollar@
    public Boolean getUsePartialDollar() {
        return usePartialDollar;
    }
	
    public void setUsePartialDollar (Boolean usePartialDollar) {
        this.usePartialDollar =  usePartialDollar;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-apply_over_minimum@
    public Boolean getApplyOverMinimum() {
        return applyOverMinimum;
    }
	
    public void setApplyOverMinimum (Boolean applyOverMinimum) {
        this.applyOverMinimum =  applyOverMinimum;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_minimum@
    public java.math.BigDecimal getItemMinimum() {
        return itemMinimum;
    }
	
    public void setItemMinimum (java.math.BigDecimal itemMinimum) {
        this.itemMinimum =  itemMinimum;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-item_maximum@
    public java.math.BigDecimal getItemMaximum() {
        return itemMaximum;
    }
	
    public void setItemMaximum (java.math.BigDecimal itemMaximum) {
        this.itemMaximum =  itemMaximum;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket01@
    public java.math.BigDecimal getBracket01() {
        return bracket01;
    }
	
    public void setBracket01 (java.math.BigDecimal bracket01) {
        this.bracket01 =  bracket01;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket02@
    public java.math.BigDecimal getBracket02() {
        return bracket02;
    }
	
    public void setBracket02 (java.math.BigDecimal bracket02) {
        this.bracket02 =  bracket02;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket03@
    public java.math.BigDecimal getBracket03() {
        return bracket03;
    }
	
    public void setBracket03 (java.math.BigDecimal bracket03) {
        this.bracket03 =  bracket03;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket04@
    public java.math.BigDecimal getBracket04() {
        return bracket04;
    }
	
    public void setBracket04 (java.math.BigDecimal bracket04) {
        this.bracket04 =  bracket04;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket05@
    public java.math.BigDecimal getBracket05() {
        return bracket05;
    }
	
    public void setBracket05 (java.math.BigDecimal bracket05) {
        this.bracket05 =  bracket05;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket06@
    public java.math.BigDecimal getBracket06() {
        return bracket06;
    }
	
    public void setBracket06 (java.math.BigDecimal bracket06) {
        this.bracket06 =  bracket06;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket07@
    public java.math.BigDecimal getBracket07() {
        return bracket07;
    }
	
    public void setBracket07 (java.math.BigDecimal bracket07) {
        this.bracket07 =  bracket07;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket08@
    public java.math.BigDecimal getBracket08() {
        return bracket08;
    }
	
    public void setBracket08 (java.math.BigDecimal bracket08) {
        this.bracket08 =  bracket08;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket09@
    public java.math.BigDecimal getBracket09() {
        return bracket09;
    }
	
    public void setBracket09 (java.math.BigDecimal bracket09) {
        this.bracket09 =  bracket09;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket10@
    public java.math.BigDecimal getBracket10() {
        return bracket10;
    }
	
    public void setBracket10 (java.math.BigDecimal bracket10) {
        this.bracket10 =  bracket10;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-bracket00@
    public java.math.BigDecimal getBracket00() {
        return bracket00;
    }
	
    public void setBracket00 (java.math.BigDecimal bracket00) {
        this.bracket00 =  bracket00;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @taxs-getter-tax_rate@
    public List<Tax> getTaxs() {
        if (taxs == null){
            taxs = new ArrayList<>();
        }
        return taxs;
    }

    public void setTaxs (List<Tax> taxs) {
        this.taxs = taxs;
    }	
    
    public void addTaxs (Tax element) {
    	    getTaxs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-tax_rate@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-tax_rate@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

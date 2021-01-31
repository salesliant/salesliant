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
 * <p>Title: ShippingService</p>
 *
 * <p>Description: Domain Object describing a ShippingService entity</p>
 *
 */
@Entity (name="ShippingService")
@Table (name="shipping_service")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ShippingService implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=32 , nullable=false , unique=true)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge_by_weight-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge_by_weight-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge_by_weight@
    @Column(name="charge_by_weight"   , nullable=true , unique=false)
    private Boolean chargeByWeight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @interpolate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @interpolate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-interpolate@
    @Column(name="interpolate"   , nullable=true , unique=false)
    private Boolean interpolate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value1@
    @Column(name="value1"   , nullable=true , unique=false)
    private java.math.BigDecimal value1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge1@
    @Column(name="charge1"   , nullable=true , unique=false)
    private java.math.BigDecimal charge1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value2@
    @Column(name="value2"   , nullable=true , unique=false)
    private java.math.BigDecimal value2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge2@
    @Column(name="charge2"   , nullable=true , unique=false)
    private java.math.BigDecimal charge2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value3@
    @Column(name="value3"   , nullable=true , unique=false)
    private java.math.BigDecimal value3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge3@
    @Column(name="charge3"   , nullable=true , unique=false)
    private java.math.BigDecimal charge3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value4@
    @Column(name="value4"   , nullable=true , unique=false)
    private java.math.BigDecimal value4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge4@
    @Column(name="charge4"   , nullable=true , unique=false)
    private java.math.BigDecimal charge4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value5@
    @Column(name="value5"   , nullable=true , unique=false)
    private java.math.BigDecimal value5; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge5@
    @Column(name="charge5"   , nullable=true , unique=false)
    private java.math.BigDecimal charge5; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value6-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value6-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value6@
    @Column(name="value6"   , nullable=true , unique=false)
    private java.math.BigDecimal value6; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge6-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge6-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge6@
    @Column(name="charge6"   , nullable=true , unique=false)
    private java.math.BigDecimal charge6; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value7-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value7-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value7@
    @Column(name="value7"   , nullable=true , unique=false)
    private java.math.BigDecimal value7; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge7-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge7-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge7@
    @Column(name="charge7"   , nullable=true , unique=false)
    private java.math.BigDecimal charge7; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value8-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value8-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value8@
    @Column(name="value8"   , nullable=true , unique=false)
    private java.math.BigDecimal value8; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge8-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge8-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge8@
    @Column(name="charge8"   , nullable=true , unique=false)
    private java.math.BigDecimal charge8; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value9-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value9-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value9@
    @Column(name="value9"   , nullable=true , unique=false)
    private java.math.BigDecimal value9; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge9-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge9-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge9@
    @Column(name="charge9"   , nullable=true , unique=false)
    private java.math.BigDecimal charge9; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value10-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value10-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value10@
    @Column(name="value10"   , nullable=true , unique=false)
    private java.math.BigDecimal value10; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge10-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge10-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge10@
    @Column(name="charge10"   , nullable=true , unique=false)
    private java.math.BigDecimal charge10; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value11-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value11-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value11@
    @Column(name="value11"   , nullable=true , unique=false)
    private java.math.BigDecimal value11; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge11-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge11-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge11@
    @Column(name="charge11"   , nullable=true , unique=false)
    private java.math.BigDecimal charge11; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value12-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value12-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value12@
    @Column(name="value12"   , nullable=true , unique=false)
    private java.math.BigDecimal value12; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge12-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge12-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge12@
    @Column(name="charge12"   , nullable=true , unique=false)
    private java.math.BigDecimal charge12; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value13-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value13-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value13@
    @Column(name="value13"   , nullable=true , unique=false)
    private java.math.BigDecimal value13; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge13-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge13-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge13@
    @Column(name="charge13"   , nullable=true , unique=false)
    private java.math.BigDecimal charge13; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value14-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value14-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value14@
    @Column(name="value14"   , nullable=true , unique=false)
    private java.math.BigDecimal value14; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge14-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge14-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge14@
    @Column(name="charge14"   , nullable=true , unique=false)
    private java.math.BigDecimal charge14; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value15-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value15-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value15@
    @Column(name="value15"   , nullable=true , unique=false)
    private java.math.BigDecimal value15; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge15-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge15-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge15@
    @Column(name="charge15"   , nullable=true , unique=false)
    private java.math.BigDecimal charge15; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="shipping_carrier_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private ShippingCarrier shippingCarrier;  

    @Column(name="shipping_carrier_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer shippingCarrierId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerGroups-field-shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerGroup.class, fetch=FetchType.LAZY, mappedBy="shippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerGroup> customerGroups = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-field-shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrder.class, fetch=FetchType.LAZY, mappedBy="shippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrder> returnOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrderEntries-field-shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrderEntry.class, fetch=FetchType.LAZY, mappedBy="shippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrderEntry> salesOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippings-field-shipping_service@
    @OneToMany (targetEntity=com.salesliant.entity.Shipping.class, fetch=FetchType.LAZY, mappedBy="shippingService", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Shipping> shippings = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public ShippingService() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-code@
    public String getCode() {
        return code;
    }
	
    public void setCode (String code) {
        this.code =  code;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge_by_weight@
    public Boolean getChargeByWeight() {
        return chargeByWeight;
    }
	
    public void setChargeByWeight (Boolean chargeByWeight) {
        this.chargeByWeight =  chargeByWeight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-interpolate@
    public Boolean getInterpolate() {
        return interpolate;
    }
	
    public void setInterpolate (Boolean interpolate) {
        this.interpolate =  interpolate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value1@
    public java.math.BigDecimal getValue1() {
        return value1;
    }
	
    public void setValue1 (java.math.BigDecimal value1) {
        this.value1 =  value1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge1@
    public java.math.BigDecimal getCharge1() {
        return charge1;
    }
	
    public void setCharge1 (java.math.BigDecimal charge1) {
        this.charge1 =  charge1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value2@
    public java.math.BigDecimal getValue2() {
        return value2;
    }
	
    public void setValue2 (java.math.BigDecimal value2) {
        this.value2 =  value2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge2@
    public java.math.BigDecimal getCharge2() {
        return charge2;
    }
	
    public void setCharge2 (java.math.BigDecimal charge2) {
        this.charge2 =  charge2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value3@
    public java.math.BigDecimal getValue3() {
        return value3;
    }
	
    public void setValue3 (java.math.BigDecimal value3) {
        this.value3 =  value3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge3@
    public java.math.BigDecimal getCharge3() {
        return charge3;
    }
	
    public void setCharge3 (java.math.BigDecimal charge3) {
        this.charge3 =  charge3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value4@
    public java.math.BigDecimal getValue4() {
        return value4;
    }
	
    public void setValue4 (java.math.BigDecimal value4) {
        this.value4 =  value4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge4@
    public java.math.BigDecimal getCharge4() {
        return charge4;
    }
	
    public void setCharge4 (java.math.BigDecimal charge4) {
        this.charge4 =  charge4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value5@
    public java.math.BigDecimal getValue5() {
        return value5;
    }
	
    public void setValue5 (java.math.BigDecimal value5) {
        this.value5 =  value5;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge5@
    public java.math.BigDecimal getCharge5() {
        return charge5;
    }
	
    public void setCharge5 (java.math.BigDecimal charge5) {
        this.charge5 =  charge5;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value6@
    public java.math.BigDecimal getValue6() {
        return value6;
    }
	
    public void setValue6 (java.math.BigDecimal value6) {
        this.value6 =  value6;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge6@
    public java.math.BigDecimal getCharge6() {
        return charge6;
    }
	
    public void setCharge6 (java.math.BigDecimal charge6) {
        this.charge6 =  charge6;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value7@
    public java.math.BigDecimal getValue7() {
        return value7;
    }
	
    public void setValue7 (java.math.BigDecimal value7) {
        this.value7 =  value7;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge7@
    public java.math.BigDecimal getCharge7() {
        return charge7;
    }
	
    public void setCharge7 (java.math.BigDecimal charge7) {
        this.charge7 =  charge7;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value8@
    public java.math.BigDecimal getValue8() {
        return value8;
    }
	
    public void setValue8 (java.math.BigDecimal value8) {
        this.value8 =  value8;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge8@
    public java.math.BigDecimal getCharge8() {
        return charge8;
    }
	
    public void setCharge8 (java.math.BigDecimal charge8) {
        this.charge8 =  charge8;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value9@
    public java.math.BigDecimal getValue9() {
        return value9;
    }
	
    public void setValue9 (java.math.BigDecimal value9) {
        this.value9 =  value9;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge9@
    public java.math.BigDecimal getCharge9() {
        return charge9;
    }
	
    public void setCharge9 (java.math.BigDecimal charge9) {
        this.charge9 =  charge9;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value10@
    public java.math.BigDecimal getValue10() {
        return value10;
    }
	
    public void setValue10 (java.math.BigDecimal value10) {
        this.value10 =  value10;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge10@
    public java.math.BigDecimal getCharge10() {
        return charge10;
    }
	
    public void setCharge10 (java.math.BigDecimal charge10) {
        this.charge10 =  charge10;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value11@
    public java.math.BigDecimal getValue11() {
        return value11;
    }
	
    public void setValue11 (java.math.BigDecimal value11) {
        this.value11 =  value11;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge11@
    public java.math.BigDecimal getCharge11() {
        return charge11;
    }
	
    public void setCharge11 (java.math.BigDecimal charge11) {
        this.charge11 =  charge11;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value12@
    public java.math.BigDecimal getValue12() {
        return value12;
    }
	
    public void setValue12 (java.math.BigDecimal value12) {
        this.value12 =  value12;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge12@
    public java.math.BigDecimal getCharge12() {
        return charge12;
    }
	
    public void setCharge12 (java.math.BigDecimal charge12) {
        this.charge12 =  charge12;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value13@
    public java.math.BigDecimal getValue13() {
        return value13;
    }
	
    public void setValue13 (java.math.BigDecimal value13) {
        this.value13 =  value13;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge13@
    public java.math.BigDecimal getCharge13() {
        return charge13;
    }
	
    public void setCharge13 (java.math.BigDecimal charge13) {
        this.charge13 =  charge13;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value14@
    public java.math.BigDecimal getValue14() {
        return value14;
    }
	
    public void setValue14 (java.math.BigDecimal value14) {
        this.value14 =  value14;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge14@
    public java.math.BigDecimal getCharge14() {
        return charge14;
    }
	
    public void setCharge14 (java.math.BigDecimal charge14) {
        this.charge14 =  charge14;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value15@
    public java.math.BigDecimal getValue15() {
        return value15;
    }
	
    public void setValue15 (java.math.BigDecimal value15) {
        this.value15 =  value15;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge15@
    public java.math.BigDecimal getCharge15() {
        return charge15;
    }
	
    public void setCharge15 (java.math.BigDecimal charge15) {
        this.charge15 =  charge15;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerGroups-getter-shipping_service@
    public List<CustomerGroup> getCustomerGroups() {
        if (customerGroups == null){
            customerGroups = new ArrayList<>();
        }
        return customerGroups;
    }

    public void setCustomerGroups (List<CustomerGroup> customerGroups) {
        this.customerGroups = customerGroups;
    }	
    
    public void addCustomerGroups (CustomerGroup element) {
    	    getCustomerGroups().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-getter-shipping_service@
    public List<ReturnOrder> getReturnOrders() {
        if (returnOrders == null){
            returnOrders = new ArrayList<>();
        }
        return returnOrders;
    }

    public void setReturnOrders (List<ReturnOrder> returnOrders) {
        this.returnOrders = returnOrders;
    }	
    
    public void addReturnOrders (ReturnOrder element) {
    	    getReturnOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrderEntries-getter-shipping_service@
    public List<SalesOrderEntry> getSalesOrderEntries() {
        if (salesOrderEntries == null){
            salesOrderEntries = new ArrayList<>();
        }
        return salesOrderEntries;
    }

    public void setSalesOrderEntries (List<SalesOrderEntry> salesOrderEntries) {
        this.salesOrderEntries = salesOrderEntries;
    }	
    
    public void addSalesOrderEntries (SalesOrderEntry element) {
    	    getSalesOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippings-getter-shipping_service@
    public List<Shipping> getShippings() {
        if (shippings == null){
            shippings = new ArrayList<>();
        }
        return shippings;
    }

    public void setShippings (List<Shipping> shippings) {
        this.shippings = shippings;
    }	
    
    public void addShippings (Shipping element) {
    	    getShippings().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-shipping_service@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-shipping_service@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

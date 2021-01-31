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
 * <p>Title: CurrencyCoin</p>
 *
 * <p>Description: Domain Object describing a CurrencyCoin entity</p>
 *
 */
@Entity (name="CurrencyCoin")
@Table (name="currency_coin")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class CurrencyCoin implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_COUNT = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=128 , nullable=false , unique=false)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_order@
    @Column(name="display_order"   , nullable=false , unique=false)
    private Integer displayOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @value-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @value-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-value@
    @Column(name="value"   , nullable=false , unique=false)
    private java.math.BigDecimal value; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-count@
    @Column(name="count"   , nullable=false , unique=false)
    private Integer count; 
//MP-MANAGED-UPDATABLE-ENDING

    /**
    * Default constructor
    */
    public CurrencyCoin() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_order@
    public Integer getDisplayOrder() {
        return displayOrder;
    }
	
    public void setDisplayOrder (Integer displayOrder) {
        this.displayOrder =  displayOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-value@
    public java.math.BigDecimal getValue() {
        return value;
    }
	
    public void setValue (java.math.BigDecimal value) {
        this.value =  value;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-count@
    public Integer getCount() {
        return count;
    }
	
    public void setCount (Integer count) {
        this.count =  count;
    }
	
//MP-MANAGED-UPDATABLE-ENDING




//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-currency_coin@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (count==null) count=__DEFAULT_COUNT;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-currency_coin@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (count==null) count=__DEFAULT_COUNT;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

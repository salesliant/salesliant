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
 * <p>Title: CustomerGroupDiscount</p>
 *
 * <p>Description: Domain Object describing a CustomerGroupDiscount entity</p>
 *
 */
@Entity (name="CustomerGroupDiscount")
@Table (name="customer_group_discount")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class CustomerGroupDiscount implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_CUSTOMER_GROUP = Integer.valueOf(0);
	public static final Integer __DEFAULT_CATEGORY = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_group_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_group_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_group_id@
    @Column(name="customer_group_id"   , nullable=false , unique=false)
    private Integer customerGroup; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @category_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @category_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-category_id@
    @Column(name="category_id"   , nullable=false , unique=false)
    private Integer category; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount@
    @Column(name="discount"  , length=16 , nullable=false , unique=false)
    private String discount; 
//MP-MANAGED-UPDATABLE-ENDING

    /**
    * Default constructor
    */
    public CustomerGroupDiscount() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_group_id@
    public Integer getCustomerGroup() {
        return customerGroup;
    }
	
    public void setCustomerGroup (Integer customerGroup) {
        this.customerGroup =  customerGroup;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-category_id@
    public Integer getCategory() {
        return category;
    }
	
    public void setCategory (Integer category) {
        this.category =  category;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount@
    public String getDiscount() {
        return discount;
    }
	
    public void setDiscount (String discount) {
        this.discount =  discount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING




//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-customer_group_discount@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (customerGroup==null) customerGroup=__DEFAULT_CUSTOMER_GROUP;
        if (category==null) category=__DEFAULT_CATEGORY;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-customer_group_discount@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (customerGroup==null) customerGroup=__DEFAULT_CUSTOMER_GROUP;
        if (category==null) category=__DEFAULT_CATEGORY;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

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
 * <p>Title: Special</p>
 *
 * <p>Description: Domain Object describing a Special entity</p>
 *
 */
@Entity (name="Special")
@Table (name="special")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Special implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_STATUS = Integer.valueOf(1);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @product_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @product_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-product_id@
    @Column(name="product_id"   , nullable=false , unique=true)
    private Integer product; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @new_product_price-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @new_product_price-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-new_product_price@
    @Column(name="new_product_price"   , nullable=false , unique=false)
    private java.math.BigDecimal newProductPrice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_added-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_added-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_added@
    @Column(name="date_added"   , nullable=true , unique=false)
    private Timestamp dateAdded; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_modified-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_modified-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_modified@
    @Column(name="last_modified"   , nullable=true , unique=false)
    private Timestamp lastModified; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @start_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @start_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-start_date@
    @Column(name="start_date"   , nullable=true , unique=false)
    private Timestamp startDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @expires_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @expires_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-expires_date@
    @Column(name="expires_date"   , nullable=true , unique=false)
    private Timestamp expiresDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_status_change-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_status_change-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_status_change@
    @Column(name="date_status_change"   , nullable=true , unique=false)
    private Timestamp dateStatusChange; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=false , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

    /**
    * Default constructor
    */
    public Special() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-product_id@
    public Integer getProduct() {
        return product;
    }
	
    public void setProduct (Integer product) {
        this.product =  product;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-new_product_price@
    public java.math.BigDecimal getNewProductPrice() {
        return newProductPrice;
    }
	
    public void setNewProductPrice (java.math.BigDecimal newProductPrice) {
        this.newProductPrice =  newProductPrice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_added@
    public Timestamp getDateAdded() {
        return dateAdded;
    }
	
    public void setDateAdded (Timestamp dateAdded) {
        this.dateAdded =  dateAdded;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_modified@
    public Timestamp getLastModified() {
        return lastModified;
    }
	
    public void setLastModified (Timestamp lastModified) {
        this.lastModified =  lastModified;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-start_date@
    public Timestamp getStartDate() {
        return startDate;
    }
	
    public void setStartDate (Timestamp startDate) {
        this.startDate =  startDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-expires_date@
    public Timestamp getExpiresDate() {
        return expiresDate;
    }
	
    public void setExpiresDate (Timestamp expiresDate) {
        this.expiresDate =  expiresDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_status_change@
    public Timestamp getDateStatusChange() {
        return dateStatusChange;
    }
	
    public void setDateStatusChange (Timestamp dateStatusChange) {
        this.dateStatusChange =  dateStatusChange;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-status@
    public Integer getStatus() {
        return status;
    }
	
    public void setStatus (Integer status) {
        this.status =  status;
    }
	
//MP-MANAGED-UPDATABLE-ENDING




//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-special@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (status==null) status=__DEFAULT_STATUS;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-special@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (status==null) status=__DEFAULT_STATUS;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

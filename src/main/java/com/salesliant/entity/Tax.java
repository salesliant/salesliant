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
 * <p>Title: Tax</p>
 *
 * <p>Description: Domain Object describing a Tax entity</p>
 *
 */
@Entity (name="Tax")
@Table (name="tax")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Tax implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(1);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="tax_class_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private TaxClass taxClass;  

    @Column(name="tax_class_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer taxClassId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="tax_rate_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private TaxRate taxRate;  

    @Column(name="tax_rate_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer taxRateId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="tax_zone_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private TaxZone taxZone;  

    @Column(name="tax_zone_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer taxZoneId;

    /**
    * Default constructor
    */
    public Tax() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public TaxClass getTaxClass () {
    	return taxClass;
    }
	
    public void setTaxClass (TaxClass taxClass) {
    	this.taxClass = taxClass;
    }

    public Integer getTaxClassId() {
        return taxClassId;
    }
	
    public void setTaxClassId (Integer taxClass) {
        this.taxClassId =  taxClass;
    }
	
    public TaxRate getTaxRate () {
    	return taxRate;
    }
	
    public void setTaxRate (TaxRate taxRate) {
    	this.taxRate = taxRate;
    }

    public Integer getTaxRateId() {
        return taxRateId;
    }
	
    public void setTaxRateId (Integer taxRate) {
        this.taxRateId =  taxRate;
    }
	
    public TaxZone getTaxZone () {
    	return taxZone;
    }
	
    public void setTaxZone (TaxZone taxZone) {
    	this.taxZone = taxZone;
    }

    public Integer getTaxZoneId() {
        return taxZoneId;
    }
	
    public void setTaxZoneId (Integer taxZone) {
        this.taxZoneId =  taxZone;
    }
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-tax@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-tax@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

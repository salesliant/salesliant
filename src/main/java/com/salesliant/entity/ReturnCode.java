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
 * <p>Title: ReturnCode</p>
 *
 * <p>Description: Domain Object describing a ReturnCode entity</p>
 *
 */
@Entity (name="ReturnCode")
@Table (name="return_code")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ReturnCode implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-field-return_code@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnEntry.class, fetch=FetchType.LAZY, mappedBy="returnCode", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnEntry> returnEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public ReturnCode() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-getter-return_code@
    public List<ReturnEntry> getReturnEntries() {
        if (returnEntries == null){
            returnEntries = new ArrayList<>();
        }
        return returnEntries;
    }

    public void setReturnEntries (List<ReturnEntry> returnEntries) {
        this.returnEntries = returnEntries;
    }	
    
    public void addReturnEntries (ReturnEntry element) {
    	    getReturnEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-return_code@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-return_code@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

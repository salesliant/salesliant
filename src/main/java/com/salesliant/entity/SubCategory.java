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
 * <p>Title: SubCategory</p>
 *
 * <p>Description: Domain Object describing a SubCategory entity</p>
 *
 */
@Entity (name="SubCategory")
@Table (name="sub_category")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class SubCategory implements Serializable {
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

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="category_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Category category;  

    @Column(name="category_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer categoryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public SubCategory() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public Category getCategory () {
    	return category;
    }
	
    public void setCategory (Category category) {
    	this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }
	
    public void setCategoryId (Integer category) {
        this.categoryId =  category;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-sub_category@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-sub_category@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

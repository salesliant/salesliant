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
 * <p>Title: ManfactureSerialNumber</p>
 *
 * <p>Description: Domain Object describing a ManfactureSerialNumber entity</p>
 *
 */
@Entity (name="ManfactureSerialNumber")
@Table (name="manfacture_serial_number")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ManfactureSerialNumber implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_id@
    @Column(name="invoice_id"   , nullable=true , unique=true)
    private Integer invoice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_entry_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_entry_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_entry_id@
    @Column(name="invoice_entry_id"   , nullable=true , unique=true)
    private Integer invoiceEntry; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @manfacture_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @manfacture_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-manfacture_id@
    @Column(name="manfacture_id"   , nullable=true , unique=true)
    private Integer manfacture; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sys_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sys_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sys_id@
    @Column(name="sys_id"   , nullable=true , unique=false)
    private Integer sys; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @serial_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @serial_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-serial_number@
    @Column(name="serial_number"  , length=32 , nullable=true , unique=true)
    private String serialNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tag@
    @Column(name="tag"   , nullable=true , unique=false)
    private Integer tag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-type@
    @Column(name="type"  , length=1 , nullable=true , unique=false)
    private String type; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @store_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @store_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-store_id@
    @Column(name="store_id"   , nullable=true , unique=false)
    private Integer store; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    /**
    * Default constructor
    */
    public ManfactureSerialNumber() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_id@
    public Integer getInvoice() {
        return invoice;
    }
	
    public void setInvoice (Integer invoice) {
        this.invoice =  invoice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_entry_id@
    public Integer getInvoiceEntry() {
        return invoiceEntry;
    }
	
    public void setInvoiceEntry (Integer invoiceEntry) {
        this.invoiceEntry =  invoiceEntry;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-manfacture_id@
    public Integer getManfacture() {
        return manfacture;
    }
	
    public void setManfacture (Integer manfacture) {
        this.manfacture =  manfacture;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sys_id@
    public Integer getSys() {
        return sys;
    }
	
    public void setSys (Integer sys) {
        this.sys =  sys;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-serial_number@
    public String getSerialNumber() {
        return serialNumber;
    }
	
    public void setSerialNumber (String serialNumber) {
        this.serialNumber =  serialNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tag@
    public Integer getTag() {
        return tag;
    }
	
    public void setTag (Integer tag) {
        this.tag =  tag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-type@
    public String getType() {
        return type;
    }
	
    public void setType (String type) {
        this.type =  type;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-store_id@
    public Integer getStore() {
        return store;
    }
	
    public void setStore (Integer store) {
        this.store =  store;
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




//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-manfacture_serial_number@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-manfacture_serial_number@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

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
 * <p>Title: TaxZone</p>
 *
 * <p>Description: Domain Object describing a TaxZone entity</p>
 *
 */
@Entity (name="TaxZone")
@Table (name="tax_zone")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class TaxZone implements Serializable {
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

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=false , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_added-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_added-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_added@
    @Column(name="date_added"   , nullable=true , unique=false)
    private Date dateAdded; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="zone_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Zone zone;  

    @Column(name="zone_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer zoneId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-field-tax_zone@
    @OneToMany (targetEntity=com.salesliant.entity.Customer.class, fetch=FetchType.LAZY, mappedBy="taxZone", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Customer> customers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-field-tax_zone@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerShipTo.class, fetch=FetchType.LAZY, mappedBy="taxZone", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerShipTo> customerShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-field-tax_zone@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrder.class, fetch=FetchType.LAZY, mappedBy="taxZone", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrder> salesOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-field-tax_zone@
    @OneToMany (targetEntity=com.salesliant.entity.Store.class, fetch=FetchType.LAZY, mappedBy="defaultTaxZone", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Store> stores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @taxs-field-tax_zone@
    @OneToMany (targetEntity=com.salesliant.entity.Tax.class, fetch=FetchType.LAZY, mappedBy="taxZone", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Tax> taxs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public TaxZone() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_added@
    public Date getDateAdded() {
        return dateAdded;
    }
	
    public void setDateAdded (Date dateAdded) {
        this.dateAdded =  dateAdded;
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


    public Zone getZone () {
    	return zone;
    }
	
    public void setZone (Zone zone) {
    	this.zone = zone;
    }

    public Integer getZoneId() {
        return zoneId;
    }
	
    public void setZoneId (Integer zone) {
        this.zoneId =  zone;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-getter-tax_zone@
    public List<Customer> getCustomers() {
        if (customers == null){
            customers = new ArrayList<>();
        }
        return customers;
    }

    public void setCustomers (List<Customer> customers) {
        this.customers = customers;
    }	
    
    public void addCustomers (Customer element) {
    	    getCustomers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-getter-tax_zone@
    public List<CustomerShipTo> getCustomerShipTos() {
        if (customerShipTos == null){
            customerShipTos = new ArrayList<>();
        }
        return customerShipTos;
    }

    public void setCustomerShipTos (List<CustomerShipTo> customerShipTos) {
        this.customerShipTos = customerShipTos;
    }	
    
    public void addCustomerShipTos (CustomerShipTo element) {
    	    getCustomerShipTos().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-getter-tax_zone@
    public List<SalesOrder> getSalesOrders() {
        if (salesOrders == null){
            salesOrders = new ArrayList<>();
        }
        return salesOrders;
    }

    public void setSalesOrders (List<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }	
    
    public void addSalesOrders (SalesOrder element) {
    	    getSalesOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-getter-tax_zone@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @taxs-getter-tax_zone@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-tax_zone@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-tax_zone@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

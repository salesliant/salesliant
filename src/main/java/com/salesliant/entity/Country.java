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
 * <p>Title: Country</p>
 *
 * <p>Description: Domain Object describing a Country entity</p>
 *
 */
@Entity (name="Country")
@Table (name="country")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name@
    @Column(name="name"  , length=128 , nullable=false , unique=true)
    private String name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @iso_code_2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @iso_code_2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-iso_code_2@
    @Column(name="iso_code_2"  , length=2 , nullable=false , unique=true)
    private String isoCode2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @iso_code_3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @iso_code_3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-iso_code_3@
    @Column(name="iso_code_3"  , length=3 , nullable=false , unique=true)
    private String isoCode3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @address_format-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @address_format-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-address_format@
    @Column(name="address_format"  , length=128 , nullable=true , unique=false)
    private String addressFormat; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.Customer.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Customer> customers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerShipTo.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerShipTo> customerShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employees-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.Employee.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Employee> employees = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @postCodes-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.PostCode.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PostCode> postCodes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.Store.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Store> stores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.Vendor.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Vendor> vendors = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShipTos-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.VendorShipTo.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorShipTo> vendorShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @zones-field-country@
    @OneToMany (targetEntity=com.salesliant.entity.Zone.class, fetch=FetchType.LAZY, mappedBy="country", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Zone> zones = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Country() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-iso_code_2@
    public String getIsoCode2() {
        return isoCode2;
    }
	
    public void setIsoCode2 (String isoCode2) {
        this.isoCode2 =  isoCode2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-iso_code_3@
    public String getIsoCode3() {
        return isoCode3;
    }
	
    public void setIsoCode3 (String isoCode3) {
        this.isoCode3 =  isoCode3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-address_format@
    public String getAddressFormat() {
        return addressFormat;
    }
	
    public void setAddressFormat (String addressFormat) {
        this.addressFormat =  addressFormat;
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



//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-getter-country@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-getter-country@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employees-getter-country@
    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<>();
        }
        return employees;
    }

    public void setEmployees (List<Employee> employees) {
        this.employees = employees;
    }	
    
    public void addEmployees (Employee element) {
    	    getEmployees().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @postCodes-getter-country@
    public List<PostCode> getPostCodes() {
        if (postCodes == null){
            postCodes = new ArrayList<>();
        }
        return postCodes;
    }

    public void setPostCodes (List<PostCode> postCodes) {
        this.postCodes = postCodes;
    }	
    
    public void addPostCodes (PostCode element) {
    	    getPostCodes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stores-getter-country@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-getter-country@
    public List<Vendor> getVendors() {
        if (vendors == null){
            vendors = new ArrayList<>();
        }
        return vendors;
    }

    public void setVendors (List<Vendor> vendors) {
        this.vendors = vendors;
    }	
    
    public void addVendors (Vendor element) {
    	    getVendors().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShipTos-getter-country@
    public List<VendorShipTo> getVendorShipTos() {
        if (vendorShipTos == null){
            vendorShipTos = new ArrayList<>();
        }
        return vendorShipTos;
    }

    public void setVendorShipTos (List<VendorShipTo> vendorShipTos) {
        this.vendorShipTos = vendorShipTos;
    }	
    
    public void addVendorShipTos (VendorShipTo element) {
    	    getVendorShipTos().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @zones-getter-country@
    public List<Zone> getZones() {
        if (zones == null){
            zones = new ArrayList<>();
        }
        return zones;
    }

    public void setZones (List<Zone> zones) {
        this.zones = zones;
    }	
    
    public void addZones (Zone element) {
    	    getZones().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING



//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

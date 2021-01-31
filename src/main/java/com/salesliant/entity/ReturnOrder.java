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
 * <p>Title: ReturnOrder</p>
 *
 * <p>Description: Domain Object describing a ReturnOrder entity</p>
 *
 */
@Entity (name="ReturnOrder")
@Table (name="return_order")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ReturnOrder implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @return_to_vendor_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_to_vendor_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_to_vendor_number@
    @Column(name="return_to_vendor_number"   , nullable=false , unique=true)
    private Integer returnToVendorNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @rma_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @rma_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-rma_number@
    @Column(name="rma_number"  , length=32 , nullable=false , unique=true)
    private String rmaNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total@
    @Column(name="total"   , nullable=true , unique=false)
    private java.math.BigDecimal total; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @shipping_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @shipping_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-shipping_amount@
    @Column(name="shipping_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal shippingAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_company-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_company-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_company@
    @Column(name="ship_to_company"  , length=64 , nullable=true , unique=false)
    private String shipToCompany; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_department-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_department-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_department@
    @Column(name="ship_to_department"  , length=64 , nullable=true , unique=false)
    private String shipToDepartment; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address1@
    @Column(name="ship_to_address1"  , length=128 , nullable=true , unique=false)
    private String shipToAddress1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_address2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_address2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_address2@
    @Column(name="ship_to_address2"  , length=128 , nullable=true , unique=false)
    private String shipToAddress2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_city@
    @Column(name="ship_to_city"  , length=64 , nullable=true , unique=false)
    private String shipToCity; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_state@
    @Column(name="ship_to_state"  , length=32 , nullable=true , unique=false)
    private String shipToState; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_post_code@
    @Column(name="ship_to_post_code"  , length=16 , nullable=true , unique=false)
    private String shipToPostCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ship_to_country-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ship_to_country-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ship_to_country@
    @Column(name="ship_to_country"  , length=64 , nullable=true , unique=false)
    private String shipToCountry; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=16277215 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Date dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_expected-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_expected-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_expected@
    @Column(name="date_expected"   , nullable=true , unique=false)
    private Timestamp dateExpected; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_returned-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_returned-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_returned@
    @Column(name="date_returned"   , nullable=true , unique=false)
    private Timestamp dateReturned; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="employee_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Employee employee;  

    @Column(name="employee_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer employeeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="shipping_service_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private ShippingService shippingService;  

    @Column(name="shipping_service_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer shippingServiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="vendor_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Vendor vendor;  

    @Column(name="vendor_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer vendorId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrderEntries-field-return_order@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrderEntry.class, fetch=FetchType.LAZY, mappedBy="returnOrder", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrderEntry> returnOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public ReturnOrder() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_to_vendor_number@
    public Integer getReturnToVendorNumber() {
        return returnToVendorNumber;
    }
	
    public void setReturnToVendorNumber (Integer returnToVendorNumber) {
        this.returnToVendorNumber =  returnToVendorNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-rma_number@
    public String getRmaNumber() {
        return rmaNumber;
    }
	
    public void setRmaNumber (String rmaNumber) {
        this.rmaNumber =  rmaNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total@
    public java.math.BigDecimal getTotal() {
        return total;
    }
	
    public void setTotal (java.math.BigDecimal total) {
        this.total =  total;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-shipping_amount@
    public java.math.BigDecimal getShippingAmount() {
        return shippingAmount;
    }
	
    public void setShippingAmount (java.math.BigDecimal shippingAmount) {
        this.shippingAmount =  shippingAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_company@
    public String getShipToCompany() {
        return shipToCompany;
    }
	
    public void setShipToCompany (String shipToCompany) {
        this.shipToCompany =  shipToCompany;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_department@
    public String getShipToDepartment() {
        return shipToDepartment;
    }
	
    public void setShipToDepartment (String shipToDepartment) {
        this.shipToDepartment =  shipToDepartment;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_address1@
    public String getShipToAddress1() {
        return shipToAddress1;
    }
	
    public void setShipToAddress1 (String shipToAddress1) {
        this.shipToAddress1 =  shipToAddress1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_address2@
    public String getShipToAddress2() {
        return shipToAddress2;
    }
	
    public void setShipToAddress2 (String shipToAddress2) {
        this.shipToAddress2 =  shipToAddress2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_city@
    public String getShipToCity() {
        return shipToCity;
    }
	
    public void setShipToCity (String shipToCity) {
        this.shipToCity =  shipToCity;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_state@
    public String getShipToState() {
        return shipToState;
    }
	
    public void setShipToState (String shipToState) {
        this.shipToState =  shipToState;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_post_code@
    public String getShipToPostCode() {
        return shipToPostCode;
    }
	
    public void setShipToPostCode (String shipToPostCode) {
        this.shipToPostCode =  shipToPostCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ship_to_country@
    public String getShipToCountry() {
        return shipToCountry;
    }
	
    public void setShipToCountry (String shipToCountry) {
        this.shipToCountry =  shipToCountry;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-note@
    public String getNote() {
        return note;
    }
	
    public void setNote (String note) {
        this.note =  note;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_created@
    public Date getDateCreated() {
        return dateCreated;
    }
	
    public void setDateCreated (Date dateCreated) {
        this.dateCreated =  dateCreated;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_expected@
    public Timestamp getDateExpected() {
        return dateExpected;
    }
	
    public void setDateExpected (Timestamp dateExpected) {
        this.dateExpected =  dateExpected;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_returned@
    public Timestamp getDateReturned() {
        return dateReturned;
    }
	
    public void setDateReturned (Timestamp dateReturned) {
        this.dateReturned =  dateReturned;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public Employee getEmployee () {
    	return employee;
    }
	
    public void setEmployee (Employee employee) {
    	this.employee = employee;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
	
    public void setEmployeeId (Integer employee) {
        this.employeeId =  employee;
    }
	
    public ShippingService getShippingService () {
    	return shippingService;
    }
	
    public void setShippingService (ShippingService shippingService) {
    	this.shippingService = shippingService;
    }

    public Integer getShippingServiceId() {
        return shippingServiceId;
    }
	
    public void setShippingServiceId (Integer shippingService) {
        this.shippingServiceId =  shippingService;
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
	
    public Vendor getVendor () {
    	return vendor;
    }
	
    public void setVendor (Vendor vendor) {
    	this.vendor = vendor;
    }

    public Integer getVendorId() {
        return vendorId;
    }
	
    public void setVendorId (Integer vendor) {
        this.vendorId =  vendor;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrderEntries-getter-return_order@
    public List<ReturnOrderEntry> getReturnOrderEntries() {
        if (returnOrderEntries == null){
            returnOrderEntries = new ArrayList<>();
        }
        return returnOrderEntries;
    }

    public void setReturnOrderEntries (List<ReturnOrderEntry> returnOrderEntries) {
        this.returnOrderEntries = returnOrderEntries;
    }	
    
    public void addReturnOrderEntries (ReturnOrderEntry element) {
    	    getReturnOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-return_order@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-return_order@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

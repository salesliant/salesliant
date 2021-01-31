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
 * <p>Title: Shipping</p>
 *
 * <p>Description: Domain Object describing a Shipping entity</p>
 *
 */
@Entity (name="Shipping")
@Table (name="shipping")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Shipping implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @first_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @first_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-first_name@
    @Column(name="first_name"  , length=32 , nullable=true , unique=false)
    private String firstName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_name@
    @Column(name="last_name"  , length=32 , nullable=true , unique=false)
    private String lastName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @title-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @title-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-title@
    @Column(name="title"  , length=32 , nullable=true , unique=false)
    private String title; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @phone_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @phone_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-phone_number@
    @Column(name="phone_number"  , length=32 , nullable=true , unique=false)
    private String phoneNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fax_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fax_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fax_number@
    @Column(name="fax_number"  , length=32 , nullable=true , unique=false)
    private String faxNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cell_phone_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cell_phone_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cell_phone_number@
    @Column(name="cell_phone_number"  , length=32 , nullable=true , unique=false)
    private String cellPhoneNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @email_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @email_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-email_address@
    @Column(name="email_address"  , length=96 , nullable=true , unique=false)
    private String emailAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @company-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @company-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-company@
    @Column(name="company"  , length=64 , nullable=true , unique=false)
    private String company; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @department-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @department-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-department@
    @Column(name="department"  , length=64 , nullable=true , unique=false)
    private String department; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @address1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @address1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-address1@
    @Column(name="address1"  , length=128 , nullable=true , unique=false)
    private String address1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @address2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @address2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-address2@
    @Column(name="address2"  , length=128 , nullable=true , unique=false)
    private String address2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-city@
    @Column(name="city"  , length=64 , nullable=true , unique=false)
    private String city; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-state@
    @Column(name="state"  , length=32 , nullable=true , unique=false)
    private String state; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-post_code@
    @Column(name="post_code"  , length=16 , nullable=true , unique=false)
    private String postCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @country-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @country-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-country@
    @Column(name="country"  , length=64 , nullable=true , unique=false)
    private String country; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @charge-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @charge-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-charge@
    @Column(name="charge"   , nullable=true , unique=false)
    private java.math.BigDecimal charge; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @net_ship_cost-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @net_ship_cost-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-net_ship_cost@
    @Column(name="net_ship_cost"   , nullable=true , unique=false)
    private java.math.BigDecimal netShipCost; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @declared_value-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @declared_value-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-declared_value@
    @Column(name="declared_value"   , nullable=true , unique=false)
    private java.math.BigDecimal declaredValue; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tracking_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tracking_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tracking_number@
    @Column(name="tracking_number"  , length=32 , nullable=true , unique=false)
    private String trackingNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cod_return_tracking_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cod_return_tracking_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cod_return_tracking_number@
    @Column(name="cod_return_tracking_number"  , length=32 , nullable=true , unique=false)
    private String codReturnTrackingNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_shipped-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_shipped-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_shipped@
    @Column(name="date_shipped"   , nullable=true , unique=false)
    private Date dateShipped; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_processed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_processed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_processed@
    @Column(name="date_processed"   , nullable=true , unique=false)
    private Date dateProcessed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total_weight-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total_weight-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total_weight@
    @Column(name="total_weight"   , nullable=true , unique=false)
    private java.math.BigDecimal totalWeight; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @data_packed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @data_packed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-data_packed@
    @Column(name="data_packed"   , nullable=true , unique=false)
    private Date dataPacked; 
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
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private SalesOrder invoice;  

    @Column(name="invoice_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer invoiceId;

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

    /**
    * Default constructor
    */
    public Shipping() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-first_name@
    public String getFirstName() {
        return firstName;
    }
	
    public void setFirstName (String firstName) {
        this.firstName =  firstName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_name@
    public String getLastName() {
        return lastName;
    }
	
    public void setLastName (String lastName) {
        this.lastName =  lastName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-title@
    public String getTitle() {
        return title;
    }
	
    public void setTitle (String title) {
        this.title =  title;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-phone_number@
    public String getPhoneNumber() {
        return phoneNumber;
    }
	
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber =  phoneNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fax_number@
    public String getFaxNumber() {
        return faxNumber;
    }
	
    public void setFaxNumber (String faxNumber) {
        this.faxNumber =  faxNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cell_phone_number@
    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }
	
    public void setCellPhoneNumber (String cellPhoneNumber) {
        this.cellPhoneNumber =  cellPhoneNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-email_address@
    public String getEmailAddress() {
        return emailAddress;
    }
	
    public void setEmailAddress (String emailAddress) {
        this.emailAddress =  emailAddress;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-company@
    public String getCompany() {
        return company;
    }
	
    public void setCompany (String company) {
        this.company =  company;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-department@
    public String getDepartment() {
        return department;
    }
	
    public void setDepartment (String department) {
        this.department =  department;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-address1@
    public String getAddress1() {
        return address1;
    }
	
    public void setAddress1 (String address1) {
        this.address1 =  address1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-address2@
    public String getAddress2() {
        return address2;
    }
	
    public void setAddress2 (String address2) {
        this.address2 =  address2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-city@
    public String getCity() {
        return city;
    }
	
    public void setCity (String city) {
        this.city =  city;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-state@
    public String getState() {
        return state;
    }
	
    public void setState (String state) {
        this.state =  state;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-post_code@
    public String getPostCode() {
        return postCode;
    }
	
    public void setPostCode (String postCode) {
        this.postCode =  postCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-country@
    public String getCountry() {
        return country;
    }
	
    public void setCountry (String country) {
        this.country =  country;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-charge@
    public java.math.BigDecimal getCharge() {
        return charge;
    }
	
    public void setCharge (java.math.BigDecimal charge) {
        this.charge =  charge;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-net_ship_cost@
    public java.math.BigDecimal getNetShipCost() {
        return netShipCost;
    }
	
    public void setNetShipCost (java.math.BigDecimal netShipCost) {
        this.netShipCost =  netShipCost;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-declared_value@
    public java.math.BigDecimal getDeclaredValue() {
        return declaredValue;
    }
	
    public void setDeclaredValue (java.math.BigDecimal declaredValue) {
        this.declaredValue =  declaredValue;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tracking_number@
    public String getTrackingNumber() {
        return trackingNumber;
    }
	
    public void setTrackingNumber (String trackingNumber) {
        this.trackingNumber =  trackingNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cod_return_tracking_number@
    public String getCodReturnTrackingNumber() {
        return codReturnTrackingNumber;
    }
	
    public void setCodReturnTrackingNumber (String codReturnTrackingNumber) {
        this.codReturnTrackingNumber =  codReturnTrackingNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_shipped@
    public Date getDateShipped() {
        return dateShipped;
    }
	
    public void setDateShipped (Date dateShipped) {
        this.dateShipped =  dateShipped;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_processed@
    public Date getDateProcessed() {
        return dateProcessed;
    }
	
    public void setDateProcessed (Date dateProcessed) {
        this.dateProcessed =  dateProcessed;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total_weight@
    public java.math.BigDecimal getTotalWeight() {
        return totalWeight;
    }
	
    public void setTotalWeight (java.math.BigDecimal totalWeight) {
        this.totalWeight =  totalWeight;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-data_packed@
    public Date getDataPacked() {
        return dataPacked;
    }
	
    public void setDataPacked (Date dataPacked) {
        this.dataPacked =  dataPacked;
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


    public SalesOrder getInvoice () {
    	return invoice;
    }
	
    public void setInvoice (SalesOrder invoice) {
    	this.invoice = invoice;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }
	
    public void setInvoiceId (Integer invoice) {
        this.invoiceId =  invoice;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-shipping@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-shipping@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

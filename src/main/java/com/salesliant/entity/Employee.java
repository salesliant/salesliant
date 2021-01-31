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
 * <p>Title: Employee</p>
 *
 * <p>Description: Domain Object describing a Employee entity</p>
 *
 */
@Entity (name="Employee")
@Table (name="employee")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @login_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @login_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-login_id@
    @Column(name="login_id"  , length=16 , nullable=false , unique=true)
    private String login; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @active_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @active_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-active_tag@
    @Column(name="active_tag"   , nullable=true , unique=false)
    private Boolean activeTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @password-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @password-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-password@
    @Column(name="password"  , length=40 , nullable=false , unique=false)
    private String password; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @ssn-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @ssn-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-ssn@
    @Column(name="ssn"  , length=32 , nullable=true , unique=false)
    private String ssn; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @name_on_sales_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @name_on_sales_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-name_on_sales_order@
    @Column(name="name_on_sales_order"  , length=64 , nullable=true , unique=false)
    private String nameOnSalesOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Date dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

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

//MP-MANAGED-ADDED-AREA-BEGINNING @picture_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @picture_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-picture_name@
    @Column(name="picture_name"  , length=128 , nullable=true , unique=false)
    private String pictureName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=16277215 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="country_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Country country;  

    @Column(name="country_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer countryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_group_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private EmployeeGroup employeeGroup;  

    @Column(name="employee_group_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer employeeGroupId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="mail_group_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private MailGroup mailGroup;  

    @Column(name="mail_group_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer mailGroupId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @appointments-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.Appointment.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Appointment> appointments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.Batch.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Batch> batchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissionEmployees-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.Commission.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Commission> commissionEmployees = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissionSaleses-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.Commission.class, fetch=FetchType.LAZY, mappedBy="sales", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Commission> commissionSaleses = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.Consignment.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Consignment> consignments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.Customer.class, fetch=FetchType.LAZY, mappedBy="sales", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Customer> customers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @dropPayouts-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.DropPayout.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <DropPayout> dropPayouts = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLog.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLog> itemLogs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentBatchs-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.PaymentBatch.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PaymentBatch> paymentBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnEntry.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnEntry> returnEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrder.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrder> returnOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactionEmployeeProcesseds-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnTransaction.class, fetch=FetchType.LAZY, mappedBy="employeeProcessed", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnTransaction> returnTransactionEmployeeProcesseds = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactionEmployeeReturneds-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnTransaction.class, fetch=FetchType.LAZY, mappedBy="employeeReturned", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnTransaction> returnTransactionEmployeeReturneds = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrder.class, fetch=FetchType.LAZY, mappedBy="sales", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrder> salesOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serviceEntries-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.ServiceEntry.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ServiceEntry> serviceEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @timeCards-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.TimeCard.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TimeCard> timeCards = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voidedTransactions-field-employee@
    @OneToMany (targetEntity=com.salesliant.entity.VoidedTransaction.class, fetch=FetchType.LAZY, mappedBy="employee", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VoidedTransaction> voidedTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Employee() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-login_id@
    public String getLogin() {
        return login;
    }
	
    public void setLogin (String login) {
        this.login =  login;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-active_tag@
    public Boolean getActiveTag() {
        return activeTag;
    }
	
    public void setActiveTag (Boolean activeTag) {
        this.activeTag =  activeTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-password@
    public String getPassword() {
        return password;
    }
	
    public void setPassword (String password) {
        this.password =  password;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-ssn@
    public String getSsn() {
        return ssn;
    }
	
    public void setSsn (String ssn) {
        this.ssn =  ssn;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-name_on_sales_order@
    public String getNameOnSalesOrder() {
        return nameOnSalesOrder;
    }
	
    public void setNameOnSalesOrder (String nameOnSalesOrder) {
        this.nameOnSalesOrder =  nameOnSalesOrder;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-picture_name@
    public String getPictureName() {
        return pictureName;
    }
	
    public void setPictureName (String pictureName) {
        this.pictureName =  pictureName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public Country getCountry () {
    	return country;
    }
	
    public void setCountry (Country country) {
    	this.country = country;
    }

    public Integer getCountryId() {
        return countryId;
    }
	
    public void setCountryId (Integer country) {
        this.countryId =  country;
    }
	
    public EmployeeGroup getEmployeeGroup () {
    	return employeeGroup;
    }
	
    public void setEmployeeGroup (EmployeeGroup employeeGroup) {
    	this.employeeGroup = employeeGroup;
    }

    public Integer getEmployeeGroupId() {
        return employeeGroupId;
    }
	
    public void setEmployeeGroupId (Integer employeeGroup) {
        this.employeeGroupId =  employeeGroup;
    }
	
    public MailGroup getMailGroup () {
    	return mailGroup;
    }
	
    public void setMailGroup (MailGroup mailGroup) {
    	this.mailGroup = mailGroup;
    }

    public Integer getMailGroupId() {
        return mailGroupId;
    }
	
    public void setMailGroupId (Integer mailGroup) {
        this.mailGroupId =  mailGroup;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @appointments-getter-employee@
    public List<Appointment> getAppointments() {
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        return appointments;
    }

    public void setAppointments (List<Appointment> appointments) {
        this.appointments = appointments;
    }	
    
    public void addAppointments (Appointment element) {
    	    getAppointments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-getter-employee@
    public List<Batch> getBatchs() {
        if (batchs == null){
            batchs = new ArrayList<>();
        }
        return batchs;
    }

    public void setBatchs (List<Batch> batchs) {
        this.batchs = batchs;
    }	
    
    public void addBatchs (Batch element) {
    	    getBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissionEmployees-getter-employee@
    public List<Commission> getCommissionEmployees() {
        if (commissionEmployees == null){
            commissionEmployees = new ArrayList<>();
        }
        return commissionEmployees;
    }

    public void setCommissionEmployees (List<Commission> commissionEmployees) {
        this.commissionEmployees = commissionEmployees;
    }	
    
    public void addCommissionEmployees (Commission element) {
    	    getCommissionEmployees().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissionSaleses-getter-employee@
    public List<Commission> getCommissionSaleses() {
        if (commissionSaleses == null){
            commissionSaleses = new ArrayList<>();
        }
        return commissionSaleses;
    }

    public void setCommissionSaleses (List<Commission> commissionSaleses) {
        this.commissionSaleses = commissionSaleses;
    }	
    
    public void addCommissionSaleses (Commission element) {
    	    getCommissionSaleses().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-getter-employee@
    public List<Consignment> getConsignments() {
        if (consignments == null){
            consignments = new ArrayList<>();
        }
        return consignments;
    }

    public void setConsignments (List<Consignment> consignments) {
        this.consignments = consignments;
    }	
    
    public void addConsignments (Consignment element) {
    	    getConsignments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-getter-employee@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @dropPayouts-getter-employee@
    public List<DropPayout> getDropPayouts() {
        if (dropPayouts == null){
            dropPayouts = new ArrayList<>();
        }
        return dropPayouts;
    }

    public void setDropPayouts (List<DropPayout> dropPayouts) {
        this.dropPayouts = dropPayouts;
    }	
    
    public void addDropPayouts (DropPayout element) {
    	    getDropPayouts().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-getter-employee@
    public List<ItemLog> getItemLogs() {
        if (itemLogs == null){
            itemLogs = new ArrayList<>();
        }
        return itemLogs;
    }

    public void setItemLogs (List<ItemLog> itemLogs) {
        this.itemLogs = itemLogs;
    }	
    
    public void addItemLogs (ItemLog element) {
    	    getItemLogs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentBatchs-getter-employee@
    public List<PaymentBatch> getPaymentBatchs() {
        if (paymentBatchs == null){
            paymentBatchs = new ArrayList<>();
        }
        return paymentBatchs;
    }

    public void setPaymentBatchs (List<PaymentBatch> paymentBatchs) {
        this.paymentBatchs = paymentBatchs;
    }	
    
    public void addPaymentBatchs (PaymentBatch element) {
    	    getPaymentBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-getter-employee@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-getter-employee@
    public List<ReturnOrder> getReturnOrders() {
        if (returnOrders == null){
            returnOrders = new ArrayList<>();
        }
        return returnOrders;
    }

    public void setReturnOrders (List<ReturnOrder> returnOrders) {
        this.returnOrders = returnOrders;
    }	
    
    public void addReturnOrders (ReturnOrder element) {
    	    getReturnOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactionEmployeeProcesseds-getter-employee@
    public List<ReturnTransaction> getReturnTransactionEmployeeProcesseds() {
        if (returnTransactionEmployeeProcesseds == null){
            returnTransactionEmployeeProcesseds = new ArrayList<>();
        }
        return returnTransactionEmployeeProcesseds;
    }

    public void setReturnTransactionEmployeeProcesseds (List<ReturnTransaction> returnTransactionEmployeeProcesseds) {
        this.returnTransactionEmployeeProcesseds = returnTransactionEmployeeProcesseds;
    }	
    
    public void addReturnTransactionEmployeeProcesseds (ReturnTransaction element) {
    	    getReturnTransactionEmployeeProcesseds().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactionEmployeeReturneds-getter-employee@
    public List<ReturnTransaction> getReturnTransactionEmployeeReturneds() {
        if (returnTransactionEmployeeReturneds == null){
            returnTransactionEmployeeReturneds = new ArrayList<>();
        }
        return returnTransactionEmployeeReturneds;
    }

    public void setReturnTransactionEmployeeReturneds (List<ReturnTransaction> returnTransactionEmployeeReturneds) {
        this.returnTransactionEmployeeReturneds = returnTransactionEmployeeReturneds;
    }	
    
    public void addReturnTransactionEmployeeReturneds (ReturnTransaction element) {
    	    getReturnTransactionEmployeeReturneds().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-getter-employee@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serviceEntries-getter-employee@
    public List<ServiceEntry> getServiceEntries() {
        if (serviceEntries == null){
            serviceEntries = new ArrayList<>();
        }
        return serviceEntries;
    }

    public void setServiceEntries (List<ServiceEntry> serviceEntries) {
        this.serviceEntries = serviceEntries;
    }	
    
    public void addServiceEntries (ServiceEntry element) {
    	    getServiceEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @timeCards-getter-employee@
    public List<TimeCard> getTimeCards() {
        if (timeCards == null){
            timeCards = new ArrayList<>();
        }
        return timeCards;
    }

    public void setTimeCards (List<TimeCard> timeCards) {
        this.timeCards = timeCards;
    }	
    
    public void addTimeCards (TimeCard element) {
    	    getTimeCards().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voidedTransactions-getter-employee@
    public List<VoidedTransaction> getVoidedTransactions() {
        if (voidedTransactions == null){
            voidedTransactions = new ArrayList<>();
        }
        return voidedTransactions;
    }

    public void setVoidedTransactions (List<VoidedTransaction> voidedTransactions) {
        this.voidedTransactions = voidedTransactions;
    }	
    
    public void addVoidedTransactions (VoidedTransaction element) {
    	    getVoidedTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-employee@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-employee@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

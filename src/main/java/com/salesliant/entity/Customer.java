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
 * <p>Title: Customer</p>
 *
 * <p>Description: Domain Object describing a Customer entity</p>
 *
 */
@Entity (name="Customer")
@Table (name="customer")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_CREDIT_LIMIT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @account_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_number@
    @Column(name="account_number"  , length=64 , nullable=false , unique=true)
    private String accountNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_type@
    @Column(name="customer_type"  , length=1 , nullable=true , unique=false)
    private String customerType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @active_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @active_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-active_tag@
    @Column(name="active_tag"   , nullable=true , unique=false)
    private Boolean activeTag; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @company-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @company-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-company@
    @Column(name="company"  , length=128 , nullable=true , unique=false)
    private String company; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @department-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @department-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-department@
    @Column(name="department"  , length=128 , nullable=true , unique=false)
    private String department; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-post_code@
    @Column(name="post_code"  , length=64 , nullable=true , unique=false)
    private String postCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-city@
    @Column(name="city"  , length=128 , nullable=true , unique=false)
    private String city; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-state@
    @Column(name="state"  , length=128 , nullable=true , unique=false)
    private String state; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_limit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_limit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_limit@
    @Column(name="credit_limit"   , nullable=true , unique=false)
    private java.math.BigDecimal creditLimit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Date dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_visit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_visit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_visit@
    @Column(name="last_visit"   , nullable=true , unique=false)
    private Date lastVisit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @global_customer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @global_customer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-global_customer@
    @Column(name="global_customer"   , nullable=true , unique=false)
    private Boolean globalCustomer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @add_to_email_list_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @add_to_email_list_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-add_to_email_list_tag@
    @Column(name="add_to_email_list_tag"   , nullable=true , unique=false)
    private Boolean addToEmailListTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @assess_finance_charges-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @assess_finance_charges-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-assess_finance_charges@
    @Column(name="assess_finance_charges"   , nullable=true , unique=false)
    private Boolean assessFinanceCharges; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @allow_partial_ship_flag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @allow_partial_ship_flag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-allow_partial_ship_flag@
    @Column(name="allow_partial_ship_flag"   , nullable=true , unique=false)
    private Boolean allowPartialShipFlag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @layaway_customer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @layaway_customer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-layaway_customer@
    @Column(name="layaway_customer"   , nullable=true , unique=false)
    private Boolean layawayCustomer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @limit_purchase-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @limit_purchase-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-limit_purchase@
    @Column(name="limit_purchase"   , nullable=true , unique=false)
    private Boolean limitPurchase; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_exempt-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_exempt-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_exempt@
    @Column(name="tax_exempt"   , nullable=true , unique=false)
    private Boolean taxExempt; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_number@
    @Column(name="tax_number"  , length=32 , nullable=true , unique=false)
    private String taxNumber; 
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
    @JoinColumn(name="customer_group_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private CustomerGroup customerGroup;  

    @Column(name="customer_group_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer customerGroupId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="customer_term_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private CustomerTerm customerTerm;  

    @Column(name="customer_term_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer customerTermId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="sales_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Employee sales;  

    @Column(name="sales_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer salesId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="price_level_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ItemPriceLevel priceLevel;  

    @Column(name="price_level_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer priceLevelId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="tax_zone_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TaxZone taxZone;  

    @Column(name="tax_zone_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer taxZoneId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.AccountReceivable.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountReceivable> accountReceivables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @appointments-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.Appointment.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Appointment> appointments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @cheques-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.Cheque.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Cheque> cheques = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.Consignment.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Consignment> consignments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerBuyers-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerBuyer.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerBuyer> customerBuyers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerNotes-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerNote.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerNote> customerNotes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerShipTo.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerShipTo> customerShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> deposits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificates-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificate.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificate> giftCertificates = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrder.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrder> purchaseOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnTransaction.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnTransaction> returnTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrder.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrder> salesOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voidedTransactions-field-customer@
    @OneToMany (targetEntity=com.salesliant.entity.VoidedTransaction.class, fetch=FetchType.LAZY, mappedBy="customer", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VoidedTransaction> voidedTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Customer() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_number@
    public String getAccountNumber() {
        return accountNumber;
    }
	
    public void setAccountNumber (String accountNumber) {
        this.accountNumber =  accountNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_type@
    public String getCustomerType() {
        return customerType;
    }
	
    public void setCustomerType (String customerType) {
        this.customerType =  customerType;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-company@
    public String getCompany() {
        return company;
    }
	
    public void setCompany (String company) {
        this.company =  company;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-department@
    public String getDepartment() {
        return department;
    }
	
    public void setDepartment (String department) {
        this.department =  department;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_limit@
    public java.math.BigDecimal getCreditLimit() {
        return creditLimit;
    }
	
    public void setCreditLimit (java.math.BigDecimal creditLimit) {
        this.creditLimit =  creditLimit;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_visit@
    public Date getLastVisit() {
        return lastVisit;
    }
	
    public void setLastVisit (Date lastVisit) {
        this.lastVisit =  lastVisit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-global_customer@
    public Boolean getGlobalCustomer() {
        return globalCustomer;
    }
	
    public void setGlobalCustomer (Boolean globalCustomer) {
        this.globalCustomer =  globalCustomer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-add_to_email_list_tag@
    public Boolean getAddToEmailListTag() {
        return addToEmailListTag;
    }
	
    public void setAddToEmailListTag (Boolean addToEmailListTag) {
        this.addToEmailListTag =  addToEmailListTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-assess_finance_charges@
    public Boolean getAssessFinanceCharges() {
        return assessFinanceCharges;
    }
	
    public void setAssessFinanceCharges (Boolean assessFinanceCharges) {
        this.assessFinanceCharges =  assessFinanceCharges;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-allow_partial_ship_flag@
    public Boolean getAllowPartialShipFlag() {
        return allowPartialShipFlag;
    }
	
    public void setAllowPartialShipFlag (Boolean allowPartialShipFlag) {
        this.allowPartialShipFlag =  allowPartialShipFlag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-layaway_customer@
    public Boolean getLayawayCustomer() {
        return layawayCustomer;
    }
	
    public void setLayawayCustomer (Boolean layawayCustomer) {
        this.layawayCustomer =  layawayCustomer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-limit_purchase@
    public Boolean getLimitPurchase() {
        return limitPurchase;
    }
	
    public void setLimitPurchase (Boolean limitPurchase) {
        this.limitPurchase =  limitPurchase;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_exempt@
    public Boolean getTaxExempt() {
        return taxExempt;
    }
	
    public void setTaxExempt (Boolean taxExempt) {
        this.taxExempt =  taxExempt;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax_number@
    public String getTaxNumber() {
        return taxNumber;
    }
	
    public void setTaxNumber (String taxNumber) {
        this.taxNumber =  taxNumber;
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
	
    public CustomerGroup getCustomerGroup () {
    	return customerGroup;
    }
	
    public void setCustomerGroup (CustomerGroup customerGroup) {
    	this.customerGroup = customerGroup;
    }

    public Integer getCustomerGroupId() {
        return customerGroupId;
    }
	
    public void setCustomerGroupId (Integer customerGroup) {
        this.customerGroupId =  customerGroup;
    }
	
    public CustomerTerm getCustomerTerm () {
    	return customerTerm;
    }
	
    public void setCustomerTerm (CustomerTerm customerTerm) {
    	this.customerTerm = customerTerm;
    }

    public Integer getCustomerTermId() {
        return customerTermId;
    }
	
    public void setCustomerTermId (Integer customerTerm) {
        this.customerTermId =  customerTerm;
    }
	
    public Employee getSales () {
    	return sales;
    }
	
    public void setSales (Employee sales) {
    	this.sales = sales;
    }

    public Integer getSalesId() {
        return salesId;
    }
	
    public void setSalesId (Integer sales) {
        this.salesId =  sales;
    }
	
    public ItemPriceLevel getPriceLevel () {
    	return priceLevel;
    }
	
    public void setPriceLevel (ItemPriceLevel priceLevel) {
    	this.priceLevel = priceLevel;
    }

    public Integer getPriceLevelId() {
        return priceLevelId;
    }
	
    public void setPriceLevelId (Integer priceLevel) {
        this.priceLevelId =  priceLevel;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-getter-customer@
    public List<AccountReceivable> getAccountReceivables() {
        if (accountReceivables == null){
            accountReceivables = new ArrayList<>();
        }
        return accountReceivables;
    }

    public void setAccountReceivables (List<AccountReceivable> accountReceivables) {
        this.accountReceivables = accountReceivables;
    }	
    
    public void addAccountReceivables (AccountReceivable element) {
    	    getAccountReceivables().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @appointments-getter-customer@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @cheques-getter-customer@
    public List<Cheque> getCheques() {
        if (cheques == null){
            cheques = new ArrayList<>();
        }
        return cheques;
    }

    public void setCheques (List<Cheque> cheques) {
        this.cheques = cheques;
    }	
    
    public void addCheques (Cheque element) {
    	    getCheques().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-getter-customer@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerBuyers-getter-customer@
    public List<CustomerBuyer> getCustomerBuyers() {
        if (customerBuyers == null){
            customerBuyers = new ArrayList<>();
        }
        return customerBuyers;
    }

    public void setCustomerBuyers (List<CustomerBuyer> customerBuyers) {
        this.customerBuyers = customerBuyers;
    }	
    
    public void addCustomerBuyers (CustomerBuyer element) {
    	    getCustomerBuyers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerNotes-getter-customer@
    public List<CustomerNote> getCustomerNotes() {
        if (customerNotes == null){
            customerNotes = new ArrayList<>();
        }
        return customerNotes;
    }

    public void setCustomerNotes (List<CustomerNote> customerNotes) {
        this.customerNotes = customerNotes;
    }	
    
    public void addCustomerNotes (CustomerNote element) {
    	    getCustomerNotes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-getter-customer@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-getter-customer@
    public List<Deposit> getDeposits() {
        if (deposits == null){
            deposits = new ArrayList<>();
        }
        return deposits;
    }

    public void setDeposits (List<Deposit> deposits) {
        this.deposits = deposits;
    }	
    
    public void addDeposits (Deposit element) {
    	    getDeposits().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificates-getter-customer@
    public List<GiftCertificate> getGiftCertificates() {
        if (giftCertificates == null){
            giftCertificates = new ArrayList<>();
        }
        return giftCertificates;
    }

    public void setGiftCertificates (List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }	
    
    public void addGiftCertificates (GiftCertificate element) {
    	    getGiftCertificates().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-getter-customer@
    public List<PurchaseOrder> getPurchaseOrders() {
        if (purchaseOrders == null){
            purchaseOrders = new ArrayList<>();
        }
        return purchaseOrders;
    }

    public void setPurchaseOrders (List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }	
    
    public void addPurchaseOrders (PurchaseOrder element) {
    	    getPurchaseOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-getter-customer@
    public List<ReturnTransaction> getReturnTransactions() {
        if (returnTransactions == null){
            returnTransactions = new ArrayList<>();
        }
        return returnTransactions;
    }

    public void setReturnTransactions (List<ReturnTransaction> returnTransactions) {
        this.returnTransactions = returnTransactions;
    }	
    
    public void addReturnTransactions (ReturnTransaction element) {
    	    getReturnTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-getter-customer@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voidedTransactions-getter-customer@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-customer@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (creditLimit==null) creditLimit=__DEFAULT_CREDIT_LIMIT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-customer@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (creditLimit==null) creditLimit=__DEFAULT_CREDIT_LIMIT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

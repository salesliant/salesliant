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
 * <p>Title: Vendor</p>
 *
 * <p>Description: Domain Object describing a Vendor entity</p>
 *
 */
@Entity (name="Vendor")
@Table (name="vendor")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Vendor implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_CREDIT_LIMIT = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_RETURN_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_DISCOUNT_DAYS = Integer.valueOf(0);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_RATE = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @company-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @company-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-company@
    @Column(name="company"  , length=64 , nullable=true , unique=false)
    private String company; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_code@
    @Column(name="vendor_code"  , length=32 , nullable=true , unique=false)
    private String vendorCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_number@
    @Column(name="account_number"  , length=32 , nullable=true , unique=true)
    private String accountNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @vendor_contact_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @vendor_contact_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-vendor_contact_name@
    @Column(name="vendor_contact_name"  , length=96 , nullable=true , unique=false)
    private String vendorContactName; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @web_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @web_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-web_address@
    @Column(name="web_address"  , length=255 , nullable=true , unique=false)
    private String webAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @email_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @email_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-email_address@
    @Column(name="email_address"  , length=255 , nullable=true , unique=false)
    private String emailAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_limit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_limit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_limit@
    @Column(name="credit_limit"   , nullable=true , unique=false)
    private java.math.BigDecimal creditLimit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @active_tag-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @active_tag-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-active_tag@
    @Column(name="active_tag"   , nullable=true , unique=false)
    private Boolean activeTag; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @return_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_days@
    @Column(name="return_days"   , nullable=true , unique=false)
    private Integer returnDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax_number@
    @Column(name="tax_number"  , length=32 , nullable=true , unique=false)
    private String taxNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_days@
    @Column(name="discount_days"   , nullable=true , unique=false)
    private Integer discountDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_rate-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_rate-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_rate@
    @Column(name="discount_rate"   , nullable=true , unique=false)
    private java.math.BigDecimal discountRate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @current_auto_sku_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @current_auto_sku_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-current_auto_sku_number@
    @Column(name="current_auto_sku_number"   , nullable=true , unique=false)
    private Integer currentAutoSkuNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @use_vendor_sku-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @use_vendor_sku-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-use_vendor_sku@
    @Column(name="use_vendor_sku"   , nullable=true , unique=false)
    private Boolean useVendorSku; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @use_auto_sku-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @use_auto_sku-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-use_auto_sku@
    @Column(name="use_auto_sku"   , nullable=true , unique=false)
    private Boolean useAutoSku; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @currency_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @currency_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-currency_id@
    @Column(name="currency_id"   , nullable=true , unique=true)
    private Integer currency; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gl_account-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gl_account-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gl_account@
    @Column(name="gl_account"  , length=32 , nullable=true , unique=false)
    private String glAccount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Date dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @last_updated-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @last_updated-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-last_updated@
    @Column(name="last_updated"   , nullable=true , unique=false)
    private Date lastUpdated; 
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
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="default_vendor_contact", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VendorContact defaultVendorContact;  

    @Column(name="default_vendor_contact"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer defaultVendorContactId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="default_vendor_shipping_service_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VendorShippingService defaultVendorShippingService;  

    @Column(name="default_vendor_shipping_service_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer defaultVendorShippingServiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="vendor_term_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private VendorTerm vendorTerm;  

    @Column(name="vendor_term_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer vendorTermId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayables-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.AccountPayable.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountPayable> accountPayables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @items-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.Item.class, fetch=FetchType.LAZY, mappedBy="primaryVendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Item> items = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrder.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrder> purchaseOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistory.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistory> purchaseOrderHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reorderLists-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.ReorderList.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReorderList> reorderLists = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrder.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrder> returnOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorContacts-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.VendorContact.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorContact> vendorContacts = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorItems-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.VendorItem.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorItem> vendorItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorNotes-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.VendorNote.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorNote> vendorNotes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShipTos-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.VendorShipTo.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorShipTo> vendorShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShippingServices-field-vendor@
    @OneToMany (targetEntity=com.salesliant.entity.VendorShippingService.class, fetch=FetchType.LAZY, mappedBy="vendor", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorShippingService> vendorShippingServices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Vendor() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-company@
    public String getCompany() {
        return company;
    }
	
    public void setCompany (String company) {
        this.company =  company;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_code@
    public String getVendorCode() {
        return vendorCode;
    }
	
    public void setVendorCode (String vendorCode) {
        this.vendorCode =  vendorCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_number@
    public String getAccountNumber() {
        return accountNumber;
    }
	
    public void setAccountNumber (String accountNumber) {
        this.accountNumber =  accountNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-vendor_contact_name@
    public String getVendorContactName() {
        return vendorContactName;
    }
	
    public void setVendorContactName (String vendorContactName) {
        this.vendorContactName =  vendorContactName;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-web_address@
    public String getWebAddress() {
        return webAddress;
    }
	
    public void setWebAddress (String webAddress) {
        this.webAddress =  webAddress;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-active_tag@
    public Boolean getActiveTag() {
        return activeTag;
    }
	
    public void setActiveTag (Boolean activeTag) {
        this.activeTag =  activeTag;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_days@
    public Integer getReturnDays() {
        return returnDays;
    }
	
    public void setReturnDays (Integer returnDays) {
        this.returnDays =  returnDays;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_days@
    public Integer getDiscountDays() {
        return discountDays;
    }
	
    public void setDiscountDays (Integer discountDays) {
        this.discountDays =  discountDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_rate@
    public java.math.BigDecimal getDiscountRate() {
        return discountRate;
    }
	
    public void setDiscountRate (java.math.BigDecimal discountRate) {
        this.discountRate =  discountRate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-current_auto_sku_number@
    public Integer getCurrentAutoSkuNumber() {
        return currentAutoSkuNumber;
    }
	
    public void setCurrentAutoSkuNumber (Integer currentAutoSkuNumber) {
        this.currentAutoSkuNumber =  currentAutoSkuNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-use_vendor_sku@
    public Boolean getUseVendorSku() {
        return useVendorSku;
    }
	
    public void setUseVendorSku (Boolean useVendorSku) {
        this.useVendorSku =  useVendorSku;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-use_auto_sku@
    public Boolean getUseAutoSku() {
        return useAutoSku;
    }
	
    public void setUseAutoSku (Boolean useAutoSku) {
        this.useAutoSku =  useAutoSku;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-currency_id@
    public Integer getCurrency() {
        return currency;
    }
	
    public void setCurrency (Integer currency) {
        this.currency =  currency;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gl_account@
    public String getGlAccount() {
        return glAccount;
    }
	
    public void setGlAccount (String glAccount) {
        this.glAccount =  glAccount;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-last_updated@
    public Date getLastUpdated() {
        return lastUpdated;
    }
	
    public void setLastUpdated (Date lastUpdated) {
        this.lastUpdated =  lastUpdated;
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
	
    public VendorContact getDefaultVendorContact () {
    	return defaultVendorContact;
    }
	
    public void setDefaultVendorContact (VendorContact defaultVendorContact) {
    	this.defaultVendorContact = defaultVendorContact;
    }

    public Integer getDefaultVendorContactId() {
        return defaultVendorContactId;
    }
	
    public void setDefaultVendorContactId (Integer defaultVendorContact) {
        this.defaultVendorContactId =  defaultVendorContact;
    }
	
    public VendorShippingService getDefaultVendorShippingService () {
    	return defaultVendorShippingService;
    }
	
    public void setDefaultVendorShippingService (VendorShippingService defaultVendorShippingService) {
    	this.defaultVendorShippingService = defaultVendorShippingService;
    }

    public Integer getDefaultVendorShippingServiceId() {
        return defaultVendorShippingServiceId;
    }
	
    public void setDefaultVendorShippingServiceId (Integer defaultVendorShippingService) {
        this.defaultVendorShippingServiceId =  defaultVendorShippingService;
    }
	
    public VendorTerm getVendorTerm () {
    	return vendorTerm;
    }
	
    public void setVendorTerm (VendorTerm vendorTerm) {
    	this.vendorTerm = vendorTerm;
    }

    public Integer getVendorTermId() {
        return vendorTermId;
    }
	
    public void setVendorTermId (Integer vendorTerm) {
        this.vendorTermId =  vendorTerm;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayables-getter-vendor@
    public List<AccountPayable> getAccountPayables() {
        if (accountPayables == null){
            accountPayables = new ArrayList<>();
        }
        return accountPayables;
    }

    public void setAccountPayables (List<AccountPayable> accountPayables) {
        this.accountPayables = accountPayables;
    }	
    
    public void addAccountPayables (AccountPayable element) {
    	    getAccountPayables().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @items-getter-vendor@
    public List<Item> getItems() {
        if (items == null){
            items = new ArrayList<>();
        }
        return items;
    }

    public void setItems (List<Item> items) {
        this.items = items;
    }	
    
    public void addItems (Item element) {
    	    getItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-getter-vendor@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-getter-vendor@
    public List<PurchaseOrderHistory> getPurchaseOrderHistories() {
        if (purchaseOrderHistories == null){
            purchaseOrderHistories = new ArrayList<>();
        }
        return purchaseOrderHistories;
    }

    public void setPurchaseOrderHistories (List<PurchaseOrderHistory> purchaseOrderHistories) {
        this.purchaseOrderHistories = purchaseOrderHistories;
    }	
    
    public void addPurchaseOrderHistories (PurchaseOrderHistory element) {
    	    getPurchaseOrderHistories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reorderLists-getter-vendor@
    public List<ReorderList> getReorderLists() {
        if (reorderLists == null){
            reorderLists = new ArrayList<>();
        }
        return reorderLists;
    }

    public void setReorderLists (List<ReorderList> reorderLists) {
        this.reorderLists = reorderLists;
    }	
    
    public void addReorderLists (ReorderList element) {
    	    getReorderLists().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-getter-vendor@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorContacts-getter-vendor@
    public List<VendorContact> getVendorContacts() {
        if (vendorContacts == null){
            vendorContacts = new ArrayList<>();
        }
        return vendorContacts;
    }

    public void setVendorContacts (List<VendorContact> vendorContacts) {
        this.vendorContacts = vendorContacts;
    }	
    
    public void addVendorContacts (VendorContact element) {
    	    getVendorContacts().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorItems-getter-vendor@
    public List<VendorItem> getVendorItems() {
        if (vendorItems == null){
            vendorItems = new ArrayList<>();
        }
        return vendorItems;
    }

    public void setVendorItems (List<VendorItem> vendorItems) {
        this.vendorItems = vendorItems;
    }	
    
    public void addVendorItems (VendorItem element) {
    	    getVendorItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorNotes-getter-vendor@
    public List<VendorNote> getVendorNotes() {
        if (vendorNotes == null){
            vendorNotes = new ArrayList<>();
        }
        return vendorNotes;
    }

    public void setVendorNotes (List<VendorNote> vendorNotes) {
        this.vendorNotes = vendorNotes;
    }	
    
    public void addVendorNotes (VendorNote element) {
    	    getVendorNotes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShipTos-getter-vendor@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShippingServices-getter-vendor@
    public List<VendorShippingService> getVendorShippingServices() {
        if (vendorShippingServices == null){
            vendorShippingServices = new ArrayList<>();
        }
        return vendorShippingServices;
    }

    public void setVendorShippingServices (List<VendorShippingService> vendorShippingServices) {
        this.vendorShippingServices = vendorShippingServices;
    }	
    
    public void addVendorShippingServices (VendorShippingService element) {
    	    getVendorShippingServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-vendor@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (creditLimit==null) creditLimit=__DEFAULT_CREDIT_LIMIT;
        if (returnDays==null) returnDays=__DEFAULT_RETURN_DAYS;
        if (discountDays==null) discountDays=__DEFAULT_DISCOUNT_DAYS;
        if (discountRate==null) discountRate=__DEFAULT_DISCOUNT_RATE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-vendor@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (creditLimit==null) creditLimit=__DEFAULT_CREDIT_LIMIT;
        if (returnDays==null) returnDays=__DEFAULT_RETURN_DAYS;
        if (discountDays==null) discountDays=__DEFAULT_DISCOUNT_DAYS;
        if (discountRate==null) discountRate=__DEFAULT_DISCOUNT_RATE;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

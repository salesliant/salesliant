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
 * <p>Title: EmployeeGroup</p>
 *
 * <p>Description: Domain Object describing a EmployeeGroup entity</p>
 *
 */
@Entity (name="EmployeeGroup")
@Table (name="employee_group")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class EmployeeGroup implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_FAIL_LOGON_ATTEMPTS = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=32 , nullable=false , unique=false)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=false , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @module_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @module_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-module_name@
    @Column(name="module_name"  , length=64 , nullable=false , unique=false)
    private String moduleName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_drawer_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_drawer_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_drawer_number@
    @Column(name="cash_drawer_number"  , length=32 , nullable=true , unique=false)
    private String cashDrawerNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fail_logon_attempts-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fail_logon_attempts-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fail_logon_attempts@
    @Column(name="fail_logon_attempts"   , nullable=true , unique=false)
    private Integer failLogonAttempts; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @floor_limit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @floor_limit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-floor_limit@
    @Column(name="floor_limit"   , nullable=true , unique=false)
    private java.math.BigDecimal floorLimit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @return_limit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_limit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_limit@
    @Column(name="return_limit"   , nullable=true , unique=false)
    private java.math.BigDecimal returnLimit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sales_rep-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sales_rep-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sales_rep@
    @Column(name="sales_rep"   , nullable=true , unique=false)
    private Boolean salesRep; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @open_close_drawer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @open_close_drawer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-open_close_drawer@
    @Column(name="open_close_drawer"   , nullable=true , unique=false)
    private Boolean openCloseDrawer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @override_prices-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @override_prices-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-override_prices@
    @Column(name="override_prices"   , nullable=true , unique=false)
    private Boolean overridePrices; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @below_minimum_prices-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @below_minimum_prices-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-below_minimum_prices@
    @Column(name="below_minimum_prices"   , nullable=true , unique=false)
    private Boolean belowMinimumPrices; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @accept_returns-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @accept_returns-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-accept_returns@
    @Column(name="accept_returns"   , nullable=true , unique=false)
    private Boolean acceptReturns; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @allow_pay_in_out-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @allow_pay_in_out-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-allow_pay_in_out@
    @Column(name="allow_pay_in_out"   , nullable=true , unique=false)
    private Boolean allowPayInOut; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @allow_no_sale-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @allow_no_sale-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-allow_no_sale@
    @Column(name="allow_no_sale"   , nullable=true , unique=false)
    private Boolean allowNoSale; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @void_invoice-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @void_invoice-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-void_invoice@
    @Column(name="void_invoice"   , nullable=true , unique=false)
    private Boolean voidInvoice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @void_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @void_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-void_order@
    @Column(name="void_order"   , nullable=true , unique=false)
    private Boolean voidOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @void_service_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @void_service_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-void_service_order@
    @Column(name="void_service_order"   , nullable=true , unique=false)
    private Boolean voidServiceOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @void_internet_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @void_internet_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-void_internet_order@
    @Column(name="void_internet_order"   , nullable=true , unique=false)
    private Boolean voidInternetOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @void_quote-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @void_quote-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-void_quote@
    @Column(name="void_quote"   , nullable=true , unique=false)
    private Boolean voidQuote; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @edit_invoice-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @edit_invoice-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-edit_invoice@
    @Column(name="edit_invoice"   , nullable=true , unique=false)
    private Boolean editInvoice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @unlock_register-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @unlock_register-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-unlock_register@
    @Column(name="unlock_register"   , nullable=true , unique=false)
    private Boolean unlockRegister; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @change_customer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @change_customer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-change_customer@
    @Column(name="change_customer"   , nullable=true , unique=false)
    private Boolean changeCustomer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @override_tax-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @override_tax-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-override_tax@
    @Column(name="override_tax"   , nullable=true , unique=false)
    private Boolean overrideTax; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @override_credit_limit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @override_credit_limit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-override_credit_limit@
    @Column(name="override_credit_limit"   , nullable=true , unique=false)
    private Boolean overrideCreditLimit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @override_commission-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @override_commission-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-override_commission@
    @Column(name="override_commission"   , nullable=true , unique=false)
    private Boolean overrideCommission; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @valid_drawer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @valid_drawer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-valid_drawer@
    @Column(name="valid_drawer"   , nullable=true , unique=false)
    private Boolean validDrawer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @allow_open_close-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @allow_open_close-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-allow_open_close@
    @Column(name="allow_open_close"   , nullable=true , unique=false)
    private Boolean allowOpenClose; 
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employees-field-employee_group@
    @OneToMany (targetEntity=com.salesliant.entity.Employee.class, fetch=FetchType.LAZY, mappedBy="employeeGroup", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Employee> employees = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public EmployeeGroup() {
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-module_name@
    public String getModuleName() {
        return moduleName;
    }
	
    public void setModuleName (String moduleName) {
        this.moduleName =  moduleName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_drawer_number@
    public String getCashDrawerNumber() {
        return cashDrawerNumber;
    }
	
    public void setCashDrawerNumber (String cashDrawerNumber) {
        this.cashDrawerNumber =  cashDrawerNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fail_logon_attempts@
    public Integer getFailLogonAttempts() {
        return failLogonAttempts;
    }
	
    public void setFailLogonAttempts (Integer failLogonAttempts) {
        this.failLogonAttempts =  failLogonAttempts;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-floor_limit@
    public java.math.BigDecimal getFloorLimit() {
        return floorLimit;
    }
	
    public void setFloorLimit (java.math.BigDecimal floorLimit) {
        this.floorLimit =  floorLimit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_limit@
    public java.math.BigDecimal getReturnLimit() {
        return returnLimit;
    }
	
    public void setReturnLimit (java.math.BigDecimal returnLimit) {
        this.returnLimit =  returnLimit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sales_rep@
    public Boolean getSalesRep() {
        return salesRep;
    }
	
    public void setSalesRep (Boolean salesRep) {
        this.salesRep =  salesRep;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-open_close_drawer@
    public Boolean getOpenCloseDrawer() {
        return openCloseDrawer;
    }
	
    public void setOpenCloseDrawer (Boolean openCloseDrawer) {
        this.openCloseDrawer =  openCloseDrawer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-override_prices@
    public Boolean getOverridePrices() {
        return overridePrices;
    }
	
    public void setOverridePrices (Boolean overridePrices) {
        this.overridePrices =  overridePrices;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-below_minimum_prices@
    public Boolean getBelowMinimumPrices() {
        return belowMinimumPrices;
    }
	
    public void setBelowMinimumPrices (Boolean belowMinimumPrices) {
        this.belowMinimumPrices =  belowMinimumPrices;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-accept_returns@
    public Boolean getAcceptReturns() {
        return acceptReturns;
    }
	
    public void setAcceptReturns (Boolean acceptReturns) {
        this.acceptReturns =  acceptReturns;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-allow_pay_in_out@
    public Boolean getAllowPayInOut() {
        return allowPayInOut;
    }
	
    public void setAllowPayInOut (Boolean allowPayInOut) {
        this.allowPayInOut =  allowPayInOut;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-allow_no_sale@
    public Boolean getAllowNoSale() {
        return allowNoSale;
    }
	
    public void setAllowNoSale (Boolean allowNoSale) {
        this.allowNoSale =  allowNoSale;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-void_invoice@
    public Boolean getVoidInvoice() {
        return voidInvoice;
    }
	
    public void setVoidInvoice (Boolean voidInvoice) {
        this.voidInvoice =  voidInvoice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-void_order@
    public Boolean getVoidOrder() {
        return voidOrder;
    }
	
    public void setVoidOrder (Boolean voidOrder) {
        this.voidOrder =  voidOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-void_service_order@
    public Boolean getVoidServiceOrder() {
        return voidServiceOrder;
    }
	
    public void setVoidServiceOrder (Boolean voidServiceOrder) {
        this.voidServiceOrder =  voidServiceOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-void_internet_order@
    public Boolean getVoidInternetOrder() {
        return voidInternetOrder;
    }
	
    public void setVoidInternetOrder (Boolean voidInternetOrder) {
        this.voidInternetOrder =  voidInternetOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-void_quote@
    public Boolean getVoidQuote() {
        return voidQuote;
    }
	
    public void setVoidQuote (Boolean voidQuote) {
        this.voidQuote =  voidQuote;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-edit_invoice@
    public Boolean getEditInvoice() {
        return editInvoice;
    }
	
    public void setEditInvoice (Boolean editInvoice) {
        this.editInvoice =  editInvoice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-unlock_register@
    public Boolean getUnlockRegister() {
        return unlockRegister;
    }
	
    public void setUnlockRegister (Boolean unlockRegister) {
        this.unlockRegister =  unlockRegister;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-change_customer@
    public Boolean getChangeCustomer() {
        return changeCustomer;
    }
	
    public void setChangeCustomer (Boolean changeCustomer) {
        this.changeCustomer =  changeCustomer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-override_tax@
    public Boolean getOverrideTax() {
        return overrideTax;
    }
	
    public void setOverrideTax (Boolean overrideTax) {
        this.overrideTax =  overrideTax;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-override_credit_limit@
    public Boolean getOverrideCreditLimit() {
        return overrideCreditLimit;
    }
	
    public void setOverrideCreditLimit (Boolean overrideCreditLimit) {
        this.overrideCreditLimit =  overrideCreditLimit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-override_commission@
    public Boolean getOverrideCommission() {
        return overrideCommission;
    }
	
    public void setOverrideCommission (Boolean overrideCommission) {
        this.overrideCommission =  overrideCommission;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-valid_drawer@
    public Boolean getValidDrawer() {
        return validDrawer;
    }
	
    public void setValidDrawer (Boolean validDrawer) {
        this.validDrawer =  validDrawer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-allow_open_close@
    public Boolean getAllowOpenClose() {
        return allowOpenClose;
    }
	
    public void setAllowOpenClose (Boolean allowOpenClose) {
        this.allowOpenClose =  allowOpenClose;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employees-getter-employee_group@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-employee_group@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (failLogonAttempts==null) failLogonAttempts=__DEFAULT_FAIL_LOGON_ATTEMPTS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-employee_group@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (failLogonAttempts==null) failLogonAttempts=__DEFAULT_FAIL_LOGON_ATTEMPTS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

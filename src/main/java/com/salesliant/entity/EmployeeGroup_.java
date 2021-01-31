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
	* - name      : DomainEntityJPA2Metamodel
	* - file name : DomainEntityJPA2Metamodel.vm
	* - time      : 2021/01/30 AD at 23:59:32 EST
*/
package com.salesliant.entity;

import java.sql.*;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.persistence.metamodel.ListAttribute;

import com.salesliant.entity.Employee;
import com.salesliant.entity.Store;

@StaticMetamodel(EmployeeGroup.class)
public class EmployeeGroup_ {

    public static volatile SingularAttribute<EmployeeGroup, Integer> id;

    public static volatile SingularAttribute<EmployeeGroup, String> code;
    public static volatile SingularAttribute<EmployeeGroup, String> description;
    public static volatile SingularAttribute<EmployeeGroup, String> moduleName;
    public static volatile SingularAttribute<EmployeeGroup, String> cashDrawerNumber;
    public static volatile SingularAttribute<EmployeeGroup, Integer> failLogonAttempts;
    public static volatile SingularAttribute<EmployeeGroup, java.math.BigDecimal> floorLimit;
    public static volatile SingularAttribute<EmployeeGroup, java.math.BigDecimal> returnLimit;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> salesRep;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> openCloseDrawer;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> overridePrices;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> belowMinimumPrices;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> acceptReturns;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> allowPayInOut;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> allowNoSale;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> voidInvoice;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> voidOrder;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> voidServiceOrder;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> voidInternetOrder;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> voidQuote;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> editInvoice;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> unlockRegister;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> changeCustomer;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> overrideTax;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> overrideCreditLimit;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> overrideCommission;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> validDrawer;
    public static volatile SingularAttribute<EmployeeGroup, Boolean> allowOpenClose;
    public static volatile SingularAttribute<EmployeeGroup, Integer> version;


    public static volatile SingularAttribute<EmployeeGroup, Store> store;
    public static volatile SingularAttribute<EmployeeGroup, Integer> store_;

    public static volatile ListAttribute<EmployeeGroup, Employee> employees;


}

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

import com.salesliant.entity.ReturnOrderEntry;
import com.salesliant.entity.Employee;
import com.salesliant.entity.ShippingService;
import com.salesliant.entity.Store;
import com.salesliant.entity.Vendor;

@StaticMetamodel(ReturnOrder.class)
public class ReturnOrder_ {

    public static volatile SingularAttribute<ReturnOrder, Integer> id;

    public static volatile SingularAttribute<ReturnOrder, Integer> returnToVendorNumber;
    public static volatile SingularAttribute<ReturnOrder, String> rmaNumber;
    public static volatile SingularAttribute<ReturnOrder, java.math.BigDecimal> total;
    public static volatile SingularAttribute<ReturnOrder, java.math.BigDecimal> shippingAmount;
    public static volatile SingularAttribute<ReturnOrder, String> shipToCompany;
    public static volatile SingularAttribute<ReturnOrder, String> shipToDepartment;
    public static volatile SingularAttribute<ReturnOrder, String> shipToAddress1;
    public static volatile SingularAttribute<ReturnOrder, String> shipToAddress2;
    public static volatile SingularAttribute<ReturnOrder, String> shipToCity;
    public static volatile SingularAttribute<ReturnOrder, String> shipToState;
    public static volatile SingularAttribute<ReturnOrder, String> shipToPostCode;
    public static volatile SingularAttribute<ReturnOrder, String> shipToCountry;
    public static volatile SingularAttribute<ReturnOrder, String> note;
    public static volatile SingularAttribute<ReturnOrder, Date> dateCreated;
    public static volatile SingularAttribute<ReturnOrder, Timestamp> dateExpected;
    public static volatile SingularAttribute<ReturnOrder, Timestamp> dateReturned;
    public static volatile SingularAttribute<ReturnOrder, Integer> status;
    public static volatile SingularAttribute<ReturnOrder, Integer> version;


    public static volatile SingularAttribute<ReturnOrder, Employee> employee;
    public static volatile SingularAttribute<ReturnOrder, Integer> employee_;

    public static volatile SingularAttribute<ReturnOrder, ShippingService> shippingService;
    public static volatile SingularAttribute<ReturnOrder, Integer> shippingService_;

    public static volatile SingularAttribute<ReturnOrder, Store> store;
    public static volatile SingularAttribute<ReturnOrder, Integer> store_;

    public static volatile SingularAttribute<ReturnOrder, Vendor> vendor;
    public static volatile SingularAttribute<ReturnOrder, Integer> vendor_;

    public static volatile ListAttribute<ReturnOrder, ReturnOrderEntry> returnOrderEntries;


}

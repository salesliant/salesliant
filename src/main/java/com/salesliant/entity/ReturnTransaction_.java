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

import com.salesliant.entity.ReturnEntry;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Item;
import com.salesliant.entity.Store;

@StaticMetamodel(ReturnTransaction.class)
public class ReturnTransaction_ {

    public static volatile SingularAttribute<ReturnTransaction, Integer> id;

    public static volatile SingularAttribute<ReturnTransaction, java.math.BigDecimal> quantity;
    public static volatile SingularAttribute<ReturnTransaction, java.math.BigDecimal> cost;
    public static volatile SingularAttribute<ReturnTransaction, java.math.BigDecimal> price;
    public static volatile SingularAttribute<ReturnTransaction, String> itemLookUpCode;
    public static volatile SingularAttribute<ReturnTransaction, String> itemDescription;
    public static volatile SingularAttribute<ReturnTransaction, String> reasonCode;
    public static volatile SingularAttribute<ReturnTransaction, Integer> rmaNumberToCustomer;
    public static volatile SingularAttribute<ReturnTransaction, String> employeeNameReturned;
    public static volatile SingularAttribute<ReturnTransaction, String> employeeNameProcessed;
    public static volatile SingularAttribute<ReturnTransaction, String> invoiceNumber;
    public static volatile SingularAttribute<ReturnTransaction, String> vendorInvoiceNumber;
    public static volatile SingularAttribute<ReturnTransaction, Integer> status;
    public static volatile SingularAttribute<ReturnTransaction, Boolean> ownTag;
    public static volatile SingularAttribute<ReturnTransaction, Integer> returnFromType;
    public static volatile SingularAttribute<ReturnTransaction, Integer> returnToType;
    public static volatile SingularAttribute<ReturnTransaction, Timestamp> dateReturned;
    public static volatile SingularAttribute<ReturnTransaction, Timestamp> dateProcessed;
    public static volatile SingularAttribute<ReturnTransaction, Integer> version;


    public static volatile SingularAttribute<ReturnTransaction, Customer> customer;
    public static volatile SingularAttribute<ReturnTransaction, Integer> customer_;

    public static volatile SingularAttribute<ReturnTransaction, Employee> employeeProcessed;
    public static volatile SingularAttribute<ReturnTransaction, Integer> employeeProcessed_;

    public static volatile SingularAttribute<ReturnTransaction, Employee> employeeReturned;
    public static volatile SingularAttribute<ReturnTransaction, Integer> employeeReturned_;

    public static volatile SingularAttribute<ReturnTransaction, InvoiceEntry> invoiceEntry;
    public static volatile SingularAttribute<ReturnTransaction, Integer> invoiceEntry_;

    public static volatile SingularAttribute<ReturnTransaction, Item> item;
    public static volatile SingularAttribute<ReturnTransaction, Integer> item_;

    public static volatile SingularAttribute<ReturnTransaction, Store> store;
    public static volatile SingularAttribute<ReturnTransaction, Integer> store_;

    public static volatile ListAttribute<ReturnTransaction, ReturnEntry> returnEntries;


}

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

import com.salesliant.entity.Deposit;
import com.salesliant.entity.GiftCertificateTransaction;
import com.salesliant.entity.InvoiceRecurring;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.Shipping;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Service;
import com.salesliant.entity.Station;
import com.salesliant.entity.Store;
import com.salesliant.entity.TaxZone;

@StaticMetamodel(SalesOrder.class)
public class SalesOrder_ {

    public static volatile SingularAttribute<SalesOrder, Integer> id;

    public static volatile SingularAttribute<SalesOrder, Integer> salesOrderNumber;
    public static volatile SingularAttribute<SalesOrder, String> customerPoNumber;
    public static volatile SingularAttribute<SalesOrder, Integer> type;
    public static volatile SingularAttribute<SalesOrder, java.math.BigDecimal> total;
    public static volatile SingularAttribute<SalesOrder, String> fob;
    public static volatile SingularAttribute<SalesOrder, Date> dateOrdered;
    public static volatile SingularAttribute<SalesOrder, Date> dateDue;
    public static volatile SingularAttribute<SalesOrder, java.math.BigDecimal> commissionAmount;
    public static volatile SingularAttribute<SalesOrder, Boolean> taxExemptFlag;
    public static volatile SingularAttribute<SalesOrder, java.math.BigDecimal> shippingCharge;
    public static volatile SingularAttribute<SalesOrder, java.math.BigDecimal> taxAmount;
    public static volatile SingularAttribute<SalesOrder, java.math.BigDecimal> creditAmount;
    public static volatile SingularAttribute<SalesOrder, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<SalesOrder, String> note;
    public static volatile SingularAttribute<SalesOrder, Integer> version;


    public static volatile SingularAttribute<SalesOrder, Customer> customer;
    public static volatile SingularAttribute<SalesOrder, Integer> customer_;

    public static volatile SingularAttribute<SalesOrder, CustomerBuyer> buyer;
    public static volatile SingularAttribute<SalesOrder, Integer> buyer_;

    public static volatile SingularAttribute<SalesOrder, CustomerShipTo> shipTo;
    public static volatile SingularAttribute<SalesOrder, Integer> shipTo_;

    public static volatile SingularAttribute<SalesOrder, Employee> sales;
    public static volatile SingularAttribute<SalesOrder, Integer> sales_;

    public static volatile SingularAttribute<SalesOrder, Service> service;
    public static volatile SingularAttribute<SalesOrder, Integer> service_;

    public static volatile SingularAttribute<SalesOrder, Station> station;
    public static volatile SingularAttribute<SalesOrder, Integer> station_;

    public static volatile SingularAttribute<SalesOrder, Store> store;
    public static volatile SingularAttribute<SalesOrder, Integer> store_;

    public static volatile SingularAttribute<SalesOrder, TaxZone> taxZone;
    public static volatile SingularAttribute<SalesOrder, Integer> taxZone_;

    public static volatile ListAttribute<SalesOrder, Deposit> deposits;
    public static volatile ListAttribute<SalesOrder, GiftCertificateTransaction> giftCertificateTransactions;
    public static volatile ListAttribute<SalesOrder, InvoiceRecurring> invoiceRecurrings;
    public static volatile ListAttribute<SalesOrder, SalesOrderEntry> salesOrderEntries;
    public static volatile ListAttribute<SalesOrder, Shipping> shippings;


}

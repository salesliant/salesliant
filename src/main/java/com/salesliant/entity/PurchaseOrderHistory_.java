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

import com.salesliant.entity.AccountPayable;
import com.salesliant.entity.AccountPayableHistory;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.Store;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.entity.VendorTerm;

@StaticMetamodel(PurchaseOrderHistory.class)
public class PurchaseOrderHistory_ {

    public static volatile SingularAttribute<PurchaseOrderHistory, Integer> id;

    public static volatile SingularAttribute<PurchaseOrderHistory, String> purchaseOrderNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorShippingServiceCode;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> customerAccountNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorTermCode;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> employeePurchasedName;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> employeeReceivedName;
    public static volatile SingularAttribute<PurchaseOrderHistory, java.math.BigDecimal> total;
    public static volatile SingularAttribute<PurchaseOrderHistory, java.math.BigDecimal> freightInvoicedAmount;
    public static volatile SingularAttribute<PurchaseOrderHistory, java.math.BigDecimal> freightPrePaidAmount;
    public static volatile SingularAttribute<PurchaseOrderHistory, Timestamp> datePurchased;
    public static volatile SingularAttribute<PurchaseOrderHistory, Timestamp> dateCreated;
    public static volatile SingularAttribute<PurchaseOrderHistory, Date> dateReceived;
    public static volatile SingularAttribute<PurchaseOrderHistory, Date> dateInvoiced;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorInvoiceNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> confirmationNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, java.math.BigDecimal> taxOnOrderAmount;
    public static volatile SingularAttribute<PurchaseOrderHistory, java.math.BigDecimal> taxOnFreightAmount;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorContactName;
    public static volatile SingularAttribute<PurchaseOrderHistory, Boolean> postedTag;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorName;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorCode;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorAddress1;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorAddress2;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorCity;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorState;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorPostCode;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorCountry;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorPhoneNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> vendorFaxNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToAddress;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToName;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToContact;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToAddress1;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToAddress2;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToCity;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToState;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToPostCode;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToCountry;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToPhoneNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> shipToFaxNumber;
    public static volatile SingularAttribute<PurchaseOrderHistory, String> note;
    public static volatile SingularAttribute<PurchaseOrderHistory, Integer> version;


    public static volatile SingularAttribute<PurchaseOrderHistory, Store> store;
    public static volatile SingularAttribute<PurchaseOrderHistory, Integer> store_;

    public static volatile SingularAttribute<PurchaseOrderHistory, Vendor> vendor;
    public static volatile SingularAttribute<PurchaseOrderHistory, Integer> vendor_;

    public static volatile SingularAttribute<PurchaseOrderHistory, VendorShippingService> vendorShippingService;
    public static volatile SingularAttribute<PurchaseOrderHistory, Integer> vendorShippingService_;

    public static volatile SingularAttribute<PurchaseOrderHistory, VendorTerm> vendorTerm;
    public static volatile SingularAttribute<PurchaseOrderHistory, Integer> vendorTerm_;

    public static volatile ListAttribute<PurchaseOrderHistory, AccountPayable> accountPayables;
    public static volatile ListAttribute<PurchaseOrderHistory, AccountPayableHistory> accountPayableHistories;
    public static volatile ListAttribute<PurchaseOrderHistory, ItemLog> itemLogs;
    public static volatile ListAttribute<PurchaseOrderHistory, PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries;


}

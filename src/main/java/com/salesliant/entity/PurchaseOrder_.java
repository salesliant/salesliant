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

import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Store;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorContact;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.entity.VendorTerm;

@StaticMetamodel(PurchaseOrder.class)
public class PurchaseOrder_ {

    public static volatile SingularAttribute<PurchaseOrder, Integer> id;

    public static volatile SingularAttribute<PurchaseOrder, Integer> purchaseOrderNumber;
    public static volatile SingularAttribute<PurchaseOrder, Integer> subStore;
    public static volatile SingularAttribute<PurchaseOrder, Integer> subPurchaseOrderNumber;
    public static volatile SingularAttribute<PurchaseOrder, Integer> purchaseOrderType;
    public static volatile SingularAttribute<PurchaseOrder, Integer> status;
    public static volatile SingularAttribute<PurchaseOrder, String> employeePurchasedName;
    public static volatile SingularAttribute<PurchaseOrder, String> employeeReceivedName;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> total;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> totalReceived;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> freightInvoicedAmount;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> freightPrePaidAmount;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> taxOnOrderAmount;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> taxOnFreightAmount;
    public static volatile SingularAttribute<PurchaseOrder, Timestamp> datePurchased;
    public static volatile SingularAttribute<PurchaseOrder, Timestamp> dateCreated;
    public static volatile SingularAttribute<PurchaseOrder, Date> dateExpected;
    public static volatile SingularAttribute<PurchaseOrder, Date> dateReceived;
    public static volatile SingularAttribute<PurchaseOrder, Date> dateInvoiced;
    public static volatile SingularAttribute<PurchaseOrder, Integer> discountDays;
    public static volatile SingularAttribute<PurchaseOrder, java.math.BigDecimal> discountPercent;
    public static volatile SingularAttribute<PurchaseOrder, Boolean> dueTag;
    public static volatile SingularAttribute<PurchaseOrder, String> vendorInvoiceNumber;
    public static volatile SingularAttribute<PurchaseOrder, String> confirmationNumber;
    public static volatile SingularAttribute<PurchaseOrder, Boolean> postedTag;
    public static volatile SingularAttribute<PurchaseOrder, String> vendorName;
    public static volatile SingularAttribute<PurchaseOrder, String> shipToAddress;
    public static volatile SingularAttribute<PurchaseOrder, String> note;
    public static volatile SingularAttribute<PurchaseOrder, Integer> version;


    public static volatile SingularAttribute<PurchaseOrder, Customer> customer;
    public static volatile SingularAttribute<PurchaseOrder, Integer> customer_;

    public static volatile SingularAttribute<PurchaseOrder, CustomerShipTo> customerShipTo;
    public static volatile SingularAttribute<PurchaseOrder, Integer> customerShipTo_;

    public static volatile SingularAttribute<PurchaseOrder, Store> store;
    public static volatile SingularAttribute<PurchaseOrder, Integer> store_;

    public static volatile SingularAttribute<PurchaseOrder, Vendor> vendor;
    public static volatile SingularAttribute<PurchaseOrder, Integer> vendor_;

    public static volatile SingularAttribute<PurchaseOrder, VendorContact> vendorContact;
    public static volatile SingularAttribute<PurchaseOrder, Integer> vendorContact_;

    public static volatile SingularAttribute<PurchaseOrder, VendorShippingService> vendorShippingService;
    public static volatile SingularAttribute<PurchaseOrder, Integer> vendorShippingService_;

    public static volatile SingularAttribute<PurchaseOrder, VendorTerm> vendorTerm;
    public static volatile SingularAttribute<PurchaseOrder, Integer> vendorTerm_;

    public static volatile ListAttribute<PurchaseOrder, PurchaseOrderEntry> purchaseOrderEntries;


}

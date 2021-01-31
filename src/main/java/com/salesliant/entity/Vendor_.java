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
import com.salesliant.entity.Item;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.ReorderList;
import com.salesliant.entity.ReturnOrder;
import com.salesliant.entity.VendorContact;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorNote;
import com.salesliant.entity.VendorShipTo;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.entity.Country;
import com.salesliant.entity.Store;
import com.salesliant.entity.VendorContact;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.entity.VendorTerm;

@StaticMetamodel(Vendor.class)
public class Vendor_ {

    public static volatile SingularAttribute<Vendor, Integer> id;

    public static volatile SingularAttribute<Vendor, String> company;
    public static volatile SingularAttribute<Vendor, String> vendorCode;
    public static volatile SingularAttribute<Vendor, String> accountNumber;
    public static volatile SingularAttribute<Vendor, String> vendorContactName;
    public static volatile SingularAttribute<Vendor, String> address1;
    public static volatile SingularAttribute<Vendor, String> address2;
    public static volatile SingularAttribute<Vendor, String> postCode;
    public static volatile SingularAttribute<Vendor, String> city;
    public static volatile SingularAttribute<Vendor, String> state;
    public static volatile SingularAttribute<Vendor, String> phoneNumber;
    public static volatile SingularAttribute<Vendor, String> faxNumber;
    public static volatile SingularAttribute<Vendor, String> webAddress;
    public static volatile SingularAttribute<Vendor, String> emailAddress;
    public static volatile SingularAttribute<Vendor, java.math.BigDecimal> creditLimit;
    public static volatile SingularAttribute<Vendor, Boolean> activeTag;
    public static volatile SingularAttribute<Vendor, Integer> returnDays;
    public static volatile SingularAttribute<Vendor, String> taxNumber;
    public static volatile SingularAttribute<Vendor, Integer> discountDays;
    public static volatile SingularAttribute<Vendor, java.math.BigDecimal> discountRate;
    public static volatile SingularAttribute<Vendor, Integer> currentAutoSkuNumber;
    public static volatile SingularAttribute<Vendor, Boolean> useVendorSku;
    public static volatile SingularAttribute<Vendor, Boolean> useAutoSku;
    public static volatile SingularAttribute<Vendor, Integer> currency;
    public static volatile SingularAttribute<Vendor, String> glAccount;
    public static volatile SingularAttribute<Vendor, Date> dateCreated;
    public static volatile SingularAttribute<Vendor, Date> lastUpdated;
    public static volatile SingularAttribute<Vendor, Integer> version;


    public static volatile SingularAttribute<Vendor, Country> country;
    public static volatile SingularAttribute<Vendor, Integer> country_;

    public static volatile SingularAttribute<Vendor, Store> store;
    public static volatile SingularAttribute<Vendor, Integer> store_;

    public static volatile SingularAttribute<Vendor, VendorContact> defaultVendorContact;
    public static volatile SingularAttribute<Vendor, Integer> defaultVendorContact_;

    public static volatile SingularAttribute<Vendor, VendorShippingService> defaultVendorShippingService;
    public static volatile SingularAttribute<Vendor, Integer> defaultVendorShippingService_;

    public static volatile SingularAttribute<Vendor, VendorTerm> vendorTerm;
    public static volatile SingularAttribute<Vendor, Integer> vendorTerm_;

    public static volatile ListAttribute<Vendor, AccountPayable> accountPayables;
    public static volatile ListAttribute<Vendor, Item> items;
    public static volatile ListAttribute<Vendor, PurchaseOrder> purchaseOrders;
    public static volatile ListAttribute<Vendor, PurchaseOrderHistory> purchaseOrderHistories;
    public static volatile ListAttribute<Vendor, ReorderList> reorderLists;
    public static volatile ListAttribute<Vendor, ReturnOrder> returnOrders;
    public static volatile ListAttribute<Vendor, VendorContact> vendorContacts;
    public static volatile ListAttribute<Vendor, VendorItem> vendorItems;
    public static volatile ListAttribute<Vendor, VendorNote> vendorNotes;
    public static volatile ListAttribute<Vendor, VendorShipTo> vendorShipTos;
    public static volatile ListAttribute<Vendor, VendorShippingService> vendorShippingServices;


}

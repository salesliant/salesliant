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

import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Item;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.Store;

@StaticMetamodel(PurchaseOrderHistoryEntry.class)
public class PurchaseOrderHistoryEntry_ {

    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Integer> id;

    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, String> itemLookUpCode;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, String> vendorItemLookUpCode;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, String> itemDescription;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> quantityOrdered;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> quantityInStock;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> quantityReceived;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> quantityTransferred;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> quantityPerUom;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> cost;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> taxRate;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> weight;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, java.math.BigDecimal> freight;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Timestamp> dateReceived;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, String> lineNote;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, String> unitOfMeasure;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, String> categoryName;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Integer> displayOrder;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Integer> version;


    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Item> item;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Integer> item_;

    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, PurchaseOrderHistory> purchaseOrderHistory;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Integer> purchaseOrderHistory_;

    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Store> store;
    public static volatile SingularAttribute<PurchaseOrderHistoryEntry, Integer> store_;

    public static volatile ListAttribute<PurchaseOrderHistoryEntry, SerialNumber> serialNumbers;


}

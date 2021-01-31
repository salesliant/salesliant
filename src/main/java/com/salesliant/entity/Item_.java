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

import com.salesliant.entity.Consignment;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemAttribute;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.ItemLabel;
import com.salesliant.entity.ItemLimit;
import com.salesliant.entity.ItemLocation;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemLot;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Kit;
import com.salesliant.entity.Kit;
import com.salesliant.entity.KitEntry;
import com.salesliant.entity.Promotion;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.ReorderList;
import com.salesliant.entity.ReturnOrderEntry;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Substitute;
import com.salesliant.entity.Substitute;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.entity.TransferOrderHistoryEntry;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.Voucher;
import com.salesliant.entity.Category;
import com.salesliant.entity.Department;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.QuantityDiscount;
import com.salesliant.entity.Item;

@StaticMetamodel(Item.class)
public class Item_ {

    public static volatile SingularAttribute<Item, Integer> id;

    public static volatile SingularAttribute<Item, Integer> label;
    public static volatile SingularAttribute<Item, String> itemLookUpCode;
    public static volatile SingularAttribute<Item, String> description;
    public static volatile SingularAttribute<Item, String> departmentName;
    public static volatile SingularAttribute<Item, String> categoryName;
    public static volatile SingularAttribute<Item, String> barcodeFormat;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> price1;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> price2;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> price3;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> price4;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> price5;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> price6;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> cost;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> lastCost;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> landedCost;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> averageCost;
    public static volatile SingularAttribute<Item, Integer> itemType;
    public static volatile SingularAttribute<Item, Integer> defaultSellQuantity;
    public static volatile SingularAttribute<Item, Integer> defaultSupplyQuantity;
    public static volatile SingularAttribute<Item, Integer> reorderPoint;
    public static volatile SingularAttribute<Item, Integer> restockLevel;
    public static volatile SingularAttribute<Item, String> unitOfMeasure;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> tagAlongQuantity;
    public static volatile SingularAttribute<Item, String> note;
    public static volatile SingularAttribute<Item, Boolean> internetItem;
    public static volatile SingularAttribute<Item, Boolean> foodStampable;
    public static volatile SingularAttribute<Item, Boolean> discountable;
    public static volatile SingularAttribute<Item, Boolean> consignment;
    public static volatile SingularAttribute<Item, Boolean> activeTag;
    public static volatile SingularAttribute<Item, Boolean> fractionTag;
    public static volatile SingularAttribute<Item, Boolean> trackSerialNumumber;
    public static volatile SingularAttribute<Item, Boolean> trackQuantity;
    public static volatile SingularAttribute<Item, Boolean> addToLabelList;
    public static volatile SingularAttribute<Item, Integer> serialNumberCount;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> suggestedRetailedPrice;
    public static volatile SingularAttribute<Item, java.math.BigDecimal> weight;
    public static volatile SingularAttribute<Item, String> mpn;
    public static volatile SingularAttribute<Item, String> vendorItemLookUpCode;
    public static volatile SingularAttribute<Item, String> pictureName;
    public static volatile SingularAttribute<Item, Integer> version;
    public static volatile SingularAttribute<Item, Integer> store;


    public static volatile SingularAttribute<Item, Category> category;
    public static volatile SingularAttribute<Item, Integer> category_;

    public static volatile SingularAttribute<Item, Department> department;
    public static volatile SingularAttribute<Item, Integer> department_;

    public static volatile SingularAttribute<Item, Vendor> primaryVendor;
    public static volatile SingularAttribute<Item, Integer> primaryVendor_;

    public static volatile SingularAttribute<Item, QuantityDiscount> quantityDiscount;
    public static volatile SingularAttribute<Item, Integer> quantityDiscount_;

    public static volatile SingularAttribute<Item, Item> tagAlongItem;
    public static volatile SingularAttribute<Item, Integer> tagAlongItem_;

    public static volatile ListAttribute<Item, Consignment> consignments;
    public static volatile ListAttribute<Item, Item> items;
    public static volatile ListAttribute<Item, ItemAttribute> itemAttributes;
    public static volatile ListAttribute<Item, ItemBom> itemBomBomItems;
    public static volatile ListAttribute<Item, ItemBom> itemBomComponentItems;
    public static volatile ListAttribute<Item, ItemLabel> itemLabels;
    public static volatile ListAttribute<Item, ItemLimit> itemLimits;
    public static volatile ListAttribute<Item, ItemLocation> itemLocations;
    public static volatile ListAttribute<Item, ItemLog> itemLogs;
    public static volatile ListAttribute<Item, ItemLot> itemLots;
    public static volatile ListAttribute<Item, ItemQuantity> itemQuantities;
    public static volatile ListAttribute<Item, Kit> kitComponentItems;
    public static volatile ListAttribute<Item, Kit> kitKitItems;
    public static volatile ListAttribute<Item, KitEntry> kitEntries;
    public static volatile ListAttribute<Item, Promotion> promotions;
    public static volatile ListAttribute<Item, PurchaseOrderEntry> purchaseOrderEntries;
    public static volatile ListAttribute<Item, PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries;
    public static volatile ListAttribute<Item, ReorderList> reorderLists;
    public static volatile ListAttribute<Item, ReturnOrderEntry> returnOrderEntries;
    public static volatile ListAttribute<Item, ReturnTransaction> returnTransactions;
    public static volatile ListAttribute<Item, SalesOrderEntry> salesOrderEntries;
    public static volatile ListAttribute<Item, SerialNumber> serialNumbers;
    public static volatile ListAttribute<Item, Substitute> substituteItems;
    public static volatile ListAttribute<Item, Substitute> substituteSubstituteItems;
    public static volatile ListAttribute<Item, TransferOrderEntry> transferOrderEntries;
    public static volatile ListAttribute<Item, TransferOrderHistoryEntry> transferOrderHistoryEntries;
    public static volatile ListAttribute<Item, VendorItem> vendorItems;
    public static volatile ListAttribute<Item, Voucher> vouchers;


}

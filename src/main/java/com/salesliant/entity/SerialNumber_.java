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

import com.salesliant.entity.Service;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Item;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.Store;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.entity.TransferOrderHistoryEntry;

@StaticMetamodel(SerialNumber.class)
public class SerialNumber_ {

    public static volatile SingularAttribute<SerialNumber, Integer> id;

    public static volatile SingularAttribute<SerialNumber, String> serialNumber;
    public static volatile SingularAttribute<SerialNumber, String> description;
    public static volatile SingularAttribute<SerialNumber, Boolean> sold;
    public static volatile SingularAttribute<SerialNumber, Date> dateSold;
    public static volatile SingularAttribute<SerialNumber, Integer> invoiceNumber;
    public static volatile SingularAttribute<SerialNumber, Date> warrantyExpireLabor;
    public static volatile SingularAttribute<SerialNumber, Date> warrantyExpirePart;
    public static volatile SingularAttribute<SerialNumber, Integer> version;


    public static volatile SingularAttribute<SerialNumber, InvoiceEntry> invoiceEntry;
    public static volatile SingularAttribute<SerialNumber, Integer> invoiceEntry_;

    public static volatile SingularAttribute<SerialNumber, Item> item;
    public static volatile SingularAttribute<SerialNumber, Integer> item_;

    public static volatile SingularAttribute<SerialNumber, PurchaseOrderEntry> purchaseOrderEntry;
    public static volatile SingularAttribute<SerialNumber, Integer> purchaseOrderEntry_;

    public static volatile SingularAttribute<SerialNumber, PurchaseOrderHistoryEntry> purchaseOrderHistoryEntry;
    public static volatile SingularAttribute<SerialNumber, Integer> purchaseOrderHistoryEntry_;

    public static volatile SingularAttribute<SerialNumber, SalesOrderEntry> salesOrderEntry;
    public static volatile SingularAttribute<SerialNumber, Integer> salesOrderEntry_;

    public static volatile SingularAttribute<SerialNumber, Store> store;
    public static volatile SingularAttribute<SerialNumber, Integer> store_;

    public static volatile SingularAttribute<SerialNumber, TransferOrderEntry> transferOrderEntry;
    public static volatile SingularAttribute<SerialNumber, Integer> transferOrderEntry_;

    public static volatile SingularAttribute<SerialNumber, TransferOrderHistoryEntry> transferOrderHistoryEntry;
    public static volatile SingularAttribute<SerialNumber, Integer> transferOrderHistoryEntry_;

    public static volatile ListAttribute<SerialNumber, Service> services;


}

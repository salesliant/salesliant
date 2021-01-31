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

import com.salesliant.entity.ItemLog;
import com.salesliant.entity.TransferOrderHistoryEntry;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.Store;

@StaticMetamodel(TransferOrderHistory.class)
public class TransferOrderHistory_ {

    public static volatile SingularAttribute<TransferOrderHistory, Integer> id;

    public static volatile SingularAttribute<TransferOrderHistory, String> transferOrderNumber;
    public static volatile SingularAttribute<TransferOrderHistory, String> sendStoreName;
    public static volatile SingularAttribute<TransferOrderHistory, String> receiveStoreName;
    public static volatile SingularAttribute<TransferOrderHistory, Timestamp> dateCreated;
    public static volatile SingularAttribute<TransferOrderHistory, Timestamp> dateSent;
    public static volatile SingularAttribute<TransferOrderHistory, Date> dateExpected;
    public static volatile SingularAttribute<TransferOrderHistory, Timestamp> dateReceived;
    public static volatile SingularAttribute<TransferOrderHistory, java.math.BigDecimal> total;
    public static volatile SingularAttribute<TransferOrderHistory, java.math.BigDecimal> freightAmount;
    public static volatile SingularAttribute<TransferOrderHistory, String> employeeSendName;
    public static volatile SingularAttribute<TransferOrderHistory, String> employeeReceiveName;
    public static volatile SingularAttribute<TransferOrderHistory, String> note;
    public static volatile SingularAttribute<TransferOrderHistory, Integer> version;


    public static volatile SingularAttribute<TransferOrderHistory, Store> receiveStore;
    public static volatile SingularAttribute<TransferOrderHistory, Integer> receiveStore_;

    public static volatile SingularAttribute<TransferOrderHistory, Store> sendStore;
    public static volatile SingularAttribute<TransferOrderHistory, Integer> sendStore_;

    public static volatile SingularAttribute<TransferOrderHistory, ShippingCarrier> shippingCarrier;
    public static volatile SingularAttribute<TransferOrderHistory, Integer> shippingCarrier_;

    public static volatile SingularAttribute<TransferOrderHistory, Store> store;
    public static volatile SingularAttribute<TransferOrderHistory, Integer> store_;

    public static volatile ListAttribute<TransferOrderHistory, ItemLog> itemLogs;
    public static volatile ListAttribute<TransferOrderHistory, TransferOrderHistoryEntry> transferOrderHistoryEntries;


}

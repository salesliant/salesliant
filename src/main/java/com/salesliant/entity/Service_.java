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

import com.salesliant.entity.Invoice;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.Appointment;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Store;

@StaticMetamodel(Service.class)
public class Service_ {

    public static volatile SingularAttribute<Service, Integer> id;

    public static volatile SingularAttribute<Service, String> note;
    public static volatile SingularAttribute<Service, Integer> version;


    public static volatile SingularAttribute<Service, Appointment> appointment;
    public static volatile SingularAttribute<Service, Integer> appointment_;

    public static volatile SingularAttribute<Service, SerialNumber> serialNumber;
    public static volatile SingularAttribute<Service, Integer> serialNumber_;

    public static volatile SingularAttribute<Service, Store> store;
    public static volatile SingularAttribute<Service, Integer> store_;

    public static volatile ListAttribute<Service, Invoice> invoices;
    public static volatile ListAttribute<Service, SalesOrder> salesOrders;
    public static volatile ListAttribute<Service, ServiceEntry> serviceEntries;


}

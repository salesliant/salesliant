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

import com.salesliant.entity.Appointment;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Commission;
import com.salesliant.entity.Commission;
import com.salesliant.entity.Consignment;
import com.salesliant.entity.Customer;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.PaymentBatch;
import com.salesliant.entity.ReturnEntry;
import com.salesliant.entity.ReturnOrder;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.TimeCard;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.entity.Country;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.MailGroup;
import com.salesliant.entity.Store;

@StaticMetamodel(Employee.class)
public class Employee_ {

    public static volatile SingularAttribute<Employee, Integer> id;

    public static volatile SingularAttribute<Employee, String> login;
    public static volatile SingularAttribute<Employee, Boolean> activeTag;
    public static volatile SingularAttribute<Employee, String> password;
    public static volatile SingularAttribute<Employee, String> ssn;
    public static volatile SingularAttribute<Employee, String> nameOnSalesOrder;
    public static volatile SingularAttribute<Employee, Date> dateCreated;
    public static volatile SingularAttribute<Employee, String> firstName;
    public static volatile SingularAttribute<Employee, String> lastName;
    public static volatile SingularAttribute<Employee, String> title;
    public static volatile SingularAttribute<Employee, String> phoneNumber;
    public static volatile SingularAttribute<Employee, String> cellPhoneNumber;
    public static volatile SingularAttribute<Employee, String> emailAddress;
    public static volatile SingularAttribute<Employee, String> address1;
    public static volatile SingularAttribute<Employee, String> address2;
    public static volatile SingularAttribute<Employee, String> city;
    public static volatile SingularAttribute<Employee, String> state;
    public static volatile SingularAttribute<Employee, String> postCode;
    public static volatile SingularAttribute<Employee, String> pictureName;
    public static volatile SingularAttribute<Employee, String> note;
    public static volatile SingularAttribute<Employee, Integer> version;


    public static volatile SingularAttribute<Employee, Country> country;
    public static volatile SingularAttribute<Employee, Integer> country_;

    public static volatile SingularAttribute<Employee, EmployeeGroup> employeeGroup;
    public static volatile SingularAttribute<Employee, Integer> employeeGroup_;

    public static volatile SingularAttribute<Employee, MailGroup> mailGroup;
    public static volatile SingularAttribute<Employee, Integer> mailGroup_;

    public static volatile SingularAttribute<Employee, Store> store;
    public static volatile SingularAttribute<Employee, Integer> store_;

    public static volatile ListAttribute<Employee, Appointment> appointments;
    public static volatile ListAttribute<Employee, Batch> batchs;
    public static volatile ListAttribute<Employee, Commission> commissionEmployees;
    public static volatile ListAttribute<Employee, Commission> commissionSaleses;
    public static volatile ListAttribute<Employee, Consignment> consignments;
    public static volatile ListAttribute<Employee, Customer> customers;
    public static volatile ListAttribute<Employee, DropPayout> dropPayouts;
    public static volatile ListAttribute<Employee, ItemLog> itemLogs;
    public static volatile ListAttribute<Employee, PaymentBatch> paymentBatchs;
    public static volatile ListAttribute<Employee, ReturnEntry> returnEntries;
    public static volatile ListAttribute<Employee, ReturnOrder> returnOrders;
    public static volatile ListAttribute<Employee, ReturnTransaction> returnTransactionEmployeeProcesseds;
    public static volatile ListAttribute<Employee, ReturnTransaction> returnTransactionEmployeeReturneds;
    public static volatile ListAttribute<Employee, SalesOrder> salesOrders;
    public static volatile ListAttribute<Employee, ServiceEntry> serviceEntries;
    public static volatile ListAttribute<Employee, TimeCard> timeCards;
    public static volatile ListAttribute<Employee, VoidedTransaction> voidedTransactions;


}

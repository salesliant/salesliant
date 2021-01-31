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

import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Appointment;
import com.salesliant.entity.Cheque;
import com.salesliant.entity.Consignment;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerNote;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.GiftCertificate;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.entity.Country;
import com.salesliant.entity.CustomerGroup;
import com.salesliant.entity.CustomerTerm;
import com.salesliant.entity.Employee;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.Store;
import com.salesliant.entity.TaxZone;

@StaticMetamodel(Customer.class)
public class Customer_ {

    public static volatile SingularAttribute<Customer, Integer> id;

    public static volatile SingularAttribute<Customer, String> accountNumber;
    public static volatile SingularAttribute<Customer, String> customerType;
    public static volatile SingularAttribute<Customer, Boolean> activeTag;
    public static volatile SingularAttribute<Customer, String> firstName;
    public static volatile SingularAttribute<Customer, String> lastName;
    public static volatile SingularAttribute<Customer, String> company;
    public static volatile SingularAttribute<Customer, String> address1;
    public static volatile SingularAttribute<Customer, String> address2;
    public static volatile SingularAttribute<Customer, String> department;
    public static volatile SingularAttribute<Customer, String> postCode;
    public static volatile SingularAttribute<Customer, String> city;
    public static volatile SingularAttribute<Customer, String> state;
    public static volatile SingularAttribute<Customer, String> phoneNumber;
    public static volatile SingularAttribute<Customer, String> faxNumber;
    public static volatile SingularAttribute<Customer, String> cellPhoneNumber;
    public static volatile SingularAttribute<Customer, String> emailAddress;
    public static volatile SingularAttribute<Customer, java.math.BigDecimal> creditLimit;
    public static volatile SingularAttribute<Customer, Date> dateCreated;
    public static volatile SingularAttribute<Customer, Date> lastVisit;
    public static volatile SingularAttribute<Customer, Boolean> globalCustomer;
    public static volatile SingularAttribute<Customer, Boolean> addToEmailListTag;
    public static volatile SingularAttribute<Customer, Boolean> assessFinanceCharges;
    public static volatile SingularAttribute<Customer, Boolean> allowPartialShipFlag;
    public static volatile SingularAttribute<Customer, Boolean> layawayCustomer;
    public static volatile SingularAttribute<Customer, Boolean> limitPurchase;
    public static volatile SingularAttribute<Customer, Boolean> taxExempt;
    public static volatile SingularAttribute<Customer, String> taxNumber;
    public static volatile SingularAttribute<Customer, Integer> version;


    public static volatile SingularAttribute<Customer, Country> country;
    public static volatile SingularAttribute<Customer, Integer> country_;

    public static volatile SingularAttribute<Customer, CustomerGroup> customerGroup;
    public static volatile SingularAttribute<Customer, Integer> customerGroup_;

    public static volatile SingularAttribute<Customer, CustomerTerm> customerTerm;
    public static volatile SingularAttribute<Customer, Integer> customerTerm_;

    public static volatile SingularAttribute<Customer, Employee> sales;
    public static volatile SingularAttribute<Customer, Integer> sales_;

    public static volatile SingularAttribute<Customer, ItemPriceLevel> priceLevel;
    public static volatile SingularAttribute<Customer, Integer> priceLevel_;

    public static volatile SingularAttribute<Customer, Store> store;
    public static volatile SingularAttribute<Customer, Integer> store_;

    public static volatile SingularAttribute<Customer, TaxZone> taxZone;
    public static volatile SingularAttribute<Customer, Integer> taxZone_;

    public static volatile ListAttribute<Customer, AccountReceivable> accountReceivables;
    public static volatile ListAttribute<Customer, Appointment> appointments;
    public static volatile ListAttribute<Customer, Cheque> cheques;
    public static volatile ListAttribute<Customer, Consignment> consignments;
    public static volatile ListAttribute<Customer, CustomerBuyer> customerBuyers;
    public static volatile ListAttribute<Customer, CustomerNote> customerNotes;
    public static volatile ListAttribute<Customer, CustomerShipTo> customerShipTos;
    public static volatile ListAttribute<Customer, Deposit> deposits;
    public static volatile ListAttribute<Customer, GiftCertificate> giftCertificates;
    public static volatile ListAttribute<Customer, PurchaseOrder> purchaseOrders;
    public static volatile ListAttribute<Customer, ReturnTransaction> returnTransactions;
    public static volatile ListAttribute<Customer, SalesOrder> salesOrders;
    public static volatile ListAttribute<Customer, VoidedTransaction> voidedTransactions;


}

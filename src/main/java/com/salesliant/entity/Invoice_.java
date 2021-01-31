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
import com.salesliant.entity.Commission;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.GiftCertificate;
import com.salesliant.entity.GiftCertificateTransaction;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.Payment;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Service;
import com.salesliant.entity.Store;

@StaticMetamodel(Invoice.class)
public class Invoice_ {

    public static volatile SingularAttribute<Invoice, Integer> id;

    public static volatile SingularAttribute<Invoice, Integer> invoiceNumber;
    public static volatile SingularAttribute<Invoice, Integer> stationNumber;
    public static volatile SingularAttribute<Invoice, String> customerPoNumber;
    public static volatile SingularAttribute<Invoice, String> customerAccountNumber;
    public static volatile SingularAttribute<Invoice, Integer> orderNumber;
    public static volatile SingularAttribute<Invoice, String> customerTermCode;
    public static volatile SingularAttribute<Invoice, String> taxZoneName;
    public static volatile SingularAttribute<Invoice, Integer> orderType;
    public static volatile SingularAttribute<Invoice, String> invoiceType;
    public static volatile SingularAttribute<Invoice, String> salesName;
    public static volatile SingularAttribute<Invoice, String> cashierName;
    public static volatile SingularAttribute<Invoice, String> customerName;
    public static volatile SingularAttribute<Invoice, String> phoneNumber;
    public static volatile SingularAttribute<Invoice, String> shipToAddress;
    public static volatile SingularAttribute<Invoice, String> fob;
    public static volatile SingularAttribute<Invoice, String> billToCompany;
    public static volatile SingularAttribute<Invoice, String> billToDepartment;
    public static volatile SingularAttribute<Invoice, String> billToAddress1;
    public static volatile SingularAttribute<Invoice, String> billToAddress2;
    public static volatile SingularAttribute<Invoice, String> billToCity;
    public static volatile SingularAttribute<Invoice, String> billToState;
    public static volatile SingularAttribute<Invoice, String> billToPostCode;
    public static volatile SingularAttribute<Invoice, String> billToCountry;
    public static volatile SingularAttribute<Invoice, Timestamp> dateInvoiced;
    public static volatile SingularAttribute<Invoice, Date> dateOrdered;
    public static volatile SingularAttribute<Invoice, Boolean> postedFlag;
    public static volatile SingularAttribute<Invoice, Boolean> taxExemptFlag;
    public static volatile SingularAttribute<Invoice, Boolean> voidedFlag;
    public static volatile SingularAttribute<Invoice, Boolean> webOrderFlag;
    public static volatile SingularAttribute<Invoice, Boolean> commissionPaidFlag;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> subTotal;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> total;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> cost;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> depositAmount;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> creditAmount;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> giftAmount;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> shippingCharge;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> taxAmount;
    public static volatile SingularAttribute<Invoice, java.math.BigDecimal> commissionAmount;
    public static volatile SingularAttribute<Invoice, String> shipVia;
    public static volatile SingularAttribute<Invoice, String> note;
    public static volatile SingularAttribute<Invoice, Integer> version;


    public static volatile SingularAttribute<Invoice, AccountReceivable> ar;
    public static volatile SingularAttribute<Invoice, Integer> ar_;

    public static volatile SingularAttribute<Invoice, Batch> batch;
    public static volatile SingularAttribute<Invoice, Integer> batch_;

    public static volatile SingularAttribute<Invoice, Service> service;
    public static volatile SingularAttribute<Invoice, Integer> service_;

    public static volatile SingularAttribute<Invoice, Store> store;
    public static volatile SingularAttribute<Invoice, Integer> store_;

    public static volatile ListAttribute<Invoice, AccountReceivable> accountReceivables;
    public static volatile ListAttribute<Invoice, Commission> commissions;
    public static volatile ListAttribute<Invoice, Deposit> deposits;
    public static volatile ListAttribute<Invoice, GiftCertificate> giftCertificates;
    public static volatile ListAttribute<Invoice, GiftCertificateTransaction> giftCertificateTransactions;
    public static volatile ListAttribute<Invoice, InvoiceEntry> invoiceEntries;
    public static volatile ListAttribute<Invoice, ItemLog> itemLogs;
    public static volatile ListAttribute<Invoice, Payment> payments;


}

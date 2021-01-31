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
	* - time      : 2019/12/15 AD at 18:23:05 EST
*/
package com.salesliant.entity;

import java.sql.*;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import javax.persistence.metamodel.ListAttribute;

import com.salesliant.entity.AccountReceivableBatch;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Invoice;

@StaticMetamodel(AccountReceivablePayment.class)
public class AccountReceivablePayment_ {

    public static volatile SingularAttribute<AccountReceivablePayment, Integer> id;

    public static volatile SingularAttribute<AccountReceivablePayment, Integer> invoiceNumber;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> accountReceivableNumber;
    public static volatile SingularAttribute<AccountReceivablePayment, String> accountReceivableType;
    public static volatile SingularAttribute<AccountReceivablePayment, String> transaction;
    public static volatile SingularAttribute<AccountReceivablePayment, Date> dateInvoiced;
    public static volatile SingularAttribute<AccountReceivablePayment, Timestamp> datePaidOn;
    public static volatile SingularAttribute<AccountReceivablePayment, Date> dateDue;
    public static volatile SingularAttribute<AccountReceivablePayment, Date> dateDiscount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> totalAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> paymentAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> balanceAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> paidAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> discountTakenAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> creditAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> glCreditAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, java.math.BigDecimal> glDebitAmount;
    public static volatile SingularAttribute<AccountReceivablePayment, String> glAccount;
    public static volatile SingularAttribute<AccountReceivablePayment, String> terms;
    public static volatile SingularAttribute<AccountReceivablePayment, String> employeeName;
    public static volatile SingularAttribute<AccountReceivablePayment, String> note;
    public static volatile SingularAttribute<AccountReceivablePayment, Boolean> collectable;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> location;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> version;


    public static volatile SingularAttribute<AccountReceivablePayment, AccountReceivableBatch> accountReceivableBatch;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> accountReceivableBatch_;

    public static volatile SingularAttribute<AccountReceivablePayment, Batch> batch;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> batch_;

    public static volatile SingularAttribute<AccountReceivablePayment, Customer> customer;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> customer_;

    public static volatile SingularAttribute<AccountReceivablePayment, Employee> employee;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> employee_;

    public static volatile SingularAttribute<AccountReceivablePayment, Invoice> invoice;
    public static volatile SingularAttribute<AccountReceivablePayment, Integer> invoice_;



}

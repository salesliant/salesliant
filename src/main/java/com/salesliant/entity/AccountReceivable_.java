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
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Payment;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Store;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Invoice;

@StaticMetamodel(AccountReceivable.class)
public class AccountReceivable_ {

    public static volatile SingularAttribute<AccountReceivable, Integer> id;

    public static volatile SingularAttribute<AccountReceivable, Integer> invoiceNumber;
    public static volatile SingularAttribute<AccountReceivable, String> transaction;
    public static volatile SingularAttribute<AccountReceivable, String> employeeName;
    public static volatile SingularAttribute<AccountReceivable, Integer> accountReceivableNumber;
    public static volatile SingularAttribute<AccountReceivable, String> accountReceivableType;
    public static volatile SingularAttribute<AccountReceivable, Integer> status;
    public static volatile SingularAttribute<AccountReceivable, Boolean> postedTag;
    public static volatile SingularAttribute<AccountReceivable, Date> dateProcessed;
    public static volatile SingularAttribute<AccountReceivable, Date> dateDue;
    public static volatile SingularAttribute<AccountReceivable, Date> dateInvoiced;
    public static volatile SingularAttribute<AccountReceivable, java.math.BigDecimal> totalAmount;
    public static volatile SingularAttribute<AccountReceivable, java.math.BigDecimal> balanceAmount;
    public static volatile SingularAttribute<AccountReceivable, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<AccountReceivable, java.math.BigDecimal> paidAmount;
    public static volatile SingularAttribute<AccountReceivable, java.math.BigDecimal> glCreditAmount;
    public static volatile SingularAttribute<AccountReceivable, java.math.BigDecimal> glDebitAmount;
    public static volatile SingularAttribute<AccountReceivable, String> glAccount;
    public static volatile SingularAttribute<AccountReceivable, String> terms;
    public static volatile SingularAttribute<AccountReceivable, String> note;
    public static volatile SingularAttribute<AccountReceivable, Boolean> collectable;
    public static volatile SingularAttribute<AccountReceivable, Integer> version;


    public static volatile SingularAttribute<AccountReceivable, AccountReceivable> accountReceivablePayment;
    public static volatile SingularAttribute<AccountReceivable, Integer> accountReceivablePayment_;

    public static volatile SingularAttribute<AccountReceivable, Store> store;
    public static volatile SingularAttribute<AccountReceivable, Integer> store_;

    public static volatile SingularAttribute<AccountReceivable, Batch> batch;
    public static volatile SingularAttribute<AccountReceivable, Integer> batch_;

    public static volatile SingularAttribute<AccountReceivable, Customer> customer;
    public static volatile SingularAttribute<AccountReceivable, Integer> customer_;

    public static volatile SingularAttribute<AccountReceivable, Invoice> invoice;
    public static volatile SingularAttribute<AccountReceivable, Integer> invoice_;

    public static volatile ListAttribute<AccountReceivable, AccountReceivable> accountReceivables;
    public static volatile ListAttribute<AccountReceivable, Invoice> invoices;
    public static volatile ListAttribute<AccountReceivable, Payment> payments;


}

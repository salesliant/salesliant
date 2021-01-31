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

import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.Store;
import com.salesliant.entity.Vendor;

@StaticMetamodel(AccountPayable.class)
public class AccountPayable_ {

    public static volatile SingularAttribute<AccountPayable, Integer> id;

    public static volatile SingularAttribute<AccountPayable, Date> dateDue;
    public static volatile SingularAttribute<AccountPayable, Date> dateInvoiced;
    public static volatile SingularAttribute<AccountPayable, Timestamp> datePosted;
    public static volatile SingularAttribute<AccountPayable, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<AccountPayable, java.math.BigDecimal> totalAmount;
    public static volatile SingularAttribute<AccountPayable, java.math.BigDecimal> paidAmount;
    public static volatile SingularAttribute<AccountPayable, java.math.BigDecimal> discountPercent;
    public static volatile SingularAttribute<AccountPayable, java.math.BigDecimal> glCreditAmount;
    public static volatile SingularAttribute<AccountPayable, java.math.BigDecimal> glDebitAmount;
    public static volatile SingularAttribute<AccountPayable, String> glAccount;
    public static volatile SingularAttribute<AccountPayable, Boolean> postedTag;
    public static volatile SingularAttribute<AccountPayable, Short> discountDay;
    public static volatile SingularAttribute<AccountPayable, Integer> status;
    public static volatile SingularAttribute<AccountPayable, Integer> accountPayableType;
    public static volatile SingularAttribute<AccountPayable, String> vendorInvoiceNumber;
    public static volatile SingularAttribute<AccountPayable, String> purchaseOrderNumber;
    public static volatile SingularAttribute<AccountPayable, Integer> version;


    public static volatile SingularAttribute<AccountPayable, PurchaseOrderHistory> purchaseOrderHistory;
    public static volatile SingularAttribute<AccountPayable, Integer> purchaseOrderHistory_;

    public static volatile SingularAttribute<AccountPayable, Store> store;
    public static volatile SingularAttribute<AccountPayable, Integer> store_;

    public static volatile SingularAttribute<AccountPayable, Vendor> vendor;
    public static volatile SingularAttribute<AccountPayable, Integer> vendor_;



}

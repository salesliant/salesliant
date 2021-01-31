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

import com.salesliant.entity.AccountReceivablePayment;
import com.salesliant.entity.Customer;

@StaticMetamodel(AccountReceivableBatch.class)
public class AccountReceivableBatch_ {

    public static volatile SingularAttribute<AccountReceivableBatch, Integer> id;

    public static volatile SingularAttribute<AccountReceivableBatch, Timestamp> datePaidOn;
    public static volatile SingularAttribute<AccountReceivableBatch, java.math.BigDecimal> totalAmount;
    public static volatile SingularAttribute<AccountReceivableBatch, String> employeeName;
    public static volatile SingularAttribute<AccountReceivableBatch, String> customerName;
    public static volatile SingularAttribute<AccountReceivableBatch, String> checkNumber;
    public static volatile SingularAttribute<AccountReceivableBatch, Integer> location;
    public static volatile SingularAttribute<AccountReceivableBatch, Integer> version;


    public static volatile SingularAttribute<AccountReceivableBatch, Customer> customer;
    public static volatile SingularAttribute<AccountReceivableBatch, Integer> customer_;

    public static volatile ListAttribute<AccountReceivableBatch, AccountReceivablePayment> accountReceivablePayments;


}

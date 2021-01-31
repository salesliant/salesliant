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

import com.salesliant.entity.Deposit;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Store;

@StaticMetamodel(VoidedTransaction.class)
public class VoidedTransaction_ {

    public static volatile SingularAttribute<VoidedTransaction, Integer> id;

    public static volatile SingularAttribute<VoidedTransaction, Integer> transactionType;
    public static volatile SingularAttribute<VoidedTransaction, Date> dateEntered;
    public static volatile SingularAttribute<VoidedTransaction, String> customerAccountNumber;
    public static volatile SingularAttribute<VoidedTransaction, String> customerName;
    public static volatile SingularAttribute<VoidedTransaction, String> transactionNumber;
    public static volatile SingularAttribute<VoidedTransaction, String> employeeName;
    public static volatile SingularAttribute<VoidedTransaction, java.math.BigDecimal> transactionAmount;
    public static volatile SingularAttribute<VoidedTransaction, Integer> version;


    public static volatile SingularAttribute<VoidedTransaction, Customer> customer;
    public static volatile SingularAttribute<VoidedTransaction, Integer> customer_;

    public static volatile SingularAttribute<VoidedTransaction, Employee> employee;
    public static volatile SingularAttribute<VoidedTransaction, Integer> employee_;

    public static volatile SingularAttribute<VoidedTransaction, Store> store;
    public static volatile SingularAttribute<VoidedTransaction, Integer> store_;

    public static volatile ListAttribute<VoidedTransaction, Deposit> deposits;


}

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

import com.salesliant.entity.PaymentType;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.Store;

@StaticMetamodel(InvoiceRecurring.class)
public class InvoiceRecurring_ {

    public static volatile SingularAttribute<InvoiceRecurring, Integer> id;

    public static volatile SingularAttribute<InvoiceRecurring, Integer> durationType;
    public static volatile SingularAttribute<InvoiceRecurring, Date> firstDueDate;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> quantityOfRecurring;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> monthlyType;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> monthlyDate;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> qunatityOfMonths;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> quantityOfDays;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> quantityOfCustomDays;
    public static volatile SingularAttribute<InvoiceRecurring, String> creditCardNumber;
    public static volatile SingularAttribute<InvoiceRecurring, String> accountHolder;
    public static volatile SingularAttribute<InvoiceRecurring, String> creditCardExpiration;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> version;


    public static volatile SingularAttribute<InvoiceRecurring, PaymentType> paymentType;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> paymentType_;

    public static volatile SingularAttribute<InvoiceRecurring, SalesOrder> originalInvoice;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> originalInvoice_;

    public static volatile SingularAttribute<InvoiceRecurring, Store> store;
    public static volatile SingularAttribute<InvoiceRecurring, Integer> store_;



}

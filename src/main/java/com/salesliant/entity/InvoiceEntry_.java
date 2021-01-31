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

import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.Store;

@StaticMetamodel(InvoiceEntry.class)
public class InvoiceEntry_ {

    public static volatile SingularAttribute<InvoiceEntry, Integer> id;

    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> quantity;
    public static volatile SingularAttribute<InvoiceEntry, String> itemLookUpCode;
    public static volatile SingularAttribute<InvoiceEntry, String> categoryName;
    public static volatile SingularAttribute<InvoiceEntry, String> taxClassName;
    public static volatile SingularAttribute<InvoiceEntry, String> salesName;
    public static volatile SingularAttribute<InvoiceEntry, String> status;
    public static volatile SingularAttribute<InvoiceEntry, String> itemDescription;
    public static volatile SingularAttribute<InvoiceEntry, Boolean> taxable;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> price;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> taxAmount;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> cost;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> commissionAmount;
    public static volatile SingularAttribute<InvoiceEntry, Integer> displayOrder;
    public static volatile SingularAttribute<InvoiceEntry, String> lineNote;
    public static volatile SingularAttribute<InvoiceEntry, String> note;
    public static volatile SingularAttribute<InvoiceEntry, Boolean> componentFlag;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> weight;
    public static volatile SingularAttribute<InvoiceEntry, Boolean> shippedFlag;
    public static volatile SingularAttribute<InvoiceEntry, Boolean> commissionPaidFlag;
    public static volatile SingularAttribute<InvoiceEntry, Boolean> dropShippedFlag;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> couponAmount;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> couponSavingAmount;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> componentQuantity;
    public static volatile SingularAttribute<InvoiceEntry, String> returnCode;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> quantityReturn;
    public static volatile SingularAttribute<InvoiceEntry, java.math.BigDecimal> quantityBackorder;
    public static volatile SingularAttribute<InvoiceEntry, Integer> version;


    public static volatile SingularAttribute<InvoiceEntry, Invoice> invoice;
    public static volatile SingularAttribute<InvoiceEntry, Integer> invoice_;

    public static volatile SingularAttribute<InvoiceEntry, ShippingCarrier> shippingCarrier;
    public static volatile SingularAttribute<InvoiceEntry, Integer> shippingCarrier_;

    public static volatile SingularAttribute<InvoiceEntry, Store> store;
    public static volatile SingularAttribute<InvoiceEntry, Integer> store_;

    public static volatile ListAttribute<InvoiceEntry, ReturnTransaction> returnTransactions;
    public static volatile ListAttribute<InvoiceEntry, SerialNumber> serialNumbers;


}

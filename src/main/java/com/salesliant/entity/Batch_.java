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
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Payment;
import com.salesliant.entity.Station;
import com.salesliant.entity.Employee;
import com.salesliant.entity.PaymentBatch;
import com.salesliant.entity.Station;
import com.salesliant.entity.Store;

@StaticMetamodel(Batch.class)
public class Batch_ {

    public static volatile SingularAttribute<Batch, Integer> id;

    public static volatile SingularAttribute<Batch, Integer> batchNumber;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> depositMade;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> depositRedeemed;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> creditMade;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> creditRedeemed;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> openingTotal;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> closingTotal;
    public static volatile SingularAttribute<Batch, Timestamp> dateOpened;
    public static volatile SingularAttribute<Batch, Timestamp> dateClosed;
    public static volatile SingularAttribute<Batch, Integer> status;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> totalTendered;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> totalChange;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> salesAmount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> returnAmount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> tax;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> commissionTotal;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> paidOut;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> dropPayoutAmount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> uncollectableDebitAmount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> uncollectableCreditAmount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> paidOnAccount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> paidToAccount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> layawayPa;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> layawayClosed;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> discountAmount;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> costOfGoods;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> shipping;
    public static volatile SingularAttribute<Batch, java.math.BigDecimal> roundingError;
    public static volatile SingularAttribute<Batch, Integer> abortedTransCount;
    public static volatile SingularAttribute<Batch, Integer> customerCount;
    public static volatile SingularAttribute<Batch, Integer> noSalesCount;
    public static volatile SingularAttribute<Batch, Integer> version;


    public static volatile SingularAttribute<Batch, Employee> employee;
    public static volatile SingularAttribute<Batch, Integer> employee_;

    public static volatile SingularAttribute<Batch, PaymentBatch> paymentBatch;
    public static volatile SingularAttribute<Batch, Integer> paymentBatch_;

    public static volatile SingularAttribute<Batch, Station> station;
    public static volatile SingularAttribute<Batch, Integer> station_;

    public static volatile SingularAttribute<Batch, Store> store;
    public static volatile SingularAttribute<Batch, Integer> store_;

    public static volatile ListAttribute<Batch, AccountReceivable> accountReceivables;
    public static volatile ListAttribute<Batch, Deposit> depositCloseBatchs;
    public static volatile ListAttribute<Batch, Deposit> depositOpenBatchs;
    public static volatile ListAttribute<Batch, DropPayout> dropPayouts;
    public static volatile ListAttribute<Batch, Invoice> invoices;
    public static volatile ListAttribute<Batch, Payment> payments;
    public static volatile ListAttribute<Batch, Station> stations;


}

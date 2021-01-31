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

import com.salesliant.entity.Signature;
import com.salesliant.entity.VoucherEntry;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.MerchantAuthorization;
import com.salesliant.entity.PaymentType;
import com.salesliant.entity.Store;

@StaticMetamodel(Payment.class)
public class Payment_ {

    public static volatile SingularAttribute<Payment, Integer> id;

    public static volatile SingularAttribute<Payment, java.math.BigDecimal> tenderedAmount;
    public static volatile SingularAttribute<Payment, java.math.BigDecimal> changeAmount;
    public static volatile SingularAttribute<Payment, String> accountName;
    public static volatile SingularAttribute<Payment, String> creditCardNumber;
    public static volatile SingularAttribute<Payment, String> creditCardExpiration;
    public static volatile SingularAttribute<Payment, String> creditCardApprovalCode;
    public static volatile SingularAttribute<Payment, String> license;
    public static volatile SingularAttribute<Payment, String> state;
    public static volatile SingularAttribute<Payment, Date> birthDate;
    public static volatile SingularAttribute<Payment, String> checkNumber;
    public static volatile SingularAttribute<Payment, String> bankNumber;
    public static volatile SingularAttribute<Payment, String> transitNumber;
    public static volatile SingularAttribute<Payment, String> couponCode;
    public static volatile SingularAttribute<Payment, String> giftCertificateNumber;
    public static volatile SingularAttribute<Payment, Integer> version;


    public static volatile SingularAttribute<Payment, AccountReceivable> accountReceivable;
    public static volatile SingularAttribute<Payment, Integer> accountReceivable_;

    public static volatile SingularAttribute<Payment, Batch> batch;
    public static volatile SingularAttribute<Payment, Integer> batch_;

    public static volatile SingularAttribute<Payment, Deposit> deposit;
    public static volatile SingularAttribute<Payment, Integer> deposit_;

    public static volatile SingularAttribute<Payment, DropPayout> dropPayout;
    public static volatile SingularAttribute<Payment, Integer> dropPayout_;

    public static volatile SingularAttribute<Payment, Invoice> invoice;
    public static volatile SingularAttribute<Payment, Integer> invoice_;

    public static volatile SingularAttribute<Payment, MerchantAuthorization> merchantAuthorization;
    public static volatile SingularAttribute<Payment, Integer> merchantAuthorization_;

    public static volatile SingularAttribute<Payment, PaymentType> paymentType;
    public static volatile SingularAttribute<Payment, Integer> paymentType_;

    public static volatile SingularAttribute<Payment, Store> store;
    public static volatile SingularAttribute<Payment, Integer> store_;

    public static volatile ListAttribute<Payment, Signature> signatures;
    public static volatile ListAttribute<Payment, VoucherEntry> voucherEntries;


}

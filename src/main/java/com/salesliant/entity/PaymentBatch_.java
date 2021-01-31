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

import com.salesliant.entity.Batch;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Station;
import com.salesliant.entity.Store;

@StaticMetamodel(PaymentBatch.class)
public class PaymentBatch_ {

    public static volatile SingularAttribute<PaymentBatch, Integer> id;

    public static volatile SingularAttribute<PaymentBatch, Integer> behaviorType;
    public static volatile SingularAttribute<PaymentBatch, String> employeeName;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> cashTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> cashCounted;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> cashLeft;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> checkTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> checkCounted;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> couponTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> couponCounted;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> giftCertificateTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> giftCertificateCounted;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> onAccountTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> onAccountCounted;
    public static volatile SingularAttribute<PaymentBatch, String> note;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> giftCardTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> giftCardCounted;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> creditCardTendered;
    public static volatile SingularAttribute<PaymentBatch, java.math.BigDecimal> creditCardCounted;
    public static volatile SingularAttribute<PaymentBatch, Integer> version;


    public static volatile SingularAttribute<PaymentBatch, Employee> employee;
    public static volatile SingularAttribute<PaymentBatch, Integer> employee_;

    public static volatile SingularAttribute<PaymentBatch, Station> station;
    public static volatile SingularAttribute<PaymentBatch, Integer> station_;

    public static volatile SingularAttribute<PaymentBatch, Store> store;
    public static volatile SingularAttribute<PaymentBatch, Integer> store_;

    public static volatile ListAttribute<PaymentBatch, Batch> batchs;


}

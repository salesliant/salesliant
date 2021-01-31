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

import com.salesliant.entity.Payment;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.Store;
import com.salesliant.entity.Voucher;

@StaticMetamodel(VoucherEntry.class)
public class VoucherEntry_ {

    public static volatile SingularAttribute<VoucherEntry, Integer> id;

    public static volatile SingularAttribute<VoucherEntry, Date> dateProcessed;
    public static volatile SingularAttribute<VoucherEntry, java.math.BigDecimal> amount;
    public static volatile SingularAttribute<VoucherEntry, Integer> version;


    public static volatile SingularAttribute<VoucherEntry, Payment> paymentEntry;
    public static volatile SingularAttribute<VoucherEntry, Integer> paymentEntry_;

    public static volatile SingularAttribute<VoucherEntry, SalesOrderEntry> invoiceEntry;
    public static volatile SingularAttribute<VoucherEntry, Integer> invoiceEntry_;

    public static volatile SingularAttribute<VoucherEntry, Store> store;
    public static volatile SingularAttribute<VoucherEntry, Integer> store_;

    public static volatile SingularAttribute<VoucherEntry, Voucher> voucher;
    public static volatile SingularAttribute<VoucherEntry, Integer> voucher_;



}

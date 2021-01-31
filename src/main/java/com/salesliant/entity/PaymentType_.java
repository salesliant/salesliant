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

import com.salesliant.entity.CustomerGroup;
import com.salesliant.entity.InvoiceRecurring;
import com.salesliant.entity.Payment;
import com.salesliant.entity.Store;

@StaticMetamodel(PaymentType.class)
public class PaymentType_ {

    public static volatile SingularAttribute<PaymentType, Integer> id;

    public static volatile SingularAttribute<PaymentType, String> code;
    public static volatile SingularAttribute<PaymentType, java.math.BigDecimal> amount;
    public static volatile SingularAttribute<PaymentType, Integer> displayOrder;
    public static volatile SingularAttribute<PaymentType, Integer> verificationType;
    public static volatile SingularAttribute<PaymentType, Integer> currency;
    public static volatile SingularAttribute<PaymentType, String> glAccount;
    public static volatile SingularAttribute<PaymentType, java.math.BigDecimal> maximumAmount;
    public static volatile SingularAttribute<PaymentType, Boolean> preventOverTendering;
    public static volatile SingularAttribute<PaymentType, Boolean> doNotPopCashDrawer;
    public static volatile SingularAttribute<PaymentType, Boolean> verifyViaEdc;
    public static volatile SingularAttribute<PaymentType, Boolean> printerValidation;
    public static volatile SingularAttribute<PaymentType, String> validationLine1;
    public static volatile SingularAttribute<PaymentType, String> validationLine2;
    public static volatile SingularAttribute<PaymentType, String> validationLine3;
    public static volatile SingularAttribute<PaymentType, String> validationMask;
    public static volatile SingularAttribute<PaymentType, Integer> scanCode;
    public static volatile SingularAttribute<PaymentType, java.math.BigDecimal> roundToValue;
    public static volatile SingularAttribute<PaymentType, Boolean> signatureRequired;
    public static volatile SingularAttribute<PaymentType, Boolean> isNetTerm;
    public static volatile SingularAttribute<PaymentType, Integer> version;


    public static volatile SingularAttribute<PaymentType, Store> store;
    public static volatile SingularAttribute<PaymentType, Integer> store_;

    public static volatile ListAttribute<PaymentType, CustomerGroup> customerGroups;
    public static volatile ListAttribute<PaymentType, InvoiceRecurring> invoiceRecurrings;
    public static volatile ListAttribute<PaymentType, Payment> payments;


}

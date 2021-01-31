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
import com.salesliant.entity.PaymentBatch;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.Station;
import com.salesliant.entity.Batch;
import com.salesliant.entity.PoleDisplayMessage;
import com.salesliant.entity.Receipt;
import com.salesliant.entity.Receipt;
import com.salesliant.entity.Store;
import com.salesliant.entity.Station;

@StaticMetamodel(Station.class)
public class Station_ {

    public static volatile SingularAttribute<Station, Integer> id;

    public static volatile SingularAttribute<Station, Integer> number;
    public static volatile SingularAttribute<Station, String> description;
    public static volatile SingularAttribute<Station, Boolean> transactionRequireLogin;
    public static volatile SingularAttribute<Station, Boolean> poleDisplayEnabled;
    public static volatile SingularAttribute<Station, Boolean> scaleEnabled;
    public static volatile SingularAttribute<Station, Boolean> scannerEnabled;
    public static volatile SingularAttribute<Station, Boolean> netDisplayEnabled;
    public static volatile SingularAttribute<Station, Boolean> cashDraw1Enabled;
    public static volatile SingularAttribute<Station, Boolean> cashDraw2Enabled;
    public static volatile SingularAttribute<Station, Boolean> micrEnabled;
    public static volatile SingularAttribute<Station, Boolean> msrEnabled;
    public static volatile SingularAttribute<Station, Boolean> touchSceenEnabled;
    public static volatile SingularAttribute<Station, Boolean> signatureCaptureEnabled;
    public static volatile SingularAttribute<Station, Boolean> cashDraw1WaitForClose;
    public static volatile SingularAttribute<Station, String> poleDisplayName;
    public static volatile SingularAttribute<Station, String> printer1Name;
    public static volatile SingularAttribute<Station, Integer> printer1Options;
    public static volatile SingularAttribute<Station, Integer> printer1Type;
    public static volatile SingularAttribute<Station, String> scaleName;
    public static volatile SingularAttribute<Station, String> scannerName;
    public static volatile SingularAttribute<Station, String> cashDraw1Name;
    public static volatile SingularAttribute<Station, String> cashDraw2Name;
    public static volatile SingularAttribute<Station, java.math.BigDecimal> cashDraw1OpenTimeout;
    public static volatile SingularAttribute<Station, String> micrName;
    public static volatile SingularAttribute<Station, String> msrName;
    public static volatile SingularAttribute<Station, Boolean> cashDraw2WaitForClose;
    public static volatile SingularAttribute<Station, java.math.BigDecimal> cashDraw2OpenTimeout;
    public static volatile SingularAttribute<Station, java.math.BigDecimal> micrTimeout;
    public static volatile SingularAttribute<Station, String> signatureCaptureName;
    public static volatile SingularAttribute<Station, String> signatureCaptureFormName;
    public static volatile SingularAttribute<Station, String> printer2Name;
    public static volatile SingularAttribute<Station, Integer> printer2Options;
    public static volatile SingularAttribute<Station, Integer> printer2Type;
    public static volatile SingularAttribute<Station, Integer> version;


    public static volatile SingularAttribute<Station, Batch> currentBatch;
    public static volatile SingularAttribute<Station, Integer> currentBatch_;

    public static volatile SingularAttribute<Station, PoleDisplayMessage> poleDisplayMessage;
    public static volatile SingularAttribute<Station, Integer> poleDisplayMessage_;

    public static volatile SingularAttribute<Station, Receipt> receipt1;
    public static volatile SingularAttribute<Station, Integer> receipt1_;

    public static volatile SingularAttribute<Station, Receipt> receipt2;
    public static volatile SingularAttribute<Station, Integer> receipt2_;

    public static volatile SingularAttribute<Station, Store> store;
    public static volatile SingularAttribute<Station, Integer> store_;

    public static volatile SingularAttribute<Station, Station> tenderedStation;
    public static volatile SingularAttribute<Station, Integer> tenderedStation_;

    public static volatile ListAttribute<Station, Batch> batchs;
    public static volatile ListAttribute<Station, PaymentBatch> paymentBatchs;
    public static volatile ListAttribute<Station, SalesOrder> salesOrders;
    public static volatile ListAttribute<Station, Station> stations;


}

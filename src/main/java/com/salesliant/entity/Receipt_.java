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

import com.salesliant.entity.Station;
import com.salesliant.entity.Station;
import com.salesliant.entity.Store;

@StaticMetamodel(Receipt.class)
public class Receipt_ {

    public static volatile SingularAttribute<Receipt, Integer> id;

    public static volatile SingularAttribute<Receipt, String> code;
    public static volatile SingularAttribute<Receipt, String> description;
    public static volatile SingularAttribute<Receipt, Boolean> displayCustomerAddress;
    public static volatile SingularAttribute<Receipt, Boolean> displayAccountBalance;
    public static volatile SingularAttribute<Receipt, Boolean> displaySaving;
    public static volatile SingularAttribute<Receipt, String> templateCancel;
    public static volatile SingularAttribute<Receipt, String> templateDrop;
    public static volatile SingularAttribute<Receipt, String> templateLayaway;
    public static volatile SingularAttribute<Receipt, String> templatePayment;
    public static volatile SingularAttribute<Receipt, String> templatePayout;
    public static volatile SingularAttribute<Receipt, String> templateInvoice;
    public static volatile SingularAttribute<Receipt, String> templateOrder;
    public static volatile SingularAttribute<Receipt, String> templateService;
    public static volatile SingularAttribute<Receipt, String> templateQuote;
    public static volatile SingularAttribute<Receipt, String> templateReport;
    public static volatile SingularAttribute<Receipt, String> line1;
    public static volatile SingularAttribute<Receipt, String> line2;
    public static volatile SingularAttribute<Receipt, String> line3;
    public static volatile SingularAttribute<Receipt, String> line4;
    public static volatile SingularAttribute<Receipt, String> line5;
    public static volatile SingularAttribute<Receipt, Integer> version;


    public static volatile SingularAttribute<Receipt, Store> store;
    public static volatile SingularAttribute<Receipt, Integer> store_;

    public static volatile ListAttribute<Receipt, Station> stationReceipt1s;
    public static volatile ListAttribute<Receipt, Station> stationReceipt2s;


}

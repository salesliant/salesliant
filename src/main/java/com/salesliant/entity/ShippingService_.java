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
import com.salesliant.entity.ReturnOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.Shipping;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.Store;

@StaticMetamodel(ShippingService.class)
public class ShippingService_ {

    public static volatile SingularAttribute<ShippingService, Integer> id;

    public static volatile SingularAttribute<ShippingService, String> code;
    public static volatile SingularAttribute<ShippingService, String> description;
    public static volatile SingularAttribute<ShippingService, Boolean> chargeByWeight;
    public static volatile SingularAttribute<ShippingService, Boolean> interpolate;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value1;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge1;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value2;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge2;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value3;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge3;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value4;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge4;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value5;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge5;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value6;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge6;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value7;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge7;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value8;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge8;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value9;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge9;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value10;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge10;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value11;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge11;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value12;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge12;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value13;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge13;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value14;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge14;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> value15;
    public static volatile SingularAttribute<ShippingService, java.math.BigDecimal> charge15;
    public static volatile SingularAttribute<ShippingService, Integer> version;


    public static volatile SingularAttribute<ShippingService, ShippingCarrier> shippingCarrier;
    public static volatile SingularAttribute<ShippingService, Integer> shippingCarrier_;

    public static volatile SingularAttribute<ShippingService, Store> store;
    public static volatile SingularAttribute<ShippingService, Integer> store_;

    public static volatile ListAttribute<ShippingService, CustomerGroup> customerGroups;
    public static volatile ListAttribute<ShippingService, ReturnOrder> returnOrders;
    public static volatile ListAttribute<ShippingService, SalesOrderEntry> salesOrderEntries;
    public static volatile ListAttribute<ShippingService, Shipping> shippings;


}

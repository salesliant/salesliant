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

import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.ShippingService;
import com.salesliant.entity.Store;

@StaticMetamodel(Shipping.class)
public class Shipping_ {

    public static volatile SingularAttribute<Shipping, Integer> id;

    public static volatile SingularAttribute<Shipping, String> firstName;
    public static volatile SingularAttribute<Shipping, String> lastName;
    public static volatile SingularAttribute<Shipping, String> title;
    public static volatile SingularAttribute<Shipping, String> phoneNumber;
    public static volatile SingularAttribute<Shipping, String> faxNumber;
    public static volatile SingularAttribute<Shipping, String> cellPhoneNumber;
    public static volatile SingularAttribute<Shipping, String> emailAddress;
    public static volatile SingularAttribute<Shipping, String> company;
    public static volatile SingularAttribute<Shipping, String> department;
    public static volatile SingularAttribute<Shipping, String> address1;
    public static volatile SingularAttribute<Shipping, String> address2;
    public static volatile SingularAttribute<Shipping, String> city;
    public static volatile SingularAttribute<Shipping, String> state;
    public static volatile SingularAttribute<Shipping, String> postCode;
    public static volatile SingularAttribute<Shipping, String> country;
    public static volatile SingularAttribute<Shipping, java.math.BigDecimal> charge;
    public static volatile SingularAttribute<Shipping, java.math.BigDecimal> netShipCost;
    public static volatile SingularAttribute<Shipping, java.math.BigDecimal> declaredValue;
    public static volatile SingularAttribute<Shipping, String> trackingNumber;
    public static volatile SingularAttribute<Shipping, String> codReturnTrackingNumber;
    public static volatile SingularAttribute<Shipping, String> note;
    public static volatile SingularAttribute<Shipping, Date> dateShipped;
    public static volatile SingularAttribute<Shipping, Date> dateProcessed;
    public static volatile SingularAttribute<Shipping, java.math.BigDecimal> totalWeight;
    public static volatile SingularAttribute<Shipping, Date> dataPacked;
    public static volatile SingularAttribute<Shipping, Integer> status;
    public static volatile SingularAttribute<Shipping, Integer> version;


    public static volatile SingularAttribute<Shipping, SalesOrder> invoice;
    public static volatile SingularAttribute<Shipping, Integer> invoice_;

    public static volatile SingularAttribute<Shipping, ShippingService> shippingService;
    public static volatile SingularAttribute<Shipping, Integer> shippingService_;

    public static volatile SingularAttribute<Shipping, Store> store;
    public static volatile SingularAttribute<Shipping, Integer> store_;



}

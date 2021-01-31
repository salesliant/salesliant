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

import com.salesliant.entity.Item;
import com.salesliant.entity.Store;

@StaticMetamodel(QuantityDiscount.class)
public class QuantityDiscount_ {

    public static volatile SingularAttribute<QuantityDiscount, Integer> id;

    public static volatile SingularAttribute<QuantityDiscount, String> description;
    public static volatile SingularAttribute<QuantityDiscount, Boolean> discountOddItems;
    public static volatile SingularAttribute<QuantityDiscount, Integer> quantity1;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price11;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price21;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price31;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price41;
    public static volatile SingularAttribute<QuantityDiscount, Integer> quantity2;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price12;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price22;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price32;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price42;
    public static volatile SingularAttribute<QuantityDiscount, Integer> quantity3;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price13;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price23;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price33;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price43;
    public static volatile SingularAttribute<QuantityDiscount, Integer> quantity4;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price14;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price24;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price34;
    public static volatile SingularAttribute<QuantityDiscount, java.math.BigDecimal> price44;
    public static volatile SingularAttribute<QuantityDiscount, Integer> version;


    public static volatile SingularAttribute<QuantityDiscount, Store> store;
    public static volatile SingularAttribute<QuantityDiscount, Integer> store_;

    public static volatile ListAttribute<QuantityDiscount, Item> items;


}

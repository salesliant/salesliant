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

import com.salesliant.entity.Tax;
import com.salesliant.entity.Store;

@StaticMetamodel(TaxRate.class)
public class TaxRate_ {

    public static volatile SingularAttribute<TaxRate, Integer> id;

    public static volatile SingularAttribute<TaxRate, String> name;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> rate;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> fixedAmount;
    public static volatile SingularAttribute<TaxRate, Boolean> includePreviousTax;
    public static volatile SingularAttribute<TaxRate, Boolean> usePartialDollar;
    public static volatile SingularAttribute<TaxRate, Boolean> applyOverMinimum;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> itemMinimum;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> itemMaximum;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket01;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket02;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket03;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket04;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket05;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket06;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket07;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket08;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket09;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket10;
    public static volatile SingularAttribute<TaxRate, java.math.BigDecimal> bracket00;
    public static volatile SingularAttribute<TaxRate, Integer> version;


    public static volatile SingularAttribute<TaxRate, Store> store;
    public static volatile SingularAttribute<TaxRate, Integer> store_;

    public static volatile ListAttribute<TaxRate, Tax> taxs;


}

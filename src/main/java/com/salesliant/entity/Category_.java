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

import com.salesliant.entity.Category;
import com.salesliant.entity.Item;
import com.salesliant.entity.SubCategory;
import com.salesliant.entity.Category;
import com.salesliant.entity.TaxClass;

@StaticMetamodel(Category.class)
public class Category_ {

    public static volatile SingularAttribute<Category, Integer> id;

    public static volatile SingularAttribute<Category, String> name;
    public static volatile SingularAttribute<Category, Integer> sortOrder;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> price1;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> price2;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> price3;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> price4;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> price5;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> price6;
    public static volatile SingularAttribute<Category, Integer> commisionMode;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> commissionFixedAmount;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> commisionMaximumAmount;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> commisionPercentProfit;
    public static volatile SingularAttribute<Category, java.math.BigDecimal> commisionPercentSale;
    public static volatile SingularAttribute<Category, Boolean> isAssetTag;
    public static volatile SingularAttribute<Category, Boolean> isShippingTag;
    public static volatile SingularAttribute<Category, Boolean> countTag;
    public static volatile SingularAttribute<Category, Integer> version;


    public static volatile SingularAttribute<Category, Category> parent;
    public static volatile SingularAttribute<Category, Integer> parent_;

    public static volatile SingularAttribute<Category, TaxClass> taxClass;
    public static volatile SingularAttribute<Category, Integer> taxClass_;

    public static volatile ListAttribute<Category, Category> categories;
    public static volatile ListAttribute<Category, Item> items;
    public static volatile ListAttribute<Category, SubCategory> subCategories;


}

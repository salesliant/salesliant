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

import com.salesliant.entity.Store;

@StaticMetamodel(FunctionAccess.class)
public class FunctionAccess_ {

    public static volatile SingularAttribute<FunctionAccess, Integer> id;

    public static volatile SingularAttribute<FunctionAccess, Boolean> salesModule;
    public static volatile SingularAttribute<FunctionAccess, Boolean> inventoryModule;
    public static volatile SingularAttribute<FunctionAccess, Boolean> accountingModule;
    public static volatile SingularAttribute<FunctionAccess, Boolean> managerModule;
    public static volatile SingularAttribute<FunctionAccess, Integer> version;
    public static volatile SingularAttribute<FunctionAccess, String> moduleName;


    public static volatile SingularAttribute<FunctionAccess, Store> store;
    public static volatile SingularAttribute<FunctionAccess, Integer> store_;



}

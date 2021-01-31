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

@StaticMetamodel(PoleDisplayMessage.class)
public class PoleDisplayMessage_ {

    public static volatile SingularAttribute<PoleDisplayMessage, Integer> id;

    public static volatile SingularAttribute<PoleDisplayMessage, String> description;
    public static volatile SingularAttribute<PoleDisplayMessage, String> messageLine1;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> effectLine1;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> updateRateLine1;
    public static volatile SingularAttribute<PoleDisplayMessage, Boolean> dateAndTimeLine1;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> scrollChangeSizeLine1;
    public static volatile SingularAttribute<PoleDisplayMessage, String> messageLine2;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> effectLine2;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> updateRateLine2;
    public static volatile SingularAttribute<PoleDisplayMessage, Boolean> dateAndTimeLine2;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> scrollChangeSizeLine2;
    public static volatile SingularAttribute<PoleDisplayMessage, Integer> version;


    public static volatile ListAttribute<PoleDisplayMessage, Station> stations;


}

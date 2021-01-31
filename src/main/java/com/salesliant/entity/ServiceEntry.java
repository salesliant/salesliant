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
	* - name      : DomainEntityJPA2Annotation
	* - file name : DomainEntityJPA2Annotation.vm
	* - time      : 2021/01/30 AD at 23:59:31 EST
*/
package com.salesliant.entity;

//MP-MANAGED-ADDED-AREA-BEGINNING @import@
//MP-MANAGED-ADDED-AREA-ENDING @import@
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * <p>Title: ServiceEntry</p>
 *
 * <p>Description: Domain Object describing a ServiceEntry entity</p>
 *
 */
@Entity (name="ServiceEntry")
@Table (name="service_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class ServiceEntry implements Serializable {
    private static final long serialVersionUID = 1L;

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name@
    @Column(name="employee_name"  , length=128 , nullable=true , unique=false)
    private String employeeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_entered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_entered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_entered@
    @Column(name="date_entered"   , nullable=true , unique=false)
    private Timestamp dateEntered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_updated-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_updated-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_updated@
    @Column(name="date_updated"   , nullable=true , unique=false)
    private Timestamp dateUpdated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Employee employee;  

    @Column(name="employee_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer employeeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="service_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Service service;  

    @Column(name="service_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer serviceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="service_code_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ServiceCode serviceCode;  

    @Column(name="service_code_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer serviceCodeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public ServiceEntry() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-note@
    public String getNote() {
        return note;
    }
	
    public void setNote (String note) {
        this.note =  note;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_name@
    public String getEmployeeName() {
        return employeeName;
    }
	
    public void setEmployeeName (String employeeName) {
        this.employeeName =  employeeName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_entered@
    public Timestamp getDateEntered() {
        return dateEntered;
    }
	
    public void setDateEntered (Timestamp dateEntered) {
        this.dateEntered =  dateEntered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_updated@
    public Timestamp getDateUpdated() {
        return dateUpdated;
    }
	
    public void setDateUpdated (Timestamp dateUpdated) {
        this.dateUpdated =  dateUpdated;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public Employee getEmployee () {
    	return employee;
    }
	
    public void setEmployee (Employee employee) {
    	this.employee = employee;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }
	
    public void setEmployeeId (Integer employee) {
        this.employeeId =  employee;
    }
	
    public Service getService () {
    	return service;
    }
	
    public void setService (Service service) {
    	this.service = service;
    }

    public Integer getServiceId() {
        return serviceId;
    }
	
    public void setServiceId (Integer service) {
        this.serviceId =  service;
    }
	
    public ServiceCode getServiceCode () {
    	return serviceCode;
    }
	
    public void setServiceCode (ServiceCode serviceCode) {
    	this.serviceCode = serviceCode;
    }

    public Integer getServiceCodeId() {
        return serviceCodeId;
    }
	
    public void setServiceCodeId (Integer serviceCode) {
        this.serviceCodeId =  serviceCode;
    }
	
    public Store getStore () {
    	return store;
    }
	
    public void setStore (Store store) {
    	this.store = store;
    }

    public Integer getStoreId() {
        return storeId;
    }
	
    public void setStoreId (Integer store) {
        this.storeId =  store;
    }
	




//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

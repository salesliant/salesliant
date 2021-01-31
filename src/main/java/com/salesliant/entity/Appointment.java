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
 * <p>Title: Appointment</p>
 *
 * <p>Description: Domain Object describing a Appointment entity</p>
 *
 */
@Entity (name="Appointment")
@Table (name="appointment")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Date dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @appointment_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @appointment_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-appointment_date@
    @Column(name="appointment_date"   , nullable=true , unique=false)
    private Timestamp appointmentDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="customer_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Customer customer;  

    @Column(name="customer_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer customerId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="employee_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private Employee employee;  

    @Column(name="employee_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer employeeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @services-field-appointment@
    @OneToMany (targetEntity=com.salesliant.entity.Service.class, fetch=FetchType.LAZY, mappedBy="appointment", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Service> services = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Appointment() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_created@
    public Date getDateCreated() {
        return dateCreated;
    }
	
    public void setDateCreated (Date dateCreated) {
        this.dateCreated =  dateCreated;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-appointment_date@
    public Timestamp getAppointmentDate() {
        return appointmentDate;
    }
	
    public void setAppointmentDate (Timestamp appointmentDate) {
        this.appointmentDate =  appointmentDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-note@
    public String getNote() {
        return note;
    }
	
    public void setNote (String note) {
        this.note =  note;
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


    public Customer getCustomer () {
    	return customer;
    }
	
    public void setCustomer (Customer customer) {
    	this.customer = customer;
    }

    public Integer getCustomerId() {
        return customerId;
    }
	
    public void setCustomerId (Integer customer) {
        this.customerId =  customer;
    }
	
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @services-getter-appointment@
    public List<Service> getServices() {
        if (services == null){
            services = new ArrayList<>();
        }
        return services;
    }

    public void setServices (List<Service> services) {
        this.services = services;
    }	
    
    public void addServices (Service element) {
    	    getServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-appointment@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-appointment@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

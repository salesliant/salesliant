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
 * <p>Title: TransactionLog</p>
 *
 * <p>Description: Domain Object describing a TransactionLog entity</p>
 *
 */
@Entity (name="TransactionLog")
@Table (name="transaction_log")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class TransactionLog implements Serializable {
    private static final long serialVersionUID = 1L;

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @from_transcation_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @from_transcation_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-from_transcation_type@
    @Column(name="from_transcation_type"   , nullable=true , unique=false)
    private Integer fromTranscationType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @to_transaction_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @to_transaction_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-to_transaction_type@
    @Column(name="to_transaction_type"   , nullable=true , unique=false)
    private Integer toTransactionType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @from_transaction_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @from_transaction_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-from_transaction_number@
    @Column(name="from_transaction_number"  , length=64 , nullable=true , unique=false)
    private String fromTransactionNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @to_transaction_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @to_transaction_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-to_transaction_number@
    @Column(name="to_transaction_number"  , length=64 , nullable=true , unique=false)
    private String toTransactionNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name@
    @Column(name="employee_name"  , length=64 , nullable=true , unique=false)
    private String employeeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_id@
    @Column(name="employee_id"   , nullable=true , unique=false)
    private Integer employee; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_entered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_entered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_entered@
    @Column(name="date_entered"   , nullable=true , unique=false)
    private Timestamp dateEntered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public TransactionLog() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-from_transcation_type@
    public Integer getFromTranscationType() {
        return fromTranscationType;
    }
	
    public void setFromTranscationType (Integer fromTranscationType) {
        this.fromTranscationType =  fromTranscationType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-to_transaction_type@
    public Integer getToTransactionType() {
        return toTransactionType;
    }
	
    public void setToTransactionType (Integer toTransactionType) {
        this.toTransactionType =  toTransactionType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-from_transaction_number@
    public String getFromTransactionNumber() {
        return fromTransactionNumber;
    }
	
    public void setFromTransactionNumber (String fromTransactionNumber) {
        this.fromTransactionNumber =  fromTransactionNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-to_transaction_number@
    public String getToTransactionNumber() {
        return toTransactionNumber;
    }
	
    public void setToTransactionNumber (String toTransactionNumber) {
        this.toTransactionNumber =  toTransactionNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_id@
    public Integer getEmployee() {
        return employee;
    }
	
    public void setEmployee (Integer employee) {
        this.employee =  employee;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


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

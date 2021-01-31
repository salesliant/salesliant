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
 * <p>Title: TransferOrder</p>
 *
 * <p>Description: Domain Object describing a TransferOrder entity</p>
 *
 */
@Entity (name="TransferOrder")
@Table (name="transfer_order")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class TransferOrder implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_STATUS = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @transfer_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transfer_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transfer_order_number@
    @Column(name="transfer_order_number"  , length=64 , nullable=true , unique=false)
    private String transferOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_created-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_created-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_created@
    @Column(name="date_created"   , nullable=true , unique=false)
    private Timestamp dateCreated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_sent-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_sent-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_sent@
    @Column(name="date_sent"   , nullable=true , unique=false)
    private Timestamp dateSent; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_expected-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_expected-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_expected@
    @Column(name="date_expected"   , nullable=true , unique=false)
    private Date dateExpected; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_received-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_received-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_received@
    @Column(name="date_received"   , nullable=true , unique=false)
    private Timestamp dateReceived; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total@
    @Column(name="total"   , nullable=true , unique=false)
    private java.math.BigDecimal total; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @freight_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @freight_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-freight_amount@
    @Column(name="freight_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal freightAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_send_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_send_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_send_name@
    @Column(name="employee_send_name"  , length=64 , nullable=true , unique=false)
    private String employeeSendName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_receive_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_receive_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_receive_name@
    @Column(name="employee_receive_name"  , length=64 , nullable=true , unique=false)
    private String employeeReceiveName; 
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
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="receive_store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store receiveStore;  

    @Column(name="receive_store_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer receiveStoreId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="send_store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store sendStore;  

    @Column(name="send_store_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer sendStoreId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="shipping_carrier_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private ShippingCarrier shippingCarrier;  

    @Column(name="shipping_carrier_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer shippingCarrierId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-ENABLE @transferOrderEntries-field-transfer_order@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderEntry.class, fetch=FetchType.LAZY, mappedBy="transferOrder", cascade=CascadeType.ALL, orphanRemoval = true)//, cascade=CascadeType.ALL)
    private List <TransferOrderEntry> transferOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public TransferOrder() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transfer_order_number@
    public String getTransferOrderNumber() {
        return transferOrderNumber;
    }
	
    public void setTransferOrderNumber (String transferOrderNumber) {
        this.transferOrderNumber =  transferOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_created@
    public Timestamp getDateCreated() {
        return dateCreated;
    }
	
    public void setDateCreated (Timestamp dateCreated) {
        this.dateCreated =  dateCreated;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_sent@
    public Timestamp getDateSent() {
        return dateSent;
    }
	
    public void setDateSent (Timestamp dateSent) {
        this.dateSent =  dateSent;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_expected@
    public Date getDateExpected() {
        return dateExpected;
    }
	
    public void setDateExpected (Date dateExpected) {
        this.dateExpected =  dateExpected;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_received@
    public Timestamp getDateReceived() {
        return dateReceived;
    }
	
    public void setDateReceived (Timestamp dateReceived) {
        this.dateReceived =  dateReceived;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-status@
    public Integer getStatus() {
        return status;
    }
	
    public void setStatus (Integer status) {
        this.status =  status;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total@
    public java.math.BigDecimal getTotal() {
        return total;
    }
	
    public void setTotal (java.math.BigDecimal total) {
        this.total =  total;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-freight_amount@
    public java.math.BigDecimal getFreightAmount() {
        return freightAmount;
    }
	
    public void setFreightAmount (java.math.BigDecimal freightAmount) {
        this.freightAmount =  freightAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_send_name@
    public String getEmployeeSendName() {
        return employeeSendName;
    }
	
    public void setEmployeeSendName (String employeeSendName) {
        this.employeeSendName =  employeeSendName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-employee_receive_name@
    public String getEmployeeReceiveName() {
        return employeeReceiveName;
    }
	
    public void setEmployeeReceiveName (String employeeReceiveName) {
        this.employeeReceiveName =  employeeReceiveName;
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


    public Store getReceiveStore () {
    	return receiveStore;
    }
	
    public void setReceiveStore (Store receiveStore) {
    	this.receiveStore = receiveStore;
    }

    public Integer getReceiveStoreId() {
        return receiveStoreId;
    }
	
    public void setReceiveStoreId (Integer receiveStore) {
        this.receiveStoreId =  receiveStore;
    }
	
    public Store getSendStore () {
    	return sendStore;
    }
	
    public void setSendStore (Store sendStore) {
    	this.sendStore = sendStore;
    }

    public Integer getSendStoreId() {
        return sendStoreId;
    }
	
    public void setSendStoreId (Integer sendStore) {
        this.sendStoreId =  sendStore;
    }
	
    public ShippingCarrier getShippingCarrier () {
    	return shippingCarrier;
    }
	
    public void setShippingCarrier (ShippingCarrier shippingCarrier) {
    	this.shippingCarrier = shippingCarrier;
    }

    public Integer getShippingCarrierId() {
        return shippingCarrierId;
    }
	
    public void setShippingCarrierId (Integer shippingCarrier) {
        this.shippingCarrierId =  shippingCarrier;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderEntries-getter-transfer_order@
    public List<TransferOrderEntry> getTransferOrderEntries() {
        if (transferOrderEntries == null){
            transferOrderEntries = new ArrayList<>();
        }
        return transferOrderEntries;
    }

    public void setTransferOrderEntries (List<TransferOrderEntry> transferOrderEntries) {
        this.transferOrderEntries = transferOrderEntries;
    }	
    
    public void addTransferOrderEntries (TransferOrderEntry element) {
    	    getTransferOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-transfer_order@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (status==null) status=__DEFAULT_STATUS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-transfer_order@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (status==null) status=__DEFAULT_STATUS;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

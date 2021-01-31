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
 * <p>Title: MerchantBatch</p>
 *
 * <p>Description: Domain Object describing a MerchantBatch entity</p>
 *
 */
@Entity (name="MerchantBatch")
@Table (name="merchant_batch")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class MerchantBatch implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @request_terminal_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_terminal_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_terminal_number@
    @Column(name="request_terminal_number"   , nullable=true , unique=false)
    private Integer requestTerminalNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_transmission_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_transmission_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_transmission_date@
    @Column(name="request_transmission_date"   , nullable=true , unique=false)
    private Date requestTransmissionDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_batch_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_batch_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_batch_number@
    @Column(name="request_batch_number"   , nullable=true , unique=false)
    private Integer requestBatchNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_record_format-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_record_format-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_record_format@
    @Column(name="response_record_format"  , length=1 , nullable=true , unique=false)
    private String responseRecordFormat; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_application_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_application_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_application_type@
    @Column(name="response_application_type"  , length=1 , nullable=true , unique=false)
    private String responseApplicationType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_x25_routing_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_x25_routing_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_x25_routing_id@
    @Column(name="response_x25_routing_id"  , length=1 , nullable=true , unique=false)
    private String responseX25Routing; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_record_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_record_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_record_type@
    @Column(name="response_record_type"  , length=5 , nullable=true , unique=false)
    private String responseRecordType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_batch_record_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_batch_record_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_batch_record_count@
    @Column(name="response_batch_record_count"  , length=9 , nullable=true , unique=false)
    private String responseBatchRecordCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_batch_net_deposit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_batch_net_deposit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_batch_net_deposit@
    @Column(name="response_batch_net_deposit"   , nullable=true , unique=false)
    private java.math.BigDecimal responseBatchNetDeposit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_batch_response_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_batch_response_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_batch_response_code@
    @Column(name="response_batch_response_code"  , length=2 , nullable=true , unique=false)
    private String responseBatchResponseCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_batch_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_batch_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_batch_number@
    @Column(name="response_batch_number"  , length=3 , nullable=true , unique=false)
    private String responseBatchNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_batch_response_text-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_batch_response_text-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_batch_response_text@
    @Column(name="response_batch_response_text"  , length=9 , nullable=true , unique=false)
    private String responseBatchResponseText; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @merchantAuthorizations-field-merchant_batch@
    @OneToMany (targetEntity=com.salesliant.entity.MerchantAuthorization.class, fetch=FetchType.LAZY, mappedBy="merchantBatch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <MerchantAuthorization> merchantAuthorizations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public MerchantBatch() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_terminal_number@
    public Integer getRequestTerminalNumber() {
        return requestTerminalNumber;
    }
	
    public void setRequestTerminalNumber (Integer requestTerminalNumber) {
        this.requestTerminalNumber =  requestTerminalNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_transmission_date@
    public Date getRequestTransmissionDate() {
        return requestTransmissionDate;
    }
	
    public void setRequestTransmissionDate (Date requestTransmissionDate) {
        this.requestTransmissionDate =  requestTransmissionDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_batch_number@
    public Integer getRequestBatchNumber() {
        return requestBatchNumber;
    }
	
    public void setRequestBatchNumber (Integer requestBatchNumber) {
        this.requestBatchNumber =  requestBatchNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_record_format@
    public String getResponseRecordFormat() {
        return responseRecordFormat;
    }
	
    public void setResponseRecordFormat (String responseRecordFormat) {
        this.responseRecordFormat =  responseRecordFormat;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_application_type@
    public String getResponseApplicationType() {
        return responseApplicationType;
    }
	
    public void setResponseApplicationType (String responseApplicationType) {
        this.responseApplicationType =  responseApplicationType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_x25_routing_id@
    public String getResponseX25Routing() {
        return responseX25Routing;
    }
	
    public void setResponseX25Routing (String responseX25Routing) {
        this.responseX25Routing =  responseX25Routing;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_record_type@
    public String getResponseRecordType() {
        return responseRecordType;
    }
	
    public void setResponseRecordType (String responseRecordType) {
        this.responseRecordType =  responseRecordType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_batch_record_count@
    public String getResponseBatchRecordCount() {
        return responseBatchRecordCount;
    }
	
    public void setResponseBatchRecordCount (String responseBatchRecordCount) {
        this.responseBatchRecordCount =  responseBatchRecordCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_batch_net_deposit@
    public java.math.BigDecimal getResponseBatchNetDeposit() {
        return responseBatchNetDeposit;
    }
	
    public void setResponseBatchNetDeposit (java.math.BigDecimal responseBatchNetDeposit) {
        this.responseBatchNetDeposit =  responseBatchNetDeposit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_batch_response_code@
    public String getResponseBatchResponseCode() {
        return responseBatchResponseCode;
    }
	
    public void setResponseBatchResponseCode (String responseBatchResponseCode) {
        this.responseBatchResponseCode =  responseBatchResponseCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_batch_number@
    public String getResponseBatchNumber() {
        return responseBatchNumber;
    }
	
    public void setResponseBatchNumber (String responseBatchNumber) {
        this.responseBatchNumber =  responseBatchNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_batch_response_text@
    public String getResponseBatchResponseText() {
        return responseBatchResponseText;
    }
	
    public void setResponseBatchResponseText (String responseBatchResponseText) {
        this.responseBatchResponseText =  responseBatchResponseText;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @merchantAuthorizations-getter-merchant_batch@
    public List<MerchantAuthorization> getMerchantAuthorizations() {
        if (merchantAuthorizations == null){
            merchantAuthorizations = new ArrayList<>();
        }
        return merchantAuthorizations;
    }

    public void setMerchantAuthorizations (List<MerchantAuthorization> merchantAuthorizations) {
        this.merchantAuthorizations = merchantAuthorizations;
    }	
    
    public void addMerchantAuthorizations (MerchantAuthorization element) {
    	    getMerchantAuthorizations().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-merchant_batch@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-merchant_batch@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

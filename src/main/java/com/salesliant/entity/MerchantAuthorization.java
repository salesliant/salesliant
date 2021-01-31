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
 * <p>Title: MerchantAuthorization</p>
 *
 * <p>Description: Domain Object describing a MerchantAuthorization entity</p>
 *
 */
@Entity (name="MerchantAuthorization")
@Table (name="merchant_authorization")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class MerchantAuthorization implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @request_record_format-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_record_format-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_record_format@
    @Column(name="request_record_format"  , length=1 , nullable=true , unique=false)
    private String requestRecordFormat; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_application_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_application_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_application_type@
    @Column(name="request_application_type"  , length=1 , nullable=true , unique=false)
    private String requestApplicationType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_acquirer_bin-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_acquirer_bin-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_acquirer_bin@
    @Column(name="request_acquirer_bin"  , length=6 , nullable=true , unique=false)
    private String requestAcquirerBin; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_merchant_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_merchant_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_merchant_number@
    @Column(name="request_merchant_number"  , length=12 , nullable=true , unique=false)
    private String requestMerchantNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_store_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_store_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_store_number@
    @Column(name="request_store_number"  , length=4 , nullable=true , unique=false)
    private String requestStoreNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_terminal_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_terminal_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_terminal_number@
    @Column(name="request_terminal_number"  , length=4 , nullable=true , unique=false)
    private String requestTerminalNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_device_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_device_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_device_code@
    @Column(name="request_device_code"  , length=1 , nullable=true , unique=false)
    private String requestDeviceCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_industry_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_industry_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_industry_code@
    @Column(name="request_industry_code"  , length=1 , nullable=true , unique=false)
    private String requestIndustryCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_currency_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_currency_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_currency_code@
    @Column(name="request_currency_code"  , length=3 , nullable=true , unique=false)
    private String requestCurrencyCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_country_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_country_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_country_code@
    @Column(name="request_country_code"  , length=3 , nullable=true , unique=false)
    private String requestCountryCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_city_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_city_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_city_code@
    @Column(name="request_city_code"  , length=9 , nullable=true , unique=false)
    private String requestCityCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_language_indicator-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_language_indicator-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_language_indicator@
    @Column(name="request_language_indicator"  , length=2 , nullable=true , unique=false)
    private String requestLanguageIndicator; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_time_zone_differential-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_time_zone_differential-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_time_zone_differential@
    @Column(name="request_time_zone_differential"  , length=3 , nullable=true , unique=false)
    private String requestTimeZoneDifferential; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_merchant_category_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_merchant_category_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_merchant_category_code@
    @Column(name="request_merchant_category_code"  , length=4 , nullable=true , unique=false)
    private String requestMerchantCategoryCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_requested_aci-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_requested_aci-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_requested_aci@
    @Column(name="request_requested_aci"  , length=1 , nullable=true , unique=false)
    private String requestRequestedAci; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_transaction_sequence_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_transaction_sequence_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_transaction_sequence_number@
    @Column(name="request_transaction_sequence_number"  , length=4 , nullable=true , unique=false)
    private String requestTransactionSequenceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_authorization_transaction_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_authorization_transaction_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_authorization_transaction_code@
    @Column(name="request_authorization_transaction_code"  , length=2 , nullable=true , unique=false)
    private String requestAuthorizationTransactionCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_card_holder_identification_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_card_holder_identification_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_card_holder_identification_code@
    @Column(name="request_card_holder_identification_code"  , length=2 , nullable=true , unique=false)
    private String requestCardHolderIdentificationCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_account_data_source-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_account_data_source-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_account_data_source@
    @Column(name="request_account_data_source"  , length=1 , nullable=true , unique=false)
    private String requestAccountDataSource; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_track1_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_track1_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_track1_data@
    @Column(name="request_track1_data"  , length=255 , nullable=true , unique=false)
    private String requestTrack1Data; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_track2_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_track2_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_track2_data@
    @Column(name="request_track2_data"  , length=255 , nullable=true , unique=false)
    private String requestTrack2Data; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_manual_account_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_manual_account_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_manual_account_data@
    @Column(name="request_manual_account_data"  , length=512 , nullable=true , unique=false)
    private String requestManualAccountData; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_manual_expiration_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_manual_expiration_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_manual_expiration_date@
    @Column(name="request_manual_expiration_date"  , length=4 , nullable=true , unique=false)
    private String requestManualExpirationDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_card_holder_identification_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_card_holder_identification_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_card_holder_identification_data@
    @Column(name="request_card_holder_identification_data"  , length=128 , nullable=true , unique=false)
    private String requestCardHolderIdentificationData; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_receiving_institution_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_receiving_institution_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_receiving_institution_id@
    @Column(name="request_receiving_institution_id"  , length=6 , nullable=true , unique=false)
    private String requestReceivingInstitution; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_transaction_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_transaction_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_transaction_amount@
    @Column(name="request_transaction_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal requestTransactionAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_secondary_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_secondary_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_secondary_amount@
    @Column(name="request_secondary_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal requestSecondaryAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_market_specific_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_market_specific_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_market_specific_data@
    @Column(name="request_market_specific_data"  , length=4 , nullable=true , unique=false)
    private String requestMarketSpecificData; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_card_acceptor_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_card_acceptor_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_card_acceptor_data@
    @Column(name="request_card_acceptor_data"  , length=40 , nullable=true , unique=false)
    private String requestCardAcceptorData; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_reversal_tran_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_reversal_tran_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_reversal_tran_id@
    @Column(name="request_reversal_tran_id"  , length=15 , nullable=true , unique=false)
    private String requestReversalTran; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_reversal_data-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_reversal_data-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_reversal_data@
    @Column(name="request_reversal_data"  , length=30 , nullable=true , unique=false)
    private String requestReversalData; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_purchase_identifier-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_purchase_identifier-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_purchase_identifier@
    @Column(name="request_purchase_identifier"  , length=25 , nullable=true , unique=false)
    private String requestPurchaseIdentifier; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @request_groupIII_version_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @request_groupIII_version_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-request_groupIII_version_number@
    @Column(name="request_groupIII_version_number"  , length=3 , nullable=true , unique=false)
    private String requestGroupiiiVersionNumber; 
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

//MP-MANAGED-ADDED-AREA-BEGINNING @response_returned_act-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_returned_act-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_returned_act@
    @Column(name="response_returned_act"  , length=1 , nullable=true , unique=false)
    private String responseReturnedAct; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_store_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_store_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_store_number@
    @Column(name="response_store_number"  , length=4 , nullable=true , unique=false)
    private String responseStoreNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_terminal_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_terminal_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_terminal_number@
    @Column(name="response_terminal_number"  , length=4 , nullable=true , unique=false)
    private String responseTerminalNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_authorization_source_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_authorization_source_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_authorization_source_code@
    @Column(name="response_authorization_source_code"  , length=1 , nullable=true , unique=false)
    private String responseAuthorizationSourceCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_transaction_sequence_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_transaction_sequence_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_transaction_sequence_number@
    @Column(name="response_transaction_sequence_number"  , length=4 , nullable=true , unique=false)
    private String responseTransactionSequenceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_response_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_response_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_response_code@
    @Column(name="response_response_code"  , length=2 , nullable=true , unique=false)
    private String responseResponseCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_approval_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_approval_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_approval_code@
    @Column(name="response_approval_code"  , length=6 , nullable=true , unique=false)
    private String responseApprovalCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_local_transaction_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_local_transaction_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_local_transaction_date@
    @Column(name="response_local_transaction_date"   , nullable=true , unique=false)
    private Timestamp responseLocalTransactionDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_authorization_response_text-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_authorization_response_text-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_authorization_response_text@
    @Column(name="response_authorization_response_text"  , length=16 , nullable=true , unique=false)
    private String responseAuthorizationResponseText; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_avs_result_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_avs_result_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_avs_result_code@
    @Column(name="response_avs_result_code"  , length=1 , nullable=true , unique=false)
    private String responseAvsResultCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_retrieval_reference_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_retrieval_reference_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_retrieval_reference_number@
    @Column(name="response_retrieval_reference_number"  , length=12 , nullable=true , unique=false)
    private String responseRetrievalReferenceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_market_specific_data_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_market_specific_data_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_market_specific_data_id@
    @Column(name="response_market_specific_data_id"  , length=1 , nullable=true , unique=false)
    private String responseMarketSpecificData; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_transaction_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_transaction_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_transaction_id@
    @Column(name="response_transaction_id"  , length=15 , nullable=true , unique=false)
    private String responseTransaction; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @response_validation_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @response_validation_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-response_validation_code@
    @Column(name="response_validation_code"  , length=4 , nullable=true , unique=false)
    private String responseValidationCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="merchant_batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private MerchantBatch merchantBatch;  

    @Column(name="merchant_batch_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer merchantBatchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-field-merchant_authorization@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="merchantAuthorization", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public MerchantAuthorization() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_record_format@
    public String getRequestRecordFormat() {
        return requestRecordFormat;
    }
	
    public void setRequestRecordFormat (String requestRecordFormat) {
        this.requestRecordFormat =  requestRecordFormat;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_application_type@
    public String getRequestApplicationType() {
        return requestApplicationType;
    }
	
    public void setRequestApplicationType (String requestApplicationType) {
        this.requestApplicationType =  requestApplicationType;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_acquirer_bin@
    public String getRequestAcquirerBin() {
        return requestAcquirerBin;
    }
	
    public void setRequestAcquirerBin (String requestAcquirerBin) {
        this.requestAcquirerBin =  requestAcquirerBin;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_merchant_number@
    public String getRequestMerchantNumber() {
        return requestMerchantNumber;
    }
	
    public void setRequestMerchantNumber (String requestMerchantNumber) {
        this.requestMerchantNumber =  requestMerchantNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_store_number@
    public String getRequestStoreNumber() {
        return requestStoreNumber;
    }
	
    public void setRequestStoreNumber (String requestStoreNumber) {
        this.requestStoreNumber =  requestStoreNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_terminal_number@
    public String getRequestTerminalNumber() {
        return requestTerminalNumber;
    }
	
    public void setRequestTerminalNumber (String requestTerminalNumber) {
        this.requestTerminalNumber =  requestTerminalNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_device_code@
    public String getRequestDeviceCode() {
        return requestDeviceCode;
    }
	
    public void setRequestDeviceCode (String requestDeviceCode) {
        this.requestDeviceCode =  requestDeviceCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_industry_code@
    public String getRequestIndustryCode() {
        return requestIndustryCode;
    }
	
    public void setRequestIndustryCode (String requestIndustryCode) {
        this.requestIndustryCode =  requestIndustryCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_currency_code@
    public String getRequestCurrencyCode() {
        return requestCurrencyCode;
    }
	
    public void setRequestCurrencyCode (String requestCurrencyCode) {
        this.requestCurrencyCode =  requestCurrencyCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_country_code@
    public String getRequestCountryCode() {
        return requestCountryCode;
    }
	
    public void setRequestCountryCode (String requestCountryCode) {
        this.requestCountryCode =  requestCountryCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_city_code@
    public String getRequestCityCode() {
        return requestCityCode;
    }
	
    public void setRequestCityCode (String requestCityCode) {
        this.requestCityCode =  requestCityCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_language_indicator@
    public String getRequestLanguageIndicator() {
        return requestLanguageIndicator;
    }
	
    public void setRequestLanguageIndicator (String requestLanguageIndicator) {
        this.requestLanguageIndicator =  requestLanguageIndicator;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_time_zone_differential@
    public String getRequestTimeZoneDifferential() {
        return requestTimeZoneDifferential;
    }
	
    public void setRequestTimeZoneDifferential (String requestTimeZoneDifferential) {
        this.requestTimeZoneDifferential =  requestTimeZoneDifferential;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_merchant_category_code@
    public String getRequestMerchantCategoryCode() {
        return requestMerchantCategoryCode;
    }
	
    public void setRequestMerchantCategoryCode (String requestMerchantCategoryCode) {
        this.requestMerchantCategoryCode =  requestMerchantCategoryCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_requested_aci@
    public String getRequestRequestedAci() {
        return requestRequestedAci;
    }
	
    public void setRequestRequestedAci (String requestRequestedAci) {
        this.requestRequestedAci =  requestRequestedAci;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_transaction_sequence_number@
    public String getRequestTransactionSequenceNumber() {
        return requestTransactionSequenceNumber;
    }
	
    public void setRequestTransactionSequenceNumber (String requestTransactionSequenceNumber) {
        this.requestTransactionSequenceNumber =  requestTransactionSequenceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_authorization_transaction_code@
    public String getRequestAuthorizationTransactionCode() {
        return requestAuthorizationTransactionCode;
    }
	
    public void setRequestAuthorizationTransactionCode (String requestAuthorizationTransactionCode) {
        this.requestAuthorizationTransactionCode =  requestAuthorizationTransactionCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_card_holder_identification_code@
    public String getRequestCardHolderIdentificationCode() {
        return requestCardHolderIdentificationCode;
    }
	
    public void setRequestCardHolderIdentificationCode (String requestCardHolderIdentificationCode) {
        this.requestCardHolderIdentificationCode =  requestCardHolderIdentificationCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_account_data_source@
    public String getRequestAccountDataSource() {
        return requestAccountDataSource;
    }
	
    public void setRequestAccountDataSource (String requestAccountDataSource) {
        this.requestAccountDataSource =  requestAccountDataSource;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_track1_data@
    public String getRequestTrack1Data() {
        return requestTrack1Data;
    }
	
    public void setRequestTrack1Data (String requestTrack1Data) {
        this.requestTrack1Data =  requestTrack1Data;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_track2_data@
    public String getRequestTrack2Data() {
        return requestTrack2Data;
    }
	
    public void setRequestTrack2Data (String requestTrack2Data) {
        this.requestTrack2Data =  requestTrack2Data;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_manual_account_data@
    public String getRequestManualAccountData() {
        return requestManualAccountData;
    }
	
    public void setRequestManualAccountData (String requestManualAccountData) {
        this.requestManualAccountData =  requestManualAccountData;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_manual_expiration_date@
    public String getRequestManualExpirationDate() {
        return requestManualExpirationDate;
    }
	
    public void setRequestManualExpirationDate (String requestManualExpirationDate) {
        this.requestManualExpirationDate =  requestManualExpirationDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_card_holder_identification_data@
    public String getRequestCardHolderIdentificationData() {
        return requestCardHolderIdentificationData;
    }
	
    public void setRequestCardHolderIdentificationData (String requestCardHolderIdentificationData) {
        this.requestCardHolderIdentificationData =  requestCardHolderIdentificationData;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_receiving_institution_id@
    public String getRequestReceivingInstitution() {
        return requestReceivingInstitution;
    }
	
    public void setRequestReceivingInstitution (String requestReceivingInstitution) {
        this.requestReceivingInstitution =  requestReceivingInstitution;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_transaction_amount@
    public java.math.BigDecimal getRequestTransactionAmount() {
        return requestTransactionAmount;
    }
	
    public void setRequestTransactionAmount (java.math.BigDecimal requestTransactionAmount) {
        this.requestTransactionAmount =  requestTransactionAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_secondary_amount@
    public java.math.BigDecimal getRequestSecondaryAmount() {
        return requestSecondaryAmount;
    }
	
    public void setRequestSecondaryAmount (java.math.BigDecimal requestSecondaryAmount) {
        this.requestSecondaryAmount =  requestSecondaryAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_market_specific_data@
    public String getRequestMarketSpecificData() {
        return requestMarketSpecificData;
    }
	
    public void setRequestMarketSpecificData (String requestMarketSpecificData) {
        this.requestMarketSpecificData =  requestMarketSpecificData;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_card_acceptor_data@
    public String getRequestCardAcceptorData() {
        return requestCardAcceptorData;
    }
	
    public void setRequestCardAcceptorData (String requestCardAcceptorData) {
        this.requestCardAcceptorData =  requestCardAcceptorData;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_reversal_tran_id@
    public String getRequestReversalTran() {
        return requestReversalTran;
    }
	
    public void setRequestReversalTran (String requestReversalTran) {
        this.requestReversalTran =  requestReversalTran;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_reversal_data@
    public String getRequestReversalData() {
        return requestReversalData;
    }
	
    public void setRequestReversalData (String requestReversalData) {
        this.requestReversalData =  requestReversalData;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_purchase_identifier@
    public String getRequestPurchaseIdentifier() {
        return requestPurchaseIdentifier;
    }
	
    public void setRequestPurchaseIdentifier (String requestPurchaseIdentifier) {
        this.requestPurchaseIdentifier =  requestPurchaseIdentifier;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-request_groupIII_version_number@
    public String getRequestGroupiiiVersionNumber() {
        return requestGroupiiiVersionNumber;
    }
	
    public void setRequestGroupiiiVersionNumber (String requestGroupiiiVersionNumber) {
        this.requestGroupiiiVersionNumber =  requestGroupiiiVersionNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_returned_act@
    public String getResponseReturnedAct() {
        return responseReturnedAct;
    }
	
    public void setResponseReturnedAct (String responseReturnedAct) {
        this.responseReturnedAct =  responseReturnedAct;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_store_number@
    public String getResponseStoreNumber() {
        return responseStoreNumber;
    }
	
    public void setResponseStoreNumber (String responseStoreNumber) {
        this.responseStoreNumber =  responseStoreNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_terminal_number@
    public String getResponseTerminalNumber() {
        return responseTerminalNumber;
    }
	
    public void setResponseTerminalNumber (String responseTerminalNumber) {
        this.responseTerminalNumber =  responseTerminalNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_authorization_source_code@
    public String getResponseAuthorizationSourceCode() {
        return responseAuthorizationSourceCode;
    }
	
    public void setResponseAuthorizationSourceCode (String responseAuthorizationSourceCode) {
        this.responseAuthorizationSourceCode =  responseAuthorizationSourceCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_transaction_sequence_number@
    public String getResponseTransactionSequenceNumber() {
        return responseTransactionSequenceNumber;
    }
	
    public void setResponseTransactionSequenceNumber (String responseTransactionSequenceNumber) {
        this.responseTransactionSequenceNumber =  responseTransactionSequenceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_response_code@
    public String getResponseResponseCode() {
        return responseResponseCode;
    }
	
    public void setResponseResponseCode (String responseResponseCode) {
        this.responseResponseCode =  responseResponseCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_approval_code@
    public String getResponseApprovalCode() {
        return responseApprovalCode;
    }
	
    public void setResponseApprovalCode (String responseApprovalCode) {
        this.responseApprovalCode =  responseApprovalCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_local_transaction_date@
    public Timestamp getResponseLocalTransactionDate() {
        return responseLocalTransactionDate;
    }
	
    public void setResponseLocalTransactionDate (Timestamp responseLocalTransactionDate) {
        this.responseLocalTransactionDate =  responseLocalTransactionDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_authorization_response_text@
    public String getResponseAuthorizationResponseText() {
        return responseAuthorizationResponseText;
    }
	
    public void setResponseAuthorizationResponseText (String responseAuthorizationResponseText) {
        this.responseAuthorizationResponseText =  responseAuthorizationResponseText;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_avs_result_code@
    public String getResponseAvsResultCode() {
        return responseAvsResultCode;
    }
	
    public void setResponseAvsResultCode (String responseAvsResultCode) {
        this.responseAvsResultCode =  responseAvsResultCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_retrieval_reference_number@
    public String getResponseRetrievalReferenceNumber() {
        return responseRetrievalReferenceNumber;
    }
	
    public void setResponseRetrievalReferenceNumber (String responseRetrievalReferenceNumber) {
        this.responseRetrievalReferenceNumber =  responseRetrievalReferenceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_market_specific_data_id@
    public String getResponseMarketSpecificData() {
        return responseMarketSpecificData;
    }
	
    public void setResponseMarketSpecificData (String responseMarketSpecificData) {
        this.responseMarketSpecificData =  responseMarketSpecificData;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_transaction_id@
    public String getResponseTransaction() {
        return responseTransaction;
    }
	
    public void setResponseTransaction (String responseTransaction) {
        this.responseTransaction =  responseTransaction;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-response_validation_code@
    public String getResponseValidationCode() {
        return responseValidationCode;
    }
	
    public void setResponseValidationCode (String responseValidationCode) {
        this.responseValidationCode =  responseValidationCode;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-version@
    public Integer getVersion() {
        return version;
    }
	
    public void setVersion (Integer version) {
        this.version =  version;
    }
	
//MP-MANAGED-UPDATABLE-ENDING


    public MerchantBatch getMerchantBatch () {
    	return merchantBatch;
    }
	
    public void setMerchantBatch (MerchantBatch merchantBatch) {
    	this.merchantBatch = merchantBatch;
    }

    public Integer getMerchantBatchId() {
        return merchantBatchId;
    }
	
    public void setMerchantBatchId (Integer merchantBatch) {
        this.merchantBatchId =  merchantBatch;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-merchant_authorization@
    public List<Payment> getPayments() {
        if (payments == null){
            payments = new ArrayList<>();
        }
        return payments;
    }

    public void setPayments (List<Payment> payments) {
        this.payments = payments;
    }	
    
    public void addPayments (Payment element) {
    	    getPayments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-merchant_authorization@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-merchant_authorization@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

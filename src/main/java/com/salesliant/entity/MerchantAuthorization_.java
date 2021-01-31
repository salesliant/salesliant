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

import com.salesliant.entity.Payment;
import com.salesliant.entity.MerchantBatch;
import com.salesliant.entity.Store;

@StaticMetamodel(MerchantAuthorization.class)
public class MerchantAuthorization_ {

    public static volatile SingularAttribute<MerchantAuthorization, Integer> id;

    public static volatile SingularAttribute<MerchantAuthorization, String> requestRecordFormat;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestApplicationType;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestAcquirerBin;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestMerchantNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestStoreNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestTerminalNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestDeviceCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestIndustryCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestCurrencyCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestCountryCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestCityCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestLanguageIndicator;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestTimeZoneDifferential;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestMerchantCategoryCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestRequestedAci;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestTransactionSequenceNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestAuthorizationTransactionCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestCardHolderIdentificationCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestAccountDataSource;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestTrack1Data;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestTrack2Data;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestManualAccountData;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestManualExpirationDate;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestCardHolderIdentificationData;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestReceivingInstitution;
    public static volatile SingularAttribute<MerchantAuthorization, java.math.BigDecimal> requestTransactionAmount;
    public static volatile SingularAttribute<MerchantAuthorization, java.math.BigDecimal> requestSecondaryAmount;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestMarketSpecificData;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestCardAcceptorData;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestReversalTran;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestReversalData;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestPurchaseIdentifier;
    public static volatile SingularAttribute<MerchantAuthorization, String> requestGroupiiiVersionNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseRecordFormat;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseApplicationType;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseReturnedAct;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseStoreNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseTerminalNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseAuthorizationSourceCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseTransactionSequenceNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseResponseCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseApprovalCode;
    public static volatile SingularAttribute<MerchantAuthorization, Timestamp> responseLocalTransactionDate;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseAuthorizationResponseText;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseAvsResultCode;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseRetrievalReferenceNumber;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseMarketSpecificData;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseTransaction;
    public static volatile SingularAttribute<MerchantAuthorization, String> responseValidationCode;
    public static volatile SingularAttribute<MerchantAuthorization, Integer> status;
    public static volatile SingularAttribute<MerchantAuthorization, Integer> version;


    public static volatile SingularAttribute<MerchantAuthorization, MerchantBatch> merchantBatch;
    public static volatile SingularAttribute<MerchantAuthorization, Integer> merchantBatch_;

    public static volatile SingularAttribute<MerchantAuthorization, Store> store;
    public static volatile SingularAttribute<MerchantAuthorization, Integer> store_;

    public static volatile ListAttribute<MerchantAuthorization, Payment> payments;


}

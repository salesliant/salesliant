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
 * <p>Title: Station</p>
 *
 * <p>Description: Domain Object describing a Station entity</p>
 *
 */
@Entity (name="Station")
@Table (name="station")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Station implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-number@
    @Column(name="number"   , nullable=true , unique=false)
    private Integer number; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transaction_require_login-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transaction_require_login-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transaction_require_login@
    @Column(name="transaction_require_login"   , nullable=true , unique=false)
    private Boolean transactionRequireLogin; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @pole_display_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @pole_display_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-pole_display_enabled@
    @Column(name="pole_display_enabled"   , nullable=true , unique=false)
    private Boolean poleDisplayEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scale_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scale_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scale_enabled@
    @Column(name="scale_enabled"   , nullable=true , unique=false)
    private Boolean scaleEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scanner_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scanner_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scanner_enabled@
    @Column(name="scanner_enabled"   , nullable=true , unique=false)
    private Boolean scannerEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @net_display_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @net_display_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-net_display_enabled@
    @Column(name="net_display_enabled"   , nullable=true , unique=false)
    private Boolean netDisplayEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw1_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw1_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw1_enabled@
    @Column(name="cash_draw1_enabled"   , nullable=true , unique=false)
    private Boolean cashDraw1Enabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw2_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw2_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw2_enabled@
    @Column(name="cash_draw2_enabled"   , nullable=true , unique=false)
    private Boolean cashDraw2Enabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @micr_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @micr_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-micr_enabled@
    @Column(name="micr_enabled"   , nullable=true , unique=false)
    private Boolean micrEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @msr_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @msr_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-msr_enabled@
    @Column(name="msr_enabled"   , nullable=true , unique=false)
    private Boolean msrEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @touch_sceen_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @touch_sceen_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-touch_sceen_enabled@
    @Column(name="touch_sceen_enabled"   , nullable=true , unique=false)
    private Boolean touchSceenEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @signature_capture_enabled-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @signature_capture_enabled-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-signature_capture_enabled@
    @Column(name="signature_capture_enabled"   , nullable=true , unique=false)
    private Boolean signatureCaptureEnabled; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw1_wait_for_close-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw1_wait_for_close-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw1_wait_for_close@
    @Column(name="cash_draw1_wait_for_close"   , nullable=true , unique=false)
    private Boolean cashDraw1WaitForClose; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @pole_display_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @pole_display_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-pole_display_name@
    @Column(name="pole_display_name"  , length=64 , nullable=true , unique=false)
    private String poleDisplayName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer1_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer1_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer1_name@
    @Column(name="printer1_name"  , length=64 , nullable=true , unique=false)
    private String printer1Name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer1_options-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer1_options-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer1_options@
    @Column(name="printer1_options"   , nullable=true , unique=false)
    private Integer printer1Options; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer1_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer1_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer1_type@
    @Column(name="printer1_type"   , nullable=true , unique=false)
    private Integer printer1Type; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scale_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scale_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scale_name@
    @Column(name="scale_name"  , length=64 , nullable=true , unique=false)
    private String scaleName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @scanner_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @scanner_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-scanner_name@
    @Column(name="scanner_name"  , length=64 , nullable=true , unique=false)
    private String scannerName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw1_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw1_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw1_name@
    @Column(name="cash_draw1_name"  , length=64 , nullable=true , unique=false)
    private String cashDraw1Name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw2_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw2_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw2_name@
    @Column(name="cash_draw2_name"  , length=64 , nullable=true , unique=false)
    private String cashDraw2Name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw1_open_timeout-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw1_open_timeout-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw1_open_timeout@
    @Column(name="cash_draw1_open_timeout"   , nullable=true , unique=false)
    private java.math.BigDecimal cashDraw1OpenTimeout; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @micr_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @micr_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-micr_name@
    @Column(name="micr_name"  , length=64 , nullable=true , unique=false)
    private String micrName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @msr_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @msr_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-msr_name@
    @Column(name="msr_name"  , length=64 , nullable=true , unique=false)
    private String msrName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw2_wait_for_close-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw2_wait_for_close-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw2_wait_for_close@
    @Column(name="cash_draw2_wait_for_close"   , nullable=true , unique=false)
    private Boolean cashDraw2WaitForClose; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_draw2_open_timeout-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_draw2_open_timeout-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_draw2_open_timeout@
    @Column(name="cash_draw2_open_timeout"   , nullable=true , unique=false)
    private java.math.BigDecimal cashDraw2OpenTimeout; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @micr_timeout-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @micr_timeout-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-micr_timeout@
    @Column(name="micr_timeout"   , nullable=true , unique=false)
    private java.math.BigDecimal micrTimeout; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @signature_capture_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @signature_capture_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-signature_capture_name@
    @Column(name="signature_capture_name"  , length=64 , nullable=true , unique=false)
    private String signatureCaptureName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @signature_capture_form_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @signature_capture_form_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-signature_capture_form_name@
    @Column(name="signature_capture_form_name"  , length=64 , nullable=true , unique=false)
    private String signatureCaptureFormName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer2_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer2_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer2_name@
    @Column(name="printer2_name"  , length=64 , nullable=true , unique=false)
    private String printer2Name; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer2_options-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer2_options-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer2_options@
    @Column(name="printer2_options"   , nullable=true , unique=false)
    private Integer printer2Options; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @printer2_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @printer2_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-printer2_type@
    @Column(name="printer2_type"   , nullable=true , unique=false)
    private Integer printer2Type; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="current_batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Batch currentBatch;  

    @Column(name="current_batch_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer currentBatchId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="pole_display_message_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private PoleDisplayMessage poleDisplayMessage;  

    @Column(name="pole_display_message_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer poleDisplayMessageId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="receipt1_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Receipt receipt1;  

    @Column(name="receipt1_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer receipt1Id;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="receipt2_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Receipt receipt2;  

    @Column(name="receipt2_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer receipt2Id;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=false , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="tendered_station_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Station tenderedStation;  

    @Column(name="tendered_station_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer tenderedStationId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-field-station@
    @OneToMany (targetEntity=com.salesliant.entity.Batch.class, fetch=FetchType.LAZY, mappedBy="station", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Batch> batchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentBatchs-field-station@
    @OneToMany (targetEntity=com.salesliant.entity.PaymentBatch.class, fetch=FetchType.LAZY, mappedBy="station", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PaymentBatch> paymentBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-field-station@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrder.class, fetch=FetchType.LAZY, mappedBy="station", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrder> salesOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-field-station@
    @OneToMany (targetEntity=com.salesliant.entity.Station.class, fetch=FetchType.LAZY, mappedBy="tenderedStation", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Station> stations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Station() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-number@
    public Integer getNumber() {
        return number;
    }
	
    public void setNumber (Integer number) {
        this.number =  number;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-description@
    public String getDescription() {
        return description;
    }
	
    public void setDescription (String description) {
        this.description =  description;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transaction_require_login@
    public Boolean getTransactionRequireLogin() {
        return transactionRequireLogin;
    }
	
    public void setTransactionRequireLogin (Boolean transactionRequireLogin) {
        this.transactionRequireLogin =  transactionRequireLogin;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-pole_display_enabled@
    public Boolean getPoleDisplayEnabled() {
        return poleDisplayEnabled;
    }
	
    public void setPoleDisplayEnabled (Boolean poleDisplayEnabled) {
        this.poleDisplayEnabled =  poleDisplayEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scale_enabled@
    public Boolean getScaleEnabled() {
        return scaleEnabled;
    }
	
    public void setScaleEnabled (Boolean scaleEnabled) {
        this.scaleEnabled =  scaleEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scanner_enabled@
    public Boolean getScannerEnabled() {
        return scannerEnabled;
    }
	
    public void setScannerEnabled (Boolean scannerEnabled) {
        this.scannerEnabled =  scannerEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-net_display_enabled@
    public Boolean getNetDisplayEnabled() {
        return netDisplayEnabled;
    }
	
    public void setNetDisplayEnabled (Boolean netDisplayEnabled) {
        this.netDisplayEnabled =  netDisplayEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw1_enabled@
    public Boolean getCashDraw1Enabled() {
        return cashDraw1Enabled;
    }
	
    public void setCashDraw1Enabled (Boolean cashDraw1Enabled) {
        this.cashDraw1Enabled =  cashDraw1Enabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw2_enabled@
    public Boolean getCashDraw2Enabled() {
        return cashDraw2Enabled;
    }
	
    public void setCashDraw2Enabled (Boolean cashDraw2Enabled) {
        this.cashDraw2Enabled =  cashDraw2Enabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-micr_enabled@
    public Boolean getMicrEnabled() {
        return micrEnabled;
    }
	
    public void setMicrEnabled (Boolean micrEnabled) {
        this.micrEnabled =  micrEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-msr_enabled@
    public Boolean getMsrEnabled() {
        return msrEnabled;
    }
	
    public void setMsrEnabled (Boolean msrEnabled) {
        this.msrEnabled =  msrEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-touch_sceen_enabled@
    public Boolean getTouchSceenEnabled() {
        return touchSceenEnabled;
    }
	
    public void setTouchSceenEnabled (Boolean touchSceenEnabled) {
        this.touchSceenEnabled =  touchSceenEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-signature_capture_enabled@
    public Boolean getSignatureCaptureEnabled() {
        return signatureCaptureEnabled;
    }
	
    public void setSignatureCaptureEnabled (Boolean signatureCaptureEnabled) {
        this.signatureCaptureEnabled =  signatureCaptureEnabled;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw1_wait_for_close@
    public Boolean getCashDraw1WaitForClose() {
        return cashDraw1WaitForClose;
    }
	
    public void setCashDraw1WaitForClose (Boolean cashDraw1WaitForClose) {
        this.cashDraw1WaitForClose =  cashDraw1WaitForClose;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-pole_display_name@
    public String getPoleDisplayName() {
        return poleDisplayName;
    }
	
    public void setPoleDisplayName (String poleDisplayName) {
        this.poleDisplayName =  poleDisplayName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer1_name@
    public String getPrinter1Name() {
        return printer1Name;
    }
	
    public void setPrinter1Name (String printer1Name) {
        this.printer1Name =  printer1Name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer1_options@
    public Integer getPrinter1Options() {
        return printer1Options;
    }
	
    public void setPrinter1Options (Integer printer1Options) {
        this.printer1Options =  printer1Options;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer1_type@
    public Integer getPrinter1Type() {
        return printer1Type;
    }
	
    public void setPrinter1Type (Integer printer1Type) {
        this.printer1Type =  printer1Type;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scale_name@
    public String getScaleName() {
        return scaleName;
    }
	
    public void setScaleName (String scaleName) {
        this.scaleName =  scaleName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-scanner_name@
    public String getScannerName() {
        return scannerName;
    }
	
    public void setScannerName (String scannerName) {
        this.scannerName =  scannerName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw1_name@
    public String getCashDraw1Name() {
        return cashDraw1Name;
    }
	
    public void setCashDraw1Name (String cashDraw1Name) {
        this.cashDraw1Name =  cashDraw1Name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw2_name@
    public String getCashDraw2Name() {
        return cashDraw2Name;
    }
	
    public void setCashDraw2Name (String cashDraw2Name) {
        this.cashDraw2Name =  cashDraw2Name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw1_open_timeout@
    public java.math.BigDecimal getCashDraw1OpenTimeout() {
        return cashDraw1OpenTimeout;
    }
	
    public void setCashDraw1OpenTimeout (java.math.BigDecimal cashDraw1OpenTimeout) {
        this.cashDraw1OpenTimeout =  cashDraw1OpenTimeout;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-micr_name@
    public String getMicrName() {
        return micrName;
    }
	
    public void setMicrName (String micrName) {
        this.micrName =  micrName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-msr_name@
    public String getMsrName() {
        return msrName;
    }
	
    public void setMsrName (String msrName) {
        this.msrName =  msrName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw2_wait_for_close@
    public Boolean getCashDraw2WaitForClose() {
        return cashDraw2WaitForClose;
    }
	
    public void setCashDraw2WaitForClose (Boolean cashDraw2WaitForClose) {
        this.cashDraw2WaitForClose =  cashDraw2WaitForClose;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_draw2_open_timeout@
    public java.math.BigDecimal getCashDraw2OpenTimeout() {
        return cashDraw2OpenTimeout;
    }
	
    public void setCashDraw2OpenTimeout (java.math.BigDecimal cashDraw2OpenTimeout) {
        this.cashDraw2OpenTimeout =  cashDraw2OpenTimeout;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-micr_timeout@
    public java.math.BigDecimal getMicrTimeout() {
        return micrTimeout;
    }
	
    public void setMicrTimeout (java.math.BigDecimal micrTimeout) {
        this.micrTimeout =  micrTimeout;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-signature_capture_name@
    public String getSignatureCaptureName() {
        return signatureCaptureName;
    }
	
    public void setSignatureCaptureName (String signatureCaptureName) {
        this.signatureCaptureName =  signatureCaptureName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-signature_capture_form_name@
    public String getSignatureCaptureFormName() {
        return signatureCaptureFormName;
    }
	
    public void setSignatureCaptureFormName (String signatureCaptureFormName) {
        this.signatureCaptureFormName =  signatureCaptureFormName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer2_name@
    public String getPrinter2Name() {
        return printer2Name;
    }
	
    public void setPrinter2Name (String printer2Name) {
        this.printer2Name =  printer2Name;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer2_options@
    public Integer getPrinter2Options() {
        return printer2Options;
    }
	
    public void setPrinter2Options (Integer printer2Options) {
        this.printer2Options =  printer2Options;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-printer2_type@
    public Integer getPrinter2Type() {
        return printer2Type;
    }
	
    public void setPrinter2Type (Integer printer2Type) {
        this.printer2Type =  printer2Type;
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


    public Batch getCurrentBatch () {
    	return currentBatch;
    }
	
    public void setCurrentBatch (Batch currentBatch) {
    	this.currentBatch = currentBatch;
    }

    public Integer getCurrentBatchId() {
        return currentBatchId;
    }
	
    public void setCurrentBatchId (Integer currentBatch) {
        this.currentBatchId =  currentBatch;
    }
	
    public PoleDisplayMessage getPoleDisplayMessage () {
    	return poleDisplayMessage;
    }
	
    public void setPoleDisplayMessage (PoleDisplayMessage poleDisplayMessage) {
    	this.poleDisplayMessage = poleDisplayMessage;
    }

    public Integer getPoleDisplayMessageId() {
        return poleDisplayMessageId;
    }
	
    public void setPoleDisplayMessageId (Integer poleDisplayMessage) {
        this.poleDisplayMessageId =  poleDisplayMessage;
    }
	
    public Receipt getReceipt1 () {
    	return receipt1;
    }
	
    public void setReceipt1 (Receipt receipt1) {
    	this.receipt1 = receipt1;
    }

    public Integer getReceipt1Id() {
        return receipt1Id;
    }
	
    public void setReceipt1Id (Integer receipt1) {
        this.receipt1Id =  receipt1;
    }
	
    public Receipt getReceipt2 () {
    	return receipt2;
    }
	
    public void setReceipt2 (Receipt receipt2) {
    	this.receipt2 = receipt2;
    }

    public Integer getReceipt2Id() {
        return receipt2Id;
    }
	
    public void setReceipt2Id (Integer receipt2) {
        this.receipt2Id =  receipt2;
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
	
    public Station getTenderedStation () {
    	return tenderedStation;
    }
	
    public void setTenderedStation (Station tenderedStation) {
    	this.tenderedStation = tenderedStation;
    }

    public Integer getTenderedStationId() {
        return tenderedStationId;
    }
	
    public void setTenderedStationId (Integer tenderedStation) {
        this.tenderedStationId =  tenderedStation;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-getter-station@
    public List<Batch> getBatchs() {
        if (batchs == null){
            batchs = new ArrayList<>();
        }
        return batchs;
    }

    public void setBatchs (List<Batch> batchs) {
        this.batchs = batchs;
    }	
    
    public void addBatchs (Batch element) {
    	    getBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentBatchs-getter-station@
    public List<PaymentBatch> getPaymentBatchs() {
        if (paymentBatchs == null){
            paymentBatchs = new ArrayList<>();
        }
        return paymentBatchs;
    }

    public void setPaymentBatchs (List<PaymentBatch> paymentBatchs) {
        this.paymentBatchs = paymentBatchs;
    }	
    
    public void addPaymentBatchs (PaymentBatch element) {
    	    getPaymentBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-getter-station@
    public List<SalesOrder> getSalesOrders() {
        if (salesOrders == null){
            salesOrders = new ArrayList<>();
        }
        return salesOrders;
    }

    public void setSalesOrders (List<SalesOrder> salesOrders) {
        this.salesOrders = salesOrders;
    }	
    
    public void addSalesOrders (SalesOrder element) {
    	    getSalesOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-getter-station@
    public List<Station> getStations() {
        if (stations == null){
            stations = new ArrayList<>();
        }
        return stations;
    }

    public void setStations (List<Station> stations) {
        this.stations = stations;
    }	
    
    public void addStations (Station element) {
    	    getStations().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-station@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-station@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

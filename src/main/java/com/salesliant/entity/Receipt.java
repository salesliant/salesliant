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
 * <p>Title: Receipt</p>
 *
 * <p>Description: Domain Object describing a Receipt entity</p>
 *
 */
@Entity (name="Receipt")
@Table (name="receipt")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Receipt implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-code@
    @Column(name="code"  , length=32 , nullable=false , unique=true)
    private String code; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_customer_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_customer_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_customer_address@
    @Column(name="display_customer_address"   , nullable=true , unique=false)
    private Boolean displayCustomerAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_account_balance-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_account_balance-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_account_balance@
    @Column(name="display_account_balance"   , nullable=true , unique=false)
    private Boolean displayAccountBalance; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_saving-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_saving-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_saving@
    @Column(name="display_saving"   , nullable=true , unique=false)
    private Boolean displaySaving; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_cancel-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_cancel-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_cancel@
    @Column(name="template_cancel"  , length=65535 , nullable=true , unique=false)
    private String templateCancel; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_drop-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_drop-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_drop@
    @Column(name="template_drop"  , length=65535 , nullable=true , unique=false)
    private String templateDrop; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_layaway-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_layaway-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_layaway@
    @Column(name="template_layaway"  , length=65535 , nullable=true , unique=false)
    private String templateLayaway; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_payment-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_payment-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_payment@
    @Column(name="template_payment"  , length=65535 , nullable=true , unique=false)
    private String templatePayment; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_payout-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_payout-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_payout@
    @Column(name="template_payout"  , length=65535 , nullable=true , unique=false)
    private String templatePayout; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_invoice-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_invoice-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_invoice@
    @Column(name="template_invoice"  , length=65535 , nullable=true , unique=false)
    private String templateInvoice; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_order-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_order-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_order@
    @Column(name="template_order"  , length=65535 , nullable=true , unique=false)
    private String templateOrder; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_service-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_service-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_service@
    @Column(name="template_service"  , length=65535 , nullable=true , unique=false)
    private String templateService; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_quote-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_quote-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_quote@
    @Column(name="template_quote"  , length=65535 , nullable=true , unique=false)
    private String templateQuote; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @template_report-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @template_report-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-template_report@
    @Column(name="template_report"  , length=65535 , nullable=true , unique=false)
    private String templateReport; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line1@
    @Column(name="line1"  , length=64 , nullable=true , unique=false)
    private String line1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line2@
    @Column(name="line2"  , length=64 , nullable=true , unique=false)
    private String line2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line3@
    @Column(name="line3"  , length=64 , nullable=true , unique=false)
    private String line3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line4@
    @Column(name="line4"  , length=64 , nullable=true , unique=false)
    private String line4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @line5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @line5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-line5@
    @Column(name="line5"  , length=64 , nullable=true , unique=false)
    private String line5; 
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stationReceipt1s-field-receipt@
    @OneToMany (targetEntity=com.salesliant.entity.Station.class, fetch=FetchType.LAZY, mappedBy="receipt1", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Station> stationReceipt1s = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stationReceipt2s-field-receipt@
    @OneToMany (targetEntity=com.salesliant.entity.Station.class, fetch=FetchType.LAZY, mappedBy="receipt2", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Station> stationReceipt2s = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Receipt() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-code@
    public String getCode() {
        return code;
    }
	
    public void setCode (String code) {
        this.code =  code;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_customer_address@
    public Boolean getDisplayCustomerAddress() {
        return displayCustomerAddress;
    }
	
    public void setDisplayCustomerAddress (Boolean displayCustomerAddress) {
        this.displayCustomerAddress =  displayCustomerAddress;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_account_balance@
    public Boolean getDisplayAccountBalance() {
        return displayAccountBalance;
    }
	
    public void setDisplayAccountBalance (Boolean displayAccountBalance) {
        this.displayAccountBalance =  displayAccountBalance;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_saving@
    public Boolean getDisplaySaving() {
        return displaySaving;
    }
	
    public void setDisplaySaving (Boolean displaySaving) {
        this.displaySaving =  displaySaving;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_cancel@
    public String getTemplateCancel() {
        return templateCancel;
    }
	
    public void setTemplateCancel (String templateCancel) {
        this.templateCancel =  templateCancel;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_drop@
    public String getTemplateDrop() {
        return templateDrop;
    }
	
    public void setTemplateDrop (String templateDrop) {
        this.templateDrop =  templateDrop;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_layaway@
    public String getTemplateLayaway() {
        return templateLayaway;
    }
	
    public void setTemplateLayaway (String templateLayaway) {
        this.templateLayaway =  templateLayaway;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_payment@
    public String getTemplatePayment() {
        return templatePayment;
    }
	
    public void setTemplatePayment (String templatePayment) {
        this.templatePayment =  templatePayment;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_payout@
    public String getTemplatePayout() {
        return templatePayout;
    }
	
    public void setTemplatePayout (String templatePayout) {
        this.templatePayout =  templatePayout;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_invoice@
    public String getTemplateInvoice() {
        return templateInvoice;
    }
	
    public void setTemplateInvoice (String templateInvoice) {
        this.templateInvoice =  templateInvoice;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_order@
    public String getTemplateOrder() {
        return templateOrder;
    }
	
    public void setTemplateOrder (String templateOrder) {
        this.templateOrder =  templateOrder;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_service@
    public String getTemplateService() {
        return templateService;
    }
	
    public void setTemplateService (String templateService) {
        this.templateService =  templateService;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_quote@
    public String getTemplateQuote() {
        return templateQuote;
    }
	
    public void setTemplateQuote (String templateQuote) {
        this.templateQuote =  templateQuote;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-template_report@
    public String getTemplateReport() {
        return templateReport;
    }
	
    public void setTemplateReport (String templateReport) {
        this.templateReport =  templateReport;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line1@
    public String getLine1() {
        return line1;
    }
	
    public void setLine1 (String line1) {
        this.line1 =  line1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line2@
    public String getLine2() {
        return line2;
    }
	
    public void setLine2 (String line2) {
        this.line2 =  line2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line3@
    public String getLine3() {
        return line3;
    }
	
    public void setLine3 (String line3) {
        this.line3 =  line3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line4@
    public String getLine4() {
        return line4;
    }
	
    public void setLine4 (String line4) {
        this.line4 =  line4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-line5@
    public String getLine5() {
        return line5;
    }
	
    public void setLine5 (String line5) {
        this.line5 =  line5;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stationReceipt1s-getter-receipt@
    public List<Station> getStationReceipt1s() {
        if (stationReceipt1s == null){
            stationReceipt1s = new ArrayList<>();
        }
        return stationReceipt1s;
    }

    public void setStationReceipt1s (List<Station> stationReceipt1s) {
        this.stationReceipt1s = stationReceipt1s;
    }	
    
    public void addStationReceipt1s (Station element) {
    	    getStationReceipt1s().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stationReceipt2s-getter-receipt@
    public List<Station> getStationReceipt2s() {
        if (stationReceipt2s == null){
            stationReceipt2s = new ArrayList<>();
        }
        return stationReceipt2s;
    }

    public void setStationReceipt2s (List<Station> stationReceipt2s) {
        this.stationReceipt2s = stationReceipt2s;
    }	
    
    public void addStationReceipt2s (Station element) {
    	    getStationReceipt2s().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-receipt@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-receipt@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

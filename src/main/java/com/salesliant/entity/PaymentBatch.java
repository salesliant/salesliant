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
 * <p>Title: PaymentBatch</p>
 *
 * <p>Description: Domain Object describing a PaymentBatch entity</p>
 *
 */
@Entity (name="PaymentBatch")
@Table (name="payment_batch")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class PaymentBatch implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_CASH_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CASH_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CASH_LEFT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CHECK_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CHECK_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COUPON_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COUPON_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_GIFT_CERTIFICATE_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_GIFT_CERTIFICATE_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_ON_ACCOUNT_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_ON_ACCOUNT_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_GIFT_CARD_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_GIFT_CARD_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CREDIT_CARD_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CREDIT_CARD_COUNTED = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @behavior_type-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @behavior_type-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-behavior_type@
    @Column(name="behavior_type"   , nullable=true , unique=false)
    private Integer behaviorType; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @employee_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @employee_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-employee_name@
    @Column(name="employee_name"  , length=64 , nullable=true , unique=false)
    private String employeeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_tendered@
    @Column(name="cash_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal cashTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_counted@
    @Column(name="cash_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal cashCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cash_left-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cash_left-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cash_left@
    @Column(name="cash_left"   , nullable=true , unique=false)
    private java.math.BigDecimal cashLeft; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_tendered@
    @Column(name="check_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal checkTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @check_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @check_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-check_counted@
    @Column(name="check_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal checkCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @coupon_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @coupon_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-coupon_tendered@
    @Column(name="coupon_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal couponTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @coupon_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @coupon_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-coupon_counted@
    @Column(name="coupon_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal couponCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gift_certificate_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gift_certificate_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gift_certificate_tendered@
    @Column(name="gift_certificate_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal giftCertificateTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gift_certificate_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gift_certificate_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gift_certificate_counted@
    @Column(name="gift_certificate_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal giftCertificateCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @on_account_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @on_account_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-on_account_tendered@
    @Column(name="on_account_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal onAccountTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @on_account_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @on_account_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-on_account_counted@
    @Column(name="on_account_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal onAccountCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @note-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @note-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-note@
    @Column(name="note"  , length=65535 , nullable=true , unique=false)
    private String note; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gift_card_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gift_card_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gift_card_tendered@
    @Column(name="gift_card_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal giftCardTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @gift_card_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @gift_card_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-gift_card_counted@
    @Column(name="gift_card_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal giftCardCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_tendered@
    @Column(name="credit_card_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal creditCardTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_card_counted-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_card_counted-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_card_counted@
    @Column(name="credit_card_counted"   , nullable=true , unique=false)
    private java.math.BigDecimal creditCardCounted; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="employee_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Employee employee;  

    @Column(name="employee_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer employeeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="station_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Station station;  

    @Column(name="station_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer stationId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-field-payment_batch@
    @OneToMany (targetEntity=com.salesliant.entity.Batch.class, fetch=FetchType.LAZY, mappedBy="paymentBatch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Batch> batchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public PaymentBatch() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-behavior_type@
    public Integer getBehaviorType() {
        return behaviorType;
    }
	
    public void setBehaviorType (Integer behaviorType) {
        this.behaviorType =  behaviorType;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_tendered@
    public java.math.BigDecimal getCashTendered() {
        return cashTendered;
    }
	
    public void setCashTendered (java.math.BigDecimal cashTendered) {
        this.cashTendered =  cashTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_counted@
    public java.math.BigDecimal getCashCounted() {
        return cashCounted;
    }
	
    public void setCashCounted (java.math.BigDecimal cashCounted) {
        this.cashCounted =  cashCounted;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cash_left@
    public java.math.BigDecimal getCashLeft() {
        return cashLeft;
    }
	
    public void setCashLeft (java.math.BigDecimal cashLeft) {
        this.cashLeft =  cashLeft;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_tendered@
    public java.math.BigDecimal getCheckTendered() {
        return checkTendered;
    }
	
    public void setCheckTendered (java.math.BigDecimal checkTendered) {
        this.checkTendered =  checkTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-check_counted@
    public java.math.BigDecimal getCheckCounted() {
        return checkCounted;
    }
	
    public void setCheckCounted (java.math.BigDecimal checkCounted) {
        this.checkCounted =  checkCounted;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-coupon_tendered@
    public java.math.BigDecimal getCouponTendered() {
        return couponTendered;
    }
	
    public void setCouponTendered (java.math.BigDecimal couponTendered) {
        this.couponTendered =  couponTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-coupon_counted@
    public java.math.BigDecimal getCouponCounted() {
        return couponCounted;
    }
	
    public void setCouponCounted (java.math.BigDecimal couponCounted) {
        this.couponCounted =  couponCounted;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gift_certificate_tendered@
    public java.math.BigDecimal getGiftCertificateTendered() {
        return giftCertificateTendered;
    }
	
    public void setGiftCertificateTendered (java.math.BigDecimal giftCertificateTendered) {
        this.giftCertificateTendered =  giftCertificateTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gift_certificate_counted@
    public java.math.BigDecimal getGiftCertificateCounted() {
        return giftCertificateCounted;
    }
	
    public void setGiftCertificateCounted (java.math.BigDecimal giftCertificateCounted) {
        this.giftCertificateCounted =  giftCertificateCounted;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-on_account_tendered@
    public java.math.BigDecimal getOnAccountTendered() {
        return onAccountTendered;
    }
	
    public void setOnAccountTendered (java.math.BigDecimal onAccountTendered) {
        this.onAccountTendered =  onAccountTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-on_account_counted@
    public java.math.BigDecimal getOnAccountCounted() {
        return onAccountCounted;
    }
	
    public void setOnAccountCounted (java.math.BigDecimal onAccountCounted) {
        this.onAccountCounted =  onAccountCounted;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gift_card_tendered@
    public java.math.BigDecimal getGiftCardTendered() {
        return giftCardTendered;
    }
	
    public void setGiftCardTendered (java.math.BigDecimal giftCardTendered) {
        this.giftCardTendered =  giftCardTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-gift_card_counted@
    public java.math.BigDecimal getGiftCardCounted() {
        return giftCardCounted;
    }
	
    public void setGiftCardCounted (java.math.BigDecimal giftCardCounted) {
        this.giftCardCounted =  giftCardCounted;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_card_tendered@
    public java.math.BigDecimal getCreditCardTendered() {
        return creditCardTendered;
    }
	
    public void setCreditCardTendered (java.math.BigDecimal creditCardTendered) {
        this.creditCardTendered =  creditCardTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_card_counted@
    public java.math.BigDecimal getCreditCardCounted() {
        return creditCardCounted;
    }
	
    public void setCreditCardCounted (java.math.BigDecimal creditCardCounted) {
        this.creditCardCounted =  creditCardCounted;
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
	
    public Station getStation () {
    	return station;
    }
	
    public void setStation (Station station) {
    	this.station = station;
    }

    public Integer getStationId() {
        return stationId;
    }
	
    public void setStationId (Integer station) {
        this.stationId =  station;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-getter-payment_batch@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-payment_batch@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (cashTendered==null) cashTendered=__DEFAULT_CASH_TENDERED;
        if (cashCounted==null) cashCounted=__DEFAULT_CASH_COUNTED;
        if (cashLeft==null) cashLeft=__DEFAULT_CASH_LEFT;
        if (checkTendered==null) checkTendered=__DEFAULT_CHECK_TENDERED;
        if (checkCounted==null) checkCounted=__DEFAULT_CHECK_COUNTED;
        if (couponTendered==null) couponTendered=__DEFAULT_COUPON_TENDERED;
        if (couponCounted==null) couponCounted=__DEFAULT_COUPON_COUNTED;
        if (giftCertificateTendered==null) giftCertificateTendered=__DEFAULT_GIFT_CERTIFICATE_TENDERED;
        if (giftCertificateCounted==null) giftCertificateCounted=__DEFAULT_GIFT_CERTIFICATE_COUNTED;
        if (onAccountTendered==null) onAccountTendered=__DEFAULT_ON_ACCOUNT_TENDERED;
        if (onAccountCounted==null) onAccountCounted=__DEFAULT_ON_ACCOUNT_COUNTED;
        if (giftCardTendered==null) giftCardTendered=__DEFAULT_GIFT_CARD_TENDERED;
        if (giftCardCounted==null) giftCardCounted=__DEFAULT_GIFT_CARD_COUNTED;
        if (creditCardTendered==null) creditCardTendered=__DEFAULT_CREDIT_CARD_TENDERED;
        if (creditCardCounted==null) creditCardCounted=__DEFAULT_CREDIT_CARD_COUNTED;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-payment_batch@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (cashTendered==null) cashTendered=__DEFAULT_CASH_TENDERED;
        if (cashCounted==null) cashCounted=__DEFAULT_CASH_COUNTED;
        if (cashLeft==null) cashLeft=__DEFAULT_CASH_LEFT;
        if (checkTendered==null) checkTendered=__DEFAULT_CHECK_TENDERED;
        if (checkCounted==null) checkCounted=__DEFAULT_CHECK_COUNTED;
        if (couponTendered==null) couponTendered=__DEFAULT_COUPON_TENDERED;
        if (couponCounted==null) couponCounted=__DEFAULT_COUPON_COUNTED;
        if (giftCertificateTendered==null) giftCertificateTendered=__DEFAULT_GIFT_CERTIFICATE_TENDERED;
        if (giftCertificateCounted==null) giftCertificateCounted=__DEFAULT_GIFT_CERTIFICATE_COUNTED;
        if (onAccountTendered==null) onAccountTendered=__DEFAULT_ON_ACCOUNT_TENDERED;
        if (onAccountCounted==null) onAccountCounted=__DEFAULT_ON_ACCOUNT_COUNTED;
        if (giftCardTendered==null) giftCardTendered=__DEFAULT_GIFT_CARD_TENDERED;
        if (giftCardCounted==null) giftCardCounted=__DEFAULT_GIFT_CARD_COUNTED;
        if (creditCardTendered==null) creditCardTendered=__DEFAULT_CREDIT_CARD_TENDERED;
        if (creditCardCounted==null) creditCardCounted=__DEFAULT_CREDIT_CARD_COUNTED;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

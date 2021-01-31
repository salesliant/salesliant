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
 * <p>Title: Batch</p>
 *
 * <p>Description: Domain Object describing a Batch entity</p>
 *
 */
@Entity (name="Batch")
@Table (name="batch")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Batch implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final java.math.BigDecimal __DEFAULT_DEPOSIT_MADE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DEPOSIT_REDEEMED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CREDIT_MADE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CREDIT_REDEEMED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_OPENING_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_CLOSING_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TOTAL_TENDERED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TOTAL_CHANGE = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_SALES_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_RETURN_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_TAX = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COMMISSION_TOTAL = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_PAID_OUT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DROP_PAYOUT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_UNCOLLECTABLE_DEBIT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_UNCOLLECTABLE_CREDIT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_PAID_ON_ACCOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_PAID_TO_ACCOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_LAYAWAY_PA = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_LAYAWAY_CLOSED = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_DISCOUNT_AMOUNT = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_COST_OF_GOODS = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_SHIPPING = java.math.BigDecimal.valueOf(0.0000);
	public static final java.math.BigDecimal __DEFAULT_ROUNDING_ERROR = java.math.BigDecimal.valueOf(0.0000);
	public static final Integer __DEFAULT_ABORTED_TRANS_COUNT = Integer.valueOf(0);
	public static final Integer __DEFAULT_CUSTOMER_COUNT = Integer.valueOf(0);
	public static final Integer __DEFAULT_NO_SALES_COUNT = Integer.valueOf(0);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @batch_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @batch_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-batch_number@
    @Column(name="batch_number"   , nullable=false , unique=true)
    private Integer batchNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @deposit_made-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @deposit_made-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-deposit_made@
    @Column(name="deposit_made"   , nullable=true , unique=false)
    private java.math.BigDecimal depositMade; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @deposit_redeemed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @deposit_redeemed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-deposit_redeemed@
    @Column(name="deposit_redeemed"   , nullable=true , unique=false)
    private java.math.BigDecimal depositRedeemed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_made-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_made-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_made@
    @Column(name="credit_made"   , nullable=true , unique=false)
    private java.math.BigDecimal creditMade; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @credit_redeemed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @credit_redeemed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-credit_redeemed@
    @Column(name="credit_redeemed"   , nullable=true , unique=false)
    private java.math.BigDecimal creditRedeemed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @opening_total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @opening_total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-opening_total@
    @Column(name="opening_total"   , nullable=true , unique=false)
    private java.math.BigDecimal openingTotal; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @closing_total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @closing_total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-closing_total@
    @Column(name="closing_total"   , nullable=true , unique=false)
    private java.math.BigDecimal closingTotal; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_opened-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_opened-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_opened@
    @Column(name="date_opened"   , nullable=true , unique=false)
    private Timestamp dateOpened; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_closed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_closed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_closed@
    @Column(name="date_closed"   , nullable=true , unique=false)
    private Timestamp dateClosed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @status-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @status-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-status@
    @Column(name="status"   , nullable=true , unique=false)
    private Integer status; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total_tendered-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total_tendered-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total_tendered@
    @Column(name="total_tendered"   , nullable=true , unique=false)
    private java.math.BigDecimal totalTendered; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @total_change-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @total_change-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-total_change@
    @Column(name="total_change"   , nullable=true , unique=false)
    private java.math.BigDecimal totalChange; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sales_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sales_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sales_amount@
    @Column(name="sales_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal salesAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @return_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @return_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-return_amount@
    @Column(name="return_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal returnAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @tax-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @tax-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-tax@
    @Column(name="tax"   , nullable=true , unique=false)
    private java.math.BigDecimal tax; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @commission_total-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @commission_total-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-commission_total@
    @Column(name="commission_total"   , nullable=true , unique=false)
    private java.math.BigDecimal commissionTotal; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @paid_out-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @paid_out-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-paid_out@
    @Column(name="paid_out"   , nullable=true , unique=false)
    private java.math.BigDecimal paidOut; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @drop_payout_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @drop_payout_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-drop_payout_amount@
    @Column(name="drop_payout_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal dropPayoutAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @uncollectable_debit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @uncollectable_debit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-uncollectable_debit_amount@
    @Column(name="uncollectable_debit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal uncollectableDebitAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @uncollectable_credit_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @uncollectable_credit_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-uncollectable_credit_amount@
    @Column(name="uncollectable_credit_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal uncollectableCreditAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @paid_on_account-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @paid_on_account-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-paid_on_account@
    @Column(name="paid_on_account"   , nullable=true , unique=false)
    private java.math.BigDecimal paidOnAccount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @paid_to_account-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @paid_to_account-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-paid_to_account@
    @Column(name="paid_to_account"   , nullable=true , unique=false)
    private java.math.BigDecimal paidToAccount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @layaway_paid-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @layaway_paid-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-layaway_paid@
    @Column(name="layaway_paid"   , nullable=true , unique=false)
    private java.math.BigDecimal layawayPa; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @layaway_closed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @layaway_closed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-layaway_closed@
    @Column(name="layaway_closed"   , nullable=true , unique=false)
    private java.math.BigDecimal layawayClosed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @discount_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @discount_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-discount_amount@
    @Column(name="discount_amount"   , nullable=true , unique=false)
    private java.math.BigDecimal discountAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @cost_of_goods-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @cost_of_goods-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-cost_of_goods@
    @Column(name="cost_of_goods"   , nullable=true , unique=false)
    private java.math.BigDecimal costOfGoods; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @shipping-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @shipping-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-shipping@
    @Column(name="shipping"   , nullable=true , unique=false)
    private java.math.BigDecimal shipping; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @rounding_error-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @rounding_error-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-rounding_error@
    @Column(name="rounding_error"   , nullable=true , unique=false)
    private java.math.BigDecimal roundingError; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @aborted_trans_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @aborted_trans_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-aborted_trans_count@
    @Column(name="aborted_trans_count"   , nullable=true , unique=false)
    private Integer abortedTransCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @customer_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @customer_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-customer_count@
    @Column(name="customer_count"   , nullable=true , unique=false)
    private Integer customerCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @no_sales_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @no_sales_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-no_sales_count@
    @Column(name="no_sales_count"   , nullable=true , unique=false)
    private Integer noSalesCount; 
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

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="payment_batch_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private PaymentBatch paymentBatch;  

    @Column(name="payment_batch_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer paymentBatchId;

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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.AccountReceivable.class, fetch=FetchType.LAZY, mappedBy="batch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountReceivable> accountReceivables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @depositCloseBatchs-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="closeBatch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> depositCloseBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @depositOpenBatchs-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="openBatch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> depositOpenBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @dropPayouts-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.DropPayout.class, fetch=FetchType.LAZY, mappedBy="batch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <DropPayout> dropPayouts = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoices-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.Invoice.class, fetch=FetchType.LAZY, mappedBy="batch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Invoice> invoices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="batch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-field-batch@
    @OneToMany (targetEntity=com.salesliant.entity.Station.class, fetch=FetchType.LAZY, mappedBy="currentBatch", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Station> stations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Batch() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-batch_number@
    public Integer getBatchNumber() {
        return batchNumber;
    }
	
    public void setBatchNumber (Integer batchNumber) {
        this.batchNumber =  batchNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-deposit_made@
    public java.math.BigDecimal getDepositMade() {
        return depositMade;
    }
	
    public void setDepositMade (java.math.BigDecimal depositMade) {
        this.depositMade =  depositMade;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-deposit_redeemed@
    public java.math.BigDecimal getDepositRedeemed() {
        return depositRedeemed;
    }
	
    public void setDepositRedeemed (java.math.BigDecimal depositRedeemed) {
        this.depositRedeemed =  depositRedeemed;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_made@
    public java.math.BigDecimal getCreditMade() {
        return creditMade;
    }
	
    public void setCreditMade (java.math.BigDecimal creditMade) {
        this.creditMade =  creditMade;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-credit_redeemed@
    public java.math.BigDecimal getCreditRedeemed() {
        return creditRedeemed;
    }
	
    public void setCreditRedeemed (java.math.BigDecimal creditRedeemed) {
        this.creditRedeemed =  creditRedeemed;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-opening_total@
    public java.math.BigDecimal getOpeningTotal() {
        return openingTotal;
    }
	
    public void setOpeningTotal (java.math.BigDecimal openingTotal) {
        this.openingTotal =  openingTotal;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-closing_total@
    public java.math.BigDecimal getClosingTotal() {
        return closingTotal;
    }
	
    public void setClosingTotal (java.math.BigDecimal closingTotal) {
        this.closingTotal =  closingTotal;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_opened@
    public Timestamp getDateOpened() {
        return dateOpened;
    }
	
    public void setDateOpened (Timestamp dateOpened) {
        this.dateOpened =  dateOpened;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_closed@
    public Timestamp getDateClosed() {
        return dateClosed;
    }
	
    public void setDateClosed (Timestamp dateClosed) {
        this.dateClosed =  dateClosed;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total_tendered@
    public java.math.BigDecimal getTotalTendered() {
        return totalTendered;
    }
	
    public void setTotalTendered (java.math.BigDecimal totalTendered) {
        this.totalTendered =  totalTendered;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-total_change@
    public java.math.BigDecimal getTotalChange() {
        return totalChange;
    }
	
    public void setTotalChange (java.math.BigDecimal totalChange) {
        this.totalChange =  totalChange;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sales_amount@
    public java.math.BigDecimal getSalesAmount() {
        return salesAmount;
    }
	
    public void setSalesAmount (java.math.BigDecimal salesAmount) {
        this.salesAmount =  salesAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-return_amount@
    public java.math.BigDecimal getReturnAmount() {
        return returnAmount;
    }
	
    public void setReturnAmount (java.math.BigDecimal returnAmount) {
        this.returnAmount =  returnAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-tax@
    public java.math.BigDecimal getTax() {
        return tax;
    }
	
    public void setTax (java.math.BigDecimal tax) {
        this.tax =  tax;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-commission_total@
    public java.math.BigDecimal getCommissionTotal() {
        return commissionTotal;
    }
	
    public void setCommissionTotal (java.math.BigDecimal commissionTotal) {
        this.commissionTotal =  commissionTotal;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-paid_out@
    public java.math.BigDecimal getPaidOut() {
        return paidOut;
    }
	
    public void setPaidOut (java.math.BigDecimal paidOut) {
        this.paidOut =  paidOut;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-drop_payout_amount@
    public java.math.BigDecimal getDropPayoutAmount() {
        return dropPayoutAmount;
    }
	
    public void setDropPayoutAmount (java.math.BigDecimal dropPayoutAmount) {
        this.dropPayoutAmount =  dropPayoutAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-uncollectable_debit_amount@
    public java.math.BigDecimal getUncollectableDebitAmount() {
        return uncollectableDebitAmount;
    }
	
    public void setUncollectableDebitAmount (java.math.BigDecimal uncollectableDebitAmount) {
        this.uncollectableDebitAmount =  uncollectableDebitAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-uncollectable_credit_amount@
    public java.math.BigDecimal getUncollectableCreditAmount() {
        return uncollectableCreditAmount;
    }
	
    public void setUncollectableCreditAmount (java.math.BigDecimal uncollectableCreditAmount) {
        this.uncollectableCreditAmount =  uncollectableCreditAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-paid_on_account@
    public java.math.BigDecimal getPaidOnAccount() {
        return paidOnAccount;
    }
	
    public void setPaidOnAccount (java.math.BigDecimal paidOnAccount) {
        this.paidOnAccount =  paidOnAccount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-paid_to_account@
    public java.math.BigDecimal getPaidToAccount() {
        return paidToAccount;
    }
	
    public void setPaidToAccount (java.math.BigDecimal paidToAccount) {
        this.paidToAccount =  paidToAccount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-layaway_paid@
    public java.math.BigDecimal getLayawayPa() {
        return layawayPa;
    }
	
    public void setLayawayPa (java.math.BigDecimal layawayPa) {
        this.layawayPa =  layawayPa;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-layaway_closed@
    public java.math.BigDecimal getLayawayClosed() {
        return layawayClosed;
    }
	
    public void setLayawayClosed (java.math.BigDecimal layawayClosed) {
        this.layawayClosed =  layawayClosed;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-discount_amount@
    public java.math.BigDecimal getDiscountAmount() {
        return discountAmount;
    }
	
    public void setDiscountAmount (java.math.BigDecimal discountAmount) {
        this.discountAmount =  discountAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-cost_of_goods@
    public java.math.BigDecimal getCostOfGoods() {
        return costOfGoods;
    }
	
    public void setCostOfGoods (java.math.BigDecimal costOfGoods) {
        this.costOfGoods =  costOfGoods;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-shipping@
    public java.math.BigDecimal getShipping() {
        return shipping;
    }
	
    public void setShipping (java.math.BigDecimal shipping) {
        this.shipping =  shipping;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-rounding_error@
    public java.math.BigDecimal getRoundingError() {
        return roundingError;
    }
	
    public void setRoundingError (java.math.BigDecimal roundingError) {
        this.roundingError =  roundingError;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-aborted_trans_count@
    public Integer getAbortedTransCount() {
        return abortedTransCount;
    }
	
    public void setAbortedTransCount (Integer abortedTransCount) {
        this.abortedTransCount =  abortedTransCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-customer_count@
    public Integer getCustomerCount() {
        return customerCount;
    }
	
    public void setCustomerCount (Integer customerCount) {
        this.customerCount =  customerCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-no_sales_count@
    public Integer getNoSalesCount() {
        return noSalesCount;
    }
	
    public void setNoSalesCount (Integer noSalesCount) {
        this.noSalesCount =  noSalesCount;
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
	
    public PaymentBatch getPaymentBatch () {
    	return paymentBatch;
    }
	
    public void setPaymentBatch (PaymentBatch paymentBatch) {
    	this.paymentBatch = paymentBatch;
    }

    public Integer getPaymentBatchId() {
        return paymentBatchId;
    }
	
    public void setPaymentBatchId (Integer paymentBatch) {
        this.paymentBatchId =  paymentBatch;
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
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-getter-batch@
    public List<AccountReceivable> getAccountReceivables() {
        if (accountReceivables == null){
            accountReceivables = new ArrayList<>();
        }
        return accountReceivables;
    }

    public void setAccountReceivables (List<AccountReceivable> accountReceivables) {
        this.accountReceivables = accountReceivables;
    }	
    
    public void addAccountReceivables (AccountReceivable element) {
    	    getAccountReceivables().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @depositCloseBatchs-getter-batch@
    public List<Deposit> getDepositCloseBatchs() {
        if (depositCloseBatchs == null){
            depositCloseBatchs = new ArrayList<>();
        }
        return depositCloseBatchs;
    }

    public void setDepositCloseBatchs (List<Deposit> depositCloseBatchs) {
        this.depositCloseBatchs = depositCloseBatchs;
    }	
    
    public void addDepositCloseBatchs (Deposit element) {
    	    getDepositCloseBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @depositOpenBatchs-getter-batch@
    public List<Deposit> getDepositOpenBatchs() {
        if (depositOpenBatchs == null){
            depositOpenBatchs = new ArrayList<>();
        }
        return depositOpenBatchs;
    }

    public void setDepositOpenBatchs (List<Deposit> depositOpenBatchs) {
        this.depositOpenBatchs = depositOpenBatchs;
    }	
    
    public void addDepositOpenBatchs (Deposit element) {
    	    getDepositOpenBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @dropPayouts-getter-batch@
    public List<DropPayout> getDropPayouts() {
        if (dropPayouts == null){
            dropPayouts = new ArrayList<>();
        }
        return dropPayouts;
    }

    public void setDropPayouts (List<DropPayout> dropPayouts) {
        this.dropPayouts = dropPayouts;
    }	
    
    public void addDropPayouts (DropPayout element) {
    	    getDropPayouts().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoices-getter-batch@
    public List<Invoice> getInvoices() {
        if (invoices == null){
            invoices = new ArrayList<>();
        }
        return invoices;
    }

    public void setInvoices (List<Invoice> invoices) {
        this.invoices = invoices;
    }	
    
    public void addInvoices (Invoice element) {
    	    getInvoices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-batch@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-getter-batch@
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-batch@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (depositMade==null) depositMade=__DEFAULT_DEPOSIT_MADE;
        if (depositRedeemed==null) depositRedeemed=__DEFAULT_DEPOSIT_REDEEMED;
        if (creditMade==null) creditMade=__DEFAULT_CREDIT_MADE;
        if (creditRedeemed==null) creditRedeemed=__DEFAULT_CREDIT_REDEEMED;
        if (openingTotal==null) openingTotal=__DEFAULT_OPENING_TOTAL;
        if (closingTotal==null) closingTotal=__DEFAULT_CLOSING_TOTAL;
        if (totalTendered==null) totalTendered=__DEFAULT_TOTAL_TENDERED;
        if (totalChange==null) totalChange=__DEFAULT_TOTAL_CHANGE;
        if (salesAmount==null) salesAmount=__DEFAULT_SALES_AMOUNT;
        if (returnAmount==null) returnAmount=__DEFAULT_RETURN_AMOUNT;
        if (tax==null) tax=__DEFAULT_TAX;
        if (commissionTotal==null) commissionTotal=__DEFAULT_COMMISSION_TOTAL;
        if (paidOut==null) paidOut=__DEFAULT_PAID_OUT;
        if (dropPayoutAmount==null) dropPayoutAmount=__DEFAULT_DROP_PAYOUT_AMOUNT;
        if (uncollectableDebitAmount==null) uncollectableDebitAmount=__DEFAULT_UNCOLLECTABLE_DEBIT_AMOUNT;
        if (uncollectableCreditAmount==null) uncollectableCreditAmount=__DEFAULT_UNCOLLECTABLE_CREDIT_AMOUNT;
        if (paidOnAccount==null) paidOnAccount=__DEFAULT_PAID_ON_ACCOUNT;
        if (paidToAccount==null) paidToAccount=__DEFAULT_PAID_TO_ACCOUNT;
        if (layawayPa==null) layawayPa=__DEFAULT_LAYAWAY_PA;
        if (layawayClosed==null) layawayClosed=__DEFAULT_LAYAWAY_CLOSED;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (costOfGoods==null) costOfGoods=__DEFAULT_COST_OF_GOODS;
        if (shipping==null) shipping=__DEFAULT_SHIPPING;
        if (roundingError==null) roundingError=__DEFAULT_ROUNDING_ERROR;
        if (abortedTransCount==null) abortedTransCount=__DEFAULT_ABORTED_TRANS_COUNT;
        if (customerCount==null) customerCount=__DEFAULT_CUSTOMER_COUNT;
        if (noSalesCount==null) noSalesCount=__DEFAULT_NO_SALES_COUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-batch@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (depositMade==null) depositMade=__DEFAULT_DEPOSIT_MADE;
        if (depositRedeemed==null) depositRedeemed=__DEFAULT_DEPOSIT_REDEEMED;
        if (creditMade==null) creditMade=__DEFAULT_CREDIT_MADE;
        if (creditRedeemed==null) creditRedeemed=__DEFAULT_CREDIT_REDEEMED;
        if (openingTotal==null) openingTotal=__DEFAULT_OPENING_TOTAL;
        if (closingTotal==null) closingTotal=__DEFAULT_CLOSING_TOTAL;
        if (totalTendered==null) totalTendered=__DEFAULT_TOTAL_TENDERED;
        if (totalChange==null) totalChange=__DEFAULT_TOTAL_CHANGE;
        if (salesAmount==null) salesAmount=__DEFAULT_SALES_AMOUNT;
        if (returnAmount==null) returnAmount=__DEFAULT_RETURN_AMOUNT;
        if (tax==null) tax=__DEFAULT_TAX;
        if (commissionTotal==null) commissionTotal=__DEFAULT_COMMISSION_TOTAL;
        if (paidOut==null) paidOut=__DEFAULT_PAID_OUT;
        if (dropPayoutAmount==null) dropPayoutAmount=__DEFAULT_DROP_PAYOUT_AMOUNT;
        if (uncollectableDebitAmount==null) uncollectableDebitAmount=__DEFAULT_UNCOLLECTABLE_DEBIT_AMOUNT;
        if (uncollectableCreditAmount==null) uncollectableCreditAmount=__DEFAULT_UNCOLLECTABLE_CREDIT_AMOUNT;
        if (paidOnAccount==null) paidOnAccount=__DEFAULT_PAID_ON_ACCOUNT;
        if (paidToAccount==null) paidToAccount=__DEFAULT_PAID_TO_ACCOUNT;
        if (layawayPa==null) layawayPa=__DEFAULT_LAYAWAY_PA;
        if (layawayClosed==null) layawayClosed=__DEFAULT_LAYAWAY_CLOSED;
        if (discountAmount==null) discountAmount=__DEFAULT_DISCOUNT_AMOUNT;
        if (costOfGoods==null) costOfGoods=__DEFAULT_COST_OF_GOODS;
        if (shipping==null) shipping=__DEFAULT_SHIPPING;
        if (roundingError==null) roundingError=__DEFAULT_ROUNDING_ERROR;
        if (abortedTransCount==null) abortedTransCount=__DEFAULT_ABORTED_TRANS_COUNT;
        if (customerCount==null) customerCount=__DEFAULT_CUSTOMER_COUNT;
        if (noSalesCount==null) noSalesCount=__DEFAULT_NO_SALES_COUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

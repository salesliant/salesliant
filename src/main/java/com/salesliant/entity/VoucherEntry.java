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
	* - time      : 2021/01/30 AD at 23:59:32 EST
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
 * <p>Title: VoucherEntry</p>
 *
 * <p>Description: Domain Object describing a VoucherEntry entity</p>
 *
 */
@Entity (name="VoucherEntry")
@Table (name="voucher_entry")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class VoucherEntry implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @Date_Processed-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @Date_Processed-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-Date_Processed@
    @Column(name="Date_Processed"   , nullable=true , unique=false)
    private Date dateProcessed; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-amount@
    @Column(name="amount"   , nullable=true , unique=false)
    private java.math.BigDecimal amount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="payment_entry_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Payment paymentEntry;  

    @Column(name="payment_entry_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer paymentEntryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_entry_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private SalesOrderEntry invoiceEntry;  

    @Column(name="invoice_entry_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer invoiceEntryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="Voucher_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Voucher voucher;  

    @Column(name="Voucher_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer voucherId;

    /**
    * Default constructor
    */
    public VoucherEntry() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-Date_Processed@
    public Date getDateProcessed() {
        return dateProcessed;
    }
	
    public void setDateProcessed (Date dateProcessed) {
        this.dateProcessed =  dateProcessed;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-amount@
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	
    public void setAmount (java.math.BigDecimal amount) {
        this.amount =  amount;
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


    public Payment getPaymentEntry () {
    	return paymentEntry;
    }
	
    public void setPaymentEntry (Payment paymentEntry) {
    	this.paymentEntry = paymentEntry;
    }

    public Integer getPaymentEntryId() {
        return paymentEntryId;
    }
	
    public void setPaymentEntryId (Integer paymentEntry) {
        this.paymentEntryId =  paymentEntry;
    }
	
    public SalesOrderEntry getInvoiceEntry () {
    	return invoiceEntry;
    }
	
    public void setInvoiceEntry (SalesOrderEntry invoiceEntry) {
    	this.invoiceEntry = invoiceEntry;
    }

    public Integer getInvoiceEntryId() {
        return invoiceEntryId;
    }
	
    public void setInvoiceEntryId (Integer invoiceEntry) {
        this.invoiceEntryId =  invoiceEntry;
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
	
    public Voucher getVoucher () {
    	return voucher;
    }
	
    public void setVoucher (Voucher voucher) {
    	this.voucher = voucher;
    }

    public Integer getVoucherId() {
        return voucherId;
    }
	
    public void setVoucherId (Integer voucher) {
        this.voucherId =  voucher;
    }
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-voucher_entry@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-voucher_entry@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

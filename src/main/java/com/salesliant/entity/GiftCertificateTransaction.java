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
 * <p>Title: GiftCertificateTransaction</p>
 *
 * <p>Description: Domain Object describing a GiftCertificateTransaction entity</p>
 *
 */
@Entity (name="GiftCertificateTransaction")
@Table (name="gift_certificate_transaction")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class GiftCertificateTransaction implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-amount@
    @Column(name="amount"   , nullable=false , unique=false)
    private java.math.BigDecimal amount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_updated-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_updated-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_updated@
    @Column(name="date_updated"   , nullable=false , unique=false)
    private Timestamp dateUpdated; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="gift_certificate_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private GiftCertificate giftCertificate;  

    @Column(name="gift_certificate_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer giftCertificateId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Invoice invoice;  

    @Column(name="invoice_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="sales_order_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private SalesOrder salesOrder;  

    @Column(name="sales_order_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer salesOrderId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=false , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    /**
    * Default constructor
    */
    public GiftCertificateTransaction() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-amount@
    public java.math.BigDecimal getAmount() {
        return amount;
    }
	
    public void setAmount (java.math.BigDecimal amount) {
        this.amount =  amount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_updated@
    public Timestamp getDateUpdated() {
        return dateUpdated;
    }
	
    public void setDateUpdated (Timestamp dateUpdated) {
        this.dateUpdated =  dateUpdated;
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


    public GiftCertificate getGiftCertificate () {
    	return giftCertificate;
    }
	
    public void setGiftCertificate (GiftCertificate giftCertificate) {
    	this.giftCertificate = giftCertificate;
    }

    public Integer getGiftCertificateId() {
        return giftCertificateId;
    }
	
    public void setGiftCertificateId (Integer giftCertificate) {
        this.giftCertificateId =  giftCertificate;
    }
	
    public Invoice getInvoice () {
    	return invoice;
    }
	
    public void setInvoice (Invoice invoice) {
    	this.invoice = invoice;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }
	
    public void setInvoiceId (Integer invoice) {
        this.invoiceId =  invoice;
    }
	
    public SalesOrder getSalesOrder () {
    	return salesOrder;
    }
	
    public void setSalesOrder (SalesOrder salesOrder) {
    	this.salesOrder = salesOrder;
    }

    public Integer getSalesOrderId() {
        return salesOrderId;
    }
	
    public void setSalesOrderId (Integer salesOrder) {
        this.salesOrderId =  salesOrder;
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
	


//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-gift_certificate_transaction@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-gift_certificate_transaction@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

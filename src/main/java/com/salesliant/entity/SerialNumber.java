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
 * <p>Title: SerialNumber</p>
 *
 * <p>Description: Domain Object describing a SerialNumber entity</p>
 *
 */
@Entity (name="SerialNumber")
@Table (name="serial_number")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class SerialNumber implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @serial_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @serial_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-serial_number@
    @Column(name="serial_number"  , length=32 , nullable=false , unique=true)
    private String serialNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @description-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @description-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-description@
    @Column(name="description"  , length=128 , nullable=true , unique=false)
    private String description; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sold-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sold-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sold@
    @Column(name="sold"   , nullable=true , unique=false)
    private Boolean sold; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @date_sold-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @date_sold-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-date_sold@
    @Column(name="date_sold"   , nullable=true , unique=false)
    private Date dateSold; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_number@
    @Column(name="invoice_number"   , nullable=true , unique=false)
    private Integer invoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @warranty_expire_labor-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @warranty_expire_labor-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-warranty_expire_labor@
    @Column(name="warranty_expire_labor"   , nullable=true , unique=false)
    private Date warrantyExpireLabor; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @warranty_expire_part-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @warranty_expire_part-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-warranty_expire_part@
    @Column(name="warranty_expire_part"   , nullable=true , unique=false)
    private Date warrantyExpirePart; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=true , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="invoice_entry_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private InvoiceEntry invoiceEntry;  

    @Column(name="invoice_entry_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer invoiceEntryId;

    @ManyToOne (fetch=FetchType.LAZY , optional=false)
    @JoinColumn(name="item_id", referencedColumnName = "id" , nullable=false , unique=false , insertable=true, updatable=true) 
    private Item item;  

    @Column(name="item_id"  , nullable=false , unique=true, insertable=false, updatable=false)
    private Integer itemId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="purchase_order_entry_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private PurchaseOrderEntry purchaseOrderEntry;  

    @Column(name="purchase_order_entry_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer purchaseOrderEntryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="purchase_order_history_entry_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private PurchaseOrderHistoryEntry purchaseOrderHistoryEntry;  

    @Column(name="purchase_order_history_entry_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer purchaseOrderHistoryEntryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="sales_order_entry_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private SalesOrderEntry salesOrderEntry;  

    @Column(name="sales_order_entry_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer salesOrderEntryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="store_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Store store;  

    @Column(name="store_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer storeId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="transfer_order_entry_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TransferOrderEntry transferOrderEntry;  

    @Column(name="transfer_order_entry_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer transferOrderEntryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="transfer_order_history_entry_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TransferOrderHistoryEntry transferOrderHistoryEntry;  

    @Column(name="transfer_order_history_entry_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer transferOrderHistoryEntryId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @services-field-serial_number@
    @OneToMany (targetEntity=com.salesliant.entity.Service.class, fetch=FetchType.LAZY, mappedBy="serialNumber", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Service> services = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public SerialNumber() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-serial_number@
    public String getSerialNumber() {
        return serialNumber;
    }
	
    public void setSerialNumber (String serialNumber) {
        this.serialNumber =  serialNumber;
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

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sold@
    public Boolean getSold() {
        return sold;
    }
	
    public void setSold (Boolean sold) {
        this.sold =  sold;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-date_sold@
    public Date getDateSold() {
        return dateSold;
    }
	
    public void setDateSold (Date dateSold) {
        this.dateSold =  dateSold;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_number@
    public Integer getInvoiceNumber() {
        return invoiceNumber;
    }
	
    public void setInvoiceNumber (Integer invoiceNumber) {
        this.invoiceNumber =  invoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-warranty_expire_labor@
    public Date getWarrantyExpireLabor() {
        return warrantyExpireLabor;
    }
	
    public void setWarrantyExpireLabor (Date warrantyExpireLabor) {
        this.warrantyExpireLabor =  warrantyExpireLabor;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-warranty_expire_part@
    public Date getWarrantyExpirePart() {
        return warrantyExpirePart;
    }
	
    public void setWarrantyExpirePart (Date warrantyExpirePart) {
        this.warrantyExpirePart =  warrantyExpirePart;
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


    public InvoiceEntry getInvoiceEntry () {
    	return invoiceEntry;
    }
	
    public void setInvoiceEntry (InvoiceEntry invoiceEntry) {
    	this.invoiceEntry = invoiceEntry;
    }

    public Integer getInvoiceEntryId() {
        return invoiceEntryId;
    }
	
    public void setInvoiceEntryId (Integer invoiceEntry) {
        this.invoiceEntryId =  invoiceEntry;
    }
	
    public Item getItem () {
    	return item;
    }
	
    public void setItem (Item item) {
    	this.item = item;
    }

    public Integer getItemId() {
        return itemId;
    }
	
    public void setItemId (Integer item) {
        this.itemId =  item;
    }
	
    public PurchaseOrderEntry getPurchaseOrderEntry () {
    	return purchaseOrderEntry;
    }
	
    public void setPurchaseOrderEntry (PurchaseOrderEntry purchaseOrderEntry) {
    	this.purchaseOrderEntry = purchaseOrderEntry;
    }

    public Integer getPurchaseOrderEntryId() {
        return purchaseOrderEntryId;
    }
	
    public void setPurchaseOrderEntryId (Integer purchaseOrderEntry) {
        this.purchaseOrderEntryId =  purchaseOrderEntry;
    }
	
    public PurchaseOrderHistoryEntry getPurchaseOrderHistoryEntry () {
    	return purchaseOrderHistoryEntry;
    }
	
    public void setPurchaseOrderHistoryEntry (PurchaseOrderHistoryEntry purchaseOrderHistoryEntry) {
    	this.purchaseOrderHistoryEntry = purchaseOrderHistoryEntry;
    }

    public Integer getPurchaseOrderHistoryEntryId() {
        return purchaseOrderHistoryEntryId;
    }
	
    public void setPurchaseOrderHistoryEntryId (Integer purchaseOrderHistoryEntry) {
        this.purchaseOrderHistoryEntryId =  purchaseOrderHistoryEntry;
    }
	
    public SalesOrderEntry getSalesOrderEntry () {
    	return salesOrderEntry;
    }
	
    public void setSalesOrderEntry (SalesOrderEntry salesOrderEntry) {
    	this.salesOrderEntry = salesOrderEntry;
    }

    public Integer getSalesOrderEntryId() {
        return salesOrderEntryId;
    }
	
    public void setSalesOrderEntryId (Integer salesOrderEntry) {
        this.salesOrderEntryId =  salesOrderEntry;
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
	
    public TransferOrderEntry getTransferOrderEntry () {
    	return transferOrderEntry;
    }
	
    public void setTransferOrderEntry (TransferOrderEntry transferOrderEntry) {
    	this.transferOrderEntry = transferOrderEntry;
    }

    public Integer getTransferOrderEntryId() {
        return transferOrderEntryId;
    }
	
    public void setTransferOrderEntryId (Integer transferOrderEntry) {
        this.transferOrderEntryId =  transferOrderEntry;
    }
	
    public TransferOrderHistoryEntry getTransferOrderHistoryEntry () {
    	return transferOrderHistoryEntry;
    }
	
    public void setTransferOrderHistoryEntry (TransferOrderHistoryEntry transferOrderHistoryEntry) {
    	this.transferOrderHistoryEntry = transferOrderHistoryEntry;
    }

    public Integer getTransferOrderHistoryEntryId() {
        return transferOrderHistoryEntryId;
    }
	
    public void setTransferOrderHistoryEntryId (Integer transferOrderHistoryEntry) {
        this.transferOrderHistoryEntryId =  transferOrderHistoryEntry;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @services-getter-serial_number@
    public List<Service> getServices() {
        if (services == null){
            services = new ArrayList<>();
        }
        return services;
    }

    public void setServices (List<Service> services) {
        this.services = services;
    }	
    
    public void addServices (Service element) {
    	    getServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-serial_number@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-serial_number@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

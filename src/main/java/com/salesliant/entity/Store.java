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
 * <p>Title: Store</p>
 *
 * <p>Description: Domain Object describing a Store entity</p>
 *
 */
@Entity (name="Store")
@Table (name="store")
//MP-MANAGED-ADDED-AREA-BEGINNING @custom-annotations@
//MP-MANAGED-ADDED-AREA-ENDING @custom-annotations@
public class Store implements Serializable {
    private static final long serialVersionUID = 1L;
	public static final Integer __DEFAULT_TRANSFER_DAY = Integer.valueOf(0);
	public static final Integer __DEFAULT_NEXT_INVOICE_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_ORDER_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_SERVICE_ORDER_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_QUOTE_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_CUSTOMER_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_PURCHASE_ORDER_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_BARCODE_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_BATCH_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_TRANSFER_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_ACCOUNT_RECEIVABLE_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_ACCOUNT_PAYABLE_BATCH_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NEXT_RMA_NUMBER = Integer.valueOf(1);
	public static final Integer __DEFAULT_NUMBER_OF_LABELS_PER_ITEM = Integer.valueOf(1);
	public static final Integer __DEFAULT_DEFAULT_ITEM_COST_METHOD = Integer.valueOf(1);
	public static final Integer __DEFAULT_DEFAULT_CUSTOMER_PRICE_METHOD = Integer.valueOf(1);
	public static final Integer __DEFAULT_ORDER_DUE_DAYS = Integer.valueOf(30);
	public static final Integer __DEFAULT_QUOTE_EXPIRATION_DAYS = Integer.valueOf(30);
	public static final Integer __DEFAULT_SERVICE_ORDER_DUE_DAYS = Integer.valueOf(14);
	public static final Integer __DEFAULT_INTERNET_ORDER_DUE_DAYS = Integer.valueOf(14);
	public static final Integer __DEFAULT_LAYAWAY_EXPIRATION_DAYS = Integer.valueOf(0);
	public static final Integer __DEFAULT_INVOICE_COUNT = Integer.valueOf(1);
	public static final Integer __DEFAULT_ORDER_COUNT = Integer.valueOf(1);
	public static final Integer __DEFAULT_QUOTE_COUNT = Integer.valueOf(1);
	public static final Integer __DEFAULT_SERVICE_COUNT = Integer.valueOf(1);
	public static final Integer __DEFAULT_VERSION = Integer.valueOf(0);

	
    @Id @Column(name="id" ) 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//MP-MANAGED-ADDED-AREA-BEGINNING @store_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @store_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-store_code@
    @Column(name="store_code"  , length=30 , nullable=false , unique=true)
    private String storeCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @store_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @store_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-store_name@
    @Column(name="store_name"  , length=64 , nullable=false , unique=false)
    private String storeName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @region-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @region-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-region@
    @Column(name="region"  , length=50 , nullable=true , unique=false)
    private String region; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @transfer_day-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @transfer_day-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-transfer_day@
    @Column(name="transfer_day"   , nullable=true , unique=false)
    private Integer transferDay; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @address1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @address1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-address1@
    @Column(name="address1"  , length=128 , nullable=true , unique=false)
    private String address1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @address2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @address2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-address2@
    @Column(name="address2"  , length=128 , nullable=true , unique=false)
    private String address2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @city-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @city-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-city@
    @Column(name="city"  , length=64 , nullable=false , unique=false)
    private String city; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @state-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @state-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-state@
    @Column(name="state"  , length=32 , nullable=false , unique=false)
    private String state; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @post_code-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @post_code-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-post_code@
    @Column(name="post_code"  , length=16 , nullable=false , unique=false)
    private String postCode; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @fax_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @fax_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-fax_number@
    @Column(name="fax_number"  , length=30 , nullable=true , unique=false)
    private String faxNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @phone_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @phone_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-phone_number@
    @Column(name="phone_number"  , length=32 , nullable=true , unique=false)
    private String phoneNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @web_address-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @web_address-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-web_address@
    @Column(name="web_address"  , length=255 , nullable=true , unique=false)
    private String webAddress; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @parent_storeid-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @parent_storeid-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-parent_storeid@
    @Column(name="parent_storeid"   , nullable=true , unique=false)
    private Integer parentStore; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_invoice_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_invoice_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_invoice_number@
    @Column(name="next_invoice_number"   , nullable=true , unique=false)
    private Integer nextInvoiceNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_order_number@
    @Column(name="next_order_number"   , nullable=true , unique=false)
    private Integer nextOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_service_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_service_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_service_order_number@
    @Column(name="next_service_order_number"   , nullable=true , unique=false)
    private Integer nextServiceOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_quote_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_quote_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_quote_number@
    @Column(name="next_quote_number"   , nullable=true , unique=false)
    private Integer nextQuoteNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_customer_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_customer_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_customer_number@
    @Column(name="next_customer_number"   , nullable=true , unique=false)
    private Integer nextCustomerNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_purchase_order_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_purchase_order_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_purchase_order_number@
    @Column(name="next_purchase_order_number"   , nullable=true , unique=false)
    private Integer nextPurchaseOrderNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_barcode_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_barcode_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_barcode_number@
    @Column(name="next_barcode_number"   , nullable=true , unique=false)
    private Integer nextBarcodeNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_batch_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_batch_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_batch_number@
    @Column(name="next_batch_number"   , nullable=true , unique=false)
    private Integer nextBatchNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_transfer_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_transfer_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_transfer_number@
    @Column(name="next_transfer_number"   , nullable=true , unique=false)
    private Integer nextTransferNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_account_receivable_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_account_receivable_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_account_receivable_number@
    @Column(name="next_account_receivable_number"   , nullable=true , unique=false)
    private Integer nextAccountReceivableNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_account_payable_batch_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_account_payable_batch_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_account_payable_batch_number@
    @Column(name="next_account_payable_batch_number"   , nullable=true , unique=false)
    private Integer nextAccountPayableBatchNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @next_rma_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @next_rma_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-next_rma_number@
    @Column(name="next_rma_number"   , nullable=true , unique=false)
    private Integer nextRmaNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @number_of_labels_per_item-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @number_of_labels_per_item-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-number_of_labels_per_item@
    @Column(name="number_of_labels_per_item"   , nullable=true , unique=false)
    private Integer numberOfLabelsPerItem; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @default_item_cost_method-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @default_item_cost_method-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-default_item_cost_method@
    @Column(name="default_item_cost_method"   , nullable=true , unique=false)
    private Integer defaultItemCostMethod; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @default_customer_price_method-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @default_customer_price_method-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-default_customer_price_method@
    @Column(name="default_customer_price_method"   , nullable=true , unique=false)
    private Integer defaultCustomerPriceMethod; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @default_customer_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @default_customer_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-default_customer_id@
    @Column(name="default_customer_id"   , nullable=true , unique=false)
    private Integer defaultCustomer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @order_due_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @order_due_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-order_due_days@
    @Column(name="order_due_days"   , nullable=true , unique=false)
    private Integer orderDueDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quote_expiration_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quote_expiration_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quote_expiration_days@
    @Column(name="quote_expiration_days"   , nullable=true , unique=false)
    private Integer quoteExpirationDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @service_order_due_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @service_order_due_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-service_order_due_days@
    @Column(name="service_order_due_days"   , nullable=true , unique=false)
    private Integer serviceOrderDueDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @internet_order_due_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @internet_order_due_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-internet_order_due_days@
    @Column(name="internet_order_due_days"   , nullable=true , unique=false)
    private Integer internetOrderDueDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @layaway_expiration_days-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @layaway_expiration_days-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-layaway_expiration_days@
    @Column(name="layaway_expiration_days"   , nullable=true , unique=false)
    private Integer layawayExpirationDays; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @order_deposit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @order_deposit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-order_deposit@
    @Column(name="order_deposit"   , nullable=true , unique=false)
    private java.math.BigDecimal orderDeposit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @layaway_deposit-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @layaway_deposit-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-layaway_deposit@
    @Column(name="layaway_deposit"   , nullable=true , unique=false)
    private java.math.BigDecimal layawayDeposit; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @layaway_fee-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @layaway_fee-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-layaway_fee@
    @Column(name="layaway_fee"   , nullable=true , unique=false)
    private java.math.BigDecimal layawayFee; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_count@
    @Column(name="invoice_count"   , nullable=true , unique=false)
    private Integer invoiceCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @order_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @order_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-order_count@
    @Column(name="order_count"   , nullable=true , unique=false)
    private Integer orderCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @quote_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @quote_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-quote_count@
    @Column(name="quote_count"   , nullable=true , unique=false)
    private Integer quoteCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @service_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @service_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-service_count@
    @Column(name="service_count"   , nullable=true , unique=false)
    private Integer serviceCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @sales_order_message-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @sales_order_message-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-sales_order_message@
    @Column(name="sales_order_message"  , length=65535 , nullable=true , unique=false)
    private String salesOrderMessage; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @service_order_message-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @service_order_message-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-service_order_message@
    @Column(name="service_order_message"  , length=65535 , nullable=true , unique=false)
    private String serviceOrderMessage; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @invoice_message-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @invoice_message-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-invoice_message@
    @Column(name="invoice_message"  , length=65535 , nullable=true , unique=false)
    private String invoiceMessage; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @allow_zero_qty_sale-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @allow_zero_qty_sale-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-allow_zero_qty_sale@
    @Column(name="allow_zero_qty_sale"   , nullable=false , unique=false)
    private Boolean allowZeroQtySale; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @require_serial_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @require_serial_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-require_serial_number@
    @Column(name="require_serial_number"   , nullable=true , unique=false)
    private Boolean requireSerialNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @display_out_of_stock-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @display_out_of_stock-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-display_out_of_stock@
    @Column(name="display_out_of_stock"   , nullable=true , unique=false)
    private Boolean displayOutOfStock; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @auto_customer_number_generation-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @auto_customer_number_generation-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-auto_customer_number_generation@
    @Column(name="auto_customer_number_generation"   , nullable=true , unique=false)
    private Boolean autoCustomerNumberGeneration; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @auto_sku_generation-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @auto_sku_generation-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-auto_sku_generation@
    @Column(name="auto_sku_generation"   , nullable=true , unique=false)
    private Boolean autoSkuGeneration; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @enforce_open_close_amount-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @enforce_open_close_amount-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-enforce_open_close_amount@
    @Column(name="enforce_open_close_amount"   , nullable=true , unique=false)
    private Boolean enforceOpenCloseAmount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @enable_back_orders-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @enable_back_orders-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-enable_back_orders@
    @Column(name="enable_back_orders"   , nullable=true , unique=false)
    private Boolean enableBackOrders; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @show_function_keys_at_pos-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @show_function_keys_at_pos-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-show_function_keys_at_pos@
    @Column(name="show_function_keys_at_pos"   , nullable=true , unique=false)
    private Boolean showFunctionKeysAtPos; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @show_address_at_pos-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @show_address_at_pos-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-show_address_at_pos@
    @Column(name="show_address_at_pos"   , nullable=true , unique=false)
    private Boolean showAddressAtPos; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @edc_time_out-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @edc_time_out-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-edc_time_out@
    @Column(name="edc_time_out"   , nullable=true , unique=false)
    private Boolean edcTimeOut; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @default_global_customer-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @default_global_customer-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-default_global_customer@
    @Column(name="default_global_customer"   , nullable=true , unique=false)
    private Boolean defaultGlobalCustomer; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @serial_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @serial_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-serial_number@
    @Column(name="serial_number"  , length=64 , nullable=true , unique=false)
    private String serialNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @registration_number-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @registration_number-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-registration_number@
    @Column(name="registration_number"  , length=32 , nullable=true , unique=false)
    private String registrationNumber; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @master_creation_date-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @master_creation_date-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-master_creation_date@
    @Column(name="master_creation_date"   , nullable=true , unique=false)
    private Date masterCreationDate; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @zone_id-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @zone_id-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-zone_id@
    @Column(name="zone_id"   , nullable=true , unique=false)
    private Integer zone; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask1-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask1-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask1@
    @Column(name="schedule_hour_mask1"   , nullable=true , unique=false)
    private Integer scheduleHourMask1; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask2-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask2-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask2@
    @Column(name="schedule_hour_mask2"   , nullable=true , unique=false)
    private Integer scheduleHourMask2; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask3-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask3-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask3@
    @Column(name="schedule_hour_mask3"   , nullable=true , unique=false)
    private Integer scheduleHourMask3; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask4-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask4-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask4@
    @Column(name="schedule_hour_mask4"   , nullable=true , unique=false)
    private Integer scheduleHourMask4; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask5-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask5-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask5@
    @Column(name="schedule_hour_mask5"   , nullable=true , unique=false)
    private Integer scheduleHourMask5; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask6-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask6-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask6@
    @Column(name="schedule_hour_mask6"   , nullable=true , unique=false)
    private Integer scheduleHourMask6; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_hour_mask7-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_hour_mask7-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_hour_mask7@
    @Column(name="schedule_hour_mask7"   , nullable=true , unique=false)
    private Integer scheduleHourMask7; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @schedule_minute-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @schedule_minute-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-schedule_minute@
    @Column(name="schedule_minute"   , nullable=true , unique=false)
    private Integer scheduleMinute; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @retry_count-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @retry_count-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-retry_count@
    @Column(name="retry_count"   , nullable=true , unique=false)
    private Integer retryCount; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @retry_delay-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @retry_delay-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-retry_delay@
    @Column(name="retry_delay"   , nullable=true , unique=false)
    private Integer retryDelay; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @account_name-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @account_name-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-account_name@
    @Column(name="account_name"  , length=50 , nullable=true , unique=false)
    private String accountName; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @password-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @password-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-password@
    @Column(name="password"  , length=40 , nullable=true , unique=false)
    private String password; 
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @version-field-annotation@
//MP-MANAGED-ADDED-AREA-ENDING @version-field-annotation@
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @ATTRIBUTE-version@
    @Column(name="version"   , nullable=false , unique=false)
	@Version
    private Integer version; 
//MP-MANAGED-UPDATABLE-ENDING

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="country", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Country country;  

    @Column(name="country"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer countryId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="default_currency_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private Currency defaultCurrency;  

    @Column(name="default_currency_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer defaultCurrencyId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="default_customer_term_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private CustomerTerm defaultCustomerTerm;  

    @Column(name="default_customer_term_id"  , nullable=true , unique=true, insertable=false, updatable=false)
    private Integer defaultCustomerTermId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="default_tax_class_id", referencedColumnName = "id" , nullable=true , unique=true  , insertable=true, updatable=true) 
    private TaxClass defaultTaxClass;  

    @Column(name="default_tax_class_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer defaultTaxClassId;

    @ManyToOne (fetch=FetchType.LAZY )
    @JoinColumn(name="default_tax_zone_id", referencedColumnName = "id" , nullable=true , unique=false , insertable=true, updatable=true) 
    private TaxZone defaultTaxZone;  

    @Column(name="default_tax_zone_id"  , nullable=true , unique=false, insertable=false, updatable=false)
    private Integer defaultTaxZoneId;

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayables-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.AccountPayable.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountPayable> accountPayables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayableBatchs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.AccountPayableBatch.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountPayableBatch> accountPayableBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayableHistories-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.AccountPayableHistory.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountPayableHistory> accountPayableHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.AccountReceivable.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <AccountReceivable> accountReceivables = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @appointments-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Appointment.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Appointment> appointments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Batch.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Batch> batchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @binLocations-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.BinLocation.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <BinLocation> binLocations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @cheques-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Cheque.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Cheque> cheques = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissions-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Commission.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Commission> commissions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Consignment.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Consignment> consignments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Customer.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Customer> customers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerBuyers-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerBuyer.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerBuyer> customerBuyers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerGroups-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerGroup.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerGroup> customerGroups = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerNotes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerNote.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerNote> customerNotes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.CustomerShipTo.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <CustomerShipTo> customerShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Deposit.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Deposit> deposits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @dropPayouts-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.DropPayout.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <DropPayout> dropPayouts = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employees-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Employee.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Employee> employees = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employeeGroups-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.EmployeeGroup.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <EmployeeGroup> employeeGroups = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @functionAccesses-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.FunctionAccess.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <FunctionAccess> functionAccesses = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificates-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificate.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificate> giftCertificates = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.GiftCertificateTransaction.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <GiftCertificateTransaction> giftCertificateTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoices-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Invoice.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Invoice> invoices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.InvoiceEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <InvoiceEntry> invoiceEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceRecurrings-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.InvoiceRecurring.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <InvoiceRecurring> invoiceRecurrings = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemBoms-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemBom.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemBom> itemBoms = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLabels-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLabel.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLabel> itemLabels = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLimits-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLimit.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLimit> itemLimits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLocations-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLocation.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLocation> itemLocations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLog.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLog> itemLogs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLots-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemLot.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemLot> itemLots = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemQuantities-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ItemQuantity.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ItemQuantity> itemQuantities = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kits-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Kit.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Kit> kits = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.KitEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <KitEntry> kitEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @mailGroups-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.MailGroup.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <MailGroup> mailGroups = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @merchantAuthorizations-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.MerchantAuthorization.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <MerchantAuthorization> merchantAuthorizations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @merchantBatchs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.MerchantBatch.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <MerchantBatch> merchantBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Payment.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Payment> payments = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentBatchs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.PaymentBatch.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PaymentBatch> paymentBatchs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentTypes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.PaymentType.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PaymentType> paymentTypes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @promotions-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Promotion.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Promotion> promotions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrder.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrder> purchaseOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderEntry.class, fetch=FetchType.LAZY, mappedBy="subStore", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderEntry> purchaseOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistory.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistory> purchaseOrderHistories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistoryEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.PurchaseOrderHistoryEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @quantityDiscounts-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.QuantityDiscount.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <QuantityDiscount> quantityDiscounts = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reasonCodes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReasonCode.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReasonCode> reasonCodes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @receipts-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Receipt.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Receipt> receipts = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reorderLists-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReorderList.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReorderList> reorderLists = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnCodes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnCode.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnCode> returnCodes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnEntry> returnEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrder.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrder> returnOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrderEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnOrderEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnOrderEntry> returnOrderEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ReturnTransaction.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ReturnTransaction> returnTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.SalesOrder.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SalesOrder> salesOrders = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @seqs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Seq.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Seq> seqs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.SerialNumber.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SerialNumber> serialNumbers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @services-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Service.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Service> services = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serviceCodes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ServiceCode.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ServiceCode> serviceCodes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serviceEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ServiceEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ServiceEntry> serviceEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippings-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Shipping.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Shipping> shippings = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippingCarriers-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ShippingCarrier.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ShippingCarrier> shippingCarriers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippingServices-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.ShippingService.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <ShippingService> shippingServices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @signatures-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Signature.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Signature> signatures = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Station.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Station> stations = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @subCategories-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.SubCategory.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <SubCategory> subCategories = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @substitutes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Substitute.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Substitute> substitutes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @taxRates-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TaxRate.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TaxRate> taxRates = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @timeCards-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TimeCard.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TimeCard> timeCards = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transactionLogs-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransactionLog.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransactionLog> transactionLogs = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderReceiveStores-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrder.class, fetch=FetchType.LAZY, mappedBy="receiveStore", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrder> transferOrderReceiveStores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderSendStores-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrder.class, fetch=FetchType.LAZY, mappedBy="sendStore", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrder> transferOrderSendStores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderStores-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrder.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrder> transferOrderStores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistoryReceiveStores-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderHistory.class, fetch=FetchType.LAZY, mappedBy="receiveStore", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrderHistory> transferOrderHistoryReceiveStores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistorySendStores-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderHistory.class, fetch=FetchType.LAZY, mappedBy="sendStore", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrderHistory> transferOrderHistorySendStores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistoryStores-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.TransferOrderHistory.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <TransferOrderHistory> transferOrderHistoryStores = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Vendor.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Vendor> vendors = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorItems-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VendorItem.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorItem> vendorItems = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorNotes-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VendorNote.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorNote> vendorNotes = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShipTos-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VendorShipTo.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorShipTo> vendorShipTos = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShippingServices-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VendorShippingService.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorShippingService> vendorShippingServices = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorTerms-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VendorTerm.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VendorTerm> vendorTerms = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voidedTransactions-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VoidedTransaction.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VoidedTransaction> voidedTransactions = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vouchers-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.Voucher.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <Voucher> vouchers = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voucherEntries-field-store@
    @OneToMany (targetEntity=com.salesliant.entity.VoucherEntry.class, fetch=FetchType.LAZY, mappedBy="store", cascade=CascadeType.REMOVE)//, cascade=CascadeType.ALL)
    private List <VoucherEntry> voucherEntries = new ArrayList<>(); 

//MP-MANAGED-UPDATABLE-ENDING
    /**
    * Default constructor
    */
    public Store() {
    }

    public Integer getId() {
        return id;
    }
	
    public void setId (Integer id) {
        this.id =  id;
    }
    
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-store_code@
    public String getStoreCode() {
        return storeCode;
    }
	
    public void setStoreCode (String storeCode) {
        this.storeCode =  storeCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-store_name@
    public String getStoreName() {
        return storeName;
    }
	
    public void setStoreName (String storeName) {
        this.storeName =  storeName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-region@
    public String getRegion() {
        return region;
    }
	
    public void setRegion (String region) {
        this.region =  region;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-transfer_day@
    public Integer getTransferDay() {
        return transferDay;
    }
	
    public void setTransferDay (Integer transferDay) {
        this.transferDay =  transferDay;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-address1@
    public String getAddress1() {
        return address1;
    }
	
    public void setAddress1 (String address1) {
        this.address1 =  address1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-address2@
    public String getAddress2() {
        return address2;
    }
	
    public void setAddress2 (String address2) {
        this.address2 =  address2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-city@
    public String getCity() {
        return city;
    }
	
    public void setCity (String city) {
        this.city =  city;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-state@
    public String getState() {
        return state;
    }
	
    public void setState (String state) {
        this.state =  state;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-post_code@
    public String getPostCode() {
        return postCode;
    }
	
    public void setPostCode (String postCode) {
        this.postCode =  postCode;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-fax_number@
    public String getFaxNumber() {
        return faxNumber;
    }
	
    public void setFaxNumber (String faxNumber) {
        this.faxNumber =  faxNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-phone_number@
    public String getPhoneNumber() {
        return phoneNumber;
    }
	
    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber =  phoneNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-web_address@
    public String getWebAddress() {
        return webAddress;
    }
	
    public void setWebAddress (String webAddress) {
        this.webAddress =  webAddress;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-parent_storeid@
    public Integer getParentStore() {
        return parentStore;
    }
	
    public void setParentStore (Integer parentStore) {
        this.parentStore =  parentStore;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_invoice_number@
    public Integer getNextInvoiceNumber() {
        return nextInvoiceNumber;
    }
	
    public void setNextInvoiceNumber (Integer nextInvoiceNumber) {
        this.nextInvoiceNumber =  nextInvoiceNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_order_number@
    public Integer getNextOrderNumber() {
        return nextOrderNumber;
    }
	
    public void setNextOrderNumber (Integer nextOrderNumber) {
        this.nextOrderNumber =  nextOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_service_order_number@
    public Integer getNextServiceOrderNumber() {
        return nextServiceOrderNumber;
    }
	
    public void setNextServiceOrderNumber (Integer nextServiceOrderNumber) {
        this.nextServiceOrderNumber =  nextServiceOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_quote_number@
    public Integer getNextQuoteNumber() {
        return nextQuoteNumber;
    }
	
    public void setNextQuoteNumber (Integer nextQuoteNumber) {
        this.nextQuoteNumber =  nextQuoteNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_customer_number@
    public Integer getNextCustomerNumber() {
        return nextCustomerNumber;
    }
	
    public void setNextCustomerNumber (Integer nextCustomerNumber) {
        this.nextCustomerNumber =  nextCustomerNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_purchase_order_number@
    public Integer getNextPurchaseOrderNumber() {
        return nextPurchaseOrderNumber;
    }
	
    public void setNextPurchaseOrderNumber (Integer nextPurchaseOrderNumber) {
        this.nextPurchaseOrderNumber =  nextPurchaseOrderNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_barcode_number@
    public Integer getNextBarcodeNumber() {
        return nextBarcodeNumber;
    }
	
    public void setNextBarcodeNumber (Integer nextBarcodeNumber) {
        this.nextBarcodeNumber =  nextBarcodeNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_batch_number@
    public Integer getNextBatchNumber() {
        return nextBatchNumber;
    }
	
    public void setNextBatchNumber (Integer nextBatchNumber) {
        this.nextBatchNumber =  nextBatchNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_transfer_number@
    public Integer getNextTransferNumber() {
        return nextTransferNumber;
    }
	
    public void setNextTransferNumber (Integer nextTransferNumber) {
        this.nextTransferNumber =  nextTransferNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_account_receivable_number@
    public Integer getNextAccountReceivableNumber() {
        return nextAccountReceivableNumber;
    }
	
    public void setNextAccountReceivableNumber (Integer nextAccountReceivableNumber) {
        this.nextAccountReceivableNumber =  nextAccountReceivableNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_account_payable_batch_number@
    public Integer getNextAccountPayableBatchNumber() {
        return nextAccountPayableBatchNumber;
    }
	
    public void setNextAccountPayableBatchNumber (Integer nextAccountPayableBatchNumber) {
        this.nextAccountPayableBatchNumber =  nextAccountPayableBatchNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-next_rma_number@
    public Integer getNextRmaNumber() {
        return nextRmaNumber;
    }
	
    public void setNextRmaNumber (Integer nextRmaNumber) {
        this.nextRmaNumber =  nextRmaNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-number_of_labels_per_item@
    public Integer getNumberOfLabelsPerItem() {
        return numberOfLabelsPerItem;
    }
	
    public void setNumberOfLabelsPerItem (Integer numberOfLabelsPerItem) {
        this.numberOfLabelsPerItem =  numberOfLabelsPerItem;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-default_item_cost_method@
    public Integer getDefaultItemCostMethod() {
        return defaultItemCostMethod;
    }
	
    public void setDefaultItemCostMethod (Integer defaultItemCostMethod) {
        this.defaultItemCostMethod =  defaultItemCostMethod;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-default_customer_price_method@
    public Integer getDefaultCustomerPriceMethod() {
        return defaultCustomerPriceMethod;
    }
	
    public void setDefaultCustomerPriceMethod (Integer defaultCustomerPriceMethod) {
        this.defaultCustomerPriceMethod =  defaultCustomerPriceMethod;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-default_customer_id@
    public Integer getDefaultCustomer() {
        return defaultCustomer;
    }
	
    public void setDefaultCustomer (Integer defaultCustomer) {
        this.defaultCustomer =  defaultCustomer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-order_due_days@
    public Integer getOrderDueDays() {
        return orderDueDays;
    }
	
    public void setOrderDueDays (Integer orderDueDays) {
        this.orderDueDays =  orderDueDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quote_expiration_days@
    public Integer getQuoteExpirationDays() {
        return quoteExpirationDays;
    }
	
    public void setQuoteExpirationDays (Integer quoteExpirationDays) {
        this.quoteExpirationDays =  quoteExpirationDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-service_order_due_days@
    public Integer getServiceOrderDueDays() {
        return serviceOrderDueDays;
    }
	
    public void setServiceOrderDueDays (Integer serviceOrderDueDays) {
        this.serviceOrderDueDays =  serviceOrderDueDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-internet_order_due_days@
    public Integer getInternetOrderDueDays() {
        return internetOrderDueDays;
    }
	
    public void setInternetOrderDueDays (Integer internetOrderDueDays) {
        this.internetOrderDueDays =  internetOrderDueDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-layaway_expiration_days@
    public Integer getLayawayExpirationDays() {
        return layawayExpirationDays;
    }
	
    public void setLayawayExpirationDays (Integer layawayExpirationDays) {
        this.layawayExpirationDays =  layawayExpirationDays;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-order_deposit@
    public java.math.BigDecimal getOrderDeposit() {
        return orderDeposit;
    }
	
    public void setOrderDeposit (java.math.BigDecimal orderDeposit) {
        this.orderDeposit =  orderDeposit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-layaway_deposit@
    public java.math.BigDecimal getLayawayDeposit() {
        return layawayDeposit;
    }
	
    public void setLayawayDeposit (java.math.BigDecimal layawayDeposit) {
        this.layawayDeposit =  layawayDeposit;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-layaway_fee@
    public java.math.BigDecimal getLayawayFee() {
        return layawayFee;
    }
	
    public void setLayawayFee (java.math.BigDecimal layawayFee) {
        this.layawayFee =  layawayFee;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_count@
    public Integer getInvoiceCount() {
        return invoiceCount;
    }
	
    public void setInvoiceCount (Integer invoiceCount) {
        this.invoiceCount =  invoiceCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-order_count@
    public Integer getOrderCount() {
        return orderCount;
    }
	
    public void setOrderCount (Integer orderCount) {
        this.orderCount =  orderCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-quote_count@
    public Integer getQuoteCount() {
        return quoteCount;
    }
	
    public void setQuoteCount (Integer quoteCount) {
        this.quoteCount =  quoteCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-service_count@
    public Integer getServiceCount() {
        return serviceCount;
    }
	
    public void setServiceCount (Integer serviceCount) {
        this.serviceCount =  serviceCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-sales_order_message@
    public String getSalesOrderMessage() {
        return salesOrderMessage;
    }
	
    public void setSalesOrderMessage (String salesOrderMessage) {
        this.salesOrderMessage =  salesOrderMessage;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-service_order_message@
    public String getServiceOrderMessage() {
        return serviceOrderMessage;
    }
	
    public void setServiceOrderMessage (String serviceOrderMessage) {
        this.serviceOrderMessage =  serviceOrderMessage;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-invoice_message@
    public String getInvoiceMessage() {
        return invoiceMessage;
    }
	
    public void setInvoiceMessage (String invoiceMessage) {
        this.invoiceMessage =  invoiceMessage;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-allow_zero_qty_sale@
    public Boolean getAllowZeroQtySale() {
        return allowZeroQtySale;
    }
	
    public void setAllowZeroQtySale (Boolean allowZeroQtySale) {
        this.allowZeroQtySale =  allowZeroQtySale;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-require_serial_number@
    public Boolean getRequireSerialNumber() {
        return requireSerialNumber;
    }
	
    public void setRequireSerialNumber (Boolean requireSerialNumber) {
        this.requireSerialNumber =  requireSerialNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-display_out_of_stock@
    public Boolean getDisplayOutOfStock() {
        return displayOutOfStock;
    }
	
    public void setDisplayOutOfStock (Boolean displayOutOfStock) {
        this.displayOutOfStock =  displayOutOfStock;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-auto_customer_number_generation@
    public Boolean getAutoCustomerNumberGeneration() {
        return autoCustomerNumberGeneration;
    }
	
    public void setAutoCustomerNumberGeneration (Boolean autoCustomerNumberGeneration) {
        this.autoCustomerNumberGeneration =  autoCustomerNumberGeneration;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-auto_sku_generation@
    public Boolean getAutoSkuGeneration() {
        return autoSkuGeneration;
    }
	
    public void setAutoSkuGeneration (Boolean autoSkuGeneration) {
        this.autoSkuGeneration =  autoSkuGeneration;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-enforce_open_close_amount@
    public Boolean getEnforceOpenCloseAmount() {
        return enforceOpenCloseAmount;
    }
	
    public void setEnforceOpenCloseAmount (Boolean enforceOpenCloseAmount) {
        this.enforceOpenCloseAmount =  enforceOpenCloseAmount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-enable_back_orders@
    public Boolean getEnableBackOrders() {
        return enableBackOrders;
    }
	
    public void setEnableBackOrders (Boolean enableBackOrders) {
        this.enableBackOrders =  enableBackOrders;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-show_function_keys_at_pos@
    public Boolean getShowFunctionKeysAtPos() {
        return showFunctionKeysAtPos;
    }
	
    public void setShowFunctionKeysAtPos (Boolean showFunctionKeysAtPos) {
        this.showFunctionKeysAtPos =  showFunctionKeysAtPos;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-show_address_at_pos@
    public Boolean getShowAddressAtPos() {
        return showAddressAtPos;
    }
	
    public void setShowAddressAtPos (Boolean showAddressAtPos) {
        this.showAddressAtPos =  showAddressAtPos;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-edc_time_out@
    public Boolean getEdcTimeOut() {
        return edcTimeOut;
    }
	
    public void setEdcTimeOut (Boolean edcTimeOut) {
        this.edcTimeOut =  edcTimeOut;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-default_global_customer@
    public Boolean getDefaultGlobalCustomer() {
        return defaultGlobalCustomer;
    }
	
    public void setDefaultGlobalCustomer (Boolean defaultGlobalCustomer) {
        this.defaultGlobalCustomer =  defaultGlobalCustomer;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-serial_number@
    public String getSerialNumber() {
        return serialNumber;
    }
	
    public void setSerialNumber (String serialNumber) {
        this.serialNumber =  serialNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-registration_number@
    public String getRegistrationNumber() {
        return registrationNumber;
    }
	
    public void setRegistrationNumber (String registrationNumber) {
        this.registrationNumber =  registrationNumber;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-master_creation_date@
    public Date getMasterCreationDate() {
        return masterCreationDate;
    }
	
    public void setMasterCreationDate (Date masterCreationDate) {
        this.masterCreationDate =  masterCreationDate;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-zone_id@
    public Integer getZone() {
        return zone;
    }
	
    public void setZone (Integer zone) {
        this.zone =  zone;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask1@
    public Integer getScheduleHourMask1() {
        return scheduleHourMask1;
    }
	
    public void setScheduleHourMask1 (Integer scheduleHourMask1) {
        this.scheduleHourMask1 =  scheduleHourMask1;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask2@
    public Integer getScheduleHourMask2() {
        return scheduleHourMask2;
    }
	
    public void setScheduleHourMask2 (Integer scheduleHourMask2) {
        this.scheduleHourMask2 =  scheduleHourMask2;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask3@
    public Integer getScheduleHourMask3() {
        return scheduleHourMask3;
    }
	
    public void setScheduleHourMask3 (Integer scheduleHourMask3) {
        this.scheduleHourMask3 =  scheduleHourMask3;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask4@
    public Integer getScheduleHourMask4() {
        return scheduleHourMask4;
    }
	
    public void setScheduleHourMask4 (Integer scheduleHourMask4) {
        this.scheduleHourMask4 =  scheduleHourMask4;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask5@
    public Integer getScheduleHourMask5() {
        return scheduleHourMask5;
    }
	
    public void setScheduleHourMask5 (Integer scheduleHourMask5) {
        this.scheduleHourMask5 =  scheduleHourMask5;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask6@
    public Integer getScheduleHourMask6() {
        return scheduleHourMask6;
    }
	
    public void setScheduleHourMask6 (Integer scheduleHourMask6) {
        this.scheduleHourMask6 =  scheduleHourMask6;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_hour_mask7@
    public Integer getScheduleHourMask7() {
        return scheduleHourMask7;
    }
	
    public void setScheduleHourMask7 (Integer scheduleHourMask7) {
        this.scheduleHourMask7 =  scheduleHourMask7;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-schedule_minute@
    public Integer getScheduleMinute() {
        return scheduleMinute;
    }
	
    public void setScheduleMinute (Integer scheduleMinute) {
        this.scheduleMinute =  scheduleMinute;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-retry_count@
    public Integer getRetryCount() {
        return retryCount;
    }
	
    public void setRetryCount (Integer retryCount) {
        this.retryCount =  retryCount;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-retry_delay@
    public Integer getRetryDelay() {
        return retryDelay;
    }
	
    public void setRetryDelay (Integer retryDelay) {
        this.retryDelay =  retryDelay;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-account_name@
    public String getAccountName() {
        return accountName;
    }
	
    public void setAccountName (String accountName) {
        this.accountName =  accountName;
    }
	
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @GETTER-SETTER-password@
    public String getPassword() {
        return password;
    }
	
    public void setPassword (String password) {
        this.password =  password;
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


    public Country getCountry () {
    	return country;
    }
	
    public void setCountry (Country country) {
    	this.country = country;
    }

    public Integer getCountryId() {
        return countryId;
    }
	
    public void setCountryId (Integer country) {
        this.countryId =  country;
    }
	
    public Currency getDefaultCurrency () {
    	return defaultCurrency;
    }
	
    public void setDefaultCurrency (Currency defaultCurrency) {
    	this.defaultCurrency = defaultCurrency;
    }

    public Integer getDefaultCurrencyId() {
        return defaultCurrencyId;
    }
	
    public void setDefaultCurrencyId (Integer defaultCurrency) {
        this.defaultCurrencyId =  defaultCurrency;
    }
	
    public CustomerTerm getDefaultCustomerTerm () {
    	return defaultCustomerTerm;
    }
	
    public void setDefaultCustomerTerm (CustomerTerm defaultCustomerTerm) {
    	this.defaultCustomerTerm = defaultCustomerTerm;
    }

    public Integer getDefaultCustomerTermId() {
        return defaultCustomerTermId;
    }
	
    public void setDefaultCustomerTermId (Integer defaultCustomerTerm) {
        this.defaultCustomerTermId =  defaultCustomerTerm;
    }
	
    public TaxClass getDefaultTaxClass () {
    	return defaultTaxClass;
    }
	
    public void setDefaultTaxClass (TaxClass defaultTaxClass) {
    	this.defaultTaxClass = defaultTaxClass;
    }

    public Integer getDefaultTaxClassId() {
        return defaultTaxClassId;
    }
	
    public void setDefaultTaxClassId (Integer defaultTaxClass) {
        this.defaultTaxClassId =  defaultTaxClass;
    }
	
    public TaxZone getDefaultTaxZone () {
    	return defaultTaxZone;
    }
	
    public void setDefaultTaxZone (TaxZone defaultTaxZone) {
    	this.defaultTaxZone = defaultTaxZone;
    }

    public Integer getDefaultTaxZoneId() {
        return defaultTaxZoneId;
    }
	
    public void setDefaultTaxZoneId (Integer defaultTaxZone) {
        this.defaultTaxZoneId =  defaultTaxZone;
    }
	

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayables-getter-store@
    public List<AccountPayable> getAccountPayables() {
        if (accountPayables == null){
            accountPayables = new ArrayList<>();
        }
        return accountPayables;
    }

    public void setAccountPayables (List<AccountPayable> accountPayables) {
        this.accountPayables = accountPayables;
    }	
    
    public void addAccountPayables (AccountPayable element) {
    	    getAccountPayables().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayableBatchs-getter-store@
    public List<AccountPayableBatch> getAccountPayableBatchs() {
        if (accountPayableBatchs == null){
            accountPayableBatchs = new ArrayList<>();
        }
        return accountPayableBatchs;
    }

    public void setAccountPayableBatchs (List<AccountPayableBatch> accountPayableBatchs) {
        this.accountPayableBatchs = accountPayableBatchs;
    }	
    
    public void addAccountPayableBatchs (AccountPayableBatch element) {
    	    getAccountPayableBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountPayableHistories-getter-store@
    public List<AccountPayableHistory> getAccountPayableHistories() {
        if (accountPayableHistories == null){
            accountPayableHistories = new ArrayList<>();
        }
        return accountPayableHistories;
    }

    public void setAccountPayableHistories (List<AccountPayableHistory> accountPayableHistories) {
        this.accountPayableHistories = accountPayableHistories;
    }	
    
    public void addAccountPayableHistories (AccountPayableHistory element) {
    	    getAccountPayableHistories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @accountReceivables-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @appointments-getter-store@
    public List<Appointment> getAppointments() {
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        return appointments;
    }

    public void setAppointments (List<Appointment> appointments) {
        this.appointments = appointments;
    }	
    
    public void addAppointments (Appointment element) {
    	    getAppointments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @batchs-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @binLocations-getter-store@
    public List<BinLocation> getBinLocations() {
        if (binLocations == null){
            binLocations = new ArrayList<>();
        }
        return binLocations;
    }

    public void setBinLocations (List<BinLocation> binLocations) {
        this.binLocations = binLocations;
    }	
    
    public void addBinLocations (BinLocation element) {
    	    getBinLocations().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @cheques-getter-store@
    public List<Cheque> getCheques() {
        if (cheques == null){
            cheques = new ArrayList<>();
        }
        return cheques;
    }

    public void setCheques (List<Cheque> cheques) {
        this.cheques = cheques;
    }	
    
    public void addCheques (Cheque element) {
    	    getCheques().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @commissions-getter-store@
    public List<Commission> getCommissions() {
        if (commissions == null){
            commissions = new ArrayList<>();
        }
        return commissions;
    }

    public void setCommissions (List<Commission> commissions) {
        this.commissions = commissions;
    }	
    
    public void addCommissions (Commission element) {
    	    getCommissions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @consignments-getter-store@
    public List<Consignment> getConsignments() {
        if (consignments == null){
            consignments = new ArrayList<>();
        }
        return consignments;
    }

    public void setConsignments (List<Consignment> consignments) {
        this.consignments = consignments;
    }	
    
    public void addConsignments (Consignment element) {
    	    getConsignments().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customers-getter-store@
    public List<Customer> getCustomers() {
        if (customers == null){
            customers = new ArrayList<>();
        }
        return customers;
    }

    public void setCustomers (List<Customer> customers) {
        this.customers = customers;
    }	
    
    public void addCustomers (Customer element) {
    	    getCustomers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerBuyers-getter-store@
    public List<CustomerBuyer> getCustomerBuyers() {
        if (customerBuyers == null){
            customerBuyers = new ArrayList<>();
        }
        return customerBuyers;
    }

    public void setCustomerBuyers (List<CustomerBuyer> customerBuyers) {
        this.customerBuyers = customerBuyers;
    }	
    
    public void addCustomerBuyers (CustomerBuyer element) {
    	    getCustomerBuyers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerGroups-getter-store@
    public List<CustomerGroup> getCustomerGroups() {
        if (customerGroups == null){
            customerGroups = new ArrayList<>();
        }
        return customerGroups;
    }

    public void setCustomerGroups (List<CustomerGroup> customerGroups) {
        this.customerGroups = customerGroups;
    }	
    
    public void addCustomerGroups (CustomerGroup element) {
    	    getCustomerGroups().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerNotes-getter-store@
    public List<CustomerNote> getCustomerNotes() {
        if (customerNotes == null){
            customerNotes = new ArrayList<>();
        }
        return customerNotes;
    }

    public void setCustomerNotes (List<CustomerNote> customerNotes) {
        this.customerNotes = customerNotes;
    }	
    
    public void addCustomerNotes (CustomerNote element) {
    	    getCustomerNotes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @customerShipTos-getter-store@
    public List<CustomerShipTo> getCustomerShipTos() {
        if (customerShipTos == null){
            customerShipTos = new ArrayList<>();
        }
        return customerShipTos;
    }

    public void setCustomerShipTos (List<CustomerShipTo> customerShipTos) {
        this.customerShipTos = customerShipTos;
    }	
    
    public void addCustomerShipTos (CustomerShipTo element) {
    	    getCustomerShipTos().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @deposits-getter-store@
    public List<Deposit> getDeposits() {
        if (deposits == null){
            deposits = new ArrayList<>();
        }
        return deposits;
    }

    public void setDeposits (List<Deposit> deposits) {
        this.deposits = deposits;
    }	
    
    public void addDeposits (Deposit element) {
    	    getDeposits().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @dropPayouts-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employees-getter-store@
    public List<Employee> getEmployees() {
        if (employees == null){
            employees = new ArrayList<>();
        }
        return employees;
    }

    public void setEmployees (List<Employee> employees) {
        this.employees = employees;
    }	
    
    public void addEmployees (Employee element) {
    	    getEmployees().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @employeeGroups-getter-store@
    public List<EmployeeGroup> getEmployeeGroups() {
        if (employeeGroups == null){
            employeeGroups = new ArrayList<>();
        }
        return employeeGroups;
    }

    public void setEmployeeGroups (List<EmployeeGroup> employeeGroups) {
        this.employeeGroups = employeeGroups;
    }	
    
    public void addEmployeeGroups (EmployeeGroup element) {
    	    getEmployeeGroups().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @functionAccesses-getter-store@
    public List<FunctionAccess> getFunctionAccesses() {
        if (functionAccesses == null){
            functionAccesses = new ArrayList<>();
        }
        return functionAccesses;
    }

    public void setFunctionAccesses (List<FunctionAccess> functionAccesses) {
        this.functionAccesses = functionAccesses;
    }	
    
    public void addFunctionAccesses (FunctionAccess element) {
    	    getFunctionAccesses().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificates-getter-store@
    public List<GiftCertificate> getGiftCertificates() {
        if (giftCertificates == null){
            giftCertificates = new ArrayList<>();
        }
        return giftCertificates;
    }

    public void setGiftCertificates (List<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }	
    
    public void addGiftCertificates (GiftCertificate element) {
    	    getGiftCertificates().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @giftCertificateTransactions-getter-store@
    public List<GiftCertificateTransaction> getGiftCertificateTransactions() {
        if (giftCertificateTransactions == null){
            giftCertificateTransactions = new ArrayList<>();
        }
        return giftCertificateTransactions;
    }

    public void setGiftCertificateTransactions (List<GiftCertificateTransaction> giftCertificateTransactions) {
        this.giftCertificateTransactions = giftCertificateTransactions;
    }	
    
    public void addGiftCertificateTransactions (GiftCertificateTransaction element) {
    	    getGiftCertificateTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoices-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceEntries-getter-store@
    public List<InvoiceEntry> getInvoiceEntries() {
        if (invoiceEntries == null){
            invoiceEntries = new ArrayList<>();
        }
        return invoiceEntries;
    }

    public void setInvoiceEntries (List<InvoiceEntry> invoiceEntries) {
        this.invoiceEntries = invoiceEntries;
    }	
    
    public void addInvoiceEntries (InvoiceEntry element) {
    	    getInvoiceEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @invoiceRecurrings-getter-store@
    public List<InvoiceRecurring> getInvoiceRecurrings() {
        if (invoiceRecurrings == null){
            invoiceRecurrings = new ArrayList<>();
        }
        return invoiceRecurrings;
    }

    public void setInvoiceRecurrings (List<InvoiceRecurring> invoiceRecurrings) {
        this.invoiceRecurrings = invoiceRecurrings;
    }	
    
    public void addInvoiceRecurrings (InvoiceRecurring element) {
    	    getInvoiceRecurrings().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemBoms-getter-store@
    public List<ItemBom> getItemBoms() {
        if (itemBoms == null){
            itemBoms = new ArrayList<>();
        }
        return itemBoms;
    }

    public void setItemBoms (List<ItemBom> itemBoms) {
        this.itemBoms = itemBoms;
    }	
    
    public void addItemBoms (ItemBom element) {
    	    getItemBoms().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLabels-getter-store@
    public List<ItemLabel> getItemLabels() {
        if (itemLabels == null){
            itemLabels = new ArrayList<>();
        }
        return itemLabels;
    }

    public void setItemLabels (List<ItemLabel> itemLabels) {
        this.itemLabels = itemLabels;
    }	
    
    public void addItemLabels (ItemLabel element) {
    	    getItemLabels().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLimits-getter-store@
    public List<ItemLimit> getItemLimits() {
        if (itemLimits == null){
            itemLimits = new ArrayList<>();
        }
        return itemLimits;
    }

    public void setItemLimits (List<ItemLimit> itemLimits) {
        this.itemLimits = itemLimits;
    }	
    
    public void addItemLimits (ItemLimit element) {
    	    getItemLimits().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLocations-getter-store@
    public List<ItemLocation> getItemLocations() {
        if (itemLocations == null){
            itemLocations = new ArrayList<>();
        }
        return itemLocations;
    }

    public void setItemLocations (List<ItemLocation> itemLocations) {
        this.itemLocations = itemLocations;
    }	
    
    public void addItemLocations (ItemLocation element) {
    	    getItemLocations().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLogs-getter-store@
    public List<ItemLog> getItemLogs() {
        if (itemLogs == null){
            itemLogs = new ArrayList<>();
        }
        return itemLogs;
    }

    public void setItemLogs (List<ItemLog> itemLogs) {
        this.itemLogs = itemLogs;
    }	
    
    public void addItemLogs (ItemLog element) {
    	    getItemLogs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemLots-getter-store@
    public List<ItemLot> getItemLots() {
        if (itemLots == null){
            itemLots = new ArrayList<>();
        }
        return itemLots;
    }

    public void setItemLots (List<ItemLot> itemLots) {
        this.itemLots = itemLots;
    }	
    
    public void addItemLots (ItemLot element) {
    	    getItemLots().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @itemQuantities-getter-store@
    public List<ItemQuantity> getItemQuantities() {
        if (itemQuantities == null){
            itemQuantities = new ArrayList<>();
        }
        return itemQuantities;
    }

    public void setItemQuantities (List<ItemQuantity> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }	
    
    public void addItemQuantities (ItemQuantity element) {
    	    getItemQuantities().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kits-getter-store@
    public List<Kit> getKits() {
        if (kits == null){
            kits = new ArrayList<>();
        }
        return kits;
    }

    public void setKits (List<Kit> kits) {
        this.kits = kits;
    }	
    
    public void addKits (Kit element) {
    	    getKits().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @kitEntries-getter-store@
    public List<KitEntry> getKitEntries() {
        if (kitEntries == null){
            kitEntries = new ArrayList<>();
        }
        return kitEntries;
    }

    public void setKitEntries (List<KitEntry> kitEntries) {
        this.kitEntries = kitEntries;
    }	
    
    public void addKitEntries (KitEntry element) {
    	    getKitEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @mailGroups-getter-store@
    public List<MailGroup> getMailGroups() {
        if (mailGroups == null){
            mailGroups = new ArrayList<>();
        }
        return mailGroups;
    }

    public void setMailGroups (List<MailGroup> mailGroups) {
        this.mailGroups = mailGroups;
    }	
    
    public void addMailGroups (MailGroup element) {
    	    getMailGroups().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @merchantAuthorizations-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @merchantBatchs-getter-store@
    public List<MerchantBatch> getMerchantBatchs() {
        if (merchantBatchs == null){
            merchantBatchs = new ArrayList<>();
        }
        return merchantBatchs;
    }

    public void setMerchantBatchs (List<MerchantBatch> merchantBatchs) {
        this.merchantBatchs = merchantBatchs;
    }	
    
    public void addMerchantBatchs (MerchantBatch element) {
    	    getMerchantBatchs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @payments-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentBatchs-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @paymentTypes-getter-store@
    public List<PaymentType> getPaymentTypes() {
        if (paymentTypes == null){
            paymentTypes = new ArrayList<>();
        }
        return paymentTypes;
    }

    public void setPaymentTypes (List<PaymentType> paymentTypes) {
        this.paymentTypes = paymentTypes;
    }	
    
    public void addPaymentTypes (PaymentType element) {
    	    getPaymentTypes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @promotions-getter-store@
    public List<Promotion> getPromotions() {
        if (promotions == null){
            promotions = new ArrayList<>();
        }
        return promotions;
    }

    public void setPromotions (List<Promotion> promotions) {
        this.promotions = promotions;
    }	
    
    public void addPromotions (Promotion element) {
    	    getPromotions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrders-getter-store@
    public List<PurchaseOrder> getPurchaseOrders() {
        if (purchaseOrders == null){
            purchaseOrders = new ArrayList<>();
        }
        return purchaseOrders;
    }

    public void setPurchaseOrders (List<PurchaseOrder> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }	
    
    public void addPurchaseOrders (PurchaseOrder element) {
    	    getPurchaseOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderEntries-getter-store@
    public List<PurchaseOrderEntry> getPurchaseOrderEntries() {
        if (purchaseOrderEntries == null){
            purchaseOrderEntries = new ArrayList<>();
        }
        return purchaseOrderEntries;
    }

    public void setPurchaseOrderEntries (List<PurchaseOrderEntry> purchaseOrderEntries) {
        this.purchaseOrderEntries = purchaseOrderEntries;
    }	
    
    public void addPurchaseOrderEntries (PurchaseOrderEntry element) {
    	    getPurchaseOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistories-getter-store@
    public List<PurchaseOrderHistory> getPurchaseOrderHistories() {
        if (purchaseOrderHistories == null){
            purchaseOrderHistories = new ArrayList<>();
        }
        return purchaseOrderHistories;
    }

    public void setPurchaseOrderHistories (List<PurchaseOrderHistory> purchaseOrderHistories) {
        this.purchaseOrderHistories = purchaseOrderHistories;
    }	
    
    public void addPurchaseOrderHistories (PurchaseOrderHistory element) {
    	    getPurchaseOrderHistories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @purchaseOrderHistoryEntries-getter-store@
    public List<PurchaseOrderHistoryEntry> getPurchaseOrderHistoryEntries() {
        if (purchaseOrderHistoryEntries == null){
            purchaseOrderHistoryEntries = new ArrayList<>();
        }
        return purchaseOrderHistoryEntries;
    }

    public void setPurchaseOrderHistoryEntries (List<PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries) {
        this.purchaseOrderHistoryEntries = purchaseOrderHistoryEntries;
    }	
    
    public void addPurchaseOrderHistoryEntries (PurchaseOrderHistoryEntry element) {
    	    getPurchaseOrderHistoryEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @quantityDiscounts-getter-store@
    public List<QuantityDiscount> getQuantityDiscounts() {
        if (quantityDiscounts == null){
            quantityDiscounts = new ArrayList<>();
        }
        return quantityDiscounts;
    }

    public void setQuantityDiscounts (List<QuantityDiscount> quantityDiscounts) {
        this.quantityDiscounts = quantityDiscounts;
    }	
    
    public void addQuantityDiscounts (QuantityDiscount element) {
    	    getQuantityDiscounts().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reasonCodes-getter-store@
    public List<ReasonCode> getReasonCodes() {
        if (reasonCodes == null){
            reasonCodes = new ArrayList<>();
        }
        return reasonCodes;
    }

    public void setReasonCodes (List<ReasonCode> reasonCodes) {
        this.reasonCodes = reasonCodes;
    }	
    
    public void addReasonCodes (ReasonCode element) {
    	    getReasonCodes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @receipts-getter-store@
    public List<Receipt> getReceipts() {
        if (receipts == null){
            receipts = new ArrayList<>();
        }
        return receipts;
    }

    public void setReceipts (List<Receipt> receipts) {
        this.receipts = receipts;
    }	
    
    public void addReceipts (Receipt element) {
    	    getReceipts().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @reorderLists-getter-store@
    public List<ReorderList> getReorderLists() {
        if (reorderLists == null){
            reorderLists = new ArrayList<>();
        }
        return reorderLists;
    }

    public void setReorderLists (List<ReorderList> reorderLists) {
        this.reorderLists = reorderLists;
    }	
    
    public void addReorderLists (ReorderList element) {
    	    getReorderLists().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnCodes-getter-store@
    public List<ReturnCode> getReturnCodes() {
        if (returnCodes == null){
            returnCodes = new ArrayList<>();
        }
        return returnCodes;
    }

    public void setReturnCodes (List<ReturnCode> returnCodes) {
        this.returnCodes = returnCodes;
    }	
    
    public void addReturnCodes (ReturnCode element) {
    	    getReturnCodes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnEntries-getter-store@
    public List<ReturnEntry> getReturnEntries() {
        if (returnEntries == null){
            returnEntries = new ArrayList<>();
        }
        return returnEntries;
    }

    public void setReturnEntries (List<ReturnEntry> returnEntries) {
        this.returnEntries = returnEntries;
    }	
    
    public void addReturnEntries (ReturnEntry element) {
    	    getReturnEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrders-getter-store@
    public List<ReturnOrder> getReturnOrders() {
        if (returnOrders == null){
            returnOrders = new ArrayList<>();
        }
        return returnOrders;
    }

    public void setReturnOrders (List<ReturnOrder> returnOrders) {
        this.returnOrders = returnOrders;
    }	
    
    public void addReturnOrders (ReturnOrder element) {
    	    getReturnOrders().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnOrderEntries-getter-store@
    public List<ReturnOrderEntry> getReturnOrderEntries() {
        if (returnOrderEntries == null){
            returnOrderEntries = new ArrayList<>();
        }
        return returnOrderEntries;
    }

    public void setReturnOrderEntries (List<ReturnOrderEntry> returnOrderEntries) {
        this.returnOrderEntries = returnOrderEntries;
    }	
    
    public void addReturnOrderEntries (ReturnOrderEntry element) {
    	    getReturnOrderEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @returnTransactions-getter-store@
    public List<ReturnTransaction> getReturnTransactions() {
        if (returnTransactions == null){
            returnTransactions = new ArrayList<>();
        }
        return returnTransactions;
    }

    public void setReturnTransactions (List<ReturnTransaction> returnTransactions) {
        this.returnTransactions = returnTransactions;
    }	
    
    public void addReturnTransactions (ReturnTransaction element) {
    	    getReturnTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @salesOrders-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @seqs-getter-store@
    public List<Seq> getSeqs() {
        if (seqs == null){
            seqs = new ArrayList<>();
        }
        return seqs;
    }

    public void setSeqs (List<Seq> seqs) {
        this.seqs = seqs;
    }	
    
    public void addSeqs (Seq element) {
    	    getSeqs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serialNumbers-getter-store@
    public List<SerialNumber> getSerialNumbers() {
        if (serialNumbers == null){
            serialNumbers = new ArrayList<>();
        }
        return serialNumbers;
    }

    public void setSerialNumbers (List<SerialNumber> serialNumbers) {
        this.serialNumbers = serialNumbers;
    }	
    
    public void addSerialNumbers (SerialNumber element) {
    	    getSerialNumbers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @services-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serviceCodes-getter-store@
    public List<ServiceCode> getServiceCodes() {
        if (serviceCodes == null){
            serviceCodes = new ArrayList<>();
        }
        return serviceCodes;
    }

    public void setServiceCodes (List<ServiceCode> serviceCodes) {
        this.serviceCodes = serviceCodes;
    }	
    
    public void addServiceCodes (ServiceCode element) {
    	    getServiceCodes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @serviceEntries-getter-store@
    public List<ServiceEntry> getServiceEntries() {
        if (serviceEntries == null){
            serviceEntries = new ArrayList<>();
        }
        return serviceEntries;
    }

    public void setServiceEntries (List<ServiceEntry> serviceEntries) {
        this.serviceEntries = serviceEntries;
    }	
    
    public void addServiceEntries (ServiceEntry element) {
    	    getServiceEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippings-getter-store@
    public List<Shipping> getShippings() {
        if (shippings == null){
            shippings = new ArrayList<>();
        }
        return shippings;
    }

    public void setShippings (List<Shipping> shippings) {
        this.shippings = shippings;
    }	
    
    public void addShippings (Shipping element) {
    	    getShippings().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippingCarriers-getter-store@
    public List<ShippingCarrier> getShippingCarriers() {
        if (shippingCarriers == null){
            shippingCarriers = new ArrayList<>();
        }
        return shippingCarriers;
    }

    public void setShippingCarriers (List<ShippingCarrier> shippingCarriers) {
        this.shippingCarriers = shippingCarriers;
    }	
    
    public void addShippingCarriers (ShippingCarrier element) {
    	    getShippingCarriers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @shippingServices-getter-store@
    public List<ShippingService> getShippingServices() {
        if (shippingServices == null){
            shippingServices = new ArrayList<>();
        }
        return shippingServices;
    }

    public void setShippingServices (List<ShippingService> shippingServices) {
        this.shippingServices = shippingServices;
    }	
    
    public void addShippingServices (ShippingService element) {
    	    getShippingServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @signatures-getter-store@
    public List<Signature> getSignatures() {
        if (signatures == null){
            signatures = new ArrayList<>();
        }
        return signatures;
    }

    public void setSignatures (List<Signature> signatures) {
        this.signatures = signatures;
    }	
    
    public void addSignatures (Signature element) {
    	    getSignatures().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @stations-getter-store@
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
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @subCategories-getter-store@
    public List<SubCategory> getSubCategories() {
        if (subCategories == null){
            subCategories = new ArrayList<>();
        }
        return subCategories;
    }

    public void setSubCategories (List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }	
    
    public void addSubCategories (SubCategory element) {
    	    getSubCategories().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @substitutes-getter-store@
    public List<Substitute> getSubstitutes() {
        if (substitutes == null){
            substitutes = new ArrayList<>();
        }
        return substitutes;
    }

    public void setSubstitutes (List<Substitute> substitutes) {
        this.substitutes = substitutes;
    }	
    
    public void addSubstitutes (Substitute element) {
    	    getSubstitutes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @taxRates-getter-store@
    public List<TaxRate> getTaxRates() {
        if (taxRates == null){
            taxRates = new ArrayList<>();
        }
        return taxRates;
    }

    public void setTaxRates (List<TaxRate> taxRates) {
        this.taxRates = taxRates;
    }	
    
    public void addTaxRates (TaxRate element) {
    	    getTaxRates().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @timeCards-getter-store@
    public List<TimeCard> getTimeCards() {
        if (timeCards == null){
            timeCards = new ArrayList<>();
        }
        return timeCards;
    }

    public void setTimeCards (List<TimeCard> timeCards) {
        this.timeCards = timeCards;
    }	
    
    public void addTimeCards (TimeCard element) {
    	    getTimeCards().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transactionLogs-getter-store@
    public List<TransactionLog> getTransactionLogs() {
        if (transactionLogs == null){
            transactionLogs = new ArrayList<>();
        }
        return transactionLogs;
    }

    public void setTransactionLogs (List<TransactionLog> transactionLogs) {
        this.transactionLogs = transactionLogs;
    }	
    
    public void addTransactionLogs (TransactionLog element) {
    	    getTransactionLogs().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderReceiveStores-getter-store@
    public List<TransferOrder> getTransferOrderReceiveStores() {
        if (transferOrderReceiveStores == null){
            transferOrderReceiveStores = new ArrayList<>();
        }
        return transferOrderReceiveStores;
    }

    public void setTransferOrderReceiveStores (List<TransferOrder> transferOrderReceiveStores) {
        this.transferOrderReceiveStores = transferOrderReceiveStores;
    }	
    
    public void addTransferOrderReceiveStores (TransferOrder element) {
    	    getTransferOrderReceiveStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderSendStores-getter-store@
    public List<TransferOrder> getTransferOrderSendStores() {
        if (transferOrderSendStores == null){
            transferOrderSendStores = new ArrayList<>();
        }
        return transferOrderSendStores;
    }

    public void setTransferOrderSendStores (List<TransferOrder> transferOrderSendStores) {
        this.transferOrderSendStores = transferOrderSendStores;
    }	
    
    public void addTransferOrderSendStores (TransferOrder element) {
    	    getTransferOrderSendStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderStores-getter-store@
    public List<TransferOrder> getTransferOrderStores() {
        if (transferOrderStores == null){
            transferOrderStores = new ArrayList<>();
        }
        return transferOrderStores;
    }

    public void setTransferOrderStores (List<TransferOrder> transferOrderStores) {
        this.transferOrderStores = transferOrderStores;
    }	
    
    public void addTransferOrderStores (TransferOrder element) {
    	    getTransferOrderStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistoryReceiveStores-getter-store@
    public List<TransferOrderHistory> getTransferOrderHistoryReceiveStores() {
        if (transferOrderHistoryReceiveStores == null){
            transferOrderHistoryReceiveStores = new ArrayList<>();
        }
        return transferOrderHistoryReceiveStores;
    }

    public void setTransferOrderHistoryReceiveStores (List<TransferOrderHistory> transferOrderHistoryReceiveStores) {
        this.transferOrderHistoryReceiveStores = transferOrderHistoryReceiveStores;
    }	
    
    public void addTransferOrderHistoryReceiveStores (TransferOrderHistory element) {
    	    getTransferOrderHistoryReceiveStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistorySendStores-getter-store@
    public List<TransferOrderHistory> getTransferOrderHistorySendStores() {
        if (transferOrderHistorySendStores == null){
            transferOrderHistorySendStores = new ArrayList<>();
        }
        return transferOrderHistorySendStores;
    }

    public void setTransferOrderHistorySendStores (List<TransferOrderHistory> transferOrderHistorySendStores) {
        this.transferOrderHistorySendStores = transferOrderHistorySendStores;
    }	
    
    public void addTransferOrderHistorySendStores (TransferOrderHistory element) {
    	    getTransferOrderHistorySendStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @transferOrderHistoryStores-getter-store@
    public List<TransferOrderHistory> getTransferOrderHistoryStores() {
        if (transferOrderHistoryStores == null){
            transferOrderHistoryStores = new ArrayList<>();
        }
        return transferOrderHistoryStores;
    }

    public void setTransferOrderHistoryStores (List<TransferOrderHistory> transferOrderHistoryStores) {
        this.transferOrderHistoryStores = transferOrderHistoryStores;
    }	
    
    public void addTransferOrderHistoryStores (TransferOrderHistory element) {
    	    getTransferOrderHistoryStores().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendors-getter-store@
    public List<Vendor> getVendors() {
        if (vendors == null){
            vendors = new ArrayList<>();
        }
        return vendors;
    }

    public void setVendors (List<Vendor> vendors) {
        this.vendors = vendors;
    }	
    
    public void addVendors (Vendor element) {
    	    getVendors().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorItems-getter-store@
    public List<VendorItem> getVendorItems() {
        if (vendorItems == null){
            vendorItems = new ArrayList<>();
        }
        return vendorItems;
    }

    public void setVendorItems (List<VendorItem> vendorItems) {
        this.vendorItems = vendorItems;
    }	
    
    public void addVendorItems (VendorItem element) {
    	    getVendorItems().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorNotes-getter-store@
    public List<VendorNote> getVendorNotes() {
        if (vendorNotes == null){
            vendorNotes = new ArrayList<>();
        }
        return vendorNotes;
    }

    public void setVendorNotes (List<VendorNote> vendorNotes) {
        this.vendorNotes = vendorNotes;
    }	
    
    public void addVendorNotes (VendorNote element) {
    	    getVendorNotes().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShipTos-getter-store@
    public List<VendorShipTo> getVendorShipTos() {
        if (vendorShipTos == null){
            vendorShipTos = new ArrayList<>();
        }
        return vendorShipTos;
    }

    public void setVendorShipTos (List<VendorShipTo> vendorShipTos) {
        this.vendorShipTos = vendorShipTos;
    }	
    
    public void addVendorShipTos (VendorShipTo element) {
    	    getVendorShipTos().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorShippingServices-getter-store@
    public List<VendorShippingService> getVendorShippingServices() {
        if (vendorShippingServices == null){
            vendorShippingServices = new ArrayList<>();
        }
        return vendorShippingServices;
    }

    public void setVendorShippingServices (List<VendorShippingService> vendorShippingServices) {
        this.vendorShippingServices = vendorShippingServices;
    }	
    
    public void addVendorShippingServices (VendorShippingService element) {
    	    getVendorShippingServices().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vendorTerms-getter-store@
    public List<VendorTerm> getVendorTerms() {
        if (vendorTerms == null){
            vendorTerms = new ArrayList<>();
        }
        return vendorTerms;
    }

    public void setVendorTerms (List<VendorTerm> vendorTerms) {
        this.vendorTerms = vendorTerms;
    }	
    
    public void addVendorTerms (VendorTerm element) {
    	    getVendorTerms().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voidedTransactions-getter-store@
    public List<VoidedTransaction> getVoidedTransactions() {
        if (voidedTransactions == null){
            voidedTransactions = new ArrayList<>();
        }
        return voidedTransactions;
    }

    public void setVoidedTransactions (List<VoidedTransaction> voidedTransactions) {
        this.voidedTransactions = voidedTransactions;
    }	
    
    public void addVoidedTransactions (VoidedTransaction element) {
    	    getVoidedTransactions().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @vouchers-getter-store@
    public List<Voucher> getVouchers() {
        if (vouchers == null){
            vouchers = new ArrayList<>();
        }
        return vouchers;
    }

    public void setVouchers (List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }	
    
    public void addVouchers (Voucher element) {
    	    getVouchers().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING
//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @voucherEntries-getter-store@
    public List<VoucherEntry> getVoucherEntries() {
        if (voucherEntries == null){
            voucherEntries = new ArrayList<>();
        }
        return voucherEntries;
    }

    public void setVoucherEntries (List<VoucherEntry> voucherEntries) {
        this.voucherEntries = voucherEntries;
    }	
    
    public void addVoucherEntries (VoucherEntry element) {
    	    getVoucherEntries().add(element);
    }
    
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @prepersist-store@
    @javax.persistence.PrePersist
    public void prePersist_ () {
        if (transferDay==null) transferDay=__DEFAULT_TRANSFER_DAY;
        if (nextInvoiceNumber==null) nextInvoiceNumber=__DEFAULT_NEXT_INVOICE_NUMBER;
        if (nextOrderNumber==null) nextOrderNumber=__DEFAULT_NEXT_ORDER_NUMBER;
        if (nextServiceOrderNumber==null) nextServiceOrderNumber=__DEFAULT_NEXT_SERVICE_ORDER_NUMBER;
        if (nextQuoteNumber==null) nextQuoteNumber=__DEFAULT_NEXT_QUOTE_NUMBER;
        if (nextCustomerNumber==null) nextCustomerNumber=__DEFAULT_NEXT_CUSTOMER_NUMBER;
        if (nextPurchaseOrderNumber==null) nextPurchaseOrderNumber=__DEFAULT_NEXT_PURCHASE_ORDER_NUMBER;
        if (nextBarcodeNumber==null) nextBarcodeNumber=__DEFAULT_NEXT_BARCODE_NUMBER;
        if (nextBatchNumber==null) nextBatchNumber=__DEFAULT_NEXT_BATCH_NUMBER;
        if (nextTransferNumber==null) nextTransferNumber=__DEFAULT_NEXT_TRANSFER_NUMBER;
        if (nextAccountReceivableNumber==null) nextAccountReceivableNumber=__DEFAULT_NEXT_ACCOUNT_RECEIVABLE_NUMBER;
        if (nextAccountPayableBatchNumber==null) nextAccountPayableBatchNumber=__DEFAULT_NEXT_ACCOUNT_PAYABLE_BATCH_NUMBER;
        if (nextRmaNumber==null) nextRmaNumber=__DEFAULT_NEXT_RMA_NUMBER;
        if (numberOfLabelsPerItem==null) numberOfLabelsPerItem=__DEFAULT_NUMBER_OF_LABELS_PER_ITEM;
        if (defaultItemCostMethod==null) defaultItemCostMethod=__DEFAULT_DEFAULT_ITEM_COST_METHOD;
        if (defaultCustomerPriceMethod==null) defaultCustomerPriceMethod=__DEFAULT_DEFAULT_CUSTOMER_PRICE_METHOD;
        if (orderDueDays==null) orderDueDays=__DEFAULT_ORDER_DUE_DAYS;
        if (quoteExpirationDays==null) quoteExpirationDays=__DEFAULT_QUOTE_EXPIRATION_DAYS;
        if (serviceOrderDueDays==null) serviceOrderDueDays=__DEFAULT_SERVICE_ORDER_DUE_DAYS;
        if (internetOrderDueDays==null) internetOrderDueDays=__DEFAULT_INTERNET_ORDER_DUE_DAYS;
        if (layawayExpirationDays==null) layawayExpirationDays=__DEFAULT_LAYAWAY_EXPIRATION_DAYS;
        if (invoiceCount==null) invoiceCount=__DEFAULT_INVOICE_COUNT;
        if (orderCount==null) orderCount=__DEFAULT_ORDER_COUNT;
        if (quoteCount==null) quoteCount=__DEFAULT_QUOTE_COUNT;
        if (serviceCount==null) serviceCount=__DEFAULT_SERVICE_COUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-UPDATABLE-BEGINNING-DISABLE @preupdate-store@
    @javax.persistence.PreUpdate
    public void preUpdate_ () {
        if (transferDay==null) transferDay=__DEFAULT_TRANSFER_DAY;
        if (nextInvoiceNumber==null) nextInvoiceNumber=__DEFAULT_NEXT_INVOICE_NUMBER;
        if (nextOrderNumber==null) nextOrderNumber=__DEFAULT_NEXT_ORDER_NUMBER;
        if (nextServiceOrderNumber==null) nextServiceOrderNumber=__DEFAULT_NEXT_SERVICE_ORDER_NUMBER;
        if (nextQuoteNumber==null) nextQuoteNumber=__DEFAULT_NEXT_QUOTE_NUMBER;
        if (nextCustomerNumber==null) nextCustomerNumber=__DEFAULT_NEXT_CUSTOMER_NUMBER;
        if (nextPurchaseOrderNumber==null) nextPurchaseOrderNumber=__DEFAULT_NEXT_PURCHASE_ORDER_NUMBER;
        if (nextBarcodeNumber==null) nextBarcodeNumber=__DEFAULT_NEXT_BARCODE_NUMBER;
        if (nextBatchNumber==null) nextBatchNumber=__DEFAULT_NEXT_BATCH_NUMBER;
        if (nextTransferNumber==null) nextTransferNumber=__DEFAULT_NEXT_TRANSFER_NUMBER;
        if (nextAccountReceivableNumber==null) nextAccountReceivableNumber=__DEFAULT_NEXT_ACCOUNT_RECEIVABLE_NUMBER;
        if (nextAccountPayableBatchNumber==null) nextAccountPayableBatchNumber=__DEFAULT_NEXT_ACCOUNT_PAYABLE_BATCH_NUMBER;
        if (nextRmaNumber==null) nextRmaNumber=__DEFAULT_NEXT_RMA_NUMBER;
        if (numberOfLabelsPerItem==null) numberOfLabelsPerItem=__DEFAULT_NUMBER_OF_LABELS_PER_ITEM;
        if (defaultItemCostMethod==null) defaultItemCostMethod=__DEFAULT_DEFAULT_ITEM_COST_METHOD;
        if (defaultCustomerPriceMethod==null) defaultCustomerPriceMethod=__DEFAULT_DEFAULT_CUSTOMER_PRICE_METHOD;
        if (orderDueDays==null) orderDueDays=__DEFAULT_ORDER_DUE_DAYS;
        if (quoteExpirationDays==null) quoteExpirationDays=__DEFAULT_QUOTE_EXPIRATION_DAYS;
        if (serviceOrderDueDays==null) serviceOrderDueDays=__DEFAULT_SERVICE_ORDER_DUE_DAYS;
        if (internetOrderDueDays==null) internetOrderDueDays=__DEFAULT_INTERNET_ORDER_DUE_DAYS;
        if (layawayExpirationDays==null) layawayExpirationDays=__DEFAULT_LAYAWAY_EXPIRATION_DAYS;
        if (invoiceCount==null) invoiceCount=__DEFAULT_INVOICE_COUNT;
        if (orderCount==null) orderCount=__DEFAULT_ORDER_COUNT;
        if (quoteCount==null) quoteCount=__DEFAULT_QUOTE_COUNT;
        if (serviceCount==null) serviceCount=__DEFAULT_SERVICE_COUNT;
        if (version==null) version=__DEFAULT_VERSION;
    }
//MP-MANAGED-UPDATABLE-ENDING

//MP-MANAGED-ADDED-AREA-BEGINNING @implementation@
//MP-MANAGED-ADDED-AREA-ENDING @implementation@

}

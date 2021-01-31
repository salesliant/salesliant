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

import com.salesliant.entity.AccountPayable;
import com.salesliant.entity.AccountPayableBatch;
import com.salesliant.entity.AccountPayableHistory;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Appointment;
import com.salesliant.entity.Batch;
import com.salesliant.entity.BinLocation;
import com.salesliant.entity.Cheque;
import com.salesliant.entity.Commission;
import com.salesliant.entity.Consignment;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerGroup;
import com.salesliant.entity.CustomerNote;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.Employee;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.FunctionAccess;
import com.salesliant.entity.GiftCertificate;
import com.salesliant.entity.GiftCertificateTransaction;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceRecurring;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.ItemLabel;
import com.salesliant.entity.ItemLimit;
import com.salesliant.entity.ItemLocation;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemLot;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Kit;
import com.salesliant.entity.KitEntry;
import com.salesliant.entity.MailGroup;
import com.salesliant.entity.MerchantAuthorization;
import com.salesliant.entity.MerchantBatch;
import com.salesliant.entity.Payment;
import com.salesliant.entity.PaymentBatch;
import com.salesliant.entity.PaymentType;
import com.salesliant.entity.Promotion;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.QuantityDiscount;
import com.salesliant.entity.ReasonCode;
import com.salesliant.entity.Receipt;
import com.salesliant.entity.ReorderList;
import com.salesliant.entity.ReturnCode;
import com.salesliant.entity.ReturnEntry;
import com.salesliant.entity.ReturnOrder;
import com.salesliant.entity.ReturnOrderEntry;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.Seq;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Service;
import com.salesliant.entity.ServiceCode;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.Shipping;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.ShippingService;
import com.salesliant.entity.Signature;
import com.salesliant.entity.Station;
import com.salesliant.entity.SubCategory;
import com.salesliant.entity.Substitute;
import com.salesliant.entity.TaxRate;
import com.salesliant.entity.TimeCard;
import com.salesliant.entity.TransactionLog;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrderHistory;
import com.salesliant.entity.TransferOrderHistory;
import com.salesliant.entity.TransferOrderHistory;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorNote;
import com.salesliant.entity.VendorShipTo;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.entity.VendorTerm;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.entity.Voucher;
import com.salesliant.entity.VoucherEntry;
import com.salesliant.entity.Country;
import com.salesliant.entity.Currency;
import com.salesliant.entity.CustomerTerm;
import com.salesliant.entity.TaxClass;
import com.salesliant.entity.TaxZone;

@StaticMetamodel(Store.class)
public class Store_ {

    public static volatile SingularAttribute<Store, Integer> id;

    public static volatile SingularAttribute<Store, String> storeCode;
    public static volatile SingularAttribute<Store, String> storeName;
    public static volatile SingularAttribute<Store, String> region;
    public static volatile SingularAttribute<Store, Integer> transferDay;
    public static volatile SingularAttribute<Store, String> address1;
    public static volatile SingularAttribute<Store, String> address2;
    public static volatile SingularAttribute<Store, String> city;
    public static volatile SingularAttribute<Store, String> state;
    public static volatile SingularAttribute<Store, String> postCode;
    public static volatile SingularAttribute<Store, String> faxNumber;
    public static volatile SingularAttribute<Store, String> phoneNumber;
    public static volatile SingularAttribute<Store, String> webAddress;
    public static volatile SingularAttribute<Store, Integer> parentStore;
    public static volatile SingularAttribute<Store, Integer> nextInvoiceNumber;
    public static volatile SingularAttribute<Store, Integer> nextOrderNumber;
    public static volatile SingularAttribute<Store, Integer> nextServiceOrderNumber;
    public static volatile SingularAttribute<Store, Integer> nextQuoteNumber;
    public static volatile SingularAttribute<Store, Integer> nextCustomerNumber;
    public static volatile SingularAttribute<Store, Integer> nextPurchaseOrderNumber;
    public static volatile SingularAttribute<Store, Integer> nextBarcodeNumber;
    public static volatile SingularAttribute<Store, Integer> nextBatchNumber;
    public static volatile SingularAttribute<Store, Integer> nextTransferNumber;
    public static volatile SingularAttribute<Store, Integer> nextAccountReceivableNumber;
    public static volatile SingularAttribute<Store, Integer> nextAccountPayableBatchNumber;
    public static volatile SingularAttribute<Store, Integer> nextRmaNumber;
    public static volatile SingularAttribute<Store, Integer> numberOfLabelsPerItem;
    public static volatile SingularAttribute<Store, Integer> defaultItemCostMethod;
    public static volatile SingularAttribute<Store, Integer> defaultCustomerPriceMethod;
    public static volatile SingularAttribute<Store, Integer> defaultCustomer;
    public static volatile SingularAttribute<Store, Integer> orderDueDays;
    public static volatile SingularAttribute<Store, Integer> quoteExpirationDays;
    public static volatile SingularAttribute<Store, Integer> serviceOrderDueDays;
    public static volatile SingularAttribute<Store, Integer> internetOrderDueDays;
    public static volatile SingularAttribute<Store, Integer> layawayExpirationDays;
    public static volatile SingularAttribute<Store, java.math.BigDecimal> orderDeposit;
    public static volatile SingularAttribute<Store, java.math.BigDecimal> layawayDeposit;
    public static volatile SingularAttribute<Store, java.math.BigDecimal> layawayFee;
    public static volatile SingularAttribute<Store, Integer> invoiceCount;
    public static volatile SingularAttribute<Store, Integer> orderCount;
    public static volatile SingularAttribute<Store, Integer> quoteCount;
    public static volatile SingularAttribute<Store, Integer> serviceCount;
    public static volatile SingularAttribute<Store, String> salesOrderMessage;
    public static volatile SingularAttribute<Store, String> serviceOrderMessage;
    public static volatile SingularAttribute<Store, String> invoiceMessage;
    public static volatile SingularAttribute<Store, Boolean> allowZeroQtySale;
    public static volatile SingularAttribute<Store, Boolean> requireSerialNumber;
    public static volatile SingularAttribute<Store, Boolean> displayOutOfStock;
    public static volatile SingularAttribute<Store, Boolean> autoCustomerNumberGeneration;
    public static volatile SingularAttribute<Store, Boolean> autoSkuGeneration;
    public static volatile SingularAttribute<Store, Boolean> enforceOpenCloseAmount;
    public static volatile SingularAttribute<Store, Boolean> enableBackOrders;
    public static volatile SingularAttribute<Store, Boolean> showFunctionKeysAtPos;
    public static volatile SingularAttribute<Store, Boolean> showAddressAtPos;
    public static volatile SingularAttribute<Store, Boolean> edcTimeOut;
    public static volatile SingularAttribute<Store, Boolean> defaultGlobalCustomer;
    public static volatile SingularAttribute<Store, String> serialNumber;
    public static volatile SingularAttribute<Store, String> registrationNumber;
    public static volatile SingularAttribute<Store, Date> masterCreationDate;
    public static volatile SingularAttribute<Store, Integer> zone;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask1;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask2;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask3;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask4;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask5;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask6;
    public static volatile SingularAttribute<Store, Integer> scheduleHourMask7;
    public static volatile SingularAttribute<Store, Integer> scheduleMinute;
    public static volatile SingularAttribute<Store, Integer> retryCount;
    public static volatile SingularAttribute<Store, Integer> retryDelay;
    public static volatile SingularAttribute<Store, String> accountName;
    public static volatile SingularAttribute<Store, String> password;
    public static volatile SingularAttribute<Store, Integer> version;


    public static volatile SingularAttribute<Store, Country> country;
    public static volatile SingularAttribute<Store, Integer> country_;

    public static volatile SingularAttribute<Store, Currency> defaultCurrency;
    public static volatile SingularAttribute<Store, Integer> defaultCurrency_;

    public static volatile SingularAttribute<Store, CustomerTerm> defaultCustomerTerm;
    public static volatile SingularAttribute<Store, Integer> defaultCustomerTerm_;

    public static volatile SingularAttribute<Store, TaxClass> defaultTaxClass;
    public static volatile SingularAttribute<Store, Integer> defaultTaxClass_;

    public static volatile SingularAttribute<Store, TaxZone> defaultTaxZone;
    public static volatile SingularAttribute<Store, Integer> defaultTaxZone_;

    public static volatile ListAttribute<Store, AccountPayable> accountPayables;
    public static volatile ListAttribute<Store, AccountPayableBatch> accountPayableBatchs;
    public static volatile ListAttribute<Store, AccountPayableHistory> accountPayableHistories;
    public static volatile ListAttribute<Store, AccountReceivable> accountReceivables;
    public static volatile ListAttribute<Store, Appointment> appointments;
    public static volatile ListAttribute<Store, Batch> batchs;
    public static volatile ListAttribute<Store, BinLocation> binLocations;
    public static volatile ListAttribute<Store, Cheque> cheques;
    public static volatile ListAttribute<Store, Commission> commissions;
    public static volatile ListAttribute<Store, Consignment> consignments;
    public static volatile ListAttribute<Store, Customer> customers;
    public static volatile ListAttribute<Store, CustomerBuyer> customerBuyers;
    public static volatile ListAttribute<Store, CustomerGroup> customerGroups;
    public static volatile ListAttribute<Store, CustomerNote> customerNotes;
    public static volatile ListAttribute<Store, CustomerShipTo> customerShipTos;
    public static volatile ListAttribute<Store, Deposit> deposits;
    public static volatile ListAttribute<Store, DropPayout> dropPayouts;
    public static volatile ListAttribute<Store, Employee> employees;
    public static volatile ListAttribute<Store, EmployeeGroup> employeeGroups;
    public static volatile ListAttribute<Store, FunctionAccess> functionAccesses;
    public static volatile ListAttribute<Store, GiftCertificate> giftCertificates;
    public static volatile ListAttribute<Store, GiftCertificateTransaction> giftCertificateTransactions;
    public static volatile ListAttribute<Store, Invoice> invoices;
    public static volatile ListAttribute<Store, InvoiceEntry> invoiceEntries;
    public static volatile ListAttribute<Store, InvoiceRecurring> invoiceRecurrings;
    public static volatile ListAttribute<Store, ItemBom> itemBoms;
    public static volatile ListAttribute<Store, ItemLabel> itemLabels;
    public static volatile ListAttribute<Store, ItemLimit> itemLimits;
    public static volatile ListAttribute<Store, ItemLocation> itemLocations;
    public static volatile ListAttribute<Store, ItemLog> itemLogs;
    public static volatile ListAttribute<Store, ItemLot> itemLots;
    public static volatile ListAttribute<Store, ItemQuantity> itemQuantities;
    public static volatile ListAttribute<Store, Kit> kits;
    public static volatile ListAttribute<Store, KitEntry> kitEntries;
    public static volatile ListAttribute<Store, MailGroup> mailGroups;
    public static volatile ListAttribute<Store, MerchantAuthorization> merchantAuthorizations;
    public static volatile ListAttribute<Store, MerchantBatch> merchantBatchs;
    public static volatile ListAttribute<Store, Payment> payments;
    public static volatile ListAttribute<Store, PaymentBatch> paymentBatchs;
    public static volatile ListAttribute<Store, PaymentType> paymentTypes;
    public static volatile ListAttribute<Store, Promotion> promotions;
    public static volatile ListAttribute<Store, PurchaseOrder> purchaseOrders;
    public static volatile ListAttribute<Store, PurchaseOrderEntry> purchaseOrderEntries;
    public static volatile ListAttribute<Store, PurchaseOrderHistory> purchaseOrderHistories;
    public static volatile ListAttribute<Store, PurchaseOrderHistoryEntry> purchaseOrderHistoryEntries;
    public static volatile ListAttribute<Store, QuantityDiscount> quantityDiscounts;
    public static volatile ListAttribute<Store, ReasonCode> reasonCodes;
    public static volatile ListAttribute<Store, Receipt> receipts;
    public static volatile ListAttribute<Store, ReorderList> reorderLists;
    public static volatile ListAttribute<Store, ReturnCode> returnCodes;
    public static volatile ListAttribute<Store, ReturnEntry> returnEntries;
    public static volatile ListAttribute<Store, ReturnOrder> returnOrders;
    public static volatile ListAttribute<Store, ReturnOrderEntry> returnOrderEntries;
    public static volatile ListAttribute<Store, ReturnTransaction> returnTransactions;
    public static volatile ListAttribute<Store, SalesOrder> salesOrders;
    public static volatile ListAttribute<Store, Seq> seqs;
    public static volatile ListAttribute<Store, SerialNumber> serialNumbers;
    public static volatile ListAttribute<Store, Service> services;
    public static volatile ListAttribute<Store, ServiceCode> serviceCodes;
    public static volatile ListAttribute<Store, ServiceEntry> serviceEntries;
    public static volatile ListAttribute<Store, Shipping> shippings;
    public static volatile ListAttribute<Store, ShippingCarrier> shippingCarriers;
    public static volatile ListAttribute<Store, ShippingService> shippingServices;
    public static volatile ListAttribute<Store, Signature> signatures;
    public static volatile ListAttribute<Store, Station> stations;
    public static volatile ListAttribute<Store, SubCategory> subCategories;
    public static volatile ListAttribute<Store, Substitute> substitutes;
    public static volatile ListAttribute<Store, TaxRate> taxRates;
    public static volatile ListAttribute<Store, TimeCard> timeCards;
    public static volatile ListAttribute<Store, TransactionLog> transactionLogs;
    public static volatile ListAttribute<Store, TransferOrder> transferOrderReceiveStores;
    public static volatile ListAttribute<Store, TransferOrder> transferOrderSendStores;
    public static volatile ListAttribute<Store, TransferOrder> transferOrderStores;
    public static volatile ListAttribute<Store, TransferOrderHistory> transferOrderHistoryReceiveStores;
    public static volatile ListAttribute<Store, TransferOrderHistory> transferOrderHistorySendStores;
    public static volatile ListAttribute<Store, TransferOrderHistory> transferOrderHistoryStores;
    public static volatile ListAttribute<Store, Vendor> vendors;
    public static volatile ListAttribute<Store, VendorItem> vendorItems;
    public static volatile ListAttribute<Store, VendorNote> vendorNotes;
    public static volatile ListAttribute<Store, VendorShipTo> vendorShipTos;
    public static volatile ListAttribute<Store, VendorShippingService> vendorShippingServices;
    public static volatile ListAttribute<Store, VendorTerm> vendorTerms;
    public static volatile ListAttribute<Store, VoidedTransaction> voidedTransactions;
    public static volatile ListAttribute<Store, Voucher> vouchers;
    public static volatile ListAttribute<Store, VoucherEntry> voucherEntries;


}

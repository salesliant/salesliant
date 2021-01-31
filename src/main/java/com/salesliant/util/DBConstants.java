package com.salesliant.util;

import java.util.List;
import javafx.scene.control.ComboBox;

/**
 * <p>
 * Title: TypeLoader
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Digiliant, LLC
 * </p>
 *
 * @author Lewis Liu
 * @version 1.0
 */
public final class DBConstants {

    private DBConstants() {
    }

    /*
     * List Type, for generate a list of types These must match the name "Type"
     * elements in DBCode.xml
     */
    public static final String TYPE_BARCODEFORMAT = "BarcodeFormat";
    public static final String TYPE_LABELTYPE = "LabelType";
    public static final String TYPE_CUSTOMER = "CustomerType";
    public static final String ITEM_TYPE = "ItemType";
    public static final String TYPE_SALESORDER = "SalesOrderType";
    public static final String TYPE_ITEMCOST = "ItemCostType";
    public static final String TYPE_COMMISSIONMETHOD = "CommissionMode";
    public static final String TYPE_PRICE_LEVEL = "PriceLevel";
    public static final String TYPE_RECEIP_PRINTER_OPTION = "ReceiptPrinterOption";
    public static final String TYPE_RECEIP_PRINTER = "ReceiptPrinterType";
    public static final String TYPE_ITEMLOG_TRANSFERTYPE = "ItemLog_TransferType";
    public static final String STATUS_PURCHASEORDER = "POStatus";
    public static final String TYPE_PURCHASEORDER = "POType";
    public static final String TYPE_RETURN = "ReturnType";
    public static final String TYPE_ITEM_COST_METHOD = "ItemCostMethod";
    public static final String TYPE_CUSTOMER_LOOKUP_METHOD = "CustomerLookupMethod";
    public static final String TYPE_ITEM_LOOKUP_METHOD = "ItemLookupMethod";
    public static final String TYPE_ITEM_PRICE_METHOD = "ItemPriceMethod";
    public static final String TYPE_PAYMENT_VERIFICATION = "PaymentVerification";
    public static final String TYPE_PAGE = "PageType";

    public static final String ITEM_LOG_DESCRIPTION_PURCHASE = "Purchase Order";
    public static final String ITEM_LOG_DESCRIPTION_INVOICE = "Invoice";
    public static final String ITEM_LOG_DESCRIPTION_TRANSFER_SEND = "Transfer Order Send";
    public static final String ITEM_LOG_DESCRIPTION_TRANSFER_RECEIVE = "Transfer Order Receive";
    public static final String ITEM_LOG_DESCRIPTION_ADJUST = "Inventory Adjustment";

    /* Default status */
    public static final int STATUS_OPEN = 0;
    public static final int STATUS_CLOSE = 1;
    public static final int STATUS_IN_PROGRESS = 2;

    /* Gift Certificate status */
    public static final int STATUS_GIFT_CERTIFICATE_ACTIVE = 0;
    public static final int STATUS_GIFT_CERTIFICATE_DISABLE = 1;
    public static final int STATUS_GIFT_CERTIFICATE_EXPIRED = 2;

    /* Acoount Payable Type */
    public static final int TYPE_APAR_INV = 0;
    public static final int TYPE_APAR_CRE = 1;

    /* Acoount Payable Type */
    public static final int TYPE_INVOICE_ENTRY_LOG_NON_INVENTORY = 0;
    public static final int TYPE_INVOICE_ENTRY_LOG_MARK_DOWN = 1;

    /* Acoount Payable Type */
    public static final int TYPE_PAYMENT_PROCESS_SALES_ORDER = 0;
    public static final int TYPE_PAYMENT_PROCESS_ACCOUNT_RECEIVABLE_TRANSACTION = 1;
    public static final int TYPE_PAYMENT_PROCESS_PAY_OUT = 2;

    /* Deposit Type */
    public static final int TYPE_DEPOSIT_DEPOSIT = 0;
    public static final int TYPE_DEPOSIT_CREDIT = 1;

    /* Payment Batch Status */
    public static final int STATUS_PAYMENT_NORMAL = 0;
    public static final int STATUS_PAYMENT_OVER = 1;
    public static final int STATUS_PAYMENT_SHORT = 2;
    public static final int STATUS_PAYMENT_MIX = 3;

    /* Payment Type */
    public static final int TYPE_VERIFICATION_CASH = 0;
    public static final int TYPE_VERIFICATION_CHECK = 1;
    public static final int TYPE_VERIFICATION_CREDIT_CARD = 2;
    public static final int TYPE_VERIFICATION_GIFT_CARD = 3;
    public static final int TYPE_VERIFICATION_ACCOUNT = 4;
    public static final int TYPE_VERIFICATION_GIFT_CERTIFICATE = 5;
    public static final int TYPE_VERIFICATION_COUPON = 6;

    /* Return Type */
    public static final int TYPE_RETURN_FROM_CUSTOMER = 0;
    public static final int TYPE_RETURN_TO_VENDOR = 1;
    public static final int TYPE_RETURN_TO_STOCK = 2;
    public static final int TYPE_RETURN_TO_CUSTOMER = 3;
    public static final int TYPE_RETURN_OUT_OF_WARRANTY = 4;

    /* Acoount Payable Type */
    public static final int TYPE_VOID_INVOICE = 0;
    public static final int TYPE_VOID_QUOTE = 1;
    public static final int TYPE_VOID_ORDER = 2;
    public static final int TYPE_VOID_SERVICE_ORDER = 3;
    public static final int TYPE_VOID_INTERNET_ORDER = 4;
    public static final int TYPE_VOID_PURCHASE_ORDER = 5;
    public static final int TYPE_VOID_LAYWAY = 6;

    /* Default status */
    public static final String SEQ_CUSTOMER_NUMBER = "customer_number";
    public static final String SEQ_INVOICE_NUMBER = "invoice_number";
    public static final String SEQ_PURCHASE_ORDER_NUMBER = "purchase_order_number";
    public static final String SEQ_ORDER_NUMBER = "order_number";
    public static final String SEQ_QUOTE_NUMBER = "quote_number";
    public static final String SEQ_SERVICE_ORDER_NUMBER = "service_order_number";
    public static final String SEQ_VENDOR_NUMBER = "vendor_number";
    public static final String SEQ_ACCOUNT_PAYABLE_BATCH_NUMBER = "account_payable_batch_number";
    public static final String SEQ_BATCH_NUMBER = "batch_number";
    public static final String SEQ_ACCOUNT_RECEIVABLE_NUMBER = "account_receivable_number";
    public static final String SEQ_ACCOUNT_RECEIVABLE__BATCH = "account_receivable_batch_number";
    public static final String SEQ_RMA_NUMBER = "rma_number";
    public static final String SEQ_TRANSFER_NUMBER = "transfer_number";

    /* Special Status Types, for generate a list of status */
 /* Special Status, for generate the index of particular status */
 /*
     * When PO is first created, it awaits the purchaser to confirm and place
     * it, then it's "In Process", which means it has added to the quantity
     * "On Order". when all items are received AND committed to inventory, the
     * PO is "Closed",
     * 
     * In RMS/QuickSell, if an item is partially received AND commited to
     * inventory, the PO's status is "Partial"
     */
    private static final String SP = STATUS_PURCHASEORDER;
    public static final int STATUS_PO_OPEN = getType(SP, "Open");
    public static final int STATUS_PO_CLOSED = getType(SP, "Close");
    public static final int STATUS_PO_PENDING = getType(SP, "Pending");
    public static final int STATUS_PO_PARTIAL = getType(SP, "Partial");
    public static final int STATUS_PO_BACKORDER = getType(SP, "Back Order");

    /* Item Types */
    public static final int ITEM_TYPE_STANDARD = getType("ItemType", "Standard");
    public static final int ITEM_TYPE_MATRIX = getType("ItemType", "Matrix");
    public static final int ITEM_TYPE_LOT = getType("ItemType", "Lot");
    public static final int ITEM_TYPE_BOM = getType("ItemType", "BOM");
    public static final int ITEM_TYPE_KIT = getType("ItemType", "Kit");
    public static final int ITEM_TYPE_NOTE = getType("ItemType", "Note");

    /* Barcode Format IDs */
    private final static String B = TYPE_BARCODEFORMAT;
    public final static int TYPE_BARCODE_2OF5 = getType(B, "2 of 5");
    public final static int TYPE_BARCODE_INTERLEAVED2OF5 = getType(B, "Interleaved 2 of 5");
    public final static int TYPE_BARCODE_3OF9 = getType(B, "3 of 9");
    public final static int TYPE_BARCODE_CODEBAR = getType(B, "Codebar (rationalized)");
    public final static int TYPE_BARCODE_3OF9EXT = getType(B, "3 of 9 (extended)");
    public final static int TYPE_BARCODE_CODE128A = getType(B, "Code 128A");
    public final static int TYPE_BARCODE_CODE128B = getType(B, "Code 128B");
    public final static int TYPE_BARCODE_CODE129C = getType(B, "Code 129C");
    public final static int TYPE_BARCODE_UPC_A = getType(B, "UPC-A");
    public final static int TYPE_BARCODE_MSI = getType(B, "MSI(Plessey)");
    public final static int TYPE_BARCODE_CODE93 = getType(B, "Code 93");
    public final static int TYPE_BARCODE_CODE93EXT = getType(B, "Extended Code 93");
    public final static int TYPE_BARCODE_EAN13 = getType(B, "EAN 13");
    public final static int TYPE_BARCODE_EAN8 = getType(B, "EAN 8");
    public final static int TYPE_BARCODE_POSTNET = getType(B, "PostNet");

    /* Page Type IDs */
    public final static String TYPE_PAGE_LETTER = "LETTER";
    /* Label Format IDs */
    private final static String L = TYPE_LABELTYPE;
    public final static int TYPE_LABEL_AVERY_5160 = getType(L, "Avery Label 5160");
    public final static int TYPE_LABEL_AVERY_5161 = getType(L, "Avery Label 5161");
    public final static int TYPE_LABEL_AVERY_5162 = getType(L, "Avery Label 5162");

    /* SalesOrder Type */
    private final static String SOT = TYPE_SALESORDER;
    public final static int TYPE_SALESORDER_INVOICE = getType(SOT, "Invoice");
    public final static int TYPE_SALESORDER_QUOTE = getType(SOT, "Quote");
    public final static int TYPE_SALESORDER_ORDER = getType(SOT, "Order");
    public final static int TYPE_SALESORDER_SERVICE = getType(SOT, "Service");
    public final static int TYPE_SALESORDER_INTERNET = getType(SOT, "Internet");
    public final static int TYPE_SALESORDER_VOID = getType(SOT, "Void");

    public static final String TYPE_SO_INVOICE = "Invoice";
    public static final String TYPE_SO_ORDER = "Order";
    public static final String TYPE_SO_QUOTE = "Quote";
    public static final String TYPE_SO_SERVICE = "Service";
    public static final String TYPE_SO_INTERNET = "Internet";

    public static final String TYPE_CHECK_INVOICE = "I";
    public static final String TYPE_CHECK_DEPOSIT = "D";
    public static final String TYPE_CHECK_ACCOUNT_RECEIVABLE_PAYMENT = "A";
    public static final String TYPE_CHECK_ORDER = "O";
    public static final String TYPE_CHECK_BAD = "B";

    public static final String TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE = "CHG";
    public static final String TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT = "PMT";
    public static final String TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_DISCOUNT = "DIS";
    public static final String TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND = "REF";
    public static final String TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT = "CRD";
    public static final String TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_UNCOLLECTABLE = "UNC";

    public static final String NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE = "Invoice";
    public static final String NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT = "AR Payment";
    public static final String NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND = "Refund Check Issued";
    public static final String NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_DISCOUNT = "Discount";
    public static final String NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT = "Return Credit";
    public static final String NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_UNCOLLECTABLE = "Uncollectable";
    /* ItemLog Transfer Type */
    public static final int TYPE_ITEMLOG_TRANSFERTYPE_PURCHASEORDER = getType(TYPE_ITEMLOG_TRANSFERTYPE, "Purchase Order");
    /* public static final int TYPE_ITEMLOG_TRANSFERTYPE_SALESORDER = getType(TYPE_ITEMLOG_TRANSFERTYPE, "Sales Order"); */
    public static final int TYPE_ITEMLOG_TRANSFERTYPE_INVOICE = getType(TYPE_ITEMLOG_TRANSFERTYPE, "Invoice");
    public static final int TYPE_ITEMLOG_TRANSFERTYPE_ADJUSTMENT = getType(TYPE_ITEMLOG_TRANSFERTYPE, "Inventory Adjustment");
    public static final int TYPE_ITEMLOG_TRANSFERTYPE_TRANSFERORDER_SEND = getType(TYPE_ITEMLOG_TRANSFERTYPE, "Transfer Order Send");
    public static final int TYPE_ITEMLOG_TRANSFERTYPE_TRANSFERORDER_RECEIVE = getType(TYPE_ITEMLOG_TRANSFERTYPE, "Transfer Order Receive");

    /* PO Type */
    public final static int TYPE_PO_PO = getType(TYPE_PURCHASEORDER, "Purchase Order");
    public final static int TYPE_PO_TRANSFER_IN = getType(TYPE_PURCHASEORDER, "Transfer In");

    /* Item Cost Update Method */
    public final static int TYPE_ITEM_COST_MEHTOD_NOUPDATE = getType(TYPE_ITEMCOST, "No Update");
    public final static int TYPE_ITEM_COST_MEHTOD_LANDED_COST = getType(TYPE_ITEMCOST, "Landed Cost");
    public final static int TYPE_ITEM_COST_MEHTOD_WEIGHTED_AVERAGE = getType(TYPE_ITEMCOST, "Average");

    /* Item Cost Update Method */
    public final static int TYPE_COMMISSION_BY_PERCENT_OF_SALES = getType(TYPE_COMMISSIONMETHOD, "By Percent of Sales");
    public final static int TYPE_COMMISSION_BY_PERCENT_OF_PROFIT = getType(TYPE_COMMISSIONMETHOD, "By Percent of Profit");
    public final static int TYPE_COMMISSION_BY_FIXED_AMOUNT = getType(TYPE_COMMISSIONMETHOD, "By Fixed Ammount");

    /* Item Price Method */
    public final static int TYPE_ITEM_PRICE_METHOD_BY_PRICE_LEVEL = getType(TYPE_ITEM_PRICE_METHOD, "Item Price Level");
    public final static int TYPE_ITEM_PRICE_METHOD_BY_GROUP_DISCOUNT = getType(TYPE_ITEM_PRICE_METHOD, "Customer Group Discount");

    /**
     * Return Type Index by name.
     *
     * @param name type name
     * @return type object
     */
    public static int getType(String[] name) {
        return DBCodeLoader.getInstance().getType(name);
    }

    /**
     * Gets the type code
     *
     * @param name String Name in DBCode.xml
     * @param description String Description in DBCode.xml
     * @return int Type code
     */
    private static int getType(String name, String description) {
        return getType(new String[]{name, description});
    }

    /**
     * Return Type List by name.
     *
     * @param name type list name
     * @return type list
     */
    public static List<String> getTypes(String name) {
        return DBCodeLoader.getInstance().getTypes(name);
    }

    /**
     * Return Combobox by name.
     *
     * @param name combo box name
     * @return combo box object
     */
    public static ComboBox<?> getComboBoxTypes(String name) {
        return DBCodeLoader.getInstance().getComboBoxTypes(name);
    }
}

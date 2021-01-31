package com.salesliant.util;

import java.util.HashMap;
import java.util.Map;

public interface AppConstants {

    /**
     * ************************************ Event/Action Command Constants
     * **********************************
     */
    /* GENERIC Action Commands -- used for "setActionCommand()" */
    public final static String DB_READ = "DB_Read";
    public final static String DB_DELETE = "DB_Delete";
    public final static String DB_UPDATE = "DB_Update";
    public final static String DB_CREATE = "DB_Create";
    public final static String DB_SALES_ORDER_READ = "DB_Sales_Order_Read";
    public final static String DB_CUSTOMER_SHIP_TO_READ = "DB_Customer_Ship_To_Read";

    public final static String ACTION_HELP = "Help";
    public final static String ACTION_PRINT = "Print";
    public final static String ACTION_PRINT_LIST = "Print List";
    public final static String ACTION_PRINT_REPORT = "Print Report";
    public final static String ACTION_PRINT_LABEL = "Print Label";
    public final static String ACTION_SELECT_ALL = "SelectAll";
    public final static String ACTION_UN_SELECT_ALL = "UnSelectAll";
    public final static String ACTION_UN_SELECT = "UnSelect";
    public final static String ACTION_EMAIL = "Email";
    public final static String ACTION_EXPORT = "Export";
    public final static String ACTION_REFRESH = "Refresh";
    public final static String ACTION_RELOAD = "Reload";
    public final static String ACTION_RESET = "Reset";
    public final static String ACTION_RETURN = "Return";
    public final static String ACTION_PACKAGE_START = "Package Start";
    public final static String ACTION_PACKAGE_END = "Package End";
    public final static String ACTION_CLOSE = "Close";
    public final static String ACTION_CLOSE_DIALOG = "CloseDialog";
    public final static String ACTION_CLOSE_CHILD_DIALOG = "Close child Dialog";
    public final static String ACTION_UN_DO = "UnDo";
    public final static String ACTION_OK_SELECTED = "OkSelect";
    public final static String ACTION_OK = "OK";
    public final static String ACTION_DISPLAY = "Display";
    /* Table related actions */
    public final static String ACTION_DELETE = "Delete";
    public final static String ACTION_KILL = "Kill";
    public final static String ACTION_EDIT = "Edit";
    public final static String ACTION_ADJUST = "Adjust";
    public final static String ACTION_ENTER_DISCOUNT = "Enter Discount";
    public final static String ACTION_EDIT_BOM = "Edit BOM";
    public final static String ACTION_ADD = "Add";
    public final static String ACTION_LINE_NOTE = "LineNote";
    public final static String ACTION_SELECT_LIST = "SelectList";
    public final static String ACTION_SELECT = "Select";
    public final static String ACTION_CLOCK = "Clock";
    public final static String ACTION_CLOCK_IN = "Clock_In";
    public final static String ACTION_CLOCK_OUT = "Clock_Out";
    public final static String ACTION_LOGIN = "Login";
    public final static String ACTION_F1 = "F1";
    public final static String ACTION_F2 = "F2";
    public final static String ACTION_MOVE_ROW_UP = "MoveUp";
    public final static String ACTION_MOVE_ROW_DOWN = "MoveDown";
    public final static String ACTION_MODIFY_LINE_NOTE = "Line Note";
    public final static String ACTION_MODIFY_NOTE = "Note";
    public final static String ACTION_NEXT = "Next";
    public final static String ACTION_SHOW_COST = "Show Cost";
    public final static String ACTION_CHANGE_CUSTOMER = "Change Customer";
    public final static String ACTION_ASSIGN_SERIAL_NUMBER = "Assign Serial Number";
    public final static String ACTION_UNASSIGN_SERIAL_NUMBER = "Unassign Serial Number";
    public final static String ACTION_TABLE_EDIT = "TableEdit";
    public final static String ACTION_TABLE_ITEM_SELECTED = "TableItemSelected";
    public final static String ACTION_TABLE_EMPTYITEM_SELECTED = "TableEmptyItemSelected";
    public final static String ACTION_TABLE_SKU_INPUT = "TableSKUImput";
    public final static String ACTION_TABLEMODEL_CHANGED = "TableModelChanged";
    /* Data details/edit view actions */
    public final static String ACTION_SAVE = "Save";
    public final static String ACTION_UPDATE = "Update";
    public final static String ACTION_REVERT = "Revert";
    public final static String ACTION_VOID = "Void";
    public final static String ACTION_CANCEL = "Cancel";
    public final static String ACTION_PLACE = "Place"; // Place an order, po
    // etc.
    public final static String ACTION_PROCESS = "Process";
    public final static String ACTION_PROCESS_FINISH = "ProcessFinish";
    public final static String ACTION_APPLY = "Apply";
    public final static String ACTION_COMMIT = "Commit";
    public final static String ACTION_RECEIVE = "Receive";
    public final static String ACTION_RECEIVE_ALL = "ReceiveAll";
    public final static String ACTION_CLONE = "Duplicate";
    public final static String ACTION_SEARCH = "Search";
    public final static String ACTION_BATCH_CLOSE = "BatchClose";
    public final static String ACTION_TENDER = "Tender";
    public final static String ACTION_SEND = "Send";
    public final static String ACTION_POST = "Post";
    public final static String ACTION_OPEN_CLOSE_REGISTER = "OpenCloseRegister";
    /* Commonly used action commands */
    public final static String ACTION_EMPLOYEE_LIST_SHOW = "EmployeeList";
    public final static String ACTION_CUSTOMER_LIST_SHOW = "CustomerList";
    public final static String ACTION_CUSTOMER_EDITVIEW_SHOW = "CustomerEditView";
    public final static String ACTION_VENDOR_LIST_SHOW = "VendorList";
    public final static String ACTION_VENDOR_EDITVIEW_SHOW = "VendorEditView";
    public final static String ACTION_ITEM_LIST_SHOW = "ItemList";
    public final static String ACTION_ITEM_DETAILS_SHOW = "ItemDetails";
    public final static String ACTION_ITEM_EDITVIEW_SHOW = "ItemEditView";
    public final static String ACTION_VENDORITEM_LIST_SHOW = "VendorItemList";
    /* Purchase Order Specific commands */
    public final static String ACTION_PO_NEW_PO = "NewPO";
    public final static String ACTION_PO_PLACE_PO = "Place PO";
    public final static String ACTION_PO_RECEIVE_PO = "Receive PO";
    public final static String ACTION_PO_RECEIVE_WITHOUT_PO = "ReceiveWithOutPO";
    public final static String ORDER_BY_ASC = "OrderByAsc";
    public final static String ORDER_BY_DESC = "OrderByDesc";
    /* Help Topics */
    public static final String HELP_HELP_TOPICS = "Help Topics";
    public static final String HELP_ABOUT = "About";
    public static final String HELP_REPORT = "Reports";
    public static final String HELP_INVENTORY = "Inventory";
    public static final String HELP_INVENTORY_ITEMS = "Inventory List";
    public static final String HELP_INVENTORY_DEPARTMENT = "Department";
    public static final String HELP_INVENTORY_CATEGORY = "Category";
    public static final String HELP_VENDOR = "Vendor Management";
    public static final String HELP_VENDOR_ITEM = "Vendor Items";
    public static final String HELP_CUSTOMER = "Customer Management";
    public static final String HELP_EMPLOYEE = "Employee Management";
    public static final String HELP_SHIPPING = "Shipping Management";
    public static final String HELP_TERM = "Terms";
    public static final String HELP_PURCHASE_ORDER = "Purchase Order";
    public static final String HELP_PURCHASE_ORDER_DETAILS = "Purchase Order Details";
    public static final String HELP_PURCHASE_ORDER_REORDER_LIST = "Reorder List";
    public static final String HELP_PURCHASE_ORDER_PO_HISTORY = "PO History";
    public static final String HELP_PURCHASE_ORDER_RECEIVE_PO = "Purchase Order Details";
    public static final String HELP_RMA = "PO History";
    public static final String HELP_SALES = "Sales Transaction Processing";
    public static final String HELP_SALES_ORDER = "Order Processing";
    public static final String HELP_SALES_INVOICE = "Invoice Processing";
    public static final String ERROR_INPUT = "Invalid input error!";

    public enum Response {
        NO, YES, SELECT, SAVE, CANCEL
    };
    public String states[] = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT",
        "VT", "VA", "WA", "WV", "WI", "WY"};
    public String honorifics[] = {"Mr", "Mrs", "Ms", "Miss", "Dr"};
    public static final Map<String, String> wageTypes = new HashMap<String, String>() {
        {
            put("H", "Hourly");
            put("S", "Salary");
        }
    };
    public static final Map<String, String> wagePeriods = new HashMap<String, String>() {
        {
            put("H", "Hour");
            put("D", "Day");
            put("W", "Week");
            put("BW", "Bi-Weekly");
            put("M", "Month");
            put("Y", "Year");
        }
    };
    public static final Map<String, String> wageOvertimeExtraPeriods = new HashMap<String, String>() {
        {
            put("H", "Hour");
            put("D", "Day");
            put("W", "Week");
            put("BW", "Bi-Weekly");
            put("M", "Month");
            put("Y", "Year");
        }
    };

}

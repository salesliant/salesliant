package com.salesliant.util;

public final class RES {

    private RES() {
    }

    // Icons
    // Common Icon file path (used when using getClass.getResource())
    public static final String ICON_RESOURCE_PATH = "";
    public static final String ICON_RESOURCE_PATH_TOOLBAR = "/toolbar/";

    public static final String ADD_ICON = ICON_RESOURCE_PATH + "/icons/add.png";
    public static final String EDIT_ICON = ICON_RESOURCE_PATH + "/icons/edit.png";
    public static final String DELETE_ICON = ICON_RESOURCE_PATH + "/icons/delete.png";
    public static final String SELECT_ICON = ICON_RESOURCE_PATH + "/icons/select.png";
    public static final String SELECT_ALL_ICON = ICON_RESOURCE_PATH + "/icons/checkall.png";
    public static final String UN_SELECT_ALL_ICON = ICON_RESOURCE_PATH + "/icons/uncheckall.png";
    public static final String UN_SELECT_ICON = ICON_RESOURCE_PATH + "/icons/uncheckall.png";
    public static final String CLONE_ICON = ICON_RESOURCE_PATH + "/icons/duplicate.png";
    public static final String SEARCH_ICON = ICON_RESOURCE_PATH + "/icons/search.png";
    public static final String EXIT_ICON = ICON_RESOURCE_PATH + "/icons/exit.png";
    public static final String SAVE_ICON = ICON_RESOURCE_PATH + "/icons/save.png";
    public static final String RECEIVE_ICON = ICON_RESOURCE_PATH + "/icons/receive.png";
    public static final String EMAIL_ICON = ICON_RESOURCE_PATH + "/icons/email.png";
    public static final String EXPORT_ICON = ICON_RESOURCE_PATH + "/icons/export.png";
    public static final String OK_ICON = ICON_RESOURCE_PATH + "/icons/ok.png";
    public static final String CANCEL_ICON = ICON_RESOURCE_PATH + "/icons/cancel.png";
    public static final String CLOSE_ICON = ICON_RESOURCE_PATH + "/icons/close.png";
    public static final String CONFIRM_ICON = ICON_RESOURCE_PATH + "/icons/confirm.png";
    public static final String WARNING_ICON = ICON_RESOURCE_PATH + "/icons/warning.png";
    public static final String CHECK_ICON = ICON_RESOURCE_PATH + "/icons/check.png";
    public static final String REFRESH_ICON = ICON_RESOURCE_PATH + "/icons/refresh.png";
    public static final String MOVE_UP_ICON = ICON_RESOURCE_PATH + "/icons/move_up.png";
    public static final String MOVE_DOWN_ICON = ICON_RESOURCE_PATH + "/icons/move_down.png";
    public static final String NAV_NEXT_ICON = ICON_RESOURCE_PATH + "/icons/navigate_next.png";
    public static final String NAV_PRIOR_ICON = ICON_RESOURCE_PATH + "/icons/navigate_prior.png";
    public static final String NAV_FIRST_ICON = ICON_RESOURCE_PATH + "/icons/navigate_first.png";
    public static final String NAV_LAST_ICON = ICON_RESOURCE_PATH + "/icons/navigate_last.png";
    public static final String HELP_ICON = ICON_RESOURCE_PATH + "/icons/help.png";
    public static final String PRINT_ICON = ICON_RESOURCE_PATH + "/icons/print.png";
    public static final String PROCESS_ICON = ICON_RESOURCE_PATH + "/icons/process.png";
    public static final String TENDER_ICON = ICON_RESOURCE_PATH + "/icons/tender.png";
    public static final String UN_POST_ICON = ICON_RESOURCE_PATH + "/icons/undo.png";
    public static final String POST_ICON = ICON_RESOURCE_PATH + "/icons/process.png";
    public static final String LINE_NOTE_ICON = ICON_RESOURCE_PATH + "/icons/linenote.png";
    public static final String NOTE_ICON = ICON_RESOURCE_PATH + "/icons/note.png";
    public static final String TIME_PICKER_ICON = ICON_RESOURCE_PATH + "/icons/time_picker.png";
    public static final String DAY_PICKER_ICON = ICON_RESOURCE_PATH + "/icons/day_picker.png";
    public static final String TABLE_VIEW_ICON = ICON_RESOURCE_PATH + "/icons/table_view.png";
    public static final String LOGIN_IN_ICON = ICON_RESOURCE_PATH + "/icons/login.png";
    public static final String LOGIN_OUT_ICON = ICON_RESOURCE_PATH + "/icons/out.png";
    public static final String LOGO_ICON = ICON_RESOURCE_PATH + "/icons/logo.png";
    public static final String SPLASH_ICON = ICON_RESOURCE_PATH + "/icons/splash.gif";
    public static final String ARCHIVE_INSERT = ICON_RESOURCE_PATH + "/icons/archive-insert.png";
    public static final String BACKGROUND_IMAGE = ICON_RESOURCE_PATH + "/icons/background.jpg";
    public static final String CONTENTS_ICON = ICON_RESOURCE_PATH + "/icons/bullets.gif";
    public static final String CLOCK = ICON_RESOURCE_PATH + "/icons/clock.png";
    public static final String CLOCK_IN = ICON_RESOURCE_PATH + "/icons/clockin.png";
    public static final String TREE_PLUS_ICON = ICON_RESOURCE_PATH + "/icons/midplus.gif";
    public static final String TREE_MINUS_ICON = ICON_RESOURCE_PATH + "/icons/midminus.gif";
    public static final String CASH_REGISTER_IMAGE_RESOURCE = ICON_RESOURCE_PATH + "/icons/CashRegister.jpg";
}
/*
 * objClass.getResource() public URL getResource(String name) Finds a resource
 * with a given name. This method returns null if no resource with this name is
 * found. The rules for searching resources associated with a given class are
 * implemented by the * defining class loader of the class.
 * 
 * This method delegates the call to its class loader, after making these
 * changes to the resource name: if the resource name starts with "/", it is
 * unchanged; otherwise, the package name is prepended to the resource name
 * after converting "." to "/". If this object was loaded by the bootstrap
 * loader, the call is delegated to ClassLoader.getSystemResource.
 * 
 * 
 * Parameters: name - name of the desired resource Returns: a java.net.URL
 * object.
 */

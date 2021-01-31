package com.salesliant.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public final class ButtonFactory {

    private static final Logger LOGGER = Logger.getLogger(ButtonFactory.class.getName());
    // Button identifiers also used as the button's captions
    public static final String BUTTON_ADD = "Add";
    public static final String BUTTON_EDIT = "Edit";
    public static final String BUTTON_ADJUST = "Adjust";
    public static final String BUTTON_DELETE = "Delete";
    public static final String BUTTON_SELECT = "Select";
    public static final String BUTTON_SELECT_ALL = "Select ALL";
    public static final String BUTTON_UN_SELECT_ALL = "UnSelect ALL";
    public static final String BUTTON_UN_SELECT = "Un Select";
    public static final String BUTTON_SELECT_LIST = "List Select";
    public static final String BUTTON_CLONE = "Clone";
    public static final String BUTTON_SEARCH = "Search";
    public static final String BUTTON_EXIT = "Exit";
    public static final String BUTTON_SAVE = "Save";
    public static final String BUTTON_UPDATE = "Update";
    public static final String BUTTON_OK = "OK";
    public static final String BUTTON_YES = "YES";
    public static final String BUTTON_NO = "NO";
    public static final String BUTTON_LOGIN = "Login";
    public static final String BUTTON_CANCEL = "Cancel";
    public static final String BUTTON_CLOSE = "Close";
    public static final String BUTTON_CHECK = "Check";
    public static final String BUTTON_REFRESH = "Refresh";
    public static final String BUTTON_RELOAD = "Reload";
    public static final String BUTTON_MOVE_UP = "MoveUp";
    public static final String BUTTON_MOVE_DOWN = "MoveDown";
    public static final String BUTTON_NAV_NEXT = "Next";
    public static final String BUTTON_NAV_PRIOR = "Previous";
    public static final String BUTTON_NAV_FIRST = "First";
    public static final String BUTTON_NAV_LAST = "Last";
    public static final String BUTTON_HELP = "Help";
    public static final String BUTTON_PRINT = "Print";
    public static final String BUTTON_PRINT_REPORT = "Print Report";
    public static final String BUTTON_PRINT_LABEL = "Print Label";
    public static final String BUTTON_EMAIL = "Email";
    public static final String BUTTON_EXPORT = "Export";
    public static final String BUTTON_PROCESS = "Process";
    public static final String BUTTON_APPLY = "Apply";
    public static final String BUTTON_RESET = "Reset";
    public static final String BUTTON_TENDER = "Tender";
    public static final String BUTTON_POST = "Post";
    public static final String BUTTON_UN_POST = "UnPost";
    public static final String BUTTON_VOID = "Void";
    public static final String BUTTON_RECEIVE = "Receive";
    public static final String BUTTON_RECEIVE_ALL = "Receive All";
    public static final String BUTTON_LINE_NOTE = "Line Note";
    public static final String BUTTON_NOTE = "Note";
    public static final String BUTTON_TIME_PICKER = "Time Picker";
    public static final String BUTTON_DAY_PICKER = "Day Picker";
    public static final String BUTTON_TABLE_VIEW = "TableView";
    public static final String BUTTON_CONTENTS = "Contents";
    public static final String BUTTON_CLOCK = "Clock";
    public static final String BUTTON_DISPLAY = "Display";
    public static final String BUTTON_EDIT_BOM = "Edit BOM";
    // Button text position style
    public static int STYLE_HORIZONTAL = 0;
    public static int STYLE_VERTICAL = 1;
    public static int STYLE_ICON = 2;
    private static HashMap<String, Image> fButtonMap = new HashMap<>();

    private ButtonFactory() {
    }

    static {
        ButtonFactory.putButton(BUTTON_ADD, IconFactory.getIcon(RES.ADD_ICON));
        ButtonFactory.putButton(BUTTON_EDIT, IconFactory.getIcon(RES.EDIT_ICON));
        ButtonFactory.putButton(BUTTON_ADJUST, IconFactory.getIcon(RES.EDIT_ICON));
        ButtonFactory.putButton(BUTTON_DELETE, IconFactory.getIcon(RES.DELETE_ICON));
        ButtonFactory.putButton(BUTTON_SELECT_ALL, IconFactory.getIcon(RES.SELECT_ALL_ICON));
        ButtonFactory.putButton(BUTTON_UN_SELECT_ALL, IconFactory.getIcon(RES.UN_SELECT_ALL_ICON));
        ButtonFactory.putButton(BUTTON_UN_SELECT, IconFactory.getIcon(RES.UN_SELECT_ICON));
        ButtonFactory.putButton(BUTTON_SELECT, IconFactory.getIcon(RES.SELECT_ICON));
        ButtonFactory.putButton(BUTTON_SELECT_LIST, IconFactory.getIcon(RES.SELECT_ICON));
        ButtonFactory.putButton(BUTTON_CLONE, IconFactory.getIcon(RES.CLONE_ICON));
        ButtonFactory.putButton(BUTTON_SEARCH, IconFactory.getIcon(RES.SEARCH_ICON));
        ButtonFactory.putButton(BUTTON_EXIT, IconFactory.getIcon(RES.EXIT_ICON));
        ButtonFactory.putButton(BUTTON_SAVE, IconFactory.getIcon(RES.SAVE_ICON));
        ButtonFactory.putButton(BUTTON_RECEIVE, IconFactory.getIcon(RES.RECEIVE_ICON));
        ButtonFactory.putButton(BUTTON_RECEIVE_ALL, IconFactory.getIcon(RES.RECEIVE_ICON));
        ButtonFactory.putButton(BUTTON_EMAIL, IconFactory.getIcon(RES.EMAIL_ICON));
        ButtonFactory.putButton(BUTTON_EXPORT, IconFactory.getIcon(RES.EXPORT_ICON));
        ButtonFactory.putButton(BUTTON_UPDATE, IconFactory.getIcon(RES.SAVE_ICON));
        ButtonFactory.putButton(BUTTON_OK, IconFactory.getIcon(RES.OK_ICON));
        ButtonFactory.putButton(BUTTON_LOGIN, IconFactory.getIcon(RES.OK_ICON));
        ButtonFactory.putButton(BUTTON_YES, IconFactory.getIcon(RES.OK_ICON));
        ButtonFactory.putButton(BUTTON_NO, IconFactory.getIcon(RES.CANCEL_ICON));
        ButtonFactory.putButton(BUTTON_CANCEL, IconFactory.getIcon(RES.CANCEL_ICON));
        ButtonFactory.putButton(BUTTON_CLOSE, IconFactory.getIcon(RES.CLOSE_ICON));
        ButtonFactory.putButton(BUTTON_CHECK, IconFactory.getIcon(RES.CHECK_ICON));
        ButtonFactory.putButton(BUTTON_REFRESH, IconFactory.getIcon(RES.REFRESH_ICON));
        ButtonFactory.putButton(BUTTON_RELOAD, IconFactory.getIcon(RES.REFRESH_ICON));
        ButtonFactory.putButton(BUTTON_RESET, IconFactory.getIcon(RES.REFRESH_ICON));
        ButtonFactory.putButton(BUTTON_MOVE_UP, IconFactory.getIcon(RES.MOVE_UP_ICON));
        ButtonFactory.putButton(BUTTON_MOVE_DOWN, IconFactory.getIcon(RES.MOVE_DOWN_ICON));
        ButtonFactory.putButton(BUTTON_NAV_NEXT, IconFactory.getIcon(RES.NAV_NEXT_ICON));
        ButtonFactory.putButton(BUTTON_NAV_PRIOR, IconFactory.getIcon(RES.NAV_PRIOR_ICON));
        ButtonFactory.putButton(BUTTON_NAV_FIRST, IconFactory.getIcon(RES.NAV_FIRST_ICON));
        ButtonFactory.putButton(BUTTON_NAV_LAST, IconFactory.getIcon(RES.NAV_LAST_ICON));
        ButtonFactory.putButton(BUTTON_HELP, IconFactory.getIcon(RES.HELP_ICON));
        ButtonFactory.putButton(BUTTON_PRINT, IconFactory.getIcon(RES.PRINT_ICON));
        ButtonFactory.putButton(BUTTON_PRINT_REPORT, IconFactory.getIcon(RES.PRINT_ICON));
        ButtonFactory.putButton(BUTTON_PRINT_LABEL, IconFactory.getIcon(RES.PRINT_ICON));
        ButtonFactory.putButton(BUTTON_PROCESS, IconFactory.getIcon(RES.PROCESS_ICON));
        ButtonFactory.putButton(BUTTON_APPLY, IconFactory.getIcon(RES.PROCESS_ICON));
        ButtonFactory.putButton(BUTTON_TENDER, IconFactory.getIcon(RES.TENDER_ICON));
        ButtonFactory.putButton(BUTTON_POST, IconFactory.getIcon(RES.POST_ICON));
        ButtonFactory.putButton(BUTTON_UN_POST, IconFactory.getIcon(RES.UN_POST_ICON));
        ButtonFactory.putButton(BUTTON_VOID, IconFactory.getIcon(RES.CANCEL_ICON));
        ButtonFactory.putButton(BUTTON_LINE_NOTE, IconFactory.getIcon(RES.LINE_NOTE_ICON));
        ButtonFactory.putButton(BUTTON_NOTE, IconFactory.getIcon(RES.NOTE_ICON));
        ButtonFactory.putButton(BUTTON_TIME_PICKER, IconFactory.getIcon(RES.TIME_PICKER_ICON));
        ButtonFactory.putButton(BUTTON_DAY_PICKER, IconFactory.getIcon(RES.DAY_PICKER_ICON));
        ButtonFactory.putButton(BUTTON_TABLE_VIEW, IconFactory.getIcon(RES.TABLE_VIEW_ICON));
        ButtonFactory.putButton(BUTTON_CONTENTS, IconFactory.getIcon(RES.CONTENTS_ICON));
        ButtonFactory.putButton(BUTTON_CLOCK, IconFactory.getIcon(RES.CLOCK));
        ButtonFactory.putButton(BUTTON_EDIT_BOM, IconFactory.getIcon(RES.EDIT_ICON));
    }

    /**
     * This method is not recommended at this time
     *
     * @param name button name
     * @return button object
     */
    public static Button getButton(String name) {
        return internalGetButton(name, null, STYLE_HORIZONTAL);
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String name, String title) {
        Button button = internalGetButton(name, null, STYLE_HORIZONTAL);
        button.setText(title);
        final Text text = new Text(title);
        final double width = text.getLayoutBounds().getWidth();
        button.setPrefWidth(width + 40);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String name, EventHandler handler) {
        Button button = internalGetButton(name, null, STYLE_HORIZONTAL);
        button.setOnAction(handler);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String name, Image icon, EventHandler handler) {
        Button button = internalGetButton(name, icon, STYLE_HORIZONTAL);
        button.setOnAction(handler);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String name, String id, EventHandler handler) {
        Button button = internalGetButton(name, null, STYLE_HORIZONTAL);
        button.setId(id);
        button.setOnAction(handler);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String name, String id, String title, EventHandler handler) {
        Button button = internalGetButton(name, null, STYLE_HORIZONTAL);
        button.setId(id);
        button.setText(title);
        final Text text = new Text(title);
        final double width = text.getLayoutBounds().getWidth();
        button.setPrefWidth(width + 40);
        button.setOnAction(handler);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String title, String id, Double width, EventHandler handler) {
        Button button = new Button();
        button.setId(id);
        button.setText(title);
        button.setPrefWidth(width);
        button.setOnAction(handler);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(String name, Image icon, int style, EventHandler handler) {
        Button button = internalGetButton(name, icon, style);
        button.setOnAction(handler);
        return button;
    }

    @SuppressWarnings("unchecked")
    public static Button getButton(Image icon, String id, EventHandler handler) {
        Button button = internalGetButton("", icon, STYLE_HORIZONTAL);
        button.setId(id);
        button.setOnAction(handler);
        button.setPrefWidth(15);
        return button;
    }

    /**
     * Gets/Creates a button with specified style and EventHandler
     *
     * @param name button name
     * @param style button style
     * @param handler button event handler
     * @return button object
     */
    @SuppressWarnings("unchecked")
    public static Button getButton(String name, int style, EventHandler handler) {
        Button button = internalGetButton(name, null, style);
        button.setOnAction(handler);
        return button;
    }

    /**
     * @param key String Button name/key for looking up the icon
     * @param Image use this icon on button if not null
     * @return Button
     */
    private static Button internalGetButton(String key, Image icon, int style) {
        Button aButton;
        Image aIcon;
        if (icon == null) {
            aIcon = (Image) getIcons().get(key);
        } else {
            aIcon = icon;
        }
        if (aIcon == null) {
            aButton = new Button(key);
        } else {
            aButton = new Button();
            aButton.setGraphic(new ImageView(aIcon));
            if (style == STYLE_HORIZONTAL) {
                aButton.setText(key);
                aButton.setContentDisplay(ContentDisplay.LEFT);
            } else if (style == STYLE_VERTICAL) {
                aButton.setText(key);
                aButton.setContentDisplay(ContentDisplay.BOTTOM);
            } else if (style == STYLE_ICON) {
            }
            if (BUTTON_HELP.equals(key)) {
                aButton.setId(AppConstants.ACTION_HELP);
            }
        }
        final Text text = new Text(key);
        final double width = text.getLayoutBounds().getWidth();
        aButton.setPrefWidth(width + 40);
        return aButton;
    }

    public static void putButton(String name, Image icon) {
        getIcons().put(name, icon);
    }

    private static Map<String, Image> getIcons() {
        if (fButtonMap == null) {
            fButtonMap = new HashMap<>();
        }
        return fButtonMap;
    }
}

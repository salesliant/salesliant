package com.salesliant.util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 * This class provides icons. The icons are reused within each session. Note:
 * This class is not synchronized and should only be called from the ULC Thread.
 *
 * @author Etienne.Studer@canoo.com
 */
public final class IconFactory {

    private static Map<String, Image> fIconMap = new HashMap<>();
    private static final IconFactory INSTANCE = new IconFactory();
    private static final Logger LOGGER = Logger.getLogger(IconFactory.class.getName());

    private IconFactory() {
        IconFactory.putIcon(RES.ADD_ICON, new Image(getClass().getResourceAsStream(RES.ADD_ICON)));
        IconFactory.putIcon(RES.EDIT_ICON, new Image(getClass().getResourceAsStream(RES.EDIT_ICON)));
        IconFactory.putIcon(RES.DELETE_ICON, new Image(getClass().getResourceAsStream(RES.DELETE_ICON)));
        IconFactory.putIcon(RES.SELECT_ICON, new Image(getClass().getResourceAsStream(RES.SELECT_ICON)));
        IconFactory.putIcon(RES.SELECT_ALL_ICON, new Image(getClass().getResourceAsStream(RES.SELECT_ALL_ICON)));
        IconFactory.putIcon(RES.UN_SELECT_ALL_ICON, new Image(getClass().getResourceAsStream(RES.UN_SELECT_ALL_ICON)));
        IconFactory.putIcon(RES.UN_SELECT_ICON, new Image(getClass().getResourceAsStream(RES.UN_SELECT_ICON)));
        IconFactory.putIcon(RES.CLONE_ICON, new Image(getClass().getResourceAsStream(RES.CLONE_ICON)));
        IconFactory.putIcon(RES.SEARCH_ICON, new Image(getClass().getResourceAsStream(RES.SEARCH_ICON)));
        IconFactory.putIcon(RES.EXIT_ICON, new Image(getClass().getResourceAsStream(RES.EXIT_ICON)));
        IconFactory.putIcon(RES.SAVE_ICON, new Image(getClass().getResourceAsStream(RES.SAVE_ICON)));
        IconFactory.putIcon(RES.RECEIVE_ICON, new Image(getClass().getResourceAsStream(RES.RECEIVE_ICON)));
        IconFactory.putIcon(RES.EMAIL_ICON, new Image(getClass().getResourceAsStream(RES.EMAIL_ICON)));
        IconFactory.putIcon(RES.EXPORT_ICON, new Image(getClass().getResourceAsStream(RES.EXPORT_ICON)));
        IconFactory.putIcon(RES.OK_ICON, new Image(getClass().getResourceAsStream(RES.OK_ICON)));
        IconFactory.putIcon(RES.CANCEL_ICON, new Image(getClass().getResourceAsStream(RES.CANCEL_ICON)));
        IconFactory.putIcon(RES.CLOSE_ICON, new Image(getClass().getResourceAsStream(RES.CLOSE_ICON)));
        IconFactory.putIcon(RES.CHECK_ICON, new Image(getClass().getResourceAsStream(RES.CHECK_ICON)));
        IconFactory.putIcon(RES.REFRESH_ICON, new Image(getClass().getResourceAsStream(RES.REFRESH_ICON)));
        IconFactory.putIcon(RES.MOVE_UP_ICON, new Image(getClass().getResourceAsStream(RES.MOVE_UP_ICON)));
        IconFactory.putIcon(RES.MOVE_DOWN_ICON, new Image(getClass().getResourceAsStream(RES.MOVE_DOWN_ICON)));
        IconFactory.putIcon(RES.NAV_NEXT_ICON, new Image(getClass().getResourceAsStream(RES.NAV_NEXT_ICON)));
        IconFactory.putIcon(RES.NAV_PRIOR_ICON, new Image(getClass().getResourceAsStream(RES.NAV_PRIOR_ICON)));
        IconFactory.putIcon(RES.NAV_FIRST_ICON, new Image(getClass().getResourceAsStream(RES.NAV_FIRST_ICON)));
        IconFactory.putIcon(RES.NAV_LAST_ICON, new Image(getClass().getResourceAsStream(RES.NAV_LAST_ICON)));
        IconFactory.putIcon(RES.HELP_ICON, new Image(getClass().getResourceAsStream(RES.HELP_ICON)));
        IconFactory.putIcon(RES.PRINT_ICON, new Image(getClass().getResourceAsStream(RES.PRINT_ICON)));
        IconFactory.putIcon(RES.PROCESS_ICON, new Image(getClass().getResourceAsStream(RES.PROCESS_ICON)));
        IconFactory.putIcon(RES.TENDER_ICON, new Image(getClass().getResourceAsStream(RES.TENDER_ICON)));
        IconFactory.putIcon(RES.POST_ICON, new Image(getClass().getResourceAsStream(RES.POST_ICON)));
        IconFactory.putIcon(RES.UN_POST_ICON, new Image(getClass().getResourceAsStream(RES.UN_POST_ICON)));
        IconFactory.putIcon(RES.LINE_NOTE_ICON, new Image(getClass().getResourceAsStream(RES.LINE_NOTE_ICON)));
        IconFactory.putIcon(RES.NOTE_ICON, new Image(getClass().getResourceAsStream(RES.NOTE_ICON)));
        IconFactory.putIcon(RES.TIME_PICKER_ICON, new Image(getClass().getResourceAsStream(RES.TIME_PICKER_ICON)));
        IconFactory.putIcon(RES.DAY_PICKER_ICON, new Image(getClass().getResourceAsStream(RES.DAY_PICKER_ICON)));
        IconFactory.putIcon(RES.TABLE_VIEW_ICON, new Image(getClass().getResourceAsStream(RES.TABLE_VIEW_ICON)));
        IconFactory.putIcon(RES.CONTENTS_ICON, new Image(getClass().getResourceAsStream(RES.CONTENTS_ICON)));
        IconFactory.putIcon(RES.LOGIN_IN_ICON, new Image(getClass().getResourceAsStream(RES.LOGIN_IN_ICON)));
        IconFactory.putIcon(RES.LOGIN_OUT_ICON, new Image(getClass().getResourceAsStream(RES.LOGIN_OUT_ICON)));
        IconFactory.putIcon(RES.CLOCK, new Image(getClass().getResourceAsStream(RES.CLOCK)));
        IconFactory.putIcon(RES.CLOCK_IN, new Image(getClass().getResourceAsStream(RES.CLOCK_IN)));
    }

    public static Image getIcon(String name) {
        Image icon = (Image) getIcons().get(name);
        if (icon == null) {
            LOGGER.log(Level.WARNING, "Specified icon not found in IconFactory: {0}", name);
        }
        return icon;
    }

    @SuppressWarnings("unchecked")
    public static void putIcon(String name, Image icon) {
        getIcons().put(name, icon);
    }

    private static Map<String, Image> getIcons() {
        if (fIconMap == null) {
            fIconMap = new HashMap<>();
        }
        return fIconMap;
    }

    public static IconFactory get() {
        return INSTANCE;
    }

}

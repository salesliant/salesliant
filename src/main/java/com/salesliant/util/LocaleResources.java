package com.salesliant.util;

import java.util.*;
import java.util.logging.Logger;

/**
 * This class provides properties from resource bundles using client-specific
 * internationalization. Note: This class is not synchronized and should only be
 * called from the ULC Thread.
 *
 * @author Etienne.Studer@canoo.com
 */
abstract public class LocaleResources {

    private static final Logger LOGGER = Logger.getLogger(LocaleResources.class.getName());
    public static final String RESOURCE_BUNDLE_FILENAME = "Salesliant";

    private static ResourceBundle bundle;

    public static char getChar(String key) {
        return getResourceString(key).charAt(0);
    }

    /**
     * Returns property string from the resource bundle.
     *
     * @param key the resource key
     * @return String
     */
    public static String getResourceString(String key) {
        String value;
        try {
            value = getResourceBundle().getString(key);
        } catch (MissingResourceException ex) {
            LOGGER.severe("Property key not defined. Please check file: " + RESOURCE_BUNDLE_FILENAME + ".properties");
            return null;
        }
        return value;
    }

    /**
     * Returns the resource bundle specified by <code>RESOURCE_BUNDLE</code>.
     *
     * @return ResourceBundle
     */
    private static ResourceBundle getResourceBundle() {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_FILENAME, Locale.ENGLISH, Thread.currentThread().getContextClassLoader());
        }
        return bundle;
    }

    private LocaleResources() {
    }
}

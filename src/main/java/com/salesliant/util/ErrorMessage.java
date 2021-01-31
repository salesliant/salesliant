/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.Alert;

/**
 *
 * @author Lewis
 */
public class ErrorMessage {

    public static final ErrorMessage INSTANCE = new ErrorMessage();
    private final Alert errorDialog;
    private static final String ERR_LOAD = "Error getting %%1 data from database.";
    private static final String ERR_INIT = "Error initlizing %%1 data from database.";
    private static final String ERR_VALIDATE = "Error validating %%1 data from database.";
    private static final String ERR_SAVE = "Can't save an invalid %%1 .";
    private static final String ERR_DELETE_IN_USE = "This %%1 is in use, you can't delete it.";
    private static final String ERR_DELETE_NONE_EXIST = "This %%1 is not exist, you can't delete it.";
    private static final String ERR_NULL_VALUE = "You must enter %%1. Null value not allowed.";
    private static final String ERR_NONE_SELECTED = "Nothing selected. Try again.";
    private static final String ERR_OPERATION_DENIED = "User doen't have privilege to this operation.";

    private ErrorMessage() {
        errorDialog = new Alert(Alert.AlertType.INFORMATION);
        errorDialog.setHeaderText(null);
        errorDialog.setTitle(null);
        errorDialog.getDialogPane().getStylesheets().add("css/styles.css");
        errorDialog.getDialogPane().getStyleClass().add("dialogView");
        errorDialog.showAndWait();
    }

    public static void showError(String message) {
        INSTANCE.show(message);
    }

    public static void showError(String message, String[] args) {
        INSTANCE.show(getErrorMessage(message, args));
    }

    public static void showError(String message, String args) {
        INSTANCE.show(getErrorMessage(message, args));
    }

    public static void showError(String message, Throwable t) {
        String completeMessage = message;
        if (t != null) {
            String detailMessage = extractStackTrace(t);
            completeMessage = completeMessage + "\n\n" + detailMessage;
        }
        INSTANCE.show(completeMessage);
    }

    public static void errorLoad(String name) {
        INSTANCE.show(getErrorMessage(ERR_LOAD, name));
    }

    public static void errorInit(String name) {
        INSTANCE.show(getErrorMessage(ERR_INIT, name));
    }

    public static void errorSave(String name) {
        INSTANCE.show(getErrorMessage(ERR_SAVE, name));
    }

    public static void errorDeleteInUse(String name) {
        INSTANCE.show(getErrorMessage(ERR_DELETE_IN_USE, name));
    }

    public static void errorDeleteNoneExist(String name) {
        INSTANCE.show(getErrorMessage(ERR_DELETE_NONE_EXIST, name));
    }

    public static void errorValidate(String name) {
        INSTANCE.show(getErrorMessage(ERR_VALIDATE, name));
    }

    public static void errorOperationDenied(String name) {
        INSTANCE.show(getErrorMessage(ERR_OPERATION_DENIED, name));
    }

    public static void errorNull(String name) {
        INSTANCE.show(getErrorMessage(ERR_NULL_VALUE, name));
    }

    public static void errorNotSelect() {
        INSTANCE.show(ERR_NONE_SELECTED);

    }

    private void show(String message) {
        errorDialog.setContentText(message);
        errorDialog.showAndWait();
    }

    private static String getErrorMessage(String message, String[] args) {
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                message = message.replaceFirst("%%" + String.valueOf(i + 1), args[i]);
            }
        }
        return message;
    }

    private static String getErrorMessage(String message, String arg) {
        return getErrorMessage(message, new String[]{arg});
    }

    private static String extractStackTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        t.printStackTrace(new PrintWriter(stringWriter));
        stringWriter.flush();
        return convertDelimiters(stringWriter.toString());
    }

    private static String convertDelimiters(String original) {
        String result = replace(original, System.getProperty("line.separator"), "\n");
        return replace(result, "\t", "    ");
    }

    private static String replace(final String original, String find, String replace) {
        String result = original;
        int index = result.indexOf(find);
        while (index > -1) {
            result = result.substring(0, index) + replace + result.substring(index + find.length());
            index = result.indexOf(find, index + find.length());
        }
        return result;
    }
}

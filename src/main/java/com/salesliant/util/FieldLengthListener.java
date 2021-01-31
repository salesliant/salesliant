/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Lewis
 */
public class FieldLengthListener implements ChangeListener<String> {

    private final int i;
    private final Control tf;

    public FieldLengthListener(Control tf, int i) {
        this.i = i;
        this.tf = tf;
    }

    @Override
    public void changed(final ObservableValue<? extends String> observableValue, final String oldValue, final String newValue) {
        if (newValue.length() > i && tf instanceof TextField) {
            ((TextField) tf).setText(oldValue);
        }
        if (newValue.length() > i && tf instanceof PasswordField) {
            ((PasswordField) tf).setText(oldValue);
        }
    }
}

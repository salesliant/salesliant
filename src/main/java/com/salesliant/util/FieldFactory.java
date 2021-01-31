/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import static com.salesliant.util.BaseUtil.decimalFilter;
import static com.salesliant.util.BaseUtil.integerFilter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.FormatStringConverter;

/**
 *
 * @author Lewis
 */
public final class FieldFactory {

    public static TextField getIntegerField() {
        TextField textField = new TextField();
        TextFormatter<Integer> integerFormatter = new TextFormatter<>(integerFilter);
        textField.setTextFormatter(integerFormatter);
        return textField;
    }

    public static TextField getDoubleField() {
        TextField textField = new TextField();
        TextFormatter<Integer> decimalFormatter = new TextFormatter<>(decimalFilter);
        textField.setTextFormatter(decimalFormatter);
        return textField;
    }

    public static boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static FormatStringConverter getIntegerFormatConverter() {
        final NumberFormat format = NumberFormat.getIntegerInstance();
        final FormatStringConverter<Number> formatSC = new FormatStringConverter<>(format);
        return formatSC;
    }

    public static FormatStringConverter getDoubleFormatConverter() {
        final DecimalFormat format = (DecimalFormat) NumberFormat.getNumberInstance();
        format.setMinimumFractionDigits(2);
        final FormatStringConverter<Number> formatSC = new FormatStringConverter<>(format);
        return formatSC;
    }
}

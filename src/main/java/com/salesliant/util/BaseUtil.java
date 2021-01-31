package com.salesliant.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class BaseUtil {

    public static String getString(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj.getClass() == BigDecimal.class) {
            return getDecimalFormat().format(obj);
        } else if (obj.getClass() == Date.class) {
            return getDateFormat().format(obj);
        } else if (obj.getClass() == Timestamp.class) {
            return getDateTimeFormat().format(obj);
        } else if (obj.getClass() == Integer.class) {
            return getIntegerFormat().format(obj);
        } else {
            return Objects.toString(obj);
        }
    }

    public static String getDateString(Object obj) {
        if (obj == null) {
            return "";
        } else if (obj.getClass() == BigDecimal.class) {
            return getDecimalFormat().format(obj);
        } else if (obj.getClass() == Date.class) {
            return getDateFormat().format(obj);
        } else if (obj.getClass() == Timestamp.class) {
            return getDateFormat().format(obj);
        } else if (obj.getClass() == Integer.class) {
            return getIntegerFormat().format(obj);
        } else {
            return Objects.toString(obj);
        }
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static Callback stringCell(Pos alignment) {
        return new FormattedTableCellFactory(alignment);
    }

    public static Callback dateCell(Pos alignment) {
        return new FormattedTableCellFactory(getDateFormat(), alignment);
    }

    public static Callback percentCell(Pos alignment) {
        return new FormattedTableCellFactory(getPercentFormat(), alignment);
    }

    public static Callback decimalEditCell(Pos alignment) {
        return new EditableTextFieldTableCell(getDecimalFormat(), alignment);
    }

    public static Callback editableCell(Pos alignment) {
        return new EditableTextFieldTableCell(alignment);
    }

    public static Callback datePickerCell() {
        return new DatePickerCell();
    }

    public static DecimalFormat getDecimalFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

    public static DecimalFormat getDecimal4Format() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMaximumFractionDigits(4);
        numberFormat.setMinimumFractionDigits(4);
        return numberFormat;
    }

    public static DecimalFormat getNumberFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    public static DecimalFormat getDoubleFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

    public static DateFormat getDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat;
    }

    public static DateFormat getDateDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat;
    }

    public static DateFormat getYearDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat;
    }

    public static DateFormat getMonthDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM");
        return dateFormat;
    }

    public static DateFormat getDateTimeFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        return dateFormat;
    }

    public static DateFormat getTimeFormat() {
        DateFormat dateFormat = new SimpleDateFormat("KK:mm a");
        return dateFormat;
    }

    public static DecimalFormat getMoneyFormat() {
        DecimalFormat moneyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
        moneyFormat.setGroupingUsed(false);
        moneyFormat.setMaximumIntegerDigits(10);
        moneyFormat.setMaximumFractionDigits(2);
        return moneyFormat;
    }

    public static DecimalFormat getPercentFormat() {
        DecimalFormat percentFormat = (DecimalFormat) NumberFormat.getPercentInstance();
        percentFormat.setGroupingUsed(false);
        percentFormat.setMaximumIntegerDigits(2);
        percentFormat.setMaximumFractionDigits(2);
        return percentFormat;
    }

    public static DecimalFormat getIntegerFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(10);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    public static Integer zeroIfNull(Integer value) {
        return value == null ? 0 : value;
    }

    public static Double zeroIfNull(Double value) {
        return value == null ? 0 : value;
    }

    public static BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    public static UnaryOperator<TextFormatter.Change> decimalPositiveOnlyFilter = (TextFormatter.Change change) -> {
        if (!change.getControlNewText().matches("\\d{0,7}([\\.]\\d{0,4})?")) {
            return null;
        } else {
            return change;
        }
    };

    public static UnaryOperator<TextFormatter.Change> integerPositiveOnlyFilter = change -> {
        if (!change.getControlNewText().matches("\\d*")) {
            return null;
        } else {
            return change;
        }
    };

    public static UnaryOperator<TextFormatter.Change> decimalFilter = (TextFormatter.Change t) -> {
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");

        if (decimalPattern.matcher(t.getControlNewText()).matches()) {
            return t;
        } else {
            return null;
        }
    };

    public static UnaryOperator<TextFormatter.Change> decimal4Filter = (TextFormatter.Change t) -> {
        Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,4})?");

        if (decimalPattern.matcher(t.getControlNewText()).matches()) {
            return t;
        } else {
            return null;
        }
    };

    public static UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("-?([0-9][0-9]*)?")) {
            return change;
        } else if ("-".equals(change.getText())) {
            if (change.getControlText().startsWith("-")) {
                change.setText("");
                change.setRange(0, 1);
                change.setCaretPosition(change.getCaretPosition() - 2);
                change.setAnchor(change.getAnchor() - 2);
                return change;
            } else {
                change.setRange(0, 0);
                return change;
            }
        }
        return null;
    };

    public static boolean isIntegerValue(BigDecimal bd) {
        return bd.signum() == 0 || bd.scale() <= 0 || bd.stripTrailingZeros().scale() <= 0;
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static <T> List<T> toList(Object object) {
        if (object != null && !(object instanceof List)) {
            return null;
        }
        return (List<T>) object;
    }

    public static Object cloneObject(Object obj) {
        try {
            Object clone = obj.getClass().newInstance();
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.get(obj) == null || Modifier.isFinal(field.getModifiers())) {
                    continue;
                }
                if (field.getType().isPrimitive() || field.getType().equals(String.class)
                        || field.getType().getSuperclass().equals(Number.class)
                        || field.getType().equals(Boolean.class)) {
                    field.set(clone, field.get(obj));
                } else {
                    Object childObj = field.get(obj);
                    if (childObj == obj) {
                        field.set(clone, clone);
                    } else {
                        field.set(clone, cloneObject(field.get(obj)));
                    }
                }
            }
            return clone;
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException e) {
            return null;
        }
    }

    public static String uppercaseFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder s = new StringBuilder();
        char ch = ' ';
        for (int i = 0; i < str.length(); i++) {
            if (ch == ' ' && str.charAt(i) != ' ') {
                s.append(Character.toUpperCase(str.charAt(i)));
            } else {
                s.append(str.charAt(i));
            }
            ch = str.charAt(i);
        }
        return s.toString();
    }

    public static class Message extends Text {

        public Message(String msg) {
            super(msg);
            setWrappingWidth(600);
        }
    }

    public static void buttonSetWidth(Button button) {
        String text = button.getText();
        final Text txt = new Text(text);
        final double width = txt.getLayoutBounds().getWidth();
        button.setPrefWidth(width + 40);
    }
}

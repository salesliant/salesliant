package com.salesliant.util;

import static com.salesliant.util.BaseUtil.getDecimalFormat;
import com.salesliant.util.FieldLengthListener;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.persistence.Column;
import javax.persistence.metamodel.SingularAttribute;

/**
 * Wrapper class for creating UI components for a Persistence class
 */
public class DataUIComponentMap {

    protected static final Logger LOGGER = Logger.getLogger(DataUIComponentMap.class.getName());
    public static int DEFAULT_TEXT_FIELD_LENGTH = 20;
    private final Map<String, Control> uiMap = new HashMap<>();
    private final Map<String, Field> fieldMap = new HashMap<>();
    private final Map<String, Integer> sizeMap = new HashMap<>();

    public DataUIComponentMap(Class cls) {

        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation instanceof Column) {
                    Column col = (Column) annotation;
                    if (col.length() > 0) {
                        sizeMap.put(field.getName(), col.length());
                    }
                }
            }
            fieldMap.put(field.getName(), field);
        }
    }

    public TextField createTextField(SingularAttribute sa, int i) {
        TextField textField = createTextField(sa);
        textField.setPrefWidth(i);
        return textField;
    }

    public TextField createTextField(SingularAttribute sa) {
        String name = sa.getName();
        TextField textField = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == double.class || type == float.class || type == Double.class || type == Float.class || type == BigDecimal.class) {
                textField = FieldFactory.getDoubleField();
            } else if (type == int.class || type == Integer.class || type == Long.class || type == long.class || type == Short.class) {
                textField = FieldFactory.getIntegerField();
            } else if (type == String.class || type == Text.class || type == Character.class) {
                textField = new TextField();
            } else if (type == Date.class) {
                textField = new TextField();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (textField != null) {
                if (!uiMap.containsKey(name)) {
                    uiMap.put(name, textField);
                }
                if (sizeMap.containsKey(name)) {
                    int size = (int) sizeMap.get(name);
                    textField.textProperty().addListener(new FieldLengthListener(textField, size));
                }
            }
        }
        return textField;
    }

    public PasswordField createPasswordField(SingularAttribute sa, int i) {
        PasswordField passwordField = createPasswordField(sa);
        passwordField.setPrefWidth(i);
        return passwordField;
    }

    public PasswordField createPasswordField(SingularAttribute sa) {
        String name = sa.getName();
        PasswordField passwordField = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == String.class) {
                passwordField = new PasswordField();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (passwordField != null) {
                if (!uiMap.containsKey(name)) {
                    uiMap.put(name, passwordField);
                }
                if (sizeMap.containsKey(name)) {
                    int size = (int) sizeMap.get(name);
                    passwordField.textProperty().addListener(new FieldLengthListener(passwordField, size));
                }
            }
        }
        return passwordField;
    }

    public TextArea createTextArea(SingularAttribute sa) {
        String name = sa.getName();
        TextArea textArea = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == String.class) {
                textArea = new TextArea();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (textArea != null) {
                if (!uiMap.containsKey(name)) {
                    uiMap.put(name, textArea);
                }
                if (sizeMap.containsKey(name)) {
                    int size = (int) sizeMap.get(name);
                    textArea.textProperty().addListener(new FieldLengthListener(textArea, size));
                }
            }
        }
        return textArea;
    }

    public CheckBox createCheckBox(SingularAttribute sa) {
        String name = sa.getName();
        CheckBox checkBox = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == Boolean.class) {
                checkBox = new CheckBox();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (checkBox != null) {
                if (uiMap.containsKey(name)) {
                    uiMap.put(name, checkBox);
                }
            }
        }
        return checkBox;
    }

    public Control getField(SingularAttribute sa) {
        if (uiMap.containsKey(sa.getName())) {
            return (Control) uiMap.get(sa.getName());
        } else {
            return null;
        }
    }

    public void setData(Object obj) throws Exception {
        Iterator iter = this.uiMap.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            Object value;
            Class cls = obj.getClass();
            final Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            value = field.get(obj);
            Control comp = (Control) this.uiMap.get(name);
            if (comp != null) {
                if (comp instanceof TextField) { // TextField
                    TextField fld = (TextField) comp;
                    if (value == null) {
                        fld.setText("");
                    } else {
                        if (value instanceof BigDecimal) {
                            fld.setText(getDecimalFormat().format((BigDecimal) value));
                            // fld.setText(new Double(((BigDecimal)
                            // value).doubleValue()).toString());
                        } else if (value instanceof Date) {
                            fld.setText((new SimpleDateFormat("MM/dd/yyyy")).format((Date) value));
                        } else {
                            fld.setText(value.toString());
                        }
                    }
                } else if (comp instanceof PasswordField) { // TextField
                    PasswordField fld = (PasswordField) comp;
                    if (value == null) {
                        fld.setText("");
                    } else {
                        fld.setText(value.toString());
                    }
                } else if (comp instanceof TextArea) { // TextArea
                    TextArea fld = (TextArea) comp;
                    try {
                        if (value != null) {
                            fld.setText(value.toString());
                        } else {
                            fld.setText("");
                        }
                    } catch (Exception ex1) {
                        LOGGER.log(Level.SEVERE, "Error setting text for TextArea.", ex1);
                    }
                } else if (comp instanceof CheckBox) { // CheckBox
                    CheckBox check = (CheckBox) comp;
                    if (value == null) {
                        check.setSelected(false);
                    } else {
                        check.setSelected(((Boolean) value).booleanValue());
                    }
                } else {
                    LOGGER.log(Level.WARNING, "setDataFields()--Invalid component type:{0}", comp);
                }
            } else {
                LOGGER.log(Level.WARNING, "Null UI component for property: {0}", name);
            }
        }
    }

    public void getData(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("Null object.");
        }
        Iterator iter = this.uiMap.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
            Object value;
            Class cls = obj.getClass();
            final Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            Control comp = (Control) this.uiMap.get(name);
            if (comp != null) {
                if (comp instanceof TextField) { // TextField
                    TextField fld = (TextField) comp;
                    value = fld.getText();
                    /**
                     * @todo Convert datatypes ..... For types not accepted by
                     * TextField
                     */
                    if (field.getType() == BigDecimal.class) {
                        if (fld.getText() != null) {
                            value = new BigDecimal(fld.getText());
                        } else {
                            value = null;
                        }
                    } else if (field.getType() == Integer.class) {
                        if (fld.getText() != null) {
                            value = Integer.parseInt(fld.getText());
                        } else {
                            value = null;
                        }
                    } else if (field.getType() == double.class) {
                        if (fld.getText() != null) {
                            value = Double.parseDouble(fld.getText());
                        } else {
                            value = null;
                        }
                    } else if (field.getType() == Long.class) {
                        if (fld.getText() != null) {
                            value = Long.parseLong(fld.getText());
                        } else {
                            value = null;
                        }
                    } else if (field.getType() == Date.class) {
                        if (fld.getText() != null) {
                            value = new SimpleDateFormat("MM/dd/yyyy").parse(fld.getText());
                        } else {
                            value = null;
                        }
                    }
                    if ("".equals(fld.getText())) {
                        value = null;
                    }
                    try {
                        field.set(obj, value);
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        LOGGER.log(Level.SEVERE, "Error setting property:" + name, ex);
                    }
                } else if (comp instanceof PasswordField) { // TextArea
                    PasswordField fld = (PasswordField) comp;
                    value = fld.getText();
                    if ("".equals(fld.getText())) {
                        value = null;
                    }
                    try {
                        field.set(obj, value);
                    } catch (IllegalArgumentException | IllegalAccessException ex1) {
                        LOGGER.log(Level.SEVERE, "Error setting property:" + name, ex1);
                    }
                } else if (comp instanceof TextArea) { // TextArea
                    TextArea fld = (TextArea) comp;
                    value = fld.getText();
                    if ("".equals(fld.getText())) {
                        value = null;
                    }
                    try {
                        field.set(obj, value);
                    } catch (IllegalArgumentException | IllegalAccessException ex1) {
                        LOGGER.log(Level.SEVERE, "Error setting property:" + name, ex1);
                    }
                } else if (comp instanceof CheckBox) { // CheckBox
                    CheckBox check = (CheckBox) comp;
                    try {
                        field.set(obj, check.isSelected());
                    } catch (IllegalArgumentException | IllegalAccessException ex2) {
                        LOGGER.log(Level.SEVERE, "Error setting property:" + name, ex2);
                    }
                } else if (comp instanceof ChoiceBox) {
                    try {
                        ChoiceBox combo = (ChoiceBox) comp;
                        field.set(obj, combo.getSelectionModel().getSelectedItem());
                    } catch (IllegalArgumentException | IllegalAccessException ex3) {
                        LOGGER.log(Level.WARNING, "ULCComboBox value not set for property: {0}", name);
                    }
                } else {
                    LOGGER.log(Level.WARNING, "getFromDataFields()--Invalid component type:{0}", comp);
                }
            } else {
                LOGGER.log(Level.WARNING, "UI Component not found for property: {0}", name);
            }
        }
    }
}

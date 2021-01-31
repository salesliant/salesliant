package com.salesliant.util;

import com.salesliant.entity.Employee;
import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import static com.salesliant.util.BaseUtil.decimal4Filter;
import static com.salesliant.util.BaseUtil.decimalFilter;
import static com.salesliant.util.BaseUtil.getDateTimeFormat;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.integerFilter;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javax.persistence.Column;
import javax.persistence.metamodel.SingularAttribute;
//import org.eclipse.persistence.jpa.jpql.parser.DateTime;

//import com.salesliant.ui.util.field.NumberField;
/**
 * The _BaseUI class provides a layer of mapping between persistence class
 * properties and their corresponding UI components(ULCTextField, ULCCheckBox
 * etc.)
 */
public class DataUI {

    protected static final Logger LOGGER = Logger.getLogger(DataUI.class.getName());
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    public static int DEFAULT_TEXT_FIELD_LENGTH = 20;
    private final Map<String, Control> uiMap = new HashMap<>();
    private final Map<String, Field> fieldMap = new HashMap<>();
    private final Map<String, Integer> sizeMap = new HashMap<>();
    private final DecimalFormat integerFormat = (DecimalFormat) DecimalFormat.getIntegerInstance();

    public DataUI(Class cls) {
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

    public TextField createTextField(SingularAttribute sa, double i) {
        TextField textField = createTextField(sa);
        textField.setPrefWidth(i);
        return textField;
    }

    public Label createLabel(SingularAttribute sa, double i, Pos pos) {
        Label label = createLabel(sa);
        label.setPrefWidth(i);
        label.setAlignment(pos);
        return label;
    }

    public TextField createLabelTextField(SingularAttribute sa, double i) {
        TextField textField = createTextField(sa);
        textField.setPrefWidth(i);
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        return textField;
    }

    public TextField createLabelField(SingularAttribute sa, double i, Pos pos) {
        TextField textField = createTextField(sa);
        textField.setPrefWidth(i);
        textField.setAlignment(pos);
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        textField.setBorder(Border.EMPTY);
        textField.setBackground(Background.EMPTY);
        return textField;
    }

    public TextField createLabelField(SingularAttribute sa) {
        TextField textField = createTextField(sa);
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        textField.setBorder(Border.EMPTY);
        textField.setBackground(Background.EMPTY);
        return textField;
    }

    public DatePicker createDatePicker(SingularAttribute sa) {
        String name = sa.getName();
        DatePicker datePicker = new DatePicker();
        datePicker.setId(name);
        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        uiMap.put(name, datePicker);
        return datePicker;
    }

    public DatePicker createDatePicker(SingularAttribute sa, double width) {
        String name = sa.getName();
        DatePicker datePicker = new DatePicker();
        datePicker.setId(name);
        datePicker.setPrefWidth(width);
        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        uiMap.put(name, datePicker);
        return datePicker;
    }

    public DateTimePicker createDateTimePicker(SingularAttribute sa) {
        String name = sa.getName();
        DateTimePicker datePicker = new DateTimePicker();
        datePicker.setId(name);
        datePicker.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        uiMap.put(name, datePicker);
        return datePicker;
    }

    public TextField createTextField(String name) {
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
            } else if (type == Timestamp.class || type == Date.class) {
                textField = new TextField();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (textField != null) {
                textField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                        Event.fireEvent(event.getTarget(), newEvent);
                        event.consume();
                    }
                });
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

    public TextField createTextField(SingularAttribute sa) {
        String name = sa.getName();
        TextField textField = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == double.class || type == float.class || type == Double.class || type == Float.class || type == BigDecimal.class) {
                textField = new TextField();
                TextFormatter<Integer> decimalFormatter = new TextFormatter<>(decimalFilter);
                textField.setTextFormatter(decimalFormatter);
            } else if (type == int.class || type == Integer.class || type == Long.class || type == long.class || type == Short.class) {
                textField = new TextField();
                TextFormatter<Integer> integerFormatter = new TextFormatter<>(integerFilter);
                textField.setTextFormatter(integerFormatter);
            } else if (type == String.class || type == Text.class || type == Character.class) {
                textField = new TextField();
            } else if (type == Timestamp.class || type == Date.class) {
                textField = new TextField();
            } else if (type == Employee.class) {
                textField = new TextField();
            } else if (type == Item.class) {
                textField = new TextField();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            textField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                    Event.fireEvent(event.getTarget(), newEvent);
                    event.consume();
                }
            });
            if (!uiMap.containsKey(name)) {
                uiMap.put(name, textField);
            }
            if (sizeMap.containsKey(name)) {
                int size = (int) sizeMap.get(name);
                textField.textProperty().addListener(new FieldLengthListener(textField, size));
            }
        }
        return textField;
    }

    public TextField createDecimal4Field(SingularAttribute sa) {
        String name = sa.getName();
        TextField textField = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == double.class || type == float.class || type == Double.class || type == Float.class || type == BigDecimal.class) {
                textField = new TextField();
                TextFormatter<Integer> decimalFormatter = new TextFormatter<>(decimal4Filter);
                textField.setTextFormatter(decimalFormatter);
            } else if (type == int.class || type == Integer.class || type == Long.class || type == long.class || type == Short.class) {
                textField = new TextField();
                TextFormatter<Integer> integerFormatter = new TextFormatter<>(integerFilter);
                textField.setTextFormatter(integerFormatter);
            } else if (type == String.class || type == Text.class || type == Character.class) {
                textField = new TextField();
            } else if (type == Timestamp.class || type == Date.class) {
                textField = new TextField();
            } else if (type == Employee.class) {
                textField = new TextField();
            } else if (type == Item.class) {
                textField = new TextField();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            textField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                    Event.fireEvent(event.getTarget(), newEvent);
                    event.consume();
                }
            });
            if (!uiMap.containsKey(name)) {
                uiMap.put(name, textField);
            }
            if (sizeMap.containsKey(name)) {
                int size = (int) sizeMap.get(name);
                textField.textProperty().addListener(new FieldLengthListener(textField, size));
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
                passwordField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                        Event.fireEvent(event.getTarget(), newEvent);
                        event.consume();
                    }
                });
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (!uiMap.containsKey(name)) {
                uiMap.put(name, passwordField);
            }
            if (sizeMap.containsKey(name)) {
                int size = (int) sizeMap.get(name);
                passwordField.textProperty().addListener(new FieldLengthListener(passwordField, size));
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
                textArea.setWrapText(true);
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (!uiMap.containsKey(name)) {
                uiMap.put(name, textArea);
            }
            if (sizeMap.containsKey(name)) {
                int size = (int) sizeMap.get(name);
                textArea.textProperty().addListener(new FieldLengthListener(textArea, size));
            }
        }
        return textArea;
    }

    public Label createLabel(SingularAttribute sa) {
        String name = sa.getName();
        Label label = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == double.class || type == float.class || type == Double.class || type == Float.class || type == BigDecimal.class) {
                label = new Label();
            } else if (type == int.class || type == Integer.class || type == Long.class || type == long.class || type == Short.class) {
                label = new Label();
            } else if (type == String.class || type == Text.class || type == Character.class) {
                label = new Label();
            } else if (type == Timestamp.class || type == Date.class) {
                label = new Label();
            } else if (type == Employee.class) {
                label = new Label();
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (!uiMap.containsKey(name)) {
                uiMap.put(name, label);
            }
            if (sizeMap.containsKey(name)) {
                int size = (int) sizeMap.get(name);
                label.textProperty().addListener(new FieldLengthListener(label, size));
            }
        }
        return label;
    }

    public CheckBox createCheckBox(SingularAttribute sa) {
        String name = sa.getName();
        CheckBox checkBox = null;
        if (fieldMap.containsKey(name)) {
            Field field = (Field) fieldMap.get(name);
            Class<?> type = field.getType();
            if (type == Boolean.class || type == boolean.class) {
                checkBox = new CheckBox();
                checkBox.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                        Event.fireEvent(event.getTarget(), newEvent);
                        event.consume();
                    }
                });
            } else {
                LOGGER.log(Level.WARNING, "Column: {0} of type {1} is ignored when creating UI component.", new Object[]{field.getName(), field.getType()});
                return null;
            }
            if (!uiMap.containsKey(name)) {
                uiMap.put(name, checkBox);
            }
        }
        return checkBox;
    }

    public CheckBox createCheckBox(SingularAttribute sa, ChangeListener listener) {
        CheckBox checkBox = createCheckBox(sa);
        checkBox.selectedProperty().addListener(listener);
        return checkBox;
    }

    public Control getUIComponent(SingularAttribute sa) {
        if (uiMap.containsKey(sa.getName())) {
            return (Control) uiMap.get(sa.getName());
        } else {
            return null;
        }
    }

    public TextField getTextField(SingularAttribute sa) {
        if (uiMap.containsKey(sa.getName())) {
            return (TextField) uiMap.get(sa.getName());
        } else {
            return null;
        }
    }

    public Label getLabel(SingularAttribute sa) {
        if (uiMap.containsKey(sa.getName())) {
            return (Label) uiMap.get(sa.getName());
        } else {
            return null;
        }
    }

    public TextField getTextField(String name) {
        if (uiMap.containsKey(name)) {
            return (TextField) uiMap.get(name);
        } else {
            return null;
        }
    }

    public TextArea getTextArea(SingularAttribute sa) {
        if (uiMap.containsKey(sa.getName())) {
            return (TextArea) uiMap.get(sa.getName());
        } else {
            return null;
        }
    }

    public void setUIComponent(SingularAttribute sa, Control c) {
        if (!uiMap.containsKey(sa.getName())) {
            uiMap.put(sa.getName(), c);

        } else {
            LOGGER.log(Level.WARNING, "can''t setUI for component type:{0}", c.getClass().getCanonicalName());
        }
    }

    public void setData(Object obj) throws Exception {
        for (String name : uiMap.keySet()) {
            Object value;
            Class cls = obj.getClass();
            final Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            value = field.get(obj);
            Control comp = (Control) this.uiMap.get(name);
            if (comp instanceof TextField) {
                TextField fld = (TextField) comp;
                if (value == null) {
                    fld.setText("");
                } else {
                    if (value instanceof Date) {
                        if (value instanceof Timestamp) {
                            fld.setText(getDateTimeFormat().format((Timestamp) value));
                        } else {
                            fld.setText((new SimpleDateFormat("MM-dd-yyyy")).format((Date) value));
                        }
                    } else if (value instanceof Timestamp) {
                        fld.setText(getDateTimeFormat().format((Timestamp) value));
                    } else if (value instanceof BigDecimal) {
                        fld.setText(getDecimalFormat().format(value));
                    } else if (value instanceof Item) {
                        fld.setText((isEmpty(((Item) value).getItemLookUpCode()) ? "" : ((Item) value).getItemLookUpCode()));
                    } else if (value instanceof Employee) {
                        fld.setText((isEmpty(((Employee) value).getNameOnSalesOrder()) ? "" : ((Employee) value).getNameOnSalesOrder()));
                    } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                        fld.setText(getDecimalFormat().format(value));
                    } else {
                        fld.setText(value.toString());
                    }
                }
            } else if (comp instanceof PasswordField) {
                PasswordField fld = (PasswordField) comp;
                if (value == null) {
                    fld.setText("");
                } else {
                    fld.setText(value.toString());
                }
            } else if (comp instanceof Label) {
                Label fld = (Label) comp;
                try {
                    if (value != null) {
                        fld.setText(value.toString());
                    } else {
                        fld.setText("");
                    }
                } catch (Exception ex1) {
                    LOGGER.log(Level.SEVERE, "Error setting text for Label.", ex1);
                }
            } else if (comp instanceof TextArea) {
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
                if (value != null && (Boolean) value) {
                    check.setSelected(true);
                } else {
                    check.setSelected(false);
                }
            } else if (comp instanceof ComboBox) {
                ComboBox combo = (ComboBox) comp;
                if (value == null) {
                    combo.getSelectionModel().select(-1);
                } else if (value instanceof Integer) {
                    if (!combo.getItems().isEmpty() && combo.getItems().size() >= ((Integer) value + 1)) {
                        combo.getSelectionModel().select(((Integer) value).intValue());
                    }
                } else {
                    Iterator it = combo.getItems().iterator();
                    while (it.hasNext()) {
                        Object o = it.next();
                        if (equalTo(value, o)) {
                            combo.getSelectionModel().select(o);
                            break;
                        }
                    }
                }
            } else if (comp instanceof ChoiceBox) {
                ChoiceBox combo = (ChoiceBox) comp;
                if (value == null) {
                    combo.getSelectionModel().select(0);
                } else if (value instanceof Integer) {
                    if (!combo.getItems().isEmpty() && combo.getItems().size() >= ((Integer) value + 1)) {
                        combo.getSelectionModel().select(((Integer) value).intValue());
                    }
                } else {
                    Iterator it = combo.getItems().iterator();
                    while (it.hasNext()) {
                        Object o = it.next();
                        if (equalTo(value, o)) {
                            combo.getSelectionModel().select(o);
                            break;
                        }
                    }
                }
            } else if (comp instanceof TableView) {
                TableView table = (TableView) comp;
                if (value == null) {
                    table.getSelectionModel().select(-1);
                } else if (value instanceof Integer) {
                    if (!table.getItems().isEmpty() && table.getItems().size() >= ((Integer) value + 1)) {
                        table.getSelectionModel().select(((Integer) value).intValue());
                    }
                } else {
                    Iterator it = table.getItems().iterator();
                    while (it.hasNext()) {
                        Object o = it.next();
                        if (equalTo(value, o)) {
                            table.getSelectionModel().select(o);
                            break;
                        }
                    }
                }
            } else if (comp instanceof DatePicker) {
                if (comp instanceof DateTimePicker) {
                    if (value != null) {
                        DateTimePicker dateTimePicker = (DateTimePicker) comp;
                        dateTimePicker.setDateTimeValue(((Timestamp) value).toLocalDateTime());
                        dateTimePicker.setTimeValue(((Timestamp) value).toLocalDateTime().toLocalTime());
                    }
                } else {
                    if (value != null) {
                        DatePicker datePicker = (DatePicker) comp;
                        datePicker.setValue(((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    }
                }
            } else {
                LOGGER.log(Level.WARNING, "setDataFields()--Invalid component type:{0}", comp);
            }
            if (comp != null) {
                comp.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        Node node = (Node) event.getSource();
                        if (node instanceof ChoiceBox) {
                            KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                            Event.fireEvent(event.getTarget(), newEvent);
                            event.consume();
                        } else if (node instanceof ComboBox) {
                            KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                            Event.fireEvent(event.getTarget(), newEvent);
                            event.consume();
                        } else if (node instanceof TableView) {
                            KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                            Event.fireEvent(event.getTarget(), newEvent);
                            event.consume();
                        }
                    }
                });
            }
        }
    }

    public void getData(Object obj) throws Exception {
        if (obj == null) {
            throw new Exception("Null object.");
        }
        for (String name : this.uiMap.keySet()) {
            Object value;
            Class cls = obj.getClass();
            final Field field = cls.getDeclaredField(name);
            field.setAccessible(true);
            Control comp = (Control) this.uiMap.get(name);
            if (comp != null) {
                if (comp instanceof TextField) { // TextField
                    TextField fld = (TextField) comp;
                    if (fld.getText().trim().isEmpty()) {
                        value = null;
                    } else {
                        if (field.getType() == BigDecimal.class) {
                            value = new BigDecimal(fld.getText().replace(",", "")).setScale(4, RoundingMode.HALF_UP);
                        } else if (field.getType() == Double.class) {
                            value = Double.parseDouble(fld.getText());
                        } else if (field.getType() == Float.class) {
                            value = Float.parseFloat(fld.getText());
                        } else if (field.getType() == Integer.class) {
                            value = Integer.parseInt(fld.getText());
                        } else if (field.getType() == int.class) {
                            value = Integer.parseInt(fld.getText());
                        } else if (field.getType() == Long.class) {
                            value = Long.parseLong(fld.getText());
                        } else if (field.getType() == long.class) {
                            value = Integer.parseInt(fld.getText());
                        } else if (field.getType() == Item.class) {
                            Item item = daoItem.find(Item_.itemLookUpCode, fld.getText());
                            if (item != null) {
                                value = item;
                            } else {
                                value = null;
                            }
                        } else if (field.getType() == Date.class) {
                            if (field.getType() == Timestamp.class) {
                                try {
                                    Date date = getDateTimeFormat().parse(fld.getText());
                                    Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                                    value = timestamp;
                                } catch (ParseException exc) {
                                    value = null;
                                }
                            } else {
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                                    Date date = dateFormat.parse(fld.getText());
                                    value = date;
                                } catch (ParseException exc) {
                                    value = null;
                                }
                            }
                        } else if (field.getType() == Timestamp.class) {
                            try {
                                Date date = getDateTimeFormat().parse(fld.getText());
                                Timestamp timestamp = new java.sql.Timestamp(date.getTime());
                                value = timestamp;
                            } catch (ParseException exc) {
                                value = null;
                            }
                        } else if (fld.getId() != null && fld.getId().equals(name)) {
                            String text = fld.getText().trim();
                            boolean empty = fld.getText().replaceAll("-", " ").trim().isEmpty();
                            if (empty) {
                                value = null;
                            } else if (fld.getText().trim().endsWith("-")) {
                                text = text.substring(0, text.length() - 1);
                                value = text;
                            } else {
                                value = text;
                            }
                        } else {
                            value = fld.getText().trim();
                        }
                    }
                    try {
                        if (field.getType() != Employee.class) {
                            field.set(obj, value);
                        }
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
                } else if (comp instanceof Label) { // TextArea
                    Label fld = (Label) comp;
                    if (fld.getText().trim().isEmpty()) {
                        value = null;
                    } else {
                        if (field.getType() == BigDecimal.class) {
                            value = new BigDecimal(fld.getText().replace(",", "")).setScale(2, RoundingMode.HALF_UP);
                        } else if (field.getType() == Double.class) {
                            value = Double.parseDouble(fld.getText());
                        } else if (field.getType() == Float.class) {
                            value = Float.parseFloat(fld.getText());
                        } else if (field.getType() == Integer.class) {
                            value = Integer.parseInt(fld.getText());
                        } else if (field.getType() == int.class) {
                            value = Integer.parseInt(fld.getText());
                        } else if (field.getType() == Long.class) {
                            value = Long.parseLong(fld.getText());
                        } else if (field.getType() == long.class) {
                            value = Integer.parseInt(fld.getText());
                        } else if (field.getType() == LocalDateTime.class) {
                            try {
                                Date date = getDateTimeFormat().parse(fld.getText());
                                value = date;
                            } catch (ParseException exc) {
                                value = null;
                            }
                        } else if (field.getType() == Date.class) {
                            if (field.getType() == Timestamp.class) {
                                try {
                                    Date date = getDateTimeFormat().parse(fld.getText());
                                    value = date;
                                } catch (ParseException exc) {
                                    value = null;
                                }
                            } else {
                                try {
                                    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                                    Date date = dateFormat.parse(fld.getText());
                                    value = date;
                                } catch (ParseException exc) {
                                    value = null;
                                }
                            }
                        } else if (fld.getId() != null && fld.getId().equals(name)) {
                            String text = fld.getText().trim();
                            boolean empty = fld.getText().replaceAll("-", " ").trim().isEmpty();
                            if (empty) {
                                value = null;
                            } else if (fld.getText().trim().endsWith("-")) {
                                text = text.substring(0, text.length() - 1);
                                value = text;
                            } else {
                                value = text;
                            }
                        } else {
                            value = fld.getText().trim();
                        }
                    }
                    try {
                        if (field.getType() != Employee.class) {
                            field.set(obj, value);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        LOGGER.log(Level.SEVERE, "Error setting property:" + name, ex);
                    }
                } else if (comp instanceof CheckBox) { // CheckBox
                    CheckBox check = (CheckBox) comp;
                    if (check.isSelected()) {
                        field.set(obj, Boolean.TRUE);
                    } else {
                        field.set(obj, Boolean.FALSE);
                    }
                } else if (comp instanceof ChoiceBox) {
                    try {
                        ChoiceBox combo = (ChoiceBox) comp;
                        field.set(obj, combo.getSelectionModel().getSelectedItem());
                    } catch (IllegalArgumentException | IllegalAccessException ex3) {
                        LOGGER.log(Level.WARNING, "ChoiceBox value not set for property: {0}", name);
                    }
                } else if (comp instanceof ComboBox) {
                    try {
                        ComboBox combo = (ComboBox) comp;
                        if (field.getType() == Integer.class
                                && combo.getSelectionModel().getSelectedIndex() >= 0) {
                            field.set(obj, combo.getSelectionModel().getSelectedIndex());
                        } else {
                            field.set(obj, combo.getSelectionModel().getSelectedItem());
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex3) {
                        LOGGER.log(Level.WARNING, "ComboBox value not set for property: {0}", name);
                    }
                } else if (comp instanceof TableView) {
                    try {
                        TableView table = (TableView) comp;
                        if (field.getType() == Integer.class && table.getSelectionModel().getSelectedIndex() >= 0) {
                            field.set(obj, table.getSelectionModel().getSelectedIndex());
                        } else {
                            field.set(obj, table.getSelectionModel().getSelectedItem());
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex3) {
                        LOGGER.log(Level.WARNING, "ComboBox value not set for property: {0}", name);
                    }
                } else if (comp instanceof DatePicker) {
                    if (comp instanceof DateTimePicker) {
                        DateTimePicker datePicker = (DateTimePicker) comp;
                        if (datePicker.getValue() != null) {
                            if (field.getType() == Timestamp.class) {
                                field.set(obj, Timestamp.valueOf(datePicker.getDateTimeValue()));
                            } else if (field.getType() == Date.class) {
                                Instant instant = datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                value = Date.from(instant);
                                field.set(obj, value);
                            }
                        }
                    } else {
                        DatePicker datePicker = (DatePicker) comp;
                        if (datePicker.getValue() != null) {
                            if (field.getType() == Date.class) {
                                Instant instant = datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                value = Date.from(instant);
                                field.set(obj, value);
                            } else if (field.getType() == Timestamp.class) {
                                Instant instant = datePicker.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                value = Timestamp.from(instant);
                                field.set(obj, value);
                            }
                        }
                    }
                } else {
                    LOGGER.log(Level.WARNING, "getFromDataFields()--Invalid component type:{0}", comp);
                }
            } else {
                LOGGER.log(Level.WARNING, "UI Component not found for property: {0}", name);
            }
        }
    }

    private Boolean equalTo(Object o1, Object o2) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (o1 instanceof String && o2 instanceof String && o1.equals(o2)) {
            return true;
        } else if (o1 != null && o2 != null && o1.getClass() != null && o2.getClass() != null && !(o1 instanceof String) && !(o2 instanceof String)) {
            Field f1 = o1.getClass().getDeclaredField("id");
            Field f2 = o2.getClass().getDeclaredField("id");
            f1.setAccessible(true);
            f2.setAccessible(true);
            if (f1.get(o1).equals(f2.get(o2))) {
                return true;
            }
        } else {
            return false;
        }
        return false;
    }

    /**
     * Process String according to its type
     *
     * @param tf text field
     * @param mask input mask
     */
    public static void addMask(final TextField tf, final String mask) {
        tf.setText(mask);
        addTextLimiter(tf, mask.length());

        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            String value = stripMask(tf.getText(), mask);
            tf.setText(merge(value, mask));
        });

        tf.setOnKeyPressed((final KeyEvent e) -> {
            int caretPosition = tf.getCaretPosition();
            if (caretPosition < mask.length() - 1 && mask.charAt(caretPosition) != ' ' && e.getCode() != KeyCode.BACK_SPACE && e.getCode() != KeyCode.LEFT) {
                tf.positionCaret(caretPosition + 1);
            }
        });
        tf.addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent event) -> {
            String character = event.getCharacter();
            if (!checkNumeric(character)) {
                event.consume();
            }
        });
    }

    static String merge(final String value, final String mask) {
        final StringBuilder sb = new StringBuilder(mask);
        int k = 0;
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == ' ' && k < value.length()) {
                sb.setCharAt(i, value.charAt(k));
                k++;
            }
        }
        return sb.toString();
    }

    static String stripMask(String text, final String mask) {
        final Set<String> maskChars = new HashSet<>();
        for (int i = 0; i < mask.length(); i++) {
            char c = mask.charAt(i);
            if (c != ' ') {
                maskChars.add(String.valueOf(c));
            }
        }
        for (String c : maskChars) {
            text = text.replace(c, "");
        }
        return text;
    }

    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }

    static boolean checkNumeric(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int j = 0; j < number.length(); j++) {
            if (!(((int) number.charAt(j) >= 47 && (int) number.charAt(j) <= 57))) {
                return false;
            }
        }
        return true;
    }

    private TextFormatter.Change addPhoneNumberMask(
            TextFormatter.Change change) {

        // Ignore cursor movements, unless the text is empty (in which case
        // we're initializing the field).
        if (!change.isContentChange()
                && !change.getControlNewText().isEmpty()) {
            return change;
        }

        String text = change.getControlNewText();
        int start = change.getRangeStart();
        int end = change.getRangeEnd();

        int anchor = change.getAnchor();
        int caret = change.getCaretPosition();

        StringBuilder newText = new StringBuilder(text);

        int dash;
        while ((dash = newText.lastIndexOf("-")) >= start) {
            newText.deleteCharAt(dash);
            if (caret > dash) {
                caret--;
            }
            if (anchor > dash) {
                anchor--;
            }
        }

        while (newText.length() < 3) {
            newText.append('#');
        }
        if (newText.length() == 3 || newText.charAt(3) != '-') {
            newText.insert(3, '-');
            if (caret > 3 || (caret == 3 && end <= 3 && change.isDeleted())) {
                caret++;
            }
            if (anchor > 3 || (anchor == 3 && end <= 3 && change.isDeleted())) {
                anchor++;
            }
        }

        while (newText.length() < 7) {
            newText.append('#');
        }
        if (newText.length() == 7 || newText.charAt(7) != '-') {
            newText.insert(7, '-');
            if (caret > 7 || (caret == 7 && end <= 7 && change.isDeleted())) {
                caret++;
            }
            if (anchor > 7 || (anchor == 7 && end <= 7 && change.isDeleted())) {
                anchor++;
            }
        }

        while (newText.length() < 12) {
            newText.append('#');
        }

        if (newText.length() > 12) {
            newText.delete(12, newText.length());
        }

        text = newText.toString();
        anchor = Math.min(anchor, 12);
        caret = Math.min(caret, 12);

        change.setText(text);
        change.setRange(0, change.getControlText().length());
        change.setAnchor(anchor);
        change.setCaretPosition(caret);

        return change;
    }
}

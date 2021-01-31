package com.salesliant.util;

import com.salesliant.client.ClientController;
import com.salesliant.client.ClientView;
import com.salesliant.client.Config;
import com.salesliant.entity.Category;
import com.salesliant.entity.Category_;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemAttribute;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Item_;
import com.salesliant.entity.Promotion;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.Store;
import com.salesliant.entity.Tax;
import com.salesliant.entity.TaxClass;
import com.salesliant.entity.TaxClass_;
import com.salesliant.entity.TaxZone;
import com.salesliant.entity.TaxZone_;
import com.salesliant.entity.Tax_;
import com.salesliant.util.AppConstants.Response;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import static com.salesliant.util.InputDialog.getConfirmDialog;
import static com.salesliant.util.InputDialog.getDialog;
import static com.salesliant.util.InputDialog.getEditDialog;
import static com.salesliant.util.InputDialog.getNoYesResponseDialog;
import static com.salesliant.util.InputDialog.getSaveCancelResponseDialog;
import static com.salesliant.util.InputDialog.getSelectResponseDialog;
import static com.salesliant.util.InputDialog.getWarningDialog;
import static com.salesliant.util.InputDialog.getYesNoResponseDialog;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.commons.beanutils.BeanUtils;

public abstract class BaseListUI<T> {

    private static final Logger LOGGER = Logger.getLogger(BaseListUI.class.getName());

    protected ClientController controller;
    protected TableView<T> fTableView = new TableView<>();
    protected ObservableList<T> fEntityList;
    protected final Label lblWarning = new Label("");
    protected final Label lblWarning1 = new Label("");
    protected final Label lblLoading = new Label("Loading ...");
    protected final Label lblProcessing = new Label("Processing ...");
    protected final ChangeListener fListener;
    public InputDialog fInputDialog;
    public Dialog fDialog;
    protected T fEntity;
    protected Node mainView;
    protected Integer fIndex;
    protected BaseListUI base;
    protected Button saveBtn, okBtn, selectBtn, cancelBtn, resetBtn;
    protected ButtonType selectButtonType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
    protected ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    protected String dialogTitle = "";

    protected EventHandler<ActionEvent> fHandler = (ActionEvent event) -> {
        handleEvent(event);
    };
    protected EventHandler<KeyEvent> fKeyListener = (KeyEvent event) -> {
        handleEvent(event);
    };
    protected String lastKey = null;

    public BaseListUI() {
        fListener = (ChangeListener) new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                validate();
                lblWarning.setText("");
                lblWarning1.setText("");
            }
        };
        lblWarning.getStyleClass().add("lblWarning");
        lblLoading.getStyleClass().add("lblLoading");
        lblProcessing.getStyleClass().add("lblLoading");

    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public Node getView() {
        return mainView;
    }

    public TableView getTableView() {
        return fTableView;
    }

    public void setParent(BaseListUI base) {
        this.base = base;
    }

    public BaseListUI getParent() {
        return base;
    }

    protected Callback<TableView<T>, TableRow<T>> mouseClickListener(int i) {
        Callback cb = (Callback<TableView<T>, TableRow<T>>) (TableView<T> param) -> {
            final TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (row.getItem() != null) {
                    if (i == 1 && event.getClickCount() == 1) {
                        handleAction(AppConstants.ACTION_TABLE_EDIT);
                    } else if (i == 2 && event.getClickCount() == 2) {
                        handleAction(AppConstants.ACTION_EDIT);
                    } else if (i == 3 && event.getClickCount() == 1) {
                        handleAction(AppConstants.ACTION_TABLE_EDIT);
                    } else if (i == 3 && event.getClickCount() == 2) {
                        handleAction(AppConstants.ACTION_EDIT);
                    }
                }
            });
            return row;
        };
        return cb;
    }

    protected void handleEvent(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (((KeyEvent) event).getCode() == KeyCode.INSERT) {
                handleAction(AppConstants.ACTION_ADD);
            }
            if (((KeyEvent) event).getCode() == KeyCode.DELETE) {
                handleAction(AppConstants.ACTION_DELETE);
            }
        }
        if (event.getEventType() == ActionEvent.ACTION) {
            if (event.getSource() instanceof Button) {
                Button aButton = (Button) event.getSource();
                if (aButton.getId() == null ? AppConstants.ACTION_CLOSE == null : aButton.getId().equals(AppConstants.ACTION_CLOSE)) {
                    handleClose();
                } else if (aButton.getId() == null ? AppConstants.ACTION_REFRESH == null : aButton.getId().equals(AppConstants.ACTION_REFRESH)) {
                    refresh();
                } else {
                    handleAction(aButton.getId());
                }
            }
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            handleAction(AppConstants.ACTION_EDIT);
        }
        event.consume();
    }

    public abstract void handleAction(String code);

    protected void handleClose() {
        if (fInputDialog != null) {
            fInputDialog.close();
        }
        ClientView.closeTab(this.getClass().getName());
        Config.createEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    protected void closeDialog() {
        if (fInputDialog != null) {
            fInputDialog.close();
        }
    }

    protected void refresh() {
        String name = this.getClass().getName();
        Config.createEntityManager().getEntityManagerFactory().getCache().evictAll();
        ClientView.refreshTab(name);
    }

    protected void changeCase() {
    }

    protected void validate() {
    }

    protected void updateTotal() {
    }

    protected void addKeyListener() {
        fTableView.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.INSERT) {
                handleAction(AppConstants.ACTION_ADD);
            }
            if (t.getCode() == KeyCode.DELETE) {
                handleAction(AppConstants.ACTION_DELETE);
            }
            if (t.getCode() == KeyCode.UP) {
                handleAction(AppConstants.ACTION_TABLE_EDIT);
            }
            if (t.getCode() == KeyCode.DOWN) {
                handleAction(AppConstants.ACTION_TABLE_EDIT);
            }
            if (t.getCode() == KeyCode.F1) {
                handleAction(AppConstants.ACTION_SELECT_LIST);
            }
            if (t.getCode() == KeyCode.F2) {
                handleAction(AppConstants.ACTION_SHOW_COST);
            }
            if (t.getCode() == KeyCode.F3) {
                handleAction(AppConstants.ACTION_ASSIGN_SERIAL_NUMBER);
            }
            if (t.getCode() == KeyCode.F4) {
                handleAction(AppConstants.ACTION_UNASSIGN_SERIAL_NUMBER);
            }
            if (t.getCode() == KeyCode.F5) {
                handleAction(AppConstants.ACTION_TABLE_EDIT);
            }
            if (t.getCode() == KeyCode.F9) {
                handleAction(AppConstants.ACTION_SAVE);
            }
            if (t.getCode() == KeyCode.F10) {
                handleAction(AppConstants.ACTION_PROCESS);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.A) {
                handleAction(AppConstants.ACTION_EDIT);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.C) {
                handleAction(AppConstants.ACTION_CHANGE_CUSTOMER);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.E) {
                handleAction(AppConstants.ACTION_TABLE_EDIT);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.V) {
                handleAction(AppConstants.ACTION_VOID);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.L) {
                handleAction(AppConstants.ACTION_SELECT_LIST);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.N) {
                handleAction(AppConstants.ACTION_LINE_NOTE);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.S) {
                handleAction(AppConstants.ACTION_SAVE);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.X) {
                handleAction(AppConstants.ACTION_DELETE);
            }

            if (t.isControlDown() && t.getCode() == KeyCode.U) {
                handleAction(AppConstants.ACTION_MOVE_ROW_UP);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.D) {
                handleAction(AppConstants.ACTION_MOVE_ROW_DOWN);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.R) {
                handleAction(AppConstants.ACTION_RETURN);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.T && fTableView.getSelectionModel().getSelectedItem() != null) {
                fTableView.requestFocus();
                if (fTableView.getSelectionModel().getSelectedItem() instanceof SalesOrderEntry) {
                    SalesOrderEntry soe = (SalesOrderEntry) fTableView.getSelectionModel().getSelectedItem();
                    if (soe.getItem() != null) {
                        fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getColumns().get(2));
                    } else {
                        fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getColumns().get(0));
                    }
                }
                if (fTableView.getSelectionModel().getSelectedItem() instanceof PurchaseOrderEntry) {
                    PurchaseOrderEntry poe = (PurchaseOrderEntry) fTableView.getSelectionModel().getSelectedItem();
                    if (poe.getItem() != null) {
                        fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getColumns().get(3));
                    } else {
                        fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getColumns().get(0));
                    }
                }
            }
        });
    }

    protected void addKeyListener(TableView<T> tv) {
        tv.setOnKeyPressed((KeyEvent t) -> {
            TablePosition tp;
            if (!t.isControlDown() && (t.getCode().isLetterKey() || t.getCode().isDigitKey())) {
                lastKey = t.getText();
                tp = tv.getFocusModel().getFocusedCell();
                tv.edit(tp.getRow(), tp.getTableColumn());
                lastKey = null;
            }
        });

        tv.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.INSERT) {
                handleAction(AppConstants.ACTION_ADD);
            }
            if (t.getCode() == KeyCode.F1) {
                handleAction(AppConstants.ACTION_F1);
            }
            if (t.getCode() == KeyCode.F2) {
                handleAction(AppConstants.ACTION_F2);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.N) {
                handleAction(AppConstants.ACTION_LINE_NOTE);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.S) {
                handleAction(AppConstants.ACTION_SAVE);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.F10) {
                handleAction(AppConstants.ACTION_PROCESS);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.P) {
                handleAction(AppConstants.ACTION_PACKAGE_START);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.E) {
                handleAction(AppConstants.ACTION_PACKAGE_END);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.V) {
                handleAction(AppConstants.ACTION_VOID);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.L) {
                handleAction(AppConstants.ACTION_SELECT_LIST);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.X) {
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.getItems().remove(i);
                    if (i >= 1) {
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i - 1);
                        fTableView.getFocusModel().focus(i - 1, fTableView.getColumns().get(0));
                    }
                }
            }
            if (t.isControlDown() && t.getCode() == KeyCode.E) {
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(i);
                    fTableView.getFocusModel().focus(i, fTableView.getColumns().get(0));
                }
            }
            if (t.isControlDown() && t.getCode() == KeyCode.U) {
                if (tv.getSelectionModel().getSelectedItem() != null) {
                    int i = tv.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1) {
                        Collections.swap(tv.getItems(), i, i - 1);
                        tv.requestFocus();
                        tv.getSelectionModel().select(i - 1);
                        tv.getFocusModel().focus(i - 1, tv.getColumns().get(0));
                    }
                }
            }
            if (t.isControlDown() && t.getCode() == KeyCode.D) {
                if (tv.getSelectionModel().getSelectedItem() != null) {
                    int i = tv.getFocusModel().getFocusedCell().getRow();
                    if (i < tv.getItems().size() - 1) {
                        Collections.swap(tv.getItems(), i, i + 1);
                        tv.requestFocus();
                        tv.getSelectionModel().select(i + 1);
                        tv.getFocusModel().focus(i + 1, tv.getColumns().get(0));
                    }
                }
            }
        });
    }

    protected HBox createNewEditDeleteCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    protected HBox createNewEditDeleteButtonPane(String add, String edit, String delete) {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        newButton.setId(add);
        editButton.setId(edit);
        deleteButton.setId(delete);
        newButton.setOnAction(fHandler);
        editButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected TextField createLabelField(double i, Pos pos) {
        TextField textField = new TextField();
        textField.setPrefWidth(i);
        textField.setAlignment(pos);
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        textField.setBorder(Border.EMPTY);
        textField.setBackground(Background.EMPTY);
        return textField;
    }

    protected TextField createLabelField() {
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setFocusTraversable(false);
        textField.setBorder(Border.EMPTY);
        textField.setBackground(Background.EMPTY);
        return textField;
    }

    protected Label createLabel(String s, double i, Pos pos) {
        Label label = new Label();
        label.setText(s);
        label.setPrefWidth(i);
        label.setAlignment(pos);
        return label;
    }

    protected Label createLabel(String s, Pos pos) {
        Label label = new Label();
        label.setText(s);
        label.setAlignment(pos);
        return label;
    }

    protected String addToString(Object input, String label) {
        String result;
        if (label.equals("\n")) {
            if (input == null) {
                result = "";
            } else {
                result = input.toString() + "\n";
            }
        } else {
            if (input == null) {
                result = label + "\n";
            } else {
                if (input instanceof BigDecimal) {
                    input = ((BigDecimal) input).setScale(2, RoundingMode.HALF_UP);
                    result = label + ((BigDecimal) input).doubleValue() + "\n";
                } else if (input instanceof Date) {
                    result = label + DateFormat.getDateInstance().format((Date) input) + "\n";
                } else {
                    result = label + input.toString() + "\n";
                }
            }
        }
        return result;
    }

    public static void add(GridPane pane, String label, TextField field, int row) {
        Label fieldLabel = new Label(label);
        pane.add(fieldLabel, 0, row);
        pane.add(field, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(field, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, TextField field, ChangeListener listener, int row) {
        Label fieldLabel = new Label(label);
        field.textProperty().addListener(listener);
        pane.add(fieldLabel, 0, row);
        pane.add(field, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(field, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, TextField field, ChangeListener listener, double width, int row) {
        Label fieldLabel = new Label(label);
        field.textProperty().addListener(listener);
        field.setPrefWidth(width);
        pane.add(fieldLabel, 0, row);
        pane.add(field, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(field, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, CheckBox check, ChangeListener listener, int row) {
        Label fieldLabel = new Label(label);
        fieldLabel.setMinWidth(label.length());
        check.selectedProperty().addListener(listener);
        pane.add(fieldLabel, 0, row);
        pane.add(check, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(check, HPos.LEFT);
    }

    protected void add(GridPane pane, CheckBox check, String label, ChangeListener listener, int row) {
        Label fieldLabel = new Label(label);
        fieldLabel.setMinWidth(label.length());
        check.selectedProperty().addListener(listener);
        pane.add(fieldLabel, 1, row);
        pane.add(check, 0, row);
        GridPane.setHalignment(fieldLabel, HPos.LEFT);
        GridPane.setHalignment(check, HPos.RIGHT);
    }

    protected void add(GridPane pane, String label, ChoiceBox choice, ChangeListener listener, int row) {
        Label fieldLabel = new Label(label);
        fieldLabel.setMinWidth(label.length());
        choice.getSelectionModel().selectedIndexProperty().addListener(listener);
        pane.add(fieldLabel, 0, row);
        pane.add(choice, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(choice, HPos.LEFT);
    }

    protected void add(GridPane pane, CheckBox check, String label, int iWidth, ChangeListener listener, int row) {
        Label fieldLabel = new Label(label);
        fieldLabel.setPrefWidth(iWidth);
        check.selectedProperty().addListener(listener);
        pane.add(fieldLabel, 1, row);
        pane.add(check, 0, row);
        GridPane.setHalignment(fieldLabel, HPos.LEFT);
        GridPane.setHalignment(check, HPos.RIGHT);
    }

    protected void add(GridPane pane, String label, TextField[] fields, ChangeListener listener, int row) {
        Label fieldLabel = new Label(label);
        HBox rightBox = new HBox();
        for (TextField tf : fields) {
            tf.textProperty().addListener(listener);
        }
        rightBox.getChildren().addAll(Arrays.asList(fields));
        pane.add(fieldLabel, 0, row);
        pane.add(rightBox, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(rightBox, HPos.LEFT);
    }

    protected void add(GridPane pane, CheckBox box, String label, int row) {
        Label boxLabel = new Label(label);
        pane.add(box, 0, row);
        pane.add(boxLabel, 1, row);
        GridPane.setHalignment(boxLabel, HPos.LEFT);
        GridPane.setHalignment(box, HPos.RIGHT);
    }

    protected void add(GridPane pane, String label, TextArea box, int iHigh, int iWidth, ChangeListener listener, int row) {
        Label boxLabel = new Label(label);
        box.setPrefHeight(iHigh);
        box.setPrefWidth(iWidth);
        box.textProperty().addListener(listener);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setValignment(boxLabel, VPos.TOP);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, Node box, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, DatePicker datePicker, ChangeListener listener, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(datePicker, 1, row);
        datePicker.valueProperty().addListener(listener);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(datePicker, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, DateTimePicker dateTimePicker, ChangeListener listener, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(dateTimePicker, 1, row);
        dateTimePicker.valueProperty().addListener(listener);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(dateTimePicker, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, ComboBox box, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, ComboBox box, ChangeListener listener, int row) {
        Label boxLabel = new Label(label);
        box.getSelectionModel().selectedItemProperty().addListener(listener);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, ComboBox box, int iWidth, ChangeListener listener, int row) {
        Label boxLabel = new Label(label);
        box.getSelectionModel().selectedItemProperty().addListener(listener);
        box.setPrefWidth(iWidth);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected Control addHLine(int i) {
        Separator s = new Separator();
        s.setFocusTraversable(false);
        s.setOrientation(Orientation.HORIZONTAL);
        s.setHalignment(HPos.CENTER);
        s.setPrefHeight(i);
        s.setDisable(true);
        return s;
    }

    protected Control addVgap(int i) {
        Label label = new Label();
        label.setFocusTraversable(false);
        label.setPrefHeight(i);
        label.setDisable(true);
        return label;
    }

    protected <S, T> TableColumn<S, T> addTableColumn(TableView<S> tableView, String header, double prefWidth, Function<S, ObservableValue<T>> valueFunction) {
        TableColumn<S, T> column = new TableColumn<>(header);
        column.setPrefWidth(prefWidth);
        column.setCellValueFactory((CellDataFeatures<S, T> cellData) -> valueFunction.apply(cellData.getValue()));
        tableView.getColumns().add(column);
        return column;
    }

    protected <S, T> TableColumn<S, T> addTableColumn(TableView<S> tableView, String header, double prefWidth, Callback propertyCallback, Callback cellCallback) {
        TableColumn<S, T> column = new TableColumn<>(header);
        column.setPrefWidth(prefWidth);
        column.setCellValueFactory(propertyCallback);
        column.setCellFactory(cellCallback);
        tableView.getColumns().add(column);
        return column;
    }

    protected Calendar addBusinessDay(final Calendar cal, final Integer numBusinessDays) {
        if (cal == null || numBusinessDays == null || numBusinessDays == 0) {
            return cal;
        }
        final int numDays = Math.abs(numBusinessDays);
        final int dateAddition = numBusinessDays < 0 ? -1 : 1;// if
        int businessDayCount = 0;
        while (businessDayCount < numDays) {
            cal.add(Calendar.DATE, dateAddition);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                continue;// adds another day
            }
            businessDayCount++;
        }
        return cal;
    }

    protected Date addDay(Date date, Integer numDays) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, numDays);
        Date newDate = c.getTime();
        return newDate;
    }

    public static String getBillToAddress(Customer customer) {
        String billTo = "";
        if (customer != null) {
            billTo = billTo + (!isEmpty(customer.getFirstName()) ? customer.getFirstName() : "")
                    + (!isEmpty(customer.getFirstName()) ? " " : "")
                    + (!isEmpty(customer.getLastName()) ? customer.getLastName() : "")
                    + "\n";
            if (!isEmpty(customer.getCompany())) {
                billTo = billTo + customer.getCompany() + "\n";
            }
            if (!isEmpty(customer.getAddress1())) {
                billTo = billTo + customer.getAddress1() + "\n";
            }
            if (!isEmpty(customer.getAddress2())) {
                billTo = billTo + customer.getAddress2() + "\n";
            }
            billTo = billTo + (!isEmpty(customer.getCity()) ? customer.getCity() : "")
                    + (!isEmpty(customer.getCity()) ? ", " : "")
                    + (!isEmpty(customer.getState()) ? customer.getState() : "")
                    + (!isEmpty(customer.getState()) ? " " : "")
                    + (!isEmpty(customer.getPostCode()) ? customer.getPostCode() : "")
                    + "\n";
            if (customer.getCountry() != null) {
                billTo = billTo + customer.getCountry().getIsoCode3() + "\n";
            } else if (customer.getStore() != null && customer.getStore().getCountry() != null) {
                billTo = billTo + customer.getStore().getCountry().getIsoCode3() + "\n";
            }
            if (!isEmpty(customer.getPhoneNumber())) {
                billTo = billTo + "Phone: " + customer.getPhoneNumber() + "\n";
            }
        }
        String billToAddress = billTo.trim();
        return billToAddress;
    }

    public static String getBillToAddress(Invoice invoice) {
        String billTo = "";
        if (invoice != null) {
            String bname = invoice.getCustomerName();
            if (!bname.isEmpty()) {
                billTo = billTo + bname + "\n";
            }
            if (!isEmpty(invoice.getBillToCompany())) {
                billTo = billTo + invoice.getBillToCompany() + "\n";
            }
            if (!isEmpty(invoice.getBillToAddress1())) {
                billTo = billTo + invoice.getBillToAddress1() + "\n";
            }
            if (!isEmpty(invoice.getBillToAddress2())) {
                billTo = billTo + invoice.getBillToAddress2() + "\n";
            }
            billTo = billTo + (!isEmpty(invoice.getBillToCity()) ? invoice.getBillToCity() : "")
                    + (!isEmpty(invoice.getBillToCity()) ? ", " : "")
                    + (!isEmpty(invoice.getBillToState()) ? invoice.getBillToState() : "")
                    + (!isEmpty(invoice.getBillToState()) ? " " : "")
                    + (!isEmpty(invoice.getBillToPostCode()) ? invoice.getBillToPostCode() : "")
                    + "\n";
            if (invoice.getBillToCountry() != null && !invoice.getBillToCountry().isEmpty()) {
                billTo = billTo + invoice.getBillToCountry() + "\n";
            } else if (invoice.getStore() != null && invoice.getStore().getCountry() != null) {
                billTo = billTo + invoice.getStore().getCountry().getIsoCode3() + "\n";
            }
            if (!isEmpty(invoice.getPhoneNumber())) {
                billTo = billTo + "Phone: " + invoice.getPhoneNumber() + "\n";
            }
        }
        String billToAddress = billTo.trim();
        return billToAddress;
    }

    public static String getBillToAddress(CustomerBuyer buyer) {
        Customer customer = buyer.getCustomer();
        String billTo = "";
        if (customer != null) {
            String bname = (!isEmpty(buyer.getFirstName()) ? buyer.getFirstName() : "")
                    + (!isEmpty(buyer.getFirstName()) ? " " : "")
                    + (!isEmpty(buyer.getLastName()) ? buyer.getLastName() : "");
            String cname = (!isEmpty(customer.getFirstName()) ? customer.getFirstName() : "")
                    + (!isEmpty(customer.getFirstName()) ? " " : "")
                    + (!isEmpty(customer.getLastName()) ? customer.getLastName() : "");
            if (!bname.isEmpty()) {
                billTo = billTo + bname + "\n";
            } else {
                billTo = billTo + cname + "\n";
            }
            if (!isEmpty(customer.getCompany())) {
                billTo = billTo + customer.getCompany() + "\n";
            }
            if (!isEmpty(customer.getAddress1())) {
                billTo = billTo + customer.getAddress1() + "\n";
            }
            if (!isEmpty(customer.getAddress2())) {
                billTo = billTo + customer.getAddress2() + "\n";
            }
            billTo = billTo + (!isEmpty(customer.getCity()) ? customer.getCity() : "")
                    + (!isEmpty(customer.getCity()) ? ", " : "")
                    + (!isEmpty(customer.getState()) ? customer.getState() : "")
                    + (!isEmpty(customer.getState()) ? " " : "")
                    + (!isEmpty(customer.getPostCode()) ? customer.getPostCode() : "")
                    + "\n";
            if (customer.getCountry() != null) {
                billTo = billTo + customer.getCountry().getIsoCode3() + "\n";
            } else if (customer.getStore() != null && customer.getStore().getCountry() != null) {
                billTo = billTo + customer.getStore().getCountry().getIsoCode3() + "\n";
            }
            if (!isEmpty(buyer.getPhoneNumber())) {
                billTo = billTo + "Phone: " + buyer.getPhoneNumber() + "\n";
            } else if (!isEmpty(customer.getPhoneNumber())) {
                billTo = billTo + "Phone: " + customer.getPhoneNumber() + "\n";
            }
        }
        String billToAddress = billTo.trim();
        return billToAddress;
    }

    public static String getShipToAddress(CustomerShipTo customerShipTo) {
        String shipTo = "";
        if (customerShipTo != null) {
            if (!isEmpty(customerShipTo.getContactName())) {
                shipTo = shipTo + customerShipTo.getContactName() + "\n";
            }
            if (!isEmpty(customerShipTo.getCompany())) {
                shipTo = shipTo + customerShipTo.getCompany() + "\n";
            }
            if (!isEmpty(customerShipTo.getAddress1())) {
                shipTo = shipTo + customerShipTo.getAddress1() + "\n";
            }
            if (!isEmpty(customerShipTo.getAddress2())) {
                shipTo = shipTo + customerShipTo.getAddress2() + "\n";
            }
            shipTo = shipTo + (!isEmpty(customerShipTo.getCity()) ? customerShipTo.getCity() : "")
                    + (!isEmpty(customerShipTo.getCity()) ? ", " : "")
                    + (!isEmpty(customerShipTo.getState()) ? customerShipTo.getState() : "")
                    + (!isEmpty(customerShipTo.getState()) ? " " : "")
                    + (!isEmpty(customerShipTo.getPostCode()) ? customerShipTo.getPostCode() : "")
                    + "\n";
            if (customerShipTo.getCountry() != null) {
                shipTo = shipTo + customerShipTo.getCountry().getIsoCode3() + "\n";
            }
            if (!isEmpty(customerShipTo.getPhoneNumber())) {
                shipTo = shipTo + "Phone: " + customerShipTo.getPhoneNumber() + "\n";
            }
        }
        String shipToAddress = shipTo.trim();
        return shipToAddress;
    }

    public static String getStoreShipToAddress() {
        String shipTo = "";
        if (!isEmpty(Config.getStore().getStoreName())) {
            shipTo = shipTo + Config.getStore().getStoreName() + "\n";
        }
        if (!isEmpty(Config.getStore().getAddress1())) {
            shipTo = shipTo + Config.getStore().getAddress1() + "\n";
        }
        if (!isEmpty(Config.getStore().getAddress2())) {
            shipTo = shipTo + Config.getStore().getAddress2() + "\n";
        }
        shipTo = shipTo + (!isEmpty(Config.getStore().getCity()) ? Config.getStore().getCity() : "")
                + (!isEmpty(Config.getStore().getCity()) ? ", " : "")
                + (!isEmpty(Config.getStore().getState()) ? Config.getStore().getState() : "")
                + (!isEmpty(Config.getStore().getState()) ? " " : "")
                + (!isEmpty(Config.getStore().getPostCode()) ? Config.getStore().getPostCode() : "")
                + "\n";
        if (!isEmpty(Config.getStore().getCountry().getIsoCode3())) {
            shipTo = shipTo + Config.getStore().getCountry().getIsoCode3() + "\n";
        }
        if (!isEmpty(Config.getStore().getPhoneNumber())) {
            shipTo = shipTo + "Phone: " + Config.getStore().getPhoneNumber() + "\n";
        }
        return shipTo;
    }

    public static BigDecimal getItemPrice(Item item, Customer customer) {
        BigDecimal price, itemPrice, promotionPrice;
        if (item != null && item.getItemType() != null) {
            if (item.getItemType() != DBConstants.ITEM_TYPE_BOM) {
                if (customer != null && Config.getStore().getDefaultCustomerPriceMethod() != null) {
                    if (Config.getStore().getDefaultCustomerPriceMethod().compareTo(DBConstants.TYPE_ITEM_PRICE_METHOD_BY_PRICE_LEVEL) == 0
                            && customer.getPriceLevel() != null && customer.getPriceLevel().getCode() != null) {
                        try {
                            Field itemPriceField, promotionPriceField;
                            itemPriceField = item.getClass().getDeclaredField(customer.getPriceLevel().getCode());
                            itemPriceField.setAccessible(true);
                            itemPrice = (BigDecimal) itemPriceField.get(item);
                            price = itemPrice;
                            if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                                for (Promotion p : item.getPromotions()) {
                                    promotionPriceField = p.getClass().getDeclaredField(customer.getPriceLevel().getCode());
                                    promotionPriceField.setAccessible(true);
                                    promotionPrice = (BigDecimal) promotionPriceField.get(p);
                                    if (itemPrice != null && promotionPrice != null && promotionPrice.compareTo(itemPrice) < 0 && p.getStartTime() != null && p.getEndTime() != null) {
                                        Date todayDate = new Date();
                                        if (todayDate.after(p.getStartTime()) && todayDate.before(p.getEndTime())) {
                                            price = promotionPrice;
                                        }
                                    } else if (itemPrice == null) {
                                        price = item.getPrice1();
                                    } else {
                                        price = itemPrice;
                                    }
                                }
                            } else {
                                price = itemPrice;
                            }
                            price = price.setScale(2, RoundingMode.HALF_UP);
                            return price;
                        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    } else if (Config.getStore().getDefaultCustomerPriceMethod().compareTo(DBConstants.TYPE_ITEM_PRICE_METHOD_BY_GROUP_DISCOUNT) == 0
                            && customer.getCustomerGroup() != null && customer.getCustomerGroup().getDiscount() != null
                            && customer.getCustomerGroup().getDiscount().compareTo(BigDecimal.ZERO) > 0 && item.getPrice1() != null) {
                        itemPrice = item.getPrice1().multiply(BigDecimal.ONE.subtract(customer.getCustomerGroup().getDiscount()));
                        price = itemPrice;
                        if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                            for (Promotion p : item.getPromotions()) {
                                promotionPrice = p.getPrice1();
                                if (promotionPrice.compareTo(itemPrice) < 0) {
                                    price = promotionPrice;
                                } else {
                                    price = itemPrice;
                                }
                            }
                        } else {
                            price = itemPrice;
                        }
                        price = price.setScale(2, RoundingMode.HALF_UP);
                        return price;
                    } else {
                        if (item.getPrice1() != null) {
                            itemPrice = item.getPrice1();
                            price = itemPrice;
                            if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                                for (Promotion p : item.getPromotions()) {
                                    promotionPrice = p.getPrice1();
                                    if (promotionPrice.compareTo(itemPrice) < 0) {
                                        price = promotionPrice;
                                    } else {
                                        price = itemPrice;
                                    }
                                }
                            } else {
                                price = itemPrice;
                            }
                            price = price.setScale(2, RoundingMode.HALF_UP);
                            return price;
                        } else {
                            return null;
                        }
                    }
                } else {
                    if (item.getPrice1() != null) {
                        itemPrice = item.getPrice1();
                        price = itemPrice;
                        if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                            for (Promotion p : item.getPromotions()) {
                                promotionPrice = p.getPrice1();
                                if (promotionPrice.compareTo(itemPrice) < 0) {
                                    price = promotionPrice;
                                } else {
                                    price = itemPrice;
                                }
                            }
                        } else {
                            price = itemPrice;
                        }
                        price = price.setScale(2, RoundingMode.HALF_UP);
                        return price;
                    } else {
                        return null;
                    }
                }
                if (item.getPrice1() != null) {
                    itemPrice = item.getPrice1();
                    price = itemPrice;
                    if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                        for (Promotion p : item.getPromotions()) {
                            promotionPrice = p.getPrice1();
                            if (promotionPrice.compareTo(itemPrice) < 0) {
                                price = promotionPrice;
                            } else {
                                price = itemPrice;
                            }
                        }
                    } else {
                        price = itemPrice;
                    }
                    price = price.setScale(2, RoundingMode.HALF_UP);
                    return price;
                } else {
                    return null;
                }
            } else {
                if (item.getPrice1() != null) {
                    itemPrice = item.getPrice1();
                    price = itemPrice;
                    if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                        for (Promotion p : item.getPromotions()) {
                            promotionPrice = p.getPrice1();
                            if (promotionPrice.compareTo(itemPrice) < 0) {
                                price = promotionPrice;
                            } else {
                                price = itemPrice;
                            }
                        }
                    } else {
                        price = itemPrice;
                    }
                    price = price.setScale(2, RoundingMode.HALF_UP);
                    return price;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public static BigDecimal getQuantityDiscountPrice(Item item, Customer customer, BigDecimal qty) {
        BigDecimal price;
        price = getItemPrice(item, customer);
        if (item != null && price != null && item.getQuantityDiscount() != null && item.getItemType() != null && item.getItemType() != DBConstants.ITEM_TYPE_BOM && customer != null && Config.getStore().getDefaultCustomerPriceMethod() != null) {
            if (Config.getStore().getDefaultCustomerPriceMethod().compareTo(DBConstants.TYPE_ITEM_PRICE_METHOD_BY_PRICE_LEVEL) == 0
                    && customer.getPriceLevel() != null && customer.getPriceLevel().getCode() != null) {
                if (Config.getItemPriceLevel().get(0) != null && Config.getItemPriceLevel().get(0).getCode() != null
                        && customer.getPriceLevel().getCode().equalsIgnoreCase(Config.getItemPriceLevel().get(0).getCode())) {
                    if (item.getQuantityDiscount().getQuantity1() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity1()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice11() != null && item.getQuantityDiscount().getPrice11().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice11();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity2() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity2()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice12() != null && item.getQuantityDiscount().getPrice12().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice12();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity3() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity3()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice13() != null && item.getQuantityDiscount().getPrice13().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice13();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity4() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity4()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice14() != null && item.getQuantityDiscount().getPrice14().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice14();
                        }
                    }
                }
                if (Config.getItemPriceLevel().get(1) != null && Config.getItemPriceLevel().get(1).getCode() != null
                        && customer.getPriceLevel().getCode().equalsIgnoreCase(Config.getItemPriceLevel().get(1).getCode())) {
                    if (item.getQuantityDiscount().getQuantity1() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity1()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice21() != null && item.getQuantityDiscount().getPrice21().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice21();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity2() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity2()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice22() != null && item.getQuantityDiscount().getPrice22().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice22();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity3() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity3()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice23() != null && item.getQuantityDiscount().getPrice23().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice23();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity4() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity4()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice24() != null && item.getQuantityDiscount().getPrice24().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice24();
                        }
                    }
                }
                if (Config.getItemPriceLevel().get(2) != null && Config.getItemPriceLevel().get(2).getCode() != null
                        && customer.getPriceLevel().getCode().equalsIgnoreCase(Config.getItemPriceLevel().get(2).getCode())) {
                    if (item.getQuantityDiscount().getQuantity1() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity1()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice31() != null && item.getQuantityDiscount().getPrice31().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice31();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity2() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity2()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice32() != null && item.getQuantityDiscount().getPrice32().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice32();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity3() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity3()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice33() != null && item.getQuantityDiscount().getPrice33().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice33();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity4() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity4()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice34() != null && item.getQuantityDiscount().getPrice34().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice34();
                        }
                    }
                }
                if (Config.getItemPriceLevel().get(3) != null && Config.getItemPriceLevel().get(3).getCode() != null
                        && customer.getPriceLevel().getCode().equalsIgnoreCase(Config.getItemPriceLevel().get(3).getCode())) {
                    if (item.getQuantityDiscount().getQuantity1() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity1()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice41() != null && item.getQuantityDiscount().getPrice41().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice41();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity2() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity2()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice42() != null && item.getQuantityDiscount().getPrice42().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice42();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity3() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity3()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice43() != null && item.getQuantityDiscount().getPrice43().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice43();
                        }
                    }
                    if (item.getQuantityDiscount().getQuantity4() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity4()).compareTo(qty) <= 0) {
                        if (item.getQuantityDiscount().getPrice44() != null && item.getQuantityDiscount().getPrice44().compareTo(price) < 0) {
                            price = item.getQuantityDiscount().getPrice44();
                        }
                    }
                }
                return price;
            } else {
                if (item.getQuantityDiscount().getQuantity1() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity1()).compareTo(qty) <= 0) {
                    if (item.getQuantityDiscount().getPrice11() != null && item.getQuantityDiscount().getPrice11().compareTo(price) < 0) {
                        price = item.getQuantityDiscount().getPrice11();
                    }
                }
                if (item.getQuantityDiscount().getQuantity2() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity2()).compareTo(qty) <= 0) {
                    if (item.getQuantityDiscount().getPrice12() != null && item.getQuantityDiscount().getPrice12().compareTo(price) < 0) {
                        price = item.getQuantityDiscount().getPrice12();
                    }
                }
                if (item.getQuantityDiscount().getQuantity3() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity3()).compareTo(qty) <= 0) {
                    if (item.getQuantityDiscount().getPrice13() != null && item.getQuantityDiscount().getPrice13().compareTo(price) < 0) {
                        price = item.getQuantityDiscount().getPrice13();
                    }
                }
                if (item.getQuantityDiscount().getQuantity4() != null && BigDecimal.valueOf(item.getQuantityDiscount().getQuantity4()).compareTo(qty) <= 0) {
                    if (item.getQuantityDiscount().getPrice14() != null && item.getQuantityDiscount().getPrice14().compareTo(price) < 0) {
                        price = item.getQuantityDiscount().getPrice14();
                    }
                }
                return price;
            }

        } else {
            return price;
        }
    }

    public static String getItemDescription(Item item) {
        if (item == null) {
            return "";
        } else {
            String description = getString(item.getDescription());
            if (item.getItemAttributes() != null && !item.getItemAttributes().isEmpty()) {
                List<ItemAttribute> list = item.getItemAttributes()
                        .stream()
                        .sorted((e1, e2) -> getString(e1.getDisplayOrder()).compareTo(getString(e2.getDisplayOrder())))
                        .collect(Collectors.toList());
                for (int i = 0; i < list.size(); i++) {
                    description = description + " " + list.get(i).getItemAttributeValue().getItemAttributeType().getDescription() + ":" + list.get(i).getItemAttributeValue().getDescription();
                }
            }
            return description;
        }
    }

    public static Item getItem(String itemLookupCode) {
        if (itemLookupCode == null || itemLookupCode.isEmpty()) {
            return null;
        } else {
            BaseDao<Item> daoItem = new BaseDao(Item.class);
            Item item = daoItem.find(Item_.itemLookUpCode, itemLookupCode);
            return item;
        }
    }

    public static Category getCategory(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return null;
        } else {
            BaseDao<Category> daoCategory = new BaseDao(Category.class);
            Category category = daoCategory.find(Category_.name, categoryName);
            return category;
        }
    }

    public static Tax getTax(TaxClass tc, TaxZone taxZone) {
        if (tc != null && taxZone != null) {
            BaseDao<Tax> daoTax = new BaseDao(Tax.class);
            Tax tax = daoTax.find(Tax_.taxClass, tc.getId(), Tax_.taxZone, taxZone.getId());
            return tax;
        }
        return null;
    }

    public static TaxZone getTaxZone(Invoice invoice) {
        BaseDao<TaxZone> daoTaxZone = new BaseDao(TaxZone.class);
        if (invoice != null && invoice.getTaxZoneName() != null && !invoice.getTaxZoneName().isEmpty()) {
            TaxZone taxZone = daoTaxZone.find(TaxZone_.name, invoice.getTaxZoneName());
            return taxZone;
        } else {
            return Config.getStore().getDefaultTaxZone();
        }
    }

    public static TaxClass getTaxClass(InvoiceEntry ie) {
        BaseDao<TaxClass> daoTaxClass = new BaseDao(TaxClass.class);
        BaseDao<Item> daoItem = new BaseDao(Item.class);
        if (ie != null) {
            if (ie.getTaxClassName() != null && !ie.getTaxClassName().isEmpty()) {
                TaxClass taxClass = daoTaxClass.find(TaxClass_.name, ie.getTaxClassName());
                return taxClass;
            } else {
                Item item = daoItem.find(Item_.itemLookUpCode, ie.getItemLookUpCode());
                if (item != null && item.getCategory() != null && item.getCategory().getTaxClass() != null) {
                    return item.getCategory().getTaxClass();
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public static Tax getTax(Item item, TaxZone taxZone) {
        BaseDao<Tax> daoTax = new BaseDao(Tax.class);
        if (item != null && item.getCategory() != null && item.getCategory().getTaxClass() != null
                && taxZone != null) {
            Tax tax = daoTax.find(Tax_.taxClass, item.getCategory().getTaxClassId(), Tax_.taxZone, taxZone.getId());
            return tax;
        } else {
            TaxClass tc = Config.getStore().getDefaultTaxClass();
            TaxZone tz = Config.getStore().getDefaultTaxZone();
            if (tc != null && tz != null) {
                Tax tax = daoTax.find(Tax_.taxClass, tc.getId(), Tax_.taxZone, tz.getId());
                return tax;
            } else {
                return null;
            }
        }

    }

    public static Employee getEmployee(String name) {
        if (name != null && !name.isEmpty()) {
            BaseDao<Employee> daoEmployee = new BaseDao(Employee.class);
            Employee employee = daoEmployee.find(Employee_.store, Config.getStore(), Employee_.nameOnSalesOrder, name);
            if (employee != null) {
                return employee;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Tax getTax(Item item, Customer customer) {
        if (item != null && item.getCategory() != null && item.getCategory().getTaxClass() != null
                && customer != null && customer.getTaxZone() != null && customer.getTaxZoneId() != null
                && customer.getTaxExempt() != null && !customer.getTaxExempt()) {
            BaseDao<Tax> daoTax = new BaseDao(Tax.class);
            Tax tax = daoTax.find(Tax_.taxClass, item.getCategory().getTaxClassId(), Tax_.taxZone, customer.getTaxZoneId());
            return tax;
        }
        return null;
    }

    public static Tax getTax(Item item, CustomerShipTo customerShipTo) {
        if (item != null && item.getCategory() != null && item.getCategory().getTaxClassId() != null
                && customerShipTo != null && customerShipTo.getTaxZone() != null && customerShipTo.getTaxZoneId() != null) {
            BaseDao<Tax> daoTax = new BaseDao(Tax.class);
            Tax tax = daoTax.find(Tax_.taxClass, item.getCategory().getTaxClassId(), Tax_.taxZone, customerShipTo.getTaxZoneId());
            return tax;
        }
        return null;
    }

    public static ItemQuantity getItemQuantity(Item item) {
        if (item != null) {
            ItemQuantity iq = item.getItemQuantities().stream()
                    .filter(e -> e.getStore() != null && e.getStore().getId().compareTo(Config.getStore().getId()) == 0 && e.getQuantity() != null)
                    .findFirst().orElse(null);
            return iq;
        } else {
            return null;
        }
    }

    public static ItemQuantity getItemQuantityByStore(Item item, Store store) {
        if (item != null) {
            ItemQuantity iq = item.getItemQuantities().stream()
                    .filter(e -> e.getStore() != null && e.getStore().getId().compareTo(store.getId()) == 0 && e.getQuantity() != null)
                    .findFirst().orElse(null);
            return iq;
        } else {
            return null;
        }
    }

    public static BigDecimal getMarkUpPercent(Item item) {
        if (item != null && item.getCost() != null && item.getPrice1() != null && item.getCost().compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal markUp = (item.getPrice1().subtract(item.getCost())).divide(item.getCost(), 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
            return markUp;
        } else {
            return new BigDecimal(9999);
        }
    }

    public static BigDecimal getQuantity(Item item) {
        if (item != null && item.getItemQuantities() != null && !item.getItemQuantities().isEmpty()) {
            ItemQuantity iq = item.getItemQuantities().stream()
                    .filter(e -> e.getStore() != null && e.getStore().getId().compareTo(Config.getStore().getId()) == 0 && e.getQuantity() != null)
                    .findFirst().orElse(null);
            if (iq != null) {
                return iq.getQuantity();
            } else {
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getQuantityCommitted(Item item) {
        if (item != null && !item.getSalesOrderEntries().isEmpty()) {
            BigDecimal quantityCommitted = item.getSalesOrderEntries().stream()
                    .filter(g -> g.getItem().getId().equals(item.getId()) && g.getSalesOrder().getStore().getId().equals(Config.getStore().getId()) && (g.getSalesOrder().getType().equals(DBConstants.TYPE_SALESORDER_ORDER) || g.getSalesOrder().getType().equals(DBConstants.TYPE_SALESORDER_SERVICE)))
                    .map((p) -> zeroIfNull(p.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
            return quantityCommitted;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getQuantityOnOrder(Item item) {
        if (item != null && !item.getPurchaseOrderEntries().isEmpty()) {
            BigDecimal quantityOnOrder = item.getPurchaseOrderEntries().stream()
                    .filter(g -> g.getItem().getId().equals(item.getId()) && g.getPurchaseOrder().getStatus().equals(DBConstants.STATUS_IN_PROGRESS))
                    .map(p -> zeroIfNull(p.getQuantityOrdered())).reduce(BigDecimal.ZERO, BigDecimal::add);
            return quantityOnOrder;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getQuantityOnRMA(Item item) {
        if (item != null && !item.getReturnOrderEntries().isEmpty()) {
            BigDecimal quantityOnRMA = item.getReturnTransactions().stream()
                    .filter(g -> g.getItem().getId().equals(item.getId()))
                    .map(p -> zeroIfNull(p.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
            return quantityOnRMA;
        } else {
            return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getQuantityOnTransfer(Item item) {
        if (item != null && !item.getTransferOrderEntries().isEmpty()) {
            BigDecimal quantityOnTransfer = item.getTransferOrderEntries().stream()
                    .filter(g -> g.getItem().getId().equals(item.getId()))
                    .map(p -> zeroIfNull(p.getQuantity())).reduce(BigDecimal.ZERO, BigDecimal::add);
            return quantityOnTransfer;
        } else {
            return BigDecimal.ZERO;
        }
    }

    protected InputDialog createSaveCancelUIDialog(Node node, String title) {
        lblWarning.setText("");
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        return getEditDialog(saveBtn, cancelBtn, node, title);
    }

    protected InputDialog createSelectCancelUIDialog(Node node, String title) {
        selectBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        return getEditDialog(selectBtn, cancelBtn, node, title);
    }

    protected InputDialog createSelectCancelResetUIDialog(Node node, String title) {
        resetBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_RESET, AppConstants.ACTION_RESET, fHandler);
        selectBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        return getEditDialog(selectBtn, cancelBtn, resetBtn, node, title);
    }

    protected Dialog createSelectCancelDialog(Node node, String title) {
        fDialog = new Dialog();
        fDialog.setTitle(title);
        fDialog.getDialogPane().getButtonTypes().addAll(selectButtonType, cancelButtonType);
        fDialog.getDialogPane().setContent(node);
        fDialog.getDialogPane().getStylesheets().add("css/styles.css");
        node.getStyleClass().add("dialogView");
        selectBtn = (Button) fDialog.getDialogPane().lookupButton(selectButtonType);
        cancelBtn = (Button) fDialog.getDialogPane().lookupButton(cancelButtonType);
        Image selectIcon = IconFactory.getIcon(RES.SELECT_ICON);
        Image cancelIcon = IconFactory.getIcon(RES.CANCEL_ICON);
        selectBtn.setGraphic(new ImageView(selectIcon));
        cancelBtn.setGraphic(new ImageView(cancelIcon));
        return fDialog;
    }

    protected InputDialog createOkCancelUIDialog(Node node, String title) {
        okBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_OK, AppConstants.ACTION_OK, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        return getEditDialog(okBtn, cancelBtn, node, title);
    }

    protected Response createSaveCancelResponseDialog(Node node, String title) {
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        return getSaveCancelResponseDialog(saveBtn, cancelBtn, node, title);
    }

    protected Response createSelectCancelResponseDialog(Node node, String title) {
        selectBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        return getSelectResponseDialog(selectBtn, cancelBtn, node, title);
    }

    protected Response createConfirmResponseDialog(final String message) {
        return getYesNoResponseDialog(message);
    }

    protected Response createConfirmNoYesResponseDialog(final String message) {
        return getNoYesResponseDialog(message);
    }

    protected void showConfirmDialog(final String s, final EventHandler<ActionEvent> yesAction) {
        getConfirmDialog(s, yesAction);
    }

    protected void showAlertDialog(final String s) {
        getWarningDialog(s);
    }

    protected void showConfirmDialog(Node node, final EventHandler<ActionEvent> yesAction) {
        getConfirmDialog(node, yesAction);
    }

    protected InputDialog createUIDialog(Node node, String title) {
        return getDialog(node, title);
    }

    protected ChangeListener fieldValueListener = (ChangeListener<String>) (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
        updateTotal();
    };

    protected GridPane createRangePane(DatePicker fromDatePicker, DatePicker toDatePicker) {
        LocalDateTime from = LocalDate.now().atTime(0, 0, 0, 0);
        LocalDateTime to = LocalDate.now().atTime(LocalTime.MAX);
        fromDatePicker.setValue(from.toLocalDate());
        toDatePicker.setValue(to.toLocalDate());
        GridPane rangePane = new GridPane();
        rangePane.setPadding(new Insets(10));
        rangePane.setHgap(8);
        rangePane.setVgap(10);
        int size = 120;
        Label fromLabel = new Label("From: ");
        Label toLabel = new Label("To: ");
        rangePane.add(fromLabel, 0, 0);
        rangePane.add(fromDatePicker, 1, 0);
        rangePane.add(toLabel, 2, 0);
        rangePane.add(toDatePicker, 3, 0);
        fromLabel.setPrefWidth(size);
        toLabel.setPrefWidth(size);
        fromDatePicker.setPrefWidth(size);
        toDatePicker.setPrefWidth(size);
        fromLabel.setAlignment(Pos.CENTER_RIGHT);
        toLabel.setAlignment(Pos.CENTER_RIGHT);
        final Separator separator = new Separator();
        separator.setMaxWidth(504);
        separator.setPrefHeight(10);
        Button todayBtn = new Button("Today");
        Button thisWeekBtn = new Button("This Week");
        Button lastWeekBtn = new Button("Last Week");
        Button lastTwoWeekBtn = new Button("Last Two Weeks");
        Button thisMonthBtn = new Button("This Month");
        Button lastMonthBtn = new Button("Last Month");
        Button thisQuarterBtn = new Button("This Quarter");
        Button lastQuarterBtn = new Button("Last Quarter");
        Button thisYearBtn = new Button("This Year");
        Button lastYearBtn = new Button("Last Year");
        todayBtn.setPrefWidth(size);
        thisWeekBtn.setPrefWidth(size);
        lastWeekBtn.setPrefWidth(size);
        lastTwoWeekBtn.setPrefWidth(size);
        thisMonthBtn.setPrefWidth(size);
        lastMonthBtn.setPrefWidth(size);
        thisQuarterBtn.setPrefWidth(size);
        lastQuarterBtn.setPrefWidth(size);
        thisYearBtn.setPrefWidth(size);
        lastYearBtn.setPrefWidth(size);
        todayBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            LocalDateTime todayfrom = today.atTime(0, 0, 0, 0);
            LocalDateTime todayTo = today.atTime(LocalTime.MAX);
            fromDatePicker.setValue(todayfrom.toLocalDate());
            toDatePicker.setValue(todayTo.toLocalDate());
        });
        thisWeekBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            LocalDate firstDayofWeek;
            if (today.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                firstDayofWeek = today;
            } else {
                firstDayofWeek = today.minusDays(today.getDayOfWeek().getValue());
            }
            LocalDateTime thisWeekFrom = firstDayofWeek.atTime(0, 0, 0, 0);
            LocalDateTime thisWeekTo = today.atTime(LocalTime.MAX);
            fromDatePicker.setValue(thisWeekFrom.toLocalDate());
            toDatePicker.setValue(thisWeekTo.toLocalDate());
        });
        lastWeekBtn.setOnAction((event) -> {
            LocalDate date, firstDayofLastWeek, lastDayofLastWeek;
            LocalDate today = LocalDate.now();
            date = today.minusWeeks(1);
            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                firstDayofLastWeek = date;
            } else {
                firstDayofLastWeek = date.minusDays(date.getDayOfWeek().getValue());
            }
            lastDayofLastWeek = firstDayofLastWeek.plusDays(6);
            LocalDateTime lastWeekFrom = firstDayofLastWeek.atTime(0, 0, 0, 0);
            LocalDateTime lastWeekTo = lastDayofLastWeek.atTime(LocalTime.MAX);
            fromDatePicker.setValue(lastWeekFrom.toLocalDate());
            toDatePicker.setValue(lastWeekTo.toLocalDate());
        });
        lastTwoWeekBtn.setOnAction((event) -> {
            LocalDate date, firstDayofLastTwoWeek, lastDayofLastTwoWeek;
            LocalDate today = LocalDate.now();
            date = today.minusWeeks(2);
            if (date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                firstDayofLastTwoWeek = date;
            } else {
                firstDayofLastTwoWeek = date.minusDays(date.getDayOfWeek().getValue());
            }
            lastDayofLastTwoWeek = firstDayofLastTwoWeek.plusDays(13);
            LocalDateTime lastTwoWeekFrom = firstDayofLastTwoWeek.atTime(0, 0, 0, 0);
            LocalDateTime lastTwoWeekTo = lastDayofLastTwoWeek.atTime(LocalTime.MAX);
            fromDatePicker.setValue(lastTwoWeekFrom.toLocalDate());
            toDatePicker.setValue(lastTwoWeekTo.toLocalDate());
        });
        thisMonthBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
            LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
            LocalDateTime thisMonthFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
            LocalDateTime thisMonthTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);
            fromDatePicker.setValue(thisMonthFrom.toLocalDate());
            toDatePicker.setValue(thisMonthTo.toLocalDate());
        });
        lastMonthBtn.setOnAction((event) -> {
            LocalDate todayofLastMonth = LocalDate.now().minusMonths(1);
            LocalDate firstDayofLastMonth = todayofLastMonth.withDayOfMonth(1);
            LocalDate lastDayofLastMonth = todayofLastMonth.withDayOfMonth(todayofLastMonth.lengthOfMonth());
            LocalDateTime lastMonthFrom = firstDayofLastMonth.atTime(0, 0, 0, 0);
            LocalDateTime lastMonthTo = lastDayofLastMonth.atTime(LocalTime.MAX);
            fromDatePicker.setValue(lastMonthFrom.toLocalDate());
            toDatePicker.setValue(lastMonthTo.toLocalDate());
        });
        thisQuarterBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            int month = today.get(ChronoField.MONTH_OF_YEAR);
            int year = today.get(ChronoField.YEAR);
            int firstMonthofThisQuarter;
            int lastMonthofThisQuarter;
            if (month <= 3) {
                firstMonthofThisQuarter = 1;
                lastMonthofThisQuarter = 3;
            } else if (month <= 6) {
                firstMonthofThisQuarter = 4;
                lastMonthofThisQuarter = 6;
            } else if (month <= 9) {
                firstMonthofThisQuarter = 7;
                lastMonthofThisQuarter = 9;
            } else {
                firstMonthofThisQuarter = 10;
                lastMonthofThisQuarter = 12;
            }
            LocalDate firstDayoFirstMonthofThisQuarter = LocalDate.of(year, firstMonthofThisQuarter, 1);
            LocalDate firstDayoLastMonthofThisQuarter = LocalDate.of(year, lastMonthofThisQuarter, 1);
            LocalDate lastDayoLastMonthofThisQuarter = firstDayoLastMonthofThisQuarter.withDayOfMonth(firstDayoLastMonthofThisQuarter.lengthOfMonth());
            LocalDateTime thisQuarterFrom = firstDayoFirstMonthofThisQuarter.atTime(0, 0, 0, 0);
            LocalDateTime thisQuarterTo = lastDayoLastMonthofThisQuarter.atTime(LocalTime.MAX);
            fromDatePicker.setValue(thisQuarterFrom.toLocalDate());
            toDatePicker.setValue(thisQuarterTo.toLocalDate());
        });
        lastQuarterBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            int month = today.get(ChronoField.MONTH_OF_YEAR);
            int year = today.get(ChronoField.YEAR);
            int firstMonthofLastQuarter;
            int lastMonthofLastQuarter;
            int yearofLastQuarter;
            if (month <= 3) {
                firstMonthofLastQuarter = 10;
                lastMonthofLastQuarter = 12;
                yearofLastQuarter = year - 1;
            } else if (month <= 6) {
                firstMonthofLastQuarter = 1;
                lastMonthofLastQuarter = 3;
                yearofLastQuarter = year;
            } else if (month <= 9) {
                firstMonthofLastQuarter = 4;
                lastMonthofLastQuarter = 6;
                yearofLastQuarter = year;
            } else {
                firstMonthofLastQuarter = 7;
                lastMonthofLastQuarter = 9;
                yearofLastQuarter = year;
            }
            LocalDate firstDayoFirstMonthofLastQuarter = LocalDate.of(yearofLastQuarter, firstMonthofLastQuarter, 1);
            LocalDate firstDayoLastMonthofLastQuarter = LocalDate.of(yearofLastQuarter, lastMonthofLastQuarter, 1);
            LocalDate lastDayoLastMonthofLastQuarter = firstDayoLastMonthofLastQuarter.withDayOfMonth(firstDayoLastMonthofLastQuarter.lengthOfMonth());
            LocalDateTime latsQuarterFrom = firstDayoFirstMonthofLastQuarter.atTime(0, 0, 0, 0);
            LocalDateTime lastQuarterTo = lastDayoLastMonthofLastQuarter.atTime(LocalTime.MAX);
            fromDatePicker.setValue(latsQuarterFrom.toLocalDate());
            toDatePicker.setValue(lastQuarterTo.toLocalDate());
        });
        thisYearBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            LocalDate firstDayofThisYear = today.with(firstDayOfYear());
            LocalDate lastDayofThisYear = today.with(lastDayOfYear());
            LocalDateTime thsiYearFrom = firstDayofThisYear.atTime(0, 0, 0, 0);
            LocalDateTime thisYearTo = lastDayofThisYear.atTime(LocalTime.MAX);
            fromDatePicker.setValue(thsiYearFrom.toLocalDate());
            toDatePicker.setValue(thisYearTo.toLocalDate());
        });
        lastYearBtn.setOnAction((event) -> {
            LocalDate today = LocalDate.now();
            LocalDate todayofLastYear = today.minusYears(1);
            LocalDate firstDayofLastYear = todayofLastYear.with(firstDayOfYear());
            LocalDate lastDayofLastYear = todayofLastYear.with(lastDayOfYear());
            LocalDateTime lastYearFrom = firstDayofLastYear.atTime(0, 0, 0, 0);
            LocalDateTime lastYearTo = lastDayofLastYear.atTime(LocalTime.MAX);
            fromDatePicker.setValue(lastYearFrom.toLocalDate());
            toDatePicker.setValue(lastYearTo.toLocalDate());
        });
        rangePane.add(separator, 0, 1, 4, 1);
        rangePane.add(todayBtn, 0, 2);
        rangePane.add(lastWeekBtn, 1, 2);
        rangePane.add(thisMonthBtn, 2, 2);
        rangePane.add(thisQuarterBtn, 3, 2);
        rangePane.add(thisYearBtn, 4, 2);
        rangePane.add(thisWeekBtn, 0, 3);
        rangePane.add(lastTwoWeekBtn, 1, 3);
        rangePane.add(lastMonthBtn, 2, 3);
        rangePane.add(lastQuarterBtn, 3, 3);
        rangePane.add(lastYearBtn, 4, 3);
        return rangePane;
    }

    protected void setTableWidth(TableView<?> table) {
        double width = table.getColumns().stream().filter(e -> e.isVisible()).mapToDouble(e -> e.getWidth()).sum() + 15.0;
        table.setPrefWidth(width);
    }

    protected void copyProperties(Object newObject, Object oldObject) {
        try {
            BeanUtils.copyProperties(newObject, oldObject);
        } catch (IllegalAccessException | InvocationTargetException ex) {
            Logger.getLogger(BaseListUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public class ObjectHolder<T> {

        private T obj;

        public ObjectHolder(T obj) {
            this.obj = obj;
        }

        public T get() {
            return obj;
        }

        public void set(T obj) {
            this.obj = obj;
        }
    }
}

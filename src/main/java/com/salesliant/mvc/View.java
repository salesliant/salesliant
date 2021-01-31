package com.salesliant.mvc;

import com.salesliant.client.ClientApp;
import com.salesliant.client.ClientView;
import com.salesliant.client.Config;
import com.salesliant.util.FormattedTableCellFactory;
import com.salesliant.util.AppConstants;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.RES;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public abstract class View<T> extends AbstractView {

    protected TableView<T> fTableView = new TableView<>();
    protected ObservableList<T> fEntityList;
    protected final Label lblWarning = new Label("");

    protected Dialog fDialog;
    protected Stage fInputDialog;
    protected Stage fUIDialog;
    protected T fEntity;
    protected Node mainView;
    protected int fIndex;
    protected Button yesBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_YES);
    protected Button cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
    protected Button selectBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT);
    protected String dialogTitle = "";
    protected Button saveActionBtn;
    protected Button saveBtn;
    protected Button selectActionBtn;
    protected Button receiveActionBtn;
    public Button titleBtn = new Button();
    protected ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
    protected ButtonType tenderButtonType = new ButtonType("Tender", ButtonBar.ButtonData.OK_DONE);
    protected ButtonType selectButtonType = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
    protected ButtonType receiveButtonType = new ButtonType("Receive", ButtonBar.ButtonData.OK_DONE);
    protected ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    protected EventHandler<ActionEvent> fHandler = (ActionEvent event) -> {
        handleEvent(event);
    };
    protected final ChangeListener fListener;
    protected EventHandler<KeyEvent> fKeyListener = (KeyEvent event) -> {
        handleEvent(event);
    };
    protected String lastKey = null;

    public View() {
        fListener = (ChangeListener) new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                validate();
                lblWarning.setText("");
            }
        };
        lblWarning.getStyleClass().add("lblWarning");
    }

    public abstract void init();

    public Node getView() {
        return mainView;
    }

    public TableView getTableView() {
        return fTableView;
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

    protected final void handleEvent(Event event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (((KeyEvent) event).getCode() == KeyCode.ENTER) {
                handleAction(AppConstants.ACTION_EDIT);
            }
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
                handleAction(aButton.getId());
            }
        }
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            handleAction(AppConstants.ACTION_EDIT);
        }
        event.consume();
    }

    protected abstract void handleAction(String code);

    protected void handleClose() {
        if (fDialog != null) {
            fDialog.close();
        }
        ClientView.closeTab(this.getClass().getName());
        Config.createEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    protected void refresh() {
        String name = this.getClass().getName();
        ClientView.refreshTab(name);
    }

    protected void validate() {
    }

    protected void updateTotal() {
    }

    protected DecimalFormat getPercentFormat() {
        DecimalFormat percentFormat = (DecimalFormat) NumberFormat.getPercentInstance();
        percentFormat.setGroupingUsed(false);
        percentFormat.setMaximumIntegerDigits(2);
        percentFormat.setMaximumFractionDigits(2);
        return percentFormat;
    }

    protected DecimalFormat getIntegerFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(10);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat;
    }

    protected DecimalFormat getNumberFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMaximumFractionDigits(2);
        return numberFormat;
    }

    protected DecimalFormat getDoubleFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

    protected DecimalFormat getDecimalFormat() {
        DecimalFormat numberFormat = (DecimalFormat) NumberFormat.getNumberInstance();
        numberFormat.setGroupingUsed(false);
        numberFormat.setMaximumIntegerDigits(12);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat;
    }

    protected DecimalFormat getMoneyFormat() {
        DecimalFormat moneyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance();
        moneyFormat.setGroupingUsed(false);
        moneyFormat.setMaximumIntegerDigits(10);
        moneyFormat.setMaximumFractionDigits(2);
        return moneyFormat;
    }

    protected DateFormat getDateFormat() {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat;
    }

    protected void addKeyListener() {
        fTableView.setOnKeyPressed((KeyEvent t) -> {
            TablePosition tp;
            if (!t.isControlDown() && (t.getCode().isLetterKey() || t.getCode().isDigitKey())) {
                lastKey = t.getText();
                tp = fTableView.getFocusModel().getFocusedCell();
                fTableView.edit(tp.getRow(), tp.getTableColumn());
                lastKey = null;
            }
        });
        fTableView.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.INSERT) {
                handleAction(AppConstants.ACTION_ADD);
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
            if (t.isControlDown() && t.getCode() == KeyCode.A) {
                handleAction(AppConstants.ACTION_EDIT);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.V) {
                handleAction(AppConstants.ACTION_VOID);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.L) {
                handleAction(AppConstants.ACTION_SELECT_LIST);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.X) {
                handleAction(AppConstants.ACTION_DELETE);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.E) {
                handleAction(AppConstants.ACTION_TABLE_EDIT);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.U) {
                handleAction(AppConstants.ACTION_MOVE_ROW_UP);
            }
            if (t.isControlDown() && t.getCode() == KeyCode.D) {
                handleAction(AppConstants.ACTION_MOVE_ROW_DOWN);
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

    protected Callback<TableColumn<T, String>, TableCell<T, String>> textCellFactory = (TableColumn<T, String> p) -> {
        return new EditableTableCell();
    };
    protected Callback<TableColumn<T, Number>, TableCell<T, Number>> integerCellFactory = (TableColumn<T, Number> p) -> {
        return new EditableTableCell(NumberFormat.getIntegerInstance());
    };
    protected Callback<TableColumn<T, Number>, TableCell<T, Number>> doubleCellFactory = (TableColumn<T, Number> p) -> {
        return new EditableTableCell<>(getNumberFormat());
    };

    protected Callback stringCell(Pos alignment) {
        return new FormattedTableCellFactory(alignment);
    }

    protected Callback numberCell(Pos alignment) {
        return new FormattedTableCellFactory(getNumberFormat(), alignment);
    }

    protected Callback doubleCell(Pos alignment) {
        return new FormattedTableCellFactory(getDoubleFormat(), alignment);
    }

    protected Callback moneyCell(Pos alignment) {
        return new FormattedTableCellFactory(getMoneyFormat(), alignment);
    }

    protected Callback percentCell(Pos alignment) {
        return new FormattedTableCellFactory(getPercentFormat(), alignment);
    }

    protected HBox createNewEditDeleteReceivePrintExportCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button EditButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button receiveButton = ButtonFactory.getButton(ButtonFactory.BUTTON_RECEIVE);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        newButton.setId(AppConstants.ACTION_ADD);
        EditButton.setId(AppConstants.ACTION_EDIT);
        receiveButton.setId(AppConstants.ACTION_RECEIVE);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        printButton.setId(AppConstants.ACTION_PRINT);
        exportButton.setId(AppConstants.ACTION_EXPORT);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        newButton.setOnAction(fHandler);
        EditButton.setOnAction(fHandler);
        receiveButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        printButton.setOnAction(fHandler);
        exportButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(EditButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(receiveButton);
        buttonGroup.getChildren().add(printButton);
        buttonGroup.getChildren().add(exportButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createPrintExportDuplicateCloseButtonPane() {
        Button duplicateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        duplicateButton.setId(AppConstants.ACTION_CLONE);
        printButton.setId(AppConstants.ACTION_PRINT);
        exportButton.setId(AppConstants.ACTION_EXPORT);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        duplicateButton.setOnAction(fHandler);
        duplicateButton.setPrefWidth(95);
        printButton.setOnAction(fHandler);
        exportButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();

        buttonGroup.getChildren().add(printButton);
        buttonGroup.getChildren().add(exportButton);
        buttonGroup.getChildren().add(duplicateButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createPrintExportCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT);
        printButton.setId(AppConstants.ACTION_PRINT);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        exportButton.setId(AppConstants.ACTION_EXPORT);
        printButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        exportButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(printButton);
        buttonGroup.getChildren().add(exportButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createLineNoteSelectButtonPane() {
        Button lineNoteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_LINE_NOTE);
        Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_LIST);
        lineNoteButton.setId(AppConstants.ACTION_LINE_NOTE);
        selectButton.setId(AppConstants.ACTION_SELECT_LIST);
        lineNoteButton.setPrefWidth(94);
        selectButton.setPrefWidth(94);
        lineNoteButton.setOnAction(fHandler);
        selectButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(lineNoteButton);
        buttonGroup.getChildren().add(selectButton);
        buttonGroup.setAlignment(Pos.CENTER_LEFT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createPrintReceiveAllButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT);
        Button receiveAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_RECEIVE_ALL);
        printButton.setId(AppConstants.ACTION_PRINT);
        receiveAllButton.setId(AppConstants.ACTION_RECEIVE_ALL);
        printButton.setOnAction(fHandler);
        receiveAllButton.setOnAction(fHandler);
        printButton.setPrefWidth(94);
        receiveAllButton.setPrefWidth(94);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(printButton);
        buttonGroup.getChildren().add(receiveAllButton);
        buttonGroup.setAlignment(Pos.CENTER_LEFT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createTenderVoidButtonPane() {
        Button tenderButton = ButtonFactory.getButton(ButtonFactory.BUTTON_TENDER);
        Button voidlButton = ButtonFactory.getButton(ButtonFactory.BUTTON_VOID);
        tenderButton.setId(AppConstants.ACTION_TENDER);
        voidlButton.setId(AppConstants.ACTION_VOID);
        tenderButton.setOnAction(fHandler);
        voidlButton.setOnAction(fHandler);
        tenderButton.setPrefWidth(94);
        voidlButton.setPrefWidth(94);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(tenderButton);
        buttonGroup.getChildren().add(voidlButton);
        buttonGroup.setAlignment(Pos.CENTER_LEFT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createPrintButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT);
        printButton.setId(AppConstants.ACTION_PRINT);
        printButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(printButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createNewDeleteButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        newButton.setId(AppConstants.ACTION_ADD);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        newButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        newButton.setPrefWidth(94);
        deleteButton.setPrefWidth(94);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.setAlignment(Pos.CENTER_LEFT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createApplyResetButtonPane() {
        Button applyButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS);
        Button resetButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH);
        applyButton.setText("Apply");
        resetButton.setText("Clear");
        applyButton.setId(AppConstants.ACTION_PROCESS);
        resetButton.setId(AppConstants.ACTION_REFRESH);
        applyButton.setOnAction(fHandler);
        resetButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(applyButton);
        buttonGroup.getChildren().add(resetButton);
        buttonGroup.setAlignment(Pos.TOP_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
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

    protected HBox createNewEditDeleteCloneCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button cloneButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, cloneButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    protected HBox createPostCloseButtonPane() {
        Button postButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        postButton.setId(AppConstants.ACTION_PROCESS);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        postButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(postButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createNewEditPostCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button postButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        newButton.setId(AppConstants.ACTION_ADD);
        newButton.setText("Add Credit");
        newButton.setPrefWidth(94);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        postButton.setId(AppConstants.ACTION_PROCESS);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        newButton.setOnAction(fHandler);
        editButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        postButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(postButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createEditPayCloseButtonPane() {

        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button postButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        postButton.setId(AppConstants.ACTION_PROCESS);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        postButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(postButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createNewEditDeleteRefreshCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button refreshButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        newButton.setId(AppConstants.ACTION_ADD);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        refreshButton.setId(AppConstants.ACTION_REFRESH);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        newButton.setOnAction(fHandler);
        editButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        refreshButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(refreshButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createEditDeleteDuplicateCloseButtonPane() {
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button duplicateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        duplicateButton.setId(AppConstants.ACTION_CLONE);
        duplicateButton.setPrefWidth(87);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        editButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        duplicateButton.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(duplicateButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    protected HBox createNewEditDeleteButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        newButton.setId(AppConstants.ACTION_ADD);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
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

    protected HBox createNewEditDeleteRefreshButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button refreshButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH);
        newButton.setId(AppConstants.ACTION_ADD);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        refreshButton.setId(AppConstants.ACTION_REFRESH);
        newButton.setOnAction(fHandler);
        editButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        refreshButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(refreshButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(2);
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

    protected HBox createSaveCancelButtonPane() {
        HBox buttons = new HBox();
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        buttons.getChildren().addAll(saveBtn, cancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(3);
        return buttons;
    }

    protected HBox createSaveCloseButtonPane() {
        HBox buttons = new HBox();
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        saveBtn.setId(AppConstants.ACTION_SAVE);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        saveBtn.setOnAction(fHandler);
        closeButton.setOnAction(fHandler);
        buttons.getChildren().add(saveBtn);
        buttons.getChildren().add(closeButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        return buttons;
    }

    protected VBox createNewEditDeleteCloseVButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        newButton.setId(AppConstants.ACTION_ADD);
        editButton.setId(AppConstants.ACTION_EDIT);
        deleteButton.setId(AppConstants.ACTION_DELETE);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        newButton.setOnAction(fHandler);
        newButton.setPrefWidth(75);
        editButton.setOnAction(fHandler);
        editButton.setPrefWidth(75);
        deleteButton.setOnAction(fHandler);
        deleteButton.setPrefWidth(75);
        closeButton.setOnAction(fHandler);
        closeButton.setPrefWidth(75);
        VBox buttonGroup = new VBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.TOP_CENTER);
        buttonGroup.setSpacing(3);
        return buttonGroup;
    }

    /**
     * Process String according to its type
     *
     * @param input object
     * @param label label
     * @return return object
     */
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

    protected String addToString(Object obj) {
        String result;
        if (obj == null) {
            result = "";
        } else {
            if (obj instanceof BigDecimal) {
                obj = ((BigDecimal) obj).setScale(2, RoundingMode.HALF_UP);
                result = "" + ((BigDecimal) obj).doubleValue();
            } else if (obj instanceof Date) {
                result = DateFormat.getDateInstance().format((Date) obj);
            } else {
                result = getString(obj);
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

    public void add(GridPane pane, String label, TextField[] field, int row) {
        Label fieldLabel = new Label(label);
        HBox rightBox = new HBox();
        rightBox.getChildren().addAll(Arrays.asList(field));
        pane.add(fieldLabel, 0, row);
        pane.add(rightBox, 1, row);
        GridPane.setHalignment(fieldLabel, HPos.RIGHT);
        GridPane.setHalignment(rightBox, HPos.LEFT);
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

    protected void add(GridPane pane, String label, CheckBox box, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected void add(GridPane pane, CheckBox box, String label, int row) {
        Label boxLabel = new Label(label);
        pane.add(box, 0, row);
        pane.add(boxLabel, 1, row);
        GridPane.setHalignment(boxLabel, HPos.LEFT);
        GridPane.setHalignment(box, HPos.RIGHT);
    }

    protected void add(GridPane pane, String label, TextArea box, int iHigh, int iWidth, int row) {
        Label boxLabel = new Label(label);
        box.setPrefHeight(iHigh);
        box.setPrefWidth(iWidth);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setValignment(boxLabel, VPos.TOP);
        GridPane.setHalignment(box, HPos.LEFT);
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

    protected void add(GridPane pane, String label, ChoiceBox box, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
    }

    protected void add(GridPane pane, String label, Node box, int row) {
        Label boxLabel = new Label(label);
        pane.add(boxLabel, 0, row);
        pane.add(box, 1, row);
        GridPane.setHalignment(boxLabel, HPos.RIGHT);
        GridPane.setHalignment(box, HPos.LEFT);
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

    boolean checkNumeric(String value) {
        String number = value.replaceAll("\\s+", "");
        for (int j = 0; j < number.length(); j++) {
            if (!(((int) number.charAt(j) >= 47 && (int) number.charAt(j) <= 57))) {
                return false;
            }
        }
        return true;
    }

    protected class BooleanTableCell<S, T> extends TableCell<S, T> {

        private final CheckBox checkBox;
        private ObservableValue<T> ov;

        public BooleanTableCell() {
            this.checkBox = new CheckBox();
            this.checkBox.setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER);
            setGraphic(checkBox);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                setGraphic(checkBox);
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().unbindBidirectional((BooleanProperty) ov);
                }
                ov = getTableColumn().getCellObservableValue(getIndex());
                if (ov instanceof BooleanProperty) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) ov);
                }
            }
        }
    }

    protected class EditableTableCell<S, T> extends TableCell<S, T> {

        private NumberFormat format;
        private TextField textField;

        public EditableTableCell(NumberFormat format) {
            this.format = format;
        }

        public EditableTableCell() {
        }

        @Override
        public void startEdit() {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            Platform.runLater(() -> {
                textField.selectAll();
                textField.requestFocus();// also selects
            });
            if (lastKey != null) {
                textField.setText(lastKey);
                Platform.runLater(() -> {
                    textField.deselect();
                    textField.end();
                });
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();
            try {
                setText(getItem().toString());
            } catch (Exception e) {
            }
            setGraphic(null);
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
                super.setAlignment(Pos.CENTER_RIGHT);
            }
        }

        public TextField getTextField() {
            return textField;
        }

        private void createTextField() {
            textField = new TextField();
            textField.setAlignment(Pos.CENTER_RIGHT);
            if (format != null) {
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue.equals("")) {
                        return;
                    }
                    try {
                        if (format.isParseIntegerOnly()) {
                            Integer i = Integer.valueOf(newValue);
                        } else {
                            Double i = Double.valueOf(newValue);
                        }
                    } catch (Exception e) {
                        ((StringProperty) observable).setValue(oldValue);
                    }
                });
            }
            textField.setText(getString());
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitHelper(false);
                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().refresh();
                            getTableView().getFocusModel().focus(getTableRow().getIndex(), nextColumn);
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                        commitHelper(false);

                        TableColumn nextColumn = getNextColumn(!t.isShiftDown());
                        if (nextColumn != null) {
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    } else if (!t.isControlDown() && t.getCode() == KeyCode.UP) {
                        commitHelper(false);
                        getTableView().getSelectionModel().selectAboveCell();
                        getTableView().requestFocus();
                        getTableView().edit(getTableView().getSelectionModel().getSelectedIndex(), getTableView().getColumns().get(0));
                    } else if (!t.isControlDown() && t.getCode() == KeyCode.DOWN) {
                        commitHelper(false);
                        getTableView().getSelectionModel().selectNext();
                        getTableView().requestFocus();
                        getTableView().edit(getTableView().getSelectionModel().getSelectedIndex(), getTableView().getColumns().get(0));
                    }
                }
            });
            textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (!newValue && textField != null) {
                        commitHelper(true);
                    }
                }
            });
        }

        protected void commitHelper(boolean losingFocus) {
            if (format == null) {
                commitEdit(((T) textField.getText()));
            } else {
                if (textField == null) {
                    return;
                }
                try {
                    String input = textField.getText();
                    if (input == null || input.length() == 0) {
                        setText(format.format(0));
                        commitEdit((T) new Integer(0));
                        return;
                    }

                    int startIndex = 0;
                    ParsePosition position = new ParsePosition(startIndex);
                    Number parsedNumber = format.parse(input, position);

                    if (position.getIndex() != input.length()) {
                        throw new ParseException("Failed to parse complete string: " + input, position.getIndex());
                    }

                    if (position.getIndex() == startIndex) {
                        throw new ParseException("Failed to parse a number from the string: " + input, position.getIndex());
                    }
                    commitEdit((T) parsedNumber);
                } catch (ParseException ex) {
                    if (losingFocus) {
                        cancelEdit();
                    }
                }
            }
        }

        private String getString() {
            if (format == null) {
                return getItem() == null ? "" : getItem().toString();
            } else {
                return getItem() == null ? "" : format.format(getItem());
            }
        }

        private TableColumn<S, ?> getNextColumn(boolean forward) {
            List<TableColumn<S, ?>> columns = new ArrayList<>();
            for (TableColumn<S, ?> column : getTableView().getColumns()) {
                columns.addAll(getLeaves(column));
            }
            if (columns.size() < 2) {
                return null;
            }
            int currentIndex = columns.indexOf(getTableColumn());
            int nextIndex = currentIndex;
            if (forward) {
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    nextIndex = 0;
                }
            } else {
                nextIndex--;
                if (nextIndex < 0) {
                    nextIndex = columns.size() - 1;
                }
            }
            return columns.get(nextIndex);
        }

        private List<TableColumn<S, ?>> getLeaves(TableColumn<S, ?> root) {
            List<TableColumn<S, ?>> columns = new ArrayList<>();
            if (root.getColumns().isEmpty()) {
                if (root.isEditable()) {
                    columns.add(root);
                }
                return columns;
            } else {
                for (TableColumn<S, ?> column : root.getColumns()) {
                    columns.addAll(getLeaves(column));
                }
                return columns;
            }
        }
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
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

    protected final Dialog<ButtonType> createSaveCancelDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.initOwner(ClientApp.primaryStage);
        dialog.getDialogPane().getButtonTypes().setAll(saveButtonType, cancelButtonType);
        saveActionBtn = (Button) dialog.getDialogPane().lookupButton(saveButtonType);
        return dialog;
    }

    protected final Dialog<ButtonType> createTenderVoidDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.initOwner(ClientApp.primaryStage);
        dialog.getDialogPane().getButtonTypes().setAll(tenderButtonType, cancelButtonType);
        saveActionBtn = (Button) dialog.getDialogPane().lookupButton(tenderButtonType);
        return dialog;
    }

    protected final Dialog<ButtonType> createNoButtonDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.initOwner(ClientApp.primaryStage);
        return dialog;
    }

    protected Dialog<ButtonType> createSelectCancelDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.initOwner(ClientApp.primaryStage);
        dialog.getDialogPane().getButtonTypes().setAll(selectButtonType, cancelButtonType);
        selectActionBtn = (Button) dialog.getDialogPane().lookupButton(selectButtonType);
        return dialog;
    }

    protected Dialog<ButtonType> createReceiveCancelDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(dialogTitle);
        dialog.initOwner(ClientApp.primaryStage);
        dialog.getDialogPane().getButtonTypes().setAll(receiveButtonType, cancelButtonType);
        receiveActionBtn = (Button) dialog.getDialogPane().lookupButton(receiveButtonType);
        return dialog;
    }

    protected String getString(Object obj) {
        return Objects.toString(obj, "");
    }

    protected Integer zeroIfNull(Integer value) {
        return value == null ? 0 : value;
    }

    protected Double zeroIfNull(Double value) {
        return value == null ? 0 : value;
    }

    protected BigDecimal zeroIfNull(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }

    protected Stage createUIDialog(Node node, String title) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle(title);
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                handleAction(AppConstants.ACTION_CLOSE);
                windowEvent.consume();
            }
        });
        return dialog;
    }

    protected Stage createCloseUIDialog(Node node, String title) {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle(title);
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                dialog.close();
                windowEvent.consume();
            }
        });
        return dialog;
    }

    protected Stage createSaveCancelUIDialog(Node node, String title) {
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(saveBtn, cancelBtn);
        HBox.setHgrow(saveBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle(title);
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                dialog.close();
                windowEvent.consume();
            }
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        return dialog;
    }

    protected Stage createSaveCancelConfirmUIDialog(Node node, String title) {
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(saveBtn, cancelBtn);
        HBox.setHgrow(saveBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle(title);
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                handleAction(AppConstants.ACTION_CLOSE);
                windowEvent.consume();
            }
        });
        return dialog;
    }

    protected Stage createSelectCancelUIDialog(Node node, String title) {
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(selectBtn, cancelBtn);
        HBox.setHgrow(selectBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle(title);
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                handleAction(AppConstants.ACTION_CLOSE);
                windowEvent.consume();
            }
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        return dialog;
    }

    protected void warn(final String s, final EventHandler<ActionEvent> yesAction) {
        Button okBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_YES);
        Button closeBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(8);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(okBtn, closeBtn);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        HBox.setHgrow(closeBtn, Priority.ALWAYS);
        Text txt = new Text();
        txt.setText(s);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 14 serif; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(txt, 1, 0);
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle("Warning");
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                dialog.close();
                windowEvent.consume();
            }
        });
        okBtn.setOnAction((ActionEvent event) -> {
            yesAction.handle(null);
            dialog.close();
        });

        closeBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        dialog.show();
    }

    protected void warn(final String s) {
        Button okBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_OK);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(8);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().add(okBtn);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        Text txt = new Text();
        txt.setText(s);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 14 serif; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(txt, 1, 0);
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle("Warning");
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(final WindowEvent windowEvent) {
                dialog.close();
                windowEvent.consume();
            }
        });
        okBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        dialog.show();
    }

    protected class CheckBoxCell extends TableCell<T, T> {

        private final ObservableSet<T> selectedItems;
        private final CheckBox checkBox;

        public CheckBoxCell(ObservableSet<T> selectedItems) {
            this.selectedItems = selectedItems;
            this.checkBox = new CheckBox();
            checkBox.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if (checkBox.isSelected()) {
                        selectedItems.add(getItem());
                    } else {
                        selectedItems.remove(getItem());
                    }
                }
            });

            selectedItems.addListener(new SetChangeListener<T>() {

                @Override
                public void onChanged(Change<? extends T> change) {
                    T item = getItem();
                    if (item != null) {
                        checkBox.setSelected(selectedItems.contains(item));
                    }
                }

            });
        }

        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                checkBox.setSelected(selectedItems.contains(item));
                setGraphic(checkBox);
            }
        }
    }

    protected ChangeListener fieldValueListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (newValue.equals("")) {
                return;
            }
            try {
                Double.valueOf(newValue);
                updateTotal();
            } catch (Exception e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        }
    };
}

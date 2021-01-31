package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.entity.VoidedTransaction_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class VoidedInvoiceListUI extends BaseListUI<VoidedTransaction> {

    private final BaseDao<VoidedTransaction> daoVoidedTransaction = new BaseDao<>(VoidedTransaction.class);
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final Label rangeLabel = new Label();
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();

    public VoidedInvoiceListUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<VoidedTransaction> list = daoVoidedTransaction.readBetweenDate(VoidedTransaction_.store, Config.getStore(),
                    VoidedTransaction_.transactionType, DBConstants.TYPE_VOID_INVOICE,
                    VoidedTransaction_.dateEntered, fFrom, fTo);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            updateRange();
            fTableView.setPlaceholder(null);
        });
    }

    private void setDefaultDate() {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        fFrom = (new Timestamp(firstDateOfPreviousMonth.getTime())).toLocalDateTime();
        fTo = (new Timestamp((new Date()).getTime())).toLocalDateTime();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT:
                fTableView.setPlaceholder(null);
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    handleAction(AppConstants.ACTION_CLOSE_DIALOG);
                });
                fInputDialog.showDialog();
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        TableColumn<VoidedTransaction, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>(VoidedTransaction_.customerName.getName()));
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(200);

        TableColumn<VoidedTransaction, String> employeeCol = new TableColumn<>("Employee");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>(VoidedTransaction_.employeeName.getName()));
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(120);

        TableColumn<VoidedTransaction, String> dateVoidedTransactionCol = new TableColumn<>("Date Voided");
        dateVoidedTransactionCol.setCellValueFactory(new PropertyValueFactory<>(VoidedTransaction_.dateEntered.getName()));
        dateVoidedTransactionCol.setCellFactory(stringCell(Pos.CENTER));
        dateVoidedTransactionCol.setPrefWidth(100);

        TableColumn<VoidedTransaction, String> invoiceCol = new TableColumn<>("Order Number");
        invoiceCol.setCellValueFactory(new PropertyValueFactory<>(VoidedTransaction_.transactionNumber.getName()));
        invoiceCol.setCellFactory(stringCell(Pos.CENTER));
        invoiceCol.setPrefWidth(100);

        TableColumn<VoidedTransaction, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(VoidedTransaction_.transactionAmount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER));
        amountCol.setPrefWidth(120);

        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(employeeCol);
        fTableView.getColumns().add(dateVoidedTransactionCol);
        fTableView.getColumns().add(invoiceCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;

    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

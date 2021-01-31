package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.report.AccountReceivableActivityReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountReceivableByDayListUI extends BaseListUI<AccountReceivable> {

    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private List<AccountReceivable> fList;
    private final Label rangeLabel = new Label();
    private final Label totalLabel = new Label();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(AccountReceivableByDayListUI.class.getName());

    public AccountReceivableByDayListUI() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
        fFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
        fTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            fList = daoAccountReceivable.readBetweenDate(AccountReceivable_.store, Config.getStore(), AccountReceivable_.dateProcessed, fFrom, fTo)
                    .stream()
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(fList);
            fTableView.setItems(fEntityList);
            updateRange();
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT:
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fList.isEmpty()) {
                    AccountReceivableActivityReportLayout layout = new AccountReceivableActivityReportLayout(fList, fFrom, fTo);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        TableColumn<AccountReceivable, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null) {
                String customer;
                if (p.getValue().getCustomer().getCompany() != null) {
                    customer = p.getValue().getCustomer().getCompany();
                } else {
                    customer = getString(p.getValue().getCustomer().getLastName()) + ","
                            + getString(p.getValue().getCustomer().getFirstName());
                }
                return new SimpleStringProperty(customer);
            } else {
                return new SimpleStringProperty("");
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(200);

        TableColumn<AccountReceivable, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null && p.getValue().getCustomer().getAccountNumber() != null) {
                return new SimpleStringProperty(p.getValue().getCustomer().getAccountNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        accountCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountCol.setPrefWidth(120);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> dateProcessedCol = new TableColumn<>("Date");
        dateProcessedCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        dateProcessedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateProcessedCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> totalAmountCol = new TableColumn<>("Amount");
        totalAmountCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.totalAmount.getName()));
        totalAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalAmountCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType() != null
                    && p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                return new SimpleStringProperty("Charge");
            } else if (p.getValue().getAccountReceivableType() != null
                    && p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT)) {
                return new SimpleStringProperty("Payment");
            } else if (p.getValue().getAccountReceivableType() != null
                    && p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                return new SimpleStringProperty("Refund");
            } else if (p.getValue().getAccountReceivableType() != null
                    && p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                return new SimpleStringProperty("Return Credit");
            } else if (p.getValue().getAccountReceivableType() != null
                    && p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_UNCOLLECTABLE)) {
                return new SimpleStringProperty("Uncollectable");
            } else {
                return new SimpleStringProperty("");
            }
        });
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        typeCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getStatus() != null
                    && p.getValue().getStatus().equals(DBConstants.STATUS_OPEN)) {
                return new SimpleStringProperty("Open");
            } else if (p.getValue().getStatus() != null
                    && p.getValue().getStatus().equals(DBConstants.STATUS_CLOSE)) {
                return new SimpleStringProperty("Close");
            } else if (p.getValue().getStatus() != null
                    && p.getValue().getStatus().equals(DBConstants.STATUS_IN_PROGRESS)) {
                return new SimpleStringProperty("Processed");
            } else {
                return new SimpleStringProperty("");
            }
        });
        statusCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        statusCol.setPrefWidth(65);

        fTableView.getColumns().add(dateProcessedCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(accountCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(totalAmountCol);
        fTableView.getColumns().add(statusCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefWidth(720);
        fTableView.setPrefHeight(510);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(totalLabel, 0, 3);
        mainPane.add(createSelectRangePrintCloseButtonPane(), 1, 3);
        return mainPane;
    }

    private HBox createSelectRangePrintCloseButtonPane() {
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        BigDecimal total = fList
                .stream()
                .map(e -> e.getPaidAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
        totalLabel.setText("Total: " + getString(total));
    }
}

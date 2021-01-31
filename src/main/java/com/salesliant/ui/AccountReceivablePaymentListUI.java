package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Cheque;
import com.salesliant.report.AccountReceivablePaymentLayout;
import com.salesliant.report.AccountReceivablePaymentReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
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
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountReceivablePaymentListUI extends BaseListUI<AccountReceivable> {

    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private List<AccountReceivable> fList;
    private final TableView<AccountReceivable> fAccountReceivableTable = new TableView<>();
    private ObservableList<AccountReceivable> fAccountReceivableList;
    private final Label rangeLabel = new Label();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(AccountReceivablePaymentListUI.class.getName());

    public AccountReceivablePaymentListUI() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
        fFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
        fTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);
        loadData();
        mainView = createMainView();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends AccountReceivable> observable, AccountReceivable newValue, AccountReceivable oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                List<AccountReceivable> list = fTableView.getSelectionModel().getSelectedItem().getAccountReceivables();
                fAccountReceivableList = FXCollections.observableList(list);
                fAccountReceivableTable.setItems(fAccountReceivableList);
            } else {
                fAccountReceivableTable.getItems().clear();
            }
        });
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            fList = daoAccountReceivable.readBetweenDate(AccountReceivable_.store, Config.getStore(),
                    AccountReceivable_.status, DBConstants.STATUS_CLOSE, AccountReceivable_.dateProcessed, fFrom, fTo)
                    .stream()
                    .filter(e -> e.getCustomer() != null)
                    .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT)
                    || e.getAccountReceivableType().equalsIgnoreCase(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND))
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
                if (fTableView.getSelectionModel().getSelectedItem() != null && !fTableView.getSelectionModel().getSelectedItem().getAccountReceivables().isEmpty()) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    AccountReceivablePaymentLayout layout = new AccountReceivablePaymentLayout(fEntity.getAccountReceivables());
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_PRINT_LIST:
                if (!fList.isEmpty()) {
                    AccountReceivablePaymentReportLayout layout = new AccountReceivablePaymentReportLayout(fList, fFrom, fTo);
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
        customerCol.setPrefWidth(350);

        TableColumn<AccountReceivable, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null && p.getValue().getCustomer().getAccountNumber() != null) {
                return new SimpleStringProperty(p.getValue().getCustomer().getAccountNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        accountCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountCol.setPrefWidth(150);

        TableColumn<AccountReceivable, String> referenceNumberCol = new TableColumn<>("Reference No.");
        referenceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        referenceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        referenceNumberCol.setPrefWidth(120);

        TableColumn<AccountReceivable, String> datePaidCol = new TableColumn<>("Date");
        datePaidCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        datePaidCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        datePaidCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> checkCol = new TableColumn<>("Check N0.");
        checkCol.setCellValueFactory((TableColumn.CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue() != null && p.getValue().getCustomer() != null && p.getValue().getCustomer().getCheques() != null && p.getValue().getAccountReceivableNumber() != null) {
                if (!p.getValue().getCustomer().getCheques().isEmpty()) {
                    Cheque cheque = p.getValue().getCustomer().getCheques().stream().filter(e -> getString(e.getReferenceNumber()).equalsIgnoreCase(p.getValue().getAccountReceivableNumber().toString())).findFirst().orElse(null);
                    if (cheque != null) {
                        return new SimpleStringProperty(getString(cheque.getCheckNumber()));
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        });
        checkCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        checkCol.setPrefWidth(100);

        TableColumn<AccountReceivable, String> paymentAmountCol = new TableColumn<>("Payment");
        paymentAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                return new SimpleStringProperty(getString(p.getValue().getTotalAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getTotalAmount()));
            }
        });
        paymentAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        paymentAmountCol.setPrefWidth(100);

        fTableView.getColumns().add(datePaidCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(accountCol);
        fTableView.getColumns().add(referenceNumberCol);
        fTableView.getColumns().add(checkCol);
        fTableView.getColumns().add(paymentAmountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(400);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Invoice");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(200);

        TableColumn<AccountReceivable, String> dateProcessedCol = new TableColumn<>("Date");
        dateProcessedCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        dateProcessedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dateProcessedCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateDue.getName()));
        dueDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dueDateCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.terms.getName()));
        termCol.setCellFactory(stringCell(Pos.CENTER));
        termCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.accountReceivableType.getName()));
        typeCol.setCellFactory(stringCell(Pos.CENTER));
        typeCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> totalAmountCol = new TableColumn<>("Total");
        totalAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                return new SimpleStringProperty(getString(p.getValue().getTotalAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getTotalAmount()));
            }
        });
        totalAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalAmountCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> paidAmountCol = new TableColumn<>("Paid");
        paidAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                return new SimpleStringProperty(getString(p.getValue().getPaidAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getPaidAmount()));
            }
        });
        paidAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        paidAmountCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> discountAmountCol = new TableColumn<>("Discount");
        discountAmountCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.discountAmount.getName()));
        discountAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        discountAmountCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> balanceAmountCol = new TableColumn<>("Balance");
        balanceAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                return new SimpleStringProperty(getString(p.getValue().getBalanceAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getBalanceAmount()));
            }
        });
        balanceAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        balanceAmountCol.setPrefWidth(90);

        fAccountReceivableTable.getColumns().add(invoiceNumberCol);
        fAccountReceivableTable.getColumns().add(dateProcessedCol);
        fAccountReceivableTable.getColumns().add(dueDateCol);
        fAccountReceivableTable.getColumns().add(termCol);
        fAccountReceivableTable.getColumns().add(typeCol);
        fAccountReceivableTable.getColumns().add(totalAmountCol);
        fAccountReceivableTable.getColumns().add(discountAmountCol);
        fAccountReceivableTable.getColumns().add(paidAmountCol);
        fAccountReceivableTable.getColumns().add(balanceAmountCol);
        setTableWidth(fAccountReceivableTable);
        fAccountReceivableTable.setPrefHeight(150);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fAccountReceivableTable, 0, 3, 2, 1);
        mainPane.add(createSelectRangePrintCloseButtonPane(), 1, 4);
        return mainPane;
    }

    private HBox createSelectRangePrintCloseButtonPane() {
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print Payment", fHandler);
        Button printListButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT_LIST, "Print Whole List", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, printButton, printListButton, closeButton);
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

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.AccountReceivableReport;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountReceivableByDateListUI extends BaseListUI<AccountReceivable> {

    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private List<AccountReceivable> fList;
    private final DataUI invoiceUI = new DataUI(Invoice.class);
    private final TableView<InvoiceEntry> fInvoiceEntryTable = new TableView<>();
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private final TabPane fTabPane = new TabPane();
    private Invoice fInvoice;
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private final Label rangeLabel = new Label();
    private static final Logger LOGGER = Logger.getLogger(AccountReceivableByDateListUI.class.getName());

    public AccountReceivableByDateListUI() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
        fFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
        fTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);
        loadData();
        mainView = createMainView();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends AccountReceivable> observable, AccountReceivable newValue, AccountReceivable oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                fInvoice = fTableView.getSelectionModel().getSelectedItem().getInvoice();
                if (fInvoice != null) {
                    List<InvoiceEntry> invoiceEntryList = fInvoice.getInvoiceEntries().stream()
                            .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                            .collect(Collectors.toList());
                    fInvoiceEntryList = FXCollections.observableList(invoiceEntryList);
                    fInvoiceEntryTable.setItems(fInvoiceEntryList);
                    try {
                        invoiceUI.setData(fInvoice);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    fInvoiceEntryTable.getItems().clear();
                    try {
                        invoiceUI.setData(new Invoice());
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                fInvoiceEntryTable.getItems().clear();
                try {
                    invoiceUI.setData(new Invoice());
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        });
        fTabPane.getSelectionModel().selectFirst();
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            fList = daoAccountReceivable.readBetweenDate(AccountReceivable_.store, Config.getStore(), AccountReceivable_.dateProcessed, fFrom, fTo);
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
                    AccountReceivableReport layout = new AccountReceivableReport(fList, fFrom, fTo);
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
        mainPane.setPadding(new Insets(6));
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
        customerCol.setPrefWidth(230);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Ref No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> dateProcessedCol = new TableColumn<>("Date");
        dateProcessedCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        dateProcessedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateProcessedCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateDue.getName()));
        dueDateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dueDateCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.terms.getName()));
        termCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        termCol.setPrefWidth(60);

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

        TableColumn<AccountReceivable, String> totalAmountCol = new TableColumn<>("Total");
        totalAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)
                    || p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                return new SimpleStringProperty(getString(p.getValue().getTotalAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getTotalAmount()));
            }
        });
        totalAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalAmountCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> paidAmountCol = new TableColumn<>("Paid");
        paidAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)
                    || p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                return new SimpleStringProperty(getString(p.getValue().getPaidAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getPaidAmount()));
            }
        });
        paidAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        paidAmountCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> discountAmountCol = new TableColumn<>("Discount");
        discountAmountCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.discountAmount.getName()));
        discountAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        discountAmountCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> balanceAmountCol = new TableColumn<>("Balance");
        balanceAmountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)
                    || p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND)) {
                return new SimpleStringProperty(getString(p.getValue().getBalanceAmount().negate()));
            } else {
                return new SimpleStringProperty(getString(p.getValue().getBalanceAmount()));
            }
        });
        balanceAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        balanceAmountCol.setPrefWidth(80);

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
        statusCol.setPrefWidth(50);

        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(dateProcessedCol);
        fTableView.getColumns().add(dueDateCol);

        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(totalAmountCol);
        fTableView.getColumns().add(paidAmountCol);
        fTableView.getColumns().add(discountAmountCol);
        fTableView.getColumns().add(balanceAmountCol);
        fTableView.getColumns().add(statusCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(300);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(new Label("Invoice Detail:"), 0, 3);
        mainPane.add(createSelectRangePrintCloseButtonPane(), 1, 3);
        mainPane.add(createInvoicePane(), 0, 4, 2, 1);
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

    private Node createInvoicePane() {
        GridPane invoicePane = new GridPane();
        invoicePane.setPadding(new Insets(1));
        invoicePane.setAlignment(Pos.CENTER);
        invoicePane.setHgap(3);
        invoicePane.setVgap(3.0);
        invoicePane.add(createOrderInfoPane(), 0, 0);
        invoicePane.add(createInvoiceEntryPane(), 1, 0);
        return invoicePane;
    }

    private Node createInvoiceEntryPane() {
        GridPane invoiceEntryPane = new GridPane();
        invoiceEntryPane.setPadding(new Insets(5));
        invoiceEntryPane.setHgap(5);
        invoiceEntryPane.setVgap(5);
        invoiceEntryPane.setAlignment(Pos.CENTER);
        invoiceEntryPane.getStyleClass().add("hboxPane");

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(120);
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            String description = getString(p.getValue().getItemDescription());
            if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                description = description + "\n" + p.getValue().getLineNote();
            }
            description = description.trim();
            return new SimpleStringProperty(description);
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(300);

        TableColumn<InvoiceEntry, String> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.quantity.getName()));
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(85);

        TableColumn<InvoiceEntry, String> detailCostCol = new TableColumn<>("Price");
        detailCostCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.price.getName()));
        detailCostCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailCostCol.setMinWidth(100);

        TableColumn<InvoiceEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                BigDecimal total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
                return new SimpleStringProperty(getString(total));
            } else {
                return new SimpleStringProperty("");
            }
        });
        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        fInvoiceEntryTable.getColumns().add(skuCol);
        fInvoiceEntryTable.getColumns().add(detailDescriptionCol);
        fInvoiceEntryTable.getColumns().add(detailQtyCol);
        fInvoiceEntryTable.getColumns().add(detailCostCol);
        fInvoiceEntryTable.getColumns().add(detailTotalCol);

        fInvoiceEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fInvoiceEntryTable.setPrefHeight(200);
        setTableWidth(fInvoiceEntryTable);

        invoiceEntryPane.add(fInvoiceEntryTable, 0, 0);
        return invoiceEntryPane;
    }

    private Node createOrderInfoPane() {
        GridPane invoiceInfotPane = new GridPane();
        invoiceInfotPane.setPadding(new Insets(5));
        invoiceInfotPane.setHgap(5);
        invoiceInfotPane.setVgap(5);
        invoiceInfotPane.setAlignment(Pos.CENTER);
        invoiceInfotPane.getStyleClass().add("hboxPane");

        add(invoiceInfotPane, "Invoice No.: ", invoiceUI.createTextField(Invoice_.invoiceNumber), 1);
        add(invoiceInfotPane, "Customer PO No.: ", invoiceUI.createTextField(Invoice_.customerPoNumber), 2);
        add(invoiceInfotPane, "Date Ordered: ", invoiceUI.createTextField(Invoice_.dateOrdered), 3);
        add(invoiceInfotPane, "Shipping Charge: ", invoiceUI.createTextField(Invoice_.shippingCharge), 4);
        add(invoiceInfotPane, "Tax Amount: ", invoiceUI.createTextField(Invoice_.taxAmount), 5);
        add(invoiceInfotPane, "Total: ", invoiceUI.createTextField(Invoice_.total), 6);

        invoiceUI.getTextField(Invoice_.invoiceNumber).setEditable(false);
        invoiceUI.getTextField(Invoice_.customerPoNumber).setEditable(false);
        invoiceUI.getTextField(Invoice_.dateOrdered).setEditable(false);
        invoiceUI.getTextField(Invoice_.shippingCharge).setEditable(false);
        invoiceUI.getTextField(Invoice_.taxAmount).setEditable(false);
        invoiceUI.getTextField(Invoice_.total).setEditable(false);

        return invoiceInfotPane;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

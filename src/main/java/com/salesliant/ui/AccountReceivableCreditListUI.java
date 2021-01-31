package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Customer;
import com.salesliant.report.AccountReceivableCreditReportLayout;
import com.salesliant.report.AccountReceivableCreditStatementLayout;
import com.salesliant.report.AccountReceivablePaymentLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountReceivableCreditListUI extends BaseListUI<AccountReceivable> {

    protected final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    protected final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    protected final DataUI dataUI = new DataUI(AccountReceivable.class);
    protected final TableView<List<AccountReceivable>> fSummaryTable = new TableView<>();
    private final ObjectProperty<BigDecimal> totalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    protected ObservableSet<AccountReceivable> selectedItems;
    protected List<AccountReceivable> accountReceivableList = new ArrayList<>();
    protected List<AccountReceivable> fList = new ArrayList<>();
    protected List<List<AccountReceivable>> fSummaryList = new ArrayList<>();
    protected PaymentUI fPaymentUI;
    private static final Logger LOGGER = Logger.getLogger(AccountReceivableCreditListUI.class.getName());

    public AccountReceivableCreditListUI() {
        mainView = createMainView();
        loadData();
        selectedItems = FXCollections.observableSet();
        selectedItems.addListener((SetChangeListener<AccountReceivable>) change -> {
            if (change.wasAdded()) {
                AccountReceivable ar = change.getElementAdded();
                if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)
                        && ar.getPaidAmount().compareTo(BigDecimal.ZERO) == 0) {
                    ar.setPaidAmount(ar.getBalanceAmount().negate());
                }
            }
            if (change.wasRemoved()) {
                AccountReceivable ar = change.getElementRemoved();
                ar.setPaidAmount(BigDecimal.ZERO);
            }
            updatePayTotal();
            fTableView.refresh();
        });
        fSummaryTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends List<AccountReceivable>> observable, List<AccountReceivable> newValue, List<AccountReceivable> oldValue) -> {
            accountReceivableList = new ArrayList<>();
            if (observable != null && observable.getValue() != null) {
                accountReceivableList = observable.getValue().stream().filter(e -> e.getCollectable()).collect(Collectors.toList());
                selectedItems.clear();
            }
            fTableView.setItems(FXCollections.observableList(accountReceivableList));
            updatePayTotal();
        });
        fTableView.setEditable(true);
    }

    private void loadData() {
        Map<Customer, List<AccountReceivable>> groupByCustomer = daoAccountReceivable.readOrderBy(AccountReceivable_.store, Config.getStore(),
                AccountReceivable_.status, DBConstants.STATUS_OPEN, AccountReceivable_.dateProcessed, AppConstants.ORDER_BY_ASC)
                .stream()
                .filter(e -> e.getCustomer() != null)
                .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                .collect(Collectors.groupingBy(e -> e.getCustomer()));
        fSummaryList = new ArrayList<>(groupByCustomer.values())
                .stream()
                .sorted((e1, e2) -> e1.get(0).getCustomer().getId().compareTo(e2.get(0).getCustomer().getId())).collect(Collectors.toList());
        fSummaryTable.setItems(FXCollections.observableList(fSummaryList));
        fSummaryTable.requestFocus();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (fSummaryTable.getSelectionModel().getSelectedItem() != null && !fSummaryTable.getSelectionModel().getSelectedItem().isEmpty()) {
                    fList.clear();
                    fList = fSummaryTable.getSelectionModel().getSelectedItem();
                    Platform.runLater(() -> {
                        AccountReceivableCreditStatementLayout layout = new AccountReceivableCreditStatementLayout(fList);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case AppConstants.ACTION_PRINT_LIST:
                if (fSummaryList != null && !fSummaryList.isEmpty()) {
                    Platform.runLater(() -> {
                        AccountReceivableCreditReportLayout layout = new AccountReceivableCreditReportLayout(fSummaryList);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case AppConstants.ACTION_TENDER:
                if (!selectedItems.isEmpty()) {
                    fList.clear();
                    fList = new ArrayList<>(selectedItems);
                    Customer customer = fList.get(0).getCustomer();
                    List<AccountReceivable> list = customer.getAccountReceivables().stream()
                            .filter(e -> e.getCollectable() != null && e.getCollectable())
                            .filter(e -> e.getStatus() != null && e.getStatus().compareTo(DBConstants.STATUS_OPEN) == 0)
                            .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)
                            || e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                            .collect(Collectors.toList());
                    BigDecimal totatDebitAmount = list
                            .stream()
                            .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE))
                            .map(AccountReceivable::getBalanceAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totatCreditAmount = list
                            .stream()
                            .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                            .map(AccountReceivable::getBalanceAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal netPayAmount = fList.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    if ((totatDebitAmount.compareTo(totatCreditAmount) > 0) && (netPayAmount.compareTo(BigDecimal.ZERO) < 0)) {
                        showAlertDialog("Can't take net credit when there is more debt to pay");
                        break;
                    }
                    fPaymentUI = new PaymentUI(fList);
                    fPaymentUI.setParent(this);
                    fInputDialog = createUIDialog(fPaymentUI.getView(), "Payment");
                    fPaymentUI.cancelButton.setOnAction(e -> {
                        fInputDialog.close();
                    });
                    Platform.runLater(() -> {
                        fPaymentUI.fPaymentTypeTable.requestFocus();
                        fPaymentUI.fPaymentTypeTable.getSelectionModel().select(0);
                        fPaymentUI.fPaymentTypeTable.getFocusModel().focus(0, fPaymentUI.fPaymentTypeTable.getColumns().get(1));
//                        fPaymentUI.fPaymentTypeTable.edit(0, fPaymentUI.fPaymentTypeTable.getColumns().get(1));
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_VOID:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to set the seleted entry uncollectable?", (ActionEvent e) -> {
                        Batch batch = Config.getBatch();
                        fEntity.setCollectable(Boolean.FALSE);
                        fEntity.setPaidAmount(BigDecimal.ZERO);
                        fEntity.setDateProcessed(new Date());
                        fEntity.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_UNCOLLECTABLE);
                        fEntity.setGlCreditAmount(BigDecimal.ZERO);
                        fEntity.setGlDebitAmount(fEntity.getBalanceAmount());
                        fEntity.setGlAccount(Integer.toString(1));
                        fEntity.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_UNCOLLECTABLE + " Invoice " + getString(fEntity.getInvoiceNumber()));
                        fEntity.setBatch(batch);
                        fEntity.setStatus(DBConstants.STATUS_CLOSE);
                        daoAccountReceivable.update(fEntity);
                        if (batch.getUncollectableCreditAmount() != null) {
                            BigDecimal uncollectableCreditAmount = batch.getUncollectableCreditAmount();
                            uncollectableCreditAmount = uncollectableCreditAmount.add(fEntity.getBalanceAmount());
                            batch.setUncollectableCreditAmount(uncollectableCreditAmount);
                        } else {
                            batch.setUncollectableCreditAmount(fEntity.getBalanceAmount());
                        }
                        daoBatch.update(batch);
                        loadData();
                    });
                }
                break;
            case AppConstants.ACTION_PROCESS_FINISH:
                fInputDialog.close();
                Batch batch = Config.getBatch();
                BigDecimal netPaymentAmount = fList.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                AccountReceivable refundAr = new AccountReceivable();
                refundAr.setAccountReceivableNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                refundAr.setStore(Config.getStore());
                refundAr.setBatch(batch);
                refundAr.setInvoice(null);
                refundAr.setInvoiceNumber(refundAr.getAccountReceivableNumber());
                refundAr.setTransaction(refundAr.getAccountReceivableNumber().toString());
                refundAr.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                refundAr.setCustomer(fList.get(0).getCustomer());
                refundAr.setTerms(fList.get(0).getTerms());
                refundAr.setCollectable(Boolean.TRUE);
                refundAr.setDateProcessed(new Date());
                refundAr.setDateDue(fList.get(0).getDateDue());
                refundAr.setDateInvoiced(new Date());
                refundAr.setStatus(DBConstants.STATUS_CLOSE);
                refundAr.setDiscountAmount(BigDecimal.ZERO);
                refundAr.setPaidAmount(BigDecimal.ZERO);
                refundAr.setBalanceAmount(BigDecimal.ZERO);
                refundAr.setPostedTag(Boolean.FALSE);
                refundAr.setTotalAmount(netPaymentAmount.negate());
                refundAr.setGlDebitAmount(netPaymentAmount.negate());
                refundAr.setGlCreditAmount(BigDecimal.ZERO);
                refundAr.setGlAccount(Integer.toString(1));
                refundAr.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND);
                refundAr.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND);
                if (fPaymentUI != null && !fPaymentUI.getPayments().isEmpty()) {
                    fPaymentUI.getPayments().forEach(e -> {
                        e.setAccountReceivable(refundAr);
                        e.setBatch(batch);
                        e.setStore(Config.getStore());
                        batch.getPayments().add(e);
                    });
                }
                daoAccountReceivable.insert(refundAr);
                batch.getAccountReceivables().add(refundAr);
                BigDecimal creditRedeemedAmount = fList.stream()
                        .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                        .map(e -> e.getPaidAmount().negate()).reduce(BigDecimal.ZERO, BigDecimal::add);
                if (creditRedeemedAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (batch.getCreditRedeemed() != null) {
                        BigDecimal creditReemedToAccountAmount = batch.getCreditRedeemed();
                        creditReemedToAccountAmount = creditReemedToAccountAmount.add(creditRedeemedAmount);
                        batch.setCreditRedeemed(creditReemedToAccountAmount);
                    } else {
                        batch.setCreditRedeemed(creditRedeemedAmount);
                    }
                }
                fList.forEach(e -> {
                    BigDecimal paymentAmount = e.getPaidAmount();
                    BigDecimal balanceAmount = e.getBalanceAmount();
                    e.setBalanceAmount(balanceAmount.subtract(paymentAmount.negate()));
                    e.setPaidAmount(paymentAmount.negate());
                    e.setGlDebitAmount(paymentAmount.negate());
                    e.setGlCreditAmount(BigDecimal.ZERO);
                    e.setGlAccount(Integer.toString(1));
                    e.setDateInvoiced(new Date());
                    e.setStatus(DBConstants.STATUS_CLOSE);
                    e.setAccountReceivablePayment(refundAr);
                    daoAccountReceivable.update(e);
                });
                daoBatch.update(batch);
                loadData();
                Platform.runLater(() -> {
                    AccountReceivablePaymentLayout layout = new AccountReceivablePaymentLayout(fList);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                break;

        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        TableColumn<List<AccountReceivable>, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                return new SimpleStringProperty(p.getValue().get(0).getCustomer().getAccountNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        accountCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountCol.setPrefWidth(250);

        TableColumn<List<AccountReceivable>, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getCustomer() != null) {
                String customer;
                if (p.getValue().get(0).getCustomer().getCompany() != null) {
                    customer = p.getValue().get(0).getCustomer().getCompany();
                } else {
                    customer = getString(p.getValue().get(0).getCustomer().getLastName()) + ","
                            + getString(p.getValue().get(0).getCustomer().getFirstName());
                }
                return new SimpleStringProperty(customer);
            } else {
                return new SimpleStringProperty("");
            }
        });
        companyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyCol.setPrefWidth(456);

        TableColumn<List<AccountReceivable>, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .map(e -> e.getBalanceAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(150);

        fSummaryTable.getColumns().add(companyCol);
        fSummaryTable.getColumns().add(accountCol);
        fSummaryTable.getColumns().add(totalCol);
        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fSummaryTable.setPrefHeight(300);
        setTableWidth(fSummaryTable);

        mainPane.add(fSummaryTable, 0, 1);
        mainPane.add(createDetailPane(), 0, 2);
        mainPane.add(createBottomPane(), 0, 3);
        return mainPane;
    }

    private Node createDetailPane() {
        TableColumn<AccountReceivable, AccountReceivable> selectedCol = new TableColumn<>("");
        selectedCol.setCellValueFactory((CellDataFeatures<AccountReceivable, AccountReceivable> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<AccountReceivable, AccountReceivable> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(140);

        TableColumn<AccountReceivable, String> invoiceDateCol = new TableColumn<>("Date");
        invoiceDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        invoiceDateCol.setPrefWidth(100);

        TableColumn<AccountReceivable, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.terms.getName()));
        termCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        termCol.setPrefWidth(80);

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
        typeCol.setPrefWidth(80);
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<AccountReceivable, String> balanceCol = new TableColumn<>("Balance");
        balanceCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                return new SimpleStringProperty(getString(p.getValue().getBalanceAmount()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        balanceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        balanceCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> creditCol = new TableColumn<>("Credit");
        creditCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                return new SimpleStringProperty(getString(p.getValue().getBalanceAmount()));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        });
        creditCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        creditCol.setPrefWidth(80);

        TableColumn<AccountReceivable, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.note.getName()));
        noteCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        noteCol.setPrefWidth(170);

        TableColumn<AccountReceivable, String> amountToPayCol = new TableColumn<>("Amount To Pay");
        amountToPayCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.paidAmount.getName()));
        amountToPayCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountToPayCol.setPrefWidth(100);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(balanceCol);
        fTableView.getColumns().add(creditCol);
        fTableView.getColumns().add(noteCol);
        fTableView.getColumns().add(amountToPayCol);
        fTableView.setPrefHeight(150);
        return fTableView;
    }

    private HBox createBottomPane() {
        HBox totalBox = new HBox();
        Label totalLbl = new Label("Total To Pay: ");
        TextField totalField = createLabelField(90.0, Pos.CENTER_LEFT);
        totalField.textProperty().bindBidirectional(totalProperty, getDecimalFormat());
        totalBox.getChildren().addAll(totalLbl, totalField);
        totalBox.setAlignment(Pos.CENTER_LEFT);

        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        HBox bottomPane = new HBox();
        bottomPane.getChildren().addAll(totalBox, filler, createButtonPane());
        bottomPane.setAlignment(Pos.CENTER);
        return bottomPane;
    }

    private HBox createButtonPane() {
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        Button arReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Credit Statement", fHandler);
        Button arSummaryReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT_LIST, "Credit Report", fHandler);
        Button payButton = ButtonFactory.getButton(ButtonFactory.BUTTON_TENDER, AppConstants.ACTION_TENDER, "Pay Selected", fHandler);
        Button uncollectableButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_VOID, "Void", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(arReportButton, arSummaryReportButton, payButton, uncollectableButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private void updatePayTotal() {
        if (selectedItems == null || selectedItems.isEmpty()) {
            totalProperty.set(BigDecimal.ZERO);
        } else {
            BigDecimal total = selectedItems.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            totalProperty.set(total);
        }
    }
}

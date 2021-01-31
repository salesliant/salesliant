package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Customer;
import com.salesliant.report.AccountReceivableAgingReportLayout;
import com.salesliant.report.AccountReceivablePaymentLayout;
import com.salesliant.report.AccountReceivableStatementReport;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.layout.RowConstraints;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountReceivableBaseListUI extends BaseListUI<AccountReceivable> {

    protected final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    protected final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    protected final DataUI dataUI = new DataUI(AccountReceivable.class);
    protected final TableView<List<AccountReceivable>> fSummaryTable = new TableView<>();
    protected final static String ENTER_DISCOUNT = "Enter Discount";
    protected final static String ADJUST_PAYMENT = "Adjust Payment";
    protected final static String EDIT_NOTE = "Edit Comment";
    protected final static String PRINT_AR_REPORT = "Print AR Report";
    protected final static String PRINT_AR_SUMMARY_REPORT = "Print AR Summary Report";
    protected final Date f0, f30, f60, f90;
    public TableColumn<AccountReceivable, AccountReceivable> selectedCol = new TableColumn<>("");
    protected ObservableSet<AccountReceivable> selectedItems;
    protected List<AccountReceivable> accountReceivableList = new ArrayList<>();
    protected List<AccountReceivable> fList = new ArrayList<>();
    protected List<List<AccountReceivable>> fSummaryList = new ArrayList<>();
    private final ObjectProperty<BigDecimal> totalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    protected final GridPane fDiscountPane, fToPayPane, fNotePane;
    protected HBox fReportButtonPane, fBottomPane;
    protected PaymentUI fPaymentUI;
    protected Button discountButton = ButtonFactory.getButton(ButtonFactory.BUTTON_MOVE_DOWN, AppConstants.ACTION_ENTER_DISCOUNT, ENTER_DISCOUNT, fHandler);
    protected Button noteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_LINE_NOTE, EDIT_NOTE, fHandler);
    protected Button adjustButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_ADJUST, ADJUST_PAYMENT, fHandler);
    protected Button uncollectableButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_VOID, "Mark Uncollectible", fHandler);
    private static final Logger LOGGER = Logger.getLogger(AccountReceivableBaseListUI.class.getName());

    public AccountReceivableBaseListUI() {
        f0 = new Date();
        Calendar c30 = Calendar.getInstance();
        c30.setTime(new Date());
        c30.add(Calendar.DATE, -30);
        f30 = c30.getTime();
        Calendar c60 = Calendar.getInstance();
        c60.setTime(new Date());
        c60.add(Calendar.DATE, -60);
        f60 = c60.getTime();
        Calendar c90 = Calendar.getInstance();
        c90.setTime(new Date());
        c90.add(Calendar.DATE, -90);
        f90 = c90.getTime();
        fReportButtonPane = createReportButtonPane();
        fBottomPane = createBottomPane();
        mainView = createMainView();

        loadData();
        selectedItems = FXCollections.observableSet();
        selectedItems.addListener((SetChangeListener<AccountReceivable>) change -> {
            if (change.wasAdded()) {
                AccountReceivable ar = change.getElementAdded();
                if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)
                        && ar.getPaidAmount().compareTo(BigDecimal.ZERO) == 0) {
                    ar.setPaidAmount(ar.getBalanceAmount().subtract(ar.getDiscountAmount()));
                } else if (ar.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)
                        && ar.getPaidAmount().compareTo(BigDecimal.ZERO) == 0) {
                    ar.setPaidAmount(ar.getBalanceAmount().negate());
                }
            }
            if (change.wasRemoved()) {
                AccountReceivable ar = change.getElementRemoved();
                ar.setPaidAmount(BigDecimal.ZERO);
            }
            totalProperty.set(selectedItems.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
            fTableView.refresh();
        });
        fSummaryTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends List<AccountReceivable>> observable, List<AccountReceivable> newValue, List<AccountReceivable> oldValue) -> {
            accountReceivableList = new ArrayList<>();
            if (observable != null && observable.getValue() != null) {
                accountReceivableList = observable.getValue().stream().filter(e -> e.getCollectable()).collect(Collectors.toList());
                selectedItems.clear();
            }
            fTableView.setItems(FXCollections.observableList(accountReceivableList));
        });
        fDiscountPane = createDiscountPane();
        fToPayPane = createToPayPane();
        fNotePane = createNoteEditPane();
        if (Config.checkTransactionRequireLogin()) {
            LoginUI.login();
        }
    }

    private void loadData() {
        Map<Customer, List<AccountReceivable>> groupByCustomer = daoAccountReceivable.readOrderBy(AccountReceivable_.store, Config.getStore(),
                AccountReceivable_.status, DBConstants.STATUS_OPEN, AccountReceivable_.dateProcessed, AppConstants.ORDER_BY_ASC)
                .stream()
                .filter(e -> e.getCustomer() != null && e.getAccountReceivableType() != null)
                .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)
                || e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
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
            case PRINT_AR_REPORT:
                if (fSummaryTable.getSelectionModel().getSelectedItem() != null) {
                    fList.clear();
                    fList = fSummaryTable.getSelectionModel().getSelectedItem();
                    Platform.runLater(() -> {
                        AccountReceivableStatementReport layout = new AccountReceivableStatementReport(fList);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case PRINT_AR_SUMMARY_REPORT:
                if (!fSummaryList.isEmpty()) {
                    Platform.runLater(() -> {
                        AccountReceivableAgingReportLayout layout = new AccountReceivableAgingReportLayout(fSummaryList);
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
                    BigDecimal totatDebitAmount = accountReceivableList
                            .stream()
                            .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE))
                            .map(e -> e.getBalanceAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                    BigDecimal totatCreditAmount = accountReceivableList
                            .stream()
                            .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                            .map(e -> e.getBalanceAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
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
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_ENTER_DISCOUNT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getAccountReceivableType() != null
                        && fTableView.getSelectionModel().getSelectedItem().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fEntity.setDiscountAmount(BigDecimal.ZERO);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fDiscountPane, ENTER_DISCOUNT);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            if (fEntity.getDiscountAmount().compareTo(fEntity.getBalanceAmount()) > 0) {
                                lblWarning.setText("Invalid Discount Amount!");
                                event.consume();
                            } else {
                                if (fEntity.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
                                    fEntity.setPaidAmount(fEntity.getBalanceAmount().subtract(fEntity.getDiscountAmount()));
                                }
                                totalProperty.set(selectedItems.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
                                fTableView.refresh();
                                fTableView.requestFocus();
                                fTableView.getSelectionModel().select(fEntity);
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    }
                    );
                    Platform.runLater(() -> dataUI.getTextField(AccountReceivable_.discountAmount).requestFocus());
                    fInputDialog.showDialog();
                }
                break;

            case AppConstants.ACTION_ADJUST:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getAccountReceivableType() != null
                        && fTableView.getSelectionModel().getSelectedItem().getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        Logger.getLogger(ItemListUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fToPayPane, ADJUST_PAYMENT);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            if (fEntity.getPaidAmount().compareTo(BigDecimal.ZERO) == 0 || fEntity.getPaidAmount().compareTo(fEntity.getBalanceAmount().subtract(zeroIfNull(fEntity.getDiscountAmount()))) > 0) {
                                lblWarning1.setText("Invalid Payment Amount!");
                                event.consume();
                            } else {
                                totalProperty.set(selectedItems.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
                                fTableView.refresh();
                                fTableView.requestFocus();
                                fTableView.getSelectionModel().select(fEntity);
                            }
                        } catch (Exception ex) {
                            Logger.getLogger(AccountReceivableBaseListUI.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(AccountReceivable_.paidAmount).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_LINE_NOTE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNotePane, EDIT_NOTE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        if (isEmpty(fEntity.getNote())) {
                            fEntity.setNote(null);
                        }
                        fTableView.refresh();
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    Platform.runLater(() -> dataUI.getTextArea(AccountReceivable_.note).requestFocus());
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
                        fEntity.setGlCreditAmount(fEntity.getBalanceAmount());
                        fEntity.setGlDebitAmount(BigDecimal.ZERO);
                        fEntity.setGlAccount(Integer.toString(1));
                        fEntity.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_UNCOLLECTABLE + " Invoice " + getString(fEntity.getInvoiceNumber()));
                        fEntity.setBatch(batch);
                        fEntity.setStatus(DBConstants.STATUS_CLOSE);
                        daoAccountReceivable.update(fEntity);
                        batch.getAccountReceivables().add(fEntity);
                        if (batch.getUncollectableDebitAmount() != null) {
                            BigDecimal uncollectableDebitAmount = batch.getUncollectableDebitAmount();
                            uncollectableDebitAmount = uncollectableDebitAmount.add(fEntity.getBalanceAmount());
                            batch.setUncollectableDebitAmount(uncollectableDebitAmount);
                        } else {
                            batch.setUncollectableDebitAmount(fEntity.getBalanceAmount());
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
                AccountReceivable paymentAr = new AccountReceivable();
                paymentAr.setAccountReceivableNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                paymentAr.setStore(Config.getStore());
                paymentAr.setBatch(batch);
                paymentAr.setInvoice(null);
                paymentAr.setInvoiceNumber(paymentAr.getAccountReceivableNumber());
                paymentAr.setTransaction(paymentAr.getAccountReceivableNumber().toString());
                paymentAr.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                paymentAr.setCustomer(fList.get(0).getCustomer());
                paymentAr.setTerms(fList.get(0).getTerms());
                paymentAr.setCollectable(Boolean.TRUE);
                paymentAr.setDateProcessed(new Date());
                paymentAr.setDateDue(fList.get(0).getDateDue());
                paymentAr.setDateInvoiced(new Date());
                paymentAr.setStatus(DBConstants.STATUS_CLOSE);
                paymentAr.setDiscountAmount(BigDecimal.ZERO);
                paymentAr.setPaidAmount(BigDecimal.ZERO);
                paymentAr.setBalanceAmount(BigDecimal.ZERO);
                paymentAr.setPostedTag(Boolean.FALSE);
                if (netPaymentAmount.compareTo(BigDecimal.ZERO) >= 0) {
                    paymentAr.setTotalAmount(netPaymentAmount);
                    paymentAr.setGlDebitAmount(BigDecimal.ZERO);
                    paymentAr.setGlCreditAmount(netPaymentAmount);
                    paymentAr.setGlAccount(Integer.toString(1));
                    paymentAr.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT);
                    paymentAr.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT);
                } else {
                    paymentAr.setTotalAmount(netPaymentAmount.negate());
                    paymentAr.setGlDebitAmount(netPaymentAmount.negate());
                    paymentAr.setGlCreditAmount(BigDecimal.ZERO);
                    paymentAr.setGlAccount(Integer.toString(1));
                    paymentAr.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND);
                    paymentAr.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND);
                }
                if (fPaymentUI != null && !fPaymentUI.getPayments().isEmpty()) {
                    fPaymentUI.getPayments().forEach(e -> {
                        e.setAccountReceivable(paymentAr);
                        e.setBatch(batch);
                        e.setStore(Config.getStore());
                        batch.getPayments().add(e);
                    });
                }
                daoAccountReceivable.insert(paymentAr);
                batch.getAccountReceivables().add(paymentAr);
                BigDecimal paidToAmount = fList.stream()
                        .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE))
                        .map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal creditRedeemedAmount = fList.stream()
                        .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                        .map(e -> e.getPaidAmount().negate()).reduce(BigDecimal.ZERO, BigDecimal::add);
                if (paidToAmount.compareTo(BigDecimal.ZERO) > 0) {
                    if (batch.getPaidToAccount() != null) {
                        BigDecimal paidToAccountAmount = batch.getPaidToAccount();
                        paidToAccountAmount = paidToAccountAmount.add(paidToAmount);
                        batch.setPaidToAccount(paidToAccountAmount);
                    } else {
                        batch.setPaidToAccount(paidToAmount);
                    }
                }
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
                    BigDecimal discountAmount = e.getDiscountAmount();
                    if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                        e.setBalanceAmount(balanceAmount.subtract(paymentAmount.add(discountAmount)));
                        e.setPaidAmount(paymentAmount);
                        e.setGlDebitAmount(BigDecimal.ZERO);
                        e.setGlCreditAmount(paymentAmount);
                        e.setGlAccount(Integer.toString(1));
                    } else {
                        e.setBalanceAmount(balanceAmount.subtract(paymentAmount.negate()));
                        e.setPaidAmount(paymentAmount.negate());
                        e.setGlDebitAmount(paymentAmount.negate());
                        e.setGlCreditAmount(BigDecimal.ZERO);
                        e.setGlAccount(Integer.toString(1));

                    }
                    e.setDateInvoiced(new Date());
                    e.setStatus(DBConstants.STATUS_CLOSE);
                    e.setAccountReceivablePayment(paymentAr);
                    daoAccountReceivable.update(e);
                    if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE) && e.getBalanceAmount().compareTo(BigDecimal.ZERO) != 0) {
                        AccountReceivable newAccountReceivable = new AccountReceivable();
                        newAccountReceivable.setPaidAmount(BigDecimal.ZERO);
                        newAccountReceivable.setDiscountAmount(BigDecimal.ZERO);
                        newAccountReceivable.setBalanceAmount(e.getBalanceAmount());
                        newAccountReceivable.setTotalAmount(e.getTotalAmount());
                        newAccountReceivable.setGlDebitAmount(e.getBalanceAmount());
                        newAccountReceivable.setGlCreditAmount(BigDecimal.ZERO);
                        newAccountReceivable.setDateProcessed(e.getDateInvoiced());
                        newAccountReceivable.setAccountReceivableNumber(e.getAccountReceivableNumber());
                        newAccountReceivable.setInvoice(e.getInvoice());
                        newAccountReceivable.setInvoiceNumber(e.getInvoiceNumber());
                        newAccountReceivable.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                        newAccountReceivable.setCustomer(e.getCustomer());
                        newAccountReceivable.setTerms(e.getTerms());
                        newAccountReceivable.setStore(Config.getStore());
                        newAccountReceivable.setTransaction(e.getTransaction());
                        newAccountReceivable.setDateDue(e.getDateDue());
                        newAccountReceivable.setDateInvoiced(e.getDateInvoiced());
                        newAccountReceivable.setStatus(DBConstants.STATUS_OPEN);
                        newAccountReceivable.setAccountReceivableType(e.getAccountReceivableType());
                        newAccountReceivable.setNote(e.getNote());
                        newAccountReceivable.setCollectable(Boolean.TRUE);
                        daoAccountReceivable.insert(newAccountReceivable);
                    }
                });
                daoBatch.update(batch);
                loadData();
                Platform.runLater(() -> {
                    AccountReceivablePaymentLayout layout = new AccountReceivablePaymentLayout(fList);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                        selectedItems.clear();
                    } catch (DRException ex) {
                        Logger.getLogger(AccountReceivableBaseListUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(6));
        mainPane.setAlignment(Pos.CENTER);
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
        accountCol.setPrefWidth(130);

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
        companyCol.setPrefWidth(318);

        TableColumn<List<AccountReceivable>, BigDecimal> l130Col = new TableColumn<>("1-30");
        l130Col.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().after(f30))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l130Col.setCellFactory(stringCell(Pos.CENTER));
        l130Col.setPrefWidth(110);

        TableColumn<List<AccountReceivable>, BigDecimal> l3160Col = new TableColumn<>("31-60");
        l3160Col.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().after(f60) && e.getDateDue().before(f30))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l3160Col.setCellFactory(stringCell(Pos.CENTER));
        l3160Col.setPrefWidth(110);

        TableColumn<List<AccountReceivable>, BigDecimal> l6190Col = new TableColumn<>("61-90");
        l6190Col.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().after(f90) && e.getDateDue().before(f60))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l6190Col.setCellFactory(stringCell(Pos.CENTER));
        l6190Col.setPrefWidth(110);

        TableColumn<List<AccountReceivable>, BigDecimal> l91Col = new TableColumn<>("90+");
        l91Col.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().before(f90))
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l91Col.setCellFactory(stringCell(Pos.CENTER));
        l91Col.setPrefWidth(110);

        TableColumn<List<AccountReceivable>, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((CellDataFeatures<List<AccountReceivable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .map(e -> {
                            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                                return e.getBalanceAmount();
                            } else {
                                return e.getBalanceAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(110);

        fSummaryTable.getColumns().add(companyCol);
        fSummaryTable.getColumns().add(accountCol);
        fSummaryTable.getColumns().add(l130Col);
        fSummaryTable.getColumns().add(l3160Col);
        fSummaryTable.getColumns().add(l6190Col);
        fSummaryTable.getColumns().add(l91Col);
        fSummaryTable.getColumns().add(totalCol);
        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fSummaryTable.setPrefHeight(300);
        setTableWidth(fSummaryTable);

        mainPane.add(fSummaryTable, 0, 1);
        mainPane.add(fReportButtonPane, 0, 2);
        mainPane.add(createDetailPane(), 0, 3);
        mainPane.add(fBottomPane, 0, 4);
        return mainPane;
    }

    private GridPane createDiscountPane() {
        GridPane discountPane = new GridPane();
        discountPane.getStyleClass().add("editView");
        discountPane.setVgap(3);
        add(discountPane, "Discount Ammount: ", dataUI.createTextField(AccountReceivable_.discountAmount, 90), fListener, 0);
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(40);
        discountPane.getRowConstraints().add(con);
        discountPane.add(lblWarning, 0, 2, 2, 1);
        return discountPane;
    }

    private GridPane createToPayPane() {
        GridPane toPayPane = new GridPane();
        toPayPane.getStyleClass().add("editView");
        toPayPane.setVgap(3);
        add(toPayPane, "To Pay Ammount: ", dataUI.createTextField(AccountReceivable_.paidAmount, 90), fListener, 0);
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(40);
        toPayPane.getRowConstraints().add(con);
        toPayPane.add(lblWarning1, 0, 2, 2, 1);
        return toPayPane;
    }

    private GridPane createNoteEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Note:", dataUI.createTextArea(AccountReceivable_.note), 200, 350, fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    private Node createDetailPane() {
        selectedCol.setCellValueFactory((CellDataFeatures<AccountReceivable, AccountReceivable> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<AccountReceivable, AccountReceivable> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(92);

        TableColumn<AccountReceivable, String> invoiceDateCol = new TableColumn<>("Date");
        invoiceDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        invoiceDateCol.setPrefWidth(75);

        TableColumn<AccountReceivable, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateDue.getName()));
        dueDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dueDateCol.setPrefWidth(75);

        TableColumn<AccountReceivable, Integer> agingCol = new TableColumn<>("Aging");
        agingCol.setCellValueFactory((CellDataFeatures<AccountReceivable, Integer> p) -> {
            if (p.getValue().getDateDue() != null) {
                int diffInDays = (int) (((new Date()).getTime() - p.getValue().getDateDue().getTime()) / (1000 * 60 * 60 * 24));
                if (diffInDays < 0) {
                    diffInDays = 0;
                }
                return new ReadOnlyObjectWrapper(diffInDays);
            } else {
                return new ReadOnlyObjectWrapper(0);
            }
        });
        agingCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        agingCol.setPrefWidth(60);

        TableColumn<AccountReceivable, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.terms.getName()));
        termCol.setCellFactory(stringCell(Pos.CENTER));
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

        TableColumn<AccountReceivable, String> discountCol = new TableColumn<>("Discount");
        discountCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.discountAmount.getName()));
        discountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        discountCol.setPrefWidth(80);

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

        TableColumn<AccountReceivable, String> amountToPayCol = new TableColumn<>("Amount To Pay");
        amountToPayCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.paidAmount.getName()));
        amountToPayCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountToPayCol.setPrefWidth(100);

        TableColumn<AccountReceivable, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.note.getName()));
        noteCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        noteCol.setPrefWidth(170);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(dueDateCol);
        fTableView.getColumns().add(agingCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(balanceCol);
        fTableView.getColumns().add(discountCol);
        fTableView.getColumns().add(creditCol);
        fTableView.getColumns().add(noteCol);
        fTableView.getColumns().add(amountToPayCol);
        fTableView.setPrefHeight(150);
        setTableWidth(fTableView);
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
        bottomPane.getChildren().addAll(totalBox, filler, createPayDiscountAdjustCloseButtonPane());
        bottomPane.setAlignment(Pos.CENTER);
        return bottomPane;
    }

    private HBox createReportButtonPane() {
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        Button arReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_AR_REPORT, "AR Statement", fHandler);
        Button arSummaryReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_AR_SUMMARY_REPORT, "AR Aging Report", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(arReportButton, arSummaryReportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private HBox createPayDiscountAdjustCloseButtonPane() {
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        Button payButton = ButtonFactory.getButton(ButtonFactory.BUTTON_TENDER, AppConstants.ACTION_TENDER, "Pay Selected", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(adjustButton, discountButton, uncollectableButton, noteButton, payButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            if (fInputDialog.getTitle().equalsIgnoreCase(ENTER_DISCOUNT)) {
                saveBtn.setDisable(dataUI.getTextField(AccountReceivable_.discountAmount).getText().trim().isEmpty());
            }
            if (fInputDialog.getTitle().equalsIgnoreCase(ADJUST_PAYMENT)) {
                saveBtn.setDisable(dataUI.getTextField(AccountReceivable_.paidAmount).getText().trim().isEmpty());
            }
        }
    }
}

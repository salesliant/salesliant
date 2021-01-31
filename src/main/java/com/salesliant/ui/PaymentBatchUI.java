package com.salesliant.ui;

import com.salesliant.client.ClientView;
import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Batch_;
import com.salesliant.entity.CurrencyCoin;
import com.salesliant.entity.CurrencyCoin_;
import com.salesliant.entity.Payment;
import com.salesliant.entity.PaymentBatch;
import com.salesliant.entity.PaymentType;
import com.salesliant.entity.Station;
import com.salesliant.report.BatchLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.decimalEditCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.isNumeric;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import static com.salesliant.util.FieldFactory.getDoubleField;
import com.salesliant.widget.RegisterWidget;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public final class PaymentBatchUI extends BaseListUI<PaymentType> {

    private final BaseDao<CurrencyCoin> daoCurrencyCoin = new BaseDao<>(CurrencyCoin.class);
    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private final BaseDao<PaymentType> daoPaymentType = new BaseDao<>(PaymentType.class);
    private final BaseDao<PaymentBatch> daoPaymentBatch = new BaseDao<>(PaymentBatch.class);
    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private TableView<CurrencyCoin> fCountTable = new TableView<>();
    private final TableView<Payment> fPaymentEntryTable = new TableView<>();
    private RegisterWidget fRegisterCombo = new RegisterWidget();
    private final List<PaymentType> fPaymentTypeList;
    private List<CurrencyCoin> fCurrencyCointList;
    private ObservableList<Payment> fPaymentEntryList;
    private Station fStation;
    private PaymentBatch fPaymentBatch;
    private TextField cashLeaveField = getDoubleField();
    private final TextArea fNote = new TextArea();
    private Label countTotalLabel = new Label();
    private BigDecimal fCashCounted, fLeave, fDeposit, fOpen, fClose;
    private boolean fCountOver = false, fCountShort = false;
    private final ObjectProperty<BigDecimal> cashTotalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> cashDepositProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private static final Logger LOGGER = Logger.getLogger(PaymentBatchUI.class.getName());
    private Batch fBatch;

    public PaymentBatchUI() {

        fTableView = new TableView<PaymentType>() {
            @Override
            public void edit(int row, TableColumn<PaymentType, ?> column) {
                if (row >= 0 && row <= (getItems().size() - 1)) {
                    PaymentType entry = getItems().get(row);
                    if (entry.getVerificationType() != null && entry.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !entry.getIsNetTerm()) {
                        return;
                    }
                }
                super.edit(row, column);
            }
        };
        fCountTable.setDisable(true);
        fCountTable.setEditable(true);
        fCountTable.setFixedCellSize(22.0);
        fTableView.setEditable(true);
        fTableView.setFixedCellSize(22.0);
        fTableView.setRowFactory(mouseClickListener(1));
        fCurrencyCointList = daoCurrencyCoin.read();
        fCurrencyCointList.stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()));
        fCountTable.setItems(FXCollections.observableList(fCurrencyCointList));
        fPaymentTypeList = daoPaymentType.read();
        fPaymentTypeList.stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()));
        fTableView.setItems(FXCollections.observableList(fPaymentTypeList));
        fRegisterCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Station> observable, Station newValue, Station oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                Station station = observable.getValue();
                Batch batch = daoBatch.find(Batch_.store, Config.getStore(), Batch_.station, station.getId(), Batch_.status, DBConstants.STATUS_OPEN);
                if (batch == null) {
                    saveBtn.setDisable(true);
                    fCountTable.setDisable(true);
                    fCountTable.setItems(FXCollections.observableList(fCurrencyCointList));
                    fBatch = null;
                } else {
                    saveBtn.setDisable(false);
                    fCountTable.setDisable(false);
                    fStation = fRegisterCombo.getSelectionModel().getSelectedItem();
                    fBatch = batch;
                    fBatch.setEmployee(Config.getEmployee());
                }
            }
        });
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends PaymentType> observable, PaymentType newValue, PaymentType oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                List<Payment> paymentList = new ArrayList<>();
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                if (fBatch != null && !fBatch.getPayments().isEmpty()) {
                    fBatch.getPayments().stream()
                            .filter((payment) -> payment.getPaymentType().getId().equals(fEntity.getId())
                            && payment.getBatch() != null
                            && payment.getBatch().getId().equals(fBatch.getId()))
                            .forEach(e -> {
                                paymentList.add(e);
                            });
                }
                fPaymentEntryList = FXCollections.observableList(paymentList);
                fPaymentEntryTable.setItems(fPaymentEntryList);

            } else {
                fPaymentEntryList.clear();
            }
        });
        if (Config.checkTransactionRequireLogin()) {
            LoginUI.login();
        }
        handleAction(AppConstants.ACTION_OPEN_CLOSE_REGISTER);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_OPEN_CLOSE_REGISTER:
                fBatch = Config.getBatch();
                if (fBatch.getInvoices().isEmpty() && fBatch.getDepositOpenBatchs().isEmpty()
                        && fBatch.getDepositCloseBatchs().isEmpty() && fBatch.getAccountReceivables().isEmpty()) {
                    showAlertDialog("No transcation to batch!");
                    ClientView.closeTab(this.getClass().getName());

                } else {
                    fInputDialog = createSaveCancelUIDialog(createCountPane(), "Count");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        updateTotal();
                        if (fBatch.getOpeningTotal() != null) {
                            fOpen = fBatch.getOpeningTotal();
                        } else {
                            fOpen = BigDecimal.ZERO;
                        }
                        fPaymentBatch = new PaymentBatch();
                        fPaymentBatch.setStation(fStation);
                        fPaymentBatch.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                        fPaymentBatch.setEmployee(Config.getEmployee());
                        fPaymentBatch.setStore(Config.getStore());
                        BigDecimal cash = BigDecimal.ZERO;
                        BigDecimal charge = BigDecimal.ZERO;
                        BigDecimal check = BigDecimal.ZERO;
                        BigDecimal giftCard = BigDecimal.ZERO;
                        BigDecimal giftCertificate = BigDecimal.ZERO;
                        BigDecimal coupon = BigDecimal.ZERO;
                        BigDecimal onAccount = BigDecimal.ZERO;
                        BigDecimal change = BigDecimal.ZERO;
                        fPaymentBatch.setCashCounted(cash);
                        fPaymentBatch.setCreditCardCounted(charge);
                        fPaymentBatch.setCheckCounted(check);
                        fPaymentBatch.setGiftCardCounted(giftCard);
                        fPaymentBatch.setGiftCertificateCounted(giftCertificate);
                        fPaymentBatch.setCouponCounted(coupon);
                        fPaymentBatch.setOnAccountCounted(onAccount);
                        if (!fBatch.getPayments().isEmpty()) {
                            for (Payment pe : fBatch.getPayments()) {
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH)
                                        && !pe.getPaymentType().getIsNetTerm()) {
                                    cash = cash.add(pe.getTenderedAmount()).subtract(zeroIfNull(pe.getChangeAmount()));
                                    change = change.add(zeroIfNull(pe.getChangeAmount()));
                                }
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                                    check = check.add(pe.getTenderedAmount()).subtract(zeroIfNull(pe.getChangeAmount()));
                                }
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                                    charge = charge.add(pe.getTenderedAmount()).subtract(zeroIfNull(pe.getChangeAmount()));
                                }
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                                    giftCard = giftCard.add(pe.getTenderedAmount()).subtract(zeroIfNull(pe.getChangeAmount()));
                                }
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                                    giftCertificate = giftCertificate.add(pe.getTenderedAmount()).subtract(zeroIfNull(pe.getChangeAmount()));
                                }
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_COUPON)) {
                                    coupon = coupon.add(pe.getTenderedAmount());
                                }
                                if (pe.getTenderedAmount() != null && pe.getPaymentType() != null && pe.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_ACCOUNT)) {
                                    onAccount = onAccount.add(pe.getTenderedAmount()).subtract(zeroIfNull(pe.getChangeAmount()));
                                }
                            }
                        }
                        fPaymentBatch.setCashTendered(cash);
                        fPaymentBatch.setCreditCardTendered(charge);
                        fPaymentBatch.setCheckTendered(check);
                        fPaymentBatch.setGiftCardTendered(giftCard);
                        fPaymentBatch.setGiftCertificateTendered(giftCertificate);
                        fPaymentBatch.setCouponTendered(coupon);
                        fPaymentBatch.setOnAccountTendered(onAccount);
                        fPaymentBatch.setCashCounted(fCashCounted);
                        fBatch.setTotalTendered(cash.subtract(change));
                        fBatch.setTotalChange(change);
                        daoBatch.update(fBatch);
                        mainView = createMainView();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_POST:
                Response postResponse = createConfirmResponseDialog("Do you want to post this batch?");
                if (postResponse.equals(Response.YES)) {
                    Platform.runLater(() -> handleAction(AppConstants.ACTION_COMMIT));
                }
                break;

            case AppConstants.ACTION_COMMIT:
                cashTotalProperty.set(fCashCounted);
                cashDepositProperty.set(fCashCounted.subtract(fOpen));
                cashLeaveField.setText(getDecimalFormat().format(fOpen));
                Response commitResponse = createSaveCancelResponseDialog(createFinalPane(), "Deposit");
                if (commitResponse.equals(Response.SAVE)) {
                    if (isNumeric(cashLeaveField.getText())) {
                        fClose = new BigDecimal(cashLeaveField.getText());
                    }
                    fBatch.setDateClosed(new Timestamp(new Date().getTime()));
                    fBatch.setStatus(DBConstants.STATUS_CLOSE);
                    fBatch.setClosingTotal(fClose);
                    fPaymentBatch.setCashLeft(fClose);
                    checkBehavior();
                    if (!fCountOver && !fCountShort) {
                        fPaymentBatch.setBehaviorType(DBConstants.STATUS_PAYMENT_NORMAL);
                    } else if (fCountOver && !fCountShort) {
                        fPaymentBatch.setBehaviorType(DBConstants.STATUS_PAYMENT_OVER);
                    } else if (!fCountOver && fCountShort) {
                        fPaymentBatch.setBehaviorType(DBConstants.STATUS_PAYMENT_SHORT);
                    } else if (fCountOver && fCountShort) {
                        fPaymentBatch.setBehaviorType(DBConstants.STATUS_PAYMENT_MIX);
                    }
                    if (fNote.getText() != null && !fNote.getText().trim().isEmpty()) {
                        fPaymentBatch.setNote(fNote.getText().trim());
                    }
                    daoPaymentBatch.insert(fPaymentBatch);
                    fBatch.setPaymentBatch(fPaymentBatch);
                    Config.closeBatch(fBatch);
                    Batch openningBatch = new Batch();
                    openningBatch.setStation(fStation.getTenderedStation());
                    openningBatch.setBatchNumber(Config.getNumber(DBConstants.SEQ_BATCH_NUMBER));
                    openningBatch.setStore(Config.getStore());
                    openningBatch.setStatus(DBConstants.STATUS_OPEN);
                    openningBatch.setDateOpened(new Timestamp(new Date().getTime()));
                    openningBatch.setOpeningTotal(fClose);
                    daoBatch.insert(openningBatch);
                    Platform.runLater(() -> handleAction(AppConstants.ACTION_CANCEL));
                    Platform.runLater(() -> {
                        BatchLayout layout = new BatchLayout(fBatch);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                Platform.runLater(() -> cashLeaveField.requestFocus());
                break;
            case AppConstants.ACTION_CANCEL:
                if (fInputDialog != null) {
                    fInputDialog.close();
                }
                ClientView.closeTab(this.getClass().getName());
                break;
            case AppConstants.ACTION_TABLE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.refresh();
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(i);
                    fTableView.getFocusModel().focus(i, fTableView.getColumns().get(2));
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        TableColumn<PaymentType, String> paymentTypeCol = new TableColumn<>("Payment Type");
        paymentTypeCol.setCellValueFactory((CellDataFeatures<PaymentType, String> p) -> {
            if (p.getValue().getCode() != null) {
                return new SimpleStringProperty(p.getValue().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        paymentTypeCol.setPrefWidth(240);
        paymentTypeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        paymentTypeCol.setEditable(false);
        paymentTypeCol.setResizable(false);

        TableColumn<PaymentType, String> typeTenderedAmountCol = new TableColumn<>("Tendered Amount");
        typeTenderedAmountCol.setCellValueFactory((CellDataFeatures<PaymentType, String> p) -> {
            if (p.getValue() != null && p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !p.getValue().getIsNetTerm()) {
                return new SimpleStringProperty(getString(fPaymentBatch.getCashTendered()));
            } else if (p.getValue() != null && p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                return new SimpleStringProperty(getString(fPaymentBatch.getCheckTendered()));
            } else if (p.getValue() != null && p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                return new SimpleStringProperty(getString(fPaymentBatch.getCreditCardTendered()));
            } else if (p.getValue() != null && p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_COUPON)) {
                return new SimpleStringProperty(getString(fPaymentBatch.getCouponTendered()));
            } else if (p.getValue() != null && p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                return new SimpleStringProperty(getString(fPaymentBatch.getGiftCardTendered()));
            } else if (p.getValue() != null && p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                return new SimpleStringProperty(getString(fPaymentBatch.getGiftCertificateTendered()));
            } else if (p.getValue() != null && p.getValue().getIsNetTerm() != null && p.getValue().getIsNetTerm()) {
                return new SimpleStringProperty(getString(fPaymentBatch.getOnAccountTendered()));
            } else {
                return null;
            }
        });
        typeTenderedAmountCol.setPrefWidth(160);
        typeTenderedAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        typeTenderedAmountCol.setEditable(false);
        typeTenderedAmountCol.setResizable(false);

        TableColumn<PaymentType, BigDecimal> typeCountedAmountCol = new TableColumn<>("Counted Amount");
        typeCountedAmountCol.setCellValueFactory((CellDataFeatures<PaymentType, BigDecimal> p) -> {
            BigDecimal amount = BigDecimal.ZERO;
            if (p.getValue() != null) {
                if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !p.getValue().getIsNetTerm()) {
                    amount = zeroIfNull(fPaymentBatch.getCashCounted());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                    amount = zeroIfNull(fPaymentBatch.getCheckCounted());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                    amount = zeroIfNull(fPaymentBatch.getCreditCardCounted());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_COUPON)) {
                    amount = zeroIfNull(fPaymentBatch.getCouponCounted());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                    amount = zeroIfNull(fPaymentBatch.getGiftCardCounted());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                    amount = zeroIfNull(fPaymentBatch.getGiftCertificateCounted());
                } else if (p.getValue().getIsNetTerm() != null && p.getValue().getIsNetTerm()) {
                    amount = zeroIfNull(fPaymentBatch.getOnAccountCounted());
                }
                return new ReadOnlyObjectWrapper(amount);
            } else {
                return null;
            }
        });
        typeCountedAmountCol.setOnEditCommit((TableColumn.CellEditEvent<PaymentType, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                PaymentType pt = (PaymentType) t.getTableView().getItems().get(t.getTablePosition().getRow());
                BigDecimal amount = t.getNewValue();
                if (pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !pt.getIsNetTerm()) {
                    fPaymentBatch.setCashCounted(amount);
                } else if (pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                    fPaymentBatch.setCheckCounted(amount);
                } else if (pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                    fPaymentBatch.setCreditCardCounted(amount);
                } else if (pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_COUPON)) {
                    fPaymentBatch.setCouponCounted(amount);
                } else if (pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                    fPaymentBatch.setGiftCardCounted(amount);
                } else if (pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                    fPaymentBatch.setGiftCertificateCounted(amount);
                } else if (pt.getIsNetTerm() != null && pt.getIsNetTerm()) {
                    fPaymentBatch.setOnAccountCounted(amount);
                }
                fTableView.refresh();
                fTableView.requestFocus();
            }
        });
        typeCountedAmountCol.setCellFactory(decimalEditCell(Pos.CENTER_RIGHT));
        typeCountedAmountCol.setResizable(false);
        typeCountedAmountCol.setPrefWidth(160);

        TableColumn<PaymentType, BigDecimal> openningCol = new TableColumn<>("Openning Total");
        openningCol.setCellValueFactory((CellDataFeatures<PaymentType, BigDecimal> p) -> {
            if (p.getValue() != null) {
                if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !p.getValue().getIsNetTerm()) {
                    return new ReadOnlyObjectWrapper(fOpen);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        });
        openningCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        openningCol.setPrefWidth(160);
        openningCol.setEditable(false);
        openningCol.setResizable(false);

        TableColumn<PaymentType, BigDecimal> diffCol = new TableColumn<>("Difference");
        diffCol.setCellValueFactory((CellDataFeatures<PaymentType, BigDecimal> p) -> {
            BigDecimal amount = BigDecimal.ZERO;
            if (p.getValue() != null) {
                if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH) && !p.getValue().getIsNetTerm()) {
                    amount = zeroIfNull(fPaymentBatch.getCashCounted()).subtract(fPaymentBatch.getCashTendered().add(fOpen));
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                    amount = zeroIfNull(fPaymentBatch.getCheckCounted()).subtract(fPaymentBatch.getCheckTendered());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CREDIT_CARD)) {
                    amount = zeroIfNull(fPaymentBatch.getCreditCardCounted()).subtract(fPaymentBatch.getCreditCardTendered());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_COUPON)) {
                    amount = zeroIfNull(fPaymentBatch.getCouponCounted()).subtract(fPaymentBatch.getCouponTendered());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CARD)) {
                    amount = zeroIfNull(fPaymentBatch.getGiftCardCounted()).subtract(fPaymentBatch.getGiftCardTendered());
                } else if (p.getValue().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE)) {
                    amount = zeroIfNull(fPaymentBatch.getGiftCertificateCounted()).subtract(fPaymentBatch.getGiftCertificateTendered());
                } else if (p.getValue().getIsNetTerm() != null && p.getValue().getIsNetTerm()) {
                    amount = zeroIfNull(fPaymentBatch.getOnAccountCounted()).subtract(fPaymentBatch.getOnAccountTendered());
                }
                return new ReadOnlyObjectWrapper(amount);
            } else {
                return null;
            }
        });
        diffCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        diffCol.setPrefWidth(160);
        diffCol.setEditable(false);
        diffCol.setResizable(false);

        fTableView.getColumns().add(paymentTypeCol);
        fTableView.getColumns().add(typeTenderedAmountCol);
        fTableView.getColumns().add(typeCountedAmountCol);
        fTableView.getColumns().add(openningCol);
        fTableView.getColumns().add(diffCol);
        fTableView.setPrefHeight(240);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 0);
        mainPane.add(createDetailPane(), 0, 1);
        mainPane.add(createPostCancelButtonPane(), 0, 2);

        return mainPane;
    }

    private Node createDetailPane() {
        VBox invoiceBox = new VBox();
        Label invoiceLabel = new Label("Transaction");

        TableColumn<Payment, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((TableColumn.CellDataFeatures<Payment, String> p) -> {
            if (p.getValue() != null && p.getValue().getDeposit() != null) {
                return new SimpleStringProperty("Order");
            } else if (p.getValue() != null && p.getValue().getInvoice() != null) {
                return new SimpleStringProperty("Invoice");
            } else if (p.getValue() != null && p.getValue().getAccountReceivable() != null) {
                return new SimpleStringProperty("Account Receivable");
            } else if (p.getValue() != null && p.getValue().getDropPayout() != null) {
                return new SimpleStringProperty("Drop Payout");
            } else {
                return null;
            }
        });
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        typeCol.setPrefWidth(120);

        TableColumn<Payment, String> orderNumberCol = new TableColumn<>("Transaction No");
        orderNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<Payment, String> p) -> {
            if (p.getValue() != null && p.getValue().getDeposit() != null && p.getValue().getDeposit().getSalesOrder() != null) {
                return new SimpleStringProperty(p.getValue().getDeposit().getSalesOrder().getSalesOrderNumber().toString());
            } else if (p.getValue() != null && p.getValue().getInvoice() != null && p.getValue().getInvoice().getInvoiceNumber() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getInvoiceNumber().toString());
            } else if (p.getValue() != null && p.getValue().getAccountReceivable() != null) {
                return new SimpleStringProperty(p.getValue().getAccountReceivable().getAccountReceivableNumber().toString());
            } else if (p.getValue() != null && p.getValue().getDropPayout() != null) {
                return new SimpleStringProperty(p.getValue().getDropPayout().getEmployee().getNameOnSalesOrder());
            } else {
                return null;
            }
        });
        orderNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        orderNumberCol.setPrefWidth(100);

        TableColumn<Payment, String> customerNameCol = new TableColumn<>("Customer Name");
        customerNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Payment, String> p) -> {
            String name = "";
            if (p.getValue() != null) {
                if (p.getValue().getDeposit() != null && p.getValue().getDeposit().getSalesOrder() != null && p.getValue().getDeposit().getSalesOrder().getCustomer() != null) {
                    if (p.getValue().getDeposit().getSalesOrder().getCustomer().getCompany() != null) {
                        name = name + p.getValue().getDeposit().getSalesOrder().getCustomer().getCompany();
                    } else {
                        name = name + (isEmpty(p.getValue().getDeposit().getSalesOrder().getCustomer().getFirstName()) ? "" : p.getValue().getDeposit().getSalesOrder().getCustomer().getFirstName())
                                + (isEmpty(p.getValue().getDeposit().getSalesOrder().getCustomer().getLastName()) ? "" : " ")
                                + (isEmpty(p.getValue().getDeposit().getSalesOrder().getCustomer().getLastName()) ? "" : p.getValue().getDeposit().getSalesOrder().getCustomer().getLastName());
                    }
                    return new SimpleStringProperty(name);
                } else if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getCustomerName() != null) {
                    if (p.getValue().getInvoice().getBillToCompany() != null) {
                        name = name + p.getValue().getInvoice().getBillToCompany();
                    } else {
                        name = name + p.getValue().getInvoice().getCustomerName();
                    }
                    return new SimpleStringProperty(name);
                } else if (p.getValue().getAccountReceivable() != null && p.getValue().getAccountReceivable().getCustomer() != null) {
                    if (p.getValue().getAccountReceivable().getCustomer().getCompany() != null) {
                        name = name + p.getValue().getAccountReceivable().getCustomer().getCompany();
                    } else {
                        name = name + (isEmpty(p.getValue().getAccountReceivable().getCustomer().getFirstName()) ? "" : p.getValue().getAccountReceivable().getCustomer().getFirstName())
                                + (isEmpty(p.getValue().getAccountReceivable().getCustomer().getLastName()) ? "" : " ")
                                + (isEmpty(p.getValue().getAccountReceivable().getCustomer().getLastName()) ? "" : p.getValue().getAccountReceivable().getCustomer().getLastName());
                    }
                    return new SimpleStringProperty(name);
                } else if (p.getValue().getDropPayout() != null && p.getValue().getDropPayout().getRecipient() != null) {
                    return new SimpleStringProperty(p.getValue().getDropPayout().getRecipient());
                }
            }
            return new SimpleStringProperty(name);
        });
        customerNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerNameCol.setPrefWidth(310);

        TableColumn<Payment, BigDecimal> depositCol = new TableColumn<>("Deposit");
        depositCol.setCellValueFactory((TableColumn.CellDataFeatures<Payment, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getDeposit() != null) {
                BigDecimal deposit = p.getValue().getDeposit().getPayments().stream().map(Payment::getTenderedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(deposit);
            } else if (p.getValue() != null && p.getValue().getInvoice() != null && p.getValue().getInvoice().getDepositAmount() != null
                    && p.getValue().getInvoice().getDepositAmount().compareTo(BigDecimal.ZERO) > 0) {
                return new ReadOnlyObjectWrapper(p.getValue().getInvoice().getDepositAmount());
            } else {
                return null;
            }
        });
        depositCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        depositCol.setPrefWidth(90);

        TableColumn<Payment, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((TableColumn.CellDataFeatures<Payment, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getDeposit() != null && p.getValue().getDeposit().getSalesOrder() != null && p.getValue().getDeposit().getSalesOrder().getTotal() != null) {
                return new ReadOnlyObjectWrapper(p.getValue().getDeposit().getSalesOrder().getTotal());
            } else if (p.getValue() != null && p.getValue().getInvoice() != null && p.getValue().getInvoice().getTotal() != null) {
                return new ReadOnlyObjectWrapper(p.getValue().getInvoice().getTotal());
            } else if (p.getValue() != null && p.getValue().getAccountReceivable() != null && p.getValue().getAccountReceivable().getTotalAmount() != null) {
                switch (p.getValue().getAccountReceivable().getAccountReceivableType()) {
                    case DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_PAYMENT:
                        return new ReadOnlyObjectWrapper(p.getValue().getAccountReceivable().getTotalAmount());
                    case DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND:
                        return new ReadOnlyObjectWrapper(p.getValue().getAccountReceivable().getTotalAmount().negate());
                    case DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE:
                        return new ReadOnlyObjectWrapper(p.getValue().getAccountReceivable().getTotalAmount());
                    case DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT:
                        return new ReadOnlyObjectWrapper(p.getValue().getAccountReceivable().getTotalAmount().negate());
                    default:
                        return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
                }
            } else if (p.getValue() != null && p.getValue().getDropPayout() != null && p.getValue().getDropPayout().getAmount() != null) {
                return new ReadOnlyObjectWrapper(p.getValue().getDropPayout().getAmount());
            } else {
                return null;
            }
        });
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(90);

        fPaymentEntryTable.getColumns().add(typeCol);
        fPaymentEntryTable.getColumns().add(orderNumberCol);
        fPaymentEntryTable.getColumns().add(customerNameCol);
        fPaymentEntryTable.getColumns().add(depositCol);
        fPaymentEntryTable.getColumns().add(totalCol);
        fPaymentEntryTable.setPrefHeight(150);
        setTableWidth(fPaymentEntryTable);
        fPaymentEntryTable.setEditable(false);
        fPaymentEntryTable.setFocusTraversable(false);
        invoiceBox.getChildren().addAll(invoiceLabel, fPaymentEntryTable);

        VBox noteBox = new VBox();
        Label noteLabel = new Label("Note");
        fNote.setPrefSize(150, 150);
        fNote.setWrapText(true);
        noteBox.getChildren().addAll(noteLabel, fNote);

        HBox detail = new HBox();
        detail.setSpacing(5);
        detail.setPadding(new Insets(6));
        detail.getChildren().addAll(invoiceBox, noteBox);
        detail.setAlignment(Pos.CENTER);
        detail.getStyleClass().add("hboxPane");
        return detail;
    }

    private Node createCountPane() {
        GridPane countPane = new GridPane();
        countPane.getStyleClass().add("editView");
        Label selectLabel = new Label("Select a register: ");
        selectLabel.setPrefWidth(150);
        fRegisterCombo.setPrefWidth(128);
        TableColumn<CurrencyCoin, String> nameCol = new TableColumn<>("Currency");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(CurrencyCoin_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER));
        nameCol.setPrefWidth(160);
        nameCol.setResizable(false);
        nameCol.setEditable(false);

        TableColumn<CurrencyCoin, BigDecimal> countCol = new TableColumn<>("Count");
        countCol.setCellValueFactory(new PropertyValueFactory<>(CurrencyCoin_.count.getName()));
        countCol.setOnEditCommit((TableColumn.CellEditEvent<CurrencyCoin, BigDecimal> t) -> {
            if (t.getNewValue() != null && !t.getNewValue().equals(t.getOldValue())) {
                ((CurrencyCoin) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCount(t.getNewValue().intValue());
                fCountTable.refresh();
                fCountTable.requestFocus();
                updateTotal();
            }
        });
        countCol.setCellFactory(decimalEditCell(Pos.CENTER_RIGHT));
        countCol.setPrefWidth(120);
        countCol.setResizable(false);

        TableColumn<CurrencyCoin, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((CellDataFeatures<CurrencyCoin, BigDecimal> p) -> {
            BigDecimal total;
            if (p.getValue() != null && p.getValue().getValue() != null) {
                total = p.getValue().getValue().multiply(new BigDecimal(p.getValue().getCount()));
            } else {
                total = BigDecimal.ZERO;
            }
            return new ReadOnlyObjectWrapper(total);
        });
        totalCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        totalCol.setPrefWidth(100);
        totalCol.setEditable(false);
        totalCol.setResizable(false);

        fCountTable.getColumns().add(nameCol);
        fCountTable.getColumns().add(countCol);
        fCountTable.getColumns().add(totalCol);
        fCountTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fCountTable.setPrefHeight(400);

        Label totalLabel = new Label("Total: ");
        totalLabel.setFont(Font.font("Serif", 15));
        countTotalLabel.setFont(Font.font("Serif", 15));
        totalLabel.setPrefWidth(250);
        countTotalLabel.setPrefWidth(50);
        countTotalLabel.setText("0.00");
        countPane.add(selectLabel, 0, 0);
        countPane.add(fRegisterCombo, 1, 0);
        countPane.add(fCountTable, 0, 1, 2, 1);
        countPane.add(totalLabel, 0, 2);
        countPane.add(countTotalLabel, 1, 2);
        selectLabel.setAlignment(Pos.CENTER_RIGHT);
        totalLabel.setAlignment(Pos.CENTER_RIGHT);
        countTotalLabel.setAlignment(Pos.CENTER_RIGHT);
        GridPane.setHalignment(totalLabel, HPos.RIGHT);
        GridPane.setHalignment(countTotalLabel, HPos.RIGHT);
        GridPane.setHgrow(countTotalLabel, Priority.ALWAYS);
        GridPane.setHalignment(selectLabel, HPos.RIGHT);
        GridPane.setHgrow(fRegisterCombo, Priority.ALWAYS);

        return countPane;
    }

    private Node createFinalPane() {
        GridPane finalPane = new GridPane();
        finalPane.getStyleClass().add("editView");
        Label totalLabel = new Label("Total Cash In Register: ");
        TextField cashTotalField = createLabelField();
        cashTotalField.textProperty().bindBidirectional(cashTotalProperty, getDecimalFormat());
        cashTotalField.setPrefWidth(80);
        Label closeLabel = new Label("Leave In Register: ");
        cashLeaveField.setPrefWidth(80);
        cashLeaveField.textProperty().addListener(doubleValueListener);
        Label depositLabel = new Label("Your Cash Deposit: ");
        TextField cashDepositField = createLabelField();
        cashDepositField.textProperty().bindBidirectional(cashDepositProperty, getDecimalFormat());
        cashDepositField.setPrefWidth(80);
        finalPane.add(totalLabel, 0, 0);
        finalPane.add(cashTotalField, 1, 0);
        finalPane.add(closeLabel, 0, 1);
        finalPane.add(cashLeaveField, 1, 1);
        finalPane.add(depositLabel, 0, 2);
        finalPane.add(cashDepositField, 1, 2);
        GridPane.setHalignment(totalLabel, HPos.RIGHT);
        GridPane.setHalignment(closeLabel, HPos.RIGHT);
        GridPane.setHalignment(depositLabel, HPos.RIGHT);

        return finalPane;
    }

    @Override
    protected void updateTotal() {
        fCashCounted = BigDecimal.ZERO;
        fCountTable.getItems().forEach(e -> fCashCounted = fCashCounted.add(e.getValue().multiply(new BigDecimal(e.getCount()))));
        countTotalLabel.setText(getDecimalFormat().format(fCashCounted));
    }

    private HBox createPostCancelButtonPane() {
        Button postButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST);
        Button cancellButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
        postButton.setId(AppConstants.ACTION_POST);
        cancellButton.setId(AppConstants.ACTION_CANCEL);
        postButton.setOnAction(fHandler);
        cancellButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(postButton);
        buttonGroup.getChildren().add(cancellButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(6);
        return buttonGroup;
    }

    protected ChangeListener doubleValueListener = new ChangeListener<String>() {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            if (newValue.equals("")) {
                return;
            }
            try {
                Double value = Double.valueOf(newValue);
                fLeave = new BigDecimal(value);
                fDeposit = fCashCounted.subtract(fLeave);
                cashDepositProperty.set(fDeposit);
            } catch (NumberFormatException e) {
                ((StringProperty) observable).setValue(oldValue);
            }
        }
    };

    private void checkBehavior() {
        boolean cashOver = false;
        boolean cashShort = false;
        boolean chargeOver = false;
        boolean chargeShort = false;
        boolean checkOver = false;
        boolean checkShort = false;
        boolean giftCardOver = false;
        boolean giftCardShort = false;
        boolean giftCertificateOver = false;
        boolean giftCertificateShort = false;
        boolean couponOver = false;
        boolean couponShort = false;
        boolean onAccountOver = false;
        boolean onAccountShort = false;
        if (fPaymentBatch.getCashCounted().subtract(fPaymentBatch.getCashTendered()).compareTo(BigDecimal.ZERO) > 0) {
            cashOver = true;
        } else if (fPaymentBatch.getCashCounted().subtract(fPaymentBatch.getCashTendered()).compareTo(BigDecimal.ZERO) < 0) {
            cashShort = true;
        }
        if (fPaymentBatch.getCheckCounted().subtract(fPaymentBatch.getCheckTendered()).compareTo(BigDecimal.ZERO) > 0) {
            checkOver = true;
        } else if (fPaymentBatch.getCheckCounted().subtract(fPaymentBatch.getCheckTendered()).compareTo(BigDecimal.ZERO) < 0) {
            checkShort = true;
        }
        if (fPaymentBatch.getCreditCardCounted().subtract(fPaymentBatch.getCreditCardTendered()).compareTo(BigDecimal.ZERO) > 0) {
            chargeOver = true;
        } else if (fPaymentBatch.getCreditCardCounted().subtract(fPaymentBatch.getCreditCardTendered()).compareTo(BigDecimal.ZERO) < 0) {
            chargeShort = true;
        }
        if (fPaymentBatch.getCouponCounted().subtract(fPaymentBatch.getCouponTendered()).compareTo(BigDecimal.ZERO) > 0) {
            couponOver = true;
        } else if (fPaymentBatch.getCouponCounted().subtract(fPaymentBatch.getCouponTendered()).compareTo(BigDecimal.ZERO) < 0) {
            couponShort = true;
        }
        if (fPaymentBatch.getOnAccountCounted().subtract(fPaymentBatch.getOnAccountTendered()).compareTo(BigDecimal.ZERO) > 0) {
            onAccountOver = true;
        } else if (fPaymentBatch.getOnAccountCounted().subtract(fPaymentBatch.getOnAccountTendered()).compareTo(BigDecimal.ZERO) < 0) {
            onAccountShort = true;
        }
        if (fPaymentBatch.getGiftCardCounted().subtract(fPaymentBatch.getGiftCardTendered()).compareTo(BigDecimal.ZERO) > 0) {
            giftCardOver = true;
        } else if (fPaymentBatch.getGiftCardCounted().subtract(fPaymentBatch.getGiftCardTendered()).compareTo(BigDecimal.ZERO) < 0) {
            giftCardShort = true;
        }
        if (fPaymentBatch.getGiftCertificateCounted().subtract(fPaymentBatch.getGiftCertificateTendered()).compareTo(BigDecimal.ZERO) > 0) {
            giftCertificateOver = true;
        } else if (fPaymentBatch.getGiftCertificateCounted().subtract(fPaymentBatch.getGiftCertificateTendered()).compareTo(BigDecimal.ZERO) < 0) {
            giftCertificateShort = true;
        }
        if (cashOver || checkOver || chargeOver || couponOver || onAccountOver || giftCardOver || giftCertificateOver) {
            fCountOver = true;
        }
        if (cashShort || checkShort || chargeShort || couponShort || onAccountShort || giftCardShort || giftCertificateShort) {
            fCountShort = true;
        }
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Payment;
import com.salesliant.entity.PaymentType;
import com.salesliant.entity.PaymentType_;
import com.salesliant.entity.Payment_;
import com.salesliant.entity.SalesOrder;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.decimalEditCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.widget.CustomerCreditWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public final class PaymentUI extends BaseListUI<Payment> {

    private final BaseDao<PaymentType> daoPaymentType = new BaseDao<>(PaymentType.class);
    private final DataUI dataUI = new DataUI(Payment.class);
    public TableView<PaymentType> fPaymentTypeTable = new TableView<>();
    private final ObservableList<PaymentType> fPaymentTypeList;
    private PaymentType fPaymentType;
    private SalesOrder fSalesOrder;
    private List<AccountReceivable> fList;
    private final List<AccountReceivable> fCreditAvailableList = new ArrayList<>();
    private List<AccountReceivable> fCreditAppliedList = new ArrayList<>();
    private Customer fCustomer;
    public BigDecimal fBalance = BigDecimal.ZERO;
    private BigDecimal fTotal = BigDecimal.ZERO;
    private BigDecimal fChangeDue = BigDecimal.ZERO;
    private BigDecimal fCreditAvailableAmount = BigDecimal.ZERO;
    private BigDecimal fCreditAppliedAmount = BigDecimal.ZERO;
    private final ObjectProperty<BigDecimal> depositProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> totalProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> totalDueProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> balanceProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> balanceDueProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> creditProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> customerCreditProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> changeProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> paidProp = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final BooleanProperty visibleProp = new SimpleBooleanProperty(Boolean.FALSE);
    private Boolean visible = Boolean.FALSE;
    public Button cancelButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, "Cancel(ESC)", fHandler);
    private final Button applyButton = ButtonFactory.getButton(ButtonFactory.BUTTON_APPLY, AppConstants.ACTION_APPLY, "Apply(INS)", fHandler);
    private final Button applyCreditButton = ButtonFactory.getButton(ButtonFactory.BUTTON_MOVE_DOWN, APPLY_CREDIT, "Apply Credit(^A)", fHandler);
    private final GridPane checkVerifyPane;
    private final GridPane creditCardVerifPane;
    private final GridPane giftCardVerifyPane;
    private final GridPane giftCertificateVerifyPane;
    private final Button checkVerifySaveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE);
    private final Button checkVerifyCancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
    private final Button creditCardVerifySaveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE);
    private final Button creditCardVerifyCancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
    private final Button giftCardVerifySaveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE);
    private final Button giftCardVerifyCancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
    private final Button giftCertificateVerifySaveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE);
    private final Button giftCertificateVerifyCancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
    private final static String APPLY_CREDIT = "Apply Customer Credit";
    private static final Logger LOGGER = Logger.getLogger(PaymentUI.class.getName());

    public PaymentUI(SalesOrder salesOrder) {
        this.fSalesOrder = salesOrder;
        fCustomer = fSalesOrder.getCustomer();
        if (fCustomer != null && fSalesOrder.getType() != null && fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)
                && fCustomer.getAccountReceivables() != null && !fCustomer.getAccountReceivables().isEmpty()) {
            List<AccountReceivable> list = fCustomer.getAccountReceivables().stream().filter(e -> e.getStatus().equals(DBConstants.STATUS_OPEN)).collect(Collectors.toList());
            if (list != null && !list.isEmpty()) {
                BigDecimal balance = BigDecimal.ZERO;
                for (AccountReceivable art : list) {
                    if (art.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)) {
                        balance = balance.add(art.getBalanceAmount());
                    }
                    if (art.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                        fCreditAvailableAmount = fCreditAvailableAmount.add(art.getBalanceAmount());
                        balance = balance.subtract(art.getBalanceAmount());
                        fCreditAvailableList.add(art);
                        visible = true;
                    }
                }
                visible = balance.compareTo(BigDecimal.ZERO) < 0 && !fCreditAvailableList.isEmpty();
                customerCreditProp.set(fCreditAvailableAmount);
            }

        } else {
            visible = false;
        }
        visibleProp.set(visible);
        mainView = createMainView();
        checkVerifyPane = createCheckVerificationPane();
        creditCardVerifPane = createCreditCardVerificationPane();
        giftCardVerifyPane = createGiftCardVerificationPane();
        giftCertificateVerifyPane = createGiftCertificateVerificationPane();
        if (!fSalesOrder.getDeposits().isEmpty()) {
            BigDecimal depositPayment = fSalesOrder.getDeposits().stream().map(Deposit::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            depositProp.set(depositPayment);
            if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_VOID)) {
                fTotal = depositPayment.negate();
            } else {
                fTotal = fSalesOrder.getTotal().subtract(depositPayment);
            }
        } else {
            fTotal = fSalesOrder.getTotal();
        }
        fBalance = fTotal;
        totalProp.set(fSalesOrder.getTotal());
        totalDueProp.set(fTotal);
        balanceDueProp.set(fBalance);
        List<PaymentType> paymentTypeList;
        if (fCustomer.getCustomerTerm() != null && fCustomer.getCustomerTerm().getStoreAccountTag() != null && fCustomer.getCustomerTerm().getStoreAccountTag()
                && fSalesOrder.getType() != null && fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
            paymentTypeList = daoPaymentType.read().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
        } else if (fTotal != null && fTotal.compareTo(BigDecimal.ZERO) < 0) {
            paymentTypeList = daoPaymentType.read().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
        } else {
            paymentTypeList = daoPaymentType.read(PaymentType_.isNetTerm, 0).stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
        }
        fPaymentTypeList = FXCollections.observableList(paymentTypeList);
        fPaymentTypeTable.setItems(fPaymentTypeList);
        fPaymentTypeTable.setEditable(true);
        fPaymentTypeTable.setFixedCellSize(22.0);
        fEntityList = FXCollections.observableList(new ArrayList<>());
        fTableView.setItems(fEntityList);
        fPaymentTypeTable.refresh();
        fTableView.setFocusTraversable(false);
        fEntityList.addListener((ListChangeListener.Change<? extends Payment> c) -> {
            BigDecimal total = fTableView.getItems().stream().map(Payment::getTenderedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            fBalance = fTotal.subtract(total).subtract(fCreditAppliedAmount);
            if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
                if (fTotal.compareTo(BigDecimal.ZERO) >= 0) {
                    if (fBalance.compareTo(BigDecimal.ZERO) > 0) {
                        fChangeDue = BigDecimal.ZERO;
                    } else {
                        fChangeDue = fBalance.negate();
                        handleAction(AppConstants.ACTION_PROCESS);
                    }
                } else {
                    if (fBalance.compareTo(BigDecimal.ZERO) == 0) {
                        fChangeDue = BigDecimal.ZERO;
                        handleAction(AppConstants.ACTION_PROCESS);
                    } else {
                        fChangeDue = BigDecimal.ZERO;
                    }
                }
            } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_VOID)) {
                if (fBalance.compareTo(BigDecimal.ZERO) == 0) {
                    fChangeDue = BigDecimal.ZERO;
                    handleAction(AppConstants.ACTION_PROCESS);
                }
            } else {
                if (Config.getStore().getOrderDeposit() != null) {
                    BigDecimal minimumDeposit = Config.getStore().getOrderDeposit().divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP).multiply(fTotal);
                    if (total.compareTo(minimumDeposit) > 0) {
                        if (total.compareTo(fTotal) <= 0) {
                            fChangeDue = BigDecimal.ZERO;
                            handleAction(AppConstants.ACTION_PROCESS);
                        } else {
                            fChangeDue = fBalance.negate();
                            handleAction(AppConstants.ACTION_PROCESS);
                        }
                    }
                } else {
                    if (fBalance.compareTo(BigDecimal.ZERO) > 0) {
                        fChangeDue = BigDecimal.ZERO;
                        handleAction(AppConstants.ACTION_PROCESS);
                    } else {
                        fChangeDue = fBalance.negate();
                        handleAction(AppConstants.ACTION_PROCESS);
                    }
                }
            }
            balanceDueProp.set(fBalance);
            paidProp.set(total);
            if (fEntityList.isEmpty()) {
                visibleProp.set(visible);
            } else {
                visibleProp.set(false);
            }
        });
        addListener();
    }

    public PaymentUI(List<AccountReceivable> list) {
        fList = list;
        visibleProp.set(visible);
        customerCreditProp.set(fCreditAvailableAmount);
        mainView = createMainView();
        checkVerifyPane = createCheckVerificationPane();
        creditCardVerifPane = createCreditCardVerificationPane();
        giftCardVerifyPane = createGiftCardVerificationPane();
        giftCertificateVerifyPane = createGiftCertificateVerificationPane();
        fTotal = fList.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
        fBalance = fTotal;
        totalDueProp.set(fTotal);
        balanceDueProp.set(fBalance);
        List<PaymentType> paymentTypeList = daoPaymentType.read(PaymentType_.isNetTerm, 0).stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
        fPaymentTypeList = FXCollections.observableList(paymentTypeList);
        fPaymentTypeTable.setItems(fPaymentTypeList);
        fPaymentTypeTable.setEditable(true);
        fPaymentTypeTable.setFixedCellSize(22.0);
        fEntityList = FXCollections.observableList(new ArrayList<>());
        fTableView.setItems(fEntityList);
        fPaymentTypeTable.refresh();
        fTableView.setFocusTraversable(false);
        fEntityList.addListener((ListChangeListener.Change<? extends Payment> c) -> {
            BigDecimal total = fTableView.getItems().stream().map(Payment::getTenderedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            fBalance = fTotal.subtract(total);
            if (fBalance.compareTo(BigDecimal.ZERO) > 0) {
                fChangeDue = BigDecimal.ZERO;
            } else {
                fChangeDue = fBalance.negate();
                handleAction(AppConstants.ACTION_PROCESS);
            }
            balanceProp.set(fBalance);
            paidProp.set(total);
        });
        addListener();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_APPLY:
                BigDecimal total = fTableView.getItems().stream().map(Payment::getTenderedAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                fPaymentTypeTable.refresh();
                for (PaymentType pt : fPaymentTypeTable.getItems()) {
                    if (pt.getAmount() != null && pt.getAmount().compareTo(BigDecimal.ZERO) != 0) {
                        if (pt.getVerificationType() != null && pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH)) {
                            total = total.add(pt.getAmount());
                            Payment payment = new Payment();
                            payment.setTenderedAmount(pt.getAmount());
                            payment.setPaymentType(pt);
                            payment.setStore(Config.getStore());
                            fTableView.getItems().add(payment);
                            pt.setAmount(BigDecimal.ZERO);
                            fPaymentTypeTable.refresh();
                        } else if (pt.getVerificationType() != null && pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_ACCOUNT)) {
                            BigDecimal amount = pt.getAmount();
                            total = total.add(amount);
                            Payment payment = new Payment();
                            payment.setTenderedAmount(amount);
                            payment.setPaymentType(pt);
                            payment.setStore(Config.getStore());
                            fTableView.getItems().add(payment);
                            pt.setAmount(BigDecimal.ZERO);
                            fPaymentTypeTable.refresh();
                        } else if (pt.getVerificationType() != null && pt.getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                            total = total.add(pt.getAmount());
                            if ((fTotal.subtract(total)).compareTo(BigDecimal.ZERO) < 0) {
                                showAlertDialog("This payment exceeds the balance amount");
                            } else {
                                Payment payment = new Payment();
                                payment.setTenderedAmount(pt.getAmount());
                                payment.setPaymentType(pt);
                                payment.setStore(Config.getStore());
                                try {
                                    dataUI.setData(payment);
                                } catch (Exception ex) {
                                    LOGGER.log(Level.SEVERE, null, ex);
                                }
                                fInputDialog = createUIDialog(checkVerifyPane, "Check Verification");
                                checkVerifySaveBtn.setOnAction(e -> {
                                    try {
                                        dataUI.getData(payment);
                                        fTableView.getItems().add(payment);
                                        pt.setAmount(BigDecimal.ZERO);
                                        fPaymentTypeTable.refresh();
                                        fInputDialog.close();
                                    } catch (Exception ex) {
                                        LOGGER.log(Level.SEVERE, null, ex);
                                    }
                                });
                                checkVerifyCancelBtn.setOnAction(e -> {
                                    fInputDialog.close();
                                });
                                Platform.runLater(() -> {
                                    dataUI.getTextField(Payment_.checkNumber).requestFocus();
                                });
                                fInputDialog.showAndWait();
                            }
                        } else if (pt.getVerificationType() != null && pt.getVerificationType() == DBConstants.TYPE_VERIFICATION_CREDIT_CARD) {
                            total = total.add(pt.getAmount());
                            if ((fTotal.subtract(total)).compareTo(BigDecimal.ZERO) < 0) {
                                showAlertDialog("This payment exceeds the balance amount");
                            } else {
                                Payment payment = new Payment();
                                payment.setTenderedAmount(pt.getAmount());
                                payment.setPaymentType(pt);
                                payment.setStore(Config.getStore());
                                try {
                                    dataUI.setData(payment);
                                } catch (Exception ex) {
                                    LOGGER.log(Level.SEVERE, null, ex);
                                }
                                fInputDialog = createUIDialog(creditCardVerifPane, "Credit Card Verification");
                                creditCardVerifySaveBtn.setOnAction(e -> {
                                    try {
                                        dataUI.getData(payment);
                                        fTableView.getItems().add(payment);
                                        pt.setAmount(BigDecimal.ZERO);
                                        fPaymentTypeTable.refresh();
                                        fInputDialog.close();
                                    } catch (Exception ex) {
                                        LOGGER.log(Level.SEVERE, null, ex);
                                    }
                                });
                                creditCardVerifyCancelBtn.setOnAction(e -> {
                                    fInputDialog.close();
                                });
                                Platform.runLater(() -> {
                                    dataUI.getTextField(Payment_.creditCardApprovalCode).requestFocus();
                                });
                                fInputDialog.showAndWait();
                            }
                        } else if (pt.getVerificationType() != null && pt.getVerificationType() == DBConstants.TYPE_VERIFICATION_GIFT_CARD) {
                            total = total.add(pt.getAmount());
                            if ((fTotal.subtract(total)).compareTo(BigDecimal.ZERO) < 0) {
                                showAlertDialog("This payment exceeds the balance amount");

                            } else {
                                Payment payment = new Payment();
                                payment.setTenderedAmount(pt.getAmount());
                                payment.setPaymentType(pt);
                                payment.setStore(Config.getStore());
                                try {
                                    dataUI.setData(payment);
                                } catch (Exception ex) {
                                    LOGGER.log(Level.SEVERE, null, ex);
                                }
                                fInputDialog = createUIDialog(giftCardVerifyPane, "Gift Card Verification");
                                giftCardVerifySaveBtn.setOnAction(e -> {
                                    try {
                                        dataUI.getData(payment);
                                        fTableView.getItems().add(payment);
                                        pt.setAmount(BigDecimal.ZERO);
                                        fPaymentTypeTable.refresh();
                                        fInputDialog.close();
                                    } catch (Exception ex) {
                                        LOGGER.log(Level.SEVERE, null, ex);
                                    }
                                });
                                giftCardVerifyCancelBtn.setOnAction(e -> {
                                    fInputDialog.close();
                                });
                                Platform.runLater(() -> {
                                    dataUI.getTextField(Payment_.creditCardApprovalCode).requestFocus();
                                });
                                fInputDialog.showAndWait();
                            }
                        } else if (pt.getVerificationType() != null && pt.getVerificationType() == DBConstants.TYPE_VERIFICATION_GIFT_CERTIFICATE) {
                            BigDecimal amount = pt.getAmount();
                            total = total.add(amount);
                            if ((fTotal.subtract(total)).compareTo(BigDecimal.ZERO) < 0) {
                                showAlertDialog("This payment exceeds the balance amount");
                            } else {
                                Payment payment = new Payment();
                                payment.setTenderedAmount(amount);
                                payment.setPaymentType(pt);
                                payment.setStore(Config.getStore());
                                try {
                                    dataUI.setData(payment);
                                } catch (Exception ex) {
                                    LOGGER.log(Level.SEVERE, null, ex);
                                }
                                fInputDialog = createUIDialog(giftCertificateVerifyPane, "Gift Certificate Verification");
                                giftCertificateVerifySaveBtn.setOnAction(e -> {
                                    try {
                                        dataUI.getData(payment);
                                        fTableView.getItems().add(payment);
                                        pt.setAmount(BigDecimal.ZERO);
                                        fPaymentTypeTable.refresh();
                                        fInputDialog.close();
                                    } catch (Exception ex) {
                                        LOGGER.log(Level.SEVERE, null, ex);
                                    }
                                });
                                giftCertificateVerifyCancelBtn.setOnAction(e -> {
                                    fInputDialog.close();
                                });
                                Platform.runLater(() -> {
                                    dataUI.getTextField(Payment_.creditCardApprovalCode).requestFocus();
                                });
                                fInputDialog.showAndWait();
                            }
                        }
                    }
                }
                if (fSalesOrder != null && fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE) && fBalance.compareTo(BigDecimal.ZERO) != 0) {
                    fChangeDue = BigDecimal.ZERO;
                    fPaymentTypeTable.requestFocus();
                    fPaymentTypeTable.getSelectionModel().selectFirst();
                    fPaymentType = fPaymentTypeTable.getSelectionModel().getSelectedItem();
                    fPaymentType.setAmount(fBalance);
                    fPaymentTypeTable.refresh();
                    fPaymentTypeTable.getFocusModel().focus(fPaymentTypeTable.getSelectionModel().getSelectedIndex(), fPaymentTypeTable.getColumns().get(1));
                } else if (fList != null && !fList.isEmpty() && fBalance.compareTo(BigDecimal.ZERO) != 0) {
                    fChangeDue = BigDecimal.ZERO;
                    fPaymentTypeTable.requestFocus();
                    fPaymentTypeTable.getSelectionModel().selectFirst();
                    fPaymentType = fPaymentTypeTable.getSelectionModel().getSelectedItem();
                    fPaymentType.setAmount(fBalance);
                    fPaymentTypeTable.refresh();
                    fPaymentTypeTable.getFocusModel().focus(fPaymentTypeTable.getSelectionModel().getSelectedIndex(), fPaymentTypeTable.getColumns().get(1));
                }
                break;

            case APPLY_CREDIT:
                CustomerCreditWidget creditListUI = new CustomerCreditWidget(fCreditAvailableList);
                fInputDialog = createSelectCancelUIDialog(creditListUI.getView(), "Credit List");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (creditListUI.getSelectedItems().size() >= 1) {
                        fCreditAppliedList = creditListUI.getSelectedItems();
                        fCreditAppliedAmount = fCreditAppliedList.stream().map(e -> e.getBalanceAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        if (fCreditAppliedAmount.compareTo(BigDecimal.ZERO) > 0) {
                            customerCreditProp.set(fCreditAvailableAmount.subtract(fCreditAppliedAmount));
                            visible = false;
                            visibleProp.set(visible);
                            fTableView.getItems().clear();
                            fBalance = fTotal.subtract(fCreditAppliedAmount);
                            creditProp.set(fCreditAppliedAmount);
                            balanceDueProp.set(fBalance);
                            if (fTotal.compareTo(BigDecimal.ZERO) >= 0) {
                                if (fBalance.compareTo(BigDecimal.ZERO) > 0) {
                                    fChangeDue = BigDecimal.ZERO;
                                    fPaymentTypeTable.requestFocus();
                                    fPaymentTypeTable.getSelectionModel().selectFirst();
                                    fPaymentType = fPaymentTypeTable.getSelectionModel().getSelectedItem();
                                    fPaymentType.setAmount(fBalance);
                                    fPaymentTypeTable.refresh();
                                    fPaymentTypeTable.getFocusModel().focus(fPaymentTypeTable.getSelectionModel().getSelectedIndex(), fPaymentTypeTable.getColumns().get(1));
                                } else {
                                    fChangeDue = fBalance.negate();
                                    handleAction(AppConstants.ACTION_PROCESS);
                                }
                            } else {
                                if (fBalance.compareTo(BigDecimal.ZERO) == 0) {
                                    fChangeDue = BigDecimal.ZERO;
                                    handleAction(AppConstants.ACTION_PROCESS);
                                } else {
                                    fChangeDue = BigDecimal.ZERO;
                                    fPaymentTypeTable.requestFocus();
                                    fPaymentTypeTable.getSelectionModel().selectFirst();
                                    fPaymentType = fPaymentTypeTable.getSelectionModel().getSelectedItem();
                                    fPaymentType.setAmount(fBalance);
                                    fPaymentTypeTable.refresh();
                                    fPaymentTypeTable.getFocusModel().focus(fPaymentTypeTable.getSelectionModel().getSelectedIndex(), fPaymentTypeTable.getColumns().get(1));
                                }
                            }
                        }
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PROCESS:
                if (fSalesOrder != null) {
                    if (fCreditAppliedAmount.compareTo(BigDecimal.ZERO) > 0) {
                        fSalesOrder.setCreditAmount(fCreditAppliedAmount);
                    } else {
                        fSalesOrder.setCreditAmount(BigDecimal.ZERO);
                    }
                }
                if (fChangeDue != null) {
                    changeProp.set(fChangeDue);
                    List<Payment> cashPaymentList = new ArrayList<>();
                    getPayments().forEach(e -> {
                        if (e.getPaymentType() != null && e.getPaymentType().getVerificationType() != null && e.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CASH)) {
                            cashPaymentList.add(e);
                        }
                    });
                    if (!cashPaymentList.isEmpty()) {
                        cashPaymentList.get(cashPaymentList.size() - 1).setChangeAmount(fChangeDue);
                    }
                } else {
                    changeProp.set(BigDecimal.ZERO);
                }
                showConfirmDialog(createChangeDuePane(), (ActionEvent e) -> {
                    Platform.runLater(() -> getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH));
                });
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.getStyleClass().add("editView");
        mainPane.setHgap(3.0);
        mainPane.setVgap(3.0);

        TableColumn<PaymentType, String> paymentTypeCol = new TableColumn<>("Payment Type");
        paymentTypeCol.setCellValueFactory((CellDataFeatures<PaymentType, String> p) -> {
            if (p.getValue().getCode() != null) {
                return new SimpleStringProperty(p.getValue().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        paymentTypeCol.setMinWidth(350);
        paymentTypeCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        paymentTypeCol.setEditable(false);
        paymentTypeCol.setSortable(false);

        TableColumn<PaymentType, BigDecimal> typeAmountCol = new TableColumn<>("Amount");
        typeAmountCol.setCellValueFactory(new PropertyValueFactory<>(PaymentType_.amount.getName()));
        typeAmountCol.setOnEditCommit((TableColumn.CellEditEvent<PaymentType, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                fPaymentType = (PaymentType) t.getTableView().getItems().get(t.getTablePosition().getRow());
                fPaymentType.setAmount(t.getNewValue());
                validateInput();
            }
        });
        typeAmountCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        typeAmountCol.setSortable(false);
        typeAmountCol.setMinWidth(100);

        fPaymentTypeTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends PaymentType> observable, PaymentType newValue, PaymentType oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fPaymentType = fPaymentTypeTable.getSelectionModel().getSelectedItem();
                fPaymentType.setAmount(fBalance);
                validateInput();
            }
        });
        fPaymentTypeTable.getColumns().add(paymentTypeCol);
        fPaymentTypeTable.getColumns().add(typeAmountCol);
        fPaymentTypeTable.setPrefHeight(170);

        TableColumn<Payment, String> typeCol = new TableColumn<>("Payment Type");
        typeCol.setCellValueFactory((CellDataFeatures<Payment, String> p) -> {
            if (p.getValue().getPaymentType() != null) {
                return new SimpleStringProperty(p.getValue().getPaymentType().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        }
        );
        typeCol.setMinWidth(220);
        typeCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        typeCol.setEditable(false);
        typeCol.setSortable(false);

        TableColumn<Payment, String> paymentAmountCol = new TableColumn<>("Amount");
        paymentAmountCol.setCellValueFactory(new PropertyValueFactory<>(Payment_.tenderedAmount.getName()));
        paymentAmountCol.setCellFactory(stringCell(Pos.CENTER));
        paymentAmountCol.setMinWidth(90);
        paymentAmountCol.setSortable(false);

        TableColumn<Payment, Boolean> deleteBtnCol = new TableColumn<>("Action");
        deleteBtnCol.setCellValueFactory((TableColumn.CellDataFeatures<Payment, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));
        deleteBtnCol.setCellFactory((TableColumn<Payment, Boolean> p) -> new ButtonCell());
        deleteBtnCol.setMinWidth(140);
        deleteBtnCol.setEditable(false);
        deleteBtnCol.setSortable(false);

        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(paymentAmountCol);
        fTableView.getColumns().add(deleteBtnCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(120);
        fTableView.setEditable(false);

        GridPane paymentPane = new GridPane();
        paymentPane.setVgap(1.0);
        paymentPane.setAlignment(Pos.TOP_RIGHT);

        Label depositLBL = createLabel("Deposit: ", 150.0, Pos.CENTER_RIGHT);
        Label totalLBL = createLabel("Amount: ", 150.0, Pos.CENTER_RIGHT);
        Label creditLBL = createLabel("Credit: ", 150.0, Pos.CENTER_RIGHT);
        Label totalDueLBL = createLabel("Total Due: ", 150.0, Pos.CENTER_RIGHT);
        Label balanceLBL = createLabel("Balance: ", 150.0, Pos.CENTER_RIGHT);
        Label paidLBL = createLabel("Paid: ", 150.0, Pos.CENTER_RIGHT);
        Label customerCreditLabel = createLabel("Available Credit: ", Pos.CENTER_LEFT);

        TextField totalField = createLabelField(110.0, Pos.CENTER_RIGHT);
        totalField.textProperty().bindBidirectional(totalProp, getDecimalFormat());
        TextField depositField = createLabelField(110.0, Pos.CENTER_RIGHT);
        depositField.textProperty().bindBidirectional(depositProp, getDecimalFormat());
        TextField creditField = createLabelField(110.0, Pos.CENTER_RIGHT);
        creditField.textProperty().bindBidirectional(creditProp, getDecimalFormat());
        TextField totalDueField = createLabelField(110.0, Pos.CENTER_RIGHT);
        totalDueField.textProperty().bindBidirectional(totalDueProp, getDecimalFormat());
        TextField paidField = createLabelField(110.0, Pos.CENTER_RIGHT);
        paidField.textProperty().bindBidirectional(paidProp, getDecimalFormat());
        TextField balanceDueField = createLabelField(110.0, Pos.CENTER_RIGHT);
        balanceDueField.textProperty().bindBidirectional(balanceDueProp, getDecimalFormat());
        TextField customerCreditField = createLabelField(110.0, Pos.CENTER_LEFT);
        customerCreditField.textProperty().bindBidirectional(customerCreditProp, getDecimalFormat());

        customerCreditLabel.visibleProperty().bindBidirectional(visibleProp);
        customerCreditField.visibleProperty().bindBidirectional(visibleProp);

        paymentPane.add(totalLBL, 0, 0);
        paymentPane.add(totalField, 1, 0);
        paymentPane.add(depositLBL, 0, 1);
        paymentPane.add(depositField, 1, 1);
        paymentPane.add(creditLBL, 0, 2);
        paymentPane.add(creditField, 1, 2);
        paymentPane.add(totalDueLBL, 0, 3);
        paymentPane.add(totalDueField, 1, 3);
        paymentPane.add(paidLBL, 0, 4);
        paymentPane.add(paidField, 1, 4);
        paymentPane.add(balanceLBL, 0, 5);
        paymentPane.add(balanceDueField, 1, 5);

        totalLBL.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        totalField.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        totalDueLBL.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        totalDueField.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        balanceLBL.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
        balanceDueField.setFont(Font.font("Verdana", FontWeight.BOLD, 16));

        GridPane.setHalignment(fTableView, HPos.LEFT);

        HBox buttonBox = new HBox(4);
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        applyCreditButton.visibleProperty().bindBidirectional(visibleProp);
        applyCreditButton.setFocusTraversable(false);
        buttonBox.getChildren().addAll(applyCreditButton, filler, applyButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        HBox creditBox = new HBox();
        creditBox.getChildren().addAll(customerCreditLabel, customerCreditField);
        creditBox.setAlignment(Pos.CENTER_LEFT);

        mainPane.add(paymentPane, 0, 1);
        mainPane.add(fPaymentTypeTable, 0, 2);
        mainPane.add(fTableView, 0, 3);
        mainPane.add(buttonBox, 0, 4);
        mainPane.add(creditBox, 0, 5);

        return mainPane;
    }

    private GridPane createCheckVerificationPane() {
        GridPane checkPane = new GridPane();
        checkPane.getStyleClass().add("editView");
        add(checkPane, "Check Number: ", dataUI.createTextField(Payment_.checkNumber), fListener, 0);
        add(checkPane, "Driver's License: ", dataUI.createTextField(Payment_.license), fListener, 1);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(checkVerifySaveBtn, checkVerifyCancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        checkVerifySaveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                checkVerifySaveBtn.fire();
                ev.consume();
            }
        });
        checkVerifyCancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                checkVerifyCancelBtn.fire();
                ev.consume();
            }
        });
        GridPane pane = new GridPane();
        pane.add(checkPane, 0, 0);
        pane.add(buttons, 0, 1);

        return pane;
    }

    private GridPane createCreditCardVerificationPane() {
        GridPane creditCardPane = new GridPane();
        creditCardPane.getStyleClass().add("editView");
        add(creditCardPane, "Approve Code", dataUI.createTextField(Payment_.creditCardApprovalCode), fListener, 0);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(creditCardVerifySaveBtn, creditCardVerifyCancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        creditCardVerifySaveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                creditCardVerifySaveBtn.fire();
                ev.consume();
            }
        });
        creditCardVerifyCancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                creditCardVerifyCancelBtn.fire();
                ev.consume();
            }
        });
        GridPane pane = new GridPane();
        pane.add(creditCardPane, 0, 0);
        pane.add(buttons, 0, 1);

        return pane;
    }

    private GridPane createGiftCardVerificationPane() {
        GridPane giftCardPane = new GridPane();
        giftCardPane.getStyleClass().add("editView");
        add(giftCardPane, "Approve Code", dataUI.createTextField(Payment_.creditCardApprovalCode), fListener, 0);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(giftCardVerifySaveBtn, giftCardVerifyCancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        giftCardVerifySaveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                giftCardVerifySaveBtn.fire();
                ev.consume();
            }
        });
        giftCardVerifyCancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                giftCardVerifyCancelBtn.fire();
                ev.consume();
            }
        });
        GridPane pane = new GridPane();
        pane.add(giftCardPane, 0, 0);
        pane.add(buttons, 0, 1);

        return pane;
    }

    private GridPane createGiftCertificateVerificationPane() {
        GridPane giftCertificatePane = new GridPane();
        giftCertificatePane.getStyleClass().add("editView");
        add(giftCertificatePane, "Gift Certificate Number: ", dataUI.createTextField(Payment_.giftCertificateNumber), fListener, 0);
        add(giftCertificatePane, "Driver's License: ", dataUI.createTextField(Payment_.license), fListener, 1);
        HBox buttons = new HBox();
        buttons.getChildren().addAll(giftCertificateVerifySaveBtn, giftCertificateVerifyCancelBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        giftCertificateVerifySaveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                giftCertificateVerifySaveBtn.fire();
                ev.consume();
            }
        });
        giftCertificateVerifyCancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                giftCertificateVerifyCancelBtn.fire();
                ev.consume();
            }
        });
        GridPane pane = new GridPane();
        pane.add(giftCertificatePane, 0, 0);
        pane.add(buttons, 0, 1);

        return pane;
    }

    private Node createChangeDuePane() {
        GridPane changeDuePane = new GridPane();
        Label changeDueLabel = createLabel("Change Due: ", 110.0, Pos.CENTER_RIGHT);
        TextField changeDueField = createLabelField(270.0, Pos.CENTER_RIGHT);
        changeDueField.textProperty().bindBidirectional(changeProp, getDecimalFormat());
        changeDueLabel.getStyleClass().add("payment-label");
        changeDueField.getStyleClass().add("payment-balance-field");
        changeDuePane.add(changeDueLabel, 0, 0);
        changeDuePane.add(changeDueField, 1, 0);

        return changeDuePane;
    }

    private void validateInput() {
        if (fPaymentType != null && fPaymentType.getAmount() != null && fPaymentType.getAmount().compareTo(BigDecimal.ZERO) != 0) {
            if (fTotal.compareTo(BigDecimal.ZERO) >= 0) {
                if (fPaymentType.getAmount().compareTo(fBalance) > 0) {
                    if (fPaymentType.getPreventOverTendering() != null && fPaymentType.getPreventOverTendering()) {
                        fPaymentType.setAmount(BigDecimal.ZERO);
                    }
                }
            } else {
                if (fPaymentType.getAmount().compareTo(fBalance) != 0) {
                    fPaymentType.setAmount(fBalance);
                }
            }
        }
        if (fPaymentType.getAmount() != null) {
            fPaymentTypeTable.getItems().stream().filter(e -> (!e.getId().equals(fPaymentType.getId()))).forEachOrdered(e -> {
                e.setAmount(BigDecimal.ZERO);
            });
        }
        fPaymentTypeTable.refresh();
        fPaymentTypeTable.requestFocus();
        fPaymentTypeTable.getFocusModel().focus(fPaymentTypeTable.getSelectionModel().getSelectedIndex(), fPaymentTypeTable.getColumns().get(1));
    }

    public List<AccountReceivable> getCreditList() {
        return fCreditAppliedList;
    }

    private class ButtonCell extends TableCell<Payment, Boolean> {

        final Button cellButton = new Button("Remove This Payment");

        ButtonCell() {
            cellButton.setOnAction((ActionEvent t) -> {
                Payment payment = (Payment) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                fEntityList.remove(payment);
                fTableView.refresh();
                fPaymentTypeTable.requestFocus();
                fPaymentTypeTable.getSelectionModel().selectFirst();
                fPaymentType = fPaymentTypeTable.getSelectionModel().getSelectedItem();
                fPaymentType.setAmount(fBalance);
                validateInput();
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                setGraphic(cellButton);
            } else {
                setGraphic(null);
            }
        }
    }

    public List<Payment> getPayments() {
        List<Payment> list = fEntityList.stream().collect(Collectors.toList());
        return list;
    }

    private void addListener() {
        mainView.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.INSERT) {
                applyButton.fire();
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelButton.fire();
            } else if (t.isControlDown() && t.getCode() == KeyCode.A) {
                if (visible) {
                    applyCreditButton.fire();
                }
            }
        });
    }
}

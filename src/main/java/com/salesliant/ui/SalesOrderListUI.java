package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Payment;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SalesOrderEntry_;
import com.salesliant.entity.SalesOrder_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Service;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.ServiceEntry_;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.report.SalesOrderLayout;
import com.salesliant.report.VoidedSalesOrderLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.dateCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class SalesOrderListUI extends BaseListUI<SalesOrder> {

    protected BaseDao<SalesOrder> daoSalesOrder = new BaseDao<>(SalesOrder.class);
    private final BaseDao<Deposit> daoDeposit = new BaseDao<>(Deposit.class);
    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final BaseDao<Service> daoService = new BaseDao<>(Service.class);
    private final BaseDao<Payment> daoPayment = new BaseDao<>(Payment.class);
    private final BaseDao<VoidedTransaction> daoVoided = new BaseDao<>(VoidedTransaction.class);
    private final TableView<SalesOrderEntry> fDetailTable = new TableView<>();
    protected final TableView<ServiceEntry> fServiceEntryTable = new TableView<>();
    private ObservableList<SalesOrderEntry> fSalesOrderEntryList;
    private ObservableList<ServiceEntry> fServiceEntryList;
    private final ObjectProperty<BigDecimal> orderProfitProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderProfitPercentProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderCostProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final HBox profitBox = new HBox();
    protected List<SalesOrder> fList;
    private final TableColumn<SalesOrderEntry, String> taxableCol = new TableColumn<>("Taxable");
    private final TableColumn<SalesOrderEntry, BigDecimal> profitCol = new TableColumn<>("Profit %");
    private final CheckBox showProfitBox = new CheckBox("Show Profit");
    private Customer fCustomer;
    private VoidedTransaction fVoidedTransaction;
    private SalesOrderUI fSalesOrderUI;
    private PaymentUI fPaymentUI;
    protected int fOrderType;
    protected final Label fSalesOrderLbl = new Label("");
    private static final Logger LOGGER = Logger.getLogger(SalesOrderListUI.class.getName());

    public SalesOrderListUI() {
    }

    protected final void createGUI() {
        mainView = createMainView();
        fSalesOrderLbl.setText("List of " + getTitle());
        fTableView.setItems(fEntityList);
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SalesOrder> observable, SalesOrder newValue, SalesOrder oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                fCustomer = fEntity.getCustomer();
                List<SalesOrderEntry> aList = fTableView.getSelectionModel().getSelectedItem().getSalesOrderEntries().stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder())).collect(Collectors.toList());
                fSalesOrderEntryList = FXCollections.observableList(aList);
                fDetailTable.setItems(fSalesOrderEntryList);
                BigDecimal subTotal = fSalesOrderEntryList
                        .stream()
                        .filter(e -> !(e.getComponentFlag() != null && e.getComponentFlag()) && e.getQuantity() != null && e.getPrice() != null)
                        .map(e -> e.getQuantity().multiply(e.getPrice()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal costTotal = fSalesOrderEntryList
                        .stream()
                        .filter(e -> !(e.getComponentFlag() != null && e.getComponentFlag()) && e.getQuantity() != null && e.getCost() != null)
                        .map(e -> e.getQuantity().multiply(e.getCost()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal profitTotal = subTotal.subtract(costTotal);
                BigDecimal profitPercent;
                if (subTotal.compareTo(BigDecimal.ZERO) != 0) {
                    profitPercent = profitTotal.divide(subTotal, 4, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                } else {
                    profitPercent = BigDecimal.ZERO;
                }
                orderCostProperty.set(costTotal);
                orderProfitProperty.set(profitTotal);
                orderProfitPercentProperty.set(profitPercent);
                if (fEntity.getService() != null) {
                    List<ServiceEntry> serviceEntryList = fEntity.getService().getServiceEntries().stream().sorted((e1, e2) -> e1.getDateEntered().compareTo(e2.getDateEntered())).collect(Collectors.toList());
                    fServiceEntryList = FXCollections.observableList(serviceEntryList);
                    fServiceEntryTable.setItems(fServiceEntryList);
                } else {
                    fServiceEntryTable.getItems().clear();
                }
            } else {
                orderCostProperty.set(BigDecimal.ZERO);
                orderProfitProperty.set(BigDecimal.ZERO);
                orderProfitPercentProperty.set(BigDecimal.ZERO);
                fDetailTable.getItems().clear();
                fServiceEntryTable.getItems().clear();
                fCustomer = null;
            }
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (Config.checkTransactionRequireLogin()) {
                    LoginUI.login();
                }
                CustomerListUI customerListUI = new CustomerListUI();
                fInputDialog = createSelectCancelUIDialog(customerListUI.getView(), "Customers");
                customerListUI.actionButtonBox.getChildren().remove(customerListUI.closeBtn);
                customerListUI.actionButtonBox.getChildren().remove(customerListUI.choiceBox);
                selectBtn.setDisable(true);
                ((TableView<Customer>) customerListUI.getTableView()).getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Customer> observable, Customer newValue, Customer oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (customerListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fCustomer = (Customer) customerListUI.getTableView().getSelectionModel().getSelectedItem();
                        fEntity = new SalesOrder();
                        fEntity.setSales(Config.getEmployee());
                        fEntity.setDateOrdered(new Date());
                        if (fOrderType == DBConstants.TYPE_SALESORDER_INVOICE) {
                            fEntity.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_INVOICE_NUMBER));
                        } else if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
                            fEntity.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
                            if (Config.getStore().getOrderDueDays() != null) {
                                fEntity.setDateDue(addDay(fEntity.getDateOrdered(), Config.getStore().getOrderDueDays()));
                            }
                        } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
                            fEntity.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_QUOTE_NUMBER));
                            if (Config.getStore().getQuoteExpirationDays() != null) {
                                fEntity.setDateDue(addDay(fEntity.getDateOrdered(), Config.getStore().getQuoteExpirationDays()));
                            }
                        } else if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
                            fEntity.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_SERVICE_ORDER_NUMBER));
                            if (Config.getStore().getServiceOrderDueDays() != null) {
                                fEntity.setDateDue(addDay(fEntity.getDateOrdered(), Config.getStore().getServiceOrderDueDays()));
                            }
                        }
                        fEntity.setCustomer(fCustomer);
                        if (fCustomer.getTaxZone() != null) {
                            fEntity.setTaxZone(fCustomer.getTaxZone());
                        } else {
                            fEntity.setTaxZone(Config.getTaxZone());
                        }
                        if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
                            fEntity.setTaxExemptFlag(Boolean.TRUE);
                        } else {
                            fEntity.setTaxExemptFlag(Boolean.FALSE);
                        }
                        fEntity.setStation(Config.getStation());
                        fEntity.setStore(Config.getStore());
                        fEntity.setType(fOrderType);
                        handleAction(AppConstants.ACTION_APPLY);
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PROCESS:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fSalesOrderUI = new SalesOrderUI(fEntity);
                    fSalesOrderUI.setParent(this);
                    fInputDialog = createUIDialog(fSalesOrderUI.getView(), fSalesOrderUI.getTitle());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_VOID:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Response answer = createConfirmResponseDialog("Are you sure to void this transaction?");
                    if (answer.equals(Response.YES)) {
                        boolean notDeposit = true;
                        fVoidedTransaction = new VoidedTransaction();
                        fVoidedTransaction.setDateEntered(new Date());
                        fVoidedTransaction.setStore(fEntity.getStore());
                        fVoidedTransaction.setTransactionType(fEntity.getType());
                        fVoidedTransaction.setCustomer(fEntity.getCustomer());
                        if (fEntity != null && fEntity.getCustomer() != null && fEntity.getCustomer().getAccountNumber() != null) {
                            fVoidedTransaction.setCustomerAccountNumber(fEntity.getCustomer().getAccountNumber());
                        }
                        if (fEntity != null && fEntity.getCustomer() != null && fEntity.getCustomer().getCompany() != null) {
                            if (fEntity.getCustomer().getCompany() != null) {
                                fVoidedTransaction.setCustomerName(fEntity.getCustomer().getCompany());
                            } else {
                                fVoidedTransaction.setCustomerName((isEmpty(fEntity.getCustomer().getFirstName()) ? "" : fEntity.getCustomer().getFirstName())
                                        + (isEmpty(fEntity.getCustomer().getFirstName()) ? "" : " ")
                                        + (isEmpty(fEntity.getCustomer().getLastName()) ? "" : fEntity.getCustomer().getLastName()));
                            }
                        }
                        fVoidedTransaction.setTransactionNumber(getString(fEntity.getSalesOrderNumber()));
                        fVoidedTransaction.setTransactionAmount(fEntity.getTotal());
                        fVoidedTransaction.setEmployee(Config.getEmployee());
                        fVoidedTransaction.setEmployeeName(getString(Config.getEmployee().getFirstName()) + " " + getString(Config.getEmployee().getLastName()));
                        daoVoided.insert(fVoidedTransaction);
                        if (daoVoided.getErrorMessage() == null) {
                            if (!fEntity.getDeposits().isEmpty()) {
                                notDeposit = false;
                                fEntity.setType(DBConstants.TYPE_SALESORDER_VOID);
                                fPaymentUI = new PaymentUI(fEntity);
                                fPaymentUI.setParent(this);
                                fInputDialog = createUIDialog(fPaymentUI.getView(), "Payment");
                                fPaymentUI.cancelButton.setOnAction(e -> {
                                    fInputDialog.close();
                                });
                                Platform.runLater(() -> {
                                    fPaymentUI.fPaymentTypeTable.requestFocus();
                                    fPaymentUI.fPaymentTypeTable.getSelectionModel().select(0);
                                    if (fEntity.getTotal().compareTo(BigDecimal.ZERO) < 0) {
                                        fPaymentUI.fPaymentTypeTable.getSelectionModel().getSelectedItem().setAmount(fEntity.getTotal());
                                    }
                                    fPaymentUI.fPaymentTypeTable.getFocusModel().focus(0, fPaymentUI.fPaymentTypeTable.getColumns().get(1));
                                    fPaymentUI.fPaymentTypeTable.edit(0, fPaymentUI.fPaymentTypeTable.getColumns().get(1));
                                });
                                fInputDialog.showDialog();
                            }
                            if (notDeposit) {
                                Platform.runLater(() -> {
                                    VoidedSalesOrderLayout layout = new VoidedSalesOrderLayout(fEntity);
                                    try {
                                        JasperReportBuilder report = layout.build();
                                        report.print(true);
                                    } catch (DRException ex) {
                                        LOGGER.log(Level.SEVERE, null, ex);
                                    }
                                });
                                daoSalesOrder.delete(fEntity);
                                if (daoSalesOrder.getErrorMessage() == null) {
                                    if (fEntity.getService() != null && fEntity.getService().getId() != null) {
                                        daoService.delete(fEntity.getService());
                                    }
                                    fEntityList.remove(fEntity);
                                    if (fEntityList.isEmpty()) {
                                        fTableView.getSelectionModel().select(null);
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            case AppConstants.ACTION_PROCESS_FINISH:
                if (fInputDialog != null) {
                    fInputDialog.close();
                }
                Batch batch = Config.getBatch();
                if (fEntity.getType().equals(DBConstants.TYPE_SALESORDER_VOID)) {
                    if (!fPaymentUI.getPayments().isEmpty()) {
                        BigDecimal depositRedeemedAmount = fPaymentUI.getPayments().stream().map(e -> e.getTenderedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add).negate();
                        if (batch.getDepositRedeemed() != null && batch.getDepositRedeemed().compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal depositRedeemedTotalAmount = depositRedeemedAmount.add(batch.getDepositRedeemed());
                            batch.setDepositRedeemed(depositRedeemedTotalAmount);
                        } else {
                            batch.setDepositRedeemed(depositRedeemedAmount);
                        }
                        Platform.runLater(() -> {
                            VoidedSalesOrderLayout layout = new VoidedSalesOrderLayout(fEntity);
                            try {
                                JasperReportBuilder report = layout.build();
                                report.print(true);
                            } catch (DRException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        });
                        Deposit deposit = new Deposit();
                        deposit.setDateCreated(new Timestamp(new Date().getTime()));
                        deposit.setVoidedTransaction(fVoidedTransaction);
                        deposit.setAmount(depositRedeemedAmount.negate());
                        deposit.setCloseBatch(batch);
                        deposit.setStore(Config.getStore());
                        if (fEntity.getCustomer() != null) {
                            deposit.setCustomer(fEntity.getCustomer());
                        }
                        deposit.setDepositType(DBConstants.TYPE_DEPOSIT_CREDIT);
                        deposit.setOrderNumber(fEntity.getSalesOrderNumber().toString());
                        deposit.setStatus(DBConstants.STATUS_CLOSE);
                        daoDeposit.insert(deposit);
                        fPaymentUI.getPayments().forEach(e -> {
                            e.setBatch(batch);
                            e.setDeposit(deposit);
                            e.setStore(Config.getStore());
                            daoPayment.update(e);
                            deposit.getPayments().add(e);
                        });
                        if (!fEntity.getDeposits().isEmpty()) {
                            fEntity.getDeposits().forEach(e -> {
                                e.setVoidedTransaction(fVoidedTransaction);
                                e.setSalesOrder(null);
                                e.setStatus(DBConstants.STATUS_CLOSE);
                                e.setCloseBatch(batch);
                                daoDeposit.update(e);
                            });
                        }
                        fEntity.getDeposits().clear();
                        daoSalesOrder.delete(fEntity);
                        if (daoSalesOrder.getErrorMessage() == null) {
                            if (fEntity.getService() != null && fEntity.getService().getId() != null) {
                                daoService.delete(fEntity.getService());
                            }
                            fEntityList.remove(fEntity);
                            if (fEntityList.isEmpty()) {
                                fTableView.getSelectionModel().select(null);
                            }
                        }
                        batch.getDepositCloseBatchs().add(deposit);
                        daoBatch.update(batch);
                    }
                }
                break;

            case AppConstants.ACTION_APPLY:
                fSalesOrderUI = new SalesOrderUI(fEntity);
                fSalesOrderUI.setParent(this);
                fInputDialog = createUIDialog(fSalesOrderUI.getView(), fSalesOrderUI.getTitle());
                Platform.runLater(() -> {
                    fSalesOrderUI.getTableView().requestFocus();
                    fSalesOrderUI.getTableView().getSelectionModel().select(0);
                    fSalesOrderUI.getTableView().getFocusModel().focus(0, (TableColumn) fSalesOrderUI.getTableView().getColumns().get(1));
                    fSalesOrderUI.getTableView().edit(0, (TableColumn) fSalesOrderUI.getTableView().getColumns().get(0));
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_CLONE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    String type = "";
                    if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
                        type = "order";
                    } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
                        type = "quote";
                    }
                    showConfirmDialog("Do you want to create a new " + type + " base on this invoice?", (ActionEvent e) -> {
                        SalesOrder so = cloneSalesOrder(fEntity);
                        daoSalesOrder.insert(so);
                        if (daoSalesOrder.getErrorMessage() != null) {
                            showAlertDialog("Fail to copy the selected invoice");
                        } else {
                            refresh();
                        }
                    });
                }
                break;
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    SalesOrderLayout layout = new SalesOrderLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_EXPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    SalesOrderLayout layout = new SalesOrderLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_CLOSE_DIALOG:
                refresh();
                if (fInputDialog != null) {
                    fInputDialog.close();
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.add(fSalesOrderLbl, 0, 1);
        GridPane.setHalignment(fSalesOrderLbl, HPos.LEFT);

        TableColumn<SalesOrder, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrder, String> p) -> {
            if (p.getValue().getBuyer() != null) {
                return new SimpleStringProperty(getString(p.getValue().getBuyer().getFirstName()) + " " + getString(p.getValue().getBuyer().getLastName()));
            } else if (p.getValue().getCustomer() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCustomer().getFirstName()) + " " + getString(p.getValue().getCustomer().getLastName()));
            } else {
                return null;
            }
        });
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(200);

        TableColumn<SalesOrder, String> companyrCol = new TableColumn<>("Company");
        companyrCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrder, String> p) -> {
            if (p.getValue().getCustomer() != null && p.getValue().getCustomer().getCompany() != null) {
                return new SimpleStringProperty(p.getValue().getCustomer().getCompany());
            } else {
                return null;
            }
        });
        companyrCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyrCol.setPrefWidth(200);

        TableColumn<SalesOrder, String> orderNoCol = new TableColumn<>("Txn Number");
        orderNoCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrder_.salesOrderNumber.getName()));
        orderNoCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        orderNoCol.setPrefWidth(100);

        TableColumn<SalesOrder, String> dateCreatedCol = new TableColumn<>("Date Entered");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrder_.dateOrdered.getName()));
        dateCreatedCol.setCellFactory(dateCell(Pos.CENTER_RIGHT));
        dateCreatedCol.setPrefWidth(100);

        TableColumn<SalesOrder, String> depositCol = new TableColumn<>("Deposit");
        depositCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrder, String> p) -> {
            if (p.getValue().getDeposits() != null) {
                BigDecimal depositAmount = p.getValue().getDeposits().stream().map(e -> e.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(depositAmount));
            } else {
                return null;
            }
        });
        depositCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        depositCol.setPrefWidth(100);

        TableColumn<SalesOrder, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrder_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(100);

        TableColumn<SalesOrder, String> employeeCol = new TableColumn<>("Employee");
        employeeCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrder, String> p) -> {
            if (p.getValue().getSales() != null) {
                return new SimpleStringProperty(p.getValue().getSales().getFirstName() + " " + p.getValue().getSales().getLastName());
            } else {
                return null;
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(150);

        fTableView.getColumns().add(orderNoCol);
        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(companyrCol);
        fTableView.getColumns().add(employeeCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(depositCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(250);
//        fTableView.setPrefWidth(765);
        setTableWidth(fTableView);

        TableColumn<SalesOrderEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return null;
            }
        });
        skuCol.setMinWidth(130);
        skuCol.setCellFactory(stringCell(Pos.TOP_LEFT));

        TableColumn<SalesOrderEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null) {
                String description = getItemDescription(p.getValue().getItem());
                if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                    description = description + "\n" + p.getValue().getLineNote();
                }
                if (p.getValue().getSerialNumbers() != null & !p.getValue().getSerialNumbers().isEmpty()) {
                    description = description + "\n";
                    for (SerialNumber sn : p.getValue().getSerialNumbers()) {
                        description = description + getString(sn.getSerialNumber()) + "; ";
                    }
                }
                description = description.trim();
                if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                    description = description.substring(0, description.length() - 1);
                }
                return new SimpleStringProperty(description);
            } else {
                return null;
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(320);

        TableColumn<SalesOrderEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrderEntry_.quantity.getName()));
        detailQtyCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailQtyCol.setMinWidth(100);

        TableColumn<SalesOrderEntry, String> qtyCommittedCol = new TableColumn<>("Committed");
        qtyCommittedCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null) {
                if (p.getValue().getItem().getItemType() != null && p.getValue().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                    return null;
                } else {
                    if (getQuantityCommitted(p.getValue().getItem()).compareTo(BigDecimal.ZERO) > 0) {
                        return new SimpleStringProperty(getString(getQuantityCommitted(p.getValue().getItem())));
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        });

        qtyCommittedCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        qtyCommittedCol.setPrefWidth(100);
        qtyCommittedCol.setResizable(false);

        TableColumn<SalesOrderEntry, BigDecimal> detailPriceCol = new TableColumn<>("Price");
        detailPriceCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getPrice() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    return new ReadOnlyObjectWrapper(p.getValue().getPrice());
                }
            } else {
                return null;
            }
        });
        detailPriceCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailPriceCol.setMinWidth(100);

        taxableCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, String> p) -> {
            if (p.getValue() != null && p.getValue().getDiscountRate() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    return new ReadOnlyObjectWrapper(p.getValue().getTaxable());
                }
            } else {
                return null;
            }
        });
        taxableCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        taxableCol.setPrefWidth(100);
        taxableCol.setResizable(false);

        profitCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getItem() != null && p.getValue().getCost() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    BigDecimal price = zeroIfNull(p.getValue().getPrice()).multiply(zeroIfNull(p.getValue().getQuantity()));
                    BigDecimal cost = zeroIfNull(p.getValue().getCost()).multiply(zeroIfNull(p.getValue().getQuantity()));
                    BigDecimal profit = price.subtract(cost);
                    BigDecimal profitPercent;
                    if (price.compareTo(BigDecimal.ZERO) != 0) {
                        profitPercent = (profit.divide(price, 6, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
                        profitPercent = profitPercent.setScale(4, RoundingMode.HALF_UP);
                        return new ReadOnlyObjectWrapper(profitPercent);
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        });
        profitCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        profitCol.setVisible(false);
        profitCol.setSortable(false);
        profitCol.setResizable(false);
        profitCol.setPrefWidth(100);

        TableColumn<SalesOrderEntry, BigDecimal> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            BigDecimal total;

            if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
                    return new ReadOnlyObjectWrapper(total);
                }
            } else {
                return null;
            }
        });

        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        fDetailTable.getColumns().add(skuCol);
        fDetailTable.getColumns().add(detailDescriptionCol);
        fDetailTable.getColumns().add(detailQtyCol);
        fDetailTable.getColumns().add(qtyCommittedCol);
        fDetailTable.getColumns().add(detailPriceCol);
        fDetailTable.getColumns().add(taxableCol);
        fDetailTable.getColumns().add(profitCol);
        fDetailTable.getColumns().add(detailTotalCol);

        fDetailTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTable.setPrefHeight(200);

        TableColumn<ServiceEntry, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getDateEntered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getDateEntered()));
            } else {
                return null;
            }
        });
        dateCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        dateCol.setSortable(false);
        dateCol.setPrefWidth(150);

        TableColumn<ServiceEntry, String> techCol = new TableColumn<>("Tech");
        techCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getEmployee() != null) {
                return new SimpleStringProperty(p.getValue().getEmployee().getNameOnSalesOrder());
            } else {
                return null;
            }
        });
        techCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        techCol.setSortable(false);
        techCol.setPrefWidth(150);

        TableColumn<ServiceEntry, String> serviceCodeCol = new TableColumn<>("Service Code");
        serviceCodeCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getServiceCode() != null) {
                return new SimpleStringProperty(p.getValue().getServiceCode().getCode());
            } else {
                return null;
            }
        });
        serviceCodeCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        serviceCodeCol.setSortable(false);
        serviceCodeCol.setPrefWidth(160);

        TableColumn<ServiceEntry, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(ServiceEntry_.note.getName()));
        noteCol.setCellFactory(tc -> {
            TableCell<ServiceEntry, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setAlignment(Pos.TOP_LEFT);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(noteCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        noteCol.setPrefWidth(440);

        fServiceEntryTable.getColumns().add(dateCol);
        fServiceEntryTable.getColumns().add(techCol);
        fServiceEntryTable.getColumns().add(serviceCodeCol);
        fServiceEntryTable.getColumns().add(noteCol);
        fServiceEntryTable.setPrefHeight(220);
        fServiceEntryTable.setEditable(false);
        setTableWidth(fServiceEntryTable);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(fDetailTable, 0, 3);
        if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
            mainPane.add(fServiceEntryTable, 0, 4);
        }
        mainPane.add(createNewEditVoidDuplicatePrintExportCloseButtonPane(), 0, 5);
        mainPane.add(createProfitBox(), 0, 6);
        return mainPane;
    }

    private HBox createProfitBox() {
        TextField costTotalField = createLabelField(90.0, Pos.CENTER_LEFT);
        costTotalField.textProperty().bindBidirectional(orderCostProperty, getDecimalFormat());
        TextField profitTotalField = createLabelField(90.0, Pos.CENTER_LEFT);
        profitTotalField.textProperty().bindBidirectional(orderProfitProperty, getDecimalFormat());
        TextField profitPercentField = createLabelField(60.0, Pos.CENTER_LEFT);
        profitPercentField.textProperty().bindBidirectional(orderProfitPercentProperty, getDecimalFormat());

        Label costTotalLabel = new Label("Cost:");
        Label profitTotalLabel = new Label("Profit:");
        Label profitPercentLabel = new Label("Profit %:");
        profitBox.getChildren().addAll(costTotalLabel, costTotalField, profitTotalLabel, profitTotalField, profitPercentLabel, profitPercentField);
        profitBox.setAlignment(Pos.CENTER_LEFT);
        profitBox.setVisible(false);
        return profitBox;
    }

    private HBox createNewEditVoidDuplicatePrintExportCloseButtonPane() {
        HBox leftButtonBox = new HBox();
        leftButtonBox.getChildren().addAll(showProfitBox);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);
        showProfitBox.setSelected(false);
        showProfitBox.selectedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (newValue) {
                taxableCol.setVisible(false);
                profitCol.setVisible(true);
                profitBox.setVisible(true);
            } else {
                taxableCol.setVisible(true);
                profitCol.setVisible(false);
                profitBox.setVisible(false);
            }
        });
        HBox rightButtonBox = new HBox();
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button processButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, fHandler);
        Button duplicateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, fHandler);
        Button voidButton = ButtonFactory.getButton(ButtonFactory.BUTTON_VOID, AppConstants.ACTION_VOID, fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, fHandler);
        Button refreshButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH, AppConstants.ACTION_REFRESH, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        duplicateButton.setPrefWidth(87);
        rightButtonBox.getChildren().addAll(newButton, processButton, voidButton, duplicateButton, printButton, exportButton, refreshButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    private SalesOrder cloneSalesOrder(SalesOrder oldSalesOrder) {
        SalesOrder newSalesOrder = new SalesOrder();
        newSalesOrder.setDateOrdered(new Timestamp(new Date().getTime()));
        newSalesOrder.setSales(Config.getEmployee());
        newSalesOrder.setNote(oldSalesOrder.getNote());
        newSalesOrder.setTotal(oldSalesOrder.getTotal());
        newSalesOrder.setShippingCharge(oldSalesOrder.getShippingCharge());
        newSalesOrder.setStore(Config.getStore());
        newSalesOrder.setShipTo(oldSalesOrder.getShipTo());
        newSalesOrder.setTaxAmount(oldSalesOrder.getTaxAmount());
        newSalesOrder.setTaxExemptFlag(oldSalesOrder.getTaxExemptFlag());
        newSalesOrder.setTaxZone(oldSalesOrder.getTaxZone());
        newSalesOrder.setStation(Config.getStation());
        if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
            newSalesOrder.setType(DBConstants.TYPE_SALESORDER_ORDER);
            newSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
            newSalesOrder.setType(DBConstants.TYPE_SALESORDER_QUOTE);
            newSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_QUOTE_NUMBER));
        }
        newSalesOrder.setCustomer(oldSalesOrder.getCustomer());
        newSalesOrder.setBuyer(oldSalesOrder.getBuyer());
        List<SalesOrderEntry> list = new ArrayList<>();
        oldSalesOrder.getSalesOrderEntries().forEach(e -> {
            SalesOrderEntry soe = new SalesOrderEntry();
            if (e.getItem() != null && e.getItem().getActiveTag() != null && e.getItem().getActiveTag()) {
                soe.setDisplayOrder(e.getDisplayOrder());
                soe.setItem(e.getItem());
                soe.setLineNote(e.getLineNote());
                soe.setQuantity(e.getQuantity());
                soe.setSalesOrder(newSalesOrder);
                soe.setTaxable(e.getTaxable());
                soe.setComponentFlag(e.getComponentFlag());
                soe.setComponentQuantity(e.getComponentQuantity());
                soe.setCost(e.getItem().getCost());
                soe.setPrice(getItemPrice(soe.getItem(), newSalesOrder.getCustomer()));
                list.add(soe);
            }
        });
        list.stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder())).collect(Collectors.toList());
        newSalesOrder.setSalesOrderEntries(list);
        return newSalesOrder;
    }

    public String getTitle() {
        String title = "";
        if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
            title = "Quotes";
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
            title = "Orders";
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
            title = "Services";
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_INVOICE) {
            title = "Invoices";
        }
        return title;
    }
}

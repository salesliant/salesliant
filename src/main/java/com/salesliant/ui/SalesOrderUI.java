package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Appointment;
import com.salesliant.entity.Appointment_;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Cheque;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Payment;
import com.salesliant.entity.ReasonCode;
import com.salesliant.entity.ReasonCode_;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SalesOrderEntry_;
import com.salesliant.entity.SalesOrder_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.SerialNumber_;
import com.salesliant.entity.Service;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.ServiceEntry_;
import com.salesliant.entity.Tax;
import com.salesliant.entity.TransactionLog;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.report.InvoiceLayout;
import com.salesliant.report.SalesOrderLayout;
import com.salesliant.report.VoidedSalesOrderLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.buttonSetWidth;
import static com.salesliant.util.BaseUtil.decimalEditCell;
import static com.salesliant.util.BaseUtil.editableCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.IconFactory;
import com.salesliant.util.RES;
import com.salesliant.validator.InvalidException;
import com.salesliant.validator.SalesOrderValidator;
import com.salesliant.widget.CustomerBuyerWidget;
import com.salesliant.widget.CustomerShipToWidget;
import com.salesliant.widget.EmployeeWidget;
import com.salesliant.widget.InvoiceWidget;
import com.salesliant.widget.ItemTableWidget;
import com.salesliant.widget.ServiceCodeWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class SalesOrderUI extends BaseListUI<SalesOrderEntry> {

    private final BaseDao<SalesOrder> daoSalesOrder = new BaseDao<>(SalesOrder.class);
    private final BaseDao<Payment> daoPayment = new BaseDao<>(Payment.class);
    private final BaseDao<Deposit> daoDeposit = new BaseDao<>(Deposit.class);
    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final BaseDao<Cheque> daoCheque = new BaseDao<>(Cheque.class);
    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private final BaseDao<ReturnTransaction> daoReturnTransaction = new BaseDao<>(ReturnTransaction.class);
    private final BaseDao<Appointment> daoAppointment = new BaseDao<>(Appointment.class);
    private final BaseDao<TransactionLog> daoLog = new BaseDao<>(TransactionLog.class);
    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final BaseDao<Service> daoService = new BaseDao<>(Service.class);
    private final BaseDao<SerialNumber> daoSerialNumber = new BaseDao<>(SerialNumber.class);
    private final BaseDao<ReasonCode> daoReasonCode = new BaseDao<>(ReasonCode.class);
    private final BaseDao<VoidedTransaction> daoVoided = new BaseDao<>(VoidedTransaction.class);
    private final DataUI dataUI = new DataUI(SalesOrderEntry.class);
    private final DataUI salesOrderUI = new DataUI(SalesOrder.class);
    private final DataUI serialNumberUI = new DataUI(SerialNumber.class);
    private final DataUI serviceEntryUI = new DataUI(ServiceEntry.class);
    private final DataUI appointmentUI = new DataUI(Appointment.class);
    private final EmployeeWidget fEmployeeCombo = new EmployeeWidget();
    private final EmployeeWidget fSalesCombo = new EmployeeWidget();
    private ServiceCodeWidget fServiceCodeCombo;
    private GridPane fNoteEditPane;
    private GridPane fCostEditPane;
    private GridPane fServiceEntryEditPane;
    private CustomerShipTo fCustomerShipTo;
    protected SalesOrder fSalesOrder;
    protected Customer fCustomer;
    private Service fService;
    private VoidedTransaction fVoidedTransaction;
    private Appointment fAppointment;
    private ServiceEntry fServiceEntry;
    private final TextArea fBillToAddressTA = new TextArea(), fShipToAddressTA = new TextArea();
    private final ChoiceBox<String> choiceBox = new ChoiceBox<>();
    private PaymentUI fPaymentUI;
    private InvoiceWidget fInvoiceUI;
    private final SalesOrderValidator validator = new SalesOrderValidator();
    private final TableColumn<SalesOrderEntry, String> skuCol = new TableColumn<>("SKU");
    private final TableColumn<SalesOrderEntry, String> descriptionCol = new TableColumn<>("Description");
    private final TableColumn<SalesOrderEntry, BigDecimal> qtyCol = new TableColumn<>("Qty");
    private final TableColumn<SalesOrderEntry, BigDecimal> priceCol = new TableColumn<>("Price");
    private final TableColumn<SalesOrderEntry, BigDecimal> costCol = new TableColumn<>("Cost");
    private final TableColumn<SalesOrderEntry, BigDecimal> discountCol = new TableColumn<>("Discount %");
    private final TableColumn<SalesOrderEntry, BigDecimal> markupCol = new TableColumn<>("Markup %");
    private final ObjectProperty<BigDecimal> orderSubTotalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderShippingChargeProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderDepositProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderBalanceProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderProfitProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderProfitPercentProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> orderCostProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private GridPane servicePane = new GridPane();
    private GridPane fAppointmentEditPane = new GridPane();
    private final HBox profitBox = new HBox();
    public Button processBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, fHandler);
    private final static String LINE_NOTE_EDIT_PANE_TITLE = "Line Note";
    private final static String SERVICE_ENTRY_EDIT_PANE_TITLE = "Service Entry";
    private final static String CUSTOMER_SHIP_TO_SELECT = "Customer ShipTo Select";
    private final static String CUSTOMER_BUYER_SELECT = "Customer Buyer Select";
    private final static String CONVERT_TO_INVOICE = "Invoice";
    private final static String CONVERT_TO_ORDER = "Order";
    private final static String CONVERT_TO_QUOTE = "Quote";
    private final static String CONVERT_TO_SERVICE = "Service";
    private final static String NEW_SERVICE_ENTRY = "New Service Entry";
    private final static String EDIT_SERVICE_ENTRY = "Edit Service Entry";
    private final static String DELETE_SERVICE_ENTRY = "Delete Service Entry";
    private final static String SELECT_SERIAL_NUMBER = "Select Serial Number";
    private final static String UN_SELECT_SERIAL_NUMBER = "Un Select Serial Number";
    private final static String APPOINTMENT = "Appointment";
    private final static String EDIT_RETURN_LIST = "Edit Return List";
    private String fConvertCode = "";
    private final TableView<ServiceEntry> fServiceEntryTable = new TableView<>();
    private TableView<InvoiceEntry> fInvoiceEntryTable = new TableView<>();
    private ObservableList<ServiceEntry> fServiceEntryList;
    private List<InvoiceEntry> fList = new ArrayList<>();
    private ObservableList<String> fReasonCodeList;
    protected int fOrderType = DBConstants.TYPE_SALESORDER_INVOICE;
    protected SalesOrderUI fSalesOrderUI;
    private final Label dueDateLabel = new Label();
    private final DatePicker dateDue = new DatePicker();
    private Button selectBuyerBtn;
    private final static String DATE_DUE = "  Due Date:";
    private final static String DATE_EXPIRED = "  Expiration Date:";
    protected final static String SELECT_CUSTOMER = "Select_Customer";
    protected final static String CUSTOMER_SELECTED = "Customer_Selected";
    private final Label functionKeyLabel = new Label();
    private final String invoiceFunctionString = "INS^I:Add, DEL^D:delete, ^U:Move Up, ^D:Move Down, ^N:Line Note, ^C:Customer, ^V:Void, F1^L:List, F2:Cost, F3:SN, F4:UnSN, F10:Tender";
    private final String orderFunctionString = "INS^I:Add, DEL^D:delete, ^U:Move Up, ^D:Move Down, ^N:Line Note, ^C:Customer, ^V:Void, F1^L:List, F2:Cost, F3:SN, F4:UnSN, F9^S:Save";
    private boolean newSalesOrder = true;
    private static final Logger LOGGER = Logger.getLogger(SalesOrderUI.class.getName());

    public SalesOrderUI() {
    }

    public SalesOrderUI(SalesOrder salesOrder) {
        this.fSalesOrder = salesOrder;
        this.fCustomer = salesOrder.getCustomer();
        fSalesOrder.setStation(Config.getStation());
        fOrderType = fSalesOrder.getType();
        fCustomerShipTo = fSalesOrder.getShipTo();
        newSalesOrder = false;
        if (fSalesOrder.getDateOrdered() == null) {
            fSalesOrder.setDateOrdered(new Date());
            if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
                if (Config.getStore().getOrderDueDays() != null) {
                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getOrderDueDays()));
                }
            } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
                if (Config.getStore().getQuoteExpirationDays() != null) {
                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getQuoteExpirationDays()));
                }
            } else if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
                if (Config.getStore().getServiceOrderDueDays() != null) {
                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getServiceOrderDueDays()));
                }
            }
        }
        createGUI();
        if (fOrderType == DBConstants.TYPE_SALESORDER_INVOICE) {
            dueDateLabel.setVisible(false);
            functionKeyLabel.setText(invoiceFunctionString);
            dateDue.setVisible(false);
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
            dueDateLabel.setText(DATE_DUE);
            functionKeyLabel.setText(orderFunctionString);
            dueDateLabel.setVisible(true);
            dateDue.setVisible(true);
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
            dueDateLabel.setText(DATE_EXPIRED);
            functionKeyLabel.setText(orderFunctionString);
            dueDateLabel.setVisible(true);
            dateDue.setVisible(true);
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
            dueDateLabel.setText(DATE_DUE);
            functionKeyLabel.setText(orderFunctionString);
            dueDateLabel.setVisible(true);
            dateDue.setVisible(true);
        }
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case SELECT_CUSTOMER:
                CustomerListUI customerListUI = new CustomerListUI();
                customerListUI.getView().getStyleClass().add("editView");
                fInputDialog = createSelectCancelUIDialog(customerListUI.getView(), "Customers");
                selectBtn.setDisable(true);
                customerListUI.actionButtonBox.getChildren().remove(customerListUI.closeBtn);
                customerListUI.actionButtonBox.getChildren().remove(customerListUI.choiceBox);
                selectBtn.setDisable(true);
                ((TableView<Customer>) customerListUI.getTableView()).getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Customer> observable, Customer newValue, Customer oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                        selectBtn.requestFocus();
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (customerListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fCustomer = (Customer) customerListUI.getTableView().getSelectionModel().getSelectedItem();
                        if (fSalesOrder == null) {
                            fSalesOrder = new SalesOrder();
                            newSalesOrder = true;
                            fSalesOrder.setSales(Config.getEmployee());
                            fSalesOrder.setDateOrdered(new Date());
                            if (fOrderType == DBConstants.TYPE_SALESORDER_INVOICE) {
                                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_INVOICE_NUMBER));
                            } else if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
                                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
                                if (Config.getStore().getOrderDueDays() != null) {
                                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getOrderDueDays()));
                                }
                            } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
                                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_QUOTE_NUMBER));
                                if (Config.getStore().getQuoteExpirationDays() != null) {
                                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getQuoteExpirationDays()));
                                }
                            } else if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
                                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_SERVICE_ORDER_NUMBER));
                                if (Config.getStore().getServiceOrderDueDays() != null) {
                                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getServiceOrderDueDays()));
                                }
                            }
                            fSalesOrder.setCustomer(fCustomer);
                            if (fCustomer.getTaxZone() != null) {
                                fSalesOrder.setTaxZone(fCustomer.getTaxZone());
                            } else {
                                fSalesOrder.setTaxZone(Config.getTaxZone());
                            }
                            if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
                                fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
                            } else {
                                fSalesOrder.setTaxExemptFlag(Boolean.FALSE);
                            }
                            fSalesOrder.setStation(Config.getStation());
                            fSalesOrder.setStore(Config.getStore());
                            fSalesOrder.setType(fOrderType);
                        } else {
                            fSalesOrder.setCustomer(fCustomer);
                            fSalesOrder.setDateOrdered(new Date());
                            if (fCustomer.getTaxZone() != null) {
                                fSalesOrder.setTaxZone(fCustomer.getTaxZone());
                            } else {
                                fSalesOrder.setTaxZone(Config.getTaxZone());
                            }
                            if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
                                fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
                            } else {
                                fSalesOrder.setTaxExemptFlag(Boolean.FALSE);
                            }
                            fSalesOrder.setStation(Config.getStation());
                            fSalesOrder.setStore(Config.getStore());
                            fSalesOrder.setType(fOrderType);
                            fCustomerShipTo = null;
                            fSalesOrder.setBuyer(null);
                            fSalesOrder.setFob(null);
                            fSalesOrder.setCustomerPoNumber(null);
                            updateAddressPane();
                            try {
                                salesOrderUI.setData(fSalesOrder);
                                updateTotal();
                            } catch (Exception ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                        handleAction(CUSTOMER_SELECTED);
                    }
                });
                fInputDialog.show();
                break;
            case AppConstants.ACTION_CHANGE_CUSTOMER:
                showConfirmDialog("Do you want to change customer?", (ActionEvent e) -> {
                    handleAction(SELECT_CUSTOMER);
                });
                break;
            case CUSTOMER_SELECTED:
                if (fSalesOrderUI == null) {
                    fSalesOrderUI = new SalesOrderUI(fSalesOrder);
                    fSalesOrderUI.setParent(this);
                    fInputDialog = createUIDialog(fSalesOrderUI.getView(), fSalesOrderUI.getTitle());
                    Platform.runLater(() -> {
                        fSalesOrderUI.getTableView().requestFocus();
                        fSalesOrderUI.getTableView().getSelectionModel().select(0);
                        fSalesOrderUI.getTableView().getFocusModel().focus(0, (TableColumn) fSalesOrderUI.getTableView().getColumns().get(0));
                        fSalesOrderUI.getTableView().edit(0, (TableColumn) fSalesOrderUI.getTableView().getColumns().get(0));
                    });
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_ADD:
                SalesOrderEntry soe = fTableView.getItems().stream().filter(e -> e.getItem() == null).findFirst().orElse(null);
                if (soe == null) {
                    fEntity = new SalesOrderEntry();
                    fEntity.setSalesOrder(fSalesOrder);
                    fEntityList.add(fEntity);
                    fTableView.refresh();
                } else {
                    fEntity = soe;
                }
                Platform.runLater(() -> {
                    fTableView.getSelectionModel().select(fEntity);
                    fTableView.scrollTo(fEntity);
                    fTableView.layout();
                    fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), skuCol);
                    fTableView.edit(fTableView.getSelectionModel().getSelectedIndex(), skuCol);
                });
                break;
            case AppConstants.ACTION_LINE_NOTE:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentFlag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag()) {
                        showAlertDialog("You can't edit  note of a component!");
                        break;
                    }
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNoteEditPane, LINE_NOTE_EDIT_PANE_TITLE);
                    Platform.runLater(() -> dataUI.getTextArea(SalesOrderEntry_.lineNote).requestFocus());
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        if (isEmpty(fEntity.getLineNote())) {
                            fEntity.setLineNote(null);
                        }
                        fTableView.refresh();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    Platform.runLater(() -> dataUI.getTextArea(SalesOrderEntry_.lineNote).requestFocus());
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentFlag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag()) {
                        showAlertDialog("You can't edit  cost of a component!");
                        break;
                    }
                    if (fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                        showAlertDialog("You can't edit cost of an assembly item!");
                        break;
                    }
                    if (fTableView.getSelectionModel().getSelectedItem().getItem().getCategory() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getCategory().getCountTag() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getCategory().getCountTag()) {
                        showAlertDialog("You can't edit the cost of a this item!");
                        break;
                    }
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fCostEditPane, "Cost");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        updateTotal();
                        fTableView.refresh();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    Platform.runLater(() -> dataUI.getTextField(SalesOrderEntry_.cost).requestFocus());
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getItem() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag()) {
                        showAlertDialog("You can't delete a component of an assembly item!");
                        break;
                    }
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    if (fTableView.getSelectionModel().getSelectedItem().getItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType().equals(DBConstants.ITEM_TYPE_BOM)) {
                        deleteBom(i + 1);
                        fTableView.getItems().remove(i);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                        fTableView.refresh();
                    } else {
                        fTableView.getItems().remove(i);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                        fTableView.refresh();
                    }
                    if (i >= 1) {
                        fTableView.refresh();
                        fTableView.getSelectionModel().select(i - 1);
                        fTableView.getFocusModel().focus(i - 1, fTableView.getColumns().get(2));
                    }
                    updateTotal();
                }
                break;
            case AppConstants.ACTION_SAVE:
                if (!fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
                    update();
                    if (!fSalesOrder.getSalesOrderEntries().isEmpty()) {
                        if (!fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_QUOTE) && fSalesOrder.getTotal() != null && fSalesOrder.getTotal().compareTo(BigDecimal.ZERO) > 0 && fSalesOrder.getDeposits().isEmpty()) {
                            Response answer = createConfirmNoYesResponseDialog("Do you like to apply deposit?");
                            if (answer.equals(Response.YES)) {
                                handleAction(AppConstants.ACTION_TENDER);
                            } else {
                                saveSalesOrder();
                                if (daoSalesOrder.getErrorMessage() != null) {
                                    break;
                                }
                                printSalesOrder();
                            }
                        } else {
                            saveSalesOrder();
                            if (daoSalesOrder.getErrorMessage() != null) {
                                break;
                            }
                            printSalesOrder();
                        }
                    } else {
                        showAlertDialog("Can't save a empty " + getTitle().toLowerCase() + " !");
                    }
                }
                break;
            case CUSTOMER_BUYER_SELECT:
                CustomerBuyerWidget buyerUI = new CustomerBuyerWidget(fSalesOrder.getCustomer());
                TableView<CustomerBuyer> buyerTable = buyerUI.getTableView();
                fInputDialog = createSelectCancelResetUIDialog(buyerUI.getView(), "Buyer");
                selectBtn.setDisable(true);
                buyerTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CustomerBuyer> observable, CustomerBuyer newValue, CustomerBuyer oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (buyerTable.getSelectionModel().getSelectedItem() != null) {
                        CustomerBuyer customerBuyer = buyerTable.getSelectionModel().getSelectedItem();
                        fSalesOrder.setBuyer(customerBuyer);
                        updateAddressPane();
                    }
                });
                resetBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fSalesOrder.setBuyer(null);
                    updateAddressPane();
                });
                fInputDialog.show();
                break;
            case CUSTOMER_SHIP_TO_SELECT:
                CustomerShipToWidget shipToUI = new CustomerShipToWidget(fSalesOrder.getCustomer());
                TableView<CustomerShipTo> shipToTable = shipToUI.getTableView();
                fInputDialog = createSelectCancelResetUIDialog(shipToUI.getView(), "Ship To");
                selectBtn.setDisable(true);
                shipToTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends CustomerShipTo> observable, CustomerShipTo newValue, CustomerShipTo oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fCustomerShipTo = shipToTable.getSelectionModel().getSelectedItem();
                    if (fCustomerShipTo.getTaxZone() != null) {
                        fSalesOrder.setTaxZone(fCustomerShipTo.getTaxZone());
                    }
                    if (fCustomerShipTo.getTaxExempt() != null && fCustomerShipTo.getTaxExempt()) {
                        fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
                    } else {
                        if (fCustomerShipTo.getTaxZone().getId().equals(fCustomer.getTaxZone().getId())) {
                            if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
                                fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
                            } else {
                                fSalesOrder.setTaxExemptFlag(Boolean.FALSE);
                            }
                        }
                    }
                    updateAddressPane();
                });
                resetBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fCustomerShipTo = null;
                    fSalesOrder.setTaxZone(fCustomer.getTaxZone());
                    if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
                        fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
                    } else {
                        fSalesOrder.setTaxExemptFlag(Boolean.FALSE);
                    }
                    updateAddressPane();
                });
                fInputDialog.show();
                break;
            case AppConstants.ACTION_SELECT_LIST:
                ItemTableWidget itemTableWidget = new ItemTableWidget();
                fInputDialog = createSelectCancelUIDialog(itemTableWidget.getView(), "Item");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemTableWidget.getSelectedItems().size() >= 1) {
                        List<SalesOrderEntry> list = fTableView.getItems().stream().filter(p -> p.getItem() == null).collect(Collectors.toList());
                        if (!list.isEmpty()) {
                            fTableView.getItems().removeAll(list);
                        }
                        int i = fTableView.getItems().size();
                        itemTableWidget.getSelectedItems().forEach((item) -> {
                            SalesOrderEntry newSalesOrderEntry = new SalesOrderEntry();
                            newSalesOrderEntry.setItem(item);
                            newSalesOrderEntry.setQuantity(BigDecimal.ONE);
                            newSalesOrderEntry.setDiscountRate(BigDecimal.ZERO);
                            newSalesOrderEntry.setCost(item.getCost());
                            newSalesOrderEntry.setPrice(getItemPrice(item, fCustomer));
                            fTableView.getItems().add(newSalesOrderEntry);
                            if (item.getItemType() != null && item.getItemType() == DBConstants.ITEM_TYPE_BOM) {
                                addBom(fTableView.getItems().size() - 1, item);
                            }

                        });
                        updateTotal();
                        fTableView.refresh();
                        fTableView.getSelectionModel().select(i);
                        fTableView.scrollTo(i);
                        fTableView.getFocusModel().focus(i, qtyCol);
                        fTableView.edit(i, qtyCol);
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.show();
                break;
            case AppConstants.ACTION_PROCESS:
                if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
                    handleAction(AppConstants.ACTION_TENDER);
                }
                break;
            case AppConstants.ACTION_TENDER:
                update();
                try {
                    validator.validate(fSalesOrder);
                } catch (InvalidException ex) {
                    showAlertDialog(ex.getMessage());
                    break;
                }
                if (!fSalesOrder.getSalesOrderEntries().isEmpty() && fSalesOrder.getTotal() != null) {
                    if (fSalesOrder.getTotal().compareTo(BigDecimal.ZERO) == 0 && orderBalanceProperty.get().compareTo(BigDecimal.ZERO) == 0) {
                        handleAction(AppConstants.ACTION_PROCESS_FINISH);
                        break;
                    } else {
                        if (!fSalesOrder.getDeposits().isEmpty() && !fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_VOID)) {
                            BigDecimal depositPayment = fSalesOrder.getDeposits().stream().map(Deposit::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                            if (fSalesOrder.getTotal().compareTo(depositPayment) == 0) {
                                handleAction(AppConstants.ACTION_PROCESS_FINISH);
                                break;
                            }
                        }
                        fPaymentUI = new PaymentUI(fSalesOrder);
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
                        fInputDialog.show();
                    }
                } else {
                    showAlertDialog("Can't process a empty " + getTitle().toLowerCase() + "!");
                }
                break;

            case AppConstants.ACTION_VOID:
                update();
                Response answer = createConfirmResponseDialog("Are you sure to void this transaction?");
                if (answer.equals(Response.YES)) {
                    boolean notDeposit = true;
                    fVoidedTransaction = new VoidedTransaction();
                    fVoidedTransaction.setDateEntered(new Date());
                    fVoidedTransaction.setStore(Config.getStore());
                    fVoidedTransaction.setTransactionType(fSalesOrder.getType());
                    fVoidedTransaction.setCustomer(fSalesOrder.getCustomer());
                    if (fSalesOrder != null && fSalesOrder.getCustomer() != null && fSalesOrder.getCustomer().getAccountNumber() != null) {
                        fVoidedTransaction.setCustomerAccountNumber(fSalesOrder.getCustomer().getAccountNumber());
                    }
                    if (fSalesOrder != null && fSalesOrder.getCustomer() != null && fSalesOrder.getCustomer().getCompany() != null) {
                        if (fSalesOrder.getCustomer().getCompany() != null) {
                            fVoidedTransaction.setCustomerName(fSalesOrder.getCustomer().getCompany());
                        } else {
                            fVoidedTransaction.setCustomerName((isEmpty(fSalesOrder.getCustomer().getFirstName()) ? "" : fSalesOrder.getCustomer().getFirstName())
                                    + (isEmpty(fSalesOrder.getCustomer().getFirstName()) ? "" : " ")
                                    + (isEmpty(fSalesOrder.getCustomer().getLastName()) ? "" : fSalesOrder.getCustomer().getLastName()));
                        }
                    }
                    fVoidedTransaction.setTransactionNumber(getString(fSalesOrder.getSalesOrderNumber()));
                    fVoidedTransaction.setTransactionAmount(fSalesOrder.getTotal());
                    fVoidedTransaction.setEmployee(Config.getEmployee());
                    fVoidedTransaction.setEmployeeName(getString(Config.getEmployee().getFirstName()) + " " + getString(Config.getEmployee().getLastName()));
                    daoVoided.insert(fVoidedTransaction);
                    if (daoVoided.getErrorMessage() == null) {
                        if (!fSalesOrder.getDeposits().isEmpty()) {
                            notDeposit = false;
                            fSalesOrder.setType(DBConstants.TYPE_SALESORDER_VOID);
                            handleAction(AppConstants.ACTION_TENDER);
                        }
                    }
                    if (notDeposit) {
                        Platform.runLater(() -> getParent().handleAction(AppConstants.ACTION_CLOSE_DIALOG));
                        Platform.runLater(() -> {
                            VoidedSalesOrderLayout layout = new VoidedSalesOrderLayout(fSalesOrder);
                            try {
                                JasperReportBuilder report = layout.build();
                                report.print(true);
                            } catch (DRException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        });
                        if (fSalesOrder.getId() != null) {
                            daoSalesOrder.delete(fSalesOrder);
                        }
                    }
                }
                break;
            case AppConstants.ACTION_ASSIGN_SERIAL_NUMBER:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().isEmpty()) {
                        SalesOrderEntry salesOrderEntry = fTableView.getSelectionModel().getSelectedItem();
                        SerialNumberListUI serialNumberListUI = new SerialNumberListUI(salesOrderEntry);
                        serialNumberListUI.setParent(this);
                        fInputDialog = createUIDialog(serialNumberListUI.getView(), "Serial Number");
                        serialNumberListUI.selectButton.setOnAction(e -> {
                            List<SerialNumber> list = new ArrayList<>(serialNumberListUI.getSelectedItems());
                            fInputDialog.close();
                            salesOrderEntry.setSerialNumbers(list);
                            fTableView.refresh();
                        });
                        serialNumberListUI.closeButton.setOnAction(e -> {
                            fInputDialog.close();
                        });
                        fInputDialog.show();
                    } else {
                        showAlertDialog("Entry already has serial number, can't modify it!");
                    }
                }
                break;
            case AppConstants.ACTION_UNASSIGN_SERIAL_NUMBER:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (!fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().isEmpty()) {
                        showConfirmDialog("Are you sure to remove serial number of the seleted entry?", (ActionEvent event) -> {
                            fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().forEach(e -> {
                                e.setSalesOrderEntry(null);
                                if (fTableView.getSelectionModel().getSelectedItem().getId() != null) {
                                    daoSerialNumber.update(e);
                                }
                            });
                            fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().clear();
                            fTableView.refresh();
                        });
                    }
                }
                break;
            case AppConstants.ACTION_SHOW_COST:
                if (costCol.isVisible()) {
                    costCol.setVisible(false);
                    markupCol.setVisible(false);
                    profitBox.setVisible(false);
                    discountCol.setVisible(true);
                    descriptionCol.setPrefWidth(375);
                } else {
                    costCol.setVisible(true);
                    markupCol.setVisible(true);
                    discountCol.setVisible(false);
                    profitBox.setVisible(true);
                    descriptionCol.setPrefWidth(285);
                }
                break;
            case AppConstants.ACTION_EDIT_BOM:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null
                        && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                    SalesOrderEntry salesOrderEntry = fTableView.getSelectionModel().getSelectedItem();
                    Item item = salesOrderEntry.getItem();
                    item.setPrice1(salesOrderEntry.getPrice());
                    item.setCost(salesOrderEntry.getCost());
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    ItemBomUI itemBomUI = new ItemBomUI(item);
                    fInputDialog = createUIDialog(itemBomUI.getView(), "Component List");
                    itemBomUI.saveButton.setOnAction(e -> {
                        itemBomUI.update();
                        deleteBom(i + 1);
                        addBom(i, item);
                        salesOrderEntry.setPrice(item.getPrice1());
                        salesOrderEntry.setCost(item.getCost());
                        updateTotal();
                        fTableView.refresh();
                        fInputDialog.close();
                    });
                    itemBomUI.cancelButton.setOnAction(e -> {
                        fInputDialog.close();
                    });
                    Platform.runLater(() -> {
                        itemBomUI.getTableView().requestFocus();
                        itemBomUI.getTableView().getSelectionModel().select(0);
                        itemBomUI.getTableView().getFocusModel().focus(0, (TableColumn) itemBomUI.getTableView().getColumns().get(2));
                    });
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_UP:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentFlag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag()) {
                        showAlertDialog("You can't move a component of an assembly item!");
                        break;
                    }
                    if (fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                        showAlertDialog("You can't move an assembly item!");
                        break;
                    }
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1) {
                        if (fTableView.getItems().get(i - 1).getComponentFlag() != null && fTableView.getItems().get(i - 1).getComponentFlag()) {
                            int startIndex = i;
                            int j = startIndex;
                            while (j > 0) {
                                if (fTableView.getItems().get(j - 1).getComponentFlag() != null && fTableView.getItems().get(j - 1).getComponentFlag()) {
                                    j--;
                                } else {
                                    break;
                                }
                            }
                            if (j < startIndex) {
                                SalesOrderEntry ie = fTableView.getItems().get(i);
                                fTableView.getItems().remove(i);
                                fTableView.getItems().add(j - 1, ie);
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(j - 1);
                            }
                            break;
                        }
                        Collections.swap(fTableView.getItems(), i, i - 1);
                        fTableView.scrollTo(i - 1);
                        fTableView.getSelectionModel().select(i - 1);
                        fTableView.getFocusModel().focus(i - 1, qtyCol);
                    }
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_DOWN:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentFlag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag()) {
                        showAlertDialog("You can't move a component of an assembly item!");
                        break;
                    }
                    if (fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() != null && fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                        showAlertDialog("You can't move an assembly item!");
                        break;
                    }
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i < fTableView.getItems().size() - 1) {
                        if (fTableView.getItems().get(i + 1).getItem() != null && fTableView.getItems().get(i + 1).getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                            int startIndex = i + 1;
                            int j = startIndex;
                            while (j > 0 && (j + 1) <= (fTableView.getItems().size() - 1)) {
                                if (fTableView.getItems().get(j + 1).getComponentFlag() != null && fTableView.getItems().get(j + 1).getComponentFlag()) {
                                    j++;
                                } else {
                                    break;
                                }
                            }
                            if (j <= (fTableView.getItems().size() - 1)) {
                                SalesOrderEntry ie = fTableView.getItems().get(i);
                                fTableView.getItems().remove(i);
                                fTableView.getItems().add(j, ie);
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(j);
                            }
                            break;
                        }
                        Collections.swap(fTableView.getItems(), i, i + 1);
                        fTableView.scrollTo(i + 1);
                        fTableView.getSelectionModel().select(i + 1);
                        fTableView.getFocusModel().focus(i + 1, qtyCol);
                    }
                }
                break;
            case APPOINTMENT:
                if (fService == null) {
                    fService = new Service();
                    fService.setStore(Config.getStore());
                    fSalesOrder.setService(fService);
                }
                if (fService.getAppointment() != null) {
                    fAppointment = fService.getAppointment();
                } else {
                    fAppointment = new Appointment();
                    fAppointment.setCustomer(fSalesOrder.getCustomer());
                    fAppointment.setEmployee(fSalesOrder.getSales());
                    fAppointment.setDateCreated(new Date());
                    fAppointment.setStore(Config.getStore());
                    fService.setAppointment(fAppointment);
                }
                try {
                    appointmentUI.setData(fAppointment);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fAppointmentEditPane, APPOINTMENT);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        appointmentUI.getData(fAppointment);
                        if (fAppointment.getAppointmentDate() != null) {
                            if (fAppointment.getId() != null) {
                                daoAppointment.update(fAppointment);
                            } else {
                                daoAppointment.insert(fAppointment);
                            }
                        } else {
                            showAlertDialog("Not appoint time set");
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> appointmentUI.getUIComponent(Appointment_.appointmentDate).requestFocus());
                fInputDialog.show();
                break;
            case NEW_SERVICE_ENTRY:
                fServiceEntry = new ServiceEntry();
                fServiceEntry.setEmployee(Config.getEmployee());
                fServiceEntry.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                if (fService == null) {
                    fService = new Service();
                    fService.setStore(Config.getStore());
                    fSalesOrder.setService(fService);
                }
                fServiceEntry.setService(fService);
                try {
                    serviceEntryUI.setData(fServiceEntry);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fServiceEntryEditPane, SERVICE_ENTRY_EDIT_PANE_TITLE);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        serviceEntryUI.getData(fServiceEntry);
                        fServiceEntry.setDateEntered(new Timestamp(new Date().getTime()));
                        fServiceEntryList.add(fServiceEntry);
                        fServiceEntryTable.refresh();
                        fServiceEntryTable.scrollTo(fServiceEntry);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> serviceEntryUI.getUIComponent(ServiceEntry_.serviceCode).requestFocus());
                fInputDialog.show();
                break;
            case EDIT_SERVICE_ENTRY:
                if (fServiceEntryTable.getSelectionModel().getSelectedItem() != null) {
                    fServiceEntry = fServiceEntryTable.getSelectionModel().getSelectedItem();
                    try {
                        serviceEntryUI.setData(fServiceEntry);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fServiceEntryEditPane, SERVICE_ENTRY_EDIT_PANE_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            serviceEntryUI.getData(fServiceEntry);
                            fServiceEntry.setDateUpdated(new Timestamp(new Date().getTime()));
                            fServiceEntryTable.refresh();
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> serviceEntryUI.getUIComponent(ServiceEntry_.serviceCode).requestFocus());
                    fInputDialog.show();
                }
                break;
            case DELETE_SERVICE_ENTRY:
                if (fServiceEntryTable.getSelectionModel().getSelectedItem() != null) {
                    fServiceEntry = fServiceEntryTable.getSelectionModel().getSelectedItem();
                    fServiceEntryTable.getItems().remove(fServiceEntry);
                    if (fServiceEntryList.isEmpty()) {
                        fServiceEntryTable.getSelectionModel().select(null);
                    }
                }
                break;
            case AppConstants.ACTION_TABLE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.refresh();
                    fTableView.getSelectionModel().select(i);
                    if (fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                        fTableView.getFocusModel().focus(i, fTableView.getColumns().get(2));
                    } else {
                        fTableView.getFocusModel().focus(i, fTableView.getColumns().get(0));
                    }
                }
                break;
            case SELECT_SERIAL_NUMBER:
                SerialNumberListUI snListUI = new SerialNumberListUI();
                snListUI.selectButton.setVisible(true);
                TableView<SerialNumber> snTable = snListUI.getTableView();
                fInputDialog = createUIDialog(snListUI.getView(), "Serial Number List");
                snListUI.selectButton.setDisable(true);
                snListUI.toggleBox.setVisible(false);
                snListUI.soldBtn.fire();
                snTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SerialNumber> observable, SerialNumber newValue, SerialNumber oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        snListUI.selectButton.setDisable(false);
                    } else {
                        snListUI.selectButton.setDisable(true);
                    }
                });
                snListUI.selectButton.addEventFilter(ActionEvent.ACTION, event -> {
                    SerialNumber serialNumber = snTable.getSelectionModel().getSelectedItem();
                    if (fService == null) {
                        fService = new Service();
                        fService.setStore(Config.getStore());
                    }
                    try {
                        serialNumberUI.setData(serialNumber);
                        fService.setSerialNumber(serialNumber);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog.close();
                });
                snListUI.closeButton.addEventFilter(ActionEvent.ACTION, event -> {
                    fInputDialog.close();
                });
                fInputDialog.show();
                break;
            case UN_SELECT_SERIAL_NUMBER:
                if (fService != null && fService.getSerialNumber() != null) {
                    fService.setSerialNumber(null);
                    serialNumberUI.getTextField(SerialNumber_.invoiceNumber).setText("");
                    serialNumberUI.getTextField(SerialNumber_.serialNumber).setText("");
                    serialNumberUI.getTextField(SerialNumber_.warrantyExpireLabor).setText("");
                    serialNumberUI.getTextField(SerialNumber_.warrantyExpirePart).setText("");
                }
                break;
            case CONVERT_TO_ORDER:
                fConvertCode = CONVERT_TO_ORDER;
                convertOrder();
                break;
            case CONVERT_TO_SERVICE:
                fConvertCode = CONVERT_TO_SERVICE;
                convertOrder();
                break;
            case CONVERT_TO_QUOTE:
                fConvertCode = CONVERT_TO_QUOTE;
                if (fSalesOrder.getDeposits().isEmpty()) {
                    convertOrder();
                } else {
                    showAlertDialog("Can't convert a transaction with deposit to a quote!");
                }
                break;
            case CONVERT_TO_INVOICE:
                fConvertCode = CONVERT_TO_INVOICE;
                convertOrder();
                break;
            case AppConstants.ACTION_PROCESS_FINISH:
                if (fInputDialog != null) {
                    fInputDialog.close();
                }
                Batch batch = Config.getBatch();
                if (fSalesOrder.getType() != null && fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
                    Invoice invoice = convertToInvoice(fSalesOrder);
                    invoice.setBatch(batch);
                    Invoice iv = daoInvoice.insert(invoice);
                    if (daoInvoice.getErrorMessage() == null) {
                        iv.getInvoiceEntries().forEach(ie -> {
                            ie.getSerialNumbers().forEach(sn -> {
                                sn.setInvoiceEntry(ie);
                                sn.setSold(Boolean.TRUE);
                                sn.setDateSold(new Date());
                                sn.setInvoiceNumber(iv.getInvoiceNumber());
                                daoSerialNumber.update(sn);
                            });
                        });
                        if (!iv.getPayments().isEmpty()) {
                            iv.getPayments().forEach(e -> {
                                e.setInvoice(iv);
                                e.setBatch(batch);
                                batch.getPayments().add(e);
                                daoPayment.update(e);
                            });
                        }
                        if (!fSalesOrder.getDeposits().isEmpty()) {
                            fSalesOrder.getDeposits().forEach(e -> {
                                e.setInvoice(iv);
                                e.setInvoiceNumber(getString(iv.getInvoiceNumber()));
                                e.setSalesOrder(null);
                                e.setStatus(DBConstants.STATUS_CLOSE);
                                e.setCloseBatch(batch);
                                daoDeposit.update(e);
                                iv.getDeposits().add(e);
                            });
                        }
                        if (fSalesOrder.getId() != null) {
                            daoSalesOrder.delete(fSalesOrder);
                        }
                        if (fPaymentUI != null && fPaymentUI.getCreditList() != null && !fPaymentUI.getCreditList().isEmpty()) {
                            List<AccountReceivable> arList = fPaymentUI.getCreditList();
                            BigDecimal refundAmount = arList.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add).negate();
                            AccountReceivable refundAr = new AccountReceivable();
                            refundAr.setAccountReceivableNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                            refundAr.setStore(Config.getStore());
                            refundAr.setBatch(batch);
                            refundAr.setInvoice(iv);
                            refundAr.setInvoiceNumber(refundAr.getAccountReceivableNumber());
                            refundAr.setTransaction(refundAr.getAccountReceivableNumber().toString());
                            refundAr.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                            refundAr.setCustomer(arList.get(0).getCustomer());
                            refundAr.setTerms(arList.get(0).getTerms());
                            refundAr.setCollectable(Boolean.TRUE);
                            refundAr.setDateProcessed(new Date());
                            refundAr.setDateDue(arList.get(0).getDateDue());
                            refundAr.setDateInvoiced(new Date());
                            refundAr.setStatus(DBConstants.STATUS_CLOSE);
                            refundAr.setDiscountAmount(BigDecimal.ZERO);
                            refundAr.setPaidAmount(BigDecimal.ZERO);
                            refundAr.setBalanceAmount(BigDecimal.ZERO);
                            refundAr.setTotalAmount(refundAmount);
                            refundAr.setGlDebitAmount(refundAmount);
                            refundAr.setGlCreditAmount(BigDecimal.ZERO);
                            refundAr.setGlAccount(Integer.toString(1));
                            refundAr.setPostedTag(Boolean.FALSE);
                            refundAr.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND);
                            refundAr.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_REFUND);
                            daoAccountReceivable.insert(refundAr);
                            batch.getAccountReceivables().add(refundAr);
                            if (refundAmount.compareTo(BigDecimal.ZERO) > 0) {
                                if (batch.getCreditRedeemed() != null) {
                                    BigDecimal creditReemedToAccountAmount = batch.getCreditRedeemed();
                                    creditReemedToAccountAmount = creditReemedToAccountAmount.add(refundAmount);
                                    batch.setCreditRedeemed(creditReemedToAccountAmount);
                                } else {
                                    batch.setCreditRedeemed(refundAmount);
                                }
                            }
                            arList.forEach(e -> {
                                e.setBalanceAmount(BigDecimal.ZERO);
                                e.setPaidAmount(e.getPaidAmount().negate());
                                e.setDateProcessed(new Date());
                                e.setGlDebitAmount(e.getPaidAmount());
                                e.setGlCreditAmount(BigDecimal.ZERO);
                                e.setGlAccount(Integer.toString(1));
                                e.setStatus(DBConstants.STATUS_CLOSE);
                                e.setAccountReceivablePayment(refundAr);
                                daoAccountReceivable.update(e);
                            });
                        }
                        batch.getInvoices().add(iv);
                        if (!iv.getPayments().isEmpty()) {
                            iv.getPayments().forEach(e -> {
                                if (e.getTenderedAmount() != null && e.getTenderedAmount().compareTo(BigDecimal.ZERO) > 0
                                        && e.getPaymentType().getIsNetTerm() != null && e.getPaymentType().getIsNetTerm()) {
                                    AccountReceivable art = new AccountReceivable();
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(new Date());
                                    c.add(Calendar.DATE, fCustomer.getCustomerTerm().getDueDays());
                                    Calendar d = Calendar.getInstance();
                                    d.setTime(new Date());
                                    d.add(Calendar.DATE, fCustomer.getCustomerTerm().getDiscountDays());
                                    art.setBatch(batch);
                                    art.setAccountReceivableNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                                    art.setInvoice(iv);
                                    art.setInvoiceNumber(iv.getInvoiceNumber());
                                    art.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                                    art.setCustomer(fCustomer);
                                    art.setTerms(fCustomer.getCustomerTerm().getCode());
                                    art.setStore(Config.getStore());
                                    art.setTransaction(art.getAccountReceivableNumber().toString());
                                    art.setDateProcessed(new Date());
                                    art.setDateDue(c.getTime());
                                    art.setDateInvoiced(new Date());
                                    art.setStatus(DBConstants.STATUS_OPEN);
                                    art.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE);
                                    art.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE + " Invoice " + getString(e.getInvoice().getInvoiceNumber()) + " -PO " + getString(e.getInvoice().getCustomerPoNumber()));
                                    art.setCollectable(Boolean.TRUE);
                                    art.setTotalAmount(e.getTenderedAmount());
                                    art.setBalanceAmount(e.getTenderedAmount());
                                    art.setPaidAmount(BigDecimal.ZERO);
                                    art.setGlDebitAmount(e.getTenderedAmount());
                                    art.setGlCreditAmount(BigDecimal.ZERO);
                                    art.setGlAccount(Integer.toString(0));
                                    art.setPostedTag(Boolean.FALSE);
                                    daoAccountReceivable.insert(art);
                                    batch.getAccountReceivables().add(art);
                                    e.setAccountReceivable(art);
                                    daoPayment.update(e);
                                    if (batch.getPaidOnAccount() != null) {
                                        BigDecimal paidOnAccountAmount = batch.getPaidOnAccount();
                                        paidOnAccountAmount = paidOnAccountAmount.add(e.getTenderedAmount());
                                        batch.setPaidOnAccount(paidOnAccountAmount);
                                    } else {
                                        batch.setPaidOnAccount(e.getTenderedAmount());
                                    }
                                } else if (e.getTenderedAmount() != null && e.getTenderedAmount().compareTo(BigDecimal.ZERO) < 0
                                        && e.getPaymentType().getIsNetTerm() != null && e.getPaymentType().getIsNetTerm()) {
                                    AccountReceivable art = new AccountReceivable();
                                    art.setBatch(batch);
                                    art.setAccountReceivableNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                                    art.setInvoice(iv);
                                    art.setInvoiceNumber(iv.getInvoiceNumber());
                                    art.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                                    art.setCustomer(fCustomer);
                                    art.setTerms(fCustomer.getCustomerTerm().getCode());
                                    art.setStore(Config.getStore());
                                    art.setTransaction(art.getAccountReceivableNumber().toString());
                                    art.setDateProcessed(new Date());
                                    art.setDateDue(new Date());
                                    art.setDateInvoiced(new Date());
                                    art.setStatus(DBConstants.STATUS_OPEN);
                                    art.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT);
                                    art.setNote(DBConstants.NOTE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT);
                                    art.setCollectable(Boolean.TRUE);
                                    art.setTotalAmount(e.getTenderedAmount().negate());
                                    art.setBalanceAmount(e.getTenderedAmount().negate());
                                    art.setPaidAmount(BigDecimal.ZERO);
                                    art.setGlDebitAmount(BigDecimal.ZERO);
                                    art.setGlCreditAmount(e.getTenderedAmount().negate());
                                    art.setGlAccount(Integer.toString(0));
                                    art.setPostedTag(Boolean.FALSE);
                                    daoAccountReceivable.insert(art);
                                    batch.getAccountReceivables().add(art);
                                    e.setAccountReceivable(art);
                                    if (batch.getCreditMade() != null) {
                                        BigDecimal creditMadeAmount = batch.getCreditMade();
                                        creditMadeAmount = creditMadeAmount.add(e.getTenderedAmount().negate());
                                        batch.setCreditMade(creditMadeAmount);
                                    } else {
                                        batch.setCreditMade(e.getTenderedAmount().negate());
                                    }
                                } else if (e.getTenderedAmount() != null && e.getTenderedAmount().compareTo(BigDecimal.ZERO) > 0
                                        && e.getPaymentType() != null && !e.getPaymentType().getIsNetTerm()
                                        && e.getPaymentType().getVerificationType().equals(DBConstants.TYPE_VERIFICATION_CHECK)) {
                                    Cheque check = new Cheque();
                                    check.setCustomer(fCustomer);
                                    check.setCheckNumber(e.getCheckNumber());
                                    check.setReferenceNumber(iv.getInvoiceNumber());
                                    check.setCustomerName(iv.getCustomerName());
                                    check.setCheckAmount(e.getTenderedAmount());
                                    check.setCheckDate(new Date());
                                    check.setStore(Config.getStore());
                                    check.setCheckType(DBConstants.TYPE_CHECK_INVOICE);
                                    check.setLicense(e.getLicense());
                                    daoCheque.insert(check);
                                }
                            });
                        }
                        if (iv.getTaxAmount() != null) {
                            if (batch.getTax() != null) {
                                BigDecimal tax = batch.getTax();
                                tax = tax.add(iv.getTaxAmount());
                                batch.setTax(tax);
                            } else {
                                batch.setTax(iv.getTaxAmount());
                            }
                        }
                        batch.setCustomerCount(batch.getCustomerCount() + 1);
                        if (iv.getCommissionAmount() != null) {
                            if (batch.getCommissionTotal() != null) {
                                BigDecimal commission = batch.getCommissionTotal();
                                commission = commission.add(iv.getCommissionAmount());
                                batch.setCommissionTotal(commission);
                            } else {
                                batch.setCommissionTotal(iv.getCommissionAmount());
                            }
                        }
                        BigDecimal discount = BigDecimal.ZERO;
                        BigDecimal returnAmount = BigDecimal.ZERO;
                        BigDecimal salesAmount = BigDecimal.ZERO;
                        for (InvoiceEntry ie : iv.getInvoiceEntries()) {
                            if (ie.getComponentFlag() == null || !ie.getComponentFlag()) {
                                if (ie.getQuantity() != null && ie.getPrice() != null) {
                                    if (ie.getQuantity().compareTo(BigDecimal.ZERO) >= 0) {
                                        salesAmount = salesAmount.add(ie.getPrice().multiply(ie.getQuantity()));
                                        if (ie.getDiscountAmount() != null) {
                                            discount = discount.add(ie.getDiscountAmount().multiply(ie.getQuantity()));
                                        }
                                    } else {
                                        returnAmount = returnAmount.add(ie.getPrice().multiply(ie.getQuantity()).negate());
                                    }
                                }
                            }
                            Item item = getItem(ie.getItemLookUpCode());
                            if (item != null && ie.getPrice() != null && ie.getQuantity() != null && item.getCategory() != null
                                    && item.getCategory().getCountTag() && item.getItemType() != DBConstants.ITEM_TYPE_BOM) {
                                if (ie.getQuantity().compareTo(BigDecimal.ZERO) >= 0) {
                                    BigDecimal qty = getQuantity(item).subtract(ie.getQuantity());
                                    ItemQuantity iq = getItemQuantity(item);
                                    BigDecimal beforeQty = iq.getQuantity();
                                    if (qty.compareTo(BigDecimal.ZERO) >= 0) {
                                        iq.setQuantity(qty);
                                    } else {
                                        iq.setQuantity(BigDecimal.ZERO);
                                    }
                                    iq.setLastSold(new Timestamp(new Date().getTime()));
                                    daoItemQuantity.update(iq);
                                    BigDecimal afterQty = iq.getQuantity();
                                    ItemLog il = new ItemLog();
                                    il.setItem(item);
                                    il.setDateCreated(new Timestamp(new Date().getTime()));
                                    il.setDescription(DBConstants.ITEM_LOG_DESCRIPTION_INVOICE);
                                    il.setInvoice(iv);
                                    il.setBeforeQuantity(beforeQty);
                                    il.setCost(ie.getCost());
                                    il.setItemCost(ie.getCost());
                                    il.setItemPrice(item.getPrice1());
                                    il.setPrice(ie.getPrice());
                                    il.setAfterQuantity(afterQty);
                                    il.setTransferType(DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_INVOICE);
                                    il.setEmployee(Config.getEmployee());
                                    il.setTransactionNumber(iv.getInvoiceNumber().toString());
                                    il.setStore(Config.getStore());
                                    daoItemLog.insert(il);
                                } else {
                                    ReturnTransaction rf = new ReturnTransaction();
                                    rf.setRmaNumberToCustomer(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                                    rf.setDateReturned(iv.getDateInvoiced());
                                    rf.setStore(Config.getStore());
                                    rf.setItem(item);
                                    rf.setQuantity(ie.getQuantity().negate());
                                    rf.setEmployeeReturned(Config.getEmployee());
                                    rf.setEmployeeNameReturned(iv.getSalesName());
                                    rf.setCost(ie.getCost());
                                    rf.setPrice(ie.getPrice());
                                    rf.setInvoiceEntry(ie);
                                    rf.setInvoiceNumber(iv.getInvoiceNumber().toString());
                                    rf.setItemDescription(ie.getItemDescription());
                                    rf.setItemLookUpCode(ie.getItemLookUpCode());
                                    rf.setOwnTag(Boolean.TRUE);
                                    rf.setReturnFromType(DBConstants.TYPE_RETURN_FROM_CUSTOMER);
                                    if (ie.getReturnCode() != null && !ie.getReturnCode().isEmpty()) {
                                        rf.setReasonCode(ie.getReturnCode());
                                    } else if (ie.getLineNote() != null && !ie.getLineNote().isEmpty()) {
                                        rf.setReasonCode(ie.getLineNote());
                                    }
                                    rf.setStatus(DBConstants.STATUS_OPEN);
                                    daoReturnTransaction.insert(rf);
                                }
                            }
                        }
                        if (batch.getCostOfGoods() != null) {
                            BigDecimal costOfGoods = batch.getCostOfGoods();
                            costOfGoods = costOfGoods.add(iv.getCost());
                            batch.setCostOfGoods(costOfGoods);
                        } else {
                            batch.setCostOfGoods(iv.getCost());
                        }
                        if (batch.getDiscountAmount() != null) {
                            BigDecimal discountAmount = batch.getDiscountAmount();
                            discountAmount = discountAmount.add(discount);
                            batch.setDiscountAmount(discountAmount);
                        } else {
                            batch.setDiscountAmount(discount);
                        }
                        if (batch.getSalesAmount() != null) {
                            BigDecimal sales = batch.getSalesAmount();
                            sales = sales.add(salesAmount);
                            batch.setSalesAmount(sales);
                        } else {
                            batch.setSalesAmount(salesAmount);
                        }
                        if (batch.getReturnAmount() != null) {
                            BigDecimal returns = batch.getReturnAmount();
                            returns = returns.add(returnAmount);
                            batch.setReturnAmount(returns);
                        } else {
                            batch.setReturnAmount(returnAmount);
                        }
                        if (!iv.getDeposits().isEmpty()) {
                            BigDecimal depositPayment = iv.getDeposits()
                                    .stream()
                                    .map(e -> e.getAmount())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            iv.setDepositAmount(depositPayment);
                            daoInvoice.update(iv);
                            if (iv.getDepositAmount() != null && iv.getDepositAmount().compareTo(BigDecimal.ZERO) > 0) {
                                if (batch.getDepositRedeemed() != null) {
                                    BigDecimal deposit = batch.getDepositRedeemed();
                                    deposit = deposit.add(iv.getDepositAmount());
                                    batch.setDepositRedeemed(deposit);
                                } else {
                                    batch.setDepositRedeemed(iv.getDepositAmount());
                                }
                            }
                        }
                        daoBatch.update(batch);
                        getParent().handleAction(AppConstants.ACTION_CLOSE_DIALOG);
                        Platform.runLater(() -> {
                            InvoiceLayout layout = new InvoiceLayout(iv);
                            try {
                                JasperReportBuilder report = layout.build();
                                if (Config.getStore().getInvoiceCount() != null) {
                                    for (int i = 0; i < Config.getStore().getInvoiceCount(); i++) {
                                        report.print(false);
                                    }
                                } else {
                                    report.print(true);
                                }
                            } catch (DRException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        });
                    } else {
                        showAlertDialog(daoInvoice.getErrorMessage());
                    }
                } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_VOID)) {
                    if (!fPaymentUI.getPayments().isEmpty()) {
                        BigDecimal depositRedeemedAmount = fPaymentUI.getPayments().stream().map(e -> e.getTenderedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add).negate();
                        if (batch.getDepositRedeemed() != null && batch.getDepositRedeemed().compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal depositRedeemedTotalAmount = depositRedeemedAmount.add(batch.getDepositRedeemed());
                            batch.setDepositRedeemed(depositRedeemedTotalAmount);
                        } else {
                            batch.setDepositRedeemed(depositRedeemedAmount);
                        }
                        Platform.runLater(() -> getParent().handleAction(AppConstants.ACTION_CLOSE_DIALOG));
                        Platform.runLater(() -> {
                            VoidedSalesOrderLayout layout = new VoidedSalesOrderLayout(fSalesOrder);
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
                        if (fSalesOrder.getCustomer() != null) {
                            deposit.setCustomer(fSalesOrder.getCustomer());
                        }
                        deposit.setDepositType(DBConstants.TYPE_DEPOSIT_CREDIT);
                        deposit.setOrderNumber(fSalesOrder.getSalesOrderNumber().toString());
                        deposit.setStatus(DBConstants.STATUS_CLOSE);
                        daoDeposit.insert(deposit);
                        fPaymentUI.getPayments().forEach(e -> {
                            e.setBatch(batch);
                            e.setDeposit(deposit);
                            e.setStore(Config.getStore());
                            daoPayment.update(e);
                            deposit.getPayments().add(e);
                        });
                        if (!fSalesOrder.getDeposits().isEmpty()) {
                            fSalesOrder.getDeposits().forEach(e -> {
                                e.setVoidedTransaction(fVoidedTransaction);
                                e.setSalesOrder(null);
                                e.setStatus(DBConstants.STATUS_CLOSE);
                                e.setCloseBatch(batch);
                                daoDeposit.update(e);
                            });
                        }
                        fSalesOrder.getDeposits().clear();
                        daoSalesOrder.delete(fSalesOrder);
                        batch.getDepositCloseBatchs().add(deposit);
                        daoBatch.update(batch);
                    }
                } else {
                    if (!fPaymentUI.getPayments().isEmpty()) {
                        BigDecimal depositAmount = fPaymentUI.getPayments().stream().map(e -> e.getTenderedAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        if (batch.getDepositMade() != null && batch.getDepositMade().compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal depositTotalAmount = depositAmount.add(batch.getDepositMade());
                            batch.setDepositMade(depositTotalAmount);
                        } else {
                            batch.setDepositMade(depositAmount);
                        }
                        saveSalesOrder();
                        if (daoSalesOrder.getErrorMessage() != null) {
                            break;
                        }
                        fSalesOrder.getDeposits().forEach(e -> {
                            e.setStatus(DBConstants.STATUS_CLOSE);
                            daoDeposit.update(e);
                        });
                        Deposit deposit = new Deposit();
                        deposit.setDateCreated(new Timestamp(new Date().getTime()));
                        deposit.setSalesOrder(fSalesOrder);
                        deposit.setAmount(depositAmount);
                        deposit.setOpenBatch(batch);
                        deposit.setStore(Config.getStore());
                        if (fSalesOrder.getCustomer() != null) {
                            deposit.setCustomer(fSalesOrder.getCustomer());
                        }
                        deposit.setDepositType(DBConstants.TYPE_DEPOSIT_DEPOSIT);
                        deposit.setOrderNumber(fSalesOrder.getSalesOrderNumber().toString());
                        deposit.setStatus(DBConstants.STATUS_OPEN);
                        fPaymentUI.getPayments().forEach(e -> {
                            e.setBatch(batch);
                            e.setDeposit(deposit);
                            e.setStore(Config.getStore());
                            deposit.getPayments().add(e);
                        });
                        daoDeposit.insert(deposit);
                        fSalesOrder.getDeposits().add(deposit);
                        daoSalesOrder.update(fSalesOrder);
                        batch.getDepositOpenBatchs().add(deposit);
                        daoBatch.update(batch);
                        printSalesOrder();
                    }
                }
                break;
            case AppConstants.ACTION_RETURN:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentFlag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentFlag()) {
                        showAlertDialog("You can't return a component of an assembly item!");
                        break;
                    }
                    BigDecimal qty = fTableView.getSelectionModel().getSelectedItem().getQuantity().negate();
                    fTableView.getSelectionModel().getSelectedItem().setQuantity(qty);
                    if (fTableView.getSelectionModel().getSelectedItem().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                        updateBom(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getSelectionModel().getSelectedItem());
                    }
                    fTableView.refresh();
                    updateTotal();
                } else {
                    fInvoiceUI = new InvoiceWidget(fCustomer);
                    fInvoiceUI.setParent(this);
                    fInputDialog = createSelectCancelUIDialog(fInvoiceUI.getView(), "Invoice");
                    selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        fList.clear();
                        fList = new ArrayList<>(fInvoiceUI.selectedItems.stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList()));
                        fList.forEach((e) -> {
                            BigDecimal qty = e.getQuantity().subtract(e.getQuantityReturn());
                            e.setQuantity(qty);
                            e.setQuantityReturn(qty);
                        });
                        handleAction(EDIT_RETURN_LIST);
                        updateTotal();
                    });
                    fInputDialog.show();
                }
                break;
            case EDIT_RETURN_LIST:
                if (!fList.isEmpty()) {
                    fInvoiceEntryTable.setItems(FXCollections.observableList(fList));
                    fInputDialog = createSaveCancelUIDialog(createReturnInvoiceEntryPane(), "Return Items");
                    Image processIcon = IconFactory.getIcon(RES.PROCESS_ICON);
                    saveBtn.setGraphic(new ImageView(processIcon));
                    saveBtn.setText("Process");
                    buttonSetWidth(saveBtn);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        if (fTableView.getSelectionModel().getSelectedItem() != null) {
                            int iSelectedIndex = fTableView.getSelectionModel().getSelectedIndex();
                            fTableView.getItems().remove(iSelectedIndex);
                            fList.stream().forEach(e -> {
                                Item item = getItem(e.getItemLookUpCode());
                                if (item != null) {
                                    SalesOrderEntry newItem = new SalesOrderEntry();
                                    newItem.setItem(item);
                                    newItem.setLineNote(e.getReturnCode());
                                    newItem.setQuantity(e.getQuantity().negate().setScale(2, RoundingMode.HALF_UP));
                                    newItem.setDiscountRate(BigDecimal.ZERO);
                                    newItem.setPrice(e.getPrice().setScale(2, RoundingMode.HALF_UP));
                                    newItem.setCost(e.getCost().setScale(2, RoundingMode.HALF_UP));
                                    fEntityList.add(newItem);
                                    if (item.getItemType() != null && item.getItemType() == DBConstants.ITEM_TYPE_BOM) {
                                        addBom(fTableView.getItems().size() - 1, item);
                                    }
                                }

                            });
                            int i = fTableView.getItems().size() - 1;
                            while (i >= 0) {
                                if (fTableView.getItems().get(i).getComponentFlag() != null && fTableView.getItems().get(i).getComponentFlag()) {
                                    i--;
                                } else {
                                    break;
                                }
                            }
                            updateTotal();
                            fTableView.refresh();
                            fTableView.requestFocus();
                            fTableView.getSelectionModel().select(i);
                            fTableView.getFocusModel().focus(i, fTableView.getColumns().get(2));
                            fTableView.edit(i, fTableView.getColumns().get(2));
                        }
                    });
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_CLOSE_DIALOG:
                if (fInputDialog != null) {
                    fInputDialog.close();
                }
                break;
        }
    }

    protected int getOrderType() {
        return fOrderType;
    }

    private void createGUI() {
        fTableView = new TableView<SalesOrderEntry>() {
            @Override
            public void edit(int row, TableColumn<SalesOrderEntry, ?> column) {
                if (row >= 0 && row <= (getItems().size() - 1)) {
                    SalesOrderEntry entry = getItems().get(row);
                    if (entry.getComponentFlag() != null && entry.getComponentFlag()) {
                        return;
                    }
                    if (entry.getItem() != null && column != null && entry.getItem().getItemType() != null && entry.getItem().getItemType().equals(DBConstants.ITEM_TYPE_BOM) && column.equals(discountCol)) {
                        return;
                    }
                    if (entry.getItem() != null && column != null && column.equals(skuCol)) {
                        return;
                    }
                    if (entry.getItem() == null && column != null && (column.equals(qtyCol) || column.equals(discountCol) || column.equals(markupCol) || column.equals(priceCol))) {
                        return;
                    }
                }
                super.edit(row, column);
            }
        };
        fTableView.setEditable(true);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addKeyListener();
        if (!fSalesOrder.getSalesOrderEntries().isEmpty()) {
            List<SalesOrderEntry> list = fSalesOrder.getSalesOrderEntries().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
            buildBom(list);
            fEntityList = FXCollections.observableList(list);
        } else {
            fEntityList = FXCollections.observableArrayList();
        }
        fTableView.setItems(fEntityList);
        fInvoiceEntryTable = new TableView<InvoiceEntry>() {
            @Override
            public void edit(int row, TableColumn<InvoiceEntry, ?> column) {
                if (row >= 0 && row <= (getItems().size() - 1)) {
                    InvoiceEntry entry = getItems().get(row);
                    Item item = getItem(entry.getItemLookUpCode());
                    if (item != null && column != getColumns().get(3) && column != getColumns().get(5)) {
                        getFocusModel().focus(row, getColumns().get(3));
                        return;
                    }
                }
                super.edit(row, column);
            }
        };
        mainView = createMainView();
        setTableWidth(fTableView);
        fNoteEditPane = createNoteEditPane();
        fCostEditPane = createCostEditPane();
        fServiceEntryEditPane = createServiceEntryEditPane();
        fAppointmentEditPane = createAppointmentEditPane();
        try {
            salesOrderUI.setData(fSalesOrder);
            updateTotal();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        updateAddressPane();
        if (fSalesOrder.getService() != null) {
            fService = fSalesOrder.getService();
            List<ServiceEntry> list = fSalesOrder.getService().getServiceEntries().stream().sorted((e1, e2) -> e1.getDateEntered().compareTo(e2.getDateEntered())).collect(Collectors.toList());
            fServiceEntryList = FXCollections.observableList(list);
            if (fSalesOrder.getService().getSerialNumber() != null) {
                try {
                    serialNumberUI.setData(fSalesOrder.getService().getSerialNumber());

                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            if (fService.getAppointment() != null) {
                fAppointment = fService.getAppointment();
            }
        } else {
            fServiceEntryList = FXCollections.observableList(new ArrayList<>());
        }
        fServiceEntryTable.setItems(fServiceEntryList);
        if (!fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_SERVICE)) {
            servicePane.setVisible(false);
            fTableView.setPrefHeight(375);
            servicePane.setPrefHeight(0);
        } else {
            servicePane.setVisible(true);
            fTableView.setPrefHeight(175);
            servicePane.setPrefHeight(250);
        }
        fReasonCodeList = FXCollections.observableList(daoReasonCode.read(ReasonCode_.store, Config.getStore()).stream().map(x -> x.getDescription()).collect(Collectors.toList()));
        reset();
        if (fTableView.getItems().isEmpty()) {
            handleAction(AppConstants.ACTION_ADD);
        } else {
            fTableView.getSelectionModel().selectFirst();
            fTableView.getSelectionModel().select(0, qtyCol);
        }
        fTableView.setRowFactory(mouseClickListener(1));
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);

        skuCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return null;
            }
        });
        skuCol.setOnEditCommit((TableColumn.CellEditEvent<SalesOrderEntry, String> t) -> {
            SalesOrderEntry soe = (SalesOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
            if (soe != null && soe.getItem() != null) {
                if (soe.getItem().getItemType() != null && soe.getItem().getItemType().equals(DBConstants.ITEM_TYPE_BOM)) {
                    int row = t.getTablePosition().getRow();
                    addBom(row, soe.getItem());
                }
                fTableView.refresh();
                updateTotal();
                handleAction(AppConstants.ACTION_ADD);
            }
        });
        skuCol.setCellFactory(editableCell(Pos.TOP_LEFT));
        skuCol.setPrefWidth(140);
        skuCol.setSortable(false);

        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, String> p) -> {
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
        descriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        descriptionCol.setEditable(false);
        descriptionCol.setSortable(false);
        descriptionCol.setPrefWidth(375);

        qtyCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrderEntry_.quantity.getName()));
        qtyCol.setOnEditCommit((TableColumn.CellEditEvent<SalesOrderEntry, BigDecimal> t) -> {
            if (t != null && t.getNewValue() != null && !t.getNewValue().equals(t.getOldValue())) {
                SalesOrderEntry soe = (SalesOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                if (soe.getItem() != null) {
                    Item item = soe.getItem();
                    if (!item.getFractionTag()) {
                        BigDecimal truncated = t.getNewValue().setScale(0, RoundingMode.DOWN);
                        soe.setQuantity(truncated);
                    } else {
                        soe.setQuantity(t.getNewValue());
                    }
                    if (item.getQuantityDiscount() != null && soe.getQuantity() != null && getQuantityDiscountPrice(item, fCustomer, soe.getQuantity()) != null) {
                        soe.setPrice(getQuantityDiscountPrice(item, fCustomer, soe.getQuantity()));
                    }
                    if (item.getItemType() != null && item.getItemType().equals(DBConstants.ITEM_TYPE_BOM)) {
                        updateBom(t.getTablePosition().getRow(), soe);
                    }
                }
                updateTotal();
                fTableView.refresh();
            }
        });
        qtyCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        qtyCol.setSortable(false);
        qtyCol.setPrefWidth(75);

        TableColumn<SalesOrderEntry, String> qtyCommittedCol = new TableColumn<>("Stock");
        qtyCommittedCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null) {
                if (p.getValue().getItem().getItemType() != null && p.getValue().getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                    return null;
                } else {
                    return new SimpleStringProperty(getString(getQuantity(p.getValue().getItem())));
                }
            } else {
                return null;
            }
        });
        qtyCommittedCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        qtyCommittedCol.setEditable(false);
        qtyCommittedCol.setSortable(false);
        qtyCommittedCol.setPrefWidth(75);

        priceCol.setOnEditCommit((TableColumn.CellEditEvent<SalesOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null && !t.getNewValue().equals(t.getOldValue())) {
                SalesOrderEntry soe = (SalesOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Item item = soe.getItem();
                if (item != null) {
                    BigDecimal price, discount;
                    price = new BigDecimal(t.getNewValue().doubleValue());
                    if (getItemPrice(item, fCustomer) != null && getItemPrice(item, fCustomer).compareTo(BigDecimal.ZERO) != 0) {
                        discount = zeroIfNull(getItemPrice(item, fCustomer).subtract(price).divide(getItemPrice(item, fCustomer), 6, RoundingMode.HALF_UP).multiply(new BigDecimal(100.00)));
                    } else {
                        discount = BigDecimal.ZERO;
                    }
                    if (discount.compareTo(BigDecimal.ZERO) < 0) {
                        discount = BigDecimal.ZERO;
                    }
                    soe.setPrice(price);
                    soe.setDiscountRate(discount);
                    if (item.getItemType() != null && soe.getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                        updateBom(t.getTablePosition().getRow(), soe);
                    }
                    updateTotal();
                    fTableView.refresh();
                }
            }
        });
        priceCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
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
        priceCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        priceCol.setEditable(true);
        priceCol.setSortable(false);
        priceCol.setPrefWidth(90);

        costCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getItem() != null && p.getValue().getCost() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    return new ReadOnlyObjectWrapper(p.getValue().getCost());
                }
            } else {
                return null;
            }
        });
        costCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        costCol.setEditable(false);
        costCol.setVisible(false);
        costCol.setSortable(false);
        costCol.setPrefWidth(90);

        discountCol.setOnEditCommit((TableColumn.CellEditEvent<SalesOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                SalesOrderEntry soe = (SalesOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Item item = soe.getItem();
                if (item != null && item.getItemType() != null) {
                    if (item.getItemType().compareTo(DBConstants.ITEM_TYPE_BOM) != 0) {
                        BigDecimal price, discount;
                        if ((soe.getQuantity() != null && soe.getQuantity().compareTo(BigDecimal.ZERO) <= 0) || (getItemPrice(item, fCustomer) != null && getItemPrice(item, fCustomer).compareTo(BigDecimal.ZERO) == 0)) {
                            discount = BigDecimal.ZERO;
                        } else {
                            discount = new BigDecimal(t.getNewValue().doubleValue());
                        }
                        if (discount.compareTo(BigDecimal.ZERO) > 0) {
                            price = zeroIfNull(getItemPrice(item, fCustomer)).multiply(BigDecimal.ONE.subtract(discount.divide(new BigDecimal(100.00), 6, RoundingMode.HALF_UP)));
                            soe.setDiscountRate(discount);
                            soe.setPrice(price);
                        } else {
                            soe.setDiscountRate(BigDecimal.ZERO);
                        }
                    }
                    updateTotal();
                    fTableView.refresh();
                }
            }
        });
        discountCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getDiscountRate() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else if (p.getValue().getItem() != null && p.getValue().getItem().getItemType() != null && p.getValue().getItem().getItemType().compareTo(DBConstants.ITEM_TYPE_BOM) == 0) {
                    return null;
                } else {
                    if (p.getValue().getQuantity() != null && p.getValue().getQuantity().compareTo(BigDecimal.ZERO) < 0) {
                        return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
                    } else {
                        return new ReadOnlyObjectWrapper(p.getValue().getDiscountRate());
                    }
                }
            } else {
                return null;
            }
        });
        discountCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        discountCol.setSortable(false);
        discountCol.setPrefWidth(75);

        markupCol.setOnEditCommit((TableColumn.CellEditEvent<SalesOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                SalesOrderEntry soe = (SalesOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                if (soe.getComponentFlag() == null || !soe.getComponentFlag()) {
                    if (soe.getItem() != null && soe.getCost() != null) {
                        BigDecimal cost = soe.getCost();
                        BigDecimal markup = (new BigDecimal(t.getNewValue().doubleValue())).divide(new BigDecimal(100), 6, RoundingMode.HALF_UP);
                        BigDecimal profit = cost.multiply(markup);
                        BigDecimal price = cost.add(profit);
                        soe.setPrice(price);
                        if (soe.getItem().getItemType() != null && soe.getItem().getItemType().compareTo(DBConstants.ITEM_TYPE_BOM) == 0) {
                            updateBom(t.getTablePosition().getRow(), soe);
                        }
                        updateTotal();
                    }
                }
            }
        });
        markupCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getItem() != null && p.getValue().getCost() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    BigDecimal price = zeroIfNull(p.getValue().getPrice()).multiply(zeroIfNull(p.getValue().getQuantity()));
                    BigDecimal cost = zeroIfNull(p.getValue().getCost()).multiply(zeroIfNull(p.getValue().getQuantity()));
                    BigDecimal profit = price.subtract(cost);
                    BigDecimal markupPercent;
                    if (cost.compareTo(BigDecimal.ZERO) != 0) {
                        markupPercent = (profit.divide(cost, 6, RoundingMode.HALF_UP)).multiply(new BigDecimal(100));
                        markupPercent = markupPercent.setScale(6, RoundingMode.HALF_UP);
                        return new ReadOnlyObjectWrapper(markupPercent);
                    } else {
                        return null;
                    }
                }
            } else {
                return null;
            }
        });
        markupCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        markupCol.setEditable(true);
        markupCol.setVisible(false);
        markupCol.setSortable(false);
        markupCol.setPrefWidth(75);

        TableColumn<SalesOrderEntry, String> taxableCol = new TableColumn<>("Taxable");
        taxableCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrderEntry_.taxable.getName()));
        taxableCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        taxableCol.setEditable(false);
        taxableCol.setSortable(false);
        taxableCol.setPrefWidth(50);

        TableColumn<SalesOrderEntry, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
            BigDecimal total;
            if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                    return null;
                } else {
                    total = p.getValue().getPrice().multiply(p.getValue().getQuantity()).setScale(2, RoundingMode.HALF_UP);
                    return new ReadOnlyObjectWrapper(total);
                }
            } else {
                return null;
            }
        }
        );
        totalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        totalCol.setEditable(false);
        totalCol.setSortable(false);
        totalCol.setPrefWidth(100);

        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(qtyCommittedCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(discountCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(markupCol);
        fTableView.getColumns().add(taxableCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(400);

        BorderPane bp = new BorderPane();
        bp.setCenter(fTableView);
        bp.setBottom(createButtonPane());
        bp.setTop(createTopPane());
        bp.getStyleClass().add("border");
        bp.getTop().setFocusTraversable(false);
        bp.getBottom().setFocusTraversable(false);

        mainPane.add(bp, 0, 0);
        mainPane.add(createBottomPane(), 0, 1);
        mainPane.add(createProfitBox(), 0, 2);
        return mainPane;
    }

    private void reset() {
        String[] convertQuote = new String[]{"Conver To", "Invoice", "Order"};
        String[] convertInvoice = new String[]{"Conver To", "Order", "Quote", "Service"};
        String[] convertOrder = new String[]{"Conver To", "Invoice", "Quote"};
        String[] convertService = new String[]{"Conver To", "Invoice"};
        String[] choice = new String[]{"Conver To", "Conver To", "Conver To"};
        if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_INVOICE) {
            choice = convertInvoice;
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_ORDER) {
            choice = convertOrder;
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_QUOTE) {
            choice = convertQuote;
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_SERVICE) {
            choice = convertService;
        }
        choiceBox.setItems(FXCollections.observableArrayList(choice));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            switch (newValue.intValue()) {
                case 1:
                    if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_INVOICE) {
                        handleAction(CONVERT_TO_ORDER);
                    } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_ORDER) {
                        handleAction(CONVERT_TO_INVOICE);
                    } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_SERVICE) {
                        handleAction(CONVERT_TO_INVOICE);
                    } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_QUOTE) {
                        handleAction(CONVERT_TO_INVOICE);
                    }
                    choiceBox.getSelectionModel().select(0);
                    break;
                case 2:
                    if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_INVOICE) {
                        handleAction(CONVERT_TO_QUOTE);
                    } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_ORDER) {
                        handleAction(CONVERT_TO_QUOTE);
                    } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_QUOTE) {
                        handleAction(CONVERT_TO_ORDER);
                    }
                    choiceBox.getSelectionModel().select(0);
                case 3:
                    if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_INVOICE && servicePane.isVisible()) {
                        handleAction(CONVERT_TO_SERVICE);
                    }
                    choiceBox.getSelectionModel().select(0);
                    break;
            }
        });
        if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_INVOICE) {
            functionKeyLabel.setText(invoiceFunctionString);
            processBtn.setText(AppConstants.ACTION_TENDER);
            processBtn.setId(AppConstants.ACTION_TENDER);
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_ORDER) {
            functionKeyLabel.setText(orderFunctionString);
            processBtn.setText(AppConstants.ACTION_SAVE);
            processBtn.setId(AppConstants.ACTION_SAVE);
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_SERVICE) {
            functionKeyLabel.setText(orderFunctionString);
            processBtn.setText(AppConstants.ACTION_SAVE);
            processBtn.setId(AppConstants.ACTION_SAVE);
        } else if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_QUOTE) {
            functionKeyLabel.setText(orderFunctionString);
            processBtn.setText(AppConstants.ACTION_SAVE);
            processBtn.setId(AppConstants.ACTION_SAVE);
        }
        fConvertCode = "";
    }

    private GridPane createNoteEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Line Note:", dataUI.createTextArea(SalesOrderEntry_.lineNote), 80, 250, fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    private GridPane createCostEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Cost:", dataUI.createTextField(SalesOrderEntry_.cost, 100), fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    private GridPane createTopPane() {
        GridPane topPane = new GridPane();
        topPane.setVgap(3.0);
        Label customerIDLabel = new Label("Customer: ");
        fBillToAddressTA.setEditable(false);
        fBillToAddressTA.setFont(Font.font("Verdana", 10));
        fBillToAddressTA.setPrefSize(485, 80);
        fBillToAddressTA.setFocusTraversable(false);
        fBillToAddressTA.setEditable(false);
        Label shipToLable = new Label(" Ship To:  ");
        fShipToAddressTA.setEditable(false);
        fShipToAddressTA.setFont(Font.font("Verdana", 10));
        fShipToAddressTA.setPrefSize(480, 80);
        fShipToAddressTA.setFocusTraversable(false);
        fShipToAddressTA.setEditable(false);
        Button selectShipToBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, CUSTOMER_SHIP_TO_SELECT, "Change Ship To", fHandler);
        selectShipToBtn.setFocusTraversable(false);
        GridPane addressPane = new GridPane();
        selectBuyerBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, CUSTOMER_BUYER_SELECT, "Change Buyer", fHandler);
        selectBuyerBtn.setFocusTraversable(false);
        Region filler = new Region();
        filler.setMinWidth(15);
        addressPane.setHgap(2.0);
        addressPane.setVgap(1.0);
        addressPane.setPadding(new Insets(2, 5, 2, 5));
        addressPane.add(customerIDLabel, 0, 0);
        addressPane.add(fBillToAddressTA, 0, 1, 2, 1);
        addressPane.add(selectBuyerBtn, 1, 0);
        addressPane.add(filler, 2, 1);
        addressPane.add(shipToLable, 3, 0);
        addressPane.add(selectShipToBtn, 4, 0);
        addressPane.add(fShipToAddressTA, 3, 1, 2, 1);

        GridPane.setHalignment(shipToLable, HPos.LEFT);
        GridPane.setHalignment(selectBuyerBtn, HPos.RIGHT);
        GridPane.setHalignment(selectShipToBtn, HPos.RIGHT);
        GridPane.setHalignment(fShipToAddressTA, HPos.RIGHT);
        GridPane.setHgrow(filler, Priority.ALWAYS);

        GridPane settingPane = new GridPane();
        settingPane.setHgap(4.0);
        Label salesOrderNumberLabel = new Label(" SalesOrder No:");
        settingPane.add(salesOrderNumberLabel, 0, 0);
        settingPane.add(salesOrderUI.createLabelField(SalesOrder_.salesOrderNumber, 90, Pos.CENTER_LEFT), 1, 0);

        Label employeeLabel = new Label("  Salesperson:");
        settingPane.add(employeeLabel, 2, 0);
        salesOrderUI.setUIComponent(SalesOrder_.sales, fSalesCombo);
        fSalesCombo.setPrefWidth(130);
        fSalesCombo.setFocusTraversable(false);
        settingPane.add(fSalesCombo, 3, 0);
        Label customerPOLabel = new Label("  Customer PO:");
        settingPane.add(customerPOLabel, 6, 0);
        settingPane.add(salesOrderUI.createTextField(SalesOrder_.customerPoNumber, 90), 7, 0);
        salesOrderUI.setUIComponent(SalesOrder_.dateDue, dateDue);
        dateDue.setPrefWidth(100);
        settingPane.add(dueDateLabel, 8, 0);
        settingPane.add(dateDue, 9, 0);
        salesOrderUI.getTextField(SalesOrder_.customerPoNumber).setFocusTraversable(false);
        salesOrderUI.getUIComponent(SalesOrder_.dateDue).setFocusTraversable(false);

        settingPane.setPadding(new Insets(2, 5, 2, 5));

        GridPane.setHalignment(salesOrderNumberLabel, HPos.RIGHT);
        GridPane.setHalignment(employeeLabel, HPos.RIGHT);

        topPane.add(addressPane, 0, 0);
        topPane.add(settingPane, 0, 1);
        return topPane;
    }

    private Node createBottomPane() {
        HBox bottomPane = new HBox();

        servicePane = createServicePane();

        GridPane sumPane = new GridPane();
        sumPane.setAlignment(Pos.TOP_RIGHT);
        Label subTotalLabel = new Label("Sub Total: ");

        TextField subTotalField = createLabelField(110.0, Pos.CENTER_RIGHT);
        subTotalField.textProperty().bindBidirectional(orderSubTotalProperty, getDecimalFormat());
        TextField balanceField = createLabelField(110.0, Pos.CENTER_RIGHT);
        balanceField.textProperty().bindBidirectional(orderBalanceProperty, getDecimalFormat());
        TextField depositField = createLabelField(110.0, Pos.CENTER_RIGHT);
        depositField.textProperty().bindBidirectional(orderDepositProperty, getDecimalFormat());
        TextField shippingField = createLabelField(110.0, Pos.CENTER_RIGHT);
        shippingField.textProperty().bindBidirectional(orderShippingChargeProperty, getDecimalFormat());

        Label depositLabel = new Label("Deposit: ");
        Label taxLabel = new Label("Tax: ");
        Label freightLabel = new Label("Freight: ");
        Label totalLabel = new Label("Total: ");
        Label balanceLabel = new Label("Balance: ");

        sumPane.add(subTotalLabel, 0, 0);
        sumPane.add(subTotalField, 1, 0);
        sumPane.add(depositLabel, 0, 1);
        sumPane.add(depositField, 1, 1);
        sumPane.add(taxLabel, 0, 2);
        sumPane.add(salesOrderUI.createLabelField(SalesOrder_.taxAmount, 110, Pos.CENTER_RIGHT), 1, 2);
        sumPane.add(freightLabel, 0, 3);
        sumPane.add(shippingField, 1, 3);
        sumPane.add(totalLabel, 0, 4);
        sumPane.add(salesOrderUI.createLabelField(SalesOrder_.total, 110, Pos.CENTER_RIGHT), 1, 4);
        sumPane.add(balanceLabel, 0, 5);
        sumPane.add(balanceField, 1, 5);

        subTotalLabel.setFont(Font.font("Verdana", 12));
        subTotalField.setFont(Font.font("Verdana", 12));
        taxLabel.setFont(Font.font("Verdana", 12));
        salesOrderUI.getTextField(SalesOrder_.taxAmount).setFont(Font.font("Verdana", 12));
        freightLabel.setFont(Font.font("Verdana", 12));
        shippingField.setFont(Font.font("Verdana", 12));
        depositLabel.setFont(Font.font("Verdana", 12));
        depositField.setFont(Font.font("Verdana", 12));
        totalLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        salesOrderUI.getTextField(SalesOrder_.total).setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        balanceLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        balanceField.setFont(Font.font("Verdana", FontWeight.BOLD, 12));

        VBox leftPane = new VBox();
        leftPane.getChildren().addAll(functionKeyLabel, servicePane);
        leftPane.setAlignment(Pos.TOP_LEFT);

        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        bottomPane.getChildren().addAll(leftPane, filler, sumPane);

        GridPane.setHalignment(subTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(depositLabel, HPos.RIGHT);
        GridPane.setHalignment(taxLabel, HPos.RIGHT);
        GridPane.setHalignment(totalLabel, HPos.RIGHT);
        GridPane.setHalignment(balanceLabel, HPos.RIGHT);
        GridPane.setHalignment(freightLabel, HPos.RIGHT);

        bottomPane.setAlignment(Pos.CENTER);
        return bottomPane;
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
        profitBox.setAlignment(Pos.CENTER_RIGHT);
        profitBox.setVisible(false);
        return profitBox;
    }

    private GridPane createServicePane() {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("insideView");
        pane.setPrefSize(765, 300);

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
        dateCol.setPrefWidth(125);

        TableColumn<ServiceEntry, String> employeeCol = new TableColumn<>("Tech");
        employeeCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getEmployee() != null) {
                return new SimpleStringProperty(p.getValue().getEmployee().getNameOnSalesOrder());
            } else {
                return null;
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        employeeCol.setSortable(false);
        employeeCol.setPrefWidth(100);

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
        noteCol.setPrefWidth(360);

        fServiceEntryTable.getColumns().add(dateCol);
        fServiceEntryTable.getColumns().add(employeeCol);
        fServiceEntryTable.getColumns().add(serviceCodeCol);
        fServiceEntryTable.getColumns().add(noteCol);
        fServiceEntryTable.setPrefHeight(220);
        fServiceEntryTable.setEditable(false);
        setTableWidth(fServiceEntryTable);

        pane.add(createServiceTitlePane(), 0, 1);
        pane.add(fServiceEntryTable, 0, 2);
        pane.add(createServiceButtonPane(), 0, 3);
        return pane;

    }

    private GridPane createServiceEntryEditPane() {
        GridPane serviceEntryEditPane = new GridPane();
        serviceEntryEditPane.getStyleClass().add("editView");
        fServiceCodeCombo = new ServiceCodeWidget();
        fServiceCodeCombo.setPrefWidth(350);
        serviceEntryUI.setUIComponent(ServiceEntry_.serviceCode, fServiceCodeCombo);
        add(serviceEntryEditPane, "Service Code:", fServiceCodeCombo, fListener, 0);
        add(serviceEntryEditPane, "Note:", serviceEntryUI.createTextArea(ServiceEntry_.note), 250, 350, fListener, 1);
        serviceEntryEditPane.add(lblWarning, 0, 3, 2, 1);
        return serviceEntryEditPane;
    }

    private GridPane createAppointmentEditPane() {
        GridPane appointmentEditPane = new GridPane();
        appointmentEditPane.getStyleClass().add("editView");
        fEmployeeCombo.setPrefWidth(350);
        appointmentUI.setUIComponent(Appointment_.employee, fEmployeeCombo);
        add(appointmentEditPane, "Technician: ", fEmployeeCombo, fListener, 1);
        add(appointmentEditPane, "Appointment Day: ", appointmentUI.createDateTimePicker(Appointment_.appointmentDate), fListener, 2);
        add(appointmentEditPane, "Note: ", appointmentUI.createTextArea(Appointment_.note), 250, 350, fListener, 3);
        appointmentEditPane.add(lblWarning, 0, 3, 2, 1);
        return appointmentEditPane;
    }

    private HBox createServiceTitlePane() {
        HBox serviceTitleBox = new HBox(4);
        Label snLabel = new Label("S/N:");
        Label invoiceLabel = new Label("Invoice:");
        Label laborWarrantyExpiredLabel = new Label("Labor Warranty Expire:");
        Label partWarrantyExpiredLabel = new Label("Parts Warranty Expire:");
        serviceTitleBox.getChildren().addAll(
                invoiceLabel, serialNumberUI.createLabelField(SerialNumber_.invoiceNumber, 100, Pos.CENTER_LEFT),
                snLabel, serialNumberUI.createLabelField(SerialNumber_.serialNumber, 100, Pos.CENTER_LEFT),
                laborWarrantyExpiredLabel, serialNumberUI.createLabelField(SerialNumber_.warrantyExpireLabor, 100, Pos.CENTER_LEFT),
                partWarrantyExpiredLabel, serialNumberUI.createLabelField(SerialNumber_.warrantyExpirePart, 100, Pos.CENTER_LEFT));

        return serviceTitleBox;
    }

    private HBox createButtonPane() {
        HBox leftButtonBox = new HBox();
        Button upButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_UP_ICON), AppConstants.ACTION_MOVE_ROW_UP, fHandler);
        Button downButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_DOWN_ICON), AppConstants.ACTION_MOVE_ROW_DOWN, fHandler);
        Button newButton = ButtonFactory.getButton(IconFactory.getIcon(RES.ADD_ICON), AppConstants.ACTION_ADD, fHandler);
        Button deleteButton = ButtonFactory.getButton(IconFactory.getIcon(RES.DELETE_ICON), AppConstants.ACTION_DELETE, fHandler);
        Button editButton = ButtonFactory.getButton(IconFactory.getIcon(RES.EDIT_ICON), AppConstants.ACTION_EDIT, fHandler);
        Button lineNoteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_LINE_NOTE, AppConstants.ACTION_LINE_NOTE, fHandler);
        Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_LIST, AppConstants.ACTION_SELECT_LIST, fHandler);
        Button editBOMBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT_BOM, AppConstants.ACTION_EDIT_BOM, fHandler);
        editBOMBtn.setPrefWidth(94);
        lineNoteButton.setPrefWidth(90);
        selectButton.setPrefWidth(90);
        leftButtonBox.getChildren().addAll(upButton, downButton, newButton, deleteButton, editButton, lineNoteButton, selectButton, editBOMBtn);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button voidButton = ButtonFactory.getButton(ButtonFactory.BUTTON_VOID, AppConstants.ACTION_VOID, "Void", fHandler);
        choiceBox.setPrefWidth(94);
        processBtn.setPrefWidth(80);
        rightButtonBox.getChildren().addAll(choiceBox, voidButton, processBtn);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        buttonGroup.setPrefWidth(960);
        return buttonGroup;
    }

    private GridPane createReturnInvoiceEntryPane() {
        GridPane invoiceEntryPane = new GridPane();
        invoiceEntryPane.setHgap(20.0);
        invoiceEntryPane.setVgap(3.0);

        TableColumn<InvoiceEntry, String> detailSKUCol = new TableColumn<>("SKU");
        detailSKUCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        detailSKUCol.setMinWidth(100);
        detailSKUCol.setCellFactory(stringCell(Pos.CENTER));
        detailSKUCol.setEditable(false);
        detailSKUCol.setSortable(false);

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                return new SimpleStringProperty(p.getValue().getItemDescription());
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        detailDescriptionCol.setMinWidth(250);
        detailDescriptionCol.setEditable(false);
        detailDescriptionCol.setSortable(false);

        TableColumn<InvoiceEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantity());
            }
        });
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(80);
        detailQtyCol.setEditable(false);
        detailQtyCol.setSortable(false);

        TableColumn<InvoiceEntry, BigDecimal> detailReturnQtyCol = new TableColumn<>("Return");
        detailReturnQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantityReturn());
            }
        });
        detailReturnQtyCol.setOnEditCommit((CellEditEvent<InvoiceEntry, BigDecimal> event) -> {
            TablePosition<InvoiceEntry, BigDecimal> pos = event.getTablePosition();
            int row = pos.getRow();
            InvoiceEntry ie = event.getTableView().getItems().get(row);
            BigDecimal newValue = new BigDecimal(event.getNewValue().toString());
            if (event.getNewValue() != null && newValue.compareTo(ie.getQuantity()) <= 0) {
                ie.setQuantityReturn(newValue);
            } else {
                ie.setQuantityReturn(new BigDecimal(event.getOldValue().toString()));
            }
        });
        detailReturnQtyCol.setCellFactory(decimalEditCell(Pos.CENTER_RIGHT));
        detailReturnQtyCol.setMinWidth(80);
        detailReturnQtyCol.setEditable(true);
        detailReturnQtyCol.setSortable(false);

        TableColumn<InvoiceEntry, BigDecimal> detailPriceCol = new TableColumn<>("Price");
        detailPriceCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getPrice());
            }
        });
        detailPriceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailPriceCol.setMinWidth(90);
        detailPriceCol.setEditable(false);
        detailPriceCol.setSortable(false);

        TableColumn<InvoiceEntry, String> returnCodeCol = new TableColumn<>("Reason");
        returnCodeCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.returnCode.getName()));
        returnCodeCol.setCellFactory(ComboBoxTableCell.forTableColumn(fReasonCodeList));
        returnCodeCol.setOnEditCommit((CellEditEvent<InvoiceEntry, String> event) -> {
            TablePosition<InvoiceEntry, String> pos = event.getTablePosition();
            int row = pos.getRow();
            InvoiceEntry ie = event.getTableView().getItems().get(row);
            ie.setReturnCode(event.getNewValue());
        });
        returnCodeCol.setPrefWidth(250);
        returnCodeCol.setResizable(false);
        returnCodeCol.setEditable(true);
        returnCodeCol.setSortable(false);

        fInvoiceEntryTable.getColumns().clear();
        fInvoiceEntryTable.getColumns().add(detailSKUCol);
        fInvoiceEntryTable.getColumns().add(detailDescriptionCol);
        fInvoiceEntryTable.getColumns().add(detailQtyCol);
        fInvoiceEntryTable.getColumns().add(detailReturnQtyCol);
        fInvoiceEntryTable.getColumns().add(detailPriceCol);
        fInvoiceEntryTable.getColumns().add(returnCodeCol);
        fInvoiceEntryTable.setEditable(true);
        fInvoiceEntryTable.setFixedCellSize(28);

        invoiceEntryPane.add(fInvoiceEntryTable, 0, 2);

        return invoiceEntryPane;
    }

    private HBox createServiceButtonPane() {
        Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, SELECT_SERIAL_NUMBER, "Select SN", fHandler);
        Button unSelectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, UN_SELECT_SERIAL_NUMBER, "Un Select SN", fHandler);
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, NEW_SERVICE_ENTRY, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, EDIT_SERVICE_ENTRY, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, DELETE_SERVICE_ENTRY, AppConstants.ACTION_DELETE, fHandler);
        Button appointmentButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DAY_PICKER, APPOINTMENT, APPOINTMENT, fHandler);
        appointmentButton.setPrefWidth(110);
        selectButton.setPrefWidth(104);
        HBox buttonGroup = new HBox(4);
        Separator sp = new Separator();
        sp.setOrientation(Orientation.VERTICAL);
        buttonGroup.getChildren().addAll(appointmentButton, selectButton, unSelectButton, sp, newButton, editButton, deleteButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        return buttonGroup;
    }

    @Override
    protected void updateTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal costTotal = BigDecimal.ZERO;
        BigDecimal profitTotal;
        BigDecimal profitPercentTotal = BigDecimal.ZERO;
        BigDecimal deposit = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;
        BigDecimal shippingTotal = BigDecimal.ZERO;
        BigDecimal commissionTotal = BigDecimal.ZERO;
        BigDecimal total;
        BigDecimal balance;
        try {
            salesOrderUI.getData(fSalesOrder);
            if (fCustomerShipTo != null) {
                fSalesOrder.setShipTo(fCustomerShipTo);

            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i <= fTableView.getItems().size() - 1; i++) {
            SalesOrderEntry soe = fTableView.getItems().get(i);
            if ((soe.getComponentFlag() == null || !soe.getComponentFlag()) && soe.getQuantity() != null && soe.getPrice() != null) {
                if (soe.getItem().getItemType() != null && soe.getItem().getItemType().compareTo(DBConstants.ITEM_TYPE_BOM) == 0) {
                    updateBom(i, soe);
                }
                BigDecimal lineTotal = soe.getQuantity().multiply(soe.getPrice());
                subTotal = subTotal.add(lineTotal);
                if (soe.getItem() != null) {
                    if (fCustomer.getTaxExempt() != null && fCustomer.getTaxExempt()) {
                        soe.setTaxable(Boolean.FALSE);
                        soe.setTaxAmount(BigDecimal.ZERO);
                    } else {
                        Tax tax = getTax(soe.getItem(), fSalesOrder.getTaxZone());
                        if (tax != null && tax.getTaxRate().getRate().compareTo(BigDecimal.ZERO) != 0) {
                            taxTotal = taxTotal.add(lineTotal.multiply(tax.getTaxRate().getRate()));
                            soe.setTaxAmount(lineTotal.multiply(tax.getTaxRate().getRate()));
                            soe.setTaxable(Boolean.TRUE);
                        } else {
                            soe.setTaxAmount(BigDecimal.ZERO);
                            soe.setTaxable(Boolean.FALSE);
                        }
                    }
                    if (soe.getItem().getCategory() != null) {
                        if (soe.getItem().getCategory().getIsShippingTag() != null && soe.getItem().getCategory().getIsShippingTag()) {
                            shippingTotal = shippingTotal.add(lineTotal);
                        }
                        BigDecimal commission = BigDecimal.ZERO;
                        if (soe.getItem().getCategory().getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES) && soe.getItem().getCategory().getCommisionPercentSale() != null) {
                            commission = soe.getItem().getCategory().getCommisionPercentSale().divide(new BigDecimal(100)).multiply(soe.getPrice().multiply(soe.getQuantity()));
                        } else if (soe.getItem().getCategory().getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_PROFIT) && soe.getItem().getCategory().getCommisionPercentProfit() != null) {
                            commission = soe.getItem().getCategory().getCommisionPercentProfit().divide(new BigDecimal(100)).multiply((soe.getPrice().subtract(zeroIfNull(soe.getCost()))).multiply(soe.getQuantity()));
                        } else if (soe.getItem().getCategory().getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_FIXED_AMOUNT) && soe.getItem().getCategory().getCommissionFixedAmount() != null) {
                            commission = soe.getItem().getCategory().getCommissionFixedAmount();
                        }
                        soe.setCommissionAmount(commission);
                        commissionTotal = commissionTotal.add(commission);
                    }
                    if (soe.getCost() != null) {
                        BigDecimal lineCost = soe.getQuantity().multiply(soe.getCost());
                        costTotal = costTotal.add(lineCost);
                    }
                }
            }
        }
        orderShippingChargeProperty.set(shippingTotal);
        subTotal = subTotal.subtract(shippingTotal);
        if (!fSalesOrder.getDeposits().isEmpty()) {
            deposit = fSalesOrder.getDeposits()
                    .stream()
                    .filter(e -> e.getAmount() != null)
                    .map(e -> e.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        total = subTotal.add(taxTotal).add(shippingTotal);
        balance = total.subtract(deposit);
        profitTotal = subTotal.subtract(costTotal);
        if (subTotal.compareTo(BigDecimal.ZERO) != 0) {
            profitPercentTotal = profitTotal.divide(subTotal, 6, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
        }
        total = total.setScale(2, RoundingMode.HALF_UP);
        balance = balance.setScale(2, RoundingMode.HALF_UP);
        subTotal = subTotal.setScale(2, RoundingMode.HALF_UP);
        taxTotal = taxTotal.setScale(2, RoundingMode.HALF_UP);
        shippingTotal = shippingTotal.setScale(2, RoundingMode.HALF_UP);
        costTotal = costTotal.setScale(2, RoundingMode.HALF_UP);
        profitPercentTotal = profitPercentTotal.setScale(4, RoundingMode.HALF_UP);
        deposit = deposit.setScale(2, RoundingMode.HALF_UP);
        orderSubTotalProperty.set(subTotal);
        orderBalanceProperty.set(balance);
        orderDepositProperty.set(deposit);
        orderCostProperty.set(costTotal);
        orderProfitProperty.set(profitTotal);
        orderProfitPercentProperty.set(profitPercentTotal);
        fSalesOrder.setTaxAmount(taxTotal);
        fSalesOrder.setShippingCharge(shippingTotal);
        fSalesOrder.setTotal(total);
        fSalesOrder.setCommissionAmount(commissionTotal);
        salesOrderUI.getTextField(SalesOrder_.taxAmount).setText(getString(taxTotal));
        salesOrderUI.getTextField(SalesOrder_.total).setText(getString(total));
        fTableView.refresh();
    }

    private void updateAddressPane() {
        if (fSalesOrder.getBuyer() != null) {
            fBillToAddressTA.setText(getBillToAddress(fSalesOrder.getBuyer()));
        } else {
            fBillToAddressTA.setText(getBillToAddress(fSalesOrder.getCustomer()));
        }
        if (fCustomerShipTo != null) {
            fShipToAddressTA.setText(getShipToAddress(fCustomerShipTo));
        } else {
            fShipToAddressTA.setText(getBillToAddress(fSalesOrder.getCustomer()));
        }
    }

    private void update() {
        updateTotal();
        try {
            salesOrderUI.getData(fSalesOrder);
            if (fCustomerShipTo != null) {
                fSalesOrder.setShipTo(fCustomerShipTo);
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        if (!fSalesOrder.getSalesOrderEntries().isEmpty()) {
            fSalesOrder.getSalesOrderEntries().clear();
        }
        for (int i = 0; i < fTableView.getItems().size(); i++) {
            SalesOrderEntry entry = fTableView.getItems().get(i);
            if (entry.getItem() != null) {
                entry.setSalesOrder(fSalesOrder);
                entry.setDisplayOrder(i);
                fSalesOrder.getSalesOrderEntries().add(entry);
            }
        }
        if (fService != null) {
            fService.getServiceEntries().clear();
            for (int i = 0; i < fServiceEntryTable.getItems().size(); i++) {
                ServiceEntry entry = fServiceEntryTable.getItems().get(i);
                if (entry.getServiceCode() != null) {
                    entry.setService(fService);
                    fService.getServiceEntries().add(entry);
                }
            }
            fSalesOrder.setService(fService);
        } else {
            fSalesOrder.setService(null);
        }
    }

    private void addBom(int row, Item item) {
        BigDecimal price, cost, pf, ncPrice;
        price = getItemPrice(item, fCustomer);
        cost = item.getCost();
        ncPrice = BigDecimal.ZERO;
        List<ItemBom> list = item.getItemBomBomItems().stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder())).collect(Collectors.toList());
        for (ItemBom ia : list) {
            Item component = ia.getComponentItem();
            if (component.getCost() == null || component.getCost().compareTo(BigDecimal.ZERO) == 0) {
                ncPrice = ncPrice.add(zeroIfNull(getItemPrice(component, fCustomer)).multiply(zeroIfNull(ia.getQuantity())));
            }
        }
        price = price.subtract(ncPrice);
        if (price != null && price.compareTo(BigDecimal.ZERO) != 0) {
            pf = (price.subtract(zeroIfNull(cost)).divide(price, 6, RoundingMode.HALF_UP));
        } else {
            pf = BigDecimal.ZERO;
        }
        int i = row + 1;
        for (ItemBom ia : list) {
            Item component = ia.getComponentItem();
            SalesOrderEntry soe = new SalesOrderEntry();
            soe.setComponentFlag(Boolean.TRUE);
            soe.setItem(component);
            soe.setComponentQuantity(ia.getQuantity());
            soe.setQuantity(ia.getQuantity());
            soe.setDiscountRate(BigDecimal.ZERO);
            if (ia.getCost() != null && ia.getCost().compareTo(BigDecimal.ZERO) != 0) {
                soe.setCost(ia.getCost());
            } else {
                soe.setCost(component.getCost());
            }
            soe.setLineNote(ia.getLineNote());
            if (soe.getItem() != null && soe.getCost() != null && soe.getCost().compareTo(BigDecimal.ZERO) != 0) {
                soe.setPrice(soe.getCost().divide(BigDecimal.ONE.subtract(pf), 6, RoundingMode.HALF_UP));
            } else {
                soe.setPrice(zeroIfNull(getItemPrice(component, fCustomer)));
            }
            fTableView.getItems().add(i, soe);
            i++;
        }
    }

    private void deleteBom(int row) {
        int j = 0;
        for (int i = row; i < fTableView.getItems().size(); i++) {
            if (fTableView.getItems().get(i) != null && fTableView.getItems().get(i).getComponentFlag() != null && fTableView.getItems().get(i).getComponentFlag()) {
                j++;
            } else {
                break;
            }
        }
        fTableView.getItems().remove(row, row + j);
        fTableView.refresh();
    }

    private void updateBom(int row, SalesOrderEntry entry) {
        BigDecimal price, cost, pf, ncPrice;
        int i;
        if (entry.getItem().getItemType() != null && entry.getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
            price = entry.getPrice();
            cost = entry.getCost();
            ncPrice = BigDecimal.ZERO;
            i = row + 1;
            while (i < fTableView.getItems().size()) {
                SalesOrderEntry soe = fTableView.getItems().get(i);
                if (soe.getItem() != null && soe.getComponentFlag() != null && soe.getComponentFlag()) {
                    if (soe.getCost() != null || soe.getCost().compareTo(BigDecimal.ZERO) != 0) {
                        ncPrice = ncPrice.add(zeroIfNull(soe.getPrice()).multiply(zeroIfNull(soe.getComponentQuantity())));
                    }
                    i++;
                } else {
                    break;
                }
            }
            price = price.subtract(ncPrice);
            if (price != null && price.compareTo(BigDecimal.ZERO) != 0) {
                pf = (price.subtract(zeroIfNull(cost)).divide(price, 6, RoundingMode.HALF_UP));
            } else {
                pf = BigDecimal.ZERO;
            }
            i = row + 1;
            while (i < fTableView.getItems().size()) {
                SalesOrderEntry soe = fTableView.getItems().get(i);
                if (soe.getItem() != null && soe.getComponentFlag() != null && soe.getComponentFlag()) {
                    if (soe.getCost() != null && pf.compareTo(BigDecimal.ONE) != 0) {
                        soe.setPrice(soe.getCost().divide(BigDecimal.ONE.subtract(pf), 6, RoundingMode.HALF_UP));
                    }
                    soe.setQuantity(entry.getQuantity().multiply(soe.getComponentQuantity()));
                    i++;
                } else {
                    break;
                }
            }
            fTableView.refresh();
        }

    }

    private void buildBom(List<SalesOrderEntry> list) {
        for (int i = 0; i < list.size(); i++) {
            SalesOrderEntry soe = list.get(i);
            if (soe.getItem() != null && soe.getItem().getItemType() != null && soe.getItem().getItemType() == DBConstants.ITEM_TYPE_BOM) {
                int j = i + 1;
                List<ItemBom> aList = new ArrayList<>();
                while (j < list.size()) {
                    if (list.get(j).getItem() != null && list.get(j).getComponentFlag() != null && list.get(j).getComponentFlag()) {
                        ItemBom ib = new ItemBom();
                        ib.setBomItem(soe.getItem());
                        ib.setComponentItem(list.get(j).getItem());
                        ib.setDisplayOrder(j - i);
                        if (list.get(j).getComponentQuantity() != null) {
                            ib.setQuantity(list.get(j).getComponentQuantity());
                        }
                        if (list.get(j).getCost() != null && list.get(j).getCost().compareTo(BigDecimal.ZERO) != 0) {
                            ib.setCost(list.get(j).getCost());
                        }
                        if (list.get(j).getLineNote() != null && !list.get(j).getLineNote().isEmpty()) {
                            ib.setLineNote(list.get(j).getLineNote());
                        }
                        aList.add(ib);
                        j++;
                    } else {
                        break;
                    }
                }
                soe.getItem().setItemBomBomItems(aList);
            }
        }
    }

    public String getTitle() {
        String title = "";
        if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_QUOTE)) {
            title = "Quote";
        } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_ORDER)) {
            title = "Order";
        } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_SERVICE)) {
            title = "Service";
        } else if (fSalesOrder.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
            title = "Invoice";
        }
        return title;
    }

    public SalesOrder getSalesOrder() {
        return fSalesOrder;
    }

    private void convertOrder() {
        showConfirmDialog("Are you sure to convert this " + getTitle().toLowerCase() + " to a " + fConvertCode.toLowerCase() + "?", (ActionEvent e) -> {
            TransactionLog transLog = new TransactionLog();
            transLog.setEmployee(Config.getEmployee().getId());
            transLog.setEmployeeName(getString(Config.getEmployee().getFirstName()) + " " + getString(Config.getEmployee().getLastName()));
            transLog.setFromTransactionNumber(Integer.toString(fSalesOrder.getSalesOrderNumber()));
            transLog.setFromTranscationType(fSalesOrder.getType());
//            fSalesOrder.setDateOrdered(new Date());
            if (!fConvertCode.isEmpty() && fConvertCode.equalsIgnoreCase(CONVERT_TO_ORDER)) {
                fSalesOrder.setType(DBConstants.TYPE_SALESORDER_ORDER);
                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
                if (Config.getStore().getOrderDueDays() != null) {
                    if (fSalesOrder.getDateOrdered() == null) {
                        fSalesOrder.setDateOrdered(new Date());
                    }
                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getOrderDueDays()));
                }
                dueDateLabel.setText(DATE_DUE);
                dueDateLabel.setVisible(true);
                dateDue.setVisible(true);
            } else if (!fConvertCode.isEmpty() && fConvertCode.equalsIgnoreCase(CONVERT_TO_SERVICE)) {
                fSalesOrder.setType(DBConstants.TYPE_SALESORDER_SERVICE);
                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_SERVICE_ORDER_NUMBER));
                if (Config.getStore().getServiceOrderDueDays() != null) {
                    if (fSalesOrder.getDateOrdered() == null) {
                        fSalesOrder.setDateOrdered(new Date());
                    }
                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getServiceOrderDueDays()));
                }
                dueDateLabel.setText(DATE_DUE);
                dueDateLabel.setVisible(true);
                dateDue.setVisible(true);
            } else if (!fConvertCode.isEmpty() && fConvertCode.equalsIgnoreCase(CONVERT_TO_QUOTE)) {
                fSalesOrder.setType(DBConstants.TYPE_SALESORDER_QUOTE);
                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_QUOTE_NUMBER));
                if (Config.getStore().getQuoteExpirationDays() != null) {
                    if (fSalesOrder.getDateOrdered() == null) {
                        fSalesOrder.setDateOrdered(new Date());
                    }
                    fSalesOrder.setDateDue(addDay(fSalesOrder.getDateOrdered(), Config.getStore().getQuoteExpirationDays()));
                }
                dueDateLabel.setText(DATE_EXPIRED);
                dueDateLabel.setVisible(true);
                dateDue.setVisible(true);
            } else if (!fConvertCode.isEmpty() && fConvertCode.equalsIgnoreCase(CONVERT_TO_INVOICE)) {
                fSalesOrder.setType(DBConstants.TYPE_SALESORDER_INVOICE);
                fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_INVOICE_NUMBER));
                dueDateLabel.setVisible(false);
                dateDue.setVisible(false);
            }
            try {
                salesOrderUI.setData(fSalesOrder);
                updateTotal();
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            transLog.setToTransactionNumber(Integer.toString(fSalesOrder.getSalesOrderNumber()));
            transLog.setToTransactionType(fSalesOrder.getType());
            daoLog.insert(transLog);
            reset();
            if (getParent() != null && getParent().fInputDialog != null) {
                getParent().fInputDialog.setTitle(getTitle());
            }
        });
    }

    private Invoice convertToInvoice(SalesOrder so) {
        Invoice invoice = new Invoice();
        if (so.getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
            invoice.setInvoiceNumber(so.getSalesOrderNumber());
        } else {
            invoice.setInvoiceNumber(Config.getNumber(DBConstants.SEQ_INVOICE_NUMBER));
        }
        invoice.setStore(so.getStore());
        invoice.setNote(so.getNote());
        invoice.setDateOrdered(so.getDateOrdered());
        invoice.setDateInvoiced(new Timestamp(new Date().getTime()));
        invoice.setStationNumber(Config.getStation().getNumber());
        invoice.setCashierName(Config.getEmployee().getNameOnSalesOrder());
        invoice.setCustomerAccountNumber(so.getCustomer().getAccountNumber());
        invoice.setFob(so.getFob());
        if (fPaymentUI != null && !fPaymentUI.getPayments().isEmpty()) {
            fPaymentUI.getPayments().forEach(e -> {
                e.setInvoice(invoice);
                invoice.getPayments().add(e);
            });
        }
        Payment netPayment = invoice.getPayments().stream()
                .filter(e -> e.getPaymentType() != null && e.getPaymentType().getIsNetTerm() != null && e.getPaymentType().getIsNetTerm())
                .findFirst().orElse(null);
        if (netPayment != null) {
            invoice.setCustomerTermCode(so.getCustomer().getCustomerTerm().getCode());
        } else {
            if (Config.getDefaultCustomerTerm() != null && Config.getDefaultCustomerTerm().getCode() != null) {
                invoice.setCustomerTermCode(Config.getDefaultCustomerTerm().getCode());
            } else {
                invoice.setCustomerTermCode(null);
            }
        }
        if (!so.getDeposits().isEmpty()) {
            BigDecimal depositPayment = so.getDeposits()
                    .stream()
                    .map(e -> e.getAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            invoice.setDepositAmount(depositPayment);
        }
        if (!invoice.getPayments().isEmpty()) {
            BigDecimal giftAmount = BigDecimal.ZERO;
            invoice.getPayments().forEach(e -> {
                if (e.getGiftCertificateNumber() != null && e.getTenderedAmount() != null && e.getTenderedAmount().compareTo(BigDecimal.ZERO) > 0) {
                    giftAmount.add(e.getTenderedAmount());
                }
            });
            invoice.setGiftAmount(giftAmount);
        }
        invoice.setOrderNumber(so.getSalesOrderNumber());
        invoice.setOrderType(so.getType());
        invoice.setSalesName(so.getSales().getNameOnSalesOrder());
        invoice.setPostedFlag(Boolean.FALSE);
        invoice.setTaxExemptFlag(so.getTaxExemptFlag());
        invoice.setVoidedFlag(Boolean.FALSE);
        invoice.setWebOrderFlag(Boolean.FALSE);
        if (so.getService() != null) {
            if (so.getService().getId() == null) {
                Service service = daoService.insert(so.getService());
                so.setService(service);
            }
            invoice.setService(so.getService());
            so.setService(null);
        }
        invoice.setBillToAddress1(fCustomer.getAddress1());
        invoice.setBillToAddress2(fCustomer.getAddress2());
        invoice.setBillToCity(fCustomer.getCity());
        invoice.setBillToState(fCustomer.getState());
        invoice.setBillToPostCode(fCustomer.getPostCode());
        invoice.setBillToDepartment(fCustomer.getDepartment());
        invoice.setBillToCountry(fCustomer.getCountry().getIsoCode3());
        String cname;
        if (so.getBuyer() != null && so.getBuyer().getPhoneNumber() != null && !so.getBuyer().getPhoneNumber().isEmpty()) {
            invoice.setPhoneNumber(so.getBuyer().getPhoneNumber());
        } else {
            invoice.setPhoneNumber(fCustomer.getPhoneNumber());
        }
        if (so.getBuyer() != null) {
            cname = (!isEmpty(so.getBuyer().getFirstName()) ? so.getBuyer().getFirstName() : "")
                    + (!isEmpty(so.getBuyer().getFirstName()) ? " " : "")
                    + (!isEmpty(so.getBuyer().getLastName()) ? so.getBuyer().getLastName() : "");

        } else {
            cname = (!isEmpty(fCustomer.getFirstName()) ? fCustomer.getFirstName() : "")
                    + (!isEmpty(fCustomer.getFirstName()) ? " " : "")
                    + (!isEmpty(fCustomer.getLastName()) ? fCustomer.getLastName() : "");
        }
        invoice.setCustomerName(cname);
        invoice.setBillToCompany(fCustomer.getCompany());
        if (so.getShipTo() != null) {
            invoice.setShipToAddress(getShipToAddress(so.getShipTo()));
        } else {
            invoice.setShipToAddress(getBillToAddress(fCustomer));
        }
        invoice.setInvoiceType(so.getCustomer().getCustomerType());
        invoice.setCommissionAmount(so.getCommissionAmount());
        invoice.setCommissionPaidFlag(Boolean.FALSE);
//        invoice.setBatch(batch);
        invoice.setCreditAmount(so.getCreditAmount());
        invoice.setTotal(so.getTotal());
        invoice.setTaxAmount(so.getTaxAmount());
        if (invoice.getTotal() != null) {
            invoice.setSubTotal(invoice.getTotal().subtract(zeroIfNull(invoice.getTaxAmount())));
        }
        if (so.getTaxZone() != null) {
            invoice.setTaxZoneName(so.getTaxZone().getName());
        }
        invoice.setShippingCharge(so.getShippingCharge());
        if (so.getCustomerPoNumber() != null) {
            invoice.setCustomerPoNumber(so.getCustomerPoNumber());
        } else {
            invoice.setCustomerPoNumber("VERBAL");
        }
        if (!so.getSalesOrderEntries().isEmpty()) {
            BigDecimal cost = BigDecimal.ZERO;
            for (SalesOrderEntry soe : so.getSalesOrderEntries()) {
                InvoiceEntry ie = new InvoiceEntry();
                ie.setComponentFlag(soe.getComponentFlag());
                ie.setComponentQuantity(soe.getComponentQuantity());
                ie.setDisplayOrder(soe.getDisplayOrder());
                ie.setPrice(soe.getPrice());
                if (so.getSales() != null) {
                    ie.setSalesName(so.getSales().getNameOnSalesOrder());
                } else {
                    ie.setSalesName(Config.getEmployee().getNameOnSalesOrder());
                }
                ie.setCommissionPaidFlag(Boolean.FALSE);
                if (getItemPrice(soe.getItem(), soe.getSalesOrder().getCustomer()) != null && soe.getPrice() != null) {
                    ie.setDiscountAmount(getItemPrice(soe.getItem(), soe.getSalesOrder().getCustomer()).subtract(soe.getPrice()));
                }
                if (soe.getPrice() != null && soe.getDiscountRate() != null && !(soe.getItem() != null && soe.getItem().getItemType() != null && soe.getItem().getItemType() == DBConstants.ITEM_TYPE_BOM)) {
                    ie.setDiscountAmount(soe.getPrice().multiply(soe.getDiscountRate()));
                } else {
                    ie.setDiscountAmount(BigDecimal.ZERO);
                }
                if (soe.getItem().getWeight() != null && soe.getQuantity() != null) {
                    ie.setWeight(soe.getItem().getWeight().multiply(soe.getQuantity()));
                }
                ie.setSerialNumbers(soe.getSerialNumbers());
                ie.setCost(soe.getCost());
                ie.setDropShippedFlag(soe.getShippedFlag());
                ie.setShippedFlag(soe.getShippedFlag());
                if (soe.getItem() != null && soe.getItem().getCategory() != null && soe.getItem().getCategory().getName() != null) {
                    ie.setCategoryName(soe.getItem().getCategory().getName());
                }
                ie.setItemLookUpCode(soe.getItem().getItemLookUpCode());
                ie.setItemDescription(getItemDescription(soe.getItem()));
                ie.setLineNote(soe.getLineNote());
                ie.setStore(Config.getStore());
                ie.setQuantity(soe.getQuantity());
                ie.setInvoice(invoice);
                ie.setTaxable(soe.getTaxable());
                ie.setTaxAmount(soe.getTaxAmount());
                ie.setCommissionAmount(soe.getCommissionAmount());
                ie.setReturnCode(soe.getReturnCode());
                if (!(ie.getComponentFlag() != null && ie.getComponentFlag()) && ie.getPrice() != null && ie.getCost() != null && ie.getQuantity() != null) {
                    cost = cost.add(ie.getCost().multiply(ie.getQuantity()));
                }
                if (soe.getItem().getCategory() != null && soe.getItem().getCategory().getTaxClass() != null) {
                    ie.setTaxClassName(soe.getItem().getCategory().getTaxClass().getName());
                }
                invoice.getInvoiceEntries().add(ie);
            }
            invoice.setCost(cost);
        }
        return invoice;
    }

    private void saveSalesOrder() {
        if (fService != null) {
            if (fService.getId() == null) {
                daoService.insert(fService);
            } else {
                daoService.update(fService);
            }
            if (daoService.getErrorMessage() != null) {
                showAlertDialog(daoService.getErrorMessage());
                return;
            }
            fSalesOrder.setService(fService);
        }
        if (fSalesOrder.getId() == null) {
            daoSalesOrder.insert(fSalesOrder);
        } else {
            daoSalesOrder.update(fSalesOrder);
        }
        if (daoSalesOrder.getErrorMessage() != null) {
            showAlertDialog(daoSalesOrder.getErrorMessage());
        } else {
            fSalesOrder.getSalesOrderEntries().forEach(e -> {
                if (e.getSerialNumbers() != null && !e.getSerialNumbers().isEmpty()) {
                    e.getSerialNumbers().forEach(s -> {
                        s.setSalesOrderEntry(e);
                        daoSerialNumber.update(s);
                    });
                }
            });
        }
    }

    private void printSalesOrder() {
        getParent().handleAction(AppConstants.ACTION_CLOSE_DIALOG);
        Platform.runLater(() -> {
            SalesOrderLayout salesOrderLayout = new SalesOrderLayout(fSalesOrder);
            if (newSalesOrder) {
                if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_ORDER) {
                    try {
                        JasperReportBuilder report = salesOrderLayout.build();
                        if (Config.getStore().getOrderCount() != null) {
                            for (int i = 0; i < Config.getStore().getOrderCount(); i++) {
                                report.print(false);
                            }
                        } else {
                            report.print(true);
                        }
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_QUOTE) {
                    try {
                        JasperReportBuilder report = salesOrderLayout.build();
                        if (Config.getStore().getQuoteCount() != null && Config.getStore().getQuoteCount() > 1) {
                            for (int i = 0; i < Config.getStore().getQuoteCount(); i++) {
                                report.print(false);
                            }
                        } else {
                            report.print(true);
                        }
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                if (fSalesOrder.getType() == DBConstants.TYPE_SALESORDER_SERVICE) {
                    try {
                        JasperReportBuilder report = salesOrderLayout.build();
                        if (Config.getStore().getServiceCount() != null) {
                            for (int i = 0; i < Config.getStore().getServiceCount(); i++) {
                                report.print(false);
                            }
                        } else {
                            report.print(true);
                        }
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                try {
                    JasperReportBuilder report = salesOrderLayout.build();
                    report.print(true);
                } catch (DRException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        });
    }
}

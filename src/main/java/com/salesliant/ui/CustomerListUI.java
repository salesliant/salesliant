package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerBuyer_;
import com.salesliant.entity.CustomerNote;
import com.salesliant.entity.CustomerNote_;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.CustomerShipTo_;
import com.salesliant.entity.Customer_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.PostCode;
import com.salesliant.entity.PostCode_;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SalesOrderEntry_;
import com.salesliant.entity.SalesOrder_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.util.AddressFactory;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.dateCell;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CustomerSearchField;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.widget.CountryWidget;
import com.salesliant.widget.CustomerGroupWidget;
import com.salesliant.widget.CustomerTermWidget;
import com.salesliant.widget.CustomerTypeWidget;
import com.salesliant.widget.ItemPriceLevelWidget;
import com.salesliant.widget.SalesRepWidget;
import com.salesliant.widget.TaxZoneWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class CustomerListUI extends BaseListUI<Customer> {

    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);
    private final BaseDao<CustomerShipTo> daoCustomerShipTo = new BaseDao<>(CustomerShipTo.class);
    private final BaseDao<CustomerBuyer> daoCustomerBuyer = new BaseDao<>(CustomerBuyer.class);
    private final BaseDao<CustomerNote> daoCustomerNote = new BaseDao<>(CustomerNote.class);
    private final BaseDao<PostCode> daoPostCode = new BaseDao<>(PostCode.class);
    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final DataUI customerUI = new DataUI(Customer.class);
    private final DataUI shipToUI = new DataUI(CustomerShipTo.class);
    private final DataUI buyerUI = new DataUI(CustomerBuyer.class);
    private final DataUI noteUI = new DataUI(CustomerNote.class);
    private final TableView<CustomerBuyer> fCustomerBuyerTable = new TableView<>();
    private final TableView<CustomerNote> fCustomerNoteTable = new TableView<>();
    private final TableView<CustomerShipTo> fCustomerShipToTable = new TableView<>();
    private final TableView<Invoice> fInvoiceTable = new TableView<>();
    private final TableView<InvoiceEntry> fInvoiceEntryTable = new TableView<>();
    private final TableView<SalesOrder> fSalesOrderTable = new TableView<>();
    private final TableView<SalesOrderEntry> fSalesOrderEntryTable = new TableView<>();
    private CustomerShipTo fCustomerShipTo;
    private SalesOrder fSalesOrder;
    private CustomerNote fCustomerNote;
    private CustomerBuyer fCustomerBuyer;
    private SalesOrderUI fSalesOrderUI;
    private ObservableList<CustomerBuyer> fCustomerBuyerList;
    private ObservableList<CustomerNote> fCustomerNoteList;
    private ObservableList<CustomerShipTo> fCustomerShipToList;
    private ObservableList<Invoice> fInvoiceList;
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private ObservableList<SalesOrder> fSalesOrderList;
    private ObservableList<SalesOrderEntry> fSalesOrderEntryList;
    private final GridPane fEditPane;
    private final GridPane fShipToEditPane;
    private final GridPane fCustomerBuyerEditPane;
    private final GridPane fNoteEditPane;
    private final GridPane fAccountNumberEditPane;
    private final TaxZoneWidget fTaxZoneCombo = new TaxZoneWidget();
    private final TaxZoneWidget fCustomerShipToTaxZoneCombo = new TaxZoneWidget();
    private final CustomerTermWidget fTermCombo = new CustomerTermWidget();
    private final CustomerTypeWidget fCustomerTypeCombo = new CustomerTypeWidget();
    private final ItemPriceLevelWidget fPriceLevelCombo = new ItemPriceLevelWidget();
    private final CustomerGroupWidget fCustomerGroupCombo = new CustomerGroupWidget();
    private final CountryWidget fCustomerCountryCombo = new CountryWidget();
    private final CountryWidget fCustomerShipToCountryCombo = new CountryWidget();
    private final Label shipToWarning = new Label("");
    private final Label buyerWarning = new Label("");
    private final Label noteWarning = new Label("");
    private final TabPane fTabPane = new TabPane();
    private final TextArea fAddressArea = new TextArea(), fInfo = new TextArea(), fAccount = new TextArea(), fOther = new TextArea();
    private final static String CUSTOMER_TITLE = "Customer";
    private final static String ACCOUNT_NUMBER_TITLE = "Account Number";
    private final static String CUSTOMER_SHIP_TO_TITLE = "Customer Ship To";
    private final static String CUSTOMER_SHIP_TO_ADD = "CustomerShipTo_Add";
    private final static String CUSTOMER_SHIP_TO_EDIT = "CustomerShipTo_Edit";
    private final static String CUSTOMER_SHIP_TO_DELETE = "CustomerShipTo_Delete";
    private final static String BUYER_ADD = "CustomerBuyer_Add";
    private final static String BUYER_EDIT = "CustomerBuyer_Edit";
    private final static String BUYER_DELETE = "CustomerBuyer_Delete";
    private final static String BUYER_TITLE = "Customer Buyer";
    private final static String CUSTOMER_NOTE_ADD = "CustomerNote_Add";
    private final static String CUSTOMER_NOTE_EDIT = "CustomerNote_Edit";
    private final static String CUSTOMER_NOTE_DELETE = "CustomerNote_Delete";
    private final static String CUSTOMER_NOTE_TITLE = "Note";
    private final static String ADD_CUSTOMER = "Add Customer";
    public final Button closeBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
    public final Button selectUIBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT);
    public HBox actionButtonBox = new HBox();
    public ChoiceBox<String> choiceBox;
    public Button processButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, "New Transaction", fHandler);
    private int fOrderType;
    private String fAccountNumber = "";
    private PostCode fPostCode;
    private final TextField fAccountNumberField = new TextField(), fPostCodeField = new TextField();
    private final CustomerSearchField searchField = new CustomerSearchField();
    private static final Logger LOGGER = Logger.getLogger(CustomerListUI.class.getName());

    public CustomerListUI() {
        actionButtonBox = createButtonGroup();
        mainView = createMainView();
        fEditPane = createEditPane();
        loadData();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Customer> observable, Customer newValue, Customer oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                updateInfoPane(fEntity);
                List<CustomerBuyer> buyerList = fEntity.getCustomerBuyers();
                fCustomerBuyerList = FXCollections.observableList(buyerList);
                fCustomerBuyerTable.setItems(fCustomerBuyerList);
                List<CustomerShipTo> shipToList = fEntity.getCustomerShipTos();
                fCustomerShipToList = FXCollections.observableList(shipToList);
                fCustomerShipToTable.setItems(fCustomerShipToList);
                List<CustomerNote> noteList = fEntity.getCustomerNotes();
                fCustomerNoteList = FXCollections.observableList(noteList);
                fCustomerNoteTable.setItems(fCustomerNoteList);
                List<Invoice> invoiceList = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.customerAccountNumber, fEntity.getAccountNumber(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC)
                        .stream()
                        .sorted((e1, e2) -> e2.getDateInvoiced().compareTo(e1.getDateInvoiced()))
                        .collect(Collectors.toList());
                fInvoiceList = FXCollections.observableList(invoiceList);
                fInvoiceTable.setItems(fInvoiceList);
                if (fInvoiceEntryList != null && !fInvoiceEntryList.isEmpty()) {
                    fInvoiceEntryList.clear();
                }
                List<SalesOrder> salesOrderList = fEntity.getSalesOrders()
                        .stream()
                        .sorted((e1, e2) -> e2.getDateOrdered().compareTo(e1.getDateOrdered()))
                        .collect(Collectors.toList());
                fSalesOrderList = FXCollections.observableList(salesOrderList);
                fSalesOrderTable.setItems(fSalesOrderList);
                if (fSalesOrderEntryList != null && !fSalesOrderEntryList.isEmpty()) {
                    fSalesOrderEntryList.clear();
                }
                selectUIBtn.setVisible(true);
            } else {
                fCustomerBuyerList.clear();
                fCustomerShipToList.clear();
                fCustomerNoteList.clear();
                fInvoiceList.clear();
                fSalesOrderList.clear();
                updateInfoPane(null);
                selectUIBtn.setVisible(false);
            }
        });
        fInvoiceTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Invoice> observable, Invoice newValue, Invoice oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                Invoice aInvoice = fInvoiceTable.getSelectionModel().getSelectedItem();
                List<InvoiceEntry> invoiceEntryList = aInvoice.getInvoiceEntries().stream()
                        .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                        .collect(Collectors.toList());
                fInvoiceEntryList = FXCollections.observableList(invoiceEntryList);
                fInvoiceEntryTable.setItems(fInvoiceEntryList);
            } else {
                fInvoiceEntryTable.setItems(null);
            }
        });
        fSalesOrderTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends SalesOrder> observable, SalesOrder newValue, SalesOrder oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                SalesOrder aSalesOrder = fSalesOrderTable.getSelectionModel().getSelectedItem();
                List<SalesOrderEntry> salesOrderEntryList = aSalesOrder.getSalesOrderEntries().stream()
                        .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                        .collect(Collectors.toList());
                fSalesOrderEntryList = FXCollections.observableList(salesOrderEntryList);
                fSalesOrderEntryTable.setItems(fSalesOrderEntryList);
            } else {
                fInvoiceEntryTable.setItems(null);
            }
        });
        fTabPane.getSelectionModel().selectFirst();
        fTabPane.getStyleClass().add("border");
        fShipToEditPane = createShipToEditPane();
        fCustomerBuyerEditPane = createCustomerBuyerEditPane();
        fNoteEditPane = createNoteEditPane();
        fAccountNumberEditPane = createAccountNumberPane();
        Platform.runLater(() -> searchField.requestFocus());

    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Customer> list = daoCustomer.read1or2(Customer_.store, Config.getStore(), Customer_.globalCustomer, true).stream()
                    .filter(e -> !e.getAccountNumber().equalsIgnoreCase("pos"))
                    .sorted((e1, e2) -> {
                        String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "")
                                + (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                                + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                        String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "")
                                + (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                                + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                        return name1.compareToIgnoreCase(name2);
                    })
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fAccountNumberField.setText("");
                fPostCodeField.setText("");
                if (Config.isAutoCustomerNumberGeneration()) {
                    String account = String.format("%09d", Config.getNumber(DBConstants.SEQ_CUSTOMER_NUMBER));
                    fAccountNumberField.setText(account);
//                    Random generator = new Random();
//                    fAccountNumberField.setText(String.format("%09d", generator.nextInt(1000000000)));
                }
                fInputDialog = createSaveCancelUIDialog(fAccountNumberEditPane, ACCOUNT_NUMBER_TITLE);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fPostCode = daoPostCode.find(PostCode_.postCode, fPostCodeField.getText().trim());
                    fAccountNumber = fAccountNumberField.getText().trim();
                    Customer c = daoCustomer.find(Customer_.accountNumber, fAccountNumber, Customer_.store, Config.getStore());
                    if (c == null) {
                        handleAction(ADD_CUSTOMER);
                    } else {
                        lblWarning1.setText("Account number existed");
                        event.consume();
                    }

                });
                Platform.runLater(() -> fAccountNumberField.requestFocus());
                fInputDialog.showDialog();
                break;
            case ADD_CUSTOMER:
                fEntity = new Customer();
                fEntity.setSales(Config.getEmployee());
                fEntity.setStore(Config.getStore());
                fEntity.setCreditLimit(BigDecimal.ZERO);
                fEntity.setAddToEmailListTag(Boolean.FALSE);
                fEntity.setAllowPartialShipFlag(Boolean.FALSE);
                fEntity.setLayawayCustomer(Boolean.FALSE);
                fEntity.setLimitPurchase(Boolean.FALSE);
                fEntity.setTaxExempt(Boolean.FALSE);
                if (Config.getDefaultGlobalCustomer()) {
                    fEntity.setGlobalCustomer(Boolean.TRUE);
                } else {
                    fEntity.setGlobalCustomer(Boolean.FALSE);
                }
                if (!Config.isAutoCustomerNumberGeneration() && fAccountNumber.substring(0, 1).matches("[0-9]")) {
                    fAccountNumber = fAccountNumber.replaceFirst("^1+(?!$)", "");
                    fAccountNumber = fAccountNumber.replaceAll("[^0-9]", "");
                    fEntity.setAccountNumber(fAccountNumber);
                    String phoneNumber = fAccountNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                    fEntity.setPhoneNumber(phoneNumber);
                } else {
                    fEntity.setAccountNumber(fAccountNumber);
                }
                if (fPostCode != null) {
                    fEntity.setCity(fPostCode.getCity());
                    fEntity.setState(fPostCode.getState());
                    fEntity.setCountry(fPostCode.getCountry());
                    fEntity.setPostCode(fPostCode.getPostCode());
                }
                if (fEntity.getCountry() == null) {
                    fEntity.setCountry(Config.getStore().getCountry());
                }
                try {
                    customerUI.setData(fEntity);
                    fTermCombo.getSelectionModel().selectFirst();
                    fTaxZoneCombo.getSelectionModel().selectFirst();
                    fCustomerTypeCombo.getSelectionModel().selectFirst();
                    fPriceLevelCombo.getSelectionModel().selectFirst();
                    if (!fCustomerGroupCombo.getItems().isEmpty()) {
                        BigDecimal minDiscount = BigDecimal.ONE;
                        for (int i = 0; i < fCustomerGroupCombo.getItems().size(); i++) {
                            if (fCustomerGroupCombo.getItems().get(i).getDiscount().compareTo(minDiscount) <= 0) {
                                minDiscount = fCustomerGroupCombo.getItems().get(i).getDiscount();
                            }
                        }
                        if (minDiscount.compareTo(BigDecimal.ONE) != 0) {
                            for (int i = 0; i < fCustomerGroupCombo.getItems().size(); i++) {
                                if (fCustomerGroupCombo.getItems().get(i).getDiscount().compareTo(minDiscount) == 0) {
                                    fCustomerGroupCombo.getSelectionModel().select(i);
                                }
                            }
                        }
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, CUSTOMER_TITLE);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        customerUI.getData(fEntity);
                        fEntity.setDateCreated(new Date());
                        daoCustomer.insert(fEntity);
                        if (daoCustomer.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                            fTableView.scrollTo(fEntity);
                        } else {
                            lblWarning.setText(daoCustomer.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> customerUI.getTextField(Customer_.firstName).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        customerUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, CUSTOMER_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            customerUI.getData(fEntity);
                            daoCustomer.update(fEntity);
                            if (daoCustomer.getErrorMessage() == null) {
                                fTableView.refresh();
                                updateInfoPane(fEntity);
                            } else {
                                lblWarning.setText(daoCustomer.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> customerUI.getTextField(Customer_.firstName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_PROCESS:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fSalesOrder = new SalesOrder();
                    fSalesOrder.setStore(Config.getStore());
                    fSalesOrder.setCustomer(fEntity);
                    if (fEntity.getTaxZone() != null) {
                        fSalesOrder.setTaxZone(fEntity.getTaxZone());
                    } else {
                        fSalesOrder.setTaxZone(Config.getTaxZone());
                    }
                    if (fEntity.getTaxExempt() != null && fEntity.getTaxExempt()) {
                        fSalesOrder.setTaxExemptFlag(Boolean.TRUE);
                    } else {
                        fSalesOrder.setTaxExemptFlag(Boolean.FALSE);
                    }
                    fSalesOrder.setSales(Config.getEmployee());
                    fSalesOrder.setStation(Config.getStation());
                    String title = "";
                    if (fOrderType == DBConstants.TYPE_SALESORDER_INVOICE) {
                        fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_INVOICE_NUMBER));
                        fSalesOrder.setType(DBConstants.TYPE_SALESORDER_INVOICE);
                        title = "Invoice";
                    } else if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
                        fSalesOrder.setType(DBConstants.TYPE_SALESORDER_ORDER);
                        fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
                        title = "Order";
                    } else if (fOrderType == DBConstants.TYPE_SALESORDER_SERVICE) {
                        fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_SERVICE_ORDER_NUMBER));
                        fSalesOrder.setType(DBConstants.TYPE_SALESORDER_SERVICE);
                        title = "Service";
                    } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
                        fSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
                        fSalesOrder.setType(DBConstants.TYPE_SALESORDER_QUOTE);
                        title = "Quote";
                    }
                    fSalesOrderUI = new SalesOrderUI(fSalesOrder);
                    fSalesOrderUI.setParent(this);
                    fInputDialog = createUIDialog(fSalesOrderUI.getView(), title);
                    choiceBox.getSelectionModel().selectFirst();
                    Platform.runLater(() -> {
                        fSalesOrderUI.getTableView().requestFocus();
                        fSalesOrderUI.getTableView().getSelectionModel().select(0);
                        fSalesOrderUI.getTableView().getFocusModel().focus(0, (TableColumn) fSalesOrderUI.getTableView().getColumns().get(1));
                        fSalesOrderUI.getTableView().edit(0, (TableColumn) fSalesOrderUI.getTableView().getColumns().get(0));
                    });
                    fInputDialog.showDialog();
                } else {
                    showAlertDialog("Please select a customer first!");
                    choiceBox.getSelectionModel().selectFirst();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (fEntity.getAccountReceivables().isEmpty() && fEntity.getAppointments().isEmpty() && fEntity.getCheques().isEmpty() && fEntity.getDeposits().isEmpty()
                            && fEntity.getSalesOrders().isEmpty() && fEntity.getReturnTransactions().isEmpty() && fEntity.getPurchaseOrders().isEmpty()) {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            daoCustomer.delete(fEntity);
                            fEntityList.remove(fEntity);
                            if (fEntityList.isEmpty()) {
                                fTableView.getSelectionModel().select(null);
                            }
                        });
                    } else {
                        showAlertDialog("There are transaction related to this customer. You can't delete this customer!");
                    }

                }
                break;
            case BUYER_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fCustomerBuyer = new CustomerBuyer();
                    fCustomerBuyer.setStore(Config.getStore());
                    fCustomerBuyer.setCustomer(fTableView.getSelectionModel().getSelectedItem());
                    try {
                        buyerUI.setData(fCustomerBuyer);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fCustomerBuyerEditPane, BUYER_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            buyerUI.getData(fCustomerBuyer);
                            fCustomerBuyer.setDateCreated(new Date());
                            daoCustomerBuyer.insert(fCustomerBuyer);
                            if (daoCustomerBuyer.getErrorMessage() == null) {
                                fCustomerBuyerList.add(fCustomerBuyer);
                                fCustomerBuyerTable.getSelectionModel().select(fCustomerBuyer);
                                fCustomerBuyerTable.scrollTo(fCustomerBuyer);
                            } else {
                                buyerWarning.setText(daoCustomerBuyer.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> buyerUI.getTextField(CustomerBuyer_.firstName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case BUYER_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fCustomerBuyerTable.getSelectionModel().getSelectedItem() != null) {
                    fCustomerBuyer = fCustomerBuyerTable.getSelectionModel().getSelectedItem();
                    try {
                        buyerUI.setData(fCustomerBuyer);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fCustomerBuyerEditPane, BUYER_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            buyerUI.getData(fCustomerBuyer);
                            daoCustomerBuyer.update(fCustomerBuyer);
                            if (daoCustomerBuyer.getErrorMessage() == null) {
                                fCustomerBuyerTable.refresh();
                            } else {
                                buyerWarning.setText(daoCustomerBuyer.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> buyerUI.getTextField(CustomerBuyer_.firstName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case BUYER_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fCustomerBuyer = (CustomerBuyer) fCustomerBuyerTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerBuyer.delete(fCustomerBuyer);
                        fCustomerBuyerList.remove(fCustomerBuyer);
                        if (fCustomerBuyerList.isEmpty()) {
                            fCustomerBuyerTable.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case CUSTOMER_SHIP_TO_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fCustomerShipTo = new CustomerShipTo();
                    fCustomerShipTo.setStore(Config.getStore());
                    fCustomerShipTo.setCustomer(fTableView.getSelectionModel().getSelectedItem());
                    fCustomerShipTo.setTaxZone(Config.getTaxZone());
                    fCustomerShipTo.setCountry(Config.getStore().getCountry());
                    try {
                        shipToUI.setData(fCustomerShipTo);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fShipToEditPane, CUSTOMER_SHIP_TO_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            shipToUI.getData(fCustomerShipTo);
                            daoCustomerShipTo.insert(fCustomerShipTo);
                            if (daoCustomerShipTo.getErrorMessage() == null) {
                                fCustomerShipToList.add(fCustomerShipTo);
                                fCustomerShipToTable.getSelectionModel().select(fCustomerShipTo);
                                fCustomerShipToTable.scrollTo(fCustomerShipTo);
                            } else {
                                shipToWarning.setText(daoCustomerShipTo.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> shipToUI.getTextField(CustomerShipTo_.contactName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case CUSTOMER_SHIP_TO_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fCustomerShipToTable.getSelectionModel().getSelectedItem() != null) {
                    fCustomerShipTo = fCustomerShipToTable.getSelectionModel().getSelectedItem();
                    try {
                        shipToUI.setData(fCustomerShipTo);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fShipToEditPane, CUSTOMER_SHIP_TO_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            shipToUI.getData(fCustomerShipTo);
                            daoCustomerShipTo.update(fCustomerShipTo);
                            if (daoCustomerShipTo.getErrorMessage() == null) {
                                fCustomerShipToTable.refresh();
                            } else {
                                shipToWarning.setText(daoCustomerShipTo.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> shipToUI.getTextField(CustomerShipTo_.contactName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case CUSTOMER_SHIP_TO_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fCustomerShipTo = (CustomerShipTo) fCustomerShipToTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerShipTo.delete(fCustomerShipTo);
                        fCustomerShipToList.remove(fCustomerShipTo);
                        if (fCustomerShipToList.isEmpty()) {
                            fCustomerShipToTable.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case CUSTOMER_NOTE_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fCustomerNote = new CustomerNote();
                    fCustomerNote.setStore(Config.getStore());
                    fCustomerNote.setCustomer(fTableView.getSelectionModel().getSelectedItem());
                    try {
                        noteUI.setData(fCustomerNote);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNoteEditPane, CUSTOMER_NOTE_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            noteUI.getData(fCustomerNote);
                            fCustomerNote.setDateCreated(new Date());
                            fCustomerNote.setLastUpdated(new Date());
                            daoCustomerNote.insert(fCustomerNote);
                            if (daoCustomerNote.getErrorMessage() == null) {
                                fCustomerNoteList.add(fCustomerNote);
                                fCustomerNoteTable.getSelectionModel().select(fCustomerNote);
                                fCustomerNoteTable.scrollTo(fCustomerNote);
                            } else {
                                noteWarning.setText(daoCustomerNote.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> noteUI.getTextField(CustomerNote_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case CUSTOMER_NOTE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fCustomerNoteTable.getSelectionModel().getSelectedItem() != null) {
                    fCustomerNote = fCustomerNoteTable.getSelectionModel().getSelectedItem();
                    try {
                        noteUI.setData(fCustomerNote);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNoteEditPane, CUSTOMER_NOTE_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            noteUI.getData(fCustomerNote);
                            fCustomerNote.setLastUpdated(new Date());
                            daoCustomerNote.update(fCustomerNote);
                            if (daoCustomerNote.getErrorMessage() == null) {
                                fCustomerShipToTable.refresh();
                            } else {
                                noteWarning.setText(daoCustomerNote.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> noteUI.getTextField(CustomerNote_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case CUSTOMER_NOTE_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fCustomerNote = (CustomerNote) fCustomerNoteTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerNote.delete(fCustomerNote);
                        fCustomerNoteList.remove(fCustomerNote);
                        if (fCustomerNoteList.isEmpty()) {
                            fCustomerNoteTable.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case AppConstants.ACTION_VOID:
                fInputDialog.close();
                choiceBox.getSelectionModel().selectFirst();
                break;
            case AppConstants.ACTION_CLOSE_DIALOG:
                fInputDialog.close();
                choiceBox.getSelectionModel().selectFirst();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Customer, String> accountNumberCol = new TableColumn<>("Account");
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.accountNumber.getName()));
        accountNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountNumberCol.setPrefWidth(100);

        TableColumn<Customer, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.firstName.getName()));
        firstNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        firstNameCol.setPrefWidth(115);

        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.lastName.getName()));
        lastNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lastNameCol.setPrefWidth(115);

        TableColumn<Customer, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((CellDataFeatures<Customer, String> p) -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getCompany());
            } else {
                return null;
            }
        });
        companyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyCol.setPrefWidth(265);

        TableColumn<Customer, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER));
        phoneNumberCol.setPrefWidth(95);

        TableColumn<Customer, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.city.getName()));
        cityCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cityCol.setPrefWidth(130);

        TableColumn<Customer, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.state.getName()));
        stateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        stateCol.setPrefWidth(100);

        fTableView.getColumns().add(accountNumberCol);
        fTableView.getColumns().add(firstNameCol);
        fTableView.getColumns().add(lastNameCol);
        fTableView.getColumns().add(companyCol);
        fTableView.getColumns().add(phoneNumberCol);
        fTableView.getColumns().add(cityCol);
        fTableView.getColumns().add(stateCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(createInfoPane(), 0, 3, 2, 1);
        mainPane.add(actionButtonBox, 1, 4);
        return mainPane;
    }

    private HBox createButtonGroup() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        choiceBox = new ChoiceBox(FXCollections.observableArrayList("New Transaction", "New Invoice", "New Order", "New Service Order", "New Quote"));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            switch (newValue.intValue()) {
                case 1:
                    fOrderType = DBConstants.TYPE_SALESORDER_INVOICE;
                    handleAction(AppConstants.ACTION_PROCESS);
                    break;
                case 2:
                    fOrderType = DBConstants.TYPE_SALESORDER_ORDER;
                    handleAction(AppConstants.ACTION_PROCESS);
                    break;
                case 3:
                    fOrderType = DBConstants.TYPE_SALESORDER_SERVICE;
                    handleAction(AppConstants.ACTION_PROCESS);
                    break;
                case 4:
                    fOrderType = DBConstants.TYPE_SALESORDER_QUOTE;
                    handleAction(AppConstants.ACTION_PROCESS);
                    break;
            }
        });
        processButton.setPrefWidth(125);
        HBox buttonGroup = new HBox();
        Region filler = new Region();
        filler.setPrefWidth(20);
        buttonGroup.getChildren().addAll(choiceBox, filler, newButton, editButton, deleteButton, closeBtn);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");
        customerUI.setUIComponent(Customer_.country, fCustomerCountryCombo);
        add(leftPane, "Account Number: ", customerUI.createTextField(Customer_.accountNumber, 180), fListener, 0);
        add(leftPane, "First Name: ", customerUI.createTextField(Customer_.firstName, 180), fListener, 1);
        add(leftPane, "Last Name: ", customerUI.createTextField(Customer_.lastName, 180), fListener, 2);
        add(leftPane, "Company: ", customerUI.createTextField(Customer_.company, 180), fListener, 3);
        add(leftPane, "Post Code: ", customerUI.createTextField(Customer_.postCode), fListener, 4);
        add(leftPane, "Address 1: ", customerUI.createTextField(Customer_.address1, 180), fListener, 5);
        add(leftPane, "Address 2: ", customerUI.createTextField(Customer_.address2, 180), fListener, 6);
        add(leftPane, "City: ", customerUI.createTextField(Customer_.city, 180), fListener, 7);
        add(leftPane, "State: ", customerUI.createTextField(Customer_.state, 180), fListener, 8);
        add(leftPane, "Country: ", fCustomerCountryCombo, fListener, 9);
        add(leftPane, "Phone Number: ", customerUI.createTextField(Customer_.phoneNumber), fListener, 10);
        add(leftPane, "Cell Number: ", customerUI.createTextField(Customer_.cellPhoneNumber), fListener, 11);
        add(leftPane, "Fax Number: ", customerUI.createTextField(Customer_.faxNumber), fListener, 12);
        add(leftPane, "Email Address: ", customerUI.createTextField(Customer_.emailAddress, 180), fListener, 13);
        ((TextField) customerUI.getUIComponent(Customer_.accountNumber)).setEditable(false);
        customerUI.getTextField(Customer_.phoneNumber).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    String txt = node.getText().replaceFirst("^1+(?!$)", "");
                    if (txt.length() == 10) {
                        String phone = txt.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                        node.setText(phone);
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        customerUI.getTextField(Customer_.postCode).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    PostCode postCode = daoPostCode.find(PostCode_.postCode, node.getText().trim());
                    if (postCode != null) {
                        customerUI.getTextField(Customer_.city).setText(postCode.getCity());
                        customerUI.getTextField(Customer_.state).setText(postCode.getState());
                        fCustomerCountryCombo.setSelectedCountry(postCode.getCountry());
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");
        fTaxZoneCombo.setPrefWidth(180);
        customerUI.setUIComponent(Customer_.taxZone, fTaxZoneCombo);
        fCustomerTypeCombo.setPrefWidth(160);
        customerUI.setUIComponent(Customer_.customerType, fCustomerTypeCombo);
        fPriceLevelCombo.setPrefWidth(160);
        customerUI.setUIComponent(Customer_.priceLevel, fPriceLevelCombo);
        fCustomerGroupCombo.setPrefWidth(160);
        customerUI.setUIComponent(Customer_.customerGroup, fCustomerGroupCombo);
        fTermCombo.setPrefWidth(160);
        customerUI.setUIComponent(Customer_.customerTerm, fTermCombo);
        SalesRepWidget salesRepCombo = new SalesRepWidget();
        salesRepCombo.setPrefWidth(160);
        customerUI.setUIComponent(Customer_.sales, salesRepCombo);

        add(rightPane, "Sales Rep: ", salesRepCombo, fListener, 1);
        add(rightPane, "Price Level: ", fPriceLevelCombo, fListener, 2);
        add(rightPane, "Cutomer Type: ", fCustomerTypeCombo, fListener, 3);
        add(rightPane, "Cutomer Group: ", fCustomerGroupCombo, fListener, 4);
        add(rightPane, "Customer Terms: ", fTermCombo, fListener, 5);
        add(rightPane, "Credit Limit: ", customerUI.createTextField(Customer_.creditLimit, 180), fListener, 6);
        add(rightPane, customerUI.createCheckBox(Customer_.globalCustomer), "Global Customer?", fListener, 7);
        add(rightPane, customerUI.createCheckBox(Customer_.addToEmailListTag), "Add to Email List?", fListener, 8);
        add(rightPane, customerUI.createCheckBox(Customer_.limitPurchase), "Limit Purchase?", fListener, 9);
        add(rightPane, customerUI.createCheckBox(Customer_.layawayCustomer), "Layaway Customer?", fListener, 10);
        add(rightPane, customerUI.createCheckBox(Customer_.assessFinanceCharges), "Finance Charges?", fListener, 11);
        add(rightPane, customerUI.createCheckBox(Customer_.taxExempt), "Tax Exempt?", fListener, 12);
        add(rightPane, "Tax Zone: ", fTaxZoneCombo, fListener, 13);
        add(rightPane, "Tax ID: ", customerUI.createTextField(Customer_.taxNumber, 80), fListener, 14);

        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(((CheckBox) customerUI.getUIComponent(Customer_.taxExempt)).selectedProperty());
            }

            @Override
            protected boolean computeValue() {
                customerUI.getTextField(Customer_.taxNumber).setText("");
                return (!((CheckBox) customerUI.getUIComponent(Customer_.taxExempt)).isSelected());
            }
        };
        ((TextField) customerUI.getUIComponent(Customer_.taxNumber)).disableProperty().bind(bb);
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        editPane.add(leftPane, 0, 0);
        editPane.add(rightPane, 1, 0);
        editPane.add(lblWarning, 0, 1, 2, 2);

        return editPane;
    }

    private Node createInfoPane() {
        Tab accountTab = new Tab(" Info ");
        accountTab.setContent(createDetailPane());
        accountTab.setClosable(false);
        fTabPane.getTabs().add(accountTab);

        Tab shipToTab = new Tab(" Ship To ");
        shipToTab.setContent(createShipToPane());
        shipToTab.setClosable(false);
        fTabPane.getTabs().add(shipToTab);

        Tab buyerTab = new Tab(" Buyers ");
        buyerTab.setContent(createCustomerBuyerPane());
        buyerTab.setClosable(false);
        fTabPane.getTabs().add(buyerTab);

        Tab salesOrderTab = new Tab(" Pending Transactions ");
        salesOrderTab.setContent(createSalesOrderPane());
        salesOrderTab.setClosable(false);
        fTabPane.getTabs().add(salesOrderTab);

        Tab historyTab = new Tab(" Purchase History ");
        historyTab.setContent(createHistoryPane());
        historyTab.setClosable(false);
        fTabPane.getTabs().add(historyTab);

        Tab noteTab = new Tab(" Notes ");
        noteTab.setContent(createNotePane());
        noteTab.setClosable(false);
        fTabPane.getTabs().add(noteTab);

        fTabPane.setPrefHeight(200);

        return fTabPane;
    }

    private Node createDetailPane() {
        VBox addressBox = new VBox();
        Label addressLabel = new Label("Address");
        fAddressArea.setEditable(false);
        fAddressArea.setPrefSize(220, 135);
        addressBox.getChildren().addAll(addressLabel, fAddressArea);

        VBox accountBox = new VBox();
        Label accountLabel = new Label("Account");
        fInfo.setEditable(false);
        fInfo.setPrefSize(220, 135);
        accountBox.getChildren().addAll(accountLabel, fInfo);

        VBox financialBox = new VBox();
        Label financialLabel = new Label("Financial");
        fAccount.setEditable(false);
        fAccount.setPrefSize(220, 135);
        financialBox.getChildren().addAll(financialLabel, fAccount);

        VBox otherBox = new VBox();
        Label otherLabel = new Label("Other");
        fOther.setEditable(false);
        fOther.setPrefSize(220, 135);
        otherBox.getChildren().addAll(otherLabel, fOther);

        HBox result = new HBox();
        result.setSpacing(10);
        result.getChildren().addAll(addressBox, accountBox, financialBox, otherBox);
        result.setAlignment(Pos.CENTER);
        result.getStyleClass().add("hboxPane");

        return result;
    }

    private Node createShipToPane() {
        GridPane shipToPane = new GridPane();
        shipToPane.setPadding(new Insets(5));
        shipToPane.setHgap(5);
        shipToPane.setVgap(5);
        shipToPane.setAlignment(Pos.CENTER);

        TableColumn<CustomerShipTo, String> contactNameCol = new TableColumn<>("Contact Name");
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.contactName.getName()));
        contactNameCol.setCellFactory(stringCell(Pos.CENTER));
        contactNameCol.setPrefWidth(150);

        TableColumn<CustomerShipTo, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.address1.getName()));
        addressCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        addressCol.setPrefWidth(300);

        TableColumn<CustomerShipTo, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.city.getName()));
        cityCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cityCol.setPrefWidth(150);

        TableColumn<CustomerShipTo, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.state.getName()));
        stateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        stateCol.setPrefWidth(100);

        TableColumn<CustomerShipTo, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory((CellDataFeatures<CustomerShipTo, String> p) -> {
            if (p.getValue() != null && p.getValue().getCountry() != null) {
                return new SimpleStringProperty(p.getValue().getCountry().getIsoCode3());
            } else {
                return null;
            }
        });
        countryCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        countryCol.setPrefWidth(100);

        TableColumn<CustomerShipTo, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.phoneNumber.getName()));
        phoneCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneCol.setPrefWidth(100);

        fCustomerShipToTable.getColumns().add(contactNameCol);
        fCustomerShipToTable.getColumns().add(addressCol);
        fCustomerShipToTable.getColumns().add(cityCol);
        fCustomerShipToTable.getColumns().add(stateCol);
        fCustomerShipToTable.getColumns().add(countryCol);
        fCustomerShipToTable.getColumns().add(phoneCol);
        fCustomerShipToTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fCustomerShipToTable);
        fCustomerShipToTable.setPrefHeight(120);

        shipToPane.add(fCustomerShipToTable, 0, 0);
        shipToPane.add(createNewEditDeleteButtonPane(CUSTOMER_SHIP_TO_ADD, CUSTOMER_SHIP_TO_EDIT, CUSTOMER_SHIP_TO_DELETE), 0, 1);
        return shipToPane;
    }

    private GridPane createShipToEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        fCustomerShipToTaxZoneCombo.setPrefWidth(180);
        shipToUI.setUIComponent(CustomerShipTo_.taxZone, fCustomerShipToTaxZoneCombo);
        shipToUI.setUIComponent(CustomerShipTo_.country, fCustomerShipToCountryCombo);
        add(editPane, "Contact Name: ", shipToUI.createTextField(CustomerShipTo_.contactName, 180), fListener, 0);
        add(editPane, "Phone Number: ", shipToUI.createTextField(CustomerShipTo_.phoneNumber), fListener, 2);
        add(editPane, "Email Address: ", shipToUI.createTextField(CustomerShipTo_.emailAddress, 180), fListener, 4);
        add(editPane, "Company: ", shipToUI.createTextField(CustomerShipTo_.company, 180), fListener, 5);
        add(editPane, "Post Code: ", shipToUI.createTextField(CustomerShipTo_.postCode), fListener, 6);
        add(editPane, "Address 1: ", shipToUI.createTextField(CustomerShipTo_.address1, 180), fListener, 7);
        add(editPane, "Address 2: ", shipToUI.createTextField(CustomerShipTo_.address2, 180), fListener, 8);
        add(editPane, "City: ", shipToUI.createTextField(CustomerShipTo_.city, 180), fListener, 9);
        add(editPane, "State: ", shipToUI.createTextField(CustomerShipTo_.state, 180), fListener, 10);
        add(editPane, "Country: ", fCustomerShipToCountryCombo, fListener, 11);
        add(editPane, "Tax Zone: ", fCustomerShipToTaxZoneCombo, fListener, 12);
        add(editPane, shipToUI.createCheckBox(CustomerShipTo_.taxExempt), "Tax Exempt?", fListener, 13);
        add(editPane, "Tax ID: ", shipToUI.createTextField(CustomerShipTo_.taxNumber, 80), fListener, 14);

        editPane.add(shipToWarning, 0, 15, 2, 1);
        shipToUI.getTextField(CustomerShipTo_.phoneNumber).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    String txt = node.getText().replaceFirst("^1+(?!$)", "");
                    if (txt.length() == 10) {
                        String phone = txt.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                        node.setText(phone);
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        shipToUI.getTextField(CustomerShipTo_.postCode).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    PostCode postCode = daoPostCode.find(PostCode_.postCode, node.getText().trim());
                    if (postCode != null) {
                        shipToUI.getTextField(CustomerShipTo_.city).setText(postCode.getCity());
                        shipToUI.getTextField(CustomerShipTo_.state).setText(postCode.getState());
                        fCustomerShipToCountryCombo.setSelectedCountry(postCode.getCountry());
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        return editPane;
    }

    private Node createCustomerBuyerPane() {
        GridPane buyerPane = new GridPane();
        buyerPane.setPadding(new Insets(5));
        buyerPane.setHgap(5);
        buyerPane.setVgap(5);
        buyerPane.setAlignment(Pos.CENTER);
        buyerPane.getStyleClass().add("hboxPane");

        TableColumn<CustomerBuyer, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.firstName.getName()));
        firstNameCol.setPrefWidth(200);

        TableColumn<CustomerBuyer, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.lastName.getName()));
        lastNameCol.setPrefWidth(200);

        TableColumn<CustomerBuyer, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.phoneNumber.getName()));
        phoneNumberCol.setPrefWidth(200);

        TableColumn<CustomerBuyer, String> emailAddressCol = new TableColumn<>("Email");
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.emailAddress.getName()));
        emailAddressCol.setPrefWidth(300);

        fCustomerBuyerTable.getColumns().add(firstNameCol);
        fCustomerBuyerTable.getColumns().add(lastNameCol);
        fCustomerBuyerTable.getColumns().add(phoneNumberCol);
        fCustomerBuyerTable.getColumns().add(emailAddressCol);
        fCustomerBuyerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fCustomerBuyerTable);
        fCustomerBuyerTable.setPrefHeight(135);

        buyerPane.add(fCustomerBuyerTable, 0, 0);
        buyerPane.add(createNewEditDeleteButtonPane(BUYER_ADD, BUYER_EDIT, BUYER_DELETE), 0, 1);
        return buyerPane;
    }

    private GridPane createCustomerBuyerEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "First Name: ", buyerUI.createTextField(CustomerBuyer_.firstName, 180), fListener, 1);
        add(editPane, "Last Name: ", buyerUI.createTextField(CustomerBuyer_.lastName, 180), fListener, 2);
        add(editPane, "Title: ", buyerUI.createTextField(CustomerBuyer_.title, 180), fListener, 3);
        add(editPane, "Phone Number: ", buyerUI.createTextField(CustomerBuyer_.phoneNumber), fListener, 4);
        add(editPane, "Cell Number: ", buyerUI.createTextField(CustomerBuyer_.cellPhoneNumber), fListener, 5);
        add(editPane, "Fax Number: ", buyerUI.createTextField(CustomerBuyer_.faxNumber), fListener, 6);
        add(editPane, "Email Address: ", buyerUI.createTextField(CustomerBuyer_.emailAddress, 180), fListener, 7);
        editPane.add(buyerWarning, 0, 8, 2, 1);
        buyerUI.getTextField(CustomerBuyer_.phoneNumber).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    String txt = node.getText().replaceFirst("^1+(?!$)", "");
                    if (txt.length() == 10) {
                        String phone = txt.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                        node.setText(phone);
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        return editPane;
    }

    private Node createSalesOrderPane() {
        GridPane salesOrderPane = new GridPane();
        salesOrderPane.setPadding(new Insets(2));
        salesOrderPane.setHgap(5);
        salesOrderPane.setVgap(1);
        salesOrderPane.setAlignment(Pos.TOP_CENTER);
        salesOrderPane.getStyleClass().add("hboxPane");

        TableColumn<SalesOrder, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrder, String> p) -> {
            String type = "";
            if (p.getValue() != null) {
                if (p.getValue().getType().equals(DBConstants.TYPE_SALESORDER_QUOTE)) {
                    type = "Quote";
                } else if (p.getValue().getType().equals(DBConstants.TYPE_SALESORDER_ORDER)) {
                    type = "Order";
                } else if (p.getValue().getType().equals(DBConstants.TYPE_SALESORDER_SERVICE)) {
                    type = "Service";
                } else if (p.getValue().getType().equals(DBConstants.TYPE_SALESORDER_INVOICE)) {
                    type = "Invoice";
                }
            }
            return new SimpleStringProperty(type);
        });
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        typeCol.setPrefWidth(60);

        TableColumn<SalesOrder, String> salesOrderNumberCol = new TableColumn<>("Txn Number");
        salesOrderNumberCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrder_.salesOrderNumber.getName()));
        salesOrderNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        salesOrderNumberCol.setPrefWidth(85);

        TableColumn<SalesOrder, Date> dateOrderedCol = new TableColumn<>("Date");
        dateOrderedCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrder_.dateOrdered.getName()));
        dateOrderedCol.setCellFactory(dateCell(Pos.CENTER));
        dateOrderedCol.setPrefWidth(75);

        TableColumn<SalesOrder, BigDecimal> orderTotalCol = new TableColumn<>("Total");
        orderTotalCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrder_.total.getName()));
        orderTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        orderTotalCol.setPrefWidth(80);

        fSalesOrderTable.getColumns().add(typeCol);
        fSalesOrderTable.getColumns().add(salesOrderNumberCol);
        fSalesOrderTable.getColumns().add(dateOrderedCol);
        fSalesOrderTable.getColumns().add(orderTotalCol);
        fSalesOrderTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fSalesOrderTable.setPrefHeight(135);
        setTableWidth(fSalesOrderTable);

        TableColumn<SalesOrderEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((TableColumn.CellDataFeatures<SalesOrderEntry, String> p) -> {
            String sku = "";
            if (p.getValue() != null && p.getValue().getItem() != null) {
                sku = p.getValue().getItem().getItemLookUpCode();
            }
            return new SimpleStringProperty(sku);
        });
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.CENTER));

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
        detailDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        detailDescriptionCol.setMinWidth(220);

        TableColumn<SalesOrderEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory(new PropertyValueFactory<>(SalesOrderEntry_.quantity.getName()));
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(70);

        TableColumn<SalesOrderEntry, BigDecimal> detailPriceCol = new TableColumn<>("Price");
        detailPriceCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
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
        detailPriceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailPriceCol.setMinWidth(90);

        TableColumn<SalesOrderEntry, BigDecimal> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((CellDataFeatures<SalesOrderEntry, BigDecimal> p) -> {
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
        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER));

        fSalesOrderEntryTable.getColumns().add(skuCol);
        fSalesOrderEntryTable.getColumns().add(detailDescriptionCol);
        fSalesOrderEntryTable.getColumns().add(detailQtyCol);
        fSalesOrderEntryTable.getColumns().add(detailPriceCol);
        fSalesOrderEntryTable.getColumns().add(detailTotalCol);
        fSalesOrderEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fSalesOrderEntryTable.setPrefHeight(125);
        setTableWidth(fSalesOrderEntryTable);
        Label salesOrderLbl = new Label("Sales Order:");
        Label salesOrderDetailLbl = new Label("Sales Order Detail:");
        GridPane.setHalignment(salesOrderLbl, HPos.LEFT);
        GridPane.setHalignment(salesOrderDetailLbl, HPos.LEFT);
        salesOrderPane.add(salesOrderLbl, 0, 0);
        salesOrderPane.add(fSalesOrderTable, 0, 1);
        salesOrderPane.add(salesOrderDetailLbl, 1, 0);
        salesOrderPane.add(fSalesOrderEntryTable, 1, 1);
        return salesOrderPane;
    }

    private Node createHistoryPane() {
        GridPane historyPane = new GridPane();
        historyPane.setPadding(new Insets(2));
        historyPane.setHgap(5);
        historyPane.setVgap(1);
        historyPane.setAlignment(Pos.TOP_CENTER);
        historyPane.getStyleClass().add("hboxPane");

        TableColumn<Invoice, String> invoiceNumberCol = new TableColumn<>("Invoice");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(100);
        TableColumn<Invoice, Date> dateInvoicedCol = new TableColumn<>("Date");
        dateInvoicedCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.dateInvoiced.getName()));
        dateInvoicedCol.setCellFactory(dateCell(Pos.CENTER));
        dateInvoicedCol.setPrefWidth(100);
        TableColumn<Invoice, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(100);
        fInvoiceTable.getColumns().add(invoiceNumberCol);
        fInvoiceTable.getColumns().add(dateInvoicedCol);
        fInvoiceTable.getColumns().add(totalCol);
        fInvoiceTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fInvoiceTable.setPrefHeight(135);
        setTableWidth(fInvoiceTable);

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.CENTER));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                return new SimpleStringProperty(p.getValue().getItemDescription());
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        detailDescriptionCol.setMinWidth(220);

        TableColumn<InvoiceEntry, String> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getQuantity()));
            }
        });
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(70);

        TableColumn<InvoiceEntry, String> detailPriceCol = new TableColumn<>("Price");
        detailPriceCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getPrice()));
            }
        });
        detailPriceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailPriceCol.setMinWidth(90);

        TableColumn<InvoiceEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                BigDecimal total;
                if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                    total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return null;
                }
            }

        });
        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER));

        fInvoiceEntryTable.getColumns().add(skuCol);
        fInvoiceEntryTable.getColumns().add(detailDescriptionCol);
        fInvoiceEntryTable.getColumns().add(detailQtyCol);
        fInvoiceEntryTable.getColumns().add(detailPriceCol);
        fInvoiceEntryTable.getColumns().add(detailTotalCol);
        fInvoiceEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fInvoiceEntryTable.setPrefHeight(125);
        setTableWidth(fInvoiceEntryTable);
        Label invoiceLbl = new Label("Invoice:");
        Label invoiceDetailLbl = new Label("Invoice Detail:");
        GridPane.setHalignment(invoiceLbl, HPos.LEFT);
        GridPane.setHalignment(invoiceDetailLbl, HPos.LEFT);
        historyPane.add(invoiceLbl, 0, 0);
        historyPane.add(fInvoiceTable, 0, 1);
        historyPane.add(invoiceDetailLbl, 1, 0);
        historyPane.add(fInvoiceEntryTable, 1, 1);
        return historyPane;
    }

    private Node createNotePane() {
        GridPane notePane = new GridPane();
        notePane.setPadding(new Insets(5));
        notePane.setHgap(5);
        notePane.setVgap(5);
        notePane.setAlignment(Pos.CENTER);
        notePane.getStyleClass().add("hboxPane");

        TableColumn<CustomerNote, Date> dateCreatedCol = new TableColumn<>("Date Created");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(CustomerNote_.dateCreated.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER));
        dateCreatedCol.setPrefWidth(150);

        TableColumn<CustomerNote, Date> lastUpdatedCol = new TableColumn<>("Last Updated");
        lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>(CustomerNote_.lastUpdated.getName()));
        lastUpdatedCol.setCellFactory(stringCell(Pos.CENTER));
        lastUpdatedCol.setPrefWidth(150);

        TableColumn<CustomerNote, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(CustomerNote_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(600);

        fCustomerNoteTable.getColumns().add(dateCreatedCol);
        fCustomerNoteTable.getColumns().add(lastUpdatedCol);
        fCustomerNoteTable.getColumns().add(descriptionCol);
        fCustomerNoteTable.setPrefHeight(135);
        setTableWidth(fCustomerNoteTable);

        notePane.add(fCustomerNoteTable, 0, 0);
        notePane.add(createNewEditDeleteButtonPane(CUSTOMER_NOTE_ADD, CUSTOMER_NOTE_EDIT, CUSTOMER_NOTE_DELETE), 0, 1);
        return notePane;
    }

    private GridPane createNoteEditPane() {
        GridPane notePane = new GridPane();
        notePane.getStyleClass().add("editView");

        add(notePane, "Description:", noteUI.createTextField(CustomerNote_.description, 250), fListener, 0);
        add(notePane, "Note:", noteUI.createTextArea(CustomerNote_.note), 150, 250, fListener, 1);
        notePane.add(noteWarning, 0, 2, 2, 1);
        return notePane;
    }

    private GridPane createAccountNumberPane() {
        GridPane phoneNumberPane = new GridPane();
        phoneNumberPane.getStyleClass().add("editView");
        add(phoneNumberPane, "Account Number:", fAccountNumberField, fListener, 150, 0);
        add(phoneNumberPane, "Post Code:", fPostCodeField, fListener, 150, 1);
        fAccountNumberField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        fPostCodeField.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        phoneNumberPane.add(lblWarning1, 0, 2, 2, 2);
        GridPane.setHalignment(lblWarning1, HPos.RIGHT);
        return phoneNumberPane;
    }

    private void updateInfoPane(Customer customer) {
        if (customer == null) {
            fAddressArea.setText("");
            fInfo.setText("");
            fAccount.setText("");
            fOther.setText("");
        } else {
            fAddressArea.setText(AddressFactory.getCustomerAddress(customer, AddressFactory.STYLE_SHORT));
            String info = "";
            info = info + addToString(customer.getFirstName() + " " + customer.getLastName(), "Name: ");
            info = info + addToString(customer.getAccountNumber(), "Account Number: ");
            if (customer.getSales() != null) {
                info = info + addToString(customer.getSales().getFirstName() + " " + customer.getSales().getLastName(), "Sales Rep: ");
            } else {
                info = info + addToString("", "Sales Rep: ");
            }
            if (customer.getTaxZone() != null && customer.getTaxZone().getName() != null) {
                info = info + addToString(customer.getTaxZone().getName(), "Tax Zone: ");
            }
            info = info + addToString(customer.getTaxNumber(), "Tax Number: ");
            fInfo.setText(info.trim());
            String acc = "";
            if (customer.getCustomerTerm() != null) {
                acc = acc + addToString(customer.getCustomerTerm().getCode(), "Term: ");
            } else {
                acc = acc + addToString("", "Term: ");
            }
            BigDecimal accoutBalance = BigDecimal.ZERO;
            BigDecimal creditAmount = BigDecimal.ZERO;
            BigDecimal depositAmount = BigDecimal.ZERO;
            if (customer.getDeposits() != null && !customer.getDeposits().isEmpty()) {
                depositAmount = customer.getDeposits().stream()
                        .filter(e -> e.getStatus() == DBConstants.STATUS_OPEN)
                        .map(e -> e.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
            }
            if (customer.getAccountReceivables() != null && !customer.getAccountReceivables().isEmpty()) {
                List<AccountReceivable> arList = customer.getAccountReceivables().stream()
                        .filter(e -> e.getStatus() == DBConstants.STATUS_OPEN)
                        .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE)
                        || e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                        .collect(Collectors.toList());
                BigDecimal totatDebitAmount = arList
                        .stream()
                        .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE))
                        .map(e -> e.getBalanceAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totatCreditAmount = arList
                        .stream()
                        .filter(e -> e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT))
                        .map(e -> e.getBalanceAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                accoutBalance = totatDebitAmount.subtract(totatCreditAmount);
                creditAmount = totatCreditAmount;
            }
            acc = acc + addToString(customer.getCreditLimit(), "Credit Limit: ");
            acc = acc + addToString(creditAmount, "Credit Amount: ");
            acc = acc + addToString(depositAmount, "Deposit Amount: ");
            acc = acc + addToString(accoutBalance, "Account Balance: ");
            if (customer.getAssessFinanceCharges() != null && customer.getAssessFinanceCharges() == true) {
                acc = acc + addToString("Yes", "Apply Financial Charge: ");
            } else {
                acc = acc + addToString("No", "Apply Financial Charge: ");
            }
            fAccount.setText(acc.trim());
            String other = "";
            other = other + addToString(customer.getDateCreated(), "Date Created: ");
            other = other + addToString(customer.getLastVisit(), "Last Visit: ");
            if (customer.getLayawayCustomer() != null && customer.getGlobalCustomer() == true) {
                other = other + addToString("Yes", "Global Customer: ");
            } else {
                other = other + addToString("No", "Global Customer: ");
            }
            if (customer.getLimitPurchase() != null && customer.getLimitPurchase() == true) {
                other = other + addToString("Yes", "Limit Purchase: ");
            } else {
                other = other + addToString("No", "Limit Purchase: ");
            }
            fOther.setText(other.trim());
        }
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            if (fInputDialog.getTitle() == null ? CUSTOMER_TITLE == null : fInputDialog.getTitle().equals(CUSTOMER_TITLE)) {
                customerUI.getTextField(Customer_.firstName).setText(uppercaseFirst(customerUI.getTextField(Customer_.firstName).getText()));
                customerUI.getTextField(Customer_.lastName).setText(uppercaseFirst(customerUI.getTextField(Customer_.lastName).getText()));
                customerUI.getTextField(Customer_.company).setText(uppercaseFirst(customerUI.getTextField(Customer_.company).getText()));
                customerUI.getTextField(Customer_.address1).setText(uppercaseFirst(customerUI.getTextField(Customer_.address1).getText()));
                customerUI.getTextField(Customer_.address2).setText(uppercaseFirst(customerUI.getTextField(Customer_.address2).getText()));
                customerUI.getTextField(Customer_.city).setText(uppercaseFirst(customerUI.getTextField(Customer_.city).getText()));
                customerUI.getTextField(Customer_.state).setText(uppercaseFirst(customerUI.getTextField(Customer_.state).getText()));
                saveBtn.setDisable(customerUI.getTextField(Customer_.accountNumber).getText().trim().isEmpty());
            } else if (fInputDialog.getTitle() == null ? BUYER_TITLE == null : fInputDialog.getTitle().equals(BUYER_TITLE)) {
                buyerUI.getTextField(CustomerBuyer_.firstName).setText(uppercaseFirst(buyerUI.getTextField(CustomerBuyer_.firstName).getText()));
                buyerUI.getTextField(CustomerBuyer_.lastName).setText(uppercaseFirst(buyerUI.getTextField(CustomerBuyer_.lastName).getText()));
                buyerUI.getTextField(CustomerBuyer_.title).setText(uppercaseFirst(buyerUI.getTextField(CustomerBuyer_.title).getText()));
                saveBtn.setDisable(false);
            } else if (fInputDialog.getTitle() == null ? CUSTOMER_NOTE_TITLE == null : fInputDialog.getTitle().equals(CUSTOMER_NOTE_TITLE)) {
                saveBtn.setDisable(false);
            } else if (fInputDialog.getTitle() == null ? CUSTOMER_SHIP_TO_TITLE == null : fInputDialog.getTitle().equals(CUSTOMER_SHIP_TO_TITLE)) {
                shipToUI.getTextField(CustomerShipTo_.contactName).setText(uppercaseFirst(shipToUI.getTextField(CustomerShipTo_.contactName).getText()));
                shipToUI.getTextField(CustomerShipTo_.company).setText(uppercaseFirst(shipToUI.getTextField(CustomerShipTo_.company).getText()));
                shipToUI.getTextField(CustomerShipTo_.address1).setText(uppercaseFirst(shipToUI.getTextField(CustomerShipTo_.address1).getText()));
                shipToUI.getTextField(CustomerShipTo_.address2).setText(uppercaseFirst(shipToUI.getTextField(CustomerShipTo_.address2).getText()));
                shipToUI.getTextField(CustomerShipTo_.city).setText(uppercaseFirst(shipToUI.getTextField(CustomerShipTo_.city).getText()));
                shipToUI.getTextField(CustomerShipTo_.state).setText(uppercaseFirst(shipToUI.getTextField(CustomerShipTo_.state).getText()));
                saveBtn.setDisable(shipToUI.getTextField(CustomerShipTo_.contactName).getText().trim().isEmpty() || shipToUI.getTextField(CustomerShipTo_.address1).getText().trim().isEmpty()
                        || shipToUI.getTextField(CustomerShipTo_.city).getText().trim().isEmpty() || shipToUI.getTextField(CustomerShipTo_.postCode).getText().trim().isEmpty());
            } else if (fInputDialog.getTitle() == null ? ACCOUNT_NUMBER_TITLE == null : fInputDialog.getTitle().equals(ACCOUNT_NUMBER_TITLE)) {
                saveBtn.setDisable(fAccountNumberField.getText().trim().isEmpty() || fPostCodeField.getText().trim().isEmpty());
            }
        }
    }
}

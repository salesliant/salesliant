/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.client.ClientView;
import com.salesliant.client.Config;
import com.salesliant.entity.AccountPayableBatch;
import com.salesliant.entity.AccountPayableBatch_;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Batch_;
import com.salesliant.entity.Category;
import com.salesliant.entity.Country;
import com.salesliant.entity.Currency;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerTerm;
import com.salesliant.entity.Customer_;
import com.salesliant.entity.Employee;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.EmployeeGroup_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrder_;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction_;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrder_;
import com.salesliant.entity.Seq;
import com.salesliant.entity.Seq_;
import com.salesliant.entity.Station;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store_;
import com.salesliant.entity.TaxClass;
import com.salesliant.entity.TaxZone;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrder_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.ProgressDialog;
import com.salesliant.validator.InvalidException;
import com.salesliant.validator.StoreValidator;
import com.salesliant.widget.CountryWidget;
import com.salesliant.widget.CurrencyWidget;
import com.salesliant.widget.CustomerTermWidget;
import com.salesliant.widget.TaxClassWidget;
import com.salesliant.widget.TaxZoneWidget;
import com.salesliant.widget.ZoneWidget;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.binding.BooleanBinding;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Lewis
 */
public class StoreUI extends BaseListUI<Store> {

    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);
    private final BaseDao<Station> daoStation = new BaseDao<>(Station.class);
    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<Category> daoCategory = new BaseDao<>(Category.class);
    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
    private final BaseDao<EmployeeGroup> daoEmployeeGroup = new BaseDao<>(EmployeeGroup.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);
    private final BaseDao<SalesOrder> daoSalesOrder = new BaseDao<>(SalesOrder.class);
    private final BaseDao<PurchaseOrder> daoPurchaseOrder = new BaseDao<>(PurchaseOrder.class);
    private final BaseDao<TransferOrder> daoTransferOrder = new BaseDao<>(TransferOrder.class);
    private final BaseDao<ReturnTransaction> daoReturnTransaction = new BaseDao<>(ReturnTransaction.class);
    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private final BaseDao<AccountPayableBatch> daoAccountPayableBatch = new BaseDao<>(AccountPayableBatch.class);
    private final BaseDao<Seq> daoSeq = new BaseDao<>(Seq.class);
    private final BaseDao<TaxZone> daoTaxZone = new BaseDao<>(TaxZone.class);
    private final BaseDao<TaxClass> daoTaxClass = new BaseDao<>(TaxClass.class);
    private final BaseDao<CustomerTerm> daoCustomerTerm = new BaseDao<>(CustomerTerm.class);
    private final BaseDao<ItemPriceLevel> daoItemPriceLevel = new BaseDao<>(ItemPriceLevel.class);
    private final BaseDao<Country> daoCountry = new BaseDao<>(Country.class);
    private final BaseDao<Currency> daoCurrency = new BaseDao<>(Currency.class);
    private List<TaxZone> taxZoneList;
    private List<CustomerTerm> customerTermList;
    private List<TaxClass> taxClassList;
    private List<Country> countryList;
    private List<Currency> currencyList;
    private final DataUI dataUI = new DataUI(Store.class);
    private final TabPane fTabPane = new TabPane();
    private ZoneWidget fZoneComboBox = new ZoneWidget();
    private ComboBox<?> fTaxClassComboBox;
    private ComboBox<?> fTaxZoneComboBox;
    private ComboBox<?> fCountryComboBox;
    private ComboBox<?> fCurrencyComboBox;
    private CustomerTermWidget fCustomerTermComboBox;
    private ComboBox<?> fItemCostTypeComboBox;
    private ComboBox<?> fItemPriceMethodComboBox;
    private int maxInvoice = 1, maxQuote = 1, maxOrder = 1, maxService = 1, maxCustomer = 1, maxPurchase = 1, maxTransfer = 1, maxRMA = 1,
            maxBatch = 1, maxAccountReceivable = 1, maxAccountPayableBatch = 1;
    private StoreValidator validator = new StoreValidator();
    private static final Logger LOGGER = Logger.getLogger(StoreUI.class.getName());

    public StoreUI() {
        taxZoneList = daoTaxZone.read().stream().sorted((e1, e2) -> e1.getId().compareTo(e1.getId())).collect(Collectors.toList());
        taxClassList = daoTaxClass.read().stream().sorted((e1, e2) -> e1.getId().compareTo(e1.getId())).collect(Collectors.toList());
        customerTermList = daoCustomerTerm.read().stream().sorted((e1, e2) -> e1.getId().compareTo(e1.getId())).collect(Collectors.toList());
        countryList = daoCountry.read().stream().sorted((e1, e2) -> e1.getId().compareTo(e1.getId())).collect(Collectors.toList());
        currencyList = daoCurrency.read().stream().sorted((e1, e2) -> e1.getId().compareTo(e1.getId())).collect(Collectors.toList());
        getMaxNumber();
        mainView = createMainView();
        fEntity = Config.getStore();
        if (fEntity != null) {
            try {
                dataUI.setData(fEntity);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        } else {
            fEntity = new Store();
            loadDefault(fEntity);
            try {
                dataUI.setData(fEntity);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
        saveBtn.setDisable(true);
        checkCustomerNumber();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SAVE:
                try {
                dataUI.getData(fEntity);
                validator.validate(fEntity);
                if (fEntity.getId() == null) {
                    daoStore.insert(fEntity);
                    if (daoStore.getErrorMessage() != null) {
                        lblWarning.setText(daoStore.getErrorMessage());
                    } else {
                        if (fEntity.getNextInvoiceNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_INVOICE_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextInvoiceNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextInvoiceNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextOrderNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ORDER_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextOrderNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextOrderNumber());
                                daoSeq.update(seq);
                            }
                        }
                        handleClose();
                        saveStore(fEntity.getStoreCode());
                        ProgressDialog pDialog = new ProgressDialog();
                        Task<Void> task = new Task<Void>() {
                            @Override
                            public Void call() throws Exception {
                                addSeq(fEntity);
                                updateItemQuantity(fEntity);
                                addEmployee(fEntity);
                                updateItemQuantity(fEntity);
                                addCustomer(fEntity);
                                addStation(fEntity);
                                addCategory();
                                return null;
                            }
                        };
                        task.setOnSucceeded(event -> {
                            pDialog.close();
                            Config.setStore(fEntity);
                            showAlertDialog("New store has been setup successful. The following entities have been setup:\n"
                                    + "1. A new employ name salesliant with login name:salesliant, password:salesliant is installed.\n"
                                    + "2. A station(main register) is created.\n"
                                    + "3. Category freight and labor are created.\n"
                                    + "4. A customer called pos is created. This customer is for quick sale purpose.\n\n"
                                    + "Please modify tax zone according to store location and other settings.");
                        });
                        pDialog.activateProgressBar(task);
                        Thread thread = new Thread(task);
                        thread.start();
                    }
                } else {
                    daoStore.update(fEntity);
                    if (daoStore.getErrorMessage() != null) {
                        lblWarning.setText(daoStore.getErrorMessage());
                    } else {
                        if (fEntity.getNextInvoiceNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_INVOICE_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextInvoiceNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextInvoiceNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextOrderNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ORDER_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextOrderNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextOrderNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextQuoteNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_QUOTE_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextQuoteNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextQuoteNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextServiceOrderNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_SERVICE_ORDER_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextServiceOrderNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextServiceOrderNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextPurchaseOrderNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_PURCHASE_ORDER_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextPurchaseOrderNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextPurchaseOrderNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextTransferNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_TRANSFER_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextTransferNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextTransferNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextRmaNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_RMA_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextRmaNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextRmaNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextBatchNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_BATCH_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextBatchNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextBatchNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextAccountReceivableNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextAccountReceivableNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextAccountReceivableNumber());
                                daoSeq.update(seq);
                            }
                        }
                        if (fEntity.getNextAccountPayableBatchNumber() != null) {
                            Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ACCOUNT_PAYABLE_BATCH_NUMBER, Seq_.store, Config.getStore());
                            if (seq != null && seq.getSeqValue() != null && (fEntity.getNextAccountPayableBatchNumber().compareTo(seq.getSeqValue()) > 0)) {
                                seq.setSeqValue(fEntity.getNextAccountPayableBatchNumber());
                                daoSeq.update(seq);
                            }
                        }
                        handleClose();
                    }
                }
            } catch (Exception ex) {
                if (ex instanceof InvalidException) {
                    lblWarning.setText(ex.getMessage());
                } else {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(5));
        mainPane.setHgap(3);
        mainPane.setVgap(5);
        mainPane.setPrefHeight(700);
        Label titleLabel = new Label("Company Setup:");
        mainPane.add(titleLabel, 0, 1);
        GridPane.setHalignment(titleLabel, HPos.LEFT);
        Tab companyTab = new Tab(" Company ");
        companyTab.setContent(createCompanyPane());
        companyTab.setClosable(false);
        fTabPane.getStyleClass().add("border");
        fTabPane.getTabs().add(companyTab);

        Tab orderEntriesTab = new Tab(" Transaction ");
        orderEntriesTab.setContent(createTransactionPane());
        orderEntriesTab.setClosable(false);
        fTabPane.getTabs().add(orderEntriesTab);

        Tab customersTab = new Tab(" Customers ");
        customersTab.setContent(createCustomerPane());
        customersTab.setClosable(false);
        fTabPane.getTabs().add(customersTab);

        Tab inventoryTab = new Tab(" Inventory ");
        inventoryTab.setContent(createInventoryPane());
        inventoryTab.setClosable(false);
        fTabPane.getTabs().add(inventoryTab);

        Tab messageTab = new Tab(" Message ");
        messageTab.setContent(createMessagePane());
        messageTab.setClosable(false);
        fTabPane.getTabs().add(messageTab);

        mainPane.add(fTabPane, 0, 2);
        mainPane.add(lblWarning, 0, 3);
        mainPane.add(createSaveCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private Node createCompanyPane() {
        GridPane companyPane = new GridPane();
        companyPane.getStyleClass().add("editView");
        companyPane.setPadding(new Insets(5));
        companyPane.setHgap(5);
        companyPane.setVgap(3);
        companyPane.setPrefHeight(320);
        companyPane.setAlignment(Pos.CENTER);
        dataUI.setUIComponent(Store_.zone, fZoneComboBox);
        fCountryComboBox = new CountryWidget();
        dataUI.setUIComponent(Store_.country, fCountryComboBox);
        fCurrencyComboBox = new CurrencyWidget();
        dataUI.setUIComponent(Store_.defaultCurrency, fCurrencyComboBox);

        add(companyPane, "Store Code:", dataUI.createTextField(Store_.storeCode, 250), fListener, 1);
        add(companyPane, "Store Name:", dataUI.createTextField(Store_.storeName, 250), fListener, 2);
        add(companyPane, "Address 1:", dataUI.createTextField(Store_.address1, 250), fListener, 3);
        add(companyPane, "Address 2:", dataUI.createTextField(Store_.address2, 250), fListener, 4);
        add(companyPane, "City:", dataUI.createTextField(Store_.city, 250), fListener, 5);
        add(companyPane, "State:", dataUI.createTextField(Store_.state, 170), fListener, 6);
        add(companyPane, "Zip:", dataUI.createTextField(Store_.postCode, 80), fListener, 7);
        add(companyPane, "Country:", fCountryComboBox, 250, fListener, 8);
        add(companyPane, "Currency:", fCurrencyComboBox, 250, fListener, 9);
        add(companyPane, "Zone:", fZoneComboBox, 250, fListener, 10);
        add(companyPane, "Phone:", dataUI.createTextField(Store_.phoneNumber, 125), fListener, 11);
        add(companyPane, "Fax:", dataUI.createTextField(Store_.faxNumber, 125), fListener, 12);
        add(companyPane, "Web Address:", dataUI.createTextField(Store_.webAddress, 250), fListener, 13);

        return companyPane;
    }

    private Node createTransactionPane() {

        GridPane orderEntriesPane = new GridPane();
        orderEntriesPane.getStyleClass().add("editView");
        orderEntriesPane.setPadding(new Insets(5));
        orderEntriesPane.setHgap(5);
        orderEntriesPane.setVgap(3);
        orderEntriesPane.setAlignment(Pos.CENTER);

        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");
        leftPane.setPadding(new Insets(2));
        leftPane.setHgap(5);
        leftPane.setVgap(3);

        add(leftPane, "Next Invoice No.:" + Integer.toString(maxInvoice), dataUI.createTextField(Store_.nextInvoiceNumber, 100), fListener, 1);
        add(leftPane, "Next Order No.:" + Integer.toString(maxOrder), dataUI.createTextField(Store_.nextOrderNumber, 100), fListener, 2);
        add(leftPane, "Next Quote No.:" + Integer.toString(maxQuote), dataUI.createTextField(Store_.nextQuoteNumber, 100), fListener, 3);
        add(leftPane, "Next Service No.:" + Integer.toString(maxService), dataUI.createTextField(Store_.nextServiceOrderNumber, 100), fListener, 4);
        add(leftPane, "Next Purchase Order Number:" + Integer.toString(maxPurchase), dataUI.createTextField(Store_.nextPurchaseOrderNumber, 150), fListener, 5);
        add(leftPane, "Next Transfer Number:" + Integer.toString(maxTransfer), dataUI.createTextField(Store_.nextTransferNumber, 150), fListener, 6);
        add(leftPane, "Next RMA Number:" + Integer.toString(maxRMA), dataUI.createTextField(Store_.nextRmaNumber, 150), fListener, 7);
        add(leftPane, "Next Batch Number:" + Integer.toString(maxBatch), dataUI.createTextField(Store_.nextBatchNumber, 150), fListener, 8);
        add(leftPane, "Next Account Receivable Number:" + Integer.toString(maxAccountReceivable), dataUI.createTextField(Store_.nextAccountReceivableNumber, 150), fListener, 9);
        add(leftPane, "Next Account Payable Batch Number:" + Integer.toString(maxAccountPayableBatch), dataUI.createTextField(Store_.nextAccountPayableBatchNumber, 150), fListener, 10);

        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");
        rightPane.setPadding(new Insets(2));
        rightPane.setHgap(5);
        rightPane.setVgap(7);

        add(rightPane, "Order Due Days:", dataUI.createTextField(Store_.orderDueDays, 100), fListener, 0);
        add(rightPane, "Quote Expiration Days:", dataUI.createTextField(Store_.quoteExpirationDays, 100), fListener, 1);
        add(rightPane, "Service Order Due Days:", dataUI.createTextField(Store_.serviceOrderDueDays, 100), fListener, 2);
        add(rightPane, "Internet Order Due Days:", dataUI.createTextField(Store_.internetOrderDueDays, 100), fListener, 3);
        add(rightPane, "Order Deposit %:", dataUI.createTextField(Store_.orderDeposit, 100), fListener, 5);
        add(rightPane, "Invoice Print Count:", dataUI.createTextField(Store_.invoiceCount, 100), fListener, 8);
        add(rightPane, "Order Print Count:", dataUI.createTextField(Store_.orderCount, 100), fListener, 9);
        add(rightPane, "Service Print Count:", dataUI.createTextField(Store_.serviceCount, 100), fListener, 10);
        add(rightPane, "Quote Print Count:", dataUI.createTextField(Store_.quoteCount, 100), fListener, 11);
        add(rightPane, dataUI.createCheckBox(Store_.allowZeroQtySale), "Allow Zero Quantity Sales", fListener, 13);
        add(rightPane, dataUI.createCheckBox(Store_.enableBackOrders), "Enable Back Orders", fListener, 14);
        add(rightPane, dataUI.createCheckBox(Store_.displayOutOfStock), "Display Out of Stock", fListener, 15);

        orderEntriesPane.add(leftPane, 0, 1);
        orderEntriesPane.add(rightPane, 1, 1);
        return orderEntriesPane;
    }

    private Node createCustomerPane() {
        GridPane customerPane = new GridPane();
        customerPane.getStyleClass().add("editView");
        customerPane.setPadding(new Insets(2));
        customerPane.setHgap(5);
        customerPane.setVgap(10);
        customerPane.setAlignment(Pos.CENTER);

        GridPane topPane = new GridPane();
        topPane.getStyleClass().add("editView");
        topPane.setPadding(new Insets(2));
        topPane.setHgap(5);
        topPane.setVgap(3);
        topPane.setAlignment(Pos.CENTER);
        add(topPane, dataUI.createCheckBox(Store_.defaultGlobalCustomer), "Set New Customer As Global Customer", 250, fListener, 0);
        add(topPane, dataUI.createCheckBox(Store_.autoCustomerNumberGeneration), "Auto Customer Number Generation", 250, fListener, 1);

        GridPane defaultPane = new GridPane();
        defaultPane.getStyleClass().add("editView");
        defaultPane.setPadding(new Insets(2));
        defaultPane.setHgap(5);
        defaultPane.setVgap(3);
        fItemPriceMethodComboBox = DBConstants.getComboBoxTypes(DBConstants.TYPE_ITEM_PRICE_METHOD);
        fItemPriceMethodComboBox.setPrefWidth(250);
        dataUI.setUIComponent(Store_.defaultCustomerPriceMethod, fItemPriceMethodComboBox);

        fTaxZoneComboBox = new TaxZoneWidget();
        dataUI.setUIComponent(Store_.defaultTaxZone, fTaxZoneComboBox);
        fCustomerTermComboBox = new CustomerTermWidget();
        dataUI.setUIComponent(Store_.defaultCustomerTerm, fCustomerTermComboBox);
        add(defaultPane, "Customer Price Methods:", fItemPriceMethodComboBox, fListener, 0);
        add(defaultPane, "Default Tax Zone:", fTaxZoneComboBox, 250, fListener, 1);
        add(defaultPane, "Default Customer Term:", fCustomerTermComboBox, 250, fListener, 2);
        add(defaultPane, "Next Customer Number:" + Integer.toString(maxCustomer), dataUI.createTextField(Store_.nextCustomerNumber, 250), fListener, 3);

        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(((CheckBox) dataUI.getUIComponent(Store_.autoCustomerNumberGeneration)).selectedProperty());
            }

            @Override
            protected boolean computeValue() {
                dataUI.getTextField(Store_.nextCustomerNumber).setEditable(false);
                return (!((CheckBox) dataUI.getUIComponent(Store_.autoCustomerNumberGeneration)).isSelected());
            }
        };
        ((TextField) dataUI.getUIComponent(Store_.nextCustomerNumber)).disableProperty().bind(bb);
        customerPane.add(topPane, 0, 0);
        customerPane.add(defaultPane, 0, 1);

        return customerPane;
    }

    private Node createInventoryPane() {
        GridPane inventoryPane = new GridPane();
        inventoryPane.getStyleClass().add("editView");
        inventoryPane.setPadding(new Insets(2));
        inventoryPane.setHgap(5);
        inventoryPane.setVgap(3);
        inventoryPane.setAlignment(Pos.CENTER);

        fItemCostTypeComboBox = DBConstants.getComboBoxTypes(DBConstants.TYPE_ITEMCOST);
        fItemCostTypeComboBox.setPrefWidth(150);
        dataUI.setUIComponent(Store_.defaultItemCostMethod, fItemCostTypeComboBox);
        fTaxClassComboBox = new TaxClassWidget();
        fTaxClassComboBox.setPrefWidth(150);
        dataUI.setUIComponent(Store_.defaultTaxClass, fTaxClassComboBox);

        add(inventoryPane, "Number of Label Per Item:", dataUI.createTextField(Store_.numberOfLabelsPerItem, 150), fListener, 1);
        add(inventoryPane, "Default Tax Class:", fTaxClassComboBox, fListener, 2);
        add(inventoryPane, "Cost Update Methods:", fItemCostTypeComboBox, fListener, 3);
        add(inventoryPane, "Auto SKU Generation", dataUI.createCheckBox(Store_.autoSkuGeneration, fListener), fListener, 4);

        return inventoryPane;
    }

    private Node createMessagePane() {
        GridPane messagePane = new GridPane();
        messagePane.getStyleClass().add("editView");
        messagePane.setPadding(new Insets(2));
        messagePane.setHgap(5);
        messagePane.setVgap(3);
        messagePane.setAlignment(Pos.CENTER);

        add(messagePane, "Sales Order Message:", dataUI.createTextArea(Store_.salesOrderMessage), 150, 700, fListener, 0);
        add(messagePane, "Service Order Message:", dataUI.createTextArea(Store_.serviceOrderMessage), 150, 700, fListener, 1);
        add(messagePane, "Invoice Message:", dataUI.createTextArea(Store_.invoiceMessage), 150, 700, fListener, 2);
        return messagePane;
    }

    private HBox createSaveCloseButtonPane() {
        HBox buttons = new HBox();
        saveBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        buttons.getChildren().addAll(saveBtn, closeButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        return buttons;
    }

    @Override
    protected void validate() {
        if (fEntity != null) {
//            Boolean test = true;
//            if (isInteger(dataUI.getTextField(Store_.nextCustomerNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextCustomerNumber).getText()) >= maxCustomer
//                    && isInteger(dataUI.getTextField(Store_.nextInvoiceNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextInvoiceNumber).getText()) >= maxInvoice
//                    && isInteger(dataUI.getTextField(Store_.nextOrderNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextOrderNumber).getText()) >= maxOrder
//                    && isInteger(dataUI.getTextField(Store_.nextQuoteNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextQuoteNumber).getText()) >= maxQuote
//                    && isInteger(dataUI.getTextField(Store_.nextServiceOrderNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextServiceOrderNumber).getText()) >= maxService
//                    && isInteger(dataUI.getTextField(Store_.nextRmaNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextRmaNumber).getText()) >= maxRMA
//                    && isInteger(dataUI.getTextField(Store_.nextAccountPayableBatchNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextAccountPayableBatchNumber).getText()) >= maxAccountPayableBatch
//                    && isInteger(dataUI.getTextField(Store_.nextAccountReceivableNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextAccountReceivableNumber).getText()) >= maxAccountReceivable
//                    && isInteger(dataUI.getTextField(Store_.nextPurchaseOrderNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextPurchaseOrderNumber).getText()) >= maxPurchase
//                    && isInteger(dataUI.getTextField(Store_.nextTransferNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextTransferNumber).getText()) >= maxTransfer
//                    && isInteger(dataUI.getTextField(Store_.nextBatchNumber).getText()) && Integer.valueOf(dataUI.getTextField(Store_.nextBatchNumber).getText()) >= maxBatch) {
//                test = false;
//            }
//            saveBtn.setDisable(test);
            saveBtn.setDisable(false);
        }
    }

    private void getMaxNumber() {
        if (daoInvoice.findMaxNumber(Invoice_.store, Config.getStore(), Invoice_.invoiceNumber) != null) {
            maxInvoice = daoInvoice.findMaxNumber(Invoice_.store, Config.getStore(), Invoice_.invoiceNumber).intValue();
        }
        Seq seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_INVOICE_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxInvoice) > 0) {
            maxInvoice = seq.getSeqValue();
        }

        if (daoSalesOrder.findMaxNumber(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_QUOTE, SalesOrder_.salesOrderNumber) != null) {
            maxQuote = daoSalesOrder.findMaxNumber(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_QUOTE, SalesOrder_.salesOrderNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_QUOTE_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxQuote) > 0) {
            maxQuote = seq.getSeqValue();
        }

        if (daoSalesOrder.findMaxNumber(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_ORDER, SalesOrder_.salesOrderNumber) != null) {
            maxOrder = daoSalesOrder.findMaxNumber(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_ORDER, SalesOrder_.salesOrderNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ORDER_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxOrder) > 0) {
            maxOrder = seq.getSeqValue();
        }

        if (daoSalesOrder.findMaxNumber(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_SERVICE, SalesOrder_.salesOrderNumber) != null) {
            maxService = daoSalesOrder.findMaxNumber(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_SERVICE, SalesOrder_.salesOrderNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_SERVICE_ORDER_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxService) > 0) {
            maxService = seq.getSeqValue();
        }

        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_CUSTOMER_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxCustomer) > 0) {
            maxCustomer = seq.getSeqValue();
        }

        if (daoPurchaseOrder.findMaxNumber(PurchaseOrder_.store, Config.getStore(), PurchaseOrder_.purchaseOrderNumber) != null) {
            maxPurchase = daoPurchaseOrder.findMaxNumber(PurchaseOrder_.store, Config.getStore(), PurchaseOrder_.purchaseOrderNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_PURCHASE_ORDER_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxPurchase) > 0) {
            maxPurchase = seq.getSeqValue();
        }

        if (daoTransferOrder.findMaxNumber(TransferOrder_.store, Config.getStore(), TransferOrder_.transferOrderNumber) != null) {
            maxTransfer = daoTransferOrder.findMaxNumber(TransferOrder_.store, Config.getStore(), TransferOrder_.transferOrderNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_TRANSFER_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxTransfer) > 0) {
            maxTransfer = seq.getSeqValue();
        }

        if (daoReturnTransaction.findMaxNumber(ReturnTransaction_.store, Config.getStore(), ReturnTransaction_.rmaNumberToCustomer) != null) {
            maxRMA = daoReturnTransaction.findMaxNumber(ReturnTransaction_.store, Config.getStore(), ReturnTransaction_.rmaNumberToCustomer).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_RMA_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxRMA) > 0) {
            maxRMA = seq.getSeqValue();
        }

        if (daoBatch.findMaxNumber(Batch_.store, Config.getStore(), Batch_.batchNumber) != null) {
            maxBatch = daoBatch.findMaxNumber(Batch_.store, Config.getStore(), Batch_.batchNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_BATCH_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxBatch) > 0) {
            maxBatch = seq.getSeqValue();
        }

        if (daoAccountReceivable.findMaxNumber(AccountReceivable_.store, Config.getStore(), AccountReceivable_.accountReceivableNumber) != null) {
            maxAccountReceivable = daoAccountReceivable.findMaxNumber(AccountReceivable_.store, Config.getStore(), AccountReceivable_.accountReceivableNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxAccountReceivable) > 0) {
            maxAccountReceivable = seq.getSeqValue();
        }

        if (daoAccountPayableBatch.findMaxNumber(AccountPayableBatch_.store, Config.getStore(), AccountPayableBatch_.batchNumber) != null) {
            maxAccountPayableBatch = daoAccountPayableBatch.findMaxNumber(AccountPayableBatch_.store, Config.getStore(), AccountPayableBatch_.batchNumber).intValue();
        }
        seq = daoSeq.find(Seq_.seqName, DBConstants.SEQ_ACCOUNT_PAYABLE_BATCH_NUMBER, Seq_.store, Config.getStore());
        if (seq != null && seq.getSeqValue() != null && seq.getSeqValue().compareTo(maxAccountPayableBatch) > 0) {
            maxAccountPayableBatch = seq.getSeqValue();
        }
    }

    private void checkCustomerNumber() {
        if (daoCustomer.findMaxNumber(Customer_.store, Config.getStore(), Customer_.accountNumber) == null) {
            dataUI.getTextField(Store_.nextCustomerNumber).setEditable(false);
            ((CheckBox) dataUI.getUIComponent(Store_.autoCustomerNumberGeneration)).setDisable(true);
        }
    }

    public final void loadDefault(Store store) {
        store.setNextInvoiceNumber(1000);
        store.setNextOrderNumber(1000);
        store.setNextServiceOrderNumber(1000);
        store.setNextQuoteNumber(1000);
        store.setNextCustomerNumber(1000);
        store.setNextPurchaseOrderNumber(1000);
        store.setNextBarcodeNumber(1000);
        store.setNextBatchNumber(1000);
        store.setNextTransferNumber(1000);
        store.setNextAccountReceivableNumber(1000);
        store.setNextAccountPayableBatchNumber(1000);
        store.setNextRmaNumber(1000);

        store.setOrderDueDays(30);
        store.setQuoteExpirationDays(30);
        store.setServiceOrderDueDays(14);
        store.setInternetOrderDueDays(14);
        store.setLayawayExpirationDays(14);
        store.setOrderDeposit(new BigDecimal(15));
        store.setLayawayDeposit(new BigDecimal(15));
        store.setLayawayFee(new BigDecimal(5));
        store.setInvoiceCount(2);
        store.setOrderCount(1);
        store.setQuoteCount(1);
        store.setServiceCount(2);
        store.setSalesOrderMessage("Thank you for your order.\n" + "Special orders are not refundable.");
        store.setInvoiceMessage("15% RESTOCKING FEE ON ALL RETURNED ITEMS; SEE THE LIMITED WARRANTY & RETURN POLICY POSTED IN THE STORE FOR FURTHER DETAILS.\n"
                + "SALES OF SOFTWARE, INCLUDING OPERATING SYSTEMS, ARE FINAL AT THE TIME OF PURCHASE. WE DOES NOT PERMIT RETURNS, EXCHANGES, OR REFUNDS OF ANY KIND ON SOFTWARE PURCHASES.\n"
                + "\n"
                + "CUSTOMERS SIGNATURE:___________________________________________________\n"
                + "\n"
                + "DATE:_________/_________/_________\n"
                + "");
        store.setServiceOrderMessage("Our technicians will make every effort to return your product with all of your software programs, data or information stored on any media intact. HOWEVER, if during the repair, the contents of the hard drive or data storage media are altered, deleted or in any way modified, our technicians are not responsible whatsoever. Please make sure important data is BACKED UP. Property not picked up within 30 days after notification your machine is ready for pick up becomes the our property.\n"
                + "We does not warranty any customer provided parts that we install on your computer.\n"
                + "\n"
                + "CUSTOMER SIGNATURE:_______________________________________________\n"
                + "\n"
                + "DATE:_________/_________/_________");
        store.setCountry(countryList.get(222));
        store.setDefaultCurrency(currencyList.get(0));
        store.setDefaultItemCostMethod(1);
        store.setNumberOfLabelsPerItem(1);
        store.setDefaultTaxZone(taxZoneList.get(0));
        store.setDefaultTaxClass(taxClassList.get(0));
        store.setDefaultCustomerTerm(customerTermList.get(0));
        store.setDefaultCustomerPriceMethod(1);
        store.setRequireSerialNumber(false);
        store.setDisplayOutOfStock(false);
        store.setAllowZeroQtySale(true);
        store.setAutoCustomerNumberGeneration(false);
        store.setAutoSkuGeneration(false);
        store.setEnforceOpenCloseAmount(false);
        store.setEnableBackOrders(false);
        store.setShowFunctionKeysAtPos(true);
        store.setShowAddressAtPos(true);
        store.setEdcTimeOut(true);
        store.setDefaultGlobalCustomer(false);
        store.setZone(32);
    }

    private void saveStore(String storeCode) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("./database.properties");
            config.setProperty("store.code", storeCode);
            config.save();
        } catch (ConfigurationException ex) {
            Logger.getLogger(StoreUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addSeq(Store store) {
        Seq seqCustomerNumber = new Seq();
        seqCustomerNumber.setStore(store);
        seqCustomerNumber.setSeqName("customer_number");
        if (store.getNextCustomerNumber() != null) {
            seqCustomerNumber.setSeqValue(store.getNextCustomerNumber());
        } else {
            seqCustomerNumber.setSeqValue(1);
        }
        daoSeq.insert(seqCustomerNumber);

        Seq seqInvoiceNumber = new Seq();
        seqInvoiceNumber.setStore(store);
        seqInvoiceNumber.setSeqName("invoice_number");
        if (store.getNextInvoiceNumber() != null) {
            seqInvoiceNumber.setSeqValue(store.getNextInvoiceNumber());
        } else {
            seqInvoiceNumber.setSeqValue(1);
        }
        daoSeq.insert(seqInvoiceNumber);

        Seq seqQuoteNumber = new Seq();
        seqQuoteNumber.setStore(store);
        seqQuoteNumber.setSeqName("quote_number");
        if (store.getNextQuoteNumber() != null) {
            seqQuoteNumber.setSeqValue(store.getNextQuoteNumber());
        } else {
            seqQuoteNumber.setSeqValue(1);
        }
        daoSeq.insert(seqQuoteNumber);

        Seq seqOrderNumber = new Seq();
        seqOrderNumber.setStore(store);
        seqOrderNumber.setSeqName("order_number");
        if (store.getNextOrderNumber() != null) {
            seqOrderNumber.setSeqValue(store.getNextOrderNumber());
        } else {
            seqOrderNumber.setSeqValue(1);
        }
        daoSeq.insert(seqOrderNumber);

        Seq seqServiceOrderNumber = new Seq();
        seqServiceOrderNumber.setStore(store);
        seqServiceOrderNumber.setSeqName("service_order_number");
        if (store.getNextServiceOrderNumber() != null) {
            seqServiceOrderNumber.setSeqValue(store.getNextServiceOrderNumber());
        } else {
            seqServiceOrderNumber.setSeqValue(1);
        }
        daoSeq.insert(seqServiceOrderNumber);

        Seq seqPurchaseOrderNumber = new Seq();
        seqPurchaseOrderNumber.setStore(store);
        seqPurchaseOrderNumber.setSeqName("purchase_order_number");
        if (store.getNextPurchaseOrderNumber() != null) {
            seqPurchaseOrderNumber.setSeqValue(store.getNextPurchaseOrderNumber());
        } else {
            seqPurchaseOrderNumber.setSeqValue(1);
        }
        daoSeq.insert(seqPurchaseOrderNumber);

        Seq seqVendorNumber = new Seq();
        seqVendorNumber.setStore(store);
        seqVendorNumber.setSeqName("vendor_number");
        seqVendorNumber.setSeqValue(1);
        daoSeq.insert(seqVendorNumber);

        Seq seqBatchNumber = new Seq();
        seqBatchNumber.setStore(store);
        seqBatchNumber.setSeqName("batch_number");
        if (store.getNextBatchNumber() != null) {
            seqBatchNumber.setSeqValue(store.getNextBatchNumber());
        } else {
            seqBatchNumber.setSeqValue(1);
        }
        daoSeq.insert(seqBatchNumber);

        Seq seqAccountReceivableNumber = new Seq();
        seqAccountReceivableNumber.setStore(store);
        seqAccountReceivableNumber.setSeqName("account_receivable_number");
        if (store.getNextAccountReceivableNumber() != null) {
            seqAccountReceivableNumber.setSeqValue(store.getNextAccountReceivableNumber());
        } else {
            seqAccountReceivableNumber.setSeqValue(1);
        }
        daoSeq.insert(seqAccountReceivableNumber);

        Seq seqAccountPayableBatchNumber = new Seq();
        seqAccountPayableBatchNumber.setStore(store);
        seqAccountPayableBatchNumber.setSeqName("account_payable_batch_number");
        if (store.getNextAccountPayableBatchNumber() != null) {
            seqAccountPayableBatchNumber.setSeqValue(store.getNextAccountPayableBatchNumber());
        } else {
            seqAccountPayableBatchNumber.setSeqValue(1);
        }
        daoSeq.insert(seqAccountPayableBatchNumber);

        Seq seqRMANumber = new Seq();
        seqRMANumber.setStore(store);
        seqRMANumber.setSeqName("rma_number");
        if (store.getNextRmaNumber() != null) {
            seqRMANumber.setSeqValue(store.getNextRmaNumber());
        } else {
            seqRMANumber.setSeqValue(1);
        }
        daoSeq.insert(seqRMANumber);

        Seq seqTransferNumber = new Seq();
        seqTransferNumber.setStore(store);
        seqTransferNumber.setSeqName("transfer_number");
        if (store.getNextTransferNumber() != null) {
            seqTransferNumber.setSeqValue(store.getNextTransferNumber());
        } else {
            seqTransferNumber.setSeqValue(1);
        }
        daoSeq.insert(seqTransferNumber);

        Seq seqAccountReceivableBatchNumber = new Seq();
        seqAccountReceivableBatchNumber.setStore(store);
        seqAccountReceivableBatchNumber.setSeqName("account_receivable_batch_number");
        seqAccountReceivableBatchNumber.setSeqValue(1);
        daoSeq.insert(seqAccountReceivableBatchNumber);
    }

    private void updateItemQuantity(Store store) {
        List<Item> list = daoItem.read().stream()
                .filter(e -> e.getItemQuantities() != null && !e.getItemQuantities().isEmpty())
                .collect(Collectors.toList());
        list.forEach(item -> {
            if (getItemQuantityByStore(item, store) == null) {
                ItemQuantity iq = new ItemQuantity();
                iq.setQuantity(BigDecimal.ZERO);
                iq.setReorderPoint(0);
                iq.setRestockLevel(0);
                iq.setItem(item);
                iq.setStore(store);
                daoItemQuantity.insert(iq);
            }
        });
    }

    private void addEmployee(Store store) {
        if (store.getEmployeeGroups() == null || store.getEmployeeGroups().isEmpty()) {
            ChoiceBox<String> choiceBox = ClientView.fLoader.findChoiceBox();
            String setup = "";
            for (String s : choiceBox.getItems()) {
                if (s.startsWith("Setup")) {
                    setup = s;
                }
            }
            choiceBox.getItems().remove(setup);
            for (String s : choiceBox.getItems()) {
                int i = s.indexOf(' ');
                String code = s.substring(0, i);
                EmployeeGroup eg = new EmployeeGroup();
                eg.setStore(store);
                eg.setCode(code);
                eg.setDescription(code);
                eg.setModuleName(s);
                eg.setFailLogonAttempts(5);
                eg.setFloorLimit(new BigDecimal(5000));
                eg.setReturnLimit(new BigDecimal(5000));
                eg.setSalesRep(Boolean.TRUE);
                eg.setOpenCloseDrawer(Boolean.TRUE);
                eg.setOverridePrices(Boolean.TRUE);
                eg.setBelowMinimumPrices(Boolean.TRUE);
                eg.setAcceptReturns(Boolean.TRUE);
                eg.setAllowPayInOut(Boolean.TRUE);
                eg.setAllowNoSale(Boolean.TRUE);
                eg.setVoidInvoice(Boolean.TRUE);
                eg.setVoidOrder(Boolean.TRUE);
                eg.setVoidInternetOrder(Boolean.TRUE);
                eg.setVoidQuote(Boolean.TRUE);
                eg.setVoidServiceOrder(Boolean.TRUE);
                eg.setEditInvoice(Boolean.TRUE);
                eg.setUnlockRegister(Boolean.TRUE);
                eg.setChangeCustomer(Boolean.TRUE);
                eg.setOverrideTax(Boolean.TRUE);
                eg.setValidDrawer(Boolean.TRUE);
                if (s.equalsIgnoreCase("Sales Module")) {
                    eg.setAllowOpenClose(Boolean.TRUE);
                    eg.setOverrideCreditLimit(Boolean.FALSE);
                    eg.setOverrideCommission(Boolean.FALSE);
                } else if (s.equalsIgnoreCase("Inventory Module")) {
                    eg.setAllowOpenClose(Boolean.FALSE);
                    eg.setOverrideCreditLimit(Boolean.FALSE);
                    eg.setOverrideCommission(Boolean.FALSE);
                } else if (s.equalsIgnoreCase("Accounting Module")) {
                    eg.setAllowOpenClose(Boolean.TRUE);
                    eg.setOverrideCreditLimit(Boolean.TRUE);
                    eg.setOverrideCommission(Boolean.TRUE);
                } else if (s.equalsIgnoreCase("Manager Module")) {
                    eg.setAllowOpenClose(Boolean.TRUE);
                    eg.setOverrideCreditLimit(Boolean.TRUE);
                    eg.setOverrideCommission(Boolean.TRUE);
                }
                daoEmployeeGroup.insert(eg);
            }
        }
        Employee employee = new Employee();
        employee.setFirstName("Salesliant");
        employee.setNameOnSalesOrder("Salesliant");
        employee.setLogin("salesliant");
        employee.setPassword("salesliant");
        List<EmployeeGroup> employeeGroupList = daoEmployeeGroup.read(EmployeeGroup_.store, store);
        for (EmployeeGroup e : employeeGroupList) {
            if (e.getCode().startsWith("Manager")) {
                employee.setEmployeeGroup(e);
            }
        }
        employee.setActiveTag(Boolean.TRUE);
        employee.setStore(store);
        employee.setDateCreated(new Date());
        employee.setCountry(store.getCountry());
        employee.setAddress1(store.getAddress1());
        employee.setAddress2(store.getAddress2());
        employee.setCity(store.getCity());
        employee.setState(store.getState());
        employee.setPostCode(store.getPostCode());
        daoEmployee.insert(employee);
    }

    private void addCustomer(Store store) {
        Customer customer = new Customer();
        customer.setAccountNumber("pos");
        customer.setCustomerType("P");
        customer.setFirstName("Prefered");
        customer.setLastName("Customer");
        customer.setAddress1("No Return Without Receipt");
        customer.setPostCode(store.getPostCode());
        customer.setCity(store.getCity());
        customer.setState(store.getState());
        customer.setCountry(store.getCountry());
        customer.setStore(store);
        customer.setCustomerTerm(store.getDefaultCustomerTerm());
        CustomerTerm ct = null;
        for (CustomerTerm c : customerTermList) {
            if (c.getStoreAccountTag() != null && !c.getStoreAccountTag()) {
                ct = c;
            }
        }
        if (ct != null) {
            customer.setCustomerTerm(ct);
        } else {
            customer.setCustomerTerm(store.getDefaultCustomerTerm());
        }
        customer.setTaxExempt(Boolean.FALSE);
        customer.setTaxZone(taxZoneList.get(0));
        customer.setCreditLimit(BigDecimal.ZERO);
        customer.setAddToEmailListTag(Boolean.FALSE);
        customer.setAllowPartialShipFlag(Boolean.FALSE);
        customer.setLayawayCustomer(Boolean.FALSE);
        customer.setLimitPurchase(Boolean.FALSE);
        customer.setGlobalCustomer(Boolean.FALSE);
        customer.setAssessFinanceCharges(Boolean.FALSE);
        List<ItemPriceLevel> priceLevelList = daoItemPriceLevel.read().stream().sorted((e1, e2) -> e1.getId().compareTo(e1.getId())).collect(Collectors.toList());
        customer.setPriceLevel(priceLevelList.get(0));
        daoCustomer.insert(customer);
    }

    private void addStation(Store store) {
        Station station = new Station();
        station.setNumber(1);
        station.setStore(store);
        station.setDescription("Main Register");
        station.setTransactionRequireLogin(Boolean.FALSE);
        station.setPoleDisplayEnabled(Boolean.FALSE);
        station.setScaleEnabled(Boolean.FALSE);
        station.setNetDisplayEnabled(Boolean.FALSE);
        station.setScaleEnabled(Boolean.FALSE);
        station.setScannerEnabled(Boolean.FALSE);
        station.setCashDraw1Enabled(Boolean.FALSE);
        station.setMicrEnabled(Boolean.FALSE);
        station.setMsrEnabled(Boolean.FALSE);
        station.setTouchSceenEnabled(Boolean.FALSE);
        station.setSignatureCaptureEnabled(Boolean.FALSE);
        station.setCashDraw1WaitForClose(Boolean.FALSE);
        station.setPrinter1Options(1);
        station.setPrinter1Type(0);
        daoStation.insert(station);
        station.setTenderedStation(station);
        daoStation.update(station);
    }

    private void addCategory() {
        List<Category> categorys = daoCategory.read();
        Optional<Category> findFreight = categorys
                .stream().parallel()
                .filter(e -> e.getName().equalsIgnoreCase("FREIGHT")).findFirst();
        Optional<Category> findLabor = categorys
                .stream().parallel()
                .filter(e -> e.getName().equalsIgnoreCase("LABOR")).findFirst();
        if (!findFreight.isPresent()) {
            Category category = new Category();
            category.setName("FREIGHT");
            category.setTaxClass(taxClassList.get(2));
            category.setPrice1(new BigDecimal(100));
            category.setPrice2(new BigDecimal(100));
            category.setPrice3(new BigDecimal(100));
            category.setPrice4(new BigDecimal(100));
            category.setPrice5(new BigDecimal(100));
            category.setPrice6(new BigDecimal(100));
            category.setCommisionMode(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES);
            category.setCommisionMaximumAmount(BigDecimal.ZERO);
            category.setCommissionFixedAmount(BigDecimal.ZERO);
            category.setCommisionPercentProfit(BigDecimal.ONE);
            category.setCommisionPercentSale(BigDecimal.ONE);
            category.setIsAssetTag(Boolean.FALSE);
            category.setIsShippingTag(Boolean.TRUE);
            category.setCountTag(Boolean.FALSE);
            daoCategory.insert(category);
        }
        if (!findLabor.isPresent()) {
            Category category = new Category();
            category.setName("LABOR");
            category.setTaxClass(taxClassList.get(1));
            category.setPrice1(new BigDecimal(100));
            category.setPrice2(new BigDecimal(100));
            category.setPrice3(new BigDecimal(100));
            category.setPrice4(new BigDecimal(100));
            category.setPrice5(new BigDecimal(100));
            category.setPrice6(new BigDecimal(100));
            category.setCommisionMode(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES);
            category.setCommisionMaximumAmount(BigDecimal.ZERO);
            category.setCommissionFixedAmount(BigDecimal.ZERO);
            category.setCommisionPercentProfit(BigDecimal.ONE);
            category.setCommisionPercentSale(BigDecimal.ONE);
            category.setIsAssetTag(Boolean.FALSE);
            category.setIsShippingTag(Boolean.FALSE);
            category.setCountTag(Boolean.FALSE);
            daoCategory.insert(category);
        }
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountPayable;
import com.salesliant.entity.AccountPayable_;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry_;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import com.salesliant.widget.VendorWidget;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class AccountPayableListUI extends BaseListUI<AccountPayable> {

    private final BaseDao<AccountPayable> daoAccountPayable = new BaseDao<>(AccountPayable.class);
    private final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private final DataUI poDispayUI = new DataUI(PurchaseOrderHistory.class);
    private final DataUI purchaseOrderUI = new DataUI(PurchaseOrderHistory.class);
    private final DataUI accountPayableUI = new DataUI(AccountPayable.class);
    private final TableView<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryTable = new TableView<>();
    private ObservableList<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryList;
    private final GridPane fEditPane;
    private final TabPane fTabPane = new TabPane();
    private final static String ACCOUNT_PAYABLE_TITLE = "Account Payable";
    private PurchaseOrderHistory fPurchaseOrderHistory;
    private final VendorWidget fVendorCombo = new VendorWidget();
    private final ObservableSet<AccountPayable> selectedItems;
    private static final Logger LOGGER = Logger.getLogger(AccountPayableListUI.class.getName());

    public AccountPayableListUI() {
        List<AccountPayable> list = daoAccountPayable.read(AccountPayable_.store, Config.getStore(), AccountPayable_.postedTag, false)
                .stream()
                .sorted((e1, e2) -> {
                    if (e1.getVendor() == null && e2.getVendor() == null) {
                        return 0;
                    } else if (e1.getVendor() == null && e2.getVendor() != null) {
                        return -1;
                    } else if (e1.getVendor() != null && e2.getVendor() == null) {
                        return 1;
                    } else {
                        return e1.getVendor().getCompany().toLowerCase().compareTo(e2.getVendor().getCompany().toLowerCase());
                    }
                })
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        mainView = createMainView();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends AccountPayable> observable, AccountPayable newValue, AccountPayable oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                if (fEntity.getPurchaseOrderHistory() != null) {
                    List<PurchaseOrderHistoryEntry> purchaseOrderEntryList = fEntity.getPurchaseOrderHistory().getPurchaseOrderHistoryEntries()
                            .stream()
                            .sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder()))
                            .collect(Collectors.toList());
                    fPurchaseOrderHistoryEntryList = FXCollections.observableList(purchaseOrderEntryList);
                    fPurchaseOrderHistoryEntryTable.setItems(fPurchaseOrderHistoryEntryList);
                    try {
                        poDispayUI.setData(fEntity.getPurchaseOrderHistory());
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    fPurchaseOrderHistoryEntryList = FXCollections.observableArrayList();
                    fPurchaseOrderHistoryEntryTable.setItems(fPurchaseOrderHistoryEntryList);

                    try {
                        poDispayUI.setData(new PurchaseOrderHistory());
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                fPurchaseOrderHistoryEntryList = FXCollections.observableArrayList();
                fPurchaseOrderHistoryEntryTable.setItems(fPurchaseOrderHistoryEntryList);
                try {
                    poDispayUI.setData(new PurchaseOrderHistory());
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        });
        fTabPane.getSelectionModel().selectFirst();
        fEditPane = createEditPane();
        selectedItems = FXCollections.observableSet();
        dialogTitle = ACCOUNT_PAYABLE_TITLE;
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_POST:
                if (!selectedItems.isEmpty()) {
                    selectedItems.forEach(p -> {
                        p.setPostedTag(Boolean.TRUE);
                        p.setDatePosted(new Timestamp(new Date().getTime()));
                        daoAccountPayable.update(p);
                        fTableView.getItems().remove(p);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                } else {
                    showAlertDialog("Please select account payable to post! ");
                }
                break;
            case AppConstants.ACTION_ADD:
                fPurchaseOrderHistory = new PurchaseOrderHistory();
                fPurchaseOrderHistory.setEmployeePurchasedName(Config.getEmployee().getNameOnSalesOrder());
                fPurchaseOrderHistory.setDateCreated(new Timestamp(new Date().getTime()));
                fPurchaseOrderHistory.setDatePurchased(new Timestamp(new Date().getTime()));
                fPurchaseOrderHistory.setDateInvoiced(new Timestamp(new Date().getTime()));
                fPurchaseOrderHistory.setEmployeeReceivedName(Config.getEmployee().getNameOnSalesOrder());
                fPurchaseOrderHistory.setDateReceived(new Timestamp(new Date().getTime()));
                fPurchaseOrderHistory.setStore(Config.getStore());
                fPurchaseOrderHistory.setPostedTag(Boolean.TRUE);
                fPurchaseOrderHistory.setFreightPrePaidAmount(BigDecimal.ZERO);
                fPurchaseOrderHistory.setFreightInvoicedAmount(BigDecimal.ZERO);
                fPurchaseOrderHistory.setTotal(BigDecimal.ZERO);
                fPurchaseOrderHistory.setTaxOnFreightAmount(BigDecimal.ZERO);
                fPurchaseOrderHistory.setTaxOnOrderAmount(BigDecimal.ZERO);
                try {
                    purchaseOrderUI.setData(fPurchaseOrderHistory);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fEntity = new AccountPayable();
                fEntity.setAccountPayableType(DBConstants.TYPE_APAR_CRE);
                fEntity.setStatus(DBConstants.STATUS_OPEN);
                fEntity.setDiscountAmount(BigDecimal.ZERO);
                fEntity.setPaidAmount(BigDecimal.ZERO);
                fEntity.setPostedTag(Boolean.FALSE);
                try {
                    accountPayableUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Purchase Order");
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        purchaseOrderUI.getData(fPurchaseOrderHistory);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    BigDecimal total = fPurchaseOrderHistory.getTotal();
                    fPurchaseOrderHistory.setTotal(total.negate());
                    try {
                        accountPayableUI.getData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fPurchaseOrderHistory.setPurchaseOrderNumber(Config.getNumber(DBConstants.SEQ_PURCHASE_ORDER_NUMBER).toString());
                    daoPurchaseOrderHistory.insert(fPurchaseOrderHistory);
                    fEntity.setDateDue(new Timestamp(new Date().getTime()));
                    fEntity.setDateInvoiced(fPurchaseOrderHistory.getDateInvoiced());
                    fEntity.setPurchaseOrderHistory(fPurchaseOrderHistory);
                    fEntity.setTotalAmount(fPurchaseOrderHistory.getTotal());
                    fEntity.setPurchaseOrderNumber(fPurchaseOrderHistory.getPurchaseOrderNumber());
                    fEntity.setVendorInvoiceNumber(fPurchaseOrderHistory.getVendorInvoiceNumber());
                    fEntity.setGlDebitAmount(fPurchaseOrderHistory.getTotal().negate());
                    fEntity.setGlCreditAmount(BigDecimal.ZERO);
                    fEntity.setGlAccount(Integer.toString(0));
                    daoAccountPayable.insert(fEntity);
                    if (daoAccountPayable.getErrorMessage() == null) {
                        fTableView.getItems().add(fEntity);
                        fTableView.getSelectionModel().select(fEntity);
                    } else {
                        showAlertDialog("Your record is not complete");
                        event.consume();
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fPurchaseOrderHistory = fEntity.getPurchaseOrderHistory();
                    PurchaseOrderHistoryUI editUI = new PurchaseOrderHistoryUI(fPurchaseOrderHistory);
                    fInputDialog = createSaveCancelUIDialog(editUI.getView(), "Purchase Order History");
                    saveBtn.setDisable(false);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        editUI.updatePurchaseOrder();
                        daoPurchaseOrderHistory.update(fPurchaseOrderHistory);
                        if (daoPurchaseOrderHistory.getErrorMessage() == null) {
                            fEntity.setTotalAmount(fPurchaseOrderHistory.getTotal());
                            fEntity.setPurchaseOrderHistory(fPurchaseOrderHistory);
                            fTableView.refresh();
                            fPurchaseOrderHistoryEntryTable.refresh();
                        } else {
                            showAlertDialog("Your record is not complete");
                            event.consume();
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoAccountPayable.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
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
        TextField searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<AccountPayable, AccountPayable> selectedCol = new TableColumn<>("");
        selectedCol.setCellValueFactory((CellDataFeatures<AccountPayable, AccountPayable> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<AccountPayable, AccountPayable> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setVisible(true);
        selectedCol.setSortable(false);

        TableColumn<AccountPayable, String> vendorCol = new TableColumn<>("Vendor");
        vendorCol.setCellValueFactory((CellDataFeatures<AccountPayable, String> p) -> {
            if (p.getValue().getVendor() != null) {
                return new SimpleStringProperty(p.getValue().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        vendorCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorCol.setPrefWidth(200);

        TableColumn<AccountPayable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.vendorInvoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(110);

        TableColumn<AccountPayable, String> purchaseOrderNumberCol = new TableColumn<>("PO No.");
        purchaseOrderNumberCol.setCellValueFactory((CellDataFeatures<AccountPayable, String> p) -> {
            if (p.getValue().getPurchaseOrderHistory() != null && p.getValue().getPurchaseOrderHistory().getPurchaseOrderNumber() != null) {
                return new SimpleStringProperty(p.getValue().getPurchaseOrderHistory().getPurchaseOrderNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        purchaseOrderNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        purchaseOrderNumberCol.setPrefWidth(110);

        TableColumn<AccountPayable, String> invoiceDateCol = new TableColumn<>("Invoice Date");
        invoiceDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.dateInvoiced.getName()));
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        invoiceDateCol.setPrefWidth(110);

        TableColumn<AccountPayable, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.dateDue.getName()));
        dueDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dueDateCol.setPrefWidth(150);

        TableColumn<AccountPayable, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((CellDataFeatures<AccountPayable, String> p) -> {
            if (p.getValue().getAccountPayableType() != null
                    && p.getValue().getAccountPayableType() == DBConstants.TYPE_APAR_INV) {
                return new SimpleStringProperty("Invoice");
            } else if (p.getValue().getAccountPayableType() != null
                    && p.getValue().getAccountPayableType() == DBConstants.TYPE_APAR_CRE) {
                return new SimpleStringProperty("Credit");
            } else {
                return new SimpleStringProperty("");
            }
        });
        typeCol.setPrefWidth(50);

        TableColumn<AccountPayable, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.totalAmount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(110);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(vendorCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(purchaseOrderNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(dueDateCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(new Label("PO Detail:"), 0, 3);
        mainPane.add(createNewEditPostCloseButton(), 1, 3);
        mainPane.add(createPOPane(), 0, 4, 2, 1);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        editPane.setVgap(3);

        purchaseOrderUI.setUIComponent(PurchaseOrderHistory_.vendor, fVendorCombo);
        fVendorCombo.setPrefWidth(90);
        DatePicker dateDue = new DatePicker();
//        purchaseOrderUI.setUIComponent(PurchaseOrderHistory_.dateDue, dateDue);
        DatePicker invoiceDate = new DatePicker();
        purchaseOrderUI.setUIComponent(PurchaseOrderHistory_.dateInvoiced, invoiceDate);
        add(editPane, "Vendor: ", fVendorCombo, 0);
        add(editPane, "Credit Memo No.: ", purchaseOrderUI.createTextField(PurchaseOrderHistory_.vendorInvoiceNumber, 90), 1);
        add(editPane, "Credit Memo Date: ", invoiceDate, 2);
        add(editPane, "Due Date: ", dateDue, 3);
        add(editPane, "Credit Amount: ", purchaseOrderUI.createTextField(PurchaseOrderHistory_.total, 90), 4);
        add(editPane, "GL Account: ", accountPayableUI.createTextField(AccountPayable_.glAccount, 90), 5);
        editPane.add(lblWarning, 0, 6, 2, 1);
        editPane.add(addHLine(1), 0, 7, 2, 1);

        return editPane;
    }

    private HBox createNewEditPostCloseButton() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, "Add Credit", fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, "Edit", fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, "Delete", fHandler);
        Button postButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST, AppConstants.ACTION_POST, "Post", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, postButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private Node createPOPane() {
        Tab contactTab = new Tab("Purchase Order");
        contactTab.setContent(createPOInfoPane());
        contactTab.setClosable(false);
        fTabPane.getTabs().add(contactTab);

        Tab accountTab = new Tab("Purchase Order Detail");
        accountTab.setContent(createPOEntryPane());
        accountTab.setClosable(false);
        fTabPane.getTabs().add(accountTab);

        fTabPane.setPrefHeight(200);

        return fTabPane;
    }

    private Node createPOEntryPane() {
        GridPane poEntryPane = new GridPane();
        poEntryPane.setPadding(new Insets(5));
        poEntryPane.setHgap(5);
        poEntryPane.setVgap(5);
        poEntryPane.setAlignment(Pos.CENTER);
        poEntryPane.getStyleClass().add("hboxPane");

        TableColumn<PurchaseOrderHistoryEntry, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.vendorItemLookUpCode.getName()));
        vendorSKUCol.setMinWidth(120);
        vendorSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<PurchaseOrderHistoryEntry, String> skuCol = new TableColumn<>("Your SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<PurchaseOrderHistoryEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.itemDescription.getName()));
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(250);

        TableColumn<PurchaseOrderHistoryEntry, String> detailQtyOrderedCol = new TableColumn<>("Qty Ordered");
        detailQtyOrderedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() != null && p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getQuantityOrdered()));
            }
        });
        detailQtyOrderedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyOrderedCol.setMinWidth(80);

        TableColumn<PurchaseOrderHistoryEntry, String> detailQtyReceivedCol = new TableColumn<>("Qty Received");
        detailQtyReceivedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() != null && p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getQuantityReceived()));
            }
        });
        detailQtyReceivedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyReceivedCol.setMinWidth(80);

        TableColumn<PurchaseOrderHistoryEntry, String> detailCostCol = new TableColumn<>("Cost");
        detailCostCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            }
        });
        detailCostCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailCostCol.setMinWidth(90);

        TableColumn<PurchaseOrderHistoryEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                BigDecimal total = zeroIfNull(p.getValue().getCost()).multiply(zeroIfNull(p.getValue().getQuantityReceived()));
                return new SimpleStringProperty(getString(total));
            }
        });
        detailTotalCol.setMinWidth(90);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        fPurchaseOrderHistoryEntryTable.getColumns().add(vendorSKUCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(skuCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(detailDescriptionCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(detailQtyOrderedCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(detailQtyReceivedCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(detailCostCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(detailTotalCol);

        fPurchaseOrderHistoryEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fPurchaseOrderHistoryEntryTable.setPrefHeight(200);
        setTableWidth(fPurchaseOrderHistoryEntryTable);

        poEntryPane.add(fPurchaseOrderHistoryEntryTable, 0, 0);
        return poEntryPane;
    }

    private Node createPOInfoPane() {
        GridPane poInfotPane = new GridPane();
        poInfotPane.setPadding(new Insets(5));
        poInfotPane.setHgap(5);
        poInfotPane.setVgap(5);
        poInfotPane.setAlignment(Pos.CENTER);
        poInfotPane.getStyleClass().add("hboxPane");

        GridPane leftPane = new GridPane();
        add(leftPane, "Vendor Name: ", poDispayUI.createTextField(PurchaseOrderHistory_.vendorName), 1);
        add(leftPane, "Vendor Invoice: ", poDispayUI.createTextField(PurchaseOrderHistory_.vendorInvoiceNumber), 2);
        add(leftPane, "PO No.: ", poDispayUI.createTextField(PurchaseOrderHistory_.purchaseOrderNumber), 3);
        add(leftPane, "Purchased By: ", poDispayUI.createTextField(PurchaseOrderHistory_.employeePurchasedName), 4);
        add(leftPane, "Received By: ", poDispayUI.createTextField(PurchaseOrderHistory_.employeeReceivedName), 5);

        GridPane middlePane = new GridPane();
        add(middlePane, "Date Ordered: ", poDispayUI.createTextField(PurchaseOrderHistory_.datePurchased), 1);
        add(middlePane, "Date Received: ", poDispayUI.createTextField(PurchaseOrderHistory_.dateReceived), 2);
        add(middlePane, "Date Invoiced: ", poDispayUI.createTextField(PurchaseOrderHistory_.dateInvoiced), 3);

        GridPane rightPane = new GridPane();
        add(rightPane, "Freight Invoiced: ", poDispayUI.createTextField(PurchaseOrderHistory_.freightInvoicedAmount), 1);
        add(rightPane, "Freight PrePaid: ", poDispayUI.createTextField(PurchaseOrderHistory_.freightPrePaidAmount), 2);
        add(rightPane, "Tax On Order: ", poDispayUI.createTextField(PurchaseOrderHistory_.taxOnOrderAmount), 3);
        add(rightPane, "Tax On Freight: ", poDispayUI.createTextField(PurchaseOrderHistory_.taxOnFreightAmount), 4);
        add(rightPane, "Total: ", poDispayUI.createTextField(PurchaseOrderHistory_.total), 5);

        poDispayUI.getTextField(PurchaseOrderHistory_.vendorName).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.vendorInvoiceNumber).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.purchaseOrderNumber).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.employeePurchasedName).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.employeeReceivedName).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.datePurchased).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.dateReceived).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.dateInvoiced).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.freightInvoicedAmount).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.freightPrePaidAmount).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.taxOnOrderAmount).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.taxOnFreightAmount).setEditable(false);
        poDispayUI.getTextField(PurchaseOrderHistory_.total).setEditable(false);

        poInfotPane.add(leftPane, 0, 0);
        poInfotPane.add(middlePane, 1, 0);
        poInfotPane.add(rightPane, 2, 0);

        return poInfotPane;
    }

    @Override
    protected void validate() {

    }
}

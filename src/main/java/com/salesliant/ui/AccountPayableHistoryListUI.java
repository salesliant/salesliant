package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountPayableHistory;
import com.salesliant.entity.AccountPayableHistory_;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry_;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.report.PurchaseOrderHistoryLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountPayableHistoryListUI extends BaseListUI<AccountPayableHistory> {

    private final BaseDao<AccountPayableHistory> daoAccountPayableHistory = new BaseDao<>(AccountPayableHistory.class);
    private final DataUI poDispayUI = new DataUI(PurchaseOrderHistory.class);
    private final TableView<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryTable = new TableView<>();
    private ObservableList<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryList;
    private final TabPane fTabPane = new TabPane();
    private PurchaseOrderHistory fPurchaseOrderHistory;
    private static final Logger LOGGER = Logger.getLogger(AccountPayableHistoryListUI.class.getName());

    public AccountPayableHistoryListUI() {
        mainView = createMainView();
        fTabPane.getSelectionModel().selectFirst();
        loadData();
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends AccountPayableHistory> observable, AccountPayableHistory newValue,
                        AccountPayableHistory oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        if (fEntity.getPurchaseOrderHistory() != null) {
                            List<PurchaseOrderHistoryEntry> purchaseOrderEntryList = fEntity.getPurchaseOrderHistory()
                                    .getPurchaseOrderHistoryEntries()
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
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<AccountPayableHistory> list = daoAccountPayableHistory.readOrderBy(AccountPayableHistory_.store, Config.getStore(),
                    AccountPayableHistory_.datePaidOn, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getPurchaseOrderHistory() != null) {
                    fPurchaseOrderHistory = fTableView.getSelectionModel().getSelectedItem().getPurchaseOrderHistory();
                    PurchaseOrderHistoryLayout layout = new PurchaseOrderHistoryLayout(fPurchaseOrderHistory);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_EXPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getPurchaseOrderHistory() != null) {
                    fPurchaseOrderHistory = fTableView.getSelectionModel().getSelectedItem().getPurchaseOrderHistory();
                    PurchaseOrderHistoryLayout layout = new PurchaseOrderHistoryLayout(fPurchaseOrderHistory);
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
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        TextField searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<AccountPayableHistory, String> batchCol = new TableColumn<>("Batch");
        batchCol.setCellValueFactory((CellDataFeatures<AccountPayableHistory, String> p) -> {
            if (p.getValue().getAccountPayableBatch() != null) {
                return new SimpleStringProperty(getString(p.getValue().getAccountPayableBatch().getBatchNumber()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        batchCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        batchCol.setPrefWidth(100);

        TableColumn<AccountPayableHistory, String> vendorCol = new TableColumn<>("Vendor");
        vendorCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayableHistory_.vendorName.getName()));
        vendorCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorCol.setPrefWidth(252);

        TableColumn<AccountPayableHistory, String> vendorInvoiceCol = new TableColumn<>("Vendor Invoice");
        vendorInvoiceCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayableHistory_.vendorInvoiceNumber.getName()));
        vendorInvoiceCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorInvoiceCol.setPrefWidth(150);

        TableColumn<AccountPayableHistory, String> employeeCol = new TableColumn<>("Employee");
        employeeCol.setCellValueFactory((CellDataFeatures<AccountPayableHistory, String> p) -> {
            if (p.getValue().getAccountPayableBatch() != null && p.getValue().getAccountPayableBatch().getEmployeeName() != null) {
                return new SimpleStringProperty(p.getValue().getAccountPayableBatch().getEmployeeName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(90);

        TableColumn<AccountPayableHistory, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory((CellDataFeatures<AccountPayableHistory, String> p) -> {
            if (p.getValue().getAccountPayableBatch() != null && p.getValue().getAccountPayableBatch().getDatePaidOn() != null) {
                return new SimpleStringProperty(getString(p.getValue().getAccountPayableBatch().getDatePaidOn()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        dateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCol.setPrefWidth(130);

        TableColumn<AccountPayableHistory, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayableHistory_.paidAmount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(100);

        fTableView.getColumns().add(batchCol);
        fTableView.getColumns().add(vendorCol);
        fTableView.getColumns().add(vendorInvoiceCol);
        fTableView.getColumns().add(employeeCol);
        fTableView.getColumns().add(dateCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(new Label("PO Detail:"), 0, 3);
        mainPane.add(createPrintCloseButtonPane(), 1, 3);
        mainPane.add(createPOPane(), 0, 4, 2, 1);
        return mainPane;
    }

    protected HBox createPrintCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print PO", fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, "Export PO", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, exportButton, closeButton);
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
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getQuantityOrdered()));
            }
        });
        detailQtyOrderedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyOrderedCol.setMinWidth(80);

        TableColumn<PurchaseOrderHistoryEntry, String> detailQtyReceivedCol = new TableColumn<>("Qty Received");
        detailQtyReceivedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
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
}

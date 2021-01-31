package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

public class AccountReceivableUncollectableListUI extends BaseListUI<AccountReceivable> {

    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private final DataUI invoiceUI = new DataUI(Invoice.class);
    private final TableView<InvoiceEntry> fInvoiceEntryTable = new TableView<>();
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private final TabPane fTabPane = new TabPane();
    private final static String MARK_COLLECTABLE = "Mark Collectable";
    private Invoice fInvoice;
    private static final Logger LOGGER = Logger.getLogger(AccountReceivableUncollectableListUI.class.getName());

    public AccountReceivableUncollectableListUI() {
        loadData();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends AccountReceivable> observable, AccountReceivable newValue, AccountReceivable oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                fInvoice = fTableView.getSelectionModel().getSelectedItem().getInvoice();
                if (fInvoice != null) {
                    List<InvoiceEntry> invoiceEntryList = fInvoice.getInvoiceEntries().stream()
                            .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                            .collect(Collectors.toList());
                    fInvoiceEntryList = FXCollections.observableList(invoiceEntryList);
                    fInvoiceEntryTable.setItems(fInvoiceEntryList);
                    try {
                        invoiceUI.setData(fInvoice);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    fInvoiceEntryTable.getItems().clear();
                    try {
                        invoiceUI.setData(new Invoice());
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
            } else {
                fInvoiceEntryTable.getItems().clear();
                try {
                    invoiceUI.setData(new Invoice());
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
        });
        fTabPane.getSelectionModel().selectFirst();
        mainView = createMainView();
    }

    private void loadData() {
        List<AccountReceivable> list = daoAccountReceivable.read(AccountReceivable_.store, Config.getStore(), AccountReceivable_.collectable, Boolean.FALSE)
                .stream()
                .sorted((e1, e2) -> e2.getDateProcessed().compareTo(e1.getDateProcessed()))
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case MARK_COLLECTABLE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to set the seleted entry collectable?", (ActionEvent e) -> {
                        fEntity.setCollectable(Boolean.TRUE);
                        fEntity.setStatus(DBConstants.STATUS_OPEN);
                        daoAccountReceivable.update(fEntity);
                        fEntityList.remove(fEntity);
                        fTableView.refresh();
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

        TableColumn<AccountReceivable, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null) {
                return new SimpleStringProperty(p.getValue().getCustomer().getAccountNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        accountCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountCol.setPrefWidth(100);

        TableColumn<AccountReceivable, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null) {
                String name = getString(p.getValue().getCustomer().getLastName()) + "," + getString(p.getValue().getCustomer().getFirstName());
                return new SimpleStringProperty(name);
            } else {
                return new SimpleStringProperty("");
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(175);

        TableColumn<AccountReceivable, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCustomer().getCompany()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        companyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyCol.setPrefWidth(175);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(110);

        TableColumn<AccountReceivable, String> invoiceDateCol = new TableColumn<>("Invoice Date");
        invoiceDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceDateCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateDue.getName()));
        dueDateCol.setCellFactory(stringCell(Pos.CENTER));
        dueDateCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory((CellDataFeatures<AccountReceivable, String> p) -> {
            if (p.getValue().getCustomer() != null && p.getValue().getCustomer().getCustomerTerm().getCode() != null) {
                return new SimpleStringProperty(p.getValue().getCustomer().getCustomerTerm().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        termCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        termCol.setPrefWidth(100);

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
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        typeCol.setPrefWidth(60);

        TableColumn<AccountReceivable, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.totalAmount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(90);

        fTableView.getColumns().add(accountCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(companyCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(dueDateCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(300);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(new Label("Invoice Detail:"), 0, 3);
        mainPane.add(createCollectableCloseButtonPane(), 1, 3);
        mainPane.add(createInvoicePane(), 0, 4, 2, 1);
        return mainPane;
    }

    private Node createInvoicePane() {
        Tab invoiceTab = new Tab("Invoice");
        invoiceTab.setContent(createOrderInfoPane());
        invoiceTab.setClosable(false);
        fTabPane.getTabs().add(invoiceTab);

        Tab invoiceDetailTab = new Tab("Invoice Detail");
        invoiceDetailTab.setContent(createInvoiceEntryPane());
        invoiceDetailTab.setClosable(false);
        fTabPane.getTabs().add(invoiceDetailTab);

        fTabPane.setPrefHeight(200);

        return fTabPane;
    }

    private Node createInvoiceEntryPane() {
        GridPane invoiceEntryPane = new GridPane();
        invoiceEntryPane.setPadding(new Insets(5));
        invoiceEntryPane.setHgap(5);
        invoiceEntryPane.setVgap(5);
        invoiceEntryPane.setAlignment(Pos.CENTER);
        invoiceEntryPane.getStyleClass().add("hboxPane");

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.TOP_CENTER));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            String description = getString(p.getValue().getItemDescription());
            if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                description = description + "\n" + p.getValue().getLineNote();
            }
            description = description.trim();
            return new SimpleStringProperty(description);
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(230);

        TableColumn<InvoiceEntry, String> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.quantity.getName()));
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(60);

        TableColumn<InvoiceEntry, String> detailCostCol = new TableColumn<>("Price");
        detailCostCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.price.getName()));
        detailCostCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailCostCol.setMinWidth(90);

        TableColumn<InvoiceEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                BigDecimal total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
                return new SimpleStringProperty(getString(total));
            } else {
                return new SimpleStringProperty("");
            }
        });
        detailTotalCol.setMinWidth(90);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        fInvoiceEntryTable.getColumns().add(skuCol);
        fInvoiceEntryTable.getColumns().add(detailDescriptionCol);
        fInvoiceEntryTable.getColumns().add(detailQtyCol);
        fInvoiceEntryTable.getColumns().add(detailCostCol);
        fInvoiceEntryTable.getColumns().add(detailTotalCol);

        fInvoiceEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fInvoiceEntryTable.setPrefHeight(200);
        setTableWidth(fInvoiceEntryTable);

        invoiceEntryPane.add(fInvoiceEntryTable, 0, 0);
        return invoiceEntryPane;
    }

    private Node createOrderInfoPane() {
        GridPane invoiceInfotPane = new GridPane();
        invoiceInfotPane.setPadding(new Insets(5));
        invoiceInfotPane.setHgap(5);
        invoiceInfotPane.setVgap(5);
        invoiceInfotPane.setAlignment(Pos.CENTER);
        invoiceInfotPane.getStyleClass().add("hboxPane");

        GridPane leftPane = new GridPane();
        add(leftPane, "Invoice No.: ", invoiceUI.createTextField(Invoice_.invoiceNumber), 1);
        add(leftPane, "Customer PO No.: ", invoiceUI.createTextField(Invoice_.customerPoNumber), 2);
        add(leftPane, "Date Ordered: ", invoiceUI.createTextField(Invoice_.dateOrdered), 3);

        GridPane rightPane = new GridPane();
        add(rightPane, "Shipping Charge: ", invoiceUI.createTextField(Invoice_.shippingCharge), 1);
        add(rightPane, "Tax Amount: ", invoiceUI.createTextField(Invoice_.taxAmount), 2);
        add(rightPane, "Total: ", invoiceUI.createTextField(Invoice_.total), 3);

        invoiceUI.getTextField(Invoice_.invoiceNumber).setEditable(false);
        invoiceUI.getTextField(Invoice_.customerPoNumber).setEditable(false);
        invoiceUI.getTextField(Invoice_.dateOrdered).setEditable(false);
        invoiceUI.getTextField(Invoice_.shippingCharge).setEditable(false);
        invoiceUI.getTextField(Invoice_.taxAmount).setEditable(false);
        invoiceUI.getTextField(Invoice_.total).setEditable(false);

        invoiceInfotPane.add(leftPane, 0, 0);
        invoiceInfotPane.add(rightPane, 1, 0);

        return invoiceInfotPane;
    }

    protected HBox createCollectableCloseButtonPane() {
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
        Button uncollectableButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
        uncollectableButton.setId(MARK_COLLECTABLE);
        closeButton.setId(AppConstants.ACTION_CLOSE);
        uncollectableButton.setText("Mark Collectable");
        uncollectableButton.setPrefWidth(130);
        closeButton.setOnAction(fHandler);
        uncollectableButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(uncollectableButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

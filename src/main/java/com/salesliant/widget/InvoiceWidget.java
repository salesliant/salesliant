package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemBom;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.InvoiceSearchField;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class InvoiceWidget extends BaseListUI<InvoiceEntry> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final TableView<Invoice> fInvoiceTableView = new TableView<>();
    private ObservableList<Invoice> fInvoiceList;
    private final static String TITLE = "Invoice";
    public TableColumn<InvoiceEntry, InvoiceEntry> selectedCol = new TableColumn<>("");
    public ObservableSet<InvoiceEntry> selectedItems = FXCollections.observableSet();
    private Customer fCustomer;
    private final InvoiceSearchField searchField = new InvoiceSearchField();

    public InvoiceWidget() {
        this(null);
    }

    public InvoiceWidget(Customer customer) {
        this.fCustomer = customer;
        loadData();
        mainView = createMainView();
        fInvoiceTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Invoice> observable, Invoice newValue, Invoice oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                List<InvoiceEntry> aList = fInvoiceTableView.getSelectionModel().getSelectedItem().getInvoiceEntries()
                        .stream()
                        .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                        .collect(Collectors.toList());
                selectedItems.clear();
                fEntityList = FXCollections.observableList(aList);
                fTableView.setItems(fEntityList);
            } else {
                fTableView.getItems().clear();
            }
        });
        selectedItems.addListener((SetChangeListener<InvoiceEntry>) change -> {
            if (change.wasAdded()) {
                InvoiceEntry ie = change.getElementAdded();
                if (ie.getQuantity().compareTo(BigDecimal.ZERO) <= 0) {
                    selectedItems.remove(ie);
                } else {
                    Item item = getItem(ie.getItemLookUpCode());
                    if (item == null || ie.getItemLookUpCode().equalsIgnoreCase("NOTE:") || ie.getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                        selectedItems.remove(ie);
                    }
                    if (item != null && item.getItemType() != null && item.getItemType().equals(DBConstants.ITEM_TYPE_BOM)) {
                        item.getItemBomBomItems().clear();
                        List<ItemBom> list = new ArrayList<>();
                        int i = ie.getDisplayOrder() + 1;
                        while (i <= fTableView.getItems().size() - 1) {
                            if (fTableView.getItems().get(i).getComponentFlag() != null && fTableView.getItems().get(i).getComponentFlag()) {
                                ItemBom itemBom = new ItemBom();
                                itemBom.setDisplayOrder(fTableView.getItems().get(i).getDisplayOrder());
                                itemBom.setBomItem(item);
                                itemBom.setQuantity(fTableView.getItems().get(i).getComponentQuantity());
                                Item itemAti = getItem(fTableView.getItems().get(i).getItemLookUpCode());
                                itemBom.setComponentItem(itemAti);
                                list.add(itemBom);
                                selectedItems.remove(fTableView.getItems().get(i));
                                i++;
                            } else {
                                break;
                            }
                        }
                        item.setItemBomBomItems(list);
                    }
                    if (ie.getComponentFlag() != null && ie.getComponentFlag()) {
                        int i = ie.getDisplayOrder() - 1;
                        while (i >= 0) {
                            Item itemAti = getItem(fTableView.getItems().get(i).getItemLookUpCode());
                            if (itemAti != null && itemAti.getItemType().equals(DBConstants.ITEM_TYPE_BOM)) {
                                selectedItems.remove(fTableView.getItems().get(i));
                                break;
                            } else {
                                i--;
                            }
                        }
                    }
                }
            }
            fTableView.refresh();
        });
        dialogTitle = TITLE;
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT_ALL:
                selectedItems.clear();
                fEntityList.forEach(e -> selectedItems.add(e));
                fTableView.refresh();
                break;
            case AppConstants.ACTION_UN_SELECT_ALL:
                selectedItems.clear();
                fTableView.refresh();
                break;
            case AppConstants.ACTION_RECEIVE:
                loadCustomerInvoiceData();
                break;
            case AppConstants.ACTION_RECEIVE_ALL:
                loadAllInvoiceData();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Invoice, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.customerName.getName()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p) -> {
            if (p.getValue().getBillToCompany() != null) {
                return new SimpleStringProperty(p.getValue().getBillToCompany());
            } else {
                return new SimpleStringProperty(p.getValue().getCustomerName());
            }
        });
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(305);

        TableColumn<Invoice, String> orderNoCol = new TableColumn<>("Invoice");
        orderNoCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.invoiceNumber.getName()));
        orderNoCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        orderNoCol.setPrefWidth(100);

        TableColumn<Invoice, String> dateCreatedCol = new TableColumn<>("Date Entered");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.dateInvoiced.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dateCreatedCol.setPrefWidth(140);
        dateCreatedCol.setSortType(TableColumn.SortType.DESCENDING);

        TableColumn<Invoice, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(100);

        TableColumn<Invoice, String> employeeCol = new TableColumn<>("Sales");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.salesName.getName()));
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(100);

        fInvoiceTableView.getColumns().add(orderNoCol);
        fInvoiceTableView.getColumns().add(nameCol);
        fInvoiceTableView.getColumns().add(dateCreatedCol);
        fInvoiceTableView.getColumns().add(totalCol);
        fInvoiceTableView.getColumns().add(employeeCol);

        fInvoiceTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fInvoiceTableView.setPrefHeight(300);

        selectedCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, InvoiceEntry> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<InvoiceEntry, InvoiceEntry> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setVisible(true);
        selectedCol.setSortable(false);

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                return new SimpleStringProperty(p.getValue().getItemDescription());
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        detailDescriptionCol.setMinWidth(224);

        TableColumn<InvoiceEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantity());
            }
        });
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(60);

        TableColumn<InvoiceEntry, BigDecimal> detailQtyReturnCol = new TableColumn<>("Qty Returned");
        detailQtyReturnCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantityReturn());
            }
        });
        detailQtyReturnCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyReturnCol.setMinWidth(60);

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

        TableColumn<InvoiceEntry, BigDecimal> discountCol = new TableColumn<>("Discount");
        discountCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getDiscountAmount());
            }
        });
        discountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        discountCol.setPrefWidth(85);
        discountCol.setResizable(false);

        TableColumn<InvoiceEntry, BigDecimal> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                BigDecimal total;
                if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                    total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
                    return new ReadOnlyObjectWrapper(total);
                } else {
                    return null;
                }
            }

        });

        detailTotalCol.setMinWidth(90);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(detailDescriptionCol);
        fTableView.getColumns().add(detailQtyCol);
        fTableView.getColumns().add(detailQtyReturnCol);
        fTableView.getColumns().add(detailPriceCol);
        fTableView.getColumns().add(discountCol);
        fTableView.getColumns().add(detailTotalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);

        mainPane.add(fInvoiceTableView, 0, 2);
        mainPane.add(fTableView, 0, 3);
        mainPane.add(createTagUnTagButtonPane(), 0, 4);
        return mainPane;
    }

    private HBox createTagUnTagButtonPane() {
        Button tagAllBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_ALL, AppConstants.ACTION_SELECT_ALL, fHandler);
        Button unTagAllBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_UN_SELECT_ALL, AppConstants.ACTION_UN_SELECT_ALL, fHandler);
        Button loadCustomerDataBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_RECEIVE, fHandler);
        Button loadAllDataBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_RECEIVE_ALL, fHandler);
        loadCustomerDataBtn.setText("Load Customer Invoice");
        loadAllDataBtn.setText("Load All Invoice");
        tagAllBtn.setPrefWidth(120);
        unTagAllBtn.setPrefWidth(120);
        loadCustomerDataBtn.setPrefWidth(160);
        loadAllDataBtn.setPrefWidth(160);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(tagAllBtn, unTagAllBtn, loadCustomerDataBtn, loadAllDataBtn);
        buttonGroup.setAlignment(Pos.CENTER_LEFT);
        buttonGroup.setSpacing(2);
        return buttonGroup;
    }

    private void loadData() {
        fInvoiceTableView.setPlaceholder(lblLoading);
        List<Invoice> list;
        if (fCustomer != null) {
            list = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.customerAccountNumber, fCustomer.getAccountNumber(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC);
        } else {
            list = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC);
        }
        fInvoiceList = FXCollections.observableList(list);
        fInvoiceTableView.setItems(fInvoiceList);
        searchField.setTableView(fInvoiceTableView);
        fInvoiceTableView.setPlaceholder(null);
        fInvoiceTableView.requestFocus();
    }

    private void loadCustomerInvoiceData() {
        List<Invoice> list = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.customerAccountNumber, fCustomer.getAccountNumber(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC);
        fInvoiceList = FXCollections.observableList(list);
        fInvoiceTableView.setItems(fInvoiceList);
        fInvoiceTableView.requestFocus();
    }

    private void loadAllInvoiceData() {
        List<Invoice> list = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC);
        fInvoiceList = FXCollections.observableList(list);
        fInvoiceTableView.setItems(fInvoiceList);
        fInvoiceTableView.requestFocus();
    }
}

package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.report.ItemOnOrderListReportLayout;
import com.salesliant.util.AppConstants;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemOnOrderListUI extends ItemListBaseUI {

    protected final TableView<PurchaseOrderEntry> fPurchaseOrderEntryTable = new TableView<>();
    protected ObservableList<PurchaseOrderEntry> fPurchaseOrderEntryList;
    private static final Logger LOGGER = Logger.getLogger(ItemOnOrderListUI.class.getName());

    public ItemOnOrderListUI() {
        loadData();
        createGUI();
        ITEM_TITLE = "Items On Order";
        priceCol.setVisible(false);
        costCol.setVisible(false);
        qtyOnOrderCol.setVisible(true);
        lookUpCodeCol.setPrefWidth(150);
        descriptionCol.setPrefWidth(300);
        qtyCol.setPrefWidth(100);
        qtyOnOrderCol.setPrefWidth(100);
        setTableWidth(fTableView);
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<PurchaseOrderEntry> purchaseOrderList = new ArrayList<>(fEntity.getPurchaseOrderEntries());
                fPurchaseOrderEntryList = FXCollections.observableList(purchaseOrderList);
                fPurchaseOrderEntryTable.setItems(fPurchaseOrderEntryList);
            }
        });
    }

    @Override
    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.activeTag, true, Item_.trackQuantity, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            List<Item> countableList = list.stream()
                    .filter(e -> e.getCategory() != null && e.getCategory().getCountTag() != null && e.getCategory().getCountTag()
                    && getQuantityOnOrder(e).compareTo(BigDecimal.ZERO) > 0)
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(countableList);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_RELOAD:
                loadData();
                fTableView.requestFocus();
                break;

            case AppConstants.ACTION_PRINT_REPORT:
                if (!fEntityList.isEmpty()) {
                    ItemOnOrderListReportLayout layout = new ItemOnOrderListReportLayout(fEntityList);
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

    @Override
    protected final HBox createButtonPane() {
        Button reloadButton = ButtonFactory.getButton(ButtonFactory.BUTTON_RELOAD, AppConstants.ACTION_RELOAD, fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT_REPORT, AppConstants.ACTION_PRINT_REPORT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(reloadButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    @Override
    protected GridPane createInfoPane() {
        GridPane purchaseOrderPane = new GridPane();
        purchaseOrderPane.setPadding(new Insets(5));
        purchaseOrderPane.setHgap(5);
        purchaseOrderPane.setVgap(5);
        purchaseOrderPane.setAlignment(Pos.CENTER);
        purchaseOrderPane.getStyleClass().add("hboxPane");

        TableColumn<PurchaseOrderEntry, String> poNumberCol = new TableColumn<>("PO Number");
        poNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getPurchaseOrder() != null) {
                return new SimpleStringProperty(Integer.toString(p.getValue().getPurchaseOrder().getPurchaseOrderNumber()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        poNumberCol.setCellFactory(stringCell(Pos.CENTER));
        poNumberCol.setPrefWidth(110);

        TableColumn<PurchaseOrderEntry, String> vendorNameCol = new TableColumn<>("Vendor");
        vendorNameCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getPurchaseOrder() != null) {
                return new SimpleStringProperty(p.getValue().getPurchaseOrder().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        vendorNameCol.setCellFactory(stringCell(Pos.CENTER));
        vendorNameCol.setPrefWidth(220);

        TableColumn<PurchaseOrderEntry, String> orderCostCol = new TableColumn<>("Cost");
        orderCostCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getCost() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        orderCostCol.setCellFactory(stringCell(Pos.CENTER));
        orderCostCol.setPrefWidth(100);

        TableColumn<PurchaseOrderEntry, String> orderQtyCol = new TableColumn<>("Qty");
        orderQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getQuantityOrdered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getQuantityOrdered()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        orderQtyCol.setCellFactory(stringCell(Pos.CENTER));
        orderQtyCol.setPrefWidth(100);

        TableColumn<PurchaseOrderEntry, String> dateExpectedCol = new TableColumn<>("Expected Date");
        dateExpectedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getPurchaseOrder() != null && p.getValue().getPurchaseOrder().getDateExpected() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPurchaseOrder().getDateExpected()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        dateExpectedCol.setCellFactory(stringCell(Pos.CENTER));
        dateExpectedCol.setPrefWidth(120);

        fPurchaseOrderEntryTable.getColumns().add(vendorNameCol);
        fPurchaseOrderEntryTable.getColumns().add(poNumberCol);
        fPurchaseOrderEntryTable.getColumns().add(orderQtyCol);
        fPurchaseOrderEntryTable.getColumns().add(orderCostCol);
        fPurchaseOrderEntryTable.getColumns().add(dateExpectedCol);
        fPurchaseOrderEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fPurchaseOrderEntryTable.setPrefHeight(140);

        purchaseOrderPane.add(fPurchaseOrderEntryTable, 0, 0);
        return purchaseOrderPane;
    }
}

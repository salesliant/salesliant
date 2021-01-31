package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction_;
import com.salesliant.report.ItemOnRMAListReportLayout;
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
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemOnRMAListUI extends ItemListBaseUI {

    protected final TableView<ReturnTransaction> fReturnTransactionTable = new TableView<>();
    protected ObservableList<ReturnTransaction> fReturnTransactionList;
    private static final Logger LOGGER = Logger.getLogger(ItemOnRMAListUI.class.getName());

    public ItemOnRMAListUI() {
        loadData();
        createGUI();
        ITEM_TITLE = "Items On Order";
        priceCol.setVisible(false);
        costCol.setVisible(false);
        qtyOnRMACol.setVisible(true);
        lookUpCodeCol.setPrefWidth(150);
        descriptionCol.setPrefWidth(300);
        qtyCol.setPrefWidth(100);
        qtyOnRMACol.setPrefWidth(100);
        setTableWidth(fTableView);
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<ReturnTransaction> returnTransactionList = new ArrayList<>(fEntity.getReturnTransactions());
                fReturnTransactionList = FXCollections.observableList(returnTransactionList);
                fReturnTransactionTable.setItems(fReturnTransactionList);
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
                    && getQuantityOnRMA(e).compareTo(BigDecimal.ZERO) > 0)
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
                    ItemOnRMAListReportLayout layout = new ItemOnRMAListReportLayout(fEntityList);
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
        GridPane returnTransactionPane = new GridPane();
        returnTransactionPane.setPadding(new Insets(5));
        returnTransactionPane.setHgap(5);
        returnTransactionPane.setVgap(5);
        returnTransactionPane.setAlignment(Pos.CENTER);
        returnTransactionPane.getStyleClass().add("hboxPane");

        TableColumn<ReturnTransaction, String> rmaNumberCol = new TableColumn<>("RMA");
        rmaNumberCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.rmaNumberToCustomer.getName()));
        rmaNumberCol.setCellFactory(stringCell(Pos.CENTER));
        rmaNumberCol.setPrefWidth(110);

        TableColumn<ReturnTransaction, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((CellDataFeatures<ReturnTransaction, String> p) -> {
            if (p.getValue() != null && p.getValue().getCustomer() != null) {
                String customer;
                if (p.getValue().getCustomer().getCompany() != null) {
                    customer = p.getValue().getCustomer().getCompany();
                } else {
                    customer = getString(p.getValue().getCustomer().getLastName()) + ","
                            + getString(p.getValue().getCustomer().getFirstName());
                }
                return new SimpleStringProperty(customer);
            } else {
                return new SimpleStringProperty("");
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(200);

        TableColumn<ReturnTransaction, String> orderCostCol = new TableColumn<>("Cost");
        orderCostCol.setCellValueFactory((TableColumn.CellDataFeatures<ReturnTransaction, String> p) -> {
            if (p.getValue().getCost() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        orderCostCol.setCellFactory(stringCell(Pos.CENTER));
        orderCostCol.setPrefWidth(100);

        TableColumn<ReturnTransaction, String> orderQtyCol = new TableColumn<>("Qty");
        orderQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<ReturnTransaction, String> p) -> {
            if (p.getValue().getQuantity() != null) {
                return new SimpleStringProperty(getString(p.getValue().getQuantity()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        orderQtyCol.setCellFactory(stringCell(Pos.CENTER));
        orderQtyCol.setPrefWidth(100);

        TableColumn<ReturnTransaction, String> dateReturnedCol = new TableColumn<>("Date Returned");
        dateReturnedCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.dateReturned.getName()));
        dateReturnedCol.setCellFactory(stringCell(Pos.CENTER));
        dateReturnedCol.setPrefWidth(140);

        fReturnTransactionTable.getColumns().add(customerCol);
        fReturnTransactionTable.getColumns().add(rmaNumberCol);
        fReturnTransactionTable.getColumns().add(orderQtyCol);
        fReturnTransactionTable.getColumns().add(orderCostCol);
        fReturnTransactionTable.getColumns().add(dateReturnedCol);
        fReturnTransactionTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fReturnTransactionTable.setPrefHeight(140);

        returnTransactionPane.add(fReturnTransactionTable, 0, 0);
        return returnTransactionPane;
    }
}

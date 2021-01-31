package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Item_;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.ItemSearchField;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ItemQuantityMissingListUI extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);
    protected GridPane fEditPane;
    private final TableColumn<Item, Item> selectedCol = new TableColumn<>("");
    private ObservableSet<Item> selectedItems = FXCollections.observableSet();
    private final TableView<Store> fStoreTableView = new TableView<>();
    private final ObservableList<Store> fStoreList;
    private final ItemSearchField searchField = new ItemSearchField();
    private final List<Store> storeList;
    private final Label qtyLabel = new Label();
    private final Label qtySelectedLabel = new Label();

    public ItemQuantityMissingListUI() {
        storeList = daoStore.read();
        fStoreList = FXCollections.observableList(storeList);
        fStoreTableView.setItems(fStoreList);
        createGUI();
        loadData();
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        qtyLabel.setText("");
        qtySelectedLabel.setText("");
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            List<Item> notFoundList = new ArrayList<>();
            list.forEach(e -> {
                storeList.forEach(s -> {
                    if (getItemQuantityByStore(e, s) == null && !notFoundList.contains(e)) {
                        notFoundList.add(e);
                    }
                });
            });
            Collections.sort(notFoundList, (e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()));
            fEntityList = FXCollections.observableList(notFoundList);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            qtyLabel.setText("Total: " + notFoundList.size());
            selectedItems.addListener((SetChangeListener<Item>) change -> {
                qtySelectedLabel.setText("Selected: " + selectedItems.size());
            });
            fTableView.setPlaceholder(null);
        });
    }

    protected final void createGUI() {
        mainView = createMainView();
        selectedItems = FXCollections.observableSet();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                fStoreTableView.refresh();
            } else {
                fEntity = null;
                fStoreTableView.refresh();
            }
        });

    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PROCESS:
                if (!selectedItems.isEmpty()) {
                    Response answer = createConfirmResponseDialog("Do you want to make selected items available to all stores?");
                    if (answer.equals(AppConstants.Response.YES)) {
                        handleAction(AppConstants.ACTION_REFRESH);
                    }
                } else {
                    showAlertDialog("Not items selected! ");
                }
                break;
            case AppConstants.ACTION_SELECT_ALL:
                fTableView.getItems().forEach(e -> {
                    if (selectedItems != null && !selectedItems.contains(e)) {
                        selectedItems.add(e);
                    }
                });
                break;
            case AppConstants.ACTION_UN_SELECT_ALL:
                fTableView.getItems().forEach(e -> {
                    if (selectedItems != null && selectedItems.contains(e)) {
                        selectedItems.remove(e);
                    }
                });
                break;
            case AppConstants.ACTION_REFRESH:
                List<Item> list = getSelectedItems();
                fTableView.setPlaceholder(lblProcessing);
                list.forEach(item -> {
                    storeList.forEach(store -> {
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
                });
                loadData();
                break;
        }
    }

    protected Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);
        GridPane.setHalignment(qtyLabel, HPos.LEFT);
        GridPane.setHalignment(qtySelectedLabel, HPos.RIGHT);

        selectedCol.setCellValueFactory((CellDataFeatures<Item, Item> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<Item, Item> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setVisible(true);
        selectedCol.setSortable(false);

        TableColumn<Item, String> lookUpCodeCol = new TableColumn<>("SKU");
        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lookUpCodeCol.setPrefWidth(150);

        TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(330);

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice1() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        priceCol.setPrefWidth(100);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(priceCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(300);

        TableColumn<Store, String> storeCol = new TableColumn<>("Store");
        storeCol.setCellValueFactory(new PropertyValueFactory<>(Store_.storeName.getName()));
        storeCol.setMinWidth(500);
        storeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<Store, String> storeQtyCol = new TableColumn<>("Qty");
        storeQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue() != null && fEntity != null) {
                ItemQuantity iq = getItemQuantityByStore(fEntity, p.getValue());
                if (iq != null && iq.getQuantity() != null) {
                    return new SimpleStringProperty(getString(iq.getQuantity()));
                } else {
                    return null;
                }
            } else {
                return null;
            }
        });
        storeQtyCol.setCellFactory(stringCell(Pos.CENTER));
        storeQtyCol.setMinWidth(100);

        fStoreTableView.getColumns().add(storeCol);
        fStoreTableView.getColumns().add(storeQtyCol);
        fStoreTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fStoreTableView);
        fStoreTableView.setPrefHeight(200);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fStoreTableView, 0, 3, 2, 1);
        mainPane.add(qtyLabel, 0, 4);
        mainPane.add(qtySelectedLabel, 1, 4);
        mainPane.add(createButtonPane(), 1, 5);
        return mainPane;
    }

    protected HBox createButtonPane() {
        Button selectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_ALL, AppConstants.ACTION_SELECT_ALL, "Tag All", fHandler);
        Button unSelectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UN_SELECT_ALL, AppConstants.ACTION_UN_SELECT_ALL, "Untag All", fHandler);
        Button processButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(selectAllButton, unSelectAllButton, processButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private List<Item> getSelectedItems() {
        ArrayList<Item> list = new ArrayList<>();
        selectedItems.stream().forEach(e -> list.add(e));
        return list;
    }
}

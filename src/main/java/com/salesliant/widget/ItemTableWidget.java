/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.ItemSearchField;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lewis
 */
public class ItemTableWidget extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final ObservableSet<Item> selectedItems = FXCollections.observableSet();
    public final TableColumn<Item, Item> selectedCol = new TableColumn<>("");
    public final TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
    public final TableColumn<Item, String> costCol = new TableColumn<>("Cost");
    public final TextField searchField;

    public ItemTableWidget() {

        List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
        ObservableList<Item> oList = FXCollections.observableList(list);
        fTableView.setItems(oList);
        searchField = new ItemSearchField(fTableView);
        mainView = createMainView();
        costCol.setVisible(false);
        costCol.setPrefWidth(0);
        descriptionCol.setPrefWidth(350);
        addListener();
        Platform.runLater(() -> searchField.requestFocus());
    }

    public ItemTableWidget(Integer itemType) {

        List<Item> list = daoItem.readOrderBy(Item_.itemType, itemType, Item_.activeTag, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
        ObservableList<Item> oList = FXCollections.observableList(list);
        fTableView.setItems(oList);
        searchField = new ItemSearchField(fTableView);
        mainView = createMainView();
        costCol.setVisible(false);
        costCol.setPrefWidth(0);
        descriptionCol.setPrefWidth(350);
        addListener();
        Platform.runLater(() -> searchField.requestFocus());
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_RESET:
                selectedItems.clear();
                fTableView.refresh();
                break;
            case AppConstants.ACTION_SEARCH:
                searchField.setText("");
                break;
            case AppConstants.ACTION_SHOW_COST:
                if (costCol.isVisible()) {
                    costCol.setVisible(false);
                    costCol.setPrefWidth(0);
                    descriptionCol.setPrefWidth(350);
                } else {
                    costCol.setVisible(true);
                    costCol.setPrefWidth(100);
                    descriptionCol.setPrefWidth(250);
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
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        selectedCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, Item> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<Item, Item> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<Item, String> lookUpCodeCol = new TableColumn<>("SKU");
        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lookUpCodeCol.setPrefWidth(120);

        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(getItemDescription(p.getValue()));
            } else {
                return null;
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        TableColumn<Item, String> qtyCol = new TableColumn<>("Stock");
        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(getString(getQuantity(p.getValue())));
            } else {
                return null;
            }
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER));
        qtyCol.setPrefWidth(90);

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice1() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER));
        priceCol.setPrefWidth(100);

        costCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getCost() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            } else {
                return null;
            }
        });
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setVisible(false);
        costCol.setPrefWidth(100);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(costCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefWidth(703);
        fTableView.setPrefHeight(300);

        mainPane.add(fTableView, 0, 2);
        return mainPane;
    }

    public List<Item> getSelectedItems() {
        ArrayList<Item> list = new ArrayList<>();
        selectedItems.stream().forEach(e -> list.add(e));
        return list;
    }

    protected final void addListener() {
        fTableView.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.F2) {
                handleAction(AppConstants.ACTION_SHOW_COST);
            }
        });
    }
}

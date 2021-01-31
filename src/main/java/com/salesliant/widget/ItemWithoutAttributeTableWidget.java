/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.ItemSearchField;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DBConstants;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lewis
 */
public class ItemWithoutAttributeTableWidget extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    public final TextField searchField;

    public ItemWithoutAttributeTableWidget() {

        List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC)
                .stream()
                .filter(e -> e.getItemAttributes().isEmpty())
                .collect(Collectors.toList());
        ObservableList<Item> oList = FXCollections.observableList(list);
        fTableView.setItems(oList);
        searchField = new ItemSearchField(fTableView);
        mainView = createMainView();
        Platform.runLater(() -> searchField.requestFocus());
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_RESET:
                fTableView.refresh();
                break;
            case AppConstants.ACTION_SEARCH:
                searchField.setText("");
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

        TableColumn<Item, String> lookUpCodeCol = new TableColumn<>("SKU");
        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lookUpCodeCol.setPrefWidth(120);

        TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
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

        TableColumn<Item, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getCost() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            } else {
                return null;
            }
        });
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setPrefWidth(100);

        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(costCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        return mainPane;
    }
}

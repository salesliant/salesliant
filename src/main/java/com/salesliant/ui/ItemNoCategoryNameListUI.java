/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.ItemSearchField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lewis
 */
public class ItemNoCategoryNameListUI extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final TableColumn<Item, Item> selectedCol = new TableColumn<>("");
    private ObservableSet<Item> selectedItems = FXCollections.observableSet();
    private final ItemSearchField searchField = new ItemSearchField();

    public ItemNoCategoryNameListUI() {
        loadData();
        createGUI();
        setTableWidth(fTableView);
    }

    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC)
                    .stream()
                    .filter(e -> e.getCategoryName() == null || e.getCategoryName().isEmpty())
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    protected final void createGUI() {
        mainView = createMainView();
        selectedItems = FXCollections.observableSet();

    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PROCESS:
                if (!selectedItems.isEmpty()) {
                    AppConstants.Response answer = createConfirmResponseDialog("Do you want to assign category name to selected items?");
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
                    if (item.getCategory() != null && item.getCategory().getName() != null && !item.getCategory().getName().isEmpty()) {
                        item.setCategoryName(item.getCategory().getName());
                        daoItem.update(item);
                    }
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

        selectedCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, Item> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
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
        mainPane.add(fTableView, 0, 2, 2, 1);
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

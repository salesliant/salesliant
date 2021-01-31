/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store_;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.stringCell;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lewis
 */
public class StoreTableWidget extends BaseListUI<Store> {

    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);

    public StoreTableWidget() {
        List<Store> list = daoStore.read().stream()
                .sorted((e1, e2) -> e1.getStoreCode().compareToIgnoreCase(e2.getStoreCode()))
                .filter(e -> !e.getId().equals(Config.getStore().getId()))
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        mainView = createMainView();

    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        TableColumn<Store, String> storeNameCol = new TableColumn<>("Store");
        storeNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue().getStoreName() != null) {
                return new SimpleStringProperty(p.getValue().getStoreName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        storeNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        storeNameCol.setPrefWidth(200);

        TableColumn<Store, String> storeCodeCol = new TableColumn<>("Store Code");
        storeCodeCol.setCellValueFactory(new PropertyValueFactory<>(Store_.storeCode.getName()));
        storeCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        storeCodeCol.setPrefWidth(90);

        TableColumn<Store, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>(Store_.phoneNumber.getName()));
        phoneCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneCol.setPrefWidth(100);

        TableColumn<Store, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue() != null) {
                String c = "";
                if (p.getValue().getAddress1() != null && !p.getValue().getAddress1().isEmpty()) {
                    c = c + p.getValue().getAddress1();
                }
                if (p.getValue().getAddress2() != null && !p.getValue().getAddress2().isEmpty()) {
                    c = c + ", " + p.getValue().getAddress2();
                }
                if (p.getValue().getCity() != null && !p.getValue().getCity().isEmpty()) {
                    c = c + ", " + p.getValue().getCity();
                }
                if (p.getValue().getState() != null && !p.getValue().getState().isEmpty()) {
                    c = c + ", " + p.getValue().getState();
                }
                if (p.getValue().getPostCode() != null && !p.getValue().getPostCode().isEmpty()) {
                    c = c + " " + p.getValue().getPostCode();
                }
                return new SimpleStringProperty(c);
            } else {
                return new SimpleStringProperty("");
            }
        });
        addressCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        addressCol.setPrefWidth(300);

        fTableView.getColumns().add(storeNameCol);
        fTableView.getColumns().add(storeCodeCol);
        fTableView.getColumns().add(phoneCol);
        fTableView.getColumns().add(addressCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        return mainPane;
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Store;
import com.salesliant.util.BaseDao;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author Lewis
 */
public class StoreWidget extends ComboBox<Store> {

    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);

    public StoreWidget() {
        setButtonCell(new StoreWidget.StoreListCell());
        setCellFactory((ListView<Store> parm) -> new StoreWidget.StoreListCell());
        List<Store> list = daoStore.read().stream()
                .sorted((e1, e2) -> e1.getStoreCode().compareToIgnoreCase(e2.getStoreCode()))
                .collect(Collectors.toList());
        List<Store> newList = new ArrayList<>();
        list.forEach(e -> {
            if (!e.getId().equals(Config.getStore().getId())) {
                newList.add(e);
            }
        });
        ObservableList<Store> oList = FXCollections.observableList(newList);
        setItems(oList);
    }

    class StoreListCell extends ListCell<Store> {

        @Override
        protected void updateItem(Store item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getStoreName());
            }
        }
    }
}

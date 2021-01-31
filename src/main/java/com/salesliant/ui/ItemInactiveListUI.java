/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.util.AppConstants;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;

/**
 *
 * @author Lewis
 */
public class ItemInactiveListUI extends ItemListBaseUI {

    public ItemInactiveListUI() {
        loadData();
        createGUI();
        setTableWidth(fTableView);
    }

    @Override
    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.activeTag, false, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }
}

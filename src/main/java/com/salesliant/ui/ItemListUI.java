package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.util.AppConstants;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;

public class ItemListUI extends ItemListBaseUI {

    public ItemListUI() {
        loadData();
        createGUI();
        setTableWidth(fTableView);
    }

    @Override
    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list;
            if (itemActiveStatus) {
                list = daoItem.readOrderBy(Item_.activeTag, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            } else {
                list = daoItem.readOrderBy(Item_.activeTag, false, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            }
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }
}

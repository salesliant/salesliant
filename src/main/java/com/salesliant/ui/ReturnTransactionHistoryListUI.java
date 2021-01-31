package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction_;
import com.salesliant.util.DBConstants;
import java.util.List;
import javafx.collections.FXCollections;

public class ReturnTransactionHistoryListUI extends ReturnTransactionBaseListUI {

    public ReturnTransactionHistoryListUI() {
        loadData();
        createGUI();
        fTopButtonBox.setVisible(false);
        fTopButtonBox.setPrefHeight(0);
        fLowerButtonBox.getChildren().removeAll(fNewButton, fEditButton, fDeleteButton);

    }

    private void loadData() {
        List<ReturnTransaction> list = daoReturnTransaction.read(ReturnTransaction_.store, Config.getStore(), ReturnTransaction_.status, DBConstants.STATUS_CLOSE);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }
}

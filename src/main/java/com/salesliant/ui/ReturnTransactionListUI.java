package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction_;
import com.salesliant.util.DBConstants;
import java.util.List;
import javafx.collections.FXCollections;

public class ReturnTransactionListUI extends ReturnTransactionBaseListUI {

    public ReturnTransactionListUI() {
        loadData();
        createGUI();
        fLowerButtonBox.getChildren().remove(fCloseButton);
    }

    private void loadData() {
        List<ReturnTransaction> list = daoReturnTransaction.read(ReturnTransaction_.store, Config.getStore(), ReturnTransaction_.status, DBConstants.STATUS_OPEN);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }
}

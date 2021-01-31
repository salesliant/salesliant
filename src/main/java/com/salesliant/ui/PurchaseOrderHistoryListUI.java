package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.SearchField;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;

public final class PurchaseOrderHistoryListUI extends PurchaseOrderHistoryListBaseUI {

    private final SearchField searchField = new SearchField();

    public PurchaseOrderHistoryListUI() {
        loadData();
        createGUI();
        topBox.getChildren().add(searchField);
        searchField.setPrefWidth(500);
        Platform.runLater(() -> searchField.requestFocus());
    }

    @Override
    protected void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<PurchaseOrderHistory> list = daoPurchaseOrderHistory.readOrderBy(PurchaseOrderHistory_.store, Config.getStore(),
                    PurchaseOrderHistory_.datePurchased, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setDisable(false);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }
}

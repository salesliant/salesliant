package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.entity.Vendor;
import com.salesliant.util.AppConstants;
import com.salesliant.widget.VendorWidget;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

public final class PurchaseOrderHistoryListByVendorUI extends PurchaseOrderHistoryListBaseUI {

    private final VendorWidget fVendorCombo = new VendorWidget();
    private List<PurchaseOrderHistory> fList;

    public PurchaseOrderHistoryListByVendorUI() {
        loadData();
        createGUI();
        fVendorCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Vendor> observable, Vendor newValue, Vendor oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fTableView.setPlaceholder(lblLoading);
                Platform.runLater(() -> {
                    fList = daoPurchaseOrderHistory.readOrderBy(PurchaseOrderHistory_.store, Config.getStore(), PurchaseOrderHistory_.vendor, observable.getValue(), PurchaseOrderHistory_.dateReceived, AppConstants.ORDER_BY_DESC);
                    fEntityList = FXCollections.observableList(fList);
                    fTableView.setItems(fEntityList);
                    fTableView.setPlaceholder(null);
                });
            } else {
                fEntityList = FXCollections.observableArrayList();
                fTableView.setItems(fEntityList);
            }
        });
        topBox.getChildren().add(fVendorCombo);
        fVendorCombo.setPrefWidth(500);
    }

    @Override
    protected void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            fList = daoPurchaseOrderHistory.readOrderBy(PurchaseOrderHistory_.store, Config.getStore(), PurchaseOrderHistory_.dateReceived, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(fList);
            fTableView.setItems(fEntityList);
            fTableView.setPlaceholder(null);
        });
    }
}

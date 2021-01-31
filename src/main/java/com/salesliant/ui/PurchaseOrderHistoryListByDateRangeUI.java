package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import static com.salesliant.util.BaseUtil.getString;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;

public final class PurchaseOrderHistoryListByDateRangeUI extends PurchaseOrderHistoryListBaseUI {

    private final Label rangeLabel = new Label();

    public PurchaseOrderHistoryListByDateRangeUI() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
        fFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
        fTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);
        loadData();
        createGUI();
        dateRangerButton.setVisible(true);
        topBox.getChildren().add(rangeLabel);
        rangeLabel.setPrefWidth(500);
    }

    @Override
    protected void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<PurchaseOrderHistory> list = daoPurchaseOrderHistory.readBetweenDate(PurchaseOrderHistory_.store, Config.getStore(),
                    PurchaseOrderHistory_.dateReceived, fFrom, fTo);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            updateRange();
            fTableView.setPlaceholder(null);
        });
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrder_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.DBConstants;
import java.util.List;
import javafx.collections.FXCollections;

public class OrderListUI extends SalesOrderListUI {

    public OrderListUI() {
        fOrderType = DBConstants.TYPE_SALESORDER_ORDER;
        List<SalesOrder> list = daoSalesOrder.readOrderBy(SalesOrder_.store, Config.getStore(), SalesOrder_.type, DBConstants.TYPE_SALESORDER_ORDER, SalesOrder_.salesOrderNumber, AppConstants.ORDER_BY_DESC);
        fEntityList = FXCollections.observableList(list);
        createGUI();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.ShippingService;
import com.salesliant.util.BaseDao;
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
public class ShippingServiceWidget extends ComboBox<ShippingService> {

    private final BaseDao<ShippingService> daoShippingService = new BaseDao(ShippingService.class);

    public ShippingServiceWidget() {
        setButtonCell(new ShippingServiceListCell());
        setCellFactory((ListView<ShippingService> parm) -> new ShippingServiceListCell());
        List<ShippingService> list = daoShippingService.read().stream()
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<ShippingService> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ShippingServiceListCell extends ListCell<ShippingService> {

        @Override
        protected void updateItem(ShippingService item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getCode());
            } else {
                setText("");
            }
        }
    }
}

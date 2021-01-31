/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.ShippingCarrier_;
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
public class ShippingCarrierWidget extends ComboBox<ShippingCarrier> {

    private final BaseDao<ShippingCarrier> daoShippingCarrier = new BaseDao(ShippingCarrier.class);

    public ShippingCarrierWidget() {
        setButtonCell(new ShippingCarrierListCell());
        setCellFactory((ListView<ShippingCarrier> parm) -> new ShippingCarrierListCell());
        List<ShippingCarrier> list = daoShippingCarrier.read(ShippingCarrier_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        ObservableList<ShippingCarrier> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ShippingCarrierListCell extends ListCell<ShippingCarrier> {

        @Override
        protected void updateItem(ShippingCarrier item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }

    public void reset() {
        setButtonCell(new ShippingCarrierListCell());
        setCellFactory((ListView<ShippingCarrier> parm) -> new ShippingCarrierListCell());
        List<ShippingCarrier> list = daoShippingCarrier.read(ShippingCarrier_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        ObservableList<ShippingCarrier> oList = FXCollections.observableList(list);
        setItems(oList);
    }
}

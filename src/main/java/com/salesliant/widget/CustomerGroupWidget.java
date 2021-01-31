/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.CustomerGroup;
import com.salesliant.entity.CustomerGroup_;
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
public class CustomerGroupWidget extends ComboBox<CustomerGroup> {

    private final BaseDao<CustomerGroup> daoCustomerGroup = new BaseDao<>(CustomerGroup.class);

    public CustomerGroupWidget() {
        List<CustomerGroup> list = daoCustomerGroup.read(CustomerGroup_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        setButtonCell(new CustomerGroupListCell());
        setCellFactory((ListView<CustomerGroup> parm) -> new CustomerGroupListCell());
        ObservableList<CustomerGroup> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class CustomerGroupListCell extends ListCell<CustomerGroup> {

        @Override
        protected void updateItem(CustomerGroup item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}

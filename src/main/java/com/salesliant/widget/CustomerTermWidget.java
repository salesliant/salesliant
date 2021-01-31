/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.CustomerTerm;
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
public class CustomerTermWidget extends ComboBox<CustomerTerm> {

    private final BaseDao<CustomerTerm> daoCustomerTerm = new BaseDao<>(CustomerTerm.class);

    public CustomerTermWidget() {

        setButtonCell(new CustomerTermListCell());
        setCellFactory((ListView<CustomerTerm> parm) -> new CustomerTermListCell());
        List<CustomerTerm> list = daoCustomerTerm.read().stream()
                .sorted((e1, e2) -> e1.getCode().compareToIgnoreCase(e2.getCode()))
                .collect(Collectors.toList());
        ObservableList<CustomerTerm> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class CustomerTermListCell extends ListCell<CustomerTerm> {

        @Override
        protected void updateItem(CustomerTerm item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getCode());
            }
        }
    }
}

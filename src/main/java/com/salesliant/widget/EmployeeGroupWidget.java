/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.EmployeeGroup_;
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
public class EmployeeGroupWidget extends ComboBox<EmployeeGroup> {

    private final BaseDao<EmployeeGroup> daoEmployeeGroup = new BaseDao(EmployeeGroup.class);

    public EmployeeGroupWidget() {
        setButtonCell(new EmployeeGroupListCell());
        setCellFactory((ListView<EmployeeGroup> parm) -> new EmployeeGroupListCell());
        List<EmployeeGroup> list = daoEmployeeGroup.read(EmployeeGroup_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<EmployeeGroup> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class EmployeeGroupListCell extends ListCell<EmployeeGroup> {

        @Override
        protected void updateItem(EmployeeGroup item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getDescription());
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.ReturnCode;
import com.salesliant.entity.ReturnCode_;
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
public class ReturnCodeWidget extends ComboBox<ReturnCode> {

    private final BaseDao<ReturnCode> daoReturnCode = new BaseDao(ReturnCode.class);

    public ReturnCodeWidget() {
        setButtonCell(new ReturnCodeListCell());
        setCellFactory((ListView<ReturnCode> parm) -> new ReturnCodeListCell());
        List<ReturnCode> list = daoReturnCode.read(ReturnCode_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<ReturnCode> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ReturnCodeListCell extends ListCell<ReturnCode> {

        @Override
        protected void updateItem(ReturnCode item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getDescription());
            }
        }
    }

}

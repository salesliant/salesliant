/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.FunctionAccess;
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
public class FunctionAccessWidget extends ComboBox<FunctionAccess> {

    private final BaseDao<FunctionAccess> daoFunctionAccess = new BaseDao<>(FunctionAccess.class);

    public FunctionAccessWidget() {
        setButtonCell(new FunctionAccessWidget.FunctionAccessListCell());
        setCellFactory((ListView<FunctionAccess> parm) -> new FunctionAccessWidget.FunctionAccessListCell());
        List<FunctionAccess> list = daoFunctionAccess.read().stream()
                .sorted((e1, e2) -> e1.getId().compareTo(e2.getId()))
                .collect(Collectors.toList());
        ObservableList<FunctionAccess> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class FunctionAccessListCell extends ListCell<FunctionAccess> {

        @Override
        protected void updateItem(FunctionAccess item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getModuleName());
            }
        }
    }
}

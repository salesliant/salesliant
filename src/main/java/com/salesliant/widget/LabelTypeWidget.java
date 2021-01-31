/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import com.salesliant.entity.LabelType;
import com.salesliant.util.BaseDao;
import java.util.stream.Collectors;

/**
 *
 * @author Lewis
 */
public class LabelTypeWidget extends ComboBox<LabelType> {

    private final BaseDao<LabelType> daoLabelType = new BaseDao(LabelType.class);
    private List<LabelType> list = null;

    public LabelTypeWidget() {
        setButtonCell(new LabelTypeListCell());
        setCellFactory((ListView<LabelType> parm) -> new LabelTypeListCell());
        list = daoLabelType.read().stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        ObservableList<LabelType> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class LabelTypeListCell extends ListCell<LabelType> {

        @Override
        protected void updateItem(LabelType item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}

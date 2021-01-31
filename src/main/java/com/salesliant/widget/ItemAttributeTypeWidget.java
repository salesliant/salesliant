/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.ItemAttributeType;
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
public class ItemAttributeTypeWidget extends ComboBox<ItemAttributeType> {

    private final BaseDao<ItemAttributeType> daoItemAttributeType = new BaseDao<>(ItemAttributeType.class);

    public ItemAttributeTypeWidget() {

        setButtonCell(new ItemAttributeTypeListCell());
        setCellFactory((ListView<ItemAttributeType> parm) -> new ItemAttributeTypeListCell());
        List<ItemAttributeType> list = daoItemAttributeType.read().stream()
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<ItemAttributeType> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ItemAttributeTypeListCell extends ListCell<ItemAttributeType> {

        @Override
        protected void updateItem(ItemAttributeType item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getDescription());
            }
        }
    }
}

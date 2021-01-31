/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.ItemAttributeType;
import com.salesliant.entity.ItemAttributeValue;
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
public class ItemAttributeValueWidget extends ComboBox<ItemAttributeValue> {

    public ItemAttributeValueWidget() {
    }

    public void setAttributeType(ItemAttributeType itemAttributeType) {

        setButtonCell(new ItemAttributeValueListCell());
        setCellFactory((ListView<ItemAttributeValue> parm) -> new ItemAttributeValueListCell());
        List<ItemAttributeValue> list = itemAttributeType.getItemAttributeValues().stream()
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<ItemAttributeValue> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ItemAttributeValueListCell extends ListCell<ItemAttributeValue> {

        @Override
        protected void updateItem(ItemAttributeValue item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getDescription());
            }
        }
    }
}

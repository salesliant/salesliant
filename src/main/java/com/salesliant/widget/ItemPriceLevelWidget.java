/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.ItemPriceLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author Lewis
 */
public class ItemPriceLevelWidget extends ComboBox<ItemPriceLevel> {

    public ItemPriceLevelWidget() {

        setButtonCell(new ItemPriceLevelListCell());
        setCellFactory((ListView<ItemPriceLevel> parm) -> new ItemPriceLevelListCell());
        ObservableList<ItemPriceLevel> oList = FXCollections.observableList(Config.getItemPriceLevel());
        setItems(oList);
    }

    class ItemPriceLevelListCell extends ListCell<ItemPriceLevel> {

        @Override
        protected void updateItem(ItemPriceLevel item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null && item.getCode() != null && item.getDescription() != null) {
                setText(item.getDescription());
            }
        }
    }

}

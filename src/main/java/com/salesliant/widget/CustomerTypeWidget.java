/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author Lewis
 */
public class CustomerTypeWidget extends ComboBox<String> {

    public CustomerTypeWidget() {
        setButtonCell(new CustomerTypeListCell());
        setCellFactory((ListView<String> parm) -> new CustomerTypeListCell());
        getItems().add("P");
        getItems().add("B");
        getItems().add("R");
        getItems().add("E");
    }

    class CustomerTypeListCell extends ListCell<String> {

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                if (item.equals("P")) {
                    setText("P---Personal");
                }
                if (item.equals("B")) {
                    setText("B---Business");
                }
                if (item.equals("R")) {
                    setText("R---Reseller");
                }
                if (item.equals("E")) {
                    setText("E---Employee");
                }
            }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;

public class CheckBoxCell<T> extends TableCell<T, T> {

    private final ObservableSet<T> selectedItems;
    private final CheckBox checkBox;

    public CheckBoxCell(ObservableSet<T> selectedItems) {
        this.selectedItems = selectedItems;
        this.checkBox = new CheckBox();
        checkBox.setOnAction((ActionEvent event) -> {
            if (checkBox.isSelected()) {
                selectedItems.add(getItem());
            } else {
                selectedItems.remove(getItem());
            }
        });

        selectedItems.addListener(new SetChangeListener<T>() {

            @Override
            public void onChanged(Change<? extends T> change) {
                T item = getItem();
                if (item != null) {
                    checkBox.setSelected(selectedItems.contains(item));
                }
            }

        });
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            checkBox.setSelected(selectedItems.contains(item));
            setGraphic(checkBox);
        }
    }
}

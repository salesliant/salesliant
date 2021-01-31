/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.test;

import com.salesliant.entity.Item;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Lewis
 */
public class TextSearchField extends TextField {

    private final String SEARCH_HINT_TEXT = "To search, please type the search term here ";

    public TextSearchField() {

    }

    public TextSearchField(TableView table) {
        setPromptText("To search, please type the search term here ");
        ObservableList<Item> data = table.getItems();
        textProperty().addListener((Observable observable) -> {
            if (textProperty().get().isEmpty()) {
                table.setItems(data);
                return;
            }
            ObservableList<Item> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Item, ?>> cols = table.getColumns();
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    String cellValue = col.getCellData(data.get(i)).toString();
                    cellValue = cellValue.toLowerCase();
                    if (cellValue.contains(textProperty().get().toLowerCase())) {
                        tableItems.add(data.get(i));
                        break;
                    }
                }
            }
            table.setItems(tableItems);
        });
        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                setText("");
            }
        });
    }

    public void setTableView(TableView table) {
        setPromptText("To search, please type the search term here ");
        ObservableList<Item> data = table.getItems();
        textProperty().addListener((Observable observable) -> {
            if (textProperty().get().isEmpty()) {
                table.setItems(data);
                return;
            }
            ObservableList<Item> tableItems = FXCollections.observableArrayList();
            ObservableList<TableColumn<Item, ?>> cols = table.getColumns();

            for (int i = 0; i < data.size(); i++) {

                for (int j = 0; j < cols.size(); j++) {
                    TableColumn col = cols.get(j);
                    if (col != null && col.getCellData(data.get(i)) != null) {
                        String cellValue = col.getCellData(data.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(textProperty().get().toLowerCase())) {
                            tableItems.add(data.get(i));
                            break;
                        }
                    }
                }
            }
            table.setItems(tableItems);
        });
        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                setText("");
            }
        });
    }
}

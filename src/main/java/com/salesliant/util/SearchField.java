/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import java.util.stream.Stream;
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
public class SearchField extends TextField {

    public SearchField() {

    }

    public SearchField(TableView table) {
        setPromptText("To search, please type the search term here ");
        ObservableList data = table.getItems();
        ObservableList<TableColumn<?, ?>> cols = table.getColumns();
        textProperty().addListener((Observable o) -> {
            if (textProperty().get().isEmpty()) {
                table.setItems(data);
                return;
            }
            ObservableList sublist = FXCollections.observableArrayList();
            ObservableList<TableColumn<?, ?>> cols1 = table.getColumns();
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < cols1.size(); j++) {
                    TableColumn col = cols1.get(j);
                    if (col != null && col.getCellData(data.get(i)) != null) {
                        String cellValue = col.getCellData(data.get(i)).toString().trim();
                        String lowerCaseFilter = textProperty().get().toLowerCase().trim();
                        String[] keywords = lowerCaseFilter.split(" ");
                        if (Stream.of(keywords).allMatch(cellValue.toLowerCase()::contains)) {
                            sublist.add(data.get(i));
                            break;
                        }
                    }
                }
            }
            table.setItems(sublist);
        });
        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                setText("");
            }
        });
    }

    public void setTableView(TableView table) {
        setPromptText("To search, please type the search term here ");
        ObservableList data = table.getItems();
        ObservableList<TableColumn<?, ?>> cols = table.getColumns();
        textProperty().addListener((Observable o) -> {
            if (textProperty().get().isEmpty()) {
                table.setItems(data);
                return;
            }
            ObservableList sublist = FXCollections.observableArrayList();
            ObservableList<TableColumn<?, ?>> cols1 = table.getColumns();
            for (int i = 0; i < data.size(); i++) {
                for (int j = 0; j < cols1.size(); j++) {
                    TableColumn col = cols1.get(j);
                    if (col.getCellData(data.get(i)) != null) {
                        String cellValue = col.getCellData(data.get(i)).toString().trim();
                        String lowerCaseFilter = textProperty().get().toLowerCase().trim();
                        String[] keywords = lowerCaseFilter.split(" ");
                        if (Stream.of(keywords).allMatch(cellValue.toLowerCase()::contains)) {
                            sublist.add(data.get(i));
                            break;
                        }
                    }
                }
            }
            table.setItems(sublist);
        });
        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                setText("");
            }
        });
    }
}

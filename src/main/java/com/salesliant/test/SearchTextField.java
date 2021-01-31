/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.test;

import java.util.function.Predicate;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Lewis
 */
public class SearchTextField extends TextField {

    private FilteredList<?> filteredData;

    public SearchTextField() {

    }

    public SearchTextField(TableView table) {
        setPromptText("To search, please type the search term here ");
        ObservableList data = table.getItems();
        filteredData = new FilteredList<>(data, p -> true);
        ObservableList<TableColumn<?, ?>> cols = table.getColumns();
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else {
                    ObservableList<TableColumn<?, ?>> cols1 = table.getColumns();
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < cols1.size(); j++) {
                            TableColumn col = cols1.get(j);
                            if (col.getCellData(data.get(i)) != null) {
                                String cellValue = col.getCellData(data.get(i)).toString().trim();
                                String lowerCaseFilter = textProperty().get().toLowerCase().trim();
                                String[] keywords = lowerCaseFilter.split(" ");
                                Predicate predicate = e -> {
                                    return Stream.of(keywords).allMatch(cellValue.toLowerCase()::contains);
                                };
                                return predicate.test(item);
                            }
                        }
                    }
                    return false;
                }
            });
        });
        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                setText("");
            }
        });
        SortedList<?> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void setTableView(TableView table) {
        setPromptText("To search, please type the search term here ");
        ObservableList data = table.getItems();
        filteredData = new FilteredList<>(data, p -> true);
        ObservableList<TableColumn<?, ?>> cols = table.getColumns();
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else {
                    ObservableList<TableColumn<?, ?>> cols1 = table.getColumns();
                    for (int i = 0; i < data.size(); i++) {
                        for (int j = 0; j < cols1.size(); j++) {
                            TableColumn col = cols1.get(j);
                            if (col.getCellData(data.get(i)) != null) {
                                String cellValue = col.getCellData(data.get(i)).toString().trim();
                                String lowerCaseFilter = textProperty().get().toLowerCase().trim();
                                String[] keywords = lowerCaseFilter.split(" ");
                                Predicate predicate = e -> {
                                    return Stream.of(keywords).allMatch(cellValue.toLowerCase()::contains);
                                };
                                return predicate.test(item);
                            }
                        }
                    }
//                    return false;
                }
                return false;
            });
        });
        setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.DELETE)) {
                setText("");
            }
        });
        SortedList<?> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
}

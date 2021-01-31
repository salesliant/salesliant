/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.entity.SerialNumber;
import java.util.stream.Stream;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author Lewis
 */
public class SerialNumberSearchField extends TextField {

    private final String SEARCH_HINT_TEXT = "To search, please type here.";
    private TableView fTableView;
    private FilteredList<SerialNumber> filteredData;

    public SerialNumberSearchField() {

    }

    public SerialNumberSearchField(TableView table) {
        fTableView = table;
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = fTableView.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sn -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (String.valueOf(sn.getSerialNumber()).toLowerCase().startsWith(newValue.toLowerCase())) {
                    return true;
                }
                return false;
            });
            if (filteredData.isEmpty()) {
                filteredData.setPredicate(sn -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    String[] keywords = lowerCaseFilter.split(" ");
                    return sn.getDescription() != null && Stream.of(keywords).allMatch(sn.getDescription().toLowerCase()::contains);
                });
            }
            if (filteredData.isEmpty()) {
                filteredData.setPredicate(sn -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    String[] keywords = lowerCaseFilter.split(" ");
                    return sn.getInvoiceNumber() != null && Stream.of(keywords).allMatch(String.valueOf(sn.getInvoiceNumber())::contains);
                });
            }
        });
        SortedList<SerialNumber> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(fTableView.comparatorProperty());
        fTableView.setItems(sortedData);
    }

    public void setTableView(TableView table) {
        fTableView = table;
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = fTableView.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sn -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (String.valueOf(sn.getSerialNumber()).toLowerCase().startsWith(newValue.toLowerCase())) {
                    return true;
                }
                return false;
            });
            if (filteredData.isEmpty()) {
                filteredData.setPredicate(sn -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    String[] keywords = lowerCaseFilter.split(" ");
                    return sn.getDescription() != null && Stream.of(keywords).allMatch(sn.getDescription().toLowerCase()::contains);
                });
            }
            if (filteredData.isEmpty()) {
                filteredData.setPredicate(sn -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    String[] keywords = lowerCaseFilter.split(" ");
                    return sn.getInvoiceNumber() != null && Stream.of(keywords).allMatch(String.valueOf(sn.getInvoiceNumber())::contains);
                });
            }
        });
        SortedList<SerialNumber> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(fTableView.comparatorProperty());
        fTableView.setItems(sortedData);
    }
}

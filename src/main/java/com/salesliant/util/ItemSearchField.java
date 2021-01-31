/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.entity.Item;
import static com.salesliant.util.BaseUtil.getString;
import java.util.function.Predicate;
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
public class ItemSearchField extends TextField {

    private final String SEARCH_HINT_TEXT = "To search, please type here.";
    private TableView fTableView;
    private FilteredList<Item> filteredData;

    public ItemSearchField() {

    }

    public ItemSearchField(TableView table) {
        fTableView = table;
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = fTableView.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else if (String.valueOf(item.getItemLookUpCode()).toLowerCase().startsWith(newValue.toLowerCase())) {
                    return true;
                }
                return false;
            });
            if (filteredData.isEmpty()) {
                filteredData.setPredicate(item -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    String[] keywords = lowerCaseFilter.split(" ");
                    return item.getDescription() != null && Stream.of(keywords).allMatch(getString(item.getDescription()).toLowerCase()::contains);
                });
            }
        });
        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(fTableView.comparatorProperty());
        fTableView.setItems(sortedData);
    }

    public void setTableView(TableView table) {
        fTableView = table;
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = fTableView.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Item item) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                } else {
                    String lowerCaseFilter = newValue.toLowerCase();
                    Predicate predicateSKU = e -> {
                        return String.valueOf(item.getItemLookUpCode()).toLowerCase().startsWith(lowerCaseFilter);
                    };
                    return predicateSKU.test(item);
                }
            });
            if (filteredData.isEmpty()) {
                filteredData.setPredicate((Item item) -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    } else {
                        String lowerCaseFilter = newValue.toLowerCase();
                        String[] keywords = lowerCaseFilter.split(" ");
                        Predicate predicateDescription = e -> {
                            return Stream.of(keywords).allMatch(getString(item.getDescription()).toLowerCase()::contains);
                        };
                        return predicateDescription.test(item);
                    }
                });
            }
        });
        SortedList<Item> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(fTableView.comparatorProperty());
        fTableView.setItems(sortedData);
    }
}

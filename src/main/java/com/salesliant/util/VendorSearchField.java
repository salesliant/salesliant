/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.entity.Vendor;
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
public class VendorSearchField extends TextField {

    private final String SEARCH_HINT_TEXT = "To search, please type here.";
    private final TableView fTableView;
    private FilteredList<Vendor> filteredData;

    public VendorSearchField(TableView table) {
        fTableView = table;
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = fTableView.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vendor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String[] keywords = lowerCaseFilter.split(" ");
                if (vendor.getAccountNumber() != null && Stream.of(keywords).allMatch(vendor.getAccountNumber().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getCompany() != null && Stream.of(keywords).allMatch(vendor.getCompany().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getPhoneNumber() != null && Stream.of(keywords).allMatch(vendor.getPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getAddress1() != null && Stream.of(keywords).allMatch(vendor.getAddress1().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getAddress2() != null && Stream.of(keywords).allMatch(vendor.getAddress2().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getCity() != null && Stream.of(keywords).allMatch(vendor.getCity().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getState() != null && Stream.of(keywords).allMatch(vendor.getState().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getVendorContactName() != null && Stream.of(keywords).allMatch(vendor.getVendorContactName().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getPostCode() != null && Stream.of(keywords).allMatch(vendor.getPostCode().toLowerCase()::contains)) {
                    return true;
                } else if (vendor.getEmailAddress() != null && Stream.of(keywords).allMatch(vendor.getEmailAddress().toLowerCase()::contains)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Vendor> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(fTableView.comparatorProperty());
        fTableView.setItems(sortedData);
    }
}

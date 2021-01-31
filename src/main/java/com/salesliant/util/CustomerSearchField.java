/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.entity.Customer;
import static com.salesliant.util.BaseUtil.getString;
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
public class CustomerSearchField extends TextField {

    private final String SEARCH_HINT_TEXT = "To search, please type here. You can search by account number, name, address, city, state, phone number, zipcode and email address. ";
    private FilteredList<Customer> filteredData;

    public CustomerSearchField() {

    }

    public CustomerSearchField(TableView table) {
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = table.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String[] keywords = lowerCaseFilter.split(" ");
                String firstLastName = (getString(customer.getFirstName()) + " " + getString(customer.getLastName())).trim();
                if (customer.getFirstName() != null && Stream.of(keywords).allMatch(customer.getFirstName().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getLastName() != null && Stream.of(keywords).allMatch(customer.getLastName().toLowerCase()::contains)) {
                    return true;
                } else if (!firstLastName.isEmpty() && Stream.of(keywords).allMatch(firstLastName.toLowerCase()::contains)) {
                    return true;
                } else if (customer.getAccountNumber() != null && Stream.of(keywords).allMatch(customer.getAccountNumber().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getCompany() != null && Stream.of(keywords).allMatch(customer.getCompany().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getPhoneNumber() != null && Stream.of(keywords).allMatch(customer.getPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getAddress1() != null && Stream.of(keywords).allMatch(customer.getAddress1().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getAddress2() != null && Stream.of(keywords).allMatch(customer.getAddress2().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getCity() != null && Stream.of(keywords).allMatch(customer.getCity().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getState() != null && Stream.of(keywords).allMatch(customer.getState().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getCellPhoneNumber() != null && Stream.of(keywords).allMatch(customer.getCellPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getDepartment() != null && Stream.of(keywords).allMatch(customer.getDepartment().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getPostCode() != null && Stream.of(keywords).allMatch(customer.getPostCode().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getEmailAddress() != null && Stream.of(keywords).allMatch(customer.getEmailAddress().toLowerCase()::contains)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void setTableView(TableView table) {
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = table.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String[] keywords = lowerCaseFilter.split(" ");
                String firstLastName = (getString(customer.getFirstName()) + " " + getString(customer.getLastName())).trim();
                if (customer.getFirstName() != null && Stream.of(keywords).allMatch(customer.getFirstName().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getLastName() != null && Stream.of(keywords).allMatch(customer.getLastName().toLowerCase()::contains)) {
                    return true;
                } else if (!firstLastName.isEmpty() && Stream.of(keywords).allMatch(firstLastName.toLowerCase()::contains)) {
                    return true;
                } else if (customer.getAccountNumber() != null && Stream.of(keywords).allMatch(customer.getAccountNumber().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getCompany() != null && Stream.of(keywords).allMatch(customer.getCompany().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getPhoneNumber() != null && Stream.of(keywords).allMatch(customer.getPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getAddress1() != null && Stream.of(keywords).allMatch(customer.getAddress1().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getAddress2() != null && Stream.of(keywords).allMatch(customer.getAddress2().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getCity() != null && Stream.of(keywords).allMatch(customer.getCity().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getState() != null && Stream.of(keywords).allMatch(customer.getState().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getCellPhoneNumber() != null && Stream.of(keywords).allMatch(customer.getCellPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getDepartment() != null && Stream.of(keywords).allMatch(customer.getDepartment().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getPostCode() != null && Stream.of(keywords).allMatch(customer.getPostCode().toLowerCase()::contains)) {
                    return true;
                } else if (customer.getEmailAddress() != null && Stream.of(keywords).allMatch(customer.getEmailAddress().toLowerCase()::contains)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
}

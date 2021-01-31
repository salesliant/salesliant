/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.entity.Invoice;
import static com.salesliant.util.BaseUtil.getDateFormat;
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
public class InvoiceSearchField extends TextField {

    private final String SEARCH_HINT_TEXT = "To search, please type here.";
    private FilteredList<Invoice> filteredData;

    public InvoiceSearchField() {
    }

    public InvoiceSearchField(TableView table) {
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = table.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(invoice -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String[] keywords = lowerCaseFilter.split(" ");
                if (invoice.getInvoiceNumber() != null && Stream.of(keywords).allMatch(invoice.getInvoiceNumber().toString()::contains)) {
                    return true;
                } else if (invoice.getCustomerPoNumber() != null && Stream.of(keywords).allMatch(invoice.getCustomerPoNumber().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getCustomerAccountNumber() != null && Stream.of(keywords).allMatch(invoice.getCustomerAccountNumber().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getSalesName() != null && Stream.of(keywords).allMatch(invoice.getSalesName().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getPhoneNumber() != null && Stream.of(keywords).allMatch(invoice.getPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getCustomerName() != null && Stream.of(keywords).allMatch(invoice.getCustomerName().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToCompany() != null && Stream.of(keywords).allMatch(invoice.getBillToCompany().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToAddress1() != null && Stream.of(keywords).allMatch(invoice.getBillToAddress1().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToCity() != null && Stream.of(keywords).allMatch(invoice.getBillToCity().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToState() != null && Stream.of(keywords).allMatch(invoice.getBillToState().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToPostCode() != null && Stream.of(keywords).allMatch(invoice.getBillToPostCode().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getOrderNumber() != null && Stream.of(keywords).allMatch(invoice.getOrderNumber().toString().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getDateInvoiced() != null && Stream.of(keywords).allMatch(getDateFormat().format(invoice.getDateInvoiced()).toLowerCase()::contains)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Invoice> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }

    public void setTableView(TableView table) {
        setPromptText(SEARCH_HINT_TEXT);
        ObservableList oList = table.getItems();
        filteredData = new FilteredList<>(oList, p -> true);
        textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(invoice -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                String[] keywords = lowerCaseFilter.split(" ");
                if (invoice.getInvoiceNumber() != null && Stream.of(keywords).allMatch(invoice.getInvoiceNumber().toString()::contains)) {
                    return true;
                } else if (invoice.getCustomerPoNumber() != null && Stream.of(keywords).allMatch(invoice.getCustomerPoNumber().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getCustomerAccountNumber() != null && Stream.of(keywords).allMatch(invoice.getCustomerAccountNumber().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getSalesName() != null && Stream.of(keywords).allMatch(invoice.getSalesName().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getPhoneNumber() != null && Stream.of(keywords).allMatch(invoice.getPhoneNumber().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getCustomerName() != null && Stream.of(keywords).allMatch(invoice.getCustomerName().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToCompany() != null && Stream.of(keywords).allMatch(invoice.getBillToCompany().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToAddress1() != null && Stream.of(keywords).allMatch(invoice.getBillToAddress1().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToCity() != null && Stream.of(keywords).allMatch(invoice.getBillToCity().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToState() != null && Stream.of(keywords).allMatch(invoice.getBillToState().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getBillToPostCode() != null && Stream.of(keywords).allMatch(invoice.getBillToPostCode().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getOrderNumber() != null && Stream.of(keywords).allMatch(invoice.getOrderNumber().toString().toLowerCase()::contains)) {
                    return true;
                } else if (invoice.getDateInvoiced() != null && Stream.of(keywords).allMatch(getDateFormat().format(invoice.getDateInvoiced()).toLowerCase()::contains)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Invoice> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
    }
}

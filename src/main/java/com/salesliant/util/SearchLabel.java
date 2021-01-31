/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Lewis
 */
public class SearchLabel extends Label {

    private final String SEARCH_HINT_TEXT = "To search, please type the first few characters of the ";
    private final String SEARCH_ENABLE_HINT_TEXT = "To enable search function, click on the table column header!";
    private final TableView fTable;
    private String labelString = "";
    private boolean dirty_ = false;

    @SuppressWarnings("unchecked")
    public SearchLabel(TableView table) {
        fTable = table;
        labelString += SEARCH_ENABLE_HINT_TEXT;
        setText(labelString);
        fTable.getSortOrder().addListener((ListChangeListener.Change c) -> {
            clearSearchLabel();
        });

        fTable.setOnKeyReleased((KeyEvent ke) -> {
            if (getSortedColumn() != null) {
                if (!ke.getText().isEmpty()) {
                    if (!SearchLabel.this.dirty_) {
                        labelString = "";
                    }
                    labelString += ke.getText();
                    SearchLabel.this.dirty_ = true;
                } else if (ke.getCode() == KeyCode.BACK_SPACE) {
                    if (!SearchLabel.this.dirty_) {
                        // Ignore BACK_SPACE key
                    } else if (labelString.length() > 1) {
                        labelString = labelString.substring(0, labelString.length() - 1);
                    } else if (labelString.length() <= 1) {
                        // s = "";
                        clearSearchLabel();
                    }
                }
                SearchLabel.this.setText(labelString);
                search();
            }
        });
    }

    private void clearSearchLabel() {
        dirty_ = false;
        if (getSortedColumn() == null) {
            labelString = SEARCH_ENABLE_HINT_TEXT;
        } else {
            labelString = SEARCH_HINT_TEXT + getSortedColumn().getText();
        }
        this.setText(labelString);
    }

    private void search() {
        TableColumn c = getSortedColumn();
        if (c != null && !fTable.getItems().isEmpty()) {
            int size = fTable.getItems().size();
            for (int i = 0; i < size; i++) {
                String cellString = (String) c.getCellData(i);
                cellString = cellString == null ? "" : cellString;
                labelString = labelString == null ? "" : labelString;
                if (cellString.toLowerCase().contains(labelString.toLowerCase())) {
                    fTable.getSelectionModel().select(i);
                }
            }
        }
    }

    private TableColumn getSortedColumn() {
        fTable.getSortOrder();
        if (fTable.getSortOrder().isEmpty()) {
            return null;
        } else {
            return (TableColumn) fTable.getSortOrder().get(0);
        }
    }
}

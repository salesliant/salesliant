package com.salesliant.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;

public class DatePickerCell<S> implements Callback<TableColumn<S, Date>, TableCell<S, Date>> {

    private DatePicker datePicker;

    public DatePickerCell() {
    }

    @Override
    public TableCell<S, Date> call(TableColumn<S, Date> param) {
        TableCell<S, Date> cell = new TableCell<S, Date>() {
            @Override
            public void startEdit() {
                if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
                    return;
                }
                super.startEdit();
                if (isEditing()) {
                    createDatePicker();
                    setText(null);
                    setGraphic(datePicker);
                    Platform.runLater(() -> {
                        datePicker.requestFocus();
                    });
                }
            }

            @Override
            public void cancelEdit() {
                super.cancelEdit();
                setText(getDate().toString());
                setGraphic(null);
            }

            @Override
            protected void updateItem(Date item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (isEditing()) {
                        if (datePicker != null) {
                            datePicker.setValue(getDate());
                        }
                        setText(null);
                        setGraphic(datePicker);
                    } else {
                        setText(getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
                        setGraphic(null);
                    }
                }
            }

            private void createDatePicker() {
                datePicker = new DatePicker(getDate());

                datePicker.setOnAction(event -> {
                    if (datePicker.getValue() != null) {
                        commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                    }
                });
                datePicker.getEditor().setOnKeyTyped((final KeyEvent keyEvent) -> {
                    if (keyEvent.getCharacter().contains("\r") || keyEvent.getCharacter().contains("\t")) {
                        TableColumn nextColumn = getNextColumn();
                        if (nextColumn != null) {
                            getTableView().refresh();
                            getTableView().requestFocus();
                            getTableView().getFocusModel().focus(getTableRow().getIndex(), nextColumn);
                            getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                            getTableView().edit(getTableRow().getIndex(), nextColumn);
                        }
                    }
                });
                datePicker.getEditor().focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue) {
                        if (datePicker.getValue() != null) {
                            commitEdit(Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        }
                    }
                });
            }

            private TableColumn<S, ?> getNextColumn() {
                List<TableColumn<S, ?>> columns = new ArrayList<>();
                getTableView().getColumns().stream().filter((column) -> (column.isEditable() && column.isVisible())).forEachOrdered((column) -> {
                    columns.addAll(getLeaves(column));
                });
                if (columns.size() < 2) {
                    return null;
                }
                int currentIndex = columns.indexOf(getTableColumn());
                int nextIndex = currentIndex;
                nextIndex++;
                if (nextIndex > columns.size() - 1) {
                    if (columns.size() > 1) {
                        nextIndex = 1;
                    } else {
                        nextIndex = 0;
                    }
                }
                if (nextIndex < 0) {
                    nextIndex = 0;
                }
                return columns.get(nextIndex);
            }

            private List<TableColumn<S, ?>> getLeaves(TableColumn<S, ?> root) {
                List<TableColumn<S, ?>> columns = new ArrayList<>();
                if (root.getColumns().isEmpty()) {
                    if (root.isEditable()) {
                        columns.add(root);
                    }
                    return columns;
                } else {
                    root.getColumns().forEach((column) -> {
                        columns.addAll(getLeaves(column));
                    });
                    return columns;
                }
            }

            private LocalDate getDate() {
                return getItem() == null ? LocalDate.now() : getItem().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        };
        return cell;
    }
}

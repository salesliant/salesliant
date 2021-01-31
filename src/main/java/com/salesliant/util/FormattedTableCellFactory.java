/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import static com.salesliant.util.BaseUtil.getDateTimeFormat;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getDateFormat;
import java.util.Date;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class FormattedTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private Pos alignment;
    private Format format;

    public FormattedTableCellFactory(NumberFormat format, Pos alignment) {
        this.format = format;
        this.alignment = alignment;
    }

    public FormattedTableCellFactory(DateFormat format, Pos alignment) {
        this.format = format;
        this.alignment = alignment;
    }

    public FormattedTableCellFactory(Pos alignment) {
        this.alignment = alignment;
    }

    public Pos getAlignment() {
        return alignment;
    }

    public void setAlignment(Pos alignment) {
        this.alignment = alignment;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {

            @Override
            public void updateItem(Object item, boolean empty) {
                if (item == getItem()) {
                    return;
                }
                super.updateItem((T) item, empty);
                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                } else if (format != null) {
                    super.setText(format.format(item));
                } else if (item instanceof Date) {
                    if (item instanceof Timestamp) {
                        super.setText(getDateTimeFormat().format(item));
                    } else {
                        super.setText(getDateFormat().format(item));
                    }
                } else if (item instanceof BigDecimal) {
                    super.setText(getDecimalFormat().format(item));
                } else if (item instanceof Double) {
                    super.setText(getDecimalFormat().format(item));
                } else if (item instanceof Node) {
                    super.setText(null);
                    super.setGraphic((Node) item);
                } else if (item instanceof Boolean) {
                    CheckBox checkBox = new CheckBox();
                    if ((Boolean) item) {
                        checkBox.setSelected(true);
                    } else {
                        checkBox.setSelected(false);
                    }
                    checkBox.setDisable(true);
                    setAlignment(alignment);
                    setGraphic(checkBox);
                } else {
                    super.setText(item.toString());
                    super.setGraphic(null);
                }
            }
        };
        cell.setAlignment(alignment);
        return cell;
    }
}

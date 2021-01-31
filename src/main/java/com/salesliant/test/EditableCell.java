/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.test;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorItem;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseListUI.getItemPrice;
import static com.salesliant.util.BaseListUI.getQuantity;
import static com.salesliant.util.BaseListUI.getQuantityCommitted;
import static com.salesliant.util.BaseUtil.decimalFilter;
import static com.salesliant.util.BaseUtil.integerFilter;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EditableCell<S, T> extends TableCell<S, T> {

    private NumberFormat format;
    private TextField textField;
    private Pos alignment = Pos.TOP_CENTER;
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);

    public EditableCell(NumberFormat nf) {
        this.format = nf;
        this.setAlignment(alignment);
    }

    public EditableCell(NumberFormat nf, Pos pos) {
        this.format = nf;
        this.alignment = pos;
        this.setAlignment(alignment);
    }

    public EditableCell() {
        this.setAlignment(alignment);
    }

    public EditableCell(Pos pos) {
        this.alignment = pos;
        this.setAlignment(alignment);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        createTextField();
        setText(null);
        setGraphic(textField);
        Platform.runLater(() -> {
            textField.selectAll();
            textField.requestFocus();
        });
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        try {
            setText(getItem().toString());
        } catch (Exception e) {
        }
        setGraphic(null);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(null);
            }
            super.setAlignment(alignment);
        }
    }

    private void createTextField() {
        textField = new TextField();
        textField.setAlignment(Pos.CENTER_RIGHT);
        if (format != null) {
            if (format.isParseIntegerOnly()) {
                TextFormatter<Integer> integerFormatter = new TextFormatter<>(integerFilter);
                textField.setTextFormatter(integerFormatter);
            } else {
                TextFormatter<BigDecimal> decimalFormatter = new TextFormatter<>(decimalFilter);
                textField.setTextFormatter(decimalFormatter);
            }
        }
        textField.setText(getString());
        textField.setOnKeyPressed((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            } else if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                TableRow row = getTableRow();
                TableColumn tc = getTableColumn();
                TableView table = getTableView();
                Boolean flag = true;
                if (tc.equals(table.getColumns().get(0)) && textField.getText() != null && !textField.getText().isEmpty() && row.getItem() instanceof SalesOrderEntry) {
                    flag = false;
                    SalesOrderEntry soe = (SalesOrderEntry) row.getItem();
                    Item aItem = daoItem.find(Item_.itemLookUpCode, textField.getText());
                    if (aItem != null) {
                        flag = true;
                        soe.setItem(aItem);
                        soe.setDiscountRate(BigDecimal.ZERO);
                        soe.setQuantity(BigDecimal.ONE);
                        soe.setPrice(getItemPrice(aItem, soe.getSalesOrder().getCustomer()));
                        if (soe.getSalesOrder().getType().equals(DBConstants.TYPE_SALESORDER_INVOICE) && !Config.getStore().getAllowZeroQtySale()) {
                            BigDecimal qty = getQuantity(aItem).subtract(getQuantityCommitted(aItem)).subtract(zeroIfNull(soe.getQuantity()));
                            if (qty.compareTo(BigDecimal.ZERO) > 0) {
                                soe.setQuantity(qty);
                            } else {
                                soe.setQuantity(BigDecimal.ZERO);
                            }
                        }
                    }
                }
                if (tc.equals(table.getColumns().get(0)) && textField.getText() != null && !textField.getText().isEmpty() && row.getItem() instanceof PurchaseOrderEntry) {
                    flag = false;
                    PurchaseOrderEntry poe = (PurchaseOrderEntry) getTableRow().getItem();
                    Vendor vendor = poe.getPurchaseOrder().getVendor();
                    VendorItem vi = vendor.getVendorItems().stream()
                            .filter(e -> e.getVendorItemLookUpCode().equalsIgnoreCase(textField.getText())
                            || (e.getItem() != null && e.getItem().getItemLookUpCode().equalsIgnoreCase(textField.getText())))
                            .findFirst().orElse(null);
                    if (vi != null) {
                        flag = true;
                        poe.setItem(vi.getItem());
                        poe.setItemLookUpCode(vi.getItem().getItemLookUpCode());
                        poe.setItemDescription(vi.getItem().getDescription());
                        poe.setVendorItemLookUpCode(vi.getVendorItemLookUpCode());
                        poe.setUnitOfMeasure(vi.getItem().getUnitOfMeasure());
                        poe.setCost(vi.getCost());
                        Optional<PurchaseOrderEntry> answer = Optional.ofNullable(poe).filter(p -> p.getPurchaseOrder().getStatus().equals(DBConstants.STATUS_OPEN));
                        if (answer.isPresent()) {
                            poe.setQuantityOrdered(BigDecimal.ONE);
                        } else {
                            poe.setQuantityReceived(BigDecimal.ONE);
                        }
                        poe.setWeight(vi.getItem().getWeight());
//                        poe.setLocation(Config.getStore().getId());
                    }
                }
                if (tc.equals(table.getColumns().get(0)) && textField.getText() != null && !textField.getText().isEmpty() && row.getItem() instanceof TransferOrderEntry) {
                    flag = false;
                    TransferOrderEntry toe = (TransferOrderEntry) row.getItem();
                    Item aItem = daoItem.find(Item_.itemLookUpCode, textField.getText());
                    if (aItem != null) {
                        flag = true;
                        toe.setItem(aItem);
                        toe.setItemLookUpCode(aItem.getItemLookUpCode());
                        toe.setCost(aItem.getCost());
                        toe.setItemDescription(aItem.getDescription());
                        Optional<TransferOrderEntry> answer = Optional.ofNullable(toe).filter(p -> p.getTransferOrder().getStatus().equals(DBConstants.STATUS_OPEN));
                        if (answer.isPresent()) {
                            toe.setQuantity(BigDecimal.ONE);
                        } else {
                            toe.setQuantityReceived(BigDecimal.ONE);
                        }
                    }
                }
                if (flag) {
                    commitEdit((T) getConverter().fromString(textField.getText()));
                    TableColumn nextColumn = getNextColumn();
                    if (nextColumn != null) {
                        getTableView().refresh();
                        getTableView().requestFocus();
                        getTableView().getFocusModel().focus(getTableRow().getIndex(), nextColumn);
                        getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
                        getTableView().edit(getTableRow().getIndex(), nextColumn);
                    }
                } else {
                    t.consume();
                }
//                    commitEdit((T) getConverter().fromString(textField.getText()));
//                    TableColumn nextColumn = getNextColumn();
//                    if (nextColumn != null) {
//                        getTableView().refresh();
//                        getTableView().requestFocus();
//                        getTableView().getFocusModel().focus(getTableRow().getIndex(), nextColumn);
//                        getTableView().getSelectionModel().select(getTableRow().getIndex(), nextColumn);
//                        getTableView().edit(getTableRow().getIndex(), nextColumn);
//                    }
            } else if (!t.isControlDown() && t.getCode() == KeyCode.UP) {
                commitEdit((T) getConverter().fromString(textField.getText()));
                getTableView().getSelectionModel().selectAboveCell();
                getTableView().requestFocus();
            } else if (!t.isControlDown() && t.getCode() == KeyCode.DOWN) {
                commitEdit((T) getConverter().fromString(textField.getText()));
                getTableView().getSelectionModel().selectBelowCell();
                getTableView().requestFocus();
            }
        });
        textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (!newValue && textField != null) {
                commitEdit((T) getConverter().fromString(textField.getText()));
            }
        });
    }

    private String getString() {
        if (format == null || format.isParseIntegerOnly()) {
            return getItem() == null ? "" : getItem().toString();
        } else {
            return getItem() == null ? "" : format.format(getItem());
        }
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

    private StringConverter getConverter() {
        if (format == null) {
            return new DefaultStringConverter();
        } else {
            if (format.isParseIntegerOnly()) {
                return new IntegerStringConverter();
            } else {
                return new BigDecimalStringConverter();
            }
        }
    }
}

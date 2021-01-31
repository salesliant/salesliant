/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

/**
 *
 * @author Lewis
 */
import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.Item_;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.SerialNumber_;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorItem;
import static com.salesliant.util.BaseListUI.getItemDescription;
import static com.salesliant.util.BaseListUI.getItemPrice;
import static com.salesliant.util.BaseUtil.decimalFilter;
import static com.salesliant.util.BaseUtil.getDateFormat;
import static com.salesliant.util.BaseUtil.getDateTimeFormat;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getIntegerFormat;
import static com.salesliant.util.BaseUtil.integerFilter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class EditableTextFieldTableCell<S, T> implements Callback<TableColumn<S, T>, TextFieldTableCell<S, T>> {

    private TextField textField;
    private final Pos alignment;
    private TablePosition< S, ?> tablePos = null;
    private StringConverter<?> stringConverter;
    private NumberFormat format;
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<SerialNumber> daoSerialNumber = new BaseDao<>(SerialNumber.class);

    public EditableTextFieldTableCell(NumberFormat format, Pos alignment) {
        this.format = format;
        this.alignment = alignment;
    }

    public EditableTextFieldTableCell(Pos alignment) {
        this.alignment = alignment;
    }

    @Override
    public TextFieldTableCell<S, T> call(TableColumn<S, T> p) {
        TextFieldTableCell<S, T> cell = new TextFieldTableCell<S, T>() {

            @Override
            public void startEdit() {
                if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
                    return;
                }
                super.startEdit();
                if (isEditing()) {
                    createTextField();
                    final TableView< S> table = getTableView();
                    tablePos = table.getEditingCell();
                    if (format == null) {
                        Object obj = tablePos.getTableColumn().getCellData(tablePos.getRow());
                        if (obj != null && obj.getClass() == BigDecimal.class) {
                            stringConverter = new BigDecimalStringConverter();
                            TextFormatter<BigDecimal> decimalFormatter = new TextFormatter<>(decimalFilter);
                            textField.setTextFormatter(decimalFormatter);
                        } else if (obj != null && obj.getClass() == Integer.class) {
                            stringConverter = new IntegerStringConverter();
                            TextFormatter<Integer> integerFormatter = new TextFormatter<>(integerFilter);
                            textField.setTextFormatter(integerFormatter);
                        } else if (obj != null && obj.getClass() == Double.class) {
                            stringConverter = new DoubleStringConverter();
                            TextFormatter<Integer> doubleFormatter = new TextFormatter<>(decimalFilter);
                            textField.setTextFormatter(doubleFormatter);
                        } else {
                            stringConverter = new DefaultStringConverter();
                        }
                    } else {
                        if (format.isParseIntegerOnly()) {
                            stringConverter = new IntegerStringConverter();
                            TextFormatter<Integer> integerFormatter = new TextFormatter<>(integerFilter);
                            textField.setTextFormatter(integerFormatter);
                        } else {
                            stringConverter = new BigDecimalStringConverter();
                            TextFormatter<BigDecimal> decimalFormatter = new TextFormatter<>(decimalFilter);
                            textField.setTextFormatter(decimalFormatter);
                        }
                    }
                    setText(null);
                    setGraphic(textField);
                    Platform.runLater(() -> {
                        textField.selectAll();
                        textField.requestFocus();
                    });
                }
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
                            textField.setText(getItemText());
                        }
                        setText(null);
                        setGraphic(textField);
                    } else {
                        setText(getItemText());
                        setGraphic(null);
                    }
                    super.setAlignment(alignment);
                }
            }

            private void createTextField() {
                textField = new TextField();
                textField.setAlignment(Pos.CENTER_RIGHT);
                textField.setText(getItemText());
                textField.setOnKeyPressed((KeyEvent t) -> {
                    if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    } else if (t.getCode() == KeyCode.TAB || t.getCode() == KeyCode.ENTER) {
                        TableRow row = getTableRow();
                        TableColumn tc = getTableColumn();
                        TableView table = getTableView();
                        Boolean flag = true;
                        if (row.getItem() instanceof SalesOrderEntry && tc.equals(table.getColumns().get(0))) {
                            if (!textField.getText().isEmpty()) {
                                flag = false;
                                SalesOrderEntry soe = (SalesOrderEntry) row.getItem();
                                Item aItem = daoItem.find(Item_.itemLookUpCode, textField.getText());
                                if (aItem != null) {
                                    flag = true;
                                    soe.setItem(aItem);
                                    soe.setDiscountRate(BigDecimal.ZERO);
                                    soe.setQuantity(BigDecimal.ONE);
                                    soe.setCost(aItem.getCost());
                                    soe.setPrice(getItemPrice(aItem, soe.getSalesOrder().getCustomer()));
                                }
                            } else {
                                flag = false;
                            }
                        }
                        if (row.getItem() instanceof PurchaseOrderEntry && tc.equals(table.getColumns().get(0))) {
                            if (!textField.getText().isEmpty()) {
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
                                    poe.setItemDescription(getItemDescription(vi.getItem()));
                                    poe.setVendorItemLookUpCode(vi.getVendorItemLookUpCode());
                                    poe.setUnitOfMeasure(vi.getItem().getUnitOfMeasure());
                                    poe.setCost(vi.getCost());
                                    Optional<PurchaseOrderEntry> answer = Optional.ofNullable(poe).filter(p -> p.getPurchaseOrder().getStatus().equals(DBConstants.STATUS_OPEN));
                                    if (answer.isPresent()) {
                                        poe.setQuantityOrdered(BigDecimal.ONE);
                                    } else {
                                        poe.setQuantityReceived(BigDecimal.ONE);
                                    }
                                    if (vi.getItem().getWeight() != null) {
                                        poe.setWeight(vi.getItem().getWeight());
                                    } else {
                                        poe.setWeight(BigDecimal.ZERO);
                                    }
                                }
                            } else {
                                flag = false;
                            }
                        }
                        if (row.getItem() instanceof TransferOrderEntry && tc.equals(table.getColumns().get(0))) {
                            if (!textField.getText().isEmpty()) {
                                flag = false;
                                TransferOrderEntry toe = (TransferOrderEntry) row.getItem();
                                Item aItem = daoItem.find(Item_.itemLookUpCode, textField.getText());
                                if (aItem != null) {
                                    flag = true;
                                    toe.setItem(aItem);
                                    toe.setItemLookUpCode(aItem.getItemLookUpCode());
                                    toe.setCost(aItem.getCost());
                                    toe.setItemDescription(getItemDescription(aItem));
                                    Optional<TransferOrderEntry> answer = Optional.ofNullable(toe).filter(p -> p.getTransferOrder().getStatus().equals(DBConstants.STATUS_OPEN));
                                    if (answer.isPresent()) {
                                        toe.setQuantity(BigDecimal.ONE);
                                    } else {
                                        toe.setQuantityReceived(BigDecimal.ONE);
                                    }
                                }
                            } else {
                                flag = false;
                            }
                        }
                        if (row.getItem() instanceof ItemBom && tc.equals(table.getColumns().get(0))) {
                            if (!textField.getText().isEmpty()) {
                                flag = false;
                                ItemBom ib = (ItemBom) row.getItem();
                                Item aItem = daoItem.find(Item_.itemLookUpCode, textField.getText());
                                if (aItem != null) {
                                    flag = true;
                                    ib.setComponentItem(aItem);
                                    ib.setQuantity(BigDecimal.ONE);
                                }
                            } else {
                                flag = false;
                            }
                        }
                        if (row.getItem() instanceof SerialNumber && tc.equals(table.getColumns().get(0))) {
                            if (!textField.getText().isEmpty()) {
                                flag = false;
                                SerialNumber sn = (SerialNumber) row.getItem();
                                SerialNumber aSerialNumber = daoSerialNumber.find(SerialNumber_.serialNumber, textField.getText());
                                if (aSerialNumber == null) {
                                    flag = true;
                                    sn.setSerialNumber(textField.getText());
                                    sn.setSold(false);
                                    sn.setStore(Config.getStore());
                                    Calendar cal = Calendar.getInstance();
                                    cal.add(Calendar.YEAR, 1);
                                    Date nextYear = cal.getTime();
                                    sn.setWarrantyExpireLabor(nextYear);
                                    sn.setWarrantyExpirePart(nextYear);
                                }
                            } else {
                                flag = false;
                            }
                        }
                        if (flag) {
                            commitEdit((T) stringConverter.fromString(textField.getText()));
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
                    } else if (!t.isControlDown() && t.getCode() == KeyCode.UP) {
                        commitEdit((T) stringConverter.fromString(textField.getText()));
                        getTableView().getSelectionModel().selectAboveCell();
                        getTableView().requestFocus();
                    } else if (!t.isControlDown() && t.getCode() == KeyCode.DOWN) {
                        commitEdit((T) stringConverter.fromString(textField.getText()));
                        getTableView().getSelectionModel().selectBelowCell();
                        getTableView().requestFocus();
                    }
                });
                textField.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (!newValue && textField != null && textField.getText() != null) {
                        commitEdit((T) stringConverter.fromString(textField.getText()));
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

            private String getItemText() {
                if (format == null) {
                    if (getItem() == null) {
                        return "";
                    } else if (getItem().getClass() == BigDecimal.class) {
                        return getDecimalFormat().format(getItem());
                    } else if (getItem().getClass() == Date.class) {
                        return getDateFormat().format(getItem());
                    } else if (getItem().getClass() == Timestamp.class) {
                        return getDateTimeFormat().format(getItem());
                    } else if (getItem().getClass() == Integer.class) {
                        return getIntegerFormat().format(getItem());
                    } else {
                        return Objects.toString(getItem());
                    }
                } else {
                    return getItem() == null ? "" : format.format(getItem());
                }
            }
        };
        cell.setAlignment(alignment);
        return cell;
    }

}

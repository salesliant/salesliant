package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLabel;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store_;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.entity.TransferOrderEntry_;
import com.salesliant.entity.TransferOrderHistory;
import com.salesliant.entity.TransferOrderHistoryEntry;
import com.salesliant.entity.TransferOrder_;
import com.salesliant.report.TransferOrderLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.decimalEditCell;
import static com.salesliant.util.BaseUtil.editableCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.isNumeric;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.IconFactory;
import com.salesliant.util.RES;
import com.salesliant.widget.ItemTableWidget;
import com.salesliant.widget.ShippingCarrierWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public final class TransferOrderUI extends BaseListUI<TransferOrderEntry> {

    private final BaseDao<TransferOrder> daoTransferOrder = new BaseDao<>(TransferOrder.class);
    private final BaseDao<TransferOrderHistory> daoTransferOrderHistory = new BaseDao<>(TransferOrderHistory.class);
    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private final BaseDao<ItemLabel> daoItemLabel = new BaseDao<>(ItemLabel.class);
    private final BaseDao<SerialNumber> daoSerialNumber = new BaseDao<>(SerialNumber.class);
    private final DataUI dataUI = new DataUI(TransferOrderEntry.class);
    private final DataUI transferOrderUI = new DataUI(TransferOrder.class);
    private GridPane fEditPane;
    private final TransferOrder fTransferOrder;
    private final TableView<Store> fStoreTableView = new TableView<>();
    private final ObservableList<Store> fStoreList;
    private final ComboBox<ShippingCarrier> fShippingCarrierCombo = new ShippingCarrierWidget();
    private final Button receiveAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_RECEIVE_ALL, AppConstants.ACTION_RECEIVE_ALL, "Receive All", fHandler);
    private final TableColumn<TransferOrderEntry, String> skuCol = new TableColumn<>("SKU");
    private final TableColumn<TransferOrderEntry, String> descriptionCol = new TableColumn<>("Description");
    private final TableColumn<TransferOrderEntry, BigDecimal> qtyOrderedCol = new TableColumn<>("Qty Send");
    private final TableColumn<TransferOrderEntry, BigDecimal> qtyReceivedCol = new TableColumn<>("Qty Receive");
    private final TableColumn<TransferOrderEntry, BigDecimal> costCol = new TableColumn<>("Cost");
    private final TableColumn<TransferOrderEntry, String> totalCol = new TableColumn<>("Total");
    private final ObjectProperty<BigDecimal> totalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private static final Logger LOGGER = Logger.getLogger(TransferOrderUI.class.getName());

    public TransferOrderUI(TransferOrder transferOrder) {
        this.fTransferOrder = transferOrder;
        List<Store> storeList = daoStore.read();
        fStoreList = FXCollections.observableList(storeList);
        fStoreTableView.setItems(fStoreList);
        createGUI();
        transferOrderUI.getTextField(TransferOrder_.freightAmount).textProperty().addListener(fieldValueListener);
        updateTotal();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                TransferOrderEntry toe = fTableView.getItems().stream().filter(p -> p.getItem() == null).findFirst().orElse(null);
                if (toe == null) {
                    fEntity = new TransferOrderEntry();
                    fEntity.setTransferOrder(fTransferOrder);
                    fEntityList.add(fEntity);
                    fTableView.refresh();
                } else {
                    fEntity = toe;
                }
                Platform.runLater(() -> {
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(fEntity);
                    fTableView.scrollTo(fEntity);
                    fTableView.layout();
                    fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), skuCol);
                    fTableView.edit(fTableView.getSelectionModel().getSelectedIndex(), skuCol);
                });
                break;
            case AppConstants.ACTION_ASSIGN_SERIAL_NUMBER:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().isEmpty()) {
                        TransferOrderEntry transferOrderEntry = fTableView.getSelectionModel().getSelectedItem();
                        SerialNumberListUI serialNumberListUI = new SerialNumberListUI(transferOrderEntry);
                        serialNumberListUI.setParent(this);
                        fInputDialog = createUIDialog(serialNumberListUI.getView(), "Serial Number");
                        serialNumberListUI.selectButton.setOnAction(e -> {
                            List<SerialNumber> list = new ArrayList<>(serialNumberListUI.getSelectedItems());
                            fInputDialog.close();
                            transferOrderEntry.setSerialNumbers(list);
                            fTableView.refresh();
                        });
                        serialNumberListUI.closeButton.setOnAction(e -> {
                            fInputDialog.close();
                        });
                        fInputDialog.showDialog();
                    } else {
                        showAlertDialog("Entry already has serial number, can't modify it!");
                    }
                }
                break;
            case AppConstants.ACTION_UNASSIGN_SERIAL_NUMBER:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (!fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().isEmpty()) {
                        showConfirmDialog("Are you sure to remove serial number of the seleted entry?", (ActionEvent event) -> {
                            fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().forEach(e -> {
                                e.setTransferOrderEntry(null);
                                if (fTableView.getSelectionModel().getSelectedItem().getId() != null) {
                                    daoSerialNumber.update(e);
                                }
                            });
                            fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().clear();
                            fTableView.refresh();
                        });
                    }
                }
                break;
            case AppConstants.ACTION_LINE_NOTE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Line Note");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        if (isEmpty(fEntity.getLineNote())) {
                            fEntity.setLineNote(null);
                        }
                        fTableView.refresh();
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    Platform.runLater(() -> dataUI.getTextArea(TransferOrderEntry_.lineNote).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fEntityList.remove(fEntity);
                    if (fEntityList.isEmpty()) {
                        fTableView.getSelectionModel().select(null);
                    }
                    fTableView.refresh();
                }
                break;
            case AppConstants.ACTION_SELECT_LIST:
                ItemTableWidget itemTableWidget = new ItemTableWidget();
                fInputDialog = createSelectCancelUIDialog(itemTableWidget.getView(), "Item");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemTableWidget.getSelectedItems().size() >= 1) {
                        List<TransferOrderEntry> list = fTableView.getItems().stream().filter(p -> p.getItem() == null).collect(Collectors.toList());
                        if (!list.isEmpty()) {
                            fTableView.getItems().removeAll(list);
                        }
                        int i = fTableView.getItems().size();
                        itemTableWidget.getSelectedItems().stream().forEach((item) -> {
                            TransferOrderEntry newTransferOrderEntry = new TransferOrderEntry();
                            newTransferOrderEntry.setTransferOrder(fTransferOrder);
                            newTransferOrderEntry.setItem(item);
                            newTransferOrderEntry.setItemDescription(getItemDescription(item));
                            newTransferOrderEntry.setItemLookUpCode(item.getItemLookUpCode());
                            newTransferOrderEntry.setQuantity(BigDecimal.ONE);
                            newTransferOrderEntry.setQuantityReceived(BigDecimal.ZERO);
                            newTransferOrderEntry.setCost(item.getCost());
                            fTableView.getItems().add(newTransferOrderEntry);

                        });
                        updateTotal();
                        fTableView.refresh();
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i);
                        fTableView.scrollTo(i);
                        fTableView.getFocusModel().focus(i, qtyOrderedCol);
                        fTableView.edit(i, qtyOrderedCol);
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_RECEIVE_ALL:
                fTableView.getItems().stream().filter(p -> p.getItemLookUpCode() != null).forEach(p -> {
                    p.setQuantityReceived(p.getQuantity());
                });
                fTableView.refresh();
                break;
            case AppConstants.ACTION_PRINT:
                update();
                if (!fTransferOrder.getTransferOrderEntries().isEmpty() && fTransferOrder.getTotal() != null) {
                    TransferOrderLayout layout = new TransferOrderLayout(fTransferOrder);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    showAlertDialog("Can't print a empty transfer order! ");
                }
                break;
            case AppConstants.ACTION_VOID:
                Response answer = createConfirmResponseDialog("Are you sure to void this transfer order?");
                if (answer.equals(Response.YES)) {
                    if (fTransferOrder.getId() != null) {
                        daoTransferOrder.delete(fTransferOrder);
                    }
                    getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH);
                }
                break;
            case AppConstants.ACTION_SAVE:
                update();
                if (!fTransferOrder.getTransferOrderEntries().isEmpty()) {
                    if (fTransferOrder.getId() == null) {
                        daoTransferOrder.insert(fTransferOrder);
                    } else {
                        daoTransferOrder.update(fTransferOrder);
                    }
                    fTransferOrder.getTransferOrderEntries().forEach(e -> {
                        if (e.getSerialNumbers() != null && !e.getSerialNumbers().isEmpty()) {
                            e.getSerialNumbers().forEach(s -> {
                                s.setTransferOrderEntry(e);
                                daoSerialNumber.update(s);
                            });
                        }
                    });
                    getParent().handleAction(AppConstants.ACTION_SAVE);
                } else {
                    showAlertDialog("Can't save a empty transfer order! ");
                }
                break;
            case AppConstants.ACTION_PROCESS:
                update();
                if (!fTransferOrder.getTransferOrderEntries().isEmpty()) {
                    processTransferOrder();
                    Platform.runLater(() -> {
                        TransferOrderLayout layout = new TransferOrderLayout(fTransferOrder);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH);
                } else {
                    showAlertDialog("Can't process a empty transfer order! ");
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_UP:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1 && fTableView.getItems().get(i - 1).getItem() != null) {
                        Collections.swap(fTableView.getItems(), i, i - 1);
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i - 1);
                    }
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_DOWN:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i < (fTableView.getItems().size() - 1) && fTableView.getItems().get(i + 1).getItem() != null) {
                        Collections.swap(fTableView.getItems(), i, i + 1);
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i + 1);
                    }
                }
                break;
            case AppConstants.ACTION_TABLE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.refresh();
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(i);
                    if (fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                        fTableView.getFocusModel().focus(i, qtyOrderedCol);
                    } else {
                        fTableView.getFocusModel().focus(i, skuCol);
                    }
                }
                break;
        }
    }

    private void createGUI() {
        fTableView = new TableView<TransferOrderEntry>() {
            @Override
            public void edit(int row, TableColumn<TransferOrderEntry, ?> column) {
                if (row >= 0 && row <= (getItems().size() - 1)) {
                    TransferOrderEntry entry = getItems().get(row);
                    if (entry.getItem() != null && column.equals(skuCol)) {
                        return;
                    }
                    if (entry.getItem() == null && column != null && (column.equals(qtyOrderedCol) || column.equals(qtyReceivedCol))) {
                        return;
                    }
                }
                super.edit(row, column);
            }
        };
        fTableView.setEditable(true);
        addKeyListener();
        if (!fTransferOrder.getTransferOrderEntries().isEmpty()) {
            List<TransferOrderEntry> list = fTransferOrder.getTransferOrderEntries().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
        } else {
            fEntityList = FXCollections.observableArrayList();
        }
        fTableView.setItems(fEntityList);
        mainView = createMainView();
        if (fTransferOrder.getStatus().equals(DBConstants.STATUS_IN_PROGRESS)) {
            qtyReceivedCol.setVisible(true);
            descriptionCol.setPrefWidth(310);
            receiveAllButton.setVisible(true);
        } else {
            qtyReceivedCol.setVisible(false);
            descriptionCol.setPrefWidth(410);
            receiveAllButton.setVisible(false);
        }
        setTableWidth(fTableView);
        fEditPane = createEditPane();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TransferOrderEntry> observable, TransferOrderEntry newValue, TransferOrderEntry oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                fStoreTableView.refresh();
            } else {
                fEntity = null;
                fStoreTableView.refresh();
            }
        });
        try {
            transferOrderUI.setData(fTransferOrder);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        dialogTitle = "Transfer Order Entry";
        if (fTableView.getItems().isEmpty()) {
            handleAction(AppConstants.ACTION_ADD);
        } else {
            fTableView.getSelectionModel().selectFirst();
            fTableView.getSelectionModel().select(0, qtyOrderedCol);
        }

        fTableView.setRowFactory(mouseClickListener(1));
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);
        skuCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.itemLookUpCode.getName()));
        skuCol.setCellFactory(editableCell(Pos.CENTER_LEFT));
        skuCol.setOnEditCommit((TableColumn.CellEditEvent<TransferOrderEntry, String> t) -> {
            TransferOrderEntry toe = (TransferOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
            updateTotal();
            fTableView.refresh();
            if (toe != null && toe.getItem() != null) {
                fEntity = toe;
                fStoreTableView.refresh();
                handleAction(AppConstants.ACTION_ADD);
            } else {
                fEntity = null;
                fStoreTableView.refresh();
            }
        });
        skuCol.setPrefWidth(150);
        skuCol.setSortable(false);

        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null) {
                String description = getString(p.getValue().getItemDescription());
                if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                    description = description + "\n" + p.getValue().getLineNote();
                }
                if (p.getValue().getSerialNumbers() != null & !p.getValue().getSerialNumbers().isEmpty()) {
                    description = description + "\n";
                    for (SerialNumber sn : p.getValue().getSerialNumbers()) {
                        description = description + getString(sn.getSerialNumber()) + "; ";
                    }
                }
                description = description.trim();
                if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                    description = description.substring(0, description.length() - 1);
                }
                return new SimpleStringProperty(description);
            } else {
                return new SimpleStringProperty("");
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        descriptionCol.setEditable(false);
        descriptionCol.setSortable(false);
        descriptionCol.setPrefWidth(330);

        qtyOrderedCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.quantity.getName()));
        qtyOrderedCol.setOnEditCommit((TableColumn.CellEditEvent<TransferOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                TransferOrderEntry toe = (TransferOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Item item = toe.getItem();
                if (item != null) {
                    if (!item.getFractionTag()) {
                        BigDecimal truncated = t.getNewValue().setScale(0, RoundingMode.DOWN);
                        toe.setQuantity(truncated);
                    } else {
                        toe.setQuantity(t.getNewValue());
                    }
                }
                updateTotal();
                fTableView.refresh();
            }
        });
        qtyOrderedCol.setCellFactory(decimalEditCell(Pos.CENTER_RIGHT));
        qtyOrderedCol.setSortable(false);
        qtyOrderedCol.setPrefWidth(100);

        qtyReceivedCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.quantityReceived.getName()));
        qtyReceivedCol.setOnEditCommit((TableColumn.CellEditEvent<TransferOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                TransferOrderEntry poe = (TransferOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Item item = poe.getItem();
                if (item != null) {
                    if (!item.getFractionTag()) {
                        BigDecimal truncated = t.getNewValue().setScale(0, RoundingMode.DOWN);
                        poe.setQuantityReceived(truncated);
                    } else {
                        poe.setQuantityReceived(t.getNewValue());
                    }
                }
                updateTotal();
                fTableView.refresh();
            }
        });
        qtyReceivedCol.setCellFactory(decimalEditCell(Pos.CENTER_RIGHT));
        qtyReceivedCol.setSortable(false);
        qtyReceivedCol.setPrefWidth(100);

        costCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.cost.getName()));
        costCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        costCol.setSortable(false);
        costCol.setPrefWidth(100);

        totalCol.setCellValueFactory((CellDataFeatures<TransferOrderEntry, String> p) -> {
            BigDecimal total;
            if (fTransferOrder.getDateSent() != null) {
                if (p.getValue() != null && p.getValue().getCost() != null && p.getValue().getQuantityReceived() != null) {
                    total = p.getValue().getCost().multiply(p.getValue().getQuantityReceived());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return new SimpleStringProperty("");
                }
            } else {
                if (p.getValue() != null && p.getValue().getCost() != null && p.getValue().getQuantity() != null) {
                    total = p.getValue().getCost().multiply(p.getValue().getQuantity());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        totalCol.setPrefWidth(100);
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setSortable(false);
        totalCol.setEditable(false);

        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyOrderedCol);
        fTableView.getColumns().add(qtyReceivedCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(totalCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(400);

        BorderPane bp = new BorderPane();
        bp.setCenter(fTableView);
        bp.setBottom(createButtonPane());
        bp.setTop(createSettingPane());
        bp.getStyleClass().add("border");

        mainPane.add(bp, 0, 0);
        mainPane.add(createBottomPane(), 0, 1);
        Label functionKeyLabel = new Label("Ctrl+L: List Items, Ctrl+X: Delete, Ctrl+U: Move Up, Ctrl+D: Move Down, Insert: Add, Ctrl+N: Add Line Note, Ctrl+V: Void, Ctrl+S: Save");
        mainPane.add(functionKeyLabel, 0, 2);
        GridPane.setHalignment(functionKeyLabel, HPos.LEFT);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Line Note:", dataUI.createTextArea(TransferOrderEntry_.lineNote), 80, 250, fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    private GridPane createSettingPane() {
        GridPane settingPane = new GridPane();
        settingPane.setHgap(4.0);
        Label transferLabel = new Label("Transfer #:");
        settingPane.add(transferLabel, 1, 0);
        settingPane.add(transferOrderUI.createLabelField(TransferOrder_.transferOrderNumber, 90, Pos.CENTER_LEFT), 2, 0);
        Label processorLabel = new Label("Processor:");
        settingPane.add(processorLabel, 3, 0);
        settingPane.add(transferOrderUI.createLabelField(TransferOrder_.employeeSendName, 120, Pos.CENTER_LEFT), 4, 0);
        Label shipViaLabel = new Label("  Ship Via:");
        settingPane.add(shipViaLabel, 5, 0);
        transferOrderUI.setUIComponent(TransferOrder_.shippingCarrier, fShippingCarrierCombo);
        fShippingCarrierCombo.setPrefWidth(120);
        settingPane.add(fShippingCarrierCombo, 6, 0);
        DatePicker dateExpected = new DatePicker();
        transferOrderUI.setUIComponent(TransferOrder_.dateExpected, dateExpected);
        dateExpected.setPrefWidth(100);
        Label expectedDateLabel = new Label("  Expected:");
        settingPane.add(expectedDateLabel, 7, 0);
        settingPane.add(dateExpected, 8, 0);
        Label invoiceDateLabel = new Label("  Received:");
        DatePicker receiveDate = new DatePicker();
        receiveDate.setPrefWidth(100);
        transferOrderUI.setUIComponent(TransferOrder_.dateReceived, receiveDate);
        settingPane.add(invoiceDateLabel, 9, 0);
        settingPane.add(receiveDate, 10, 0);
        settingPane.setPadding(new Insets(1));

        GridPane.setHalignment(expectedDateLabel, HPos.RIGHT);
        GridPane.setHalignment(shipViaLabel, HPos.RIGHT);
        GridPane.setHalignment(invoiceDateLabel, HPos.RIGHT);

        return settingPane;
    }

    private Node createBottomPane() {
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("border");

        TableColumn<Store, String> storeCol = new TableColumn<>("Store");
        storeCol.setCellValueFactory(new PropertyValueFactory<>(Store_.storeName.getName()));
        storeCol.setMinWidth(200);
        storeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        storeCol.setSortable(false);

        TableColumn<Store, String> storeQtyCol = new TableColumn<>("Qty");
        storeQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue() != null && fEntity != null && fEntity.getItem() != null && getItemQuantityByStore(fEntity.getItem(), p.getValue()) != null) {
                return new SimpleStringProperty(getString(getItemQuantityByStore(fEntity.getItem(), p.getValue()).getQuantity()));
            } else {
                return null;
            }
        });
        storeQtyCol.setCellFactory(stringCell(Pos.CENTER));
        storeQtyCol.setMinWidth(100);
        storeQtyCol.setSortable(false);

        TableColumn<Store, String> storeCostCol = new TableColumn<>("Cost");
        storeCostCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue() != null && fEntity != null && fEntity.getItem() != null && fEntity.getItem().getCost() != null) {
                return new SimpleStringProperty(getString(fEntity.getItem().getCost()));
            } else {
                return null;
            }
        });
        storeCostCol.setCellFactory(stringCell(Pos.CENTER));
        storeCostCol.setMinWidth(100);
        storeCostCol.setSortable(false);

        TableColumn<Store, String> storePriceCol = new TableColumn<>("Price");
        storePriceCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue() != null && fEntity != null && fEntity.getItem() != null && fEntity.getItem().getPrice1() != null) {
                return new SimpleStringProperty(getString(fEntity.getItem().getPrice1()));
            } else {
                return null;
            }
        });
        storePriceCol.setCellFactory(stringCell(Pos.CENTER));
        storePriceCol.setMinWidth(100);
        storePriceCol.setSortable(false);

        fStoreTableView.getColumns().add(storeCol);
        fStoreTableView.getColumns().add(storeQtyCol);
        fStoreTableView.getColumns().add(storeCostCol);
        fStoreTableView.getColumns().add(storePriceCol);
        fStoreTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fStoreTableView.setPrefHeight(100);
        setTableWidth(fStoreTableView);

        Tab storeStockTab = new Tab(" Stock");
        storeStockTab.setContent(fStoreTableView);
        storeStockTab.setClosable(false);
        tabPane.getTabs().add(storeStockTab);

        GridPane notePane = new GridPane();
        notePane.setPadding(new Insets(8));
        Label noteLabel = new Label("Note:");
        GridPane.setHalignment(noteLabel, HPos.RIGHT);
        notePane.add(noteLabel, 0, 1);
        notePane.add(transferOrderUI.createTextArea(TransferOrder_.note), 1, 2, 2, 1);
        transferOrderUI.getTextArea(TransferOrder_.note).setPrefSize(400, 100);

        Tab noteTab = new Tab("Note");
        noteTab.setContent(notePane);
        noteTab.setClosable(false);
        tabPane.getTabs().add(noteTab);

        GridPane totalPane = new GridPane();
        Label subtotalLabel = new Label("Subtotal: ");
        totalPane.add(subtotalLabel, 0, 0);
        totalPane.add(transferOrderUI.createLabelField(TransferOrder_.total, 130, Pos.CENTER_RIGHT), 1, 0);

        Label freightLabel = new Label("Freight: ");
        totalPane.add(freightLabel, 0, 1);
        totalPane.add(transferOrderUI.createTextField(TransferOrder_.freightAmount, 130), 1, 1);
        transferOrderUI.getTextField(TransferOrder_.freightAmount).setAlignment(Pos.CENTER_RIGHT);

        Label totalLabel = new Label("Total: ");
        TextField totalField = createLabelField(130, Pos.CENTER_RIGHT);
        totalField.textProperty().bindBidirectional(totalProperty, getDecimalFormat());
        totalPane.add(totalLabel, 0, 2);
        totalPane.add(totalField, 1, 2);

        totalLabel.getStyleClass().add("subtotal");
        totalField.getStyleClass().add("subtotal");

        totalPane.setAlignment(Pos.TOP_RIGHT);

        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);

        HBox bottomPane = new HBox();
        bottomPane.getChildren().addAll(tabPane, filler, totalPane);

        return bottomPane;
    }

    protected HBox createButtonPane() {

        HBox leftButtonBox = new HBox();
        Button upButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_UP_ICON), AppConstants.ACTION_MOVE_ROW_UP, fHandler);
        Button downButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_DOWN_ICON), AppConstants.ACTION_MOVE_ROW_DOWN, fHandler);
        Button newButton = ButtonFactory.getButton(IconFactory.getIcon(RES.ADD_ICON), AppConstants.ACTION_ADD, fHandler);
        Button deleteButton = ButtonFactory.getButton(IconFactory.getIcon(RES.DELETE_ICON), AppConstants.ACTION_DELETE, fHandler);
        Button editButton = ButtonFactory.getButton(IconFactory.getIcon(RES.EDIT_ICON), AppConstants.ACTION_EDIT, fHandler);
        Button lineNoteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_LINE_NOTE, AppConstants.ACTION_LINE_NOTE, fHandler);
        Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_LIST, AppConstants.ACTION_SELECT_LIST, fHandler);
        lineNoteButton.setPrefWidth(90);
        selectButton.setPrefWidth(90);
        leftButtonBox.getChildren().addAll(upButton, downButton, newButton, deleteButton, editButton, lineNoteButton, selectButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print", fHandler);
        Button processButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, "Process", fHandler);
        Button saveButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, "Save", fHandler);
        Button voidButton = ButtonFactory.getButton(ButtonFactory.BUTTON_VOID, AppConstants.ACTION_VOID, "Void", fHandler);
        printButton.setPrefWidth(94);
        receiveAllButton.setPrefWidth(94);
        rightButtonBox.getChildren().addAll(receiveAllButton, printButton, processButton, voidButton, saveButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    @Override
    protected void updateTotal() {
        BigDecimal freightAmount = BigDecimal.ZERO;
        BigDecimal total;
        BigDecimal subTotal = fTableView.getItems().stream()
                .map(poe -> {
                    if (fTransferOrder.getDateSent() != null) {
                        return zeroIfNull(poe.getCost()).multiply(zeroIfNull(poe.getQuantityReceived()));
                    } else {
                        return zeroIfNull(poe.getCost()).multiply(zeroIfNull(poe.getQuantity()));
                    }
                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        transferOrderUI.getTextField(TransferOrder_.total).setText(getString(subTotal));
        if (isNumeric(transferOrderUI.getTextField(TransferOrder_.freightAmount).getText())) {
            freightAmount = new BigDecimal(transferOrderUI.getTextField(TransferOrder_.freightAmount).getText());
        }
        total = subTotal.add(freightAmount);
        totalProperty.set(total);
        fTableView.refresh();
    }

    private void update() {
        updateTotal();
        try {
            transferOrderUI.getData(fTransferOrder);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        if (!fTransferOrder.getTransferOrderEntries().isEmpty()) {
            fTransferOrder.getTransferOrderEntries().clear();
        }
        for (int i = 0; i < fTableView.getItems().size(); i++) {
            TransferOrderEntry entry = fTableView.getItems().get(i);
            if (entry.getItem() != null) {
                entry.setDisplayOrder(i);
                entry.setTransferOrder(fTransferOrder);
                fTransferOrder.getTransferOrderEntries().add(entry);
            }
        }
    }

    private void processTransferOrder() {
        BigDecimal subTotal = fTransferOrder.getTransferOrderEntries().stream().map(p -> zeroIfNull(p.getCost()).multiply(zeroIfNull(p.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (subTotal.compareTo(BigDecimal.ZERO) != 0) {
            if (fTransferOrder.getStatus().equals(DBConstants.STATUS_OPEN)) {
                fTransferOrder.setStatus(DBConstants.STATUS_IN_PROGRESS);
                fTransferOrder.setDateSent(new Timestamp(new Date().getTime()));
                if (fTransferOrder.getDateExpected() == null) {
                    fTransferOrder.setDateExpected(new Date());
                }
                if (fTransferOrder.getId() == null) {
                    daoTransferOrder.insert(fTransferOrder);
                } else {
                    daoTransferOrder.update(fTransferOrder);
                }
            } else if (fTransferOrder.getStatus().equals(DBConstants.STATUS_IN_PROGRESS)) {
                fTransferOrder.setStatus(DBConstants.STATUS_CLOSE);
                fTransferOrder.setDateReceived(new Timestamp(new Date().getTime()));
                TransferOrderHistory transferOrderHistory = new TransferOrderHistory();
                TransferOrder newTransferOrder = new TransferOrder();
                fTransferOrder.getTransferOrderEntries().forEach(e -> {
                    if (e.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0) {
                        TransferOrderHistoryEntry tohe = new TransferOrderHistoryEntry();
                        tohe.setQuantityReceived(e.getQuantityReceived());
                        tohe.setQuantity(e.getQuantity());
                        tohe.setTransferOrderHistory(transferOrderHistory);
                        tohe.setCost(e.getCost());
                        tohe.setDisplayOrder(e.getDisplayOrder());
                        tohe.setItem(e.getItem());
                        tohe.setItemDescription(e.getItemDescription());
                        tohe.setItemLookUpCode(e.getItemLookUpCode());
                        tohe.setLineNote(e.getLineNote());
                        tohe.setSerialNumbers(e.getSerialNumbers());
                        transferOrderHistory.getTransferOrderHistoryEntries().add(tohe);
                        if (e.getQuantity().compareTo(e.getQuantityReceived()) > 0) {
                            TransferOrderEntry newTransferOrderEntry = new TransferOrderEntry();
                            newTransferOrderEntry.setCost(e.getCost());
                            newTransferOrderEntry.setDisplayOrder(e.getDisplayOrder());
                            newTransferOrderEntry.setItem(e.getItem());
                            newTransferOrderEntry.setItemLookUpCode(e.getItemLookUpCode());
                            newTransferOrderEntry.setItemDescription(e.getItemDescription());
                            newTransferOrderEntry.setTransferOrder(newTransferOrder);
                            newTransferOrderEntry.setQuantity(e.getQuantity().subtract(e.getQuantityReceived()));
                            newTransferOrderEntry.setQuantityReceived(BigDecimal.ZERO);
                            newTransferOrder.getTransferOrderEntries().add(newTransferOrderEntry);
                        }
                    }
                });
                if (!newTransferOrder.getTransferOrderEntries().isEmpty()) {
                    Collections.sort(newTransferOrder.getTransferOrderEntries(), (e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()));
                    newTransferOrder.setSendStore(fTransferOrder.getSendStore());
                    newTransferOrder.setReceiveStore(fTransferOrder.getReceiveStore());
                    newTransferOrder.setDateSent(fTransferOrder.getDateSent());
                    newTransferOrder.setDateCreated(fTransferOrder.getDateCreated());
                    newTransferOrder.setDateExpected(fTransferOrder.getDateExpected());
                    newTransferOrder.setEmployeeSendName(fTransferOrder.getEmployeeSendName());
                    newTransferOrder.setStore(fTransferOrder.getStore());
                    newTransferOrder.setNote(fTransferOrder.getNote());
                    newTransferOrder.setTransferOrderNumber(fTransferOrder.getTransferOrderNumber());
                    newTransferOrder.setShippingCarrier(fTransferOrder.getShippingCarrier());
                    newTransferOrder.setStatus(DBConstants.STATUS_IN_PROGRESS);
                    BigDecimal newTotal = newTransferOrder.getTransferOrderEntries().stream().map(p -> zeroIfNull(p.getCost()).multiply(zeroIfNull(p.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
                    newTransferOrder.setTotal(newTotal);
                    daoTransferOrder.insert(newTransferOrder);
                }
                if (!transferOrderHistory.getTransferOrderHistoryEntries().isEmpty()) {
                    transferOrderHistory.setTransferOrderNumber(fTransferOrder.getTransferOrderNumber());
                    transferOrderHistory.setSendStore(fTransferOrder.getSendStore());
                    transferOrderHistory.setReceiveStore(fTransferOrder.getReceiveStore());
                    transferOrderHistory.setSendStoreName(fTransferOrder.getSendStore().getStoreName());
                    transferOrderHistory.setReceiveStoreName(fTransferOrder.getReceiveStore().getStoreName());
                    transferOrderHistory.setDateCreated(fTransferOrder.getDateCreated());
                    transferOrderHistory.setDateExpected(fTransferOrder.getDateExpected());
                    transferOrderHistory.setDateSent(fTransferOrder.getDateSent());
                    transferOrderHistory.setDateReceived(new Timestamp(new Date().getTime()));
                    transferOrderHistory.setEmployeeSendName(fTransferOrder.getEmployeeSendName());
                    transferOrderHistory.setEmployeeReceiveName(Config.getEmployee().getNameOnSalesOrder());
                    transferOrderHistory.setShippingCarrier(fTransferOrder.getShippingCarrier());
                    transferOrderHistory.setStore(Config.getStore());
                    transferOrderHistory.setNote(fTransferOrder.getNote());
                    transferOrderHistory.setFreightAmount(fTransferOrder.getFreightAmount());
                    transferOrderHistory.setTotal(subTotal);
                    daoTransferOrderHistory.insert(transferOrderHistory);
                    if (daoTransferOrderHistory.getErrorMessage() == null) {
                        transferOrderHistory.getTransferOrderHistoryEntries().stream().filter((g) -> (g.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0)).forEachOrdered((g) -> {
                            g.getSerialNumbers().forEach(sn -> {
                                sn.setStore(transferOrderHistory.getReceiveStore());
                                sn.setTransferOrderHistoryEntry(g);
                                daoSerialNumber.update(sn);
                            });
                            Item item = g.getItem();
                            BigDecimal beforeQtySend;
                            BigDecimal afterQtySend;
                            ItemQuantity sendIQ = getItemQuantityByStore(item, fTransferOrder.getSendStore());
                            if (sendIQ == null) {
                                sendIQ = new ItemQuantity();
                                sendIQ.setItem(item);
                                sendIQ.setStore(fTransferOrder.getSendStore());
                                sendIQ.setQuantity(BigDecimal.ZERO);
                                sendIQ.setLastSold(new Timestamp(new Date().getTime()));
                                daoItemQuantity.insert(sendIQ);
                                beforeQtySend = BigDecimal.ZERO;
                                afterQtySend = BigDecimal.ZERO;
                            } else {
                                beforeQtySend = sendIQ.getQuantity();
                                BigDecimal qtySend = (sendIQ.getQuantity()).subtract(g.getQuantity());
                                if (qtySend.compareTo(BigDecimal.ZERO) < 0) {
                                    sendIQ.setQuantity(BigDecimal.ZERO);
                                } else {
                                    sendIQ.setQuantity(qtySend);
                                }
                                sendIQ.setLastSold(new Timestamp(new Date().getTime()));
                                daoItemQuantity.update(sendIQ);
                                afterQtySend = sendIQ.getQuantity();
                            }
                            ItemLog itemSendLog = new ItemLog();
                            itemSendLog.setDateCreated(new Timestamp(new Date().getTime()));
                            itemSendLog.setDescription(DBConstants.ITEM_LOG_DESCRIPTION_TRANSFER_SEND);
                            itemSendLog.setItem(item);
                            itemSendLog.setCost(g.getCost());
                            itemSendLog.setItemCost(item.getCost());
                            itemSendLog.setPrice(item.getPrice1());
                            itemSendLog.setItemPrice(item.getPrice1());
                            itemSendLog.setStore(fTransferOrder.getSendStore());
                            itemSendLog.setTransactionNumber(fTransferOrder.getTransferOrderNumber());
                            itemSendLog.setAfterQuantity(afterQtySend);
                            itemSendLog.setBeforeQuantity(beforeQtySend);
                            itemSendLog.setEmployee(Config.getEmployee());
                            itemSendLog.setTransferType(DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_TRANSFERORDER_SEND);
                            daoItemLog.insert(itemSendLog);
                            BigDecimal beforeQtyReceive;
                            BigDecimal afterQtyReceive;
                            ItemQuantity receiveIQ = getItemQuantityByStore(item, transferOrderHistory.getReceiveStore());
                            if (receiveIQ == null) {
                                beforeQtyReceive = BigDecimal.ZERO;
                                receiveIQ = new ItemQuantity();
                                receiveIQ.setItem(item);
                                receiveIQ.setStore(transferOrderHistory.getReceiveStore());
                                if (g.getQuantityReceived().compareTo(BigDecimal.ZERO) < 0) {
                                    receiveIQ.setQuantity(BigDecimal.ZERO);
                                    afterQtyReceive = BigDecimal.ZERO;
                                } else {
                                    receiveIQ.setQuantity(g.getQuantityReceived());
                                    afterQtyReceive = g.getQuantityReceived();
                                }
                                receiveIQ.setLastReceived(new Timestamp(new Date().getTime()));
                                daoItemQuantity.insert(receiveIQ);
                            } else {
                                beforeQtyReceive = receiveIQ.getQuantity();
                                BigDecimal qtyReceive = (receiveIQ.getQuantity()).add(g.getQuantityReceived());
                                if (qtyReceive.compareTo(BigDecimal.ZERO) < 0) {
                                    receiveIQ.setQuantity(BigDecimal.ZERO);
                                } else {
                                    receiveIQ.setQuantity(qtyReceive);
                                }
                                receiveIQ.setLastReceived(new Timestamp(new Date().getTime()));
                                daoItemQuantity.update(receiveIQ);
                                afterQtyReceive = receiveIQ.getQuantity();
                            }
                            ItemLog itemReceiveLog = new ItemLog();
                            itemReceiveLog.setDateCreated(new Timestamp(new Date().getTime()));
                            itemReceiveLog.setDescription(DBConstants.ITEM_LOG_DESCRIPTION_TRANSFER_RECEIVE);
                            itemReceiveLog.setItem(item);
                            itemReceiveLog.setCost(g.getCost());
                            itemReceiveLog.setItemCost(item.getCost());
                            itemReceiveLog.setPrice(item.getPrice1());
                            itemReceiveLog.setItemPrice(item.getPrice1());
                            itemReceiveLog.setStore(transferOrderHistory.getReceiveStore());
                            itemReceiveLog.setBeforeQuantity(beforeQtyReceive);
                            itemReceiveLog.setTransferOrderHistory(transferOrderHistory);
                            itemReceiveLog.setAfterQuantity(afterQtyReceive);
                            itemReceiveLog.setTransferType(DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_TRANSFERORDER_RECEIVE);
                            daoItemLog.insert(itemReceiveLog);
                        });
                    }
                }
                showConfirmDialog("Do you want to add received item to label list?", (ActionEvent a) -> {
                    fTransferOrder.getTransferOrderEntries().stream().filter((p) -> (p.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0)).forEachOrdered((p) -> {
                        ItemLabel itemLabel = new ItemLabel();
                        itemLabel.setDateAdded(new Timestamp(new Date().getTime()));
                        itemLabel.setItem(p.getItem());
                        itemLabel.setStore(Config.getStore());
                        itemLabel.setQuantity(p.getQuantityReceived().intValue());
                        daoItemLabel.insert(itemLabel);
                    });
                });
                if (fTransferOrder.getId() != null) {
                    daoTransferOrder.delete(fTransferOrder);
                }
            }
        } else {
            showAlertDialog("Nothing to receive! ");
        }
    }
}

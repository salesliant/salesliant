package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountPayable;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLabel;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.PurchaseOrderEntry_;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.PurchaseOrder_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Store;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorContact;
import com.salesliant.entity.VendorContact_;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.report.PurchaseOrderHistoryLayout;
import com.salesliant.report.PurchaseOrderLayout;
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
import com.salesliant.widget.CustomerShipToListUI;
import com.salesliant.widget.VendorContactWidget;
import com.salesliant.widget.VendorShippingWidget;
import com.salesliant.widget.VendorTermWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public final class PurchaseOrderUI extends BaseListUI<PurchaseOrderEntry> {

    private final BaseDao<PurchaseOrder> daoPurchaseOrder = new BaseDao<>(PurchaseOrder.class);
    private final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private final BaseDao<AccountPayable> daoAccountPayable = new BaseDao<>(AccountPayable.class);
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<VendorItem> daoVendorItem = new BaseDao<>(VendorItem.class);
    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private final BaseDao<ItemLabel> daoItemLabel = new BaseDao<>(ItemLabel.class);
    private final BaseDao<SerialNumber> daoSerialNumber = new BaseDao<>(SerialNumber.class);
    private final DataUI dataUI = new DataUI(PurchaseOrderEntry.class);
    private final DataUI purchaseOrderUI = new DataUI(PurchaseOrder.class);
    private GridPane fEditPane;
    private Vendor fVendor;
    private final VendorTermWidget fVendorTermCombo = new VendorTermWidget();
    private Customer fCustomer;
    private CustomerShipTo fCustomerShipTo;
    private final PurchaseOrder fPurchaseOrder;
    private final TableView<VendorItem> fVendorItemTable = new TableView<>();
    private final TableView<VendorContact> fVendorContactTable = new TableView<>();
    private ObservableList<VendorItem> fVendorItemList;
    private ObservableList<VendorContact> fVendorContactList;
    private ComboBox<VendorShippingService> fShippingServiceCombo;
    private ComboBox<VendorContact> fVendorContactCombo;
    private final TextArea fVendorAddress = new TextArea(), fShipToAddress = new TextArea();
    private Button selectShipToBtn;
    private final Button receiveAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_RECEIVE_ALL, AppConstants.ACTION_RECEIVE_ALL, "Receive All", fHandler);
    private final CheckBox shipToCustomerCheckBox = new CheckBox("Ship To Customer        ");
    private final TextArea fInfo = new TextArea();
    private final TableColumn<PurchaseOrderEntry, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
    private final TableColumn<PurchaseOrderEntry, String> yourSKUCol = new TableColumn<>("Your SKU");
    private final TableColumn<PurchaseOrderEntry, String> descriptionCol = new TableColumn<>("Description");
    private final TableColumn<PurchaseOrderEntry, BigDecimal> qtyOrderedCol = new TableColumn<>("Qty Ordered");
    private final TableColumn<PurchaseOrderEntry, BigDecimal> qtyReceivedCol = new TableColumn<>("Qty Received");
    private final TableColumn<PurchaseOrderEntry, BigDecimal> costCol = new TableColumn<>("Cost");
    private final TableColumn<PurchaseOrderEntry, BigDecimal> weightCol = new TableColumn<>("Weight");
    private final TableColumn<PurchaseOrderEntry, String> totalCol = new TableColumn<>("Total");
    private final ObjectProperty<BigDecimal> totalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final static String SHIP_TO_SELECT = "Ship_To_Select";
    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderUI.class.getName());

    public PurchaseOrderUI(PurchaseOrder purchaseOrder) {
        this.fPurchaseOrder = purchaseOrder;
        createGUI();
        purchaseOrderUI.getTextField(PurchaseOrder_.taxOnOrderAmount).textProperty().addListener(fieldValueListener);
        purchaseOrderUI.getTextField(PurchaseOrder_.taxOnFreightAmount).textProperty().addListener(fieldValueListener);
        purchaseOrderUI.getTextField(PurchaseOrder_.freightPrePaidAmount).textProperty().addListener(fieldValueListener);
        purchaseOrderUI.getTextField(PurchaseOrder_.freightInvoicedAmount).textProperty().addListener(fieldValueListener);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                PurchaseOrderEntry poe = fTableView.getItems().stream().filter(p -> p.getItem() == null).findFirst().orElse(null);
                if (poe == null) {
                    fEntity = new PurchaseOrderEntry();
                    fEntity.setPurchaseOrder(fPurchaseOrder);
                    fEntityList.add(fEntity);
                    fTableView.refresh();
                } else {
                    fEntity = poe;
                }
                Platform.runLater(() -> {
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(fEntity);
                    fTableView.scrollTo(fEntity);
                    fTableView.layout();
                    fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), vendorSKUCol);
                    fTableView.edit(fTableView.getSelectionModel().getSelectedIndex(), vendorSKUCol);
                });
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
                    Platform.runLater(() -> dataUI.getTextArea(PurchaseOrderEntry_.lineNote).requestFocus());
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fEntityList.remove(fEntity);
                    if (fEntityList.isEmpty()) {
                        fTableView.getSelectionModel().select(null);
                    }
                    updateTotal();
                    fTableView.refresh();
                }
                break;
            case SHIP_TO_SELECT:
                CustomerShipToListUI shipToUI = new CustomerShipToListUI();
                fInputDialog = createSelectCancelUIDialog(shipToUI.getView(), "Ship To");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (shipToUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fCustomer = (Customer) shipToUI.getTableView().getSelectionModel().getSelectedItem();
                        if (!shipToUI.getShipToTable().getItems().isEmpty() && shipToUI.getShipToTable().getSelectionModel().getSelectedItem() != null) {
                            fCustomerShipTo = (CustomerShipTo) shipToUI.getShipToTable().getSelectionModel().getSelectedItem();
                            fPurchaseOrder.setCustomerShipTo(fCustomerShipTo);
                            fPurchaseOrder.setCustomer(fCustomer);
                            fShipToAddress.setText(getShipToAddress(fPurchaseOrder.getCustomerShipTo()));
                        }
                    }
                });
                fInputDialog.show();
                break;
            case AppConstants.ACTION_SELECT_LIST:
                VendorItemListUI vendorItemWidget = new VendorItemListUI(fPurchaseOrder.getVendor());
                fInputDialog = createSelectCancelUIDialog(vendorItemWidget.getView(), "Vendor Item");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (vendorItemWidget.getSelectedItems().size() >= 1) {
                        List<PurchaseOrderEntry> list = fTableView.getItems().stream().filter(p -> p.getItem() == null).collect(Collectors.toList());
                        if (!list.isEmpty()) {
                            fTableView.getItems().removeAll(list);
                        }
                        int i = fTableView.getItems().size();
                        vendorItemWidget.getSelectedItems().forEach(vi -> {
                            PurchaseOrderEntry newPurchaseOrderEntry = new PurchaseOrderEntry();
                            newPurchaseOrderEntry.setItem(vi.getItem());
                            newPurchaseOrderEntry.setItemLookUpCode(vi.getItem().getItemLookUpCode());
                            newPurchaseOrderEntry.setItemDescription(getItemDescription(vi.getItem()));
                            newPurchaseOrderEntry.setVendorItemLookUpCode(vi.getVendorItemLookUpCode());
                            if (vi.getItem().getWeight() != null) {
                                newPurchaseOrderEntry.setWeight(vi.getItem().getWeight());
                            } else {
                                newPurchaseOrderEntry.setWeight(BigDecimal.ZERO);
                            }
                            newPurchaseOrderEntry.setUnitOfMeasure(vi.getItem().getUnitOfMeasure());
                            newPurchaseOrderEntry.setPurchaseOrder(fPurchaseOrder);
                            newPurchaseOrderEntry.setCost(vi.getCost());
                            newPurchaseOrderEntry.setQuantityOrdered(BigDecimal.ONE);
                            newPurchaseOrderEntry.setProcessedTag(Boolean.FALSE);
                            fTableView.getItems().add(newPurchaseOrderEntry);
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
                fInputDialog.show();
                break;
            case AppConstants.ACTION_ASSIGN_SERIAL_NUMBER:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getSerialNumbers().isEmpty()) {
                        PurchaseOrderEntry purchaseOrderEntry = fTableView.getSelectionModel().getSelectedItem();
                        SerialNumberListUI serialNumberListUI = new SerialNumberListUI(purchaseOrderEntry);
                        serialNumberListUI.setParent(this);
                        fInputDialog = createUIDialog(serialNumberListUI.getView(), "Serial Number");
                        serialNumberListUI.selectButton.setOnAction(e -> {
                            List<SerialNumber> list = new ArrayList<>(serialNumberListUI.getSelectedItems());
                            fInputDialog.close();
                            purchaseOrderEntry.setSerialNumbers(list);
                            fTableView.refresh();
                        });
                        serialNumberListUI.closeButton.setOnAction(e -> {
                            fInputDialog.close();
                        });
                        fInputDialog.show();
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
                                e.setPurchaseOrderEntry(null);
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
            case AppConstants.ACTION_RECEIVE_ALL:
                fTableView.getItems().stream().filter(p -> p.getVendorItemLookUpCode() != null).forEach(p -> {
                    p.setQuantityReceived(p.getQuantityOrdered());
                });
                fTableView.refresh();
                updateTotal();
                break;
            case AppConstants.ACTION_PRINT:
                update();
                if (!fPurchaseOrder.getPurchaseOrderEntries().isEmpty() && fPurchaseOrder.getTotal() != null) {
                    PurchaseOrderLayout layout = new PurchaseOrderLayout(fPurchaseOrder);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    showAlertDialog("Can't print a empty purchase order! ");
                }
                break;
            case AppConstants.ACTION_VOID:
                Response answer = createConfirmResponseDialog("Are you sure to void this purchase order?");
                if (answer.equals(Response.YES)) {
                    if (fPurchaseOrder.getId() != null) {
                        daoPurchaseOrder.delete(fPurchaseOrder);
                    }
                    getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH);
                }
                break;
            case AppConstants.ACTION_SAVE:
                update();
                if (!fPurchaseOrder.getPurchaseOrderEntries().isEmpty()) {
                    if (fPurchaseOrder.getId() == null) {
                        daoPurchaseOrder.insert(fPurchaseOrder);
                    } else {
                        daoPurchaseOrder.update(fPurchaseOrder);
                    }
                    fPurchaseOrder.getPurchaseOrderEntries().forEach(e -> {
                        if (e.getSerialNumbers() != null && !e.getSerialNumbers().isEmpty()) {
                            e.getSerialNumbers().forEach(s -> {
                                s.setPurchaseOrderEntry(e);
                                daoSerialNumber.update(s);
                            });
                        }
                    });
                    getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH);
                } else {
                    showAlertDialog("Can't save a empty purchase order! ");
                }
                break;
            case AppConstants.ACTION_PROCESS:
                update();
                if (!fPurchaseOrder.getPurchaseOrderEntries().isEmpty() && fPurchaseOrder.getTotal() != null && fPurchaseOrder.getTotal().compareTo(BigDecimal.ZERO) != 0) {
                    if (fPurchaseOrder.getStatus().equals(DBConstants.STATUS_OPEN)) {
                        fPurchaseOrder.setStatus(DBConstants.STATUS_IN_PROGRESS);
                        if (fPurchaseOrder.getId() == null) {
                            daoPurchaseOrder.insert(fPurchaseOrder);
                        } else {
                            daoPurchaseOrder.update(fPurchaseOrder);
                        }
                        fPurchaseOrder.getPurchaseOrderEntries().forEach(e -> {
                            if (e.getSerialNumbers() != null && !e.getSerialNumbers().isEmpty()) {
                                e.getSerialNumbers().forEach(s -> {
                                    s.setPurchaseOrderEntry(e);
                                    daoSerialNumber.update(s);
                                });
                            }
                        });
                        getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH);
                    } else {
                        BigDecimal receivedTotal = fTableView.getItems().stream()
                                .map(e -> {
                                    return zeroIfNull(e.getCost()).multiply(zeroIfNull(e.getQuantityReceived()));
                                })
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        if (receivedTotal.compareTo(BigDecimal.ZERO) > 0) {
                            fPurchaseOrder.setDatePurchased(new Timestamp(new Date().getTime()));
                            if (fPurchaseOrder.getVendorInvoiceNumber() == null || fPurchaseOrder.getVendorInvoiceNumber().isEmpty()) {
                                Response confirm = createConfirmResponseDialog("Do you want to proceed without vendor invoice number?");
                                if (confirm.equals(Response.YES)) {
                                    processPO();
                                }
                            } else {
                                processPO();
                            }
                        } else {
                            showAlertDialog("Nothing to receive! ");
                        }
                    }
                } else {
                    showAlertDialog("Can't process a empty purchase order! ");
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
                        fTableView.getFocusModel().focus(i, vendorSKUCol);
                    }

                }
                break;
        }
    }

    private void createGUI() {
        fTableView = new TableView<PurchaseOrderEntry>() {
            @Override
            public void edit(int row, TableColumn<PurchaseOrderEntry, ?> column) {
                if (row >= 0 && row <= (getItems().size() - 1)) {
                    PurchaseOrderEntry entry = getItems().get(row);
                    if (entry.getItem() != null && column.equals(vendorSKUCol)) {
                        return;
                    }
                    if (entry.getItem() == null && column != null && (column.equals(qtyOrderedCol) || column.equals(qtyReceivedCol) || column.equals(costCol))) {
                        return;
                    }
                }
                super.edit(row, column);
            }
        };
        fTableView.setEditable(true);
        addKeyListener();
        if (!fPurchaseOrder.getPurchaseOrderEntries().isEmpty()) {
            List<PurchaseOrderEntry> list = fPurchaseOrder.getPurchaseOrderEntries().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
        } else {
            fEntityList = FXCollections.observableArrayList();
        }
        fTableView.setItems(fEntityList);
        fVendor = fPurchaseOrder.getVendor();
        fVendorContactList = FXCollections.observableList(fVendor.getVendorContacts());
        fShippingServiceCombo = new VendorShippingWidget(fVendor);
        fVendorContactCombo = new VendorContactWidget(fVendor);
        fVendorContactTable.setItems(fVendorContactList);
        updateInfoPane();
        mainView = createMainView();
        if (fPurchaseOrder.getStatus().equals(DBConstants.STATUS_IN_PROGRESS)) {
            vendorSKUCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.125));
            yourSKUCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.115));
            descriptionCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.243));
            qtyOrderedCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.10));
            qtyReceivedCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.10));
            costCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.10));
            weightCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.08));
            totalCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.12));
            receiveAllButton.setVisible(true);

        } else {
            qtyReceivedCol.setVisible(false);
            vendorSKUCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.15));
            yourSKUCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.14));
            descriptionCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.273));
            qtyOrderedCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.10));
            weightCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.08));
            costCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.115));
            totalCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.125));
            receiveAllButton.setVisible(false);
        }
        fEditPane = createEditPane();
        fVendorAddress.setEditable(false);
        fShippingServiceCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends VendorShippingService> observable, VendorShippingService newValue, VendorShippingService oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                Date date = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                Calendar cc = addBusinessDay(cal, observable.getValue().getDeliveryDay());
                date = cc.getTime();
                ((DatePicker) purchaseOrderUI.getUIComponent(PurchaseOrder_.dateExpected)).setValue(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
        });
        fTableView.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> paramObservableValue, Number prevRowIndex, Number currentRowIndex) -> {
            if (currentRowIndex.intValue() > -1
                    && fTableView.getItems().get(currentRowIndex.intValue()).getItem() != null
                    && fTableView.getItems().get(currentRowIndex.intValue()).getItem().getVendorItems() != null
                    && !fTableView.getItems().get(currentRowIndex.intValue()).getItem().getVendorItems().isEmpty()) {
                fVendorItemList = FXCollections.observableList(fTableView.getItems().get(currentRowIndex.intValue()).getItem().getVendorItems());
            } else {
                fVendorItemList = FXCollections.observableArrayList();
            }
            fVendorItemTable.setItems(fVendorItemList);
        });

        dialogTitle = "Purchase Order Entry";

        shipToCustomerCheckBox.setIndeterminate(false);
        if (fPurchaseOrder.getStatus().equals(DBConstants.STATUS_OPEN)) {
            shipToCustomerCheckBox.setDisable(false);
            selectShipToBtn.setDisable(false);
        } else {
            shipToCustomerCheckBox.setDisable(true);
            selectShipToBtn.setDisable(true);
        }
        shipToCustomerCheckBox.selectedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if (new_val) {
                selectShipToBtn.setDisable(false);
                fShipToAddress.setText(getShipToAddress(fPurchaseOrder.getCustomerShipTo()));
            } else {
                selectShipToBtn.setDisable(true);
                fCustomerShipTo = null;
                fPurchaseOrder.setShipToAddress(getStoreShipToAddress());
                fPurchaseOrder.setCustomerShipTo(null);
                fPurchaseOrder.setCustomer(null);
                fShipToAddress.setText(fPurchaseOrder.getShipToAddress());
            }
        });
        if (fPurchaseOrder.getCustomerShipTo() != null) {
            shipToCustomerCheckBox.setSelected(true);
            selectShipToBtn.setDisable(false);
            fShipToAddress.setText(getShipToAddress(fPurchaseOrder.getCustomerShipTo()));
        } else {
            shipToCustomerCheckBox.setSelected(false);
            selectShipToBtn.setDisable(true);
            fShipToAddress.setText(fPurchaseOrder.getShipToAddress());
        }
        if (fTableView.getItems().isEmpty()) {
            handleAction(AppConstants.ACTION_ADD);
        } else {
            fTableView.getSelectionModel().selectFirst();
            fTableView.getSelectionModel().select(0, qtyOrderedCol);
        }
        fTableView.setRowFactory(mouseClickListener(1));
        try {
            purchaseOrderUI.setData(fPurchaseOrder);
            updateTotal();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);
        vendorSKUCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.vendorItemLookUpCode.getName()));
        vendorSKUCol.setCellFactory(editableCell(Pos.TOP_LEFT));
        vendorSKUCol.setOnEditCommit((TableColumn.CellEditEvent<PurchaseOrderEntry, String> t) -> {
            PurchaseOrderEntry poe = (PurchaseOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
            fTableView.refresh();
            updateTotal();
            if (poe != null && poe.getItem() != null && !poe.getItem().getVendorItems().isEmpty()) {
                fVendorItemList = FXCollections.observableList(poe.getItem().getVendorItems());
                fVendorItemTable.setItems(fVendorItemList);
                handleAction(AppConstants.ACTION_ADD);
            } else {
                fVendorItemList = FXCollections.observableArrayList();
                fVendorItemTable.setItems(fVendorItemList);
            }
        });

        yourSKUCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.itemLookUpCode.getName()));
        yourSKUCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        yourSKUCol.setEditable(false);
        yourSKUCol.setSortable(false);

        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null) {
                String description = p.getValue().getItemDescription();
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

        qtyOrderedCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.quantityOrdered.getName()));
        qtyOrderedCol.setOnEditCommit((TableColumn.CellEditEvent<PurchaseOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                PurchaseOrderEntry poe = (PurchaseOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Item item = poe.getItem();
                if (item != null) {
                    if (!item.getFractionTag()) {
                        BigDecimal truncated = t.getNewValue().setScale(0, RoundingMode.DOWN);
                        poe.setQuantityOrdered(truncated);
                    } else {
                        poe.setQuantityOrdered(t.getNewValue());
                    }
                }
                fTableView.refresh();
                updateTotal();
            }
        });
        qtyOrderedCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        qtyOrderedCol.setSortable(false);

        qtyReceivedCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.quantityReceived.getName()));
        qtyReceivedCol.setOnEditCommit((TableColumn.CellEditEvent<PurchaseOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null) {
                PurchaseOrderEntry poe = (PurchaseOrderEntry) t.getTableView().getItems().get(t.getTablePosition().getRow());
                Item item = poe.getItem();
                if (item != null) {
                    if (!item.getFractionTag()) {
                        BigDecimal truncated = t.getNewValue().setScale(0, RoundingMode.DOWN);
                        poe.setQuantityReceived(truncated);
                    } else {
                        poe.setQuantityReceived(t.getNewValue());
                    }
                }
                fTableView.refresh();
                updateTotal();
            }
        });
        qtyReceivedCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        qtyReceivedCol.setSortable(false);

        costCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.cost.getName()));
        costCol.setOnEditCommit((TableColumn.CellEditEvent<PurchaseOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null && t.getTableView().getItems().get(t.getTablePosition().getRow()) != null && t.getTableView().getItems().get(t.getTablePosition().getRow()).getItem() != null) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setCost(t.getNewValue());
                fTableView.refresh();
                updateTotal();
            }
        });
        costCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        costCol.setSortable(false);

        weightCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.weight.getName()));
        weightCol.setOnEditCommit((TableColumn.CellEditEvent<PurchaseOrderEntry, BigDecimal> t) -> {
            if (t.getNewValue() != null && t.getTableView().getItems().get(t.getTablePosition().getRow()) != null && t.getTableView().getItems().get(t.getTablePosition().getRow()).getItem() != null) {
                t.getTableView().getItems().get(t.getTablePosition().getRow()).setWeight(t.getNewValue());
                fTableView.refresh();
            }
        });
        weightCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        weightCol.setSortable(false);

        totalCol.setCellValueFactory((CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            BigDecimal total;
            if (fPurchaseOrder.getStatus().equals(DBConstants.STATUS_IN_PROGRESS)) {
                if (p.getValue() != null && p.getValue().getCost() != null && p.getValue().getQuantityReceived() != null) {
                    total = p.getValue().getCost().multiply(p.getValue().getQuantityReceived());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return new SimpleStringProperty("");
                }
            } else {
                if (p.getValue() != null && p.getValue().getCost() != null && p.getValue().getQuantityOrdered() != null) {
                    total = p.getValue().getCost().multiply(p.getValue().getQuantityOrdered());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        totalCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        totalCol.setEditable(false);
        totalCol.setSortable(false);

        fTableView.getColumns().add(vendorSKUCol);
        fTableView.getColumns().add(yourSKUCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyOrderedCol);
        fTableView.getColumns().add(qtyReceivedCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(weightCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(400);

        GridPane.setHgrow(fTableView, Priority.ALWAYS);

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
        add(editPane, "Line Note:", dataUI.createTextArea(PurchaseOrderEntry_.lineNote), 80, 250, fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    private GridPane createSettingPane() {
        GridPane settingPane = new GridPane();
        settingPane.setHgap(4.0);
        Label vendorTermLabel = new Label("Term:");
        purchaseOrderUI.setUIComponent(PurchaseOrder_.vendorTerm, fVendorTermCombo);
        fVendorTermCombo.setPrefWidth(80);
        fVendorTermCombo.setStyle("-fx-font-family: Arial Narrow;-fx-font-size: 10px;");
        settingPane.add(vendorTermLabel, 2, 0);
        settingPane.add(fVendorTermCombo, 3, 0);
        Label vendorContactLabel = new Label("Contact:");
        purchaseOrderUI.setUIComponent(PurchaseOrder_.vendorContact, fVendorContactCombo);
        fVendorContactCombo.setPrefWidth(105);
        fVendorContactCombo.setStyle("-fx-font-family: Arial Narrow;-fx-font-size: 10px;");
        settingPane.add(vendorContactLabel, 4, 0);
        settingPane.add(fVendorContactCombo, 5, 0);
        Label shipViaLabel = new Label("Ship Via:");
        settingPane.add(shipViaLabel, 6, 0);
        purchaseOrderUI.setUIComponent(PurchaseOrder_.vendorShippingService, fShippingServiceCombo);
        fShippingServiceCombo.setPrefWidth(200);
        fShippingServiceCombo.setStyle("-fx-font-family: Arial Narrow;-fx-font-size: 10px;");
        settingPane.add(fShippingServiceCombo, 7, 0);
        DatePicker dateExpected = new DatePicker();
        purchaseOrderUI.setUIComponent(PurchaseOrder_.dateExpected, dateExpected);
        dateExpected.setPrefWidth(100);
        Label expectedDateLabel = new Label("Expected:");
        settingPane.add(expectedDateLabel, 8, 0);
        settingPane.add(dateExpected, 9, 0);
        Label invoiceDateLabel = new Label("Invoiced:");
        DatePicker invoiceDate = new DatePicker();
        invoiceDate.setPrefWidth(100);
        purchaseOrderUI.setUIComponent(PurchaseOrder_.dateInvoiced, invoiceDate);
        settingPane.add(invoiceDateLabel, 10, 0);
        settingPane.add(invoiceDate, 11, 0);
        Label invoiceNumberLabel = new Label("Invo No.:");
        settingPane.add(invoiceNumberLabel, 12, 0);
        settingPane.add(purchaseOrderUI.createTextField(PurchaseOrder_.vendorInvoiceNumber, 92), 13, 0);
        settingPane.setPadding(new Insets(1));

        GridPane.setHalignment(vendorTermLabel, HPos.RIGHT);
        GridPane.setHalignment(expectedDateLabel, HPos.RIGHT);
        GridPane.setHalignment(shipViaLabel, HPos.RIGHT);
        GridPane.setHalignment(invoiceDateLabel, HPos.RIGHT);
        GridPane.setHalignment(invoiceNumberLabel, HPos.RIGHT);

        return settingPane;
    }

    private Node createBottomPane() {
        TabPane tabPane = new TabPane();
        tabPane.getStyleClass().add("border");

        TableColumn<VendorItem, String> vendorNameCol = new TableColumn<>("Vendor");
        vendorNameCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getVendor() != null) {
                return new SimpleStringProperty(p.getValue().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        vendorNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorNameCol.setSortable(false);
        vendorNameCol.setPrefWidth(250);

        TableColumn<VendorItem, String> lastCostCol = new TableColumn<>("Cost");
        lastCostCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.cost.getName()));
        lastCostCol.setCellFactory(stringCell(Pos.CENTER));
        lastCostCol.setSortable(false);
        lastCostCol.setPrefWidth(150);

        TableColumn<VendorItem, String> lastReceviedCol = new TableColumn<>("Last Received");
        lastReceviedCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.lastUpdated.getName()));
        lastReceviedCol.setCellFactory(stringCell(Pos.CENTER));
        lastReceviedCol.setSortable(false);
        lastReceviedCol.setPrefWidth(150);

        fVendorItemTable.getColumns().add(vendorNameCol);
        fVendorItemTable.getColumns().add(lastCostCol);
        fVendorItemTable.getColumns().add(lastReceviedCol);
        fVendorItemTable.setSelectionModel(null);
        fVendorItemTable.setPrefHeight(100);
        setTableWidth(fVendorItemTable);

        Tab vendorItemTab = new Tab(" History");
        vendorItemTab.setContent(fVendorItemTable);
        vendorItemTab.setClosable(false);
        tabPane.getTabs().add(vendorItemTab);

        TableColumn<VendorContact, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.contactName.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setSortable(false);
        nameCol.setPrefWidth(120);

        TableColumn<VendorContact, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.phoneNumber.getName()));
        phoneCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneCol.setSortable(false);
        phoneCol.setPrefWidth(150);

        TableColumn<VendorContact, String> cellCol = new TableColumn<>("Cell");
        cellCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.cellPhoneNumber.getName()));
        cellCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cellCol.setSortable(false);
        cellCol.setPrefWidth(90);

        TableColumn<VendorContact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.emailAddress.getName()));
        emailCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        emailCol.setSortable(false);
        emailCol.setPrefWidth(130);

        fVendorContactTable.getColumns().add(nameCol);
        fVendorContactTable.getColumns().add(phoneCol);
        fVendorContactTable.getColumns().add(cellCol);
        fVendorContactTable.getColumns().add(emailCol);
        fVendorContactTable.setPrefHeight(120);
        setTableWidth(fVendorContactTable);

        VBox infoBox = new VBox();
        Label infoLabel = new Label("Address");
        fInfo.setEditable(false);
        fInfo.setFont(new Font("Verdana", 10));
        fInfo.setPrefSize(200, 120);
        infoBox.getChildren().addAll(infoLabel, fInfo);

        VBox tableBox = new VBox();
        Label tableLabel = new Label("Contact");
        tableBox.getChildren().addAll(tableLabel, fVendorContactTable);

        HBox vendorBox = new HBox();
        vendorBox.setSpacing(5);
        vendorBox.getChildren().addAll(infoBox, tableBox);
        vendorBox.setAlignment(Pos.CENTER);
        vendorBox.getStyleClass().add("hboxPane");

        Tab vendorTab = new Tab(" Vendor ");
        vendorTab.setContent(vendorBox);
        vendorTab.setClosable(false);
        tabPane.getTabs().add(vendorTab);

        Image imageSelect = new Image(RES.ARCHIVE_INSERT);
        selectShipToBtn = new Button("Select", new ImageView(imageSelect));
        selectShipToBtn.setId(SHIP_TO_SELECT);
        selectShipToBtn.setOnAction(fHandler);
        selectShipToBtn.setDisable(true);
        fShipToAddress.setEditable(false);
        fShipToAddress.setFont(Font.font("Verdana", 10));
        fShipToAddress.setPrefSize(300, 100);

        GridPane shipToPane = new GridPane();
        shipToPane.setPadding(new Insets(8));
        shipToPane.setHgap(2.0);
        shipToPane.setVgap(3.0);
        shipToPane.add(shipToCustomerCheckBox, 3, 0);
        shipToPane.add(selectShipToBtn, 4, 0);
        shipToPane.add(fShipToAddress, 2, 1, 4, 1);

        GridPane.setHalignment(shipToCustomerCheckBox, HPos.LEFT);
        GridPane.setHalignment(selectShipToBtn, HPos.RIGHT);
        GridPane.setHalignment(fShipToAddress, HPos.RIGHT);
        GridPane.setHgrow(fShipToAddress, Priority.ALWAYS);

        Tab shipToTab = new Tab("Ship To");
        shipToTab.setContent(shipToPane);
        shipToTab.setClosable(false);
        tabPane.getTabs().add(shipToTab);

        GridPane notePane = new GridPane();
        notePane.setPadding(new Insets(8));
        Label confirmationLabel = new Label("Confirmation Number: ");
        GridPane.setHalignment(confirmationLabel, HPos.RIGHT);
        notePane.add(confirmationLabel, 0, 1);
        notePane.add(purchaseOrderUI.createTextField(PurchaseOrder_.confirmationNumber, 92), 1, 1);
        Label noteLabel = new Label("Note:");
        GridPane.setHalignment(noteLabel, HPos.RIGHT);
        notePane.add(noteLabel, 0, 2);
        notePane.add(purchaseOrderUI.createTextArea(PurchaseOrder_.note), 1, 2, 4, 1);
        purchaseOrderUI.getTextArea(PurchaseOrder_.note).setPrefSize(400, 80);

        Tab noteTab = new Tab("Note");
        noteTab.setContent(notePane);
        noteTab.setClosable(false);
        tabPane.getTabs().add(noteTab);
        GridPane totalPane = new GridPane();
        Label orderTotalLabel = new Label("Order Total: ");
        totalPane.add(orderTotalLabel, 0, 0);
        totalPane.add(purchaseOrderUI.createLabelField(PurchaseOrder_.total, 90, Pos.CENTER_RIGHT), 1, 0);
        Label receivedTotalLabel = new Label("Received Total: ");
        totalPane.add(receivedTotalLabel, 0, 1);
        totalPane.add(purchaseOrderUI.createLabelField(PurchaseOrder_.totalReceived, 90, Pos.CENTER_RIGHT), 1, 1);
        Label taxOnOrderLabel = new Label("Tax On Order: ");
        totalPane.add(taxOnOrderLabel, 0, 2);
        totalPane.add(purchaseOrderUI.createTextField(PurchaseOrder_.taxOnOrderAmount, 90), 1, 2);
        Label taxOnFreightLabel = new Label("Tax On Freight: ");
        totalPane.add(taxOnFreightLabel, 0, 3);
        totalPane.add(purchaseOrderUI.createTextField(PurchaseOrder_.taxOnFreightAmount, 90), 1, 3);
        Label freightInvoicedLabel = new Label("Freight Invoiced: ");
        totalPane.add(freightInvoicedLabel, 0, 4);
        totalPane.add(purchaseOrderUI.createTextField(PurchaseOrder_.freightInvoicedAmount, 90), 1, 4);
        Label freightPrePaidLabel = new Label("Freight PrePaid: ");
        totalPane.add(freightPrePaidLabel, 0, 5);
        totalPane.add(purchaseOrderUI.createTextField(PurchaseOrder_.freightPrePaidAmount, 90), 1, 5);

        Label subTotalLabel = new Label(" Total: ");
        TextField subTotalField = createLabelField(90, Pos.CENTER_RIGHT);
        subTotalField.textProperty().bindBidirectional(totalProperty, getDecimalFormat());
        totalPane.add(subTotalLabel, 0, 6);
        totalPane.add(subTotalField, 1, 6);

        GridPane.setHalignment(subTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(subTotalField, HPos.RIGHT);
        GridPane.setHalignment(taxOnOrderLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderUI.getTextField(PurchaseOrder_.taxOnOrderAmount), HPos.RIGHT);
        GridPane.setHalignment(taxOnFreightLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderUI.getTextField(PurchaseOrder_.taxOnFreightAmount), HPos.RIGHT);
        GridPane.setHalignment(freightInvoicedLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderUI.getTextField(PurchaseOrder_.freightInvoicedAmount), HPos.RIGHT);
        GridPane.setHalignment(freightPrePaidLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderUI.getTextField(PurchaseOrder_.freightPrePaidAmount), HPos.RIGHT);
        GridPane.setHalignment(orderTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderUI.getTextField(PurchaseOrder_.total), HPos.RIGHT);
        GridPane.setHalignment(receivedTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderUI.getTextField(PurchaseOrder_.totalReceived), HPos.RIGHT);
        totalPane.setAlignment(Pos.TOP_RIGHT);

        purchaseOrderUI.getTextField(PurchaseOrder_.taxOnOrderAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderUI.getTextField(PurchaseOrder_.taxOnFreightAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderUI.getTextField(PurchaseOrder_.freightInvoicedAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderUI.getTextField(PurchaseOrder_.freightPrePaidAmount).setAlignment(Pos.CENTER_RIGHT);

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
        BigDecimal feeTotal = BigDecimal.ZERO;
        BigDecimal taxTotal = BigDecimal.ZERO;
        BigDecimal total;
        try {
            purchaseOrderUI.getData(fPurchaseOrder);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        BigDecimal subTotal = fTableView.getItems().stream()
                .map(poe -> {
                    return zeroIfNull(poe.getCost()).multiply(zeroIfNull(poe.getQuantityOrdered()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal receivedTotal = fTableView.getItems().stream()
                .map(poe -> {
                    return zeroIfNull(poe.getCost()).multiply(zeroIfNull(poe.getQuantityReceived()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        purchaseOrderUI.getTextField(PurchaseOrder_.total).setText(getString(subTotal));
        purchaseOrderUI.getTextField(PurchaseOrder_.totalReceived).setText(getString(receivedTotal));
        if (isNumeric(purchaseOrderUI.getTextField(PurchaseOrder_.taxOnOrderAmount).getText())) {
            taxTotal = taxTotal.add(new BigDecimal(purchaseOrderUI.getTextField(PurchaseOrder_.taxOnOrderAmount).getText()));
        }
        if (isNumeric(purchaseOrderUI.getTextField(PurchaseOrder_.taxOnFreightAmount).getText())) {
            taxTotal = taxTotal.add(new BigDecimal(purchaseOrderUI.getTextField(PurchaseOrder_.taxOnFreightAmount).getText()));
        }
        if (isNumeric(purchaseOrderUI.getTextField(PurchaseOrder_.freightInvoicedAmount).getText())) {
            feeTotal = feeTotal.add(new BigDecimal(purchaseOrderUI.getTextField(PurchaseOrder_.freightInvoicedAmount).getText()));
        }
        if (isNumeric(purchaseOrderUI.getTextField(PurchaseOrder_.freightPrePaidAmount).getText())) {
            feeTotal = feeTotal.subtract(new BigDecimal(purchaseOrderUI.getTextField(PurchaseOrder_.freightPrePaidAmount).getText()));
        }
        if (fPurchaseOrder.getStatus().equals(DBConstants.STATUS_OPEN)) {
            total = subTotal.add(taxTotal).add(feeTotal);
        } else {
            total = receivedTotal.add(taxTotal).add(feeTotal);
        }
        totalProperty.set(total);
        if (subTotal.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal taxRate = taxTotal.divide(subTotal, 6, RoundingMode.HALF_UP);
            fTableView.getItems().forEach(p -> {
                p.setTaxRate(taxRate);
            });
        } else {
            fTableView.getItems().forEach(p -> {
                p.setTaxRate(BigDecimal.ZERO);
            });
        }
        fTableView.refresh();

    }

    private void updateInfoPane() {
        if (fPurchaseOrder.getVendor() == null) {
            fVendorAddress.setText("");
            fInfo.setText("");
        } else {
            String info = "";
            if (fPurchaseOrder.getVendor().getCompany() != null) {
                info = info + fPurchaseOrder.getVendor().getCompany() + "\n";
            }
            if (!isEmpty(fPurchaseOrder.getVendor().getAddress1())) {
                info = info + fPurchaseOrder.getVendor().getAddress1() + "\n";
            }
            if (!isEmpty(fPurchaseOrder.getVendor().getAddress2())) {
                info = info + fPurchaseOrder.getVendor().getAddress2() + "\n";
            }
            info = info + (!isEmpty(fPurchaseOrder.getVendor().getCity()) ? fPurchaseOrder.getVendor().getCity() : "")
                    + (!isEmpty(fPurchaseOrder.getVendor().getCity()) ? ", " : "")
                    + (!isEmpty(fPurchaseOrder.getVendor().getState()) ? fPurchaseOrder.getVendor().getState() : "")
                    + (!isEmpty(fPurchaseOrder.getVendor().getState()) ? " " : "")
                    + (!isEmpty(fPurchaseOrder.getVendor().getPostCode()) ? fPurchaseOrder.getVendor().getPostCode() : "")
                    + "\n";
            if (fPurchaseOrder.getVendor().getCountry() != null) {
                info = info + fPurchaseOrder.getVendor().getCountry().getIsoCode3() + "\n";
            }
            if (!isEmpty(fPurchaseOrder.getVendor().getPhoneNumber())) {
                info = info + "Phone: " + fPurchaseOrder.getVendor().getPhoneNumber() + "\n";
            }
            if (fPurchaseOrder.getVendor().getVendorTerm() != null) {
                info = info + "Terms: " + fPurchaseOrder.getVendor().getVendorTerm().getCode();
            }
            fInfo.setText(info);
            fShipToAddress.setText(fPurchaseOrder.getShipToAddress());
        }
    }

    private void update() {
        updateTotal();
        try {
            purchaseOrderUI.getData(fPurchaseOrder);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        if (!fPurchaseOrder.getPurchaseOrderEntries().isEmpty()) {
            fPurchaseOrder.getPurchaseOrderEntries().clear();
        }
        for (int i = 0; i < fTableView.getItems().size(); i++) {
            PurchaseOrderEntry entry = fTableView.getItems().get(i);
            if (entry.getItem() != null) {
                entry.setDisplayOrder(i);
                entry.setPurchaseOrder(fPurchaseOrder);
                fPurchaseOrder.getPurchaseOrderEntries().add(entry);
            }
        }
    }

    private void processPO() {
        BigDecimal subTotal = fPurchaseOrder.getPurchaseOrderEntries().stream().map(p -> zeroIfNull(p.getCost()).multiply(zeroIfNull(p.getQuantityReceived()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        if (subTotal.compareTo(BigDecimal.ZERO) != 0) {
            PurchaseOrderHistory purchaseOrderHistory = new PurchaseOrderHistory();
            PurchaseOrder newPurchaseOrder = new PurchaseOrder();
            ArrayList<PurchaseOrderEntry> list = new ArrayList<>();
            fPurchaseOrder.getPurchaseOrderEntries().stream().forEachOrdered((x) -> {
                if (x.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0) {
                    PurchaseOrderHistoryEntry pohe = new PurchaseOrderHistoryEntry();
                    pohe.setQuantityReceived(x.getQuantityReceived());
                    pohe.setQuantityOrdered(x.getQuantityOrdered());
                    pohe.setPurchaseOrderHistory(purchaseOrderHistory);
                    pohe.setCost(x.getCost());
                    pohe.setDisplayOrder(x.getDisplayOrder());
                    pohe.setDateReceived(new Timestamp(new Date().getTime()));
                    pohe.setDiscountAmount(x.getDiscountAmount());
                    pohe.setItem(x.getItem());
                    pohe.setItemDescription(x.getItemDescription());
                    pohe.setItemLookUpCode(x.getItemLookUpCode());
                    pohe.setVendorItemLookUpCode(x.getVendorItemLookUpCode());
                    pohe.setWeight(x.getWeight());
                    pohe.setTaxRate(x.getTaxRate());
                    pohe.setUnitOfMeasure(x.getItem().getUnitOfMeasure());
                    pohe.setLineNote(x.getLineNote());
                    if (x.getItem() != null && x.getItem().getCategoryName() != null) {
                        pohe.setCategoryName(x.getItem().getCategoryName());
                    }
                    pohe.setSerialNumbers(x.getSerialNumbers());
                    pohe.setStore(Config.getStore());
                    purchaseOrderHistory.getPurchaseOrderHistoryEntries().add(pohe);
                    if (x.getQuantityOrdered().compareTo(x.getQuantityReceived()) > 0) {
                        PurchaseOrderEntry newPurchaseOrderEntry = new PurchaseOrderEntry();
                        newPurchaseOrderEntry.setCost(x.getCost());
                        newPurchaseOrderEntry.setDisplayOrder(x.getDisplayOrder());
                        newPurchaseOrderEntry.setTaxRate(x.getTaxRate());
                        newPurchaseOrderEntry.setItem(x.getItem());
                        newPurchaseOrderEntry.setLineNote(x.getLineNote());
                        newPurchaseOrderEntry.setItemLookUpCode(x.getItemLookUpCode());
                        newPurchaseOrderEntry.setProcessedTag(Boolean.FALSE);
                        newPurchaseOrderEntry.setItemDescription(x.getItemDescription());
                        newPurchaseOrderEntry.setPurchaseOrder(newPurchaseOrder);
                        newPurchaseOrderEntry.setQuantityPerUom(x.getQuantityPerUom());
                        newPurchaseOrderEntry.setQuantityOrdered(x.getQuantityOrdered().subtract(x.getQuantityReceived()));
                        newPurchaseOrderEntry.setQuantityReceived(BigDecimal.ZERO);
                        newPurchaseOrderEntry.setUnitOfMeasure(x.getUnitOfMeasure());
                        newPurchaseOrderEntry.setVendorItemLookUpCode(x.getVendorItemLookUpCode());
                        newPurchaseOrderEntry.setWeight(x.getWeight());
                        list.add(newPurchaseOrderEntry);
                    }
                } else {
                    PurchaseOrderEntry newPurchaseOrderEntry = new PurchaseOrderEntry();
                    newPurchaseOrderEntry.setCost(x.getCost());
                    newPurchaseOrderEntry.setDisplayOrder(x.getDisplayOrder());
                    newPurchaseOrderEntry.setTaxRate(x.getTaxRate());
                    newPurchaseOrderEntry.setItem(x.getItem());
                    newPurchaseOrderEntry.setLineNote(x.getLineNote());
                    newPurchaseOrderEntry.setItemLookUpCode(x.getItemLookUpCode());
                    newPurchaseOrderEntry.setProcessedTag(Boolean.FALSE);
                    newPurchaseOrderEntry.setItemDescription(x.getItemDescription());
                    newPurchaseOrderEntry.setPurchaseOrder(newPurchaseOrder);
                    newPurchaseOrderEntry.setQuantityPerUom(x.getQuantityPerUom());
                    newPurchaseOrderEntry.setQuantityOrdered(x.getQuantityOrdered());
                    newPurchaseOrderEntry.setQuantityReceived(BigDecimal.ZERO);
                    newPurchaseOrderEntry.setUnitOfMeasure(x.getUnitOfMeasure());
                    newPurchaseOrderEntry.setVendorItemLookUpCode(x.getVendorItemLookUpCode());
                    newPurchaseOrderEntry.setWeight(x.getWeight());
                    list.add(newPurchaseOrderEntry);
                }
            });
            if (!list.isEmpty()) {
                Collections.sort(list, (e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()));
                newPurchaseOrder.setPurchaseOrderEntries(list);
                newPurchaseOrder.setConfirmationNumber(fPurchaseOrder.getConfirmationNumber());
                newPurchaseOrder.setCustomer(fPurchaseOrder.getCustomer());
                newPurchaseOrder.setCustomerShipTo(fPurchaseOrder.getCustomerShipTo());
                newPurchaseOrder.setDateCreated(fPurchaseOrder.getDateCreated());
                newPurchaseOrder.setDateExpected(fPurchaseOrder.getDateExpected());
                newPurchaseOrder.setDatePurchased(fPurchaseOrder.getDatePurchased());
                newPurchaseOrder.setDiscountDays(fPurchaseOrder.getDiscountDays());
                newPurchaseOrder.setDiscountPercent(fPurchaseOrder.getDiscountPercent());
                newPurchaseOrder.setDueTag(fPurchaseOrder.getDueTag());
                newPurchaseOrder.setEmployeePurchasedName(fPurchaseOrder.getEmployeePurchasedName());
                newPurchaseOrder.setStore(fPurchaseOrder.getStore());
                newPurchaseOrder.setNote(fPurchaseOrder.getNote());
                newPurchaseOrder.setPostedTag(fPurchaseOrder.getPostedTag());
                newPurchaseOrder.setPurchaseOrderNumber(fPurchaseOrder.getPurchaseOrderNumber());
                newPurchaseOrder.setPurchaseOrderType(fPurchaseOrder.getPurchaseOrderType());
                newPurchaseOrder.setShipToAddress(fPurchaseOrder.getShipToAddress());
                newPurchaseOrder.setStatus(DBConstants.STATUS_IN_PROGRESS);
                BigDecimal newTotal = list.stream().map(p -> zeroIfNull(p.getCost()).multiply(zeroIfNull(p.getQuantityOrdered()))).reduce(BigDecimal.ZERO, BigDecimal::add);
                newPurchaseOrder.setTotal(newTotal);
                newPurchaseOrder.setTotalReceived(BigDecimal.ZERO);
                newPurchaseOrder.setVendor(fPurchaseOrder.getVendor());
                newPurchaseOrder.setVendorContact(fPurchaseOrder.getVendorContact());
                newPurchaseOrder.setVendorName(fPurchaseOrder.getVendorName());
                newPurchaseOrder.setVendorShippingService(fPurchaseOrder.getVendorShippingService());
                newPurchaseOrder.setVendorTerm(fPurchaseOrder.getVendorTerm());
                daoPurchaseOrder.insert(newPurchaseOrder);
            }
            if (!purchaseOrderHistory.getPurchaseOrderHistoryEntries().isEmpty()) {
                purchaseOrderHistory.setDateCreated(fPurchaseOrder.getDateCreated());
                purchaseOrderHistory.setDatePurchased(fPurchaseOrder.getDatePurchased());
                if (fPurchaseOrder.getDateInvoiced() != null) {
                    purchaseOrderHistory.setDateInvoiced(fPurchaseOrder.getDateInvoiced());
                } else {
                    purchaseOrderHistory.setDateInvoiced(fPurchaseOrder.getDatePurchased());
                }
                purchaseOrderHistory.setConfirmationNumber(fPurchaseOrder.getConfirmationNumber());
                if (fPurchaseOrder.getCustomer() != null) {
                    purchaseOrderHistory.setCustomerAccountNumber(fPurchaseOrder.getCustomer().getAccountNumber());
                }
                purchaseOrderHistory.setDateReceived(new Timestamp(new Date().getTime()));
                purchaseOrderHistory.setEmployeePurchasedName(fPurchaseOrder.getEmployeePurchasedName());
                purchaseOrderHistory.setEmployeeReceivedName(Config.getEmployee().getNameOnSalesOrder());
                purchaseOrderHistory.setFreightInvoicedAmount(fPurchaseOrder.getFreightInvoicedAmount());
                purchaseOrderHistory.setFreightPrePaidAmount(fPurchaseOrder.getFreightPrePaidAmount());
                purchaseOrderHistory.setTaxOnFreightAmount(fPurchaseOrder.getTaxOnFreightAmount());
                purchaseOrderHistory.setTaxOnOrderAmount(fPurchaseOrder.getTaxOnOrderAmount());
                purchaseOrderHistory.setStore(Config.getStore());
                purchaseOrderHistory.setNote(fPurchaseOrder.getNote());
                purchaseOrderHistory.setPostedTag(Boolean.FALSE);
                purchaseOrderHistory.setPurchaseOrderNumber(fPurchaseOrder.getPurchaseOrderNumber().toString());
                purchaseOrderHistory.setShipToAddress(fPurchaseOrder.getShipToAddress());
                if (fCustomerShipTo != null) {
                    purchaseOrderHistory.setShipToAddress1(fCustomerShipTo.getAddress1());
                    purchaseOrderHistory.setShipToAddress2(fCustomerShipTo.getAddress2());
                    purchaseOrderHistory.setShipToCity(fCustomerShipTo.getCity());
                    purchaseOrderHistory.setShipToContact(fCustomerShipTo.getContactName());
                    purchaseOrderHistory.setShipToCountry(fCustomerShipTo.getCountry().getIsoCode3());
                    purchaseOrderHistory.setShipToPhoneNumber(fCustomerShipTo.getPhoneNumber());
                    if (fCustomerShipTo.getCompany() != null) {
                        purchaseOrderHistory.setShipToName(fCustomerShipTo.getCompany());
                    } else {
                        purchaseOrderHistory.setShipToName(getString(fCustomer.getFirstName()) + " " + getString(fCustomer.getLastName()));
                    }
                    purchaseOrderHistory.setShipToPostCode(fCustomerShipTo.getPostCode());
                    purchaseOrderHistory.setShipToState(fCustomerShipTo.getState());
                } else {
                    purchaseOrderHistory.setShipToAddress1(Config.getStore().getAddress1());
                    purchaseOrderHistory.setShipToAddress2(Config.getStore().getAddress2());
                    purchaseOrderHistory.setShipToCity(Config.getStore().getCity());
                    purchaseOrderHistory.setShipToContact(fPurchaseOrder.getEmployeePurchasedName());
                    purchaseOrderHistory.setShipToCountry(Config.getStore().getCountry().getIsoCode3());
                    purchaseOrderHistory.setShipToPhoneNumber(Config.getStore().getPhoneNumber());
                    purchaseOrderHistory.setShipToName(Config.getStore().getStoreName());
                    purchaseOrderHistory.setShipToPostCode(Config.getStore().getPostCode());
                    purchaseOrderHistory.setShipToState(Config.getStore().getState());
                }
                purchaseOrderHistory.setTotal(subTotal);
                purchaseOrderHistory.setVendor(fPurchaseOrder.getVendor());
                purchaseOrderHistory.setVendorAddress1(fVendor.getAddress1());
                purchaseOrderHistory.setVendorAddress2(fVendor.getAddress2());
                purchaseOrderHistory.setVendorCity(fVendor.getCity());
                purchaseOrderHistory.setVendorCode(fVendor.getVendorCode());
                purchaseOrderHistory.setVendorContactName(fVendor.getVendorContactName());
                purchaseOrderHistory.setVendorCountry(fVendor.getCountry().getIsoCode3());
                purchaseOrderHistory.setVendorFaxNumber(fVendor.getFaxNumber());
                purchaseOrderHistory.setVendorInvoiceNumber(fPurchaseOrder.getVendorInvoiceNumber());
                purchaseOrderHistory.setVendorName(fVendor.getCompany());
                purchaseOrderHistory.setVendorPhoneNumber(fVendor.getPhoneNumber());
                purchaseOrderHistory.setVendorPostCode(fVendor.getPostCode());
                purchaseOrderHistory.setVendorShippingService(fPurchaseOrder.getVendorShippingService());
                if (purchaseOrderHistory.getVendorShippingService() != null && purchaseOrderHistory.getVendorShippingService().getCode() != null) {
                    purchaseOrderHistory.setVendorShippingServiceCode(purchaseOrderHistory.getVendorShippingService().getCode());
                }
                purchaseOrderHistory.setVendorState(fVendor.getState());
                purchaseOrderHistory.setVendorTerm(fPurchaseOrder.getVendorTerm());
                if (purchaseOrderHistory.getVendorTerm() != null && purchaseOrderHistory.getVendorTerm().getCode() != null) {
                    purchaseOrderHistory.setVendorTermCode(purchaseOrderHistory.getVendorTerm().getCode());
                }
                BigDecimal totalQuantity = purchaseOrderHistory.getPurchaseOrderHistoryEntries().stream().map(p -> zeroIfNull(p.getQuantityReceived())).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal totalFreight = zeroIfNull(purchaseOrderHistory.getFreightInvoicedAmount()).add(zeroIfNull(purchaseOrderHistory.getTaxOnFreightAmount()));
                BigDecimal totalTax = zeroIfNull(purchaseOrderHistory.getTaxOnFreightAmount()).add(zeroIfNull(purchaseOrderHistory.getTaxOnOrderAmount()));
                BigDecimal totalWeight = purchaseOrderHistory.getPurchaseOrderHistoryEntries()
                        .stream().filter(e -> e.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0)
                        .map(p -> zeroIfNull(p.getQuantityReceived()).multiply(zeroIfNull(p.getWeight()))).reduce(BigDecimal.ZERO, BigDecimal::add);
                daoPurchaseOrderHistory.insert(purchaseOrderHistory);
                if (daoPurchaseOrderHistory.getErrorMessage() == null) {
                    purchaseOrderHistory.getPurchaseOrderHistoryEntries().stream().filter(e -> e.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0).forEachOrdered((poe) -> {
                        poe.getSerialNumbers().forEach(sn -> {
                            sn.setPurchaseOrderHistoryEntry(poe);
                            daoSerialNumber.update(sn);
                        });
                        Item item = poe.getItem();
                        BigDecimal beforeQty = getQuantity(item);
                        BigDecimal qty = getQuantity(item).add(poe.getQuantityReceived());
                        item.setLastCost(item.getCost());
                        BigDecimal newCost;
                        BigDecimal cost;
                        BigDecimal poCost = poe.getCost();
                        if (totalFreight.compareTo(BigDecimal.ZERO) > 0 && totalQuantity.compareTo(BigDecimal.ZERO) > 0) {
                            if (totalWeight.compareTo(BigDecimal.ZERO) > 0) {
                                poCost = poCost.add(poe.getWeight().divide(totalWeight, 4, RoundingMode.HALF_UP).multiply(totalFreight));
                            } else {
                                poCost = poCost.add(totalFreight.divide(totalQuantity, 2, RoundingMode.HALF_UP));
                            }
                        }
                        if (totalTax.compareTo(BigDecimal.ZERO) > 0 && purchaseOrderHistory.getTotal().compareTo(BigDecimal.ZERO) > 0 && poe.getCost().compareTo(BigDecimal.ZERO) >= 0) {
                            poCost = poCost.add(totalTax.divide(purchaseOrderHistory.getTotal(), 2, RoundingMode.HALF_UP).multiply(poe.getCost()));
                        }
                        if (poCost.compareTo(BigDecimal.ZERO) > 0) {
                            cost = poCost;
                        } else {
                            cost = BigDecimal.ZERO;
                        }
                        if (Config.getStore().getDefaultItemCostMethod().equals(DBConstants.TYPE_ITEM_COST_MEHTOD_LANDED_COST)) {
                            newCost = cost;
                        } else if (Config.getStore().getDefaultItemCostMethod().equals(DBConstants.TYPE_ITEM_COST_MEHTOD_NOUPDATE)) {
                            newCost = zeroIfNull(item.getCost());
                        } else {
                            newCost = (cost.add(zeroIfNull(item.getCost()))).divide(new BigDecimal(2.0), 2, RoundingMode.HALF_UP);
                        }
                        newCost = newCost.setScale(2, RoundingMode.HALF_UP);
                        item.setCost(newCost);
                        item.setWeight(poe.getWeight());
                        daoItem.update(item);
                        ItemQuantity iq = getItemQuantity(item);
                        BigDecimal afterQty;
                        if (iq == null) {
                            List<Store> storeList = Config.getStoreList();
                            storeList.forEach(s -> {
                                ItemQuantity q = getItemQuantityByStore(item, s);
                                if (q == null) {
                                    q = new ItemQuantity();
                                    q.setItem(item);
                                    q.setReorderPoint(0);
                                    q.setRestockLevel(0);
                                    q.setStore(s);
                                    if (s.getId().equals(Config.getStore().getId())) {
                                        q.setQuantity(poe.getQuantityReceived());
                                        q.setLastReceived(new Timestamp(new Date().getTime()));
                                    } else {
                                        q.setQuantity(BigDecimal.ZERO);
                                    }
                                    daoItemQuantity.insert(q);
                                }
                            });
                            afterQty = poe.getQuantityReceived();
                        } else {
                            iq.setQuantity(qty);
                            iq.setLastReceived(new Timestamp(new Date().getTime()));
                            daoItemQuantity.update(iq);
                            afterQty = qty;
                        }
                        VendorItem vItem = poe.getItem().getVendorItems().stream()
                                .filter(i -> i.getItem().getId().equals(poe.getItem().getId()) && i.getVendor().getId().equals(purchaseOrderHistory.getVendor().getId()))
                                .findFirst().orElse(null);
                        if (vItem != null) {
                            vItem.setVendorItemLookUpCode(poe.getVendorItemLookUpCode());
                            vItem.setCost(poe.getCost());
                            vItem.setLastUpdated(new Timestamp(new Date().getTime()));
                            daoVendorItem.update(vItem);
                        }
                        ItemLog itemLog = new ItemLog();
                        itemLog.setDateCreated(new Timestamp(new Date().getTime()));
                        itemLog.setDescription(DBConstants.ITEM_LOG_DESCRIPTION_PURCHASE);
                        itemLog.setItem(item);
                        itemLog.setCost(poe.getCost());
                        itemLog.setItemCost(item.getCost());
                        itemLog.setPrice(item.getPrice1());
                        itemLog.setItemPrice(item.getPrice1());
                        itemLog.setStore(Config.getStore());
                        itemLog.setBeforeQuantity(beforeQty);
                        itemLog.setPurchaseOrderHistory(purchaseOrderHistory);
                        itemLog.setAfterQuantity(afterQty);
                        itemLog.setTransferType(DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_PURCHASEORDER);
                        itemLog.setTransactionNumber(getString(fPurchaseOrder.getPurchaseOrderNumber()));
                        daoItemLog.insert(itemLog);
                    });
                }
                if (daoPurchaseOrderHistory.getErrorMessage() == null && purchaseOrderHistory.getVendorTerm() != null && purchaseOrderHistory.getVendorTerm().getPostToAp()) {
                    AccountPayable ap = new AccountPayable();
                    BigDecimal totalAmount = purchaseOrderHistory.getTotal()
                            .add(zeroIfNull(purchaseOrderHistory.getFreightInvoicedAmount()))
                            .add(zeroIfNull(purchaseOrderHistory.getTaxOnFreightAmount()))
                            .add(zeroIfNull(purchaseOrderHistory.getTaxOnOrderAmount()))
                            .subtract(zeroIfNull(purchaseOrderHistory.getFreightPrePaidAmount()));
                    if (purchaseOrderHistory.getTotal().compareTo(BigDecimal.ZERO) >= 0) {
                        ap.setAccountPayableType(DBConstants.TYPE_APAR_INV);
                    } else {
                        ap.setAccountPayableType(DBConstants.TYPE_APAR_CRE);
                    }
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.DATE, purchaseOrderHistory.getVendorTerm().getDueDays());
                    ap.setDateDue(c.getTime());
                    ap.setDateInvoiced(purchaseOrderHistory.getDateInvoiced());
                    ap.setPostedTag(Boolean.FALSE);
                    ap.setDiscountDay((short) 0);
                    ap.setDiscountAmount(BigDecimal.ZERO);
                    ap.setStore(Config.getStore());
                    ap.setTotalAmount(totalAmount);
                    if (purchaseOrderHistory.getVendorTerm().getPaidTag()) {
                        ap.setPaidAmount(purchaseOrderHistory.getTotal());
                    } else {
                        ap.setPaidAmount(BigDecimal.ZERO);
                    }
                    ap.setPurchaseOrderHistory(purchaseOrderHistory);
                    ap.setPurchaseOrderNumber(purchaseOrderHistory.getPurchaseOrderNumber());
                    ap.setStatus(DBConstants.STATUS_OPEN);
                    ap.setVendor(purchaseOrderHistory.getVendor());
                    ap.setVendorInvoiceNumber(purchaseOrderHistory.getVendorInvoiceNumber());
                    if (purchaseOrderHistory.getTotal().compareTo(BigDecimal.ZERO) >= 0) {
                        ap.setGlDebitAmount(BigDecimal.ZERO);
                        ap.setGlCreditAmount(totalAmount);
                    } else {
                        ap.setGlDebitAmount(totalAmount.negate());
                        ap.setGlCreditAmount(BigDecimal.ZERO);
                    }
                    ap.setGlAccount(Integer.toString(0));
                    daoAccountPayable.insert(ap);
                }
            }
            showConfirmDialog("Do you want to add received item to label list?", (ActionEvent a) -> {
                fPurchaseOrder.getPurchaseOrderEntries().stream().filter((p) -> (p.getQuantityReceived().compareTo(BigDecimal.ZERO) > 0)).forEachOrdered((p) -> {
                    ItemLabel itemLabel = new ItemLabel();
                    itemLabel.setDateAdded(new Timestamp(new Date().getTime()));
                    itemLabel.setItem(p.getItem());
                    itemLabel.setStore(Config.getStore());
                    itemLabel.setQuantity(p.getQuantityReceived().intValue());
                    daoItemLabel.insert(itemLabel);
                });
            });
            Platform.runLater(() -> {
                PurchaseOrderHistoryLayout layout = new PurchaseOrderHistoryLayout(purchaseOrderHistory);
                try {
                    JasperReportBuilder report = layout.build();
                    report.show(false);
                } catch (DRException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
            if (fPurchaseOrder.getId() != null) {
                daoPurchaseOrder.delete(fPurchaseOrder);
            }
            getParent().handleAction(AppConstants.ACTION_PROCESS_FINISH);
        } else {
            showAlertDialog("Nothing to receive! ");
        }
    }
}

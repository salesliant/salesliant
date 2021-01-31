package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry_;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.entity.PurchaseOrder_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.report.PurchaseOrderHistoryLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isNumeric;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public final class PurchaseOrderHistoryUI extends BaseListUI<PurchaseOrderHistoryEntry> {

    private final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private final DataUI dataUI = new DataUI(PurchaseOrderHistoryEntry.class);
    private final DataUI purchaseOrderHistoryUI = new DataUI(PurchaseOrderHistory.class);
    private final GridPane fEditPane;
    private Label fVendorLabel = new Label();
    private PurchaseOrderHistory fPurchaseOrderHistory;
    private PurchaseOrderHistory originalPurchaseOrderHistory;
    private TextField subTotalField = new TextField();
    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderHistoryUI.class.getName());

    public PurchaseOrderHistoryUI(PurchaseOrderHistory purchaseOrderHistory) {
        this.fPurchaseOrderHistory = purchaseOrderHistory;
        this.originalPurchaseOrderHistory = purchaseOrderHistory;
        if (!fPurchaseOrderHistory.getPurchaseOrderHistoryEntries().isEmpty()) {
            List<PurchaseOrderHistoryEntry> list = fPurchaseOrderHistory.getPurchaseOrderHistoryEntries().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
        } else {
            fEntityList = FXCollections.observableArrayList();
        }
        fTableView.setItems(fEntityList);
        mainView = createMainView();
        fEditPane = createEditPane();
        try {
            purchaseOrderHistoryUI.setData(fPurchaseOrderHistory);
            updateTotal();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        fVendorLabel.setText(fPurchaseOrderHistory.getVendorName());
        dialogTitle = "Purchase Order History Entry";
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                updatePurchaseOrder();
                if (!fPurchaseOrderHistory.getPurchaseOrderHistoryEntries().isEmpty() && fPurchaseOrderHistory.getTotal() != null) {
                    PurchaseOrderHistoryLayout layout = new PurchaseOrderHistoryLayout(fPurchaseOrderHistory);
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
            case AppConstants.ACTION_SAVE:
                updatePurchaseOrder();
                if (!fPurchaseOrderHistory.getPurchaseOrderHistoryEntries().isEmpty()) {
                    daoPurchaseOrderHistory.update(fPurchaseOrderHistory);
                    fPurchaseOrderHistory.getPurchaseOrderHistoryEntries().stream().filter((p) -> (!p.getVendorItemLookUpCode().equalsIgnoreCase("NOTE:"))).forEachOrdered((p) -> {
                        originalPurchaseOrderHistory.getPurchaseOrderHistoryEntries().stream().filter((o) -> (o.getId().equals(p.getId()))).forEachOrdered((o) -> {
                            if (o.getQuantityReceived().compareTo(p.getQuantityReceived()) != 0) {
                                Item item = p.getItem();
                                BigDecimal qtyReceived = p.getQuantityReceived().subtract(o.getQuantityReceived());
                                BigDecimal qty = getQuantity(item).add(qtyReceived);
                                if (qty.compareTo(BigDecimal.ZERO) < 0) {
                                    qty = BigDecimal.ZERO;
                                }
                                item.setCost(p.getCost());
                                BigDecimal beforeQty = getQuantity(item);
                                Item aItem = daoItem.update(item);
                                ItemQuantity iq = getItemQuantity(aItem);
                                iq.setQuantity(qty);
                                daoItemQuantity.update(iq);
                                ItemLog itemLog = new ItemLog();
                                itemLog.setEmployee(Config.getEmployee());
                                itemLog.setCost(o.getCost());
                                itemLog.setItemCost(o.getCost());
                                itemLog.setPrice(o.getItem().getPrice1());
                                itemLog.setItemPrice(o.getItem().getPrice1());
                                itemLog.setDateCreated(new Timestamp(new Date().getTime()));
                                itemLog.setDescription(DBConstants.ITEM_LOG_DESCRIPTION_PURCHASE);
                                itemLog.setItem(item);
                                itemLog.setStore(Config.getStore());
                                itemLog.setPurchaseOrderHistory(fPurchaseOrderHistory);
                                itemLog.setBeforeQuantity(beforeQty);
                                itemLog.setAfterQuantity(qty);
                                itemLog.setTransferType(DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_PURCHASEORDER);
                                itemLog.setTransactionNumber(fPurchaseOrderHistory.getPurchaseOrderNumber());
                                daoItemLog.insert(itemLog);
                            }
                        });
                    });
                    getParent().handleAction(AppConstants.ACTION_SAVE);
                } else {
                    showAlertDialog("Can't save a empty purchase order! ");
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (!fEntity.getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                        try {
                            dataUI.setData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        fInputDialog = createSaveCancelUIDialog(fEditPane, "Purchase Order History Entry");
                        saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                            try {
                                dataUI.getData(fEntity);
                                fTableView.refresh();
                                updateTotal();
                            } catch (Exception ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        });
                        fInputDialog.showDialog();
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);

        TableColumn<PurchaseOrderHistoryEntry, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.vendorItemLookUpCode.getName()));

        TableColumn<PurchaseOrderHistoryEntry, String> yourSKUCol = new TableColumn<>("Your SKU");
        yourSKUCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.itemLookUpCode.getName()));
        yourSKUCol.setCellFactory(stringCell(Pos.TOP_CENTER));

        TableColumn<PurchaseOrderHistoryEntry, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
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

        TableColumn<PurchaseOrderHistoryEntry, String> qtyOrderedCol = new TableColumn<>("Qty Ordered");
        qtyOrderedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getQuantityOrdered()));
            }
        });
        qtyOrderedCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        TableColumn<PurchaseOrderHistoryEntry, String> qtyReceivedCol = new TableColumn<>("Qty Received");
        qtyReceivedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getQuantityReceived()));
            }
        });
        qtyReceivedCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        TableColumn<PurchaseOrderHistoryEntry, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            }
        });
        costCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        TableColumn<PurchaseOrderHistoryEntry, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                BigDecimal total = zeroIfNull(p.getValue().getCost()).multiply(zeroIfNull(p.getValue().getQuantityReceived()));
                return new SimpleStringProperty(getString(total));
            }
        });
        totalCol.setCellFactory(stringCell(Pos.TOP_CENTER));

        fTableView.getColumns().add(vendorSKUCol);
        fTableView.getColumns().add(yourSKUCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyOrderedCol);
        fTableView.getColumns().add(qtyReceivedCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(totalCol);

        vendorSKUCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.125));
        yourSKUCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.125));
        descriptionCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.325));
        qtyOrderedCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.10));
        qtyReceivedCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.10));
        costCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.11));
        totalCol.prefWidthProperty().bind(fTableView.widthProperty().multiply(0.11));

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(400);
        setTableWidth(fTableView);

        GridPane.setHgrow(fTableView, Priority.ALWAYS);

        BorderPane bp = new BorderPane();
        bp.setCenter(fTableView);
        bp.setBottom(createButtonPane());
        bp.setTop(createSettingPane());
        bp.setStyle("-fx-border-color: gray");

        GridPane.setHalignment(fVendorLabel, HPos.LEFT);
        mainPane.add(fVendorLabel, 0, 0);
        mainPane.add(bp, 0, 1);
        mainPane.add(createBottomPane(), 0, 2);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Qty Ordered:", dataUI.createTextField(PurchaseOrderHistoryEntry_.quantityOrdered), fListener, 250.0, 0);
        add(editPane, "Qty Received:", dataUI.createTextField(PurchaseOrderHistoryEntry_.quantityReceived), fListener, 250.0, 1);
        add(editPane, "Cost:", dataUI.createTextField(PurchaseOrderHistoryEntry_.cost), fListener, 250.0, 2);
        editPane.add(lblWarning, 0, 3, 2, 1);
        return editPane;
    }

    private GridPane createSettingPane() {
        GridPane settingPane = new GridPane();
        settingPane.setHgap(4.0);
        Label poNumberLabel = new Label("PO Number:");
        settingPane.add(poNumberLabel, 2, 1);
        settingPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrderHistory_.purchaseOrderNumber, 92), 3, 1);
        Label invoiceNumberLabel = new Label("Invoice Number:");
        settingPane.add(invoiceNumberLabel, 4, 1);
        settingPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrderHistory_.vendorInvoiceNumber, 92), 5, 1);
        Label termLabel = new Label("Term:");
        settingPane.add(termLabel, 6, 1);
        settingPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrderHistory_.vendorTermCode, 92), 7, 1);

        settingPane.setPadding(new Insets(1));

        GridPane.setHalignment(poNumberLabel, HPos.RIGHT);
        GridPane.setHalignment(invoiceNumberLabel, HPos.RIGHT);
        GridPane.setHalignment(termLabel, HPos.RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrderHistory_.vendorTermCode).setEditable(false);

        return settingPane;
    }

    private Node createBottomPane() {
        GridPane totalPane = new GridPane();
        Label subTotalLabel = new Label(" Sub Total: ");
        subTotalField.setEditable(false);
        subTotalField.setPrefWidth(90);
        totalPane.add(subTotalLabel, 0, 0);
        totalPane.add(subTotalField, 1, 0);
        Label taxOnOrderLabel = new Label("Tax On Order: ");
        totalPane.add(taxOnOrderLabel, 0, 1);
        totalPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrder_.taxOnOrderAmount, 90), 1, 1);
        Label taxOnFreightLabel = new Label("Tax On Freight: ");
        totalPane.add(taxOnFreightLabel, 0, 2);
        totalPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrder_.taxOnFreightAmount, 90), 1, 2);
        Label freightInvoicedLabel = new Label("Freight Invoiced: ");
        totalPane.add(freightInvoicedLabel, 0, 4);
        totalPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrder_.freightInvoicedAmount, 90), 1, 4);
        Label freightPrePaidLabel = new Label("Freight PrePaid: ");
        totalPane.add(freightPrePaidLabel, 0, 5);
        totalPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrder_.freightPrePaidAmount, 90), 1, 5);
        Label totalLabel = new Label("Total: ");
        totalPane.add(totalLabel, 0, 6);
        totalPane.add(purchaseOrderHistoryUI.createTextField(PurchaseOrder_.total, 90), 1, 6);

        GridPane.setHalignment(subTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(subTotalField, HPos.RIGHT);
        GridPane.setHalignment(taxOnOrderLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnOrderAmount), HPos.RIGHT);
        GridPane.setHalignment(taxOnFreightLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnFreightAmount), HPos.RIGHT);
        GridPane.setHalignment(freightInvoicedLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightInvoicedAmount), HPos.RIGHT);
        GridPane.setHalignment(freightPrePaidLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightPrePaidAmount), HPos.RIGHT);
        GridPane.setHalignment(totalLabel, HPos.RIGHT);
        GridPane.setHalignment(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.total), HPos.RIGHT);
        totalPane.setAlignment(Pos.TOP_RIGHT);

        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnOrderAmount).textProperty().addListener(fieldValueListener);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnFreightAmount).textProperty().addListener(fieldValueListener);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightPrePaidAmount).textProperty().addListener(fieldValueListener);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightInvoicedAmount).textProperty().addListener(fieldValueListener);

        subTotalField.setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnOrderAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnFreightAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightInvoicedAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightPrePaidAmount).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.total).setAlignment(Pos.CENTER_RIGHT);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.total).setEditable(false);

        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);

        HBox bottomPane = new HBox();
        bottomPane.getChildren().addAll(filler, totalPane);

        return bottomPane;
    }

    protected HBox createButtonPane() {
        HBox buttonGroup = new HBox();
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, "Edit", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print", fHandler);
        buttonGroup.getChildren().addAll(printButton, editButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(1));

        return buttonGroup;
    }

    @Override
    protected void updateTotal() {
        BigDecimal feeTotal = BigDecimal.ZERO;
        BigDecimal total;
        BigDecimal subTotal = fTableView.getItems().stream()
                .map(p -> zeroIfNull(p.getCost()).multiply(zeroIfNull(p.getQuantityReceived()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        subTotalField.setText(getString(subTotal));
        if (isNumeric(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnOrderAmount).getText())) {
            feeTotal = feeTotal.add(new BigDecimal(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnOrderAmount).getText()));
        }
        if (isNumeric(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnFreightAmount).getText())) {
            feeTotal = feeTotal.add(new BigDecimal(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.taxOnFreightAmount).getText()));
        }
        if (isNumeric(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightInvoicedAmount).getText())) {
            feeTotal = feeTotal.add(new BigDecimal(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightInvoicedAmount).getText()));
        }
        if (isNumeric(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightPrePaidAmount).getText())) {
            feeTotal = feeTotal.add(new BigDecimal(purchaseOrderHistoryUI.getTextField(PurchaseOrder_.freightPrePaidAmount).getText()));
        }
        total = subTotal.add(feeTotal);
        purchaseOrderHistoryUI.getTextField(PurchaseOrder_.total).setText(getString(total));
    }

    public void updatePurchaseOrder() {
        try {
            purchaseOrderHistoryUI.getData(fPurchaseOrderHistory);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}

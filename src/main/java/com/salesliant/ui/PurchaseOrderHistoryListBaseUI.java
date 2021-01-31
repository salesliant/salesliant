package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry_;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.report.PurchaseOrderHistoryLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class PurchaseOrderHistoryListBaseUI extends BaseListUI<PurchaseOrderHistory> {

    protected final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    protected final BaseDao<PurchaseOrder> daoPurchaseOrder = new BaseDao<>(PurchaseOrder.class);
    protected TableView<PurchaseOrderHistoryEntry> fDetailTableView = new TableView<>();
    protected ObservableList<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryList;
    protected LocalDateTime fFrom;
    protected LocalDateTime fTo;
    protected HBox topBox = new HBox();
    protected final DatePicker fFromDatePicker = new DatePicker();
    protected final DatePicker fToDatePicker = new DatePicker();
    protected Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderHistoryListBaseUI.class.getName());

    public PurchaseOrderHistoryListBaseUI() {
    }

    protected final void createGUI() {
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends PurchaseOrderHistory> observable, PurchaseOrderHistory newValue, PurchaseOrderHistory oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<PurchaseOrderHistoryEntry> aList = fEntity.getPurchaseOrderHistoryEntries().stream()
                        .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                        .collect(Collectors.toList());
                fPurchaseOrderHistoryEntryList = FXCollections.observableList(aList);
                fDetailTableView.setItems(fPurchaseOrderHistoryEntryList);
            } else {
                fDetailTableView.getItems().clear();
            }
        });
        mainView = createMainView();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT:
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_CLONE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Do you want to create a new PO base on this?", (ActionEvent e) -> {
                        PurchaseOrder aPurchaseOrder = dupliactePurchaseOrder(fEntity);
                        daoPurchaseOrder.insert(aPurchaseOrder);
                        if (daoPurchaseOrderHistory.getErrorMessage() != null) {
                            showAlertDialog("Cannot copy the selected Purchase Order");
                        }
                    });
                }
                break;
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    PurchaseOrderHistoryLayout layout = new PurchaseOrderHistoryLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_EXPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    PurchaseOrderHistoryLayout layout = new PurchaseOrderHistoryLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }

    protected void loadData() {
    }

    protected GridPane createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        topBox.setStyle("-fx-background-color: transparent;-fx-border-width: 0px;");

        TableColumn<PurchaseOrderHistory, String> vendorCol = new TableColumn<>("Vendor");
        vendorCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistory_.vendorName.getName()));
        vendorCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorCol.setPrefWidth(150);

        TableColumn<PurchaseOrderHistory, String> vendorInvoiceCol = new TableColumn<>("Vendor Invoice");
        vendorInvoiceCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistory_.vendorInvoiceNumber.getName()));
        vendorInvoiceCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorInvoiceCol.setPrefWidth(90);

        TableColumn<PurchaseOrderHistory, String> poNumberCol = new TableColumn<>("P.O.#");
        poNumberCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistory_.purchaseOrderNumber.getName()));
        poNumberCol.setCellFactory(stringCell(Pos.CENTER));
        poNumberCol.setPrefWidth(120);

        TableColumn<PurchaseOrderHistory, String> dateReceivedCol = new TableColumn<>("Date Received");
        dateReceivedCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistory_.dateReceived.getName()));
        dateReceivedCol.setCellFactory(stringCell(Pos.CENTER));
        dateReceivedCol.setPrefWidth(90);

        TableColumn<PurchaseOrderHistory, String> employeePurchasedCol = new TableColumn<>("Place By");
        employeePurchasedCol.setCellValueFactory((new PropertyValueFactory<>(PurchaseOrderHistory_.employeePurchasedName.getName())));
        employeePurchasedCol.setCellFactory(stringCell(Pos.CENTER));
        employeePurchasedCol.setPrefWidth(90);

        TableColumn<PurchaseOrderHistory, String> employeeReceivedCol = new TableColumn<>("Received By");
        employeeReceivedCol.setCellValueFactory((new PropertyValueFactory<>(PurchaseOrderHistory_.employeeReceivedName.getName())));
        employeeReceivedCol.setCellFactory(stringCell(Pos.CENTER));
        employeeReceivedCol.setPrefWidth(90);

        TableColumn<PurchaseOrderHistory, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory((new PropertyValueFactory<>(PurchaseOrderHistory_.vendorTermCode.getName())));
        termCol.setCellFactory(stringCell(Pos.CENTER));
        termCol.setPrefWidth(70);

        TableColumn<PurchaseOrderHistory, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistory_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER));
        totalCol.setPrefWidth(90);

        TableColumn<PurchaseOrderHistory, String> taxCol = new TableColumn<>("Tax");
        taxCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistory, String> p) -> {
            if (p.getValue() != null) {
                BigDecimal tax = p.getValue().getTaxOnOrderAmount().add(p.getValue().getTaxOnFreightAmount());
                return new SimpleStringProperty(getString(tax));
            } else {
                return null;
            }
        });
        taxCol.setCellFactory(stringCell(Pos.CENTER));
        taxCol.setPrefWidth(90);

        TableColumn<PurchaseOrderHistory, String> freightCol = new TableColumn<>("Freight");
        freightCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistory, String> p) -> {
            if (p.getValue() != null) {
                BigDecimal freight = p.getValue().getFreightInvoicedAmount().add(p.getValue().getFreightPrePaidAmount());
                return new SimpleStringProperty(getString(freight));
            } else {
                return null;
            }
        });
        freightCol.setCellFactory(stringCell(Pos.CENTER));
        freightCol.setPrefWidth(100);

        fTableView.getColumns().add(vendorCol);
        fTableView.getColumns().add(vendorInvoiceCol);
        fTableView.getColumns().add(poNumberCol);
        fTableView.getColumns().add(dateReceivedCol);
        fTableView.getColumns().add(employeePurchasedCol);
        fTableView.getColumns().add(employeeReceivedCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(taxCol);
        fTableView.getColumns().add(freightCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        TableColumn<PurchaseOrderHistoryEntry, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(p.getValue().getVendorItemLookUpCode());
            }
        });
        vendorSKUCol.setMinWidth(150);
        vendorSKUCol.setCellFactory(stringCell(Pos.TOP_CENTER));

        TableColumn<PurchaseOrderHistoryEntry, String> skuCol = new TableColumn<>("Your SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(150);
        skuCol.setCellFactory(stringCell(Pos.TOP_CENTER));

        TableColumn<PurchaseOrderHistoryEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() != null && p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return new SimpleStringProperty(p.getValue().getLineNote());
            } else {
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
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(300);

        TableColumn<PurchaseOrderHistoryEntry, String> detailQtyOrderedCol = new TableColumn<>("Qty Ordered");
        detailQtyOrderedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                if (p.getValue().getQuantityOrdered() != null) {
                    return new SimpleStringProperty(getString(p.getValue().getQuantityOrdered()));
                } else {
                    return null;
                }
            }
        });
        detailQtyOrderedCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        detailQtyOrderedCol.setMinWidth(90);

        TableColumn<PurchaseOrderHistoryEntry, String> detailQtyReceivedCol = new TableColumn<>("Qty Received");
        detailQtyReceivedCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                if (p.getValue().getQuantityReceived() != null) {
                    return new SimpleStringProperty(getString(p.getValue().getQuantityReceived()));
                } else {
                    return null;
                }
            }
        });
        detailQtyReceivedCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        detailQtyReceivedCol.setMinWidth(90);

        TableColumn<PurchaseOrderHistoryEntry, String> detailCostCol = new TableColumn<>("Cost");
        detailCostCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            }
        });
        detailCostCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        detailCostCol.setMinWidth(100);

        TableColumn<PurchaseOrderHistoryEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                if (p.getValue() != null && p.getValue().getCost() != null && p.getValue().getQuantityReceived() != null) {
                    BigDecimal total = p.getValue().getCost().multiply(p.getValue().getQuantityReceived());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.TOP_CENTER));

        fDetailTableView.getColumns().add(vendorSKUCol);
        fDetailTableView.getColumns().add(skuCol);
        fDetailTableView.getColumns().add(detailDescriptionCol);
        fDetailTableView.getColumns().add(detailQtyOrderedCol);
        fDetailTableView.getColumns().add(detailQtyReceivedCol);
        fDetailTableView.getColumns().add(detailCostCol);
        fDetailTableView.getColumns().add(detailTotalCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(200);
        setTableWidth(fDetailTableView);

        mainPane.add(topBox, 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(fDetailTableView, 0, 3);
        mainPane.add(createPrintExportDuplicateCloseButtonPane(), 0, 4);
        return mainPane;
    }

    protected HBox createPrintExportDuplicateCloseButtonPane() {
        HBox leftButtonBox = new HBox();
        leftButtonBox.getChildren().addAll(dateRangerButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button duplicateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, "Duplicate", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, fHandler);
        Button refreshButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH, AppConstants.ACTION_REFRESH, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        rightButtonBox.getChildren().addAll(printButton, exportButton, duplicateButton, refreshButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        dateRangerButton.setVisible(false);
        return buttonGroup;
    }

    protected PurchaseOrder dupliactePurchaseOrder(PurchaseOrderHistory oldPO) {
        PurchaseOrder newPO = new PurchaseOrder();
        newPO.setPurchaseOrderNumber(Config.getNumber(DBConstants.SEQ_PURCHASE_ORDER_NUMBER));
        newPO.setNote(oldPO.getNote());
        newPO.setVendor(oldPO.getVendor());
        newPO.setVendorContact(oldPO.getVendor().getDefaultVendorContact());
        newPO.setShipToAddress(oldPO.getShipToAddress());
        newPO.setVendorShippingService(oldPO.getVendorShippingService());
        newPO.setVendorTerm(oldPO.getVendorTerm());
        newPO.setStore(oldPO.getStore());
        newPO.setStatus(DBConstants.STATUS_OPEN);
        newPO.setPurchaseOrderType(DBConstants.TYPE_PO_PO);
        newPO.setEmployeePurchasedName(oldPO.getEmployeePurchasedName());
        newPO.setPostedTag(Boolean.FALSE);
        newPO.setFreightPrePaidAmount(oldPO.getFreightPrePaidAmount());
        newPO.setFreightInvoicedAmount(oldPO.getFreightInvoicedAmount());
        newPO.setTaxOnFreightAmount(oldPO.getTaxOnFreightAmount());
        newPO.setTaxOnOrderAmount(oldPO.getTaxOnOrderAmount());
        newPO.setTotal(oldPO.getTotal());
        newPO.setDatePurchased(new Timestamp(new Date().getTime()));
        if (oldPO.getVendorShippingService() != null && oldPO.getVendorShippingService().getDeliveryDay() != null) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            Calendar cc = addBusinessDay(cal, oldPO.getVendorShippingService().getDeliveryDay());
            date = cc.getTime();
            newPO.setDateExpected(date);
        }
        List<PurchaseOrderEntry> list = new ArrayList<>();
        oldPO.getPurchaseOrderHistoryEntries().forEach(e -> {
            PurchaseOrderEntry aPurchaseOrderEntry = new PurchaseOrderEntry();
            aPurchaseOrderEntry.setQuantityOrdered(zeroIfNull(e.getQuantityReceived()));
            aPurchaseOrderEntry.setQuantityReceived(BigDecimal.ZERO);
            aPurchaseOrderEntry.setCost(e.getCost());
            aPurchaseOrderEntry.setDisplayOrder(e.getDisplayOrder());
            aPurchaseOrderEntry.setItem(e.getItem());
            aPurchaseOrderEntry.setLineNote(e.getLineNote());
            aPurchaseOrderEntry.setPurchaseOrder(newPO);
            aPurchaseOrderEntry.setItemLookUpCode(e.getItemLookUpCode());
            aPurchaseOrderEntry.setItemDescription(e.getItemDescription());
            aPurchaseOrderEntry.setVendorItemLookUpCode(e.getVendorItemLookUpCode());
            aPurchaseOrderEntry.setUnitOfMeasure(e.getUnitOfMeasure());
            aPurchaseOrderEntry.setProcessedTag(Boolean.FALSE);
            aPurchaseOrderEntry.setWeight(e.getWeight());
            aPurchaseOrderEntry.setTaxRate(e.getTaxRate());
            aPurchaseOrderEntry.setQuantityPerUom(e.getQuantityPerUom());
            list.add(aPurchaseOrderEntry);
        });
        if (list.size() >= 1) {
            newPO.setPurchaseOrderEntries(list);
        }
        return newPO;
    }
}

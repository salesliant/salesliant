package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.PurchaseOrderEntry_;
import com.salesliant.entity.PurchaseOrder_;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Vendor;
import com.salesliant.report.PurchaseOrderLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseListUI.getStoreShipToAddress;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.widget.VendorTableWidget;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class PurchaseOrderListUI extends BaseListUI<PurchaseOrder> {

    private final BaseDao<PurchaseOrder> daoPurchaseOrder = new BaseDao<>(PurchaseOrder.class);
    private TableView<PurchaseOrderEntry> fDetailTableView = new TableView<>();
    private ObservableList<PurchaseOrderEntry> fPurchaseOrderEntryList;
    private Vendor fVendor;
    private final Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
    private final static String PURCHASE_ORDER_TITLE = "Purchase Order";
    private final RadioButton poToPlaceBtn = new RadioButton("Prepared PO  ");
    private final RadioButton poToReceiveBtn = new RadioButton("Processed PO  ");
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderListUI.class.getName());

    public PurchaseOrderListUI() {
        mainView = createMainView();
        loadData();
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends PurchaseOrder> observable, PurchaseOrder newValue, PurchaseOrder oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        fVendor = fEntity.getVendor();
                        List<PurchaseOrderEntry> aList = fEntity.getPurchaseOrderEntries()
                                .stream()
                                .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                                .collect(Collectors.toList());
                        fPurchaseOrderEntryList = FXCollections.observableList(aList);
                        fDetailTableView.setItems(fPurchaseOrderEntryList);
                    } else {
                        fDetailTableView.getItems().clear();
                        fVendor = null;
                    }
                });
    }

    private void loadData() {
        if (toggleGroup.getSelectedToggle().equals(poToPlaceBtn)) {
            newButton.setVisible(true);
            List<PurchaseOrder> list = daoPurchaseOrder.read(PurchaseOrder_.store, Config.getStore(), PurchaseOrder_.status, DBConstants.STATUS_OPEN);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
        } else {
            newButton.setVisible(false);
            List<PurchaseOrder> list = daoPurchaseOrder.read(PurchaseOrder_.store, Config.getStore(), PurchaseOrder_.status, DBConstants.STATUS_IN_PROGRESS);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
        }
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (Config.checkTransactionRequireLogin()) {
                    LoginUI.login();
                }
                VendorTableWidget vendorListUI = new VendorTableWidget();
                fInputDialog = createSelectCancelUIDialog(vendorListUI.getView(), "Vendor");
                selectBtn.setDisable(true);
                ((TableView<Vendor>) vendorListUI.getTableView()).getSelectionModel().selectedItemProperty().addListener(
                        (ObservableValue<? extends Vendor> observable, Vendor newValue, Vendor oldValue) -> {
                            if (observable != null && observable.getValue() != null) {
                                selectBtn.setDisable(false);
                            } else {
                                selectBtn.setDisable(true);
                            }
                        });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (vendorListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fVendor = (Vendor) vendorListUI.getTableView().getSelectionModel().getSelectedItem();
                        fEntity = new PurchaseOrder();
                        fEntity.setVendor(fVendor);
                        fEntity.setVendorTerm(fVendor.getVendorTerm());
                        fEntity.setVendorName(fVendor.getCompany());
                        if (fVendor.getVendorTerm() != null) {
                            fEntity.setVendorTerm(fVendor.getVendorTerm());
                            fEntity.setDiscountDays(fVendor.getVendorTerm().getDiscountDays());
                            fEntity.setDiscountPercent(fVendor.getVendorTerm().getDiscountRate());
                        }
                        fEntity.setStore(Config.getStore());
                        fEntity.setStatus(DBConstants.STATUS_OPEN);
                        fEntity.setPurchaseOrderType(DBConstants.TYPE_PO_PO);
                        fEntity.setPurchaseOrderNumber(Config.getNumber(DBConstants.SEQ_PURCHASE_ORDER_NUMBER));
                        fEntity.setShipToAddress(getStoreShipToAddress());
                        fEntity.setDateCreated(new Timestamp(new Date().getTime()));
                        fEntity.setDueTag(Boolean.FALSE);
                        fEntity.setPostedTag(Boolean.FALSE);
                        fEntity.setEmployeePurchasedName(Config.getEmployee().getNameOnSalesOrder());
                        fEntity.setFreightInvoicedAmount(BigDecimal.ZERO);
                        fEntity.setFreightPrePaidAmount(BigDecimal.ZERO);
                        fEntity.setTaxOnFreightAmount(BigDecimal.ZERO);
                        fEntity.setTaxOnOrderAmount(BigDecimal.ZERO);
                        fEntity.setVendorContact(fVendor.getDefaultVendorContact());
                        fEntity.setVendorShippingService(fVendor.getDefaultVendorShippingService());
                        PurchaseOrderUI newUI = new PurchaseOrderUI(fEntity);
                        newUI.setParent(this);
                        fInputDialog.close();
                        fInputDialog = createUIDialog(newUI.getView(), PURCHASE_ORDER_TITLE + "---" + fVendor.getCompany() + ", Purchase Order Number:" + fEntity.getPurchaseOrderNumber()
                                + ", Purchaser:" + fEntity.getEmployeePurchasedName());
                        fInputDialog.showDialog();
                    }
                });
                fInputDialog.show();
                break;
            case AppConstants.ACTION_PROCESS:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (fEntity.getStatus().equals(DBConstants.STATUS_IN_PROGRESS) && Config.checkTransactionRequireLogin()) {
                        LoginUI.login();
                    }
                    PurchaseOrderUI editUI = new PurchaseOrderUI(fEntity);
                    fVendor = fEntity.getVendor();
                    editUI.setParent(this);
                    fInputDialog = createUIDialog(editUI.getView(), PURCHASE_ORDER_TITLE + "---" + fVendor.getCompany() + ", Purchase Order Number:" + fEntity.getPurchaseOrderNumber()
                            + ", Purchaser:" + fEntity.getEmployeePurchasedName());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    PurchaseOrderLayout layout = new PurchaseOrderLayout(fEntity);
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
                    PurchaseOrderLayout layout = new PurchaseOrderLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_SAVE:
                fInputDialog.close();
                Optional<PurchaseOrder> p = fTableView.getItems().stream().filter(e -> e.getId().equals(fEntity.getId())).findFirst();
                if (p.isPresent()) {
                    fTableView.refresh();
                    fDetailTableView.refresh();
                } else {
                    fTableView.getItems().add(fEntity);
                    fTableView.getSelectionModel().select(fEntity);
                }
                break;
            case AppConstants.ACTION_VOID:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to void this purchase order?", (ActionEvent e) -> {
                        if (fEntity.getId() != null) {
                            daoPurchaseOrder.delete(fEntity);
                        }
                        fEntityList.remove(fEntity);
                        fTableView.refresh();
                        fDetailTableView.refresh();
                    });
                }
                break;
            case AppConstants.ACTION_PROCESS_FINISH:
                if (fInputDialog != null) {
                    fInputDialog.close();
                }
                loadData();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        poToPlaceBtn.setSelected(true);
        poToReceiveBtn.setSelected(false);
        poToPlaceBtn.setToggleGroup(toggleGroup);
        poToReceiveBtn.setToggleGroup(toggleGroup);
        poToPlaceBtn.setOnAction((ActionEvent e) -> {
            loadData();
        });
        poToReceiveBtn.setOnAction((ActionEvent e) -> {
            loadData();
        });

        mainPane.add(poToPlaceBtn, 0, 0);
        mainPane.add(poToReceiveBtn, 1, 0);
        GridPane.setHalignment(poToPlaceBtn, HPos.LEFT);
        GridPane.setHalignment(poToReceiveBtn, HPos.LEFT);

        TableColumn<PurchaseOrder, String> poNumberCol = new TableColumn<>("P.O.#");
        poNumberCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrder_.purchaseOrderNumber.getName()));
        poNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        poNumberCol.setPrefWidth(120);

        TableColumn<PurchaseOrder, String> vendorCol = new TableColumn<>("Vendor");
        vendorCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrder, String> p) -> {
            if (p.getValue().getVendor() != null) {
                return new SimpleStringProperty(p.getValue().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        vendorCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorCol.setPrefWidth(180);

        TableColumn<PurchaseOrder, String> dateCreatedCol = new TableColumn<>("Date Ordered");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrder_.datePurchased.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCreatedCol.setPrefWidth(170);

        TableColumn<PurchaseOrder, String> dateexpectedCol = new TableColumn<>("Date Expected");
        dateexpectedCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrder_.dateExpected.getName()));
        dateexpectedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateexpectedCol.setPrefWidth(90);

        TableColumn<PurchaseOrder, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrder_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(100);

        TableColumn<PurchaseOrder, String> employeeCol = new TableColumn<>("Purchaser");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrder_.employeePurchasedName.getName()));
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(130);

        fTableView.getColumns().add(vendorCol);
        fTableView.getColumns().add(poNumberCol);
        fTableView.getColumns().add(employeeCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(dateexpectedCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(250);
        setTableWidth(fTableView);

        TableColumn<PurchaseOrderEntry, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(p.getValue().getVendorItemLookUpCode());
            }
        });
        vendorSKUCol.setMinWidth(100);
        vendorSKUCol.setCellFactory(stringCell(Pos.TOP_LEFT));

        TableColumn<PurchaseOrderEntry, String> skuCol = new TableColumn<>("Your SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.TOP_LEFT));

        TableColumn<PurchaseOrderEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
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
        detailDescriptionCol.setMinWidth(330);

        TableColumn<PurchaseOrderEntry, String> detailQtyCol = new TableColumn<>("Qty Ordered");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
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
        detailQtyCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailQtyCol.setMinWidth(70);

        TableColumn<PurchaseOrderEntry, String> detailCostCol = new TableColumn<>("Cost");
        detailCostCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            }
        });
        detailCostCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailCostCol.setMinWidth(90);

        TableColumn<PurchaseOrderEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderEntry, String> p) -> {
            if (p.getValue().getVendorItemLookUpCode() == null || p.getValue().getVendorItemLookUpCode().equalsIgnoreCase("NOTE:")) {
                return null;
            } else {
                if (p.getValue() != null && p.getValue().getCost() != null && p.getValue().getQuantityOrdered() != null) {
                    BigDecimal total = p.getValue().getCost().multiply(p.getValue().getQuantityOrdered());
                    return new SimpleStringProperty(getString(total));
                } else {
                    return new SimpleStringProperty("");
                }
            }
        });
        detailTotalCol.setMinWidth(90);
        detailTotalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        fDetailTableView.getColumns().add(vendorSKUCol);
        fDetailTableView.getColumns().add(skuCol);
        fDetailTableView.getColumns().add(detailDescriptionCol);
        fDetailTableView.getColumns().add(detailQtyCol);
        fDetailTableView.getColumns().add(detailCostCol);
        fDetailTableView.getColumns().add(detailTotalCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(200);
        setTableWidth(fDetailTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fDetailTableView, 0, 3, 2, 1);
        mainPane.add(createButtonPane(), 0, 4, 2, 1);

        return mainPane;
    }

    protected HBox createButtonPane() {
        Button processButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, fHandler);
        Button voidButton = ButtonFactory.getButton(ButtonFactory.BUTTON_VOID, AppConstants.ACTION_VOID, fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, fHandler);
        Button refreshButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH, AppConstants.ACTION_REFRESH, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(processButton);
        buttonGroup.getChildren().add(voidButton);
        buttonGroup.getChildren().add(printButton);
        buttonGroup.getChildren().add(exportButton);
        buttonGroup.getChildren().add(refreshButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private PurchaseOrder dupliactePurchaseOrder(PurchaseOrder oldPO) {
        PurchaseOrder newPO = new PurchaseOrder();
        newPO.setDatePurchased(oldPO.getDatePurchased());
//        newPO.setCurrency(oldPO.getCurrency());
//        newPO.setEmployee(oldPO.getEmployee());
        newPO.setNote(oldPO.getNote());
        newPO.setPurchaseOrderNumber(oldPO.getPurchaseOrderNumber());
        newPO.setPurchaseOrderType(oldPO.getPurchaseOrderType());
//        newPO.setShipToAddress1(oldPO.getShipToAddress1());
//        newPO.setShipToAddress2(oldPO.getShipToAddress2());
//        newPO.setShipToCity(oldPO.getShipToCity());
//        newPO.setShipToCompany(oldPO.getShipToCompany());
//        newPO.setShipToCountry(oldPO.getShipToCountry());
//        newPO.setShipToDepartment(oldPO.getShipToDepartment());
//        newPO.setShipToFirstName(oldPO.getShipToFirstName());
//        newPO.setShipToLastName(oldPO.getShipToLastName());
//        newPO.setShipToPhoneNumber(oldPO.getShipToPhoneNumber());
//        newPO.setShipToPostCode(oldPO.getShipToPostCode());
//        newPO.setShipToState(oldPO.getShipToState());
        newPO.setVendor(oldPO.getVendor());
        newPO.setVendorShippingService(oldPO.getVendorShippingService());
        newPO.setVendorTerm(fEntity.getVendorTerm());

        newPO.setStatus(DBConstants.STATUS_OPEN);

        int disporder = 0;
        List<PurchaseOrderEntry> list = new ArrayList<>();
        for (PurchaseOrderEntry poe : oldPO.getPurchaseOrderEntries()) {
            if (poe.getQuantityOrdered().compareTo(poe.getQuantityReceived()) > 0) {
                PurchaseOrderEntry aPurchaseOrderEntry = new PurchaseOrderEntry();
                aPurchaseOrderEntry.setQuantityOrdered(zeroIfNull(poe.getQuantityOrdered()).subtract(zeroIfNull(poe.getQuantityReceived())));
                aPurchaseOrderEntry.setQuantityReceived(BigDecimal.ZERO);
                aPurchaseOrderEntry.setDisplayOrder(disporder);
                aPurchaseOrderEntry.setItem(poe.getItem());
                aPurchaseOrderEntry.setLineNote(poe.getLineNote());
                aPurchaseOrderEntry.setPurchaseOrder(newPO);
                list.add(aPurchaseOrderEntry);
                disporder++;
            }
        }
        if (list.size() >= 1) {
            newPO.setPurchaseOrderEntries(list);
        }
        return newPO;
    }
}

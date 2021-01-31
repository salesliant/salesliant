/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorShippingService;
import com.salesliant.entity.VendorShippingService_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.ShippingCarrierWidget;
import com.salesliant.widget.VendorWidget;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lewis
 */
public class VendorShippingServiceListUI extends BaseListUI<VendorShippingService> {

    private final BaseDao<VendorShippingService> daoVendorShippingService = new BaseDao<>(VendorShippingService.class);
    private final BaseDao<Vendor> daoVendor = new BaseDao<>(Vendor.class);
    private final DataUI dataUI = new DataUI(VendorShippingService.class);
    private final GridPane fEditPane;
    private final ShippingCarrierWidget fShippingCarrierCombo = new ShippingCarrierWidget();
    private final CheckBox fDefaultCheckBox = new CheckBox();
    private VendorWidget fVendorCombo = new VendorWidget();
    private Vendor fVendor;
    private static final Logger LOGGER = Logger.getLogger(VendorShippingServiceListUI.class.getName());

    public VendorShippingServiceListUI() {
        mainView = createMainView();
        fVendorCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Vendor> observable, Vendor newValue, Vendor oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fVendor = fVendorCombo.getSelectionModel().getSelectedItem();
                List<VendorShippingService> list = fVendor.getVendorShippingServices()
                        .stream()
                        .sorted((e1, e2) -> e1.getDescription().compareTo(e2.getDescription()))
                        .collect(Collectors.toList());
                if (!list.isEmpty()) {
                    fEntityList = FXCollections.observableList(list);
                } else {
                    fEntityList = FXCollections.observableArrayList();
                }
            } else {
                fEntityList = FXCollections.observableArrayList();
            }
            fTableView.setItems(fEntityList);
        });
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (fVendor != null) {
                    fEntity = new VendorShippingService();
                    fEntity.setVendor(fVendor);
                    fEntity.setStore(Config.getStore());
                    fShippingCarrierCombo.reset();
                    fDefaultCheckBox.setDisable(false);
                    fDefaultCheckBox.setSelected(false);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Shipping Service");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            fEntity.setDescription(fEntity.getShippingCarrier().getName());
                            fEntity.setCode(fEntity.getShippingCarrier().getCode());
                            daoVendorShippingService.insert(fEntity);
                            if (daoVendorShippingService.getErrorMessage() == null) {
                                if (fDefaultCheckBox.isSelected()) {
                                    fVendor.setDefaultVendorShippingService(fEntity);
                                    daoVendor.update(fVendor);
                                }
                                fEntityList.add(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoVendorShippingService.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    List<ShippingCarrier> list = Collections.singletonList(fEntity.getShippingCarrier());
                    fShippingCarrierCombo.setItems(FXCollections.observableList(list));
                    fDefaultCheckBox.setDisable(false);
                    Boolean isDefault;
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    if (fVendor != null && fVendor.getDefaultVendorShippingService() != null && fVendor.getDefaultVendorShippingService().getId().equals(fEntity.getId())) {
                        fDefaultCheckBox.setSelected(true);
                        fDefaultCheckBox.setDisable(true);
                        isDefault = true;
                    } else {
                        fDefaultCheckBox.setSelected(false);
                        isDefault = false;
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Shipping Service");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            fEntity.setDescription(fEntity.getShippingCarrier().getName());
                            fEntity.setCode(fEntity.getShippingCarrier().getCode());
                            daoVendorShippingService.update(fEntity);
                            if (daoVendorShippingService.getErrorMessage() == null) {
                                if (isDefault.compareTo(fDefaultCheckBox.isSelected()) != 0 && fDefaultCheckBox.isSelected()) {
                                    fVendor.setDefaultVendorShippingService(fEntity);
                                    daoVendor.update(fVendor);
                                }
                                List<VendorShippingService> newList = fVendor.getVendorShippingServices()
                                        .stream()
                                        .sorted((e1, e2) -> e1.getDescription().compareTo(e2.getDescription()))
                                        .collect(Collectors.toList());
                                if (!list.isEmpty()) {
                                    fEntityList = FXCollections.observableList(newList);
                                } else {
                                    fEntityList = FXCollections.observableArrayList();
                                }
                                fTableView.setItems(fEntityList);
                            } else {
                                lblWarning.setText(daoVendorShippingService.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(VendorShippingService_.deliveryDay).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fVendor != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        if (fVendor.getDefaultVendorShippingService() != null && fVendor.getDefaultVendorShippingService().getId().equals(fEntity.getId())) {
                            fVendor.setDefaultVendorShippingService(null);
                            daoVendor.update(fVendor);
                        }
                        daoVendorShippingService.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        fVendorCombo.setPrefWidth(300);

        TableColumn<VendorShippingService, String> vendorCol = new TableColumn<>("Vendor");
        vendorCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorShippingService, String> p) -> {
            if (p.getValue().getVendor() != null) {
                return new SimpleStringProperty(p.getValue().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        vendorCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorCol.setPrefWidth(300);

        TableColumn<VendorShippingService, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(VendorShippingService_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        codeCol.setPrefWidth(100);

        TableColumn<VendorShippingService, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(VendorShippingService_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        TableColumn<VendorShippingService, String> deliveryDayCol = new TableColumn<>("Delivery Day");
        deliveryDayCol.setCellValueFactory(new PropertyValueFactory<>(VendorShippingService_.deliveryDay.getName()));
        deliveryDayCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        deliveryDayCol.setPrefWidth(100);

        TableColumn<VendorShippingService, Boolean> defaultCol = new TableColumn<>("Default");
        defaultCol.setCellValueFactory((CellDataFeatures<VendorShippingService, Boolean> p) -> {
            if (p.getValue().getVendor() != null && p.getValue().getVendor().getDefaultVendorShippingService() != null) {
                return new SimpleBooleanProperty(p.getValue().getId().equals(p.getValue().getVendor().getDefaultVendorShippingService().getId()));
            } else {
                return new SimpleBooleanProperty(Boolean.FALSE);
            }
        });
        defaultCol.setCellFactory(stringCell(Pos.CENTER));
        defaultCol.setPrefWidth(60);

        fTableView.getColumns().add(vendorCol);
        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(deliveryDayCol);
        fTableView.getColumns().add(defaultCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fVendorCombo, 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        dataUI.setUIComponent(VendorShippingService_.shippingCarrier, fShippingCarrierCombo);
        fShippingCarrierCombo.setPrefWidth(250);
        fDefaultCheckBox.setPrefWidth(60);
        add(editPane, "Carrier:", fShippingCarrierCombo, fListener, 1);
        add(editPane, "Delivery Days:", dataUI.createTextField(VendorShippingService_.deliveryDay, 250), fListener, 2);
        add(editPane, "Is Default:", fDefaultCheckBox, fListener, 3);
        return editPane;
    }
}

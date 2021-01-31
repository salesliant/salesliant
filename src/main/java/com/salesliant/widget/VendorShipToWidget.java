package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorShipTo;
import com.salesliant.entity.VendorShipTo_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class VendorShipToWidget extends BaseListUI<VendorShipTo> {

    private final BaseDao<VendorShipTo> daoVendorShipTo = new BaseDao<>(VendorShipTo.class);
    private final DataUI dataUI = new DataUI(VendorShipTo.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(VendorShipToWidget.class.getName());

    public VendorShipToWidget(Vendor vendor) {
        mainView = createMainView();
        List<VendorShipTo> shipToList = daoVendorShipTo.read(VendorShipTo_.vendor, vendor.getId());
        fEntityList = FXCollections.observableList(shipToList);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new VendorShipTo();
                fEntity.setStore(Config.getStore());
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Vendor ShipTo");
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        VendorShipTo e = daoVendorShipTo.insert(fEntity);
                        if (daoVendorShipTo.getErrorMessage() == null) {
                            fEntityList.add(e);
                            fTableView.getSelectionModel().select(e);
                        } else {
                            lblWarning.setText(daoVendorShipTo.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    dataUI.getTextField(VendorShipTo_.address1).requestFocus();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "VendorShipTo");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            VendorShipTo e = daoVendorShipTo.update(fEntity);
                            if (daoVendorShipTo.getErrorMessage() == null) {
                                fTableView.getItems().set(fTableView.getSelectionModel().getSelectedIndex(), e);
                                fTableView.getSelectionModel().select(e);
                            } else {
                                lblWarning.setText(daoVendorShipTo.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        dataUI.getTextField(VendorShipTo_.address1).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendorShipTo.delete(fEntity);
                        fTableView.getItems().remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Customer Ship To:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<VendorShipTo, String> shipToFirstNameCol = new TableColumn<>("Contact");
        shipToFirstNameCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorShipTo, String> p) -> {
            if (p.getValue() != null) {
                String contactName = (!isEmpty(p.getValue().getFirstName()) ? p.getValue().getFirstName() : "")
                        + (!isEmpty(p.getValue().getFirstName()) ? " " : "")
                        + (!isEmpty(p.getValue().getLastName()) ? p.getValue().getLastName() : "");
                return new SimpleStringProperty(contactName);
            } else {
                return null;
            }
        });
        shipToFirstNameCol.setPrefWidth(100);

        TableColumn<VendorShipTo, String> shipToAddressCol = new TableColumn<>("Address");
        shipToAddressCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorShipTo, String> p) -> {
            if (p.getValue() != null) {
                String shipTo = "";
                if (!isEmpty(p.getValue().getAddress1())) {
                    shipTo = shipTo + p.getValue().getAddress1() + "\n";
                }
                if (!isEmpty(p.getValue().getAddress2())) {
                    shipTo = shipTo + p.getValue().getAddress2();
                }
                return new SimpleStringProperty(shipTo);
            } else {
                return null;
            }
        });
        shipToAddressCol.setPrefWidth(150);

        TableColumn<VendorShipTo, String> shipToCityCol = new TableColumn<>("City");
        shipToCityCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.city.getName()));
        shipToCityCol.setPrefWidth(120);

        TableColumn<VendorShipTo, String> shipToStateCol = new TableColumn<>("State");
        shipToStateCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.state.getName()));
        shipToStateCol.setPrefWidth(90);

        TableColumn<VendorShipTo, String> shipToPhoneCol = new TableColumn<>("Phone");
        shipToPhoneCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.phoneNumber.getName()));
        shipToPhoneCol.setPrefWidth(110);

        fTableView.getColumns().add(shipToFirstNameCol);
        fTableView.getColumns().add(shipToAddressCol);
        fTableView.getColumns().add(shipToCityCol);
        fTableView.getColumns().add(shipToStateCol);
        fTableView.getColumns().add(shipToPhoneCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(140);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane pane = new GridPane();
        pane.getStyleClass().add("editView");

        add(pane, "First Name: ", dataUI.createTextField(VendorShipTo_.firstName, 180), fListener, 1);
        add(pane, "Last Name: ", dataUI.createTextField(VendorShipTo_.lastName, 180), fListener, 2);
        add(pane, "Company: ", dataUI.createTextField(VendorShipTo_.company, 180), fListener, 3);
        add(pane, "Address 1: ", dataUI.createTextField(VendorShipTo_.address1, 180), fListener, 4);
        add(pane, "Address 2: ", dataUI.createTextField(VendorShipTo_.address2, 180), fListener, 5);
        add(pane, "City: ", dataUI.createTextField(VendorShipTo_.city, 180), fListener, 6);
        add(pane, "State: ", dataUI.createTextField(VendorShipTo_.state, 180), fListener, 7);
        add(pane, "Post Code: ", dataUI.createTextField(VendorShipTo_.postCode), fListener, 8);
        add(pane, "Country: ", dataUI.createTextField(VendorShipTo_.country), fListener, 9);
        add(pane, "Phone Number: ", dataUI.createTextField(VendorShipTo_.phoneNumber), fListener, 10);

        Button copyBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, fHandler);
        copyBtn.setText("Copy From Bill To");
        copyBtn.setPrefWidth(150);

        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        editPane.add(pane, 0, 0);
        editPane.add(copyBtn, 1, 0);
        editPane.add(lblWarning, 0, 1, 2, 2);

        return editPane;
    }

    protected HBox createButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

}

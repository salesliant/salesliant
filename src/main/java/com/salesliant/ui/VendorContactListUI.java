/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorContact;
import com.salesliant.entity.VendorContact_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.DataUI;
import com.salesliant.widget.VendorWidget;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
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
public class VendorContactListUI extends BaseListUI<VendorContact> {

    private final BaseDao<VendorContact> daoVendorContact = new BaseDao<>(VendorContact.class);
    private final BaseDao<Vendor> daoVendor = new BaseDao<>(Vendor.class);
    private final DataUI dataUI = new DataUI(VendorContact.class);
    private final GridPane fEditPane;
    private final CheckBox fDefaultCheckBox = new CheckBox();
    private VendorWidget fVendorCombo = new VendorWidget();
    private Vendor fVendor;
    private static final Logger LOGGER = Logger.getLogger(VendorContactListUI.class.getName());

    public VendorContactListUI() {
        mainView = createMainView();
        fVendorCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Vendor> observable, Vendor newValue, Vendor oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fVendor = fVendorCombo.getSelectionModel().getSelectedItem();
                List<VendorContact> list = fVendor.getVendorContacts()
                        .stream()
                        .sorted((e1, e2) -> e1.getContactName().compareTo(e2.getContactName()))
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
                    fEntity = new VendorContact();
                    fEntity.setVendor(fVendor);
                    fDefaultCheckBox.setDisable(false);
                    fDefaultCheckBox.setSelected(false);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Vendor Contact");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoVendorContact.insert(fEntity);
                            if (daoVendorContact.getErrorMessage() == null) {
                                if (fDefaultCheckBox.isSelected()) {
                                    fVendor.setDefaultVendorContact(fEntity);
                                    daoVendor.update(fVendor);
                                }
                                fEntityList.add(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoVendorContact.getErrorMessage());
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
                    fDefaultCheckBox.setDisable(false);
                    Boolean isDefault;
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    if (fVendor != null && fVendor.getDefaultVendorContact() != null && fVendor.getDefaultVendorContact().getId().equals(fEntity.getId())) {
                        fDefaultCheckBox.setSelected(true);
                        fDefaultCheckBox.setDisable(true);
                        isDefault = true;
                    } else {
                        fDefaultCheckBox.setSelected(false);
                        isDefault = false;
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Vendor Contact");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoVendorContact.update(fEntity);
                            if (daoVendorContact.getErrorMessage() == null) {
                                if (isDefault.compareTo(fDefaultCheckBox.isSelected()) != 0 && fDefaultCheckBox.isSelected()) {
                                    fVendor.setDefaultVendorContact(fEntity);
                                    daoVendor.update(fVendor);
                                }
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoVendorContact.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(VendorContact_.contactName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fVendor != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        if (fVendor.getDefaultVendorContact() != null && fVendor.getDefaultVendorContact().getId().equals(fEntity.getId())) {
                            fVendor.setDefaultVendorContact(null);
                            daoVendor.update(fVendor);
                        }
                        daoVendorContact.delete(fEntity);
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

        TableColumn<VendorContact, String> nameCol = new TableColumn<>("Contact Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.contactName.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(175);

        TableColumn<VendorContact, String> phoneCol = new TableColumn<>("Phone Number");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.phoneNumber.getName()));
        phoneCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneCol.setPrefWidth(175);

        TableColumn<VendorContact, String> cellCol = new TableColumn<>("Cell Number");
        cellCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.cellPhoneNumber.getName()));
        cellCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cellCol.setPrefWidth(120);

        TableColumn<VendorContact, String> faxCol = new TableColumn<>("Fax Number");
        faxCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.faxNumber.getName()));
        faxCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        faxCol.setPrefWidth(120);

        TableColumn<VendorContact, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.emailAddress.getName()));
        emailCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        emailCol.setPrefWidth(150);

        TableColumn<VendorContact, String> departmentCol = new TableColumn<>("Department");
        departmentCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.department.getName()));
        departmentCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        departmentCol.setPrefWidth(100);

        TableColumn<VendorContact, Boolean> defaultCol = new TableColumn<>("Default");
        defaultCol.setCellValueFactory((CellDataFeatures<VendorContact, Boolean> p) -> {
            if (p.getValue().getVendor() != null && p.getValue().getVendor().getDefaultVendorContact() != null) {
                return new SimpleBooleanProperty(p.getValue().getId().equals(p.getValue().getVendor().getDefaultVendorContact().getId()));
            } else {
                return new SimpleBooleanProperty(Boolean.FALSE);
            }
        });
        defaultCol.setCellFactory(stringCell(Pos.CENTER));
        defaultCol.setPrefWidth(50);

        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(phoneCol);
        fTableView.getColumns().add(cellCol);
        fTableView.getColumns().add(faxCol);
        fTableView.getColumns().add(emailCol);
        fTableView.getColumns().add(departmentCol);
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
        add(editPane, "Contact Name:", dataUI.createTextField(VendorContact_.contactName, 180), fListener, 1);
        add(editPane, "Department:", dataUI.createTextField(VendorContact_.department, 180), fListener, 2);
        add(editPane, "Phone Number:", dataUI.createTextField(VendorContact_.phoneNumber), fListener, 3);
        add(editPane, "Cell Number:", dataUI.createTextField(VendorContact_.cellPhoneNumber), fListener, 4);
        add(editPane, "Fax Number:", dataUI.createTextField(VendorContact_.faxNumber), fListener, 5);
        add(editPane, "Email Address:", dataUI.createTextField(VendorContact_.emailAddress, 180), fListener, 6);
        fDefaultCheckBox.setPrefWidth(60);
        add(editPane, "Is Default:", fDefaultCheckBox, fListener, 7);
        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            dataUI.getTextField(VendorContact_.contactName).setText(uppercaseFirst(dataUI.getTextField(VendorContact_.contactName).getText()));
            saveBtn.setDisable(dataUI.getTextField(VendorContact_.contactName).getText().trim().isEmpty());
        }
    }
}

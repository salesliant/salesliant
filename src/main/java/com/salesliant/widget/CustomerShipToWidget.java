package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.CustomerShipTo_;
import com.salesliant.entity.PostCode;
import com.salesliant.entity.PostCode_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CustomerShipToWidget extends BaseListUI<CustomerShipTo> {

    private final BaseDao<CustomerShipTo> daoCustomerShipTo = new BaseDao<>(CustomerShipTo.class);
    private final BaseDao<PostCode> daoPostCode = new BaseDao<>(PostCode.class);
    private final DataUI dataUI = new DataUI(CustomerShipTo.class);
    private TaxZoneWidget fTaxZoneCombo;
    private final CountryWidget fCountryCombo = new CountryWidget();
    private final GridPane fEditPane;
    private final Customer fCustomer;
    private static final Logger LOGGER = Logger.getLogger(CustomerShipToWidget.class.getName());

    public CustomerShipToWidget(Customer customer) {
        this.fCustomer = customer;
        mainView = createMainView();
        List<CustomerShipTo> shipToList = fCustomer.getCustomerShipTos().stream()
                .sorted((e1, e2) -> e1.getContactName().compareToIgnoreCase(e2.getContactName()))
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(shipToList);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new CustomerShipTo();
                fEntity.setStore(Config.getStore());
                fEntity.setCustomer(fCustomer);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Ship To");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        CustomerShipTo e = daoCustomerShipTo.insert(fEntity);
                        if (daoCustomerShipTo.getErrorMessage() == null) {
                            fEntityList.add(e);
                            fTableView.getSelectionModel().select(e);
                            fTableView.scrollTo(e);
                        } else {
                            lblWarning.setText(daoCustomerShipTo.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    dataUI.getTextField(CustomerShipTo_.contactName).requestFocus();
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Ship To");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            CustomerShipTo e = daoCustomerShipTo.update(fEntity);
                            if (daoCustomerShipTo.getErrorMessage() == null) {
                                fEntity.setVersion(e.getVersion());
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoCustomerShipTo.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        dataUI.getTextField(CustomerShipTo_.contactName).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_CLONE:
                fEntity = new CustomerShipTo();
                fEntity.setCustomer(fCustomer);
                fEntity.setStore(Config.getStore());
                String contactName = (!isEmpty(fCustomer.getFirstName()) ? fCustomer.getFirstName() : "")
                        + (!isEmpty(fCustomer.getFirstName()) ? " " : "")
                        + (!isEmpty(fCustomer.getLastName()) ? fCustomer.getLastName() : "");
                fEntity.setContactName(contactName);
                fEntity.setCompany(fCustomer.getCompany());
                fEntity.setAddress1(fCustomer.getAddress1());
                fEntity.setAddress2(fCustomer.getAddress2());
                fEntity.setCity(fCustomer.getCity());
                fEntity.setState(fCustomer.getState());
                fEntity.setPostCode(fCustomer.getPostCode());
                fEntity.setCountry(fCustomer.getCountry());
                fEntity.setPhoneNumber(fCustomer.getPhoneNumber());
                fEntity.setEmailAddress(fCustomer.getEmailAddress());
                fEntity.setTaxZone(fCustomer.getTaxZone());
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerShipTo.delete(fEntity);
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

        TableColumn<CustomerShipTo, String> shipToFirstNameCol = new TableColumn<>("Contact");
        shipToFirstNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.contactName.getName()));
        shipToFirstNameCol.setPrefWidth(150);

        TableColumn<CustomerShipTo, String> shipToAddressCol = new TableColumn<>("Address");
        shipToAddressCol.setCellValueFactory((TableColumn.CellDataFeatures<CustomerShipTo, String> p) -> {
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
        shipToAddressCol.setPrefWidth(300);

        TableColumn<CustomerShipTo, String> shipToCityCol = new TableColumn<>("City");
        shipToCityCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.city.getName()));
        shipToCityCol.setPrefWidth(120);

        TableColumn<CustomerShipTo, String> shipToStateCol = new TableColumn<>("State");
        shipToStateCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.state.getName()));
        shipToStateCol.setPrefWidth(90);

        TableColumn<CustomerShipTo, String> shipToPhoneCol = new TableColumn<>("Phone");
        shipToPhoneCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.phoneNumber.getName()));
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

        fTaxZoneCombo = new TaxZoneWidget();
        fTaxZoneCombo.setPrefWidth(180);
        dataUI.setUIComponent(CustomerShipTo_.taxZone, fTaxZoneCombo);
        dataUI.setUIComponent(CustomerShipTo_.country, fCountryCombo);
        add(pane, "Contact Name: ", dataUI.createTextField(CustomerShipTo_.contactName, 180), fListener, 1);
        add(pane, "Company: ", dataUI.createTextField(CustomerShipTo_.company, 180), fListener, 3);
        add(pane, "Address 1: ", dataUI.createTextField(CustomerShipTo_.address1, 180), fListener, 4);
        add(pane, "Address 2: ", dataUI.createTextField(CustomerShipTo_.address2, 180), fListener, 5);
        add(pane, "Post Code: ", dataUI.createTextField(CustomerShipTo_.postCode), fListener, 6);
        add(pane, "City: ", dataUI.createTextField(CustomerShipTo_.city, 180), fListener, 7);
        add(pane, "State: ", dataUI.createTextField(CustomerShipTo_.state, 180), fListener, 8);
        add(pane, "Country: ", fCountryCombo, fListener, 9);
        add(pane, "Phone Number: ", dataUI.createTextField(CustomerShipTo_.phoneNumber), fListener, 10);
        add(pane, "Email Address: ", dataUI.createTextField(CustomerShipTo_.emailAddress, 180), fListener, 13);
        add(pane, "Tax Group: ", fTaxZoneCombo, fListener, 12);

        dataUI.getTextField(CustomerShipTo_.postCode).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    PostCode postCode = daoPostCode.find(PostCode_.postCode, node.getText().trim());
                    if (postCode != null) {
                        dataUI.getTextField(CustomerShipTo_.city).setText(postCode.getCity());
                        dataUI.getTextField(CustomerShipTo_.state).setText(postCode.getState());
                        fCountryCombo.setSelectedCountry(postCode.getCountry());
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
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

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(CustomerShipTo_.contactName).getText().trim().isEmpty() || dataUI.getTextField(CustomerShipTo_.address1).getText().trim().isEmpty()
                    || dataUI.getTextField(CustomerShipTo_.city).getText().trim().isEmpty() || dataUI.getTextField(CustomerShipTo_.postCode).getText().trim().isEmpty());
        }
    }
}

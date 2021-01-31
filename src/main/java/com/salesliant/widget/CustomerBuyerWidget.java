package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerBuyer;
import com.salesliant.entity.CustomerBuyer_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
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

public class CustomerBuyerWidget extends BaseListUI<CustomerBuyer> {

    private final BaseDao<CustomerBuyer> daoCustomerBuyer = new BaseDao<>(CustomerBuyer.class);
    private final DataUI dataUI = new DataUI(CustomerBuyer.class);
    private final GridPane fEditPane;
    private final Customer fCustomer;
    private static final Logger LOGGER = Logger.getLogger(CustomerBuyerWidget.class.getName());

    public CustomerBuyerWidget(Customer customer) {
        this.fCustomer = customer;
        mainView = createMainView();
        List<CustomerBuyer> buyerList = fCustomer.getCustomerBuyers().stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getLastName()) ? e1.getLastName() : "") + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                    String name2 = (!isEmpty(e2.getLastName()) ? e2.getLastName() : "") + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                    return name1.compareToIgnoreCase(name2);
                })
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(buyerList);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new CustomerBuyer();
                fEntity.setStore(Config.getStore());
                fEntity.setCustomer(fCustomer);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Buyer");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setDateCreated(new Date());
                        daoCustomerBuyer.insert(fEntity);
                        if (daoCustomerBuyer.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                            fTableView.scrollTo(fEntity);
                        } else {
                            lblWarning.setText(daoCustomerBuyer.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    dataUI.getTextField(CustomerBuyer_.firstName).requestFocus();
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Buyer");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCustomerBuyer.update(fEntity);
                            if (daoCustomerBuyer.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoCustomerBuyer.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        dataUI.getTextField(CustomerBuyer_.firstName).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerBuyer.delete(fEntity);
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
        Label tableLbl = new Label("List of Customer Buyer:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<CustomerBuyer, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.firstName.getName()));
        firstNameCol.setPrefWidth(200);

        TableColumn<CustomerBuyer, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.lastName.getName()));
        lastNameCol.setPrefWidth(200);

        TableColumn<CustomerBuyer, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.phoneNumber.getName()));
        phoneNumberCol.setPrefWidth(200);

        TableColumn<CustomerBuyer, String> emailAddressCol = new TableColumn<>("Email");
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<>(CustomerBuyer_.emailAddress.getName()));
        emailAddressCol.setPrefWidth(300);

        fTableView.getColumns().add(firstNameCol);
        fTableView.getColumns().add(lastNameCol);
        fTableView.getColumns().add(phoneNumberCol);
        fTableView.getColumns().add(emailAddressCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(140);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "First Name: ", dataUI.createTextField(CustomerBuyer_.firstName, 180), fListener, 1);
        add(editPane, "Last Name: ", dataUI.createTextField(CustomerBuyer_.lastName, 180), fListener, 2);
        add(editPane, "Title: ", dataUI.createTextField(CustomerBuyer_.title, 180), fListener, 3);
        add(editPane, "Phone Number: ", dataUI.createTextField(CustomerBuyer_.phoneNumber), fListener, 4);
        add(editPane, "Cell Number: ", dataUI.createTextField(CustomerBuyer_.cellPhoneNumber), fListener, 5);
        add(editPane, "Fax Number: ", dataUI.createTextField(CustomerBuyer_.faxNumber), fListener, 6);
        add(editPane, "Email Address: ", dataUI.createTextField(CustomerBuyer_.emailAddress, 180), fListener, 7);
        editPane.add(lblWarning, 0, 8, 2, 1);

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
            saveBtn.setDisable(dataUI.getTextField(CustomerBuyer_.firstName).getText().trim().isEmpty() || dataUI.getTextField(CustomerBuyer_.lastName).getText().trim().isEmpty());
        }
    }
}

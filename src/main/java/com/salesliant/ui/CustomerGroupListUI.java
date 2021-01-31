package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.CustomerGroup;
import com.salesliant.entity.CustomerGroup_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.percentCell;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.DataUI;
import com.salesliant.widget.PaymentTypeWidget;
import com.salesliant.widget.ShippingServiceWidget;
import java.math.BigDecimal;
import java.util.Date;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class CustomerGroupListUI extends BaseListUI<CustomerGroup> {

    private final BaseDao<CustomerGroup> daoCustomerGroup = new BaseDao<>(CustomerGroup.class);
    private final DataUI dataUI = new DataUI(CustomerGroup.class);
    private final GridPane fEditPane;
    private ComboBox fPaymentTypeComboBox;
    private ComboBox fShippingServiceComboBox;
    private final static String CUSTOMER_GROUP_TITLE = "Customer Group";
    private static final Logger LOGGER = Logger.getLogger(CustomerGroupListUI.class.getName());

    public CustomerGroupListUI() {
        mainView = createMainView();
        List<CustomerGroup> list = daoCustomerGroup.read(CustomerGroup_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        dialogTitle = CUSTOMER_GROUP_TITLE;
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new CustomerGroup();
                setDefault();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Group");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        fEntity.setDateAdded(new Date());
                        dataUI.getData(fEntity);
                        daoCustomerGroup.insert(fEntity);
                        if (daoCustomerGroup.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoCustomerGroup.getErrorMessage());
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(CustomerGroup_.name).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Group");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCustomerGroup.update(fEntity);
                            if (daoCustomerGroup.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoCustomerGroup.getErrorMessage());
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(CustomerGroup_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerGroup.delete(fEntity);
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
        Label tableLbl = new Label("List of Customer Group:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<CustomerGroup, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerGroup_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(150);
        TableColumn<CustomerGroup, String> discountCol = new TableColumn<>("Discount");
        discountCol.setCellValueFactory(new PropertyValueFactory<>(CustomerGroup_.discount.getName()));
        discountCol.setCellFactory(percentCell(Pos.CENTER));
        discountCol.setPrefWidth(150);
        TableColumn<CustomerGroup, String> taxExcemptCol = new TableColumn<>("Tax Excempt");
        taxExcemptCol.setCellValueFactory(new PropertyValueFactory<>(CustomerGroup_.taxExempt.getName()));
        taxExcemptCol.setCellFactory(stringCell(Pos.CENTER));
        taxExcemptCol.setPrefWidth(150);
        TableColumn<CustomerGroup, String> paymentTypeCol = new TableColumn<>("Payment Type");
        paymentTypeCol.setCellValueFactory((CellDataFeatures<CustomerGroup, String> p) -> {
            if (p.getValue().getPaymentType() != null) {
                return new SimpleStringProperty(p.getValue().getPaymentType().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        paymentTypeCol.setCellFactory(stringCell(Pos.CENTER));
        paymentTypeCol.setPrefWidth(150);

        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(discountCol);
        fTableView.getColumns().add(taxExcemptCol);
        fTableView.getColumns().add(paymentTypeCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        fPaymentTypeComboBox = new PaymentTypeWidget();
        fPaymentTypeComboBox.setPrefWidth(250);
        dataUI.setUIComponent(CustomerGroup_.paymentType, fPaymentTypeComboBox);
        fShippingServiceComboBox = new ShippingServiceWidget();
        fShippingServiceComboBox.setPrefWidth(250);
        dataUI.setUIComponent(CustomerGroup_.shippingService, fShippingServiceComboBox);
        add(editPane, "Name:*", dataUI.createTextField(CustomerGroup_.name), fListener, 250.0, 0);
        add(editPane, "Discount %:*", dataUI.createTextField(CustomerGroup_.discount), fListener, 1);
        add(editPane, "Tax Excempt:*", dataUI.createCheckBox(CustomerGroup_.taxExempt), fListener, 2);
        add(editPane, "Shipping Modules:*", fShippingServiceComboBox, fListener, 3);
        add(editPane, "Payment Modules:*", fPaymentTypeComboBox, fListener, 4);
        // dataUI.getNumberField(CustomerGroup_.discount).setDecimalFormat(getPercentFormat());

        editPane.add(lblWarning, 0, 5, 2, 1);

        return editPane;
    }

    private void setDefault() {
        fEntity.setDiscount(BigDecimal.ZERO);
        fEntity.setTaxExempt(Boolean.FALSE);
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            dataUI.getTextField(CustomerGroup_.name).setText(uppercaseFirst(dataUI.getTextField(CustomerGroup_.name).getText()));
            saveBtn.setDisable(dataUI.getTextField(CustomerGroup_.name).getText().trim().isEmpty());
        }
    }
}

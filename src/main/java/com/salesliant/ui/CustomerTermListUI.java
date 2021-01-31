package com.salesliant.ui;

import com.salesliant.entity.CustomerTerm;
import com.salesliant.entity.CustomerTerm_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.percentCell;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class CustomerTermListUI extends BaseListUI<CustomerTerm> {

    private final BaseDao<CustomerTerm> daoCustomerTerm = new BaseDao<>(CustomerTerm.class);
    private final DataUI dataUI = new DataUI(CustomerTerm.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(CustomerTermListUI.class.getName());

    public CustomerTermListUI() {
        mainView = createMainView();
        List<CustomerTerm> list = daoCustomerTerm.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new CustomerTerm();
                fEntity.setDiscountDays(0);
                fEntity.setDiscountRate(BigDecimal.ZERO);
                fEntity.setMinimumPayment(BigDecimal.ZERO);
                fEntity.setMinimumFinanceCharge(BigDecimal.ZERO);
                fEntity.setInterestRate(BigDecimal.ZERO);
                fEntity.setStoreAccountTag(Boolean.TRUE);

                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Term");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoCustomerTerm.insert(fEntity);
                        if (daoCustomerTerm.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoCustomerTerm.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(CustomerTerm_.code).requestFocus());
                fInputDialog.showAndWait();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Customer Term");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCustomerTerm.update(fEntity);
                            if (daoCustomerTerm.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoCustomerTerm.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(CustomerTerm_.code).requestFocus());
                    fInputDialog.showAndWait();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCustomerTerm.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3);
        Label tableLbl = new Label("List of Customer Term:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<CustomerTerm, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        codeCol.setPrefWidth(120);

        TableColumn<CustomerTerm, String> dueDaysCol = new TableColumn<>("Due Days");
        dueDaysCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.dueDays.getName()));
        dueDaysCol.setCellFactory(stringCell(Pos.CENTER));
        dueDaysCol.setPrefWidth(90);

        TableColumn<CustomerTerm, String> interestCol = new TableColumn<>("Interest Rate");
        interestCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.interestRate.getName()));
        interestCol.setCellFactory(percentCell(Pos.CENTER));
        interestCol.setPrefWidth(120);

        TableColumn<CustomerTerm, String> miniumChargeCol = new TableColumn<>("Minimum Finance Charge");
        miniumChargeCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.minimumFinanceCharge.getName()));
        miniumChargeCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        miniumChargeCol.setPrefWidth(160);

        TableColumn<CustomerTerm, String> miniumPaymentCol = new TableColumn<>("Minimum Payment");
        miniumPaymentCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.minimumPayment.getName()));
        miniumPaymentCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        miniumPaymentCol.setPrefWidth(120);

        TableColumn<CustomerTerm, String> discountDaysCol = new TableColumn<>("Discount Days");
        discountDaysCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.discountDays.getName()));
        discountDaysCol.setCellFactory(stringCell(Pos.CENTER));
        discountDaysCol.setPrefWidth(90);

        TableColumn<CustomerTerm, String> discountRateCol = new TableColumn<>("Discount Rate");
        discountRateCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.discountRate.getName()));
        discountRateCol.setCellFactory(percentCell(Pos.CENTER));
        discountRateCol.setPrefWidth(90);

        TableColumn<CustomerTerm, String> storeAccountCol = new TableColumn<>("Store Account");
        storeAccountCol.setCellValueFactory(new PropertyValueFactory<>(CustomerTerm_.storeAccountTag.getName()));
        storeAccountCol.setCellFactory(stringCell(Pos.CENTER));
        storeAccountCol.setPrefWidth(120);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(dueDaysCol);
        fTableView.getColumns().add(interestCol);
        fTableView.getColumns().add(miniumChargeCol);
        fTableView.getColumns().add(miniumPaymentCol);
        fTableView.getColumns().add(discountDaysCol);
        fTableView.getColumns().add(discountRateCol);
        fTableView.getColumns().add(storeAccountCol);

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
        add(editPane, "Code:*", dataUI.createTextField(CustomerTerm_.code), fListener, 0);
        add(editPane, "Due Days:*", dataUI.createTextField(CustomerTerm_.dueDays), fListener, 1);
        add(editPane, "Interest Rate:*", dataUI.createTextField(CustomerTerm_.interestRate), fListener, 2);
        add(editPane, "Minimum Financial Charge:*", dataUI.createTextField(CustomerTerm_.minimumFinanceCharge), fListener, 3);
        add(editPane, "Minimum Payment:*", dataUI.createTextField(CustomerTerm_.minimumPayment), fListener, 4);
        add(editPane, "Discount Days:*", dataUI.createTextField(CustomerTerm_.discountDays), fListener, 5);
        add(editPane, "Discount Rate:*", dataUI.createTextField(CustomerTerm_.discountRate), fListener, 6);
        add(editPane, "Is On Account:*", dataUI.createCheckBox(CustomerTerm_.storeAccountTag), fListener, 7);
        editPane.add(lblWarning, 0, 7, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(
                    dataUI.getTextField(CustomerTerm_.code).getText().trim().isEmpty() || dataUI.getTextField(CustomerTerm_.dueDays).getText().trim().isEmpty() || dataUI.getTextField(CustomerTerm_.interestRate).getText().trim().isEmpty()
                    || dataUI.getTextField(CustomerTerm_.minimumFinanceCharge).getText().trim().isEmpty() || dataUI.getTextField(CustomerTerm_.minimumPayment).getText().trim().isEmpty() || dataUI.getTextField(CustomerTerm_.discountDays).getText().trim().isEmpty()
                    || dataUI.getTextField(CustomerTerm_.discountRate).getText().trim().isEmpty());
        }
    }
}

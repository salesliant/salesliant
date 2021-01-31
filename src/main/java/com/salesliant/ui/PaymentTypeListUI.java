package com.salesliant.ui;

import com.salesliant.entity.PaymentType;
import com.salesliant.entity.PaymentType_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DBConstants;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class PaymentTypeListUI extends BaseListUI<PaymentType> {

    private final BaseDao<PaymentType> daoPaymentType = new BaseDao<>(PaymentType.class);
    private final DataUI dataUI = new DataUI(PaymentType.class);
    private final GridPane fEditPane;
    private final ComboBox fPaymentVerificationTypeCombo = DBConstants.getComboBoxTypes(DBConstants.TYPE_PAYMENT_VERIFICATION);
    private static final Logger LOGGER = Logger.getLogger(PaymentTypeListUI.class.getName());

    public PaymentTypeListUI() {
        mainView = createMainView();
        List<PaymentType> list = daoPaymentType.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new PaymentType();
                fEntity.setAmount(BigDecimal.ZERO);
                fEntity.setMaximumAmount(BigDecimal.ZERO);
                fEntity.setPreventOverTendering(Boolean.TRUE);
                fEntity.setDoNotPopCashDrawer(Boolean.TRUE);
                fEntity.setVerificationType(DBConstants.TYPE_VERIFICATION_CREDIT_CARD);
                fEntity.setIsNetTerm(Boolean.FALSE);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Payment Type");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoPaymentType.insert(fEntity);
                        if (daoPaymentType.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoPaymentType.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(PaymentType_.code).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Payment Type");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            fEntity.setAmount(BigDecimal.ZERO);
                            daoPaymentType.update(fEntity);
                            if (daoPaymentType.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoPaymentType.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(PaymentType_.code).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (fEntity.getPayments() != null && !fEntity.getPayments().isEmpty()) {
                        showAlertDialog("There are payments related to this type. You can't delete this payment type!");
                    } else {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            daoPaymentType.delete(fEntity);
                            fEntityList.remove(fEntity);
                        });
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Payment Type:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<PaymentType, String> descriptionCol = new TableColumn<>("Code");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(PaymentType_.code.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER));
        descriptionCol.setPrefWidth(150);

        TableColumn<PaymentType, String> displayOrderCol = new TableColumn<>("Display Order");
        displayOrderCol.setCellValueFactory(new PropertyValueFactory<>(PaymentType_.displayOrder.getName()));
        displayOrderCol.setCellFactory(stringCell(Pos.CENTER));
        displayOrderCol.setPrefWidth(100);

        TableColumn<PaymentType, String> maximumAmountCol = new TableColumn<>("Maximum Amount");
        maximumAmountCol.setCellValueFactory(new PropertyValueFactory<>(PaymentType_.maximumAmount.getName()));
        maximumAmountCol.setCellFactory(stringCell(Pos.CENTER));
        maximumAmountCol.setPrefWidth(140);

        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(displayOrderCol);
        fTableView.getColumns().add(maximumAmountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.setHgap(5);
        editPane.setVgap(5);
        editPane.setPrefSize(450, 5);

        dataUI.setUIComponent(PaymentType_.verificationType, fPaymentVerificationTypeCombo);
        add(editPane, "Code:*", dataUI.createTextField(PaymentType_.code), fListener, 0);
        add(editPane, "Display Order:", dataUI.createTextField(PaymentType_.displayOrder), fListener, 1);
        add(editPane, "Is Net Term:", dataUI.createCheckBox(PaymentType_.isNetTerm), fListener, 2);
        add(editPane, "Maximum Amount:", dataUI.createTextField(PaymentType_.maximumAmount), fListener, 3);
        add(editPane, "Validation Line #1:", dataUI.createTextField(PaymentType_.validationLine1), fListener, 4);
        add(editPane, "Validation Line #2:", dataUI.createTextField(PaymentType_.validationLine2), fListener, 5);
        add(editPane, "Validation Mask:", dataUI.createTextField(PaymentType_.validationLine3), fListener, 6);
        add(editPane, "Round-to Value:", dataUI.createTextField(PaymentType_.roundToValue), fListener, 7);
        add(editPane, "Verification Type:", fPaymentVerificationTypeCombo, fListener, 8);
        add(editPane, "Scan Code:", dataUI.createTextField(PaymentType_.scanCode), fListener, 9);
        add(editPane, "Prevent Over-tendering:", dataUI.createCheckBox(PaymentType_.preventOverTendering), fListener, 10);
        add(editPane, "Do Not pop cahs drawer:", dataUI.createCheckBox(PaymentType_.doNotPopCashDrawer), fListener, 11);
        add(editPane, "Verify via EDC:", dataUI.createCheckBox(PaymentType_.verifyViaEdc), fListener, 12);
        add(editPane, "Printer Validation:", dataUI.createCheckBox(PaymentType_.printerValidation), fListener, 13);
        add(editPane, "Signature Required:", dataUI.createCheckBox(PaymentType_.signatureRequired), fListener, 14);

        editPane.add(lblWarning, 0, 15, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(PaymentType_.code).getText().trim().isEmpty());
        }
    }
}

package com.salesliant.ui;

import com.salesliant.client.ClientView;
import com.salesliant.client.Config;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.EmployeeGroup_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class EmployeeGroupListUI extends BaseListUI<EmployeeGroup> {

    private final BaseDao<EmployeeGroup> daoEmployeeGroup = new BaseDao<>(EmployeeGroup.class);
    private final DataUI dataUI = new DataUI(EmployeeGroup.class);
    private ChoiceBox<String> fModuleChoiceBox;
    private final GridPane fEditPane;
    private final TabPane fTabPane = new TabPane();
    private static final Logger LOGGER = Logger.getLogger(EmployeeGroupListUI.class.getName());

    public EmployeeGroupListUI() {
        mainView = createMainView();
        List<EmployeeGroup> list = daoEmployeeGroup.read(EmployeeGroup_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new EmployeeGroup();
                fEntity.setStore(Config.getStore());
                fEntity.setFloorLimit(BigDecimal.ZERO);
                fEntity.setReturnLimit(BigDecimal.ZERO);
                fEntity.setSalesRep(Boolean.TRUE);
                fEntity.setOpenCloseDrawer(Boolean.TRUE);
                fEntity.setOverridePrices(Boolean.FALSE);
                fEntity.setBelowMinimumPrices(Boolean.FALSE);
                fEntity.setAcceptReturns(Boolean.TRUE);
                fEntity.setAllowPayInOut(Boolean.TRUE);
                fEntity.setAllowNoSale(Boolean.TRUE);
                fEntity.setVoidInvoice(Boolean.TRUE);
                fEntity.setVoidOrder(Boolean.TRUE);
                fEntity.setVoidServiceOrder(Boolean.TRUE);
                fEntity.setVoidQuote(Boolean.TRUE);
                fEntity.setVoidInternetOrder(Boolean.TRUE);
                fEntity.setEditInvoice(Boolean.TRUE);
                fEntity.setUnlockRegister(Boolean.FALSE);
                fEntity.setChangeCustomer(Boolean.TRUE);
                fEntity.setOverrideTax(Boolean.TRUE);
                fEntity.setOverrideCreditLimit(Boolean.FALSE);
                fEntity.setOverrideCommission(Boolean.TRUE);
                fEntity.setValidDrawer(Boolean.FALSE);
                fEntity.setAllowOpenClose(Boolean.FALSE);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Employee Group");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoEmployeeGroup.insert(fEntity);
                        if (daoEmployeeGroup.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoEmployeeGroup.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    dataUI.getTextField(EmployeeGroup_.code).requestFocus();
                    fTabPane.getSelectionModel().selectFirst();
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Employee Group");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoEmployeeGroup.update(fEntity);
                            if (daoEmployeeGroup.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoEmployeeGroup.getErrorMessage());
                                fTableView.refresh();
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        dataUI.getTextField(EmployeeGroup_.code).requestFocus();
                        fTabPane.getSelectionModel().selectFirst();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoEmployeeGroup.delete(fEntity);
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
        Label tableLbl = new Label("List of Employee Group:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<EmployeeGroup, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(EmployeeGroup_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(150);

        TableColumn<EmployeeGroup, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(EmployeeGroup_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER));
        descriptionCol.setPrefWidth(250);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(descriptionCol);
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
        fModuleChoiceBox = ClientView.fLoader.findChoiceBox();
        String setup = "";
        for (String s : fModuleChoiceBox.getItems()) {
            if (s.startsWith("Setup")) {
                setup = s;
            }
        }
        fModuleChoiceBox.getItems().remove(setup);
//        fModuleChoiceBox.setPrefWidth(400);
        dataUI.setUIComponent(EmployeeGroup_.moduleName, fModuleChoiceBox);
        add(editPane, "Code:*", dataUI.createTextField(EmployeeGroup_.code), fListener, 0);
        add(editPane, "Description:*", dataUI.createTextField(EmployeeGroup_.description), fListener, 1);
        add(editPane, "Module Access:*", fModuleChoiceBox, fListener, 2);
        add(editPane, "Fail Logon Attempts:", dataUI.createTextField(EmployeeGroup_.failLogonAttempts), fListener, 3);
        add(editPane, "Floor Limit:", dataUI.createTextField(EmployeeGroup_.floorLimit), fListener, 4);
        add(editPane, "Return Limit:", dataUI.createTextField(EmployeeGroup_.returnLimit), fListener, 5);
        add(editPane, "Cash Draw Number:", dataUI.createTextField(EmployeeGroup_.cashDrawerNumber), fListener, 6);
        BorderPane bp = new BorderPane();
        bp.setCenter(createFunctionAccessPane());
        editPane.add(bp, 0, 7, 2, 1);
        editPane.add(lblWarning, 0, 8, 2, 1);

        return editPane;
    }

    private Node createFunctionAccessPane() {
        GridPane topLeftPane = new GridPane();
        topLeftPane.getStyleClass().add("editView");
        topLeftPane.setPadding(new Insets(3));
        topLeftPane.setHgap(5);
        topLeftPane.setVgap(5);
        topLeftPane.setAlignment(Pos.CENTER_RIGHT);
        add(topLeftPane, "Is Sales Rep:", dataUI.createCheckBox(EmployeeGroup_.salesRep), fListener, 1);
        add(topLeftPane, "Allow No Sale:", dataUI.createCheckBox(EmployeeGroup_.allowNoSale), fListener, 2);
        add(topLeftPane, "Accept Returns:", dataUI.createCheckBox(EmployeeGroup_.acceptReturns), fListener, 3);
        add(topLeftPane, "Allow Change Customer:", dataUI.createCheckBox(EmployeeGroup_.changeCustomer), fListener, 4);
        add(topLeftPane, "Allow Edit Invoice:", dataUI.createCheckBox(EmployeeGroup_.editInvoice), fListener, 5);

        GridPane topRightPane = new GridPane();
        topRightPane.getStyleClass().add("editView");
        topRightPane.setPadding(new Insets(3));
        topRightPane.setHgap(5);
        topRightPane.setVgap(5);
        topRightPane.setAlignment(Pos.CENTER_RIGHT);
        add(topRightPane, "Allow Daily Open and Close:", dataUI.createCheckBox(EmployeeGroup_.allowOpenClose), fListener, 1);
        add(topRightPane, "Allow Pay In/Out:", dataUI.createCheckBox(EmployeeGroup_.allowPayInOut), fListener, 2);
        add(topRightPane, "Allow Open Close Drawer:", dataUI.createCheckBox(EmployeeGroup_.openCloseDrawer), fListener, 3);
        add(topRightPane, "Allow Unlock Register:", dataUI.createCheckBox(EmployeeGroup_.unlockRegister), fListener, 4);
        add(topRightPane, "Allow Valid Drawer:", dataUI.createCheckBox(EmployeeGroup_.validDrawer), fListener, 5);

        GridPane buttomLeftPane = new GridPane();
        buttomLeftPane.getStyleClass().add("editView");
        buttomLeftPane.setPadding(new Insets(3));
        buttomLeftPane.setHgap(5);
        buttomLeftPane.setVgap(5);
        buttomLeftPane.setAlignment(Pos.CENTER_RIGHT);
        add(buttomLeftPane, "Allow Override Commission:", dataUI.createCheckBox(EmployeeGroup_.overrideCommission), fListener, 0);
        add(buttomLeftPane, "Allow Override Creditlimit:", dataUI.createCheckBox(EmployeeGroup_.overrideCreditLimit), fListener, 1);
        add(buttomLeftPane, "Allow Override Price:", dataUI.createCheckBox(EmployeeGroup_.overridePrices), fListener, 2);
        add(buttomLeftPane, "Allow Override Tax:", dataUI.createCheckBox(EmployeeGroup_.overrideTax), fListener, 3);
        add(buttomLeftPane, "Allow Sell Below Minimum Price:", dataUI.createCheckBox(EmployeeGroup_.belowMinimumPrices), fListener, 4);

        GridPane buttomRightPane = new GridPane();
        buttomRightPane.getStyleClass().add("editView");
        buttomRightPane.setPadding(new Insets(3));
        buttomRightPane.setHgap(5);
        buttomRightPane.setVgap(5);
        buttomRightPane.setAlignment(Pos.CENTER_RIGHT);
        add(buttomRightPane, "Allow Void Internet Order:", dataUI.createCheckBox(EmployeeGroup_.voidInternetOrder), fListener, 0);
        add(buttomRightPane, "Allow Void Invoice:", dataUI.createCheckBox(EmployeeGroup_.voidInvoice), fListener, 1);
        add(buttomRightPane, "Allow Void Order:", dataUI.createCheckBox(EmployeeGroup_.voidOrder), fListener, 2);
        add(buttomRightPane, "Allow Void Quote:", dataUI.createCheckBox(EmployeeGroup_.voidQuote), fListener, 3);
        add(buttomRightPane, "Allow Void Service Order:", dataUI.createCheckBox(EmployeeGroup_.voidServiceOrder), fListener, 4);

        GridPane functionPane = new GridPane();
        functionPane.getStyleClass().add("editView");
        functionPane.setPadding(new Insets(3));
        functionPane.setHgap(5);
        functionPane.setVgap(5);
        functionPane.add(topLeftPane, 0, 0);
        functionPane.add(topRightPane, 1, 0);
        functionPane.add(buttomLeftPane, 0, 1);
        functionPane.add(buttomRightPane, 1, 1);

        return functionPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(EmployeeGroup_.code).getText().trim().isEmpty()
                    || dataUI.getTextField(EmployeeGroup_.description).getText().trim().isEmpty());
        }
    }
}

package com.salesliant.ui;

import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.ShippingCarrier_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
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

public class ShippingCarrierListUI extends BaseListUI<ShippingCarrier> {

    private final BaseDao<ShippingCarrier> daoShippingCarrier = new BaseDao<>(ShippingCarrier.class);
    private final DataUI dataUI = new DataUI(ShippingCarrier.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(ShippingCarrierListUI.class.getName());

    public ShippingCarrierListUI() {
        mainView = createMainView();
        List<ShippingCarrier> list = daoShippingCarrier.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new ShippingCarrier();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Shipping Carrier");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoShippingCarrier.insert(fEntity);
                        if (daoShippingCarrier.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoShippingCarrier.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(ShippingCarrier_.code).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Shipping Carrier");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoShippingCarrier.update(fEntity);
                            if (daoShippingCarrier.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoShippingCarrier.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(ShippingCarrier_.code).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoShippingCarrier.delete(fEntity);
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
        Label tableLbl = new Label("List of Shipping Carrier:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<ShippingCarrier, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(ShippingCarrier_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        codeCol.setPrefWidth(150);

        TableColumn<ShippingCarrier, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(ShippingCarrier_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(250);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(nameCol);
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
        editPane.setPrefSize(350, 5);
        add(editPane, "Code:*", dataUI.createTextField(ShippingCarrier_.code), fListener, 250.0, 0);
        add(editPane, "Name:*", dataUI.createTextField(ShippingCarrier_.name), fListener, 250.0, 1);
        add(editPane, "Web Address:", dataUI.createTextField(ShippingCarrier_.url), fListener, 250.0, 2);
        add(editPane, "Tracking Site:", dataUI.createTextField(ShippingCarrier_.trackingUrl), fListener, 250.0, 3);
        add(editPane, "Shipping Site:", dataUI.createTextField(ShippingCarrier_.shippingUrl), fListener, 250.0, 4);
        editPane.add(lblWarning, 0, 5, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(ShippingCarrier_.code).getText().trim().isEmpty()
                    || dataUI.getTextField(ShippingCarrier_.name).getText().trim().isEmpty());
        }
    }
}

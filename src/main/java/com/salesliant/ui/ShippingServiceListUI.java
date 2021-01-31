/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.ui;

import com.salesliant.entity.ShippingCarrier;
import com.salesliant.entity.ShippingService;
import com.salesliant.entity.ShippingService_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.ShippingCarrierWidget;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lewis
 */
public class ShippingServiceListUI extends BaseListUI<ShippingService> {

    private final BaseDao<ShippingService> daoShippingService = new BaseDao<>(ShippingService.class);
    private final DataUI dataUI = new DataUI(ShippingService.class);
    private final GridPane fEditPane;
    private ComboBox fShippingCarrierComboBox;
    private static final Logger LOGGER = Logger.getLogger(ShippingServiceListUI.class.getName());

    public ShippingServiceListUI() {
        mainView = createMainView();
        List<ShippingService> list = daoShippingService.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new ShippingService();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Shipping Service");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setShippingCarrier(
                                (ShippingCarrier) fShippingCarrierComboBox.getSelectionModel().getSelectedItem());
                        daoShippingService.insert(fEntity);
                        if (daoShippingService.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoShippingService.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(ShippingService_.code).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                        fShippingCarrierComboBox.getSelectionModel().select(fEntity.getShippingCarrier());
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Shipping Service");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            fEntity.setShippingCarrier(
                                    (ShippingCarrier) fShippingCarrierComboBox.getSelectionModel().getSelectedItem());
                            daoShippingService.update(fEntity);
                            if (daoShippingService.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoShippingService.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(ShippingService_.code).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoShippingService.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
        }
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(ShippingService_.code).getText().trim().isEmpty());
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label ShippingServiceLbl = new Label("List of Shipping Services:");
        mainPane.add(ShippingServiceLbl, 0, 1);
        GridPane.setHalignment(ShippingServiceLbl, HPos.LEFT);

        TableColumn<ShippingService, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(ShippingService_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(150);

        TableColumn<ShippingService, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(ShippingService_.description.getName()));
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
        editPane.getStyleClass().add("editView");
        editPane.setPadding(new Insets(2));
        editPane.setHgap(5);
        editPane.setVgap(1);
        Label ShippingServiceLbl = new Label("Shipping Service:");
        editPane.add(ShippingServiceLbl, 0, 2);
        GridPane.setHalignment(ShippingServiceLbl, HPos.LEFT);
        fShippingCarrierComboBox = new ShippingCarrierWidget();
        fShippingCarrierComboBox.setPrefWidth(250);
        add(editPane, "Code:", dataUI.createTextField(ShippingService_.code, 250), fListener, 3);
        add(editPane, "Description:", dataUI.createTextField(ShippingService_.description, 250), fListener, 4);
        add(editPane, "Carrier:", fShippingCarrierComboBox, fListener, 5);
        add(editPane, dataUI.createCheckBox(ShippingService_.chargeByWeight), "Charge by weight", fListener, 6);
        add(editPane, dataUI.createCheckBox(ShippingService_.interpolate), "Interpolate", fListener, 7);
        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");
        leftPane.setPadding(new Insets(1));
        leftPane.setHgap(5);
        leftPane.setVgap(3);
        add(leftPane, "Value 1:", dataUI.createTextField(ShippingService_.value1, 125), fListener, 1);
        add(leftPane, "Charge 1:", dataUI.createTextField(ShippingService_.charge1, 125), fListener, 2);
        add(leftPane, "Value 2:", dataUI.createTextField(ShippingService_.value2, 125), fListener, 3);
        add(leftPane, "Charge 2:", dataUI.createTextField(ShippingService_.charge2, 125), fListener, 4);
        add(leftPane, "Value 3:", dataUI.createTextField(ShippingService_.value3, 125), fListener, 5);
        add(leftPane, "Charge 3:", dataUI.createTextField(ShippingService_.charge3, 125), fListener, 6);
        add(leftPane, "Value 4:", dataUI.createTextField(ShippingService_.value4, 125), fListener, 7);
        add(leftPane, "Charge 4:", dataUI.createTextField(ShippingService_.charge4, 125), fListener, 8);
        add(leftPane, "Value 5:", dataUI.createTextField(ShippingService_.value5, 125), fListener, 9);
        add(leftPane, "Charge 5:", dataUI.createTextField(ShippingService_.charge5, 125), fListener, 10);
        add(leftPane, "Value 6:", dataUI.createTextField(ShippingService_.value6, 125), fListener, 11);
        add(leftPane, "Charge 6:", dataUI.createTextField(ShippingService_.charge6, 125), fListener, 12);
        add(leftPane, "Value 7:", dataUI.createTextField(ShippingService_.value7, 125), fListener, 13);
        add(leftPane, "Charge 7:", dataUI.createTextField(ShippingService_.charge7, 125), fListener, 14);

        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");
        rightPane.setPadding(new Insets(2));
        rightPane.setHgap(5);
        rightPane.setVgap(1);
        add(rightPane, "Value 8:", dataUI.createTextField(ShippingService_.value1, 125), fListener, 1);
        add(rightPane, "Charge 8:", dataUI.createTextField(ShippingService_.charge1, 125), fListener, 2);
        add(rightPane, "Value 9:", dataUI.createTextField(ShippingService_.value2, 125), fListener, 3);
        add(rightPane, "Charge 9:", dataUI.createTextField(ShippingService_.charge2, 125), fListener, 4);
        add(rightPane, "Value 10:", dataUI.createTextField(ShippingService_.value3, 125), fListener, 5);
        add(rightPane, "Charge 10:", dataUI.createTextField(ShippingService_.charge3, 125), fListener, 6);
        add(rightPane, "Value 11:", dataUI.createTextField(ShippingService_.value4, 125), fListener, 7);
        add(rightPane, "Charge 11:", dataUI.createTextField(ShippingService_.charge4, 125), fListener, 8);
        add(rightPane, "Value 12:", dataUI.createTextField(ShippingService_.value5, 125), fListener, 9);
        add(rightPane, "Charge 12:", dataUI.createTextField(ShippingService_.charge5, 125), fListener, 10);
        add(rightPane, "Value 13:", dataUI.createTextField(ShippingService_.value6, 125), fListener, 11);
        add(rightPane, "Charge 13:", dataUI.createTextField(ShippingService_.charge6, 125), fListener, 12);
        add(rightPane, "Value 14:", dataUI.createTextField(ShippingService_.value7, 125), fListener, 13);
        add(rightPane, "Charge 14:", dataUI.createTextField(ShippingService_.charge7, 125), fListener, 14);
        add(rightPane, "Value 15:", dataUI.createTextField(ShippingService_.value7, 125), fListener, 15);
        add(rightPane, "Charge 15:", dataUI.createTextField(ShippingService_.charge7, 125), fListener, 16);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(leftPane, rightPane);
        hbox.setSpacing(3);

        GridPane editBox = new GridPane();
        editBox.setVgap(3.0);
        editBox.add(editPane, 0, 0);
        editBox.add(hbox, 0, 1);
        return editBox;

    }
}

package com.salesliant.ui;

import com.salesliant.entity.TaxZone;
import com.salesliant.entity.TaxZone_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.ZoneWidget;
import java.util.Date;
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

public class TaxZoneListUI extends BaseListUI<TaxZone> {

    private final BaseDao<TaxZone> daoTaxZone = new BaseDao<>(TaxZone.class);
    private final DataUI dataUI = new DataUI(TaxZone.class);
    private final GridPane fEditPane;
    private ComboBox fZoneCombo;
    private static final Logger LOGGER = Logger.getLogger(TaxZoneListUI.class.getName());

    public TaxZoneListUI() {
        mainView = createMainView();
        List<TaxZone> list = daoTaxZone.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new TaxZone();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Tax Zone");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setDateAdded(new Date());
                        daoTaxZone.insert(fEntity);
                        if (daoTaxZone.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoTaxZone.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(TaxZone_.name).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Tax Zone");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            fEntity = daoTaxZone.update(fEntity);
                            if (daoTaxZone.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoTaxZone.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(TaxZone_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoTaxZone.delete(fEntity);
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
        Label TaxZoneLbl = new Label("List of Tax Zone:");
        mainPane.add(TaxZoneLbl, 0, 1);
        GridPane.setHalignment(TaxZoneLbl, HPos.LEFT);

        TableColumn<TaxZone, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(TaxZone_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER));
        nameCol.setPrefWidth(150);

        TableColumn<TaxZone, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(TaxZone_.description.getName()));
        descriptionCol.setPrefWidth(250);

        fTableView.getColumns().add(nameCol);
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

        fZoneCombo = new ZoneWidget();
        fZoneCombo.setPrefWidth(350);
        dataUI.setUIComponent(TaxZone_.zone, fZoneCombo);
        add(editPane, "Name:*", dataUI.createTextField(TaxZone_.name), fListener, 250.0, 0);
        add(editPane, "Description:*", dataUI.createTextField(TaxZone_.description), fListener, 250.0, 1);
        add(editPane, "Zone:", fZoneCombo, fListener, 2);

        editPane.add(lblWarning, 0, 3, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(TaxZone_.name).getText().trim().isEmpty() || dataUI.getTextField(TaxZone_.name).getText().trim().isEmpty() || fZoneCombo.getSelectionModel().getSelectedItem() == null);
        }
    }
}

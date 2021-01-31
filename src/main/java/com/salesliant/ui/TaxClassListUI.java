package com.salesliant.ui;

import com.salesliant.entity.TaxClass;
import com.salesliant.entity.TaxClass_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
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
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class TaxClassListUI extends BaseListUI<TaxClass> {

    private final BaseDao<TaxClass> daoTaxClass = new BaseDao<>(TaxClass.class);
    private final DataUI dataUI = new DataUI(TaxClass.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(TaxClassListUI.class.getName());

    public TaxClassListUI() {
        mainView = createMainView();
        List<TaxClass> list = daoTaxClass.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        dialogTitle = "Tax Class";
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new TaxClass();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, dialogTitle);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setDateAdded(new Date());
                        daoTaxClass.insert(fEntity);
                        if (daoTaxClass.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoTaxClass.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(TaxClass_.name).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, dialogTitle);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoTaxClass.update(fEntity);
                            if (daoTaxClass.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoTaxClass.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(TaxClass_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoTaxClass.delete(fEntity);
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
        Label TaxClassLbl = new Label("List of TaxClass:");
        mainPane.add(TaxClassLbl, 0, 1);
        GridPane.setHalignment(TaxClassLbl, HPos.LEFT);

        TableColumn<TaxClass, String> titleCol = new TableColumn<>("Title");
        titleCol.setCellValueFactory(new PropertyValueFactory<>(TaxClass_.name.getName()));
        titleCol.setCellFactory(stringCell(Pos.CENTER));
        titleCol.setPrefWidth(150);

        TableColumn<TaxClass, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(TaxClass_.description.getName()));
        descriptionCol.setPrefWidth(450);

        fTableView.getColumns().add(titleCol);
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

        add(editPane, "Title:*", dataUI.createTextField(TaxClass_.name), fListener, 250.0, 0);
        add(editPane, "Description:*", dataUI.createTextField(TaxClass_.description), fListener, 250.0, 1);

        editPane.add(lblWarning, 0, 2, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(TaxClass_.name).getText().trim().isEmpty()
                    || dataUI.getTextField(TaxClass_.description).getText().trim().isEmpty());
        }
    }
}

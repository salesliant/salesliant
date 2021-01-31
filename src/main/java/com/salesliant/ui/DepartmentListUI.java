package com.salesliant.ui;

import com.salesliant.entity.Department;
import com.salesliant.entity.Department_;
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

public class DepartmentListUI extends BaseListUI<Department> {

    private final BaseDao<Department> daoDepartment = new BaseDao<>(Department.class);
    private final DataUI dataUI = new DataUI(Department.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(DepartmentListUI.class.getName());

    public DepartmentListUI() {
        mainView = createMainView();
        List<Department> list = daoDepartment.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Department();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Department");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoDepartment.insert(fEntity);
                        if (daoDepartment.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoDepartment.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(Department_.code).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Department");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoDepartment.update(fEntity);
                            if (daoDepartment.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoDepartment.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(Department_.code).requestFocus());
                    fInputDialog.showAndWait();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoDepartment.delete(fEntity);
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
        Label tableLbl = new Label("List of Department:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Department, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(Department_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        codeCol.setPrefWidth(150);

        TableColumn<Department, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Department_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(300);

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
        editPane.setPrefSize(350, 5);

        add(editPane, "Code:*", dataUI.createTextField(Department_.code), fListener, 250.0, 0);
        add(editPane, "Description:*", dataUI.createTextField(Department_.description), fListener, 250.0, 1);
        editPane.add(lblWarning, 0, 2, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(Department_.code).getText().trim().isEmpty()
                    || dataUI.getTextField(Department_.description).getText().trim().isEmpty());
        }
    }
}

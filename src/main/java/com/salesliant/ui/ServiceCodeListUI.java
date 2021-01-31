package com.salesliant.ui;

import com.salesliant.entity.ServiceCode;
import com.salesliant.entity.ServiceCode_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import com.salesliant.util.IconFactory;
import com.salesliant.util.RES;
import java.util.Collections;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ServiceCodeListUI extends BaseListUI<ServiceCode> {

    private final BaseDao<ServiceCode> daoServiceCode = new BaseDao<>(ServiceCode.class);
    private final DataUI dataUI = new DataUI(ServiceCode.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(ServiceCodeListUI.class.getName());

    public ServiceCodeListUI() {
        mainView = createMainView();
        List<ServiceCode> list = daoServiceCode.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new ServiceCode();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Service Code");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoServiceCode.insert(fEntity);
                        if (daoServiceCode.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.refresh();
                            for (int i = 0; i < fTableView.getItems().size(); i++) {
                                ServiceCode sc = fTableView.getItems().get(i);
                                sc.setDisplayOrder(i);
                                daoServiceCode.update(sc);
                            }
                            fTableView.scrollTo(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoServiceCode.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(ServiceCode_.code).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Service Code");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoServiceCode.update(fEntity);
                            if (daoServiceCode.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoServiceCode.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(ServiceCode_.code).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoServiceCode.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_UP:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1) {
                        Collections.swap(fTableView.getItems(), i, i - 1);
                        fTableView.scrollTo(i - 1);
                        fTableView.getSelectionModel().select(i - 1);
                    }
                    updateDisplayOrder();
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_DOWN:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i < fTableView.getItems().size() - 1) {
                        Collections.swap(fTableView.getItems(), i, i + 1);
                        fTableView.scrollTo(i + 1);
                        fTableView.getSelectionModel().select(i + 1);
                    }
                    updateDisplayOrder();
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label ServiceCodeLbl = new Label("List of Service Code:");
        mainPane.add(ServiceCodeLbl, 0, 1);
        GridPane.setHalignment(ServiceCodeLbl, HPos.LEFT);

        TableColumn<ServiceCode, String> nameCol = new TableColumn<>("Code");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(ServiceCode_.code.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER));
        nameCol.setPrefWidth(150);

        TableColumn<ServiceCode, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(ServiceCode_.description.getName()));
        descriptionCol.setPrefWidth(250);

        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createMoveNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    protected HBox createMoveNewEditDeleteCloseButtonPane() {
        HBox leftButtonBox = new HBox();
        Button upButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_UP_ICON), AppConstants.ACTION_MOVE_ROW_UP, fHandler);
        Button downButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_DOWN_ICON), AppConstants.ACTION_MOVE_ROW_DOWN, fHandler);
        leftButtonBox.getChildren().addAll(upButton, downButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        rightButtonBox.getChildren().addAll(newButton, editButton, deleteButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);
        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Code:*", dataUI.createTextField(ServiceCode_.code), fListener, 250.0, 0);
        add(editPane, "Description:*", dataUI.createTextField(ServiceCode_.description), fListener, 250.0, 1);

        editPane.add(lblWarning, 0, 2, 2, 1);

        return editPane;
    }

    private void updateDisplayOrder() {
        if (!fTableView.getItems().isEmpty()) {
            for (int i = 0; i < fTableView.getItems().size(); i++) {
                ServiceCode sc = fTableView.getItems().get(i);
                sc.setDisplayOrder(i);
                daoServiceCode.update(sc);
            }
        }
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(ServiceCode_.code).getText().trim().isEmpty());
        }
    }
}

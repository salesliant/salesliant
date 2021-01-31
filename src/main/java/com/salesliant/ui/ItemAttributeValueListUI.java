package com.salesliant.ui;

import com.salesliant.entity.ItemAttributeType;
import com.salesliant.entity.ItemAttributeValue;
import com.salesliant.entity.ItemAttributeValue_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.ItemAttributeTypeWidget;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

public class ItemAttributeValueListUI extends BaseListUI<ItemAttributeValue> {

    private final BaseDao<ItemAttributeValue> daoItemAttributeValue = new BaseDao<>(ItemAttributeValue.class);
    private final DataUI dataUI = new DataUI(ItemAttributeValue.class);
    private final GridPane fEditPane;
    private ComboBox<ItemAttributeType> fAttributeTypeCombo;
    private static final Logger LOGGER = Logger.getLogger(ItemAttributeValueListUI.class.getName());

    public ItemAttributeValueListUI() {
        mainView = createMainView();
        List<ItemAttributeValue> list = daoItemAttributeValue.read()
                .stream()
                .sorted((e1, e2) -> {
                    if (e1.getItemAttributeType().getDescription().compareTo(e2.getItemAttributeType().getDescription()) == 0) {
                        return e1.getDescription().compareTo(e2.getDescription());
                    } else {
                        return e1.getItemAttributeType().getDescription().compareTo(e2.getItemAttributeType().getDescription());
                    }
                })
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new ItemAttributeValue();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Attribute Value");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoItemAttributeValue.insert(fEntity);
                        if (daoItemAttributeValue.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoItemAttributeValue.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getUIComponent(ItemAttributeValue_.itemAttributeType).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Attribute Value");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoItemAttributeValue.update(fEntity);
                            if (daoItemAttributeValue.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoItemAttributeValue.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getUIComponent(ItemAttributeValue_.itemAttributeType).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoItemAttributeValue.delete(fEntity);
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
        Label ItemAttributeValueLbl = new Label("List of Item Attribute Value:");
        mainPane.add(ItemAttributeValueLbl, 0, 1);
        GridPane.setHalignment(ItemAttributeValueLbl, HPos.LEFT);

        TableColumn<ItemAttributeValue, String> optionalCol = new TableColumn<>("Item Attribute Type");
        optionalCol.setCellValueFactory((CellDataFeatures<ItemAttributeValue, String> p) -> {
            if (p.getValue().getItemAttributeType() != null) {
                return new SimpleStringProperty(p.getValue().getItemAttributeType().getDescription());
            } else {
                return null;
            }
        });
        optionalCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        optionalCol.setEditable(false);
        optionalCol.setSortable(false);
        optionalCol.setPrefWidth(175);

        TableColumn<ItemAttributeValue, String> descriptionCol = new TableColumn<>("Item Attribute Value Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(ItemAttributeValue_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(350);

        fTableView.getColumns().add(optionalCol);
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

        fAttributeTypeCombo = new ItemAttributeTypeWidget();
        fAttributeTypeCombo.setPrefWidth(250);
        dataUI.setUIComponent(ItemAttributeValue_.itemAttributeType, fAttributeTypeCombo);

        add(editPane, "Item Attribute Type*:", fAttributeTypeCombo, fListener, 0);
        add(editPane, "Item Attribute Value Description:*", dataUI.createTextField(ItemAttributeValue_.description), fListener, 250.0, 1);
        editPane.add(lblWarning, 0, 2, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(ItemAttributeValue_.description).getText().trim().isEmpty());
        }
    }
}

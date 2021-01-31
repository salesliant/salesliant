package com.salesliant.ui;

import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.ItemPriceLevel_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

public class ItemPriceLevelListUI extends BaseListUI<ItemPriceLevel> {

    private final BaseDao<ItemPriceLevel> daoItemPriceLevel = new BaseDao<>(ItemPriceLevel.class);
    private final DataUI dataUI = new DataUI(ItemPriceLevel.class);
    private List<ItemPriceLevel> fList = new ArrayList<>();
    private List<ItemPriceLevel> fNoEmptyList = new ArrayList<>();
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(ItemPriceLevelListUI.class.getName());

    public ItemPriceLevelListUI() {
        mainView = createMainView();
        fEditPane = createEditPane();
        loadData();
    }

    private void loadData() {
        fList = daoItemPriceLevel.read();
        fNoEmptyList = fList.stream()
                .sorted((e1, e2) -> Integer.compare(e1.getId(), e2.getId()))
                .filter(e -> !getString(e.getDescription()).trim().isEmpty())
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(fNoEmptyList);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (fList.size() > fNoEmptyList.size()) {
                    fEntity = fList.get(fNoEmptyList.size());
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Item Price Level");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoItemPriceLevel.update(fEntity);
                            if (daoItemPriceLevel.getErrorMessage() == null) {
                                loadData();
                            } else {
                                lblWarning.setText(daoItemPriceLevel.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(ItemPriceLevel_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Item Price Level");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoItemPriceLevel.update(fEntity);
                            if (daoItemPriceLevel.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoItemPriceLevel.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(ItemPriceLevel_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedIndex() == (fNoEmptyList.size() - 1)) {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            fEntity = fTableView.getSelectionModel().getSelectedItem();
                            fEntity.setDescription(null);
                            daoItemPriceLevel.update(fEntity);
                            loadData();
                        });
                    } else {
                        showAlertDialog("You can only delete the last row!");
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Item Price Levels:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<ItemPriceLevel, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(ItemPriceLevel_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        codeCol.setPrefWidth(150);

        TableColumn<ItemPriceLevel, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(ItemPriceLevel_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
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
        editPane.setPrefSize(420, 5);

        add(editPane, "Description:* ", dataUI.createTextField(ItemPriceLevel_.description), fListener, 250.0, 0);
        editPane.add(lblWarning, 0, 3, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(ItemPriceLevel_.description).getText().trim().isEmpty());
        }
    }
}

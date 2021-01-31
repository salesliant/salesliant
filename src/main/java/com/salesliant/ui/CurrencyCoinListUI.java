package com.salesliant.ui;

import com.salesliant.entity.CurrencyCoin;
import com.salesliant.entity.CurrencyCoin_;
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

public class CurrencyCoinListUI extends BaseListUI<CurrencyCoin> {

    private final BaseDao<CurrencyCoin> daoCurrencyCoin = new BaseDao<>(CurrencyCoin.class);
    private final DataUI dataUI = new DataUI(CurrencyCoin.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(CurrencyCoinListUI.class.getName());

    public CurrencyCoinListUI() {
        mainView = createMainView();
        List<CurrencyCoin> list = daoCurrencyCoin.read();
        list.stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()));
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new CurrencyCoin();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Currency Coin");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoCurrencyCoin.insert(fEntity);
                        if (daoCurrencyCoin.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoCurrencyCoin.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(CurrencyCoin_.name).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Currency Coin");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCurrencyCoin.update(fEntity);
                            if (daoCurrencyCoin.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.requestFocus();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoCurrencyCoin.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(CurrencyCoin_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCurrencyCoin.delete(fEntity);
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
        Label tableLbl = new Label("List of Currency Coin:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<CurrencyCoin, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(CurrencyCoin_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER));
        nameCol.setPrefWidth(120);

        TableColumn<CurrencyCoin, String> displayOrderCol = new TableColumn<>("Display order(Large to Small)");
        displayOrderCol.setCellValueFactory(new PropertyValueFactory<>(CurrencyCoin_.displayOrder.getName()));
        displayOrderCol.setCellFactory(stringCell(Pos.CENTER));
        displayOrderCol.setPrefWidth(200);

        TableColumn<CurrencyCoin, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<>(CurrencyCoin_.value.getName()));
        valueCol.setCellFactory(stringCell(Pos.CENTER));
        valueCol.setPrefWidth(100);

        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(displayOrderCol);
        fTableView.getColumns().add(valueCol);
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

        add(editPane, "Name:*", dataUI.createTextField(CurrencyCoin_.name), fListener, 150.0, 0);
        add(editPane, "Display Order:*", dataUI.createTextField(CurrencyCoin_.displayOrder), fListener, 80.0, 1);
        add(editPane, "Value:*", dataUI.createTextField(CurrencyCoin_.value), fListener, 80.0, 2);
        editPane.add(lblWarning, 0, 2, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(CurrencyCoin_.name).getText().trim().isEmpty() || dataUI.getTextField(CurrencyCoin_.displayOrder).getText().trim().isEmpty() || dataUI.getTextField(CurrencyCoin_.value).getText().trim().isEmpty());
        }
    }
}

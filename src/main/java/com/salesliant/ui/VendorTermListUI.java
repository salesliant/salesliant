package com.salesliant.ui;

import com.salesliant.entity.VendorTerm;
import com.salesliant.entity.VendorTerm_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.percentCell;
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

public class VendorTermListUI extends BaseListUI<VendorTerm> {

    private final BaseDao<VendorTerm> daoVendorTerm = new BaseDao<>(VendorTerm.class);
    private final DataUI dataUI = new DataUI(VendorTerm.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(VendorTermListUI.class.getName());

    public VendorTermListUI() {
        mainView = createMainView();
        List<VendorTerm> list = daoVendorTerm.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new VendorTerm();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Vendor Term");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoVendorTerm.insert(fEntity);
                        if (daoVendorTerm.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoVendorTerm.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(VendorTerm_.code).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Vendor Term");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoVendorTerm.update(fEntity);
                            if (daoVendorTerm.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoVendorTerm.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(VendorTerm_.code).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendorTerm.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3);
        Label tableLbl = new Label("List of Vendor Term:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<VendorTerm, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(VendorTerm_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(160);

        TableColumn<VendorTerm, String> dueDaysCol = new TableColumn<>("Due Days");
        dueDaysCol.setCellValueFactory(new PropertyValueFactory<>(VendorTerm_.dueDays.getName()));
        dueDaysCol.setCellFactory(stringCell(Pos.CENTER));
        dueDaysCol.setPrefWidth(120);

        TableColumn<VendorTerm, Boolean> postToApCol = new TableColumn<>("Post To AP");
        postToApCol.setCellValueFactory(new PropertyValueFactory<>(VendorTerm_.postToAp.getName()));
        postToApCol.setCellFactory(stringCell(Pos.CENTER));
        postToApCol.setPrefWidth(150);

        TableColumn<VendorTerm, String> discountDaysCol = new TableColumn<>("Discount Days");
        discountDaysCol.setCellValueFactory(new PropertyValueFactory<>(VendorTerm_.discountDays.getName()));
        discountDaysCol.setCellFactory(stringCell(Pos.CENTER));
        discountDaysCol.setPrefWidth(120);

        TableColumn<VendorTerm, String> discountRateCol = new TableColumn<>("Discount Rate");
        discountRateCol.setCellValueFactory(new PropertyValueFactory<>(VendorTerm_.discountRate.getName()));
        discountRateCol.setCellFactory(percentCell(Pos.CENTER));
        discountRateCol.setPrefWidth(120);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(dueDaysCol);
        fTableView.getColumns().add(postToApCol);
        fTableView.getColumns().add(discountDaysCol);
        fTableView.getColumns().add(discountRateCol);
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
        add(editPane, "Code:*", dataUI.createTextField(VendorTerm_.code), fListener, 0);
        add(editPane, "Due Days:*", dataUI.createTextField(VendorTerm_.dueDays), fListener, 1);
        add(editPane, "Post To AP:*", dataUI.createCheckBox(VendorTerm_.postToAp), fListener, 2);
        add(editPane, "Discount Days:*", dataUI.createTextField(VendorTerm_.discountDays), fListener, 3);
        add(editPane, "Discount Rate:*", dataUI.createTextField(VendorTerm_.discountRate), fListener, 4);
        editPane.add(lblWarning, 0, 7, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(
                    dataUI.getTextField(VendorTerm_.code).getText().trim().isEmpty() || dataUI.getTextField(VendorTerm_.dueDays).getText().trim().isEmpty()
                    || dataUI.getTextField(VendorTerm_.discountDays).getText().trim().isEmpty()
                    || dataUI.getTextField(VendorTerm_.discountRate).getText().trim().isEmpty());
        }
    }
}

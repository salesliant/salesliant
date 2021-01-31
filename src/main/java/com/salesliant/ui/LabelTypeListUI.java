package com.salesliant.ui;

import com.salesliant.entity.LabelType;
import com.salesliant.entity.LabelType_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DBConstants;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class LabelTypeListUI extends BaseListUI<LabelType> {

    private final BaseDao<LabelType> daoLabelType = new BaseDao<>(LabelType.class);
    private final DataUI dataUI = new DataUI(LabelType.class);
    private final GridPane fEditPane;
    private final ComboBox fPageTypeCombo = DBConstants.getComboBoxTypes(DBConstants.TYPE_PAGE);
    private final static String LABEL_TYPE_TITLE = "Label Type";
    private static final Logger LOGGER = Logger.getLogger(LabelTypeListUI.class.getName());

    public LabelTypeListUI() {
        mainView = createMainView();
        List<LabelType> list = daoLabelType.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        dialogTitle = LABEL_TYPE_TITLE;
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new LabelType();
                fEntity.setPageType(DBConstants.TYPE_PAGE_LETTER);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, LABEL_TYPE_TITLE);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoLabelType.insert(fEntity);
                        if (daoLabelType.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoLabelType.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getUIComponent(LabelType_.name).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, LABEL_TYPE_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoLabelType.update(fEntity);
                            if (daoLabelType.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoLabelType.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getUIComponent(LabelType_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoLabelType.delete(fEntity);
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
        Label LabelTypeLbl = new Label("List of Label Type:");
        mainPane.add(LabelTypeLbl, 0, 1);
        GridPane.setHalignment(LabelTypeLbl, HPos.LEFT);

        TableColumn<LabelType, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(LabelType_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER));
        nameCol.setPrefWidth(120);

        TableColumn<LabelType, String> maxRowCol = new TableColumn<>("Row/Page");
        maxRowCol.setCellValueFactory(new PropertyValueFactory<>(LabelType_.maximumRows.getName()));
        maxRowCol.setCellFactory(stringCell(Pos.CENTER));
        maxRowCol.setPrefWidth(100);

        TableColumn<LabelType, String> maximumColumnsCol = new TableColumn<>("Columns/Page");
        maximumColumnsCol.setCellValueFactory(new PropertyValueFactory<>(LabelType_.maximumColumns.getName()));
        maximumColumnsCol.setCellFactory(stringCell(Pos.CENTER));
        maximumColumnsCol.setPrefWidth(100);

        TableColumn<LabelType, String> descLengthCol = new TableColumn<>("Desc Length");
        descLengthCol.setCellValueFactory(new PropertyValueFactory<>(LabelType_.maxLength.getName()));
        descLengthCol.setCellFactory(stringCell(Pos.CENTER));
        descLengthCol.setPrefWidth(100);

        TableColumn<LabelType, String> pageTypeCol = new TableColumn<>("Page Type");
        pageTypeCol.setCellValueFactory(new PropertyValueFactory<>(LabelType_.pageType.getName()));
        pageTypeCol.setCellFactory(stringCell(Pos.CENTER));
        pageTypeCol.setPrefWidth(100);

        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(maxRowCol);
        fTableView.getColumns().add(maximumColumnsCol);
        fTableView.getColumns().add(descLengthCol);
        fTableView.getColumns().add(pageTypeCol);

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
        dataUI.setUIComponent(LabelType_.pageType, fPageTypeCombo);

        add(editPane, "Name:*", dataUI.createTextField(LabelType_.name), fListener, 100, 0);
        add(editPane, "Rows Per Page(Integer):*", dataUI.createTextField(LabelType_.maximumRows), fListener, 100, 1);
        add(editPane, "Columns Per Page(Integer):*", dataUI.createTextField(LabelType_.maximumColumns), fListener, 100.0, 2);
        add(editPane, "Description Character Length(Integer):*", dataUI.createTextField(LabelType_.maxLength), fListener, 100.0, 3);
        add(editPane, "Label Height(Integer):*", dataUI.createTextField(LabelType_.labelHeight), fListener, 100.0, 4);
        add(editPane, "Label Width(Integer):*", dataUI.createTextField(LabelType_.labelWidth), fListener, 100.0, 5);
        add(editPane, "Top Margin(Integer):*", dataUI.createTextField(LabelType_.topMargin), fListener, 100.0, 6);
        add(editPane, "Button Margin(Integer):*", dataUI.createTextField(LabelType_.bottomMargin), fListener, 100.0, 7);
        add(editPane, "Left Margin(Integer):*", dataUI.createTextField(LabelType_.leftMargin), fListener, 100.0, 8);
        add(editPane, "Right Margin(Integer):*", dataUI.createTextField(LabelType_.rightMargin), fListener, 100.0, 9);
        add(editPane, "Horizontal Space(Integer):*", dataUI.createTextField(LabelType_.horizontalSpace), fListener, 100.0, 10);
        add(editPane, "Vertical Space(Integer):*", dataUI.createTextField(LabelType_.verticalSpace), fListener, 100.0, 11);
        add(editPane, "Barcode Height(Integer):*", dataUI.createTextField(LabelType_.barcodeHeight), fListener, 100.0, 12);
        add(editPane, "Barcode Width(Integer):*", dataUI.createTextField(LabelType_.barcodeWidth), fListener, 100.0, 13);
        add(editPane, "Page Type(Integer):", fPageTypeCombo, fListener, 14);
        editPane.add(lblWarning, 0, 15, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(LabelType_.name).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.maximumColumns).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.maximumRows).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.maxLength).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.barcodeHeight).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.barcodeWidth).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.topMargin).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.bottomMargin).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.horizontalSpace).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.verticalSpace).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.leftMargin).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.rightMargin).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.labelHeight).getText().trim().isEmpty()
                    || dataUI.getTextField(LabelType_.labelWidth).getText().trim().isEmpty());
        }
    }
}

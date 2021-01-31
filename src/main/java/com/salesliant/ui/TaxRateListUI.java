package com.salesliant.ui;

import com.salesliant.entity.TaxRate;
import com.salesliant.entity.TaxRate_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.BaseUtil;
import static com.salesliant.util.BaseUtil.getDecimal4Format;
import static com.salesliant.util.BaseUtil.percentCell;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.BorderedTitledPane;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class TaxRateListUI extends BaseListUI<TaxRate> {

    private final BaseDao<TaxRate> daoTaxRate = new BaseDao<>(TaxRate.class);
    private final DataUI dataUI = new DataUI(TaxRate.class);
    private final GridPane fEditPane;
    private final CheckBox fUseFixedAmount = new CheckBox();
    private static final Logger LOGGER = Logger.getLogger(TaxRateListUI.class.getName());

    public TaxRateListUI() {
        mainView = createMainView();
        List<TaxRate> list = daoTaxRate.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new TaxRate();
                fEntity.setItemMinimum(BigDecimal.ZERO);
                fEntity.setItemMaximum(BigDecimal.ZERO);
                fEntity.setFixedAmount(BigDecimal.ZERO);
                fEntity.setBracket00(BigDecimal.ZERO);
                fEntity.setBracket01(BigDecimal.ZERO);
                fEntity.setBracket02(BigDecimal.ZERO);
                fEntity.setBracket03(BigDecimal.ZERO);
                fEntity.setBracket04(BigDecimal.ZERO);
                fEntity.setBracket05(BigDecimal.ZERO);
                fEntity.setBracket06(BigDecimal.ZERO);
                fEntity.setBracket07(BigDecimal.ZERO);
                fEntity.setBracket08(BigDecimal.ZERO);
                fEntity.setBracket09(BigDecimal.ZERO);
                fEntity.setBracket10(BigDecimal.ZERO);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Tax");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoTaxRate.insert(fEntity);
                        if (daoTaxRate.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoTaxRate.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(TaxRate_.name).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if ((fEntity.getFixedAmount() != null && fEntity.getFixedAmount().doubleValue() > 0)
                            || (fEntity.getItemMinimum() != null && fEntity.getItemMinimum().doubleValue() > 0)) {
                        fUseFixedAmount.setSelected(true);
                    } else {
                        fUseFixedAmount.setSelected(false);
                    }
                    try {
                        dataUI.setData(fEntity);
                        dataUI.getTextField(TaxRate_.rate).setText(getDecimal4Format().format(zeroIfNull(fEntity.getRate())));
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Tax");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoTaxRate.update(fEntity);
                            if (daoTaxRate.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoTaxRate.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(TaxRate_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoTaxRate.delete(fEntity);
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
        Label tableLbl = new Label("List of Tax Code");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<TaxRate, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(TaxRate_.name.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(150);

        TableColumn<TaxRate, String> rateCol = new TableColumn<>("Tax Rate");
        rateCol.setCellValueFactory(new PropertyValueFactory<>(TaxRate_.rate.getName()));
        rateCol.setCellFactory(percentCell(Pos.CENTER));
        rateCol.setPrefWidth(150);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(rateCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.setHgap(15);
        editPane.setVgap(5);
        editPane.setPrefSize(495, 80);
        editPane.add(createTaxRateBox(), 0, 1);
        editPane.add(createOptionBox(), 1, 1);
        editPane.add(createTaxBracketBox(), 0, 2, 2, 1);
        editPane.add(lblWarning, 0, 3, 2, 1);

        return editPane;
    }

    /**
     * createTaxRateBox @return GridPane
     */
    private GridPane createTaxRateBox() {
        GridPane taxRateBox = new GridPane();
        add(taxRateBox, "Tax Code:* ", dataUI.createTextField(TaxRate_.name), fListener, 200.00, 0);
        add(taxRateBox, "Rate (example:0.06) :*", dataUI.createDecimal4Field(TaxRate_.rate), fListener, 1);
        add(taxRateBox, "Item Minimum : ", dataUI.createTextField(TaxRate_.itemMinimum), fListener, 2);
        add(taxRateBox, "Item Maximum : ", dataUI.createTextField(TaxRate_.itemMaximum), fListener, 3);
        add(taxRateBox, "Fixed Amount : ", dataUI.createTextField(TaxRate_.fixedAmount), fListener, 4);
        fUseFixedAmount.selectedProperty()
                .addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                    if (newValue) {
                        dataUI.getTextField(TaxRate_.itemMinimum).setText("0");
                        dataUI.getTextField(TaxRate_.fixedAmount).setText("0");
                    }
                });
        return taxRateBox;
    }

    private Node createOptionBox() {
        GridPane optionBox = new GridPane();

        add(optionBox, dataUI.createCheckBox(TaxRate_.includePreviousTax), "Include Previous Tax", fListener, 0);
        add(optionBox, dataUI.createCheckBox(TaxRate_.usePartialDollar), "Use Partial Dollar", fListener, 1);
        add(optionBox, dataUI.createCheckBox(TaxRate_.applyOverMinimum), "Apply Over Minimum", fListener, 2);
        add(optionBox, fUseFixedAmount, "Use Fixed Amount", 3);
        optionBox.setAlignment(Pos.CENTER);
        optionBox.setPadding(new Insets(10, 10, 10, 10));
        optionBox.setVgap(5);
        optionBox.getStyleClass().add("editView");
        return optionBox;
    }

    /**
     * createTaxBracketBox @return GridPane
     */
    private Node createTaxBracketBox() {
        GridPane bracketBox = new GridPane();
        Label lblTax = new Label("  Tax($)");
        Label lblBracket = new Label("Bracket");
        bracketBox.add(lblTax, 0, 0);
        bracketBox.add(lblBracket, 1, 0);
        GridPane.setHalignment(lblBracket, HPos.CENTER);
        add(bracketBox, "  0.00: ", dataUI.createTextField(TaxRate_.bracket00), fListener, 1);
        add(bracketBox, "  0.01: ", dataUI.createTextField(TaxRate_.bracket01), fListener, 2);
        add(bracketBox, "  0.02: ", dataUI.createTextField(TaxRate_.bracket02), fListener, 3);
        add(bracketBox, "  0.03: ", dataUI.createTextField(TaxRate_.bracket03), fListener, 4);
        add(bracketBox, "  0.04: ", dataUI.createTextField(TaxRate_.bracket04), fListener, 5);
        add(bracketBox, "  0.05: ", dataUI.createTextField(TaxRate_.bracket05), fListener, 6);
        add(bracketBox, "  0.06: ", dataUI.createTextField(TaxRate_.bracket06), fListener, 7);
        add(bracketBox, "  0.07: ", dataUI.createTextField(TaxRate_.bracket07), fListener, 8);
        add(bracketBox, "  0.08: ", dataUI.createTextField(TaxRate_.bracket08), fListener, 9);
        add(bracketBox, "  0.09: ", dataUI.createTextField(TaxRate_.bracket09), fListener, 10);
        add(bracketBox, "  0.10: ", dataUI.createTextField(TaxRate_.bracket10), fListener, 11);
        bracketBox.setAlignment(Pos.CENTER);
        Node borderBox = new BorderedTitledPane("Tax Bracket", bracketBox);

        return borderBox;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(TaxRate_.name).getText().trim().isEmpty()
                    || dataUI.getTextField(TaxRate_.rate).getText().trim().isEmpty());
        }
    }
}

package com.salesliant.ui;

import com.salesliant.entity.Currency;
import com.salesliant.entity.Currency_;
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

public class CurrencyListUI extends BaseListUI<Currency> {

    private final BaseDao<Currency> daoCurrency = new BaseDao<>(Currency.class);
    private final DataUI dataUI = new DataUI(Currency.class);
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(CurrencyListUI.class.getName());

    public CurrencyListUI() {
        mainView = createMainView();
        List<Currency> list = daoCurrency.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Currency();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Currency");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoCurrency.insert(fEntity);
                        if (daoCurrency.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoCurrency.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(Currency_.code).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Currency");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCurrency.update(fEntity);
                            if (daoCurrency.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoCurrency.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(Currency_.code).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCurrency.delete(fEntity);
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
        Label tableLbl = new Label("List of Currency:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Currency, String> codeCol = new TableColumn<>("Currency");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(Currency_.title.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(150);

        TableColumn<Currency, String> descriptionCol = new TableColumn<>("Code");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Currency_.code.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER));
        descriptionCol.setPrefWidth(250);

        TableColumn<Currency, String> rateCol = new TableColumn<>("Value");
        rateCol.setCellValueFactory(new PropertyValueFactory<>(Currency_.value.getName()));
        rateCol.setCellFactory(stringCell(Pos.CENTER));
        rateCol.setPrefWidth(250);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(descriptionCol);
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
        editPane.setHgap(5);
        editPane.setVgap(5);
        editPane.setPrefSize(380, 5);

        add(editPane, "Title:*", dataUI.createTextField(Currency_.title), fListener, 250, 0);
        add(editPane, "Code:*", dataUI.createTextField(Currency_.code), fListener, 250, 1);
        add(editPane, "Symbol Left:*", dataUI.createTextField(Currency_.symbolLeft), fListener, 250, 2);
        add(editPane, "Symbol Right:*", dataUI.createTextField(Currency_.symbolRight), fListener, 250, 3);
        add(editPane, "Decimal Places:*", dataUI.createTextField(Currency_.decimalPlaces), fListener, 250, 4);
        add(editPane, "Value:*", dataUI.createTextField(Currency_.value), fListener, 5);

        // dataUI.getNumberField(Currency_.value).setDecimalFormat(getNumberFormat());
        editPane.add(lblWarning, 0, 6, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(Currency_.code).getText().trim().isEmpty()
                    || dataUI.getTextField(Currency_.title).getText().trim().isEmpty()
                    || dataUI.getTextField(Currency_.value).getText().trim().isEmpty());
        }
    }
}

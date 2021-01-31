package com.salesliant.ui;

import com.salesliant.entity.Tax;
import com.salesliant.entity.Tax_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.percentCell;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.TaxClassWidget;
import com.salesliant.widget.TaxRateWidget;
import com.salesliant.widget.TaxZoneWidget;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.layout.GridPane;

public class TaxListUI extends BaseListUI<Tax> {

    private final BaseDao<Tax> daoTax = new BaseDao<>(Tax.class);
    private final DataUI dataUI = new DataUI(Tax.class);
    private final GridPane fEditPane;
    private final ComboBox fTaxZoneCombo = new TaxZoneWidget();
    private final ComboBox fTaxClassCombo = new TaxClassWidget();
    private final ComboBox fTaxRateCombo = new TaxRateWidget();
    private static final Logger LOGGER = Logger.getLogger(TaxListUI.class.getName());

    public TaxListUI() {
        mainView = createMainView();
        List<Tax> list = daoTax.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Tax();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Tax");
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoTax.insert(fEntity);
                        if (daoTax.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoTax.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> fTaxZoneCombo.requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Tax");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoTax.update(fEntity);
                            if (daoTax.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(daoTax.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> fTaxZoneCombo.requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoTax.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
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

        TableColumn<Tax, BigDecimal> taxRateCol = new TableColumn<>("Rate");
        taxRateCol.setCellValueFactory((TableColumn.CellDataFeatures<Tax, BigDecimal> p) -> {
            if (p.getValue() != null && p.getValue().getTaxRate() != null && p.getValue().getTaxRate().getRate() != null) {
                return new ReadOnlyObjectWrapper(p.getValue().getTaxRate().getRate());
            } else {
                return null;
            }
        });
        taxRateCol.setCellFactory(percentCell(Pos.CENTER_RIGHT));
        taxRateCol.setPrefWidth(150);

        TableColumn<Tax, String> rateNameCol = new TableColumn<>("Tax Rate Name");
        rateNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Tax, String> p) -> {
            if (p.getValue() != null && p.getValue().getTaxRate() != null && p.getValue().getTaxRate().getName() != null) {
                return new SimpleStringProperty(p.getValue().getTaxRate().getName());
            } else {
                return null;
            }
        });
        rateNameCol.setCellFactory(stringCell(Pos.CENTER));
        rateNameCol.setPrefWidth(150);

        TableColumn<Tax, String> taxZoneCol = new TableColumn<>("Tax Zone");
        taxZoneCol.setCellValueFactory((TableColumn.CellDataFeatures<Tax, String> p) -> {
            if (p.getValue() != null && p.getValue().getTaxZone() != null && p.getValue().getTaxZone().getName() != null) {
                return new SimpleStringProperty(p.getValue().getTaxZone().getName());
            } else {
                return null;
            }
        });
        taxZoneCol.setCellFactory(stringCell(Pos.CENTER));
        taxZoneCol.setPrefWidth(150);

        TableColumn<Tax, String> taxClassCol = new TableColumn<>("Tax Class");
        taxClassCol.setCellValueFactory((TableColumn.CellDataFeatures<Tax, String> p) -> {
            if (p.getValue() != null && p.getValue().getTaxClass() != null && p.getValue().getTaxClass().getName() != null) {
                return new SimpleStringProperty(p.getValue().getTaxClass().getName());
            } else {
                return null;
            }
        });
        taxClassCol.setCellFactory(stringCell(Pos.CENTER));
        taxClassCol.setPrefWidth(150);

        fTableView.getColumns().add(taxZoneCol);
        fTableView.getColumns().add(taxClassCol);
        fTableView.getColumns().add(rateNameCol);
        fTableView.getColumns().add(taxRateCol);
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
        fTaxRateCombo.setPrefWidth(200);
        dataUI.setUIComponent(Tax_.taxRate, fTaxRateCombo);
        fTaxClassCombo.setPrefWidth(200);
        dataUI.setUIComponent(Tax_.taxClass, fTaxClassCombo);
        fTaxZoneCombo.setPrefWidth(200);
        dataUI.setUIComponent(Tax_.taxZone, fTaxZoneCombo);

        add(editPane, "Tax Zone :*", fTaxZoneCombo, fListener, 1);
        add(editPane, "Tax Class :*", fTaxClassCombo, fListener, 2);
        add(editPane, "Tax Rate :*", fTaxRateCombo, fListener, 3);
        editPane.add(lblWarning, 0, 4, 2, 1);

        return editPane;
    }
}

package com.salesliant.ui;

import com.salesliant.entity.Zone;
import com.salesliant.entity.Zone_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.CountryWidget;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class ZoneListUI extends BaseListUI<Zone> {

    private final BaseDao<Zone> daoZone = new BaseDao<>(Zone.class);
    private final DataUI dataUI = new DataUI(Zone.class);
    private final GridPane fEditPane;
    private ComboBox fCountryCombo;
    private static final Logger LOGGER = Logger.getLogger(ZoneListUI.class.getName());

    public ZoneListUI() {
        mainView = createMainView();
        List<Zone> list = daoZone.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Zone();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Zone");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoZone.insert(fEntity);
                        if (daoZone.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoZone.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(Zone_.name).requestFocus());
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Zone");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoZone.update(fEntity);
                            if (daoZone.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoZone.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(Zone_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoZone.delete(fEntity);
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
        Label ZoneLbl = new Label("List of Zone:");
        mainPane.add(ZoneLbl, 0, 1);
        GridPane.setHalignment(ZoneLbl, HPos.LEFT);

        TableColumn<Zone, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory((CellDataFeatures<Zone, String> p) -> {
            if (p.getValue().getCountry() != null) {
                return new SimpleStringProperty(p.getValue().getCountry().getName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        countryCol.setCellFactory(stringCell(Pos.CENTER));
        countryCol.setPrefWidth(250);

        TableColumn<Zone, String> codeCol = new TableColumn<>("Code");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(Zone_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(150);

        TableColumn<Zone, String> nameCol = new TableColumn<>("Zone");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Zone_.name.getName()));
        nameCol.setPrefWidth(250);

        fTableView.getColumns().add(countryCol);
        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(codeCol);

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

        fCountryCombo = new CountryWidget();
        fCountryCombo.setPrefWidth(180);
        dataUI.setUIComponent(Zone_.country, fCountryCombo);
        add(editPane, "Zone Name:*", dataUI.createTextField(Zone_.name), fListener, 250.0, 0);
        add(editPane, "Zone Code:*", dataUI.createTextField(Zone_.code), fListener, 250.0, 1);
        add(editPane, "Country:", fCountryCombo, fListener, 2);

        editPane.add(lblWarning, 0, 3, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(Zone_.code).getText().trim().isEmpty()
                    || dataUI.getTextField(Zone_.name).getText().trim().isEmpty()
                    || fCountryCombo.getSelectionModel().getSelectedItem() == null);
        }
    }

}

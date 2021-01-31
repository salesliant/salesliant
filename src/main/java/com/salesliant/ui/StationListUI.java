package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Station;
import com.salesliant.entity.Station_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.widget.StationWidget;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StationListUI extends BaseListUI<Station> {

    private final BaseDao<Station> daoStation = new BaseDao<>(Station.class);
    private final DataUI dataUI = new DataUI(Station.class);
    private final ListView<String> fListView = new ListView<>();
    private ComboBox fStationCombo;
    private final GridPane fEditPane;
    private static final Logger LOGGER = Logger.getLogger(StationListUI.class.getName());

    public StationListUI() {
        mainView = createMainView();
        List<Station> list = daoStation.read(Station_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Station();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Station");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setStore(Config.getStore());
                        daoStation.insert(fEntity);
                        if (daoStation.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoStation.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    fListView.getSelectionModel().select(0);
                    dataUI.getTextField(Station_.number).requestFocus();
                });
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Station");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoStation.update(fEntity);
                            if (daoStation.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoStation.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        fListView.getSelectionModel().select(0);
                        dataUI.getTextField(Station_.number).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_CLONE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Response answer = createConfirmResponseDialog("Do you want to create a new station base on this station?");
                    if (answer.equals(Response.YES)) {
                        Station station = cloneStation(fEntity);
                        daoStation.insert(station);
                        if (daoStation.getErrorMessage() != null) {
                            showAlertDialog("Fail to copy the selected station");
                        } else {
                            fTableView.getItems().add(station);
                        }
                    }
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fEntityList.size() >= 1) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoStation.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                        if (Config.getStation().getId().equals(fEntity.getId())) {
                            Config.setStation(Config.getStore().getStations().get(0));
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
        Label tableLbl = new Label("List of Stations");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Station, String> stationNumberCol = new TableColumn<>("Station");
        stationNumberCol.setCellValueFactory(new PropertyValueFactory<>(Station_.number.getName()));
        stationNumberCol.setCellFactory(stringCell(Pos.CENTER));
        stationNumberCol.setPrefWidth(150);

        TableColumn<Station, String> tenderCollectNoCol = new TableColumn<>("Collect Tender At");
        tenderCollectNoCol.setCellValueFactory((TableColumn.CellDataFeatures<Station, String> p) -> {
            if (p.getValue() != null && p.getValue().getTenderedStation() != null && p.getValue().getTenderedStation().getNumber() != null) {
                return new SimpleStringProperty(p.getValue().getTenderedStation().getNumber().toString());
            } else {
                return null;
            }
        });
        tenderCollectNoCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        tenderCollectNoCol.setPrefWidth(150);

        TableColumn<Station, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Station_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        fTableView.getColumns().add(stationNumberCol);
        fTableView.getColumns().add(tenderCollectNoCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloneCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createNewEditDeleteCloneCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button cloneButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, cloneButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.setHgap(15);
        editPane.setVgap(5);
        editPane.getStyleClass().add("editView");

        GridPane generalPane = createGeneralPane();
        GridPane printerPane = createPrinterPane();
        GridPane drawPane = createCashDrawPane();
        GridPane mmPane = createMicrMsrPane();
        GridPane scalePane = createScalePane();
        GridPane scannerPane = createScannerPane();
        GridPane displayPane = createDisplayPane();
        GridPane signaturePane = createSignaturePane();

        BorderPane content = new BorderPane();
        content.setPrefWidth(450);

        ObservableList<String> settings = FXCollections.observableArrayList("General", "Receipt Printer", "Cash Draw", "MICR & MSR", "Scale", "Scanner", "Display", "Signature Capture");
        fListView.setItems(settings);
        fListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> observable, String newValue, String oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                if (observable.getValue().equalsIgnoreCase("General")) {
                    content.setCenter(generalPane);
                } else if (observable.getValue().equalsIgnoreCase("Receipt Printer")) {
                    content.setCenter(printerPane);
                } else if (observable.getValue().equalsIgnoreCase("Cash Draw")) {
                    content.setCenter(drawPane);
                } else if (observable.getValue().equalsIgnoreCase("MICR & MSR")) {
                    content.setCenter(mmPane);
                } else if (observable.getValue().equalsIgnoreCase("Scale")) {
                    content.setCenter(scalePane);
                } else if (observable.getValue().equalsIgnoreCase("Scanner")) {
                    content.setCenter(scannerPane);
                } else if (observable.getValue().equalsIgnoreCase("Signature Capture")) {
                    content.setCenter(signaturePane);
                } else if (observable.getValue().equalsIgnoreCase("Display")) {
                    content.setCenter(displayPane);
                } else {
                    content.setCenter(null);
                }
            } else {
                content.setCenter(null);
            }

        });
        fListView.setPrefHeight(190);
        fListView.setPrefWidth(120);
        fListView.setFocusTraversable(false);

        editPane.add(fListView, 0, 0);
        editPane.add(content, 1, 0);
        return editPane;
    }

    private GridPane createGeneralPane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        fStationCombo = new StationWidget();
        fStationCombo.setPrefWidth(200);
        dataUI.setUIComponent(Station_.tenderedStation, fStationCombo);
        add(gp, "Station Number:* ", dataUI.createTextField(Station_.number), fListener, 200.00, 0);
        add(gp, "Description :*", dataUI.createTextField(Station_.description), fListener, 200.00, 1);
        add(gp, "Collect Tender At Station :* ", fStationCombo, fListener, 2);
        add(gp, dataUI.createCheckBox(Station_.transactionRequireLogin), "Transaction Require Login", fListener, 3);
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createPrinterPane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        ComboBox receivePrinterCombo = DBConstants.getComboBoxTypes(DBConstants.TYPE_RECEIP_PRINTER_OPTION);
        ComboBox printerTypeCombo = DBConstants.getComboBoxTypes(DBConstants.TYPE_RECEIP_PRINTER);
        receivePrinterCombo.setPrefWidth(200);
        dataUI.setUIComponent(Station_.printer1Options, receivePrinterCombo);
        dataUI.setUIComponent(Station_.printer1Type, printerTypeCombo);
        add(gp, "Receive Printing Options: ", receivePrinterCombo, 200, fListener, 0);
        add(gp, "Printer Type: ", printerTypeCombo, 200, fListener, 1);
        add(gp, "OPOS Device Name:* ", dataUI.createTextField(Station_.printer1Name), fListener, 200.00, 2);
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createCashDrawPane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        add(gp, dataUI.createCheckBox(Station_.cashDraw1Enabled), "Cash Draw is enabled", fListener, 0);
        add(gp, "Cash Draw Name : ", dataUI.createTextField(Station_.cashDraw1Name), fListener, 200, 1);
        add(gp, dataUI.createCheckBox(Station_.cashDraw1WaitForClose), "Wait for cash draw to close", fListener, 2);
        add(gp, "Draw closing time out(seconds) : ", dataUI.createTextField(Station_.cashDraw1OpenTimeout), fListener, 200, 3);
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createMicrMsrPane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        add(gp, dataUI.createCheckBox(Station_.micrEnabled), "MICR is enabled", fListener, 0);
        add(gp, "OPOS Device Name : ", dataUI.createTextField(Station_.micrName), fListener, 200, 1);
        add(gp, "MICR OPOS time out(seconds) : ", dataUI.createTextField(Station_.micrTimeout), fListener, 200, 2);
        add(gp, dataUI.createCheckBox(Station_.msrEnabled), "Magnetic stripe reader is enabled", fListener, 3);
        add(gp, "OPOS Device Name : ", dataUI.createTextField(Station_.msrName), fListener, 200, 4);
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createScalePane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        add(gp, dataUI.createCheckBox(Station_.scaleEnabled), "Scale is enabled", fListener, 0);
        add(gp, "OPOS Device Name : ", dataUI.createTextField(Station_.scaleName), fListener, 200, 1);
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createScannerPane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        add(gp, dataUI.createCheckBox(Station_.scannerEnabled), "Scanner is enabled", fListener, 0);
        add(gp, "OPOS Device Name : ", dataUI.createTextField(Station_.scannerName), fListener, 200, 1);
        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createDisplayPane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        add(gp, dataUI.createCheckBox(Station_.poleDisplayEnabled), "Pole display is enabled", fListener, 0);
        add(gp, "Pole OPOS Device Name : ", dataUI.createTextField(Station_.poleDisplayName), fListener, 200, 1);
        add(gp, dataUI.createCheckBox(Station_.netDisplayEnabled), "Net display is enabled", fListener, 2);

        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private GridPane createSignaturePane() {
        GridPane gp = new GridPane();
        gp.getStyleClass().add("editView");
        add(gp, dataUI.createCheckBox(Station_.signatureCaptureEnabled), "Signature capture is enabled", fListener, 0);
        add(gp, "OPOS Device Name : ", dataUI.createTextField(Station_.signatureCaptureName), fListener, 200, 1);
        add(gp, "Form Name : ", dataUI.createTextField(Station_.signatureCaptureFormName), fListener, 200, 2);

        gp.setAlignment(Pos.CENTER);
        return gp;
    }

    private Station cloneStation(Station station) {
        Station newStation = new Station();
        List<Station> list = daoStation.read();
        Integer nextNumber = list.stream().mapToInt(Station::getNumber).max().getAsInt() + 1;
        newStation.setNumber(nextNumber);
        newStation.setDescription(getString(station.getDescription()) + "-copy");
        newStation.setCashDraw1Name(station.getCashDraw1Name());
        newStation.setCashDraw1OpenTimeout(station.getCashDraw1OpenTimeout());
        newStation.setCashDraw1WaitForClose(station.getCashDraw1WaitForClose());
        newStation.setCashDraw1Enabled(station.getCashDraw1Enabled());
        newStation.setMicrEnabled(station.getMicrEnabled());
        newStation.setMicrTimeout(station.getMicrTimeout());
        newStation.setMicrName(station.getMicrName());
        newStation.setMsrEnabled(station.getMsrEnabled());
        newStation.setMsrName(station.getMsrName());
        newStation.setNetDisplayEnabled(station.getNetDisplayEnabled());
        newStation.setPoleDisplayEnabled(station.getPoleDisplayEnabled());
        newStation.setPoleDisplayMessage(station.getPoleDisplayMessage());
        newStation.setPoleDisplayName(station.getPoleDisplayName());
        newStation.setPrinter1Name(station.getPrinter1Name());
        newStation.setPrinter1Options(station.getPrinter1Options());
        newStation.setPrinter1Type(station.getPrinter1Type());
        newStation.setScaleEnabled(station.getScaleEnabled());
        newStation.setScaleName(station.getScaleName());
        newStation.setScannerEnabled(station.getScannerEnabled());
        newStation.setScannerName(station.getScannerName());
        newStation.setSignatureCaptureEnabled(station.getSignatureCaptureEnabled());
        newStation.setSignatureCaptureFormName(station.getSignatureCaptureFormName());
        newStation.setSignatureCaptureName(station.getSignatureCaptureName());
        newStation.setTenderedStation(station.getTenderedStation());
        return newStation;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(Station_.number).getText().trim().isEmpty() || dataUI.getTextField(Station_.description).getText().trim().isEmpty());
        }
    }
}

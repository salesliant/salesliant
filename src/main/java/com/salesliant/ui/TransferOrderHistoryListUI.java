package com.salesliant.ui;

import java.util.List;
import com.salesliant.client.Config;
import com.salesliant.entity.TransferOrderHistory;
import com.salesliant.entity.TransferOrderHistoryEntry;
import com.salesliant.entity.TransferOrderHistoryEntry_;
import com.salesliant.entity.TransferOrderHistory_;
import com.salesliant.entity.TransferOrder_;
import com.salesliant.report.TransferOrderHistoryLayout;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class TransferOrderHistoryListUI extends BaseListUI<TransferOrderHistory> {

    private final BaseDao<TransferOrderHistory> daoTransferOrderHistory = new BaseDao<>(TransferOrderHistory.class);
    private TableView<TransferOrderHistoryEntry> fTransferOrderHistoryEntryTableView = new TableView<>();
    private ObservableList<TransferOrderHistoryEntry> fTransferOrderHistoryEntryList;
    private static final Logger LOGGER = Logger.getLogger(TransferOrderHistoryListUI.class.getName());

    public TransferOrderHistoryListUI() {
        mainView = createMainView();
        List<TransferOrderHistory> list = daoTransferOrderHistory.readOrderBy(TransferOrder_.sendStore, Config.getStore(), TransferOrder_.dateCreated, AppConstants.ORDER_BY_DESC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);

        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends TransferOrderHistory> observable, TransferOrderHistory newValue,
                TransferOrderHistory oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fTransferOrderHistoryEntryList = FXCollections.observableList(fTableView.getSelectionModel().getSelectedItem().getTransferOrderHistoryEntries());
                fTransferOrderHistoryEntryTableView.setItems(fTransferOrderHistoryEntryList);
                fTransferOrderHistoryEntryTableView.refresh();
            } else {
                fTransferOrderHistoryEntryTableView.getItems().clear();
                fTransferOrderHistoryEntryTableView.refresh();
            }
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    TransferOrderHistoryLayout layout = new TransferOrderHistoryLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        RadioButton sendBtn = new RadioButton("Send  ");
        RadioButton recBtn = new RadioButton("Receive  ");
        final ToggleGroup group = new ToggleGroup();
        sendBtn.setSelected(true);
        recBtn.setSelected(false);
        sendBtn.setToggleGroup(group);
        recBtn.setToggleGroup(group);
        sendBtn.setOnAction((ActionEvent e) -> {
            List<TransferOrderHistory> list = daoTransferOrderHistory.readOrderBy(TransferOrderHistory_.sendStore, Config.getStore(), TransferOrderHistory_.dateCreated, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
        });
        recBtn.setOnAction((ActionEvent e) -> {
            List<TransferOrderHistory> list = daoTransferOrderHistory.readOrderBy(TransferOrderHistory_.receiveStore, Config.getStore(), TransferOrderHistory_.dateCreated, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
        });

        mainPane.add(sendBtn, 0, 0);
        mainPane.add(recBtn, 1, 0);
        GridPane.setHalignment(sendBtn, HPos.LEFT);
        GridPane.setHalignment(recBtn, HPos.LEFT);

        TableColumn<TransferOrderHistory, String> sendStoreCol = new TableColumn<>("Transfer From");
        sendStoreCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrderHistory, String> p) -> {
            if (p.getValue().getSendStore() != null) {
                return new SimpleStringProperty(p.getValue().getSendStore().getStoreName());
            } else {
                return null;
            }
        });
        sendStoreCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        sendStoreCol.setPrefWidth(180);

        TableColumn<TransferOrderHistory, String> receiveStoreCol = new TableColumn<>("Transfer To");
        receiveStoreCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrderHistory, String> p) -> {
            if (p.getValue().getSendStore() != null) {
                return new SimpleStringProperty(p.getValue().getReceiveStore().getStoreName());
            } else {
                return null;
            }
        });
        receiveStoreCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        receiveStoreCol.setPrefWidth(180);

        TableColumn<TransferOrderHistory, String> transferNumberCol = new TableColumn<>("Transfer #");
        transferNumberCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistory_.transferOrderNumber.getName()));
        transferNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        transferNumberCol.setPrefWidth(100);

        TableColumn<TransferOrderHistory, String> sendDateCol = new TableColumn<>("Send Date");
        sendDateCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistory_.dateSent.getName()));
        sendDateCol.setCellFactory(stringCell(Pos.CENTER));
        sendDateCol.setPrefWidth(130);

        TableColumn<TransferOrderHistory, String> receiveDateCol = new TableColumn<>("Receive Date");
        receiveDateCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistory_.dateReceived.getName()));
        receiveDateCol.setCellFactory(stringCell(Pos.CENTER));
        receiveDateCol.setPrefWidth(130);

        TableColumn<TransferOrderHistory, String> totalAmountCol = new TableColumn<>("Total Amount");
        totalAmountCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistory_.total.getName()));
        totalAmountCol.setCellFactory(stringCell(Pos.CENTER));
        totalAmountCol.setPrefWidth(120);

        fTableView.getColumns().add(sendStoreCol);
        fTableView.getColumns().add(receiveStoreCol);
        fTableView.getColumns().add(sendDateCol);
        fTableView.getColumns().add(receiveDateCol);
        fTableView.getColumns().add(transferNumberCol);
        fTableView.getColumns().add(totalAmountCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(250);
        setTableWidth(fTableView);

        TableColumn<TransferOrderHistoryEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistoryEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(140);
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<TransferOrderHistoryEntry, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistoryEntry_.itemDescription.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER));
        descriptionCol.setMinWidth(400);

        TableColumn<TransferOrderHistoryEntry, String> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistoryEntry_.quantity.getName()));
        qtyCol.setCellFactory(stringCell(Pos.CENTER));
        qtyCol.setMinWidth(100);

        TableColumn<TransferOrderHistoryEntry, String> costCol = new TableColumn<>("cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderHistoryEntry_.cost.getName()));
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setMinWidth(100);

        TableColumn<TransferOrderHistoryEntry, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrderHistoryEntry, String> p) -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(getString(p.getValue().getItem().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER));
        priceCol.setMinWidth(100);
        fTransferOrderHistoryEntryTableView.getColumns().add(skuCol);
        fTransferOrderHistoryEntryTableView.getColumns().add(descriptionCol);
        fTransferOrderHistoryEntryTableView.getColumns().add(qtyCol);
        fTransferOrderHistoryEntryTableView.getColumns().add(costCol);
        fTransferOrderHistoryEntryTableView.getColumns().add(priceCol);
        fTransferOrderHistoryEntryTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTransferOrderHistoryEntryTableView.setPrefHeight(200);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fTransferOrderHistoryEntryTableView, 0, 3, 2, 1);
        mainPane.add(createReceivePrintButtonPane(), 0, 4, 2, 1);
        return mainPane;
    }

    protected HBox createReceivePrintButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.Store;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.entity.TransferOrderEntry_;
import com.salesliant.entity.TransferOrder_;
import com.salesliant.report.TransferOrderLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.widget.StoreTableWidget;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

public class TransferOrderListUI extends BaseListUI<TransferOrder> {

    private final BaseDao<TransferOrder> daoTransferOrder = new BaseDao<>(TransferOrder.class);
    private TableView<TransferOrderEntry> fDetailTableView = new TableView<>();
    private ObservableList<TransferOrderEntry> fTransferOrderEntryList;
    private Store fStore;
    private final Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
    private final static String TRANSFER_ORDER_TITLE = "Transfer Order";
    private static final Logger LOGGER = Logger.getLogger(TransferOrderListUI.class.getName());

    public TransferOrderListUI() {
        mainView = createMainView();
        List<TransferOrder> list = daoTransferOrder.read(TransferOrder_.status, DBConstants.STATUS_OPEN)
                .stream()
                .filter(e -> e.getSendStore().getId().equals(Config.getStore().getId()))
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TransferOrder> observable, TransferOrder newValue, TransferOrder oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        fStore = fEntity.getReceiveStore();
                        List<TransferOrderEntry> aList = fEntity.getTransferOrderEntries()
                                .stream()
                                .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                                .collect(Collectors.toList());
                        fTransferOrderEntryList = FXCollections.observableList(aList);
                        fDetailTableView.setItems(fTransferOrderEntryList);
                    } else {
                        fDetailTableView.getItems().clear();
                        fStore = null;
                    }
                });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                StoreTableWidget storeListUI = new StoreTableWidget();
                fInputDialog = createSelectCancelUIDialog(storeListUI.getView(), "Store");
                selectBtn.setDisable(true);
                ((TableView<Store>) storeListUI.getTableView()).getSelectionModel().selectedItemProperty().addListener(
                        (ObservableValue<? extends Store> observable, Store newValue, Store oldValue) -> {
                            if (observable != null && observable.getValue() != null) {
                                selectBtn.setDisable(false);
                            } else {
                                selectBtn.setDisable(true);
                            }
                        });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (storeListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fStore = (Store) storeListUI.getTableView().getSelectionModel().getSelectedItem();
                        fEntity = new TransferOrder();
                        fEntity.setReceiveStore(fStore);
                        fEntity.setSendStore(Config.getStore());
                        fEntity.setStore(Config.getStore());
                        fEntity.setStatus(DBConstants.STATUS_OPEN);
                        fEntity.setTransferOrderNumber(Config.getNumber(DBConstants.SEQ_TRANSFER_NUMBER).toString());
                        fEntity.setDateCreated(new Timestamp(new Date().getTime()));
                        fEntity.setEmployeeSendName(Config.getEmployee().getNameOnSalesOrder());
                        fEntity.setTotal(BigDecimal.ZERO);
                        fEntity.setFreightAmount(BigDecimal.ZERO);
                        TransferOrderUI newUI = new TransferOrderUI(fEntity);
                        newUI.setParent(this);
                        fInputDialog.close();
                        fInputDialog = createUIDialog(newUI.getView(), TRANSFER_ORDER_TITLE + "---" + fStore.getStoreName() + ", Transfer Order Number:" + fEntity.getTransferOrderNumber()
                                + ", Sender:" + fEntity.getEmployeeSendName());
                        fInputDialog.showDialog();
                    }
                });
                fInputDialog.show();
                break;

            case AppConstants.ACTION_PROCESS:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    TransferOrderUI editUI = new TransferOrderUI(fEntity);
                    fStore = fEntity.getReceiveStore();
                    editUI.setParent(this);
                    fInputDialog = createUIDialog(editUI.getView(), TRANSFER_ORDER_TITLE + "---" + fStore.getStoreName() + ", Transfer Order Number:" + fEntity.getTransferOrderNumber()
                            + ", Sender:" + fEntity.getEmployeeSendName());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    TransferOrderLayout layout = new TransferOrderLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_EXPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    TransferOrderLayout layout = new TransferOrderLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_SAVE:
                fInputDialog.close();
                Optional<TransferOrder> p = fTableView.getItems().stream().filter(e -> e.getId().equals(fEntity.getId())).findFirst();
                if (p.isPresent()) {
                    fTableView.refresh();
                    fDetailTableView.refresh();
                } else {
                    fTableView.getItems().add(fEntity);
                    fTableView.getSelectionModel().select(fEntity);
                }
                break;
            case AppConstants.ACTION_VOID:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to void this transfer order?", (ActionEvent e) -> {
                        if (fEntity.getId() != null) {
                            daoTransferOrder.delete(fEntity);
                        }
                        fEntityList.remove(fEntity);
                        fTableView.refresh();
                        fDetailTableView.refresh();
                    });
                }
                break;
            case AppConstants.ACTION_PROCESS_FINISH:
                fInputDialog.close();
                Optional<TransferOrder> po = fTableView.getItems().stream().filter(e -> e.getId().equals(fEntity.getId())).findFirst();
                if (po.isPresent()) {
                    fEntityList.remove(fEntity);
                    fTableView.refresh();
                    fDetailTableView.refresh();
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        RadioButton sendTransferBtn = new RadioButton("Send Transfer  ");
        RadioButton receiveTransferBtn = new RadioButton("Receive Transfer  ");
        final ToggleGroup group = new ToggleGroup();
        sendTransferBtn.setSelected(true);
        receiveTransferBtn.setSelected(false);
        sendTransferBtn.setToggleGroup(group);
        receiveTransferBtn.setToggleGroup(group);
        sendTransferBtn.setOnAction((ActionEvent event) -> {
            newButton.setVisible(true);
            List<TransferOrder> list = daoTransferOrder.read(TransferOrder_.status, DBConstants.STATUS_OPEN)
                    .stream()
                    .filter(e -> e.getSendStore().getId().equals(Config.getStore().getId()))
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
        });
        receiveTransferBtn.setOnAction((ActionEvent event) -> {
            newButton.setVisible(false);
            List<TransferOrder> list = daoTransferOrder.read(TransferOrder_.status, DBConstants.STATUS_IN_PROGRESS)
                    .stream()
                    .filter(e -> e.getReceiveStore().getId().equals(Config.getStore().getId()))
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
        });

        mainPane.add(sendTransferBtn, 0, 0);
        mainPane.add(receiveTransferBtn, 1, 0);
        GridPane.setHalignment(sendTransferBtn, HPos.LEFT);
        GridPane.setHalignment(receiveTransferBtn, HPos.LEFT);

        TableColumn<TransferOrder, String> transferNumberCol = new TableColumn<>("Transfer#");
        transferNumberCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrder_.transferOrderNumber.getName()));
        transferNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        transferNumberCol.setPrefWidth(100);

        TableColumn<TransferOrder, String> storeCol = new TableColumn<>("Store");
        storeCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrder, String> p) -> {
            if (p.getValue().getDateCreated() != null) {
                return new SimpleStringProperty(p.getValue().getSendStore().getStoreName());
            } else {
                return new SimpleStringProperty(p.getValue().getReceiveStore().getStoreName());
            }
        });
        storeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        storeCol.setPrefWidth(210);

        TableColumn<TransferOrder, String> dateCreatedCol = new TableColumn<>("Date Sent");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrder_.dateSent.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCreatedCol.setPrefWidth(125);

        TableColumn<TransferOrder, String> dateexpectedCol = new TableColumn<>("Date Expected");
        dateexpectedCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrder_.dateExpected.getName()));
        dateexpectedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateexpectedCol.setPrefWidth(125);

        TableColumn<TransferOrder, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrder_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(90);

        TableColumn<TransferOrder, String> freightAmountCol = new TableColumn<>("Freight");
        freightAmountCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrder_.freightAmount.getName()));
        freightAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        freightAmountCol.setPrefWidth(90);

        TableColumn<TransferOrder, String> employeeCol = new TableColumn<>("Processor");
        employeeCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrder, String> p) -> {
            if (p.getValue().getDateCreated() != null) {
                return new SimpleStringProperty(p.getValue().getEmployeeSendName());
            } else {
                return new SimpleStringProperty(p.getValue().getEmployeeReceiveName());
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(130);

        fTableView.getColumns().add(storeCol);
        fTableView.getColumns().add(transferNumberCol);
        fTableView.getColumns().add(employeeCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(dateexpectedCol);
        fTableView.getColumns().add(freightAmountCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(250);
        setTableWidth(fTableView);

        TableColumn<TransferOrderEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(150);
        skuCol.setCellFactory(stringCell(Pos.TOP_LEFT));

        TableColumn<TransferOrderEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrderEntry, String> p) -> {
            if (p.getValue().getItem() != null) {
                String description = getString(p.getValue().getItemDescription());
                if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                    description = description + "\n" + p.getValue().getLineNote();
                }
                if (p.getValue().getSerialNumbers() != null & !p.getValue().getSerialNumbers().isEmpty()) {
                    description = description + "\n";
                    for (SerialNumber sn : p.getValue().getSerialNumbers()) {
                        description = description + getString(sn.getSerialNumber()) + "; ";
                    }
                }
                description = description.trim();
                if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                    description = description.substring(0, description.length() - 1);
                }
                return new SimpleStringProperty(description);
            } else {
                return new SimpleStringProperty("");
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(320);

        TableColumn<TransferOrderEntry, String> detailQtySendCol = new TableColumn<>("Qty Send");
        detailQtySendCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.quantity.getName()));
        detailQtySendCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailQtySendCol.setMinWidth(100);

        TableColumn<TransferOrderEntry, String> detailQtyReceiveCol = new TableColumn<>("Qty Receive");
        detailQtyReceiveCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.quantityReceived.getName()));
        detailQtyReceiveCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailQtyReceiveCol.setMinWidth(100);

        TableColumn<TransferOrderEntry, String> detailCostCol = new TableColumn<>("Cost");
        detailCostCol.setCellValueFactory(new PropertyValueFactory<>(TransferOrderEntry_.cost.getName()));
        detailCostCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailCostCol.setMinWidth(100);

        TableColumn<TransferOrderEntry, String> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<TransferOrderEntry, String> p) -> {
            if (p.getValue() != null) {
                BigDecimal total = zeroIfNull(p.getValue().getCost()).multiply(zeroIfNull(p.getValue().getQuantity()));
                return new SimpleStringProperty(getString(total));
            } else {
                return null;
            }
        });
        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        fDetailTableView.getColumns().add(skuCol);
        fDetailTableView.getColumns().add(detailDescriptionCol);
        fDetailTableView.getColumns().add(detailQtySendCol);
        fDetailTableView.getColumns().add(detailQtyReceiveCol);
        fDetailTableView.getColumns().add(detailCostCol);
        fDetailTableView.getColumns().add(detailTotalCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(200);
        setTableWidth(fDetailTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fDetailTableView, 0, 3, 2, 1);
        mainPane.add(createButtonPane(), 0, 4, 2, 1);

        return mainPane;
    }

    protected HBox createButtonPane() {
        Button processButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PROCESS, AppConstants.ACTION_PROCESS, fHandler);
        Button voidButton = ButtonFactory.getButton(ButtonFactory.BUTTON_VOID, AppConstants.ACTION_VOID, fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(processButton);
        buttonGroup.getChildren().add(voidButton);
        buttonGroup.getChildren().add(printButton);
        buttonGroup.getChildren().add(exportButton);
        buttonGroup.getChildren().add(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

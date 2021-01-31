package com.salesliant.widget;

import com.salesliant.ui.SerialNumberUI;
import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.SerialNumber_;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class SerialNumberWidge extends BaseListUI<SerialNumber> {

    private final BaseDao<SerialNumber> daoSerialNumber = new BaseDao<>(SerialNumber.class);
    private final DataUI dataUI = new DataUI(SerialNumber.class);
    private SalesOrderEntry fSalesOrderEntry;
    private PurchaseOrderEntry fPurchaseOrderEntry;
    private TransferOrderEntry fTransferOrderEntry;
    private final GridPane fEditPane;
    private final Item fItem;
    private GridPane fServicePane;
    private final static String SERIAL_NUMBER_TITLE = "Serial Number";
    public Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, fHandler);
    public Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
    private final ObservableSet<SerialNumber> selectedItems = FXCollections.observableSet();
    private final TableColumn<SerialNumber, SerialNumber> selectedCol = new TableColumn<>("");
    private static final Logger LOGGER = Logger.getLogger(SerialNumberWidge.class.getName());

    public SerialNumberWidge(Item item) {
        fItem = item;
        mainView = createMainView();
        List<SerialNumber> list = daoSerialNumber.read(SerialNumber_.item, fItem, SerialNumber_.sold, Boolean.FALSE, SerialNumber_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    public SerialNumberWidge(SalesOrderEntry soe) {
        fSalesOrderEntry = soe;
        fItem = fSalesOrderEntry.getItem();
        mainView = createMainView();
        List<SerialNumber> list = daoSerialNumber.read(SerialNumber_.item, fItem, SerialNumber_.sold, Boolean.FALSE, SerialNumber_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        fTableView.getItems().forEach(e -> {
            fSalesOrderEntry.getSerialNumbers().forEach(s -> {
                if (e.getSerialNumber().equals(s.getSerialNumber())) {
                    selectedItems.add(e);
                }
            });
        });
    }

    public SerialNumberWidge(PurchaseOrderEntry poe) {
        fPurchaseOrderEntry = poe;
        fItem = fPurchaseOrderEntry.getItem();
        mainView = createMainView();
        List<SerialNumber> list = daoSerialNumber.read(SerialNumber_.item, fItem, SerialNumber_.sold, Boolean.FALSE, SerialNumber_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        fTableView.getItems().forEach(e -> {
            fPurchaseOrderEntry.getSerialNumbers().forEach(s -> {
                if (e.getSerialNumber().equals(s.getSerialNumber())) {
                    selectedItems.add(e);
                }
            });
        });
    }

    public SerialNumberWidge(TransferOrderEntry toe) {
        fTransferOrderEntry = toe;
        fItem = fTransferOrderEntry.getItem();
        mainView = createMainView();
        List<SerialNumber> list = daoSerialNumber.read(SerialNumber_.item, fItem, SerialNumber_.sold, Boolean.FALSE, SerialNumber_.store, Config.getStore())
                .stream()
                .filter(e -> e.getTransferOrderEntry() == null)
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        fTableView.getItems().forEach(e -> {
            fTransferOrderEntry.getSerialNumbers().forEach(s -> {
                if (e.getSerialNumber().equals(s.getSerialNumber())) {
                    selectedItems.add(e);
                }
            });
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                SerialNumberUI snUI = new SerialNumberUI(fItem);
                snUI.setParent(this);
                fInputDialog = createUIDialog(snUI.getView(), SERIAL_NUMBER_TITLE);
                snUI.saveButton.addEventFilter(ActionEvent.ACTION, event -> {
                    List<SerialNumber> list = snUI.getSerialNumberList();
                    fInputDialog.close();
                    if (!list.isEmpty()) {
                        list.forEach(e -> {
                            daoSerialNumber.insert(e);
                            if (daoSerialNumber.getErrorMessage() == null) {
                                fEntityList.add(e);
                            }
                        });
                    }
                    fTableView.scrollTo(fTableView.getItems().size() - 1);
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, SERIAL_NUMBER_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoSerialNumber.update(fEntity);
                            if (daoSerialNumber.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoSerialNumber.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(SerialNumber_.serialNumber).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoSerialNumber.delete(fEntity);
                        fEntityList.remove(fEntity);
                        selectedItems.remove(fEntity);
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
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        TextField searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        selectedCol.setCellValueFactory((TableColumn.CellDataFeatures<SerialNumber, SerialNumber> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<SerialNumber, SerialNumber> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<SerialNumber, String> serialNumberCol = new TableColumn<>("Serial Number");
        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>(SerialNumber_.serialNumber.getName()));
        serialNumberCol.setCellFactory(stringCell(Pos.CENTER));
        serialNumberCol.setPrefWidth(120);

        TableColumn<SerialNumber, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<SerialNumber, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getDescription() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        TableColumn<SerialNumber, String> laborWarrantyCol = new TableColumn<>("Labor Warranty Expires On");
        laborWarrantyCol.setCellValueFactory(new PropertyValueFactory<>(SerialNumber_.warrantyExpireLabor.getName()));
        laborWarrantyCol.setCellFactory(stringCell(Pos.CENTER));
        laborWarrantyCol.setPrefWidth(160);
        laborWarrantyCol.setSortable(false);

        TableColumn<SerialNumber, String> partWarrantyCol = new TableColumn<>("Part Warranty Expires On");
        partWarrantyCol.setCellValueFactory(new PropertyValueFactory<>(SerialNumber_.warrantyExpirePart.getName()));
        partWarrantyCol.setCellFactory(stringCell(Pos.CENTER));
        partWarrantyCol.setPrefWidth(150);
        partWarrantyCol.setSortable(false);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(serialNumberCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(laborWarrantyCol);
        fTableView.getColumns().add(partWarrantyCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(240);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 1, 2, 1);
        if (fItem == null) {
            mainPane.add(fServicePane, 0, 2, 2, 1);
        }
        mainPane.add(createButtonGroup(), 1, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        editPane.setVgap(3);

        DatePicker laborExpireDate = new DatePicker();
        dataUI.setUIComponent(SerialNumber_.warrantyExpireLabor, laborExpireDate);
        laborExpireDate.setPrefWidth(200);

        DatePicker partExpireDate = new DatePicker();
        dataUI.setUIComponent(SerialNumber_.warrantyExpirePart, partExpireDate);
        partExpireDate.setPrefWidth(200);

        add(editPane, "Description:", dataUI.createLabelField(SerialNumber_.description, 200, Pos.CENTER_LEFT), fListener, 1);
        add(editPane, "Serial Number:", dataUI.createTextField(SerialNumber_.serialNumber), fListener, 200.0, 2);
        add(editPane, "Labor Warranty Expire On:", laborExpireDate, 4);
        add(editPane, "Part Warranty Expire On:", partExpireDate, 5);

        editPane.add(lblWarning, 0, 6, 2, 1);
        editPane.add(addHLine(1), 0, 7, 2, 1);

        return editPane;
    }

    private HBox createButtonGroup() {
        HBox leftButtonBox = new HBox();
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        leftButtonBox.getChildren().addAll(newButton, editButton, deleteButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(2);

        HBox rightButtonBox = new HBox();
        rightButtonBox.getChildren().addAll(selectButton, closeButton);
        selectButton.setVisible(true);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    public List<SerialNumber> getSelectedItems() {
        ArrayList<SerialNumber> list = new ArrayList<>();
        selectedItems.stream().forEach(e -> list.add(e));
        return list;
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.PurchaseOrderEntry;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SerialNumber;
import com.salesliant.entity.SerialNumber_;
import com.salesliant.entity.TransferOrderEntry;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.datePickerCell;
import static com.salesliant.util.BaseUtil.editableCell;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.IconFactory;
import com.salesliant.util.RES;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public final class SerialNumberUI extends BaseListUI<SerialNumber> {

    private final TableColumn<SerialNumber, String> serialNumberCol = new TableColumn<>("Serial Number");
    private SalesOrderEntry fSalesOrderEntry;
    private PurchaseOrderEntry fPurchaseOrderEntry;
    private TransferOrderEntry fTransferOrderEntry;
    private Item fItem;
    public Button saveButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);

    public SerialNumberUI() {
        initilize();
    }

    public SerialNumberUI(Item item) {
        fItem = item;
        initilize();
    }

    public SerialNumberUI(SalesOrderEntry soe) {
        fSalesOrderEntry = soe;
        initilize();
    }

    public SerialNumberUI(PurchaseOrderEntry poe) {
        fPurchaseOrderEntry = poe;
        initilize();
    }

    public SerialNumberUI(TransferOrderEntry toe) {
        fTransferOrderEntry = toe;
        initilize();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                SerialNumber sn = fTableView.getItems().stream().filter(e -> e.getSerialNumber() == null).findFirst().orElse(null);
                if (sn == null) {
                    fEntity = new SerialNumber();
                    fEntity.setSold(Boolean.FALSE);
                    fEntity.setStore(Config.getStore());
                    if (fItem != null) {
                        fEntity.setItem(fItem);
                        if (fItem.getDescription() != null) {
                            fEntity.setDescription(fItem.getDescription());
                        }
                    } else if (fSalesOrderEntry != null) {
                        fEntity.setSalesOrderEntry(fSalesOrderEntry);
                        Item item = fSalesOrderEntry.getItem();
                        if (item != null) {
                            fEntity.setItem(item);
                            if (item.getDescription() != null) {
                                fEntity.setDescription(item.getDescription());
                            }
                        }
                    } else if (fPurchaseOrderEntry != null) {
                        fEntity.setPurchaseOrderEntry(fPurchaseOrderEntry);
                        Item item = fPurchaseOrderEntry.getItem();
                        if (item != null) {
                            fEntity.setItem(item);
                            if (item.getDescription() != null) {
                                fEntity.setDescription(item.getDescription());
                            }
                        }
                    } else if (fTransferOrderEntry != null) {
                        fEntity.setTransferOrderEntry(fTransferOrderEntry);
                        Item item = fTransferOrderEntry.getItem();
                        if (item != null) {
                            fEntity.setItem(item);
                            if (item.getDescription() != null) {
                                fEntity.setDescription(item.getDescription());
                            }
                        }
                    }
                    fEntityList.add(fEntity);
                    fTableView.refresh();
                } else {
                    fEntity = sn;
                }
                Platform.runLater(() -> {
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(fEntity);
                    fTableView.scrollTo(fEntity);
                    fTableView.layout();
                    fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), serialNumberCol);
                    fTableView.edit(fTableView.getSelectionModel().getSelectedIndex(), serialNumberCol);
                });
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fEntityList.remove(fEntity);
                    if (fEntityList.isEmpty()) {
                        fTableView.getSelectionModel().select(null);
                    }
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_UP:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1 && fTableView.getItems().get(i - 1).getItem() != null) {
                        Collections.swap(fTableView.getItems(), i, i - 1);
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i - 1);
                    }
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_DOWN:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i < (fTableView.getItems().size() - 1) && fTableView.getItems().get(i + 1).getItem() != null) {
                        Collections.swap(fTableView.getItems(), i, i + 1);
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i + 1);
                    }
                }
                break;
            case AppConstants.ACTION_TABLE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.refresh();
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(i);
                    fTableView.getFocusModel().focus(i, serialNumberCol);
                }
                break;
        }
    }

    private void initilize() {
        mainView = createMainView();
        fEntityList = FXCollections.observableArrayList();
        fTableView.setItems(fEntityList);
        fTableView.setEditable(true);
        addKeyListener();
        handleAction(AppConstants.ACTION_ADD);
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        serialNumberCol.setCellValueFactory(new PropertyValueFactory<>(SerialNumber_.serialNumber.getName()));
        serialNumberCol.setCellFactory(editableCell(Pos.CENTER_LEFT));
        serialNumberCol.setPrefWidth(120);
        serialNumberCol.setEditable(true);
        serialNumberCol.setSortable(false);

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
        descriptionCol.setEditable(false);
        descriptionCol.setSortable(false);

        TableColumn<SerialNumber, Date> laborWarrantyCol = new TableColumn<>("Labor Warranty Expires On");
        laborWarrantyCol.setCellValueFactory(new PropertyValueFactory<>(SerialNumber_.warrantyExpireLabor.getName()));
        laborWarrantyCol.setCellFactory(datePickerCell());
        laborWarrantyCol.setOnEditCommit((TableColumn.CellEditEvent<SerialNumber, Date> t) -> {
            SerialNumber sn = (SerialNumber) t.getTableView().getItems().get(t.getTablePosition().getRow());
            sn.setWarrantyExpireLabor(t.getNewValue());
        });
        laborWarrantyCol.setPrefWidth(160);
        laborWarrantyCol.setSortable(false);
        laborWarrantyCol.setEditable(true);
        laborWarrantyCol.setSortable(false);

        TableColumn<SerialNumber, Date> partWarrantyCol = new TableColumn<>("Part Warranty Expires On");
        partWarrantyCol.setCellValueFactory(new PropertyValueFactory<>(SerialNumber_.warrantyExpirePart.getName()));
        partWarrantyCol.setCellFactory(datePickerCell());
        partWarrantyCol.setOnEditCommit((TableColumn.CellEditEvent<SerialNumber, Date> t) -> {
            SerialNumber sn = (SerialNumber) t.getTableView().getItems().get(t.getTablePosition().getRow());
            sn.setWarrantyExpirePart(t.getNewValue());
        });
        partWarrantyCol.setPrefWidth(150);
        partWarrantyCol.setSortable(false);
        partWarrantyCol.setEditable(true);
        partWarrantyCol.setSortable(false);

        fTableView.getColumns().add(serialNumberCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(laborWarrantyCol);
        fTableView.getColumns().add(partWarrantyCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(240);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 1, 2, 1);
        mainPane.add(createButtonGroup(), 1, 2);
        return mainPane;
    }

    private HBox createButtonGroup() {
        HBox leftButtonBox = new HBox();
        Button upButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_UP_ICON), AppConstants.ACTION_MOVE_ROW_UP, fHandler);
        Button downButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_DOWN_ICON), AppConstants.ACTION_MOVE_ROW_DOWN, fHandler);
        Button newButton = ButtonFactory.getButton(IconFactory.getIcon(RES.ADD_ICON), AppConstants.ACTION_ADD, fHandler);
        Button deleteButton = ButtonFactory.getButton(IconFactory.getIcon(RES.DELETE_ICON), AppConstants.ACTION_DELETE, fHandler);
        leftButtonBox.getChildren().addAll(upButton, downButton, newButton, deleteButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        rightButtonBox.getChildren().addAll(saveButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    public List<SerialNumber> getSerialNumberList() {
        List<SerialNumber> list = new ArrayList<>();
        for (int i = 0; i < fTableView.getItems().size(); i++) {
            if (fTableView.getItems().get(i).getSerialNumber() != null && !fTableView.getItems().get(i).getSerialNumber().isEmpty()) {
                list.add(fTableView.getItems().get(i));
            }
        }
        return list;
    }
}

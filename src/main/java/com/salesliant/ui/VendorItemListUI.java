package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.dateCell;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import com.salesliant.validator.InvalidException;
import com.salesliant.validator.VendorItemValidator;
import com.salesliant.widget.ItemTableWidget;
import com.salesliant.widget.VendorWidget;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class VendorItemListUI extends BaseListUI<VendorItem> {

    private final BaseDao<VendorItem> daoVendorItem = new BaseDao<>(VendorItem.class);
    private final DataUI dataUI = new DataUI(VendorItem.class);
    private VendorWidget fVendorCombo = new VendorWidget();
    private final GridPane fEditPane;
    private Item fSelectedItem;
    private SearchField searchField;
    private final TableColumn<VendorItem, String> descriptionCol = new TableColumn<>("Description");
    protected ObservableSet<VendorItem> selectedItems;
    public TableColumn<VendorItem, VendorItem> selectedCol = new TableColumn("");
    public final Button closeBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
    private final Button btn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, fHandler);
    public HBox actionButtonBox = new HBox();
    private final static String VENDOR_ITEM_TITLE = "Vendor Item";
    private final VendorItemValidator validator = new VendorItemValidator();
    private static final Logger LOGGER = Logger.getLogger(VendorItemListUI.class.getName());

    public VendorItemListUI() {
        closeBtn.setId(AppConstants.ACTION_CLOSE);
        closeBtn.setOnAction(fHandler);
        actionButtonBox = createNewEditDeleteButtonPane();
        actionButtonBox.getChildren().add(closeBtn);
        mainView = createMainView();
        fVendorCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Vendor> observable, Vendor newValue, Vendor oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                List<VendorItem> list = fVendorCombo.getSelectionModel().getSelectedItem().getVendorItems()
                        .stream()
                        .filter(e -> e.getItem() != null && e.getItem().getActiveTag() != null && e.getItem().getActiveTag())
                        .collect(Collectors.toList());
                if (!list.isEmpty()) {
                    fEntityList = FXCollections.observableList(list);
                } else {
                    fEntityList = FXCollections.observableArrayList();
                }
            } else {
                fEntityList = FXCollections.observableArrayList();
            }
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            searchField.setText("");
        });
        fEditPane = createEditPane();
        dialogTitle = VENDOR_ITEM_TITLE;
        descriptionCol.setPrefWidth(326);
    }

    public VendorItemListUI(Vendor vendor) {
        this();
        selectedCol.setVisible(true);
        fTableView.setEditable(false);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fVendorCombo.getItems().clear();
        fVendorCombo.getItems().add(vendor);
        fVendorCombo.getSelectionModel().select(vendor);
        actionButtonBox.getChildren().remove(closeBtn);
        selectedItems = FXCollections.observableSet();
        descriptionCol.setPrefWidth(300);
        Platform.runLater(() -> searchField.requestFocus());
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (fVendorCombo.getSelectionModel().getSelectedItem() != null) {
                    fEntity = new VendorItem();
                    fEntity.setVendor(fVendorCombo.getSelectionModel().getSelectedItem());
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, VENDOR_ITEM_TITLE);
                    btn.setVisible(true);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            fEntity.setItem(fSelectedItem);
                            validator.validate(fEntity);
                            daoVendorItem.insert(fEntity);
                            if (daoVendorItem.getErrorMessage() == null) {
                                fEntityList.add(fEntity);
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                                fEntity.getVendor().getVendorItems().add(fEntity);
                            } else {
                                lblWarning.setText(daoVendorItem.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            if (ex instanceof InvalidException) {
                                lblWarning.setText(ex.getMessage());
                                event.consume();
                            } else {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(VendorItem_.vendorItemLookUpCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, VENDOR_ITEM_TITLE);
                    btn.setVisible(false);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            validator.validate(fEntity);
                            daoVendorItem.update(fEntity);
                            if (daoVendorItem.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoVendorItem.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            if (ex instanceof InvalidException) {
                                lblWarning.setText(ex.getMessage());
                                event.consume();
                            } else {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(VendorItem_.vendorItemLookUpCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_SELECT:
                ItemTableWidget itemTableWidget = new ItemTableWidget();
                fInputDialog = createSelectCancelUIDialog(itemTableWidget.getView(), "Item");
                selectBtn.setDisable(true);
                itemTableWidget.selectedCol.setVisible(false);
                ((TableView<Item>) itemTableWidget.getTableView()).getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemTableWidget.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fSelectedItem = (Item) itemTableWidget.getTableView().getSelectionModel().getSelectedItem();
                        fEntity.setItem((Item) itemTableWidget.getTableView().getSelectionModel().getSelectedItem());
                        dataUI.getTextField(VendorItem_.item).setText(fSelectedItem.getItemLookUpCode());
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    AppConstants.Response answer = createConfirmResponseDialog("Are you sure to delete the seleted entry?");
                    if (answer.equals(AppConstants.Response.YES)) {
                        daoVendorItem.delete(fEntity);
                        if (daoVendorItem.getErrorMessage() == null) {
                            if (selectedItems != null && !selectedItems.isEmpty() && selectedItems.contains(fEntity)) {
                                selectedItems.remove(fEntity);
                            }
                            fEntityList.remove(fEntity);
                            fEntity.getVendor().getVendorItems().remove(fEntity);
                        } else {
                            showAlertDialog("Cannot delete the selected Vendor Item");
                        }
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        fVendorCombo.setPrefWidth(250);
        selectedCol.setEditable(true);
        selectedCol.setCellValueFactory((CellDataFeatures<VendorItem, VendorItem> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<VendorItem, VendorItem> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setVisible(false);

        TableColumn<VendorItem, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.vendorItemLookUpCode.getName()));
        vendorSKUCol.setCellFactory(stringCell(Pos.CENTER));
        vendorSKUCol.setPrefWidth(150);

        TableColumn<VendorItem, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        skuCol.setCellFactory(stringCell(Pos.CENTER));
        skuCol.setPrefWidth(150);

        descriptionCol.setCellValueFactory((CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(getItemDescription(p.getValue().getItem()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<VendorItem, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.cost.getName()));
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setPrefWidth(100);

        TableColumn<VendorItem, String> lastReceviedCol = new TableColumn<>("Last Received");
        lastReceviedCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.lastUpdated.getName()));
        lastReceviedCol.setCellFactory(dateCell(Pos.CENTER));
        lastReceviedCol.setPrefWidth(100);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(vendorSKUCol);
        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(lastReceviedCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        fTableView.setPrefWidth(842);
        fTableView.setEditable(false);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(actionButtonBox, 0, 3);
        return mainPane;
    }

    private HBox createNewEditDeleteButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        HBox buttonGroup = new HBox();
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        Label vendorLbl = new Label("Vendor: ");
        buttonGroup.getChildren().addAll(vendorLbl, fVendorCombo, filler, newButton, editButton, deleteButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        add(editPane, "Vendor SKU:*", dataUI.createTextField(VendorItem_.vendorItemLookUpCode), fListener, 250.0, 1);
        add(editPane, "Your SKU:", dataUI.createLabelField(VendorItem_.item), fListener, 250.0, 2);
        editPane.add(btn, 3, 2);
        add(editPane, "Cost:", dataUI.createTextField(VendorItem_.cost), fListener, 3);
        editPane.add(lblWarning, 1, 4, 2, 1);

        return editPane;
    }

    public List<VendorItem> getSelectedItems() {
        ArrayList<VendorItem> list = new ArrayList<>();
        selectedItems.stream().forEach(e -> list.add(e));
        return list;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(VendorItem_.vendorItemLookUpCode).getText().trim().isEmpty()
                    || dataUI.getTextField(VendorItem_.cost).getText().trim().isEmpty() || dataUI.getTextField(VendorItem_.item).getText().trim().isEmpty());
        }
    }
}

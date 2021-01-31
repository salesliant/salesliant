package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.ItemAttribute;
import com.salesliant.entity.ItemAttributeType;
import com.salesliant.entity.ItemAttributeValue;
import com.salesliant.entity.ItemAttributeValue_;
import com.salesliant.entity.Item_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.IconFactory;
import com.salesliant.util.ItemSearchField;
import com.salesliant.util.RES;
import com.salesliant.widget.ItemAttributeTypeListWidget;
import com.salesliant.widget.ItemAttributeValueWidget;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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

public class ItemWithAttributeListUI extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<ItemAttribute> daoItemAttribute = new BaseDao<>(ItemAttribute.class);
    private TableView<ItemAttribute> fItemAttributeTable = new TableView<>();
    private ObservableList<ItemAttribute> fItemAttributeList = FXCollections.observableArrayList();
    private final TableView<ItemAttributeValue> fItemAttributeValueTable = new TableView<>();
    private ObservableList<ItemAttributeValue> fItemAttributeValueList;
    private final ItemAttributeTypeListWidget fAttributeTypeListView = new ItemAttributeTypeListWidget();
    private ObservableSet<ItemAttributeValue> selectedItems = FXCollections.observableSet();
    private final TableColumn<ItemAttributeValue, ItemAttributeValue> selectedCol = new TableColumn<>("");
    private final ItemAttributeValueWidget fAttributeValueCombo = new ItemAttributeValueWidget();
    private ItemAttribute fItemAttribute;
    private final GridPane fAddPane;
    private final GridPane fEditPane;
    private final Label descriptionLabel = new Label();
    private final Label skuLabel = new Label();
    private final Label itemAttributeTypeLabel = new Label();
    protected ItemSearchField searchField = new ItemSearchField();
    private static final Logger LOGGER = Logger.getLogger(ItemWithAttributeListUI.class.getName());

    public ItemWithAttributeListUI() {
        loadData();
        mainView = createMainView();
        fAddPane = createAddPane();
        fEditPane = createEditPane();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<ItemAttribute> aList = fTableView.getSelectionModel().getSelectedItem().getItemAttributes()
                        .stream()
                        .sorted((e1, e2) -> getString(e1.getDisplayOrder()).compareTo(getString(e2.getDisplayOrder())))
                        .collect(Collectors.toList());
                fItemAttributeList = FXCollections.observableList(aList);
                fItemAttributeTable.setItems(fItemAttributeList);
            } else {
                fEntity = null;
                fItemAttributeTable.getItems().clear();
            }
        });
        fAttributeTypeListView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ItemAttributeType> observable, ItemAttributeType newValue, ItemAttributeType oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                List<ItemAttributeValue> aList = fAttributeTypeListView.getSelectionModel().getSelectedItem().getItemAttributeValues()
                        .stream()
                        .sorted((e1, e2) -> e1.getDescription().compareTo(e2.getDescription()))
                        .collect(Collectors.toList());
                fItemAttributeValueList = FXCollections.observableList(aList);
                fItemAttributeValueTable.setItems(fItemAttributeValueList);
            } else {
                fItemAttributeValueTable.getItems().clear();
            }
        });
        selectedItems.addListener((SetChangeListener<ItemAttributeValue>) change -> {
            if (change.wasAdded()) {
                ItemAttributeValue iav = change.getElementAdded();
                fItemAttributeValueTable.getItems().forEach(e -> {
                    if (!e.equals(iav)) {
                        selectedItems.remove(e);
                    }
                });
                saveBtn.setDisable(false);
            }
            if (change.wasRemoved()) {
                if (selectedItems.isEmpty()) {
                    saveBtn.setDisable(true);
                } else {
                    saveBtn.setDisable(false);
                }
            }
        });
        Platform.runLater(() -> searchField.requestFocus());
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.itemType, DBConstants.ITEM_TYPE_MATRIX, Item_.itemLookUpCode, AppConstants.ORDER_BY_DESC)
                    .stream()
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
            selectedItems.clear();
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    Item item = fTableView.getSelectionModel().getSelectedItem();
                    selectedItems.clear();
                    fInputDialog = createSaveCancelUIDialog(fAddPane, "Item Attribute");
                    updateAddPane();
                    if (!fItemAttributeTable.getItems().isEmpty()) {
                        List<ItemAttributeType> list = new ArrayList<>();
                        fItemAttributeTable.getItems().forEach(e -> {
                            list.add(e.getItemAttributeValue().getItemAttributeType());
                        });
                        List<ItemAttributeType> existedlist = new ArrayList<>();
                        fAttributeTypeListView.getItems().forEach(e -> {
                            list.forEach(c -> {
                                if (e.getId().equals(c.getId())) {
                                    existedlist.add(e);
                                }
                            });
                        });
                        fAttributeTypeListView.getItems().removeAll(existedlist);
                    }
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            selectedItems.forEach(e -> {
                                ItemAttribute ia = new ItemAttribute();
                                ia.setItem(item);
                                ia.setItemAttributeValue(e);
                                item.getItemAttributes().add(ia);
                                daoItemAttribute.insert(ia);
                                fItemAttributeList.add(ia);
                            });
                            if (daoItemAttribute.getErrorMessage() == null) {
                                fItemAttributeTable.refresh();
                                for (int i = 0; i < fItemAttributeTable.getItems().size(); i++) {
                                    ItemAttribute ia = fItemAttributeTable.getItems().get(i);
                                    ia.setDisplayOrder(i);
                                    daoItemAttribute.update(ia);
                                }
                                fTableView.getSelectionModel().select(item);
                            } else {
                                lblWarning.setText(daoItemAttribute.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fItemAttributeTable.getSelectionModel().getSelectedItem() != null) {
                    fItemAttribute = fItemAttributeTable.getSelectionModel().getSelectedItem();
                    ItemAttributeType io = fItemAttribute.getItemAttributeValue().getItemAttributeType();
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Item Attribute Value");
                    itemAttributeTypeLabel.setText(io.getDescription());
                    fAttributeValueCombo.setAttributeType(io);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            ItemAttributeValue iov = fAttributeValueCombo.getSelectionModel().getSelectedItem();
                            fItemAttribute.setItemAttributeValue(iov);
                            daoItemAttribute.update(fItemAttribute);
                            if (daoItemAttribute.getErrorMessage() == null) {
                                fItemAttributeTable.refresh();
                            } else {
                                lblWarning.setText(daoItemAttribute.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> fAttributeValueCombo.requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fItemAttributeTable.getSelectionModel().getSelectedItem() != null) {
                    fItemAttribute = fItemAttributeTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoItemAttribute.delete(fItemAttribute);
                        fItemAttributeList.remove(fItemAttribute);
                    });
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_UP:
                if (fItemAttributeTable.getSelectionModel().getSelectedItem() != null) {
                    int i = fItemAttributeTable.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1) {
                        Collections.swap(fItemAttributeTable.getItems(), i, i - 1);
                        fItemAttributeTable.scrollTo(i - 1);
                        fItemAttributeTable.getSelectionModel().select(i - 1);
                    }
                    updateDisplayOrder();
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_DOWN:
                if (fItemAttributeTable.getSelectionModel().getSelectedItem() != null) {
                    int i = fItemAttributeTable.getFocusModel().getFocusedCell().getRow();
                    if (i < fItemAttributeTable.getItems().size() - 1) {
                        Collections.swap(fItemAttributeTable.getItems(), i, i + 1);
                        fItemAttributeTable.scrollTo(i + 1);
                        fItemAttributeTable.getSelectionModel().select(i + 1);
                    }
                    updateDisplayOrder();
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Item, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        skuCol.setPrefWidth(150);

        TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((CellDataFeatures<Item, String> p) -> {
            if (p.getValue().getItemAttributes() != null && !p.getValue().getItemAttributes().isEmpty()) {
                String description = getString(p.getValue().getDescription());

                return new SimpleStringProperty(description);
            } else {
                return new SimpleStringProperty(p.getValue().getDescription());
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(400);

        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(250);
        setTableWidth(fTableView);

        TableColumn<ItemAttribute, String> optionCol = new TableColumn<>("Attribute Type");
        optionCol.setCellValueFactory((CellDataFeatures<ItemAttribute, String> p) -> {
            if (p.getValue().getItemAttributeValue() != null && p.getValue().getItemAttributeValue().getItemAttributeType() != null) {
                return new SimpleStringProperty(p.getValue().getItemAttributeValue().getItemAttributeType().getDescription());
            } else {
                return null;
            }
        });
        optionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        optionCol.setPrefWidth(150);

        TableColumn<ItemAttribute, String> optionValueCol = new TableColumn<>("Attribute Value");
        optionValueCol.setCellValueFactory((CellDataFeatures<ItemAttribute, String> p) -> {
            if (p.getValue().getItemAttributeValue() != null) {
                return new SimpleStringProperty(p.getValue().getItemAttributeValue().getDescription());
            } else {
                return null;
            }
        });
        optionValueCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        optionValueCol.setPrefWidth(400);

        fItemAttributeTable.getColumns().add(optionCol);
        fItemAttributeTable.getColumns().add(optionValueCol);
        fItemAttributeTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fItemAttributeTable.setPrefHeight(150);
        setTableWidth(fItemAttributeTable);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(fItemAttributeTable, 0, 4);
        mainPane.add(createMoveNewEditDeleteCloseButtonPane(), 0, 5);
        return mainPane;
    }

    protected HBox createMoveNewEditDeleteCloseButtonPane() {
        HBox leftButtonBox = new HBox();
        Button upButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_UP_ICON), AppConstants.ACTION_MOVE_ROW_UP, fHandler);
        Button downButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_DOWN_ICON), AppConstants.ACTION_MOVE_ROW_DOWN, fHandler);
        leftButtonBox.getChildren().addAll(upButton, downButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        rightButtonBox.getChildren().addAll(newButton, editButton, deleteButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);
        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    private GridPane createAddPane() {
        GridPane editPane = new GridPane();
        editPane.setHgap(5);
        editPane.setVgap(5);
        Label skuLbl = new Label("Item SKU: ");
        Label descLbl = new Label("Item Description: ");
        GridPane.setHalignment(skuLbl, HPos.RIGHT);
        GridPane.setHalignment(descLbl, HPos.RIGHT);
        editPane.add(skuLbl, 0, 0);
        editPane.add(skuLabel, 1, 0);
        editPane.add(descLbl, 0, 1);
        editPane.add(descriptionLabel, 1, 1);
        editPane.add(fAttributeTypeListView, 0, 2);

        selectedCol.setCellValueFactory((CellDataFeatures<ItemAttributeValue, ItemAttributeValue> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<ItemAttributeValue, ItemAttributeValue> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<ItemAttributeValue, String> invoiceNumberCol = new TableColumn<>("Attribute Value");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(ItemAttributeValue_.description.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(300);

        fItemAttributeValueTable.getColumns().add(selectedCol);
        fItemAttributeValueTable.getColumns().add(invoiceNumberCol);
        fItemAttributeValueTable.setPrefHeight(250);
        setTableWidth(fItemAttributeValueTable);
        editPane.add(fItemAttributeValueTable, 1, 2);
        editPane.add(lblWarning, 0, 3, 2, 1);

        return editPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.setHgap(5);
        editPane.setVgap(5);
        Label optionLbl = new Label("Item Attribute Type: ");
        Label valueLbl = new Label("Item Attribute Value: ");
        GridPane.setHalignment(optionLbl, HPos.RIGHT);
        GridPane.setHalignment(valueLbl, HPos.RIGHT);
        editPane.add(optionLbl, 0, 0);
        editPane.add(itemAttributeTypeLabel, 1, 0);
        editPane.add(valueLbl, 0, 1);
        editPane.add(fAttributeValueCombo, 1, 1);
        fAttributeValueCombo.setPrefWidth(250);

        return editPane;
    }

    private void updateAddPane() {
        String sku;
        String description;
        if (fEntity != null) {
            sku = getString(fEntity.getItemLookUpCode());
            description = getString(fEntity.getDescription());
        } else {
            sku = "";
            description = "";
        }
        skuLabel.setText(sku);
        descriptionLabel.setText(description);
        fAttributeTypeListView.resetAttributeType();
    }

    private void updateDisplayOrder() {
        if (!fItemAttributeTable.getItems().isEmpty()) {
            for (int i = 0; i < fItemAttributeTable.getItems().size(); i++) {
                ItemAttribute ia = fItemAttributeTable.getItems().get(i);
                ia.setDisplayOrder(i);
                daoItemAttribute.update(ia);
            }
        }
    }
}

package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.ItemBom_;
import com.salesliant.entity.Item_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getNumberFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.SearchField;
import com.salesliant.validator.InvalidException;
import com.salesliant.validator.ItemValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ItemBomListUI extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final TableView<ItemBom> fItemBomTable = new TableView<>();
    private ObservableList<ItemBom> fItemBomList;
    public HBox actionButtonBox = new HBox();
    private final static String ITEM_TITLE = "Item";
    private final ItemValidator validator = new ItemValidator();
    private static final Logger LOGGER = Logger.getLogger(ItemBomListUI.class.getName());

    public ItemBomListUI() {
        actionButtonBox = createNewEditDeleteDuplicateCloseButtonPane();
        mainView = createMainView();
        loadData();

        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                List<ItemBom> itemBomList = fTableView.getSelectionModel().getSelectedItem().getItemBomBomItems().stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder())).collect(Collectors.toList());
                fItemBomList = FXCollections.observableList(itemBomList);
                fItemBomTable.setItems(fItemBomList);
            } else {
                fItemBomList.clear();
            }
            lblWarning.setText("");
        });
        dialogTitle = ITEM_TITLE;
    }

    private void loadData() {
        List<Item> list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_BOM, Item_.activeTag, true, Item_.description, AppConstants.ORDER_BY_ASC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    ItemBomUI itemBomUI = new ItemBomUI(fEntity);
                    itemBomUI.updateButton.setDisable(true);
                    itemBomUI.updateButton.setVisible(false);
                    fInputDialog = createUIDialog(itemBomUI.getView(), "Component List");
                    itemBomUI.saveButton.setOnAction(event -> {
                        try {
                            itemBomUI.update();
                            validator.validate(fEntity);
                            Item item = daoItem.update(fEntity);
                            if (daoItem.getErrorMessage() == null) {
                                fTableView.getItems().set(fTableView.getSelectionModel().getSelectedIndex(), item);
                                fTableView.getSelectionModel().select(item);
                                List<ItemBom> itemBomList = item.getItemBomBomItems().stream().sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder())).collect(Collectors.toList());
                                fItemBomList = FXCollections.observableList(itemBomList);
                                fItemBomTable.setItems(fItemBomList);
                                fInputDialog.close();
                            } else {
                                itemBomUI.warning.setText(daoItem.getErrorMessage());
                                event.consume();
                            }
                        } catch (InvalidException ex) {
                            itemBomUI.warning.setText(ex.getMessage());
                            event.consume();
                        }
                    });
                    itemBomUI.cancelButton.setOnAction(e -> {
                        fInputDialog.close();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (fEntity.getSalesOrderEntries().isEmpty()) {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            daoItem.delete(fEntity);
                            fEntityList.remove(fEntity);
                            if (fEntityList.isEmpty()) {
                                fTableView.getSelectionModel().select(null);
                            }
                        });
                    } else {
                        showAlertDialog("There are transaction related to this item. You can't delete this item!");
                    }
                }
                break;
            case AppConstants.ACTION_CLONE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    showConfirmDialog("Are you sure to duplicate the seleted entry?", (ActionEvent e) -> {
                        Item oldItem = fTableView.getSelectionModel().getSelectedItem();
                        fEntity = new Item();
                        fEntity.setActiveTag(true);
                        fEntity.setBarcodeFormat(oldItem.getBarcodeFormat());
                        fEntity.setAddToLabelList(oldItem.getAddToLabelList());
                        fEntity.setConsignment(false);
                        fEntity.setDefaultSellQuantity(oldItem.getDefaultSellQuantity());
                        fEntity.setDefaultSupplyQuantity(oldItem.getDefaultSupplyQuantity());
                        fEntity.setDiscountable(oldItem.getDiscountable());
                        fEntity.setFoodStampable(oldItem.getFoodStampable());
                        fEntity.setInternetItem(oldItem.getInternetItem());
                        fEntity.setItemType(oldItem.getItemType());
                        fEntity.setUnitOfMeasure(oldItem.getUnitOfMeasure());
                        fEntity.setSerialNumberCount(oldItem.getSerialNumberCount());
                        fEntity.setTrackQuantity(oldItem.getTrackQuantity());
                        fEntity.setTrackSerialNumumber(oldItem.getTrackSerialNumumber());
                        fEntity.setCategory(oldItem.getCategory());
                        fEntity.setCategoryName(oldItem.getCategoryName());
                        fEntity.setDepartment(oldItem.getDepartment());
                        fEntity.setDepartmentName(oldItem.getDepartmentName());
                        fEntity.setAverageCost(oldItem.getAverageCost());
                        fEntity.setCost(oldItem.getCost());
                        fEntity.setLastCost(oldItem.getLastCost());
                        fEntity.setPrice1(oldItem.getPrice1());
                        fEntity.setPrice2(oldItem.getPrice2());
                        fEntity.setPrice3(oldItem.getPrice3());
                        fEntity.setPrice4(oldItem.getPrice4());
                        fEntity.setPrice5(oldItem.getPrice5());
                        fEntity.setPrice6(oldItem.getPrice6());
                        fEntity.setItemLookUpCode(oldItem.getItemLookUpCode() + "-Copy");
                        fEntity.setDescription(oldItem.getDescription() + "-Copy");
                        List<ItemBom> newList = new ArrayList<>();
                        oldItem.getItemBomBomItems().stream().forEach(ia -> newList.add(ia));
                        newList.forEach((ib) -> {
                            ib.setBomItem(fEntity);
                        });
                        fEntity.setItemBomBomItems(newList);
                        try {
                            validator.validate(fEntity);
                            daoItem.insert(fEntity);
                            if (daoItem.getErrorMessage() == null) {
                                refresh();
                            } else {
                                showAlertDialog("Fail to clone the selected BOM");
                            }
                        } catch (InvalidException ex) {
                            lblWarning.setText(ex.getMessage());
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

        TableColumn<Item, String> lookUpCodeCol = new TableColumn<>("SKU");
        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lookUpCodeCol.setPrefWidth(150);

        TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice1() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER));
        priceCol.setPrefWidth(100);
        priceCol.setSortable(false);

        TableColumn<Item, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getCost() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getCost()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setPrefWidth(100);
        costCol.setSortable(false);

        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(costCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(239);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 1, 2, 1);
        mainPane.add(createItemBomPane(), 0, 2, 2, 1);
        mainPane.add(actionButtonBox, 1, 3);
        mainPane.add(lblWarning, 0, 4, 2, 1);
        return mainPane;
    }

    private HBox createNewEditDeleteDuplicateCloseButtonPane() {
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button duplicateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, fHandler);
        Button refreshButton = ButtonFactory.getButton(ButtonFactory.BUTTON_REFRESH, AppConstants.ACTION_REFRESH, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(duplicateButton, editButton, deleteButton, refreshButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private Node createItemBomPane() {
        GridPane ItemBomPane = new GridPane();
        ItemBomPane.setPadding(new Insets(2));
        ItemBomPane.setHgap(5);
        ItemBomPane.setVgap(5);
        ItemBomPane.setAlignment(Pos.CENTER);
        ItemBomPane.getStyleClass().add("hboxPane");

        TableColumn<ItemBom, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue().getComponentItem() != null && p.getValue().getComponentItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getComponentItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        skuCol.setCellFactory(stringCell(Pos.CENTER));
        skuCol.setPrefWidth(90);
        skuCol.setSortable(false);

        TableColumn<ItemBom, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue().getComponentItem() != null) {
                String description = getString(p.getValue().getComponentItem().getDescription());
                if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                    description = description + "\n" + p.getValue().getLineNote();
                }
                description = description.trim();
                return new SimpleStringProperty(description);
            } else {
                return null;
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);
        descriptionCol.setSortable(false);

        TableColumn<ItemBom, String> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemBom_.quantity.getName()));
        qtyCol.setCellFactory(stringCell(Pos.CENTER));
        qtyCol.setPrefWidth(80);
        qtyCol.setSortable(false);

        TableColumn<ItemBom, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue() != null && p.getValue().getComponentItem() != null && p.getValue().getComponentItem().getPrice1() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getComponentItem().getPrice1()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER));
        priceCol.setPrefWidth(100);
        priceCol.setSortable(false);

        TableColumn<ItemBom, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue().getComponentItem() != null) {
                if (p.getValue().getCost() != null) {
                    return new SimpleStringProperty(getString(p.getValue().getCost()));
                } else {
                    return new SimpleStringProperty(getString(p.getValue().getComponentItem().getCost()));
                }
            } else {
                return null;
            }
        });

        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setPrefWidth(80);
        costCol.setSortable(false);

        fItemBomTable.getColumns().add(skuCol);
        fItemBomTable.getColumns().add(descriptionCol);
        fItemBomTable.getColumns().add(qtyCol);
        fItemBomTable.getColumns().add(priceCol);
        fItemBomTable.getColumns().add(costCol);
        fItemBomTable.setPrefHeight(300);
        setTableWidth(fItemBomTable);
        fItemBomTable.setEditable(false);
        // fItemBomTable.setSortPolicy(null);
        fItemBomTable.setSelectionModel(null);
        fItemBomTable.setFocusTraversable(false);

        ItemBomPane.add(fItemBomTable, 0, 0);
        return ItemBomPane;
    }

}

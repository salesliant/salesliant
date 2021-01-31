package com.salesliant.ui;

import com.salesliant.entity.Category;
import com.salesliant.entity.Category_;
import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.entity.TaxClass;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.widget.CategoryWidget;
import com.salesliant.widget.TaxClassWidget;
import java.math.BigDecimal;
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
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

public class CategoryListUI extends BaseListUI<Category> {

    private final BaseDao<Category> daoCategory = new BaseDao<>(Category.class);
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<TaxClass> daoTaxClass = new BaseDao<>(TaxClass.class);
    private final DataUI dataUI = new DataUI(Category.class);
    private final DataUI itemUI = new DataUI(Item.class);
    private TableView<Item> fItemTableView = new TableView<>();
    private ObservableList<Item> fItemList;
    private final List<TaxClass> taxClassList;
    private final ObservableSet<Item> selectedItems = FXCollections.observableSet();
    private final ComboBox fTaxClassCombo = new TaxClassWidget();
    private final ComboBox fCategoryCombo = new CategoryWidget();
    private final ComboBox<?> fCommissionModeComboBox = DBConstants.getComboBoxTypes(DBConstants.TYPE_COMMISSIONMETHOD);
    private final GridPane fEditPane, fAdjustPane;
    private static final Logger LOGGER = Logger.getLogger(CategoryListUI.class.getName());

    public CategoryListUI() {
        taxClassList = daoTaxClass.read();
        fTableView.setItems(fEntityList);
        mainView = createMainView();
        fEditPane = createEditPane();
        fAdjustPane = createAdjustPane();
        loadData();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Category> observable, Category newValue, Category oldValue) -> {
            if (observable != null && observable.getValue() != null && fTableView.getSelectionModel().getSelectedItem() != null) {
                List<Item> itemList = fTableView.getSelectionModel().getSelectedItem().getItems().stream()
                        .sorted((e1, e2) -> e1.getItemLookUpCode().compareToIgnoreCase(e2.getItemLookUpCode()))
                        .collect(Collectors.toList());
                fItemList = FXCollections.observableList(itemList);
                fItemTableView.setItems(fItemList);
                selectedItems.clear();
            } else {
                fItemList = FXCollections.observableArrayList();
                fItemTableView.setItems(fItemList);
                selectedItems.clear();
            }
        });
    }

    private void loadData() {
        List<Category> list = daoCategory.read().stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Category();
                fEntity.setCommisionMode(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES);
                fEntity.setCommisionPercentSale(BigDecimal.ONE);
                fEntity.setCommisionPercentProfit(BigDecimal.ONE);
                fEntity.setCommissionFixedAmount(BigDecimal.ZERO);
                fEntity.setCommisionMaximumAmount(BigDecimal.ZERO);
                fEntity.setPrice1(new BigDecimal(100));
                fEntity.setPrice2(new BigDecimal(100));
                fEntity.setPrice3(new BigDecimal(100));
                fEntity.setPrice4(new BigDecimal(100));
                fEntity.setPrice5(new BigDecimal(100));
                fEntity.setPrice6(new BigDecimal(100));
                fEntity.setIsAssetTag(Boolean.FALSE);
                fEntity.setIsShippingTag(Boolean.FALSE);
                fEntity.setCountTag(Boolean.TRUE);
                fEntity.setTaxClass(taxClassList.get(0));
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Category");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoCategory.insert(fEntity);
                        if (daoCategory.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.scrollTo(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoCategory.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    dataUI.getTextField(Category_.name).requestFocus();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Category backup = new Category();
                    copyProperties(backup, fEntity);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Category");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCategory.update(fEntity);
                            if (daoCategory.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                                fTableView.scrollTo(fEntity);
                            } else {
                                lblWarning.setText(AppConstants.ERROR_INPUT);
                                copyProperties(fEntity, backup);
                                fTableView.refresh();
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        dataUI.getTextField(Category_.name).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (fEntity.getItems().isEmpty()) {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            daoCategory.delete(fEntity);
                            fEntityList.remove(fEntity);
                            if (fEntityList.isEmpty()) {
                                fTableView.getSelectionModel().select(null);
                            }
                        });
                    } else {
                        showAlertDialog("There are items assigned to this category. You can't delete this category!");
                    }
                }
                break;
            case AppConstants.ACTION_SELECT_ALL:
                fItemTableView.getItems().forEach(e -> {
                    if (selectedItems != null && !selectedItems.contains(e)) {
                        selectedItems.add(e);
                    }
                });
                break;
            case AppConstants.ACTION_UN_SELECT_ALL:
                fItemTableView.getItems().forEach(e -> {
                    if (selectedItems != null && selectedItems.contains(e)) {
                        selectedItems.remove(e);
                    }
                });
                break;
            case AppConstants.ACTION_ADJUST:
                if (!selectedItems.isEmpty()) {
                    Item firstItem = selectedItems.iterator().next();
                    Category oldCategory = firstItem.getCategory();
                    try {
                        itemUI.setData(firstItem);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    Response adjustResponse = createSaveCancelResponseDialog(fAdjustPane, "Item");
                    if (adjustResponse.equals(Response.SAVE)) {
                        try {
                            itemUI.getData(firstItem);
                            Category newCategory = firstItem.getCategory();
                            selectedItems.forEach(e -> {
                                e.setCategory(newCategory);
                                daoItem.update(e);
                                oldCategory.getItems().remove(e);
                                newCategory.getItems().add(e);
                                daoCategory.update(oldCategory);
                                daoCategory.update(newCategory);
                            });
                            loadData();
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
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

        Label tableLbl = new Label("List of Category:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Category, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Category_.name.getName()));
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(80);

        TableColumn<Category, String> taxClassCol = new TableColumn<>("Tax Class");
        taxClassCol.setCellValueFactory((TableColumn.CellDataFeatures<Category, String> p) -> {
            if (p.getValue().getTaxClass() != null && p.getValue().getTaxClass().getName() != null) {
                return new SimpleStringProperty(p.getValue().getTaxClass().getName());
            } else {
                return null;
            }
        });
        taxClassCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        taxClassCol.setPrefWidth(80);

        TableColumn<Category, String> countTagCol = new TableColumn<>("Count");
        countTagCol.setCellValueFactory(new PropertyValueFactory<>(Category_.countTag.getName()));
        countTagCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        countTagCol.setPrefWidth(40);

        TableColumn<Category, String> shippingTagCol = new TableColumn<>("Shipping");
        shippingTagCol.setCellValueFactory(new PropertyValueFactory<>(Category_.isShippingTag.getName()));
        shippingTagCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        shippingTagCol.setPrefWidth(60);

        TableColumn<Category, String> assetTagCol = new TableColumn<>("Asset");
        assetTagCol.setCellValueFactory(new PropertyValueFactory<>(Category_.isAssetTag.getName()));
        assetTagCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        assetTagCol.setPrefWidth(40);

        TableColumn<Category, String> price1Col = new TableColumn<>("Price1MU");
        price1Col.setCellValueFactory(new PropertyValueFactory<>(Category_.price1.getName()));
        price1Col.setCellFactory(stringCell(Pos.CENTER));
        price1Col.setPrefWidth(60);

        TableColumn<Category, String> price2Col = new TableColumn<>("Price2MU");
        price2Col.setCellValueFactory(new PropertyValueFactory<>(Category_.price2.getName()));
        price2Col.setCellFactory(stringCell(Pos.CENTER));
        price2Col.setPrefWidth(60);

        TableColumn<Category, String> price3Col = new TableColumn<>("Price3MU");
        price3Col.setCellValueFactory(new PropertyValueFactory<>(Category_.price3.getName()));
        price3Col.setCellFactory(stringCell(Pos.CENTER));
        price3Col.setPrefWidth(60);

        TableColumn<Category, String> price4Col = new TableColumn<>("Price4MU");
        price4Col.setCellValueFactory(new PropertyValueFactory<>(Category_.price4.getName()));
        price4Col.setCellFactory(stringCell(Pos.CENTER));
        price4Col.setPrefWidth(60);

        TableColumn<Category, String> price5Col = new TableColumn<>("Price5MU");
        price5Col.setCellValueFactory(new PropertyValueFactory<>(Category_.price5.getName()));
        price5Col.setCellFactory(stringCell(Pos.CENTER));
        price5Col.setPrefWidth(60);

        TableColumn<Category, String> price6Col = new TableColumn<>("Price6MU");
        price6Col.setCellValueFactory(new PropertyValueFactory<>(Category_.price6.getName()));
        price6Col.setCellFactory(stringCell(Pos.CENTER));
        price6Col.setPrefWidth(60);

        TableColumn<Category, String> commModeCol = new TableColumn<>("Comm Mode");
        commModeCol.setCellValueFactory((TableColumn.CellDataFeatures<Category, String> p) -> {
            if (p.getValue().getCommisionMode() != null) {
                if (p.getValue().getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_PROFIT)) {
                    return new SimpleStringProperty("By Percent of Profit");
                } else if (p.getValue().getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES)) {
                    return new SimpleStringProperty("By Percent of Sales");
                } else if (p.getValue().getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_PROFIT)) {
                    return new SimpleStringProperty("By Fixed Amount");
                } else {
                    return null;
                }
            } else {
                return null;
            }
        });
        commModeCol.setCellFactory(stringCell(Pos.CENTER));
        commModeCol.setPrefWidth(80);

        TableColumn<Category, String> commProfitCol = new TableColumn<>("Comm Profit");
        commProfitCol.setCellValueFactory(new PropertyValueFactory<>(Category_.commisionPercentProfit.getName()));
        commProfitCol.setCellFactory(stringCell(Pos.CENTER));
        commProfitCol.setPrefWidth(80);

        TableColumn<Category, String> commSalesCol = new TableColumn<>("Comm Sales");
        commSalesCol.setCellValueFactory(new PropertyValueFactory<>(Category_.commisionPercentSale.getName()));
        commSalesCol.setCellFactory(stringCell(Pos.CENTER));
        commSalesCol.setPrefWidth(75);

        TableColumn<Category, String> commFixedCol = new TableColumn<>("Comm Fixed");
        commFixedCol.setCellValueFactory(new PropertyValueFactory<>(Category_.commissionFixedAmount.getName()));
        commFixedCol.setCellFactory(stringCell(Pos.CENTER));
        commFixedCol.setPrefWidth(75);

        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(taxClassCol);
        fTableView.getColumns().add(countTagCol);
        fTableView.getColumns().add(shippingTagCol);
        fTableView.getColumns().add(assetTagCol);
        fTableView.getColumns().add(price1Col);
        fTableView.getColumns().add(price2Col);
        fTableView.getColumns().add(price3Col);
        fTableView.getColumns().add(price4Col);
        fTableView.getColumns().add(price5Col);
        fTableView.getColumns().add(price6Col);
        fTableView.getColumns().add(commModeCol);
        fTableView.getColumns().add(commProfitCol);
        fTableView.getColumns().add(commSalesCol);
        fTableView.getColumns().add(commFixedCol);
        fTableView.setPrefHeight(350);
//        fTableView.setPrefWidth(1063);
        setTableWidth(fTableView);

        GridPane itemPane = new GridPane();
        itemPane.setVgap(3);
        itemPane.setAlignment(Pos.CENTER);

        TableColumn<Item, Item> selectedCol = new TableColumn<>("");
        selectedCol.setCellValueFactory((CellDataFeatures<Item, Item> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<Item, Item> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);
        TableColumn<Item, String> itemSKUCol = new TableColumn<>("Item SKU");
        itemSKUCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        itemSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemSKUCol.setPrefWidth(112);

        TableColumn<Item, String> itemDescriptionCol = new TableColumn<>("Description");
        itemDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        itemDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemDescriptionCol.setPrefWidth(350);

        TableColumn<Item, String> itemPriceCol = new TableColumn<>("Price");
        itemPriceCol.setCellValueFactory(new PropertyValueFactory<>(Item_.price1.getName()));
        itemPriceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        itemPriceCol.setPrefWidth(100);

        TableColumn<Item, String> itemCostCol = new TableColumn<>("Cost");
        itemCostCol.setCellValueFactory(new PropertyValueFactory<>(Item_.cost.getName()));
        itemCostCol.setCellFactory(stringCell(Pos.CENTER));
        itemCostCol.setPrefWidth(100);

        TableColumn<Item, String> itemActiveCol = new TableColumn<>("Active");
        itemActiveCol.setCellValueFactory(new PropertyValueFactory<>(Item_.activeTag.getName()));
        itemActiveCol.setCellFactory(stringCell(Pos.CENTER));
        itemActiveCol.setPrefWidth(120);

        TableColumn<Item, String> itemTypeCol = new TableColumn<>("Type");
        itemTypeCol.setCellValueFactory((CellDataFeatures<Item, String> p) -> {
            if (p.getValue().getItemType() != null && p.getValue().getItemType().equals(DBConstants.ITEM_TYPE_KIT)) {
                return new SimpleStringProperty("Kit");
            } else if (p.getValue().getItemType() != null && p.getValue().getItemType().equals(DBConstants.ITEM_TYPE_STANDARD)) {
                return new SimpleStringProperty("Standard");
            } else if (p.getValue().getItemType() != null && p.getValue().getItemType().equals(DBConstants.ITEM_TYPE_BOM)) {
                return new SimpleStringProperty("Bill of Material");
            } else if (p.getValue().getItemType() != null && p.getValue().getItemType().equals(DBConstants.ITEM_TYPE_MATRIX)) {
                return new SimpleStringProperty("Matrix");
            } else {
                return null;
            }
        });
        itemTypeCol.setCellFactory(stringCell(Pos.CENTER));
        itemTypeCol.setPrefWidth(130);

        fItemTableView.getColumns().add(selectedCol);
        fItemTableView.getColumns().add(itemSKUCol);
        fItemTableView.getColumns().add(itemDescriptionCol);
        fItemTableView.getColumns().add(itemCostCol);
        fItemTableView.getColumns().add(itemPriceCol);
        fItemTableView.getColumns().add(itemActiveCol);
        fItemTableView.getColumns().add(itemTypeCol);
        fItemTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fItemTableView.setPrefHeight(140);
        setTableWidth(fItemTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteButtonPane(), 0, 3);
        mainPane.add(fItemTableView, 0, 4);
        mainPane.add(createAdjustButtonPane(), 0, 5);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        fTaxClassCombo.setPrefWidth(250);
        dataUI.setUIComponent(Category_.taxClass, fTaxClassCombo);
        fCommissionModeComboBox.setPrefWidth(250);
        dataUI.setUIComponent(Category_.commisionMode, fCommissionModeComboBox);
        add(editPane, "Name:*", dataUI.createTextField(Category_.name), fListener, 250.0, 0);
        add(editPane, "Price 1 Markup %:*", dataUI.createTextField(Category_.price1), fListener, 250.0, 1);
        add(editPane, "Price 2 Markup %:*", dataUI.createTextField(Category_.price2), fListener, 250.0, 2);
        add(editPane, "Price 3 Markup %:*", dataUI.createTextField(Category_.price3), fListener, 250.0, 3);
        add(editPane, "Price 4 Markup %:*", dataUI.createTextField(Category_.price4), fListener, 250.0, 4);
        add(editPane, "Price 5 Markup %:*", dataUI.createTextField(Category_.price5), fListener, 250.0, 5);
        add(editPane, "Price 6 Markup %:*", dataUI.createTextField(Category_.price6), fListener, 250.0, 6);
        add(editPane, "Tax Category*", fTaxClassCombo, fListener, 7);
        add(editPane, "Commission Mode:", fCommissionModeComboBox, fListener, 8);
        add(editPane, "Commission on Percent of Sales %:*", dataUI.createTextField(Category_.commisionPercentSale), fListener, 250.0, 9);
        add(editPane, "Commission on Percent of Profit %:*", dataUI.createTextField(Category_.commisionPercentProfit), fListener, 250.0, 10);
        add(editPane, "Commission with Fixed Amount:*", dataUI.createTextField(Category_.commissionFixedAmount), fListener, 250.0, 11);
        add(editPane, "Is Shipping:", dataUI.createCheckBox(Category_.isShippingTag), fListener, 12);
        add(editPane, "Is Asset:", dataUI.createCheckBox(Category_.isAssetTag), fListener, 13);
        add(editPane, "Countable:*", dataUI.createCheckBox(Category_.countTag), fListener, 14);
        editPane.add(lblWarning, 0, 1, 2, 15);

        return editPane;
    }

    private GridPane createAdjustPane() {
        GridPane adjustPane = new GridPane();
        adjustPane.getStyleClass().add("editView");
        fCategoryCombo.setPrefWidth(250);
        itemUI.setUIComponent(Item_.category, fCategoryCombo);
        add(adjustPane, "Category: ", fCategoryCombo, fListener, 1);
        adjustPane.add(lblWarning, 0, 1, 2, 2);

        return adjustPane;
    }

    private HBox createNewEditDeleteButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private HBox createAdjustButtonPane() {
        HBox leftButtonBox = new HBox();
        Button selectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_ALL, AppConstants.ACTION_SELECT_ALL, "Tag All", fHandler);
        Button unSelectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UN_SELECT_ALL, AppConstants.ACTION_UN_SELECT_ALL, "Untag All", fHandler);
        leftButtonBox.getChildren().addAll(selectAllButton, unSelectAllButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button adjustButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADJUST, AppConstants.ACTION_ADJUST, fHandler);
        rightButtonBox.getChildren().addAll(adjustButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            dataUI.getTextField(Category_.name).setText(uppercaseFirst(dataUI.getTextField(Category_.name).getText()));
            saveBtn.setDisable(dataUI.getTextField(Category_.name).getText().trim().isEmpty());
        }
    }
}

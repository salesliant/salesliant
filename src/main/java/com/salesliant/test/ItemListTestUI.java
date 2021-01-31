package com.salesliant.test;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.Item_;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.dateCell;
import static com.salesliant.util.BaseUtil.getNumberFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.ItemSearchField;
import com.salesliant.widget.CategoryWidget;
import com.salesliant.widget.VendorWidget;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ItemListTestUI extends BaseListUI<Item> {

    protected final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    protected final BaseDao<VendorItem> daoVendorItem = new BaseDao<>(VendorItem.class);
    protected final BaseDao<ItemPriceLevel> daoItemPriceLevel = new BaseDao<>(ItemPriceLevel.class);
    protected final DataUI dataUI = new DataUI(Item.class);
    protected final Button fNoteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UPDATE);
    protected final TableView<VendorItem> fVendorItemTable = new TableView<>();
    protected ObservableList<VendorItem> fVendorItemList;
    protected GridPane fEditPane;
    protected final ComboBox fCategoryCombo = new CategoryWidget();
    protected final ComboBox fPrimaryVendorCombo = new VendorWidget();
    protected final ComboBox fCommissionModeCombo = DBConstants.getComboBoxTypes(DBConstants.TYPE_COMMISSIONMETHOD);
    protected final ComboBox fItemTypeCombo = DBConstants.getComboBoxTypes(DBConstants.ITEM_TYPE);
    protected final TabPane fTabPane = new TabPane();
    protected final TextArea fNote = new TextArea(), fStockArea = new TextArea();
    public final Button closeBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
    public HBox actionButtonBox = new HBox();
    protected final Label qtyInStock = new Label(), qtyOnOrder = new Label(), qtyComitted = new Label(), qtyOnRMA = new Label();
    protected static String ITEM_TITLE = "Item";
    protected GridPane fItemInfoPane;
    public TableColumn<Item, Item> selectedCol = new TableColumn<>("");
    protected TableColumn<Item, String> lookUpCodeCol = new TableColumn<>("SKU");
    protected TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
    protected TableColumn<Item, String> qtyCol = new TableColumn<>("Stock");
    protected TableColumn<Item, String> qtyOnOrderCol = new TableColumn<>("On Order");
    protected TableColumn<Item, String> qtyOnRMACol = new TableColumn<>("On RMA");
    protected TableColumn<Item, String> priceCol = new TableColumn<>("Price");
    protected TableColumn<Item, String> costCol = new TableColumn<>("Cost");
    protected TableColumn<Item, String> valueCol = new TableColumn<>("Value");
    protected ObservableSet<Item> selectedItems;
    protected Node oldPlaceHolder;
    protected ProgressIndicator progressIndicator = new ProgressIndicator();
//    protected Task<List<?>> loadDataTask;
    protected Thread loadDataThread;
    protected Boolean itemActiveStatus = true;
    protected ItemSearchField searchField;
    private static final Logger LOGGER = Logger.getLogger(ItemListTestUI.class.getName());

    public ItemListTestUI() {
//        oldPlaceHolder = fTableView.getPlaceholder();
        System.out.println("items list");
//        search
//        fTableView.setPlaceholder(progressIndicator);
//        List<Item> list = daoItem.read(Item_.activeTag, true);
//        fEntityList = FXCollections.observableList(list);
//        fTableView.setItems(fEntityList);
//        fTableView.setPlaceholder(progressIndicator);
//      Task<List<?>> loadDataTask = new Task<List<?>>() {
//            @Override
//            protected List<?> call() throws Exception {
//                List<?> list = daoItem.read(Item_.activeTag, true);
//                return list;
//            }
//        };
//        loadDataTask.setOnSucceeded(e -> {
//            fTableView.getItems().setAll(fEntityList = FXCollections.observableList((List<Item>) loadDataTask.getValue()));
//            fTableView.setPlaceholder(null);
//            loadDataThread = null;
//        });
//        fTableView.setPlaceholder(progressIndicator);
//        loadDataThread = new Thread(loadDataTask);
//        loadDataThread.start();
//        loadData();
        List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.description, AppConstants.ORDER_BY_ASC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        searchField = new ItemSearchField(fTableView);
//        searchField = new ItemSearchField(fTableView);
//        searchField = new SearchField(fTableView);
//        ITEM_TITLE = "Item List";
//        lookUpCodeCol.setPrefWidth(120);
//        descriptionCol.setPrefWidth(250);
//        qtyCol.setPrefWidth(90);
//        qtyOnOrderCol.setPrefWidth(90);
//        qtyOnRMACol.setPrefWidth(90);
//        priceCol.setPrefWidth(100);
//        costCol.setPrefWidth(100);
//        qtyOnOrderCol.setVisible(false);
//        qtyOnRMACol.setVisible(false);
//        valueCol.setVisible(false);
//        fTableView.setPrefWidth(675);
//        fTableView.setPrefHeight(300);
//         mainView = createMainView();
//        searchField = new ItemSearchField(fTableView);
        createGUI();
    }

    private void loadData() {
//        List<Item> list = daoItem.read(Item_.activeTag, true);
//        fTableView.getItems().setAll(fEntityList = FXCollections.observableList(list));
//        fTableView.setItems(fEntityList);
//        Task<List<Item>> loadDataTask = new Task<List<Item>>() {
//            @Override
//            protected List<Item> call() throws Exception {
//                List<Item> list = daoItem.read(Item_.activeTag, true);
//                return list;
//            }
//        };
//        loadDataTask.setOnSucceeded(e -> {
//            fEntityList = FXCollections.observableList(loadDataTask.getValue());
////            fTableView.getItems().setAll(fEntityList = FXCollections.observableList((List<Item>) loadDataTask.getValue()));
//            fTableView.setItems(fEntityList);
////            searchField.setTableView(fTableView);
////            fTableView.setPlaceholder(null);
//            loadDataThread = null;
//        });
//        fTableView.setPlaceholder(progressIndicator);
//        loadDataThread = new Thread(loadDataTask);
//        loadDataThread.start();
        List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.description, AppConstants.ORDER_BY_ASC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        searchField.setTableView(fTableView);

//        fTableView.setPlaceholder(lblLoading);
//        Platform.runLater(() -> {
//            List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.description, AppConstants.ORDER_BY_ASC);
//            fEntityList = FXCollections.observableList(list);
//            fTableView.setItems(fEntityList);
//            searchField.setTableView(fTableView);
//            fTableView.setPlaceholder(null);
//        });
    }

    protected final void createGUI() {
        actionButtonBox = createButtonPane();
        fItemInfoPane = createInfoPane();
        selectedItems = FXCollections.observableSet();
        fNoteButton.setDisable(true);
        fNote.setDisable(true);
        fEditPane = createEditPane();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                if (fEntity != null) {
                    qtyInStock.setText(getString(getQuantity(fEntity)));
                    qtyOnOrder.setText(getString(getQuantityOnOrder(fEntity)));
                    qtyComitted.setText(getString(BaseListUI.getQuantityCommitted(fEntity)));
                    qtyOnRMA.setText(getString(getQuantityOnRMA(fEntity)));
                }
                try {
                    fNote.setDisable(false);
                    fNoteButton.setDisable(false);
                    fNote.setText(fEntity.getNote());
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                List<VendorItem> vendorItemList = new ArrayList<>(fEntity.getVendorItems());
                fVendorItemList = FXCollections.observableList(vendorItemList);
                fVendorItemTable.setItems(fVendorItemList);
            } else {
                fNote.setDisable(true);
                fNoteButton.setDisable(true);
                fVendorItemList.clear();
            }
        });
        fTabPane.getSelectionModel().selectFirst();
        dialogTitle = ITEM_TITLE;
        mainView = createMainView();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                dataUI.getTextField(Item_.itemLookUpCode).setEditable(true);
                fEntity = new Item();
                setDefault();
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Item");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        Item item = daoItem.find(Item_.itemLookUpCode, fEntity.getItemLookUpCode());
                        if (item != null) {
                            lblWarning.setText("The SKU exists");
                            event.consume();
                        } else {
                            Item e = daoItem.insert(fEntity);
                            if (daoItem.getErrorMessage() == null) {
                                if (e.getVendorItemLookUpCode() != null && !e.getVendorItemLookUpCode().isEmpty() && e.getPrimaryVendor() != null) {
                                    VendorItem aVendorItem = new VendorItem();
                                    aVendorItem.setCost(e.getCost());
                                    aVendorItem.setItem(e);
                                    aVendorItem.setVendor(e.getPrimaryVendor());
                                    aVendorItem.setVendorItemLookUpCode(e.getVendorItemLookUpCode());
                                    daoVendorItem.insert(aVendorItem);
                                }
                                fEntityList.add(e);
                                fTableView.getSelectionModel().select(e);
                            } else {
                                lblWarning.setText(daoItem.getErrorMessage());
                                event.consume();
                            }
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(Item_.itemLookUpCode).requestFocus());
                fInputDialog.showAndWait();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    dataUI.getTextField(Item_.itemLookUpCode).setEditable(false);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Item");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            Item e = daoItem.update(fEntity);
                            if (daoItem.getErrorMessage() == null) {
                                fEntity.setVersion(e.getVersion());
                                fTableView.refresh();
                                if (fEntity.getVendorItemLookUpCode() != null && !fEntity.getVendorItemLookUpCode().isEmpty() && fEntity.getPrimaryVendor() != null) {
                                    VendorItem v = null;
                                    for (VendorItem vi : fEntity.getVendorItems()) {
                                        if (vi.getVendor().equals(fEntity.getPrimaryVendor()) && vi.getStore().getId().equals(Config.getStore().getId())) {
                                            v = vi;
                                        }
                                    }
                                    if (v == null) {
                                        v = new VendorItem();
                                        v.setCost(fEntity.getCost());
                                        v.setItem(fEntity);
                                        v.setVendor(fEntity.getPrimaryVendor());
                                        v.setVendorItemLookUpCode(fEntity.getVendorItemLookUpCode());
                                        v.setStore(Config.getStore());
                                        daoVendorItem.insert(v);
                                    } else {
                                        v.setCost(fEntity.getCost());
                                        v.setVendorItemLookUpCode(fEntity.getVendorItemLookUpCode());
                                        daoVendorItem.update(v);
                                    }
                                }
                            } else {
                                lblWarning.setText(daoItem.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(Item_.price1).requestFocus());
                    fInputDialog.showAndWait();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fEntityList.remove(fEntity);
//                    confirm("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
//                        fEntity = daoItem.delete(fEntity);
//                        if (selectedItems.contains(fEntity)) {
//                            selectedItems.remove(fEntity);
//                        }
//                        fTableView.getItems().remove(fEntity);
//                        if (fEntityList.isEmpty()) {
//                            fTableView.getSelectionModel().select(null);
//                        }
//                    });
                }
                break;
            case AppConstants.ACTION_RESET:
                selectedItems.clear();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
//        TextField searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        selectedCol.setCellValueFactory((CellDataFeatures<Item, Item> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<Item, Item> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setVisible(false);
        selectedCol.setSortable(false);

        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER));
        lookUpCodeCol.setPrefWidth(120);

        TableColumn<Item, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        descCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descCol.setPrefWidth(250);

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && getQuantity(p.getValue()) != null) {
                return new SimpleStringProperty(getNumberFormat().format(getQuantity(p.getValue())));
            } else {
                return new SimpleStringProperty(getNumberFormat().format(BigDecimal.ZERO));
            }
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER));
        qtyCol.setPrefWidth(90);

        qtyOnOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && getQuantityOnOrder(p.getValue()) != null) {
                return new SimpleStringProperty(getNumberFormat().format(getQuantityOnOrder(p.getValue())));
            } else {
                return null;
            }
        });
        qtyOnOrderCol.setCellFactory(stringCell(Pos.CENTER));
        qtyOnOrderCol.setPrefWidth(90);

        qtyOnRMACol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && getQuantityOnRMA(p.getValue()) != null) {
                return new SimpleStringProperty(getNumberFormat().format(getQuantityOnRMA(p.getValue())));
            } else {
                return null;
            }
        });
        qtyOnRMACol.setCellFactory(stringCell(Pos.CENTER));
        qtyOnRMACol.setPrefWidth(90);

        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice1() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER));
        priceCol.setPrefWidth(100);

        costCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getCost() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getCost()));
            } else {
                return null;
            }
        });
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setPrefWidth(100);

        valueCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            return new SimpleStringProperty(getNumberFormat().format(getQuantity(p.getValue())));
        });
        valueCol.setCellFactory(stringCell(Pos.CENTER));
        valueCol.setPrefWidth(100);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(qtyOnOrderCol);
        fTableView.getColumns().add(qtyOnRMACol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(valueCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(300);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fItemInfoPane, 0, 3, 2, 1);
        mainPane.add(actionButtonBox, 1, 4);
        return mainPane;
    }

    protected HBox createButtonPane() {
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Active", "Inactive"));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue.equals("Active")) {
                List<Item> list = daoItem.readOrderBy(Item_.activeTag, true, Item_.description, AppConstants.ORDER_BY_ASC);
                fEntityList = FXCollections.observableList(list);
                fTableView.setItems(fEntityList);
            } else {
                List<Item> list = daoItem.readOrderBy(Item_.activeTag, false, Item_.description, AppConstants.ORDER_BY_ASC);
                fEntityList = FXCollections.observableList(list);
                fTableView.setItems(fEntityList);
            }

        }
        );
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(choiceBox, newButton, editButton, deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    protected GridPane createEditPane() {
        GridPane topPane = new GridPane();
        topPane.getStyleClass().add("editView");

        add(topPane, "SKU:*", dataUI.createTextField(Item_.itemLookUpCode), fListener, 145.0, 0);
        add(topPane, "Description:*", dataUI.createTextField(Item_.description), fListener, 440.0, 1);

        fItemTypeCombo.setPrefWidth(145);
        dataUI.setUIComponent(Item_.itemType, fItemTypeCombo);

        fCategoryCombo.setPrefWidth(145);
        dataUI.setUIComponent(Item_.category, fCategoryCombo);

        fPrimaryVendorCombo.setPrefWidth(145);
        dataUI.setUIComponent(Item_.primaryVendor, fPrimaryVendorCombo);

//        dataUI.setUIComponent(Item_.commisionMode, fCommissionModeCombo);
//        fCommissionModeCombo.setPrefWidth(145);
        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");
        leftPane.setVgap(1);
        add(leftPane, "Cost:*", dataUI.createTextField(Item_.cost), fListener, 145.0, 0);
        int i = 1;
        List<ItemPriceLevel> list = daoItemPriceLevel.read();
        for (ItemPriceLevel e : list) {
            if (e.getDescription() != null && !e.getDescription().isEmpty()) {
                i++;
                TextField textField = dataUI.createTextField(e.getCode());
                Label fieldLabel = new Label(e.getDescription());
                textField.textProperty().addListener(fListener);
                textField.setPrefWidth(145.0);
                leftPane.add(fieldLabel, 0, i);
                leftPane.add(textField, 1, i);
                GridPane.setHalignment(fieldLabel, HPos.RIGHT);
                GridPane.setHalignment(textField, HPos.LEFT);
            }
        }
        add(leftPane, "Suggested Retail Price:*", dataUI.createTextField(Item_.suggestedRetailedPrice), fListener, 145.0, i + 1);
        add(leftPane, "Default Sell Qty: ", dataUI.createTextField(Item_.defaultSellQuantity), fListener, 145.0, i + 2);
        add(leftPane, "Reorder Point: ", dataUI.createTextField(Item_.reorderPoint), fListener, 145.0, i + 3);
        add(leftPane, "Restock Level: ", dataUI.createTextField(Item_.restockLevel), fListener, 145.0, i + 4);
        add(leftPane, "Item Type: ", fItemTypeCombo, fListener, i + 5);
        add(leftPane, "Category: ", fCategoryCombo, fListener, i + 6);
        add(leftPane, "Unit of Measure: ", dataUI.createTextField(Item_.unitOfMeasure), fListener, 145.0, i + 7);
        add(leftPane, "Weight: ", dataUI.createTextField(Item_.weight), fListener, 145.0, i + 8);
        add(leftPane, "Manfacture Part Number: ", dataUI.createTextField(Item_.mpn), fListener, 145.0, i + 9);

        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");
        rightPane.setVgap(1);

//        add(rightPane, "Commision Mode: ", fCommissionModeCombo, fListener, 0);
//        add(rightPane, "Commision of Profit%: ", dataUI.createTextField(Item_.commisionPercentProfit), fListener, 145.0, 1);
//        add(rightPane, "Commision of Sales%: ", dataUI.createTextField(Item_.commisionPercentSale), fListener, 145.0, 2);
//        add(rightPane, "Commision Fixed Ammount: ", dataUI.createTextField(Item_.commissionFixedAmount), fListener, 145.0, 3);
        add(rightPane, dataUI.createCheckBox(Item_.activeTag), "Active:", fListener, 4);
        add(rightPane, dataUI.createCheckBox(Item_.fractionTag), "Has Fraction?", fListener, 5);
        add(rightPane, dataUI.createCheckBox(Item_.trackSerialNumumber), "Track Serial Number?", fListener, 6);
        add(rightPane, dataUI.createCheckBox(Item_.trackQuantity), "Track Qty?", fListener, 7);
        add(rightPane, dataUI.createCheckBox(Item_.addToLabelList), "Add to label list?", fListener, 8);
        add(rightPane, dataUI.createCheckBox(Item_.discountable), "Discountable?", fListener, 9);
        add(rightPane, dataUI.createCheckBox(Item_.consignment), "Allow Consignment?", fListener, 10);
        add(rightPane, dataUI.createCheckBox(Item_.internetItem), "Internet Item?", fListener, 11);
        add(rightPane, dataUI.createCheckBox(Item_.foodStampable), "Food Stamble?", fListener, 12);
        add(rightPane, "Primary Vendor: ", fPrimaryVendorCombo, fListener, 13);
        add(rightPane, "Vendor SKU: ", dataUI.createTextField(Item_.vendorItemLookUpCode), fListener, 14);

        GridPane bottomPane = new GridPane();
        bottomPane.getStyleClass().add("editView");
        add(bottomPane, "              Picture Name: ", dataUI.createTextField(Item_.pictureName), fListener, 300.0, 0);

        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        editPane.setVgap(3);
        editPane.add(topPane, 0, 0, 2, 1);
        editPane.add(addHLine(1), 0, 1, 2, 1);
        editPane.add(leftPane, 0, 2);
        editPane.add(rightPane, 1, 2);
        editPane.add(addHLine(1), 0, 3, 2, 1);
        editPane.add(bottomPane, 0, 4, 2, 1);
        editPane.add(lblWarning, 0, 5, 2, 1);
        editPane.add(addHLine(1), 0, 6, 2, 1);

        return editPane;
    }

    protected GridPane createInfoPane() {
        GridPane infoPane = new GridPane();
        infoPane.getStyleClass().add("editView");
        VBox stockBox = new VBox();
        Label stockLabel = new Label("Info");
        stockBox.setSpacing(10);
        GridPane stockPane = new GridPane();
        stockPane.getStyleClass().add("editView");
        stockPane.setPrefSize(160, 125);
        add(stockPane, "Qty In Stock: ", qtyInStock, 0);
        add(stockPane, "Qty On Order: ", qtyOnOrder, 1);
        add(stockPane, "Qty Committed: ", qtyComitted, 2);
        add(stockPane, "Qty On RMA: ", qtyOnRMA, 3);

        fStockArea.setEditable(false);
        fStockArea.setPrefSize(160, 125);
        stockBox.getChildren().addAll(stockLabel, stockPane);

        Tab vendorTab = new Tab(" Vendor ");
        vendorTab.setContent(createVendorItemPane());
        vendorTab.setClosable(false);
        fTabPane.getTabs().add(vendorTab);

        Tab noteTab = new Tab(" Note ");
        noteTab.setContent(createNotePane());
        noteTab.setClosable(false);
        fTabPane.getTabs().add(noteTab);
        fTabPane.setPrefHeight(150);

        infoPane.add(stockBox, 0, 1);
        infoPane.add(fTabPane, 1, 1);

        return infoPane;
    }

    private Node createVendorItemPane() {
        GridPane vendorItemPane = new GridPane();
        vendorItemPane.setPadding(new Insets(5));
        vendorItemPane.setHgap(5);
        vendorItemPane.setVgap(5);
        vendorItemPane.setAlignment(Pos.CENTER);
        vendorItemPane.getStyleClass().add("hboxPane");

        TableColumn<VendorItem, String> vendorNameCol = new TableColumn<>("Vendor");
        vendorNameCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.vendor.getName()));
        vendorNameCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getVendor() != null) {
                return new SimpleStringProperty(p.getValue().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        vendorNameCol.setCellFactory(stringCell(Pos.CENTER));
        vendorNameCol.setPrefWidth(160);

        TableColumn<VendorItem, String> mpnCol = new TableColumn<>("MPN");
        mpnCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.vendorItemLookUpCode.getName()));
        mpnCol.setCellFactory(stringCell(Pos.CENTER));
        mpnCol.setPrefWidth(110);

        TableColumn<VendorItem, String> vendorCostCol = new TableColumn<>("Cost");
        vendorCostCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.cost.getName()));
        vendorCostCol.setCellFactory(stringCell(Pos.CENTER));
        vendorCostCol.setPrefWidth(100);

        TableColumn<VendorItem, String> lastReceviedCol = new TableColumn<>("Last Received");
        lastReceviedCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.lastUpdated.getName()));
        lastReceviedCol.setCellFactory(dateCell(Pos.CENTER));
        lastReceviedCol.setPrefWidth(110);

        fVendorItemTable.getColumns().add(vendorNameCol);
        fVendorItemTable.getColumns().add(mpnCol);
        fVendorItemTable.getColumns().add(vendorCostCol);
        fVendorItemTable.getColumns().add(lastReceviedCol);
        fVendorItemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fVendorItemTable.setPrefHeight(140);
        setTableWidth(fVendorItemTable);

        vendorItemPane.add(fVendorItemTable, 0, 0);
        return vendorItemPane;
    }

    private Node createNotePane() {
        GridPane notePane = new GridPane();
        notePane.getStyleClass().add("editView");
        fNote.setEditable(true);
        fNote.setPrefWidth(380);
        notePane.add(fNote, 0, 1);
        notePane.add(fNoteButton, 1, 1);
        fNoteButton.setId(AppConstants.ACTION_UPDATE);
        fNoteButton.setOnAction(fHandler);
        GridPane.setHalignment(fNoteButton, HPos.RIGHT);
        return notePane;
    }

    private void setDefault() {
        fEntity.setActiveTag(Boolean.TRUE);
//        fEntity.setCommissionFixedAmount(BigDecimal.ZERO);
//        fEntity.setCommisionPercentProfit(BigDecimal.ZERO);
//        fEntity.setCommisionPercentSale(BigDecimal.ZERO);
//        fEntity.setCommisionMode(0);
        fEntity.setConsignment(Boolean.FALSE);
        fEntity.setDefaultSellQuantity(1);
        fEntity.setDiscountable(Boolean.TRUE);
        fEntity.setFoodStampable(Boolean.FALSE);
        fEntity.setInternetItem(Boolean.FALSE);
        fEntity.setItemType(0);
        fEntity.setUnitOfMeasure("Each");
        fEntity.setSerialNumberCount(0);
        fEntity.setTrackQuantity(Boolean.TRUE);
        fEntity.setTrackSerialNumumber(Boolean.FALSE);
    }

    public List<Item> getSelectedItems() {
        ArrayList<Item> list = new ArrayList<>();
        selectedItems.stream().forEach(e -> list.add(e));
        return list;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(Item_.itemLookUpCode).getText().trim().isEmpty() || dataUI.getTextField(Item_.description).getText().trim().isEmpty());
        }
    }
}

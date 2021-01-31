package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Category;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemLog_;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Item_;
import com.salesliant.entity.PurchaseOrderHistoryEntry;
import com.salesliant.entity.PurchaseOrderHistoryEntry_;
import com.salesliant.entity.Store;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.dateCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isInteger;
import static com.salesliant.util.BaseUtil.isNumeric;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.ItemSearchField;
import com.salesliant.validator.InvalidException;
import com.salesliant.validator.ItemValidator;
import com.salesliant.widget.CategoryWidget;
import com.salesliant.widget.VendorWidget;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
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

public class ItemListBaseUI extends BaseListUI<Item> {

    protected final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    protected final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    protected final BaseDao<Store> daoStore = new BaseDao<>(Store.class);
    protected final BaseDao<VendorItem> daoVendorItem = new BaseDao<>(VendorItem.class);
    protected final DataUI dataUI = new DataUI(Item.class);
    protected final Button fNoteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UPDATE);
    protected final TableView<VendorItem> fVendorItemTable = new TableView<>();
    protected final TableView<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryTable = new TableView<>();
    protected ObservableList<VendorItem> fVendorItemList;
    protected ObservableList<PurchaseOrderHistoryEntry> fPurchaseOrderHistoryEntryList;
    protected TableView<ItemLog> fItemLogTableView = new TableView<>();
    protected ObservableList<ItemLog> fItemLogList;
    protected GridPane fEditPane;
    protected final ComboBox<Category> fCategoryCombo = new CategoryWidget();
    protected final ComboBox fPrimaryVendorCombo = new VendorWidget();
    protected final ComboBox fItemTypeCombo = DBConstants.getComboBoxTypes(DBConstants.ITEM_TYPE);
    protected final TabPane fTabPane = new TabPane();
    protected final TextArea fNote = new TextArea(), fStockArea = new TextArea();
    public final Button closeBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
    public HBox actionButtonBox = new HBox();
    protected final Label qtyInStock = new Label(), qtyOnOrder = new Label(), qtyComitted = new Label(), qtyOnRMA = new Label(), markUpValue = new Label();
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
    protected ItemSearchField searchField = new ItemSearchField();
    protected Boolean itemActiveStatus = true;
    private final List<SimpleObjectProperty> muList = new ArrayList<>();
    private final ItemValidator validator = new ItemValidator();
    private static final Logger LOGGER = Logger.getLogger(ItemListBaseUI.class.getName());

    public ItemListBaseUI() {
        ITEM_TITLE = "Item List";
        lookUpCodeCol.setPrefWidth(150);
        descriptionCol.setPrefWidth(250);
        qtyCol.setPrefWidth(100);
        qtyOnOrderCol.setPrefWidth(100);
        qtyOnRMACol.setPrefWidth(100);
        priceCol.setPrefWidth(100);
        costCol.setPrefWidth(100);
        qtyOnOrderCol.setVisible(false);
        qtyOnRMACol.setVisible(false);
        valueCol.setVisible(false);
        Config.getItemPriceLevel().forEach(e -> muList.add(new SimpleObjectProperty<>(BigDecimal.ZERO)));
        fTableView.setPrefHeight(300);
        Platform.runLater(() -> searchField.requestFocus());
    }

    protected final void createGUI() {
        actionButtonBox = createButtonPane();
        fItemInfoPane = createInfoPane();
        mainView = createMainView();
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
                    markUpValue.setText(getString(getMarkUpPercent(fEntity)));
                }
                try {
                    fNote.setDisable(false);
                    fNoteButton.setDisable(false);
                    fNote.setText(fEntity.getNote());
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                List<VendorItem> vendorItemList = fEntity.getVendorItems().stream()
                        .sorted((e1, e2) -> {
                            if (e1.getLastUpdated() == null && e2.getLastUpdated() == null) {
                                return 0;
                            } else if (e1.getLastUpdated() == null && e2.getLastUpdated() != null) {
                                return 1;
                            } else if (e1.getLastUpdated() != null && e2.getLastUpdated() == null) {
                                return -1;
                            } else {
                                return e2.getLastUpdated().compareTo(e1.getLastUpdated());
                            }
                        })
                        .collect(Collectors.toList());
                fVendorItemList = FXCollections.observableList(vendorItemList);
                fVendorItemTable.setItems(fVendorItemList);
                List<ItemLog> itemLogList = fEntity.getItemLogs()
                        .stream()
                        .filter(e -> e.getStore() != null && e.getStore().getId().equals(Config.getStore().getId()))
                        .sorted((e1, e2) -> e2.getDateCreated().compareTo(e1.getDateCreated()))
                        .collect(Collectors.toList());
                fItemLogList = FXCollections.observableList(itemLogList);
                fItemLogTableView.setItems(fItemLogList);
                List<PurchaseOrderHistoryEntry> poeList = fEntity.getPurchaseOrderHistoryEntries()
                        .stream()
                        .filter(e -> e.getQuantityReceived() != null && e.getQuantityReceived().compareTo(BigDecimal.ZERO) != 0)
                        .sorted((e1, e2) -> e2.getDateReceived().compareTo(e1.getDateReceived()))
                        .collect(Collectors.toList());
                fPurchaseOrderHistoryEntryList = FXCollections.observableList(poeList);
                fPurchaseOrderHistoryEntryTable.setItems(fPurchaseOrderHistoryEntryList);
            } else {
                fNote.setDisable(true);
                fNoteButton.setDisable(true);
                fVendorItemList.clear();
                fItemLogList.clear();
                fPurchaseOrderHistoryEntryList.clear();
            }
        });
        fTabPane.getSelectionModel().selectFirst();
        dialogTitle = ITEM_TITLE;
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Item();
                if (Config.getStore().getAutoSkuGeneration()) {
                    while (true) {
                        Random rand = new Random();
                        int num = rand.nextInt(900000) + 100000;
                        String sku = String.format("%06d", num);
                        if (daoItem.find(Item_.itemLookUpCode, sku) == null) {
                            fEntity.setItemLookUpCode(sku);
                            dataUI.getTextField(Item_.itemLookUpCode).setEditable(false);
                            break;
                        }
                    }
                } else {
                    dataUI.getTextField(Item_.itemLookUpCode).setEditable(true);
                }
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
                        if (fEntity.getCategory() != null && fEntity.getCategory().getName() != null) {
                            fEntity.setCategoryName(fEntity.getCategory().getName());
                        }
                        validator.validate(fEntity);
                        daoItem.insert(fEntity);
                        if (daoItem.getErrorMessage() == null) {
                            if (fEntity.getVendorItemLookUpCode() != null && !fEntity.getVendorItemLookUpCode().isEmpty() && fEntity.getPrimaryVendor() != null) {
                                VendorItem aVendorItem = new VendorItem();
                                aVendorItem.setCost(fEntity.getCost());
                                aVendorItem.setItem(fEntity);
                                aVendorItem.setVendor(fEntity.getPrimaryVendor());
                                aVendorItem.setVendorItemLookUpCode(fEntity.getVendorItemLookUpCode());
                                aVendorItem.setMinimumOrder(1);
                                aVendorItem.setPackQuantity(1);
                                aVendorItem.setStore(Config.getStore());
                                daoVendorItem.insert(aVendorItem);
                            }
                            List<Store> storeList = Config.getStoreList();
                            storeList.forEach(s -> {
                                ItemQuantity iq = new ItemQuantity();
                                iq.setQuantity(BigDecimal.ZERO);
                                iq.setReorderPoint(0);
                                iq.setRestockLevel(0);
                                iq.setItem(fEntity);
                                iq.setStore(s);
                                daoItemQuantity.insert(iq);
                            });
                            fEntityList.add(fEntity);
                            fTableView.scrollTo(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoItem.getErrorMessage());
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
                Platform.runLater(() -> {
                    if (Config.getStore().getAutoSkuGeneration()) {
                        dataUI.getTextField(Item_.description).requestFocus();
                    } else {
                        dataUI.getTextField(Item_.itemLookUpCode).requestFocus();
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                        update();
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Item");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            if (fEntity.getCategory() != null && fEntity.getCategory().getName() != null) {
                                fEntity.setCategoryName(fEntity.getCategory().getName());
                            }
                            validator.validate(fEntity);
                            daoItem.update(fEntity);
                            if (daoItem.getErrorMessage() == null) {
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
                                        v.setMinimumOrder(1);
                                        v.setPackQuantity(1);
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
                            if (ex instanceof InvalidException) {
                                lblWarning.setText(ex.getMessage());
                                event.consume();
                            } else {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(Item_.price1).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    if (fEntity.getSalesOrderEntries().isEmpty() && fEntity.getPurchaseOrderEntries().isEmpty()) {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            daoItem.delete(fEntity);
                            if (selectedItems != null && selectedItems.contains(fEntity)) {
                                selectedItems.remove(fEntity);
                            }
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
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getItemType() != DBConstants.ITEM_TYPE_BOM) {
                    AppConstants.Response answer = createConfirmResponseDialog("Are you sure to duplicate the seleted entry?");
                    if (answer.equals(Response.YES)) {
                        Item oldItem = fTableView.getSelectionModel().getSelectedItem();
                        fEntity = new Item();
                        fEntity.setActiveTag(Boolean.TRUE);
                        fEntity.setBarcodeFormat(oldItem.getBarcodeFormat());
                        fEntity.setAddToLabelList(oldItem.getAddToLabelList());
                        fEntity.setConsignment(oldItem.getConsignment());
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
                        fEntity.setDescription(oldItem.getDescription() + "-Copy");
                        if (Config.getStore().getAutoSkuGeneration()) {
                            while (true) {
                                Random rand = new Random();
                                int num = rand.nextInt(900000) + 100000;
                                String sku = String.format("%06d", num);
                                if (daoItem.find(Item_.itemLookUpCode, sku) == null) {
                                    fEntity.setItemLookUpCode(sku);
                                    dataUI.getTextField(Item_.itemLookUpCode).setEditable(false);
                                    break;
                                }
                            }
                        } else {
                            dataUI.getTextField(Item_.itemLookUpCode).setEditable(true);
                            if (oldItem.getItemLookUpCode().length() >= 4 && isInteger(oldItem.getItemLookUpCode().substring(oldItem.getItemLookUpCode().length() - 3))) {
                                String last3Digits = oldItem.getItemLookUpCode().substring(oldItem.getItemLookUpCode().length() - 3);
                                int posA = oldItem.getItemLookUpCode().indexOf(last3Digits);
                                String begin = oldItem.getItemLookUpCode().substring(0, posA);
                                int i = Integer.parseInt(last3Digits);
                                while (true) {
                                    i = i + 1;
                                    String sku = begin + String.valueOf(i);
                                    if (daoItem.find(Item_.itemLookUpCode, sku) == null) {
                                        fEntity.setItemLookUpCode(sku);
                                        break;
                                    }
                                }
                                if (fEntity.getItemLookUpCode() == null) {
                                    fEntity.setItemLookUpCode(oldItem.getItemLookUpCode() + "-Copy");
                                }
                            } else {
                                fEntity.setItemLookUpCode(oldItem.getItemLookUpCode() + "-Copy");
                            }
                        }
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
                                if (fEntity.getCategory() != null && fEntity.getCategory().getName() != null) {
                                    fEntity.setCategoryName(fEntity.getCategory().getName());
                                }
                                validator.validate(fEntity);
                                daoItem.insert(fEntity);
                                if (daoItem.getErrorMessage() == null) {
                                    if (fEntity.getVendorItemLookUpCode() != null && !fEntity.getVendorItemLookUpCode().isEmpty() && fEntity.getPrimaryVendor() != null) {
                                        VendorItem aVendorItem = new VendorItem();
                                        aVendorItem.setCost(fEntity.getCost());
                                        aVendorItem.setItem(fEntity);
                                        aVendorItem.setVendor(fEntity.getPrimaryVendor());
                                        aVendorItem.setVendorItemLookUpCode(fEntity.getVendorItemLookUpCode());
                                        aVendorItem.setMinimumOrder(1);
                                        aVendorItem.setPackQuantity(1);
                                        aVendorItem.setStore(Config.getStore());
                                        daoVendorItem.insert(aVendorItem);
                                    }
                                    List<Store> storeList = Config.getStoreList();
                                    storeList.forEach(s -> {
                                        ItemQuantity iq = new ItemQuantity();
                                        iq.setQuantity(BigDecimal.ZERO);
                                        iq.setReorderPoint(0);
                                        iq.setRestockLevel(0);
                                        iq.setItem(fEntity);
                                        iq.setStore(s);
                                        daoItemQuantity.insert(iq);
                                    });
                                    fEntityList.add(fEntity);
                                    fTableView.scrollTo(fEntity);
                                    fTableView.getSelectionModel().select(fEntity);
                                } else {
                                    lblWarning.setText(daoItem.getErrorMessage());
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
                        Platform.runLater(() -> {
                            if (Config.getStore().getAutoSkuGeneration()) {
                                dataUI.getTextField(Item_.description).requestFocus();
                            } else {
                                dataUI.getTextField(Item_.itemLookUpCode).requestFocus();
                            }
                        });
                        fInputDialog.showDialog();
                    }
                }
                break;
            case AppConstants.ACTION_RESET:
                selectedItems.clear();
                break;
        }
    }

    protected void loadData() {
    }

    protected Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        selectedCol.setCellValueFactory((CellDataFeatures<Item, Item> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<Item, Item> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setVisible(false);
        selectedCol.setSortable(false);

        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lookUpCodeCol.setPrefWidth(150);

        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(330);

        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            return new SimpleStringProperty(getString(getQuantity(p.getValue())));
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(100);

        qtyOnOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && getQuantityOnOrder(p.getValue()) != null) {
                return new SimpleStringProperty(getString(getQuantityOnOrder(p.getValue())));
            } else {
                return null;
            }
        });
        qtyOnOrderCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyOnOrderCol.setPrefWidth(100);

        qtyOnRMACol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && getQuantityOnRMA(p.getValue()) != null) {
                return new SimpleStringProperty(getString(getQuantityOnRMA(p.getValue())));
            } else {
                return null;
            }
        });
        qtyOnRMACol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyOnRMACol.setPrefWidth(100);

        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice1() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        priceCol.setPrefWidth(100);

        costCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getCost() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCost()));
            } else {
                return null;
            }
        });
        costCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        costCol.setPrefWidth(100);

        valueCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            return new SimpleStringProperty(getString(getQuantity(p.getValue())));
        });
        valueCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
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
                itemActiveStatus = true;
                loadData();
            } else {
                itemActiveStatus = false;
                loadData();
            }

        }
        );
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button duplicateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLONE, AppConstants.ACTION_CLONE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(choiceBox, newButton, editButton, deleteButton, duplicateButton, closeButton);
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
        fCategoryCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Category> observable, Category newValue, Category oldValue) -> {
            if (observable != null && observable.getValue() != null && fEntity != null) {
                fEntity.setCategory(fCategoryCombo.getSelectionModel().getSelectedItem());
            }
        });
        dataUI.setUIComponent(Item_.category, fCategoryCombo);

        fPrimaryVendorCombo.setPrefWidth(145);
        dataUI.setUIComponent(Item_.primaryVendor, fPrimaryVendorCombo);

        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");
        leftPane.setVgap(1);
        add(leftPane, "Category: ", fCategoryCombo, fListener, 0);
        add(leftPane, "Cost:*", dataUI.createTextField(Item_.cost), fListener, 145.0, 1);
        int i = 2;
        List<ItemPriceLevel> list = Config.getItemPriceLevel();
        TextField markUpField[] = new TextField[list.size()];
        Label markUpLabel[] = new Label[list.size()];
        for (int j = 0; j < list.size(); j++) {
            i++;
            add(leftPane, list.get(j).getDescription() + " :*", dataUI.createTextField(list.get(j).getCode()), fListener, 145.0, i);
            markUpField[j] = createLabelField();
            markUpField[j].setPrefWidth(60);
            markUpField[j].textProperty().bindBidirectional(muList.get(j), getDecimalFormat());
            markUpLabel[j] = new Label("%");
            markUpLabel[j].setPrefWidth(10);
            markUpField[j].setAlignment(Pos.CENTER_RIGHT);
            markUpLabel[j].setAlignment(Pos.CENTER_LEFT);
            if (j == 0) {
                markUpField[j].textProperty().bindBidirectional(muList.get(j), getDecimalFormat());
            }
            dataUI.getTextField(list.get(j).getCode()).textProperty().addListener((observable, oldValue, newValue) -> {
                update();
            });

            leftPane.add(markUpField[j], 2, i);
            leftPane.add(markUpLabel[j], 3, i);
        }

        add(leftPane, "Suggested Retail Price:*", dataUI.createTextField(Item_.suggestedRetailedPrice), fListener, 145.0, i + 1);
        add(leftPane, "Default Sell Qty: ", dataUI.createTextField(Item_.defaultSellQuantity), fListener, 145.0, i + 2);
        add(leftPane, "Reorder Point: ", dataUI.createTextField(Item_.reorderPoint), fListener, 145.0, i + 3);
        add(leftPane, "Restock Level: ", dataUI.createTextField(Item_.restockLevel), fListener, 145.0, i + 4);
        add(leftPane, "Item Type: ", fItemTypeCombo, fListener, i + 5);
        add(leftPane, "Unit of Measure: ", dataUI.createTextField(Item_.unitOfMeasure), fListener, 145.0, i + 6);
        add(leftPane, "Weight: ", dataUI.createTextField(Item_.weight), fListener, 145.0, i + 7);
        add(leftPane, "Manfacture Part Number: ", dataUI.createTextField(Item_.mpn), fListener, 145.0, i + 8);

        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");
        rightPane.setVgap(1);

        add(rightPane, dataUI.createCheckBox(Item_.activeTag), "Active:", fListener, 0);
        add(rightPane, dataUI.createCheckBox(Item_.fractionTag), "Has Fraction?", fListener, 1);
        add(rightPane, dataUI.createCheckBox(Item_.trackSerialNumumber), "Track Serial Number?", fListener, 2);
        add(rightPane, dataUI.createCheckBox(Item_.trackQuantity), "Track Qty?", fListener, 3);
        add(rightPane, dataUI.createCheckBox(Item_.addToLabelList), "Add to label list?", fListener, 4);
        add(rightPane, dataUI.createCheckBox(Item_.discountable), "Discountable?", fListener, 5);
        add(rightPane, dataUI.createCheckBox(Item_.consignment), "Allow Consignment?", fListener, 6);
        add(rightPane, dataUI.createCheckBox(Item_.internetItem), "Internet Item?", fListener, 7);
        add(rightPane, dataUI.createCheckBox(Item_.foodStampable), "Food Stamble?", fListener, 8);
        add(rightPane, "Primary Vendor: ", fPrimaryVendorCombo, fListener, 9);
        add(rightPane, "Vendor SKU: ", dataUI.createTextField(Item_.vendorItemLookUpCode), fListener, 10);

        GridPane bottomPane = new GridPane();
        bottomPane.getStyleClass().add("editView");
        add(bottomPane, "              Picture Name: ", dataUI.createTextField(Item_.pictureName), fListener, 300.0, 0);

        dataUI.getTextField(Item_.cost).textProperty().addListener((observable, oldValue, newValue) -> {
            if (isNumeric(newValue)) {
                if (fEntity.getId() == null) {
                    BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(newValue));
                    BigDecimal mPrice = BigDecimal.ZERO;
                    if (fEntity.getCategory() != null) {
                        for (ItemPriceLevel e : list) {
                            try {
                                Field muField;
                                muField = fEntity.getCategory().getClass().getDeclaredField(e.getCode());
                                muField.setAccessible(true);
                                BigDecimal mu = (zeroIfNull((BigDecimal) muField.get(fEntity.getCategory()))).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                                BigDecimal price = cost.multiply(mu.add(BigDecimal.ONE));
                                if (price.compareTo(BigDecimal.ZERO) >= 0) {
                                    dataUI.getTextField(e.getCode()).setText(getString(price));
                                    if (price.compareTo(mPrice) > 0) {
                                        mPrice = price;
                                    }
                                }
                            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    String retail = dataUI.getTextField(Item_.suggestedRetailedPrice).getText();
                    if (retail.isEmpty() && mPrice.compareTo(BigDecimal.ZERO) >= 0) {
                        dataUI.getTextField(Item_.suggestedRetailedPrice).setText(getString(mPrice));
                    } else if (!retail.isEmpty() && isNumeric(retail)) {
                        BigDecimal retailPrice = BigDecimal.valueOf(Double.parseDouble(retail));
                        if (retailPrice.compareTo(mPrice) < 0 && mPrice.compareTo(BigDecimal.ZERO) > 0) {
                            dataUI.getTextField(Item_.suggestedRetailedPrice).setText(getString(mPrice));
                        }
                    }
                } else {
                    update();
                }
            }
        });

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
        add(stockPane, "Markup Percent: ", markUpValue, 4);

        fStockArea.setEditable(false);
        fStockArea.setPrefSize(160, 125);
        stockBox.getChildren().addAll(stockLabel, stockPane);

        Tab vendorTab = new Tab(" Vendor ");
        vendorTab.setContent(createVendorItemPane());
        vendorTab.setClosable(false);
        fTabPane.getTabs().add(vendorTab);

        Tab poTab = new Tab(" PO History ");
        poTab.setContent(createPOHistoryPane());
        poTab.setClosable(false);
        fTabPane.getTabs().add(poTab);

        Tab logTab = new Tab(" Transaction Log ");
        logTab.setContent(createItemLogPane());
        logTab.setClosable(false);
        fTabPane.getTabs().add(logTab);

        Tab noteTab = new Tab(" Note ");
        noteTab.setContent(createNotePane());
        noteTab.setClosable(false);
        fTabPane.getTabs().add(noteTab);
        fTabPane.setPrefHeight(150);

        infoPane.add(stockBox, 0, 1);
        infoPane.add(fTabPane, 1, 1);

        return infoPane;
    }

    protected Node createVendorItemPane() {
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
        vendorNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorNameCol.setPrefWidth(160);

        TableColumn<VendorItem, String> mpnCol = new TableColumn<>("MPN");
        mpnCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.vendorItemLookUpCode.getName()));
        mpnCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        mpnCol.setPrefWidth(200);

        TableColumn<VendorItem, String> vendorCostCol = new TableColumn<>("Cost");
        vendorCostCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.cost.getName()));
        vendorCostCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        vendorCostCol.setPrefWidth(100);

        TableColumn<VendorItem, String> lastReceviedCol = new TableColumn<>("Last Received");
        lastReceviedCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.lastUpdated.getName()));
        lastReceviedCol.setCellFactory(dateCell(Pos.CENTER_LEFT));
        lastReceviedCol.setPrefWidth(120);

        fVendorItemTable.getColumns().add(vendorNameCol);
        fVendorItemTable.getColumns().add(mpnCol);
        fVendorItemTable.getColumns().add(lastReceviedCol);
        fVendorItemTable.getColumns().add(vendorCostCol);
        fVendorItemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fVendorItemTable.setPrefHeight(140);
        setTableWidth(fVendorItemTable);

        vendorItemPane.add(fVendorItemTable, 0, 0);
        return vendorItemPane;
    }

    protected Node createPOHistoryPane() {
        GridPane poHistoryPane = new GridPane();
        poHistoryPane.setPadding(new Insets(5));
        poHistoryPane.setHgap(5);
        poHistoryPane.setVgap(5);
        poHistoryPane.setAlignment(Pos.CENTER);
        poHistoryPane.getStyleClass().add("hboxPane");

        TableColumn<PurchaseOrderHistoryEntry, String> poeVendorNameCol = new TableColumn<>("Vendor");
        poeVendorNameCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getPurchaseOrderHistory().getVendor() != null) {
                return new SimpleStringProperty(p.getValue().getPurchaseOrderHistory().getVendor().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        poeVendorNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        poeVendorNameCol.setPrefWidth(160);

        TableColumn<PurchaseOrderHistoryEntry, String> poNumberCol = new TableColumn<>("PO Number");
        poNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getPurchaseOrderHistory() != null && p.getValue().getPurchaseOrderHistory().getPurchaseOrderNumber() != null) {
                return new SimpleStringProperty(p.getValue().getPurchaseOrderHistory().getPurchaseOrderNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        poNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        poNumberCol.setPrefWidth(120);

        TableColumn<PurchaseOrderHistoryEntry, String> invoiceNumberCol = new TableColumn<>("Invoice Number");
        invoiceNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<PurchaseOrderHistoryEntry, String> p) -> {
            if (p.getValue().getPurchaseOrderHistory() != null && p.getValue().getPurchaseOrderHistory().getVendorInvoiceNumber() != null) {
                return new SimpleStringProperty(p.getValue().getPurchaseOrderHistory().getVendorInvoiceNumber());
            } else {
                return new SimpleStringProperty("");
            }
        });
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(120);

        TableColumn<PurchaseOrderHistoryEntry, String> poeQtyCol = new TableColumn<>("Qty");
        poeQtyCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.quantityReceived.getName()));
        poeQtyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        poeQtyCol.setPrefWidth(60);

        TableColumn<PurchaseOrderHistoryEntry, String> poeDateReceviedCol = new TableColumn<>("Date Received");
        poeDateReceviedCol.setCellValueFactory(new PropertyValueFactory<>(PurchaseOrderHistoryEntry_.dateReceived.getName()));
        poeDateReceviedCol.setCellFactory(dateCell(Pos.CENTER_LEFT));
        poeDateReceviedCol.setPrefWidth(120);

        fPurchaseOrderHistoryEntryTable.getColumns().add(poeVendorNameCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(poNumberCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(invoiceNumberCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(poeQtyCol);
        fPurchaseOrderHistoryEntryTable.getColumns().add(poeDateReceviedCol);
        fPurchaseOrderHistoryEntryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fPurchaseOrderHistoryEntryTable.setPrefHeight(140);
        setTableWidth(fPurchaseOrderHistoryEntryTable);

        poHistoryPane.add(fPurchaseOrderHistoryEntryTable, 0, 0);
        return poHistoryPane;
    }

    protected Node createItemLogPane() {
        GridPane logPane = new GridPane();
        logPane.setPadding(new Insets(5));
        logPane.setHgap(5);
        logPane.setVgap(5);
        logPane.setAlignment(Pos.CENTER);
        logPane.getStyleClass().add("hboxPane");

        TableColumn<ItemLog, String> itemLogDescriptionCol = new TableColumn<>("Type");
        itemLogDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.description.getName()));
        itemLogDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemLogDescriptionCol.setPrefWidth(150);

        TableColumn<ItemLog, String> itemLogInvoiceCol = new TableColumn<>("Number");
        itemLogInvoiceCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.transactionNumber.getName()));
        itemLogInvoiceCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemLogInvoiceCol.setPrefWidth(120);

        TableColumn<ItemLog, String> itemLogDateCol = new TableColumn<>("Date");
        itemLogDateCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.dateCreated.getName()));
        itemLogDateCol.setCellFactory(stringCell(Pos.CENTER));
        itemLogDateCol.setPrefWidth(130);

        TableColumn<ItemLog, String> itemLogItemQtyCol = new TableColumn<>("Before Qty");
        itemLogItemQtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.beforeQuantity.getName()));
        itemLogItemQtyCol.setCellFactory(stringCell(Pos.CENTER));
        itemLogItemQtyCol.setPrefWidth(90);

        TableColumn<ItemLog, String> itemLogQtyCol = new TableColumn<>("After Qty");
        itemLogQtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.afterQuantity.getName()));
        itemLogQtyCol.setCellFactory(stringCell(Pos.CENTER));
        itemLogQtyCol.setPrefWidth(90);

        fItemLogTableView.getColumns().add(itemLogDescriptionCol);
        fItemLogTableView.getColumns().add(itemLogInvoiceCol);
        fItemLogTableView.getColumns().add(itemLogDateCol);
        fItemLogTableView.getColumns().add(itemLogItemQtyCol);
        fItemLogTableView.getColumns().add(itemLogQtyCol);
        fItemLogTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fItemLogTableView.setPrefHeight(140);
        setTableWidth(fItemLogTableView);
        logPane.add(fItemLogTableView, 0, 0);
        return logPane;
    }

    protected Node createNotePane() {
        GridPane notePane = new GridPane();
        notePane.getStyleClass().add("editView");
        fNote.setEditable(true);
        fNote.setPrefWidth(380);
        fNote.setWrapText(true);
        notePane.add(fNote, 0, 1);
        notePane.add(fNoteButton, 1, 1);
        fNoteButton.setId(AppConstants.ACTION_UPDATE);
        fNoteButton.setOnAction(fHandler);
        GridPane.setHalignment(fNoteButton, HPos.RIGHT);
        return notePane;
    }

    protected void setDefault() {
        fEntity.setActiveTag(Boolean.TRUE);
        fEntity.setConsignment(Boolean.FALSE);
        fEntity.setDefaultSellQuantity(1);
        fEntity.setDiscountable(Boolean.TRUE);
        fEntity.setFoodStampable(Boolean.FALSE);
        fEntity.setInternetItem(Boolean.FALSE);
        fEntity.setItemType(DBConstants.ITEM_TYPE_STANDARD);
        fEntity.setUnitOfMeasure("Each");
        fEntity.setSerialNumberCount(0);
        fEntity.setTrackQuantity(Boolean.TRUE);
        fEntity.setTrackSerialNumumber(Boolean.FALSE);
        fEntity.setFractionTag(Boolean.FALSE);
        fEntity.setAddToLabelList(Boolean.TRUE);
    }

    public List<Item> getSelectedItems() {
        ArrayList<Item> list = new ArrayList<>();
        selectedItems.stream().forEach(e -> list.add(e));
        return list;
    }

    private void update() {
        List<ItemPriceLevel> list = Config.getItemPriceLevel();
        for (int i = 0; i < list.size(); i++) {
            if (isNumeric(dataUI.getTextField(list.get(i).getCode()).getText()) && isNumeric(dataUI.getTextField(Item_.cost).getText())) {
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(dataUI.getTextField(list.get(i).getCode()).getText()));
                BigDecimal cost = BigDecimal.valueOf(Double.parseDouble(dataUI.getTextField(Item_.cost).getText()));
                if (cost != null && cost.compareTo(BigDecimal.ZERO) != 0) {
                    BigDecimal markUp = (price.subtract(cost)).divide(cost, 2, RoundingMode.HALF_UP).multiply(new BigDecimal(100));
                    muList.get(i).set(markUp);
                } else {
                    muList.get(i).set(new BigDecimal(9999));
                }
            } else {
                muList.get(i).set(BigDecimal.ZERO);
            }
        }
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(Item_.itemLookUpCode).getText().trim().isEmpty());
        }
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.Item_;
import com.salesliant.entity.Promotion;
import com.salesliant.entity.Promotion_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class ItemPromotionListUI extends BaseListUI<Promotion> {

    private final BaseDao<Promotion> daoPromotion = new BaseDao<>(Promotion.class);
    private final DataUI dataUI = new DataUI(Promotion.class);
    private final DataUI itemUI = new DataUI(Item.class);
    private final GridPane fEditPane;
    private Item fItem;
    private static final Logger LOGGER = Logger.getLogger(ItemPromotionListUI.class.getName());

    public ItemPromotionListUI() {
        mainView = createMainView();
        List<Promotion> list = daoPromotion.readOrderBy(Promotion_.store, Config.getStore(), Promotion_.startTime, AppConstants.ORDER_BY_ASC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        dialogTitle = "Promotion";
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Promotion();
                fEntity.setStore(Config.getStore());
                fEntity.setStartTime(new Date());
                fEntity.setEndTime(new Date());
                handleAction(AppConstants.ACTION_SELECT_LIST);
                fInputDialog = createSaveCancelUIDialog(fEditPane, dialogTitle);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        fEntity.setStore(Config.getStore());
                        daoPromotion.insert(fEntity);
                        if (daoPromotion.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.refresh();
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoPromotion.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> {
                    dataUI.getTextField(Promotion_.price1).requestFocus();
                });
                fInputDialog.showDialog();
                break;

            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Item item = fEntity.getItem();
                    try {
                        itemUI.setData(item);
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Promotion");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoPromotion.update(fEntity);
                            if (daoPromotion.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                            } else {
                                lblWarning.setText(daoPromotion.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> {
                        dataUI.getTextField(Promotion_.price1).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoPromotion.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
            case AppConstants.ACTION_SELECT_LIST:
                ItemListUI itemListUI = new ItemListUI();
                itemListUI.actionButtonBox.setDisable(true);
                TableView<Item> itemTable = itemListUI.getTableView();
                Response selectResponse = createSelectCancelResponseDialog(itemListUI.getView(), "Item");
                selectBtn.setDisable(true);
                itemTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        Item item = observable.getValue();
                        if (item.getPromotions() != null && !item.getPromotions().isEmpty()) {
                            selectBtn.setDisable(true);
                        } else {
                            selectBtn.setDisable(false);
                        }
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                if (selectResponse.equals(Response.SELECT)) {
                    fItem = itemTable.getSelectionModel().getSelectedItem();
                    fEntity.setItem(fItem);
                    fEntity.setPrice1(fItem.getPrice1());
                    fEntity.setPrice2(fItem.getPrice2());
                    fEntity.setPrice3(fItem.getPrice3());
                    fEntity.setPrice4(fItem.getPrice4());
                    fEntity.setPrice5(fItem.getPrice5());
                    fEntity.setPrice6(fItem.getPrice6());
                    fEntity.setEnabled(Boolean.TRUE);
                    try {
                        itemUI.setData(fEntity.getItem());
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
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
        Label PromotionLbl = new Label("List of Promotion:");
        mainPane.add(PromotionLbl, 0, 1);
        GridPane.setHalignment(PromotionLbl, HPos.LEFT);

        TableColumn<Promotion, String> itemSKUCol = new TableColumn<>("SKU");
        itemSKUCol.setCellValueFactory((CellDataFeatures<Promotion, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        itemSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemSKUCol.setPrefWidth(85);

        TableColumn<Promotion, String> itemDescriptionCol = new TableColumn<>("Description");
        itemDescriptionCol.setCellValueFactory((CellDataFeatures<Promotion, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        itemDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemDescriptionCol.setPrefWidth(220);

        TableColumn<Promotion, String> startDateCol = new TableColumn<>("Start Date");
        startDateCol.setCellValueFactory(new PropertyValueFactory<>(Promotion_.startTime.getName()));
        startDateCol.setCellFactory(stringCell(Pos.CENTER));
        startDateCol.setPrefWidth(80);

        TableColumn<Promotion, String> endDateCol = new TableColumn<>("End Date");
        endDateCol.setCellValueFactory(new PropertyValueFactory<>(Promotion_.endTime.getName()));
        endDateCol.setCellFactory(stringCell(Pos.CENTER));
        endDateCol.setPrefWidth(90);

        TableColumn<Promotion, String> enabledCol = new TableColumn<>("Enabled");
        enabledCol.setCellValueFactory(new PropertyValueFactory<>(Promotion_.enabled.getName()));
        enabledCol.setCellFactory(stringCell(Pos.CENTER));
        enabledCol.setPrefWidth(60);

        fTableView.getColumns().add(itemSKUCol);
        fTableView.getColumns().add(itemDescriptionCol);
        fTableView.getColumns().add(enabledCol);
        fTableView.getColumns().add(startDateCol);
        fTableView.getColumns().add(endDateCol);
        List<ItemPriceLevel> list = Config.getItemPriceLevel();
        TableColumn<Promotion, String> priceTableCol[] = new TableColumn[list.size()];
        for (int i = 0; i < list.size(); i++) {
            priceTableCol[i] = new TableColumn<>(list.get(i).getDescription());
            if (list.get(i).getCode().equalsIgnoreCase("price1")) {
                priceTableCol[i].setCellValueFactory(new PropertyValueFactory<>(Promotion_.price1.getName()));
            } else if (list.get(i).getCode().equalsIgnoreCase("price2")) {
                priceTableCol[i].setCellValueFactory(new PropertyValueFactory<>(Promotion_.price2.getName()));
            } else if (list.get(i).getCode().equalsIgnoreCase("price3")) {
                priceTableCol[i].setCellValueFactory(new PropertyValueFactory<>(Promotion_.price3.getName()));
            } else if (list.get(i).getCode().equalsIgnoreCase("price4")) {
                priceTableCol[i].setCellValueFactory(new PropertyValueFactory<>(Promotion_.price4.getName()));
            } else if (list.get(i).getCode().equalsIgnoreCase("price5")) {
                priceTableCol[i].setCellValueFactory(new PropertyValueFactory<>(Promotion_.price5.getName()));
            } else if (list.get(i).getCode().equalsIgnoreCase("price6")) {
                priceTableCol[i].setCellValueFactory(new PropertyValueFactory<>(Promotion_.price6.getName()));
            }
            priceTableCol[i].setCellFactory(stringCell(Pos.CENTER));
            priceTableCol[i].setPrefWidth(95);
            fTableView.getColumns().add(priceTableCol[i]);
        }

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(400);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        GridPane topPane = new GridPane();
        topPane.getStyleClass().add("editView");
        Label itemLabel = new Label("Item Information:");
        topPane.add(itemLabel, 0, 0);
        add(topPane, "Item SKU:", itemUI.createLabelTextField(Item_.itemLookUpCode, 250), 1);
        add(topPane, "Item Description:", itemUI.createLabelTextField(Item_.description, 250.0), 2);
        int i = 3;
        List<ItemPriceLevel> list = Config.getItemPriceLevel();
        for (int j = 0; j < list.size(); j++) {
            i++;
            add(topPane, list.get(j).getDescription() + " :*", itemUI.createTextField(list.get(j).getCode()), fListener, 250.0, i);
        }
        topPane.setAlignment(Pos.TOP_LEFT);

        GridPane promotionPane = new GridPane();
        promotionPane.getStyleClass().add("editView");
        DatePicker startTime = new DatePicker();
        dataUI.setUIComponent(Promotion_.startTime, startTime);
        startTime.setPrefWidth(100);
        DatePicker endTime = new DatePicker();
        dataUI.setUIComponent(Promotion_.endTime, endTime);
        endTime.setPrefWidth(100);
        Label promotionLabel = new Label("Promotion Information:");
        promotionPane.add(promotionLabel, 0, 0);
        add(promotionPane, dataUI.createCheckBox(Promotion_.enabled), "Enable :*", fListener, 1);
        add(promotionPane, "Start Date:*", startTime, fListener, 2);
        add(promotionPane, "End Date:*", endTime, fListener, 3);
        int k = 4;
        for (int j = 0; j < list.size(); j++) {
            k++;
            add(promotionPane, list.get(j).getDescription() + " :*", dataUI.createTextField(list.get(j).getCode()), fListener, 250.0, k);
        }
        promotionPane.setAlignment(Pos.TOP_LEFT);

        editPane.add(topPane, 0, 0);
        editPane.add(promotionPane, 0, 1);
        editPane.add(lblWarning, 0, 2);

        return editPane;
    }

    private Boolean checkDate() {
        DatePicker startTimeDatePicker = (DatePicker) dataUI.getUIComponent(Promotion_.startTime);
        DatePicker endTimeDatePicker = (DatePicker) dataUI.getUIComponent(Promotion_.endTime);
        if (startTimeDatePicker != null && endTimeDatePicker != null && startTimeDatePicker.getValue() != null && endTimeDatePicker.getValue() != null) {
            LocalDate startLocalDate = startTimeDatePicker.getValue();
            LocalDate endLocalDate = endTimeDatePicker.getValue();
            Instant startInstant = Instant.from(startLocalDate.atStartOfDay(ZoneId.systemDefault()));
            Instant endInstant = Instant.from(endLocalDate.atStartOfDay(ZoneId.systemDefault()));
            Date startDate = Date.from(startInstant);
            Date endDate = Date.from(endInstant);
            if (endDate.after(startDate)) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            Boolean disable = false;
            List<ItemPriceLevel> list = Config.getItemPriceLevel();
            for (int i = 0; i < list.size(); i++) {
                if (dataUI.getTextField(list.get(i).getCode()).getText().trim().isEmpty()) {
                    disable = true;
                }
            }
            saveBtn.setDisable(itemUI.getTextField(Item_.itemLookUpCode).getText().trim().isEmpty() || disable || !checkDate());
        }
    }
}

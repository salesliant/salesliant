package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.ItemQuantity_;
import com.salesliant.entity.Item_;
import com.salesliant.report.ItemCountListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemCountListUI extends ItemListBaseUI {

    private final GridPane fAdjustPane;
    private final DataUI itemQuantityDataUI = new DataUI(ItemQuantity.class);
    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private static final Logger LOGGER = Logger.getLogger(ItemCountListUI.class.getName());

    public ItemCountListUI() {
        loadData();
        createGUI();
        fAdjustPane = createAdjustPane();
        ITEM_TITLE = "Physical Inventory";
        priceCol.setVisible(false);
        costCol.setVisible(false);
        lookUpCodeCol.setPrefWidth(150);
        descriptionCol.setPrefWidth(350);
        qtyCol.setPrefWidth(150);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(455);
    }

    @Override
    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.activeTag, true, Item_.trackQuantity, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            List<Item> countableList = list.stream()
                    .filter(e -> e.getCategory() != null && e.getCategory().getCountTag() != null && e.getCategory().getCountTag())
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(countableList);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADJUST:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    ItemQuantity iq = getItemQuantity(fEntity);
                    BigDecimal qty = iq.getQuantity();
                    try {
                        itemQuantityDataUI.setData(iq);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fAdjustPane, "Item");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            BigDecimal beforeQty = iq.getQuantity();
                            itemQuantityDataUI.getData(iq);
                            daoItemQuantity.update(iq);
                            BigDecimal afterQty = iq.getQuantity();
                            if (daoItemQuantity.getErrorMessage() == null) {
                                ItemLog itemLog = new ItemLog();
                                itemLog.setDateCreated(new Timestamp(new Date().getTime()));
                                itemLog.setDescription(DBConstants.ITEM_LOG_DESCRIPTION_ADJUST);
                                itemLog.setItem(iq.getItem());
                                itemLog.setCost(iq.getItem().getCost());
                                itemLog.setItemCost(iq.getItem().getCost());
                                itemLog.setPrice(iq.getItem().getPrice1());
                                itemLog.setItemPrice(iq.getItem().getPrice1());
                                itemLog.setStore(Config.getStore());
                                itemLog.setTransactionNumber(Config.getEmployee().getNameOnSalesOrder());
                                itemLog.setBeforeQuantity(beforeQty);
                                itemLog.setAfterQuantity(afterQty);
                                itemLog.setEmployee(Config.getEmployee());
                                itemLog.setTransferType(DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_ADJUSTMENT);
                                daoItemLog.insert(itemLog);
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoItemQuantity.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> itemQuantityDataUI.getTextField(ItemQuantity_.quantity).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_RELOAD:
                loadData();
                fTableView.requestFocus();
                break;

            case AppConstants.ACTION_PRINT_REPORT:
                if (!fEntityList.isEmpty()) {
                    ItemCountListReportLayout layout = new ItemCountListReportLayout(fEntityList);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
        }
    }

    @Override
    protected final HBox createButtonPane() {
        Button reloadButton = ButtonFactory.getButton(ButtonFactory.BUTTON_RELOAD, AppConstants.ACTION_RELOAD, fHandler);
        Button adjustButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADJUST, AppConstants.ACTION_ADJUST, fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT_REPORT, AppConstants.ACTION_PRINT_REPORT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(reloadButton, adjustButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    @Override
    protected GridPane createInfoPane() {
        GridPane infoPane = new GridPane();
        infoPane.setMaxHeight(0);
        return infoPane;
    }

    private GridPane createAdjustPane() {
        GridPane adjustPane = new GridPane();
        adjustPane.getStyleClass().add("editView");

        add(adjustPane, "New Quantity:*", itemQuantityDataUI.createTextField(ItemQuantity_.quantity), fListener, 145.0, 0);
        adjustPane.add(lblWarning, 0, 2, 2, 1);

        return adjustPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(itemQuantityDataUI.getTextField(ItemQuantity_.quantity).getText().trim().isEmpty());
        }
    }
}

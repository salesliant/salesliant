package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.report.ItemBackOrderListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemBackOrderListUI extends ItemListBaseUI {

    private static final Logger LOGGER = Logger.getLogger(ItemBackOrderListUI.class.getName());

    public ItemBackOrderListUI() {
        loadData();
        createGUI();
        ITEM_TITLE = "Items On Order";
        priceCol.setVisible(false);
        costCol.setVisible(false);
        qtyOnOrderCol.setVisible(true);
        lookUpCodeCol.setPrefWidth(150);
        descriptionCol.setPrefWidth(300);
        qtyCol.setPrefWidth(100);
        qtyOnOrderCol.setPrefWidth(100);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(455);
    }

    @Override
    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.activeTag, true, Item_.trackQuantity, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            List<Item> countableList = list.stream()
                    .filter(e -> e.getCategory() != null && e.getCategory().getCountTag() != null && e.getCategory().getCountTag()
                    && getQuantity(e).compareTo(BigDecimal.ZERO) < 0)
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
            case AppConstants.ACTION_RELOAD:
                loadData();
                fTableView.requestFocus();
                break;

            case AppConstants.ACTION_PRINT:
                if (!fEntityList.isEmpty()) {
                    ItemBackOrderListReportLayout layout = new ItemBackOrderListReportLayout(fEntityList);
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
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT_REPORT, AppConstants.ACTION_PRINT_REPORT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(reloadButton, printButton, closeButton);
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
}

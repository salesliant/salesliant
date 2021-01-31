package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.Item_;
import com.salesliant.report.ItemValueListReportLayout;
import com.salesliant.util.AppConstants;
import static com.salesliant.util.BaseUtil.getNumberFormat;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemValueListUI extends ItemListBaseUI {

    private final TextField totalValueField = new TextField();
    private final TextField totalQtyField = new TextField();
    private static final Logger LOGGER = Logger.getLogger(ItemValueListUI.class.getName());

    public ItemValueListUI() {
        loadData();
        createGUI();
        ITEM_TITLE = "Item Value List";
        priceCol.setVisible(false);
        valueCol.setVisible(true);
        lookUpCodeCol.setPrefWidth(120);
        descriptionCol.setPrefWidth(250);
        qtyCol.setPrefWidth(100);
        costCol.setPrefWidth(100);
        valueCol.setPrefWidth(110);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(445);
    }

    @Override
    protected final void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD,
                    Item_.activeTag, true, Item_.trackQuantity, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            List<Item> countableList = list.stream()
                    .filter(e -> e.getCategory() != null && e.getCategory().getCountTag() != null && e.getCategory().getCountTag()
                    && e.getCost() != null && getQuantity(e).compareTo(BigDecimal.ZERO) > 0)
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(countableList);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            Function<Item, BigDecimal> totalVMapper = item -> item.getCost().multiply(getQuantity(item));
            Function<Item, BigDecimal> totalQMapper = item -> getQuantity(item);
            BigDecimal totalv = countableList.stream()
                    .map(totalVMapper)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal totalq = countableList.stream()
                    .map(totalQMapper)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalValueField.setText(getNumberFormat().format(totalv));
            totalQtyField.setText(getNumberFormat().format(totalq));
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

            case AppConstants.ACTION_PRINT_REPORT:
                if (!fEntityList.isEmpty()) {
                    ItemValueListReportLayout layout = new ItemValueListReportLayout(fEntityList);
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
        Label totalValueLabel = new Label("Total Value: ");
        Label totalQtyLabel = new Label("Total Qty: ");
        Label spaceLabel = new Label("     ");
        totalValueField.setEditable(false);
        totalQtyField.setEditable(false);

        infoPane.add(totalQtyLabel, 0, 0);
        infoPane.add(totalQtyField, 1, 0);
        infoPane.add(spaceLabel, 2, 0);
        infoPane.add(totalValueLabel, 3, 0);
        infoPane.add(totalValueField, 4, 0);

        infoPane.setMaxHeight(10);
        totalQtyField.setAlignment(Pos.CENTER_RIGHT);
        totalValueField.setAlignment(Pos.CENTER_RIGHT);
        infoPane.setAlignment(Pos.TOP_RIGHT);
        return infoPane;
    }
}

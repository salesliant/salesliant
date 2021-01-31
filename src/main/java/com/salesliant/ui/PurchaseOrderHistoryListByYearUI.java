package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.report.PurchaseOrderHistoryListByYearReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getMonthDateFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.getYearDateFormat;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class PurchaseOrderHistoryListByYearUI extends BaseListUI<PurchaseOrderHistory> {

    private final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private List<List<PurchaseOrderHistory>> fList = new ArrayList<>();
    private final TableView<List<PurchaseOrderHistory>> fSummaryTable = new TableView<>();
    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderHistoryListByYearUI.class.getName());

    public PurchaseOrderHistoryListByYearUI() {
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            Map<String, List<PurchaseOrderHistory>> groupByYear = daoPurchaseOrderHistory.readOrderBy(PurchaseOrderHistory_.store, Config.getStore(), PurchaseOrderHistory_.datePurchased, AppConstants.ORDER_BY_ASC)
                    .stream()
                    .filter(e -> e.getDatePurchased() != null)
                    .collect(Collectors.groupingBy(e -> getYearDateFormat().format(e.getDatePurchased())));
            fList = new ArrayList<>(groupByYear.values())
                    .stream()
                    .sorted((e1, e2) -> e2.get(0).getDatePurchased().compareTo(e1.get(0).getDatePurchased())).collect(Collectors.toList());
            fSummaryTable.getItems().setAll(FXCollections.observableList(fList));
            fSummaryTable.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (!fList.isEmpty()) {
                    PurchaseOrderHistoryListByYearReportLayout layout = new PurchaseOrderHistoryListByYearReportLayout(fList);
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

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        TableColumn<List<PurchaseOrderHistory>, String> monthCol = new TableColumn<>("Year");
        monthCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String year = getMonthDateFormat().format(p.getValue().get(0).getDatePurchased());
                return new SimpleStringProperty(year);
            } else {
                return null;
            }
        });
        monthCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        monthCol.setPrefWidth(150);

        TableColumn<List<PurchaseOrderHistory>, String> numberOfOrderCol = new TableColumn<>("Qty");
        numberOfOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String size = String.valueOf(p.getValue().size());
                return new SimpleStringProperty(size);
            } else {
                return null;
            }
        });
        numberOfOrderCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        numberOfOrderCol.setPrefWidth(120);

        TableColumn<List<PurchaseOrderHistory>, String> salesCol = new TableColumn<>("Amount");
        salesCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(total));
            } else {
                return null;
            }
        }
        );
        salesCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        salesCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(monthCol);
        fSummaryTable.getColumns().add(numberOfOrderCol);
        fSummaryTable.getColumns().add(salesCol);

        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(550);

        mainPane.add(fSummaryTable, 0, 2);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;

    }
}

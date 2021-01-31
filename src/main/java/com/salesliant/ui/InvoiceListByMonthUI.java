package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.InvoiceListByMonthReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getMonthDateFormat;
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
import javafx.beans.property.ReadOnlyObjectWrapper;
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

public class InvoiceListByMonthUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private List<List<Invoice>> fSummaryList = new ArrayList<>();
    private final TableView<List<Invoice>> fSummaryTable = new TableView<>();
    private static final Logger LOGGER = Logger.getLogger(InvoiceListByMonthUI.class.getName());

    public InvoiceListByMonthUI() {
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            Map<String, List<Invoice>> groupByMonth = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_ASC)
                    .stream()
                    .filter(e -> e.getDateInvoiced() != null)
                    .collect(Collectors.groupingBy(e -> getMonthDateFormat().format(e.getDateInvoiced())));
            List<List<Invoice>> list = new ArrayList<>(groupByMonth.values())
                    .stream()
                    .sorted((e1, e2) -> e2.get(0).getDateInvoiced().compareTo(e1.get(0).getDateInvoiced())).collect(Collectors.toList());
            fSummaryList = FXCollections.observableList(list);
            fSummaryTable.getItems().setAll(fSummaryList);
            fSummaryTable.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (!fSummaryList.isEmpty()) {
                    InvoiceListByMonthReportLayout layout = new InvoiceListByMonthReportLayout(fSummaryList);
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

        TableColumn<List<Invoice>, String> monthCol = new TableColumn<>("Month");
        monthCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String year = getMonthDateFormat().format(p.getValue().get(0).getDateInvoiced());
                return new SimpleStringProperty(year);
            } else {
                return null;
            }
        });
        monthCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        monthCol.setPrefWidth(140);

        TableColumn<List<Invoice>, String> numberOfOrderCol = new TableColumn<>("Number of Order");
        numberOfOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String size = String.valueOf(p.getValue().size());
                return new SimpleStringProperty(size);
            } else {
                return null;
            }
        });
        numberOfOrderCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        numberOfOrderCol.setPrefWidth(150);

        TableColumn<List<Invoice>, String> numberOfReturnCol = new TableColumn<>("Number of Return");
        numberOfReturnCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                long size = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) < 0)
                        .count();
                return new SimpleStringProperty(String.valueOf(size));
            } else {
                return null;
            }
        });
        numberOfReturnCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        numberOfReturnCol.setPrefWidth(140);

        TableColumn<List<Invoice>, BigDecimal> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(150);

        fSummaryTable.getColumns().add(monthCol);
        fSummaryTable.getColumns().add(numberOfOrderCol);
        fSummaryTable.getColumns().add(numberOfReturnCol);
        fSummaryTable.getColumns().add(amountCol);

        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(545);

        mainPane.add(fSummaryTable, 0, 2);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 4);
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

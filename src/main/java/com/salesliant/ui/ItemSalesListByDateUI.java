package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.ItemSalesListByDateReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemSalesListByDateUI extends BaseListUI<InvoiceEntry> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private List<List<InvoiceEntry>> fList = new ArrayList<>();
    private final TableView<List<InvoiceEntry>> fSummaryTable = new TableView<>();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final Label rangeLabel = new Label();
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(ItemSalesListByDateUI.class.getName());

    public ItemSalesListByDateUI() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
        fFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
        fTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            Map<String, List<InvoiceEntry>> groupByItem = getData(fFrom, fTo)
                    .stream()
                    .filter(e -> e.getItemLookUpCode() != null && !e.getItemLookUpCode().isEmpty())
                    .filter(e -> e.getQuantity() != null && e.getQuantity().compareTo(BigDecimal.ZERO) >= 0)
                    .collect(Collectors.groupingBy(e -> e.getItemLookUpCode()));
            fList = new ArrayList<>(groupByItem.values())
                    .stream()
                    .sorted((e1, e2) -> {
                        BigDecimal t1 = e1.stream()
                                .map(e -> e.getQuantity())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal t2 = e2.stream()
                                .map(e -> e.getQuantity())
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        return t2.compareTo(t1);
                    }).collect(Collectors.toList());
            fSummaryTable.getItems().setAll(FXCollections.observableList(fList));
            updateRange();
            fSummaryTable.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT:
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fSummaryTable.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fList.isEmpty()) {
                    ItemSalesListByDateReportLayout layout = new ItemSalesListByDateReportLayout(fList, fFrom, fTo);
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
        mainPane.setVgap(5.0);

        TableColumn<List<InvoiceEntry>, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                return new SimpleStringProperty(p.getValue().get(0).getItemLookUpCode());
            } else {
                return null;
            }
        });
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        skuCol.setPrefWidth(110);

        TableColumn<List<InvoiceEntry>, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                return new SimpleStringProperty(p.getValue().get(0).getItemDescription());
            } else {
                return null;
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(300);

        TableColumn<List<InvoiceEntry>, String> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal qty = p.getValue()
                        .stream()
                        .filter(e -> e.getQuantity().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getQuantity())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(qty));
            } else {
                return null;
            }
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(100);

        TableColumn<List<InvoiceEntry>, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getPrice().compareTo(BigDecimal.ZERO) >= 0 && e.getQuantity().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getPrice().multiply(e.getQuantity()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(total));
            } else {
                return null;
            }
        }
        );
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(skuCol);
        fSummaryTable.getColumns().add(descriptionCol);
        fSummaryTable.getColumns().add(qtyCol);
        fSummaryTable.getColumns().add(amountCol);

        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        fSummaryTable.setPrefHeight(550);
        setTableWidth(fSummaryTable);

        rangeLabel.setTextAlignment(TextAlignment.LEFT);
        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fSummaryTable, 0, 2);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;

    }

    private List<InvoiceEntry> getData(LocalDateTime from, LocalDateTime to) {
        List<Invoice> invoiceList = daoInvoice.readBetweenDate(InvoiceEntry_.store, Config.getStore(), Invoice_.dateInvoiced, fFrom, fTo);
        List<InvoiceEntry> list = new ArrayList<>();
        invoiceList.stream().forEach(e -> list.addAll(e.getInvoiceEntries()));

        return list;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

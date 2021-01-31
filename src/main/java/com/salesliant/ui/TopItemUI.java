package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.TopItemReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
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

public class TopItemUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private List<List<InvoiceEntry>> fSummaryList = new ArrayList<>();
    private List<List<InvoiceEntry>> fGroupList = new ArrayList<>();
    private final TableView<List<InvoiceEntry>> fSummaryTable = new TableView<>();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final Label rangeLabel = new Label();
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(TopItemUI.class.getName());

    public TopItemUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Invoice> invoiceList = daoInvoice.readBetweenDate(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, fFrom, fTo);
            List<InvoiceEntry> invoiceEntryList = new ArrayList<>();
            invoiceList.forEach(e -> {
                invoiceEntryList.addAll(e.getInvoiceEntries());
            });
            Map<String, List<InvoiceEntry>> groupByItem = invoiceEntryList
                    .stream()
                    .filter(e -> e.getItemLookUpCode() != null)
                    .filter(e -> e.getQuantity() != null)
                    .collect(Collectors.groupingBy(e -> e.getItemLookUpCode()));
            fGroupList = new ArrayList<>(groupByItem.values())
                    .stream()
                    .sorted((e1, e2) -> {
                        BigDecimal total1 = e1.stream().map(e -> e.getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal total2 = e2.stream().map(e -> e.getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        return total2.compareTo(total1);
                    })
                    .limit(100)
                    .collect(Collectors.toList());
            fSummaryList = FXCollections.observableList(fGroupList);
            fSummaryTable.getItems().setAll(fSummaryList);
            updateRange();
            fSummaryTable.setPlaceholder(null);
        });
    }

    private void setDefaultDate() {
        Timestamp fromTimestamp = daoInvoice.findMinTimestamp(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced);
        if (fromTimestamp != null) {
            fFrom = fromTimestamp.toLocalDateTime();
        } else {
            fFrom = (new Timestamp((new Date()).getTime())).toLocalDateTime();
        }
        fTo = (new Timestamp((new Date()).getTime())).toLocalDateTime();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT:
                fSummaryTable.setPlaceholder(null);
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fSummaryTable.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    handleAction(AppConstants.ACTION_CLOSE_DIALOG);
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fSummaryList.isEmpty()) {
                    TopItemReportLayout layout = new TopItemReportLayout(fGroupList, fFrom, fTo);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        TableColumn<List<InvoiceEntry>, String> itemDescriptionCol = new TableColumn<>("Item Description");
        itemDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getItemDescription() != null) {
                return new SimpleStringProperty(p.getValue().get(0).getItemDescription());
            } else {
                return null;
            }
        });
        itemDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemDescriptionCol.setPrefWidth(300);

        TableColumn<List<InvoiceEntry>, String> itemSKUCol = new TableColumn<>("Item SKU");
        itemSKUCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().get(0).getItemLookUpCode());
            } else {
                return null;
            }
        });
        itemSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemSKUCol.setPrefWidth(120);

        TableColumn<List<InvoiceEntry>, String> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal qty = p.getValue()
                        .stream()
                        .filter(e -> e.getPrice() != null)
                        .filter(e -> e.getQuantity() != null)
                        .map(e -> e.getQuantity())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(qty));
            } else {
                return null;
            }
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(120);

        TableColumn<List<InvoiceEntry>, String> salesCol = new TableColumn<>("Sales");
        salesCol.setCellValueFactory((TableColumn.CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getPrice() != null)
                        .filter(e -> e.getQuantity() != null)
                        .map(e -> e.getPrice().multiply(e.getPrice()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(total));
            } else {
                return null;
            }
        }
        );
        salesCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        salesCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(itemDescriptionCol);
        fSummaryTable.getColumns().add(itemSKUCol);
        fSummaryTable.getColumns().add(qtyCol);
        fSummaryTable.getColumns().add(salesCol);
        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fSummaryTable.setPrefHeight(545);
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

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

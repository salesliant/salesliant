package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.report.TopVendorReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
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

public class TopVendorUI extends BaseListUI<PurchaseOrderHistory> {

    protected final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private List<List<PurchaseOrderHistory>> fSummaryList = new ArrayList<>();
    private List<List<PurchaseOrderHistory>> fGroupList = new ArrayList<>();
    private List<PurchaseOrderHistory> fPurchaseOrderHistoryList;
    private final TableView<List<PurchaseOrderHistory>> fSummaryTable = new TableView<>();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final Label rangeLabel = new Label();
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(TopVendorUI.class.getName());

    public TopVendorUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            fPurchaseOrderHistoryList = daoPurchaseOrderHistory.readBetweenDate(PurchaseOrderHistory_.store, Config.getStore(), PurchaseOrderHistory_.dateReceived, fFrom, fTo);
            Map<String, List<PurchaseOrderHistory>> groupByVendor = fPurchaseOrderHistoryList
                    .stream()
                    .filter(e -> e.getVendorName() != null)
                    .collect(Collectors.groupingBy(e -> e.getVendorName()));
            fGroupList = new ArrayList<>(groupByVendor.values())
                    .stream()
                    .sorted((e1, e2) -> {
                        BigDecimal total1 = e1.stream().map(e -> e.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        BigDecimal total2 = e2.stream().map(e -> e.getTotal()).reduce(BigDecimal.ZERO, BigDecimal::add);
                        return total2.compareTo(total1);
                    })
                    .collect(Collectors.toList());
            fSummaryList = FXCollections.observableList(fGroupList);
            fSummaryTable.getItems().setAll(fSummaryList);
            updateRange();
            fSummaryTable.setPlaceholder(null);
        });
    }

    private void setDefaultDate() {
        Date fromDate = daoPurchaseOrderHistory.findMinDate(PurchaseOrderHistory_.store, Config.getStore(), PurchaseOrderHistory_.dateReceived);
        if (fromDate != null) {
            Instant instant = Instant.ofEpochMilli(fromDate.getTime());
            LocalDateTime fromLocaldate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
            fFrom = fromLocaldate;
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
                    TopVendorReportLayout layout = new TopVendorReportLayout(fGroupList, fFrom, fTo);
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

        TableColumn<List<PurchaseOrderHistory>, String> vendorNameCol = new TableColumn<>("Vendor");
        vendorNameCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getVendorName() != null) {
                return new SimpleStringProperty(p.getValue().get(0).getVendorName());
            } else {
                return null;
            }
        });
        vendorNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorNameCol.setPrefWidth(300);

        TableColumn<List<PurchaseOrderHistory>, String> vendorCodeCol = new TableColumn<>("Account");
        vendorCodeCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getVendorCode() != null) {
                return new SimpleStringProperty(p.getValue().get(0).getVendorCode());
            } else {
                return null;
            }
        });
        vendorCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorCodeCol.setPrefWidth(120);

        TableColumn<List<PurchaseOrderHistory>, String> numberOfPurchaseOrderCol = new TableColumn<>("Qty");
        numberOfPurchaseOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String size = String.valueOf(p.getValue().size());
                return new SimpleStringProperty(size);
            } else {
                return null;
            }
        });
        numberOfPurchaseOrderCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        numberOfPurchaseOrderCol.setPrefWidth(120);

        TableColumn<List<PurchaseOrderHistory>, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
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
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(vendorNameCol);
        fSummaryTable.getColumns().add(vendorCodeCol);
        fSummaryTable.getColumns().add(numberOfPurchaseOrderCol);
        fSummaryTable.getColumns().add(amountCol);
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

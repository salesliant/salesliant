package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.report.PurchaseOrderHistoryListByDateReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class PurchaseOrderHistoryListByDateUI extends BaseListUI<PurchaseOrderHistory> {

    private final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private List<List<PurchaseOrderHistory>> fList = new ArrayList<>();
    private final TableView<List<PurchaseOrderHistory>> fSummaryTable = new TableView<>();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(PurchaseOrderHistoryListByDateUI.class.getName());

    public PurchaseOrderHistoryListByDateUI() {
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            Map<String, List<PurchaseOrderHistory>> groupByDate = daoPurchaseOrderHistory.readOrderBy(PurchaseOrderHistory_.store, Config.getStore(), PurchaseOrderHistory_.datePurchased, AppConstants.ORDER_BY_ASC)
                    .stream()
                    .filter(e -> e.getDatePurchased() != null)
                    .collect(Collectors.groupingBy(e -> getDateDateFormat().format(e.getDatePurchased())));
            fList = new ArrayList<>(groupByDate.values())
                    .stream()
                    .sorted((e1, e2) -> e2.get(0).getDatePurchased().compareTo(e1.get(0).getDatePurchased())).collect(Collectors.toList());
            fSummaryTable.getItems().setAll(FXCollections.observableList(fList));
            fSummaryTable.setPlaceholder(null);
        });
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
                    Timestamp from = Timestamp.valueOf(fFrom);
                    Timestamp to = Timestamp.valueOf(fTo);
                    List<List<PurchaseOrderHistory>> listList = fList
                            .stream()
                            .filter(e -> e.get(0).getDatePurchased().after(from) && e.get(0).getDatePurchased().before(to))
                            .collect(Collectors.toList());
                    fSummaryTable.getItems().setAll(FXCollections.observableList(listList));
                });
                cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    handleAction(AppConstants.ACTION_CLOSE_DIALOG);
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fList.isEmpty()) {
                    PurchaseOrderHistoryListByDateReportLayout layout = new PurchaseOrderHistoryListByDateReportLayout(fList);
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

        TableColumn<List<PurchaseOrderHistory>, String> monthCol = new TableColumn<>("Date");
        monthCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String year = getDateDateFormat().format(p.getValue().get(0).getDatePurchased());
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

        TableColumn<List<PurchaseOrderHistory>, String> amountCol = new TableColumn<>("Month To Date");
        amountCol.setCellValueFactory((TableColumn.CellDataFeatures<List<PurchaseOrderHistory>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                LocalDate date = p.getValue().get(0).getDatePurchased().toLocalDateTime().toLocalDate();
                LocalDate firstDayofMonth = date.withDayOfMonth(1).minusDays(1);
                LocalDateTime thisMonthFrom = firstDayofMonth.atTime(LocalTime.MAX);
                LocalDateTime thisMonthTo = date.atTime(LocalTime.MAX);
                Timestamp from = Timestamp.valueOf(thisMonthFrom);
                Timestamp to = Timestamp.valueOf(thisMonthTo);
                BigDecimal total = fList
                        .stream()
                        .filter(e -> e.get(0).getDatePurchased().after(from) && e.get(0).getDatePurchased().before(to))
                        .map(e -> {
                            BigDecimal amount = e
                                    .stream()
                                    .map(g -> g.getTotal())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            return amount;
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(total));
            } else {
                return new SimpleStringProperty(getString(BigDecimal.ZERO));
            }
        }
        );
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(monthCol);
        fSummaryTable.getColumns().add(numberOfOrderCol);
        fSummaryTable.getColumns().add(salesCol);
        fSummaryTable.getColumns().add(amountCol);

        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(550);

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
}

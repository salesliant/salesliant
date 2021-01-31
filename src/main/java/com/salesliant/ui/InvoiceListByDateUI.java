package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.InvoiceListByDateReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDateDateFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

public class InvoiceListByDateUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private List<List<Invoice>> fSummaryList = new ArrayList<>();
    private List<List<Invoice>> fList = new ArrayList<>();
    private final TableView<List<Invoice>> fSummaryTable = new TableView<>();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(InvoiceListByDateUI.class.getName());

    public InvoiceListByDateUI() {
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            Map<String, List<Invoice>> groupByDate = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_ASC)
                    .stream()
                    .filter(e -> e.getDateInvoiced() != null)
                    .collect(Collectors.groupingBy(e -> getDateDateFormat().format(e.getDateInvoiced())));
            fList = new ArrayList<>(groupByDate.values())
                    .stream()
                    .sorted((e1, e2) -> e2.get(0).getDateInvoiced().compareTo(e1.get(0).getDateInvoiced())).collect(Collectors.toList());
            fSummaryList = FXCollections.observableList(fList);
            fSummaryTable.getItems().setAll(fSummaryList);
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
                    List<List<Invoice>> listList = fList
                            .stream()
                            .filter(e -> e.get(0).getDateInvoiced().after(from) && e.get(0).getDateInvoiced().before(to))
                            .collect(Collectors.toList());
                    fSummaryTable.getItems().setAll(fSummaryList = FXCollections.observableList(listList));
                });
                cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    handleAction(AppConstants.ACTION_CLOSE_DIALOG);
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fSummaryList.isEmpty()) {
                    InvoiceListByDateReportLayout layout = new InvoiceListByDateReportLayout(fSummaryList);
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

        TableColumn<List<Invoice>, String> monthCol = new TableColumn<>("Date");
        monthCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String year = getDateDateFormat().format(p.getValue().get(0).getDateInvoiced());
                return new SimpleStringProperty(year);
            } else {
                return null;
            }
        });
        monthCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        monthCol.setPrefWidth(110);

        TableColumn<List<Invoice>, String> numberOfOrderCol = new TableColumn<>("Qty");
        numberOfOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String size = String.valueOf(p.getValue().size());
                return new SimpleStringProperty(size);
            } else {
                return null;
            }
        });
        numberOfOrderCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        numberOfOrderCol.setPrefWidth(80);

        TableColumn<List<Invoice>, String> salesCol = new TableColumn<>("Sales");
        salesCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
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
        salesCol.setPrefWidth(110);

        TableColumn<List<Invoice>, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal cost = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getCost())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(cost));
            } else {
                return null;
            }
        }
        );
        costCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        costCol.setPrefWidth(110);

        TableColumn<List<Invoice>, String> profitCol = new TableColumn<>("Profit");
        profitCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal profit = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> zeroIfNull(e.getTotal()).subtract(zeroIfNull(e.getCost())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(profit));
            } else {
                return null;
            }
        }
        );
        profitCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        profitCol.setPrefWidth(110);

        TableColumn<List<Invoice>, String> marginCol = new TableColumn<>("Margin");
        marginCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal cost = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> e.getCost())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal profit = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) >= 0)
                        .map(e -> zeroIfNull(e.getTotal()).subtract(zeroIfNull(e.getCost())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal margin = BigDecimal.ZERO;
                if (cost.compareTo(BigDecimal.ZERO) != 0) {
                    margin = profit.divide(cost, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
                }
                return new SimpleStringProperty(getString(margin));
            } else {
                return null;
            }
        }
        );
        marginCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        marginCol.setPrefWidth(80);

        TableColumn<List<Invoice>, String> returnCol = new TableColumn<>("Return");
        returnCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal returns = p.getValue()
                        .stream()
                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) < 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(returns));
            } else {
                return null;
            }
        }
        );
        returnCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        returnCol.setPrefWidth(110);

        TableColumn<List<Invoice>, String> netAmountCol = new TableColumn<>("Net-Amount");
        netAmountCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal amount = p.getValue()
                        .stream()
                        //                        .filter(e -> e.getTotal().compareTo(BigDecimal.ZERO) < 0)
                        .map(e -> e.getTotal())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(amount));
            } else {
                return null;
            }
        }
        );
        netAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        netAmountCol.setPrefWidth(110);

        TableColumn<List<Invoice>, String> amountCol = new TableColumn<>("Month To Date");
        amountCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                LocalDate date = p.getValue().get(0).getDateInvoiced().toLocalDateTime().toLocalDate();
                LocalDate firstDayofMonth = date.withDayOfMonth(1);
                LocalDateTime thisMonthFrom = firstDayofMonth.atTime(0, 0, 0, 0);
                LocalDateTime thisMonthTo = date.atTime(LocalTime.MAX);
                Timestamp from = Timestamp.valueOf(thisMonthFrom);
                Timestamp to = Timestamp.valueOf(thisMonthTo);
                BigDecimal total = fSummaryList
                        .stream()
                        .filter(e -> e.get(0).getDateInvoiced().after(from) && e.get(0).getDateInvoiced().before(to))
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
        amountCol.setPrefWidth(110);

        fSummaryTable.getColumns().add(monthCol);
        fSummaryTable.getColumns().add(numberOfOrderCol);
        fSummaryTable.getColumns().add(salesCol);
        fSummaryTable.getColumns().add(costCol);
        fSummaryTable.getColumns().add(profitCol);
        fSummaryTable.getColumns().add(marginCol);
        fSummaryTable.getColumns().add(returnCol);
        fSummaryTable.getColumns().add(netAmountCol);
        fSummaryTable.getColumns().add(amountCol);

        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(545);

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

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.TopServiceReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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

public class TopServiceRepsUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private List<List<Invoice>> fSummaryList = new ArrayList<>();
    private List<List<Invoice>> fGroupList = new ArrayList<>();
    private List<Invoice> fInvoiceList;
    private final TableView<List<Invoice>> fSummaryTable = new TableView<>();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final Label rangeLabel = new Label();
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(TopServiceRepsUI.class.getName());

    public TopServiceRepsUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            fInvoiceList = daoInvoice.readBetweenDate(Invoice_.store, Config.getStore(), Invoice_.invoiceType, DBConstants.TYPE_SALESORDER_SERVICE, Invoice_.dateInvoiced, fFrom, fTo);
            Map<String, List<Invoice>> groupByEmployee = fInvoiceList
                    .stream()
                    .filter(e -> e.getDateInvoiced() != null && e.getSalesName() != null && !e.getSalesName().isEmpty())
                    .collect(Collectors.groupingBy(e -> e.getSalesName()));
            fGroupList = new ArrayList<>(groupByEmployee.values())
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
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.add(Calendar.MONTH, -1);
        aCalendar.set(Calendar.DATE, 1);
        Date firstDateOfPreviousMonth = aCalendar.getTime();
        aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDateOfPreviousMonth = aCalendar.getTime();

        LocalDate firstLocalDateOfPreviousMonth = firstDateOfPreviousMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate lastLocalDateOfPreviousMonth = lastDateOfPreviousMonth.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        fFrom = firstLocalDateOfPreviousMonth.atTime(0, 0, 0, 0);
        fTo = lastLocalDateOfPreviousMonth.atTime(LocalTime.MAX);
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
                    TopServiceReportLayout layout = new TopServiceReportLayout(fGroupList, fFrom, fTo);
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

        TableColumn<List<Invoice>, String> monthCol = new TableColumn<>("Name");
        monthCol.setCellValueFactory((TableColumn.CellDataFeatures<List<Invoice>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                String name = p.getValue().get(0).getSalesName();
                return new SimpleStringProperty(name);
            } else {
                return null;
            }
        });
        monthCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        monthCol.setPrefWidth(300);

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
        numberOfOrderCol.setPrefWidth(120);

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
        salesCol.setPrefWidth(150);

        fSummaryTable.getColumns().add(monthCol);
        fSummaryTable.getColumns().add(numberOfOrderCol);
        fSummaryTable.getColumns().add(salesCol);
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

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

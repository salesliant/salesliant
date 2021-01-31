package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Commission;
import com.salesliant.entity.Commission_;
import com.salesliant.entity.Employee;
import com.salesliant.report.CommissionHistoryLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class CommissionHistoryListUI extends BaseListUI<Commission> {

    private final BaseDao<Commission> daoCommission = new BaseDao<>(Commission.class);
    private List<Commission> fCommissionList;
    private final TableView<List<Commission>> fSummaryTable = new TableView<>();
    private List<List<Commission>> fSummaryList = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(CommissionHistoryListUI.class.getName());

    public CommissionHistoryListUI() {
        loadData();
        mainView = createMainView();
        fSummaryTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends List<Commission>> observable, List<Commission> newValue, List<Commission> oldValue) -> {
            fCommissionList = new ArrayList<>();
            if (observable != null && observable.getValue() != null) {
                fCommissionList = observable.getValue()
                        .stream()
                        .sorted((e1, e2) -> {
                            String name1 = (!isEmpty(e1.getSales().getFirstName()) ? e1.getSales().getFirstName() : "")
                                    + (!isEmpty(e1.getSales().getLastName()) ? " " : "");
                            String name2 = (!isEmpty(e2.getSales().getFirstName()) ? e2.getSales().getFirstName() : "")
                                    + (!isEmpty(e2.getSales().getLastName()) ? " " : "");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());
            }
            fTableView.setItems(FXCollections.observableList(fCommissionList));
            fTableView.refresh();
        });
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Commission> commissionList = daoCommission.read(Commission_.store, Config.getStore());
            Map<Timestamp, List<Commission>> groupByTimestamp = commissionList
                    .stream()
                    .filter(e -> e.getSales() != null)
                    .collect(Collectors.groupingBy(e -> e.getPaidDate()));
            List<List<Commission>> list = new ArrayList<>(groupByTimestamp.values())
                    .stream()
                    .sorted((e1, e2) -> e2.get(0).getPaidDate().compareTo(e1.get(0).getPaidDate()))
                    .collect(Collectors.toList());
            fSummaryList = FXCollections.observableList(list);
            fSummaryTable.getItems().setAll(fSummaryList);
            fSummaryTable.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (fSummaryTable.getSelectionModel().getSelectedItem() != null && !fSummaryTable.getSelectionModel().getSelectedItem().isEmpty()) {
                    List<Commission> list = fSummaryTable.getSelectionModel().getSelectedItem();
                    Employee employee = list.get(0).getEmployee();
                    Timestamp paidDate = list.get(0).getPaidDate();
                    Timestamp from = new Timestamp(new Date().getTime());
                    Calendar calendar = new GregorianCalendar(1900, Calendar.JANUARY, 1);
                    Timestamp to = new Timestamp(calendar.getTime().getTime());
                    for (Commission c : list) {
                        if (c.getInvoice().getDateInvoiced() != null && c.getInvoice().getDateInvoiced().before(from)) {
                            from = c.getInvoice().getDateInvoiced();
                        }
                        if (c.getInvoice().getDateInvoiced() != null && c.getInvoice().getDateInvoiced().after(to)) {
                            to = c.getInvoice().getDateInvoiced();
                        }
                    }
                    CommissionHistoryLayout layout = new CommissionHistoryLayout(list, employee, paidDate, from.toLocalDateTime(), to.toLocalDateTime());
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    break;
                }
        }
    }

    private Node createMainTable() {
        TableColumn<List<Commission>, String> TimeCol = new TableColumn<>("Time");
        TimeCol.setCellValueFactory((CellDataFeatures<List<Commission>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getPaidDate() != null) {
                return new SimpleStringProperty(getString(p.getValue().get(0).getPaidDate()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        TimeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        TimeCol.setPrefWidth(350);

        TableColumn<List<Commission>, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory((CellDataFeatures<List<Commission>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                Timestamp ts = new Timestamp(new Date().getTime());
                for (Commission c : p.getValue()) {
                    if (c.getInvoice().getDateInvoiced() != null && c.getInvoice().getDateInvoiced().before(ts)) {
                        ts = c.getInvoice().getDateInvoiced();
                    }
                }
                String date = new SimpleDateFormat("MM/dd/yyyy").format(ts);
                return new SimpleStringProperty(date);
            } else {
                return new SimpleStringProperty("");
            }
        });
        fromCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        fromCol.setPrefWidth(110);

        TableColumn<List<Commission>, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory((CellDataFeatures<List<Commission>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                Calendar calendar = new GregorianCalendar(1900, Calendar.JANUARY, 1);
                Timestamp ts = new Timestamp(calendar.getTime().getTime());
                for (Commission c : p.getValue()) {
                    if (c.getInvoice().getDateInvoiced() != null && c.getInvoice().getDateInvoiced().after(ts)) {
                        ts = c.getInvoice().getDateInvoiced();
                    }
                }
                String date = new SimpleDateFormat("MM/dd/yyyy").format(ts);
                return new SimpleStringProperty(date);
            } else {
                return new SimpleStringProperty("");
            }
        });
        toCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        toCol.setPrefWidth(110);

        TableColumn<List<Commission>, String> employeeNameCol = new TableColumn<>("Processed By");
        employeeNameCol.setCellValueFactory((CellDataFeatures<List<Commission>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getEmployee() != null) {
                String name = (!isEmpty(p.getValue().get(0).getEmployee().getFirstName()) ? p.getValue().get(0).getEmployee().getFirstName() : "")
                        + (!isEmpty(p.getValue().get(0).getEmployee().getLastName()) ? " " : "");
                return new SimpleStringProperty(name);
            } else {
                return new SimpleStringProperty("");
            }
        });
        employeeNameCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        employeeNameCol.setPrefWidth(150);

        TableColumn<List<Commission>, String> commissionAmountCol = new TableColumn<>("Commission");
        commissionAmountCol.setCellValueFactory((CellDataFeatures<List<Commission>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal commissionAmount = p.getValue()
                        .stream()
                        .map(e -> e.getCommisionAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(commissionAmount));
            } else {
                return new SimpleStringProperty("");
            }
        });
        commissionAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        commissionAmountCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(TimeCol);
        fSummaryTable.getColumns().add(fromCol);
        fSummaryTable.getColumns().add(toCol);
        fSummaryTable.getColumns().add(employeeNameCol);
        fSummaryTable.getColumns().add(commissionAmountCol);
        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(200);
        return fSummaryTable;
    }

    private Node createDetailTable() {
        TableColumn<Commission, String> salesNameCol = new TableColumn<>("Sales Name");
        salesNameCol.setCellValueFactory((TableColumn.CellDataFeatures<Commission, String> p) -> {
            if (p.getValue() != null && p.getValue().getSales() != null) {
                String name = (!isEmpty(p.getValue().getSales().getFirstName()) ? p.getValue().getSales().getFirstName() : "")
                        + (!isEmpty(p.getValue().getSales().getLastName()) ? " " : "");
                return new SimpleStringProperty(name);
            } else {
                return null;
            }
        });
        salesNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        salesNameCol.setPrefWidth(180);

        TableColumn<Commission, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((TableColumn.CellDataFeatures<Commission, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getCustomerName() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getCustomerName());
            } else {
                return null;
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(220);

        TableColumn<Commission, String> invoiceNumberCol = new TableColumn<>("Invoice Number");
        invoiceNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<Commission, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getInvoiceNumber() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getInvoiceNumber().toString());
            } else {
                return null;
            }
        });
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(150);

        TableColumn<Commission, String> invoiceAmountCol = new TableColumn<>("Invoice Amount");
        invoiceAmountCol.setCellValueFactory((TableColumn.CellDataFeatures<Commission, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getTotal() != null) {
                return new SimpleStringProperty(getString(p.getValue().getInvoice().getTotal()));
            } else {
                return null;
            }
        });
        invoiceAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        invoiceAmountCol.setPrefWidth(150);

        TableColumn<Commission, String> commissionCol = new TableColumn<>("Commission Amount");
        commissionCol.setCellValueFactory((TableColumn.CellDataFeatures<Commission, String> p) -> {
            if (p.getValue() != null && p.getValue().getCommisionAmount() != null) {
                return new SimpleStringProperty(getString(p.getValue().getCommisionAmount()));
            } else {
                return null;
            }
        });
        commissionCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        commissionCol.setPrefWidth(150);

        fTableView.getColumns().add(salesNameCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceAmountCol);
        fTableView.getColumns().add(commissionCol);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);
        return fTableView;
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        mainPane.add(createMainTable(), 0, 1);
        mainPane.add(createDetailTable(), 0, 2);
        mainPane.add(createPrintCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createPrintCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print Selected", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

}

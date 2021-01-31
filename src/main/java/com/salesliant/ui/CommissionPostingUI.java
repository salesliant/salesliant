package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Category;
import com.salesliant.entity.Commission;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.Item;
import com.salesliant.report.CommissionPaymentLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.widget.EmployeeTableWidget;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class CommissionPostingUI extends BaseListUI<InvoiceEntry> {

    private final BaseDao<InvoiceEntry> daoInvoiceEntry = new BaseDao<>(InvoiceEntry.class);
    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final BaseDao<Commission> daoCommission = new BaseDao<>(Commission.class);
    private List<InvoiceEntry> fInvoiceEntryList;
    private final TableView<List<InvoiceEntry>> fSummaryTable = new TableView<>();
    private final TableColumn<InvoiceEntry, InvoiceEntry> selectedCol = new TableColumn<>("");
    private ObservableSet<InvoiceEntry> selectedItems = FXCollections.observableSet();
    private List<InvoiceEntry> fList = new ArrayList<>();
    private List<List<InvoiceEntry>> fSummaryList = new ArrayList<>();
    private Employee fEmployee;
    private final Label rangeLabel = new Label();
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private SetChangeListener<InvoiceEntry> selectedItemsListener;
    private final ObjectProperty<BigDecimal> totalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> totalByEmployeeProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private static final Logger LOGGER = Logger.getLogger(CommissionPostingUI.class.getName());

    public CommissionPostingUI() {
        selectedItemsListener = (SetChangeListener<InvoiceEntry>) c -> updatePayTotal();
        setDefaultDate();
        loadData();
        mainView = createMainView();
        fSummaryTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends List<InvoiceEntry>> observable, List<InvoiceEntry> newValue, List<InvoiceEntry> oldValue) -> {
            fInvoiceEntryList = new ArrayList<>();
            if (observable != null && observable.getValue() != null) {
                fInvoiceEntryList = observable.getValue().stream().collect(Collectors.toList());
                fEmployee = getEmployee(fInvoiceEntryList.get(0).getSalesName());
                selectedItems.removeListener(selectedItemsListener);
                selectedItems.addListener(selectedItemsListener);
            }
            fTableView.setItems(FXCollections.observableList(fInvoiceEntryList));
            fTableView.refresh();
            updatePayTotal();
        });
    }

    private void loadData() {
        fSummaryTable.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Invoice> invoiceList = daoInvoice.readBetweenDate(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, fFrom, fTo);
            List<InvoiceEntry> invoiceEntryList = new ArrayList<>();
            invoiceList.forEach(e -> {
                invoiceEntryList.addAll(e.getInvoiceEntries());
            });
            Map<String, List<InvoiceEntry>> groupByEmployee = invoiceEntryList
                    .stream()
                    .filter(e -> e.getComponentFlag() == null || !e.getComponentFlag())
                    .filter(e -> e.getCommissionPaidFlag() != null && !e.getCommissionPaidFlag())
                    .filter(e -> e.getCommissionAmount() != null)
                    .filter(e -> getEmployee(e.getSalesName()) != null)
                    .collect(Collectors.groupingBy(e -> e.getSalesName()));
            List<List<InvoiceEntry>> list = new ArrayList<>(groupByEmployee.values())
                    .stream()
                    .sorted((e1, e2) -> {
                        Employee employee1 = getEmployee(e1.get(0).getSalesName());
                        Employee employee2 = getEmployee(e2.get(0).getSalesName());
                        String name1 = (!isEmpty(employee1.getFirstName()) ? employee1.getFirstName() : "")
                                + (!isEmpty(employee1.getLastName()) ? " " + employee1.getLastName() : "");
                        String name2 = (!isEmpty(employee2.getFirstName()) ? employee2.getFirstName() : "")
                                + (!isEmpty(employee2.getLastName()) ? " " + employee2.getLastName() : "");
                        return name1.compareToIgnoreCase(name2);
                    })
                    .collect(Collectors.toList());
            fSummaryList = FXCollections.observableList(list);
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
            case AppConstants.ACTION_SELECT_ALL:
                selectedItems.removeListener(selectedItemsListener);
                fTableView.getItems().forEach(e -> {
                    if (selectedItems != null && !selectedItems.contains(e)) {
                        selectedItems.add(e);
                    }
                });
                updatePayTotal();
                selectedItems.addListener(selectedItemsListener);
                break;
            case AppConstants.ACTION_UN_SELECT_ALL:
                selectedItems.removeListener(selectedItemsListener);
                fTableView.getItems().forEach(e -> {
                    if (selectedItems != null && selectedItems.contains(e)) {
                        selectedItems.remove(e);
                    }
                });
                updatePayTotal();
                selectedItems.addListener(selectedItemsListener);
                break;
            case AppConstants.ACTION_SELECT:
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fSummaryTable.setItems(FXCollections.observableArrayList());
                    fTableView.setItems(FXCollections.observableArrayList());
                    selectedItems.clear();
                    loadData();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_ADJUST:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    EmployeeTableWidget employeeListUI = new EmployeeTableWidget();
                    employeeListUI.getView().getStyleClass().add("editView");
                    fInputDialog = createSelectCancelUIDialog(employeeListUI.getView(), "Customers");
                    selectBtn.setDisable(true);
                    ((TableView<Employee>) employeeListUI.getTableView()).getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Employee> observable, Employee newValue, Employee oldValue) -> {
                        if (observable != null && observable.getValue() != null) {
                            selectBtn.setDisable(false);
                            selectBtn.requestFocus();
                        } else {
                            selectBtn.setDisable(true);
                        }
                    });
                    selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        if (employeeListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                            Employee employee = (Employee) employeeListUI.getTableView().getSelectionModel().getSelectedItem();
                            fEntity.setSalesName(employee.getNameOnSalesOrder());
                            daoInvoiceEntry.update(fEntity);
                            loadData();
                        }
                    });
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_PROCESS:
                if (!selectedItems.isEmpty()) {
                    showConfirmDialog("Do you want to pay the commission of select invoice entry?", (ActionEvent e) -> {
                        fList.clear();
                        fList = new ArrayList<>(selectedItems);
                        Map<Invoice, List<InvoiceEntry>> groupByInvoice = fList
                                .stream()
                                .collect(Collectors.groupingBy(c -> c.getInvoice()));
                        List<List<InvoiceEntry>> list = new ArrayList<>(groupByInvoice.values());
                        Timestamp now = new Timestamp(new Date().getTime());
                        list.stream().forEach((g) -> {
                            BigDecimal commissionAmount = g.stream()
                                    .filter(f -> f.getCommissionAmount() != null)
                                    .map(f -> f.getCommissionAmount())
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            Commission commission = new Commission();
                            commission.setCommisionAmount(commissionAmount);
                            commission.setStore(Config.getStore());
                            commission.setPaidDate(now);
                            commission.setEmployee(Config.getEmployee());
                            commission.setSales(getEmployee(g.get(0).getSalesName()));
                            commission.setInvoice((g.get(0).getInvoice()));
                            daoCommission.insert(commission);
                            if (daoCommission.getErrorMessage() == null) {
                                g.stream().forEach((c) -> {
                                    c.setCommissionPaidFlag(Boolean.TRUE);
                                    daoInvoiceEntry.update(c);
                                });
                            }
                        });
                        Platform.runLater(() -> {
                            CommissionPaymentLayout layout = new CommissionPaymentLayout(fList, Config.getEmployee(), now, fFrom, fTo);
                            try {
                                JasperReportBuilder report = layout.build();
                                report.show(false);
                            } catch (DRException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                            setDefaultDate();
                            fSummaryTable.setItems(FXCollections.observableArrayList());
                            fTableView.setItems(FXCollections.observableArrayList());
                            selectedItems.clear();
                            loadData();
                        });
                    });
                }
                break;
            case AppConstants.ACTION_UPDATE:
                if (!fSummaryList.isEmpty()) {
                    showConfirmDialog("Do you want to update the commission of all invoice entry?", (ActionEvent e) -> {
                        fSummaryList.forEach(list -> {
                            list.forEach(ie -> {
                                if (ie.getCommissionAmount() != null) {
                                    Category category = getCategory(ie.getCategoryName());
                                    if (category == null) {
                                        Item item = getItem(ie.getItemLookUpCode());
                                        category = item.getCategory();
                                    }
                                    BigDecimal commission = BigDecimal.ZERO;
                                    if (category != null) {
                                        if (category.getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES) && category.getCommisionPercentSale() != null) {
                                            commission = category.getCommisionPercentSale().divide(new BigDecimal(100)).multiply(ie.getPrice().multiply(ie.getQuantity()));
                                        } else if (category.getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_PROFIT) && category.getCommisionPercentProfit() != null) {
                                            commission = category.getCommisionPercentProfit().divide(new BigDecimal(100)).multiply((ie.getPrice().subtract(zeroIfNull(ie.getCost()))).multiply(ie.getQuantity()));
                                        } else if (category.getCommisionMode().equals(DBConstants.TYPE_COMMISSION_BY_FIXED_AMOUNT) && category.getCommissionFixedAmount() != null) {
                                            commission = category.getCommissionFixedAmount();
                                        }
                                    }
                                    ie.setCommissionAmount(commission);
                                    daoInvoiceEntry.update(ie);
                                }
                            });
                        });
                        fSummaryTable.setItems(FXCollections.observableArrayList());
                        fTableView.setItems(FXCollections.observableArrayList());
                        selectedItems.clear();
                        loadData();
                    });
                }
                break;
        }
    }

    private Node createMainTable() {
        TableColumn<List<InvoiceEntry>, String> employeeCol = new TableColumn<>("Employee");
        employeeCol.setCellValueFactory((CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && getEmployee(p.getValue().get(0).getSalesName()) != null) {
                String name = (!isEmpty(getEmployee(p.getValue().get(0).getSalesName()).getFirstName()) ? getEmployee(p.getValue().get(0).getSalesName()).getFirstName() : "")
                        + (!isEmpty(getEmployee(p.getValue().get(0).getSalesName()).getLastName()) ? " " + getEmployee(p.getValue().get(0).getSalesName()).getLastName() : "");
                return new SimpleStringProperty(name);
            } else {
                return new SimpleStringProperty("");
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(350);

        TableColumn<List<InvoiceEntry>, String> nameCol = new TableColumn<>("Code");
        nameCol.setCellValueFactory((CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getSalesName() != null) {
                return new SimpleStringProperty(p.getValue().get(0).getSalesName());
            } else {
                return new SimpleStringProperty("");
            }
        });
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(110);

        TableColumn<List<InvoiceEntry>, String> salesAmountCol = new TableColumn<>("Sales");
        salesAmountCol.setCellValueFactory((CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal salesAmount = p.getValue()
                        .stream()
                        .map(e -> (e.getPrice().multiply(e.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(salesAmount));
            } else {
                return new SimpleStringProperty("");
            }
        });
        salesAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        salesAmountCol.setPrefWidth(150);

        TableColumn<List<InvoiceEntry>, String> commissionAmountCol = new TableColumn<>("Commission Total");
        commissionAmountCol.setCellValueFactory((CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal commissionAmount = p.getValue()
                        .stream()
                        .filter(e -> e.getCommissionAmount() != null)
                        .map(e -> e.getCommissionAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(commissionAmount));
            } else {
                return new SimpleStringProperty("");
            }
        });
        commissionAmountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        commissionAmountCol.setPrefWidth(120);

        TableColumn<List<InvoiceEntry>, String> commissionToPaidCol = new TableColumn<>("Commission To Pay");
        commissionToPaidCol.setCellValueFactory((CellDataFeatures<List<InvoiceEntry>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && getEmployee(p.getValue().get(0).getSalesName()) != null) {
                BigDecimal commissionToPaidAmount = selectedItems
                        .stream()
                        .filter(e -> getEmployee(e.getSalesName()).getId().equals(getEmployee(p.getValue().get(0).getSalesName()).getId()) && e.getCommissionAmount() != null)
                        .map(e -> (e.getCommissionAmount()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new SimpleStringProperty(getString(commissionToPaidAmount));
            } else {
                return new SimpleStringProperty("");
            }
        });
        commissionToPaidCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        commissionToPaidCol.setPrefWidth(120);

        fSummaryTable.getColumns().add(employeeCol);
        fSummaryTable.getColumns().add(nameCol);
        fSummaryTable.getColumns().add(salesAmountCol);
        fSummaryTable.getColumns().add(commissionAmountCol);
        fSummaryTable.getColumns().add(commissionToPaidCol);
        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(250);
        return fSummaryTable;
    }

    private Node createDetailTable() {
        selectedCol.setCellValueFactory((CellDataFeatures<InvoiceEntry, InvoiceEntry> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<InvoiceEntry, InvoiceEntry> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<InvoiceEntry, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getInvoiceNumber() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getInvoiceNumber().toString());
            } else {
                return null;
            }
        });
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(110);

        TableColumn<InvoiceEntry, String> invoiceDateCol = new TableColumn<>("Date");
        invoiceDateCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getDateInvoiced() != null) {
                return new SimpleStringProperty(getString(p.getValue().getInvoice().getDateInvoiced()));
            } else {
                return null;
            }
        });
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceDateCol.setPrefWidth(80);

        TableColumn<InvoiceEntry, String> NameCol = new TableColumn<>("Customer");
        NameCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getCustomerName() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getCustomerName());
            } else {
                return null;
            }
        });
        NameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        NameCol.setPrefWidth(140);

        TableColumn<InvoiceEntry, String> itemCol = new TableColumn<>("Item");
        itemCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemDescription.getName()));
        itemCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemCol.setPrefWidth(170);

        TableColumn<InvoiceEntry, String> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.quantity.getName()));
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(70);

        TableColumn<InvoiceEntry, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.price.getName()));
        priceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        priceCol.setPrefWidth(70);

        TableColumn<InvoiceEntry, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory((CellDataFeatures<InvoiceEntry, String> p) -> {
            BigDecimal amount = zeroIfNull(p.getValue().getQuantity()).multiply(zeroIfNull(p.getValue().getPrice()));
            return new SimpleStringProperty(getString(amount));
        });
        amountCol.setPrefWidth(100);
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        TableColumn<InvoiceEntry, String> commissionCol = new TableColumn<>("Commission");
        commissionCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.commissionAmount.getName()));
        commissionCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        commissionCol.setPrefWidth(80);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(NameCol);
        fTableView.getColumns().add(itemCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getColumns().add(commissionCol);
        fTableView.setPrefHeight(300);
        return fTableView;
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(createMainTable(), 0, 2);
        mainPane.add(createDetailTable(), 0, 3);
        mainPane.add(createTotalByEmployeePane(), 0, 4);
        mainPane.add(createPaySelectCloseButtonPane(), 0, 5);

        mainPane.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.U) {
                handleAction(AppConstants.ACTION_UPDATE);
                event.consume();
            }
        });
        return mainPane;
    }

    private HBox createTotalByEmployeePane() {
        HBox totalByEmployeeBox = new HBox();
        Label totalByEmployeeLbl = new Label("By Employee: ");
        TextField totalByEmployeeField = createLabelField(120, Pos.CENTER_LEFT);
        totalByEmployeeField.textProperty().bindBidirectional(totalByEmployeeProperty, getDecimalFormat());
        totalByEmployeeBox.getChildren().add(totalByEmployeeLbl);
        totalByEmployeeBox.getChildren().add(totalByEmployeeField);
        totalByEmployeeBox.setAlignment(Pos.CENTER_LEFT);

        HBox totalBox = new HBox();
        Label totalLbl = new Label("Total To Pay: ");
        TextField totalField = createLabelField(120, Pos.CENTER_LEFT);
        totalField.textProperty().bindBidirectional(totalProperty, getDecimalFormat());
        totalBox.getChildren().add(totalLbl);
        totalBox.getChildren().add(totalField);
        totalBox.setAlignment(Pos.CENTER_RIGHT);

        HBox amountBox = new HBox();
        amountBox.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        amountBox.getChildren().addAll(totalByEmployeeBox, filler, totalBox);
        return amountBox;
    }

    private HBox createPaySelectCloseButtonPane() {

        HBox leftButtonBox = new HBox();
        Button selectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_ALL, AppConstants.ACTION_SELECT_ALL, "Tag All", fHandler);
        Button unSelectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UN_SELECT_ALL, AppConstants.ACTION_UN_SELECT_ALL, "Untag All", fHandler);
        leftButtonBox.getChildren().addAll(selectAllButton, unSelectAllButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button adjustButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADJUST, AppConstants.ACTION_ADJUST, fHandler);
        Button payButton = ButtonFactory.getButton(ButtonFactory.BUTTON_TENDER, AppConstants.ACTION_PROCESS, "Pay", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        rightButtonBox.getChildren().addAll(dateRangerButton, adjustButton, payButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }

    private void updatePayTotal() {
        if (selectedItems == null || selectedItems.isEmpty()) {
            totalProperty.set(BigDecimal.ZERO);
            totalByEmployeeProperty.set(BigDecimal.ZERO);
        } else {
            BigDecimal total = selectedItems.stream()
                    .filter(e -> e.getCommissionAmount() != null)
                    .map(e -> e.getCommissionAmount())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            totalProperty.set(total);
            if (fEmployee != null) {
                BigDecimal totalByEmployeeAmount = selectedItems
                        .stream()
                        .filter(e -> getEmployee(e.getSalesName()).getId().equals(fEmployee.getId()) && e.getCommissionAmount() != null)
                        .map(e -> (e.getCommissionAmount()))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                totalByEmployeeProperty.set(totalByEmployeeAmount);
            }
        }
        fSummaryTable.refresh();
    }
}

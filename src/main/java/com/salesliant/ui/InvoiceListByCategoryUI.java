package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Category;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.Item;
import com.salesliant.report.InvoiceEntryListByCategoryReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.widget.CategoryWidget;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class InvoiceListByCategoryUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private TableView<InvoiceEntry> fInvoiceEntryTableView = new TableView<>();
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private List<InvoiceEntry> fList = new ArrayList<>();
    private final ComboBox<Category> fCategoryCombo = new CategoryWidget();
    private Category fCategory;
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private final Label rangeLabel = new Label();
    private static final Logger LOGGER = Logger.getLogger(InvoiceListByCategoryUI.class.getName());

    public InvoiceListByCategoryUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
        updateRange();
        fCategoryCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Category> observable, Category newValue, Category oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fCategory = fCategoryCombo.getSelectionModel().getSelectedItem();
                fList = new ArrayList<>();
                if (fEntityList != null && !fEntityList.isEmpty()) {
                    fEntityList.forEach(e -> {
                        e.getInvoiceEntries().forEach(i -> {
                            Category category = getCategory(i.getCategoryName());
                            if (category == null) {
                                Item item = getItem(i.getItemLookUpCode());
                                category = item.getCategory();
                            }
                            if (category != null && category.getId().equals(fCategory.getId())) {
                                fList.add(i);
                            }
                        });
                    });
                }
                fInvoiceEntryList = FXCollections.observableList(fList);
                fInvoiceEntryTableView.setItems(fInvoiceEntryList);
            } else {
                fCategory = null;
            }
        });
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Invoice> list = daoInvoice.readBetweenDate(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, fFrom, fTo);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            fTableView.setPlaceholder(null);
            fList = new ArrayList<>();
            if (fCategory != null) {
                fEntityList.forEach(e -> {
                    e.getInvoiceEntries().forEach(i -> {
                        Item item = getItem(i.getItemLookUpCode());
                        if (item != null && item.getCategory() != null && item.getCategory().getId().equals(fCategory.getId()) && i.getItemLookUpCode() != null
                                && !i.getItemLookUpCode().equalsIgnoreCase("NOTE:") && !i.getItemLookUpCode().equalsIgnoreCase("SYSSN") && !i.getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                            fList.add(i);
                        }
                    });
                });
            }
            fInvoiceEntryList = FXCollections.observableList(fList);
            fInvoiceEntryTableView.setItems(fInvoiceEntryList);
        });
    }

    private void setDefaultDate() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayofCurrentMonth = today.withDayOfMonth(1);
        LocalDate lastDayofCurrentMonth = today.withDayOfMonth(today.lengthOfMonth());
        fFrom = firstDayofCurrentMonth.atTime(0, 0, 0, 0);
        fTo = lastDayofCurrentMonth.atTime(LocalTime.MAX);

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
                fTableView.setPlaceholder(null);
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.setItems(FXCollections.observableArrayList());
                    updateRange();
                    loadData();
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fList.isEmpty()) {
                    InvoiceEntryListByCategoryReportLayout layout = new InvoiceEntryListByCategoryReportLayout(fList, fFrom, fTo);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                } else {
                    showAlertDialog("Nothing to print!");
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        HBox categoryBox = new HBox();
        Label categoryLabel = new Label("Category: ");
        categoryBox.getChildren().addAll(categoryLabel, fCategoryCombo);
        categoryBox.setAlignment(Pos.CENTER_RIGHT);
        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(categoryBox, 1, 1);
        fCategoryCombo.setPrefWidth(200);
        GridPane.setHalignment(rangeLabel, HPos.LEFT);
        GridPane.setHalignment(categoryBox, HPos.RIGHT);

        TableColumn<Invoice, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p) -> {
            if (p.getValue().getBillToCompany() != null) {
                return new SimpleStringProperty(p.getValue().getBillToCompany());
            } else {
                return new SimpleStringProperty(p.getValue().getCustomerName());
            }
        });
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(470);

        TableColumn<Invoice, String> orderNoCol = new TableColumn<>("Invoice");
        orderNoCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.invoiceNumber.getName()));
        orderNoCol.setCellFactory(stringCell(Pos.CENTER));
        orderNoCol.setPrefWidth(160);

        TableColumn<Invoice, String> dateCreatedCol = new TableColumn<>("Date Entered");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.dateInvoiced.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER));
        dateCreatedCol.setPrefWidth(140);
        dateCreatedCol.setSortType(TableColumn.SortType.DESCENDING);

        TableColumn<Invoice, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.CENTER));
        totalCol.setPrefWidth(100);

        TableColumn<Invoice, String> employeeCol = new TableColumn<>("Sales");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.salesName.getName()));
        employeeCol.setCellFactory(stringCell(Pos.CENTER));
        employeeCol.setPrefWidth(100);

        fTableView.getColumns().add(orderNoCol);
        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(totalCol);
        fTableView.getColumns().add(employeeCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);

        TableColumn<InvoiceEntry, String> entrySKUCol = new TableColumn<>("SKU");
        entrySKUCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        entrySKUCol.setMinWidth(100);
        entrySKUCol.setCellFactory(stringCell(Pos.CENTER));

        TableColumn<InvoiceEntry, String> entryDescriptionCol = new TableColumn<>("Description");
        entryDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemDescription.getName()));
        entryDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        entryDescriptionCol.setMinWidth(280);

        TableColumn<InvoiceEntry, String> entryInvoiceNumberCol = new TableColumn<>("Invoice #");
        entryInvoiceNumberCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getDateInvoiced() != null) {
                return new SimpleStringProperty(getString(p.getValue().getInvoice().getInvoiceNumber()));
            } else {
                return null;
            }
        });
        entryInvoiceNumberCol.setCellFactory(stringCell(Pos.CENTER));
        entryInvoiceNumberCol.setPrefWidth(90);
        entryInvoiceNumberCol.setResizable(false);

        TableColumn<InvoiceEntry, BigDecimal> entryQtyCol = new TableColumn<>("Qty");
        entryQtyCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.quantity.getName()));
        entryQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        entryQtyCol.setMinWidth(70);

        TableColumn<InvoiceEntry, BigDecimal> entryPriceCol = new TableColumn<>("Price");
        entryPriceCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.price.getName()));
        entryPriceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        entryPriceCol.setMinWidth(90);

        TableColumn<InvoiceEntry, String> entryDateCol = new TableColumn<>("Date");
        entryDateCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getDateInvoiced() != null) {
                return new SimpleStringProperty(getString(p.getValue().getInvoice().getDateInvoiced()));
            } else {
                return null;
            }
        });
        entryDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        entryDateCol.setPrefWidth(140);
        entryDateCol.setResizable(false);

        TableColumn<InvoiceEntry, String> entrySalesCol = new TableColumn<>("Sales Person");
        entrySalesCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getSalesName() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getSalesName());
            } else {
                return null;
            }
        });
        entrySalesCol.setCellFactory(stringCell(Pos.CENTER));
        entrySalesCol.setPrefWidth(100);
        entrySalesCol.setResizable(false);

        TableColumn<InvoiceEntry, BigDecimal> entryTotalCol = new TableColumn<>("Total");
        entryTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            BigDecimal total = BigDecimal.ZERO;
            if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
            }
            return new ReadOnlyObjectWrapper(total);
        });
        entryTotalCol.setMinWidth(90);
        entryTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        fInvoiceEntryTableView.getColumns().add(entrySKUCol);
        fInvoiceEntryTableView.getColumns().add(entryDescriptionCol);
        fInvoiceEntryTableView.getColumns().add(entryInvoiceNumberCol);
        fInvoiceEntryTableView.getColumns().add(entryQtyCol);
        fInvoiceEntryTableView.getColumns().add(entryPriceCol);
        fInvoiceEntryTableView.getColumns().add(entryTotalCol);
        fInvoiceEntryTableView.getColumns().add(entryDateCol);
        fInvoiceEntryTableView.getColumns().add(entrySalesCol);

        fInvoiceEntryTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fInvoiceEntryTableView.setPrefHeight(300);
        setTableWidth(fInvoiceEntryTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(fInvoiceEntryTableView, 0, 3, 2, 1);
        mainPane.add(createClonePrintExportCloseButtonPane(), 1, 4);
        return mainPane;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
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

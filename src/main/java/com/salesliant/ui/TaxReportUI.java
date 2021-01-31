package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.report.InvoiceLayout;
import com.salesliant.report.TaxReportLayout;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
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

public class TaxReportUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final TableView<InvoiceEntry> fDetailTableView = new TableView<>();
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private final Label rangeLabel = new Label();
    private static final Logger LOGGER = Logger.getLogger(TaxReportUI.class.getName());

    public TaxReportUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Invoice> observable, Invoice newValue, Invoice oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<InvoiceEntry> aList = fTableView.getSelectionModel().getSelectedItem().getInvoiceEntries()
                        .stream()
                        .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                        .collect(Collectors.toList());
                fInvoiceEntryList = FXCollections.observableList(aList);
                fDetailTableView.setItems(fInvoiceEntryList);
            } else {
                fDetailTableView.getItems().clear();
            }
        });
    }

    private void setDefaultDate() {
        LocalDate now = LocalDate.now();
        fFrom = now.atTime(0, 0, 0, 0);
        fTo = now.atTime(LocalTime.MAX);
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Invoice> list = daoInvoice.readBetweenDate(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, fFrom, fTo);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            updateRange();
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (fEntityList != null & !fEntityList.isEmpty()) {
                    TaxReportLayout layout = new TaxReportLayout(fEntityList, fFrom, fTo);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_EXPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    InvoiceLayout layout = new InvoiceLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case AppConstants.ACTION_SELECT:
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                fInputDialog.showDialog();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        TableColumn<Invoice, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.customerName.getName()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p) -> {
            if (p.getValue().getBillToCompany() != null) {
                return new SimpleStringProperty(p.getValue().getBillToCompany());
            } else {
                return new SimpleStringProperty(p.getValue().getCustomerName());
            }
        });
        nameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        nameCol.setPrefWidth(245);

        TableColumn<Invoice, String> orderNoCol = new TableColumn<>("Invoice");
        orderNoCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.invoiceNumber.getName()));
        orderNoCol.setCellFactory(stringCell(Pos.CENTER));
        orderNoCol.setPrefWidth(100);

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

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(100);
        skuCol.setCellFactory(stringCell(Pos.CENTER));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                return new SimpleStringProperty(p.getValue().getItemDescription());
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        detailDescriptionCol.setMinWidth(230);

        TableColumn<InvoiceEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantity());
            }
        });
        detailQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailQtyCol.setMinWidth(70);

        TableColumn<InvoiceEntry, BigDecimal> detailPriceCol = new TableColumn<>("Price");
        detailPriceCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getPrice());
            }
        });
        detailPriceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        detailPriceCol.setMinWidth(100);

        TableColumn<InvoiceEntry, BigDecimal> discountCol = new TableColumn<>("Discount");
        discountCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getDiscountAmount());
            }
        });
        discountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        discountCol.setPrefWidth(85);
        discountCol.setResizable(false);

        TableColumn<InvoiceEntry, BigDecimal> detailTotalCol = new TableColumn<>("Total");
        detailTotalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                BigDecimal total;
                if (p.getValue() != null && p.getValue().getPrice() != null && p.getValue().getQuantity() != null) {
                    total = p.getValue().getPrice().multiply(p.getValue().getQuantity());
                    return new ReadOnlyObjectWrapper(total);
                } else {
                    return null;
                }
            }

        });

        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));

        fDetailTableView.getColumns().add(skuCol);
        fDetailTableView.getColumns().add(detailDescriptionCol);
        fDetailTableView.getColumns().add(detailQtyCol);
        fDetailTableView.getColumns().add(detailPriceCol);
        fDetailTableView.getColumns().add(discountCol);
        fDetailTableView.getColumns().add(detailTotalCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(300);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(fDetailTableView, 0, 3);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button printTaxReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print Tax Report", fHandler);
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, "Print Invoice", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, printTaxReportButton, exportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

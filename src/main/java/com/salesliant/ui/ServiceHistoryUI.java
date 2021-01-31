package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.ServiceEntry_;
import com.salesliant.report.InvoiceLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.InvoiceSearchField;
import java.math.BigDecimal;
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
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ServiceHistoryUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private TableView<InvoiceEntry> fDetailTableView = new TableView<>();
    private TableView<ServiceEntry> fServiceEntryTable = new TableView<>();
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private ObservableList<ServiceEntry> fServiceEntryList;
    private final static String TITLE = "Invoice";
    private final InvoiceSearchField searchField = new InvoiceSearchField();
    private static final Logger LOGGER = Logger.getLogger(ServiceHistoryUI.class.getName());

    public ServiceHistoryUI() {
        loadData();
        mainView = createMainView();
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Invoice> observable, Invoice newValue, Invoice oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        List<InvoiceEntry> aList = fTableView.getSelectionModel().getSelectedItem().getInvoiceEntries()
                                .stream()
                                .sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder()))
                                .collect(Collectors.toList());
                        fInvoiceEntryList = FXCollections.observableList(aList);
                        fDetailTableView.setItems(fInvoiceEntryList);
                        List<ServiceEntry> serviceEntryList = fTableView.getSelectionModel().getSelectedItem().getService().getServiceEntries()
                                .stream()
                                .sorted((e1, e2) -> e1.getDateEntered().compareTo(e2.getDateEntered()))
                                .collect(Collectors.toList());
                        fServiceEntryList = FXCollections.observableList(serviceEntryList);
                        fServiceEntryTable.setItems(fServiceEntryList);
                    } else {
                        fDetailTableView.getItems().clear();
                        fServiceEntryTable.getItems().clear();
                    }
                });
        dialogTitle = TITLE;
        Platform.runLater(() -> searchField.requestFocus());
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Invoice> list = daoInvoice.readOrderByIsNotNull(Invoice_.store, Config.getStore(), Invoice_.service, Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    InvoiceLayout layout = new InvoiceLayout(fEntity);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.print(true);
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
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Invoice, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.customerName.getName()));
        nameCol.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p) -> {
            if (p.getValue().getBillToCompany() != null) {
                return new SimpleStringProperty(p.getValue().getBillToCompany());
            } else {
                return new SimpleStringProperty(p.getValue().getCustomerName());
            }
        });
        nameCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        nameCol.setPrefWidth(300);

        TableColumn<Invoice, String> orderNoCol = new TableColumn<>("Invoice");
        orderNoCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.invoiceNumber.getName()));
        orderNoCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        orderNoCol.setPrefWidth(120);

        TableColumn<Invoice, String> dateCreatedCol = new TableColumn<>("Date Entered");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.dateOrdered.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        dateCreatedCol.setPrefWidth(140);
        dateCreatedCol.setSortType(TableColumn.SortType.DESCENDING);

        TableColumn<Invoice, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.total.getName()));
        totalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        totalCol.setPrefWidth(120);

        TableColumn<Invoice, String> dateInvoicedCol = new TableColumn<>("Date Invoiced");
        dateInvoicedCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.dateInvoiced.getName()));
        dateInvoicedCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        dateInvoicedCol.setPrefWidth(150);

        fTableView.getColumns().add(orderNoCol);
        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(dateInvoicedCol);
        fTableView.getColumns().add(totalCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(120);
        skuCol.setCellFactory(stringCell(Pos.TOP_LEFT));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                return new SimpleStringProperty(p.getValue().getItemDescription());
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(300);

        TableColumn<InvoiceEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantity());
            }
        });
        detailQtyCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailQtyCol.setMinWidth(100);

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
        detailPriceCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
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
        discountCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        discountCol.setPrefWidth(100);
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

        detailTotalCol.setMinWidth(110);
        detailTotalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        fDetailTableView.getColumns().add(skuCol);
        fDetailTableView.getColumns().add(detailDescriptionCol);
        fDetailTableView.getColumns().add(detailQtyCol);
        fDetailTableView.getColumns().add(detailPriceCol);
        fDetailTableView.getColumns().add(discountCol);
        fDetailTableView.getColumns().add(detailTotalCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(100);

        TableColumn<ServiceEntry, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getDateEntered() != null) {
                return new SimpleStringProperty(getString(p.getValue().getDateEntered()));
            } else {
                return null;
            }
        });
        dateCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        dateCol.setPrefWidth(130);

        TableColumn<ServiceEntry, String> techCol = new TableColumn<>("Tech");
        techCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getEmployee() != null) {
                return new SimpleStringProperty(p.getValue().getEmployee().getNameOnSalesOrder());
            } else {
                return null;
            }
        });
        techCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        techCol.setPrefWidth(120);

        TableColumn<ServiceEntry, String> serviceCodeCol = new TableColumn<>("Service Code");
        serviceCodeCol.setCellValueFactory((TableColumn.CellDataFeatures<ServiceEntry, String> p) -> {
            if (p.getValue().getServiceCode() != null) {
                return new SimpleStringProperty(p.getValue().getServiceCode().getCode());
            } else {
                return null;
            }
        });
        serviceCodeCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        serviceCodeCol.setPrefWidth(180);

        TableColumn<ServiceEntry, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(ServiceEntry_.note.getName()));
        noteCol.setCellFactory(tc -> {
            TableCell<ServiceEntry, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setAlignment(Pos.TOP_LEFT);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(noteCol.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });
        noteCol.setPrefWidth(400);

        fServiceEntryTable.getColumns().add(dateCol);
        fServiceEntryTable.getColumns().add(techCol);
        fServiceEntryTable.getColumns().add(serviceCodeCol);
        fServiceEntryTable.getColumns().add(noteCol);
        fServiceEntryTable.setPrefHeight(220);
        setTableWidth(fServiceEntryTable);
        fServiceEntryTable.setEditable(false);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(fDetailTableView, 0, 3);
        mainPane.add(fServiceEntryTable, 0, 4);
        mainPane.add(createPrintExportCloseButtonPane(), 0, 5);
        mainPane.setPadding(new Insets(2, 5, 2, 5));
        return mainPane;
    }

    private HBox createPrintExportCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, exportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

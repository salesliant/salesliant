package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Customer_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.Item;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrderEntry;
import com.salesliant.entity.SerialNumber;
import com.salesliant.report.InvoiceLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.InvoiceSearchField;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class InvoiceListUI extends BaseListUI<Invoice> {

    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final BaseDao<SalesOrder> daoSalesOrder = new BaseDao<>(SalesOrder.class);
    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);
    private TableView<InvoiceEntry> fDetailTableView = new TableView<>();
    private ObservableList<InvoiceEntry> fInvoiceEntryList;
    private ChoiceBox<String> choiceBox;
    private InvoiceUI fInvoiceUI;
    private int fOrderType;
    private Customer fCustomer;
    private final static String TITLE = "Invoice";
    private boolean exist = false;
    private final InvoiceSearchField searchField = new InvoiceSearchField();
    private int from = 100000;
    private int end = 100000;
    private static final Logger LOGGER = Logger.getLogger(InvoiceListUI.class.getName());

    public InvoiceListUI() {
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
                        from = 100000;
                        end = 100000;
                        for (int i = 0; i < aList.size(); i++) {
                            if (aList.get(i).getItemLookUpCode().equalsIgnoreCase("Package")) {
                                from = aList.get(i).getDisplayOrder();
                            }
                            if (aList.get(i).getItemLookUpCode().equalsIgnoreCase("Subtotal")) {
                                end = aList.get(i).getDisplayOrder();
                            }
                        }
                        fInvoiceEntryList = FXCollections.observableList(aList);
                        fDetailTableView.setItems(fInvoiceEntryList);
                    } else {
                        fDetailTableView.getItems().clear();
                    }
                });
        dialogTitle = TITLE;
        Platform.runLater(() -> searchField.requestFocus());
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Invoice> list = daoInvoice.readOrderBy(Invoice_.store, Config.getStore(), Invoice_.dateInvoiced, AppConstants.ORDER_BY_DESC);
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_DISPLAY:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fInvoiceUI = new InvoiceUI(fEntity);
                    fInvoiceUI.setParent(this);
                    fInputDialog = createUIDialog(fInvoiceUI.getView(), fInvoiceUI.getTitle());
                    fInvoiceUI.closeBtn.setOnAction(e -> {
                        fInputDialog.close();
                    });
                    Platform.runLater(() -> {
                        fInvoiceUI.closeBtn.requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_CLONE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    boolean isOldStyleInvoice = isOldStyleInvoice(fEntity);
                    if (!isOldStyleInvoice) {
                        fCustomer = daoCustomer.find(Customer_.store, Config.getStore(), Customer_.accountNumber, fEntity.getCustomerAccountNumber());
                        if (fCustomer != null) {
                            String type = "";
                            if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
                                type = "order";
                            } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
                                type = "quote";
                            }
                            Response answer = createConfirmResponseDialog("Do you want to create a new " + type + " base on this invoice?");
                            if (answer.equals(Response.YES)) {
                                SalesOrder so = cloneInvoice(fEntity);
                                daoSalesOrder.insert(so);
                                if (daoInvoice.getErrorMessage() != null) {
                                    showAlertDialog("Fail to copy the selected invoice");
                                } else {
                                    showAlertDialog("Copy complete.");
                                }
                            }
                        } else {
                            showAlertDialog("The customer of the selected invoice is no longer available! ");
                        }
                    } else {
                        showAlertDialog("Can't convert old style invoice! ");
                    }

                }
                break;
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
            case AppConstants.ACTION_VOID:
                exist = false;
                fEntityList.stream().filter((so) -> (so.getId().equals(fEntity.getId()))).map((so) -> {
                    exist = true;
                    return so;
                }).forEach((so) -> {
                    so = fEntity;
                });
                if (exist) {
                    fEntityList.remove(fEntity);
                    fEntityList.set(fIndex, fEntity);

                }
                fTableView.refresh();
                fTableView.requestFocus();
                fInputDialog.close();
                choiceBox.getSelectionModel().selectFirst();
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

        TableColumn<Invoice, String> paymentCol = new TableColumn<>("Payment Type");
        paymentCol.setCellValueFactory((TableColumn.CellDataFeatures<Invoice, String> p) -> {
            if (p.getValue().getPayments() != null && !p.getValue().getPayments().isEmpty() && p.getValue().getPayments().get(0) != null && p.getValue().getPayments().get(0).getPaymentType() != null) {
                return new SimpleStringProperty(p.getValue().getPayments().get(0).getPaymentType().getCode());
            } else {
                return null;
            }
        });
        paymentCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        paymentCol.setPrefWidth(150);

        TableColumn<Invoice, String> employeeCol = new TableColumn<>("Sales");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>(Invoice_.salesName.getName()));
        employeeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        employeeCol.setPrefWidth(100);

        fTableView.getColumns().add(orderNoCol);
        fTableView.getColumns().add(nameCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(totalCol);
        fTableView.getColumns().add(paymentCol);
        fTableView.getColumns().add(employeeCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setMinWidth(150);
        skuCol.setCellFactory(stringCell(Pos.TOP_LEFT));

        TableColumn<InvoiceEntry, String> detailDescriptionCol = new TableColumn<>("Description");
        detailDescriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                String description = getString(p.getValue().getItemDescription());
                if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                    description = description + "\n" + p.getValue().getLineNote();
                }
                if (p.getValue().getSerialNumbers() != null & !p.getValue().getSerialNumbers().isEmpty()) {
                    description = description + "\n";
                    for (SerialNumber sn : p.getValue().getSerialNumbers()) {
                        description = description + getString(sn.getSerialNumber()) + "; ";
                    }
                }
                description = description.trim();
                if (description != null && description.length() > 0 && description.charAt(description.length() - 1) == ';') {
                    description = description.substring(0, description.length() - 1);
                }
                return new SimpleStringProperty(description);
            }
        });
        detailDescriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        detailDescriptionCol.setMinWidth(320);

        TableColumn<InvoiceEntry, BigDecimal> detailQtyCol = new TableColumn<>("Qty");
        detailQtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantity());
            }
        });
        detailQtyCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailQtyCol.setMinWidth(60);

        TableColumn<InvoiceEntry, BigDecimal> detailPriceCol = new TableColumn<>("Price");
        detailPriceCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                BigDecimal price = p.getValue().getPrice();
                if (from < 10000 && end < 10000 && p.getValue().getDisplayOrder() > from && p.getValue().getDisplayOrder() < end) {
                    price = null;
                }
                return new ReadOnlyObjectWrapper(price);
            }
        });
        detailPriceCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        detailPriceCol.setMinWidth(100);

        TableColumn<InvoiceEntry, String> discountCol = new TableColumn<>("Taxable");
        discountCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getTaxable());
            }
        });
        discountCol.setCellFactory(stringCell(Pos.TOP_CENTER));
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
                    if (from < 10000 && end < 10000 && p.getValue().getDisplayOrder() > from && p.getValue().getDisplayOrder() < end) {
                        total = null;
                    }
                    return new ReadOnlyObjectWrapper(total);
                } else {
                    return null;
                }
            }

        });
        detailTotalCol.setMinWidth(100);
        detailTotalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));

        fDetailTableView.getColumns().add(skuCol);
        fDetailTableView.getColumns().add(detailDescriptionCol);
        fDetailTableView.getColumns().add(detailQtyCol);
        fDetailTableView.getColumns().add(detailPriceCol);
        fDetailTableView.getColumns().add(discountCol);
        fDetailTableView.getColumns().add(detailTotalCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(300);
        setTableWidth(fDetailTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(fDetailTableView, 0, 3);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button exportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EXPORT, AppConstants.ACTION_EXPORT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        Button displayButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DISPLAY, AppConstants.ACTION_DISPLAY, fHandler);
        String[] convertInvoice = new String[]{"Copy To", "Quote"};
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(convertInvoice));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> ov, Number value, Number new_value) -> {
            switch (new_value.intValue()) {
                case 1:
                    fOrderType = DBConstants.TYPE_SALESORDER_QUOTE;
                    handleAction(AppConstants.ACTION_CLONE);
                    choiceBox.getSelectionModel().select(0);
                    break;
            }
        });
        choiceBox.setPrefWidth(98);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(choiceBox, displayButton, printButton, exportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private SalesOrder cloneInvoice(Invoice oldInvoice) {
        SalesOrder newSalesOrder = new SalesOrder();
        newSalesOrder.setDateOrdered(new Timestamp(new Date().getTime()));
        newSalesOrder.setSales(Config.getEmployee());
        newSalesOrder.setNote(oldInvoice.getNote());
        newSalesOrder.setTotal(oldInvoice.getTotal());
        newSalesOrder.setShippingCharge(oldInvoice.getShippingCharge());
        newSalesOrder.setStore(Config.getStore());
        newSalesOrder.setStation(Config.getStation());
        if (fOrderType == DBConstants.TYPE_SALESORDER_ORDER) {
            newSalesOrder.setType(DBConstants.TYPE_SALESORDER_ORDER);
            newSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_ORDER_NUMBER));
        } else if (fOrderType == DBConstants.TYPE_SALESORDER_QUOTE) {
            newSalesOrder.setType(DBConstants.TYPE_SALESORDER_QUOTE);
            newSalesOrder.setSalesOrderNumber(Config.getNumber(DBConstants.SEQ_QUOTE_NUMBER));
        }
        newSalesOrder.setCustomer(fCustomer);
        List<SalesOrderEntry> list = new ArrayList<>();
        oldInvoice.getInvoiceEntries().forEach(e -> {
            SalesOrderEntry soe = new SalesOrderEntry();
            Item item = getItem(e.getItemLookUpCode());
            if (item != null) {
                soe.setDisplayOrder(e.getDisplayOrder());
                soe.setItem(item);
                soe.setLineNote(e.getLineNote());
                soe.setQuantity(e.getQuantity());
                soe.setSalesOrder(newSalesOrder);
                soe.setTaxable(e.getTaxable());
                soe.setComponentFlag(e.getComponentFlag());
                soe.setComponentQuantity(e.getComponentQuantity());
                soe.setCost(item.getCost());
                soe.setPrice(getItemPrice(item, newSalesOrder.getCustomer()));
                list.add(soe);
            }
        });
        list.stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder())).collect(Collectors.toList());
        newSalesOrder.setSalesOrderEntries(list);
        return newSalesOrder;
    }

    private boolean isOldStyleInvoice(Invoice invoice) {
        boolean is = false;
        if (invoice != null && invoice.getInvoiceEntries() != null && !invoice.getInvoiceEntries().isEmpty()) {
            for (InvoiceEntry ie : invoice.getInvoiceEntries()) {
                if (ie.getItemLookUpCode().toLowerCase().contains("package") || ie.getItemLookUpCode().toLowerCase().equalsIgnoreCase("subtotal")) {
                    is = true;
                    break;
                }
            }
        }
        return is;
    }

    @Override
    protected void validate() {

    }

}

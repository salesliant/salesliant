package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.GiftCertificate;
import com.salesliant.entity.GiftCertificateTransaction;
import com.salesliant.entity.GiftCertificateTransaction_;
import com.salesliant.entity.GiftCertificate_;
import com.salesliant.report.GiftCertificateListReportLayout;
import com.salesliant.report.GiftCertificateTransactionReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class GiftCertificateListUI extends BaseListUI<GiftCertificate> {

    private final BaseDao<GiftCertificate> daoGiftCertificate = new BaseDao<>(GiftCertificate.class);
    private TableView<GiftCertificateTransaction> fDetailTableView = new TableView<>();
    private ObservableList<GiftCertificateTransaction> fGiftCertificateTransactionList;
    private final static String PRINT_REPORT = "Print GiftCertificate Report";
    private final static String PRINT_DETAIL_REPORT = "Print GiftCertificate Detail Report";
    private static final Logger LOGGER = Logger.getLogger(GiftCertificateListUI.class.getName());

    public GiftCertificateListUI() {
        mainView = createMainView();
        List<GiftCertificate> list = daoGiftCertificate.readOrderBy(GiftCertificate_.store, Config.getStore(), GiftCertificate_.status, DBConstants.STATUS_CLOSE, GiftCertificate_.datePurchased, AppConstants.ORDER_BY_DESC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends GiftCertificate> observable, GiftCertificate newValue, GiftCertificate oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        List<GiftCertificateTransaction> transactionList = fTableView.getSelectionModel().getSelectedItem().getGiftCertificateTransactions()
                                .stream()
                                .sorted((e1, e2) -> e1.getInvoice().getDateInvoiced().compareTo(e2.getInvoice().getDateInvoiced()))
                                .collect(Collectors.toList());
                        fGiftCertificateTransactionList = FXCollections.observableList(transactionList);
                        fDetailTableView.setItems(fGiftCertificateTransactionList);
                    } else {
                        fDetailTableView.getItems().clear();
                    }
                });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case PRINT_REPORT:
                if (!fEntityList.isEmpty()) {
                    Platform.runLater(() -> {
                        GiftCertificateListReportLayout layout = new GiftCertificateListReportLayout(fEntityList);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case PRINT_DETAIL_REPORT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Platform.runLater(() -> {
                        GiftCertificateTransactionReportLayout layout = new GiftCertificateTransactionReportLayout(fEntity);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        Label tableLbl = new Label("List of GiftCertificate:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<GiftCertificate, String> codeCol = new TableColumn<>("Gift Certificate");
        codeCol.setCellValueFactory(new PropertyValueFactory<>(GiftCertificate_.code.getName()));
        codeCol.setCellFactory(stringCell(Pos.CENTER));
        codeCol.setPrefWidth(90);

        TableColumn<GiftCertificate, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((TableColumn.CellDataFeatures<GiftCertificate, String> p) -> {
            if (p.getValue() != null && p.getValue().getCustomer() != null) {
                Customer customer = p.getValue().getCustomer();
                String name;
                if (customer.getCompany() != null && !customer.getCompany().isEmpty()) {
                    name = customer.getCompany();
                } else {
                    name = !isEmpty(customer.getFirstName()) ? customer.getFirstName() : ""
                            + (!isEmpty(customer.getFirstName()) ? " " : "")
                            + (!isEmpty(customer.getLastName()) ? customer.getLastName() : "");
                }
                return new SimpleStringProperty(name);
            } else {
                return null;
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER));
        customerCol.setPrefWidth(125);

        TableColumn<GiftCertificate, String> dateCloseCol = new TableColumn<>("Date Purchased");
        dateCloseCol.setCellValueFactory(new PropertyValueFactory<>(GiftCertificate_.datePurchased.getName()));
        dateCloseCol.setCellFactory(stringCell(Pos.CENTER));
        dateCloseCol.setPrefWidth(125);

        TableColumn<GiftCertificate, String> invoiceCol = new TableColumn<>("Invoice");
        invoiceCol.setCellValueFactory((TableColumn.CellDataFeatures<GiftCertificate, String> p) -> {
            if (p.getValue() != null && p.getValue().getInvoice() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getInvoiceNumber().toString());
            } else {
                return null;
            }
        });
        invoiceCol.setCellFactory(stringCell(Pos.CENTER));
        invoiceCol.setPrefWidth(90);

        TableColumn<GiftCertificate, String> originalAmountCol = new TableColumn<>("Original Amount");
        originalAmountCol.setCellValueFactory(new PropertyValueFactory<>(GiftCertificate_.originalAmount.getName()));
        originalAmountCol.setCellFactory(stringCell(Pos.CENTER));
        originalAmountCol.setPrefWidth(90);

        TableColumn<GiftCertificate, String> balanceCol = new TableColumn<>("Balance");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>(GiftCertificate_.balance.getName()));
        balanceCol.setCellFactory(stringCell(Pos.CENTER));
        balanceCol.setPrefWidth(90);

        fTableView.getColumns().add(codeCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(dateCloseCol);
        fTableView.getColumns().add(invoiceCol);
        fTableView.getColumns().add(originalAmountCol);
        fTableView.getColumns().add(balanceCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        TableColumn<GiftCertificateTransaction, String> typeCol = new TableColumn<>("Transaction Type");
        typeCol.setCellValueFactory((TableColumn.CellDataFeatures<GiftCertificateTransaction, String> p) -> {
            if (p.getValue() != null && p.getValue().getInvoice() != null) {
                return new SimpleStringProperty("Invoice");
            } else if (p.getValue() != null && p.getValue().getSalesOrder() != null) {
                return new SimpleStringProperty("Order");
            } else {
                return null;
            }
        });
        typeCol.setCellFactory(stringCell(Pos.CENTER));
        typeCol.setMinWidth(205);

        TableColumn<GiftCertificateTransaction, String> numberCol = new TableColumn<>("Number");
        numberCol.setCellValueFactory((TableColumn.CellDataFeatures<GiftCertificateTransaction, String> p) -> {
            if (p.getValue() != null && p.getValue().getInvoice() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getInvoiceNumber().toString());
            } else if (p.getValue() != null && p.getValue().getSalesOrder() != null) {
                return new SimpleStringProperty(p.getValue().getSalesOrder().getSalesOrderNumber().toString());
            } else {
                return null;
            }
        });
        numberCol.setCellFactory(stringCell(Pos.CENTER));
        numberCol.setMinWidth(205);

        TableColumn<GiftCertificateTransaction, String> transactionDateCol = new TableColumn<>("Date");
        transactionDateCol.setCellValueFactory(new PropertyValueFactory<>(GiftCertificateTransaction_.dateUpdated.getName()));
        transactionDateCol.setCellFactory(stringCell(Pos.CENTER));
        transactionDateCol.setMinWidth(100);

        TableColumn<GiftCertificateTransaction, String> transactionAmountCol = new TableColumn<>("Amount");
        transactionAmountCol.setCellValueFactory(new PropertyValueFactory<>(GiftCertificateTransaction_.amount.getName()));
        transactionAmountCol.setCellFactory(stringCell(Pos.CENTER));
        transactionAmountCol.setMinWidth(100);

        fDetailTableView.getColumns().add(typeCol);
        fDetailTableView.getColumns().add(numberCol);
        fDetailTableView.getColumns().add(transactionDateCol);
        fDetailTableView.getColumns().add(transactionAmountCol);

        fDetailTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fDetailTableView.setPrefHeight(200);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(fDetailTableView, 0, 3);
        mainPane.add(createButtonPane(), 0, 4);
        return mainPane;
    }

    protected HBox createButtonPane() {
        Button printGiftCertificateReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_REPORT, "Print Report", fHandler);
        printGiftCertificateReportButton.setPrefWidth(120);
        Button printGiftCertificateDetailReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_DETAIL_REPORT, "Print Detail Report", fHandler);
        printGiftCertificateDetailReportButton.setPrefWidth(150);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printGiftCertificateReportButton, printGiftCertificateDetailReportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

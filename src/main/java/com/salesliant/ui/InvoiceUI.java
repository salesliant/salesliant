package com.salesliant.ui;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.InvoiceEntry;
import com.salesliant.entity.InvoiceEntry_;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.ServiceEntry;
import com.salesliant.entity.ServiceEntry_;
import com.salesliant.report.InvoiceLayout;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.DataUI;
import com.salesliant.util.AppConstants;
import static com.salesliant.util.BaseUtil.getDoubleFormat;
import static com.salesliant.util.BaseUtil.getPercentFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public final class InvoiceUI extends BaseListUI<InvoiceEntry> {

    private final DataUI invoiceUI = new DataUI(Invoice.class);
    private Invoice fInvoice;
    private final TextArea fBillToAddressTA = new TextArea(), fShipToAddressTA = new TextArea();
    private TabPane fTabPane = new TabPane();
    private Tab fNoteTab = new Tab(), fServiceTab = new Tab();
    public Button closeBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE);
    private TextField fProfitField = createLabelField(90.0, Pos.CENTER_RIGHT), fProfitPercentField = createLabelField(90.0, Pos.CENTER_RIGHT);
    private String title = "Invoice";
    private TableView<ServiceEntry> fServiceEntryTable = new TableView<>();
    private static final Logger LOGGER = Logger.getLogger(InvoiceUI.class.getName());

    public InvoiceUI(Invoice invoice) {
        this.fInvoice = invoice;
        if (fInvoice.getInvoiceEntries() != null) {
            List<InvoiceEntry> list = fInvoice.getInvoiceEntries().stream()
                    .sorted((e1, e2) -> e1.getDisplayOrder().compareTo(e2.getDisplayOrder()))
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
        } else {
            fEntityList = FXCollections.observableArrayList();
        }
        fTableView.setItems(fEntityList);
        mainView = createMainView();
        updateAddressPane();

        try {
            invoiceUI.setData(fInvoice);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        dialogTitle = "Invoice Entry";
        init();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                InvoiceLayout layout = new InvoiceLayout(fInvoice);
                try {
                    JasperReportBuilder report = layout.build();
                    report.print(true);
                } catch (DRException ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);

        TableColumn<InvoiceEntry, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.itemLookUpCode.getName()));
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        skuCol.setPrefWidth(100);

        TableColumn<InvoiceEntry, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, String> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") && p.getValue().getItemDescription().equalsIgnoreCase("SYSSN")) {
                return new SimpleStringProperty(p.getValue().getNote());
            } else {
                return new SimpleStringProperty(p.getValue().getItemDescription());
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(290);

        TableColumn<InvoiceEntry, BigDecimal> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantity());
            }
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(50);

        TableColumn<InvoiceEntry, BigDecimal> qtyBackOrderCol = new TableColumn<>("BackOrder");
        qtyBackOrderCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getQuantityBackorder());
            }
        });
        qtyBackOrderCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyBackOrderCol.setPrefWidth(70);

        TableColumn<InvoiceEntry, BigDecimal> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getPrice());
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        priceCol.setPrefWidth(85);

        TableColumn<InvoiceEntry, BigDecimal> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getCost());
            }
        });
        costCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        costCol.setPrefWidth(75);

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
        discountCol.setPrefWidth(75);

        TableColumn<InvoiceEntry, BigDecimal> couponCol = new TableColumn<>("Coupon");
        couponCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
            if (p.getValue().getComponentFlag() != null && p.getValue().getComponentFlag()) {
                return null;
            } else if (p.getValue().getItemLookUpCode().equalsIgnoreCase("NOTE:") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SYSSN") || p.getValue().getItemLookUpCode().equalsIgnoreCase("SUBTOTAL")) {
                return null;
            } else {
                return new ReadOnlyObjectWrapper(p.getValue().getCouponAmount());
            }
        });
        couponCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        couponCol.setPrefWidth(75);

        TableColumn<InvoiceEntry, String> taxableCol = new TableColumn<>("Taxable");
        taxableCol.setCellValueFactory(new PropertyValueFactory<>(InvoiceEntry_.taxable.getName()));
        taxableCol.setCellFactory(stringCell(Pos.CENTER));

        TableColumn<InvoiceEntry, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((TableColumn.CellDataFeatures<InvoiceEntry, BigDecimal> p) -> {
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
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(90);

        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(discountCol);
        fTableView.getColumns().add(couponCol);
        fTableView.getColumns().add(totalCol);
        setTableWidth(fTableView);

        mainPane.add(createAddressPane(), 0, 0);
        mainPane.add(createSettingPane(), 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(createBottomPane(), 0, 3);
        mainPane.add(createButtonPane(), 0, 4);

        return mainPane;
    }

    private void init() {
        fTableView.setEditable(false);
        fTableView.setFocusTraversable(false);
        fBillToAddressTA.setFocusTraversable(false);
        fShipToAddressTA.setFocusTraversable(false);
        fBillToAddressTA.setEditable(false);
        fShipToAddressTA.setEditable(false);
        fProfitField.setEditable(false);
        fProfitField.setFocusTraversable(false);
        fProfitPercentField.setEditable(false);
        fProfitPercentField.setFocusTraversable(false);

        BigDecimal pf;
        BigDecimal pfp;
        pf = zeroIfNull(fInvoice.getSubTotal()).subtract(zeroIfNull(fInvoice.getCost()));
        if (fInvoice.getSubTotal() != null && (fInvoice.getSubTotal().compareTo(BigDecimal.ZERO) != 0)) {
            pfp = (zeroIfNull(fInvoice.getSubTotal())).subtract(zeroIfNull(fInvoice.getCost())).divide(zeroIfNull(fInvoice.getSubTotal()), 4, RoundingMode.HALF_UP);
        } else {
            pfp = BigDecimal.ZERO;
        }

        fProfitField.setText(getDoubleFormat().format(pf));
        fProfitPercentField.setText(getPercentFormat().format(pfp));
        if (fInvoice.getService() != null) {
            fTabPane.getTabs().add(fServiceTab);
        }
        if (fInvoice.getNote() != null && !fInvoice.getNote().isEmpty()) {
            fTabPane.getTabs().add(fNoteTab);
        }
    }

    private Node createAddressPane() {
        Label customerIDLabel = new Label("Customer: ");
        fBillToAddressTA.setEditable(false);
        fBillToAddressTA.setFont(Font.font("Arial Narrow", 12));
        fBillToAddressTA.setPrefSize(400, 110);
        Label shipToLable = new Label(" Ship To:  ");
        fShipToAddressTA.setEditable(false);
        fShipToAddressTA.setFont(Font.font("Arial Narrow", 12));
        fShipToAddressTA.setPrefSize(400, 110);
        GridPane addressPane = new GridPane();
        addressPane.setHgap(5.0);
        addressPane.setVgap(3.0);
        Label hGap = new Label(" ");
        addressPane.add(customerIDLabel, 0, 0);
        addressPane.add(fBillToAddressTA, 0, 1, 2, 1);
        addressPane.add(hGap, 1, 0, 1, 2);
        addressPane.add(shipToLable, 2, 0);
        addressPane.add(fShipToAddressTA, 2, 1, 2, 1);

        GridPane.setHalignment(shipToLable, HPos.LEFT);
        GridPane.setHalignment(fShipToAddressTA, HPos.RIGHT);
        GridPane.setHgrow(hGap, Priority.ALWAYS);

        return addressPane;
    }

    private GridPane createSettingPane() {
        GridPane settingPane = new GridPane();
        settingPane.setHgap(3.0);
        Label invoiceNumberLabel = new Label("Invoice No:");
        settingPane.add(invoiceNumberLabel, 0, 0);
        settingPane.add(invoiceUI.createLabelTextField(Invoice_.invoiceNumber, 90), 1, 0);
        Label employeeLabel = new Label("Salesperson:");
        settingPane.add(employeeLabel, 2, 0);
        settingPane.add(invoiceUI.createLabelTextField(Invoice_.salesName, 90), 3, 0);
        Label shipViaLabel = new Label("Ship Via:");
        settingPane.add(shipViaLabel, 4, 0);
        settingPane.add(invoiceUI.createLabelTextField(Invoice_.shipVia, 90), 5, 0);
        Label customerPOLabel = new Label("Customer PO:");
        settingPane.add(customerPOLabel, 6, 0);
        settingPane.add(invoiceUI.createLabelTextField(Invoice_.customerPoNumber, 90), 7, 0);

        GridPane.setHalignment(invoiceNumberLabel, HPos.RIGHT);
        GridPane.setHalignment(employeeLabel, HPos.RIGHT);
        GridPane.setHalignment(shipViaLabel, HPos.RIGHT);

        return settingPane;
    }

    private Node createBottomPane() {
        GridPane bottomPane = new GridPane();
        GridPane bottomLeftPane = new GridPane();
        fTabPane.setSide(Side.TOP);
        fNoteTab = createNoteTab();
        fServiceTab = createServiceTab();
        bottomLeftPane.add(fTabPane, 0, 0);

        GridPane bottomRightPane = createButtomRightPane();
        Label hGap = new Label("           ");
        GridPane.setHalignment(bottomRightPane, HPos.RIGHT);
        bottomPane.add(bottomLeftPane, 0, 0);
        bottomPane.add(hGap, 1, 0, 1, 1);
        bottomPane.add(bottomRightPane, 2, 0);

        GridPane.setHalignment(bottomLeftPane, HPos.LEFT);
        GridPane.setHalignment(bottomRightPane, HPos.RIGHT);
        GridPane.setHgrow(bottomLeftPane, Priority.ALWAYS);

        return bottomPane;
    }

    private GridPane createButtomRightPane() {
        GridPane sumPane = new GridPane();
        sumPane.setVgap(1.0);
        Label subTotalLabel = new Label(" Sub Total: ");
        Label depositLabel = new Label("Deposit: ");
        Label storeCreditLabel = new Label("Store Credit: ");
        Label taxLabel = new Label("Tax: ");
        Label totalLabel = new Label("Total: ");
        Label costLabel = new Label("Cost: ");
        Label profitLabel = new Label("Profit: ");
        Label profitPercentLabel = new Label("Profit %: ");

        fProfitField.setPrefWidth(100);
        fProfitPercentField.setPrefWidth(100);
        sumPane.add(subTotalLabel, 0, 0);
        sumPane.add(invoiceUI.createLabelField(Invoice_.subTotal, 100, Pos.CENTER_RIGHT), 1, 0);
        sumPane.add(depositLabel, 0, 1);
        sumPane.add(invoiceUI.createLabelField(Invoice_.depositAmount, 100, Pos.CENTER_RIGHT), 1, 1);
        sumPane.add(storeCreditLabel, 0, 2);
        sumPane.add(invoiceUI.createLabelField(Invoice_.creditAmount, 100, Pos.CENTER_RIGHT), 1, 2);
        sumPane.add(taxLabel, 0, 3);
        sumPane.add(invoiceUI.createLabelField(Invoice_.taxAmount, 100, Pos.CENTER_RIGHT), 1, 3);
        sumPane.add(totalLabel, 0, 4);
        sumPane.add(invoiceUI.createLabelField(Invoice_.total, 100, Pos.CENTER_RIGHT), 1, 4);
        sumPane.add(costLabel, 0, 5);
        sumPane.add(invoiceUI.createLabelField(Invoice_.cost, 100, Pos.CENTER_RIGHT), 1, 5);
        sumPane.add(profitLabel, 0, 6);
        sumPane.add(fProfitField, 1, 6);
        sumPane.add(profitPercentLabel, 0, 7);
        sumPane.add(fProfitPercentField, 1, 7);
        GridPane.setHalignment(subTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(depositLabel, HPos.RIGHT);
        GridPane.setHalignment(storeCreditLabel, HPos.RIGHT);
        GridPane.setHalignment(taxLabel, HPos.RIGHT);
        GridPane.setHalignment(totalLabel, HPos.RIGHT);
        GridPane.setHalignment(costLabel, HPos.RIGHT);
        GridPane.setHalignment(profitLabel, HPos.RIGHT);
        GridPane.setHalignment(profitPercentLabel, HPos.RIGHT);

        sumPane.setAlignment(Pos.TOP_RIGHT);
        return sumPane;
    }

    private Tab createNoteTab() {
        Tab noteTab = new Tab();
        noteTab.setText("Note");
        GridPane notePane = new GridPane();
        notePane.setAlignment(Pos.CENTER_LEFT);
        notePane.getStyleClass().add("insideView");
        noteTab.setContent(notePane);
        return noteTab;
    }

    private Tab createServiceTab() {
        Tab serviceTab = new Tab();
        serviceTab.setText("Service");
        GridPane servicePane = new GridPane();
        servicePane.getStyleClass().add("insideView");
        servicePane.setAlignment(Pos.CENTER_LEFT);

        TableColumn<ServiceEntry, String> dateCol = new TableColumn<>("Time");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(ServiceEntry_.dateEntered.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER));
        dateCol.setPrefWidth(80);

        TableColumn<ServiceEntry, String> employeeCol = new TableColumn<>("Tech");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>(ServiceEntry_.employeeName.getName()));
        employeeCol.setCellFactory(stringCell(Pos.CENTER));
        employeeCol.setPrefWidth(100);

        TableColumn<ServiceEntry, String> serviceCodeCol = new TableColumn<>("Service Code");
        serviceCodeCol.setCellValueFactory(new PropertyValueFactory<>(ServiceEntry_.serviceCode.getName()));
        serviceCodeCol.setCellFactory(stringCell(Pos.CENTER));
        serviceCodeCol.setPrefWidth(140);

        TableColumn<ServiceEntry, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(ServiceEntry_.note.getName()));
        noteCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        noteCol.setPrefWidth(300);

        fServiceEntryTable.getColumns().add(dateCol);
        fServiceEntryTable.getColumns().add(employeeCol);
        fServiceEntryTable.getColumns().add(serviceCodeCol);
        fServiceEntryTable.getColumns().add(noteCol);
        fServiceEntryTable.setPrefHeight(130);
        fServiceEntryTable.setEditable(false);

        servicePane.add(fServiceEntryTable, 0, 2);
        serviceTab.setContent(servicePane);
        return serviceTab;
    }

    private HBox createButtonPane() {
        HBox btnBox = new HBox(4);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        btnBox.getChildren().addAll(printButton, closeBtn);

        return btnBox;
    }

    private void updateAddressPane() {
        fBillToAddressTA.setText("");
        fShipToAddressTA.setText("");
        String billTo = "";
        if (fInvoice.getCustomerName() != null && !fInvoice.getCustomerName().isEmpty()) {
            billTo = billTo + getString(fInvoice.getCustomerName()) + "\n";
        }
        if (fInvoice.getBillToCompany() != null && !fInvoice.getBillToCompany().isEmpty()) {
            billTo = billTo + getString(fInvoice.getBillToCompany()) + "\n";
        }
        if (fInvoice.getBillToAddress1() != null && !fInvoice.getBillToAddress1().isEmpty()) {
            billTo = billTo + getString(fInvoice.getBillToAddress1()) + "\n";
        }
        if (fInvoice.getBillToAddress2() != null && !fInvoice.getBillToAddress2().isEmpty()) {
            billTo = billTo + getString(fInvoice.getBillToAddress2()) + "\n";
        }
        billTo = billTo + getString(fInvoice.getBillToCity()) + ", " + getString(fInvoice.getBillToState()) + " " + getString(fInvoice.getBillToPostCode()) + "\n";
        if (fInvoice.getBillToCountry() != null && !fInvoice.getBillToCountry().isEmpty()) {
            billTo = billTo + getString(fInvoice.getBillToCountry()) + "\n";
        }
        if (fInvoice.getPhoneNumber() != null && !fInvoice.getPhoneNumber().isEmpty()) {
            billTo = billTo + getString(fInvoice.getPhoneNumber());
        }
        fBillToAddressTA.setText(billTo);
        if (fInvoice.getShipToAddress() != null && !fInvoice.getShipToAddress().isEmpty()) {
            fShipToAddressTA.setText(fInvoice.getShipToAddress().trim());
        } else {
            fShipToAddressTA.setText(billTo);
        }
    }

    public String getTitle() {
        return title;
    }
}

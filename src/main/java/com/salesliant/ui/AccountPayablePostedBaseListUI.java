package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountPayable;
import com.salesliant.entity.AccountPayableBatch;
import com.salesliant.entity.AccountPayableHistory;
import com.salesliant.entity.AccountPayable_;
import com.salesliant.entity.Vendor;
import com.salesliant.report.AccountPayableAgingReportLayout;
import com.salesliant.report.AccountPayableBatchLayout;
import com.salesliant.report.AccountPayableReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.zeroIfNull;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.sql.Timestamp;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class AccountPayablePostedBaseListUI extends BaseListUI<AccountPayable> {

    private final BaseDao<AccountPayable> daoAccountPayable = new BaseDao<>(AccountPayable.class);
    private final BaseDao<AccountPayableBatch> daoAccountPayableBatch = new BaseDao<>(AccountPayableBatch.class);
    private final BaseDao<AccountPayableHistory> daoAccountPayableHistory = new BaseDao<>(AccountPayableHistory.class);
    private final DataUI dataUI = new DataUI(AccountPayable.class);
    protected final TableView<List<AccountPayable>> fSummaryTable = new TableView<>();
    private final static String ENTER_DISCOUNT = "Enter Discount";
    private final static String ADJUST_PAYMENT = "Adjust Payment";
    private final static String PRINT_AP_REPORT = "Print AP Report";
    private final static String PRINT_AP_AGING_REPORT = "Print AP Aging Report";
    private Date f0, f30, f60, f90;
    protected final TableColumn<AccountPayable, AccountPayable> selectedCol = new TableColumn<>("");
    protected final TableColumn<List<AccountPayable>, BigDecimal> paymentTotalCol = new TableColumn<>("Payment");
    protected final TableColumn<List<AccountPayable>, String> companyCol = new TableColumn<>("Vendor");
    protected ObservableSet<AccountPayable> selectedItems = FXCollections.observableSet();
    protected List<AccountPayable> accounPayableList = new ArrayList<>();
    private List<AccountPayable> fList = new ArrayList<>();
    private List<List<AccountPayable>> fSummaryList = new ArrayList<>();
    protected final ObjectProperty<BigDecimal> totalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    protected GridPane fDiscountPane, fToPayPane;
    private static final Logger LOGGER = Logger.getLogger(AccountPayablePostedBaseListUI.class.getName());

    public AccountPayablePostedBaseListUI() {
    }

    protected final void loadData() {
        f0 = new Date();
        Calendar c30 = Calendar.getInstance();
        c30.setTime(new Date());
        c30.add(Calendar.DATE, -30);
        f30 = c30.getTime();
        Calendar c60 = Calendar.getInstance();
        c60.setTime(new Date());
        c60.add(Calendar.DATE, -60);
        f60 = c60.getTime();
        Calendar c90 = Calendar.getInstance();
        c90.setTime(new Date());
        c90.add(Calendar.DATE, -90);
        f90 = c90.getTime();
        Map<Vendor, List<AccountPayable>> groupByVendor = daoAccountPayable.readOrderBy(AccountPayable_.store, Config.getStore(),
                AccountPayable_.postedTag, true, AccountPayable_.dateInvoiced, AppConstants.ORDER_BY_ASC)
                .stream()
                .filter(e -> e.getVendor() != null && e.getAccountPayableType() != null)
                .filter(e -> e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV) || e.getAccountPayableType().equals(DBConstants.TYPE_APAR_CRE))
                .collect(Collectors.groupingBy(e -> e.getVendor()));
        fSummaryList = new ArrayList<>(groupByVendor.values())
                .stream()
                .sorted((e1, e2) -> e1.get(0).getVendor().getCompany().toLowerCase().compareTo(e2.get(0).getVendor().getCompany().toLowerCase())).collect(Collectors.toList());
        fSummaryTable.setItems(FXCollections.observableList(fSummaryList));
        fSummaryTable.requestFocus();
        selectedItems.clear();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case PRINT_AP_REPORT:
                if (!fSummaryList.isEmpty()) {
                    Platform.runLater(() -> {
                        AccountPayableReportLayout layout = new AccountPayableReportLayout(fSummaryList);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case PRINT_AP_AGING_REPORT:
                if (!fSummaryList.isEmpty()) {
                    Platform.runLater(() -> {
                        AccountPayableAgingReportLayout layout = new AccountPayableAgingReportLayout(fSummaryList);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.show(false);
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                }
                break;
            case AppConstants.ACTION_SELECT_ALL:
                fTableView.getItems().forEach(e -> {
                    if (selectedItems != null && !selectedItems.contains(e)) {
                        selectedItems.add(e);
                    }
                });
                break;
            case AppConstants.ACTION_UN_SELECT_ALL:
                fTableView.getItems().forEach(e -> {
                    if (selectedItems != null && selectedItems.contains(e)) {
                        selectedItems.remove(e);
                    }
                });
                break;
            case AppConstants.ACTION_PROCESS:
                if (selectedItems != null && !selectedItems.isEmpty()) {
                    fList.clear();
                    fList = new ArrayList<>(selectedItems);
                    AccountPayableBatch apb = new AccountPayableBatch();
                    List<AccountPayableHistory> aphList = new ArrayList<>();
                    apb.setBatchNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_PAYABLE_BATCH_NUMBER));
                    apb.setDatePaidOn(new Timestamp(new Date().getTime()));
                    apb.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                    apb.setStore(Config.getStore());
                    BigDecimal totalAmount = BigDecimal.ZERO;
                    for (AccountPayable e : fList) {
                        if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                            e.setPaidAmount(e.getPaidAmount());
                        }
                        if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_CRE)) {
                            e.setPaidAmount(e.getPaidAmount().negate());
                        }
                        BigDecimal netAmount = e.getTotalAmount().subtract(e.getDiscountAmount()).subtract(e.getPaidAmount());
                        if (netAmount.compareTo(BigDecimal.ZERO) >= 0) {
                            totalAmount = totalAmount.add(e.getPaidAmount());
                            AccountPayableHistory aph = new AccountPayableHistory();
                            aph.setAccountPayableType(e.getAccountPayableType());
                            aph.setDateInvoiced(e.getDateInvoiced());
                            aph.setDateDue(e.getDateDue());
                            aph.setDatePaidOn(new Timestamp(new Date().getTime()));
                            aph.setDatePosted(e.getDatePosted());
                            aph.setDiscountAmount(e.getDiscountAmount());
                            aph.setGlAccount(e.getGlAccount());
                            if (e.getPaidAmount().compareTo(BigDecimal.ZERO) >= 0) {
                                aph.setGlDebitAmount(e.getPaidAmount());
                                aph.setGlCreditAmount(BigDecimal.ZERO);
                            } else {
                                aph.setGlDebitAmount(BigDecimal.ZERO);
                                aph.setGlCreditAmount(e.getPaidAmount().negate());
                            }
                            aph.setStore(Config.getStore());
                            aph.setPaidAmount(e.getPaidAmount());
                            aph.setPurchaseOrderHistory(e.getPurchaseOrderHistory());
                            aph.setPurchaseOrderNumber(e.getPurchaseOrderNumber());
                            aph.setTotalAmount(e.getTotalAmount());
                            aph.setVendorInvoiceNumber(e.getVendorInvoiceNumber());
                            aph.setVendorName(e.getVendor().getCompany());
                            daoAccountPayableHistory.insert(aph);
                            aphList.add(aph);
                            if (netAmount.compareTo(BigDecimal.ZERO) > 0) {
                                AccountPayable ap = new AccountPayable();
                                ap.setAccountPayableType(e.getAccountPayableType());
                                ap.setDateInvoiced(e.getDateInvoiced());
                                ap.setDateDue(e.getDateDue());
                                ap.setDatePosted(e.getDatePosted());
                                ap.setGlAccount(e.getGlAccount());
                                ap.setStore(Config.getStore());
                                ap.setPaidAmount(BigDecimal.ZERO);
                                ap.setPurchaseOrderHistory(e.getPurchaseOrderHistory());
                                ap.setPurchaseOrderNumber(e.getPurchaseOrderNumber());
                                ap.setVendorInvoiceNumber(e.getVendorInvoiceNumber());
                                ap.setVendor(e.getVendor());
                                ap.setDiscountAmount(BigDecimal.ZERO);
                                ap.setTotalAmount(netAmount);
                                ap.setDiscountPercent(e.getDiscountPercent());
                                ap.setDiscountDay(e.getDiscountDay());
                                ap.setPostedTag(Boolean.TRUE);
                                ap.setStatus(DBConstants.STATUS_IN_PROGRESS);
                                ap.setGlDebitAmount(BigDecimal.ZERO);
                                ap.setGlCreditAmount(netAmount);
                                daoAccountPayable.insert(ap);
                            }
                        }
                        daoAccountPayable.delete(e);
                    }
                    apb.setTotalAmount(totalAmount);
                    if (totalAmount.compareTo(BigDecimal.ZERO) >= 0) {
                        apb.setGlDebitAmount(totalAmount);
                        apb.setGlCreditAmount(BigDecimal.ZERO);
                    } else {
                        apb.setGlDebitAmount(BigDecimal.ZERO);
                        apb.setGlCreditAmount(totalAmount.negate());
                    }
                    apb.setGlAccount(Integer.toString(0));
                    if (!aphList.isEmpty()) {
                        apb.setAccountPayableHistories(aphList);
                        daoAccountPayableBatch.insert(apb);
                        aphList.forEach(e -> {
                            e.setAccountPayableBatch(apb);
                            daoAccountPayableHistory.update(e);
                        });
                        if (daoAccountPayableBatch.getErrorMessage() == null) {
                            AccountPayableBatchLayout layout = new AccountPayableBatchLayout(apb);
                            try {
                                JasperReportBuilder report = layout.build();
                                report.show(false);
                            } catch (DRException ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                            loadData();
                        }
                    }
                }
                break;
            case AppConstants.ACTION_ENTER_DISCOUNT:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fTableView.getSelectionModel().getSelectedItem().getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fEntity.setDiscountAmount(BigDecimal.ZERO);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fDiscountPane, ENTER_DISCOUNT);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            if (fEntity.getDiscountAmount().compareTo(fEntity.getTotalAmount().subtract(fEntity.getPaidAmount())) > 0) {
                                lblWarning.setText("Invalid Discount Amount!");
                                event.consume();
                            } else {
                                if (fEntity.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
                                    fEntity.setPaidAmount(fEntity.getTotalAmount().subtract(fEntity.getDiscountAmount()));
                                }
                                fTableView.refresh();
                                fTableView.requestFocus();
                                fTableView.getSelectionModel().select(fEntity);
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(AccountPayable_.discountAmount).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_ADJUST:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fTableView.getSelectionModel().getSelectedItem().getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fToPayPane, ADJUST_PAYMENT);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            if (fEntity.getPaidAmount().compareTo(BigDecimal.ZERO) == 0 || fEntity.getPaidAmount().compareTo(fEntity.getTotalAmount().subtract(zeroIfNull(fEntity.getDiscountAmount()))) > 0) {
                                lblWarning1.setText("Invalid Payment Amount!");
                                event.consume();
                            } else {
                                totalProperty.set(selectedItems.stream().map(e -> e.getPaidAmount()).reduce(BigDecimal.ZERO, BigDecimal::add));
                                fTableView.refresh();
                                fTableView.requestFocus();
                                fTableView.getSelectionModel().select(fEntity);
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(AccountPayable_.paidAmount).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
        }
    }

    protected Node createMainTable() {
        companyCol.setCellValueFactory((CellDataFeatures<List<AccountPayable>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getVendor() != null) {
                String vendor;
                if (p.getValue().get(0).getVendor().getCompany() != null) {
                    vendor = p.getValue().get(0).getVendor().getCompany();
                } else {
                    vendor = p.getValue().get(0).getVendor().getVendorContactName();
                }
                return new SimpleStringProperty(vendor);
            } else {
                return new SimpleStringProperty("");
            }
        });
        companyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyCol.setPrefWidth(250);

        TableColumn<List<AccountPayable>, String> accountCol = new TableColumn<>("Account");
        accountCol.setCellValueFactory((CellDataFeatures<List<AccountPayable>, String> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty() && p.getValue().get(0).getVendor() != null) {
                return new SimpleStringProperty(getString(p.getValue().get(0).getVendor().getAccountNumber()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        accountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        accountCol.setPrefWidth(100);

        TableColumn<List<AccountPayable>, BigDecimal> l130Col = new TableColumn<>("1-30");
        l130Col.setCellValueFactory((CellDataFeatures<List<AccountPayable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().after(f30))
                        .map(e -> {
                            if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                                return e.getTotalAmount();
                            } else {
                                return e.getTotalAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l130Col.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        l130Col.setPrefWidth(100);

        TableColumn<List<AccountPayable>, BigDecimal> l3160Col = new TableColumn<>("31-60");
        l3160Col.setCellValueFactory((CellDataFeatures<List<AccountPayable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().after(f60) && e.getDateDue().before(f30))
                        .map(e -> {
                            if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                                return e.getTotalAmount();
                            } else {
                                return e.getTotalAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l3160Col.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        l3160Col.setPrefWidth(100);

        TableColumn<List<AccountPayable>, BigDecimal> l6190Col = new TableColumn<>("61-90");
        l6190Col.setCellValueFactory((CellDataFeatures<List<AccountPayable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().after(f90) && e.getDateDue().before(f60))
                        .map(e -> {
                            if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                                return e.getTotalAmount();
                            } else {
                                return e.getTotalAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l6190Col.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        l6190Col.setPrefWidth(100);

        TableColumn<List<AccountPayable>, BigDecimal> l91Col = new TableColumn<>("90+");
        l91Col.setCellValueFactory((CellDataFeatures<List<AccountPayable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .filter(e -> e.getDateDue().before(f90))
                        .map(e -> {
                            if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                                return e.getTotalAmount();
                            } else {
                                return e.getTotalAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        l91Col.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        l91Col.setPrefWidth(100);

        TableColumn<List<AccountPayable>, BigDecimal> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((CellDataFeatures<List<AccountPayable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .map(e -> {
                            if (e.getAccountPayableType().equals(DBConstants.TYPE_APAR_INV)) {
                                return e.getTotalAmount();
                            } else {
                                return e.getTotalAmount().negate();
                            }
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        totalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        totalCol.setPrefWidth(100);

        paymentTotalCol.setCellValueFactory((CellDataFeatures<List<AccountPayable>, BigDecimal> p) -> {
            if (p.getValue() != null && !p.getValue().isEmpty()) {
                BigDecimal total = p.getValue()
                        .stream()
                        .map(e -> e.getPaidAmount())
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                return new ReadOnlyObjectWrapper(total);
            } else {
                return new ReadOnlyObjectWrapper(BigDecimal.ZERO);
            }
        });
        paymentTotalCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        paymentTotalCol.setPrefWidth(100);

        fSummaryTable.getColumns().add(companyCol);
        fSummaryTable.getColumns().add(accountCol);
        fSummaryTable.getColumns().add(l130Col);
        fSummaryTable.getColumns().add(l3160Col);
        fSummaryTable.getColumns().add(l6190Col);
        fSummaryTable.getColumns().add(l91Col);
        fSummaryTable.getColumns().add(totalCol);
        fSummaryTable.getColumns().add(paymentTotalCol);
        fSummaryTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fSummaryTable);
        fSummaryTable.setPrefHeight(300);
        return fSummaryTable;
    }

    protected Node createDetailTable() {
        selectedCol.setCellValueFactory((CellDataFeatures<AccountPayable, AccountPayable> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<AccountPayable, AccountPayable> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<AccountPayable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.vendorInvoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(205);

        TableColumn<AccountPayable, String> invoiceDateCol = new TableColumn<>("Date");
        invoiceDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.dateInvoiced.getName()));
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        invoiceDateCol.setPrefWidth(90);

        TableColumn<AccountPayable, String> dueDateCol = new TableColumn<>("Due Date");
        dueDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.dateDue.getName()));
        dueDateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dueDateCol.setPrefWidth(90);

        TableColumn<AccountPayable, Integer> agingCol = new TableColumn<>("Aging");
        agingCol.setCellValueFactory((CellDataFeatures<AccountPayable, Integer> p) -> {
            if (p.getValue().getDateDue() != null) {
                int diffInDays = (int) (((new Date()).getTime() - p.getValue().getDateDue().getTime()) / (1000 * 60 * 60 * 24));
                if (diffInDays < 0) {
                    diffInDays = 0;
                }
                return new ReadOnlyObjectWrapper(diffInDays);
            } else {
                return new ReadOnlyObjectWrapper(0);
            }
        });
        agingCol.setCellFactory(stringCell(Pos.CENTER));
        agingCol.setPrefWidth(60);

        TableColumn<AccountPayable, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory((CellDataFeatures<AccountPayable, String> p) -> {
            if (p.getValue().getVendor() != null && p.getValue().getVendor().getVendorTerm() != null && p.getValue().getVendor().getVendorTerm().getCode() != null) {
                return new SimpleStringProperty(p.getValue().getVendor().getVendorTerm().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        termCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        termCol.setPrefWidth(100);

        TableColumn<AccountPayable, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((CellDataFeatures<AccountPayable, String> p) -> {
            if (p.getValue().getAccountPayableType() != null
                    && p.getValue().getAccountPayableType() == DBConstants.TYPE_APAR_INV) {
                return new SimpleStringProperty("Invoice");
            } else if (p.getValue().getAccountPayableType() != null
                    && p.getValue().getAccountPayableType() == DBConstants.TYPE_APAR_CRE) {
                return new SimpleStringProperty("Credit");
            } else {
                return new SimpleStringProperty("");
            }
        });
        typeCol.setPrefWidth(80);
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));

        TableColumn<AccountPayable, String> balanceCol = new TableColumn<>("Total");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.totalAmount.getName()));
        balanceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        balanceCol.setPrefWidth(100);

        TableColumn<AccountPayable, String> discountCol = new TableColumn<>("Discount");
        discountCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.discountAmount.getName()));
        discountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        discountCol.setPrefWidth(100);

        TableColumn<AccountPayable, String> amountToPayCol = new TableColumn<>("Amount To Pay");
        amountToPayCol.setCellValueFactory(new PropertyValueFactory<>(AccountPayable_.paidAmount.getName()));
        amountToPayCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountToPayCol.setPrefWidth(100);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(dueDateCol);
        fTableView.getColumns().add(agingCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(balanceCol);
        fTableView.getColumns().add(discountCol);
        fTableView.getColumns().add(amountToPayCol);
        fTableView.setPrefHeight(150);
        setTableWidth(fTableView);
        return fTableView;
    }

    protected final Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        mainPane.add(createMainTable(), 0, 1);
        mainPane.add(createDetailTable(), 0, 2);
        mainPane.add(createTotalPane(), 0, 3);
        mainPane.add(createPayDiscountAdjustCloseButtonPane(), 0, 4);
        return mainPane;
    }

    protected final Node createReportView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(10));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        mainPane.add(createMainTable(), 0, 1);
        mainPane.add(createReportButtonPane(), 0, 4);
        return mainPane;
    }

    protected final GridPane createDiscountPane() {
        GridPane discountPane = new GridPane();
        discountPane.getStyleClass().add("editView");
        discountPane.setVgap(3);
        add(discountPane, "Discount Ammount: ", dataUI.createTextField(AccountPayable_.discountAmount, 90), fListener, 0);
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(40);
        discountPane.getRowConstraints().add(con);
        discountPane.add(lblWarning, 0, 2, 2, 1);
        return discountPane;
    }

    protected final GridPane createToPayPane() {
        GridPane toPayPane = new GridPane();
        toPayPane.getStyleClass().add("editView");
        toPayPane.setVgap(3);
        add(toPayPane, "To Pay Ammount: ", dataUI.createTextField(AccountPayable_.paidAmount, 90), fListener, 0);
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(40);
        toPayPane.getRowConstraints().add(con);
        toPayPane.add(lblWarning1, 0, 2, 2, 1);
        return toPayPane;
    }

    protected HBox createTotalPane() {
        HBox totalBox = new HBox();
        Label totalLbl = new Label("Total To Pay: ");
        TextField totalField = createLabelField(90.0, Pos.CENTER_RIGHT);
        totalField.textProperty().bindBidirectional(totalProperty, getDecimalFormat());
        totalBox.getChildren().addAll(totalLbl, totalField);
        totalBox.setAlignment(Pos.CENTER_RIGHT);
        return totalBox;
    }

    protected HBox createPayDiscountAdjustCloseButtonPane() {

        HBox leftButtonBox = new HBox();
        Button selectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_ALL, AppConstants.ACTION_SELECT_ALL, "Tag All", fHandler);
        Button unSelectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UN_SELECT_ALL, AppConstants.ACTION_UN_SELECT_ALL, "Untag All", fHandler);
        leftButtonBox.getChildren().addAll(selectAllButton, unSelectAllButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        Button discountButton = ButtonFactory.getButton(ButtonFactory.BUTTON_MOVE_DOWN, AppConstants.ACTION_ENTER_DISCOUNT, ENTER_DISCOUNT, fHandler);
        Button adjustButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_ADJUST, ADJUST_PAYMENT, fHandler);
        Button payButton = ButtonFactory.getButton(ButtonFactory.BUTTON_TENDER, AppConstants.ACTION_PROCESS, "Pay Selected", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        Button apReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_AP_REPORT, "AP Report", fHandler);
        Button apAgingReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_AP_AGING_REPORT, "AP Aging Report", fHandler);
        rightButtonBox.getChildren().addAll(apReportButton, apAgingReportButton, adjustButton, discountButton, payButton, closeButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    protected HBox createReportButtonPane() {
        HBox buttonGroup = new HBox();
        Button apReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_AP_REPORT, "AP Report", fHandler);
        Button apAgingReportButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, PRINT_AP_AGING_REPORT, "AP Aging Report", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, "Close", fHandler);
        buttonGroup.getChildren().addAll(apReportButton, apAgingReportButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            if (fInputDialog.getTitle().equalsIgnoreCase(ENTER_DISCOUNT)) {
                saveBtn.setDisable(dataUI.getTextField(AccountPayable_.discountAmount).getText().trim().isEmpty());
            }
            if (fInputDialog.getTitle().equalsIgnoreCase(ADJUST_PAYMENT)) {
                saveBtn.setDisable(dataUI.getTextField(AccountPayable_.paidAmount).getText().trim().isEmpty());
            }
        }
    }
}

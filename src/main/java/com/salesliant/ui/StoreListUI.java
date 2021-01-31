package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountPayable;
import com.salesliant.entity.AccountPayableBatch;
import com.salesliant.entity.AccountPayableBatch_;
import com.salesliant.entity.AccountPayableHistory;
import com.salesliant.entity.AccountPayableHistory_;
import com.salesliant.entity.AccountPayable_;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.entity.Appointment;
import com.salesliant.entity.Appointment_;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Batch_;
import com.salesliant.entity.Cheque;
import com.salesliant.entity.Cheque_;
import com.salesliant.entity.Commission;
import com.salesliant.entity.Commission_;
import com.salesliant.entity.Consignment;
import com.salesliant.entity.Consignment_;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Customer_;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Deposit_;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.DropPayout_;
import com.salesliant.entity.Employee;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.EmployeeGroup_;
import com.salesliant.entity.Employee_;
import com.salesliant.entity.GiftCertificate;
import com.salesliant.entity.GiftCertificateTransaction;
import com.salesliant.entity.GiftCertificateTransaction_;
import com.salesliant.entity.GiftCertificate_;
import com.salesliant.entity.Invoice;
import com.salesliant.entity.Invoice_;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLocation;
import com.salesliant.entity.ItemLocation_;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemLog_;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.Payment;
import com.salesliant.entity.PaymentBatch;
import com.salesliant.entity.PaymentBatch_;
import com.salesliant.entity.Payment_;
import com.salesliant.entity.Promotion;
import com.salesliant.entity.Promotion_;
import com.salesliant.entity.PurchaseOrder;
import com.salesliant.entity.PurchaseOrderHistory;
import com.salesliant.entity.PurchaseOrderHistory_;
import com.salesliant.entity.PurchaseOrder_;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction_;
import com.salesliant.entity.SalesOrder;
import com.salesliant.entity.SalesOrder_;
import com.salesliant.entity.Seq;
import com.salesliant.entity.Seq_;
import com.salesliant.entity.Service;
import com.salesliant.entity.Service_;
import com.salesliant.entity.Store;
import com.salesliant.entity.Store_;
import com.salesliant.entity.TimeCard;
import com.salesliant.entity.TimeCard_;
import com.salesliant.entity.TransferOrder;
import com.salesliant.entity.TransferOrderHistory;
import com.salesliant.entity.TransferOrderHistory_;
import com.salesliant.entity.TransferOrder_;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.Vendor_;
import com.salesliant.entity.VoidedTransaction;
import com.salesliant.entity.VoidedTransaction_;
import com.salesliant.entity.Voucher;
import com.salesliant.entity.Voucher_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.ProgressDialog;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StoreListUI extends BaseListUI<Store> {

    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final BaseDao<Cheque> daoCheque = new BaseDao<>(Cheque.class);
    private final BaseDao<Appointment> daoAppointment = new BaseDao<>(Appointment.class);
    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
    private final BaseDao<EmployeeGroup> daoEmployeeGroup = new BaseDao<>(EmployeeGroup.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);
    private final BaseDao<Invoice> daoInvoice = new BaseDao<>(Invoice.class);
    private final BaseDao<SalesOrder> daoSalesOrder = new BaseDao<>(SalesOrder.class);
    private final BaseDao<PurchaseOrder> daoPurchaseOrder = new BaseDao<>(PurchaseOrder.class);
    private final BaseDao<PurchaseOrderHistory> daoPurchaseOrderHistory = new BaseDao<>(PurchaseOrderHistory.class);
    private final BaseDao<TransferOrder> daoTransferOrder = new BaseDao<>(TransferOrder.class);
    private final BaseDao<TransferOrderHistory> daoTransferOrderHistory = new BaseDao<>(TransferOrderHistory.class);
    private final BaseDao<ReturnTransaction> daoReturnTransaction = new BaseDao<>(ReturnTransaction.class);
    private final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    private final BaseDao<AccountPayable> daoAccountPayable = new BaseDao<>(AccountPayable.class);
    private final BaseDao<AccountPayableBatch> daoAccountPayableBatch = new BaseDao<>(AccountPayableBatch.class);
    private final BaseDao<AccountPayableHistory> daoAccountPayableHistory = new BaseDao<>(AccountPayableHistory.class);
    private final BaseDao<Seq> daoSeq = new BaseDao<>(Seq.class);
    private final BaseDao<Commission> daoCommission = new BaseDao<>(Commission.class);
    private final BaseDao<Consignment> daoConsignment = new BaseDao<>(Consignment.class);
    private final BaseDao<Deposit> daoDeposit = new BaseDao<>(Deposit.class);
    private final BaseDao<DropPayout> daoDropPayout = new BaseDao<>(DropPayout.class);
    private final BaseDao<GiftCertificate> daoGiftCertificate = new BaseDao<>(GiftCertificate.class);
    private final BaseDao<GiftCertificateTransaction> daoGiftCertificateTransaction = new BaseDao<>(GiftCertificateTransaction.class);
    private final BaseDao<ItemLocation> daoItemLocation = new BaseDao<>(ItemLocation.class);
    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private final BaseDao<Payment> daoPayment = new BaseDao<>(Payment.class);
    private final BaseDao<PaymentBatch> daoPaymentBatch = new BaseDao<>(PaymentBatch.class);
    private final BaseDao<Promotion> daoPromotion = new BaseDao<>(Promotion.class);
    private final BaseDao<Service> daoService = new BaseDao<>(Service.class);
    private final BaseDao<TimeCard> daoTimeCard = new BaseDao<>(TimeCard.class);
    private final BaseDao<Vendor> daoVendor = new BaseDao<>(Vendor.class);
    private final BaseDao<VoidedTransaction> daoVoidedTransaction = new BaseDao<>(VoidedTransaction.class);
    private final BaseDao<Voucher> daoVoucher = new BaseDao<>(Voucher.class);
    private static final Logger LOGGER = Logger.getLogger(StoreListUI.class.getName());

    public StoreListUI() {
        mainView = createMainView();
        List<Store> list = daoStore.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        mainView.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.D) {
                handleAction(AppConstants.ACTION_DELETE);
            }
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Response answer = createConfirmResponseDialog("Are you sure to delete the seleted entry?");
                    if (answer.equals(Response.YES)) {
                        ProgressDialog pDialog = new ProgressDialog();
                        Task<Void> task = new Task<Void>() {
                            @Override
                            public Void call() throws Exception {
                                deleteItemQuantity(fEntity);
                                deleteOthers(fEntity);
                                return null;
                            }
                        };
                        task.setOnSucceeded(event -> {
                            pDialog.close();
                            if (Config.getStore() != null && Config.getStore().getId().equals(fEntity.getId())) {
                                Config.setStore(null);
                            }
                            daoStore.delete(fEntity);
                            fEntityList.remove(fEntity);
                            if (fEntityList.isEmpty()) {
                                fTableView.getSelectionModel().select(null);
                            }
                        });
                        pDialog.activateProgressBar(task);
                        Thread thread = new Thread(task);
                        thread.start();
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Stores");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Store, String> stationNumberCol = new TableColumn<>("Store Code");
        stationNumberCol.setCellValueFactory(new PropertyValueFactory<>(Store_.storeCode.getName()));
        stationNumberCol.setCellFactory(stringCell(Pos.TOP_CENTER));
        stationNumberCol.setPrefWidth(150);

        TableColumn<Store, String> storeNameCol = new TableColumn<>("Store Name");
        storeNameCol.setCellValueFactory(new PropertyValueFactory<>(Store_.storeName.getName()));
        storeNameCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        storeNameCol.setPrefWidth(150);

        TableColumn<Store, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory((TableColumn.CellDataFeatures<Store, String> p) -> {
            if (p.getValue() != null) {
                Store store = p.getValue();
                String address = "";
                if (!isEmpty(store.getAddress1())) {
                    address = address + store.getAddress1() + "\n";
                }
                if (!isEmpty(store.getAddress2())) {
                    address = address + store.getAddress2() + "\n";
                }
                address = address + (!isEmpty(store.getCity()) ? store.getCity() : "")
                        + (!isEmpty(store.getCity()) ? ", " : "")
                        + (!isEmpty(store.getState()) ? store.getState() : "")
                        + (!isEmpty(store.getState()) ? " " : "")
                        + (!isEmpty(store.getPostCode()) ? store.getPostCode() : "")
                        + "\n";
                if (!isEmpty(store.getCountry().getIsoCode3())) {
                    address = address + store.getCountry().getIsoCode3() + "\n";
                }
                if (!isEmpty(store.getPhoneNumber())) {
                    address = address + "Phone: " + store.getPhoneNumber() + "\n";
                }
                return new SimpleStringProperty(address);
            } else {
                return null;
            }
        });
        addressCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        addressCol.setPrefWidth(250);

        fTableView.getColumns().add(stationNumberCol);
        fTableView.getColumns().add(storeNameCol);
        fTableView.getColumns().add(addressCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createDeleteCloseButtonPane() {
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        deleteButton.setVisible(false);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private void deleteItemQuantity(Store store) {
        List<Item> list = daoItem.read().stream()
                .filter(e -> e.getItemQuantities() != null && !e.getItemQuantities().isEmpty())
                .collect(Collectors.toList());
        list.forEach(item -> {
            if (getItemQuantityByStore(item, store) != null) {
                ItemQuantity iq = getItemQuantityByStore(item, store);
                daoItemQuantity.delete(iq);
            }
        });
    }

    private void deleteOthers(Store store) {
        List<Employee> employees = daoEmployee.read(Employee_.store, store);
        employees.forEach(e -> daoEmployee.delete(e));
        List<EmployeeGroup> employeeGroups = daoEmployeeGroup.read(EmployeeGroup_.store, store);
        employeeGroups.forEach(e -> daoEmployeeGroup.delete(e));
        List<Customer> customers = daoCustomer.read(Customer_.store, store);
        customers.forEach(e -> daoCustomer.delete(e));
        List<Seq> seqs = daoSeq.read(Seq_.store, store);
        seqs.forEach(e -> daoSeq.delete(e));
        List<SalesOrder> salesOrders = daoSalesOrder.read(SalesOrder_.store, store);
        salesOrders.forEach(e -> daoSalesOrder.delete(e));
        List<Invoice> invoices = daoInvoice.read(Invoice_.store, store);
        invoices.forEach(e -> daoInvoice.delete(e));
        List<PurchaseOrder> purchaseOrders = daoPurchaseOrder.read(PurchaseOrder_.store, store);
        purchaseOrders.forEach(e -> daoPurchaseOrder.delete(e));
        List<PurchaseOrderHistory> purchaseOrderHistorys = daoPurchaseOrderHistory.read(PurchaseOrderHistory_.store, store);
        purchaseOrderHistorys.forEach(e -> daoPurchaseOrderHistory.delete(e));
        List<AccountReceivable> accountReceivables = daoAccountReceivable.read(AccountReceivable_.store, store);
        accountReceivables.forEach(e -> daoAccountReceivable.delete(e));
        List<AccountPayable> accountPayables = daoAccountPayable.read(AccountPayable_.store, store);
        accountPayables.forEach(e -> daoAccountPayable.delete(e));
        List<AccountPayableBatch> accountPayableBatchs = daoAccountPayableBatch.read(AccountPayableBatch_.store, store);
        accountPayableBatchs.forEach(e -> daoAccountPayableBatch.delete(e));
        List<AccountPayableHistory> accountPayableHistorys = daoAccountPayableHistory.read(AccountPayableHistory_.store, store);
        accountPayableHistorys.forEach(e -> daoAccountPayableHistory.delete(e));
        List<Batch> batchs = daoBatch.read(Batch_.store, store);
        batchs.forEach(e -> daoBatch.delete(e));
        List<TransferOrder> transferOrders = daoTransferOrder.read(TransferOrder_.store, store);
        transferOrders.forEach(e -> daoTransferOrder.delete(e));
        List<TransferOrderHistory> transferOrderHistorys = daoTransferOrderHistory.read(TransferOrderHistory_.store, store);
        transferOrderHistorys.forEach(e -> daoTransferOrderHistory.delete(e));
        List<ReturnTransaction> returnTransactions = daoReturnTransaction.read(ReturnTransaction_.store, store);
        returnTransactions.forEach(e -> daoReturnTransaction.delete(e));
        List<Appointment> appointments = daoAppointment.read(Appointment_.store, store);
        appointments.forEach(e -> daoAppointment.delete(e));
        List<Cheque> cheques = daoCheque.read(Cheque_.store, store);
        cheques.forEach(e -> daoCheque.delete(e));
        List<Commission> commissions = daoCommission.read(Commission_.store, store);
        commissions.forEach(e -> daoCommission.delete(e));
        List<Consignment> consignments = daoConsignment.read(Consignment_.store, store);
        consignments.forEach(e -> daoConsignment.delete(e));
        List<Deposit> deposits = daoDeposit.read(Deposit_.store, store);
        deposits.forEach(e -> daoDeposit.delete(e));
        List<DropPayout> dropPayouts = daoDropPayout.read(DropPayout_.store, store);
        dropPayouts.forEach(e -> daoDropPayout.delete(e));
        List<GiftCertificate> giftCertificates = daoGiftCertificate.read(GiftCertificate_.store, store);
        giftCertificates.forEach(e -> daoGiftCertificate.delete(e));
        List<GiftCertificateTransaction> giftCertificateTransactions = daoGiftCertificateTransaction.read(GiftCertificateTransaction_.store, store);
        giftCertificateTransactions.forEach(e -> daoGiftCertificateTransaction.delete(e));
        List<ItemLocation> itemLocations = daoItemLocation.read(ItemLocation_.store, store);
        itemLocations.forEach(e -> daoItemLocation.delete(e));
        List<ItemLog> itemLogs = daoItemLog.read(ItemLog_.store, store);
        itemLogs.forEach(e -> daoItemLog.delete(e));
        List<Payment> payments = daoPayment.read(Payment_.store, store);
        payments.forEach(e -> daoPayment.delete(e));
        List<PaymentBatch> paymentBatchs = daoPaymentBatch.read(PaymentBatch_.store, store);
        paymentBatchs.forEach(e -> daoPaymentBatch.delete(e));
        List<Promotion> promotions = daoPromotion.read(Promotion_.store, store);
        promotions.forEach(e -> daoPromotion.delete(e));
        List<Service> services = daoService.read(Service_.store, store);
        services.forEach(e -> daoService.delete(e));
        List<TimeCard> timeCards = daoTimeCard.read(TimeCard_.store, store);
        timeCards.forEach(e -> daoTimeCard.delete(e));
        List<Vendor> vendors = daoVendor.read(Vendor_.store, store);
        vendors.forEach(e -> daoVendor.delete(e));
        List<VoidedTransaction> voidedTransactions = daoVoidedTransaction.read(VoidedTransaction_.store, store);
        voidedTransactions.forEach(e -> daoVoidedTransaction.delete(e));
        List<Voucher> vouchers = daoVoucher.read(Voucher_.store, store);
        vouchers.forEach(e -> daoVoucher.delete(e));
    }
}

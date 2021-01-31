package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.Batch;
import com.salesliant.entity.Cheque;
import com.salesliant.entity.Cheque_;
import com.salesliant.entity.Customer;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

public class BadCheckListUI extends BaseListUI<Cheque> {

    private final BaseDao<Cheque> daoCheque = new BaseDao<>(Cheque.class);
    private final BaseDao<AccountReceivable> daoAccountReceivable = new BaseDao<>(AccountReceivable.class);
    protected final BaseDao<Batch> daoBatch = new BaseDao<>(Batch.class);
    private final DataUI dataUI = new DataUI(Cheque.class);
    private Customer fCustomer;
    private final GridPane fEditPane;
    protected final static String CUSTOMER_SELECTED = "Customer_Selected";
    private static final Logger LOGGER = Logger.getLogger(BadCheckListUI.class.getName());

    public BadCheckListUI() {
        mainView = createMainView();
        fEditPane = createEditPane();
        loadData();
    }

    private void loadData() {
        List<Cheque> list = daoCheque.readOrderBy(Cheque_.store, Config.getStore(), Cheque_.checkType, DBConstants.TYPE_CHECK_BAD, Cheque_.checkDate, AppConstants.ORDER_BY_DESC);
        fTableView.getItems().setAll(FXCollections.observableList(list));
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                CustomerListUI customerListUI = new CustomerListUI();
                fInputDialog = createSelectCancelUIDialog(customerListUI.getView(), "Customers");
                customerListUI.actionButtonBox.getChildren().remove(customerListUI.closeBtn);
                customerListUI.actionButtonBox.getChildren().remove(customerListUI.choiceBox);
                selectBtn.setDisable(true);
                ((TableView<Customer>) customerListUI.getTableView()).getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Customer> observable, Customer newValue, Customer oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (customerListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fCustomer = (Customer) customerListUI.getTableView().getSelectionModel().getSelectedItem();
                        fEntity = new Cheque();
                        fEntity.setCheckType(DBConstants.TYPE_CHECK_BAD);
                        fEntity.setStore(Config.getStore());
                        fEntity.setCheckDate(new Date());
                        fEntity.setCustomer(fCustomer);
                        fEntity.setCustomerName(fCustomer.getCompany());
                        fEntity.setCheckAmount(BigDecimal.ZERO);

                        handleAction(CUSTOMER_SELECTED);
                    }
                });
                fInputDialog.showDialog();
                break;
            case CUSTOMER_SELECTED:
                try {
                dataUI.setData(fEntity);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
            fInputDialog = createSaveCancelUIDialog(fEditPane, "Bad Check");
            saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                try {
                    dataUI.getData(fEntity);
                    daoCheque.insert(fEntity);
                    if (daoCheque.getErrorMessage() == null) {
                        fTableView.getItems().add(fEntity);
                        AccountReceivable art = new AccountReceivable();
                        Batch batch = Config.getBatch();
                        art.setBatch(batch);
                        art.setAccountReceivableNumber(Config.getNumber(DBConstants.SEQ_ACCOUNT_RECEIVABLE_NUMBER));
                        art.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                        art.setCustomer(fCustomer);
                        art.setTerms(fCustomer.getCustomerTerm().getCode());
                        art.setStore(Config.getStore());
                        art.setTransaction(art.getAccountReceivableNumber().toString());
                        art.setDateProcessed(new Date());
                        art.setDateDue(new Date());
                        art.setDateInvoiced(new Date());
                        art.setStatus(DBConstants.STATUS_OPEN);
                        art.setAccountReceivableType(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_CHARGE);
                        art.setNote("Bad Check");
                        art.setCollectable(Boolean.TRUE);
                        art.setDiscountAmount(BigDecimal.ZERO);
                        art.setPaidAmount(BigDecimal.ZERO);
                        art.setTotalAmount(fEntity.getCheckAmount());
                        art.setBalanceAmount(fEntity.getCheckAmount());
                        art.setPostedTag(Boolean.FALSE);
                        daoAccountReceivable.insert(art);
                        if (batch.getPaidOnAccount() != null) {
                            BigDecimal paidOnAccountAmount = batch.getPaidOnAccount();
                            paidOnAccountAmount = paidOnAccountAmount.add(fEntity.getCheckAmount());
                            batch.setPaidOnAccount(paidOnAccountAmount);
                        } else {
                            batch.setPaidOnAccount(fEntity.getCheckAmount());
                        }
                        daoBatch.update(batch);
                    } else {
                        lblWarning.setText(daoCheque.getErrorMessage());
                        event.consume();
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            });
            Platform.runLater(() -> dataUI.getTextField(Cheque_.customerName).requestFocus());
            fInputDialog.showDialog();
            break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoCheque.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Bad Check:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Cheque, String> checkNumberCol = new TableColumn<>("Check Number");
        checkNumberCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.checkNumber.getName()));
        checkNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        checkNumberCol.setPrefWidth(100);

        TableColumn<Cheque, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.customerName.getName()));
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(200);

        TableColumn<Cheque, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.checkDate.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCol.setPrefWidth(100);

        TableColumn<Cheque, String> checkDescriptionCol = new TableColumn<>("Description");
        checkDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.description.getName()));
        checkDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        checkDescriptionCol.setPrefWidth(100);

        TableColumn<Cheque, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.checkAmount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(100);

        fTableView.getColumns().add(dateCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(checkNumberCol);
        fTableView.getColumns().add(checkDescriptionCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefWidth(615);
        fTableView.setPrefHeight(350);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createAddDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createAddDeleteCloseButtonPane() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.setHgap(5);
        editPane.setVgap(5);

        add(editPane, "Customer:*", dataUI.createTextField(Cheque_.customerName), fListener, 250, 0);
        add(editPane, "Checker Number:*", dataUI.createTextField(Cheque_.checkNumber), fListener, 250, 1);
        add(editPane, "Driver License:*", dataUI.createTextField(Cheque_.license), fListener, 250, 2);
        add(editPane, "Description:*", dataUI.createTextField(Cheque_.description), fListener, 250, 3);
        add(editPane, "Amount:*", dataUI.createTextField(Cheque_.checkAmount), fListener, 250, 4);

        editPane.add(lblWarning, 0, 5, 2, 1);

        return editPane;
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemQuantity;
import com.salesliant.entity.ReasonCode;
import com.salesliant.entity.ReasonCode_;
import com.salesliant.entity.ReturnEntry;
import com.salesliant.entity.ReturnEntry_;
import com.salesliant.entity.ReturnTransaction;
import com.salesliant.entity.ReturnTransaction_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.widget.ReturnCodeWidget;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;

public class ReturnTransactionBaseListUI extends BaseListUI<ReturnTransaction> {

    protected final BaseDao<ReturnTransaction> daoReturnTransaction = new BaseDao<>(ReturnTransaction.class);
    private final BaseDao<ReturnEntry> daoReturnEntry = new BaseDao<>(ReturnEntry.class);
    private final BaseDao<ReasonCode> daoReasonCode = new BaseDao(ReasonCode.class);
    private final BaseDao<ItemQuantity> daoItemQuantity = new BaseDao<>(ItemQuantity.class);
    private final DataUI dataUI = new DataUI(ReturnTransaction.class);
    private final DataUI returnEntryUI = new DataUI(ReturnEntry.class);
    private final TableView<ReturnEntry> fReturnEntryTable = new TableView<>();
    private ObservableList<ReturnEntry> fReturnEntryList;
    private GridPane fEditPane;
    private ReturnCodeWidget fReturnCodeCombo;
    private List<ReasonCode> fReasonCodeList = new ArrayList<>();
    private final ComboBox fReasonCodeCombobox = new ComboBox();
    protected HBox fTopButtonBox = new HBox();
    protected HBox fLowerButtonBox = new HBox();
    protected Button fCloseButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
    protected Button fNewButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, NEW_RETURN_ENTRY, AppConstants.ACTION_ADD, fHandler);
    protected Button fEditButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, EDIT_RETURN_ENTRY, AppConstants.ACTION_EDIT, fHandler);
    protected Button fDeleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, DELETE_RETURN_ENTRY, AppConstants.ACTION_DELETE, fHandler);
    private ReturnEntry fReturnEntry;
    private final static String RETURN_TO_CUSTOMER = "Return To Customer";
    private final static String RETURN_TO_STOCK = "Return To Stock";
    private final static String RETURN_TO_OUT_OF_WARRANTY = "Return To Out of Warranty";
    private final static String NEW_RETURN_ENTRY = "New Return Entry";
    private final static String EDIT_RETURN_ENTRY = "Edit Return Entry";
    private final static String DELETE_RETURN_ENTRY = "Delete Return Entry";
    private final static String RETURN_ENTRY_EDIT_PANE_TITLE = "Return Entry";
    private static final Logger LOGGER = Logger.getLogger(ReturnTransactionBaseListUI.class.getName());

    public ReturnTransactionBaseListUI() {
    }

    protected final void createGUI() {
        fLowerButtonBox = createReturnEntryButtonPane();
        fTopButtonBox = createButtonPane();
        mainView = createMainView();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends ReturnTransaction> observable, ReturnTransaction newValue, ReturnTransaction oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<ReturnEntry> aList = fEntity.getReturnEntries()
                        .stream()
                        .sorted((e1, e2) -> e1.getDateEntered().compareTo(e2.getDateEntered()))
                        .collect(Collectors.toList());
                fReturnEntryList = FXCollections.observableList(aList);
                fReturnEntryTable.setItems(fReturnEntryList);
                fReturnEntryTable.refresh();
            } else {
                fEntity = null;
                fReturnEntryList.clear();
                fReturnEntryTable.refresh();
            }
        });
        fEditPane = createEditPane();
        fReasonCodeList = daoReasonCode.read(ReasonCode_.store, Config.getStore());
        List<String> reasonCodeList = fReasonCodeList
                .stream()
                .map(e -> e.getDescription())
                .collect(Collectors.toList());
        fReasonCodeCombobox.setItems(FXCollections.observableList(reasonCodeList));
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                List<String> choices = new ArrayList<>();
                choices.add("Store");
                choices.add("Customer");
                ChoiceDialog<String> dialog = new ChoiceDialog<>("Store", choices);
                dialog.setHeaderText("Who own the return item?");
                dialog.setContentText("This item belong to:");
                Optional<String> resultOwner = dialog.showAndWait();
                if (resultOwner.isPresent()) {
                    if (resultOwner.get().equalsIgnoreCase("Store")) {
                        fEntity = new ReturnTransaction();
                        fEntity.setStore(Config.getStore());
                        fEntity.setOwnTag(Boolean.TRUE);
                        handleAction(AppConstants.ACTION_ITEM_LIST_SHOW);
                    } else if (resultOwner.get().equalsIgnoreCase("Customer")) {
                        fEntity = new ReturnTransaction();
                        fEntity.setStore(Config.getStore());
                        fEntity.setOwnTag(Boolean.FALSE);
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
                                Customer selectedCustomer = (Customer) customerListUI.getTableView().getSelectionModel().getSelectedItem();
                                fEntity.setCustomer(selectedCustomer);
                                handleAction(AppConstants.ACTION_ITEM_LIST_SHOW);
                            }
                        });
                        fInputDialog.showDialog();
                    }
                }
                break;
            case AppConstants.ACTION_ITEM_LIST_SHOW:
                ItemListUI itemTableWidget = new ItemListUI();
                fInputDialog = createSelectCancelUIDialog(itemTableWidget.getView(), "Item");
                selectBtn.setDisable(true);
                ((TableView<Item>) itemTableWidget.getTableView()).getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemTableWidget.getTableView().getSelectionModel().getSelectedItem() != null) {
                        Item selectedItem = (Item) itemTableWidget.getTableView().getSelectionModel().getSelectedItem();
                        fEntity.setItem(selectedItem);
                        fEntity.setItemLookUpCode(selectedItem.getItemLookUpCode());
                        fEntity.setItemDescription(selectedItem.getDescription());
                        fEntity.setCost(selectedItem.getCost());
                        fEntity.setPrice(selectedItem.getPrice1());
                        handleAction(AppConstants.ACTION_OK_SELECTED);
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_OK_SELECTED:
                fEntity.setRmaNumberToCustomer(Config.getNumber(DBConstants.SEQ_RMA_NUMBER));
                fEntity.setDateReturned(new Timestamp(new Date().getTime()));
                fEntity.setEmployeeReturned(Config.getEmployee());
                fEntity.setEmployeeNameReturned(Config.getEmployee().getNameOnSalesOrder());
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Return");
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoReturnTransaction.insert(fEntity);
                        if (daoReturnTransaction.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoReturnTransaction.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Return");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoReturnTransaction.update(fEntity);
                            if (daoReturnTransaction.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoReturnTransaction.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoReturnTransaction.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case RETURN_TO_CUSTOMER:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("This process will close the return and send item to customer", (ActionEvent e) -> {
                        fEntity.setDateProcessed(new Timestamp(new Date().getTime()));
                        fEntity.setReturnToType(DBConstants.TYPE_RETURN_TO_CUSTOMER);
                        fEntity.setStatus(DBConstants.STATUS_CLOSE);
                        daoReturnTransaction.update(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case RETURN_TO_STOCK:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("This process will close the return and send item to inventry stock", (ActionEvent e) -> {
                        ReturnEntry re = new ReturnEntry();
                        re.setEmployee(Config.getEmployee());
                        re.setStore(Config.getStore());
                        re.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                        re.setDateEntered(new Timestamp(new Date().getTime()));
                        re.setRenturnTransaction(fEntity);
                        re.setNote("Receive to Inventory");
                        daoReturnEntry.insert(re);
                        fEntity.setDateProcessed(new Timestamp(new Date().getTime()));
                        fEntity.setReturnToType(DBConstants.TYPE_RETURN_TO_STOCK);
                        fEntity.setStatus(DBConstants.STATUS_CLOSE);
                        daoReturnTransaction.update(fEntity);
                        if (fEntity.getItem() != null && fEntity.getQuantity() != null && fEntity.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal qty = getQuantity(fEntity.getItem()).add(fEntity.getQuantity());
                            ItemQuantity iq = getItemQuantity(fEntity.getItem());
                            iq.setQuantity(qty);
                            daoItemQuantity.update(iq);
                        }
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case RETURN_TO_OUT_OF_WARRANTY:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("This process will close the return and dispose the item", (ActionEvent e) -> {
                        fEntity.setDateProcessed(new Timestamp(new Date().getTime()));
                        fEntity.setReturnToType(DBConstants.TYPE_RETURN_TO_CUSTOMER);
                        fEntity.setStatus(DBConstants.STATUS_CLOSE);
                        daoReturnTransaction.update(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case NEW_RETURN_ENTRY:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fReturnEntry = new ReturnEntry();
                    fReturnEntry.setEmployee(Config.getEmployee());
                    fReturnEntry.setEmployeeName(Config.getEmployee().getNameOnSalesOrder());
                    fReturnEntry.setRenturnTransaction(fEntity);
                    fReturnEntry.setDateEntered(new Timestamp(new Date().getTime()));
                    try {
                        returnEntryUI.setData(fReturnEntry);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(createReturnEntryEditPane(), RETURN_ENTRY_EDIT_PANE_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            returnEntryUI.getData(fReturnEntry);
                            fReturnEntry.setDateEntered(new Timestamp(new Date().getTime()));
                            daoReturnEntry.insert(fReturnEntry);
                            fReturnEntryList.add(fReturnEntry);
                            fEntity.getReturnEntries().add(fReturnEntry);
                            fReturnEntryTable.refresh();
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> returnEntryUI.getUIComponent(ReturnEntry_.returnCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case EDIT_RETURN_ENTRY:
                if (fReturnEntryTable.getSelectionModel().getSelectedItem() != null) {
                    fReturnEntry = fReturnEntryTable.getSelectionModel().getSelectedItem();
                    try {
                        returnEntryUI.setData(fReturnEntry);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(createReturnEntryEditPane(), RETURN_ENTRY_EDIT_PANE_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            returnEntryUI.getData(fReturnEntry);
                            fReturnEntry.setDateEntered(new Timestamp(new Date().getTime()));
                            daoReturnEntry.update(fReturnEntry);
                            fReturnEntryTable.refresh();
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> returnEntryUI.getUIComponent(ReturnEntry_.returnCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case DELETE_RETURN_ENTRY:
                if (fReturnEntryTable.getSelectionModel().getSelectedItem() != null) {
                    fReturnEntry = fReturnEntryTable.getSelectionModel().getSelectedItem();
                    fReturnEntryTable.getItems().remove(fReturnEntry);
                    daoReturnEntry.delete(fReturnEntry);
                    fEntity.getReturnEntries().remove(fReturnEntry);
                    if (fReturnEntryList.isEmpty()) {
                        fReturnEntryTable.getSelectionModel().select(null);
                    }
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(6));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        Label tableLbl = new Label("List of Return:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<ReturnTransaction, String> rmaNumberCol = new TableColumn<>("RMA");
        rmaNumberCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.rmaNumberToCustomer.getName()));
        rmaNumberCol.setCellFactory(stringCell(Pos.CENTER));
        rmaNumberCol.setPrefWidth(90);

        TableColumn<ReturnTransaction, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((CellDataFeatures<ReturnTransaction, String> p) -> {
            if (p.getValue() != null && p.getValue().getCustomer() != null) {
                String customer;
                if (p.getValue().getCustomer().getCompany() != null) {
                    customer = p.getValue().getCustomer().getCompany();
                } else {
                    customer = getString(p.getValue().getCustomer().getLastName()) + ","
                            + getString(p.getValue().getCustomer().getFirstName());
                }
                return new SimpleStringProperty(customer);
            } else {
                return new SimpleStringProperty("");
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(130);

        TableColumn<ReturnTransaction, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.itemLookUpCode.getName()));
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        skuCol.setPrefWidth(100);

        TableColumn<ReturnTransaction, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.itemDescription.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(140);

        TableColumn<ReturnTransaction, String> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.quantity.getName()));
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(60);

        TableColumn<ReturnTransaction, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.price.getName()));
        priceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        priceCol.setPrefWidth(70);

        TableColumn<ReturnTransaction, String> dateReturnedCol = new TableColumn<>("Date Returned");
        dateReturnedCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.dateReturned.getName()));
        dateReturnedCol.setCellFactory(stringCell(Pos.CENTER));
        dateReturnedCol.setPrefWidth(125);

        TableColumn<ReturnTransaction, String> reasonCodeCol = new TableColumn<>("Reason");
        reasonCodeCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.reasonCode.getName()));
        reasonCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        reasonCodeCol.setPrefWidth(125);

        TableColumn<ReturnTransaction, String> invoiceNumberCol = new TableColumn<>("Invoice");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(ReturnTransaction_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(90);

        TableColumn<ReturnTransaction, String> belongCol = new TableColumn<>("Belong To");
        belongCol.setCellValueFactory((CellDataFeatures<ReturnTransaction, String> p) -> {
            if (p.getValue() != null && p.getValue().getOwnTag() != null && p.getValue().getOwnTag()) {
                return new SimpleStringProperty("Store");
            } else {
                return new SimpleStringProperty("Customer");
            }
        });
        belongCol.setCellFactory(stringCell(Pos.CENTER));
        belongCol.setPrefWidth(68);

        fTableView.getColumns().add(rmaNumberCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(dateReturnedCol);
        fTableView.getColumns().add(reasonCodeCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(belongCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(300);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 1);
        mainPane.add(fTopButtonBox, 0, 2);
        mainPane.add(createReturnEntryPane(), 0, 3);
        return mainPane;
    }

    private HBox createButtonPane() {

        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        Button postToCustomerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST, RETURN_TO_CUSTOMER, "Post To Customer", fHandler);
        Button postToStockButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST, RETURN_TO_STOCK, "Post To Stock", fHandler);
        Button postToOutOfWarrantyButton = ButtonFactory.getButton(ButtonFactory.BUTTON_POST, RETURN_TO_OUT_OF_WARRANTY, "Post To OOW", fHandler);
        HBox buttonGroup = new HBox(6);
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, postToCustomerButton, postToStockButton, postToOutOfWarrantyButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        fReasonCodeCombobox.setPrefWidth(160);
        dataUI.setUIComponent(ReturnTransaction_.reasonCode, fReasonCodeCombobox);
        add(editPane, "Item SKU:*", dataUI.createTextField(ReturnTransaction_.itemLookUpCode), fListener, 150.0, 0);
        add(editPane, "Item Description:*", dataUI.createTextField(ReturnTransaction_.itemDescription), fListener, 150.0, 1);
        RowConstraints con = new RowConstraints();
        con.setPrefHeight(40);
        editPane.getRowConstraints().add(con);
        editPane.add(addHLine(1), 0, 2, 2, 1);
        add(editPane, "Return Reason:*", fReasonCodeCombobox, fListener, 3);
        add(editPane, "Quantity:*", dataUI.createTextField(ReturnTransaction_.quantity), fListener, 150.0, 4);
        add(editPane, "Invoice Number:*", dataUI.createTextField(ReturnTransaction_.invoiceNumber), fListener, 150.0, 5);
        add(editPane, "Item Belong To Store:", dataUI.createCheckBox(ReturnTransaction_.ownTag), fListener, 6);
        editPane.add(lblWarning, 0, 1, 2, 8);

        dataUI.getTextField(ReturnTransaction_.itemLookUpCode).setEditable(false);
        dataUI.getTextField(ReturnTransaction_.itemDescription).setEditable(false);

        return editPane;
    }

    private GridPane createReturnEntryPane() {
        GridPane returnEntryPane = new GridPane();
        returnEntryPane.getStyleClass().add("insideView");

        TableColumn<ReturnEntry, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(ReturnEntry_.dateEntered.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER));
        dateCol.setPrefWidth(130);

        TableColumn<ReturnEntry, String> returnCodeCol = new TableColumn<>("Action");
        returnCodeCol.setCellValueFactory((TableColumn.CellDataFeatures<ReturnEntry, String> p) -> {
            if (p.getValue().getReturnCode() != null) {
                return new SimpleStringProperty(p.getValue().getReturnCode().getDescription());
            } else {
                return null;
            }
        });
        returnCodeCol.setCellFactory(stringCell(Pos.CENTER));
        returnCodeCol.setPrefWidth(210);

        TableColumn<ReturnEntry, String> trackingNumberCol = new TableColumn<>("Tracking");
        trackingNumberCol.setCellValueFactory(new PropertyValueFactory<>(ReturnEntry_.trackingNumber.getName()));
        trackingNumberCol.setCellFactory(stringCell(Pos.CENTER));
        trackingNumberCol.setPrefWidth(150);

        TableColumn<ReturnEntry, String> employeeCol = new TableColumn<>("Tech");
        employeeCol.setCellValueFactory((TableColumn.CellDataFeatures<ReturnEntry, String> p) -> {
            if (p.getValue().getEmployee() != null) {
                return new SimpleStringProperty(p.getValue().getEmployee().getNameOnSalesOrder());
            } else {
                return null;
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.CENTER));
        employeeCol.setPrefWidth(120);

        TableColumn<ReturnEntry, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(ReturnEntry_.note.getName()));
        noteCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        noteCol.setPrefWidth(380);

        fReturnEntryTable.getColumns().add(dateCol);
        fReturnEntryTable.getColumns().add(returnCodeCol);
        fReturnEntryTable.getColumns().add(trackingNumberCol);
        fReturnEntryTable.getColumns().add(employeeCol);
        fReturnEntryTable.getColumns().add(noteCol);
        fReturnEntryTable.setPrefHeight(130);
        setTableWidth(fReturnEntryTable);
        fReturnEntryTable.setEditable(false);

        returnEntryPane.add(fReturnEntryTable, 0, 2);
        returnEntryPane.add(fLowerButtonBox, 0, 3);

        return returnEntryPane;

    }

    private HBox createReturnEntryButtonPane() {
        HBox buttonGroup = new HBox(6);
        buttonGroup.getChildren().addAll(fNewButton, fEditButton, fDeleteButton, fCloseButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        return buttonGroup;
    }

    private GridPane createReturnEntryEditPane() {
        GridPane returnEntryEditPane = new GridPane();
        returnEntryEditPane.getStyleClass().add("editView");
        fReturnCodeCombo = new ReturnCodeWidget();
        fReturnCodeCombo.setPrefWidth(350);
        returnEntryUI.setUIComponent(ReturnEntry_.returnCode, fReturnCodeCombo);
        add(returnEntryEditPane, "Return Code:", fReturnCodeCombo, fListener, 0);
        add(returnEntryEditPane, "Tracking Number:", returnEntryUI.createTextField(ReturnEntry_.trackingNumber), fListener, 1);
        add(returnEntryEditPane, "Note:", returnEntryUI.createTextArea(ReturnEntry_.note), 250, 350, fListener, 2);
        returnEntryEditPane.add(lblWarning, 0, 3, 2, 1);
        return returnEntryEditPane;
    }

}

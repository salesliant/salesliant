package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.PostCode;
import com.salesliant.entity.PostCode_;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorContact;
import com.salesliant.entity.VendorContact_;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.entity.VendorNote;
import com.salesliant.entity.VendorNote_;
import com.salesliant.entity.VendorShipTo;
import com.salesliant.entity.VendorShipTo_;
import com.salesliant.entity.Vendor_;
import com.salesliant.util.AddressFactory;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import com.salesliant.widget.CountryWidget;
import com.salesliant.widget.VendorShippingWidget;
import com.salesliant.widget.VendorTermWidget;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class VendorListUI extends BaseListUI<Vendor> {

    private final BaseDao<Vendor> daoVendor = new BaseDao<>(Vendor.class);
    private final BaseDao<VendorShipTo> daoVendorShipTo = new BaseDao<>(VendorShipTo.class);
    private final BaseDao<VendorContact> daoVendorContact = new BaseDao<>(VendorContact.class);
    private final BaseDao<VendorNote> daoVendorNote = new BaseDao<>(VendorNote.class);
    private final BaseDao<VendorItem> daoVendorItem = new BaseDao<>(VendorItem.class);
    private final BaseDao<PostCode> daoPostCode = new BaseDao<>(PostCode.class);
    private final DataUI vendorUI = new DataUI(Vendor.class);
    private final DataUI shipToUI = new DataUI(VendorShipTo.class);
    private final DataUI contactUI = new DataUI(VendorContact.class);
    private final DataUI noteUI = new DataUI(VendorNote.class);
    private final DataUI vendorItemUI = new DataUI(VendorItem.class);
    private final TableView<VendorContact> fVendorContactTable = new TableView<>();
    private final TableView<VendorItem> fVendorItemTable = new TableView<>();
    private final TableView<VendorNote> fVendorNoteTable = new TableView<>();
    private final TableView<VendorShipTo> fVendorShipToTable = new TableView<>();
    private final TextField fItemField = new TextField();
    private final CountryWidget fVendorCountryCombo = new CountryWidget();
    private final CountryWidget fVendorShipToCountryCombo = new CountryWidget();
    private VendorShipTo fVendorShipTo;
    private VendorNote fVendorNote;
    private VendorContact fVendorContact;
    private VendorItem fVendorItem;
    private final VendorShippingWidget fVendorShippingService = new VendorShippingWidget();
    private final VendorTermWidget fTermCombo = new VendorTermWidget();
    private ObservableList<VendorContact> fVendorContactList;
    private ObservableList<VendorNote> fVendorNoteList;
    private ObservableList<VendorShipTo> fVendorShipToList;
    private ObservableList<VendorItem> fVendorItemList;
    private final GridPane fEditPane;
    private final GridPane fShipToEditPane;
    private final GridPane fVendorContactEditPane;
    private final GridPane fNoteEditPane;
    private final GridPane fVendorItemEditPane;
    private final Label shipToWarning = new Label("");
    private final Label contactWarning = new Label("");
    private final Label noteWarning = new Label("");
    private final Label vendorItemWarning = new Label("");
    private final TabPane fTabPane = new TabPane();
    private final TextArea fAddressArea = new TextArea(), fInfo = new TextArea(), fAccount = new TextArea();
    private SearchField searchField;
    private final static String VENDOR_TITLE = "Vendor";
    private final static String VENDOR_SHIP_TO_TITLE = "Vendor Ship To";
    private final static String VENDOR_SHIP_TO_ADD = "VendorShipTo_Add";
    private final static String VENDOR_SHIP_TO_EDIT = "VendorShipTo_Edit";
    private final static String VENDOR_SHIP_TO_DELETE = "VendorShipTo_Delete";
    private final static String CONTACT_ADD = "VendorContact_Add";
    private final static String CONTACT_EDIT = "VendorContact_Edit";
    private final static String CONTACT_DELETE = "VendorContact_Delete";
    private final static String CONTACT_TITLE = "Vendor Contact";
    private final static String VENDOR_NOTE_ADD = "VendorNote_Add";
    private final static String VENDOR_NOTE_EDIT = "VendorNote_Edit";
    private final static String VENDOR_NOTE_DELETE = "VendorNote_Delete";
    private final static String VENDOR_NOTE_TITLE = "Note";
    private final static String VENDOR_ITEM_ADD = "VendorItem_Add";
    private final static String VENDOR_ITEM_EDIT = "VendorItem_Edit";
    private final static String VENDOR_ITEM_DELETE = "VendorItem_Delete";
    private final static String VENDOR_ITEM_TITLE = "Vendor Item";
    private static final Logger LOGGER = Logger.getLogger(VendorListUI.class.getName());

    public VendorListUI() {
        loadData();
        mainView = createMainView();
        fEditPane = createEditPane();
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Vendor> observable, Vendor newValue, Vendor oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        updateInfoPane(fEntity);
                        List<VendorContact> contactList = fEntity.getVendorContacts();
                        fVendorContactList = FXCollections.observableList(contactList);
                        fVendorContactTable.setItems(fVendorContactList);
                        List<VendorShipTo> shipToList = fEntity.getVendorShipTos();
                        fVendorShipToList = FXCollections.observableList(shipToList);
                        fVendorShipToTable.setItems(fVendorShipToList);
                        List<VendorNote> noteList = fEntity.getVendorNotes();
                        fVendorNoteList = FXCollections.observableList(noteList);
                        fVendorNoteTable.setItems(fVendorNoteList);
                        List<VendorItem> vendorItemList = fEntity.getVendorItems();
                        fVendorItemList = FXCollections.observableList(vendorItemList);
                        fVendorItemTable.setItems(fVendorItemList);
                    } else {
                        fVendorContactList.clear();
                        fVendorShipToList.clear();
                        fVendorNoteList.clear();
                        fVendorItemList.clear();
                        updateInfoPane(null);
                    }
                    fItemField.setEditable(false);
                });

        fTabPane.getSelectionModel().selectFirst();
        fShipToEditPane = createShipToEditPane();
        fVendorContactEditPane = createVendorContactEditPane();
        fNoteEditPane = createNoteEditPane();
        fVendorItemEditPane = createVendorItemEditPane();
        dialogTitle = VENDOR_TITLE;
//        Platform.runLater(() -> searchField.requestFocus());
    }

    private void loadData() {
        List<Vendor> list = daoVendor.read(Vendor_.store, Config.getStore(), Vendor_.activeTag, true).stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "")
                            + (!isEmpty(e1.getVendorContactName()) ? e1.getVendorContactName() : "");
                    String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "")
                            + (!isEmpty(e2.getVendorContactName()) ? e2.getVendorContactName() : "");
                    return name1.compareToIgnoreCase(name2);
                })
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Vendor();
                fEntity.setActiveTag(Boolean.TRUE);
                fEntity.setStore(Config.getStore());
                fEntity.setDateCreated(new Date());
                fEntity.setCountry(Config.getStore().getCountry());
                fVendorShippingService.getItems().clear();
                fVendorShippingService.setVendor(fEntity);
                fVendorShippingService.setValue(fEntity.getDefaultVendorShippingService());
                try {
                    vendorUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, VENDOR_TITLE);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        vendorUI.getData(fEntity);
                        daoVendor.insert(fEntity);
                        if (daoVendor.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.scrollTo(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoVendor.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> vendorUI.getTextField(Vendor_.accountNumber).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fVendorShippingService.getItems().clear();
                    fVendorShippingService.setVendor(fEntity);
                    fVendorShippingService.setValue(fEntity.getDefaultVendorShippingService());
                    try {
                        vendorUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, VENDOR_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            vendorUI.getData(fEntity);
                            fEntity.setLastUpdated(new Date());
                            fEntity.setDefaultVendorShippingService(fVendorShippingService.getValue());
                            daoVendor.update(fEntity);
                            if (daoVendor.getErrorMessage() == null) {
                                loadData();
                            } else {
                                lblWarning.setText(daoVendor.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> vendorUI.getTextField(Vendor_.accountNumber).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendor.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case CONTACT_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fVendorContact = new VendorContact();
                    fVendorContact.setVendor(fTableView.getSelectionModel().getSelectedItem());
                    try {
                        contactUI.setData(fVendorContact);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fVendorContactEditPane, CONTACT_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            contactUI.getData(fVendorContact);
                            daoVendorContact.insert(fVendorContact);
                            if (daoVendorContact.getErrorMessage() == null) {
                                fVendorContactList.add(fVendorContact);
                            } else {
                                contactWarning.setText(daoVendorContact.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> contactUI.getTextField(VendorContact_.contactName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case CONTACT_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorContactTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorContact = fVendorContactTable.getSelectionModel().getSelectedItem();
                    try {
                        contactUI.setData(fVendorContact);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fVendorContactEditPane, CONTACT_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            contactUI.getData(fVendorContact);
                            daoVendorContact.update(fVendorContact);
                            if (daoVendorContact.getErrorMessage() == null) {
                                fVendorContactTable.refresh();
                            } else {
                                contactWarning.setText(daoVendorContact.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> contactUI.getTextField(VendorContact_.contactName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case CONTACT_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorContactTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorContact = (VendorContact) fVendorContactTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendorContact.delete(fVendorContact);
                        fVendorContactList.remove(fVendorContact);
                    });
                }
                break;
            case VENDOR_SHIP_TO_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fVendorShipTo = new VendorShipTo();
                    fVendorShipTo.setCountry(fTableView.getSelectionModel().getSelectedItem().getCountry());
                    fVendorShipTo.setVendor(fTableView.getSelectionModel().getSelectedItem());
                    try {
                        shipToUI.setData(fVendorShipTo);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fShipToEditPane, VENDOR_SHIP_TO_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            shipToUI.getData(fVendorShipTo);
                            daoVendorShipTo.insert(fVendorShipTo);
                            if (daoVendorShipTo.getErrorMessage() == null) {
                                fVendorShipToList.add(fVendorShipTo);
                            } else {
                                shipToWarning.setText(daoVendorShipTo.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> shipToUI.getTextField(Vendor_.company).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case VENDOR_SHIP_TO_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorShipToTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorShipTo = fVendorShipToTable.getSelectionModel().getSelectedItem();
                    try {
                        shipToUI.setData(fVendorShipTo);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fShipToEditPane, VENDOR_SHIP_TO_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            shipToUI.getData(fVendorShipTo);
                            daoVendorShipTo.update(fVendorShipTo);
                            if (daoVendorShipTo.getErrorMessage() == null) {
                                fVendorShipToTable.refresh();
                            } else {
                                shipToWarning.setText(daoVendorShipTo.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> shipToUI.getTextField(VendorShipTo_.firstName).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case VENDOR_SHIP_TO_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorShipToTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorShipTo = (VendorShipTo) fVendorShipToTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendorShipTo.delete(fVendorShipTo);
                        fVendorShipToList.remove(fVendorShipTo);
                    });
                }
                break;
            case VENDOR_NOTE_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fVendorNote = new VendorNote();
                    fVendorNote.setVendor(fTableView.getSelectionModel().getSelectedItem());
                    try {
                        noteUI.setData(fVendorNote);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNoteEditPane, VENDOR_NOTE_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            noteUI.getData(fVendorNote);
                            fVendorNote.setDateCreated(new Date());
                            daoVendorNote.insert(fVendorNote);
                            if (daoVendorNote.getErrorMessage() == null) {
                                fVendorNoteList.add(fVendorNote);
                            } else {
                                noteWarning.setText(daoVendorNote.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> noteUI.getTextField(VendorNote_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case VENDOR_NOTE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorNoteTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorNote = fVendorNoteTable.getSelectionModel().getSelectedItem();
                    try {
                        noteUI.setData(fVendorNote);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNoteEditPane, VENDOR_NOTE_TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            noteUI.getData(fVendorNote);
                            fVendorNote.setLastUpdated(new Date());
                            daoVendorNote.update(fVendorNote);
                            if (daoVendorNote.getErrorMessage() == null) {
                                fVendorNoteTable.refresh();
                            } else {
                                noteWarning.setText(daoVendorNote.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> noteUI.getTextField(VendorNote_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case VENDOR_NOTE_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorNoteTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorNote = (VendorNote) fVendorNoteTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendorNote.delete(fVendorNote);
                        fVendorNoteList.remove(fVendorNote);
                    });
                }
                break;
            case VENDOR_ITEM_ADD:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    fVendorItem = new VendorItem();
                    fVendorItem.setVendor(fEntity);
                    fItemField.setText("");
                    try {
                        vendorItemUI.setData(fVendorItem);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fVendorItemEditPane, VENDOR_ITEM_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            vendorItemUI.getData(fVendorItem);
                            daoVendorItem.insert(fVendorItem);
                            if (daoVendorItem.getErrorMessage() == null) {
                                fVendorItemList.add(fVendorItem);
                            } else {
                                vendorItemWarning.setText(daoVendorItem.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> vendorItemUI.getTextField(VendorItem_.vendorItemLookUpCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case VENDOR_ITEM_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorItemTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorItem = fVendorItemTable.getSelectionModel().getSelectedItem();
                    fItemField.setText(fVendorItem.getItem().getItemLookUpCode());
                    try {
                        vendorItemUI.setData(fVendorItem);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fVendorItemEditPane, VENDOR_ITEM_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            vendorItemUI.getData(fVendorItem);
                            daoVendorItem.update(fVendorItem);
                            if (daoVendorItem.getErrorMessage() == null) {
                                fVendorItemTable.refresh();
                            } else {
                                vendorItemWarning.setText(daoVendorItem.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> vendorItemUI.getTextField(VendorItem_.vendorItemLookUpCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case VENDOR_ITEM_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null
                        && fVendorItemTable.getSelectionModel().getSelectedItem() != null) {
                    fVendorItem = (VendorItem) fVendorItemTable.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoVendorItem.delete(fVendorItem);
                        fVendorItemList.remove(fVendorItem);
                    });
                }
                break;
            case AppConstants.ACTION_SELECT:
                ItemListUI itemListUI = new ItemListUI();
                itemListUI.actionButtonBox.getChildren().remove(itemListUI.closeBtn);
                fInputDialog = createSelectCancelUIDialog(itemListUI.getView(), "Item");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemListUI.getTableView().getSelectionModel().getSelectedItem() != null) {
                        fVendorItem.setItem((Item) itemListUI.getTableView().getSelectionModel().getSelectedItem());
                        fItemField.setText(((Item) itemListUI.getTableView().getSelectionModel().getSelectedItem())
                                .getItemLookUpCode());
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.showDialog();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Vendor, String> accountNumberCol = new TableColumn<>("Account");
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>(Vendor_.accountNumber.getName()));
        accountNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountNumberCol.setPrefWidth(90);

        TableColumn<Vendor, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((CellDataFeatures<Vendor, String> p) -> {
            if (p.getValue().getCompany() != null) {
                return new SimpleStringProperty(p.getValue().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        companyCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        companyCol.setPrefWidth(220);

        TableColumn<Vendor, String> contactrCol = new TableColumn<>("Contact");
        contactrCol.setCellValueFactory(new PropertyValueFactory<>(Vendor_.vendorContactName.getName()));
        contactrCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        contactrCol.setPrefWidth(150);

        TableColumn<Vendor, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Vendor_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneNumberCol.setPrefWidth(150);

        TableColumn<Vendor, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory((CellDataFeatures<Vendor, String> p) -> {
            if (p.getValue().getVendorTerm() != null && p.getValue().getVendorTerm().getCode() != null) {
                return new SimpleStringProperty(p.getValue().getVendorTerm().getCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        termCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        termCol.setPrefWidth(100);

        TableColumn<Vendor, String> creditLimitCol = new TableColumn<>("Credit Limit");
        creditLimitCol.setCellValueFactory(new PropertyValueFactory<>(Vendor_.creditLimit.getName()));
        creditLimitCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        creditLimitCol.setPrefWidth(120);

        fTableView.getColumns().add(companyCol);
        fTableView.getColumns().add(accountNumberCol);
        fTableView.getColumns().add(contactrCol);
        fTableView.getColumns().add(phoneNumberCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(creditLimitCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2, 2, 1);
        mainPane.add(createButtonPane(), 1, 4);
        mainPane.add(createInfoPane(), 0, 3, 2, 1);
        return mainPane;
    }

    protected HBox createButtonPane() {
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Active", "Inactive"));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue.equals("Active")) {
                List<Vendor> list = daoVendor.read(Vendor_.store, Config.getStore(), Vendor_.activeTag, true).stream()
                        .sorted((e1, e2) -> {
                            String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "")
                                    + (!isEmpty(e1.getVendorContactName()) ? e1.getVendorContactName() : "");
                            String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "")
                                    + (!isEmpty(e2.getVendorContactName()) ? e2.getVendorContactName() : "");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());
                fEntityList = FXCollections.observableList(list);
            } else {
                List<Vendor> list = daoVendor.read(Vendor_.store, Config.getStore(), Vendor_.activeTag, false).stream()
                        .sorted((e1, e2) -> {
                            String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "")
                                    + (!isEmpty(e1.getVendorContactName()) ? e1.getVendorContactName() : "");
                            String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "")
                                    + (!isEmpty(e2.getVendorContactName()) ? e2.getVendorContactName() : "");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());
                fEntityList = FXCollections.observableList(list);
            }
            fTableView.setItems(fEntityList);
        });
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(choiceBox, newButton, editButton, deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private GridPane createEditPane() {
        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");

        vendorUI.setUIComponent(Vendor_.country, fVendorCountryCombo);
        add(leftPane, "Account Number: ", vendorUI.createTextField(Vendor_.accountNumber, 180), fListener, 0);
        add(leftPane, "Vendor Code: ", vendorUI.createTextField(Vendor_.vendorCode, 180), fListener, 1);
        add(leftPane, "Company: ", vendorUI.createTextField(Vendor_.company, 180), fListener, 2);
        add(leftPane, "Contact: ", vendorUI.createTextField(Vendor_.vendorContactName, 180), fListener, 3);
        add(leftPane, "Post Code: ", vendorUI.createTextField(Vendor_.postCode), fListener, 4);
        add(leftPane, "Address 1: ", vendorUI.createTextField(Vendor_.address1, 180), fListener, 5);
        add(leftPane, "Address 2: ", vendorUI.createTextField(Vendor_.address2, 180), fListener, 6);
        add(leftPane, "City: ", vendorUI.createTextField(Vendor_.city, 180), fListener, 7);
        add(leftPane, "State:", vendorUI.createTextField(Vendor_.state, 180), fListener, 8);
        add(leftPane, "Country: ", fVendorCountryCombo, fListener, 9);
        add(leftPane, "Phone Number: ", vendorUI.createTextField(Vendor_.phoneNumber), fListener, 10);
        add(leftPane, "Fax Number: ", vendorUI.createTextField(Vendor_.faxNumber), fListener, 11);

        vendorUI.getTextField(Vendor_.phoneNumber).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    String txt = node.getText().replaceFirst("^1+(?!$)", "");
                    if (txt.length() == 10) {
                        String phone = txt.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                        node.setText(phone);
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        vendorUI.getTextField(Vendor_.postCode).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    PostCode postCode = daoPostCode.find(PostCode_.postCode, node.getText().trim());
                    if (postCode != null) {
                        vendorUI.getTextField(Vendor_.city).setText(postCode.getCity());
                        vendorUI.getTextField(Vendor_.state).setText(postCode.getState());
                        fVendorCountryCombo.setSelectedCountry(postCode.getCountry());
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });

        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");

        fTermCombo.setPrefWidth(160);
        vendorUI.setUIComponent(Vendor_.vendorTerm, fTermCombo);
        fVendorShippingService.setPrefWidth(160);

        add(rightPane, "Vendor Default Shipping Service: ", fVendorShippingService, fListener, 0);
        add(rightPane, "Vendor Terms: ", fTermCombo, fListener, 1);
        add(rightPane, "Return Days: ", vendorUI.createTextField(Vendor_.returnDays), fListener, 2);
        add(rightPane, "Discount Days: ", vendorUI.createTextField(Vendor_.discountDays), fListener, 3);
        add(rightPane, "Discount Rate %: ", vendorUI.createTextField(Vendor_.discountRate), fListener, 4);
        add(rightPane, "Credit Limit: ", vendorUI.createTextField(Vendor_.creditLimit), fListener, 5);
        add(rightPane, "Tax ID: ", vendorUI.createTextField(Vendor_.taxNumber, 80), fListener, 6);
        add(rightPane, "Web Address: ", vendorUI.createTextField(Vendor_.webAddress, 180), fListener, 7);
        add(rightPane, vendorUI.createCheckBox(Vendor_.useVendorSku), "Use Vendor SKU?", fListener, 8);
        add(rightPane, vendorUI.createCheckBox(Vendor_.useAutoSku), "Use Auto SKU?", fListener, 9);
        add(rightPane, vendorUI.createCheckBox(Vendor_.activeTag), "Active?", fListener, 10);

        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(((CheckBox) vendorUI.getUIComponent(Vendor_.useVendorSku)).selectedProperty());
            }

            @Override
            protected boolean computeValue() {
                if (((CheckBox) vendorUI.getUIComponent(Vendor_.useVendorSku)).isSelected()) {
                    ((CheckBox) vendorUI.getUIComponent(Vendor_.useAutoSku)).setSelected(false);
                    return true;
                } else {
                    return false;
                }
            }
        };
        ((CheckBox) vendorUI.getUIComponent(Vendor_.useAutoSku)).disableProperty().bind(bb);

        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        editPane.setVgap(3);
        editPane.add(leftPane, 0, 0);
        editPane.add(rightPane, 1, 0);
        editPane.add(lblWarning, 0, 1, 2, 1);
        editPane.add(addHLine(1), 0, 2, 2, 1);

        return editPane;
    }

    private Node createInfoPane() {
        Tab contactTab = new Tab(" Contacts ");
        contactTab.setContent(createVendorContactPane());
        contactTab.setClosable(false);
        fTabPane.getTabs().add(contactTab);

        Tab accountTab = new Tab(" Info ");
        accountTab.setContent(createDetailPane());
        accountTab.setClosable(false);
        fTabPane.getTabs().add(accountTab);

        Tab shipToTab = new Tab(" Ship To ");
        shipToTab.setContent(createShipToPane());
        shipToTab.setClosable(false);
        fTabPane.getTabs().add(shipToTab);

        Tab noteTab = new Tab(" Notes ");
        noteTab.setContent(createNotePane());
        noteTab.setClosable(false);
        fTabPane.getTabs().add(noteTab);

        Tab itemTab = new Tab(" Vendor Items ");
        itemTab.setContent(createVendorItemPane());
        itemTab.setClosable(false);
        fTabPane.getTabs().add(itemTab);

        fTabPane.setPrefHeight(200);

        return fTabPane;
    }

    private Node createDetailPane() {
        VBox addressBox = new VBox();
        Label addressLabel = new Label("Address");
        fAddressArea.setEditable(false);
        fAddressArea.setPrefSize(250, 125);
        addressBox.getChildren().addAll(addressLabel, fAddressArea);

        VBox accountBox = new VBox();
        Label accountLabel = new Label("Info");
        fInfo.setEditable(false);
        fInfo.setPrefSize(250, 125);
        accountBox.getChildren().addAll(accountLabel, fInfo);

        VBox financialBox = new VBox();
        Label financialLabel = new Label("Financial");
        fAccount.setEditable(false);
        fAccount.setPrefSize(250, 125);
        financialBox.getChildren().addAll(financialLabel, fAccount);

        HBox result = new HBox();
        result.setSpacing(5);
        result.getChildren().addAll(addressBox, accountBox, financialBox);
        result.setAlignment(Pos.CENTER);
        result.getStyleClass().add("hboxPane");

        return result;
    }

    private Node createShipToPane() {
        GridPane shipToPane = new GridPane();
        shipToPane.setPadding(new Insets(5));
        shipToPane.setHgap(5);
        shipToPane.setVgap(5);
        shipToPane.setAlignment(Pos.CENTER);
        shipToPane.getStyleClass().add("hboxPane");

        TableColumn<VendorShipTo, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.firstName.getName()));
        firstNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        firstNameCol.setPrefWidth(100);

        TableColumn<VendorShipTo, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.lastName.getName()));
        lastNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lastNameCol.setPrefWidth(100);

        TableColumn<VendorShipTo, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.address1.getName()));
        addressCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        addressCol.setPrefWidth(265);

        TableColumn<VendorShipTo, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.city.getName()));
        cityCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cityCol.setPrefWidth(120);

        TableColumn<VendorShipTo, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.state.getName()));
        stateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        stateCol.setPrefWidth(100);

        TableColumn<VendorShipTo, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>(VendorShipTo_.phoneNumber.getName()));
        phoneCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneCol.setPrefWidth(130);

        fVendorShipToTable.getColumns().add(firstNameCol);
        fVendorShipToTable.getColumns().add(lastNameCol);
        fVendorShipToTable.getColumns().add(addressCol);
        fVendorShipToTable.getColumns().add(cityCol);
        fVendorShipToTable.getColumns().add(stateCol);
        fVendorShipToTable.getColumns().add(phoneCol);
        fVendorShipToTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fVendorShipToTable.setPrefHeight(140);
        setTableWidth(fVendorShipToTable);

        shipToPane.add(fVendorShipToTable, 0, 0);
        shipToPane.add(createNewEditDeleteButtonLeftPane(VENDOR_SHIP_TO_ADD, VENDOR_SHIP_TO_EDIT, VENDOR_SHIP_TO_DELETE), 0, 1);
        return shipToPane;
    }

    private GridPane createShipToEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        shipToUI.setUIComponent(VendorShipTo_.country, fVendorShipToCountryCombo);
        add(editPane, "First Name:", shipToUI.createTextField(VendorShipTo_.firstName, 180), fListener, 0);
        add(editPane, "Last Name:", shipToUI.createTextField(VendorShipTo_.lastName, 180), fListener, 1);
        add(editPane, "Phone Number:", shipToUI.createTextField(VendorShipTo_.phoneNumber), fListener, 2);
        add(editPane, "Fax Number:", shipToUI.createTextField(VendorShipTo_.faxNumber), fListener, 3);
        add(editPane, "Company:", shipToUI.createTextField(VendorShipTo_.company, 180), fListener, 4);
        add(editPane, "Post Code:", shipToUI.createTextField(VendorShipTo_.postCode, 180), fListener, 5);
        add(editPane, "Address 1:", shipToUI.createTextField(VendorShipTo_.address1, 180), fListener, 6);
        add(editPane, "Address 2:", shipToUI.createTextField(VendorShipTo_.address2, 180), fListener, 7);
        add(editPane, "City:", shipToUI.createTextField(VendorShipTo_.city, 180), fListener, 8);
        add(editPane, "State:", shipToUI.createTextField(VendorShipTo_.state, 180), fListener, 9);
        add(editPane, "Country:", fVendorShipToCountryCombo, fListener, 10);

        shipToUI.getTextField(VendorShipTo_.phoneNumber).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    String txt = node.getText().replaceFirst("^1+(?!$)", "");
                    if (txt.length() == 10) {
                        String phone = txt.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "($1) $2-$3");
                        node.setText(phone);
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        shipToUI.getTextField(VendorShipTo_.postCode).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    PostCode postCode = daoPostCode.find(PostCode_.postCode, node.getText().trim());
                    if (postCode != null) {
                        shipToUI.getTextField(VendorShipTo_.city).setText(postCode.getCity());
                        shipToUI.getTextField(VendorShipTo_.state).setText(postCode.getState());
                        fVendorShipToCountryCombo.setSelectedCountry(postCode.getCountry());
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });

        editPane.add(shipToWarning, 0, 12, 2, 1);
        return editPane;
    }

    private Node createVendorContactPane() {
        GridPane contactPane = new GridPane();
        contactPane.setPadding(new Insets(5));
        contactPane.setHgap(5);
        contactPane.setVgap(5);
        contactPane.setAlignment(Pos.CENTER);
        contactPane.getStyleClass().add("hboxPane");

        TableColumn<VendorContact, String> contactNameCol = new TableColumn<>("Contact Name");
        contactNameCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.contactName.getName()));
        contactNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        contactNameCol.setPrefWidth(200);

        TableColumn<VendorContact, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneNumberCol.setPrefWidth(150);

        TableColumn<VendorContact, String> cellNumberCol = new TableColumn<>("Cell Phone");
        cellNumberCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.cellPhoneNumber.getName()));
        cellNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cellNumberCol.setPrefWidth(150);

        TableColumn<VendorContact, String> emailAddressCol = new TableColumn<>("Email");
        emailAddressCol.setCellValueFactory(new PropertyValueFactory<>(VendorContact_.emailAddress.getName()));
        emailAddressCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        emailAddressCol.setPrefWidth(315);

        fVendorContactTable.getColumns().add(contactNameCol);
        fVendorContactTable.getColumns().add(phoneNumberCol);
        fVendorContactTable.getColumns().add(cellNumberCol);
        fVendorContactTable.getColumns().add(emailAddressCol);
        fVendorContactTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fVendorContactTable.setPrefHeight(140);
        setTableWidth(fVendorContactTable);

        contactPane.add(fVendorContactTable, 0, 0);
        contactPane.add(createNewEditDeleteButtonLeftPane(CONTACT_ADD, CONTACT_EDIT, CONTACT_DELETE), 0, 1);
        return contactPane;
    }

    private GridPane createVendorContactEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Contact Name:", contactUI.createTextField(VendorContact_.contactName, 180), fListener, 1);
        add(editPane, "Department:", contactUI.createTextField(VendorContact_.department, 180), fListener, 2);
        add(editPane, "Phone Number:", contactUI.createTextField(VendorContact_.phoneNumber), fListener, 3);
        add(editPane, "Cell Number:", contactUI.createTextField(VendorContact_.cellPhoneNumber), fListener, 4);
        add(editPane, "Fax Number:", contactUI.createTextField(VendorContact_.faxNumber), fListener, 5);
        add(editPane, "Email Address:", contactUI.createTextField(VendorContact_.emailAddress, 250), fListener, 6);
        editPane.add(contactWarning, 0, 8, 2, 1);
        return editPane;
    }

    private Node createNotePane() {
        GridPane notePane = new GridPane();
        notePane.setPadding(new Insets(5));
        notePane.setHgap(5);
        notePane.setVgap(5);
        notePane.setAlignment(Pos.CENTER);
        notePane.getStyleClass().add("hboxPane");

        TableColumn<VendorNote, Date> dateCreatedCol = new TableColumn<>("Date Created");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(VendorNote_.dateCreated.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCreatedCol.setPrefWidth(200);

        TableColumn<VendorNote, Date> lastUpdatedCol = new TableColumn<>("Last Updated");
        lastUpdatedCol.setCellValueFactory(new PropertyValueFactory<>(VendorNote_.lastUpdated.getName()));
        lastUpdatedCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lastUpdatedCol.setPrefWidth(200);

        TableColumn<VendorNote, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(VendorNote_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(415);

        fVendorNoteTable.getColumns().add(dateCreatedCol);
        fVendorNoteTable.getColumns().add(lastUpdatedCol);
        fVendorNoteTable.getColumns().add(descriptionCol);
        fVendorNoteTable.setPrefHeight(200);
        setTableWidth(fVendorNoteTable);

        notePane.add(fVendorNoteTable, 0, 0);
        notePane.add(createNewEditDeleteButtonLeftPane(VENDOR_NOTE_ADD, VENDOR_NOTE_EDIT, VENDOR_NOTE_DELETE), 0, 1);
        return notePane;
    }

    private GridPane createNoteEditPane() {
        GridPane notePane = new GridPane();
        notePane.getStyleClass().add("editView");

        add(notePane, "Description:", noteUI.createTextField(VendorNote_.description, 250), fListener, 0);
        Label noteLbl = new Label("Note:");
        notePane.add(noteLbl, 0, 1);
        GridPane.setHalignment(noteLbl, HPos.LEFT);
        TextArea ta = noteUI.createTextArea(VendorNote_.note);
        ta.setPrefSize(250, 250);
        ta.textProperty().addListener(fListener);
        notePane.add(ta, 0, 2, 2, 1);
        notePane.add(noteWarning, 0, 3, 2, 1);
        return notePane;
    }

    private Node createVendorItemPane() {
        GridPane vendorItemPane = new GridPane();
        vendorItemPane.setPadding(new Insets(5));
        vendorItemPane.setHgap(5);
        vendorItemPane.setVgap(5);
        vendorItemPane.setAlignment(Pos.CENTER);
        vendorItemPane.getStyleClass().add("hboxPane");

        TableColumn<VendorItem, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.vendorItemLookUpCode.getName()));
        vendorSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        vendorSKUCol.setPrefWidth(150);

        TableColumn<VendorItem, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        skuCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        skuCol.setPrefWidth(150);

        TableColumn<VendorItem, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(415);

        TableColumn<VendorItem, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.cost.getName()));
        costCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        costCol.setPrefWidth(100);

        fVendorItemTable.getColumns().add(vendorSKUCol);
        fVendorItemTable.getColumns().add(skuCol);
        fVendorItemTable.getColumns().add(descriptionCol);
        fVendorItemTable.getColumns().add(costCol);
        fVendorItemTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fVendorItemTable.setPrefHeight(140);
        setTableWidth(fVendorItemTable);

        vendorItemPane.add(fVendorItemTable, 0, 0);
        vendorItemPane.add(createNewEditDeleteButtonLeftPane(VENDOR_ITEM_ADD, VENDOR_ITEM_EDIT, VENDOR_ITEM_DELETE), 0, 1);
        return vendorItemPane;
    }

    private GridPane createVendorItemEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        Button btn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT);
        btn.setId(AppConstants.ACTION_SELECT);
        btn.setOnAction(fHandler);

        add(editPane, "Vendor SKU:*", vendorItemUI.createTextField(VendorItem_.vendorItemLookUpCode), fListener, 250.0, 1);
        add(editPane, "Your SKU:", fItemField, fListener, 2);
        editPane.add(btn, 3, 2);
        add(editPane, "Cost:", vendorItemUI.createTextField(VendorItem_.cost), fListener, 3);

        editPane.add(vendorItemWarning, 1, 4, 2, 1);

        return editPane;
    }

    private void updateInfoPane(Vendor vendor) {
        if (vendor == null) {
            fAddressArea.setText("");
            fAccount.setText("");
            fInfo.setText("");
        } else {
            fAddressArea.setText(AddressFactory.getVendorAddress(vendor, AddressFactory.STYLE_SHORT));
            fAddressArea.setFont(Font.font("Arial Narrow", 12));
            String info = "";
            info = info + addToString(vendor.getAccountNumber(), "Account Number: ");
            info = info + addToString(vendor.getTaxNumber(), "Tax Number: ");
            info = info + addToString(vendor.getWebAddress(), "Web Address: ");
            if (vendor.getUseVendorSku() != null && vendor.getUseVendorSku() == true) {
                info = info + addToString("Yes", "Use Vendor SKU: ");
            } else {
                info = info + addToString("No", "Use Vendor SKU: ");
            }
            if (vendor.getUseAutoSku() != null && vendor.getUseAutoSku() == true) {
                info = info + addToString("Yes", "Use auto SKU: ");
            } else {
                info = info + addToString("No", "Use auto SKU: ");
            }
            info = info + addToString(vendor.getDateCreated(), "Date Created: ");
            info = info + addToString(vendor.getLastUpdated(), "Last Updated: ");

            fInfo.setText(info.trim());
            fInfo.setFont(Font.font("Arial Narrow", 12));
            String acc = "";
            if (vendor.getVendorTerm() != null) {
                acc = acc + addToString(vendor.getVendorTerm().getCode(), "Term: ");
            } else {
                acc = acc + addToString("", "Term: ");
            }
            acc = acc + addToString(vendor.getCreditLimit(), "Credit Limit: ");
            acc = acc + addToString(vendor.getReturnDays(), "Return Days: ");
            acc = acc + addToString(vendor.getDiscountDays(), "Discount Days: ");
            acc = acc + addToString(vendor.getDiscountRate(), "Discount Rate%: ");

            fAccount.setText(acc.trim());
            fAccount.setFont(Font.font("Arial Narrow", 12));
        }
    }

    private HBox createNewEditDeleteButtonLeftPane(String add, String edit, String delete) {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE);
        newButton.setId(add);
        editButton.setId(edit);
        deleteButton.setId(delete);
        newButton.setOnAction(fHandler);
        editButton.setOnAction(fHandler);
        deleteButton.setOnAction(fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().add(newButton);
        buttonGroup.getChildren().add(editButton);
        buttonGroup.getChildren().add(deleteButton);
        buttonGroup.setAlignment(Pos.CENTER_LEFT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            if (fInputDialog.getTitle() == null ? VENDOR_TITLE == null : fInputDialog.getTitle().equals(VENDOR_TITLE)) {
                vendorUI.getTextField(Vendor_.company).setText(uppercaseFirst(vendorUI.getTextField(Vendor_.company).getText()));
                vendorUI.getTextField(Vendor_.vendorContactName).setText(uppercaseFirst(vendorUI.getTextField(Vendor_.vendorContactName).getText()));
                vendorUI.getTextField(Vendor_.address1).setText(uppercaseFirst(vendorUI.getTextField(Vendor_.address1).getText()));
                vendorUI.getTextField(Vendor_.address2).setText(uppercaseFirst(vendorUI.getTextField(Vendor_.address2).getText()));
                vendorUI.getTextField(Vendor_.city).setText(uppercaseFirst(vendorUI.getTextField(Vendor_.city).getText()));
                vendorUI.getTextField(Vendor_.state).setText(uppercaseFirst(vendorUI.getTextField(Vendor_.state).getText()));
                saveBtn.setDisable(vendorUI.getTextField(Vendor_.company).getText().trim().isEmpty());
            } else if (fInputDialog.getTitle() == null ? CONTACT_TITLE == null : fInputDialog.getTitle().equals(CONTACT_TITLE)) {
                contactUI.getTextField(VendorContact_.contactName).setText(uppercaseFirst(contactUI.getTextField(VendorContact_.contactName).getText()));
                saveBtn.setDisable(contactUI.getTextField(VendorContact_.contactName).getText().trim().isEmpty());
            } else if (fInputDialog.getTitle() == null ? VENDOR_NOTE_TITLE == null : fInputDialog.getTitle().equals(VENDOR_NOTE_TITLE)) {
                saveBtn.setDisable(false);
            } else if (fInputDialog.getTitle() == null ? VENDOR_SHIP_TO_TITLE == null : fInputDialog.getTitle().equals(VENDOR_SHIP_TO_TITLE)) {
                shipToUI.getTextField(VendorShipTo_.firstName).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.firstName).getText()));
                shipToUI.getTextField(VendorShipTo_.lastName).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.lastName).getText()));
                shipToUI.getTextField(VendorShipTo_.company).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.company).getText()));
                shipToUI.getTextField(VendorShipTo_.address1).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.address1).getText()));
                shipToUI.getTextField(VendorShipTo_.address2).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.address2).getText()));
                shipToUI.getTextField(VendorShipTo_.city).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.city).getText()));
                shipToUI.getTextField(VendorShipTo_.state).setText(uppercaseFirst(shipToUI.getTextField(VendorShipTo_.state).getText()));
                saveBtn.setDisable(shipToUI.getTextField(VendorShipTo_.address1).getText().trim().isEmpty() || shipToUI.getTextField(VendorShipTo_.city).getText().trim().isEmpty()
                        || shipToUI.getTextField(VendorShipTo_.state).getText().trim().isEmpty() || shipToUI.getTextField(VendorShipTo_.postCode).getText().trim().isEmpty());
            }
        }
    }
}

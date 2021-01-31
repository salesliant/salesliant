package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.EmployeeGroup;
import com.salesliant.entity.EmployeeGroup_;
import com.salesliant.entity.Employee_;
import com.salesliant.entity.PostCode;
import com.salesliant.entity.PostCode_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import com.salesliant.util.SearchField;
import com.salesliant.validator.EmployeeValidator;
import com.salesliant.validator.InvalidException;
import com.salesliant.widget.CountryWidget;
import com.salesliant.widget.EmployeeGroupWidget;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EmployeeListUI extends BaseListUI<Employee> {

    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
    private final BaseDao<EmployeeGroup> daoEmployeeGroup = new BaseDao<>(EmployeeGroup.class);
    private final BaseDao<PostCode> daoPostCode = new BaseDao<>(PostCode.class);
    private final DataUI dataUI = new DataUI(Employee.class);
    private final VBox fEditPane;
    private final TabPane fTabPane = new TabPane();
    private final EmployeeValidator validator = new EmployeeValidator();
    private ComboBox<EmployeeGroup> fEmployeeGroupCombo;
    private final CountryWidget fCountryCombo = new CountryWidget();
    private static final Logger LOGGER = Logger.getLogger(EmployeeListUI.class.getName());

    public EmployeeListUI() {
        loadData();
        mainView = createMainView();
        fEditPane = createEditPane();
    }

    private void loadData() {
        List<Employee> list = daoEmployee.read(Employee_.store, Config.getStore(), Employee_.activeTag, true).stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                            + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                    String name2 = (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                            + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
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
                fEntity = new Employee();
                fEntity.setActiveTag(Boolean.TRUE);
                fEntity.setStore(Config.getStore());
                fEntity.setDateCreated(new Date());
                fEntity.setCountry(Config.getStore().getCountry());
                fEntity.setState(Config.getStore().getState());
                List<EmployeeGroup> employeeGroupList = daoEmployeeGroup.read(EmployeeGroup_.store, Config.getStore());
                employeeGroupList.stream().filter(e -> (e.getCode().startsWith("Sales"))).forEachOrdered(e -> {
                    fEntity.setEmployeeGroup(e);
                });
                if (fEntity.getEmployeeGroup() == null) {
                    fEntity.setEmployeeGroup(employeeGroupList.get(0));
                }
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Employee");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        validator.validate(fEntity);
                        daoEmployee.insert(fEntity);
                        if (daoEmployee.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.scrollTo(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoEmployee.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        if (ex instanceof InvalidException) {
                            lblWarning.setText(ex.getMessage());
                            event.consume();
                        } else {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    }
                });
                Platform.runLater(() -> {
                    fTabPane.getSelectionModel().selectFirst();
                    dataUI.getTextField(Employee_.firstName).requestFocus();
                });
                fInputDialog.showDialog();
                break;

            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    Employee backup = new Employee();
                    copyProperties(backup, fEntity);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Employee");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            validator.validate(fEntity);
                            daoEmployee.update(fEntity);
                            if (daoEmployee.getErrorMessage() == null) {
                                loadData();
                            } else {
                                lblWarning.setText(AppConstants.ERROR_INPUT);
                                copyProperties(fEntity, backup);
                                fTableView.refresh();
                                event.consume();
                            }
                        } catch (Exception ex) {
                            if (ex instanceof InvalidException) {
                                lblWarning.setText(ex.getMessage());
                                event.consume();
                            } else {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    Platform.runLater(() -> {
                        fTabPane.getSelectionModel().selectFirst();
                        dataUI.getTextField(Employee_.firstName).requestFocus();
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoEmployee.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
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

        Label tableLbl = new Label("List of Employee:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TextField searchField = new SearchField(fTableView);
        mainPane.add(searchField, 0, 2, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

//        TableColumn<Employee, String> firstNameCol = new TableColumn<>("First Name");
//        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.firstName.getName()));
//        firstNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
//        firstNameCol.setPrefWidth(150);
//
//        TableColumn<Employee, String> lastNameCol = new TableColumn<>("Last Name");
//        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.lastName.getName()));
//        lastNameCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
//        lastNameCol.setPrefWidth(150);
//
//        TableColumn<Employee, String> phoneNumberCol = new TableColumn<>("Phone Number");
//        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.phoneNumber.getName()));
//        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
//        phoneNumberCol.setPrefWidth(150);
//
//        TableColumn<Employee, String> emailCol = new TableColumn<>("Email");
//        emailCol.setCellValueFactory(new PropertyValueFactory<>(Employee_.emailAddress.getName()));
//        emailCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
//        emailCol.setPrefWidth(250);
        addTableColumn(fTableView, "First Name", 150, new PropertyValueFactory<>(Employee_.firstName.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Last Name", 150, new PropertyValueFactory<>(Employee_.lastName.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Name On Sales Order", 150, new PropertyValueFactory<>(Employee_.nameOnSalesOrder.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Phone Number", 150, new PropertyValueFactory<>(Employee_.phoneNumber.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Email", 200, new PropertyValueFactory<>(Employee_.emailAddress.getName()), stringCell(Pos.CENTER_LEFT));
//        fTableView.getColumns().add(firstNameCol);
//        fTableView.getColumns().add(lastNameCol);
//        fTableView.getColumns().add(phoneNumberCol);
//        fTableView.getColumns().add(emailCol);
//        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 3);
        mainPane.add(createButtonPane(), 0, 4);
        return mainPane;
    }

    protected HBox createButtonPane() {
        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList("Active", "Inactive"));
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue.equals("Active")) {
                List<Employee> list = daoEmployee.read(Employee_.store, Config.getStore(), Employee_.activeTag, true).stream()
                        .sorted((e1, e2) -> {
                            String name1 = (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                                    + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                            String name2 = (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                                    + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());
                fEntityList = FXCollections.observableList(list);
            } else {
                List<Employee> list = daoEmployee.read(Employee_.store, Config.getStore(), Employee_.activeTag, false).stream()
                        .sorted((e1, e2) -> {
                            String name1 = (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                                    + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                            String name2 = (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                                    + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                            return name1.compareToIgnoreCase(name2);
                        })
                        .collect(Collectors.toList());
                fEntityList = FXCollections.observableList(list);
            }
            fTableView.setItems(fEntityList);
        }
        );
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

    private VBox createEditPane() {
        VBox editPane = new VBox();
        editPane.getStyleClass().add("editView");
        editPane.setSpacing(5);
//        editPane.setPrefSize(500, 450);
        fTabPane.getStyleClass().add("editView");

        Tab addressTab = new Tab(" Address ");
        addressTab.setContent(createAddressPane());
        addressTab.setClosable(false);
        fTabPane.getTabs().add(addressTab);

        Tab settingTab = new Tab(" Setting ");
        settingTab.setContent(createSettingPane());
        settingTab.setClosable(false);
        fTabPane.getTabs().add(settingTab);

        Tab noteTab = new Tab(" Note ");
        noteTab.setContent(createNotePane());
        noteTab.setClosable(false);
        fTabPane.getTabs().add(noteTab);

        editPane.getChildren().addAll(fTabPane, lblWarning);

        return editPane;
    }

    private Node createAddressPane() {
        GridPane addressPane = new GridPane();
        addressPane.getStyleClass().add("editView");
        addressPane.setPadding(new Insets(3));
        addressPane.setHgap(5);
        addressPane.setVgap(5);
        dataUI.setUIComponent(Employee_.country, fCountryCombo);
        add(addressPane, "First Name:*", dataUI.createTextField(Employee_.firstName), fListener, 0);
        add(addressPane, "Last Name:*", dataUI.createTextField(Employee_.lastName), fListener, 1);
        add(addressPane, "Post Code:", dataUI.createTextField(Employee_.postCode), fListener, 2);
        add(addressPane, "Address 1:", dataUI.createTextField(Employee_.address1), fListener, 3);
        add(addressPane, "Address 2:", dataUI.createTextField(Employee_.address2), fListener, 4);
        add(addressPane, "City:", dataUI.createTextField(Employee_.city), fListener, 5);
        add(addressPane, "State:", dataUI.createTextField(Employee_.state), fListener, 6);
        add(addressPane, "Country:", fCountryCombo, fListener, 7);
        add(addressPane, "Phone/Cell:", new TextField[]{dataUI.createTextField(Employee_.phoneNumber), dataUI.createTextField(Employee_.cellPhoneNumber)}, fListener, 8);
        add(addressPane, "Email Address:", dataUI.createTextField(Employee_.emailAddress), fListener, 9);

        dataUI.getTextField(Employee_.phoneNumber).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
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
        dataUI.getTextField(Employee_.postCode).addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                TextField node = (TextField) event.getSource();
                if (node.getText() != null && !node.getText().isEmpty()) {
                    PostCode postCode = daoPostCode.find(PostCode_.postCode, node.getText().trim());
                    if (postCode != null) {
                        dataUI.getTextField(Employee_.city).setText(postCode.getCity());
                        dataUI.getTextField(Employee_.state).setText(postCode.getState());
                        fCountryCombo.setSelectedCountry(postCode.getCountry());
                    }
                }
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        return addressPane;
    }

    private Node createSettingPane() {
        GridPane settingPane = new GridPane();
        settingPane.getStyleClass().add("editView");
        settingPane.setPadding(new Insets(3));
        settingPane.setHgap(5);
        settingPane.setVgap(5);
        fEmployeeGroupCombo = new EmployeeGroupWidget();
        fEmployeeGroupCombo.setPrefWidth(250);
        dataUI.setUIComponent(Employee_.employeeGroup, fEmployeeGroupCombo);

        add(settingPane, "Active:", dataUI.createCheckBox(Employee_.activeTag), fListener, 0);
        add(settingPane, "Login ID:*", dataUI.createTextField(Employee_.login), fListener, 1);
        add(settingPane, "Password:*", dataUI.createPasswordField(Employee_.password), fListener, 2);
        add(settingPane, "Name On Sales Order:*", dataUI.createTextField(Employee_.nameOnSalesOrder), fListener, 3);
        add(settingPane, "Title:", dataUI.createTextField(Employee_.title), fListener, 4);
        add(settingPane, "Social Security No.:", dataUI.createTextField(Employee_.ssn), fListener, 5);
        add(settingPane, "Date Created:", dataUI.createDatePicker(Employee_.dateCreated), fListener, 6);
        add(settingPane, "Group*:", fEmployeeGroupCombo, fListener, 7);
        add(settingPane, "Picture Name:", dataUI.createTextField(Employee_.pictureName), fListener, 8);

        return settingPane;
    }

    private Node createNotePane() {
        GridPane notePane = new GridPane();
        notePane.getStyleClass().add("editView");
        notePane.setPadding(new Insets(3));
        notePane.setHgap(5);
        notePane.setVgap(5);
        add(notePane, "Note:", dataUI.createTextArea(Employee_.note), 250, 350, fListener, 0);
        return notePane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            dataUI.getTextField(Employee_.firstName).setText(uppercaseFirst(dataUI.getTextField(Employee_.firstName).getText()));
            dataUI.getTextField(Employee_.lastName).setText(uppercaseFirst(dataUI.getTextField(Employee_.lastName).getText()));
            dataUI.getTextField(Employee_.address1).setText(uppercaseFirst(dataUI.getTextField(Employee_.address1).getText()));
            dataUI.getTextField(Employee_.address2).setText(uppercaseFirst(dataUI.getTextField(Employee_.address2).getText()));
            dataUI.getTextField(Employee_.city).setText(uppercaseFirst(dataUI.getTextField(Employee_.city).getText()));
            dataUI.getTextField(Employee_.state).setText(uppercaseFirst(dataUI.getTextField(Employee_.state).getText()));
            dataUI.getTextField(Employee_.nameOnSalesOrder).setText(uppercaseFirst(dataUI.getTextField(Employee_.nameOnSalesOrder).getText()));
            saveBtn.setDisable(dataUI.getTextField(Employee_.firstName).getText().trim().isEmpty()
                    || dataUI.getTextField(Employee_.lastName).getText().trim().isEmpty()
                    || dataUI.getTextField(Employee_.nameOnSalesOrder).getText().trim().isEmpty()
                    || fEmployeeGroupCombo.getSelectionModel().getSelectedItem() == null);
        }
    }
}

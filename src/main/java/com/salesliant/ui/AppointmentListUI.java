package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Appointment;
import com.salesliant.entity.Appointment_;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Employee;
import static com.salesliant.ui.SalesOrderUI.CUSTOMER_SELECTED;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.EmployeeWidget;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class AppointmentListUI extends BaseListUI<Appointment> {

    private final BaseDao<Appointment> daoAppointment = new BaseDao<>(Appointment.class);
    private final DataUI dataUI = new DataUI(Appointment.class);
    private final EmployeeWidget fEmployeeCombo = new EmployeeWidget();
    protected Customer fCustomer;
    private final GridPane fEditPane;
    private final static String TITLE = "Employee Time Card";
    protected final static String SELECT_CUSTOMER = "Select_Customer";
    private static final Logger LOGGER = Logger.getLogger(AppointmentListUI.class.getName());

    public AppointmentListUI() {
        fEmployeeCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Employee> observable, Employee newValue, Employee oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                saveBtn.setDisable(false);
            } else {
                saveBtn.setDisable(true);
            }
        });
        mainView = createMainView();
        List<Appointment> list = daoAppointment.readOrderBy(Appointment_.store, Config.getStore(), Appointment_.appointmentDate, AppConstants.ORDER_BY_DESC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        dialogTitle = TITLE;
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
                        handleAction(CUSTOMER_SELECTED);
                    }
                });
                fInputDialog.showDialog();
                break;
            case CUSTOMER_SELECTED:
                fEntity = new Appointment();
                fEntity.setCustomer(fCustomer);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, TITLE);
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (fEmployeeCombo.getSelectionModel().getSelectedItem() != null) {
                        try {
                            dataUI.getData(fEntity);
                            fEntity.setStore(Config.getStore());
                            fEntity.setDateCreated(new Date());
                            daoAppointment.insert(fEntity);
                            if (daoAppointment.getErrorMessage() == null) {
                                fEntityList.add(fEntity);
                                fTableView.refresh();
                                fTableView.requestFocus();
                                fTableView.getSelectionModel().select(fEntity);
                            } else {
                                lblWarning.setText(daoAppointment.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }

                    } else {
                        event.consume();
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
                    fInputDialog = createSaveCancelUIDialog(fEditPane, TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        if (fEmployeeCombo.getSelectionModel().getSelectedItem() != null) {
                            try {
                                dataUI.getData(fEntity);
                                if (fEntity.getDateCreated() == null) {
                                    fEntity.setDateCreated(new Date());
                                }
                                daoAppointment.update(fEntity);
                                if (daoAppointment.getErrorMessage() == null) {
                                    fTableView.refresh();
                                } else {
                                    lblWarning.setText(daoAppointment.getErrorMessage());
                                    event.consume();
                                }
                            } catch (Exception ex) {
                                LOGGER.log(Level.SEVERE, null, ex);
                            }

                        } else {
                            event.consume();
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoAppointment.delete(fEntity);
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
        Label tableLbl = new Label("List of Appointment:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Appointment, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((TableColumn.CellDataFeatures<Appointment, String> p) -> {
            if (p.getValue().getCustomer() != null) {
                if (p.getValue().getCustomer().getCompany() != null && !p.getValue().getCustomer().getCompany().isEmpty()) {
                    return new SimpleStringProperty(p.getValue().getCustomer().getCompany());
                } else {
                    String name = !isEmpty(p.getValue().getCustomer().getFirstName()) ? p.getValue().getCustomer().getFirstName() : ""
                            + (!isEmpty(p.getValue().getCustomer().getFirstName()) ? " " : "")
                            + (!isEmpty(p.getValue().getCustomer().getLastName()) ? p.getValue().getCustomer().getLastName() : "");
                    return new SimpleStringProperty(name);
                }
            } else {
                return null;
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(200);

        TableColumn<Appointment, String> techCol = new TableColumn<>("Tech");
        techCol.setCellValueFactory((TableColumn.CellDataFeatures<Appointment, String> p) -> {
            if (p.getValue().getEmployee() != null && p.getValue().getEmployee().getNameOnSalesOrder() != null) {
                return new SimpleStringProperty(p.getValue().getEmployee().getNameOnSalesOrder());
            } else {
                return null;
            }
        });
        techCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        techCol.setPrefWidth(100);

        TableColumn<Appointment, String> appointmentCol = new TableColumn<>("Appointment");
        appointmentCol.setCellValueFactory(new PropertyValueFactory<>(Appointment_.appointmentDate.getName()));
        appointmentCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        appointmentCol.setPrefWidth(130);

        TableColumn<Appointment, String> dateCreatedCol = new TableColumn<>("Date Created");
        dateCreatedCol.setCellValueFactory(new PropertyValueFactory<>(Appointment_.dateCreated.getName()));
        dateCreatedCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dateCreatedCol.setPrefWidth(100);

        TableColumn<Appointment, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(Appointment_.note.getName()));
        noteCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        noteCol.setPrefWidth(350);

        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(techCol);
        fTableView.getColumns().add(appointmentCol);
        fTableView.getColumns().add(dateCreatedCol);
        fTableView.getColumns().add(noteCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        fTableView.setEditable(false);
        setTableWidth(fTableView);

//        mainPane.add(fEmployeeCombo, 0, 2);
        mainPane.add(fTableView, 0, 3);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        fEmployeeCombo.setPrefWidth(350);
        dataUI.setUIComponent(Appointment_.employee, fEmployeeCombo);
        add(editPane, "Technician: ", fEmployeeCombo, fListener, 1);
        add(editPane, "Appointment Day: ", dataUI.createDateTimePicker(Appointment_.appointmentDate), fListener, 2);
        add(editPane, "Note: ", dataUI.createTextArea(Appointment_.note), 250, 350, fListener, 3);
        editPane.add(lblWarning, 0, 3, 2, 1);
        return editPane;
    }
}

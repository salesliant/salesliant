package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.CustomerShipTo;
import com.salesliant.entity.CustomerShipTo_;
import com.salesliant.entity.Customer_;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import com.salesliant.util.FormattedTableCellFactory;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class CustomerShipToListUI extends BaseListUI<Customer> {

    private final BaseDao<Customer> daoCustomer = new BaseDao<>(Customer.class);
    private final BaseDao<CustomerShipTo> daoCustomerShipTo = new BaseDao<>(CustomerShipTo.class);
    private TableView<CustomerShipTo> fShipToTableView = new TableView<>();
    private ObservableList<CustomerShipTo> fCustomerShipToList;

    public CustomerShipToListUI() {
        mainView = createMainView();
        List<Customer> list = daoCustomer.read(Customer_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "")
                            + (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                            + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                    String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "")
                            + (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                            + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                    return name1.compareToIgnoreCase(name2);
                })
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Customer> observable, Customer newValue, Customer oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                List<CustomerShipTo> shipToList = daoCustomerShipTo.read(CustomerShipTo_.customer, fEntity.getId());
                fCustomerShipToList = FXCollections.observableList(shipToList);

            } else {
                fCustomerShipToList.clear();
            }
            fShipToTableView.setItems(fCustomerShipToList);
        });
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Customer:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Customer, String> accountNumberCol = new TableColumn<>("Account");
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.accountNumber.getName()));
        accountNumberCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        accountNumberCol.setPrefWidth(90);

        TableColumn<Customer, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.firstName.getName()));
        firstNameCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        firstNameCol.setPrefWidth(150);

        TableColumn<Customer, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.lastName.getName()));
        lastNameCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        lastNameCol.setPrefWidth(150);
        TableColumn<Customer, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((TableColumn.CellDataFeatures<Customer, String> p) -> {
            if (p.getValue() != null) {
                return new SimpleStringProperty(p.getValue().getCompany());
            } else {
                return new SimpleStringProperty("");
            }
        });
        companyCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        companyCol.setPrefWidth(150);

        TableColumn<Customer, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Customer_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        phoneNumberCol.setPrefWidth(140);

        fTableView.getColumns().add(accountNumberCol);
        fTableView.getColumns().add(firstNameCol);
        fTableView.getColumns().add(lastNameCol);
        fTableView.getColumns().add(companyCol);
        fTableView.getColumns().add(phoneNumberCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);

        TableColumn<CustomerShipTo, String> shipToNameCol = new TableColumn<>("Contact Name");
        shipToNameCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.contactName.getName()));
        shipToNameCol.setPrefWidth(100);

        TableColumn<CustomerShipTo, String> shipToAddressCol = new TableColumn<>("Address");
        shipToAddressCol.setCellValueFactory((CellDataFeatures<CustomerShipTo, String> p) -> {
            if (p.getValue() != null) {
                String shipTo = "";
                if (!isEmpty(p.getValue().getAddress1())) {
                    shipTo = shipTo + p.getValue().getAddress1() + "\n";
                }
                if (!isEmpty(p.getValue().getAddress2())) {
                    shipTo = shipTo + p.getValue().getAddress2();
                }
                return new SimpleStringProperty(shipTo);
            } else {
                return null;
            }
        });
        shipToAddressCol.setPrefWidth(150);

        TableColumn<CustomerShipTo, String> shipToCityCol = new TableColumn<>("City");
        shipToCityCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.city.getName()));
        shipToCityCol.setPrefWidth(120);

        TableColumn<CustomerShipTo, String> shipToStateCol = new TableColumn<>("State");
        shipToStateCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.state.getName()));
        shipToStateCol.setPrefWidth(90);

        TableColumn<CustomerShipTo, String> shipToPhoneCol = new TableColumn<>("Phone");
        shipToPhoneCol.setCellValueFactory(new PropertyValueFactory<>(CustomerShipTo_.phoneNumber.getName()));
        shipToPhoneCol.setPrefWidth(110);

        fShipToTableView.getColumns().add(shipToNameCol);
        fShipToTableView.getColumns().add(shipToAddressCol);
        fShipToTableView.getColumns().add(shipToCityCol);
        fShipToTableView.getColumns().add(shipToStateCol);
        fShipToTableView.getColumns().add(shipToPhoneCol);
        fShipToTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fShipToTableView.setPrefHeight(140);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(fShipToTableView, 0, 3);
        return mainPane;
    }

    public TableView<CustomerShipTo> getShipToTable() {
        return fShipToTableView;
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); // To
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.Vendor_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.SearchField;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lewis
 */
public class VendorTableWidget extends BaseListUI<Vendor> {

    private final BaseDao<Vendor> daoVendor = new BaseDao<>(Vendor.class);

    public VendorTableWidget() {
        List<Vendor> list = daoVendor.readOrderBy(Vendor_.store, Config.getStore(), Vendor_.company, AppConstants.ORDER_BY_ASC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        mainView = createMainView();

    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);

        TextField searchField = new SearchField(fTableView);

        mainPane.add(searchField, 0, 1, 2, 1);
        GridPane.setHalignment(searchField, HPos.LEFT);

        TableColumn<Vendor, String> accountNumberCol = new TableColumn<>("Account");
        accountNumberCol.setCellValueFactory(new PropertyValueFactory<>(Vendor_.accountNumber.getName()));
        accountNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        accountNumberCol.setPrefWidth(90);

        TableColumn<Vendor, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory((TableColumn.CellDataFeatures<Vendor, String> p) -> {
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
        contactrCol.setPrefWidth(140);

        TableColumn<Vendor, String> phoneNumberCol = new TableColumn<>("Phone");
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>(Vendor_.phoneNumber.getName()));
        phoneNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        phoneNumberCol.setPrefWidth(100);

        TableColumn<Vendor, String> termCol = new TableColumn<>("Term");
        termCol.setCellValueFactory((TableColumn.CellDataFeatures<Vendor, String> p) -> {
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
        creditLimitCol.setPrefWidth(100);

        fTableView.getColumns().add(companyCol);
        fTableView.getColumns().add(contactrCol);
        fTableView.getColumns().add(phoneNumberCol);
        fTableView.getColumns().add(accountNumberCol);
        fTableView.getColumns().add(termCol);
        fTableView.getColumns().add(creditLimitCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        fTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        mainPane.add(fTableView, 0, 2, 2, 1);
        return mainPane;
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

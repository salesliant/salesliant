/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.SearchField;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Lewis
 */
public class EmployeeTableWidget extends BaseListUI<Employee> {

    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);

    public EmployeeTableWidget() {
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
        mainView = createMainView();

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

        addTableColumn(fTableView, "First Name", 100, new PropertyValueFactory<>(Employee_.firstName.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Last Name", 100, new PropertyValueFactory<>(Employee_.lastName.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Name on Sales Order", 150, new PropertyValueFactory<>(Employee_.nameOnSalesOrder.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Address", 150, new PropertyValueFactory<>(Employee_.address1.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "City", 100, new PropertyValueFactory<>(Employee_.city.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "State", 100, new PropertyValueFactory<>(Employee_.state.getName()), stringCell(Pos.CENTER_LEFT));
        addTableColumn(fTableView, "Zip", 100, new PropertyValueFactory<>(Employee_.postCode.getName()), stringCell(Pos.CENTER_LEFT));

        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 3);
        return mainPane;
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

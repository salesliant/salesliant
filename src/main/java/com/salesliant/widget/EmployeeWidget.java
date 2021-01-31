/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.isEmpty;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;

/**
 *
 * @author Lewis
 */
public class EmployeeWidget extends ComboBox<Employee> {

    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);

    public EmployeeWidget() {
        setButtonCell(new EmployeeListCell());
        setCellFactory((ListView<Employee> parm) -> new EmployeeListCell());
        List<Employee> list = daoEmployee.read(Employee_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getLastName()) ? e1.getLastName() : "")
                            + (!isEmpty(e1.getFirstName()) ? e1.getFirstName() : "");
                    String name2 = (!isEmpty(e2.getLastName()) ? e2.getLastName() : "")
                            + (!isEmpty(e2.getFirstName()) ? e2.getFirstName() : "");
                    return name1.compareToIgnoreCase(name2);
                })
                .collect(Collectors.toList());
        ObservableList<Employee> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class EmployeeListCell extends ListCell<Employee> {

        @Override
        protected void updateItem(Employee item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getNameOnSalesOrder());
                setFont(Font.font("Arial Narrow", 12));
            }
        }
    }
}

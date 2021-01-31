/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.ServiceCode;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.getString;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author Lewis
 */
public class ServiceCodeWidget extends ComboBox<ServiceCode> {

    private final BaseDao<ServiceCode> daoServiceCode = new BaseDao(ServiceCode.class);
    private final List<ServiceCode> list;

    public ServiceCodeWidget() {
        setButtonCell(new ServiceCodeListCell());
        setCellFactory((ListView<ServiceCode> parm) -> new ServiceCodeListCell());
        list = daoServiceCode.read().stream()
                .sorted((e1, e2) -> getString(e1.getDisplayOrder()).compareTo(getString(e2.getDisplayOrder())))
                .collect(Collectors.toList());
        ObservableList<ServiceCode> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ServiceCodeListCell extends ListCell<ServiceCode> {

        @Override
        protected void updateItem(ServiceCode item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getCode());
            }
        }
    }

}

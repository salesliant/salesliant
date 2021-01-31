/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.PaymentType;
import com.salesliant.util.BaseDao;
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
public class PaymentTypeWidget extends ComboBox<PaymentType> {

    private final BaseDao<PaymentType> daoPaymentType = new BaseDao<>(PaymentType.class);

    public PaymentTypeWidget() {
        setButtonCell(new PaymentTypeListCell());
        setCellFactory((ListView<PaymentType> parm) -> new PaymentTypeListCell());
        List<PaymentType> list = daoPaymentType.read().stream()
                .sorted((e1, e2) -> e1.getCode().compareToIgnoreCase(e2.getCode()))
                .collect(Collectors.toList());
        ObservableList<PaymentType> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class PaymentTypeListCell extends ListCell<PaymentType> {

        @Override
        protected void updateItem(PaymentType item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getCode());
            }
        }
    }
}

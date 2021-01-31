/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.TaxRate;
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
public class TaxRateWidget extends ComboBox<TaxRate> {

    private final BaseDao<TaxRate> daoTaxRate = new BaseDao(TaxRate.class);

    public TaxRateWidget() {

        setButtonCell(new TaxRateListCell());
        setCellFactory((ListView<TaxRate> parm) -> new TaxRateListCell());
        List<TaxRate> list = daoTaxRate.read().stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        ObservableList<TaxRate> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class TaxRateListCell extends ListCell<TaxRate> {

        @Override
        protected void updateItem(TaxRate item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}

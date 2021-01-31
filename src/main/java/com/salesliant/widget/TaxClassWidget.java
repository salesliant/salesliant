/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.TaxClass;
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
public class TaxClassWidget extends ComboBox<TaxClass> {

    private final BaseDao<TaxClass> daoTaxClass = new BaseDao(TaxClass.class);

    public TaxClassWidget() {

        setButtonCell(new TaxClassListCell());
        setCellFactory((ListView<TaxClass> parm) -> new TaxClassListCell());
        List<TaxClass> list = daoTaxClass.read().stream()
                .sorted((e1, e2) -> e1.getDescription().compareToIgnoreCase(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<TaxClass> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class TaxClassListCell extends ListCell<TaxClass> {

        @Override
        protected void updateItem(TaxClass item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}

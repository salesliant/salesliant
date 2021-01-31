/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Tax;
import com.salesliant.util.BaseDao;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author Lewis
 */
public class TaxWidget extends ComboBox<Tax> {

    private final BaseDao<Tax> daoTax = new BaseDao<>(Tax.class);

    public TaxWidget() {
        setButtonCell(new TaxListCell());
        setCellFactory((ListView<Tax> parm) -> new TaxListCell());
        List<Tax> list = daoTax.read();
        ObservableList<Tax> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class TaxListCell extends ListCell<Tax> {

        @Override
        protected void updateItem(Tax item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(null);
            }
        }
    }
}

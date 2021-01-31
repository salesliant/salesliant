/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Currency;
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
public class CurrencyWidget extends ComboBox<Currency> {

    private final BaseDao<Currency> daoCurrency = new BaseDao(Currency.class);

    public CurrencyWidget() {
        setButtonCell(new CurrencyListCell());
        setCellFactory((ListView<Currency> parm) -> new CurrencyListCell());
        List<Currency> list = daoCurrency.read();
        ObservableList<Currency> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class CurrencyListCell extends ListCell<Currency> {

        @Override
        protected void updateItem(Currency item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getTitle());
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.TaxZone;
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
public class TaxZoneWidget extends ComboBox<TaxZone> {

    private final BaseDao<TaxZone> daoTaxZone = new BaseDao(TaxZone.class);

    public TaxZoneWidget() {
        setButtonCell(new TaxZoneListCell());
        setCellFactory((ListView<TaxZone> parm) -> new TaxZoneListCell());
        List<TaxZone> list = daoTaxZone.read();
        ObservableList<TaxZone> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class TaxZoneListCell extends ListCell<TaxZone> {

        @Override
        protected void updateItem(TaxZone item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}

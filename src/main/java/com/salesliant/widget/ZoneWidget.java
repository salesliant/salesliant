/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import com.salesliant.entity.Country;
import com.salesliant.entity.Zone;
import com.salesliant.entity.Zone_;
import com.salesliant.util.BaseDao;

/**
 *
 * @author Lewis
 */
public class ZoneWidget extends ComboBox<Zone> {

    private final BaseDao<Zone> daoZone = new BaseDao(Zone.class);
    private List<Zone> list = null;

    public ZoneWidget() {
        setButtonCell(new ZoneListCell());
        setCellFactory((ListView<Zone> parm) -> new ZoneListCell());
        list = daoZone.read();
        ObservableList<Zone> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class ZoneListCell extends ListCell<Zone> {

        @Override
        protected void updateItem(Zone item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }

    public void setCountry(Country country) {
        list = daoZone.read(Zone_.country, country.getId());
        ObservableList<Zone> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    public void selectState(String state) {
        Zone selected = null;
        if (list != null) {
            for (Zone zone : list) {
                if (zone.getName().equalsIgnoreCase(state)) {
                    selected = zone;
                    break;
                }
            }
        }
        getSelectionModel().select(selected);
    }
}

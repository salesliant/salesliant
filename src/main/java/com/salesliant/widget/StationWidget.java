/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Station;
import com.salesliant.entity.Station_;
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
public class StationWidget extends ComboBox<Station> {

    private final BaseDao<Station> daoStation = new BaseDao<>(Station.class);

    public StationWidget() {
        List<Station> list = daoStation.read(Station_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> e1.getNumber().compareTo(e2.getNumber()))
                .collect(Collectors.toList());
        setButtonCell(new StationListCell());
        setCellFactory((ListView<Station> parm) -> new StationListCell());
        ObservableList<Station> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class StationListCell extends ListCell<Station> {

        @Override
        protected void updateItem(Station item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getNumber() + "  " + item.getDescription());
            }
        }
    }
}

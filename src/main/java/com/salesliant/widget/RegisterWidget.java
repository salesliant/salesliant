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
public class RegisterWidget extends ComboBox<Station> {

    private final BaseDao<Station> daoStation = new BaseDao<>(Station.class);

    public RegisterWidget() {
        setButtonCell(new StationListCell());
        setCellFactory((ListView<Station> parm) -> new StationListCell());
        List<Station> list = daoStation.read(Station_.store, Config.getStore()).stream()
                .sorted((e1, e2) -> Integer.compare(e1.getNumber(), e2.getNumber()))
                .collect(Collectors.toList());
        ObservableList<Station> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class StationListCell extends ListCell<Station> {

        @Override
        protected void updateItem(Station station, boolean empty) {
            super.updateItem(station, empty);
            if (station != null) {
                setText(station.getNumber() + "  " + station.getDescription());
            }
        }
    }
}

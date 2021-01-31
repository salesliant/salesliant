/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Country;
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
public class CountryWidget extends ComboBox<Country> {

    private final BaseDao<Country> daoCountry = new BaseDao(Country.class);

    public CountryWidget() {
        setButtonCell(new CountryListCell());
        setCellFactory((ListView<Country> parm) -> new CountryListCell());
        List<Country> list = daoCountry.read();
        ObservableList<Country> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class CountryListCell extends ListCell<Country> {

        @Override
        protected void updateItem(Country item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }

    public void setSelectedCountry(Country country) {
        int i = 0;
        if (country != null) {
            for (int j = 0; j < getItems().size(); j++) {
                if (getItems().get(j).getIsoCode3().equalsIgnoreCase(country.getIsoCode3())) {
                    i = j;
                    break;
                }
            }
        }
        getSelectionModel().select(i);
    }
}

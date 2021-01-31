/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.VendorTerm;
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
public class VendorTermWidget extends ComboBox<VendorTerm> {

    private final BaseDao<VendorTerm> daoVendorTerm = new BaseDao(VendorTerm.class);

    public VendorTermWidget() {
        setButtonCell(new VendorTermListCell());
        setCellFactory((ListView<VendorTerm> parm) -> new VendorTermListCell());
        List<VendorTerm> list = daoVendorTerm.read();
        ObservableList<VendorTerm> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class VendorTermListCell extends ListCell<VendorTerm> {

        @Override
        protected void updateItem(VendorTerm item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getCode());
            }
        }
    }
}

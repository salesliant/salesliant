/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.client.Config;
import com.salesliant.entity.Vendor;
import com.salesliant.entity.Vendor_;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.isEmpty;
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
public class VendorWidget extends ComboBox<Vendor> {

    private final BaseDao<Vendor> daoVendor = new BaseDao(Vendor.class);

    public VendorWidget() {
        setButtonCell(new VendorListCell());
        setCellFactory((ListView<Vendor> parm) -> new VendorListCell());
        List<Vendor> list = daoVendor.read(Vendor_.store, Config.getStore(), Vendor_.activeTag, true).stream()
                .sorted((e1, e2) -> {
                    String name1 = (!isEmpty(e1.getCompany()) ? e1.getCompany() : "");
                    String name2 = (!isEmpty(e2.getCompany()) ? e2.getCompany() : "");
                    return name1.compareToIgnoreCase(name2);
                }).collect(Collectors.toList());
        ObservableList<Vendor> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    public void selectVendor(Vendor vendor) {
        this.getItems().clear();
        this.getItems().add(vendor);
        this.getSelectionModel().select(vendor);
    }

    class VendorListCell extends ListCell<Vendor> {

        @Override
        protected void updateItem(Vendor item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getCompany());
            }
        }
    }
}

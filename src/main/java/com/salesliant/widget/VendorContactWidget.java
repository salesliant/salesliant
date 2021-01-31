/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorContact;
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
public class VendorContactWidget extends ComboBox<VendorContact> {

    public VendorContactWidget() {

    }

    public VendorContactWidget(Vendor vendor) {
        setButtonCell(new VendorContactListCell());
        setCellFactory((ListView<VendorContact> parm) -> new VendorContactListCell());
        List<VendorContact> list = vendor.getVendorContacts().stream()
                .sorted((e1, e2) -> e1.getContactName().compareTo(e2.getContactName()))
                .collect(Collectors.toList());
        ObservableList<VendorContact> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class VendorContactListCell extends ListCell<VendorContact> {

        @Override
        protected void updateItem(VendorContact item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getContactName());
            }
        }
    }

    public void setVendor(Vendor vendor) {
        setButtonCell(new VendorContactListCell());
        setCellFactory((ListView<VendorContact> parm) -> new VendorContactListCell());
        List<VendorContact> list = vendor.getVendorContacts().stream()
                .sorted((e1, e2) -> e1.getContactName().compareTo(e2.getContactName()))
                .collect(Collectors.toList());
        ObservableList<VendorContact> oList = FXCollections.observableList(list);
        setItems(oList);
    }
}

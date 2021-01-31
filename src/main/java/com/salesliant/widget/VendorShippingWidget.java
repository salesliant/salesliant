/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorShippingService;
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
public class VendorShippingWidget extends ComboBox<VendorShippingService> {

    public VendorShippingWidget() {

    }

    public VendorShippingWidget(Vendor vendor) {
        setButtonCell(new VendorShippingServiceListCell());
        setCellFactory((ListView<VendorShippingService> parm) -> new VendorShippingServiceListCell());
        List<VendorShippingService> list = vendor.getVendorShippingServices().stream()
                .sorted((e1, e2) -> e1.getDescription().compareTo(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<VendorShippingService> oList = FXCollections.observableList(list);
        setItems(oList);
        VendorShippingService vendorShippingService1 = vendor.getDefaultVendorShippingService();
        if (vendorShippingService1 != null && vendorShippingService1.getId() != null) {
            VendorShippingService vendorShippingService2 = oList.stream().filter(e -> vendorShippingService1.getId().equals(e.getId()))
                    .findAny()
                    .orElse(null);
            if (vendorShippingService2 != null) {
                getSelectionModel().select(vendorShippingService2);
            }
        }
    }

    class VendorShippingServiceListCell extends ListCell<VendorShippingService> {

        @Override
        protected void updateItem(VendorShippingService item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getDescription());
            }
        }
    }

    public void setVendor(Vendor vendor) {
        setButtonCell(new VendorShippingServiceListCell());
        setCellFactory((ListView<VendorShippingService> parm) -> new VendorShippingServiceListCell());
        List<VendorShippingService> list = vendor.getVendorShippingServices().stream()
                .sorted((e1, e2) -> e1.getDescription().compareTo(e2.getDescription()))
                .collect(Collectors.toList());
        ObservableList<VendorShippingService> oList = FXCollections.observableList(list);
        setItems(oList);
    }
}

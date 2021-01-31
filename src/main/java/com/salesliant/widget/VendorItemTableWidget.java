/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Vendor;
import com.salesliant.entity.VendorItem;
import com.salesliant.entity.VendorItem_;
import com.salesliant.util.FormattedTableCellFactory;
import com.salesliant.util.BaseDao;
import static com.salesliant.util.BaseUtil.stringCell;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 *
 * @author Lewis
 */
public final class VendorItemTableWidget extends GridPane {

    private final BaseDao<VendorItem> daoVendorItem = new BaseDao(VendorItem.class);
    protected TableView<VendorItem> fTableView = new TableView<>();
    List<BooleanProperty> selectedRowList = new ArrayList<>();
    List<VendorItem> fList = new ArrayList<>();

    public VendorItemTableWidget(Vendor vendor) {
        fList = daoVendorItem.read(VendorItem_.vendor, vendor);
        ObservableList<VendorItem> oList = FXCollections.observableList(fList);
        fTableView.setItems(oList);
        fList.forEach((item) -> {
            selectedRowList.add(new SimpleBooleanProperty());
        });

        TableColumn selectCol = new TableColumn("Select");
        selectCol.setEditable(true);

        selectCol.setCellValueFactory(new Callback<CellDataFeatures<VendorItem, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<VendorItem, Boolean> t) {
                VendorItem rowData = t.getValue();
                int rowIndex = t.getTableView().getItems().indexOf(rowData);
                return selectedRowList.get(rowIndex);
            }
        });
        selectCol.setCellFactory(CheckBoxTableCell.forTableColumn(selectCol));
        selectCol.setEditable(true);

        TableColumn<VendorItem, String> vendorSKUCol = new TableColumn<>("Vendor SKU");
        vendorSKUCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.vendorItemLookUpCode.getName()));
        vendorSKUCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        vendorSKUCol.setPrefWidth(100);
        vendorSKUCol.setEditable(false);

        TableColumn<VendorItem, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        skuCol.setCellFactory(new FormattedTableCellFactory(Pos.CENTER));
        skuCol.setPrefWidth(100);
        skuCol.setEditable(false);

        TableColumn<VendorItem, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<VendorItem, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER));
        descriptionCol.setPrefWidth(230);
        descriptionCol.setEditable(false);

        TableColumn<VendorItem, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.cost.getName()));
        costCol.setCellFactory(stringCell(Pos.CENTER));
        costCol.setPrefWidth(90);
        costCol.setEditable(false);

        TableColumn<VendorItem, String> lastReceviedCol = new TableColumn<>("Last Received");
        lastReceviedCol.setCellValueFactory(new PropertyValueFactory<>(VendorItem_.lastUpdated.getName()));
        lastReceviedCol.setCellFactory(stringCell(Pos.CENTER));
        lastReceviedCol.setPrefWidth(80);
        lastReceviedCol.setEditable(false);

        fTableView.getColumns().add(selectCol);
        fTableView.getColumns().add(vendorSKUCol);
        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(lastReceviedCol);
        fTableView.setPrefSize(665, 300);
        fTableView.setEditable(true);

        this.add(fTableView, 0, 0);
    }

    public List<VendorItem> getSelectedItems() {
        List<VendorItem> list = new ArrayList<>();
        if (fList.size() >= 1) {
            int i = 0;
            for (BooleanProperty b : selectedRowList) {
                if (b.getValue()) {
                    list.add(fList.get(i));
                }
                i++;
            }
        }
        return list;
    }
}

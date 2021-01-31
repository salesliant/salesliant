package com.salesliant.widget;

import com.salesliant.entity.AccountReceivable;
import com.salesliant.entity.AccountReceivable_;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.CheckBoxCell;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DBConstants;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class CustomerCreditWidget extends BaseListUI<AccountReceivable> {

    private final ObservableSet<AccountReceivable> selectedAccountReceivables = FXCollections.observableSet();
    private final TableColumn<AccountReceivable, AccountReceivable> selectedCol = new TableColumn<>("");

    public CustomerCreditWidget(List<AccountReceivable> list) {
        mainView = createMainView();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Credit:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        selectedCol.setCellValueFactory((TableColumn.CellDataFeatures<AccountReceivable, AccountReceivable> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));

        selectedCol.setCellFactory((TableColumn<AccountReceivable, AccountReceivable> param) -> new CheckBoxCell(selectedAccountReceivables));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<AccountReceivable, String> invoiceNumberCol = new TableColumn<>("Invoice No.");
        invoiceNumberCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.invoiceNumber.getName()));
        invoiceNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceNumberCol.setPrefWidth(110);

        TableColumn<AccountReceivable, String> invoiceDateCol = new TableColumn<>("Date");
        invoiceDateCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.dateProcessed.getName()));
        invoiceDateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        invoiceDateCol.setPrefWidth(90);

        TableColumn<AccountReceivable, String> balanceCol = new TableColumn<>("Balance");
        balanceCol.setCellValueFactory(new PropertyValueFactory<>(AccountReceivable_.balanceAmount.getName()));
        balanceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        balanceCol.setPrefWidth(80);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(invoiceNumberCol);
        fTableView.getColumns().add(invoiceDateCol);
        fTableView.getColumns().add(balanceCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        fTableView.setPrefHeight(140);
        fTableView.setEditable(true);

        mainPane.add(fTableView, 0, 2);
        return mainPane;
    }

    public List<AccountReceivable> getSelectedItems() {
        ArrayList<AccountReceivable> list = new ArrayList<>();
        selectedAccountReceivables.stream().forEach(e -> {
            if (e.getAccountReceivableType().equalsIgnoreCase(DBConstants.TYPE_ACCOUNT_RECEIVABLE_TRANSACTION_RETURN_CREDIT)) {
                e.setPaidAmount(e.getBalanceAmount().negate());
            }
            list.add(e);
        });
        return list;
    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

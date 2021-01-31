package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Cheque;
import com.salesliant.entity.Cheque_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class CheckListUI extends BaseListUI<Cheque> {

    private final BaseDao<Cheque> daoCheque = new BaseDao<>(Cheque.class);

    public CheckListUI() {
        mainView = createMainView();
        loadData();
    }

    private void loadData() {
        List<Cheque> list = daoCheque.readOrderBy(Cheque_.store, Config.getStore(), Cheque_.checkDate, AppConstants.ORDER_BY_DESC);
        fTableView.getItems().setAll(FXCollections.observableList(list));
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Check:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Cheque, String> checkNumberCol = new TableColumn<>("Check Number");
        checkNumberCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.checkNumber.getName()));
        checkNumberCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        checkNumberCol.setPrefWidth(100);

        TableColumn<Cheque, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.customerName.getName()));
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(150);

        TableColumn<Cheque, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.checkDate.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        dateCol.setPrefWidth(100);

        TableColumn<Cheque, String> checkDescriptionCol = new TableColumn<>("Description");
        checkDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.description.getName()));
        checkDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        checkDescriptionCol.setPrefWidth(100);

        TableColumn<Cheque, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(Cheque_.checkAmount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        amountCol.setPrefWidth(100);

        TableColumn<Cheque, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((CellDataFeatures<Cheque, String> p) -> {
            if (p.getValue().getCheckType() != null
                    && p.getValue().getCheckType().equalsIgnoreCase(DBConstants.TYPE_CHECK_ACCOUNT_RECEIVABLE_PAYMENT)) {
                return new SimpleStringProperty("AR Payment");
            } else if (p.getValue().getCheckType() != null
                    && p.getValue().getCheckType().equalsIgnoreCase(DBConstants.TYPE_CHECK_INVOICE)) {
                return new SimpleStringProperty("Invoice Payment");
            } else if (p.getValue().getCheckType() != null
                    && p.getValue().getCheckType().equalsIgnoreCase(DBConstants.TYPE_CHECK_DEPOSIT)) {
                return new SimpleStringProperty("Deposit");
            } else if (p.getValue().getCheckType() != null
                    && p.getValue().getCheckType().equalsIgnoreCase(DBConstants.TYPE_CHECK_ORDER)) {
                return new SimpleStringProperty("Order");
            } else if (p.getValue().getCheckType() != null
                    && p.getValue().getCheckType().equalsIgnoreCase(DBConstants.TYPE_CHECK_BAD)) {
                return new SimpleStringProperty("Bad");
            } else {
                return new SimpleStringProperty("");
            }
        });
        typeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        typeCol.setPrefWidth(100);

        fTableView.getColumns().add(dateCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(checkNumberCol);
        fTableView.getColumns().add(checkDescriptionCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getColumns().add(typeCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        setTableWidth(fTableView);
        fTableView.setPrefHeight(350);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createCloseButtonPane() {
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }
}

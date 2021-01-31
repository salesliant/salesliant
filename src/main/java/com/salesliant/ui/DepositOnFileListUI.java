package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Customer;
import com.salesliant.entity.Deposit;
import com.salesliant.entity.Deposit_;
import com.salesliant.report.DepositListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class DepositOnFileListUI extends BaseListUI<Deposit> {

    private final BaseDao<Deposit> daoDeposit = new BaseDao<>(Deposit.class);
    private static final Logger LOGGER = Logger.getLogger(DepositOnFileListUI.class.getName());

    public DepositOnFileListUI() {
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        List<Deposit> depositList = daoDeposit.readOrderBy(Deposit_.store, Config.getStore(), Deposit_.status, DBConstants.STATUS_OPEN, Deposit_.dateCreated, AppConstants.ORDER_BY_DESC)
                .stream()
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(depositList);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (!fEntityList.isEmpty()) {
                    DepositListReportLayout layout = new DepositListReportLayout(fEntityList);
                    try {
                        JasperReportBuilder report = layout.build();
                        report.show(false);
                    } catch (DRException ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                }
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);

        Label tableLbl = new Label("Open Deposit List:");
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<Deposit, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((TableColumn.CellDataFeatures<Deposit, String> p) -> {
            if (p.getValue().getCustomer() != null) {
                Customer customer = p.getValue().getCustomer();
                String name;
                if (customer.getCompany() != null && !customer.getCompany().isEmpty()) {
                    name = customer.getCompany();
                } else {
                    name = !isEmpty(customer.getFirstName()) ? customer.getFirstName() : ""
                            + (!isEmpty(customer.getFirstName()) ? " " : "")
                            + (!isEmpty(customer.getLastName()) ? customer.getLastName() : "");
                }
                return new SimpleStringProperty(name);
            } else {
                return null;
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(250);

        TableColumn<Deposit, String> dateDepositCol = new TableColumn<>("Date Deposit");
        dateDepositCol.setCellValueFactory(new PropertyValueFactory<>(Deposit_.dateCreated.getName()));
        dateDepositCol.setCellFactory(stringCell(Pos.CENTER));
        dateDepositCol.setPrefWidth(150);

        TableColumn<Deposit, String> soCol = new TableColumn<>("Order Number");
        soCol.setCellValueFactory((TableColumn.CellDataFeatures<Deposit, String> p) -> {
            if (p.getValue() != null && p.getValue().getSalesOrder() != null && p.getValue().getSalesOrder().getSalesOrderNumber() != null) {
                return new SimpleStringProperty(p.getValue().getSalesOrder().getSalesOrderNumber().toString());
            } else {
                return null;
            }
        });
        soCol.setCellFactory(stringCell(Pos.CENTER));
        soCol.setPrefWidth(150);

        TableColumn<Deposit, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(Deposit_.amount.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER));
        amountCol.setPrefWidth(150);

        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(dateDepositCol);
        fTableView.getColumns().add(soCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(tableLbl, 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;

    }
}

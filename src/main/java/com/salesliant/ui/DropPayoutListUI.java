package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.DropPayout;
import com.salesliant.entity.DropPayout_;
import com.salesliant.report.DropPayoutListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.math.BigDecimal;
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

public class DropPayoutListUI extends BaseListUI<DropPayout> {

    private final BaseDao<DropPayout> daoDropPayout = new BaseDao<>(DropPayout.class);
    private static final Logger LOGGER = Logger.getLogger(DropPayoutListUI.class.getName());

    public DropPayoutListUI() {
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        List<DropPayout> dropPayoutList = daoDropPayout.readOrderBy(DropPayout_.store, Config.getStore(), DropPayout_.dateCreated, AppConstants.ORDER_BY_DESC)
                .stream()
                .collect(Collectors.toList());
        fEntityList = FXCollections.observableList(dropPayoutList);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (!fEntityList.isEmpty()) {
                    DropPayoutListReportLayout layout = new DropPayoutListReportLayout(fEntityList);
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

        Label tableLbl = new Label("Drop Payout List:");
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        TableColumn<DropPayout, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory((TableColumn.CellDataFeatures<DropPayout, String> p) -> {
            if (p.getValue().getAmount() != null && p.getValue().getAmount().compareTo(BigDecimal.ZERO) >= 0) {
                return new SimpleStringProperty("Out");
            } else {
                return new SimpleStringProperty("In");
            }
        });
        typeCol.setCellFactory(stringCell(Pos.CENTER));
        typeCol.setPrefWidth(100);

        TableColumn<DropPayout, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(DropPayout_.dateCreated.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER));
        dateCol.setPrefWidth(120);

        TableColumn<DropPayout, String> amountCol = new TableColumn<>("Is Check");
        amountCol.setCellValueFactory(new PropertyValueFactory<>(DropPayout_.checkTag.getName()));
        amountCol.setCellFactory(stringCell(Pos.CENTER));
        amountCol.setPrefWidth(80);

        TableColumn<DropPayout, String> isCheckCol = new TableColumn<>("Amount");
        isCheckCol.setCellValueFactory(new PropertyValueFactory<>(DropPayout_.amount.getName()));
        isCheckCol.setCellFactory(stringCell(Pos.CENTER));
        isCheckCol.setPrefWidth(100);

        TableColumn<DropPayout, String> employeeCol = new TableColumn<>("Employee");
        employeeCol.setCellValueFactory((TableColumn.CellDataFeatures<DropPayout, String> p) -> {
            if (p.getValue() != null && p.getValue().getEmployee() != null) {
                String name = "";
                name = name + (!isEmpty(p.getValue().getEmployee().getFirstName()) ? p.getValue().getEmployee().getFirstName() : "")
                        + (!isEmpty(p.getValue().getEmployee().getFirstName()) ? " " : "")
                        + (!isEmpty(p.getValue().getEmployee().getLastName()) ? p.getValue().getEmployee().getLastName() : "");
                return new SimpleStringProperty(name);
            } else {
                return null;
            }
        });
        employeeCol.setCellFactory(stringCell(Pos.CENTER));
        employeeCol.setPrefWidth(150);

        TableColumn<DropPayout, String> noteCol = new TableColumn<>("Note");
        noteCol.setCellValueFactory(new PropertyValueFactory<>(DropPayout_.note.getName()));
        noteCol.setCellFactory(stringCell(Pos.CENTER));
        noteCol.setPrefWidth(350);

        fTableView.getColumns().add(typeCol);
        fTableView.getColumns().add(amountCol);
        fTableView.getColumns().add(isCheckCol);
        fTableView.getColumns().add(dateCol);
        fTableView.getColumns().add(employeeCol);
        fTableView.getColumns().add(noteCol);

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

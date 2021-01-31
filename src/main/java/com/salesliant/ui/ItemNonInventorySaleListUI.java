package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemLog_;
import com.salesliant.report.ItemNonInventorySalesListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemNonInventorySaleListUI extends BaseListUI<ItemLog> {

    private final BaseDao<ItemLog> daoItemLog = new BaseDao<>(ItemLog.class);
    private LocalDateTime fFrom;
    private LocalDateTime fTo;
    private final Label rangeLabel = new Label();
    private final DatePicker fFromDatePicker = new DatePicker();
    private final DatePicker fToDatePicker = new DatePicker();
    private static final Logger LOGGER = Logger.getLogger(ItemNonInventorySaleListUI.class.getName());

    public ItemNonInventorySaleListUI() {
        setDefaultDate();
        loadData();
        mainView = createMainView();
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<ItemLog> list = daoItemLog.readBetweenDate(ItemLog_.store, Config.getStore(), ItemLog_.transferType, DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_INVOICE, ItemLog_.dateCreated, fFrom, fTo)
                    .stream()
                    .filter(e -> e.getInvoice() != null && e.getBeforeQuantity() != null && e.getAfterQuantity() != null && e.getBeforeQuantity().compareTo(e.getAfterQuantity()) <= 0)
                    .collect(Collectors.toList());
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            updateRange();
            fTableView.setPlaceholder(null);
        });
    }

    private void setDefaultDate() {
        Timestamp fromTimestamp = daoItemLog.findMinTimestamp(ItemLog_.store, Config.getStore(), ItemLog_.transferType, DBConstants.TYPE_ITEMLOG_TRANSFERTYPE_INVOICE, ItemLog_.dateCreated);
        if (fromTimestamp != null) {
            fFrom = fromTimestamp.toLocalDateTime();
        } else {
            fFrom = (new Timestamp((new Date()).getTime())).toLocalDateTime();
        }
        fTo = (new Timestamp((new Date()).getTime())).toLocalDateTime();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_SELECT:
                fTableView.setPlaceholder(null);
                fInputDialog = createSelectCancelUIDialog(createRangePane(fFromDatePicker, fToDatePicker), "Select Range");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fFrom = fFromDatePicker.getValue().atTime(0, 0, 0, 0);
                    fTo = fToDatePicker.getValue().atTime(LocalTime.MAX);
                    fTableView.setItems(FXCollections.observableArrayList());
                    loadData();
                });
                cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    handleAction(AppConstants.ACTION_CLOSE_DIALOG);
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_PRINT:
                if (!fEntityList.isEmpty()) {
                    ItemNonInventorySalesListReportLayout layout = new ItemNonInventorySalesListReportLayout(fEntityList, fFrom, fTo);
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

        TableColumn<ItemLog, String> itemSKUCol = new TableColumn<>("Item SKU");
        itemSKUCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemLog, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return null;
            }
        });
        itemSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemSKUCol.setPrefWidth(100);

        TableColumn<ItemLog, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemLog, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getDescription() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return null;
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(300);

        TableColumn<ItemLog, String> invoiceCol = new TableColumn<>("Invoice");
        invoiceCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.transactionNumber.getName()));
        invoiceCol.setCellFactory(stringCell(Pos.CENTER));
        invoiceCol.setPrefWidth(110);

        TableColumn<ItemLog, String> customerCol = new TableColumn<>("Customer");
        customerCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemLog, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getCustomerName() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getCustomerName());
            } else {
                return null;
            }
        });
        customerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        customerCol.setPrefWidth(130);

        TableColumn<ItemLog, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.dateCreated.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        dateCol.setPrefWidth(130);

        TableColumn<ItemLog, String> itemQtyCol = new TableColumn<>("Before Qty");
        itemQtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.beforeQuantity.getName()));
        itemQtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        itemQtyCol.setPrefWidth(90);

        TableColumn<ItemLog, String> qtyCol = new TableColumn<>("After Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.afterQuantity.getName()));
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(90);

        fTableView.getColumns().add(itemSKUCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(invoiceCol);
        fTableView.getColumns().add(customerCol);
        fTableView.getColumns().add(dateCol);
        fTableView.getColumns().add(itemQtyCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(rangeLabel, 0, 1);
        mainPane.add(fTableView, 0, 2);
        mainPane.add(createClonePrintExportCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private HBox createClonePrintExportCloseButtonPane() {
        Button dateRangerButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, "Select Date Range", fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print Report", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(dateRangerButton, printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;

    }

    private void updateRange() {
        Date fromDate = Date.from(fFrom.atZone(ZoneId.systemDefault()).toInstant());
        Date toDate = Date.from(fTo.atZone(ZoneId.systemDefault()).toInstant());
        rangeLabel.setText("Date Range: " + getString(fromDate) + " - " + getString(toDate));
    }
}

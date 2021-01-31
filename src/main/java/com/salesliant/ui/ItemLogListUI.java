package com.salesliant.ui;

import com.salesliant.entity.Item;
import com.salesliant.entity.ItemLog;
import com.salesliant.entity.ItemLog_;
import com.salesliant.entity.Item_;
import com.salesliant.report.ItemLogListReportLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.ItemSearchField;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemLogListUI extends BaseListUI<Item> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final ItemSearchField searchField = new ItemSearchField();
    private final RadioButton activeBtn = new RadioButton("Active  ");
    private final RadioButton inActiveBtn = new RadioButton("Inactive  ");
    private final ToggleGroup toggleGroup = new ToggleGroup();
    private TableView<ItemLog> fItemLogTableView = new TableView<>();
    private ObservableList<ItemLog> fItemLogList;
    private static final Logger LOGGER = Logger.getLogger(ItemLogListUI.class.getName());

    public ItemLogListUI() {
        mainView = createMainView();
        loadData();
        fTableView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fEntity = fTableView.getSelectionModel().getSelectedItem();
                        List<ItemLog> aList = fEntity.getItemLogs()
                                .stream()
                                .sorted((e1, e2) -> e1.getDateCreated().compareTo(e2.getDateCreated()))
                                .collect(Collectors.toList());
                        fItemLogList = FXCollections.observableList(aList);
                        fItemLogTableView.setItems(fItemLogList);
                    } else {
                        fItemLogList.clear();
                    }
                });
        Platform.runLater(() -> searchField.requestFocus());
    }

    private void loadData() {
        fTableView.setPlaceholder(lblLoading);
        Platform.runLater(() -> {
            List<Item> list;
            if (toggleGroup.getSelectedToggle().equals(activeBtn)) {
                list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.activeTag, true, Item_.trackQuantity, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            } else {
                list = daoItem.readOrderBy(Item_.itemType, DBConstants.ITEM_TYPE_STANDARD, Item_.activeTag, false, Item_.trackQuantity, true, Item_.itemLookUpCode, AppConstants.ORDER_BY_ASC);
            }
            fEntityList = FXCollections.observableList(list);
            fTableView.setItems(fEntityList);
            searchField.setTableView(fTableView);
            fTableView.setPlaceholder(null);
        });
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_PRINT:
                if (!fItemLogList.isEmpty()) {
                    ItemLogListReportLayout layout = new ItemLogListReportLayout(fItemLogList);
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
        mainPane.setPadding(new Insets(1));
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(3);
        mainPane.setVgap(3.0);
        mainPane.add(createTopPane(), 0, 1, 2, 1);

        TableColumn<Item, String> lookUpCodeCol = new TableColumn<>("SKU");
        lookUpCodeCol.setCellValueFactory(new PropertyValueFactory<>(Item_.itemLookUpCode.getName()));
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        lookUpCodeCol.setPrefWidth(150);

        TableColumn<Item, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(Item_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(460);

        TableColumn<Item, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            if (p.getValue() != null && p.getValue().getPrice1() != null) {
                return new SimpleStringProperty(getString(p.getValue().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        priceCol.setPrefWidth(150);

        TableColumn<Item, String> qtyCol = new TableColumn<>("Stock");
        qtyCol.setCellValueFactory((TableColumn.CellDataFeatures<Item, String> p) -> {
            return new SimpleStringProperty(getString(getQuantity(p.getValue())));
        });
        qtyCol.setCellFactory(stringCell(Pos.CENTER_RIGHT));
        qtyCol.setPrefWidth(150);

        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(260);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);

        TableColumn<ItemLog, String> itemLogSKUCol = new TableColumn<>("Item SKU");
        itemLogSKUCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemLog, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return null;
            }
        });
        itemLogSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemLogSKUCol.setPrefWidth(110);

        TableColumn<ItemLog, String> itemLogDescriptionCol = new TableColumn<>("Type");
        itemLogDescriptionCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.description.getName()));
        itemLogDescriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemLogDescriptionCol.setPrefWidth(140);

        TableColumn<ItemLog, String> itemLogInvoiceCol = new TableColumn<>("Number");
        itemLogInvoiceCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.transactionNumber.getName()));
        itemLogInvoiceCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemLogInvoiceCol.setPrefWidth(120);

        TableColumn<ItemLog, String> itemLogCustomerCol = new TableColumn<>("Customer");
        itemLogCustomerCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemLog, String> p) -> {
            if (p.getValue().getInvoice() != null && p.getValue().getInvoice().getCustomerName() != null) {
                return new SimpleStringProperty(p.getValue().getInvoice().getCustomerName());
            } else {
                return null;
            }
        });
        itemLogCustomerCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemLogCustomerCol.setPrefWidth(230);

        TableColumn<ItemLog, String> itemLogDateCol = new TableColumn<>("Date");
        itemLogDateCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.dateCreated.getName()));
        itemLogDateCol.setCellFactory(stringCell(Pos.CENTER));
        itemLogDateCol.setPrefWidth(130);

        TableColumn<ItemLog, String> itemLogItemQtyCol = new TableColumn<>("Before Qty");
        itemLogItemQtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.beforeQuantity.getName()));
        itemLogItemQtyCol.setCellFactory(stringCell(Pos.CENTER));
        itemLogItemQtyCol.setPrefWidth(90);

        TableColumn<ItemLog, String> itemLogQtyCol = new TableColumn<>("After Qty");
        itemLogQtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLog_.afterQuantity.getName()));
        itemLogQtyCol.setCellFactory(stringCell(Pos.CENTER));
        itemLogQtyCol.setPrefWidth(90);

        fItemLogTableView.getColumns().add(itemLogSKUCol);
        fItemLogTableView.getColumns().add(itemLogDescriptionCol);
        fItemLogTableView.getColumns().add(itemLogInvoiceCol);
        fItemLogTableView.getColumns().add(itemLogCustomerCol);
        fItemLogTableView.getColumns().add(itemLogDateCol);
        fItemLogTableView.getColumns().add(itemLogItemQtyCol);
        fItemLogTableView.getColumns().add(itemLogQtyCol);
        fItemLogTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fItemLogTableView.setPrefHeight(250);
        setTableWidth(fItemLogTableView);
        mainPane.add(fItemLogTableView, 0, 3);

        mainPane.add(createPrintCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private HBox createTopPane() {
        HBox leftButtonBox = new HBox();
        searchField.setPrefWidth(400);
        leftButtonBox.getChildren().addAll(searchField);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);
        activeBtn.setSelected(true);
        inActiveBtn.setSelected(false);
        activeBtn.setToggleGroup(toggleGroup);
        inActiveBtn.setToggleGroup(toggleGroup);
        activeBtn.setOnAction((ActionEvent e) -> {
            loadData();
            searchField.setText("");
            searchField.requestFocus();
        });
        inActiveBtn.setOnAction((ActionEvent e) -> {
            loadData();
            searchField.setText("");
            searchField.requestFocus();
        });
        HBox toggleBox = new HBox();
        toggleBox.getChildren().addAll(activeBtn, inActiveBtn);
        toggleBox.setAlignment(Pos.CENTER_RIGHT);
        toggleBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, toggleBox);
        return buttonGroup;
    }

    private HBox createPrintCloseButtonPane() {
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT, AppConstants.ACTION_PRINT, "Print Report", fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(printButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);

        return buttonGroup;

    }

}

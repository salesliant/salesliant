package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.ItemLabel;
import com.salesliant.entity.ItemLabel_;
import com.salesliant.entity.LabelType;
import com.salesliant.report.LabelLayout;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.integerFilter;
import static com.salesliant.util.BaseUtil.isNumeric;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.CheckBoxCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.ItemTableWidget;
import com.salesliant.widget.LabelTypeWidget;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;

public class ItemLabelListUI extends BaseListUI<ItemLabel> {

    private final BaseDao<ItemLabel> daoItemLabel = new BaseDao<>(ItemLabel.class);
    private final DataUI dataUI = new DataUI(ItemLabel.class);
    private final GridPane fEditPane;
    private final LabelTypeWidget fLabelTypeCombo = new LabelTypeWidget();
    private final ObservableSet<ItemLabel> selectedItems = FXCollections.observableSet();
    private int fRow = 1, fCol = 1;
    private final TextField initRowField = new TextField();
    private final TextField initColField = new TextField();
    private static final Logger LOGGER = Logger.getLogger(ItemLabelListUI.class.getName());

    public ItemLabelListUI() {
        mainView = createMainView();
        List<ItemLabel> list = daoItemLabel.read(ItemLabel_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
        fLabelTypeCombo.getSelectionModel().select(0);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                ItemTableWidget itemTableWidget = new ItemTableWidget();
                fInputDialog = createSelectCancelUIDialog(itemTableWidget.getView(), "Item");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemTableWidget.getSelectedItems().size() >= 1) {
                        itemTableWidget.getSelectedItems().forEach((item) -> {
                            itemTableWidget.getSelectedItems().forEach(e -> {
                                ItemLabel newItemLabel = new ItemLabel();
                                newItemLabel.setDateAdded(new Date());
                                newItemLabel.setQuantity(1);
                                newItemLabel.setItem(e);
                                newItemLabel.setStore(Config.getStore());
                                daoItemLabel.insert(newItemLabel);
                                fTableView.getItems().add(newItemLabel);
                            });
                            itemTableWidget.getSelectedItems().clear();
                            fTableView.refresh();
                            fTableView.getSelectionModel().select(fTableView.getItems().size() - 1);
                            fTableView.scrollTo(fTableView.getItems().size() - 1);
                        });
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.show();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Item Label");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoItemLabel.update(fEntity);
                            if (daoItemLabel.getErrorMessage() == null) {
                                fTableView.refresh();
                                fTableView.getSelectionModel().select(fEntity);
                            } else {
                                lblWarning.setText(daoItemLabel.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(ItemLabel_.quantity).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (!selectedItems.isEmpty()) {
                    showConfirmDialog("Are you sure to delete the seleted entries?", (ActionEvent e) -> {
                        selectedItems.forEach(il -> {
                            daoItemLabel.delete(il);
                            fTableView.getItems().remove(il);
                        });
                        selectedItems.clear();
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                        fTableView.refresh();
                    });
                }
                break;
            case AppConstants.ACTION_SELECT_ALL:
                selectedItems.clear();
                fTableView.getItems().forEach(itemLabel -> selectedItems.add(itemLabel));
                fTableView.refresh();
                break;
            case AppConstants.ACTION_UN_SELECT_ALL:
                selectedItems.clear();
                break;
            case AppConstants.ACTION_PRINT_LABEL:
                if (!selectedItems.isEmpty()) {
                    if (isNumeric(initRowField.getText())) {
                        fRow = Integer.parseInt(initRowField.getText());
                    }
                    if (isNumeric(initColField.getText())) {
                        fCol = Integer.parseInt(initColField.getText());
                    }
                    if (fLabelTypeCombo.getSelectionModel().getSelectedItem() != null) {
                        LabelType labelType = fLabelTypeCombo.getSelectionModel().getSelectedItem();
                        LabelLayout layout = new LabelLayout(new ArrayList<>(selectedItems), fRow, fCol, labelType);
                        try {
                            JasperReportBuilder report = layout.build();
                            report.setPageFormat(PageType.valueOf(labelType.getPageType()));
                            report.show(false);
                            selectedItems.forEach(e -> {
                                daoItemLabel.delete(e);
                                fTableView.getItems().remove(e);
                            });
                            selectedItems.clear();
                            fTableView.refresh();
                        } catch (DRException ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    }
                }

                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label ItemLabelLbl = new Label("List of Labesl:");
        mainPane.add(ItemLabelLbl, 0, 0);
        GridPane.setHalignment(ItemLabelLbl, HPos.LEFT);

        TableColumn<ItemLabel, ItemLabel> selectedCol = new TableColumn<>("");
        selectedCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemLabel, ItemLabel> data) -> new ReadOnlyObjectWrapper<>(data.getValue()));
        selectedCol.setCellFactory((TableColumn<ItemLabel, ItemLabel> param) -> new CheckBoxCell(selectedItems));
        selectedCol.setEditable(true);
        selectedCol.setPrefWidth(26);
        selectedCol.setSortable(false);

        TableColumn<ItemLabel, String> lookUpCodeCol = new TableColumn<>("SKU");
        lookUpCodeCol.setCellValueFactory((CellDataFeatures<ItemLabel, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        lookUpCodeCol.setCellFactory(stringCell(Pos.CENTER));
        lookUpCodeCol.setPrefWidth(120);

        TableColumn<ItemLabel, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((CellDataFeatures<ItemLabel, String> p) -> {
            if (p.getValue().getItem() != null && p.getValue().getItem().getDescription() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(250);

        TableColumn<ItemLabel, String> dateCol = new TableColumn<>("Added Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>(ItemLabel_.dateAdded.getName()));
        dateCol.setCellFactory(stringCell(Pos.CENTER));
        dateCol.setPrefWidth(150);

        TableColumn<ItemLabel, String> qtyCol = new TableColumn<>("Quantity");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemLabel_.quantity.getName()));
        qtyCol.setCellFactory(stringCell(Pos.CENTER));
        qtyCol.setPrefWidth(150);

        fTableView.getColumns().add(selectedCol);
        fTableView.getColumns().add(lookUpCodeCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(dateCol);
        fTableView.getColumns().add(qtyCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        fLabelTypeCombo.setPrefWidth(140);
        Label initRowLabel = new Label("Start row: ");
        initRowField.setTextFormatter(new TextFormatter<>(integerFilter));
        initColField.setTextFormatter(new TextFormatter<>(integerFilter));
        initRowField.setPrefWidth(46);
        initRowField.setText("1");
        Label initColLabel = new Label("Start col: ");
        initColField.setPrefWidth(46);
        initColField.setText("1");
        Label typeLabel = new Label("Label Type: ");
        HBox startBox = new HBox();
        HBox typeBox = new HBox();
        startBox.setSpacing(3);
        typeBox.setSpacing(3);
        startBox.getChildren().addAll(initRowLabel, initRowField, initColLabel, initColField);
        typeBox.getChildren().addAll(typeLabel, fLabelTypeCombo);
        mainPane.add(fTableView, 0, 1, 2, 1);
        mainPane.add(startBox, 0, 2);
        mainPane.add(createTagUnTagPrintButtonGroup(), 1, 2);
        mainPane.add(typeBox, 0, 3);
        mainPane.add(createNewEditPurgeCloseButtonGroup(), 1, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        add(editPane, "Qty:*", dataUI.createTextField(ItemLabel_.quantity), fListener, 80.0, 0);
        editPane.add(lblWarning, 0, 1, 2, 1);

        return editPane;
    }

    private HBox createNewEditPurgeCloseButtonGroup() {
        Button newButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button editButton = ButtonFactory.getButton(ButtonFactory.BUTTON_EDIT, AppConstants.ACTION_EDIT, "Change Quantity", fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(newButton, editButton, deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    private HBox createTagUnTagPrintButtonGroup() {
        Button selectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT_ALL, AppConstants.ACTION_SELECT_ALL, fHandler);
        Button unSelectAllButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UN_SELECT_ALL, AppConstants.ACTION_UN_SELECT_ALL, fHandler);
        Button printButton = ButtonFactory.getButton(ButtonFactory.BUTTON_PRINT_LABEL, AppConstants.ACTION_PRINT_LABEL, fHandler);
        selectAllButton.setPrefWidth(105);
        unSelectAllButton.setPrefWidth(105);
        printButton.setPrefWidth(105);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(selectAllButton, unSelectAllButton, printButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null && saveBtn != null) {
            saveBtn.setDisable(dataUI.getTextField(ItemLabel_.quantity).getText().trim().isEmpty());
        }
    }
}

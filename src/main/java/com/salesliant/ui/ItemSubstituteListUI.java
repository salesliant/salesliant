package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.Substitute;
import com.salesliant.entity.Substitute_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getNumberFormat;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ItemSubstituteListUI extends BaseListUI<Substitute> {

    private final BaseDao<Substitute> daoSubstitute = new BaseDao<>(Substitute.class);
    private final TextField itemTextField = new TextField(), subTextField = new TextField();
    private Item fItem, fSubItem;
    private final Button addButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
    private final static String ITEM_SELECT = "Item Select";
    private final static String SUB_SELECT = "Sub Select";
    private static final Logger LOGGER = Logger.getLogger(ItemSubstituteListUI.class.getName());

    public ItemSubstituteListUI() {
        mainView = createMainView();
        List<Substitute> list = daoSubstitute.read(Substitute_.store, Config.getStore());
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        dialogTitle = "Substitute";
        addButton.setDisable(true);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Substitute();
                fEntity.setStore(Config.getStore());
                fEntity.setItem(fItem);
                fEntity.setSubstituteItem(fSubItem);
                daoSubstitute.insert(fEntity);
                if (daoSubstitute.getErrorMessage() == null) {
                    fEntityList.add(fEntity);
                    fTableView.refresh();
                    fTableView.getSelectionModel().select(fEntity);
                    fItem = null;
                    fSubItem = null;
                    itemTextField.setText("");
                    subTextField.setText("");
                    addButton.setDisable(true);
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoSubstitute.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
            case ITEM_SELECT:
                ItemListUI itemListUI = new ItemListUI();
                itemListUI.actionButtonBox.setDisable(true);
                TableView<Item> itemTable = itemListUI.getTableView();
                fInputDialog = createSelectCancelUIDialog(itemListUI.getView(), "Item");
                selectBtn.setDisable(true);
                itemTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fItem = observable.getValue();
                        if (fItem != null) {
                            selectBtn.setDisable(false);
                        } else {
                            selectBtn.setDisable(true);
                        }
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fItem = itemTable.getSelectionModel().getSelectedItem();
                    itemTextField.setText(fItem.getItemLookUpCode());
                });
                fInputDialog.showDialog();
                break;
            case SUB_SELECT:
                ItemListUI subItemListUI = new ItemListUI();
                subItemListUI.actionButtonBox.setDisable(true);
                TableView<Item> subItemTable = subItemListUI.getTableView();
                fInputDialog = createSelectCancelUIDialog(subItemListUI.getView(), "Item");
                selectBtn.setDisable(true);
                subItemTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        fSubItem = observable.getValue();
                        if (fSubItem != null) {
                            selectBtn.setDisable(false);
                        } else {
                            selectBtn.setDisable(true);
                        }
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    fSubItem = subItemTable.getSelectionModel().getSelectedItem();
                    subTextField.setText(fSubItem.getItemLookUpCode());
                });
                fInputDialog.showDialog();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label SubstituteLbl = new Label("List of Substitute:");
        mainPane.add(SubstituteLbl, 0, 1);
        GridPane.setHalignment(SubstituteLbl, HPos.LEFT);

        TableColumn<Substitute, String> itemSKUCol = new TableColumn<>("Item SKU");
        itemSKUCol.setCellValueFactory((CellDataFeatures<Substitute, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        itemSKUCol.setCellFactory(stringCell(Pos.CENTER));
        itemSKUCol.setPrefWidth(100);

        TableColumn<Substitute, String> itemDescriptionCol = new TableColumn<>("Description");
        itemDescriptionCol.setCellValueFactory((CellDataFeatures<Substitute, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        itemDescriptionCol.setCellFactory(stringCell(Pos.CENTER));
        itemDescriptionCol.setPrefWidth(225);

        TableColumn<Substitute, String> itemPriceCol = new TableColumn<>("Item Price");
        itemPriceCol.setCellValueFactory((CellDataFeatures<Substitute, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getSubstituteItem().getPrice1()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        itemPriceCol.setCellFactory(stringCell(Pos.CENTER));
        itemPriceCol.setPrefWidth(90);

        TableColumn<Substitute, String> substituteItemSKUCol = new TableColumn<>("Sub Item SKU");
        substituteItemSKUCol.setCellValueFactory((CellDataFeatures<Substitute, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getSubstituteItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        substituteItemSKUCol.setCellFactory(stringCell(Pos.CENTER));
        substituteItemSKUCol.setPrefWidth(100);

        TableColumn<Substitute, String> substituteItemDescriptionCol = new TableColumn<>("Sub Item Description");
        substituteItemDescriptionCol.setCellValueFactory((CellDataFeatures<Substitute, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getSubstituteItem().getDescription());
            } else {
                return new SimpleStringProperty("");
            }
        });
        substituteItemDescriptionCol.setCellFactory(stringCell(Pos.CENTER));
        substituteItemDescriptionCol.setPrefWidth(225);

        TableColumn<Substitute, String> substituteItemPriceCol = new TableColumn<>("Sub Item Price");
        substituteItemPriceCol.setCellValueFactory((CellDataFeatures<Substitute, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(getNumberFormat().format(p.getValue().getSubstituteItem().getPrice1()));
            } else {
                return new SimpleStringProperty("");
            }
        });
        substituteItemPriceCol.setCellFactory(stringCell(Pos.CENTER));
        substituteItemPriceCol.setPrefWidth(90);

        fTableView.getColumns().add(itemSKUCol);
        fTableView.getColumns().add(itemDescriptionCol);
        fTableView.getColumns().add(itemPriceCol);
        fTableView.getColumns().add(substituteItemSKUCol);
        fTableView.getColumns().add(substituteItemDescriptionCol);
        fTableView.getColumns().add(substituteItemPriceCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createSelectPane(), 0, 3);
        mainPane.add(createButtonPane(), 0, 4);
        return mainPane;
    }

    private GridPane createSelectPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        Label itemLabel = new Label("Item SKU: ");
        Label subLabel = new Label("Substitute Item SKU: ");
        Label spaceLabel = new Label("    ");
        Button selectItemButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, ITEM_SELECT, "Select Item", fHandler);
        Button selectSubItemButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, SUB_SELECT, "Select Sub Item", fHandler);
        editPane.add(itemLabel, 0, 0);
        editPane.add(itemTextField, 1, 0);
        editPane.add(selectItemButton, 2, 0);
        editPane.add(subLabel, 3, 0);
        editPane.add(subTextField, 4, 0);
        editPane.add(selectSubItemButton, 5, 0);
        editPane.add(spaceLabel, 6, 0);
        editPane.add(addButton, 7, 0);

        itemTextField.textProperty().addListener(fListener);
        subTextField.textProperty().addListener(fListener);

        editPane.setAlignment(Pos.TOP_LEFT);

        return editPane;
    }

    protected final HBox createButtonPane() {
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button closeButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOSE, AppConstants.ACTION_CLOSE, fHandler);
        HBox buttonGroup = new HBox();
        buttonGroup.getChildren().addAll(deleteButton, closeButton);
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(4);
        return buttonGroup;
    }

    @Override
    protected void validate() {
        if (!itemTextField.getText().trim().isEmpty() && !subTextField.getText().trim().isEmpty() && fItem != null && fSubItem != null
                && fItem.getId().compareTo(fSubItem.getId()) != 0) {
            addButton.setDisable(false);
            fTableView.getItems().stream().filter((s) -> (s.getItem().getId().compareTo(fItem.getId()) == 0 && s.getSubstituteItem().getId().compareTo(fSubItem.getId()) == 0)).forEachOrdered((_item) -> {
                addButton.setDisable(true);
            });
        } else {
            addButton.setDisable(true);
        }
    }
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.ItemBom;
import com.salesliant.entity.ItemBom_;
import com.salesliant.entity.ItemPriceLevel;
import com.salesliant.entity.Item_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.decimalEditCell;
import static com.salesliant.util.BaseUtil.editableCell;
import static com.salesliant.util.BaseUtil.getDecimalFormat;
import static com.salesliant.util.BaseUtil.getPercentFormat;
import static com.salesliant.util.BaseUtil.getString;
import static com.salesliant.util.BaseUtil.isEmpty;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.util.IconFactory;
import com.salesliant.util.RES;
import com.salesliant.widget.ItemTableWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public final class ItemBomUI extends BaseListUI<ItemBom> {

    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final DataUI dataUI = new DataUI(Item.class);
    private final DataUI itemBomUI = new DataUI(ItemBom.class);
    public Button saveButton, cancelButton, updateButton;
    private final Item fItem;
    private final ObjectProperty<BigDecimal> subTotalProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> profitProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    private final ObjectProperty<BigDecimal> priceProperty = new SimpleObjectProperty<>(BigDecimal.ZERO);
    public final Label warning = new Label("");
    private final static String LINE_NOTE_EDIT_PANE_TITLE = "Line Note";
    private GridPane fNoteEditPane;
    private GridPane fCostEditPane;
    private static final Logger LOGGER = Logger.getLogger(ItemBomUI.class.getName());

    public ItemBomUI(Item item) {
        this.fItem = item;
        createGUI();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                ItemBom ib = fTableView.getItems().stream().filter(e -> e.getComponentItem() == null).findFirst().orElse(null);
                if (ib == null) {
                    fEntity = new ItemBom();
                    fEntityList.add(fEntity);
                    fTableView.refresh();
                } else {
                    fEntity = ib;
                }
                updateTotal();
                Platform.runLater(() -> {
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(fEntity);
                    fTableView.scrollTo(fEntity);
                    fTableView.layout();
                    fTableView.getFocusModel().focus(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getColumns().get(0));
                    fTableView.edit(fTableView.getSelectionModel().getSelectedIndex(), fTableView.getColumns().get(0));
                });
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.getItems().remove(i);
                    fTableView.refresh();
                    if (i >= 1) {
                        fTableView.requestFocus();
                        fTableView.getSelectionModel().select(i - 1);
                        fTableView.getFocusModel().focus(i - 1, fTableView.getColumns().get(0));
                    }
                    updateTotal();
                }
                break;
            case AppConstants.ACTION_LINE_NOTE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        itemBomUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fNoteEditPane, LINE_NOTE_EDIT_PANE_TITLE);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            itemBomUI.getData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        if (isEmpty(fEntity.getLineNote())) {
                            fEntity.setLineNote(null);
                        }
                        fTableView.refresh();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    Platform.runLater(() -> itemBomUI.getTextArea(ItemBom_.lineNote).requestFocus());
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null && fTableView.getSelectionModel().getSelectedItem().getComponentItem() != null) {
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentItem().getCategory() != null && fTableView.getSelectionModel().getSelectedItem().getComponentItem().getCategory().getCountTag() != null && fTableView.getSelectionModel().getSelectedItem().getComponentItem().getCategory().getCountTag()) {
                        showAlertDialog("You can't edit the cost of a this item!");
                        break;
                    }
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        itemBomUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fCostEditPane, "Cost");
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            itemBomUI.getData(fEntity);
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                        updateTotal();
                        fTableView.refresh();
                        fTableView.getSelectionModel().select(fEntity);
                    });
                    Platform.runLater(() -> itemBomUI.getTextField(ItemBom_.cost).requestFocus());
                    fInputDialog.show();
                }
                break;
            case AppConstants.ACTION_UPDATE:
                showConfirmDialog("Are you sure to update the original BOM?", (ActionEvent e) -> {
                    update();
                    daoItem.update(fItem);
                });
                break;
            case AppConstants.ACTION_SELECT_LIST:
                ItemTableWidget itemWidget = new ItemTableWidget(DBConstants.ITEM_TYPE_STANDARD);
                fInputDialog = createSelectCancelUIDialog(itemWidget.getView(), "Item");
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    if (itemWidget.getSelectedItems().size() >= 1) {
                        List<ItemBom> list = fTableView.getItems().stream().filter(p -> p.getComponentItem() == null).collect(Collectors.toList());
                        if (!list.isEmpty()) {
                            fTableView.getItems().removeAll(list);
                        }
                        int i = fTableView.getItems().size();
                        itemWidget.getSelectedItems().forEach(e -> {
                            ItemBom newItemBom = new ItemBom();
                            newItemBom.setComponentItem(e);
                            newItemBom.setQuantity(BigDecimal.ONE);
                            fTableView.getItems().add(newItemBom);
                        });
                        updateTotal();
                        Platform.runLater(() -> {
                            fTableView.requestFocus();
                            fTableView.getSelectionModel().select(i);
                            fTableView.scrollTo(i);
                            fTableView.layout();
                            fTableView.getFocusModel().focus(i, fTableView.getColumns().get(2));
                            fTableView.edit(i, fTableView.getColumns().get(2));
                        });
                    } else {
                        event.consume();
                    }
                });
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_MOVE_ROW_UP:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i >= 1) {
                        Collections.swap(fTableView.getItems(), i, i - 1);
                        fTableView.requestFocus();
                        fTableView.scrollTo(i - 1);
                        fTableView.getSelectionModel().select(i - 1);
                    }
                }
                break;
            case AppConstants.ACTION_MOVE_ROW_DOWN:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getFocusModel().getFocusedCell().getRow();
                    if (i < fTableView.getItems().size() - 1) {
                        Collections.swap(fTableView.getItems(), i, i + 1);
                        fTableView.requestFocus();
                        fTableView.scrollTo(i + 1);
                        fTableView.getSelectionModel().select(i + 1);
                    }
                }
                break;
            case AppConstants.ACTION_TABLE_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    int i = fTableView.getSelectionModel().getSelectedIndex();
                    fTableView.refresh();
                    fTableView.requestFocus();
                    fTableView.getSelectionModel().select(i);
                    if (fTableView.getSelectionModel().getSelectedItem().getComponentItem() != null) {
                        fTableView.getFocusModel().focus(i, fTableView.getColumns().get(2));
                    } else {
                        fTableView.getFocusModel().focus(i, fTableView.getColumns().get(0));
                    }
                }
                break;
        }
    }

    private void createGUI() {
        fTableView = new TableView<ItemBom>() {
            @Override
            public void edit(int row, TableColumn<ItemBom, ?> column) {
                if (row >= 0 && row <= (getItems().size() - 1)) {
                    ItemBom entry = getItems().get(row);
                    if (entry.getComponentItem() != null && column != null && column.equals(getColumns().get(0))) {
                        return;
                    }
                }
                super.edit(row, column);
            }
        };
        fTableView.setEditable(true);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        addKeyListener();
        if (fItem.getItemBomBomItems() != null) {
            List<ItemBom> list = new ArrayList<>();
            fItem.getItemBomBomItems().stream().sorted((e1, e2) -> Integer.compare(e1.getDisplayOrder(), e2.getDisplayOrder())).forEach(e -> list.add(e));
            fEntityList = FXCollections.observableList(list);
        } else {
            fEntityList = FXCollections.observableArrayList();
        }
        fTableView.setItems(fEntityList);
        mainView = createMainView();
        fNoteEditPane = createNoteEditPane();
        fCostEditPane = createCostEditPane();
        try {
            dataUI.setData(fItem);
            updateTotal();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        dataUI.getTextField(Item_.itemLookUpCode).setEditable(true);
        dataUI.getTextField(Item_.itemLookUpCode).setFocusTraversable(true);
        dataUI.getTextField(Item_.description).setFocusTraversable(true);
        dataUI.getTextField(Item_.description).setEditable(true);
        dataUI.getTextField(Item_.price1).setFocusTraversable(true);
        dataUI.getTextField(Item_.price1).textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            updateTotal();
        });
        if (!fItem.getSalesOrderEntries().isEmpty()) {
            dataUI.getTextField(Item_.itemLookUpCode).setEditable(false);
            dataUI.getTextField(Item_.itemLookUpCode).setFocusTraversable(false);
            dataUI.getTextField(Item_.itemLookUpCode).setBorder(Border.EMPTY);
            dataUI.getTextField(Item_.itemLookUpCode).setBackground(Background.EMPTY);
            dataUI.getTextField(Item_.description).setEditable(false);
            dataUI.getTextField(Item_.description).setFocusTraversable(false);
            dataUI.getTextField(Item_.description).setBorder(Border.EMPTY);
            dataUI.getTextField(Item_.description).setBackground(Background.EMPTY);
            dataUI.getTextField(Item_.price1).setFocusTraversable(false);
        }
        if (fTableView.getItems().isEmpty()) {
            handleAction(AppConstants.ACTION_ADD);
        } else {
            fTableView.getSelectionModel().selectFirst();
            fTableView.getSelectionModel().select(0, fTableView.getColumns().get(2));
        }
        fTableView.setRowFactory(mouseClickListener(1));
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);

        TableColumn<ItemBom, String> skuCol = new TableColumn<>("SKU");
        skuCol.setCellValueFactory((CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue() != null && p.getValue().getComponentItem() != null && p.getValue().getComponentItem().getItemLookUpCode() != null) {
                return new SimpleStringProperty(p.getValue().getComponentItem().getItemLookUpCode());
            } else {
                return null;
            }
        });
        skuCol.setOnEditCommit((TableColumn.CellEditEvent<ItemBom, String> t) -> {
            ItemBom ib = (ItemBom) t.getTableView().getItems().get(t.getTablePosition().getRow());
            fTableView.refresh();
            updateTotal();
            if (ib != null && ib.getComponentItem() != null) {
                handleAction(AppConstants.ACTION_ADD);
            }
        });
        skuCol.setCellFactory(editableCell(Pos.TOP_CENTER));
        skuCol.setPrefWidth(100);
        skuCol.setResizable(false);
        skuCol.setSortable(false);

        TableColumn<ItemBom, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue().getComponentItem() != null) {
                String description = getString(p.getValue().getComponentItem().getDescription());
                if (p.getValue().getLineNote() != null && !p.getValue().getLineNote().isEmpty()) {
                    description = description + "\n" + p.getValue().getLineNote();
                }
                description = description.trim();
                return new SimpleStringProperty(description);
            } else {
                return null;
            }
        });
        descriptionCol.setCellFactory(stringCell(Pos.TOP_LEFT));
        descriptionCol.setEditable(false);
        descriptionCol.setPrefWidth(290);
        descriptionCol.setSortable(false);

        TableColumn<ItemBom, BigDecimal> qtyCol = new TableColumn<>("Qty");
        qtyCol.setCellValueFactory(new PropertyValueFactory<>(ItemBom_.quantity.getName()));
        qtyCol.setOnEditCommit((TableColumn.CellEditEvent<ItemBom, BigDecimal> t) -> {
            if (t.getNewValue() != null && !t.getNewValue().equals(t.getOldValue())) {
                ((ItemBom) t.getTableView().getItems().get(t.getTablePosition().getRow())).setQuantity(t.getNewValue());
                updateTotal();
                fTableView.refresh();
            }
        });
        qtyCol.setCellFactory(decimalEditCell(Pos.TOP_RIGHT));
        qtyCol.setPrefWidth(80);
        qtyCol.setResizable(false);
        qtyCol.setSortable(false);

        TableColumn<ItemBom, String> priceCol = new TableColumn<>("Price");
        priceCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue() != null && p.getValue().getComponentItem() != null && p.getValue().getComponentItem().getPrice1() != null) {
                return new SimpleStringProperty(getString(p.getValue().getComponentItem().getPrice1()));
            } else {
                return null;
            }
        });
        priceCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        priceCol.setPrefWidth(85);
        priceCol.setEditable(false);
        priceCol.setResizable(false);
        priceCol.setSortable(false);

        TableColumn<ItemBom, String> costCol = new TableColumn<>("Cost");
        costCol.setCellValueFactory((TableColumn.CellDataFeatures<ItemBom, String> p) -> {
            if (p.getValue().getComponentItem() != null) {
                if (p.getValue().getCost() != null) {
                    return new SimpleStringProperty(getString(p.getValue().getCost()));
                } else {
                    return new SimpleStringProperty(getString(p.getValue().getComponentItem().getCost()));
                }
            } else {
                return null;
            }
        });
        costCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        costCol.setPrefWidth(85);
        costCol.setEditable(false);
        costCol.setResizable(false);
        costCol.setSortable(false);

        TableColumn<ItemBom, String> totalCol = new TableColumn<>("Total");
        totalCol.setCellValueFactory((CellDataFeatures<ItemBom, String> p) -> {
            BigDecimal total;
            if (p.getValue() != null && p.getValue().getComponentItem() != null && p.getValue().getComponentItem().getPrice1() != null && p.getValue().getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                total = p.getValue().getComponentItem().getPrice1().multiply(p.getValue().getQuantity());
                return new SimpleStringProperty(getString(total));
            } else {
                return new SimpleStringProperty("");
            }
        });
        totalCol.setCellFactory(stringCell(Pos.TOP_RIGHT));
        totalCol.setEditable(false);
        totalCol.setPrefWidth(90);
        totalCol.setResizable(false);
        totalCol.setSortable(false);

        fTableView.getColumns().add(skuCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qtyCol);
        fTableView.getColumns().add(priceCol);
        fTableView.getColumns().add(costCol);
        fTableView.getColumns().add(totalCol);
        fTableView.setPrefHeight(500);
        setTableWidth(fTableView);

        mainPane.add(createSettingPane(), 0, 0);
        mainPane.add(fTableView, 0, 1);
        mainPane.add(createButtomPane(), 0, 2);
        return mainPane;
    }

    private GridPane createSettingPane() {
        GridPane settingPane = new GridPane();
        settingPane.setHgap(3.0);
        add(settingPane, "SKU: ", dataUI.createTextField(Item_.itemLookUpCode, 90), fListener, 0);
        add(settingPane, "Description: ", dataUI.createTextField(Item_.description, 250), fListener, 1);
        add(settingPane, "Price: ", dataUI.createTextField(Item_.price1, 90), fListener, 3);
        return settingPane;
    }

    private GridPane createButtomPane() {
        GridPane totalPane = new GridPane();
        totalPane.setHgap(20.0);
        totalPane.setVgap(3.0);

        GridPane sumPane = new GridPane();
        sumPane.setVgap(1.0);
        Label subTotalLabel = new Label(" Sub Total: ");
        Label priceLabel = new Label(" Price: ");
        Label costLabel = new Label("Cost: ");
        Label muLabel = new Label("Profit %: ");

        TextField subTotalField = createLabelField(90.0, Pos.CENTER_RIGHT);
        subTotalField.textProperty().bindBidirectional(subTotalProperty, getDecimalFormat());
        TextField priceField = createLabelField(90.0, Pos.CENTER_RIGHT);
        priceField.textProperty().bindBidirectional(priceProperty, getDecimalFormat());
        TextField profitField = createLabelField(90.0, Pos.CENTER_RIGHT);
        profitField.textProperty().bindBidirectional(profitProperty, getPercentFormat());

        sumPane.add(subTotalLabel, 0, 0);
        sumPane.add(subTotalField, 1, 0);
        sumPane.add(priceLabel, 0, 1);
        sumPane.add(priceField, 1, 1);
        sumPane.add(costLabel, 0, 2);
        sumPane.add(dataUI.createLabelField(Item_.cost, 90, Pos.CENTER_RIGHT), 1, 2);
        sumPane.add(muLabel, 0, 3);
        sumPane.add(profitField, 1, 3);

        Label functionKeyLabel = new Label("Ctrl+L: List Items, Ctrl+X: Delete, Ctrl+U: Move Up, Ctrl+D: Move Down, Insert: Add, Ctrl+S: Save");
        GridPane.setHgrow(functionKeyLabel, Priority.ALWAYS);
        GridPane.setHalignment(functionKeyLabel, HPos.LEFT);
        GridPane.setValignment(functionKeyLabel, VPos.TOP);

        GridPane.setHalignment(sumPane, HPos.RIGHT);
        GridPane.setHalignment(subTotalLabel, HPos.RIGHT);
        GridPane.setHalignment(subTotalField, HPos.RIGHT);
        GridPane.setHalignment(costLabel, HPos.RIGHT);
        GridPane.setHalignment(muLabel, HPos.RIGHT);
        GridPane.setHalignment(priceLabel, HPos.RIGHT);
        sumPane.setAlignment(Pos.TOP_RIGHT);

        totalPane.add(createButtonPane(), 0, 0, 2, 1);
        totalPane.add(functionKeyLabel, 0, 1);
        totalPane.add(sumPane, 1, 1);
        totalPane.add(warning, 0, 2, 2, 3);

        return totalPane;
    }

    private HBox createButtonPane() {
        HBox leftButtonBox = new HBox();
        Button upButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_UP_ICON), AppConstants.ACTION_MOVE_ROW_UP, fHandler);
        Button downButton = ButtonFactory.getButton(IconFactory.getIcon(RES.MOVE_DOWN_ICON), AppConstants.ACTION_MOVE_ROW_DOWN, fHandler);
        Button addButton = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, AppConstants.ACTION_ADD, fHandler);
        Button deleteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_DELETE, AppConstants.ACTION_DELETE, fHandler);
        Button editButton = ButtonFactory.getButton(IconFactory.getIcon(RES.EDIT_ICON), AppConstants.ACTION_EDIT, fHandler);
        Button lineNoteButton = ButtonFactory.getButton(ButtonFactory.BUTTON_LINE_NOTE, AppConstants.ACTION_LINE_NOTE, fHandler);
        Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT_LIST, fHandler);
        leftButtonBox.getChildren().addAll(upButton, downButton, addButton, deleteButton, editButton, lineNoteButton, selectButton);
        leftButtonBox.setAlignment(Pos.CENTER_LEFT);
        leftButtonBox.setSpacing(4);

        HBox rightButtonBox = new HBox();
        saveButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE, AppConstants.ACTION_SAVE, fHandler);
        cancelButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL, AppConstants.ACTION_CANCEL, fHandler);
        updateButton = ButtonFactory.getButton(ButtonFactory.BUTTON_UPDATE, AppConstants.ACTION_UPDATE, "Update Original", fHandler);
        rightButtonBox.getChildren().addAll(updateButton, saveButton, cancelButton);
        rightButtonBox.setAlignment(Pos.CENTER_RIGHT);
        rightButtonBox.setSpacing(4);

        HBox buttonGroup = new HBox();
        buttonGroup.setPadding(new Insets(1));
        Region filler = new Region();
        HBox.setHgrow(filler, Priority.ALWAYS);
        buttonGroup.getChildren().addAll(leftButtonBox, filler, rightButtonBox);
        return buttonGroup;
    }

    private GridPane createCostEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Cost:", itemBomUI.createTextField(ItemBom_.cost, 100), fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    private GridPane createNoteEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        add(editPane, "Line Note:", itemBomUI.createTextArea(ItemBom_.lineNote), 80, 250, fListener, 0);
        editPane.add(lblWarning, 0, 2, 2, 1);
        return editPane;
    }

    protected void updatePrice() {
        BigDecimal costTotal = BigDecimal.ZERO;
        for (ItemBom ia : fTableView.getItems()) {
            if (ia != null && ia.getComponentItem() != null) {
                if (ia.getCost() != null && ia.getCost().compareTo(BigDecimal.ZERO) != 0 && ia.getQuantity() != null && ia.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                    costTotal = costTotal.add(ia.getQuantity().multiply(ia.getCost()));
                } else if (ia.getComponentItem().getCost() != null && ia.getComponentItem().getCost().compareTo(BigDecimal.ZERO) != 0 && ia.getQuantity() != null && ia.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                    costTotal = costTotal.add(ia.getQuantity().multiply(ia.getComponentItem().getCost()));
                }
            }
        }
        if (fItem != null && fItem.getCategory() != null && fItem.getCategory().getPrice1() != null) {
            BigDecimal mu = fItem.getCategory().getPrice1().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            BigDecimal price = costTotal.multiply(mu.add(BigDecimal.ONE));
            dataUI.getTextField(Item_.price1).setText(getString(price));
        }
    }

    @Override
    protected void updateTotal() {
        BigDecimal subTotal = BigDecimal.ZERO;
        BigDecimal costTotal = BigDecimal.ZERO;
        BigDecimal pf = BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (ItemBom ia : fTableView.getItems()) {
            if (ia != null && ia.getComponentItem() != null && ia.getComponentItem().getPrice1() != null && ia.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                subTotal = subTotal.add(ia.getQuantity().multiply(ia.getComponentItem().getPrice1()));
            }
            if (ia != null && ia.getComponentItem() != null) {
                if (ia.getCost() != null && ia.getCost().compareTo(BigDecimal.ZERO) != 0 && ia.getQuantity() != null && ia.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                    costTotal = costTotal.add(ia.getQuantity().multiply(ia.getCost()));
                } else if (ia.getComponentItem().getCost() != null && ia.getComponentItem().getCost().compareTo(BigDecimal.ZERO) != 0 && ia.getQuantity() != null && ia.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                    costTotal = costTotal.add(ia.getQuantity().multiply(ia.getComponentItem().getCost()));
                }
            }
        }
        if (dataUI.getTextField(Item_.price1).getText() != null && !dataUI.getTextField(Item_.price1).getText().isEmpty()) {
            total = new BigDecimal(dataUI.getTextField(Item_.price1).getText());
        }
        if (total.compareTo(BigDecimal.ZERO) != 0) {
            pf = (total.subtract(costTotal)).divide(total, 4, RoundingMode.HALF_UP);
        }
        subTotalProperty.set(subTotal);
        profitProperty.set(pf);
        priceProperty.set(total);
        dataUI.getTextField(Item_.cost).setText(getString(costTotal));
    }

    public void update() {
        updateTotal();
        try {
            dataUI.getData(fItem);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        List<ItemPriceLevel> listPriceLevel = Config.getItemPriceLevel();
        int n = listPriceLevel.size();
        if (fItem.getPrice1() != null) {
            if (fItem.getPrice2() != null && n >= 2) {
                fItem.setPrice2(fItem.getPrice1());
            }
            if (fItem.getPrice3() != null && n >= 3) {
                fItem.setPrice3(fItem.getPrice1());
            }
            if (fItem.getPrice4() != null && n >= 4) {
                fItem.setPrice4(fItem.getPrice1());
            }
            if (fItem.getPrice5() != null && n >= 5) {
                fItem.setPrice5(fItem.getPrice1());
            }
            if (fItem.getPrice6() != null && n >= 6) {
                fItem.setPrice6(fItem.getPrice1());
            }
        }
        fItem.getItemBomBomItems().clear();
        List<ItemBom> list = new ArrayList<>();
        for (int i = 0; i < fTableView.getItems().size(); i++) {
            ItemBom itemBom = fTableView.getItems().get(i);
            if (itemBom.getComponentItem() != null) {
                itemBom.setDisplayOrder(i);
                itemBom.setBomItem(fItem);
                list.add(itemBom);
            }
        }
        fItem.setItemBomBomItems(list);
    }
}

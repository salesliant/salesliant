package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Item;
import com.salesliant.entity.QuantityDiscount;
import com.salesliant.entity.QuantityDiscount_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getDoubleFormat;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class QuantityDiscountListUI extends BaseListUI<QuantityDiscount> {

    private final BaseDao<QuantityDiscount> daoQuantityDiscount = new BaseDao<>(QuantityDiscount.class);
    private final BaseDao<Item> daoItem = new BaseDao<>(Item.class);
    private final DataUI dataUI = new DataUI(QuantityDiscount.class);
    private Item fItem;
    private final String Price1;
    private final String Price2;
    private final String Price3;
    private final String Price4;
    private final GridPane fEditPane;
    private final Button selectButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT, AppConstants.ACTION_SELECT, fHandler);
    private final TextField fItemSKU = new TextField(), fItemDescription = new TextField(), fItemPrice = new TextField(),
            fItemPriceA = new TextField(), fItemPriceB = new TextField(), fItemPriceC = new TextField();
    private final TextArea fQty1Area = new TextArea(), fQty2Area = new TextArea(), fQty3Area = new TextArea(), fQty4Area = new TextArea();
    private static final Logger LOGGER = Logger.getLogger(QuantityDiscountListUI.class.getName());

    public QuantityDiscountListUI() {
        if (Config.getItemPriceLevel() != null && Config.getItemPriceLevel().size() >= 1 && Config.getItemPriceLevel().get(0) != null && Config.getItemPriceLevel().get(0).getDescription() != null) {
            Price1 = Config.getItemPriceLevel().get(0).getDescription();
        } else {
            Price1 = "";
        }
        if (Config.getItemPriceLevel() != null && Config.getItemPriceLevel().size() >= 2 && Config.getItemPriceLevel().get(1) != null && Config.getItemPriceLevel().get(1).getDescription() != null) {
            Price2 = Config.getItemPriceLevel().get(1).getDescription();
        } else {
            Price2 = "";
        }
        if (Config.getItemPriceLevel() != null && Config.getItemPriceLevel().size() >= 3 && Config.getItemPriceLevel().get(2) != null && Config.getItemPriceLevel().get(2).getDescription() != null) {
            Price3 = Config.getItemPriceLevel().get(2).getDescription();
        } else {
            Price3 = "";
        }
        if (Config.getItemPriceLevel() != null && Config.getItemPriceLevel().size() >= 4 && Config.getItemPriceLevel().get(3) != null && Config.getItemPriceLevel().get(3).getDescription() != null) {
            Price4 = Config.getItemPriceLevel().get(3).getDescription();
        } else {
            Price4 = "";
        }
        loadData();
        mainView = createMainView();
        fEditPane = createEditPane();
        fTableView.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends QuantityDiscount> observable, QuantityDiscount newValue, QuantityDiscount oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntity = fTableView.getSelectionModel().getSelectedItem();
                updateInfoPane(fEntity);
            } else {
                updateInfoPane(null);
            }
        });
        dialogTitle = "Quantity Discount";
    }

    private void loadData() {
        List<QuantityDiscount> list = daoQuantityDiscount.read();
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new QuantityDiscount();
                selectButton.setDisable(false);
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fItemSKU.setText("");
                fItemDescription.setText("");
                fItemPrice.setText("");
                fItemPriceA.setText("");
                fItemPriceB.setText("");
                fItemPriceC.setText("");
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Quantity Discount");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoQuantityDiscount.insert(fEntity);
                        fItem = fEntity.getItem();
                        fItem.setQuantityDiscount(fEntity);
                        daoItem.update(fItem);
                        if (daoQuantityDiscount.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                            fTableView.scrollTo(fEntity);
                            fTableView.getSelectionModel().select(fEntity);
                        } else {
                            lblWarning.setText(daoQuantityDiscount.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(QuantityDiscount_.description).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    selectButton.setDisable(true);
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fItemSKU.setText(fEntity.getItem().getItemLookUpCode());
                    fItemDescription.setText(fEntity.getItem().getDescription());
                    if (fEntity.getItem().getPrice1() != null) {
                        fItemPrice.setText(getDoubleFormat().format(fEntity.getItem().getPrice1()));
                    } else {
                        fItemPrice.setText("");
                    }
                    if (fEntity.getItem().getPrice2() != null) {
                        fItemPriceA.setText(getDoubleFormat().format(fEntity.getItem().getPrice2()));
                    } else {
                        fItemPriceA.setText("");
                    }
                    if (fEntity.getItem().getPrice3() != null) {
                        fItemPriceB.setText(getDoubleFormat().format(fEntity.getItem().getPrice3()));
                    } else {
                        fItemPriceB.setText("");
                    }
                    if (fEntity.getItem().getPrice4() != null) {
                        fItemPriceC.setText(getDoubleFormat().format(fEntity.getItem().getPrice4()));
                    } else {
                        fItemPriceC.setText("");
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Quantity Discount");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoQuantityDiscount.update(fEntity);
                            if (daoQuantityDiscount.getErrorMessage() == null) {
                                loadData();
                            } else {
                                lblWarning.setText(daoQuantityDiscount.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(QuantityDiscount_.description).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoQuantityDiscount.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
            case AppConstants.ACTION_SELECT:
                ItemListUI itemListUI = new ItemListUI();
                itemListUI.actionButtonBox.setDisable(true);
                TableView<Item> itemTable = itemListUI.getTableView();
                fInputDialog = createSelectCancelUIDialog(itemListUI.getView(), "Item");
                selectBtn.setDisable(true);
                itemTable.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Item> observable, Item newValue, Item oldValue) -> {
                    if (observable != null && observable.getValue() != null) {
                        selectBtn.setDisable(false);
                    } else {
                        selectBtn.setDisable(true);
                    }
                });
                selectBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    Item selectedItem = itemTable.getSelectionModel().getSelectedItem();
                    fEntity.setItem(selectedItem);
                    fItemSKU.setText(fEntity.getItem().getItemLookUpCode());
                    fItemDescription.setText(fEntity.getItem().getDescription());
                    if (fEntity.getItem().getPrice1() != null) {
                        fItemPrice.setText(getDoubleFormat().format(fEntity.getItem().getPrice1()));
                    } else {
                        fItemPrice.setText("");
                    }
                    if (fEntity.getItem().getPrice2() != null) {
                        fItemPriceA.setText(getDoubleFormat().format(fEntity.getItem().getPrice2()));
                    } else {
                        fItemPriceA.setText("");
                    }
                    if (fEntity.getItem().getPrice3() != null) {
                        fItemPriceB.setText(getDoubleFormat().format(fEntity.getItem().getPrice3()));
                    } else {
                        fItemPriceB.setText("");
                    }
                    if (fEntity.getItem().getPrice4() != null) {
                        fItemPriceC.setText(getDoubleFormat().format(fEntity.getItem().getPrice4()));
                    } else {
                        fItemPriceC.setText("");
                    }
                    Platform.runLater(() -> dataUI.getTextField(QuantityDiscount_.description).requestFocus());
                });
                fInputDialog.showDialog();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label QuantityDiscountLbl = new Label("List of QuantityDiscount:");
        mainPane.add(QuantityDiscountLbl, 0, 1);
        GridPane.setHalignment(QuantityDiscountLbl, HPos.LEFT);

        TableColumn<QuantityDiscount, String> itemSKUCol = new TableColumn<>("SKU");
        itemSKUCol.setCellValueFactory((CellDataFeatures<QuantityDiscount, String> p) -> {
            if (p.getValue().getItem() != null) {
                return new SimpleStringProperty(p.getValue().getItem().getItemLookUpCode());
            } else {
                return new SimpleStringProperty("");
            }
        });
        itemSKUCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        itemSKUCol.setPrefWidth(120);

        TableColumn<QuantityDiscount, String> descriptionCol = new TableColumn<>("Description");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.description.getName()));
        descriptionCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        descriptionCol.setPrefWidth(150);

        TableColumn<QuantityDiscount, Integer> qt1Col = new TableColumn<>("Quantity1");
        qt1Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.quantity1.getName()));
        qt1Col.setCellFactory(stringCell(Pos.CENTER));
        qt1Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, BigDecimal> price1Col = new TableColumn<>(Price1);
        price1Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.price11.getName()));
        price1Col.setCellFactory(stringCell(Pos.CENTER));
        price1Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, Integer> qt2Col = new TableColumn<>("Quantity2");
        qt2Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.quantity2.getName()));
        qt2Col.setCellFactory(stringCell(Pos.CENTER));
        qt2Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, BigDecimal> price2Col = new TableColumn<>(Price1);
        price2Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.price12.getName()));
        price2Col.setCellFactory(stringCell(Pos.CENTER));
        price2Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, Integer> qt3Col = new TableColumn<>("Quantity3");
        qt3Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.quantity3.getName()));
        qt3Col.setCellFactory(stringCell(Pos.CENTER));
        qt3Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, BigDecimal> price3Col = new TableColumn<>(Price1);
        price3Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.price13.getName()));
        price3Col.setCellFactory(stringCell(Pos.CENTER));
        price3Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, Integer> qt4Col = new TableColumn<>("Quantity4");
        qt4Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.quantity4.getName()));
        qt4Col.setCellFactory(stringCell(Pos.CENTER));
        qt4Col.setPrefWidth(90);

        TableColumn<QuantityDiscount, BigDecimal> price4Col = new TableColumn<>(Price1);
        price4Col.setCellValueFactory(new PropertyValueFactory<>(QuantityDiscount_.price14.getName()));
        price4Col.setCellFactory(stringCell(Pos.CENTER));
        price4Col.setPrefWidth(90);

        fTableView.getColumns().add(itemSKUCol);
        fTableView.getColumns().add(descriptionCol);
        fTableView.getColumns().add(qt1Col);
        fTableView.getColumns().add(price1Col);
        fTableView.getColumns().add(qt2Col);
        fTableView.getColumns().add(price2Col);
        fTableView.getColumns().add(qt3Col);
        fTableView.getColumns().add(price3Col);
        fTableView.getColumns().add(qt4Col);
        fTableView.getColumns().add(price4Col);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createDetailPane(), 0, 3);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private Node createDetailPane() {
        VBox qty1Box = new VBox();
        Label qty1Label = new Label("Quantity1");
        fQty1Area.setEditable(false);
        fQty1Area.setPrefSize(220, 100);
        qty1Box.setPadding(new Insets(5, 5, 5, 5));
        qty1Box.getChildren().addAll(qty1Label, fQty1Area);

        VBox qty2Box = new VBox();
        Label qty2Label = new Label("Quantity2");
        fQty2Area.setEditable(false);
        fQty2Area.setPrefSize(220, 100);
        qty2Box.getChildren().addAll(qty2Label, fQty2Area);

        VBox qty3Box = new VBox();
        Label qty3Label = new Label("Quantity3");
        fQty3Area.setEditable(false);
        fQty3Area.setPrefSize(220, 100);
        qty3Box.getChildren().addAll(qty3Label, fQty3Area);

        VBox qty4Box = new VBox();
        Label qty4Label = new Label("Quantity4");
        fQty4Area.setEditable(false);
        fQty4Area.setPrefSize(220, 100);
        qty4Box.getChildren().addAll(qty4Label, fQty4Area);

        HBox result = new HBox();
        result.setSpacing(10);
        result.getChildren().addAll(qty1Box, qty2Box, qty3Box, qty4Box);
        result.setAlignment(Pos.CENTER);
        result.getStyleClass().add("hboxPane");

        return result;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        GridPane topPane = new GridPane();
        topPane.getStyleClass().add("editView");
        add(topPane, "Item SKU:", fItemSKU, fListener, 250.0, 0);
        add(topPane, "Item Description:", fItemDescription, fListener, 250.0, 1);
        add(topPane, Price1 + ":*", fItemPrice, fListener, 250.0, 2);
        add(topPane, Price2 + ":*", fItemPriceA, fListener, 250.0, 3);
        add(topPane, Price3 + ":*", fItemPriceB, fListener, 250.0, 4);
        add(topPane, Price4 + ":*", fItemPriceC, fListener, 250.0, 5);
        topPane.add(selectButton, 2, 0);

        GridPane discountPane = new GridPane();
        discountPane.getStyleClass().add("editView");
        add(discountPane, "Discount Description:*", dataUI.createTextField(QuantityDiscount_.description), fListener, 250.0, 0);

        GridPane leftPane = new GridPane();
        leftPane.getStyleClass().add("editView");
        add(leftPane, "Quantity 1:*", dataUI.createTextField(QuantityDiscount_.quantity1), fListener, 70.0, 0);
        add(leftPane, Price1 + ":*", dataUI.createTextField(QuantityDiscount_.price11), fListener, 70.0, 1);
        add(leftPane, Price2 + ":*", dataUI.createTextField(QuantityDiscount_.price21), fListener, 70.0, 2);
        add(leftPane, Price3 + ":*", dataUI.createTextField(QuantityDiscount_.price31), fListener, 70.0, 3);
        add(leftPane, Price4 + ":*", dataUI.createTextField(QuantityDiscount_.price41), fListener, 70.0, 4);
        add(leftPane, "Quantity 2:*", dataUI.createTextField(QuantityDiscount_.quantity2), fListener, 70.0, 5);
        add(leftPane, Price1 + ":*", dataUI.createTextField(QuantityDiscount_.price12), fListener, 70.0, 6);
        add(leftPane, Price2 + ":*", dataUI.createTextField(QuantityDiscount_.price22), fListener, 70.0, 7);
        add(leftPane, Price3 + ":*", dataUI.createTextField(QuantityDiscount_.price32), fListener, 70.0, 8);
        add(leftPane, Price4 + ":*", dataUI.createTextField(QuantityDiscount_.price42), fListener, 70.0, 9);

        GridPane rightPane = new GridPane();
        rightPane.getStyleClass().add("editView");
        add(rightPane, "Quantity 3:*", dataUI.createTextField(QuantityDiscount_.quantity3), fListener, 70.0, 0);
        add(rightPane, Price1 + ":*", dataUI.createTextField(QuantityDiscount_.price13), fListener, 70.0, 1);
        add(rightPane, Price2 + ":*", dataUI.createTextField(QuantityDiscount_.price23), fListener, 70.0, 2);
        add(rightPane, Price3 + ":*", dataUI.createTextField(QuantityDiscount_.price33), fListener, 70.0, 3);
        add(rightPane, Price4 + ":*", dataUI.createTextField(QuantityDiscount_.price34), fListener, 70.0, 4);
        add(rightPane, "Quantity 4:*", dataUI.createTextField(QuantityDiscount_.quantity4), fListener, 70.0, 5);
        add(rightPane, Price1 + ":*", dataUI.createTextField(QuantityDiscount_.price14), fListener, 70.0, 6);
        add(rightPane, Price2 + ":*", dataUI.createTextField(QuantityDiscount_.price24), fListener, 70.0, 7);
        add(rightPane, Price3 + ":*", dataUI.createTextField(QuantityDiscount_.price34), fListener, 70.0, 8);
        add(rightPane, Price4 + ":*", dataUI.createTextField(QuantityDiscount_.price44), fListener, 70.0, 9);

        GridPane bottomPane = new GridPane();
        bottomPane.getStyleClass().add("editView");
        bottomPane.add(leftPane, 0, 0);
        bottomPane.add(rightPane, 1, 0);
        bottomPane.setAlignment(Pos.CENTER);
        editPane.add(topPane, 0, 0);
        editPane.add(discountPane, 0, 1);
        editPane.add(bottomPane, 0, 3);
        editPane.add(lblWarning, 0, 4);

        fItemSKU.setEditable(false);
        fItemDescription.setEditable(false);
        fItemPrice.setEditable(false);
        fItemPriceA.setEditable(false);
        fItemPriceB.setEditable(false);
        fItemPriceC.setEditable(false);

        return editPane;
    }

    private void updateInfoPane(QuantityDiscount quantityDiscount) {
        if (quantityDiscount == null) {
            fQty1Area.setText("");
            fQty2Area.setText("");
            fQty3Area.setText("");
            fQty4Area.setText("");
        } else {
            if (quantityDiscount.getQuantity1() != null && quantityDiscount.getQuantity1() >= 1) {
                String info1 = "";
                info1 = info1 + addToString(quantityDiscount.getQuantity1(), "Quantity: ");
                info1 = info1 + addToString(quantityDiscount.getPrice11(), Price1 + ":*");
                info1 = info1 + addToString(quantityDiscount.getPrice21(), Price2 + ":*");
                info1 = info1 + addToString(quantityDiscount.getPrice31(), Price3 + ":*");
                info1 = info1 + addToString(quantityDiscount.getPrice41(), Price4 + ":*");
                fQty1Area.setText(info1.trim());
            } else {
                fQty1Area.setText("");
            }
            if (quantityDiscount.getQuantity2() != null && quantityDiscount.getQuantity2() >= 1) {
                String info2 = "";
                info2 = info2 + addToString(quantityDiscount.getQuantity2(), "Quantity: ");
                info2 = info2 + addToString(quantityDiscount.getPrice12(), Price1 + ":*");
                info2 = info2 + addToString(quantityDiscount.getPrice22(), Price2 + ":*");
                info2 = info2 + addToString(quantityDiscount.getPrice32(), Price3 + ":*");
                info2 = info2 + addToString(quantityDiscount.getPrice42(), Price4 + ":*");
                fQty2Area.setText(info2.trim());
            } else {
                fQty2Area.setText("");
            }
            if (quantityDiscount.getQuantity3() != null && quantityDiscount.getQuantity3() >= 1) {
                String info3 = "";
                info3 = info3 + addToString(quantityDiscount.getQuantity3(), "Quantity: ");
                info3 = info3 + addToString(quantityDiscount.getPrice13(), Price1 + ":*");
                info3 = info3 + addToString(quantityDiscount.getPrice23(), Price2 + ":*");
                info3 = info3 + addToString(quantityDiscount.getPrice33(), Price3 + ":*");
                info3 = info3 + addToString(quantityDiscount.getPrice43(), Price4 + ":*");
                fQty3Area.setText(info3.trim());
            } else {
                fQty3Area.setText("");
            }
            if (quantityDiscount.getQuantity4() != null && quantityDiscount.getQuantity4() >= 1) {
                String info4 = "";
                info4 = info4 + addToString(quantityDiscount.getQuantity4(), "Quantity: ");
                info4 = info4 + addToString(quantityDiscount.getPrice14(), Price1 + ":*");
                info4 = info4 + addToString(quantityDiscount.getPrice24(), Price2 + ":*");
                info4 = info4 + addToString(quantityDiscount.getPrice34(), Price3 + ":*");
                info4 = info4 + addToString(quantityDiscount.getPrice44(), Price4 + ":*");
                fQty4Area.setText(info4.trim());
            } else {
                fQty4Area.setText("");
            }
        }
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(fItemSKU.getText().trim().isEmpty()
                    || dataUI.getTextField(QuantityDiscount_.description).getText().trim().isEmpty());
        }
    }
}

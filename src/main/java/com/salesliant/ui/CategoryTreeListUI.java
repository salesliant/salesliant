package com.salesliant.ui;

import com.salesliant.entity.Category;
import com.salesliant.entity.Category_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.uppercaseFirst;
import com.salesliant.util.DBConstants;
import com.salesliant.util.DataUI;
import com.salesliant.widget.TaxClassWidget;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

public class CategoryTreeListUI extends BaseListUI<Category> {

    private final BaseDao<Category> daoCategory = new BaseDao<>(Category.class);
    private final DataUI dataUI = new DataUI(Category.class);
    private final ComboBox fTaxClassCombo = new TaxClassWidget();
    private final GridPane fEditPane;
    private final TreeItem<String> fRootNode;
    private final TreeView<String> fTreeView;
    private final List<Category> fList;
    private static final Logger LOGGER = Logger.getLogger(CategoryTreeListUI.class.getName());

    public CategoryTreeListUI() {
        fRootNode = new TreeItem<>("Category");
        fTreeView = new TreeView<>(fRootNode);
        fTreeView.setShowRoot(true);
        fTreeView.setEditable(true);
        fRootNode.setExpanded(true);
        fList = daoCategory.read();
        Collections.sort(fList, categoryComparator);

        mainView = createMainView();
        fEditPane = createEditPane();
        fTreeView.getSelectionModel().select(0);
    }

    @Override
    public void handleAction(String code) {
        TreeItem<String> selectedItem = fTreeView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new Category();
                fEntity.setCommisionMode(DBConstants.TYPE_COMMISSION_BY_PERCENT_OF_SALES);
                fEntity.setCommisionPercentSale(BigDecimal.ONE);
                fEntity.setCommisionPercentProfit(BigDecimal.ONE);
                fEntity.setCommissionFixedAmount(BigDecimal.ZERO);
                fEntity.setCommisionMaximumAmount(BigDecimal.ZERO);
                fEntity.setPrice1(new BigDecimal(100));
                fEntity.setPrice2(new BigDecimal(100));
                fEntity.setPrice3(new BigDecimal(100));
                fEntity.setPrice4(new BigDecimal(100));
                fEntity.setPrice5(new BigDecimal(100));
                fEntity.setPrice6(new BigDecimal(100));
                fEntity.setIsAssetTag(Boolean.FALSE);
                fEntity.setIsShippingTag(Boolean.FALSE);
                fEntity.setCountTag(Boolean.TRUE);
                if (selectedItem == fRootNode) {
                    fEntity.setParent(null);
                } else {
                    fList.forEach(e -> {
                        if (e.getName().equalsIgnoreCase(selectedItem.getValue())) {
                            fEntity.setParent(e);
                        }
                    });
                }
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Category");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoCategory.insert(fEntity);
                        if (daoCategory.getErrorMessage() == null) {
                            fList.add(fEntity);
                            TreeItem treeItem = new TreeItem<>(fEntity.getName());
                            selectedItem.getChildren().add(treeItem);
                        } else {
                            lblWarning.setText(daoCategory.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(Category_.name).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (selectedItem != fRootNode) {
                    fList.forEach(e -> {
                        if (e.getName().equalsIgnoreCase(selectedItem.getValue())) {
                            fEntity = e;
                        }
                    });
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Category");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoCategory.update(fEntity);
                            if (daoCategory.getErrorMessage() == null) {
                                selectedItem.setValue(fEntity.getName());
                            } else {
                                lblWarning.setText(daoCategory.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(Category_.name).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (selectedItem != fRootNode && selectedItem.getChildren().isEmpty()) {
                    fList.stream().filter((category) -> (category.getName().equalsIgnoreCase(selectedItem.getValue()))).forEachOrdered((category) -> {
                        showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                            daoCategory.delete(category);
                            fList.remove(category);
                            TreeItem<String> parentItem = selectedItem.getParent();
                            parentItem.getChildren().remove(selectedItem);
                        });
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Category:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);
        fList.forEach(e -> {
            if (e.getParent() == null) {
                TreeItem parentNode = new TreeItem(e.getName());
                fRootNode.getChildren().add(parentNode);
                if (!e.getCategories().isEmpty()) {
                    addChildren(e, parentNode);
                }
            }
        });
        mainPane.add(fTreeView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");
        fTaxClassCombo.setPrefWidth(145);
        dataUI.setUIComponent(Category_.taxClass, fTaxClassCombo);
        add(editPane, "Name:*", dataUI.createTextField(Category_.name), fListener, 250.0, 0);
        add(editPane, "Price 1 Markup %:*", dataUI.createTextField(Category_.price1), fListener, 250.0, 1);
        add(editPane, "Price 2 Markup %:*", dataUI.createTextField(Category_.price2), fListener, 250.0, 2);
        add(editPane, "Price 3 Markup %:*", dataUI.createTextField(Category_.price3), fListener, 250.0, 3);
        add(editPane, "Price 4 Markup %:*", dataUI.createTextField(Category_.price4), fListener, 250.0, 4);
        add(editPane, "Price 5 Markup %:*", dataUI.createTextField(Category_.price5), fListener, 250.0, 5);
        add(editPane, "Price 6 Markup %:*", dataUI.createTextField(Category_.price6), fListener, 250.0, 6);
        add(editPane, "Tax Category*", fTaxClassCombo, fListener, 7);
        editPane.add(lblWarning, 0, 1, 2, 8);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            dataUI.getTextField(Category_.name).setText(uppercaseFirst(dataUI.getTextField(Category_.name).getText()));
            saveBtn.setDisable(dataUI.getTextField(Category_.name).getText().trim().isEmpty());
        }
    }

    private void addChildren(Category category, TreeItem<String> treeItem) {
        List<Category> list = new ArrayList<>(category.getCategories()).subList(0, category.getCategories().size());
        Collections.sort(list, categoryComparator);
        list.stream().forEach((c) -> {
            TreeItem<String> categoryLeaf = new TreeItem(c.getName());
            treeItem.getChildren().add(categoryLeaf);
            if (!c.getCategories().isEmpty()) {
                addChildren(c, categoryLeaf);
            }
        });
    }

    private final Comparator<Category> categoryComparator = (Category o1, Category o2) -> {
        String s1 = o1.getName().toUpperCase();
        String s2 = o2.getName().toUpperCase();
        return s1.compareTo(s2);
    };
}

package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.PostCode;
import com.salesliant.entity.PostCode_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.DataUI;
import com.salesliant.widget.CountryWidget;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class PostCodeListUI extends BaseListUI<PostCode> {

    private final BaseDao<PostCode> daoPostCode = new BaseDao<>(PostCode.class);
    private final DataUI dataUI = new DataUI(PostCode.class);
    private final GridPane fEditPane;
    private final CountryWidget fCountryCombo = new CountryWidget();
    private static final Logger LOGGER = Logger.getLogger(PostCodeListUI.class.getName());

    public PostCodeListUI() {
        mainView = createMainView();
        List<PostCode> list = daoPostCode.readOrderBy(PostCode_.postCode, AppConstants.ORDER_BY_ASC);
        fEntityList = FXCollections.observableList(list);
        fTableView.setItems(fEntityList);
        fEditPane = createEditPane();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                fEntity = new PostCode();
                fEntity.setCountry(Config.getStore().getCountry());
                try {
                    dataUI.setData(fEntity);
                } catch (Exception ex) {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
                fInputDialog = createSaveCancelUIDialog(fEditPane, "Post Code");
                saveBtn.setDisable(true);
                saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                    try {
                        dataUI.getData(fEntity);
                        daoPostCode.insert(fEntity);
                        if (daoPostCode.getErrorMessage() == null) {
                            fEntityList.add(fEntity);
                        } else {
                            lblWarning.setText(daoPostCode.getErrorMessage());
                            event.consume();
                        }
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                });
                Platform.runLater(() -> dataUI.getTextField(PostCode_.postCode).requestFocus());
                fInputDialog.showDialog();
                break;
            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, "Post Code");
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoPostCode.update(fEntity);
                            if (daoPostCode.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoPostCode.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    Platform.runLater(() -> dataUI.getTextField(PostCode_.postCode).requestFocus());
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoPostCode.delete(fEntity);
                        fEntityList.remove(fEntity);
                    });
                }
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label PostCodeLbl = new Label("List of PostCode:");
        mainPane.add(PostCodeLbl, 0, 1);
        GridPane.setHalignment(PostCodeLbl, HPos.LEFT);

        TableColumn<PostCode, String> postCodeCol = new TableColumn<>("Post Code");
        postCodeCol.setCellValueFactory(new PropertyValueFactory<>(PostCode_.postCode.getName()));
        postCodeCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        postCodeCol.setPrefWidth(100);

        TableColumn<PostCode, String> cityCol = new TableColumn<>("City");
        cityCol.setCellValueFactory(new PropertyValueFactory<>(PostCode_.city.getName()));
        cityCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        cityCol.setPrefWidth(250);

        TableColumn<PostCode, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>(PostCode_.state.getName()));
        stateCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        stateCol.setPrefWidth(250);

        TableColumn<PostCode, String> countryCol = new TableColumn<>("Country");
        countryCol.setCellValueFactory((CellDataFeatures<PostCode, String> p) -> {
            if (p.getValue() != null && p.getValue().getCountry() != null) {
                return new SimpleStringProperty(p.getValue().getCountry().getIsoCode3());
            } else {
                return null;
            }
        });
        countryCol.setCellFactory(stringCell(Pos.CENTER_LEFT));
        countryCol.setPrefWidth(250);

        fTableView.getColumns().add(postCodeCol);
        fTableView.getColumns().add(cityCol);
        fTableView.getColumns().add(stateCol);
        fTableView.getColumns().add(countryCol);

        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        setTableWidth(fTableView);

        mainPane.add(fTableView, 0, 2);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 3);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        dataUI.setUIComponent(PostCode_.country, fCountryCombo);
        fCountryCombo.setPrefWidth(250);
        add(editPane, "Post Code:*", dataUI.createTextField(PostCode_.postCode), fListener, 100.0, 0);
        add(editPane, "City:*", dataUI.createTextField(PostCode_.city), fListener, 250.0, 1);
        add(editPane, "State:*", dataUI.createTextField(PostCode_.state), fListener, 250.0, 2);
        add(editPane, "Country:", fCountryCombo, fListener, 3);

        editPane.add(lblWarning, 0, 3, 2, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(PostCode_.postCode).getText().trim().isEmpty()
                    || dataUI.getTextField(PostCode_.city).getText().trim().isEmpty()
                    || dataUI.getTextField(PostCode_.state).getText().trim().isEmpty());
        }
    }

}

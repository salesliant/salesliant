package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.TimeCard;
import com.salesliant.entity.TimeCard_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.stringCell;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DataUI;
import com.salesliant.widget.EmployeeWidget;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TimeCardListUI extends BaseListUI<TimeCard> {

    private final BaseDao<TimeCard> daoTimeCard = new BaseDao<>(TimeCard.class);
    private final DataUI dataUI = new DataUI(TimeCard.class);
    private EmployeeWidget fEmployeeCombo = new EmployeeWidget();
    private Timestamp fTimestamp;
    private final GridPane fEditPane;
    private final static String TITLE = "Employee Time Card";
    private static final Logger LOGGER = Logger.getLogger(TimeCardListUI.class.getName());

    public TimeCardListUI() {
        mainView = createMainView();
        fEmployeeCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Employee> observable, Employee newValue, Employee oldValue) -> {
            if (observable != null && observable.getValue() != null) {
                fEntityList = FXCollections.observableList(fEmployeeCombo.getSelectionModel().getSelectedItem().getTimeCards());
                fTableView.setItems(fEntityList);
            } else {
                fEntityList = FXCollections.observableArrayList();
                fTableView.setItems(fEntityList);
            }
        });
        fEditPane = createEditPane();
        dialogTitle = TITLE;
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_ADD:
                if (fEmployeeCombo.getSelectionModel().getSelectedItem() != null) {
                    fEntity = new TimeCard();
                    fEntity.setEmployee(fEmployeeCombo.getSelectionModel().getSelectedItem());
                    fEntity.setStore(Config.getStore());
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoTimeCard.insert(fEntity);
                            if (daoTimeCard.getErrorMessage() == null) {
                                fEntityList.add(fEntity);
                                fTableView.scrollTo(fEntity);
                                fTableView.getSelectionModel().select(fEntity);
                            } else {
                                lblWarning.setText(daoTimeCard.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;

            case AppConstants.ACTION_EDIT:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    fInputDialog = createSaveCancelUIDialog(fEditPane, TITLE);
                    saveBtn.setDisable(true);
                    saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
                        try {
                            dataUI.getData(fEntity);
                            daoTimeCard.update(fEntity);
                            if (daoTimeCard.getErrorMessage() == null) {
                                fTableView.refresh();
                            } else {
                                lblWarning.setText(daoTimeCard.getErrorMessage());
                                event.consume();
                            }
                        } catch (Exception ex) {
                            LOGGER.log(Level.SEVERE, null, ex);
                        }
                    });
                    fInputDialog.showDialog();
                }
                break;
            case AppConstants.ACTION_DELETE:
                if (fTableView.getSelectionModel().getSelectedItem() != null) {
                    fEntity = fTableView.getSelectionModel().getSelectedItem();
                    showConfirmDialog("Are you sure to delete the seleted entry?", (ActionEvent e) -> {
                        daoTimeCard.delete(fEntity);
                        fEntityList.remove(fEntity);
                        if (fEntityList.isEmpty()) {
                            fTableView.getSelectionModel().select(null);
                        }
                    });
                }
                break;
            case AppConstants.ACTION_CLOCK_IN:
                if (fEntity.getTimeIn() != null) {
                    fTimestamp = fEntity.getTimeIn();
                } else {
                    fTimestamp = new Timestamp(new Date().getTime());
                }
                TimeCardUI clockInUI = new TimeCardUI(fTimestamp);
                Stage clockInDialog = createUIDialog(clockInUI.getView(), TITLE);
                clockInUI.saveButton.setOnAction((ActionEvent e) -> {
                    fEntity.setTimeIn(clockInUI.getTimestamp());
                    if (fEntity.getTimeOut() != null) {
                        long diffSeconds = (fEntity.getTimeOut().getTime() - fEntity.getTimeIn().getTime()) / 1000;
                        BigDecimal actualTime = new BigDecimal(diffSeconds);
                        BigDecimal seconds = new BigDecimal(3600);
                        BigDecimal hours = actualTime.divide(seconds, 3, RoundingMode.CEILING);
                        fEntity.setHours(hours);
                    } else {
                        fEntity.setHours(null);
                    }
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    clockInDialog.close();
                });
                clockInUI.cancelButton.addEventFilter(ActionEvent.ACTION, event -> {
                    clockInDialog.close();
                });
                clockInDialog.show();
                break;
            case AppConstants.ACTION_CLOCK_OUT:
                if (fEntity.getTimeOut() != null) {
                    fTimestamp = fEntity.getTimeOut();
                } else {
                    fTimestamp = new Timestamp(new Date().getTime());
                }
                TimeCardUI clockOutUI = new TimeCardUI(fTimestamp);
                Stage clockOutDialog = createUIDialog(clockOutUI.getView(), TITLE);
                clockOutUI.saveButton.setOnAction((ActionEvent e) -> {
                    fEntity.setTimeOut(clockOutUI.getTimestamp());
                    if (fEntity.getTimeIn() != null) {
                        long diffSeconds = (fEntity.getTimeOut().getTime() - fEntity.getTimeIn().getTime()) / 1000;
                        BigDecimal actualTime = new BigDecimal(diffSeconds);
                        BigDecimal seconds = new BigDecimal(3600);
                        BigDecimal hours = actualTime.divide(seconds, 3, RoundingMode.CEILING);
                        fEntity.setHours(hours);
                    } else {
                        fEntity.setHours(null);
                    }
                    try {
                        dataUI.setData(fEntity);
                    } catch (Exception ex) {
                        LOGGER.log(Level.SEVERE, null, ex);
                    }
                    clockOutDialog.close();
                });
                clockOutUI.cancelButton.addEventFilter(ActionEvent.ACTION, event -> {
                    clockOutDialog.close();
                });
                clockOutDialog.show();
                break;
        }
    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        Label tableLbl = new Label("List of Time Card by Employee:");
        mainPane.add(tableLbl, 0, 1);
        GridPane.setHalignment(tableLbl, HPos.LEFT);

        fEmployeeCombo.setPrefWidth(250);

        TableColumn<TimeCard, String> clockInTimeCol = new TableColumn<>("Clock In Time");
        clockInTimeCol.setCellValueFactory(new PropertyValueFactory<>(TimeCard_.timeIn.getName()));
        clockInTimeCol.setCellFactory(stringCell(Pos.CENTER));
        clockInTimeCol.setPrefWidth(200);

        TableColumn<TimeCard, String> clockOutTimeCol = new TableColumn<>("Clock Out Time");
        clockOutTimeCol.setCellValueFactory(new PropertyValueFactory<>(TimeCard_.timeOut.getName()));
        clockOutTimeCol.setCellFactory(stringCell(Pos.CENTER));
        clockOutTimeCol.setPrefWidth(200);

        TableColumn<TimeCard, String> hoursCol = new TableColumn<>("Hours");
        hoursCol.setCellValueFactory(new PropertyValueFactory<>(TimeCard_.hours.getName()));
        hoursCol.setCellFactory(stringCell(Pos.CENTER));
        hoursCol.setPrefWidth(110);

        fTableView.getColumns().add(clockInTimeCol);
        fTableView.getColumns().add(clockOutTimeCol);
        fTableView.getColumns().add(hoursCol);
        fTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        fTableView.setPrefHeight(350);
        fTableView.setEditable(false);
        setTableWidth(fTableView);

        mainPane.add(fEmployeeCombo, 0, 2);
        mainPane.add(fTableView, 0, 3);
        mainPane.add(createNewEditDeleteCloseButtonPane(), 0, 4);
        return mainPane;
    }

    private GridPane createEditPane() {
        GridPane editPane = new GridPane();
        editPane.getStyleClass().add("editView");

        Button clockInBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT);
        clockInBtn.setId(AppConstants.ACTION_CLOCK_IN);
        clockInBtn.setOnAction(fHandler);

        Button clockOutBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_SELECT);
        clockOutBtn.setId(AppConstants.ACTION_CLOCK_OUT);
        clockOutBtn.setOnAction(fHandler);

        add(editPane, "Clock In:*", dataUI.createTextField(TimeCard_.timeIn), fListener, 150.0, 1);
        add(editPane, "Clock Out:*", dataUI.createTextField(TimeCard_.timeOut), fListener, 150.0, 2);
        add(editPane, "Hours:*", dataUI.createTextField(TimeCard_.hours), fListener, 150.0, 3);
        editPane.add(clockInBtn, 2, 1);
        editPane.add(clockOutBtn, 2, 2);

        dataUI.getTextField(TimeCard_.timeIn).setEditable(false);
        dataUI.getTextField(TimeCard_.timeOut).setEditable(false);
        dataUI.getTextField(TimeCard_.hours).setEditable(false);

        editPane.add(lblWarning, 1, 4, 3, 1);

        return editPane;
    }

    @Override
    protected void validate() {
        if (fInputDialog != null) {
            saveBtn.setDisable(dataUI.getTextField(TimeCard_.timeIn).getText().trim().isEmpty()
                    || dataUI.getTextField(TimeCard_.timeOut).getText().trim().isEmpty()
                    || dataUI.getTextField(TimeCard_.hours).getText().trim().isEmpty()
                    || dataUI.getTextField(TimeCard_.hours).getText().trim().contains("-"));
        }
    }
}

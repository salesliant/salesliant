package com.salesliant.ui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.DateTimePicker;
import java.sql.Timestamp;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class TimeCardUI extends BaseListUI<Timestamp> {

    public Button saveButton = ButtonFactory.getButton(ButtonFactory.BUTTON_SAVE);
    public Button cancelButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
    private final Timestamp fTimestamp;
    private final DateTimePicker clockTime = new DateTimePicker();

    public TimeCardUI(Timestamp timestamp) {
        this.fTimestamp = timestamp;
        mainView = createMainView();
        clockTime.setDateTimeValue(fTimestamp.toLocalDateTime());
        clockTime.setTimeValue(fTimestamp.toLocalDateTime().toLocalTime());
        dialogTitle = "Time Card";

    }

    private Node createMainView() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setVgap(3.0);
        mainPane.setHgap(3.0);
        mainPane.add(clockTime, 0, 0);
        mainPane.add(createButtonPane(), 0, 1);
        return mainPane;
    }

    protected HBox createButtonPane() {
        HBox buttons = new HBox();
        buttons.getChildren().add(saveButton);
        buttons.getChildren().add(cancelButton);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        buttons.setSpacing(6);
        return buttons;
    }

    public Timestamp getTimestamp() {
        return Timestamp.valueOf(clockTime.getDateTimeValue());
    }

    @Override
    protected void validate() {

    }

    @Override
    public void handleAction(String code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

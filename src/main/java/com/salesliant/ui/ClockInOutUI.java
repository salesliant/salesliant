package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.entity.Employee_;
import com.salesliant.entity.TimeCard;
import com.salesliant.entity.TimeCard_;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseDao;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.buttonSetWidth;
import static com.salesliant.util.BaseUtil.getDateTimeFormat;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.IconFactory;
import com.salesliant.util.RES;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Lewis
 */
public class ClockInOutUI extends BaseListUI<Employee> {

    private final BaseDao<Employee> daoEmployee = new BaseDao<>(Employee.class);
    private final BaseDao<TimeCard> daoTimeCard = new BaseDao<>(TimeCard.class);
    private Employee fEmployee;
    private TimeCard fTimeCard;
    private final TextField userNameFld = new TextField();
    private final PasswordField passwordFld = new PasswordField();
    private final Label employeeLabel = new Label(""), currentTimeLabel = new Label(""), lastLoginLabel = new Label("");
    private final static String CLOCK_IN = "Clock_In";
    private final static String CLOCK_OUT = "Clock_Out";
    private final Button clockInButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOCK, AppConstants.ACTION_CLOCK_IN, fHandler);
    private final Button clockOutButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CLOCK, AppConstants.ACTION_CLOCK_OUT, fHandler);

    public ClockInOutUI() {
        init();
    }

    @Override
    public void handleAction(String code) {
        switch (code) {
            case AppConstants.ACTION_LOGIN:
                fInputDialog = createUIDialog(createLoginPane(), "Clock In/Out");
                fInputDialog.show();
                break;
            case AppConstants.ACTION_CLOCK:
                String userName = userNameFld.getText();
                String password = passwordFld.getText();
                List<Employee> employeeList = daoEmployee.read(Employee_.store, Config.getStore());
                fEmployee = employeeList.stream().filter(e -> e.getLogin().equalsIgnoreCase(userName) && e.getPassword().equalsIgnoreCase(password)).findFirst().orElse(null);
                if (fEmployee != null) {
                    fInputDialog.close();
                    fInputDialog = createUIDialog(createTimeCardPane(), "Time Clock");
                    String name = getString(fEmployee.getFirstName()) + " " + getString(fEmployee.getLastName());
                    employeeLabel.setText(name);
                    String currentTime = getDateTimeFormat().format(daoTimeCard.getSystemDate());
                    currentTimeLabel.setText(currentTime);
                    List<TimeCard> timeCardList = daoTimeCard.read(TimeCard_.store, Config.getStore(), TimeCard_.employee, fEmployee.getId()).stream().sorted((e1, e2) -> Integer.compare(e1.getId(), e2.getId())).collect(Collectors.toList());
                    if (timeCardList.isEmpty()) {
                        lastLoginLabel.setText("None");
                        clockInButton.setDisable(false);
                        clockOutButton.setDisable(true);
                    } else {
                        fTimeCard = timeCardList.get(timeCardList.size() - 1);
                        if (fTimeCard.getHours() == null) {
                            lastLoginLabel.setText(CLOCK_IN);
                            clockInButton.setDisable(true);
                            clockOutButton.setDisable(false);
                        } else {
                            lastLoginLabel.setText(CLOCK_OUT);
                            clockInButton.setDisable(false);
                            clockOutButton.setDisable(true);
                        }
                    }
                    fInputDialog.show();
                } else {
                    lblWarning.setText("Try again");
                }
                break;

            case AppConstants.ACTION_CLOCK_IN:
                fInputDialog.close();
                TimeCard clockInTimeCard = new TimeCard();
                clockInTimeCard.setEmployee(fEmployee);
                clockInTimeCard.setStore(Config.getStore());
                clockInTimeCard.setTimeIn(new Timestamp(daoTimeCard.getSystemDate().getTime()));
                daoTimeCard.insert(clockInTimeCard);
                break;
            case AppConstants.ACTION_CLOCK_OUT:
                fInputDialog.close();
                if (fTimeCard != null && fTimeCard.getHours() == null && fTimeCard.getTimeIn() != null) {
                    fTimeCard.setTimeOut(new Timestamp(daoTimeCard.getSystemDate().getTime()));
                    long diff = fTimeCard.getTimeOut().getTime() - fTimeCard.getTimeIn().getTime();
                    long diffSeconds = diff / 1000;
                    BigDecimal actualTime = new BigDecimal(diffSeconds);
                    BigDecimal seconds = new BigDecimal(3600);
                    BigDecimal hours = actualTime.divide(seconds, 3, RoundingMode.CEILING);
                    fTimeCard.setHours(hours);
                    daoTimeCard.update(fTimeCard);
                } else {
                    TimeCard clockOutTimeCard = new TimeCard();
                    clockOutTimeCard.setEmployee(fEmployee);
                    clockOutTimeCard.setTimeOut(new Timestamp(daoTimeCard.getSystemDate().getTime()));
                    daoTimeCard.insert(clockOutTimeCard);
                }
                break;
        }
    }

    private void init() {
        handleAction(AppConstants.ACTION_LOGIN);
    }

    private HBox createLoginPane() {
        GridPane loginPane = new GridPane();
        loginPane.setPadding(new Insets(5));
        loginPane.setHgap(8);
        loginPane.setVgap(10);
        ImageView iv = new ImageView(IconFactory.getIcon(RES.CLOCK_IN));
        Label mainLabel = new Label("Enter User Name & Password");
        loginPane.add(mainLabel, 1, 0, 2, 1);
        Label userNameLbl = new Label("User Name: ");
        loginPane.add(userNameLbl, 0, 1);
        loginPane.add(userNameFld, 1, 1);
        Label passwordLbl = new Label("Password: ");
        loginPane.add(passwordLbl, 0, 2);
        loginPane.add(passwordFld, 1, 2);
        userNameFld.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblWarning.setText("");
        });
        userNameFld.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        passwordFld.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            lblWarning.setText("");
        });
        passwordFld.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        Button loginButton = ButtonFactory.getButton(ButtonFactory.BUTTON_YES);
        Button cancelButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
        loginButton.setText("Login");
        loginButton.setOnAction((ActionEvent e) -> {
            handleAction(AppConstants.ACTION_CLOCK);
        });
        loginButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loginButton.fire();
                ev.consume();
            }
        });
        buttonSetWidth(loginButton);
        cancelButton.setOnAction((ActionEvent e) -> {
            fInputDialog.close();
        });
        cancelButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelButton.fire();
                ev.consume();
            }
        });
        buttonSetWidth(cancelButton);
        HBox btnBox = new HBox();
        btnBox.setSpacing(5);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        btnBox.getChildren().addAll(loginButton, cancelButton);
        loginPane.add(btnBox, 1, 3);
        loginPane.add(lblWarning, 0, 4, 2, 1);
        GridPane.setHalignment(btnBox, HPos.RIGHT);
        GridPane.setHalignment(passwordLbl, HPos.RIGHT);
        GridPane.setHalignment(userNameLbl, HPos.RIGHT);

        HBox loginBox = new HBox();
        loginBox.getChildren().addAll(iv, loginPane);
        loginBox.setAlignment(Pos.CENTER);
        loginPane.setAlignment(Pos.CENTER_RIGHT);

        return loginBox;
    }

    private GridPane createTimeCardPane() {
        GridPane timeCardPane = new GridPane();
        timeCardPane.setPadding(new Insets(10));
        timeCardPane.setHgap(8);
        timeCardPane.setVgap(10);
        Label nameLabel = new Label("Name: ");
        timeCardPane.add(nameLabel, 0, 0);
        timeCardPane.add(employeeLabel, 1, 0);
        nameLabel.setPrefWidth(130);
        employeeLabel.setPrefWidth(130);
        nameLabel.setAlignment(Pos.CENTER_RIGHT);
        Label timeLabel = new Label("Current Time: ");
        timeCardPane.add(timeLabel, 0, 1);
        timeCardPane.add(currentTimeLabel, 1, 1);
        Label lastLabel = new Label("Last Action Was: ");
        timeCardPane.add(lastLabel, 0, 2);
        timeCardPane.add(lastLoginLabel, 1, 2);
        Separator sp = new Separator();
        sp.setPrefHeight(10);
        timeCardPane.add(sp, 0, 3, 2, 1);
        clockInButton.setText("Clock In");
        clockInButton.setPrefWidth(90);
        clockOutButton.setText("Clock Out");
        clockOutButton.setPrefWidth(90);
        clockInButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                clockInButton.fire();
                ev.consume();
            }
        });
        clockOutButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                clockOutButton.fire();
                ev.consume();
            }
        });
        Button cancelButton = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
        cancelButton.setOnAction((ActionEvent e) -> {
            fInputDialog.close();
        });
        cancelButton.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelButton.fire();
                ev.consume();
            }
        });
        HBox btnBox = new HBox(8);
        btnBox.getChildren().addAll(clockInButton, clockOutButton, cancelButton);
        timeCardPane.add(btnBox, 0, 4, 2, 1);
        GridPane.setHalignment(nameLabel, HPos.RIGHT);
        GridPane.setHalignment(employeeLabel, HPos.LEFT);
        GridPane.setHalignment(timeLabel, HPos.RIGHT);
        GridPane.setHalignment(currentTimeLabel, HPos.LEFT);
        GridPane.setHalignment(lastLabel, HPos.RIGHT);
        GridPane.setHalignment(lastLoginLabel, HPos.LEFT);
        GridPane.setHalignment(clockInButton, HPos.LEFT);
        GridPane.setHalignment(clockOutButton, HPos.RIGHT);
        GridPane.setHalignment(cancelButton, HPos.CENTER);
        timeCardPane.setAlignment(Pos.CENTER_RIGHT);

        return timeCardPane;
    }
}

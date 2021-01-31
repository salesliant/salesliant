package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.IconFactory;
import com.salesliant.util.InputDialog;
import static com.salesliant.util.InputDialog.getDialog;
import com.salesliant.util.RES;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
public class LoginUI {

    private final TextField userNameFld = new TextField();
    private final PasswordField passwordFld = new PasswordField();
    private final Label warningLabel = new Label();
    private final InputDialog fInputDialog;

    public LoginUI() {
        fInputDialog = getDialog(createLoginPane(), "Login");
        fInputDialog.showAndWait();
    }

    public static LoginUI login() {
        LoginUI loginUI = new LoginUI();
        return loginUI;
    }

    private HBox createLoginPane() {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setVgap(8);
        ImageView iv = new ImageView(IconFactory.getIcon(RES.LOGIN_IN_ICON));

        Label userNameLbl = new Label("User Name: ");
        gridpane.add(userNameLbl, 0, 1);
        gridpane.add(userNameFld, 1, 1);
        Label passwordLbl = new Label("Password: ");
        gridpane.add(passwordLbl, 0, 2);
        gridpane.add(passwordFld, 1, 2);
        userNameFld.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        passwordFld.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        gridpane.add(warningLabel, 1, 3, 2, 1);
        Button loginBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_LOGIN);
        Button cancelLoginBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
        loginBtn.setOnAction((ActionEvent event) -> {
            loginBtnAction();
        });
        cancelLoginBtn.setOnAction((ActionEvent event) -> {
            cancelBtnAction();
        });
        loginBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loginBtn.fire();
                ev.consume();
            }
        });
        cancelLoginBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelLoginBtn.fire();
                ev.consume();
            }
        });

        HBox loginButtonBox = new HBox();
        loginButtonBox.getChildren().addAll(loginBtn, cancelLoginBtn);
        loginButtonBox.setAlignment(Pos.CENTER_RIGHT);
        loginButtonBox.setSpacing(5);
        gridpane.add(loginButtonBox, 1, 5, 2, 1);

        GridPane.setHalignment(passwordLbl, HPos.RIGHT);
        GridPane.setHalignment(userNameLbl, HPos.RIGHT);

        HBox loginBox = new HBox();
        loginBox.getChildren().addAll(iv, gridpane);
        loginBox.setAlignment(Pos.CENTER);
        gridpane.setAlignment(Pos.CENTER_RIGHT);
        return loginBox;
    }

    private void loginBtnAction() {
        if (Config.checkLogin(userNameFld.getText(), passwordFld.getText())) {
            fInputDialog.close();
        } else {
            warningLabel.setText("Invalid usernamr or password");
        }
    }

    private void cancelBtnAction() {
        fInputDialog.close();
    }
}

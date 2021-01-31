package com.salesliant.client;

import com.salesliant.entity.Store;
import com.salesliant.mvc.AbstractView;
import com.salesliant.util.BaseDao;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.IconFactory;
import com.salesliant.util.InputDialog;
import static com.salesliant.util.InputDialog.getDialog;
import com.salesliant.util.RES;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

/**
 *
 * @author Lewis
 */
public class LoginDialogView extends AbstractView {

    private final ClientController controller;
    private final BaseDao<Store> daoStore = new BaseDao<>(Store.class);
    private final TextField userNameFld = new TextField();
    private final ComboBox<Store> storeBox = new ComboBox();
    private final PasswordField passwordFld = new PasswordField();
    private final Label warningLabel = new Label();
    protected ObservableList<Store> fEntityList;
    private final InputDialog fInputDialog;
    private final Button loginBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_LOGIN);
    private final Button createBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_ADD, "Create New Store");

    public LoginDialogView(ClientController controller) {
        this.controller = controller;

        fInputDialog = getDialog(createLoginPane(), "Login");
        if (Config.getStore() != null) {
            List<Store> storeList = new ArrayList();
            storeList.add(Config.getStore());
            fEntityList = FXCollections.observableList(storeList);
            storeBox.setItems(fEntityList);
            storeBox.getSelectionModel().select(Config.getStore());
        } else {
            warningLabel.setText("Not Store Found");
            loginBtn.setDisable(true);
            createBtn.setVisible(true);
        }
        ClientView.MAIN_PANE.setBackground(null);
        fInputDialog.show();
    }

    private HBox createLoginPane() {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setVgap(8);
        ImageView iv = new ImageView(IconFactory.getIcon(RES.LOGIN_IN_ICON));
        HBox createButtonBox = new HBox();
        createButtonBox.getChildren().addAll(createBtn);
        createButtonBox.setAlignment(Pos.CENTER_RIGHT);
        gridpane.add(createButtonBox, 1, 0, 2, 1);
        createBtn.setVisible(false);
        createBtn.setOnAction((ActionEvent event) -> {
            createBtnAction();
        });
        Label userNameLbl = new Label("User Name: ");
        gridpane.add(userNameLbl, 0, 1);
        gridpane.add(userNameFld, 1, 1);
        Label passwordLbl = new Label("Password: ");
        gridpane.add(passwordLbl, 0, 2);
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
        gridpane.add(passwordFld, 1, 2);
        List<Store> list = daoStore.read();
        fEntityList = FXCollections.observableList(list);
        storeBox.setItems(fEntityList);
        storeBox.setPrefWidth(220);
        storeBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                Config.setStore(newValue);
                loginBtn.setDisable(false);
                createBtn.setVisible(false);
                warningLabel.setText("");
            }
        });
        storeBox.setCellFactory((ListView<Store> s) -> new ListCell<Store>() {
            @Override
            protected void updateItem(Store item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getStoreName());
                }
            }
        });
        storeBox.setConverter(new StringConverter<Store>() {
            @Override
            public String toString(Store store) {
                if (store == null) {
                    return null;
                } else {
                    return store.getStoreName();
                }
            }

            @Override
            public Store fromString(String store) {
                return null;
            }
        });
        storeBox.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                KeyEvent newEvent = new KeyEvent(null, null, KeyEvent.KEY_PRESSED, "", "\t", KeyCode.TAB, event.isShiftDown(), event.isControlDown(), event.isAltDown(), event.isMetaDown());
                Event.fireEvent(event.getTarget(), newEvent);
                event.consume();
            }
        });
        Label storeLbl = new Label("Store: ");
        gridpane.add(storeLbl, 0, 3);
        gridpane.add(storeBox, 1, 3);
        gridpane.add(warningLabel, 1, 4, 2, 1);
        Button cancelBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_CANCEL);
        loginBtn.setOnAction((ActionEvent event) -> {
            loginBtnAction();
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            cancelBtnAction();
        });
        loginBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                loginBtn.fire();
                ev.consume();
            }
        });
        cancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelBtn.fire();
                ev.consume();
            }
        });

        HBox loginButtonBox = new HBox();
        loginButtonBox.getChildren().addAll(loginBtn, cancelBtn);
        loginButtonBox.setAlignment(Pos.CENTER_RIGHT);
        loginButtonBox.setSpacing(5);
        gridpane.add(loginButtonBox, 1, 5, 2, 1);

        GridPane.setHalignment(passwordLbl, HPos.RIGHT);
        GridPane.setHalignment(userNameLbl, HPos.RIGHT);
        GridPane.setHalignment(storeLbl, HPos.RIGHT);

        HBox loginBox = new HBox();
        loginBox.getChildren().addAll(iv, gridpane);
        loginBox.setAlignment(Pos.CENTER);
        gridpane.setAlignment(Pos.CENTER_RIGHT);
        gridpane.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.isControlDown() && event.getCode() == KeyCode.C) {
                createBtnAction();
                event.consume();
            }
        });
        return loginBox;
    }

    @Override
    public void updateView(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ClientController.USERNAME_PASSWORD)) {
            if (evt.getNewValue().toString().equals("true")) {
                fInputDialog.close();
            } else {
                warningLabel.setText("Invalid usernamr or password");
            }
        }
    }

    private void loginBtnAction() {
        if (Config.getStore() != null) {
            List<String> loginPair = Arrays.asList(userNameFld.getText(), passwordFld.getText());
            controller.sendLoginInfo(loginPair);
        } else {
            warningLabel.setText("Not Store is selected");
        }
    }

    private void createBtnAction() {
        Config.setStore(null);
        controller.sendCheckSetUp();
    }

    private void cancelBtnAction() {
        fInputDialog.close();
        ClientApp.primaryStage.close();
    }

    public void close() {
        fInputDialog.close();
    }

    public void hide() {
        fInputDialog.hide();
    }

    public void show() {
        fInputDialog.show();
    }
}

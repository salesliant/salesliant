/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.client;

import com.salesliant.mvc.AbstractView;
import com.salesliant.mvc.Controller;
import com.salesliant.mvc.View;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.MenuLoader;
import com.salesliant.util.RES;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 *
 * @author Lewis
 */
public class ClientView extends AbstractView {

    private final ClientController controller;

    private MenuBar fMenuBar = null;
    private ToolBar fToolBar = null;
    private ChoiceBox<String> fModuleBox = null;
    public static final BorderPane MAIN_PANE = new BorderPane();
    public static LoginDialogView fLoginDialog;
    public static final TabPane MAIN_TAB_PANE = new TabPane();
    public static MenuLoader fLoader;
    private static final Logger LOGGER = Logger.getLogger(ClientView.class.getName());

    public ClientView(ClientController c) {
        this.controller = c;
    }

    public void init() {
        MenuBar aMenuBar = new MenuBar();
        Menu windowsMenu = new Menu("Windows");
        Menu exitMenu = new Menu("Exit");
        aMenuBar.getMenus().addAll(windowsMenu, exitMenu);
        aMenuBar.setMinWidth(3000);
        MAIN_PANE.setTop(aMenuBar);
        MAIN_TAB_PANE.getTabs().clear();
        MAIN_TAB_PANE.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (MAIN_TAB_PANE.getTabs().isEmpty()) {
                setBackGround();
            } else {
                MAIN_PANE.setBackground(null);
            }
        });
        this.getChildren().add(MAIN_PANE);
        fLoginDialog = new LoginDialogView(controller);
        controller.addView(fLoginDialog);
    }

    private void mainView() {
        MAIN_PANE.prefHeightProperty().bind(ClientApp.primaryStage.getScene().heightProperty());
        MAIN_PANE.prefWidthProperty().bind(ClientApp.primaryStage.getScene().widthProperty());
        MAIN_PANE.setFocusTraversable(false);
        MAIN_TAB_PANE.setSide(Side.TOP);
        MAIN_PANE.setCenter(MAIN_TAB_PANE);
        setBackGround();
        fLoader = new MenuLoader((EventHandler<?>) (Event t) -> {
            String name = null;
            String cmd = null;
            Boolean findTab = false;
            if (t.getSource() instanceof MenuItem) {
                name = ((MenuItem) t.getSource()).getText();
                cmd = ((MenuItem) t.getSource()).getId();
            }
            if (t.getSource() instanceof Button) {
                name = ((Button) t.getSource()).getTooltip().getText();
                cmd = ((Button) t.getSource()).getId();
            }
            if (MAIN_TAB_PANE.getTabs().size() >= 1) {
                for (int i = 0; i < MAIN_TAB_PANE.getTabs().size(); i++) {
                    if (MAIN_TAB_PANE.getTabs().get(i).getText().equals(name)) {
                        MAIN_TAB_PANE.getSelectionModel().select(i);
                        findTab = true;
                    }
                }
            }
            if (!findTab) {
                if (cmd != null && cmd.endsWith("UI")) {
                    BaseListUI<?> ui = findViewClass(cmd);
                    if (ui != null) {
                        ui.setController(controller);
                        if (ui.getView() != null) {
                            Node node = ui.getView();
                            Tab aTab = new Tab();
                            aTab.setText(name);
                            aTab.setContent(node);
                            aTab.setId(cmd);
                            aTab.setClosable(false);
                            MAIN_TAB_PANE.setFocusTraversable(false);
                            MAIN_TAB_PANE.getTabs().add(aTab);
                            MAIN_TAB_PANE.getSelectionModel().select(aTab);
                        }

                    }
                } else {
                    Controller c = findControllerClass(cmd);
                    if (c != null) {
                        View aView = (View) c.getView();
                        aView.setAlignment(Pos.CENTER);
                        Tab aTab = new Tab();
                        aTab.setText(name);
                        aTab.setContent(aView);
                        aTab.setId(cmd);
                        aTab.setClosable(aView.setClosable());
                        MAIN_TAB_PANE.getTabs().add(aTab);
                        MAIN_TAB_PANE.getSelectionModel().select(aTab);
                    }
                }
            }
        });

        fModuleBox = fLoader.findChoiceBox();
        fModuleBox.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
            fMenuBar = fLoader.findMenuBar(new_val);
            fMenuBar.setMinWidth(850);
            HBox menuBox = new HBox();
            menuBox.getChildren().add(fMenuBar);
            HBox.setHgrow(fMenuBar, Priority.ALWAYS);
            fToolBar = fLoader.findToolBar(new_val);
            if (fToolBar != null) {
                fToolBar.setMinWidth(800);
            }
            VBox topBox = new VBox();
            topBox.setSpacing(0);
            topBox.getChildren().add(menuBox);
            if (fToolBar != null) {
                topBox.getChildren().add(fToolBar);
            }
            MAIN_PANE.setTop(topBox);
        });
        if (Config.getEmployee() != null) {
            int j = 0;
            for (int i = 0; i < fModuleBox.getItems().size(); i++) {
                if (Config.getEmployee().getEmployeeGroup().getModuleName().equalsIgnoreCase(fModuleBox.getItems().get(i))) {
                    j = i;
                }
            }
            if (Config.getEmployee().getLogin().equalsIgnoreCase("pos")) {
                int k = 0;
                for (int i = 0; i < fModuleBox.getItems().size(); i++) {
                    if (fModuleBox.getItems().get(i).contains("Manager")) {
                        k = i;
                    }
                }
                fModuleBox.getSelectionModel().select(k);
            } else {
                fModuleBox.getSelectionModel().select(j);
            }
        } else {
            int l = 0;
            for (int i = 0; i < fModuleBox.getItems().size(); i++) {
                if (fModuleBox.getItems().get(i).contains("Setup")) {
                    l = i;
                }
            }
            fModuleBox.getSelectionModel().select(l);
        }
        this.getChildren().add(MAIN_PANE);
    }

    @Override
    public void updateView(PropertyChangeEvent event) {
        if (event.getPropertyName().equals(ClientController.USERNAME_PASSWORD)) {
            if (event.getNewValue().toString().equals("true")) {
                this.getChildren().clear();
                mainView();
                fLoginDialog.close();
                String title = "";
                if (Config.getStore() != null) {
                    title = title + getString(Config.getStore().getStoreName()) + "     ";
                }
                if (Config.getStation() != null) {
                    title = title + "Stataion:" + getString(Config.getStation().getNumber()) + "    ";
                }
                if (Config.getEmployee() != null) {
                    title = title + "  User:" + getString(Config.getEmployee().getFirstName()) + " " + getString(Config.getEmployee().getLastName());
                }
                ClientApp.primaryStage.setTitle(title);
            }
        }
        if (event.getPropertyName().equals(ClientController.SET_UP)) {
            if (event.getNewValue().toString().equals("true")) {
                this.getChildren().clear();
                fLoginDialog.close();
                mainView();
                for (Menu mu : fMenuBar.getMenus()) {
                    for (MenuItem mi : mu.getItems()) {
                        if (mi != null && mi.getId() != null && mi.getId().equalsIgnoreCase("com.salesliant.ui.StoreUI")) {
                            mi.fire();
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void closeTab(String name) {
        for (int i = 0; i < MAIN_TAB_PANE.getTabs().size(); i++) {
            Tab aTab = MAIN_TAB_PANE.getTabs().get(i);
            if (aTab.getId().equals(name)) {
                MAIN_TAB_PANE.getTabs().remove(i);
            }
        }
    }

    public static void refreshTab(String title) {
        for (int i = 0; i < MAIN_TAB_PANE.getTabs().size(); i++) {
            Tab aTab = MAIN_TAB_PANE.getTabs().get(i);
            if (aTab.getId().equals(title)) {
                String cmd = aTab.getId();
                String name = aTab.getText();
                MAIN_TAB_PANE.getTabs().remove(i);
                BaseListUI<?> ui = findViewClass(cmd);
                if (ui != null) {
                    Node node = ui.getView();
                    Tab tab = new Tab();
                    tab.setText(name);
                    tab.setContent(node);
                    tab.setId(cmd);
                    MAIN_TAB_PANE.getTabs().add(tab);
                    MAIN_TAB_PANE.getSelectionModel().select(tab);
                }
            }
        }
    }

    private static BaseListUI<?> findViewClass(String cmd) {
        BaseListUI<?> ui = null;
        try {
            Class clasz = Class.forName(cmd);
            Constructor constructor = clasz.getConstructor(new Class[]{});
            ui = (BaseListUI<?>) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            System.err.println("\nClass no found:" + cmd);
        }
        return ui;
    }

    private Controller findControllerClass(String cmd) {
        Controller c = null;
        try {
            Class clasz = Class.forName(cmd);
            Constructor constructor = clasz.getConstructor(new Class[]{});
            c = (Controller) constructor.newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException
                | InstantiationException | IllegalAccessException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            System.err.println("\nClass no found:" + cmd);
        }
        return c;
    }

    private void setBackGround() {
        Image image = new Image(RES.BACKGROUND_IMAGE);
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        MAIN_PANE.setBackground(background);
    }
}

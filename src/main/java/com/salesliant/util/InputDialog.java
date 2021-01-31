/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.client.ClientApp;
import com.salesliant.util.AppConstants.Response;
import com.salesliant.util.BaseUtil.Message;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Lewis
 */
public class InputDialog extends Stage {

    private static Response buttonSelected = Response.CANCEL;

    public InputDialog(String title, Stage owner, Scene scene, String iconFile) {
        setTitle(title);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setScene(scene);
    }

    public InputDialog(String title, Stage owner, Scene scene) {
        setTitle(title);
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(owner);
        setResizable(false);
        setScene(scene);
    }

    public void showDialog() {
        sizeToScene();
        centerOnScreen();
        showAndWait();
    }

    public static InputDialog getEditDialog(Button okBtn, Button cancelBtn, Node node, String title) {
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(okBtn, cancelBtn);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog(title, ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        okBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        okBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                okBtn.fire();
                ev.consume();
            }
        });
        cancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelBtn.fire();
                ev.consume();
            }
        });
        content.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.F10 && !okBtn.isDisabled()) {
                okBtn.fire();
            }
            if (t.getCode() == KeyCode.ESCAPE) {
                cancelBtn.fire();
            }
        });
        return dialog;
    }

    public static InputDialog getEditDialog(Button okBtn, Button cancelBtn, Button resetBtn, Node node, String title) {
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(resetBtn, okBtn, cancelBtn);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        HBox.setHgrow(resetBtn, Priority.ALWAYS);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog(title, ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        okBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        resetBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        okBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                okBtn.fire();
                ev.consume();
            }
        });
        resetBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                resetBtn.fire();
                ev.consume();
            }
        });
        cancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelBtn.fire();
                ev.consume();
            }
        });
        return dialog;
    }

    public static Response getSelectResponseDialog(Button okBtn, Button cancelBtn, Node node, String title) {
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(okBtn, cancelBtn);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog(title, ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        okBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
            buttonSelected = Response.SELECT;
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
            buttonSelected = Response.NO;
        });
        okBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                okBtn.fire();
                ev.consume();
            }
        });
        cancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelBtn.fire();
                ev.consume();
            }
        });
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        dialog.showDialog();
        return buttonSelected;
    }

    public static Response getSaveCancelResponseDialog(Button saveBtn, Button cancelBtn, Node node, String title) {
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(3);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(saveBtn, cancelBtn);
        HBox.setHgrow(saveBtn, Priority.ALWAYS);
        HBox.setHgrow(cancelBtn, Priority.ALWAYS);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog(title, ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        saveBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
            buttonSelected = Response.SAVE;
        });
        cancelBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
            buttonSelected = Response.CANCEL;
        });
        saveBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                saveBtn.fire();
                ev.consume();
            }
        });
        cancelBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                cancelBtn.fire();
                ev.consume();
            }
        });
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        dialog.showDialog();
        return buttonSelected;
    }

    public static InputDialog getDialog(Node node, String title) {
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog(title, ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        return dialog;
    }

    public static void getConfirmDialog(Node node, final EventHandler<ActionEvent> yesAction) {
        Button yesBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_OK);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(yesBtn);
        BorderPane content = new BorderPane();
        content.setCenter(node);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(25, 25, 5, 25));
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog("", ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        yesBtn.setOnAction((ActionEvent e) -> {
            yesAction.handle(null);
            dialog.close();
        });
        yesBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                yesBtn.fire();
                ev.consume();
            }
        });
        dialog.showAndWait();
    }

    public static void getConfirmDialog(String message, final EventHandler<ActionEvent> yesAction) {
        Button yesBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_YES);
        Button noBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_NO);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(yesBtn, noBtn);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 13 Verdana; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(new Message(message), 1, 0);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(25, 25, 5, 25));
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog("", ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        yesBtn.setOnAction((ActionEvent e) -> {
            yesAction.handle(null);
            dialog.close();
        });
        noBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
        });
        yesBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                yesBtn.fire();
                ev.consume();
            }
        });
        noBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                noBtn.fire();
                ev.consume();
            }
        });
        dialog.showDialog();
    }

    public static void getConfirmDialog(String message) {
        Button okBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_OK);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(okBtn);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 13 Verdana; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(new Message(message), 1, 0);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(25, 25, 5, 25));
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog("", ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        okBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
        });
        okBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                okBtn.fire();
                ev.consume();
            }
        });
        dialog.showDialog();
    }

    public static void getWarningDialog(String message) {
        Button okBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_OK);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(okBtn);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 13 Verdana; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.WARNING_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(new Message(message), 1, 0);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(25, 25, 5, 25));
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog("", ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        okBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
        });
        okBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                okBtn.fire();
                ev.consume();
            }
        });
        dialog.showDialog();
    }

    public static Response getYesNoResponseDialog(String message) {
        Button yesBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_YES);
        Button noBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_NO);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(yesBtn, noBtn);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 14 Verdana; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(new Message(message), 1, 0);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(25, 25, 5, 25));
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog("", ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        yesBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
            buttonSelected = Response.YES;
        });
        noBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
            buttonSelected = Response.NO;
        });
        yesBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                yesBtn.fire();
                ev.consume();
            }
        });
        noBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                noBtn.fire();
                ev.consume();
            }
        });
        dialog.showDialog();
        return buttonSelected;
    }

    public static Response getNoYesResponseDialog(String message) {
        Button yesBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_YES);
        Button noBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_NO);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(10);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().addAll(yesBtn, noBtn);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 14 Verdana; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(new Message(message), 1, 0);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(25, 25, 5, 25));
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        final InputDialog dialog = new InputDialog("", ClientApp.primaryStage, scene);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        yesBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
            buttonSelected = Response.YES;
        });
        noBtn.setOnAction((ActionEvent e) -> {
            dialog.close();
            buttonSelected = Response.NO;
        });
        yesBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                yesBtn.fire();
                ev.consume();
            }
        });
        noBtn.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                noBtn.fire();
                ev.consume();
            }
        });
        noBtn.requestFocus();
        dialog.showDialog();
        return buttonSelected;
    }
}

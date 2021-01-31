/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import com.salesliant.client.ClientApp;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Lewis
 */
public class ProgressDialog extends Stage {

    private final Label messageLbl = new Label("Please Wait");
    private final ProgressIndicator pin = new ProgressIndicator();

    public ProgressDialog() {
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        initOwner(ClientApp.primaryStage);

        pin.setProgress(-1F);

        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 25 Verdana; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(15);
        messageLbl.setPrefWidth(400);
        messageLbl.setAlignment(Pos.CENTER);
        txtPane.add(messageLbl, 0, 0);
        txtPane.add(pin, 0, 1);
        pin.setPrefHeight(100);

        Scene scene = new Scene(txtPane);
        setScene(scene);
    }

    public void activateProgressBar(final Task<?> task) {
        pin.progressProperty().bind(task.progressProperty());
        show();
    }

    public void setMessage(String s) {
        messageLbl.setText(s);
    }
}

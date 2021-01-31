/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.test;

/**
 *
 * @author Lewis
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class OJFXTableView extends Application {

    @Override
    public void start(Stage stage) {
        TableView<Object> table = new TableView<>();
        table.getColumns().add(new TableColumn<Object, String>("Column 1"));
        table.setEditable(true);
        stage.setScene(new Scene(table));
//        table.requestFocus();
//        table.setFocusTraversable(false);
        stage.show();
//        table.requestFocus();
//        table.getSelectionModel().select(0);
//        table.setFocusTraversable(false);
        table.requestFocus();
        table.getSelectionModel().select(0);
        table.getFocusModel().focus(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

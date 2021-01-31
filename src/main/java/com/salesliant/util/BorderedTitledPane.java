/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.util;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Lewis
 */
public class BorderedTitledPane extends StackPane {

    public BorderedTitledPane(String titleString, Node content) {
        Label title = new Label(" " + titleString + " ");
        title.getStyleClass().add("borderedTitlePane-titled-title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane contentPane = new StackPane();

        content.getStyleClass().add("borderedTitlePane-titled-content");
        contentPane.getChildren().add(content);

        getStyleClass().add("borderedTitlePane-titled-border");
        getChildren().addAll(title, contentPane);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.widget;

import com.salesliant.entity.Category;
import com.salesliant.util.BaseDao;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

/**
 *
 * @author Lewis
 */
public class CategoryWidget extends ComboBox<Category> {

    private final BaseDao<Category> daoCategory = new BaseDao<>(Category.class);

    public CategoryWidget() {

        setButtonCell(new CategoryListCell());
        setCellFactory((ListView<Category> parm) -> new CategoryListCell());
        List<Category> list = daoCategory.read().stream()
                .sorted((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()))
                .collect(Collectors.toList());
        ObservableList<Category> oList = FXCollections.observableList(list);
        setItems(oList);
    }

    class CategoryListCell extends ListCell<Category> {

        @Override
        protected void updateItem(Category item, boolean empty) {
            super.updateItem(item, empty);
            if (item != null) {
                setText(item.getName());
            }
        }
    }
}

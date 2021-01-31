/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.mvc;

import com.salesliant.util.AppConstants;
import javafx.scene.Parent;

/**
 *
 * @author Lewis
 */
public abstract class Controller extends AbstractController {

    public void read() {
        setModelProperty(AppConstants.DB_READ, null);
    }

    public <T> void update(T emp) {
        setModelProperty(AppConstants.DB_UPDATE, emp);
    }

    public <T> void create(T emp) {
        setModelProperty(AppConstants.DB_CREATE, emp);
    }

    public <T> void delete(T emp) {
        setModelProperty(AppConstants.DB_DELETE, emp);
    }

    public abstract Parent getView();
}

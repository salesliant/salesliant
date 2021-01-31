/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salesliant.mvc;

import com.salesliant.util.AppConstants;
import static com.salesliant.util.BaseUtil.toList;
import com.salesliant.util.ErrorMessage;
import java.util.List;

/**
 *
 * @author Lewis
 */
public class Model extends AbstractModel {

    private final Class<?> cls;

    public Model(Class cls) {
        this.cls = cls;
    }

    @Override
    public void updateModel(String propertyName, Object newValue) {
        if (propertyName.equals(AppConstants.DB_READ)) {
            List<?> aList = toList(readAll(cls));
            if (aList != null) {
                firePropertyChange(AppConstants.DB_READ, newValue, aList);
            } else {
                ErrorMessage.showError("Error reading from database");
            }
        }
        if (propertyName.equals(AppConstants.DB_CREATE)) {
            if (create(newValue)) {
                firePropertyChange(AppConstants.DB_CREATE, newValue, true);
            } else {
                ErrorMessage.showError("Error saving to database");
            }
        }
        if (propertyName.equals(AppConstants.DB_UPDATE)) {
            if (update(newValue)) {
                firePropertyChange(AppConstants.DB_UPDATE, newValue, true);
            } else {
                ErrorMessage.showError("Error saving to database");
            }
        }
        if (propertyName.equals(AppConstants.DB_DELETE)) {
            if (delete(newValue)) {
                firePropertyChange(AppConstants.DB_DELETE, newValue, true);
            } else {
                ErrorMessage.showError("Error deleting from databse");
            }
        }
    }
}

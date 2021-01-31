package com.salesliant.mvc;

import java.beans.PropertyChangeEvent;
import javafx.scene.layout.GridPane;

/**
 * This class provides the base level abstraction for views. All views that
 * extend this class also extend Parent, as well as providing the updateView()
 * method that controllers can use to propagate model property changes.
 */
public abstract class AbstractView extends GridPane {

    /**
     * Called by the controller when it needs to pass along a property change
     * from a model.
     *
     * @param event The event object
     */
    public abstract void updateView(PropertyChangeEvent event);

    public boolean setClosable() {
        return true;
    }
;
}

package com.salesliant.mvc;

import com.salesliant.client.ClientApp;
import com.salesliant.client.Config;
import com.salesliant.util.ButtonFactory;
import com.salesliant.util.RES;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

/**
 * This class provides base level functionality for all models, including a
 * support for a property change mechanism (using the PropertyChangeSupport
 * class, as well as a convenience method that other objects can use to reset
 * model state.
 */
public abstract class AbstractModel {

    // public final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory("salesliantPU");
    /**
     * Convenience class that allow others to observe changes to the model
     * properties
     */
    protected PropertyChangeSupport propertyChangeSupport;

    /**
     * Default constructor. Instantiates the PropertyChangeSupport class.
     */
    public AbstractModel() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * Adds a property change listener to the observer list.
     *
     * @param l The property change listener
     */
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    /**
     * Removes a property change listener from the observer list.
     *
     * @param l The property change listener
     */
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    /**
     * Fires an event to all registered listeners informing them that a property
     * in this model has changed.
     *
     * @param propertyName The name of the property
     * @param oldValue The previous value of the property before the change
     * @param newValue The new property value after the change
     */
    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
    }

    public abstract void updateModel(String propertyName, Object newValue);

    protected <T> List readAll(Class<T> cls) {
        try {
            EntityManager em = Config.createEntityManager();
            CriteriaBuilder qb = em.getCriteriaBuilder();
            CriteriaQuery<T> c = qb.createQuery(cls);
            TypedQuery<T> q = em.createQuery(c);
            List<T> list = q.getResultList();
            em.close();
            return list;
        } catch (Exception ex) {
            Logger.getLogger(cls.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    protected <T> Boolean create(T t) {
        try {
            EntityManager em = Config.createEntityManager();
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(t.getClass().getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    protected <T> Boolean update(T t) {
        try {
            EntityManager em = Config.createEntityManager();
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(t.getClass().getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    protected <T> Boolean delete(T t) {
        try {
            EntityManager em = Config.createEntityManager();
            em.getTransaction().begin();
            Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(t);
            Object o = em.find(t.getClass(), id);
            em.merge(o);
            em.remove(o);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(t.getClass().getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    protected void warn(final String s) {
        Button okBtn = ButtonFactory.getButton(ButtonFactory.BUTTON_OK);
        HBox buttonGroup = new HBox();
        buttonGroup.setAlignment(Pos.CENTER_RIGHT);
        buttonGroup.setSpacing(8);
        buttonGroup.setPadding(new Insets(10, 0, 10, 0));
        buttonGroup.getChildren().add(okBtn);
        HBox.setHgrow(okBtn, Priority.ALWAYS);
        Text txt = new Text();
        txt.setText(s);
        GridPane txtPane = new GridPane();
        txtPane.setStyle("-fx-font: 14 serif; -fx-padding: 10;");
        txtPane.setPadding(new Insets(3));
        txtPane.setHgap(15);
        txtPane.setVgap(10);
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        txtPane.add(iv, 0, 0);
        txtPane.add(txt, 1, 0);
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        BorderPane content = new BorderPane();
        content.setCenter(txtPane);
        content.setBottom(buttonGroup);
        content.setPadding(new Insets(5, 5, 5, 5));
        content.getStyleClass().add("editView");
        Scene scene = new Scene(content);
        scene.getStylesheets().add("css/styles.css");
        dialog.setScene(scene);
        dialog.setTitle("Warning");
        dialog.sizeToScene();
        dialog.centerOnScreen();
        dialog.initOwner(ClientApp.primaryStage);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setResizable(false);
        dialog.setOnCloseRequest((final WindowEvent windowEvent) -> {
            dialog.close();
            windowEvent.consume();
        });
        okBtn.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        dialog.show();
    }
}

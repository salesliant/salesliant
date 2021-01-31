package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Employee;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseListUI;
import com.salesliant.util.RES;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 *
 * @author Lewis
 */
public class LogoutUI extends BaseListUI<Employee> {

    public LogoutUI() {
        Text txt = new Text("Do you want to logout of the system?");
        Image image = new Image(RES.CONFIRM_ICON);
        ImageView iv = new ImageView();
        iv.setImage(image);
        HBox pane = new HBox();
        pane.getChildren().addAll(iv, txt);
        pane.setStyle("-fx-font: 14 Verdana; -fx-spacing: 10;");
        pane.setAlignment(Pos.CENTER);
        fInputDialog = createOkCancelUIDialog(pane, "Logout");
        fInputDialog.show();
        okBtn.addEventFilter(ActionEvent.ACTION, event -> {
            fInputDialog.close();
            handleAction(AppConstants.ACTION_RESET);
        });
        cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
            fInputDialog.close();
        });
    }

    @Override
    public void handleAction(String code) {
        if (code == null ? AppConstants.ACTION_RESET == null : code.equals(AppConstants.ACTION_RESET)) {
            Config.createEntityManager().getEntityManagerFactory().getCache().evictAll();
            this.controller.getView().getChildren().clear();
            this.controller.getView().init();
        }
    }
}

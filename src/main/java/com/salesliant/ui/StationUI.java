package com.salesliant.ui;

import com.salesliant.client.Config;
import com.salesliant.entity.Station;
import com.salesliant.util.AppConstants;
import com.salesliant.util.BaseListUI;
import static com.salesliant.util.BaseUtil.getString;
import com.salesliant.util.RES;
import com.salesliant.widget.StationWidget;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StationUI extends BaseListUI<Station> {

    private final StationWidget fStationCombo = new StationWidget();

    public StationUI() {
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(5));
        mainPane.setHgap(8);
        mainPane.setVgap(10.0);
        Station currenStation = Config.getStation();
        Label currentStationNumberLbl = new Label("Current Station Number:");
        mainPane.add(currentStationNumberLbl, 0, 0);
        Label numberLbl = new Label(getString(currenStation.getNumber()));
        mainPane.add(numberLbl, 1, 0);
        Label currentStationDescLbl = new Label("Description:");
        mainPane.add(currentStationDescLbl, 0, 1);
        Label descLbl = new Label(getString(currenStation.getDescription()));
        mainPane.add(descLbl, 1, 1);
        Label defaultStationLbl = new Label("Select Default Station:");
        fStationCombo.getItems().forEach(e -> {
            if (e.getId().equals(currenStation.getId())) {
                fStationCombo.getSelectionModel().select(e);
            }
        });
        fStationCombo.setPrefWidth(300);
        mainPane.add(defaultStationLbl, 0, 2);
        mainPane.add(fStationCombo, 1, 2);

        fStationCombo.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            saveBtn.setDisable(false);
        });
        Image image = new Image(RES.CASH_REGISTER_IMAGE_RESOURCE);
        ImageView iv = new ImageView();
        iv.setImage(image);
        HBox hboxPane = new HBox();
        hboxPane.getChildren().addAll(iv, mainPane);
        hboxPane.setSpacing(5);
        hboxPane.setPadding(new Insets(15));
        hboxPane.setAlignment(Pos.CENTER);
        fInputDialog = createSaveCancelUIDialog(hboxPane, "Logout");
        fInputDialog.show();
        saveBtn.addEventFilter(ActionEvent.ACTION, event -> {
            Station selectStation = fStationCombo.getSelectionModel().getSelectedItem();
            String path = System.getenv("APPDATA") + File.separator + "Salesliant" + File.separator + Config.getStore().getStoreCode();
            String file =path + File.separator + "user.properties";
            File directory = new File(path);
            if (!directory.isDirectory()) {
                if (!directory.mkdirs()) {
                    System.out.printf("Unable to create the folder %s, check your privileges.", path);
                }
            }
            File userData = new File(file);
            if (userData.exists()) {
                try (InputStream input = new FileInputStream(file)) {
                    Properties prop = new Properties();
                    prop.load(input);
                    input.close();
                    prop.setProperty("station.number", selectStation.getNumber().toString());
                    try (FileOutputStream output = new FileOutputStream(file)) {
                        prop.store(output, null);
                        output.close();
                    }
                } catch (IOException e) {
                    System.out.printf("Error while loading the settings: %s", e.getMessage());
                }
            } else {
                try (OutputStream output = new FileOutputStream(file)) {
                    Properties prop = new Properties();
                    prop.setProperty("station.number", selectStation.getNumber().toString());
                    prop.store(output, null);
                    output.close();
                } catch (IOException e) {
                    System.out.printf("Error while creating the settings: %s", e.getMessage());
                }
            }
            Config.setStation(selectStation);
//            fInputDialog.close();
//            handleAction(AppConstants.ACTION_RESET);
        });
        saveBtn.setDisable(true);
        cancelBtn.addEventFilter(ActionEvent.ACTION, event -> {
            fInputDialog.close();
        });
        GridPane.setHalignment(currentStationNumberLbl, HPos.RIGHT);
        GridPane.setHalignment(numberLbl, HPos.LEFT);
        GridPane.setHalignment(currentStationDescLbl, HPos.RIGHT);
        GridPane.setHalignment(descLbl, HPos.LEFT);
        GridPane.setHalignment(defaultStationLbl, HPos.RIGHT);
        GridPane.setHalignment(fStationCombo, HPos.LEFT);
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

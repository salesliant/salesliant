package com.salesliant.client;

import com.salesliant.test.EmptyReport;
import com.salesliant.util.OutPrintStream;
import com.salesliant.util.RES;
import static java.awt.SplashScreen.getSplashScreen;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.PlatformLoggingMXBean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Lewis
 */
public class ClientApp extends Application {

    public static Stage primaryStage;
    private static final Logger LOGGER = Logger.getLogger(ClientApp.class.getName());

    public static void main(String[] args) throws FileNotFoundException {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        Config config = Config.getInstance();
        config.initialize();
        ClientController controller = new ClientController();
        ClientView view = controller.getView();
        Scene scene = new Scene(view, 1024, 768);
        scene.getStylesheets().add("css/styles.css");
        stage.setScene(scene);
        stage.setTitle("Salesliant");
        stage.getIcons().add(new Image(RES.LOGO_ICON));
        stage.setMaximized(true);
        loadReportLibrary();
        logConsole();
        stage.show();
        view.init();
        if (getSplashScreen() != null) {
            getSplashScreen().close();
        }
        ManagementFactory.getPlatformMXBean(PlatformLoggingMXBean.class).setLoggerLevel("javafx.css", "OFF");
    }

    private void loadReportLibrary() {
        EmptyReport layout = new EmptyReport();
        try {
            JasperReportBuilder report = layout.build();
            JasperPrint jasperPrint = report.toJasperPrint();
        } catch (DRException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    private void logConsole() {
        if (Config.getStore() != null && Config.getStore().getStoreCode() != null) {
            String path = System.getenv("APPDATA") + File.separator + "Salesliant" + File.separator + Config.getStore().getStoreCode() + File.separator + "error.txt";
            try {
                FileOutputStream file = new FileOutputStream(path);
                OutPrintStream tee = new OutPrintStream(file, System.out);
                System.setOut(tee);
                System.setErr(tee);
            } catch (FileNotFoundException e) {
                System.out.printf("Error while openning error log:", e.getMessage());
            }
        }
    }
}

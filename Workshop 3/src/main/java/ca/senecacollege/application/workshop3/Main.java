/**********************************************
 Workshop # 3
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-12-01
 **********************************************/
package ca.senecacollege.application.workshop3;

import ca.senecacollege.application.workshop3.di.AppInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage mainStage;
    private static AppInitializer initializer;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        initializer = new AppInitializer();

        loadScene("homepage.fxml");
        stage.setTitle("Auto Loan Application");
        stage.show();
    }

    // Loads an FXML file and injects dependencies using AppInitializer
    public static void loadScene(String fxmlName) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    Main.class.getResource(fxmlName)
            );

            // Controller Factory (Manual DI)
            loader.setControllerFactory((cls) -> initializer.getController(cls));

            Scene scene = new Scene(loader.load());
            mainStage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Public method used by controllers to switch screens
    public static void changeScene(String fxmlName) {
        loadScene(fxmlName);
    }

    public static void main(String[] args) {
        launch();
    }
}

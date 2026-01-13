package com.apd545.hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main JavaFX application.
 */
public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        stage.setTitle("Hotel Reservation System");
        setRoot("LaunchScreen");
        stage.show();
    }

    public static void setRoot(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(name + ".fxml"));
            Parent root = loader.load();
            Scene sc = new Scene(root, 1000, 680);
            primaryStage.setScene(sc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // Close EntityManagerFactory when app shuts down
        JPAUtil.shutdown();
    }

    public static void main(String[] args) {
        launch();
    }
}

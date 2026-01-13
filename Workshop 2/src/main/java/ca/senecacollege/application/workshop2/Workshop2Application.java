/**********************************************
 Workshop # 2
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-24
 **********************************************/
package ca.senecacollege.application.workshop2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

// main app class
public class Workshop2Application extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // load main view
        FXMLLoader loader = new FXMLLoader(
                Workshop2Application.class.getResource("main-view.fxml"));

        Scene scene = new Scene(loader.load());
        stage.setTitle("Workshop 2");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

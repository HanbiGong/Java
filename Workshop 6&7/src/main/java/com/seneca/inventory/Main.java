/**********************************************
 Workshop # 4, 5, 6 & 7
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-28
 **********************************************/
package com.seneca.inventory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.seneca.inventory.config.AppModule;
import com.seneca.inventory.service.InventoryService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Main JavaFX application entry point.
 * This version uses Guice DI for controllers and services.
 */
public class Main extends Application {

    private static Injector injector;

    @Override
    public void start(Stage stage) throws Exception {
        // Create DI container
        injector = Guice.createInjector(new AppModule());

        // Use FXMLLoader with controller factory wired to Guice
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setTitle("Inventory Manager — Workshop 4+5");
        stage.setScene(scene);
        stage.show();
    }

    // Global access to our DI-managed InventoryService
    public static InventoryService getInventoryService() {
        return injector.getInstance(InventoryService.class);
    }

    public static void main(String[] args) {
        launch();
    }
}

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

import ca.senecacollege.application.workshop2.manager.MaintenanceManager;
import ca.senecacollege.application.workshop2.manager.UsageManager;
import ca.senecacollege.application.workshop2.model.Vehicle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SummaryWindowLoader {

    // show summary window
    public static void show(Vehicle vehicle,
                            MaintenanceManager maintenanceManager,
                            UsageManager usageManager) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                SummaryWindowLoader.class.getResource("summary-view.fxml"));

        Scene scene = new Scene(loader.load());

        SummaryController controller = loader.getController();
        controller.setData(vehicle, maintenanceManager, usageManager);

        Stage stage = new Stage();
        stage.setTitle("Summary");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
    }
}

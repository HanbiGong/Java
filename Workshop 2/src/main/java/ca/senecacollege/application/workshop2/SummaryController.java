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
import ca.senecacollege.application.workshop2.model.MaintenanceRecord;
import ca.senecacollege.application.workshop2.model.UsageLog;
import ca.senecacollege.application.workshop2.model.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class SummaryController {

    @FXML private ComboBox<String> cbSummaryType;
    @FXML private TableView<SummaryItem> tblSummary;
    @FXML private TableColumn<SummaryItem, String> colField;
    @FXML private TableColumn<SummaryItem, String> colValue;

    private Vehicle vehicle;
    private MaintenanceManager maintenanceManager;
    private UsageManager usageManager;

    @FXML
    public void initialize() {
        // table setup
        colField.setCellValueFactory(new PropertyValueFactory<>("field"));
        colValue.setCellValueFactory(new PropertyValueFactory<>("value"));

        // fill combo box
        cbSummaryType.getItems().addAll("Vehicle", "Maintenance", "Usage Log");

        cbSummaryType.setOnAction(e -> loadSummary());
    }

    // called by loader
    public void setData(Vehicle vehicle,
                        MaintenanceManager maintenanceManager,
                        UsageManager usageManager) {

        this.vehicle = vehicle;
        this.maintenanceManager = maintenanceManager;
        this.usageManager = usageManager;
    }

    // load summary table based on selected type
    private void loadSummary() {
        tblSummary.getItems().clear();

        String type = cbSummaryType.getValue();
        if (type == null) {
            return;
        }

        if (type.equals("Vehicle")) {
            loadVehicleSummary();
        } else if (type.equals("Maintenance")) {
            loadMaintenanceSummary();
        } else if (type.equals("Usage Log")) {
            loadUsageSummary();
        }
    }

    // vehicle info
    private void loadVehicleSummary() {
        if (vehicle == null) {
            return;
        }

        tblSummary.getItems().add(new SummaryItem("Model", vehicle.getModel()));
        tblSummary.getItems().add(new SummaryItem("Make", vehicle.getMake()));
        tblSummary.getItems().add(new SummaryItem("Year",
                String.valueOf(vehicle.getYear())));
        tblSummary.getItems().add(new SummaryItem("Type", vehicle.getType()));
    }

    // maintenance info
    private void loadMaintenanceSummary() {
        if (vehicle == null || maintenanceManager == null) {
            return;
        }

        List<MaintenanceRecord> list =
                maintenanceManager.getRecords(vehicle.getModel());

        int index = 1;
        for (MaintenanceRecord r : list) {
            tblSummary.getItems().add(
                    new SummaryItem("Record " + index, r.toString()));
            index++;
        }
    }

    // usage info
    private void loadUsageSummary() {
        if (vehicle == null || usageManager == null) {
            return;
        }

        List<UsageLog> logs =
                usageManager.getLogs(vehicle.getModel());

        int index = 1;
        for (UsageLog u : logs) {
            tblSummary.getItems().add(
                    new SummaryItem("Log " + index, u.toString()));
            index++;
        }
    }

    @FXML
    private void onCloseClicked() {
        tblSummary.getScene().getWindow().hide();
    }
}

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class MainController {

    // vehicle fields
    @FXML private TextField txtModel;
    @FXML private TextField txtMake;
    @FXML private TextField txtYear;
    @FXML private ComboBox<String> cbType;

    // maintenance fields
    @FXML private DatePicker dpMaintenanceDate;
    @FXML private TextField txtMaintenanceDescription;
    @FXML private TextField txtMaintenanceCost;

    // usage fields
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private TextField txtKilometers;

    // table view
    @FXML private TableView<RecordView> tblRecords;
    @FXML private TableColumn<RecordView, String> colRecordType;
    @FXML private TableColumn<RecordView, String> colDetails;

    // data for table
    private ObservableList<RecordView> recordList =
            FXCollections.observableArrayList();

    // current vehicle and managers
    private Vehicle currentVehicle;
    private MaintenanceManager maintenanceManager = new MaintenanceManager();
    private UsageManager usageManager = new UsageManager();

    @FXML
    public void initialize() {
        // combo box values
        cbType.getItems().addAll("Sedan", "SUV", "Truck", "Van");

        // table setup
        colRecordType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colDetails.setCellValueFactory(new PropertyValueFactory<>("details"));
        tblRecords.setItems(recordList);
    }

    // clear all input fields
    @FXML
    private void onClearClicked() {
        txtModel.clear();
        txtMake.clear();
        txtYear.clear();
        cbType.getSelectionModel().clearSelection();

        dpMaintenanceDate.setValue(null);
        txtMaintenanceDescription.clear();
        txtMaintenanceCost.clear();

        dpStartDate.setValue(null);
        dpEndDate.setValue(null);
        txtKilometers.clear();
    }

    // save data to models, managers and table
    @FXML
    private void onSaveClicked() {

        String model = txtModel.getText().trim();
        String make = txtMake.getText().trim();
        String yearText = txtYear.getText().trim();
        String type = cbType.getValue();

        // simple validation
        if (model.isEmpty() || make.isEmpty() || yearText.isEmpty() || type == null) {
            showAlert("Please fill in model, make, year and type.");
            return;
        }

        int year;
        try {
            year = Integer.parseInt(yearText);
        } catch (NumberFormatException ex) {
            showAlert("Year must be a number.");
            return;
        }

        // create vehicle object
        currentVehicle = new Vehicle(model, make, year, type);

        // put vehicle info into table
        recordList.add(new RecordView("Vehicle", currentVehicle.toString()));

        // maintenance part (optional)
        LocalDate mDate = dpMaintenanceDate.getValue();
        String mDesc = txtMaintenanceDescription.getText().trim();
        String mCostText = txtMaintenanceCost.getText().trim();

        if (mDate != null && !mDesc.isEmpty() && !mCostText.isEmpty()) {
            double cost;
            try {
                cost = Double.parseDouble(mCostText);
            } catch (NumberFormatException ex) {
                showAlert("Cost must be a number.");
                return;
            }

            MaintenanceRecord record =
                    new MaintenanceRecord(mDate, mDesc, cost);

            // save into manager (use model as key)
            maintenanceManager.addRecord(model, record);

            recordList.add(new RecordView("Maintenance", record.toString()));
        }

        // usage part (optional)
        LocalDate start = dpStartDate.getValue();
        LocalDate end = dpEndDate.getValue();
        String kmText = txtKilometers.getText().trim();

        if (start != null && end != null && !kmText.isEmpty()) {
            double km;
            try {
                km = Double.parseDouble(kmText);
            } catch (NumberFormatException ex) {
                showAlert("Kilometers must be a number.");
                return;
            }

            UsageLog log = new UsageLog(start, end, km);

            // save into manager
            usageManager.addLog(model, log);

            recordList.add(new RecordView("Usage Log", log.toString()));
        }

        showAlert("Data saved.");
    }

    // open summary window
    @FXML
    private void onViewSummaryClicked() {
        try {
            SummaryWindowLoader.show(currentVehicle,
                    maintenanceManager, usageManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // small helper to show messages
    private void showAlert(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Info");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}

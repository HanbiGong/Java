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
package ca.senecacollege.application.workshop3.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.event.ActionEvent;
import ca.senecacollege.application.workshop3.Main;

public class CustomerController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtCity;

    @FXML
    private ComboBox<String> cmbProvince;

    public CustomerController() {
    }

    @FXML
    private void initialize() {
        cmbProvince.getItems().addAll("ON", "BC", "QC");
    }

    @FXML
    private void onNext(ActionEvent e) {
        Main.changeScene("vehicle.fxml");
    }
}

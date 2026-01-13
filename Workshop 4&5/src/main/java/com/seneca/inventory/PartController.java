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

import com.seneca.inventory.service.InventoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/*
 * Controller for Add Part screen.
 */
public class PartController {

    @FXML
    private ToggleGroup tgOrigin;

    @FXML
    private RadioButton rbInHouse;

    @FXML
    private RadioButton rbOutsourced;

    @FXML
    private TextField fieldId;
    @FXML
    private TextField fieldName;
    @FXML
    private TextField fieldInv;
    @FXML
    private TextField fieldPrice;
    @FXML
    private TextField fieldMin;
    @FXML
    private TextField fieldMax;
    @FXML
    private TextField fieldMachineId;
    @FXML
    private TextField fieldCompanyName;

    @FXML
    private Label labelMachineOrCompany;

    private InventoryService inventoryService;

    public PartController() {
        // empty
    }

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

        // show next id
        int nextId = inventoryService.peekNextPartId();
        fieldId.setText(String.valueOf(nextId));

        // make sure toggle is synced
        onToggleOrigin(null);
    }

    @FXML
    private void initialize() {
        // will be called before setInventoryService,
        // so we only do simple things here.
        if (rbOutsourced != null) {
            rbOutsourced.setSelected(true);
        }
    }

    @FXML
    private void onToggleOrigin(ActionEvent e) {
        boolean inHouse = rbInHouse.isSelected();

        fieldMachineId.setVisible(inHouse);
        fieldMachineId.setManaged(inHouse);

        fieldCompanyName.setVisible(!inHouse);
        fieldCompanyName.setManaged(!inHouse);

        labelMachineOrCompany.setText(inHouse ? "Machine ID" : "Company Name");
    }

    @FXML
    private void onSave(ActionEvent e) {
        if (inventoryService == null) {
            showError("Inventory service is not set.");
            return;
        }

        try {
            String name = fieldName.getText();
            double price = Double.parseDouble(fieldPrice.getText().trim());
            int stock = Integer.parseInt(fieldInv.getText().trim());
            int min = Integer.parseInt(fieldMin.getText().trim());
            int max = Integer.parseInt(fieldMax.getText().trim());

            boolean inHouse = rbInHouse.isSelected();

            if (inHouse) {
                int machineId = Integer.parseInt(fieldMachineId.getText().trim());
                inventoryService.addInHousePart(name, price, stock, min, max, machineId);
            } else {
                String companyName = fieldCompanyName.getText();
                inventoryService.addOutsourcedPart(name, price, stock, min, max, companyName);
            }

            closeWindow();
        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for price, inventory, min, max, and machine id.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    @FXML
    private void onCancel(ActionEvent e) {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldName.getScene().getWindow();
        stage.close();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Cannot save part");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

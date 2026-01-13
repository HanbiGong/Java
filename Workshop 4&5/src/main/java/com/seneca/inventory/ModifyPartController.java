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

import com.google.inject.Inject;
import com.seneca.inventory.model.InHouse;
import com.seneca.inventory.model.Outsourced;
import com.seneca.inventory.model.Part;
import com.seneca.inventory.service.InventoryService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/*
 * Controller for Modify Part screen (DI version).
 */
public class ModifyPartController {

    private InventoryService inventoryService;
    private Part originalPart;

    @FXML private ToggleGroup tgOrigin;
    @FXML private RadioButton rbInHouse;
    @FXML private RadioButton rbOutsourced;

    @FXML private TextField fieldId;
    @FXML private TextField fieldName;
    @FXML private TextField fieldInv;
    @FXML private TextField fieldPrice;
    @FXML private TextField fieldMin;
    @FXML private TextField fieldMax;
    @FXML private TextField fieldMachineId;
    @FXML private TextField fieldCompanyName;

    @FXML private Label labelMachineOrCompany;
    @FXML private Button btnCancel;

    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void setPart(Part part) {
        this.originalPart = part;

        fieldId.setText(String.valueOf(part.getId()));
        fieldName.setText(part.getName());
        fieldInv.setText(String.valueOf(part.getStock()));
        fieldPrice.setText(String.valueOf(part.getPrice()));
        fieldMin.setText(String.valueOf(part.getMin()));
        fieldMax.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse ih) {
            rbInHouse.setSelected(true);
            labelMachineOrCompany.setText("Machine ID");
            fieldMachineId.setText(String.valueOf(ih.getMachineId()));
            fieldMachineId.setVisible(true);
            fieldMachineId.setManaged(true);
            fieldCompanyName.setVisible(false);
            fieldCompanyName.setManaged(false);
        } else if (part instanceof Outsourced os) {
            rbOutsourced.setSelected(true);
            labelMachineOrCompany.setText("Company Name");
            fieldCompanyName.setText(os.getCompanyName());
            fieldCompanyName.setVisible(true);
            fieldCompanyName.setManaged(true);
            fieldMachineId.setVisible(false);
            fieldMachineId.setManaged(false);
        }
    }

    @FXML
    private void onToggleOrigin(ActionEvent e) {
        boolean inHouse = rbInHouse.isSelected();
        labelMachineOrCompany.setText(inHouse ? "Machine ID" : "Company Name");
        fieldMachineId.setVisible(inHouse);
        fieldMachineId.setManaged(inHouse);
        fieldCompanyName.setVisible(!inHouse);
        fieldCompanyName.setManaged(!inHouse);
    }

    @FXML
    private void onSave(ActionEvent e) {
        try {
            String name = fieldName.getText();
            int stock = Integer.parseInt(fieldInv.getText());
            double price = Double.parseDouble(fieldPrice.getText());
            int min = Integer.parseInt(fieldMin.getText());
            int max = Integer.parseInt(fieldMax.getText());
            boolean inHouse = rbInHouse.isSelected();

            Integer machineId = null;
            String companyName = null;

            if (inHouse) {
                machineId = Integer.parseInt(fieldMachineId.getText());
            } else {
                companyName = fieldCompanyName.getText();
            }

            inventoryService.updatePart(
                    originalPart,
                    name,
                    price,
                    stock,
                    min,
                    max,
                    inHouse,
                    machineId,
                    companyName
            );

            closeWindow();

        } catch (NumberFormatException ex) {
            showWarning("Invalid Input", "Check numeric fields.");
        } catch (IllegalArgumentException ex) {
            showWarning("Validation Error", ex.getMessage());
        } catch (Exception ex) {
            showError("Unexpected Error", "Could not save changes.");
        }
    }

    @FXML
    private void onCancel(ActionEvent e) {
        if (showConfirm("Cancel", "Discard changes?")) {
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void showWarning(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private boolean showConfirm(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        return a.showAndWait().filter(b -> b == ButtonType.OK).isPresent();
    }
}

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

import com.seneca.inventory.model.Part;
import com.seneca.inventory.model.Product;
import com.seneca.inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/*
 * Controller for Add Product window.
 * It lets user enter product info and choose parts.
 */
public class ProductController {

    // simple link to service
    private InventoryService inventoryService;

    // list for parts added to this product
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // ----- left side fields -----
    @FXML private TextField fieldId;
    @FXML private TextField fieldName;
    @FXML private TextField fieldInv;
    @FXML private TextField fieldPrice;
    @FXML private TextField fieldMin;
    @FXML private TextField fieldMax;

    // ----- right side widgets -----
    @FXML private TextField searchAvailableParts;
    @FXML private TableView<Part> tableAvailableParts;

    @FXML private TextField searchAssociatedParts;
    @FXML private TableView<Part> tableAssociatedParts;

    @FXML
    private void initialize() {
        // set up table columns (no data yet)
        setupAvailablePartsTable();
        setupAssociatedPartsTable();
    }

    // called from MainController after FXML is loaded
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

        // show next product id
        fieldId.setText(String.valueOf(inventoryService.peekNextProductId()));

        // fill top table with all parts
        tableAvailableParts.setItems(inventoryService.getAllParts());

        // set bottom table to our list
        associatedParts = FXCollections.observableArrayList();
        tableAssociatedParts.setItems(associatedParts);
    }

    // ===== table setup =====

    private void setupAvailablePartsTable() {
        TableColumn<Part, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Part, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Part, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Part, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableAvailableParts.getColumns().setAll(colId, colName, colStock, colPrice);
    }

    private void setupAssociatedPartsTable() {
        TableColumn<Part, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Part, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Part, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Part, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableAssociatedParts.getColumns().setAll(colId, colName, colStock, colPrice);
    }

    // ===== search buttons (for FXML onAction) =====

    @FXML
    private void onSearchAvailableParts(ActionEvent e) {
        if (inventoryService == null) return;

        String keyword = searchAvailableParts.getText();
        if (keyword == null || keyword.isBlank()) {
            tableAvailableParts.setItems(inventoryService.getAllParts());
            return;
        }

        ObservableList<Part> result = inventoryService.searchParts(keyword);
        if (result.isEmpty()) {
            showInfo("No parts found.");
        }
        tableAvailableParts.setItems(result);
    }

    @FXML
    private void onSearchAssociatedParts(ActionEvent e) {
        // simple local search in associatedParts list
        String keyword = searchAssociatedParts.getText();
        if (keyword == null || keyword.isBlank()) {
            // just reset to full associated list
            tableAssociatedParts.setItems(associatedParts);
            return;
        }

        ObservableList<Part> filtered = FXCollections.observableArrayList();
        for (Part p : associatedParts) {
            if (p.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filtered.add(p);
            }
        }

        if (filtered.isEmpty()) {
            showInfo("No associated parts found.");
        }

        tableAssociatedParts.setItems(filtered);
    }

    // ===== buttons Add / Delete for parts =====

    @FXML
    private void onAddPart(ActionEvent e) {
        Part selected = tableAvailableParts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a part to add.");
            return;
        }

        if (!associatedParts.contains(selected)) {
            associatedParts.add(selected);
        }
        // refresh view
        tableAssociatedParts.setItems(associatedParts);
    }

    @FXML
    private void onRemovePart(ActionEvent e) {
        Part selected = tableAssociatedParts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a part to remove.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Part");
        alert.setHeaderText("Remove selected part from product?");
        alert.setContentText("This does not delete the part from inventory.");
        alert.showAndWait()
                .filter(btn -> btn == ButtonType.OK)
                .ifPresent(btn -> associatedParts.remove(selected));

        tableAssociatedParts.setItems(associatedParts);
    }

    // ===== Save / Cancel =====

    @FXML
    private void onSave(ActionEvent e) {
        if (inventoryService == null) {
            showError("Service is not set.");
            return;
        }

        try {
            String name = fieldName.getText();
            int stock = Integer.parseInt(fieldInv.getText());
            double price = Double.parseDouble(fieldPrice.getText());
            int min = Integer.parseInt(fieldMin.getText());
            int max = Integer.parseInt(fieldMax.getText());

            if (associatedParts.isEmpty()) {
                showError("Please add at least one part.");
                return;
            }

            // service will do main validation
            Product product = inventoryService.addProduct(
                    name, price, stock, min, max, associatedParts
            );

            // close window after save
            closeWindow(e);

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for inventory, price, min, and max.");
        } catch (IllegalArgumentException ex) {
            // message from service validation
            showError(ex.getMessage());
        }
    }

    @FXML
    private void onCancel(ActionEvent e) {
        // just close window
        closeWindow(e);
    }

    private void closeWindow(ActionEvent e) {
        Stage stage = (Stage) ((Control) e.getSource()).getScene().getWindow();
        stage.close();
    }

    // ===== small alert helpers =====

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Something went wrong");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

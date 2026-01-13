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
 * Controller for Modify Product window.
 * Uses InventoryService (DI) to update product correctly.
 */
public class ModifyProductController {

    private InventoryService inventoryService;
    private Product originalProduct;

    // local list for associated parts while editing
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
        setupAvailablePartsTable();
        setupAssociatedPartsTable();
    }

    // ===== Service setter (called by MainController) =====
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;

        if (inventoryService != null) {
            tableAvailableParts.setItems(inventoryService.getAllParts());
        }
    }

    // ===== Product setter (called by MainController) =====
    public void setProductToEdit(Product product) {
        this.originalProduct = product;
        if (product == null) return;

        // fill form
        fieldId.setText(String.valueOf(product.getId()));
        fieldName.setText(product.getName());
        fieldInv.setText(String.valueOf(product.getStock()));
        fieldPrice.setText(String.valueOf(product.getPrice()));
        fieldMin.setText(String.valueOf(product.getMin()));
        fieldMax.setText(String.valueOf(product.getMax()));

        // copy associated parts to local editable list
        associatedParts = FXCollections.observableArrayList(product.getAllAssociatedParts());
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

    // ===== search buttons =====

    @FXML
    private void onSearchAvailableParts(ActionEvent e) {
        if (inventoryService == null) return;

        String keyword = searchAvailableParts.getText();
        if (keyword == null || keyword.isBlank()) {
            tableAvailableParts.setItems(inventoryService.getAllParts());
            return;
        }

        ObservableList<Part> result = inventoryService.searchParts(keyword);
        if (result.isEmpty()) showInfo("No parts found.");
        tableAvailableParts.setItems(result);
    }

    @FXML
    private void onSearchAssociatedParts(ActionEvent e) {
        String keyword = searchAssociatedParts.getText();
        if (keyword == null || keyword.isBlank()) {
            tableAssociatedParts.setItems(associatedParts);
            return;
        }

        ObservableList<Part> filtered = FXCollections.observableArrayList();
        for (Part part : associatedParts) {
            if (part.getName() != null &&
                    part.getName().toLowerCase().contains(keyword.toLowerCase())) {
                filtered.add(part);
            }
        }

        tableAssociatedParts.setItems(filtered);
    }

    // ===== Add / Remove associated parts =====

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
        tableAssociatedParts.setItems(associatedParts);
    }

    @FXML
    private void onRemovePart(ActionEvent e) {
        Part selected = tableAssociatedParts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a part to remove.");
            return;
        }

        if (showConfirm("Remove Part", "Remove this part from the product?")) {
            associatedParts.remove(selected);
        }
        tableAssociatedParts.setItems(associatedParts);
    }

    // ===== Save =====

    @FXML
    private void onSave(ActionEvent e) {
        if (inventoryService == null) {
            showError("Service is not set.");
            return;
        }
        if (originalProduct == null) {
            showError("No product to update.");
            return;
        }

        try {
            String name = fieldName.getText();
            int stock = Integer.parseInt(fieldInv.getText());
            double price = Double.parseDouble(fieldPrice.getText());
            int min = Integer.parseInt(fieldMin.getText());
            int max = Integer.parseInt(fieldMax.getText());

            if (associatedParts.isEmpty()) {
                showError("Product must have at least one part.");
                return;
            }

            // =====🔥 핵심: Service 레이어로 모든 수정 위임 =====
            inventoryService.updateProduct(
                    originalProduct,
                    name,
                    price,
                    stock,
                    min,
                    max,
                    associatedParts
            );

            closeWindow(e);

        } catch (NumberFormatException ex) {
            showError("Please enter valid numbers for Inventory, Price, Min, and Max.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Could not save changes.");
        }
    }

    // ===== Cancel =====

    @FXML
    private void onCancel(ActionEvent e) {
        if (showConfirm("Cancel", "Discard changes?")) {
            closeWindow(e);
        }
    }

    private void closeWindow(ActionEvent e) {
        Stage stage = (Stage) ((Control) e.getSource()).getScene().getWindow();
        stage.close();
    }

    // ===== alerts =====

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

    private boolean showConfirm(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        var result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}


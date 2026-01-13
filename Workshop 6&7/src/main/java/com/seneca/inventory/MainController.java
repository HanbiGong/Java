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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.File;
import java.io.IOException;

public class MainController {

    // search boxes
    @FXML
    private TextField searchParts;

    @FXML
    private TextField searchProducts;

    // tables
    @FXML
    private TableView<Part> tableParts;

    @FXML
    private TableView<Product> tableProducts;

    // shared service (from Main / DI)
    private final InventoryService inventoryService = Main.getInventoryService();

    public MainController() {
        // empty
    }

    @FXML
    private void initialize() {
        setupPartsTable();
        setupProductsTable();
        refreshTables();
    }

    private void refreshTables() {
        tableParts.setItems(inventoryService.getAllParts());
        tableProducts.setItems(inventoryService.getAllProducts());
    }

    // ============== table setup ==============

    private void setupPartsTable() {
        TableColumn<Part, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Part, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Part, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Part, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableParts.getColumns().setAll(colId, colName, colStock, colPrice);
    }

    private void setupProductsTable() {
        TableColumn<Product, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> colName = new TableColumn<>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Product, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableProducts.getColumns().setAll(colId, colName, colStock, colPrice);
    }

    // ============== top buttons ==============

    @FXML
    private void onExit(ActionEvent e) {
        Platform.exit();
    }

    // ----- file buttons -----

    // FXML: onAction="#onSaveDataToFile"
    @FXML
    private void onSaveDataToFile(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save Inventory To XML File");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );
        chooser.setInitialFileName("inventory-data.xml");

        Stage stage = (Stage) tableParts.getScene().getWindow();
        File file = chooser.showSaveDialog(stage);
        if (file == null) {
            return;
        }

        try {
            inventoryService.saveToXmlFile(file);
            showInfo("Data saved to file:\n" + file.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Could not save data to file.");
        }
    }

    // FXML: onAction="#onLoadDataFromFile"
    @FXML
    private void onLoadDataFromFile(ActionEvent e) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Load Inventory From XML File");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml")
        );

        Stage stage = (Stage) tableParts.getScene().getWindow();
        File file = chooser.showOpenDialog(stage);
        if (file == null) {
            return;
        }

        try {
            inventoryService.loadFromXmlFile(file);
            refreshTables();
            showInfo("Data loaded from file:\n" + file.getAbsolutePath());
        } catch (UnsupportedOperationException uoe) {
            showError("XML load is not implemented yet.");
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Could not load data from file.");
        }
    }

    // ----- DB buttons -----

    // FXML: onAction="#onWriteDataToDb"
    @FXML
    private void onWriteDataToDb(ActionEvent e) {
        try {
            inventoryService.saveToDatabase();
            showInfo("Data saved to database (inventory.db).");
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Could not save data to database.");
        }
    }

    // FXML: onAction="#onLoadDataFromDb"
    @FXML
    private void onLoadDataFromDb(ActionEvent e) {
        try {
            inventoryService.loadFromDatabase();
            refreshTables();
            showInfo("Data loaded from database (inventory.db).");
        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Could not load data from database.");
        }
    }

    // -------- parts actions --------

    @FXML
    private void addPart(ActionEvent e) {
        openAddPartDialog();
    }

    @FXML
    private void modifyPart(ActionEvent e) {
        Part selected = tableParts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a part first.");
            return;
        }

        openModifyPartDialog(selected);
    }

    @FXML
    private void deletePart(ActionEvent e) {
        Part selected = tableParts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a part first.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Part");
        alert.setHeaderText("Delete selected part?");
        alert.setContentText("This cannot be undone.");
        alert.showAndWait()
                .filter(btn -> btn == ButtonType.OK)
                .ifPresent(btn -> {
                    inventoryService.deletePart(selected);
                    refreshTables();
                });
    }

    // -------- product actions --------

    @FXML
    private void addProduct(ActionEvent e) {
        openAddProductDialog();
    }

    @FXML
    private void modifyProduct(ActionEvent e) {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a product first.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyProduct.fxml"));
            Parent root = loader.load();

            ModifyProductController controller = loader.getController();
            controller.setInventoryService(inventoryService);
            controller.setProductToEdit(selected);

            Stage stage = new Stage();
            stage.setTitle("Modify Product");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTables();

        } catch (Exception ex) {
            ex.printStackTrace();
            showError("Could not open Modify Product window.");
        }
    }


    @FXML
    private void deleteProduct(ActionEvent e) {
        Product selected = tableProducts.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showInfo("Please select a product first.");
            return;
        }

        if (!inventoryService.deleteProduct(selected)) {
            showError("Cannot delete. Product still has associated parts.");
            return;
        }

        refreshTables();
    }

    // ============== search actions ==============

    @FXML
    private void searchPartsAction(ActionEvent e) {
        String keyword = searchParts.getText();
        if (keyword == null || keyword.isBlank()) {
            tableParts.setItems(inventoryService.getAllParts());
            return;
        }

        ObservableList<Part> result = inventoryService.searchParts(keyword);
        if (result.isEmpty()) {
            showInfo("No parts found.");
        }
        tableParts.setItems(result);
    }

    @FXML
    private void searchProductsAction(ActionEvent e) {
        String keyword = searchProducts.getText();
        if (keyword == null || keyword.isBlank()) {
            tableProducts.setItems(inventoryService.getAllProducts());
            return;
        }

        ObservableList<Product> result = inventoryService.searchProducts(keyword);
        if (result.isEmpty()) {
            showInfo("No products found.");
        }
        tableProducts.setItems(result);
    }

    // ============== dialog helpers ==============

    private void openAddPartDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addPart.fxml"));
            Parent root = loader.load();

            PartController controller = loader.getController();
            controller.setInventoryService(inventoryService);

            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTables();
        } catch (IOException ex) {
            ex.printStackTrace();
            showError("Could not open Add Part window.");
        }
    }

    private void openModifyPartDialog(Part part) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modifyPart.fxml"));
            Parent root = loader.load();

            ModifyPartController controller = loader.getController();
            controller.setInventoryService(inventoryService);
            controller.setPart(part);

            Stage stage = new Stage();
            stage.setTitle("Modify Part");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTables();
        } catch (IOException ex) {
            ex.printStackTrace();
            showError("Could not open Modify Part window.");
        }
    }

    private void openAddProductDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addProduct.fxml"));
            Parent root = loader.load();

            ProductController controller = loader.getController();
            controller.setInventoryService(inventoryService);

            Stage stage = new Stage();
            stage.setTitle("Add Product");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            refreshTables();
        } catch (IOException ex) {
            ex.printStackTrace();
            showError("Could not open Add Product window.");
        }
    }

    // ============== alert helpers ==============

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

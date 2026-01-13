package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class LoyaltyDashboardController {

    @FXML private TableView<Customer> tblCustomers;
    @FXML private TableColumn<Customer, Long> colId;
    @FXML private TableColumn<Customer, String> colName;
    @FXML private TableColumn<Customer, String> colPhone;
    @FXML private TableColumn<Customer, String> colEmail;
    @FXML private TableColumn<Customer, Integer> colPoints;
    @FXML private TableColumn<Customer, String> colLoyalty;

    @FXML private TextField txtSearchName;
    @FXML private TextField txtSearchPhone;

    private final CustomerRepository repo = CustomerRepository.getInstance();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleLongProperty(
                c.getValue().getId()).asObject()
        );

        colName.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(
                        c.getValue().getFirstName() + " " + c.getValue().getLastName()
                )
        );

        colPhone.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getPhone())
        );

        colEmail.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getEmail())
        );

        colPoints.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getPoints()).asObject()
        );

        colLoyalty.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getLoyaltyNumber())
        );

        loadAll();
    }

    private void loadAll() {
        tblCustomers.setItems(FXCollections.observableArrayList(repo.findAll()));
    }

    @FXML
    private void handleSearch() {
        String name = txtSearchName.getText().trim().toLowerCase();
        String phone = txtSearchPhone.getText().trim();

        var list = repo.findAll();

        if (!name.isEmpty()) {
            list.removeIf(c ->
                    !(c.getFirstName() + " " + c.getLastName()).toLowerCase().contains(name)
            );
        }

        if (!phone.isEmpty()) {
            list.removeIf(c -> c.getPhone() == null || !c.getPhone().contains(phone));
        }

        tblCustomers.setItems(FXCollections.observableArrayList(list));
    }

    @FXML
    private void handleReset() {
        txtSearchName.clear();
        txtSearchPhone.clear();
        loadAll();
    }

    @FXML
    private void handleBack() {
        App.setRoot("AdminDashboard");
    }
}

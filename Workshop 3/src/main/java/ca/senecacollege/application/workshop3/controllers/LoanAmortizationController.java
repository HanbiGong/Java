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
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import ca.senecacollege.application.workshop3.services.AmortizationService;
import ca.senecacollege.application.workshop3.models.*;
import ca.senecacollege.application.workshop3.Main;

public class LoanAmortizationController {

    @FXML
    private TableView<AmortizationEntry> tblAmortization;

    @FXML
    private TableColumn<AmortizationEntry, Integer> colMonth;

    @FXML
    private TableColumn<AmortizationEntry, Double> colPrincipal;

    @FXML
    private TableColumn<AmortizationEntry, Double> colInterest;

    @FXML
    private TableColumn<AmortizationEntry, Double> colBalance;

    private AmortizationService amortService;

    public void setAmortizationService(AmortizationService service) {
        this.amortService = service;
    }

    @FXML
    private void initialize() {
        colMonth.setCellValueFactory(new PropertyValueFactory<>("month"));
        colPrincipal.setCellValueFactory(new PropertyValueFactory<>("principal"));
        colInterest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        colBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        loadTable();
    }

    private void loadTable() {
        Loan loan = LoanController.getLastLoan();

        if (loan == null || amortService == null) {
            return;
        }

        var list = amortService.generateSchedule(loan);
        tblAmortization.getItems().setAll(list);
    }

    @FXML
    private void onBack() {
        Main.changeScene("loan.fxml");
    }
}

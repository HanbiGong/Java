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
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import ca.senecacollege.application.workshop3.Main;
import ca.senecacollege.application.workshop3.models.*;
import ca.senecacollege.application.workshop3.repositories.LoanRepository;
import ca.senecacollege.application.workshop3.services.LoanCalculation;

public class LoanController {

    @FXML
    private TextField txtDownPayment;

    @FXML
    private ComboBox<String> cmbInterest;

    @FXML
    private Slider sldDuration;

    @FXML
    private ComboBox<String> cmbFrequency;

    @FXML
    private Label lblPayment;

    @FXML
    private Label lblDurationVal;

    @FXML
    private ListView<String> listSaved;

    // Dependencies from DI
    private LoanRepository loanRepo;
    private LoanCalculation loanCalc;

    private static Loan lastLoan;

    public static Loan getLastLoan() {
        return lastLoan;
    }

    // Provided by AppInitializer
    public void setLoanRepository(LoanRepository repo) {
        this.loanRepo = repo;
    }

    public void setLoanCalculation(LoanCalculation calc) {
        this.loanCalc = calc;
    }

    @FXML
    private void initialize() {

        cmbInterest.getItems().addAll("4.5", "5.0", "6.0", "7.0");
        cmbFrequency.getItems().addAll("MONTHLY", "BIWEEKLY", "WEEKLY");

        sldDuration.setValue(36);

        sldDuration.valueProperty().addListener((obs, oldVal, newVal) -> {
            int m = newVal.intValue();
            lblDurationVal.setText(String.valueOf(m));
        });

        lblPayment.setText("$0.00");
    }

    @FXML
    private void onCalculate(ActionEvent e) {

        double down = parse(txtDownPayment.getText());
        double rate = parse(cmbInterest.getValue());
        int months = (int) sldDuration.getValue();

        if (rate <= 0) {
            lblPayment.setText("Select interest");
            return;
        }

        Vehicle v = new Vehicle();
        v.setPrice(25000);

        Loan loan = new Loan();
        loan.setDownPayment(down);
        loan.setInterestRate(rate);
        loan.setDuration(months);
        loan.setVehicle(v);

        double pay = loanCalc.calculatePayment(loan, v);
        lblPayment.setText(String.format("$%.2f", pay));

        lastLoan = loan;
    }

    @FXML
    private void onSave(ActionEvent e) {
        double down = parse(txtDownPayment.getText());
        double rate = parse(cmbInterest.getValue());
        int months = (int) sldDuration.getValue();

        Vehicle v = new Vehicle();
        v.setPrice(25000);

        Loan loan = new Loan();
        loan.setDownPayment(down);
        loan.setInterestRate(rate);
        loan.setDuration(months);
        loan.setVehicle(v);

        loanRepo.saveLoan(loan);

        listSaved.getItems().add("Down $" + down + ", " + months + " months");
    }

    @FXML
    private void onShow(ActionEvent e) {
        listSaved.getItems().clear();

        for (Loan l : loanRepo.getAllLoans()) {
            listSaved.getItems().add(
                    "Down: $" + l.getDownPayment() +
                            ", Rate: " + l.getInterestRate() +
                            ", Months: " + l.getDuration()
            );
        }
    }

    @FXML
    private void onClear(ActionEvent e) {
        txtDownPayment.clear();
        cmbInterest.getSelectionModel().clearSelection();
        cmbFrequency.getSelectionModel().clearSelection();
        sldDuration.setValue(36);
        lblDurationVal.setText("36");
        lblPayment.setText("$0.00");
    }

    @FXML
    private void onAmortization(ActionEvent e) {
        if (lastLoan == null) {
            lblPayment.setText("Calculate first");
            return;
        }
        Main.changeScene("amortization.fxml");
    }

    private double parse(String v) {
        try {
            return Double.parseDouble(v);
        } catch (Exception e) {
            return 0;
        }
    }
}

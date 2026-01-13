package ca.senecacollege.application.workshop3.services;

import ca.senecacollege.application.workshop3.models.Loan;
import ca.senecacollege.application.workshop3.models.Vehicle;

public interface LoanCalculation {
    double calculatePayment(Loan loan, Vehicle vehicle);
}

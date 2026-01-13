package ca.senecacollege.application.workshop3.services;

import ca.senecacollege.application.workshop3.models.Loan;
import ca.senecacollege.application.workshop3.models.Vehicle;

public class FixedRateLoan implements LoanCalculation {

    public FixedRateLoan() {
    }

    @Override
    public double calculatePayment(Loan loan, Vehicle vehicle) {
        double price = vehicle.getPrice();
        double down = loan.getDownPayment();
        double rate = loan.getInterestRate() / 100.0;   // convert %
        int months = loan.getDuration();

        double principal = price - down;

        if (principal <= 0 || months <= 0) {
            return 0;
        }

        double monthlyRate = rate / 12.0;

        // PMT formula
        double top = monthlyRate * Math.pow(1 + monthlyRate, months);
        double bottom = Math.pow(1 + monthlyRate, months) - 1;

        if (bottom == 0) {
            return 0;
        }

        double payment = principal * (top / bottom);
        return payment;
    }
}

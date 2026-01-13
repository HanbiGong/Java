package ca.senecacollege.application.workshop3.services;

import java.util.ArrayList;
import java.util.List;

import ca.senecacollege.application.workshop3.models.*;

public class AmortizationService {

    private LoanCalculation calculator;

    public AmortizationService(LoanCalculation calc) {
        this.calculator = calc;
    }

    public List<AmortizationEntry> generateSchedule(Loan loan) {

        List<AmortizationEntry> list = new ArrayList<>();

        double price = loan.getVehicle().getPrice();
        double down = loan.getDownPayment();
        double balance = price - down;
        double rate = loan.getInterestRate() / 100.0;
        int months = loan.getDuration();

        if (balance <= 0 || months <= 0) {
            return list;
        }

        double monthly = calculator.calculatePayment(loan, loan.getVehicle());
        double monthlyRate = rate / 12.0;

        for (int m = 1; m <= months; m++) {

            double interest = balance * monthlyRate;
            double principal = monthly - interest;
            balance = balance - principal;

            if (balance < 0) {
                balance = 0;  // avoid negative
            }

            AmortizationEntry entry = new AmortizationEntry();
            entry.setMonth(m);
            entry.setInterest(interest);
            entry.setPrincipal(principal);
            entry.setBalance(balance);

            list.add(entry);
        }

        return list;
    }
}

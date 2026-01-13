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
package ca.senecacollege.application.workshop3.repositories;

import java.util.ArrayList;
import java.util.List;

import ca.senecacollege.application.workshop3.models.Loan;

public class LoanRepository {

    private List<Loan> loans = new ArrayList<>();

    public LoanRepository() {
    }

    public void saveLoan(Loan loan) {
        loans.add(loan);
    }

    public List<Loan> getAllLoans() {
        return loans;
    }
}

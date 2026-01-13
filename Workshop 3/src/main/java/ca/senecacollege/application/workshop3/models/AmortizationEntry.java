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
package ca.senecacollege.application.workshop3.models;

import javafx.beans.property.*;

public class AmortizationEntry {

    private IntegerProperty month = new SimpleIntegerProperty();
    private DoubleProperty principal = new SimpleDoubleProperty();
    private DoubleProperty interest = new SimpleDoubleProperty();
    private DoubleProperty balance = new SimpleDoubleProperty();

    public AmortizationEntry() {
    }

    public int getMonth() {
        return month.get();
    }

    public void setMonth(int value) {
        month.set(value);
    }

    public IntegerProperty monthProperty() {
        return month;
    }

    public double getPrincipal() {
        return principal.get();
    }

    public void setPrincipal(double value) {
        principal.set(value);
    }

    public DoubleProperty principalProperty() {
        return principal;
    }

    public double getInterest() {
        return interest.get();
    }

    public void setInterest(double value) {
        interest.set(value);
    }

    public DoubleProperty interestProperty() {
        return interest;
    }

    public double getBalance() {
        return balance.get();
    }

    public void setBalance(double value) {
        balance.set(value);
    }

    public DoubleProperty balanceProperty() {
        return balance;
    }
}

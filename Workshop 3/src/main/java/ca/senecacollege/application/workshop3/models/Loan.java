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

public class Loan {

    private DoubleProperty downPayment = new SimpleDoubleProperty();
    private DoubleProperty interestRate = new SimpleDoubleProperty();
    private IntegerProperty duration = new SimpleIntegerProperty();
    private ObjectProperty<PaymentFrequency> frequency = new SimpleObjectProperty<>();
    private ObjectProperty<Customer> customer = new SimpleObjectProperty<>();
    private ObjectProperty<Vehicle> vehicle = new SimpleObjectProperty<>();

    public Loan() {
    }

    public double getDownPayment() {
        return downPayment.get();
    }

    public void setDownPayment(double value) {
        downPayment.set(value);
    }

    public DoubleProperty downPaymentProperty() {
        return downPayment;
    }

    public double getInterestRate() {
        return interestRate.get();
    }

    public void setInterestRate(double value) {
        interestRate.set(value);
    }

    public DoubleProperty interestRateProperty() {
        return interestRate;
    }

    public int getDuration() {
        return duration.get();
    }

    public void setDuration(int value) {
        duration.set(value);
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public PaymentFrequency getFrequency() {
        return frequency.get();
    }

    public void setFrequency(PaymentFrequency value) {
        frequency.set(value);
    }

    public ObjectProperty<PaymentFrequency> frequencyProperty() {
        return frequency;
    }

    public Customer getCustomer() {
        return customer.get();
    }

    public void setCustomer(Customer value) {
        customer.set(value);
    }

    public ObjectProperty<Customer> customerProperty() {
        return customer;
    }

    public Vehicle getVehicle() {
        return vehicle.get();
    }

    public void setVehicle(Vehicle value) {
        vehicle.set(value);
    }

    public ObjectProperty<Vehicle> vehicleProperty() {
        return vehicle;
    }
}

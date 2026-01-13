/**********************************************
 Workshop # 2
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-24
 **********************************************/
package ca.senecacollege.application.workshop2.model;

import java.time.LocalDate;

// data for vehicle usage
public class UsageLog {

    private LocalDate startDate;
    private LocalDate endDate;
    private double kilometers;

    public UsageLog() {
        // empty constructor
    }

    public UsageLog(LocalDate startDate, LocalDate endDate, double kilometers) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.kilometers = kilometers;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    @Override
    public String toString() {
        return startDate + " -> " + endDate + " / " + kilometers + " km";
    }
}

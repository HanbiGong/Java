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

// data for maintenance
public class MaintenanceRecord {

    private LocalDate date;
    private String description;
    private double cost;

    public MaintenanceRecord() {
        // empty constructor
    }

    public MaintenanceRecord(LocalDate date, String description, double cost) {
        this.date = date;
        this.description = description;
        this.cost = cost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return date + " / " + description + " / $" + cost;
    }
}

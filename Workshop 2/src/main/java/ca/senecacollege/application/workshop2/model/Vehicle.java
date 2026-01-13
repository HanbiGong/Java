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

// simple data class for vehicle info
public class Vehicle {

    private String model;
    private String make;
    private int year;
    private String type;

    public Vehicle() {
        // empty constructor
    }

    public Vehicle(String model, String make, int year, String type) {
        this.model = model;
        this.make = make;
        this.year = year;
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // for printing
    @Override
    public String toString() {
        return model + " / " + make + " / " + year + " / " + type;
    }
}

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

public class Vehicle {

    private ObjectProperty<VehicleType> type = new SimpleObjectProperty<>();
    private ObjectProperty<VehicleAge> age = new SimpleObjectProperty<>();
    private DoubleProperty price = new SimpleDoubleProperty();

    public Vehicle() {
    }

    public VehicleType getType() {
        return type.get();
    }

    public void setType(VehicleType value) {
        type.set(value);
    }

    public ObjectProperty<VehicleType> typeProperty() {
        return type;
    }

    public VehicleAge getAge() {
        return age.get();
    }

    public void setAge(VehicleAge value) {
        age.set(value);
    }

    public ObjectProperty<VehicleAge> ageProperty() {
        return age;
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double value) {
        price.set(value);
    }

    public DoubleProperty priceProperty() {
        return price;
    }
}

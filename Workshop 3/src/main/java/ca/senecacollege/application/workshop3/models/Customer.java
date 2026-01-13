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

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty phone = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty province = new SimpleStringProperty();

    public Customer() {
    }

    public String getName() {
        return name.get();
    }

    public void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String value) {
        city.set(value);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getProvince() {
        return province.get();
    }

    public void setProvince(String value) {
        province.set(value);
    }

    public StringProperty provinceProperty() {
        return province;
    }
}

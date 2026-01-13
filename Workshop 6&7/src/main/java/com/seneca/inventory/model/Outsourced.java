/**********************************************
 Workshop # 4, 5, 6 & 7
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-28
 **********************************************/
package com.seneca.inventory.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Outsourced part - has companyName.
 */
public class Outsourced extends Part {

    private final StringProperty companyName = new SimpleStringProperty();

    public Outsourced(int id,
                      String name,
                      double price,
                      int stock,
                      int min,
                      int max,
                      String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName.set(companyName);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }
}

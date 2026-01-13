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

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/*
 * InHouse part - has machineId.
 */
public class InHouse extends Part {

    private final IntegerProperty machineId = new SimpleIntegerProperty();

    public InHouse(int id,
                   String name,
                   double price,
                   int stock,
                   int min,
                   int max,
                   int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId.set(machineId);
    }

    public int getMachineId() {
        return machineId.get();
    }

    public void setMachineId(int machineId) {
        this.machineId.set(machineId);
    }

    public IntegerProperty machineIdProperty() {
        return machineId;
    }
}

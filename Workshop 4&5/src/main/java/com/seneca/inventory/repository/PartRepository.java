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
package com.seneca.inventory.repository;

import com.seneca.inventory.model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Simple in-memory repository for Parts.
 */
public class PartRepository {

    private final ObservableList<Part> parts = FXCollections.observableArrayList();

    public ObservableList<Part> getAll() {
        return parts;
    }

    public void add(Part part) {
        if (part != null && !parts.contains(part)) {
            parts.add(part);
        }
    }

    public void remove(Part part) {
        parts.remove(part);
    }

    public Part findById(int id) {
        for (Part p : parts) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public ObservableList<Part> searchByName(String name) {
        ObservableList<Part> result = FXCollections.observableArrayList();
        if (name == null || name.isBlank()) {
            result.addAll(parts);
            return result;
        }
        String lower = name.toLowerCase();
        for (Part p : parts) {
            if (p.getName() != null && p.getName().toLowerCase().contains(lower)) {
                result.add(p);
            }
        }
        return result;
    }
}

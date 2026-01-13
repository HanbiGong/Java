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

import com.seneca.inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Simple in-memory repository for Products.
 */
public class ProductRepository {

    private final ObservableList<Product> products = FXCollections.observableArrayList();

    public ObservableList<Product> getAll() {
        return products;
    }

    public void add(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
        }
    }

    public void remove(Product product) {
        products.remove(product);
    }

    public Product findById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public ObservableList<Product> searchByName(String name) {
        ObservableList<Product> result = FXCollections.observableArrayList();
        if (name == null || name.isBlank()) {
            result.addAll(products);
            return result;
        }
        String lower = name.toLowerCase();
        for (Product p : products) {
            if (p.getName() != null && p.getName().toLowerCase().contains(lower)) {
                result.add(p);
            }
        }
        return result;
    }
}

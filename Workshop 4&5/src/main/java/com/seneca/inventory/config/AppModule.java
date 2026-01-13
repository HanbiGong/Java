/**********************************************
 Workshop # 4 & 5
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-16
 **********************************************/
package com.seneca.inventory.config;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.seneca.inventory.repository.PartRepository;
import com.seneca.inventory.repository.ProductRepository;
import com.seneca.inventory.service.InventoryService;

/*
 * Simple Guice module for our app.
 * It wires repositories and service as singletons.
 */
public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        // repositories live as singletons in memory
        bind(PartRepository.class).in(Singleton.class);
        bind(ProductRepository.class).in(Singleton.class);

        // one InventoryService for the whole app
        bind(InventoryService.class).in(Singleton.class);
    }
}

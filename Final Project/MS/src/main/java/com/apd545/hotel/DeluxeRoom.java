package com.apd545.hotel;

public class DeluxeRoom implements Room {

    @Override
    public String getName() {
        return "Deluxe";
    }

    @Override
    public double getBasePrice() {
        return 200;
    }
}

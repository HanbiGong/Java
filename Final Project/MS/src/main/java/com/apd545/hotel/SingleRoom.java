package com.apd545.hotel;

public class SingleRoom implements Room {

    @Override
    public String getName() {
        return "Single";
    }

    @Override
    public double getBasePrice() {
        return 100;
    }
}

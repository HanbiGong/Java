package com.apd545.hotel;

public class DoubleRoom implements Room {

    @Override
    public String getName() {
        return "Double";
    }

    @Override
    public double getBasePrice() {
        return 150;
    }
}

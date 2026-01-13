package com.apd545.hotel;

public class PenthouseRoom implements Room {

    @Override
    public String getName() {
        return "Penthouse";
    }

    @Override
    public double getBasePrice() {
        return 400;
    }
}

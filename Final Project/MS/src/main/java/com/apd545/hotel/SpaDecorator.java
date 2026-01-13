package com.apd545.hotel;

public class SpaDecorator extends RoomPriceDecorator {

    public SpaDecorator(RoomPrice inner) {
        super(inner);
    }

    @Override
    public double calculate() {
        return super.calculate() + 40;
    }
}

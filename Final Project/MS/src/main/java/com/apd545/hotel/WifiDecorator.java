package com.apd545.hotel;

public class WifiDecorator extends RoomPriceDecorator {

    private final long nights;

    public WifiDecorator(RoomPrice inner, long nights) {
        super(inner);
        this.nights = nights;
    }

    @Override
    public double calculate() {
        return super.calculate() + (20 * nights);
    }
}

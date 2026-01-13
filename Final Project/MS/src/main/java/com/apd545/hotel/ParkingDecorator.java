package com.apd545.hotel;

public class ParkingDecorator extends RoomPriceDecorator {

    private final long nights;

    public ParkingDecorator(RoomPrice inner, long nights) {
        super(inner);
        this.nights = nights;
    }

    @Override
    public double calculate() {
        return super.calculate() + (25 * nights);
    }
}

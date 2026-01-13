package com.apd545.hotel;

public class BreakfastDecorator extends RoomPriceDecorator {

    private final long nights;

    public BreakfastDecorator(RoomPrice inner, long nights) {
        super(inner);
        this.nights = nights;
    }

    @Override
    public double calculate() {
        return super.calculate() + (15 * nights);
    }
}

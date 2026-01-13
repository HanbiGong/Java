package com.apd545.hotel;

public abstract class RoomPriceDecorator implements RoomPrice {

    protected RoomPrice inner;

    public RoomPriceDecorator(RoomPrice inner) {
        this.inner = inner;
    }

    @Override
    public double calculate() {
        return inner.calculate();
    }
}

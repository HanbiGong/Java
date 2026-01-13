package com.apd545.hotel;

public class BaseRoomPrice implements RoomPrice {

    private final BookingInfo info;
    private final long nights;

    public BaseRoomPrice(BookingInfo info, long nights) {
        this.info = info;
        this.nights = nights;
    }

    @Override
    public double calculate() {

        double room = 0;

        room += info.getSingleCount() * 100;
        room += info.getDoubleCount() * 150;
        room += info.getDeluxeCount() * 200;
        room += info.getPenthouseCount() * 400;

        return room * nights;
    }
}

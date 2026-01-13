package com.apd545.hotel;

public class RoomFactory {

    public static Room create(String roomName) {

        if (roomName == null) return null;

        switch(roomName) {
            case "Single":
                return new SingleRoom();
            case "Double":
                return new DoubleRoom();
            case "Deluxe":
                return new DeluxeRoom();
            case "Penthouse":
                return new PenthouseRoom();
            default:
                return null;
        }
    }
}

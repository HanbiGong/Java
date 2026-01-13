package com.apd545.hotel;

public class AppState {

    private static BookingInfo bookingInfo;
    private static Reservation lastReservation;

    // NEW: current logged admin
    private static Admin currentAdmin;

    public static BookingInfo getBookingInfo() {
        return bookingInfo;
    }

    public static void setBookingInfo(BookingInfo b) {
        bookingInfo = b;
    }

    public static Reservation getLastReservation() {
        return lastReservation;
    }

    public static void setLastReservation(Reservation r) {
        lastReservation = r;
    }

    // ----------------- ADMIN SESSION -----------------
    public static void setCurrentAdmin(Admin a) {
        currentAdmin = a;
    }

    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void reset() {
        bookingInfo = null;
        lastReservation = null;
        currentAdmin = null;
    }
}

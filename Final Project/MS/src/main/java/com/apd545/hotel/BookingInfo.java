package com.apd545.hotel;

import java.time.LocalDate;

public class BookingInfo {

    // Step1
    private int adults;
    private int children;

    // Step2
    private LocalDate checkIn;
    private LocalDate checkOut;

    // Step3
    private int singleCount;
    private int doubleCount;
    private int deluxeCount;
    private int penthouseCount;

    // Step4 (Customer Info)
    private String guestName;
    private String phone;

    // Step5 Add-ons
    private boolean wifi;
    private boolean breakfast;
    private boolean parking;
    private boolean spa;

    // Totals
    private double subtotal;
    private double tax;
    private double total;

    // ---------------- GETTERS / SETTERS ----------------

    public int getAdults() { return adults; }
    public void setAdults(int adults) { this.adults = adults; }

    public int getChildren() { return children; }
    public void setChildren(int children) { this.children = children; }

    public LocalDate getCheckIn() { return checkIn; }
    public void setCheckIn(LocalDate checkIn) { this.checkIn = checkIn; }

    public LocalDate getCheckOut() { return checkOut; }
    public void setCheckOut(LocalDate checkOut) { this.checkOut = checkOut; }

    public int getSingleCount() { return singleCount; }
    public void setSingleCount(int c) { this.singleCount = c; }

    public int getDoubleCount() { return doubleCount; }
    public void setDoubleCount(int c) { this.doubleCount = c; }

    public int getDeluxeCount() { return deluxeCount; }
    public void setDeluxeCount(int c) { this.deluxeCount = c; }

    public int getPenthouseCount() { return penthouseCount; }
    public void setPenthouseCount(int c) { this.penthouseCount = c; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public boolean isWifi() { return wifi; }
    public void setWifi(boolean wifi) { this.wifi = wifi; }

    public boolean isBreakfast() { return breakfast; }
    public void setBreakfast(boolean breakfast) { this.breakfast = breakfast; }

    public boolean isParking() { return parking; }
    public void setParking(boolean parking) { this.parking = parking; }

    public boolean isSpa() { return spa; }
    public void setSpa(boolean spa) { this.spa = spa; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getTax() { return tax; }
    public void setTax(double tax) { this.tax = tax; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}

package com.apd545.hotel;

public class RevenueRow {
    private String date;
    private String roomName;
    private int nights;
    private double amount;
    private String payment;

    public RevenueRow(String date, String roomName, int nights, double amount, String payment) {
        this.date = date;
        this.roomName = roomName;
        this.nights = nights;
        this.amount = amount;
        this.payment = payment;
    }

    public String getDate() { return date; }
    public String getRoomName() { return roomName; }
    public int getNights() { return nights; }
    public double getAmount() { return amount; }
    public String getPayment() { return payment; }
}


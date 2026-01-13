package com.apd545.hotel;

public class OccupancyRow {

    private String date;
    private int totalRooms;
    private int occupied;
    private double percent;

    public OccupancyRow(String date, int totalRooms, int occupied, double percent) {
        this.date = date;
        this.totalRooms = totalRooms;
        this.occupied = occupied;
        this.percent = percent;
    }

    public String getDate() { return date; }
    public int getTotalRooms() { return totalRooms; }
    public int getOccupied() { return occupied; }
    public double getPercent() { return percent; }
}

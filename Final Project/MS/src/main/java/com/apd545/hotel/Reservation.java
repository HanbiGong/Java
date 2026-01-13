package com.apd545.hotel;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String guestName;
    private String roomName;

    private LocalDate checkIn;
    private LocalDate checkOut;

    private String addons;
    private double total;
    private String status;
    private String phone;

    private int rating;   // feedback rating

    @Column(length = 1000)
    private String comments;

    private String sentiment;

    // JPA requires default constructor
    public Reservation() {}

    // Normal constructor (WITHOUT ID)
    public Reservation(String guestName,
                       String roomName,
                       LocalDate checkIn,
                       LocalDate checkOut,
                       String addons,
                       double total,
                       String phone) {

        this.guestName = guestName;
        this.roomName = roomName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.addons = addons;
        this.total = total;
        this.status = "Booked";
        this.phone = phone;
    }

    // ---------- Getters / Setters ----------

    public int getId() { return id; }

    public String getGuestName() { return guestName; }
    public void setGuestName(String guestName) { this.guestName = guestName; }

    public String getRoomName() { return roomName; }

    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    public String getAddons() { return addons; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPhoneNumber() { return phone; }
    public String getPhone() { return phone; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    // ---------- Sentiment Getter/Setter ----------
    public String getSentiment() {   // <-- added
        return sentiment;
    }

    public void setSentiment(String sentiment) {   // <-- added
        this.sentiment = sentiment;
    }
}

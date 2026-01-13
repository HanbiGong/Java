package com.apd545.hotel;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Stores admin/kiosk activity logs (login, reservation, checkout, etc.)
 */
@Entity
public class ActivityLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime timestamp;

    @Column(name = "username")
    private String username;

    private String action;

    public ActivityLog() {}

    public ActivityLog(String username, String action) {
        this.timestamp = LocalDateTime.now();
        this.username = username;
        this.action = action;
    }

    public int getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getUsername() { return username; }
    public String getAction() { return action; }
}

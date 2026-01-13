package com.apd545.hotel;

public class Admin {

    private String email;
    private String role; // "Admin" or "Manager"

    public Admin(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() { return email; }
    public String getRole() { return role; }
}

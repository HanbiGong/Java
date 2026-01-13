package com.apd545.hotel;

import jakarta.persistence.*;

/**
 * Simple customer model used for kiosk loyalty check.
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String province;
    private String city;
    private String country;
    private String address;
    private String email;
    private String phone;

    private String loyaltyNumber; // null if not registered
    private int points;           // simple integer points

    private boolean registerForLoyalty;  // NEW

    // *** JPA 에 필요한 기본 생성자 ***
    public Customer() {
    }

    // -------- Getters / Setters --------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoyaltyNumber() {
        return loyaltyNumber;
    }

    public void setLoyaltyNumber(String loyaltyNumber) {
        this.loyaltyNumber = loyaltyNumber;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isRegisterForLoyalty() {  // NEW
        return registerForLoyalty;
    }

    public void setRegisterForLoyalty(boolean registerForLoyalty) { // NEW
        this.registerForLoyalty = registerForLoyalty;
    }

}

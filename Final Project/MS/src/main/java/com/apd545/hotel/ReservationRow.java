
package com.apd545.hotel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
public class ReservationRow {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty guest = new SimpleStringProperty();
    private final StringProperty nights = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    public ReservationRow(String id, String guest, String nights, String status) {
        this.id.set(id); this.guest.set(guest); this.nights.set(nights); this.status.set(status);
    }
    public StringProperty idProperty(){ return id; }
    public StringProperty guestProperty(){ return guest; }
    public StringProperty nightsProperty(){ return nights; }
    public StringProperty statusProperty(){ return status; }
}

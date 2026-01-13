package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.time.temporal.ChronoUnit;

public class SummaryController {

    @FXML private Label lblAdults;
    @FXML private Label lblChildren;
    @FXML private Label lblCheckIn;
    @FXML private Label lblCheckOut;
    @FXML private Label lblNights;

    @FXML private Label lblWifi;
    @FXML private Label lblBreakfast;
    @FXML private Label lblParking;
    @FXML private Label lblSpa;

    @FXML private Label lblSubtotal;
    @FXML private Label lblTax;
    @FXML private Label lblTotal;

    private BookingInfo info;

    @FXML
    public void initialize() {

        info = AppState.getBookingInfo();
        if (info == null) return;

        // basic info
        lblAdults.setText(String.valueOf(info.getAdults()));
        lblChildren.setText(String.valueOf(info.getChildren()));

        if (info.getCheckIn() != null) lblCheckIn.setText(info.getCheckIn().toString());
        if (info.getCheckOut() != null) lblCheckOut.setText(info.getCheckOut().toString());

        // number of nights
        long nights = 0;
        if (info.getCheckIn() != null && info.getCheckOut() != null) {
            nights = ChronoUnit.DAYS.between(info.getCheckIn(), info.getCheckOut());
        }
        lblNights.setText(String.valueOf(nights));

        // add-on labels
        lblWifi.setText(info.isWifi() ? "Yes" : "No");
        lblBreakfast.setText(info.isBreakfast() ? "Yes" : "No");
        lblParking.setText(info.isParking() ? "Yes" : "No");
        lblSpa.setText(info.isSpa() ? "Yes" : "No");

        // ----------------------------------------------------------------------
        // Room price with Factory + Decorator pattern
        // ----------------------------------------------------------------------

        // base decorator (room price per night)
        RoomPrice price = new BaseRoomPrice(info, nights);

        // add-on decorators
        if (info.isWifi())      price = new WifiDecorator(price, nights);
        if (info.isBreakfast()) price = new BreakfastDecorator(price, nights);
        if (info.isParking())   price = new ParkingDecorator(price, nights);
        if (info.isSpa())       price = new SpaDecorator(price);

        // final amounts
        double subtotal = price.calculate();
        double tax = subtotal * 0.13;
        double total = subtotal + tax;

        // save to BookingInfo
        info.setSubtotal(subtotal);
        info.setTax(tax);
        info.setTotal(total);

        // show on UI
        lblSubtotal.setText(String.format("$%.2f", subtotal));
        lblTax.setText(String.format("$%.2f", tax));
        lblTotal.setText(String.format("$%.2f", total));
    }

    @FXML
    private void backToAddOn() {
        App.setRoot("KioskAddOn");
    }

    @FXML
    private void goToConfirmation() {
        ActivityLogService.getInstance().log("KIOSK", "SUMMARY CONFIRMED");
        App.setRoot("Confirmation");
    }
}

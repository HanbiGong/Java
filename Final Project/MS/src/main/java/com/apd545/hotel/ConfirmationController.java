package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Step 7 - Final confirmation screen.
 * Shows booking info and saves the reservation into DB.
 */
public class ConfirmationController {

    @FXML private Label lblGuest;
    @FXML private Label lblRoom;
    @FXML private Label lblCheckIn;
    @FXML private Label lblCheckOut;
    @FXML private Label lblAddons;
    @FXML private Label lblTotal;

    private BookingInfo info;

    @FXML
    public void initialize() {

        // load booking info
        info = AppState.getBookingInfo();

        if (info == null) {
            lblGuest.setText("-");
            lblRoom.setText("-");
            lblCheckIn.setText("-");
            lblCheckOut.setText("-");
            lblAddons.setText("-");
            lblTotal.setText("-");
            return;
        }

        lblGuest.setText(info.getGuestName());
        lblRoom.setText(buildRoomSummary());
        lblCheckIn.setText(info.getCheckIn() != null ? info.getCheckIn().toString() : "-");
        lblCheckOut.setText(info.getCheckOut() != null ? info.getCheckOut().toString() : "-");
        lblAddons.setText(buildAddonsSummary());
        lblTotal.setText(String.format("$%.2f", info.getTotal()));
    }

    // room summary builder
    private String buildRoomSummary() {
        StringBuilder sb = new StringBuilder();

        if (info.getSingleCount() > 0) sb.append("Single x").append(info.getSingleCount()).append(", ");
        if (info.getDoubleCount() > 0) sb.append("Double x").append(info.getDoubleCount()).append(", ");
        if (info.getDeluxeCount() > 0) sb.append("Deluxe x").append(info.getDeluxeCount()).append(", ");
        if (info.getPenthouseCount() > 0) sb.append("Penthouse x").append(info.getPenthouseCount()).append(", ");

        if (sb.length() == 0) return "None";
        return sb.substring(0, sb.length() - 2);
    }

    // addon summary builder
    private String buildAddonsSummary() {
        StringBuilder sb = new StringBuilder();

        if (info.isWifi()) sb.append("Wi-Fi, ");
        if (info.isBreakfast()) sb.append("Breakfast, ");
        if (info.isParking()) sb.append("Parking, ");
        if (info.isSpa()) sb.append("Spa, ");

        if (sb.length() == 0) return "None";
        return sb.substring(0, sb.length() - 2);
    }

    @FXML
    private void handleConfirm() {

        ReservationService service = ReservationService.getInstance();

        Reservation r = service.createReservation(
                info.getGuestName(),
                buildRoomSummary(),
                info.getCheckIn(),
                info.getCheckOut(),
                buildAddonsSummary(),
                info.getTotal(),
                info.getPhone()
        );

        // store last reservation for next screen
        AppState.setLastReservation(r);

        App.setRoot("KioskDone");
    }

    @FXML
    private void handleBack() {
        App.setRoot("Summary");
    }
}


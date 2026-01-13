package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * Final kiosk receipt screen.
 * Loads the last saved reservation from AppState.
 */
public class KioskDoneController {

    @FXML private Label lblResId;
    @FXML private Label lblGuest;
    @FXML private Label lblRoom;
    @FXML private Label lblCheckIn;
    @FXML private Label lblCheckOut;
    @FXML private Label lblTotal;

    @FXML
    public void initialize() {

        Reservation r = AppState.getLastReservation();

        // if no reservation, show placeholder
        if (r == null) {
            lblResId.setText("N/A");
            lblGuest.setText("-");
            lblRoom.setText("-");
            lblCheckIn.setText("-");
            lblCheckOut.setText("-");
            lblTotal.setText("-");
            return;
        }

        lblResId.setText(String.valueOf(r.getId()));
        lblGuest.setText(r.getGuestName() != null ? r.getGuestName() : "-");
        lblRoom.setText(r.getRoomName() != null ? r.getRoomName() : "-");
        lblCheckIn.setText(r.getCheckIn() != null ? r.getCheckIn().toString() : "-");
        lblCheckOut.setText(r.getCheckOut() != null ? r.getCheckOut().toString() : "-");
        lblTotal.setText(String.format("$%.2f", r.getTotal()));
    }

    @FXML
    private void handleBackToHome() {
        App.setRoot("LaunchScreen");
    }
}

package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import java.time.LocalDate;

public class KioskStep2Controller {

    @FXML private DatePicker dpCheckIn;
    @FXML private DatePicker dpCheckOut;
    @FXML private Label lblError;

    @FXML
    public void initialize() {
        if (dpCheckIn != null && dpCheckOut != null) {
            LocalDate today = LocalDate.now();
            dpCheckIn.setValue(today.plusDays(1));
            dpCheckOut.setValue(today.plusDays(2));
        }
    }

    @FXML
    public void backToStep1() {
        App.setRoot("KioskStep1");
    }

    @FXML
    public void goStep3() {
        LocalDate in = dpCheckIn.getValue();
        LocalDate out = dpCheckOut.getValue();

        if (in == null || out == null) {
            lblError.setText("Please select both check-in and check-out dates.");
            return;
        }

        if (!out.isAfter(in)) {
            lblError.setText("Check-out must be after check-in.");
            return;
        }

        BookingInfo info = AppState.getBookingInfo();
        info.setCheckIn(in);
        info.setCheckOut(out);

        AppState.setBookingInfo(info);

        App.setRoot("KioskStep3");
    }
}

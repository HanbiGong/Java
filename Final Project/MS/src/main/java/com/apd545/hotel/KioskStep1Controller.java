package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class KioskStep1Controller {

    @FXML private Spinner<Integer> spAdults;
    @FXML private Spinner<Integer> spChildren;

    @FXML
    public void initialize() {
        if (spAdults != null) {
            spAdults.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6, 2)
            );
        }
        if (spChildren != null) {
            spChildren.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6, 0)
            );
        }
    }

    @FXML
    public void goStep2() {

        BookingInfo info = new BookingInfo();
        info.setAdults(spAdults.getValue());
        info.setChildren(spChildren.getValue());

        // save to global
        AppState.setBookingInfo(info);

        App.setRoot("KioskStep2");
    }
}

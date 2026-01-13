package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class KioskAddOnController {

    @FXML private CheckBox cbWifi;
    @FXML private CheckBox cbBreakfast;
    @FXML private CheckBox cbParking;
    @FXML private CheckBox cbSpa;

    @FXML private Label lblMessage;

    @FXML
    private void backToCustomerInfo() {
        App.setRoot("CustomerInfo");
    }

    @FXML
    private void onShowSummary() {

        BookingInfo info = AppState.getBookingInfo();
        if (info == null) {
            lblMessage.setText("Missing booking info.");
            return;
        }

        info.setWifi(cbWifi.isSelected());
        info.setBreakfast(cbBreakfast.isSelected());
        info.setParking(cbParking.isSelected());
        info.setSpa(cbSpa.isSelected());

        AppState.setBookingInfo(info);
        App.setRoot("Summary");
    }
}

package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class KioskStep3Controller {

    @FXML private Spinner<Integer> spSingle;
    @FXML private Spinner<Integer> spDouble;
    @FXML private Spinner<Integer> spDeluxe;
    @FXML private Spinner<Integer> spPenthouse;

    @FXML private Label lblError;

    // Saved values (will be used in Step5/6)
    private static int savedSingle;
    private static int savedDouble;
    private static int savedDeluxe;
    private static int savedPenthouse;

    @FXML
    public void initialize() {

        if (spSingle != null) {
            spSingle.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 1)
            );
        }
        if (spDouble != null) {
            spDouble.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0)
            );
        }
        if (spDeluxe != null) {
            spDeluxe.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0)
            );
        }
        if (spPenthouse != null) {
            spPenthouse.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5, 0)
            );
        }
    }

    @FXML
    public void backToStep2() {
        App.setRoot("KioskStep2");
    }

    @FXML
    public void goCustomerInfo() {

        if (spSingle.getValue() == 0 &&
                spDouble.getValue() == 0 &&
                spDeluxe.getValue() == 0 &&
                spPenthouse.getValue() == 0) {

            lblError.setText("Please select at least one room.");
            return;
        }

        BookingInfo info = AppState.getBookingInfo();
        info.setSingleCount(spSingle.getValue());
        info.setDoubleCount(spDouble.getValue());
        info.setDeluxeCount(spDeluxe.getValue());
        info.setPenthouseCount(spPenthouse.getValue());

        AppState.setBookingInfo(info);
        App.setRoot("CustomerInfo");
    }


    // Static getters for Step4~6
    public static int getSingleCount() { return savedSingle; }
    public static int getDoubleCount() { return savedDouble; }
    public static int getDeluxeCount() { return savedDeluxe; }
    public static int getPenthouseCount() { return savedPenthouse; }
}

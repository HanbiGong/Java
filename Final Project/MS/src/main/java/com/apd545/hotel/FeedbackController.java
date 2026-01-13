package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class FeedbackController {

    @FXML private TextField txtPhone;
    @FXML private Label lblCheckMsg;
    @FXML private VBox boxPhone;

    @FXML private VBox boxForm;
    @FXML private Slider sldRating;
    @FXML private TextArea txtComments;
    @FXML private Label lblMsg;

    private final ReservationService reservationService = ReservationService.getInstance();
    private Reservation foundReservation;

    @FXML
    public void initialize() {

        if (sldRating != null) {
            sldRating.setMin(1);
            sldRating.setMax(5);
            sldRating.setMajorTickUnit(1);
            sldRating.setShowTickMarks(true);
            sldRating.setShowTickLabels(true);
            sldRating.setValue(5);
        }

        if (boxForm != null) boxForm.setVisible(false);
    }

    @FXML
    public void onCheckPhone() {

        String phone = txtPhone.getText().trim();

        if (phone.isEmpty()) {
            lblCheckMsg.setText("Please enter your phone number.");
            boxForm.setVisible(false);
            return;
        }

        foundReservation = reservationService.findByPhone(phone);

        if (foundReservation == null) {
            lblCheckMsg.setText("No reservation found.");
            boxForm.setVisible(false);
            return;
        }

        if (!"Checked-out".equalsIgnoreCase(foundReservation.getStatus())) {
            lblCheckMsg.setText("Feedback available only after checkout.");
            boxForm.setVisible(false);
            return;
        }

        lblCheckMsg.setText("Reservation found. You may submit feedback.");
        boxForm.setVisible(true);
    }

    @FXML
    public void onSubmit() {

        if (foundReservation == null) {
            lblMsg.setText("Check phone number first.");
            return;
        }

        int rating = (int) sldRating.getValue();
        String comments = txtComments.getText().trim();

        // sentiment 계산은 ReservationService에서 처리됨
        reservationService.saveFeedback(foundReservation, rating, comments);

        lblMsg.setText("Thank you! Feedback saved.");
        txtComments.clear();
    }

    @FXML
    public void onBack() {
        App.setRoot("LaunchScreen");
    }
}

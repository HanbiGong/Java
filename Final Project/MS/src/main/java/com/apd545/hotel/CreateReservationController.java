package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateReservationController {

    @FXML private TextField txtGuest;
    @FXML private TextField txtRoom;
    @FXML private DatePicker dpIn;
    @FXML private DatePicker dpOut;
    @FXML private TextField txtAddon;
    @FXML private TextField txtTotal;
    @FXML private TextField txtPhone;
    @FXML private Label lblMsg;

    private final ReservationService service = ReservationService.getInstance();

    @FXML
    private void onSave() {

        if (txtGuest.getText().isBlank() ||
                txtRoom.getText().isBlank() ||
                dpIn.getValue() == null ||
                dpOut.getValue() == null ||
                txtTotal.getText().isBlank() ||
                txtPhone.getText().isBlank()) {

            lblMsg.setText("All fields are required.");
            return;
        }

        double total = 0;
        try {
            total = Double.parseDouble(txtTotal.getText());
        } catch (Exception e) {
            lblMsg.setText("Total must be a number.");
            return;
        }

        service.createReservation(
                txtGuest.getText(),
                txtRoom.getText(),
                dpIn.getValue(),
                dpOut.getValue(),
                txtAddon.getText(),
                total,
                txtPhone.getText()
        );

        ((Stage) lblMsg.getScene().getWindow()).close();
    }

    @FXML
    private void onCancel() {
        ((Stage) lblMsg.getScene().getWindow()).close();
    }
}

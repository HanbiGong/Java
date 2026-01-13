package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class EditReservationController {

    @FXML private TextField txtGuest;
    @FXML private TextField txtRoom;
    @FXML private DatePicker dpIn;
    @FXML private DatePicker dpOut;
    @FXML private TextField txtAddon;
    @FXML private TextField txtTotal;
    @FXML private TextField txtStatus;
    @FXML private Label lblMsg;

    private Reservation target;
    private final ReservationService service = ReservationService.getInstance();

    public void init(Reservation r) {
        this.target = r;

        txtGuest.setText(r.getGuestName());
        txtRoom.setText(r.getRoomName());
        dpIn.setValue(r.getCheckIn());
        dpOut.setValue(r.getCheckOut());
        txtAddon.setText(r.getAddons());
        txtTotal.setText(String.valueOf(r.getTotal()));
        txtStatus.setText(r.getStatus());
    }

    @FXML
    private void onSave() {

        if (txtGuest.getText().isBlank() ||
                txtRoom.getText().isBlank() ||
                dpIn.getValue() == null ||
                dpOut.getValue() == null ||
                txtTotal.getText().isBlank()) {

            lblMsg.setText("Missing fields.");
            return;
        }

        try {
            target.setTotal(Double.parseDouble(txtTotal.getText()));
        } catch (Exception e) {
            lblMsg.setText("Total must be number.");
            return;
        }

        target.setGuestName(txtGuest.getText());
        target.setStatus(txtStatus.getText());

        service.update(target);

        ((Stage) lblMsg.getScene().getWindow()).close();
    }

    @FXML
    private void onCancel() {
        ((Stage) lblMsg.getScene().getWindow()).close();
    }
}

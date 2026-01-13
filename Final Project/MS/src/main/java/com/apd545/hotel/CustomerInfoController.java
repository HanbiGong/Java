package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CustomerInfoController {

    @FXML private TextField txtFirstName;
    @FXML private TextField txtLastName;
    @FXML private TextField txtProvince;
    @FXML private TextField txtCity;
    @FXML private TextField txtCountry;
    @FXML private TextField txtAddress;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhone;

    @FXML private VBox loyaltyBox;
    @FXML private Label loyaltyNumberLabel;
    @FXML private Label loyaltyPointsLabel;

    @FXML private CheckBox registerCheckBox;
    @FXML private Label lblError;

    private final CustomerService customerService = CustomerService.getInstance();

    @FXML
    public void initialize() {

        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() > 3) {
                checkLoyalty();
            }
        });

        txtPhone.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() > 3) {
                checkLoyalty();
            }
        });
    }


    private void checkLoyalty() {
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        if (email == null || phone == null || email.isBlank() || phone.isBlank()) {
            return;
        }

        Customer c = customerService.findCustomer(email, phone);

        if (c != null && c.getLoyaltyNumber() != null) {

            loyaltyBox.setVisible(true);
            registerCheckBox.setVisible(false);

            loyaltyNumberLabel.setText("Loyalty #: " + c.getLoyaltyNumber());
            loyaltyPointsLabel.setText("Points: " + c.getPoints());
        }
        else {
            loyaltyBox.setVisible(false);
            registerCheckBox.setVisible(true);
        }
    }

    @FXML
    private void handleNext() {

        // validation
        if (txtFirstName.getText().isEmpty()
                || txtLastName.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtPhone.getText().isEmpty()) {

            lblError.setText("Please fill all required fields.");
            return;
        }

        // booking info
        BookingInfo info = AppState.getBookingInfo();
        if (info != null) {
            info.setPhone(txtPhone.getText());
            info.setGuestName(txtFirstName.getText() + " " + txtLastName.getText());
        }

        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        Customer c = customerService.findCustomer(email, phone);

        if (c == null) {
            c = new Customer();
            c.setEmail(email);
            c.setPhone(phone);

            if (registerCheckBox.isSelected()) {
                c.setRegisterForLoyalty(true);
                c.setLoyaltyNumber(customerService.generateLoyaltyNumber());
                c.setPoints(0);
            }
        }

        c.setFirstName(txtFirstName.getText());
        c.setLastName(txtLastName.getText());
        c.setProvince(txtProvince.getText());
        c.setCity(txtCity.getText());
        c.setCountry(txtCountry.getText());
        c.setAddress(txtAddress.getText());

        customerService.saveOrUpdate(c);

        App.setRoot("KioskAddOn");
    }

    @FXML
    private void backToStep3() {
        App.setRoot("KioskStep3");
    }
}

package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AdminController {

    @FXML private TextField txtEmail;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMsg;

    private final ActivityLogService logService = ActivityLogService.getInstance();

    @FXML
    public void onBack() {
        App.setRoot("LaunchScreen");
    }

    @FXML
    public void onLogin() {

        String email = txtEmail.getText().trim();
        String pass = txtPassword.getText().trim();

        if (email.isBlank() || pass.isBlank()) {
            lblMsg.setText("Please enter email and password.");
            return;
        }

        // ----- simple hard-coded credential check -----
        Admin admin = null;

        if (email.equals("admin@example.com") && pass.equals("admin123")) {
            admin = new Admin(email, "Admin");
        }
        else if (email.equals("manager@example.com") && pass.equals("manager123")) {
            admin = new Admin(email, "Manager");
        }

        if (admin == null) {
            lblMsg.setText("Invalid credentials.");
            logService.log(email, "ADMIN LOGIN FAILED");
            return;
        }

        // successful login
        AppState.setCurrentAdmin(admin);
        lblMsg.setText("");

        logService.log(email, "LOGIN SUCCESS (" + admin.getRole() + ")");

        App.setRoot("AdminDashboard");
    }
}

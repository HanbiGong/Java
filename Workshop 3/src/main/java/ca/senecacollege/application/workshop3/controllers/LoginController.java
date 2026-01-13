/**********************************************
 Workshop # 3
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-12-01
 **********************************************/
package ca.senecacollege.application.workshop3.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import ca.senecacollege.application.workshop3.repositories.UserRepository;
import ca.senecacollege.application.workshop3.Main;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Label lblMessage;

    private UserRepository userRepo;

    public void setUserRepository(UserRepository repo) {
        this.userRepo = repo;
    }

    @FXML
    private void onLogin(ActionEvent e) {

        String u = txtUsername.getText();
        String p = txtPassword.getText();

        if (u.isEmpty() || p.isEmpty()) {
            lblMessage.setText("Enter username & password");
            return;
        }

        boolean ok = userRepo.validateLogin(u, p);

        if (!ok) {
            lblMessage.setText("Invalid login");
            return;
        }

        lblMessage.setText("Login success");
        Main.changeScene("customer.fxml");
    }
    @FXML
    private void onBack(ActionEvent e) {
        Main.changeScene("homepage.fxml");
    }

}

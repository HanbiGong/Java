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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

import ca.senecacollege.application.workshop3.repositories.UserRepository;
import ca.senecacollege.application.workshop3.models.User;
import ca.senecacollege.application.workshop3.Main;

public class SignupController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirm;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblMessage;

    private UserRepository userRepo;

    public void setUserRepository(UserRepository repo) {
        this.userRepo = repo;
    }

    @FXML
    private void onCreate(ActionEvent e) {

        String u = txtUsername.getText();
        String p = txtPassword.getText();
        String c = txtConfirm.getText();
        String m = txtEmail.getText();

        if (u.isEmpty() || p.isEmpty() || c.isEmpty() || m.isEmpty()) {
            lblMessage.setText("All fields required");
            return;
        }

        if (!p.equals(c)) {
            lblMessage.setText("Password mismatch");
            return;
        }

        if (userRepo.findUser(u) != null) {
            lblMessage.setText("Username already exists");
            return;
        }

        User user = new User();
        user.setUsername(u);
        user.setPassword(p);
        user.setEmail(m);

        userRepo.addUser(user);

        lblMessage.setText("Signup successful");
        Main.changeScene("login.fxml");
    }
    @FXML
    private void onBack(ActionEvent e) {
        Main.changeScene("homepage.fxml");
    }

}

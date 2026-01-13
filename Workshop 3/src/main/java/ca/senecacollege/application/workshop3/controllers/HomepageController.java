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
import javafx.event.ActionEvent;
import ca.senecacollege.application.workshop3.Main;

public class HomepageController {

    @FXML
    private void onLogin(ActionEvent e) {
        Main.changeScene("login.fxml");
    }

    @FXML
    private void onSignup(ActionEvent e) {
        Main.changeScene("signup.fxml");
    }
}

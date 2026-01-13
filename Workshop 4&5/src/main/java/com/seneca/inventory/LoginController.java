/**********************************************
 Workshop # 4, 5, 6 & 7
 Course: APD545 - Fall 2025
 Last Name: Gong
 First Name: Hanbi
 ID: 111932224
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Signature
 Date: 2025-11-28
 **********************************************/
package com.seneca.inventory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField inputUsername;
    @FXML private PasswordField inputPassword;

    @FXML
    private void onLogin(ActionEvent e) {
        try {
            Parent main = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
            Stage stage = (Stage) inputUsername.getScene().getWindow();
            stage.setScene(new Scene(main));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onCancel(ActionEvent e) {
        Stage stage = (Stage) inputUsername.getScene().getWindow();
        stage.close();
    }
}
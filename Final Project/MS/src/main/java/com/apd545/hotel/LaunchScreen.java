package com.apd545.hotel;

import javafx.event.ActionEvent;

public class LaunchScreen {

    // Kiosk self-service flow (starts at Step 1: how many guests)
    public void goKiosk(ActionEvent e) {
        App.setRoot("KioskStep1");
    }

    // Admin module: authentication + dashboard + admin functions
    public void goAdmin(ActionEvent e) {
        App.setRoot("AdminLogin");
    }

    // Guest feedback submission screen
    public void goFeedback(ActionEvent e) {
        App.setRoot("Feedback");
    }

    // Reporting module (tabular only: revenue, occupancy, activity logs)
    public void goReporting(ActionEvent e) {
        App.setRoot("Reporting");
    }

    // Optional: open rules & regulations screen later, for now just stay here
    public void showRules(ActionEvent e) {
        // You can replace this with a popup later.
        // For the front-end screenshot it is enough that the button exists.
    }
}

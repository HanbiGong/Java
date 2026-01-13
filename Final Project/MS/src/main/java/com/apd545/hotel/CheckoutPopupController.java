package com.apd545.hotel;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CheckoutPopupController {

    @FXML private Label lblTotal;
    @FXML private TextField txtDiscount;
    @FXML private TextField txtPoints;
    @FXML private TextField txtPayAmount;
    @FXML private ComboBox<String> cbType;
    @FXML private Label lblMsg;

    private Reservation target;
    private final ReservationService reservationService = ReservationService.getInstance();
    private final CustomerService customerService = CustomerService.getInstance();

    public void init(Reservation r) {
        this.target = r;

        lblTotal.setText(String.format("%.2f", r.getTotal()));

        cbType.getItems().addAll("CASH", "CARD", "POINTS");
        cbType.getSelectionModel().select("CASH");
    }

    @FXML
    private void onConfirm() {

        double discount = parseOrZero(txtDiscount.getText());
        int usePoints = (int) parseOrZero(txtPoints.getText());
        double payAmount = parseOrZero(txtPayAmount.getText());

        String type = cbType.getValue();

        // ----- Strategy: Admin(15%) Manager(30%) -----
        String role = AppState.getCurrentAdmin().getRole();
        double maxDiscount = role.equals("Admin") ? 15 : 30;

        if (discount > maxDiscount) {
            lblMsg.setText("Max discount for your role is: " + maxDiscount + "%");
            return;
        }

        // --- discount from percentage to amount ---
        double discountAmount = (discount / 100.0) * target.getTotal();

        // --- points check ---
        Customer customer = customerService.findCustomer(null, target.getPhoneNumber());
        if (customer != null && usePoints > customer.getPoints()) {
            lblMsg.setText("Not enough points.");
            return;
        }

        // --- apply computations ---
        double newTotal = target.getTotal() - discountAmount - usePoints;
        if (newTotal < 0) newTotal = 0;

        // partial payment
        newTotal -= payAmount;
        if (newTotal < 0) newTotal = 0;

        target.setTotal(newTotal);

        // update points (redeem + earn)
        if (customer != null) {

            // max 50% of your total points can be used
            int maxUse = (int) (customer.getPoints() * 0.5);

            if (usePoints > maxUse) {
                lblMsg.setText("You can use up to " + maxUse + " points.");
                return;
            }

            if (usePoints > customer.getPoints()) {
                lblMsg.setText("Not enough points.");
                return;
            }

            // deduct used points
            customer.setPoints(customer.getPoints() - usePoints);

            // earn points (5% of the payment amount)
            int earn = (int) (payAmount * 0.05);
            customer.setPoints(customer.getPoints() + earn);

            customerService.saveOrUpdate(customer);
        }


        // finalize checkout
        target.setStatus("Checked-out");
        reservationService.update(target);

        ActivityLogService.getInstance().log(
                AppState.getCurrentAdmin().getEmail(),
                "CHECKOUT: ID=" + target.getId()
        );

        ((Stage) lblMsg.getScene().getWindow()).close();
    }

    private double parseOrZero(String s) {
        try { return Double.parseDouble(s); }
        catch (Exception e) { return 0; }
    }

    @FXML
    private void onCancel() {
        ((Stage) lblMsg.getScene().getWindow()).close();
    }
}
